/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.ui.editor.contentassist;

import java.util.*;

import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.internal.core.ModelElement;
import org.eclipse.dltk.internal.core.SourceType;
import org.eclipse.dltk.ui.text.completion.ScriptCompletionProposal;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.php.core.compiler.PHPFlags;
import org.eclipse.php.internal.core.PHPCoreConstants;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.ast.nodes.*;
import org.eclipse.php.internal.core.codeassist.AliasType;
import org.eclipse.php.internal.core.codeassist.ProposalExtraInfo;
import org.eclipse.php.internal.core.compiler.ast.nodes.NamespaceReference;
import org.eclipse.php.internal.core.compiler.ast.nodes.UsePart;
import org.eclipse.php.internal.core.compiler.ast.parser.ASTUtils;
import org.eclipse.php.internal.core.documentModel.parser.PHPRegionContext;
import org.eclipse.php.internal.core.documentModel.parser.regions.IPhpScriptRegion;
import org.eclipse.php.internal.core.project.ProjectOptions;
import org.eclipse.php.internal.core.typeinference.FakeConstructor;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;
import org.eclipse.php.internal.core.util.text.PHPTextSequenceUtilities;
import org.eclipse.php.internal.core.util.text.TextSequence;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.php.internal.ui.editor.PHPStructuredTextViewer;
import org.eclipse.text.edits.TextEdit;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.wst.html.core.internal.Logger;
import org.eclipse.wst.sse.core.internal.provisional.text.*;

/**
 * This class injects USE statement if needed for the given completion proposal
 * 
 * @author michael
 */
public class UseStatementInjector {

	private ScriptCompletionProposal proposal;

	public UseStatementInjector(ScriptCompletionProposal proposal) {
		this.proposal = proposal;
	}

	private Collection<Identifier> createIdentifiers(AST ast,
			String namespaceName) {
		String[] split = namespaceName.split("\\\\"); //$NON-NLS-1$
		List<Identifier> identifiers = new ArrayList<Identifier>(split.length);
		for (String s : split) {
			identifiers.add(ast.newIdentifier(s));
		}
		return identifiers;
	}

	private NamespaceDeclaration getCurrentNamespace(Program program,
			ISourceModule sourceModule, int offset) {
		SourceType ns = (SourceType) PHPModelUtils.getPossibleCurrentNamespace(
				sourceModule, offset);
		if (ns == null) {
			if (program.statements() != null
					&& !program.statements().isEmpty()
					&& (program.statements().get(0) instanceof NamespaceDeclaration)) {
				NamespaceDeclaration result = (NamespaceDeclaration) program
						.statements().get(0);
				for (Statement statement : program.statements()) {
					if (statement.getStart() >= offset) {
						return result;
					}
					if (statement instanceof NamespaceDeclaration) {
						result = (NamespaceDeclaration) statement;
					}
				}
				return result;
			} else {
				return null;
			}
		}
		ASTNode node = null;
		try {
			node = program.getElementAt(ns.getSourceRange().getOffset());
		} catch (ModelException e) {
		}
		if (node == null) {
			return null;
		}
		do {
			switch (node.getType()) {
			case ASTNode.NAMESPACE:
				return (NamespaceDeclaration) node;
			}
			node = node.getParent();
		} while (node != null);

		return null;
	}

	private String getNamespaceName(NamespaceDeclaration namespaceDecl) {
		StringBuilder nameBuf = new StringBuilder();
		NamespaceName name = namespaceDecl.getName();
		if (name == null) {
			return "\\"; //$NON-NLS-1$
		}
		for (Identifier identifier : name.segments()) {
			if (nameBuf.length() > 0) {
				nameBuf.append('\\');
			}
			nameBuf.append(identifier.getName());
		}
		return nameBuf.toString();
	}

