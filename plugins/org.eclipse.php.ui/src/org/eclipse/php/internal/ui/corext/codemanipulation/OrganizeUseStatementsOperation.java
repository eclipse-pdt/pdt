/*******************************************************************************
 * Copyright (c) 2017 Alex Xu and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Alex Xu - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.corext.codemanipulation;

import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.runtime.*;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.dltk.compiler.problem.DefaultProblem;
import org.eclipse.dltk.compiler.problem.IProblem;
import org.eclipse.dltk.compiler.problem.IProblemIdentifier;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.core.index2.search.ISearchEngine.MatchRule;
import org.eclipse.dltk.core.index2.search.ModelAccess;
import org.eclipse.dltk.core.manipulation.SourceModuleChange;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.core.search.MethodNameMatch;
import org.eclipse.dltk.core.search.SearchEngine;
import org.eclipse.dltk.core.search.TypeNameMatch;
import org.eclipse.dltk.ui.viewsupport.BasicElementLabels;
import org.eclipse.ltk.core.refactoring.*;
import org.eclipse.php.core.ast.nodes.*;
import org.eclipse.php.core.ast.visitor.ApplyAll;
import org.eclipse.php.core.compiler.ast.nodes.NamespaceReference;
import org.eclipse.php.internal.core.ast.locator.PHPElementConciliator;
import org.eclipse.php.internal.core.ast.rewrite.ImportRewrite;
import org.eclipse.php.internal.core.ast.rewrite.ImportRewrite.ImportRewriteContext;
import org.eclipse.php.internal.core.compiler.ast.parser.PHPProblemIdentifier;
import org.eclipse.php.internal.core.search.*;
import org.eclipse.php.internal.core.typeinference.PHPSimpleTypes;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.corext.util.FieldNameMatchCollector;
import org.eclipse.php.internal.ui.corext.util.Messages;
import org.eclipse.php.internal.ui.corext.util.MethodNameMatchCollector;
import org.eclipse.php.internal.ui.corext.util.TypeNameMatchCollector;
import org.eclipse.php.internal.ui.text.correction.ASTResolving;
import org.eclipse.php.internal.ui.text.correction.ProblemLocation;
import org.eclipse.php.internal.ui.text.correction.SimilarElementsRequestor;
import org.eclipse.php.ui.editor.SharedASTProvider;
import org.eclipse.text.edits.MalformedTreeException;
import org.eclipse.text.edits.TextEdit;

public class OrganizeUseStatementsOperation implements IWorkspaceRunnable {
	public static interface IChooseImportQuery {
		/**
		 * Selects imports from a list of choices.
		 * 
		 * @param openChoices
		 *            From each array, a type reference has to be selected
		 * @param ranges
		 *            For each choice the range of the corresponding type reference.
		 * @return Returns <code>null</code> to cancel the operation, or the selected
		 *         imports.
		 */
		IElementNameMatch[] chooseImports(IElementNameMatch[][] openChoices, ISourceRange[] ranges);
	}

	private static class UnresolvableImportMatcher {
		private static final char FUNCTION_PREFIX = 'f';
		private static final char CONSTANT_PREFIX = 'c';
		private static final char NORMAL_PREFIX = 'n';

		static UnresolvableImportMatcher forProgram(Program cu) {
			Map<NamespaceDeclaration, Map<String, Set<String>>> elementImportsBySimpleName = new HashMap<>();
			List<NamespaceDeclaration> namespaces = cu.getNamespaceDeclarations();
			if (namespaces.size() > 0) {
				for (NamespaceDeclaration namespace : namespaces) {
					forProgram(cu, namespace, elementImportsBySimpleName);
				}
			} else {
				forProgram(cu, null, elementImportsBySimpleName);
			}

			return new UnresolvableImportMatcher(elementImportsBySimpleName);
		}

		private static void forProgram(Program cu, NamespaceDeclaration namespace,
				Map<NamespaceDeclaration, Map<String, Set<String>>> typeImportsBySimpleName) {
			typeImportsBySimpleName.put(namespace, new HashMap<>());
			Collection<UseStatement> unresolvableImports = determineUnresolvableImports(cu, namespace);
			for (UseStatement importDeclaration : unresolvableImports) {
				char prefix = NORMAL_PREFIX;
				if (importDeclaration.getStatementType() == UseStatement.T_FUNCTION) {
					prefix = FUNCTION_PREFIX;
				} else if (importDeclaration.getStatementType() == UseStatement.T_CONST) {
					prefix = CONSTANT_PREFIX;
				}
				for (UseStatementPart part : importDeclaration.parts()) {

					String qualifiedName = part.getName().getName();

					String simpleName = qualifiedName
							.substring(qualifiedName.lastIndexOf(NamespaceReference.NAMESPACE_SEPARATOR) + 1);

					Map<String, Set<String>> importsBySimpleName = typeImportsBySimpleName.get(namespace);
					Set<String> importsWithSimpleName = importsBySimpleName.get(simpleName);
					if (importsWithSimpleName == null) {
						importsWithSimpleName = new HashSet<>();
						importsBySimpleName.put(simpleName, importsWithSimpleName);
					}

					importsWithSimpleName.add(prefix + qualifiedName);
				}
			}
		}

		private static Collection<UseStatement> determineUnresolvableImports(Program program,
				NamespaceDeclaration namespaceDeclaration) {
			Collection<UseStatement> unresolvableImports = new ArrayList<>(
					program.getUseStatements(namespaceDeclaration).size());
			IProblem[] problems = program.getProblems();
			for (IProblem problem : problems) {
				IProblemIdentifier id = ((DefaultProblem) problem).getID();
				if (id == PHPProblemIdentifier.ImportNotFound) {
					UseStatement problematicImport = getProblematicImport(problem, program);
					if (problematicImport != null
							&& program.getNamespaceDeclaration(problematicImport.getStart()) == namespaceDeclaration) {
						unresolvableImports.add(problematicImport);
					}
				}
			}

			return unresolvableImports;
		}

		private static UseStatement getProblematicImport(IProblem problem, Program cu) {
			ASTNode coveringNode = new ProblemLocation(problem).getCoveringNode(cu);
			if (coveringNode != null && coveringNode instanceof UseStatement) {
				return (UseStatement) coveringNode;
			}
			return null;
		}

		private final Map<NamespaceDeclaration, Map<String, Set<String>>> fElementImportsBySimpleName;

		private UnresolvableImportMatcher(
				Map<NamespaceDeclaration, Map<String, Set<String>>> elementImportsBySimpleName) {
			fElementImportsBySimpleName = elementImportsBySimpleName;
		}

		private Set<String> matchImports(NamespaceDeclaration namespace, String simpleName) {
			Map<String, Set<String>> importsBySimpleName = fElementImportsBySimpleName.get(namespace);

			Set<String> matchingSingleImports = importsBySimpleName.get(simpleName);
			if (matchingSingleImports != null) {
				return Collections.unmodifiableSet(matchingSingleImports);
			}

			return Collections.emptySet();
		}

		Set<String> matchElementImports(NamespaceDeclaration namespace, String simpleName) {
			return matchImports(namespace, simpleName);
		}
	}

	private static class ElementReferenceProcessor {

		private static class UnresolvedElementData {
			final ASTNode ref;
			final int typeKinds;
			final List<IElementNameMatch> foundElementInfos;

			public UnresolvedElementData(ASTNode ref) {
				this.ref = ref;
				this.typeKinds = ASTResolving.getPossibleElementKinds(ref);
				this.foundElementInfos = new ArrayList<>(3);
			}

			public void addInfo(IElementNameMatch info) {
				for (int i = this.foundElementInfos.size() - 1; i >= 0; i--) {
					IElementNameMatch curr = this.foundElementInfos.get(i);
					if (curr.getContainerName().equals(info.getContainerName())) {
						return; // not added. already contains type with same
								// name
					}
				}
				foundElementInfos.add(info);
			}

		}

		private Map<NamespaceDeclaration, Set<String>> fOldSingleImports;

		private ImportRewrite fImpStructure;

		private final UnresolvableImportMatcher fUnresolvableImportMatcher;

		// private IPackageFragment fCurrPackage;
		//
		// private ScopeAnalyzer fAnalyzer;

		private Map<NamespaceDeclaration, Map<String, UnresolvedElementData>> fUnresolvedTypes = new HashMap<>();
		private Map<NamespaceDeclaration, Set<String>> fImportsAdded = new HashMap<>();
		private Map<NamespaceDeclaration, IElementNameMatch[][]> fOpenChoices = new HashMap<>();
		private Map<NamespaceDeclaration, SourceRange[]> fSourceRanges = new HashMap<>();
		private Program fRoot;

		public ElementReferenceProcessor(Map<NamespaceDeclaration, Set<String>> oldSingleImports, Program root,
				ImportRewrite impStructure, UnresolvableImportMatcher unresolvableImportMatcher) {
			fRoot = root;
			fOldSingleImports = oldSingleImports;
			fImpStructure = impStructure;
			fUnresolvableImportMatcher = unresolvableImportMatcher;

			// fAnalyzer= new ScopeAnalyzer(root);
			//
			// fCurrPackage= (IPackageFragment) cu.getParent()

			List<NamespaceDeclaration> namespaces = root.getNamespaceDeclarations();
			if (namespaces.size() > 0) {
				for (NamespaceDeclaration namespace : namespaces) {
					fImportsAdded.put(namespace, new HashSet<>());
					fUnresolvedTypes.put(namespace, new HashMap<>());
				}
			} else {
				fImportsAdded.put(null, new HashSet<>());
				fUnresolvedTypes.put(null, new HashMap<>());
			}
		}

		/**
		 * Tries to find the given element name and add it to the import structure.
		 * 
		 * @param ref
		 *            the name node
		 */
		public void add(ASTNode ref) {
			NamespaceDeclaration namespace = fRoot.getNamespaceDeclaration(ref.getStart());
			String elementName = null;
			IBinding binding = null;
			if (ref instanceof Identifier) {
				elementName = ((Identifier) ref).getName();
				binding = ((Identifier) ref).resolveBinding();
			} else if (ref instanceof Scalar) {
				elementName = ((Scalar) ref).getStringValue();
				binding = ((Scalar) ref).resolveBinding();
			} else {
				return;
			}
			String importName = elementName;

			int index = elementName.indexOf(NamespaceReference.NAMESPACE_DELIMITER);
			if (index > 0) {
				importName = elementName.substring(0, index);
				elementName = elementName.substring(index + 1);
			}

			if (fImportsAdded.get(namespace) == null || fImportsAdded.get(namespace).contains(importName)) {
				return;
			}

			if (binding != null) {
				IBinding typeBinding = null;
				int bindingKind = binding.getKind();
				switch (bindingKind) {
				case IBinding.TYPE:
					typeBinding = ((ITypeBinding) binding).getTypeDeclaration();
					break;
				case IBinding.METHOD:
					typeBinding = ((IMethodBinding) binding).getDeclaringClass();
					// do not import functions declared in global namespace
					if (typeBinding == null) {
						return;
					}
					break;
				case IBinding.VARIABLE:
					typeBinding = ((IVariableBinding) binding).getDeclaringClass();
					// do not import constants declared in global namespace
					if (typeBinding == null) {
						return;
					}
					break;
				default:
					return;
				}
				if (typeBinding != null) {
					String alias = null;
					String typeBindingName = typeBinding.getName();
					int importKind = ImportRewriteContext.KIND_TYPE;
					if (typeBindingName != null) {
						if (typeBindingName.startsWith(NamespaceReference.NAMESPACE_DELIMITER)) {
							typeBindingName = typeBindingName.substring(1);
						}
						if (bindingKind == IBinding.METHOD || bindingKind == IBinding.VARIABLE) {
							if (importName.equalsIgnoreCase(elementName)) {
								switch (bindingKind) {
								case IBinding.METHOD:
									importKind = ImportRewriteContext.KIND_FUNCTION;
									break;
								case IBinding.VARIABLE:
									importKind = ImportRewriteContext.KIND_CONSTANT;
									break;
								}
							}
							typeBindingName = typeBindingName + NamespaceReference.NAMESPACE_DELIMITER
									+ binding.getName();
						}
						int indexOfNs = typeBindingName.lastIndexOf(elementName);
						if (indexOfNs > 0 && !importName.equalsIgnoreCase(elementName)) {
							typeBindingName = typeBindingName.substring(0, indexOfNs);
							if (typeBindingName.endsWith(NamespaceReference.NAMESPACE_DELIMITER)) {
								typeBindingName = typeBindingName.substring(0, typeBindingName.length() - 1);
							}
						}
						String lastSeg = typeBindingName;
						String[] segs = typeBindingName.split("\\\\"); //$NON-NLS-1$
						if (segs.length > 0) {
							lastSeg = segs[segs.length - 1];
						}
						if (!lastSeg.equalsIgnoreCase(importName)) {
							alias = importName;
						}
					}
					fImpStructure.addImport(namespace, typeBindingName, alias, importKind);
					fImportsAdded.get(namespace).add(importName);
					return;
				}
			}

			fImportsAdded.get(namespace).add(elementName);
			fUnresolvedTypes.get(namespace).put(elementName, new UnresolvedElementData(ref));
		}

		public Map<NamespaceDeclaration, Boolean> process(IProgressMonitor monitor) {
			Map<NamespaceDeclaration, Boolean> hasOpenChoices = new HashMap<>();
			try {
				final IScriptProject project = fImpStructure.getSourceModule().getScriptProject();
				IDLTKSearchScope scope = SearchEngine.createSearchScope(project);

				List<NamespaceDeclaration> namespaces = fRoot.getNamespaceDeclarations();
				if (namespaces.size() > 0) {
					for (NamespaceDeclaration namespace : namespaces) {
						hasOpenChoices.put(namespace, internalProcess(namespace, scope, monitor));
					}
				} else {
					hasOpenChoices.put(null, internalProcess(null, scope, monitor));
				}
			} catch (Exception e) {
				PHPUiPlugin.log(e);
			} finally {
				monitor.done();
			}
			return hasOpenChoices;
		}

		private boolean internalProcess(NamespaceDeclaration namespace, IDLTKSearchScope scope,
				IProgressMonitor monitor) throws ModelException {
			int nUnresolved = fUnresolvedTypes.get(namespace).size();
			if (nUnresolved == 0) {
				return false;
			}
			final List<IElementNameMatch> typesFound = new ArrayList<>();
			final List<IElementNameMatch> methodsFound = new ArrayList<>();
			final List<IElementNameMatch> fieldsFound = new ArrayList<>();
			for (Iterator<String> iter = fUnresolvedTypes.get(namespace).keySet().iterator(); iter.hasNext();) {
				String key = iter.next();
				int typeKind = fUnresolvedTypes.get(namespace).get(key).typeKinds;
				ModelAccess modelAccess = new ModelAccess();
				if (typeKind == SimilarElementsRequestor.ALL_TYPES) {
					TypeNameMatchCollector collector = new TypeNameMatchCollector(typesFound);
					IType[] types = modelAccess.findTypes(key, MatchRule.EXACT, 0, 0, scope, monitor);
					for (IType type : types) {
						TypeNameMatch match = new PHPSearchTypeNameMatch(type, type.getFlags());
						collector.acceptTypeNameMatch(match);
					}
				} else if (typeKind == SimilarElementsRequestor.FUNCTIONS) {
					IMethod[] methods = modelAccess.findMethods(key, MatchRule.EXACT, 0, 0, scope, monitor);
					for (IMethod method : methods) {
						MethodNameMatchCollector collector = new MethodNameMatchCollector(methodsFound);
						MethodNameMatch match = new PHPSearchMethodNameMatch(method, method.getFlags());
						collector.acceptMethodNameMatch(match);
					}
				} else if (typeKind == SimilarElementsRequestor.CONSTANTS) {
					IField[] fields = modelAccess.findFields(key, MatchRule.EXACT, 0, 0, scope, monitor);
					for (IField field : fields) {
						FieldNameMatchCollector collector = new FieldNameMatchCollector(fieldsFound);
						FieldNameMatch match = new PHPSearchFieldNameMatch(field, field.getFlags());
						collector.acceptFieldNameMatch(match);
					}
				}
			}

			List<IElementNameMatch> elementsFound = new ArrayList<>();
			elementsFound.addAll(typesFound);
			elementsFound.addAll(methodsFound);
			elementsFound.addAll(fieldsFound);

			for (int i = 0; i < elementsFound.size(); i++) {
				IElementNameMatch curr = elementsFound.get(i);
				UnresolvedElementData data = fUnresolvedTypes.get(namespace).get(curr.getSimpleName());
				if (data != null && isOfKind(curr, data.typeKinds)) {
					data.addInfo(curr);
				}
			}

			for (Entry<String, UnresolvedElementData> entry : fUnresolvedTypes.get(namespace).entrySet()) {
				if (entry.getValue().foundElementInfos.size() == 0) { // No
																		// result
																		// found
																		// in
																		// search
					Set<String> matchingUnresolvableImports = fUnresolvableImportMatcher.matchElementImports(namespace,
							entry.getKey());
					if (!matchingUnresolvableImports.isEmpty()) {
						// If there are matching unresolvable import(s),
						// rely on them to provide the type.
						for (String string : matchingUnresolvableImports) {
							char prefix = string.charAt(0);
							int importKind = ImportRewriteContext.KIND_TYPE;
							switch (prefix) {
							case UnresolvableImportMatcher.FUNCTION_PREFIX:
								importKind = ImportRewriteContext.KIND_FUNCTION;
								break;
							case UnresolvableImportMatcher.CONSTANT_PREFIX:
								importKind = ImportRewriteContext.KIND_CONSTANT;
								break;
							}
							string = string.substring(1);
							fImpStructure.addImport(namespace, string, null, importKind, UNRESOLVABLE_IMPORT_CONTEXT);
						}
					}
				}
			}

			ArrayList<IElementNameMatch[]> openChoices = new ArrayList<>(nUnresolved);
			ArrayList<SourceRange> sourceRanges = new ArrayList<>(nUnresolved);
			for (Iterator<UnresolvedElementData> iter = fUnresolvedTypes.get(namespace).values().iterator(); iter
					.hasNext();) {
				UnresolvedElementData data = iter.next();
				IElementNameMatch[] openChoice = processElementInfo(namespace, data.foundElementInfos);
				if (openChoice != null) {
					openChoices.add(openChoice);
					sourceRanges.add(new SourceRange(data.ref.getStart(), data.ref.getLength()));
				}
			}
			if (openChoices.isEmpty()) {
				return false;
			}
			fOpenChoices.put(namespace, openChoices.toArray(new IElementNameMatch[openChoices.size()][]));
			fSourceRanges.put(namespace, sourceRanges.toArray(new SourceRange[sourceRanges.size()]));
			return true;

		}

		private IElementNameMatch[] processElementInfo(NamespaceDeclaration namespace,
				List<IElementNameMatch> elementRefsFound) {
			int nFound = elementRefsFound.size();
			if (nFound == 0) {
				// nothing found
				return null;
			} else if (nFound == 1) {
				IElementNameMatch elementRef = elementRefsFound.get(0);
				int importKind = ImportRewriteContext.KIND_TYPE;
				if (elementRef instanceof MethodNameMatch) {
					importKind = ImportRewriteContext.KIND_FUNCTION;
					if (StringUtils.isBlank(elementRef.getContainerName())) {
						return null;
					}
				} else if (elementRef instanceof FieldNameMatch) {
					importKind = ImportRewriteContext.KIND_CONSTANT;
					if (StringUtils.isBlank(elementRef.getContainerName())) {
						return null;
					}
				}
				fImpStructure.addImport(namespace, elementRef.getFullyQualifiedName(), importKind);
				return null;
			} else {
				// multiple found, use old imports to find an entry
				for (int i = 0; i < nFound; i++) {
					IElementNameMatch elementRef = elementRefsFound.get(i);
					int importKind = ImportRewriteContext.KIND_TYPE;
					if (elementRef instanceof MethodNameMatch) {
						importKind = ImportRewriteContext.KIND_FUNCTION;
					} else if (elementRef instanceof FieldNameMatch) {
						importKind = ImportRewriteContext.KIND_CONSTANT;
					}
					String fullName = elementRef.getFullyQualifiedName();
					if (fOldSingleImports.get(namespace).contains(fullName)) {
						// was single-imported
						fImpStructure.addImport(namespace, fullName, importKind);
						return null;
					}
				}
				// return the open choices
				return elementRefsFound.toArray(new IElementNameMatch[nFound]);
			}
		}

		private boolean isOfKind(IElementNameMatch curr, int typeKinds) {
			int flags = curr.getModifiers();
			if (Flags.isInterface(flags)) {
				return (typeKinds & SimilarElementsRequestor.INTERFACES) != 0;
			} else if (curr.getElementType() == IElementNameMatch.T_METHOD) {
				return (typeKinds & SimilarElementsRequestor.FUNCTIONS) != 0;
			} else if (curr.getElementType() == IElementNameMatch.T_FIELD) {
				return (typeKinds & SimilarElementsRequestor.CONSTANTS) != 0;
			}
			return (typeKinds & SimilarElementsRequestor.CLASSES) != 0;
		}

		public Map<NamespaceDeclaration, IElementNameMatch[][]> getChoices() {
			return fOpenChoices;
		}

		public Map<NamespaceDeclaration, SourceRange[]> getChoicesSourceRanges() {
			return fSourceRanges;
		}
	}

	/**
	 * Used to ensure that unresolvable imports don't get reduced into on-demand
	 * imports.
	 */
	private static ImportRewriteContext UNRESOLVABLE_IMPORT_CONTEXT = new ImportRewriteContext() {
		@Override
		public int findInContext(NamespaceDeclaration namespace, String qualifier, String name, int kind) {
			return RES_NAME_UNKNOWN;
		}
	};

	private int fNumberOfImportsAdded;
	private int fNumberOfImportsRemoved;

	private IChooseImportQuery fChooseImportQuery;

	private ISourceModule fSourceModule;

	private Program fASTRoot;

	public OrganizeUseStatementsOperation(ISourceModule sourceModule, Program astRoot,
			IChooseImportQuery chooseImportQuery) {
		fSourceModule = sourceModule;
		fASTRoot = astRoot;

		// fAllowSyntaxErrors= allowSyntaxErrors;
		fChooseImportQuery = chooseImportQuery;

		fNumberOfImportsAdded = 0;
		fNumberOfImportsRemoved = 0;

		// fParsingError= null;
	}

	public TextEdit createTextEdit(IProgressMonitor monitor)
			throws CoreException, OperationCanceledException, IOException {
		if (monitor == null) {
			monitor = new NullProgressMonitor();
		}
		try {
			fNumberOfImportsAdded = 0;
			fNumberOfImportsRemoved = 0;

			monitor.beginTask(Messages.format(CodeGenerationMessages.OrganizeImportsOperation_description,
					BasicElementLabels.getFileName(fSourceModule)), 9);

			Program astRoot = fASTRoot;
			if (astRoot == null) {
				astRoot = SharedASTProvider.getAST(fSourceModule, SharedASTProvider.WAIT_YES,
						SubMonitor.convert(monitor, 2));
				if (monitor.isCanceled())
					throw new OperationCanceledException();
			} else {
				monitor.worked(2);
			}

			ImportRewrite importsRewrite = ImportRewrite.create(astRoot, false);

			Map<NamespaceDeclaration, Set<String>> oldSingleImports = new HashMap<>();
			List<ASTNode> elementReferences = new ArrayList<>();

			if (!collectReferences(astRoot, elementReferences, oldSingleImports))
				return null;

			UnresolvableImportMatcher unresolvableImportMatcher = UnresolvableImportMatcher.forProgram(astRoot);

			ElementReferenceProcessor processor = new ElementReferenceProcessor(oldSingleImports, astRoot,
					importsRewrite, unresolvableImportMatcher);

			Iterator<ASTNode> refIterator = elementReferences.iterator();
			while (refIterator.hasNext()) {
				ASTNode elementRef = refIterator.next();
				processor.add(elementRef);
			}

			Map<NamespaceDeclaration, Boolean> hasOpenChoices = processor.process(SubMonitor.convert(monitor, 3));

			if (fChooseImportQuery != null) {
				Map<NamespaceDeclaration, IElementNameMatch[][]> choices = processor.getChoices();
				Map<NamespaceDeclaration, SourceRange[]> ranges = processor.getChoicesSourceRanges();
				for (Iterator<Entry<NamespaceDeclaration, Boolean>> iter = hasOpenChoices.entrySet().iterator(); iter
						.hasNext();) {
					Entry<NamespaceDeclaration, Boolean> entry = iter.next();
					NamespaceDeclaration namespace = entry.getKey();
					if (entry.getValue()) {
						IElementNameMatch[] chosen = fChooseImportQuery.chooseImports(choices.get(namespace),
								ranges.get(namespace));
						if (chosen == null) {
							// cancel pressed by the user
							return null;
						}
						for (int i = 0; i < chosen.length; i++) {
							IElementNameMatch elementInfo = chosen[i];
							if (elementInfo != null) {
								int importKind = ImportRewriteContext.KIND_TYPE;
								if (elementInfo.getElementType() == IElementNameMatch.T_METHOD) {
									importKind = ImportRewriteContext.KIND_FUNCTION;
									if (StringUtils.isBlank(elementInfo.getContainerName())) {
										return null;
									}
								} else if (elementInfo.getElementType() == IElementNameMatch.T_FIELD) {
									importKind = ImportRewriteContext.KIND_CONSTANT;
									if (StringUtils.isBlank(elementInfo.getContainerName())) {
										return null;
									}
								}
								importsRewrite.addImport(namespace, elementInfo.getFullyQualifiedName(), importKind);
							} else { // Skipped by user
								String elementName = choices.get(namespace)[i][0].getSimpleName();
								Set<String> matchingUnresolvableImports = unresolvableImportMatcher
										.matchElementImports(namespace, elementName);
								if (!matchingUnresolvableImports.isEmpty()) {
									// If there are matching unresolvable
									// import(s),
									// rely on them to provide the type.
									for (String string : matchingUnresolvableImports) {
										importsRewrite.addImport(namespace, string, UNRESOLVABLE_IMPORT_CONTEXT);
									}
								}
							}
						}
					}
				}
			}

			TextEdit result = importsRewrite.rewriteImports(SubMonitor.convert(monitor, 3));

			determineImportDifferences(importsRewrite, oldSingleImports.values());

			return result;
		} catch (Exception e) {
			PHPUiPlugin.log(e);
		} finally {
			monitor.done();
		}
		return null;
	}

	private void determineImportDifferences(ImportRewrite importsStructure,
			Collection<Set<String>> oldSingleImportsCollection) {
		ArrayList<String> importsAdded = new ArrayList<>();
		importsAdded.addAll(Arrays.asList(importsStructure.getCreatedImports()));

		for (Set<String> oldSingleImports : oldSingleImportsCollection) {
			Object[] content = oldSingleImports.toArray();
			for (int i = 0; i < content.length; i++) {
				String importName = (String) content[i];
				if (importsAdded.remove(importName))
					oldSingleImports.remove(importName);
			}
			fNumberOfImportsRemoved += oldSingleImports.size();
		}
		fNumberOfImportsAdded = importsAdded.size();
	}

	/**
	 * Runs the operation.
	 * 
	 * @param monitor
	 *            the progress monitor
	 * @throws CoreException
	 *             thrown when the operation failed
	 * @throws OperationCanceledException
	 *             Runtime error thrown when operation is canceled.
	 */
	@Override
	public void run(IProgressMonitor monitor) throws CoreException, OperationCanceledException {
		if (monitor == null) {
			monitor = new NullProgressMonitor();
		}
		try {
			monitor.beginTask(Messages.format(CodeGenerationMessages.OrganizeImportsOperation_description,
					BasicElementLabels.getFileName(fSourceModule)), 10);

			SourceModuleChange cuChange = new SourceModuleChange("OrganizeUseStatements", fSourceModule); //$NON-NLS-1$
			cuChange.setSaveMode(TextFileChange.LEAVE_DIRTY);
			TextChange change = cuChange;
			TextEdit edit = createTextEdit(SubMonitor.convert(monitor));
			if (edit != null) {
				change.setEdit(edit);
			}

			change.initializeValidationData(new NullProgressMonitor());
			RefactoringStatus valid = change.isValid(new NullProgressMonitor());
			if (valid.hasFatalError()) {
				IStatus status = new Status(IStatus.ERROR, PHPUiPlugin.ID, IStatus.ERROR,
						valid.getMessageMatchingSeverity(RefactoringStatus.FATAL), null);
				throw new CoreException(status);
			} else {
				IUndoManager manager = RefactoringCore.getUndoManager();
				Change undoChange;
				boolean successful = false;
				try {
					manager.aboutToPerformChange(change);
					undoChange = change.perform(new NullProgressMonitor());
					successful = true;
				} finally {
					manager.changePerformed(change, successful);
				}
				if (undoChange != null) {
					undoChange.initializeValidationData(new NullProgressMonitor());
					manager.addUndo("OrganizeUseStatements", undoChange); //$NON-NLS-1$
				}
			}
		} catch (MalformedTreeException | IOException e) {
			PHPUiPlugin.log(e);
		} finally {
			monitor.done();
		}
	}

	private boolean collectReferences(Program astRoot, List<ASTNode> elementReferences,
			Map<NamespaceDeclaration, Set<String>> oldSingleImports) {
		List<NamespaceDeclaration> namespaces = astRoot.getNamespaceDeclarations();
		if (namespaces.size() > 0) {
			for (NamespaceDeclaration namespace : namespaces) {
				collectImports(astRoot, namespace, oldSingleImports);
			}
		} else {
			collectImports(astRoot, null, oldSingleImports);
		}
		astRoot.accept(new ReferencesCollector(elementReferences));
		return true;
	}

	private void collectImports(Program astRoot, NamespaceDeclaration namespace,
			Map<NamespaceDeclaration, Set<String>> oldSingleImports) {
		oldSingleImports.put(namespace, new HashSet<>());
		List<UseStatement> imports = astRoot.getUseStatements(namespace);
		for (int i = 0; i < imports.size(); i++) {
			UseStatement curr = imports.get(i);
			for (UseStatementPart part : curr.parts()) {
				String importName = part.getName().getName();
				if (part.getAlias() != null) {
					importName += " as " + part.getAlias().getName(); //$NON-NLS-1$
				}
				oldSingleImports.get(namespace).add(importName);
			}
		}
	}

	public int getNumberOfImportsAdded() {
		return fNumberOfImportsAdded;
	}

	public int getNumberOfImportsRemoved() {
		return fNumberOfImportsRemoved;
	}

	/**
	 * @return Returns the scheduling rule for this operation
	 */
	public ISchedulingRule getScheduleRule() {
		return fSourceModule.getResource();
	}

	static class ReferencesCollector extends ApplyAll {
		private static final List<String> TYPE_SKIP = new ArrayList<>();
		private static final Pattern CONSTANT_NAME = Pattern.compile("[a-zA-Z_\\x7f-\\xff][a-zA-Z0-9_\\x7f-\\xff]*");

		static {
			TYPE_SKIP.add("parent"); //$NON-NLS-1$
			TYPE_SKIP.add("self"); //$NON-NLS-1$
			TYPE_SKIP.add("static"); //$NON-NLS-1$
			TYPE_SKIP.add("class"); //$NON-NLS-1$
		}
		List<ASTNode> fElementReferences;

		public ReferencesCollector(List<ASTNode> elementReferences) {
			fElementReferences = elementReferences;
		}

		@Override
		protected boolean apply(ASTNode node) {
			return true;
		}

		@Override
		public boolean visit(UseStatement statement) {
			return false;
		}

		@Override
		public boolean visit(NamespaceName name) {
			if (!name.isGlobal() && !(name.getParent() instanceof NamespaceDeclaration)) {
				if (PHPSimpleTypes.isHintable(name.getName(), name.getAST().apiLevel())
						|| TYPE_SKIP.contains(name.getName())) {
					return false;
				}
				List<Identifier> segs = name.segments();
				if (segs.size() > 0) {
					Identifier node = segs.get(segs.size() - 1);
					if (PHPElementConciliator.concile(node) == PHPElementConciliator.CONCILIATOR_CLASSNAME
							|| PHPElementConciliator.concile(node) == PHPElementConciliator.CONCILIATOR_TRAITNAME
							|| PHPElementConciliator.concile(node) == PHPElementConciliator.CONCILIATOR_CONSTANT) {
						fElementReferences.add(name);
					}
				}
			}
			return false;
		}

		@Override
		public boolean visit(FunctionName functionName) {
			if (functionName.getName() instanceof NamespaceName) {
				NamespaceName name = (NamespaceName) functionName.getName();
				if (!name.isGlobal()) {
					fElementReferences.add(name);
				}
			}
			return false;
		}

		@Override
		public boolean visit(Scalar scalar) {
			if (scalar.getScalarType() == Scalar.TYPE_STRING
					&& CONSTANT_NAME.matcher(scalar.getStringValue()).matches()) {
				fElementReferences.add(scalar);
			}
			return false;
		}

	}
}
