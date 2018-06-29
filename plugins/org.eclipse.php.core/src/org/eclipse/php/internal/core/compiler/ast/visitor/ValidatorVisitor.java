/*******************************************************************************
 * Copyright (c) 2017, 2018 Alex Xu and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Alex Xu - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.compiler.ast.visitor;

import java.text.MessageFormat;
import java.util.*;
import java.util.regex.Matcher;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.declarations.TypeDeclaration;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ast.references.ConstantReference;
import org.eclipse.dltk.ast.references.TypeReference;
import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.dltk.ast.statements.Statement;
import org.eclipse.dltk.compiler.problem.*;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.core.builder.IBuildContext;
import org.eclipse.dltk.core.builder.ISourceLineTracker;
import org.eclipse.dltk.core.index2.search.ISearchEngine.MatchRule;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.core.search.SearchEngine;
import org.eclipse.osgi.util.NLS;
import org.eclipse.php.core.PHPVersion;
import org.eclipse.php.core.compiler.PHPFlags;
import org.eclipse.php.core.compiler.ast.nodes.*;
import org.eclipse.php.core.compiler.ast.validator.IValidatorExtension;
import org.eclipse.php.core.compiler.ast.validator.IValidatorVisitor;
import org.eclipse.php.core.compiler.ast.visitor.PHPASTVisitor;
import org.eclipse.php.core.project.ProjectOptions;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.codeassist.PHPSelectionEngine;
import org.eclipse.php.internal.core.compiler.ast.parser.ASTUtils;
import org.eclipse.php.internal.core.compiler.ast.parser.Messages;
import org.eclipse.php.internal.core.compiler.ast.parser.PHPProblemIdentifier;
import org.eclipse.php.internal.core.language.PHPVariables;
import org.eclipse.php.internal.core.model.PHPModelAccess;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;
import org.eclipse.php.internal.core.typeinference.PHPSimpleTypes;
import org.eclipse.php.internal.core.typeinference.evaluators.PHPEvaluationUtils;
import org.eclipse.php.internal.core.util.text.PHPTextSequenceUtilities;

public class ValidatorVisitor extends PHPASTVisitor implements IValidatorVisitor {
	private static final String EXTENSION_POINT = PHPCorePlugin.ID + ".validatorExtension"; //$NON-NLS-1$
	private static final String ATTR_CLASS = "class"; //$NON-NLS-1$

	private static final String PAAMAYIM_NEKUDOTAIM = "::"; //$NON-NLS-1$
	private static final List<String> TYPE_SKIP = new ArrayList<>();
	private static final List<String> COMMENT_TYPE_SKIP = new ArrayList<>();

	static {
		TYPE_SKIP.add("parent"); //$NON-NLS-1$
		TYPE_SKIP.add("self"); //$NON-NLS-1$
		TYPE_SKIP.add("static"); //$NON-NLS-1$
		TYPE_SKIP.add("null"); //$NON-NLS-1$
		COMMENT_TYPE_SKIP.add("true"); //$NON-NLS-1$
		COMMENT_TYPE_SKIP.add("false"); //$NON-NLS-1$
	}

	private Map<String, UsePartInfo> usePartInfo = new LinkedHashMap<>();
	private Map<String, Boolean> elementExists = new HashMap<>();
	private NamespaceDeclaration currentNamespace;
	private Set<String> typeDeclared = new HashSet<>();
	private ArrayList<VarComment> varComments = new ArrayList<>();
	private boolean hasNamespace;
	private ISourceModule sourceModule;
	private PHPVersion version;
	private IBuildContext context;
	private IValidatorExtension[] extensions;
	/**
	 * Used to hold visited namespaces in depth
	 */
	private Stack<NamespaceDeclaration> fNamespaceDeclarations = new Stack<>();

	@SuppressWarnings("null")
	public ValidatorVisitor(IBuildContext context) {
		this.context = context;
		this.sourceModule = context.getSourceModule();
		this.version = ProjectOptions.getPHPVersion(sourceModule);

		IConfigurationElement[] elements = Platform.getExtensionRegistry().getConfigurationElementsFor(EXTENSION_POINT);
		List<IValidatorExtension> list = new ArrayList<>(elements.length);
		for (IConfigurationElement el : elements) {
			try {
				Object ext = el.createExecutableExtension(ATTR_CLASS);
				if (ext != null && ext instanceof IValidatorExtension) {
					IValidatorExtension tmp = (IValidatorExtension) ext;
					if (tmp.isSupported(context)) {
						tmp.init(context, this);
						list.add(tmp);
					}
				}
			} catch (CoreException e) {
			}
		}
		extensions = list.toArray(new IValidatorExtension[0]);
	}

	@Override
	public boolean visit(ModuleDeclaration s) throws Exception {
		if (s instanceof PHPModuleDeclaration) {
			// See also VariableReferenceEvaluator.init().
			varComments.addAll(((PHPModuleDeclaration) s).getVarComments());
		}
		return super.visit(s);
	}

	@Override
	public boolean endvisit(ModuleDeclaration s) throws Exception {
		boolean res = super.endvisit(s);

		if (!hasNamespace) {
			checkUnusedImport();
		}
		return res;
	}

	@Override
	public boolean visit(NamespaceDeclaration s) throws Exception {
		hasNamespace = true;
		currentNamespace = s;
		fNamespaceDeclarations.push(s);
		if (fNamespaceDeclarations.size() > 1) {
			reportProblem(s, Messages.NestedNamespaceDeclarations, PHPProblemIdentifier.NestedNamespaceDeclarations,
					ProblemSeverities.Error);
		}
		visitGeneral(s);
		return true;
	}

	@Override
	public boolean endvisit(NamespaceDeclaration s) throws Exception {
		boolean res = super.endvisit(s);
		checkUnusedImport();
		fNamespaceDeclarations.pop();
		return res;
	}

	@Override
	public boolean visit(PHPMethodDeclaration s) throws Exception {
		if (s.getPHPDoc() != null) {
			s.getPHPDoc().traverse(this);
		}
		return visitGeneral(s);
	}

	@Override
	public boolean visit(PHPFieldDeclaration s) throws Exception {
		if (s.getPHPDoc() != null) {
			s.getPHPDoc().traverse(this);
		}
		return super.visit(s);
	}

	@Override
	public boolean visit(PHPCallExpression node) throws Exception {
		if (node.getReceiver() != null) {
			node.getReceiver().traverse(this);
		}
		if (node.getArgs() != null) {
			node.getArgs().traverse(this);
		}
		visitGeneral(node);
		return false;
	}

	@Override
	public boolean visit(FullyQualifiedReference node) throws Exception {
		if (node.getElementType() == FullyQualifiedReference.T_TYPE) {
			return visit((TypeReference) node);
		}
		return super.visit(node);
	}

	@Override
	public boolean visit(TypeReference node) throws Exception {
		return visit(node, ProblemSeverities.Error, false);
	}

	private boolean visit(TypeReference node, ProblemSeverity severity, boolean isInDoc) throws Exception {
		boolean skip = false;
		if (isInDoc) {
			skip = PHPSimpleTypes.isSimpleType(node.getName());
		} else {
			skip = PHPSimpleTypes.isHintable(node.getName(), version);
		}
		if (skip || TYPE_SKIP.contains(node.getName().toLowerCase())) {
			visitGeneral(node);
			return true;
		}
		TypeReferenceInfo tri = new TypeReferenceInfo(node, false);
		String nodeName = tri.getTypeName();
		String key = null;
		if (tri.isGlobal()) {
			key = nodeName;
		} else {
			key = getFirstSegmentOfTypeName(nodeName);
		}
		UsePartInfo info = usePartInfo.get(key.toLowerCase());

		if (info != null) {
			info.increaseRefCount();
		}
		boolean isFound = findElement(tri);
		if (!isFound) {
			reportProblem(node, Messages.UndefinedType, PHPProblemIdentifier.UndefinedType, node.getName(), severity);
		}
		visitGeneral(node);
		return false;
	}

	@Override
	public boolean visit(AnonymousClassDeclaration s) throws Exception {
		int end = s.start();
		int start = end - 1;
		while (sourceModule.getSource().charAt(start) != ' ') {
			start--;
		}
		checkUnimplementedMethods(s, start + 1, end);

		IModelElement element = sourceModule.getElementAt(s.start());
		if (s.getSuperClass() != null && element != null) {
			checkSuperclass(s.getSuperClass(), false, element.getElementName());
		}

		Collection<TypeReference> interfaces = s.getInterfaceList();
		if (interfaces != null && element != null) {
			for (TypeReference itf : interfaces) {
				checkSuperclass(itf, true, element.getElementName());
			}
		}
		return super.visit(s);
	}

	@Override
	public boolean visit(ClassDeclaration s) throws Exception {
		checkUnimplementedMethods(s, s.getRef());
		if (s.getSuperClass() != null) {
			checkSuperclass(s.getSuperClass(), false, s.getName());
		}

		Collection<TypeReference> interfaces = s.getInterfaceList();
		if (interfaces != null && interfaces.size() > 0) {
			for (TypeReference itf : interfaces) {
				checkSuperclass(itf, true, s.getName());
			}
		}
		return visitGeneral(s);
	}

	@Override
	public boolean visit(ClassInstanceCreation s) throws Exception {
		if (s.getClassName() instanceof TypeReference) {
			TypeReferenceInfo tri = new TypeReferenceInfo((TypeReference) s.getClassName(), false);
			IType[] types = getAllTypes(tri.getFullyQualifiedName(), sourceModule, s.getClassName().sourceStart());
			for (IType type : types) {
				if (PHPFlags.isTrait(type.getFlags()) || PHPFlags.isInterface(type.getFlags())
						|| PHPFlags.isAbstract(type.getFlags())) {
					reportProblem(s.getClassName(), Messages.CannotInstantiateType,
							PHPProblemIdentifier.CannotInstantiateType, type.getElementName(), ProblemSeverities.Error);
					break;
				}
			}
		}
		return visitGeneral(s);
	}

	@Override
	public boolean visit(InterfaceDeclaration s) throws Exception {
		if (s.getSuperClasses() == null) {
			return visitGeneral(s);
		}
		for (ASTNode node : s.getSuperClasses().getChilds()) {
			checkSuperclass((TypeReference) node, true, s.getName());
		}
		return visitGeneral(s);
	}

	@Override
	public boolean visit(TypeDeclaration s) throws Exception {
		if (!(s instanceof NamespaceDeclaration)) {
			checkDuplicateTypeDeclaration(s);
		}
		return super.visit(s);
	}

	@Override
	public boolean visit(UsePart part) throws Exception {
		UsePartInfo info = new UsePartInfo(part);
		TypeReferenceInfo tri = info.getTypeReferenceInfo();

		if (tri.getTypeReference() instanceof FullyQualifiedReference) {
			int elementType = ((FullyQualifiedReference) tri.getTypeReference()).getElementType();
			if (elementType == FullyQualifiedReference.T_CONSTANT
					|| elementType == FullyQualifiedReference.T_FUNCTION) {
				// TODO implement later, skip check for function and constant
				// for now
				visitGeneral(part);
				return false;
			}
		}

		String name = tri.getTypeName();
		String currentNamespaceName;
		if (currentNamespace == null || currentNamespace.isGlobal()) {
			currentNamespaceName = ""; //$NON-NLS-1$
		} else {
			currentNamespaceName = currentNamespace.getName();
		}
		String lcName = info.getRealName().toLowerCase();
		if (!findElement(tri)) {
			info.isProblemReported = true;
			reportProblem(tri.getTypeReference(), Messages.ImportNotFound, PHPProblemIdentifier.ImportNotFound, name,
					ProblemSeverities.Error);
		} else if (usePartInfo.get(lcName) != null) {
			info.isProblemReported = true;
			reportProblem(tri.getTypeReference(), Messages.DuplicateImport, PHPProblemIdentifier.DuplicateImport,
					new String[] { name, info.getRealName() }, ProblemSeverities.Error);
		} else if (!info.isAlias && info.getNamespaceName().equals(currentNamespaceName)) {
			info.isProblemReported = true;
			reportProblem(tri.getTypeReference(), Messages.UnnecessaryImport, PHPProblemIdentifier.UnnecessaryImport,
					new String[] { name }, ProblemSeverities.Warning);
		}
		usePartInfo.put(lcName, info);
		visitGeneral(part);
		return false;
	}

	@Override
	public boolean visit(TraitUseStatement s) throws Exception {
		List<TypeReference> traits = s.getTraitList();
		for (TypeReference trait : traits) {
			TypeReferenceInfo tri = new TypeReferenceInfo(trait, false);
			IType[] types = getAllTypes(tri.getFullyQualifiedName(), sourceModule, trait.start());
			for (IType type : types) {
				if (!PHPFlags.isTrait(type.getFlags())) {
					reportProblem(trait, Messages.CannotUseTypeAsTrait, PHPProblemIdentifier.CannotUseTypeAsTrait,
							new String[] { trait.getName() }, ProblemSeverities.Error);
					break;
				}
			}
		}
		return visitGeneral(s);
	}

	/**
	 * Generic checks to only visit PHPDoc type references whose names are valid php
	 * identifier names. See also
	 * {@link PHPSelectionEngine#lookForMatchingElements()} for more complete and
	 * precise PHPDoc type references handling.
	 */
	@SuppressWarnings("null")
	private void visitCommentType(TypeReference typeReference, ProblemSeverity severity) throws Exception {
		if (COMMENT_TYPE_SKIP.contains(typeReference.getName().toLowerCase())) {
			return;
		}

		String name = typeReference.getName();
		assert name.length() > 0;

		int idx = name.indexOf(PAAMAYIM_NEKUDOTAIM);
		if (idx == -1) {
			// look if complete name is a valid identifier name
			if (PHPTextSequenceUtilities.readIdentifierStartIndex(version, name, name.length(), false) == 0) {
				visit(typeReference, severity, true);
			}
		} else if (idx > 0) {
			// look if name part before "::" is a valid (and non-empty)
			// identifier name
			if (PHPTextSequenceUtilities.readIdentifierStartIndex(version, name, idx, false) == 0) {
				visit(new TypeReference(typeReference.start(), typeReference.start() + idx, name.substring(0, idx)),
						severity, true);
			}
		}
	}

	private void visitCommentTypes(TypeReference fullTypeReference) throws Exception {
		String fullTypeName = fullTypeReference.getName();
		// look for all type names inside fullTypeName by removing all type
		// delimiters, regardless if fullTypeName's content respects PHPDoc
		// standards or not
		Matcher matcher = PHPEvaluationUtils.TYPE_DELIMS_PATTERN.matcher(fullTypeName);
		int start = 0;
		while (matcher.find()) {
			if (matcher.start() != start) {
				String typeName = fullTypeName.substring(start, matcher.start());
				TypeReference typeReference = new TypeReference(fullTypeReference.start() + start,
						fullTypeReference.start() + start + typeName.length(), typeName);
				visitCommentType(typeReference, ProblemSeverities.Warning);
			}
			start = matcher.end();
		}
		if (start == 0) {
			visitCommentType(fullTypeReference, ProblemSeverities.Warning);
		} else if (start != fullTypeName.length()) {
			String typeName = fullTypeName.substring(start);
			TypeReference typeReference = new TypeReference(fullTypeReference.start() + start,
					fullTypeReference.start() + start + typeName.length(), typeName);
			visitCommentType(typeReference, ProblemSeverities.Warning);
		}
	}

	@Override
	public boolean visit(PHPDocTag phpDocTag) throws Exception {
		for (TypeReference fullTypeReference : phpDocTag.getTypeReferences()) {
			visitCommentTypes(fullTypeReference);
		}
		visitGeneral(phpDocTag);
		return false;
	}

	/**
	 * Fake VarComment visitor.
	 */
	private boolean visit(VarComment varComment) throws Exception {
		for (TypeReference fullTypeReference : varComment.getTypeReferences()) {
			visitCommentTypes(fullTypeReference);
		}
		visitGeneral(varComment);
		return false;
	}

	private void checkVarComment(VariableReference s) throws Exception {
		// See also VariableReferenceEvaluator.init().
		// XXX: we only analyze VarComments that are located before some
		// VariableReferences but we don't check that those VarComments are
		// *directly* located before the VariableReferences.
		int minPos = currentNamespace != null ? currentNamespace.sourceStart() : 0;
		int maxPos = s.sourceStart();
		Iterator<VarComment> itr = varComments.iterator();
		while (itr.hasNext()) {
			VarComment varComment = itr.next();
			if (varComment.sourceStart() < minPos) {
				itr.remove();
				continue;
			}
			if (varComment.sourceEnd() > maxPos) {
				break;
			}
			visit(varComment);
			itr.remove();
		}
	}

	@Override
	public boolean visit(VariableReference s) throws Exception {
		checkVarComment(s);
		return super.visit(s);
	}

	@Override
	public boolean visit(ArrayVariableReference s) throws Exception {
		checkVarComment(s);
		return super.visit(s);
	}

	private void checkDuplicateTypeDeclaration(TypeDeclaration node) {
		String name = node.getName();
		String currentNamespaceName;
		if (currentNamespace == null || currentNamespace.isGlobal()) {
			currentNamespaceName = ""; //$NON-NLS-1$
		} else {
			currentNamespaceName = currentNamespace.getName();
		}
		boolean isDuplicateWithUse = false;
		UsePartInfo info = usePartInfo.get(name.toLowerCase());
		if (info != null) {
			String fullyQualifiedName = PHPModelUtils.concatFullyQualifiedNames(NamespaceReference.NAMESPACE_DELIMITER,
					currentNamespaceName, name);
			if (!info.getFullyQualifiedName().equals(fullyQualifiedName)) {
				isDuplicateWithUse = true;
			}
		}
		if (isDuplicateWithUse || !typeDeclared.add(name.toLowerCase())) {
			reportProblem(node.getRef(), Messages.DuplicateDeclaration, PHPProblemIdentifier.DuplicateDeclaration, name,
					ProblemSeverities.Error);
		}
	}

	private void checkUnimplementedMethods(Statement statement, ASTNode classNode) throws ModelException {
		checkUnimplementedMethods(statement, classNode.sourceStart(), classNode.sourceEnd());
	}

	private void checkUnimplementedMethods(Statement statement, int nodeStart, int nodeEnd) throws ModelException {
		IModelElement element = sourceModule.getElementAt(statement.start());
		if (!(element instanceof IType)) {
			return;
		}
		IType type = (IType) element;
		if (type.getSuperClasses().length > 0 && !PHPFlags.isAbstract(type.getFlags())) {
			IMethod[] methods = PHPModelUtils.getUnimplementedMethods(type, null);
			for (IMethod method : methods) {
				if (method.getParent().getElementName().equals(type.getElementName())) {
					continue;
				}
				StringBuilder methodName = new StringBuilder(method.getParent().getElementName()).append("::"); //$NON-NLS-1$
				PHPModelUtils.getMethodLabel(method, methodName);
				String message = Messages.getString("AbstractMethodMustBeImplemented", //$NON-NLS-1$
						new String[] { type.getElementName(), methodName.toString() });
				reportProblem(nodeStart, nodeEnd, message, PHPProblemIdentifier.AbstractMethodMustBeImplemented,
						ProblemSeverities.Error);
			}
		}
	}

	private void checkSuperclass(TypeReference superClass, boolean isInterface, String className)
			throws ModelException {
		if (superClass != null) {
			TypeReferenceInfo refInfo = new TypeReferenceInfo(superClass, false);
			IType[] types = getAllTypes(refInfo.getFullyQualifiedName(), sourceModule, superClass.sourceStart());
			for (IType type : types) {
				if (!isInterface) {
					if (PHPFlags.isTrait(type.getFlags()) || PHPFlags.isInterface(type.getFlags())) {
						reportProblem(superClass, Messages.SuperclassMustBeAClass,
								PHPProblemIdentifier.SuperclassMustBeAClass,
								new String[] { superClass.getName(), className }, ProblemSeverities.Error);
					}
					if (PHPFlags.isFinal(type.getFlags())) {
						reportProblem(superClass, Messages.ClassExtendFinalClass,
								PHPProblemIdentifier.ClassExtendFinalClass,
								new String[] { className, type.getElementName() }, ProblemSeverities.Error);
					}
				} else {
					if (PHPFlags.isTrait(type.getFlags()) || !PHPFlags.isInterface(type.getFlags())) {
						reportProblem(superClass, Messages.SuperInterfaceMustBeAnInterface,
								PHPProblemIdentifier.SuperInterfaceMustBeAnInterface,
								new String[] { superClass.getName(), className }, ProblemSeverities.Error);
					}
				}
			}
		}
	}

	private void checkUnusedImport() {
		Collection<UsePartInfo> useInfos = usePartInfo.values();
		for (UsePartInfo useInfo : useInfos) {
			if (!useInfo.isProblemReported && useInfo.getRefCount() == 0) {
				FullyQualifiedReference m = useInfo.getUsePart().getNamespace();
				String name = useInfo.getUsePart().getFullUseStatementName();
				reportProblem(m, Messages.UnusedImport, PHPProblemIdentifier.UnusedImport, name,
						ProblemSeverities.Warning);
			}
		}
		usePartInfo.clear();
		elementExists.clear();
		typeDeclared.clear();
	}

	private boolean findElement(TypeReferenceInfo info) {
		String name = info.getFullyQualifiedName();
		if (elementExists.containsKey(name)) {
			return elementExists.get(name);
		}

		boolean isFound = false;
		try {
			TypeReference type = info.getTypeReference();
			int elementType = FullyQualifiedReference.T_TYPE;
			if (type instanceof FullyQualifiedReference) {
				elementType = ((FullyQualifiedReference) type).getElementType();
			}
			switch (elementType) {
			case FullyQualifiedReference.T_TYPE:
				IModelElement[] elements = PHPModelUtils.getTypes(name, sourceModule, type.start(), null);
				if (elements.length == 0) {
					elements = PHPModelUtils.getTraits(name, sourceModule, type.start(), null, null);
				}
				if (elements.length == 0 && info.isUseStatement()) {
					elements = sourceModule.codeSelect(type.start(), type.end() - type.start());
					if (elements.length == 0) {
						IDLTKSearchScope scope = SearchEngine.createSearchScope(sourceModule.getScriptProject());
						IType[] namespaces = PHPModelAccess.getDefault().findNamespaces(null, info.getTypeName(),
								MatchRule.PREFIX, 0, 0, scope, null);
						for (IType namespace : namespaces) {
							String namespaceName = namespace.getElementName();
							String typeName = info.getTypeName();
							if (namespaceName.length() > typeName.length() && namespaceName
									.charAt(typeName.length()) == NamespaceReference.NAMESPACE_SEPARATOR) {
								isFound = true;
								break;
							}
						}
					}
				}

				if (elements.length > 0) {
					isFound = true;
				}
				break;
			case FullyQualifiedReference.T_CONSTANT:
			case FullyQualifiedReference.T_FUNCTION:
				// TODO implement later, skip check for function and constant
				// for now
				isFound = true;
				break;
			default:
				isFound = false;
				break;
			}
		} catch (ModelException e) {
			PHPCorePlugin.log(e);
		}
		elementExists.put(name, isFound);
		return isFound;
	}

	private void reportProblem(ASTNode s, String message, IProblemIdentifier id, String[] stringArguments,
			ProblemSeverity severity) {
		message = MessageFormat.format(message, (Object[]) stringArguments);
		reportProblem(s, message, id, severity);
	}

	@Override
	public void reportProblem(ASTNode s, String message, IProblemIdentifier id, ProblemSeverity severity) {

		for (IValidatorExtension extension : extensions) {
			if (extension.skipProblem(s, message, id)) {
				return;
			}
		}
		int start = 0, end = 0;
		if (s != null) {
			start = s.sourceStart();
			end = s.sourceEnd();
		} else {
			start = end = context.getLineTracker().getLineOffset(1);
		}
		reportProblem(start, end, message, id, severity);
	}

	private void reportProblem(ASTNode s, String message, IProblemIdentifier id, String stringArguments,
			ProblemSeverity severity) {
		reportProblem(s, message, id, new String[] { stringArguments }, severity);
	}

	@Override
	public void reportProblem(int start, int end, String message, IProblemIdentifier id, ProblemSeverity severity) {
		for (IValidatorExtension extension : extensions) {
			if (extension.skipProblem(start, end, message, id)) {
				return;
			}
		}
		ISourceLineTracker tracker = context.getLineTracker();
		int line = tracker.getLineNumberOfOffset(start);
		IProblem problem = new DefaultProblem(context.getFile().getName(), message, id, null, severity, start, end,
				line, 0);
		context.getProblemReporter().reportProblem(problem);
	}

	private String getFirstSegmentOfTypeName(String typeName) {
		if (typeName != null) {
			String[] segments = typeName.split("\\\\"); //$NON-NLS-1$
			for (String segment : segments) {
				if (segment.trim().length() > 0) {
					return segment;
				}
			}
		}
		return ""; //$NON-NLS-1$
	}

	/**
	 * Returns all types of the given type name(class, interface, trait)
	 * 
	 * @param typeName
	 * @param sourceModule
	 * @param offset
	 * @return
	 */
	private IType[] getAllTypes(String typeName, ISourceModule sourceModule, int offset) {
		try {
			List<IType> lists = new ArrayList<>();
			IType[] types = PHPModelUtils.getTypes(typeName, sourceModule, offset, null);
			lists.addAll(Arrays.asList(types));
			types = PHPModelUtils.getTraits(typeName, sourceModule, offset, null, null);
			lists.addAll(Arrays.asList(types));
			return lists.toArray(new IType[0]);
		} catch (ModelException e) {
			PHPCorePlugin.log(e);
		}
		return new IType[0];
	}

	private class UsePartInfo implements IUsePartInfo {
		private UsePart usePart;
		private String realName;
		private int refCount;
		private String fullyQualifiedName;
		private TypeReferenceInfo tri;
		private boolean isAlias = false;
		private boolean isProblemReported = false;

		public UsePartInfo(UsePart usePart) {
			this.usePart = usePart;

			if (usePart.getGroupNamespace() == null) {
				tri = new TypeReferenceInfo(usePart.getNamespace(), true);
			} else {
				tri = new TypeReferenceInfo(ASTUtils.createFakeGroupUseType(usePart), true);
			}

			if (usePart.getAlias() != null) {
				realName = usePart.getAlias().getName();
				isAlias = true;
			} else {
				realName = usePart.getNamespace().getName();
			}
			if (tri.getNamespaceName() != null) {
				fullyQualifiedName = tri.getNamespaceName();
			}
			fullyQualifiedName = PHPModelUtils.concatFullyQualifiedNames(fullyQualifiedName,
					usePart.getNamespace().getName());
			if (fullyQualifiedName.length() > 0
					&& fullyQualifiedName.charAt(0) != NamespaceReference.NAMESPACE_SEPARATOR) {
				fullyQualifiedName = NamespaceReference.NAMESPACE_SEPARATOR + fullyQualifiedName;
			}
		}

		@Override
		public UsePart getUsePart() {
			return usePart;
		}

		@Override
		public int getRefCount() {
			return refCount;
		}

		@Override
		public void increaseRefCount() {
			refCount++;
		}

		@Override
		public String getRealName() {
			return realName;
		}

		@Override
		public String getFullyQualifiedName() {
			return fullyQualifiedName;
		}

		@Override
		public String getNamespaceName() {
			return tri.getNamespaceName();
		}

		@Override
		public TypeReferenceInfo getTypeReferenceInfo() {
			return tri;
		}

		@Override
		public boolean isAlias() {
			return isAlias;
		}

		@Override
		public String toString() {
			String str = "use " + fullyQualifiedName; //$NON-NLS-1$
			if (isAlias) {
				str += " as " + realName; //$NON-NLS-1$
			}
			return str;
		}
	}

	private class TypeReferenceInfo implements ITypeReferenceInfo {

		private TypeReference typeReference;
		private boolean isGlobal = false;
		private boolean hasNamespace = false;
		private String namespaceName = ""; //$NON-NLS-1$
		private String typeName;
		private String fullyQualifiedName;
		private boolean isUseStatement;

		public TypeReferenceInfo(TypeReference typeReference, boolean isUseStatement) {
			this.typeReference = typeReference;
			this.isUseStatement = isUseStatement;
			FullyQualifiedReference fullTypeReference = null;
			if (typeReference instanceof FullyQualifiedReference) {
				fullTypeReference = (FullyQualifiedReference) typeReference;
				if (fullTypeReference.getNamespace() != null) {
					if (!fullTypeReference.getNamespace().isLocal()) {
						hasNamespace = true;
						namespaceName = fullTypeReference.getNamespace().getName();
					}
					// for use statement, no need to lookup the use statement
					// to compute namespace name
					if (!isUseStatement) {
						// use first segment of namespace to lookup the use
						// statements
						String[] segments = namespaceName.split("\\\\", 2); //$NON-NLS-1$
						UsePartInfo info = usePartInfo.get(segments[0].toLowerCase());
						if (info != null) {
							String restSegs = ""; //$NON-NLS-1$
							if (segments.length > 1) {
								restSegs = segments[1];
							}
							namespaceName = PHPModelUtils.concatFullyQualifiedNames(info.getFullyQualifiedName(),
									restSegs);
						}
					}
				}
			}

			if (fullTypeReference != null && hasNamespace) {
				isGlobal = fullTypeReference.getNamespace().isGlobal();
				typeName = fullTypeReference.getFullyQualifiedName();
			} else {
				typeName = typeReference.getName();
			}

			if (fullTypeReference != null && isGlobal) {
				fullyQualifiedName = fullTypeReference.getFullyQualifiedName();
			} else if (hasNamespace) {
				fullyQualifiedName = PHPModelUtils.concatFullyQualifiedNames(namespaceName, typeReference.getName());
			} else {
				fullyQualifiedName = typeName;
			}
			if (!isUseStatement && !fullyQualifiedName.startsWith(NamespaceReference.NAMESPACE_DELIMITER)) {
				String key = getFirstSegmentOfTypeName(fullyQualifiedName).toLowerCase();
				if (usePartInfo.containsKey(key)) {
					fullyQualifiedName = usePartInfo.get(key).getFullyQualifiedName();
				} else if (currentNamespace != null && !currentNamespace.isGlobal()) {
					fullyQualifiedName = PHPModelUtils.concatFullyQualifiedNames(currentNamespace.getName(),
							fullyQualifiedName);
				}
			}
			if (fullyQualifiedName.length() > 0
					&& fullyQualifiedName.charAt(0) != NamespaceReference.NAMESPACE_SEPARATOR) {
				fullyQualifiedName = NamespaceReference.NAMESPACE_SEPARATOR + fullyQualifiedName;
			}
		}

		@Override
		public boolean isGlobal() {
			return isGlobal;
		}

		@Override
		public String getTypeName() {
			return typeName;
		}

		@Override
		public String getFullyQualifiedName() {
			return fullyQualifiedName;
		}

		public String getNamespaceName() {
			return namespaceName;
		}

		@Override
		public TypeReference getTypeReference() {
			return typeReference;
		}

		@Override
		public boolean isUseStatement() {
			return isUseStatement;
		}
	}

	@Override
	public boolean visit(ConstantDeclaration s) throws Exception {
		validateConstantExpression(s.getConstantValue(), false);
		return super.visit(s);
	}

	@Override
	public boolean visit(FormalParameter s) throws Exception {
		validateConstantExpression(s.getInitialization(), true);
		if (version.isGreaterThan(PHPVersion.PHP5_3) && s.getParameterName() != null
				&& PHPVariables.isVariable(s.getParameterName().getName(), version)) {
			reportProblem(s.getParameterName(), Messages.ReassignAutoGlobalVariable,
					PHPProblemIdentifier.ReassignAutoGlobalVariable, s.getParameterName().getName(),
					ProblemSeverities.Error);
		}

		return super.visit(s);
	}

	@Override
	public boolean endvisit(PHPFieldDeclaration s) throws Exception {
		validateConstantExpression(s.getVariableValue(), true);
		return super.endvisit(s);
	}

	private void validateConstantExpression(ASTNode astNode, boolean allowArray) {
		if (astNode == null || astNode instanceof Scalar || astNode instanceof ConstantReference) {
			return;
		}
		if (astNode instanceof UnaryOperation) {
			UnaryOperation op = (UnaryOperation) astNode;
			if (version.isLessThan(PHPVersion.PHP5_6) && op.getOperatorType() != UnaryOperation.OP_MINUS
					&& op.getOperatorType() != UnaryOperation.OP_PLUS) {
				reportProblem(astNode, Messages.InvalidConstantExpression,
						PHPProblemIdentifier.InvalidConstantExpression, ProblemSeverities.Error);
			} else {
				validateConstantExpression(op.getExpr(), allowArray);
			}
		} else if (astNode instanceof StaticConstantAccess) {
			StaticConstantAccess acc = (StaticConstantAccess) astNode;
			if (!(acc.getDispatcher() instanceof FullyQualifiedReference)) {
				reportProblem(acc.getDispatcher(), Messages.DynamicClassNotAllowed,
						PHPProblemIdentifier.InvalidConstantExpression, ProblemSeverities.Error);
			}
		} else if (version.isGreaterThan(PHPVersion.PHP5_5) && astNode instanceof InfixExpression) {
			InfixExpression expr = (InfixExpression) astNode;
			validateConstantExpression(expr.getLeft(), allowArray);
			validateConstantExpression(expr.getRight(), allowArray);
		} else if ((allowArray || version.isGreaterThan(PHPVersion.PHP5_5)) && astNode instanceof ArrayCreation) {
			((ArrayCreation) astNode).getElements().stream().forEach(n -> {
				validateConstantExpression(n.getKey(), allowArray);
				validateConstantExpression(n.getValue(), allowArray);
			});
		} else if (version.isGreaterThan(PHPVersion.PHP5_5) && astNode instanceof ReflectionArrayVariableReference) {
			ReflectionArrayVariableReference ref = (ReflectionArrayVariableReference) astNode;
			validateConstantExpression(ref.getExpression(), allowArray);
			validateConstantExpression(ref.getIndex(), allowArray);
		} else {
			reportProblem(astNode, Messages.InvalidConstantExpression, PHPProblemIdentifier.InvalidConstantExpression,
					ProblemSeverities.Error);
		}
	}

	@Override
	public boolean visit(BreakStatement s) throws Exception {
		validatePositiveConstantInteger(s.getExpr(), "break"); //$NON-NLS-1$
		return true;
	}

	@Override
	public boolean visit(ContinueStatement s) throws Exception {
		validatePositiveConstantInteger(s.getExpr(), "continue"); //$NON-NLS-1$
		return true;
	}

	private void validatePositiveConstantInteger(Expression expr, String operator) {
		if (expr == null || version.isLessThan(PHPVersion.PHP5_4)) {
			return;
		}
		if (!(expr instanceof Scalar)) {
			reportProblem(expr, NLS.bind(Messages.UsupportedNonConstantOperand, operator), PHPProblemIdentifier.SYNTAX,
					ProblemSeverities.Error);
			return;
		}
		Scalar sc = (Scalar) expr;
		if (sc.getScalarType() != Scalar.TYPE_INT || Integer.parseInt(sc.getValue()) < 1) {
			reportProblem(expr, NLS.bind(Messages.OperatorAcceptOnlyPositiveNumbers, operator),
					PHPProblemIdentifier.SYNTAX, ProblemSeverities.Error);
		}
	}

	@Override
	public boolean visitGeneral(ASTNode node) throws Exception {
		for (IValidatorExtension extension : extensions) {
			extension.visit(node);
		}
		return true;
	}

	@Override
	public void endvisitGeneral(ASTNode node) throws Exception {
		for (IValidatorExtension extension : extensions) {
			extension.endvisit(node);
		}
	}

	@Override
	public UsePartInfo getUsePartInfo(String name) {
		return usePartInfo.get(name);
	}

	@Override
	public boolean hasNamespace() {
		return this.hasNamespace;
	}

	@Override
	public NamespaceDeclaration getCurrentNamespace() {
		return currentNamespace;
	}

	@Override
	public PHPVersion getPHPVersion() {
		return version;
	}

}