	private boolean needsAliasPrepend(IModelElement modelElement)
			throws ModelException {
		if (modelElement instanceof IMethod) {
			if (modelElement instanceof FakeConstructor) {
				return true;
			}
			IType declaringType = ((IMethod) modelElement).getDeclaringType();
			return declaringType == null
					|| PHPFlags.isNamespace(declaringType.getFlags());
		}
		if (modelElement instanceof IField) {
			IField field = (IField) modelElement;
			if (!PHPFlags.isConstant(field.getFlags())) {
				return false;
			}
			IType declaringType = ((IField) modelElement).getDeclaringType();
			return declaringType == null
					|| PHPFlags.isNamespace(declaringType.getFlags());
		}
		return true;
	}

	private String readNamespacePrefix(ISourceModule sourceModule,
			IDocument document, int offset, PHPVersion phpVersion) {

		if (offset > 0) {
			--offset;
		}

		IStructuredDocumentRegion sRegion = ((IStructuredDocument) document)
				.getRegionAtCharacterOffset(offset);
		if (sRegion != null) {
			ITextRegion tRegion = sRegion.getRegionAtCharacterOffset(offset);

			ITextRegionCollection container = sRegion;
			if (tRegion instanceof ITextRegionContainer) {
				container = (ITextRegionContainer) tRegion;
				tRegion = container.getRegionAtCharacterOffset(offset);
			}

			if (tRegion != null
					&& tRegion.getType() == PHPRegionContext.PHP_CONTENT) {
				IPhpScriptRegion phpScriptRegion = (IPhpScriptRegion) tRegion;
				try {
					tRegion = phpScriptRegion.getPhpToken(offset
							- container.getStartOffset()
							- phpScriptRegion.getStart());
				} catch (BadLocationException e) {
					return null;
				}

				// Determine element name:
				int elementStart = container.getStartOffset()
						+ phpScriptRegion.getStart() + tRegion.getStart();
				TextSequence statement = PHPTextSequenceUtilities.getStatement(
						elementStart + tRegion.getLength(), sRegion, true);
				int endPosition = PHPTextSequenceUtilities.readBackwardSpaces(
						statement, statement.length());
				int startPosition = PHPTextSequenceUtilities
						.readIdentifierStartIndex(phpVersion, statement,
								endPosition, true);
				String elementName = statement.subSequence(startPosition,
						endPosition).toString();
				if (elementName.length() > 0) {
					return PHPModelUtils.extractNamespaceName(elementName,
							sourceModule, offset);
				}
			}
		}
		return null;
	}

	/**
	 * Inserts USE statement into beginning of the document, or after the last
	 * USE statement.
	 * 
	 * @param document
	 * @param textViewer
	 * @param offset
	 * @return new offset
	 */
	public int inject(IDocument document, ITextViewer textViewer, int offset) {
		IModelElement modelElement = proposal.getModelElement();
		if (modelElement instanceof FakeConstructor) {
			FakeConstructor fc = (FakeConstructor) modelElement;
			if (fc.getParent() instanceof AliasType) {
				return offset;
			}

		} else if (modelElement instanceof AliasType) {
			return offset;
		}
		if (modelElement == null)
			return offset;
		if (proposal instanceof PHPCompletionProposal) {
			PHPCompletionProposal phpCompletionProposal = (PHPCompletionProposal) proposal;
			if (ProposalExtraInfo.isNoInsert(phpCompletionProposal
					.getExtraInfo())) {
				return offset;
			}
		}
		try {
			// quanlified namespace should return offset directly
			if (offset - proposal.getReplacementLength() > 0
					&& document.getChar(offset
							- proposal.getReplacementLength() - 1) == NamespaceReference.NAMESPACE_SEPARATOR) {
				return offset;
			}
			if (modelElement.getElementType() == IModelElement.TYPE
					&& PHPFlags.isNamespace(((IType) modelElement).getFlags())) {

				if (offset - proposal.getReplacementLength() > 0) {
					String prefix = document.get(
							offset - proposal.getReplacementLength(),
							proposal.getReplacementLength());
					String fullName = ((IType) modelElement).getElementName();
					// String fullName = PHPModelUtils
					// .getFullName((IType) modelElement);
					if (fullName.startsWith(prefix)
							&& prefix
									.indexOf(NamespaceReference.NAMESPACE_SEPARATOR) < 0) {
						// int
						ITextEditor textEditor = ((PHPStructuredTextViewer) textViewer)
								.getTextEditor();
						if (textEditor instanceof PHPStructuredEditor) {
							IModelElement editorElement = ((PHPStructuredEditor) textEditor)
									.getModelElement();
							if (editorElement != null) {
								ISourceModule sourceModule = ((ModelElement) editorElement)
										.getSourceModule();

								String namespaceName = fullName;
								if (fullName
										.indexOf(NamespaceReference.NAMESPACE_SEPARATOR) > 0) {
									namespaceName = fullName
											.substring(
													0,
													fullName.indexOf(NamespaceReference.NAMESPACE_SEPARATOR));
								}
								String usePartName = namespaceName;
								boolean useAlias = !Platform
										.getPreferencesService()
										.getBoolean(
												PHPCorePlugin.ID,
												PHPCoreConstants.CODEASSIST_INSERT_FULL_QUALIFIED_NAME_FOR_NAMESPACE,
												true, null);
								// if (!useAlias) {
								// usePartName = usePartName
								// + NamespaceReference.NAMESPACE_SEPARATOR
								// + modelElement.getElementName();
								// }
								ModuleDeclaration moduleDeclaration = SourceParserUtil
										.getModuleDeclaration(sourceModule);
								TextEdit edits = null;

								ASTParser parser = ASTParser
										.newParser(sourceModule);
								parser.setSource(document.get().toCharArray());
								Program program = parser.createAST(null);

								// don't insert USE statement for current
								// namespace
								if (isSameNamespace(namespaceName, program,
										sourceModule, offset)) {
									return offset;
								}

								// find existing use statement:
								UsePart usePart = ASTUtils
										.findUseStatementByNamespace(
												moduleDeclaration, usePartName,
												offset);

								List<String> importedTypeName = getImportedTypeName(
										moduleDeclaration, offset);
								String typeName = namespaceName;
								// if (!useAlias) {
								// typeName = modelElement.getElementName()
								// .toLowerCase();
								// } else {
								// if (usePart != null
								// && usePart.getAlias() != null
								// && usePart.getAlias().getName() != null) {
								// typeName = usePart.getAlias().getName();
								// } else {
								// typeName = PHPModelUtils
								// .extractElementName(namespaceName)
								// .toLowerCase();
								// }
								// }

								// if the class/namesapce has not been imported
								// add use statement
								if (!importedTypeName.contains(typeName)) {

									program.recordModifications();
									AST ast = program.getAST();
									NamespaceName newNamespaceName = ast
											.newNamespaceName(
													createIdentifiers(ast,
															usePartName),
													false, false);
									UseStatementPart newUseStatementPart = ast
											.newUseStatementPart(
													newNamespaceName, null);
									org.eclipse.php.internal.core.ast.nodes.UseStatement newUseStatement = ast
											.newUseStatement(Arrays
													.asList(new UseStatementPart[] { newUseStatementPart }));

									NamespaceDeclaration currentNamespace = getCurrentNamespace(
											program, sourceModule, offset - 1);
									if (currentNamespace != null) {
										// insert in the beginning of the
										// current
										// namespace:
										int index = getLastUsestatementIndex(
												currentNamespace.getBody()
														.statements(), offset);
										if (index > 0) {// workaround for bug
														// 393253
											try {
												int beginLine = document
														.getLineOfOffset(currentNamespace
																.getBody()
																.statements()
																.get(index - 1)
																.getEnd()) + 1;
												newUseStatement
														.setSourceRange(
																document.getLineOffset(beginLine),
																0);
											} catch (Exception e) {
											}
										}
										currentNamespace.getBody().statements()
												.add(index, newUseStatement);
									} else {
										// insert in the beginning of the
										// document:
										int index = getLastUsestatementIndex(
												program.statements(), offset);
										if (index > 0) {// workaround for bug
														// 393253
											try {
												int beginLine = document
														.getLineOfOffset(program
																.statements()
																.get(index - 1)
																.getEnd()) + 1;
												newUseStatement
														.setSourceRange(
																document.getLineOffset(beginLine),
																0);
											} catch (Exception e) {
											}
										}
										program.statements().add(index,
												newUseStatement);
									}
									Map options = new HashMap(
											PHPCorePlugin.getOptions());
									// TODO project may be null
									IScopeContext[] contents = new IScopeContext[] {
											new ProjectScope(modelElement
													.getScriptProject()
													.getProject()),
											InstanceScope.INSTANCE,
											DefaultScope.INSTANCE };
									for (int i = 0; i < contents.length; i++) {
										IScopeContext scopeContext = contents[i];
										IEclipsePreferences node = scopeContext
												.getNode(PHPCorePlugin.ID);
										if (node != null) {
											if (!options
													.containsKey(PHPCoreConstants.FORMATTER_USE_TABS)) {
												String useTabs = node
														.get(PHPCoreConstants.FORMATTER_USE_TABS,
																null);
												if (useTabs != null) {
													options.put(
															PHPCoreConstants.FORMATTER_USE_TABS,
															useTabs);
												}
											}
											if (!options
													.containsKey(PHPCoreConstants.FORMATTER_INDENTATION_SIZE)) {
												String size = node
														.get(PHPCoreConstants.FORMATTER_INDENTATION_SIZE,
																null);
												if (size != null) {
													options.put(
															PHPCoreConstants.FORMATTER_INDENTATION_SIZE,
															size);
												}
											}
										}
									}
									ast.setInsertUseStatement(true);
									edits = program.rewrite(document, options);
									edits.apply(document);
									ast.setInsertUseStatement(false);
								} else if (!useAlias
										&& (usePart == null || !usePartName
												.equals(usePart
														.getNamespace()
														.getFullyQualifiedName()))) {
									// if the type name already exists, use
									// fully
									// qualified name to replace
									proposal.setReplacementString(NamespaceReference.NAMESPACE_SEPARATOR
											+ fullName);
								}

								// if (useAlias &&
								// needsAliasPrepend(modelElement)) {
								//
								// // update replacement string: add namespace
								// // alias prefix
								// String namespacePrefix = typeName
								// + NamespaceReference.NAMESPACE_SEPARATOR;
								// String replacementString = proposal
								// .getReplacementString();
								//
								// String existingNamespacePrefix =
								// readNamespacePrefix(
								// sourceModule, document, offset,
								// ProjectOptions
								// .getPhpVersion(editorElement));
								//
								// // Add alias to the replacement string:
								// if (!usePartName
								// .equals(existingNamespacePrefix)) {
								// replacementString = namespacePrefix
								// + replacementString;
								// }
								// proposal.setReplacementString(replacementString);
								// }

								if (edits != null) {
									int replacementOffset = proposal
											.getReplacementOffset()
											+ edits.getLength();
									offset += edits.getLength();
									proposal.setReplacementOffset(replacementOffset);
								}
							}
						}

					}
					return offset;
				}
				return offset;
			}
			// class members should return offset directly
			if (modelElement.getElementType() != IModelElement.TYPE
					&& !(modelElement instanceof FakeConstructor)) {
				IModelElement type = modelElement
						.getAncestor(IModelElement.TYPE);
				if (type != null
						&& !PHPFlags.isNamespace(((IType) type).getFlags())) {
					return offset;
				}
			}
		} catch (Exception e) {
			Logger.logException(e);
		}

		// add use statement if needed:
		IType namespace = PHPModelUtils.getCurrentNamespace(modelElement);
		if (namespace != null) {

			// find source module of the current editor:
			if (textViewer instanceof PHPStructuredTextViewer) {
				ITextEditor textEditor = ((PHPStructuredTextViewer) textViewer)
						.getTextEditor();
				if (textEditor instanceof PHPStructuredEditor) {
					IModelElement editorElement = ((PHPStructuredEditor) textEditor)
							.getModelElement();
					if (editorElement != null) {
						ISourceModule sourceModule = ((ModelElement) editorElement)
								.getSourceModule();

						try {
							String namespaceName = namespace.getElementName();
							String usePartName = namespaceName;
							boolean useAlias = !Platform
									.getPreferencesService()
									.getBoolean(
											PHPCorePlugin.ID,
											PHPCoreConstants.CODEASSIST_INSERT_FULL_QUALIFIED_NAME_FOR_NAMESPACE,
											true, null);
							if (!useAlias) {
								usePartName = usePartName
										+ NamespaceReference.NAMESPACE_SEPARATOR
										+ modelElement.getElementName();
							}
							ModuleDeclaration moduleDeclaration = SourceParserUtil
									.getModuleDeclaration(sourceModule);
							TextEdit edits = null;

							ASTParser parser = ASTParser
									.newParser(sourceModule);
							parser.setSource(document.get().toCharArray());
							Program program = parser.createAST(null);

							// don't insert USE statement for current namespace
							if (isSameNamespace(namespaceName, program,
									sourceModule, offset)) {
								return offset;
							}

							// find existing use statement:
							UsePart usePart = ASTUtils
									.findUseStatementByNamespace(
											moduleDeclaration, usePartName,
											offset);

							List<String> importedTypeName = getImportedTypeName(
									moduleDeclaration, offset);
							String typeName = ""; //$NON-NLS-1$
							if (!useAlias) {
								typeName = modelElement.getElementName()
										.toLowerCase();
							} else {
								if (usePart != null
										&& usePart.getAlias() != null
										&& usePart.getAlias().getName() != null) {
									typeName = usePart.getAlias().getName();
								} else {
									typeName = PHPModelUtils
											.extractElementName(namespaceName)
											.toLowerCase();
								}
							}

							// if the class/namesapce has not been imported
							// add use statement
							if (!importedTypeName.contains(typeName)) {

								program.recordModifications();
								AST ast = program.getAST();
								NamespaceName newNamespaceName = ast
										.newNamespaceName(
												createIdentifiers(ast,
														usePartName), false,
												false);
								UseStatementPart newUseStatementPart = ast
										.newUseStatementPart(newNamespaceName,
												null);
								org.eclipse.php.internal.core.ast.nodes.UseStatement newUseStatement = ast
										.newUseStatement(Arrays
												.asList(new UseStatementPart[] { newUseStatementPart }));

								NamespaceDeclaration currentNamespace = getCurrentNamespace(
										program, sourceModule, offset - 1);
								if (currentNamespace != null) {
									// insert in the beginning of the
									// current
									// namespace:
									int index = getLastUsestatementIndex(
											currentNamespace.getBody()
													.statements(), offset);
									if (index > 0) {// workaround for bug 393253
										try {
											int beginLine = document
													.getLineOfOffset(currentNamespace
															.getBody()
															.statements()
															.get(index - 1)
															.getEnd()) + 1;
											newUseStatement
													.setSourceRange(
															document.getLineOffset(beginLine),
															0);
										} catch (Exception e) {
										}
									}
									currentNamespace.getBody().statements()
											.add(index, newUseStatement);
								} else {
									// insert in the beginning of the
									// document:
									int index = getLastUsestatementIndex(
											program.statements(), offset);
									if (index > 0) {// workaround for bug 393253
										try {
											int beginLine = document
													.getLineOfOffset(program
															.statements()
															.get(index - 1)
															.getEnd()) + 1;
											newUseStatement
													.setSourceRange(
															document.getLineOffset(beginLine),
															0);
										} catch (Exception e) {
										}
									}
									program.statements().add(index,
											newUseStatement);
								}
								Map options = new HashMap(
										PHPCorePlugin.getOptions());
								// TODO project may be null
								IScopeContext[] contents = new IScopeContext[] {
										new ProjectScope(modelElement
												.getScriptProject()
												.getProject()),
										InstanceScope.INSTANCE,
										DefaultScope.INSTANCE };
								for (int i = 0; i < contents.length; i++) {
									IScopeContext scopeContext = contents[i];
									IEclipsePreferences node = scopeContext
											.getNode(PHPCorePlugin.ID);
									if (node != null) {
										if (!options
												.containsKey(PHPCoreConstants.FORMATTER_USE_TABS)) {
											String useTabs = node
													.get(PHPCoreConstants.FORMATTER_USE_TABS,
															null);
											if (useTabs != null) {
												options.put(
														PHPCoreConstants.FORMATTER_USE_TABS,
														useTabs);
											}
										}
										if (!options
												.containsKey(PHPCoreConstants.FORMATTER_INDENTATION_SIZE)) {
											String size = node
													.get(PHPCoreConstants.FORMATTER_INDENTATION_SIZE,
															null);
											if (size != null) {
												options.put(
														PHPCoreConstants.FORMATTER_INDENTATION_SIZE,
														size);
											}
										}
									}
								}
								ast.setInsertUseStatement(true);
								edits = program.rewrite(document, options);
								edits.apply(document);
								ast.setInsertUseStatement(false);
							} else if (!useAlias
									&& (usePart == null || !usePartName
											.equals(usePart.getNamespace()
													.getFullyQualifiedName()))) {
								// if the type name already exists, use fully
								// qualified name to replace
								proposal.setReplacementString(NamespaceReference.NAMESPACE_SEPARATOR
										+ namespaceName
										+ NamespaceReference.NAMESPACE_SEPARATOR
										+ proposal.getReplacementString());
							}

							if (useAlias && needsAliasPrepend(modelElement)) {

								// update replacement string: add namespace
								// alias prefix
								String namespacePrefix = typeName
										+ NamespaceReference.NAMESPACE_SEPARATOR;
								String replacementString = proposal
										.getReplacementString();

								String existingNamespacePrefix = readNamespacePrefix(
										sourceModule, document, offset,
										ProjectOptions
												.getPhpVersion(editorElement));

								// Add alias to the replacement string:
								if (!usePartName
										.equals(existingNamespacePrefix)) {
									replacementString = namespacePrefix
											+ replacementString;
								}
								proposal.setReplacementString(replacementString);
							}

							if (edits != null) {
								int replacementOffset = proposal
										.getReplacementOffset()
										+ edits.getLength();
								offset += edits.getLength();
								proposal.setReplacementOffset(replacementOffset);
							}

						} catch (Exception e) {
							Logger.logException(e);
						}
					}
				}
			}
		}

		return offset;
	}

	private int getLastUsestatementIndex(List<Statement> statements, int offset) {
		int result = 0;
		for (int i = 0; i < statements.size(); i++) {
			Statement statement = statements.get(i);
			if (statement.getEnd() <= offset
					&& statement instanceof UseStatement) {
				result = i + 1;
			}
		}
		return result;
	}

	/**
	 * Get all the class / namespace names which have been imported by use
	 * statement
	 * 
	 * @param moduleDeclaration
	 * @param offset
	 * @return
	 */
	private List<String> getImportedTypeName(
			ModuleDeclaration moduleDeclaration, final int offset) {
		org.eclipse.php.internal.core.compiler.ast.nodes.UseStatement[] useStatements = ASTUtils
				.getUseStatements(moduleDeclaration, offset);
		List<String> importedClass = new ArrayList<String>();
		for (org.eclipse.php.internal.core.compiler.ast.nodes.UseStatement statement : useStatements) {
			for (UsePart usePart : statement.getParts()) {
				String name;
				if (usePart.getAlias() != null) {
					name = usePart.getAlias().getName();
				} else {
					// In case there's no alias - the alias is the
					// last segment of the namespace name:
					name = usePart.getNamespace().getName();
				}
				importedClass.add(name.toLowerCase());
			}
		}
		return importedClass;
	}

	private boolean isSameNamespace(String namespaceName, Program program,
			ISourceModule sourceModule, int offset) {
		NamespaceDeclaration currentNamespace = getCurrentNamespace(program,
				sourceModule, offset - 1);
		if (currentNamespace == null) {
			return false;
		}
		if (namespaceName.equals(getNamespaceName(currentNamespace))) {
			return true;
		}
		return false;
	}
}
