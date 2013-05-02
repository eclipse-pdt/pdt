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
package org.eclipse.php.internal.core.codeassist;

import java.util.*;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.ast.declarations.Declaration;
import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.declarations.TypeDeclaration;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ast.references.SimpleReference;
import org.eclipse.dltk.ast.references.TypeReference;
import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.dltk.codeassist.ScriptSelectionEngine;
import org.eclipse.dltk.compiler.env.IModuleSource;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.core.index2.search.ISearchEngine.MatchRule;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.core.search.SearchEngine;
import org.eclipse.dltk.evaluation.types.AmbiguousType;
import org.eclipse.dltk.evaluation.types.MultiTypeType;
import org.eclipse.dltk.internal.core.AbstractSourceModule;
import org.eclipse.dltk.internal.core.SourceRefElement;
import org.eclipse.dltk.ti.IContext;
import org.eclipse.dltk.ti.ISourceModuleContext;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.core.compiler.PHPFlags;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.compiler.ast.nodes.*;
import org.eclipse.php.internal.core.compiler.ast.parser.ASTUtils;
import org.eclipse.php.internal.core.documentModel.parser.PHPRegionContext;
import org.eclipse.php.internal.core.documentModel.parser.regions.IPhpScriptRegion;
import org.eclipse.php.internal.core.documentModel.parser.regions.PHPRegionTypes;
import org.eclipse.php.internal.core.documentModel.partitioner.PHPPartitionTypes;
import org.eclipse.php.internal.core.model.PhpModelAccess;
import org.eclipse.php.internal.core.project.ProjectOptions;
import org.eclipse.php.internal.core.typeinference.PHPClassType;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;
import org.eclipse.php.internal.core.typeinference.PHPTypeInferenceUtils;
import org.eclipse.php.internal.core.typeinference.evaluators.PHPTraitType;
import org.eclipse.php.internal.core.util.text.PHPTextSequenceUtilities;
import org.eclipse.php.internal.core.util.text.TextSequence;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;
import org.eclipse.wst.sse.core.internal.provisional.text.*;

public class PHPSelectionEngine extends ScriptSelectionEngine {

	private static final String OPEN_BRACE = "("; //$NON-NLS-1$
	private static final String PROTECTED = "protected"; //$NON-NLS-1$
	private static final String PUBLIC = "public"; //$NON-NLS-1$
	private static final String PAAMAYIM_NEKUDOTAIM = "::"; //$NON-NLS-1$
	private static final String NS_SEPARATOR = "\\"; //$NON-NLS-1$
	private static final String CONST = "const"; //$NON-NLS-1$
	private static final String THIS = "this"; //$NON-NLS-1$
	private static final String STATIC = "static"; //$NON-NLS-1$
	private static final String PRIVATE = "private"; //$NON-NLS-1$
	private static final String VAR = "var"; //$NON-NLS-1$
	private static final String IMPLEMENTS = "implements"; //$NON-NLS-1$
	private static final String EXTENDS = "extends"; //$NON-NLS-1$
	private static final String NEW = "new"; //$NON-NLS-1$
	private static final String INTERFACE = "interface"; //$NON-NLS-1$
	private static final String CLASS = "class"; //$NON-NLS-1$
	private static final String FUNCTION = "function"; //$NON-NLS-1$
	private static final IModelElement[] EMPTY = {};
	private PHPVersion phpVersion;

	public IModelElement[] select(IModuleSource sourceUnit, int offset, int end) {

		if (!PHPCorePlugin.toolkitInitialized) {
			return EMPTY;
		}

		if (end < offset) {
			end = offset + 1;
		}

		ISourceModule sourceModule = (ISourceModule) sourceUnit
				.getModelElement();
		phpVersion = ProjectOptions.getPhpVersion(sourceModule
				.getScriptProject().getProject());

		// First, try to resolve using AST (if we have parsed it well):
		try {
			IModelElement[] elements = internalASTResolve(sourceModule, offset,
					end);
			if (elements != null) {
				Collection<IModelElement> filtered = PHPModelUtils
						.filterElements(sourceModule, Arrays.asList(elements),
								null, null);
				return (IModelElement[]) filtered
						.toArray(new IModelElement[filtered.size()]);
			}
		} catch (Exception e) {
			PHPCorePlugin.log(e);
		}

		// Use the old way by playing with document & buffer:
		IStructuredDocument document = null;
		IStructuredModel structuredModel = null;
		try {
			IFile file = (IFile) sourceUnit.getModelElement().getResource();
			if (file != null) {
				if (file.exists()) {
					structuredModel = StructuredModelManager.getModelManager()
							.getExistingModelForRead(file);
					if (structuredModel != null) {
						document = structuredModel.getStructuredDocument();
					} else {
						document = StructuredModelManager.getModelManager()
								.createStructuredDocumentFor(file);
					}
				} else {
					document = StructuredModelManager.getModelManager()
							.createNewStructuredDocumentFor(file);
					document.set(sourceUnit.getSourceContents());
				}
			}
		} catch (Exception e) {
			PHPCorePlugin.log(e);
		} finally {
			if (structuredModel != null) {
				structuredModel.releaseFromRead();
			}
		}

		if (document != null) {
			IModelElement[] elements = internalResolve(document, sourceModule,
					offset, end);
			if (elements != null) {
				Collection<IModelElement> filtered = PHPModelUtils
						.filterElements(sourceModule, Arrays.asList(elements),
								null, null);

				if (filtered.size() == 0) {
					return EMPTY;
				}
				IStructuredDocumentRegion sRegion = document
						.getRegionAtCharacterOffset(offset);
				if (sRegion != null) {
					ITextRegion tRegion = sRegion
							.getRegionAtCharacterOffset(offset);

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
						}
						if (tRegion != null) {
							// Determine element name:
							int elementStart = container.getStartOffset()
									+ phpScriptRegion.getStart()
									+ tRegion.getStart();
							TextSequence statement = PHPTextSequenceUtilities
									.getStatement(
											elementStart + tRegion.getLength(),
											sRegion, true);
							if (statement != null) {
								int endPosition = PHPTextSequenceUtilities
										.readBackwardSpaces(statement,
												statement.length());
								int startPosition = PHPTextSequenceUtilities
										.readIdentifierStartIndex(phpVersion,
												statement, endPosition, true);
								String elementName = statement.subSequence(
										startPosition, endPosition).toString();
								List<IModelElement> result = new LinkedList<IModelElement>();
								for (Iterator<IModelElement> iterator = filtered
										.iterator(); iterator.hasNext();) {
									IModelElement modelElement = (IModelElement) iterator
											.next();
									if (modelElement instanceof IField) {
										String fieldName = elementName;
										if (!fieldName.startsWith("$")) { //$NON-NLS-1$
											fieldName = "$" + fieldName; //$NON-NLS-1$
										}
										if (modelElement.getElementName()
												.equals(fieldName)
												|| modelElement
														.getElementName()
														.equals(elementName)) {
											result.add(modelElement);
										}
									} else if (modelElement.getElementName()
											.equals(elementName)) {
										result.add(modelElement);
									}
								}
								return (IModelElement[]) result
										.toArray(new IModelElement[result
												.size()]);
							}
						}
					}
				}
				return (IModelElement[]) filtered
						.toArray(new IModelElement[filtered.size()]);
			}
		}

		return EMPTY;
	}

	private IModelElement[] internalASTResolve(ISourceModule sourceModule,
			int offset, int end) throws ModelException {
		String source;

		try {
			source = sourceModule.getSource();
			offset = PHPTextSequenceUtilities.readIdentifierStartIndex(source,
					offset, true);
		} catch (IndexOutOfBoundsException ex) {
			// ISourceModule.getSource() may throw
			// ArrayIndexOutOfBoundsException and
			// PHPTextSequenceUtilities.readIdentifierStartIndex() may throw
			// StringIndexOutOfBoundExceptio in case when main thread
			// modifies editor content in parallel to this action in background
			// thread
			return null;
		}

		end = PHPTextSequenceUtilities
				.readIdentifierEndIndex(source, end, true);

		int methodEnd = PHPTextSequenceUtilities.getMethodEndIndex(source, end);
		if (methodEnd != -1) {
			end = methodEnd;
		}

		ModuleDeclaration parsedUnit = SourceParserUtil.getModuleDeclaration(
				sourceModule, null);

		// boolean inDocBlock=false;
		if (parsedUnit instanceof PHPModuleDeclaration) {
			PHPModuleDeclaration phpModuleDeclaration = (PHPModuleDeclaration) parsedUnit;
			List<PHPDocBlock> phpBlocks = phpModuleDeclaration
					.getPhpDocBlocks();
			for (PHPDocBlock phpDocBlock : phpBlocks) {
				int realStart = phpDocBlock.sourceStart();
				int realEnd = phpDocBlock.sourceEnd();
				if (realStart <= offset && realEnd >= end) {
					// inDocBlock=true;
					PHPDocTag[] tags = phpDocBlock.getTags();
					if (tags != null) {
						for (PHPDocTag phpDocTag : tags) {
							if (phpDocTag.sourceStart() <= offset
									&& phpDocTag.sourceEnd() >= end) {
								SimpleReference[] references = phpDocTag
										.getReferences();
								if (references != null) {
									for (SimpleReference simpleReference : references) {
										if (simpleReference instanceof TypeReference) {
											TypeReference typeReference = (TypeReference) simpleReference;
											if (typeReference.sourceStart() <= offset
													&& typeReference
															.sourceEnd() >= end) {
												IType[] types = filterNS(PHPModelUtils
														.getTypes(typeReference
																.getName(),
																sourceModule,
																offset, null));
												return types;

											}
										}
									}
								}
							}
						}
					}
					return null;
				}
			}
		}
		ASTNode node = ASTUtils.findMinimalNode(parsedUnit, offset, end);
		if (node != null) {

			IContext context = ASTUtils.findContext(sourceModule, parsedUnit,
					node);
			if (context != null) {

				// Function call:
				if (node instanceof PHPCallExpression) {
					PHPCallExpression callExpression = (PHPCallExpression) node;
					if (callExpression.getReceiver() != null) {
						IEvaluatedType receiverType = PHPTypeInferenceUtils
								.resolveExpression(sourceModule, parsedUnit,
										context, callExpression.getReceiver());
						// (new class1())->avc2()[1][1]->avc1()
						if ((receiverType instanceof MultiTypeType)
								&& callExpression.getReceiver() instanceof PHPCallExpression) {
							PHPCallExpression receiverCallExpression = (PHPCallExpression) callExpression
									.getReceiver();
							if (((PHPCallArgumentsList) receiverCallExpression
									.getArgs()).getArrayDereferenceList() != null
									&& !((PHPCallArgumentsList) receiverCallExpression
											.getArgs())
											.getArrayDereferenceList()
											.getChilds().isEmpty()) {
								receiverType = new AmbiguousType(
										((MultiTypeType) receiverType)
												.getTypes().toArray(
														new IEvaluatedType[0]));
							}
						}
						if (receiverType != null) {
							IModelElement[] elements = null;
							if ((receiverType instanceof PHPClassType)
									&& ((PHPClassType) receiverType).isGlobal()) {

								IDLTKSearchScope scope = SearchEngine
										.createSearchScope(sourceModule
												.getScriptProject());
								elements = PhpModelAccess.getDefault()
										.findTypes(receiverType.getTypeName(),
												MatchRule.EXACT, 0, 0, scope,
												null);
								LinkedList<IModelElement> result = new LinkedList<IModelElement>();
								for (IModelElement element : elements) {
									IModelElement parent = element.getParent();
									while (parent.getParent() instanceof IType) {
										parent = parent.getParent();
									}
									if ((parent instanceof IType)
											&& PHPFlags
													.isNamespace(((IType) parent)
															.getFlags())) {
										// Do nothing
									} else {
										result.add(element);
									}
								}
								elements = result.toArray(new IType[result
										.size()]);
							} else {
								elements = PHPTypeInferenceUtils
										.getModelElements(receiverType,
												(ISourceModuleContext) context,
												offset);
							}
							List<IModelElement> methods = new LinkedList<IModelElement>();
							if (elements != null) {
								for (IModelElement element : elements) {
									if (element instanceof IType) {
										IType type = (IType) element;
										try {
											IMethod[] method = PHPModelUtils
													.getFirstTypeHierarchyMethod(
															type, null,
															callExpression
																	.getName(),
															true, null);
											methods.addAll(Arrays
													.asList(method));
										} catch (CoreException e) {
											PHPCorePlugin.log(e);
										}
									}
								}
							}
							return methods.toArray(new IModelElement[methods
									.size()]);
						}
					} else {
						SimpleReference callName = callExpression.getCallName();
						String methodName = callName instanceof FullyQualifiedReference ? ((FullyQualifiedReference) callName)
								.getFullyQualifiedName() : callName.getName();
						IMethod[] functions = PHPModelUtils.getFunctions(
								methodName, sourceModule, offset, null, null);
						return functions == null ? EMPTY : functions;
					}
				}
				// Static field or constant access:
				else if (node instanceof StaticDispatch) {
					StaticDispatch dispatch = (StaticDispatch) node;
					String fieldName = null;
					if (dispatch instanceof StaticConstantAccess) {
						fieldName = ((StaticConstantAccess) dispatch)
								.getConstant().getName();
					} else if (dispatch instanceof StaticFieldAccess) {
						ASTNode field = ((StaticFieldAccess) dispatch)
								.getField();
						if (field instanceof VariableReference) {
							fieldName = ((VariableReference) field).getName();
						}
					}
					if (fieldName != null && dispatch.getDispatcher() != null) {
						IEvaluatedType dispatcherType = PHPTypeInferenceUtils
								.resolveExpression(sourceModule, parsedUnit,
										context, dispatch.getDispatcher());
						if (dispatcherType != null) {
							IModelElement[] elements = PHPTypeInferenceUtils
									.getModelElements(dispatcherType,
											(ISourceModuleContext) context,
											offset);
							List<IModelElement> fields = new LinkedList<IModelElement>();
							if (elements != null) {
								for (IModelElement element : elements) {
									if (element instanceof IType) {
										IType type = (IType) element;
										try {
											fields.addAll(Arrays.asList(PHPModelUtils
													.getTypeHierarchyField(
															type,
															fieldName,
															true,
															new NullProgressMonitor())));
										} catch (Exception e) {
											PHPCorePlugin.log(e);
										}
									}
								}
							}
							return fields.toArray(new IModelElement[fields
									.size()]);
						}
					}
				}
				// Dynamic field access:
				else if (node instanceof FieldAccess) {
					FieldAccess fieldAccess = (FieldAccess) node;
					ASTNode field = fieldAccess.getField();
					String fieldName = null;
					if (field instanceof SimpleReference) {
						fieldName = ((SimpleReference) field).getName();
					}
					if (fieldName != null
							&& fieldAccess.getDispatcher() != null) {
						IEvaluatedType dispatcherType = PHPTypeInferenceUtils
								.resolveExpression(sourceModule, parsedUnit,
										context, fieldAccess.getDispatcher());
						if (dispatcherType != null) {
							IModelElement[] elements = PHPTypeInferenceUtils
									.getModelElements(dispatcherType,
											(ISourceModuleContext) context,
											offset);
							List<IModelElement> fields = new LinkedList<IModelElement>();
							if (elements != null) {
								for (IModelElement element : elements) {
									if (element instanceof IType) {
										IType type = (IType) element;
										try {
											fields.addAll(Arrays
													.asList(PHPModelUtils
															.getTypeField(type,
																	fieldName,
																	true)));
										} catch (ModelException e) {
											PHPCorePlugin.log(e);
										}
									}
								}
							}
							return fields.toArray(new IModelElement[fields
									.size()]);
						}
					}
				} else if (node instanceof NamespaceReference) {
					String name = ((NamespaceReference) node).getName();
					IType[] namespace = PHPModelUtils.getNamespaceOf(name
							+ NamespaceReference.NAMESPACE_SEPARATOR,
							sourceModule, offset, null, null);
					return namespace == null ? EMPTY : namespace;
				}
				// Class/Interface reference:
				else if (node instanceof TypeReference) {
					IEvaluatedType evaluatedType = PHPTypeInferenceUtils
							.resolveExpression(sourceModule, node);
					if (evaluatedType == null) {
						return EMPTY;
					}
					String name = evaluatedType.getTypeName();
					IType[] types = PHPModelUtils.getTypes(name, sourceModule,
							offset, null, null,
							!(evaluatedType instanceof PHPTraitType));
					if (types == null || types.length == 0) {
						// This can be a constant or namespace in PHP 5.3:
						if (name.charAt(0) == NamespaceReference.NAMESPACE_SEPARATOR) {
							name = name.substring(1);
						}
						IDLTKSearchScope scope = SearchEngine
								.createSearchScope(sourceModule
										.getScriptProject());
						if (evaluatedType instanceof PHPTraitType) {
							types = PhpModelAccess.getDefault().findTraits(
									null, name, MatchRule.EXACT,
									Modifiers.AccNameSpace, 0, scope, null);
						} else {
							types = PhpModelAccess.getDefault().findTypes(null,
									name, MatchRule.EXACT,
									Modifiers.AccNameSpace, 0, scope, null);
						}

						if (types == null || types.length == 0) {
							name = NamespaceReference.NAMESPACE_SEPARATOR
									+ name;
							return PHPModelUtils.getFields(name, sourceModule,
									offset, null, null);
						}
					}
					return types;
				}
				// 'new' statement
				else if (node instanceof ClassInstanceCreation) {
					ClassInstanceCreation newNode = (ClassInstanceCreation) node;

					Expression className = newNode.getClassName();

					if ((className instanceof SimpleReference || className instanceof FullyQualifiedReference)) {
						IEvaluatedType evaluatedType = PHPTypeInferenceUtils
								.resolveExpression(sourceModule, node);
						return getConstructorsIfAny(extractClasses(PHPModelUtils
								.getTypes(evaluatedType.getTypeName(),
										sourceModule, offset, null, null)));
					} else if ((className instanceof StaticFieldAccess)) {
						StaticFieldAccess staticFieldAccess = (StaticFieldAccess) className;
						if ((offset >= staticFieldAccess.getDispatcher()
								.sourceStart())
								&& (offset <= staticFieldAccess.getDispatcher()
										.sourceEnd())) {
							className = staticFieldAccess.getDispatcher();
							IEvaluatedType evaluatedType = PHPTypeInferenceUtils
									.resolveExpression(sourceModule, className);
							return extractClasses(PHPModelUtils.getTypes(
									evaluatedType.getTypeName(), sourceModule,
									offset, null, null));
						} else if ((offset >= staticFieldAccess.getField()
								.sourceStart())
								&& (offset <= staticFieldAccess.getField()
										.sourceEnd())) {
							className = staticFieldAccess.getField();

							String fieldName = null;
							ASTNode field = staticFieldAccess.getField();
							if (field instanceof VariableReference) {
								fieldName = ((VariableReference) field)
										.getName();
							}
							if (fieldName != null
									&& staticFieldAccess.getDispatcher() != null) {
								IEvaluatedType dispatcherType = PHPTypeInferenceUtils
										.resolveExpression(sourceModule,
												parsedUnit, context,
												staticFieldAccess
														.getDispatcher());
								if (dispatcherType != null) {
									IModelElement[] elements = PHPTypeInferenceUtils
											.getModelElements(
													dispatcherType,
													(ISourceModuleContext) context,
													offset);
									List<IModelElement> fields = new LinkedList<IModelElement>();
									if (elements != null) {
										for (IModelElement element : elements) {
											if (element instanceof IType) {
												IType type = (IType) element;
												try {
													fields.addAll(Arrays
															.asList(PHPModelUtils
																	.getTypeField(
																			type,
																			fieldName,
																			true)));
												} catch (ModelException e) {
													PHPCorePlugin.log(e);
												}
											}
										}
									}
									return fields
											.toArray(new IModelElement[fields
													.size()]);
								}
							}
						}

					}
				}
				// Class name in declaration
				else if ((node instanceof TypeDeclaration || node instanceof MethodDeclaration)
						&& ((Declaration) node).getNameStart() <= offset
						&& ((Declaration) node).getNameEnd() >= offset) {

					IModelElement element = sourceModule.getElementAt(node
							.sourceStart());
					if (element != null) {
						return new IModelElement[] { element };
					}
				} else if (node instanceof SimpleReference) {

					SimpleReference reference = (SimpleReference) node;

					node = ASTUtils.findMinimalNode(parsedUnit, offset,
							node.end() + 1);
					if (node instanceof TraitAliasStatement) {
						node = ASTUtils.findMinimalNode(parsedUnit, offset,
								node.end() + 1);
						if (node instanceof TraitUseStatement) {
							TraitUseStatement statement = (TraitUseStatement) node;
							List<IModelElement> methods = new LinkedList<IModelElement>();
							for (TypeReference typeReference : statement
									.getTraitList()) {
								IType[] types = PHPModelUtils.getTypes(
										typeReference.getName(), sourceModule,
										offset, null, null, false);
								for (IType t : types) {
									IModelElement[] children = t.getChildren();
									for (IModelElement modelElement : children) {
										String name = modelElement
												.getElementName();
										if (name.startsWith("$")) { //$NON-NLS-1$
											name = name.substring(1);
										}
										if (name.equals(reference.getName())) {
											methods.add(modelElement);
										}
									}
								}
							}

							return methods.toArray(new IMethod[methods.size()]);
						}

					}
				}
				/*
				 * else if (node instanceof Scalar) { Scalar scalar = (Scalar)
				 * node; if (PHPModelUtils.isQuotesString(scalar.getValue())) {
				 * 
				 * IEvaluatedType evaluatedType = PHPTypeInferenceUtils
				 * .resolveExpression(sourceModule, node); if (evaluatedType !=
				 * null) {
				 * 
				 * IType[] types = PHPModelUtils.getTypes(
				 * evaluatedType.getTypeName(), sourceModule, offset, null,
				 * null); return types; } } }
				 */
			}
		}
		return null;
	}

	private IType[] filterNS(IType[] types) throws ModelException {
		// TODO Auto-generated method stub
		if (types == null) {
			return types;
		} else {
			Set<? super IType> result = new HashSet<IType>();
			// List<IType> result = new LinkedList<IType>();
			for (IType type : types) {
				if (PHPFlags.isClass(type.getFlags())) {
					result.add(type);
				}
			}
			return (IType[]) result.toArray(new IType[result.size()]);
		}
	}

	private IModelElement[] internalResolve(IStructuredDocument sDoc,
			ISourceModule sourceModule, int offset, int end) {
		try {
			IStructuredDocumentRegion sRegion = sDoc
					.getRegionAtCharacterOffset(offset);
			if (sRegion != null) {
				ITextRegion tRegion = sRegion
						.getRegionAtCharacterOffset(offset);

				ITextRegionCollection container = sRegion;
				if (tRegion instanceof ITextRegionContainer) {
					container = (ITextRegionContainer) tRegion;
					tRegion = container.getRegionAtCharacterOffset(offset);
				}

				if (tRegion != null
						&& tRegion.getType() == PHPRegionContext.PHP_CONTENT) {
					IPhpScriptRegion phpScriptRegion = (IPhpScriptRegion) tRegion;
					tRegion = phpScriptRegion.getPhpToken(offset
							- container.getStartOffset()
							- phpScriptRegion.getStart());
					if (tRegion == null) {
						return EMPTY;
					}
					// Determine element name:
					int elementStart = container.getStartOffset()
							+ phpScriptRegion.getStart() + tRegion.getStart();
					TextSequence statement = PHPTextSequenceUtilities
							.getStatement(elementStart + tRegion.getLength(),
									sRegion, true);
					if (statement == null) {
						return EMPTY;
					}
					int endPosition = PHPTextSequenceUtilities
							.readBackwardSpaces(statement, statement.length());
					int startPosition = PHPTextSequenceUtilities
							.readIdentifierStartIndex(phpVersion, statement,
									endPosition, true);
					String elementName = statement.subSequence(startPosition,
							endPosition).toString();

					// Determine previous word:
					int prevWordEnd = PHPTextSequenceUtilities
							.readBackwardSpaces(statement, startPosition);
					int prevWordStart = PHPTextSequenceUtilities
							.readIdentifierStartIndex(phpVersion, statement,
									prevWordEnd, false);
					String prevWord = statement.subSequence(prevWordStart,
							prevWordEnd).toString();

					// Determine next word:
					ITextRegion nextRegion = tRegion;
					do {
						nextRegion = phpScriptRegion.getPhpToken(nextRegion
								.getEnd());
						if (!PHPPartitionTypes.isPHPCommentState(nextRegion
								.getType())
								&& nextRegion.getType() != PHPRegionTypes.WHITESPACE) {
							break;
						}
					} while (nextRegion.getEnd() < phpScriptRegion.getLength());

					String nextWord = sDoc.get(
							container.getStartOffset()
									+ phpScriptRegion.getStart()
									+ nextRegion.getStart(),
							nextRegion.getTextLength());

					if (elementName.length() > 0) {
						IType containerType = PHPModelUtils.getCurrentType(
								sourceModule, offset);
						if (containerType == null) {
							containerType = PHPModelUtils.getCurrentNamespace(
									sourceModule, offset);
						}

						// If we are in function declaration:
						if (FUNCTION.equalsIgnoreCase(prevWord)) { //$NON-NLS-1$
							if (containerType != null) {
								return PHPModelUtils.getTypeMethod(
										containerType, elementName, true);
							}
							return getFunction(sourceModule, elementName);
						}

						// If we are in class declaration:
						if (CLASS.equalsIgnoreCase(prevWord)
								|| INTERFACE.equalsIgnoreCase(prevWord)) { //$NON-NLS-1$ //$NON-NLS-2$
							if (containerType != null) {
								if (containerType.getElementName()
										.equalsIgnoreCase(elementName)) {
									containerType = PHPModelUtils
											.getCurrentNamespace(sourceModule,
													offset);
								}
								if (containerType != null) {
									return PHPModelUtils.getTypeType(
											containerType, elementName, true);
								}
							}
							return getClass(sourceModule, elementName);
						}

						// Class instantiation:
						if (NEW.equalsIgnoreCase(prevWord)) { //$NON-NLS-1$
							return getConstructorsIfAny(extractClasses(PHPModelUtils
									.getTypes(elementName, sourceModule,
											offset, null, null)));
						}

						// Handle extends and implements:
						// Check that the statement suites the condition. If
						// class or interface keywords don't appear in the
						// beginning of the statement or they are alone there.
						boolean isClassDeclaration = false;
						if (statement.length() > 6
								&& (CLASS.equals(statement.subSequence(0, 5)
										.toString())
										&& (isClassDeclaration = true) || statement
										.length() > 10
										&& INTERFACE.equals(statement
												.subSequence(0, 9).toString()))) { //$NON-NLS-1$ //$NON-NLS-2$

							IModelElement[] generalizationTypes = getGeneralizationTypes(
									sourceModule, isClassDeclaration, prevWord,
									elementName, offset);
							if (generalizationTypes != null) {
								return generalizationTypes;
							}

							// Multiple extensions and implementations:
							int listStartPosition = PHPTextSequenceUtilities
									.readIdentifierListStartIndex(statement,
											endPosition);

							// Determine pre-list word:
							int preListWordEnd = PHPTextSequenceUtilities
									.readBackwardSpaces(statement,
											listStartPosition);
							int preListWordStart = PHPTextSequenceUtilities
									.readIdentifierStartIndex(statement,
											preListWordEnd, false);
							String preListWord = statement.subSequence(
									preListWordStart, preListWordEnd)
									.toString();

							generalizationTypes = getGeneralizationTypes(
									sourceModule, isClassDeclaration,
									preListWord, elementName, offset);
							if (generalizationTypes != null) {
								return generalizationTypes;
							}
						}

						// Previous trigger:
						String trigger = null;
						if (startPosition > 2) {
							trigger = statement.subSequence(startPosition - 2,
									startPosition).toString();
						}

						// If this is variable:
						if (elementName.charAt(0) == '$'
								&& !PAAMAYIM_NEKUDOTAIM.equals(trigger)) { //$NON-NLS-1$
							// Don't show escaped variables within PHP string:
							if (PHPPartitionTypes.isPHPQuotesState(tRegion
									.getType())) {
								try {
									char charBefore = sDoc.get(
											elementStart - 2, 1).charAt(0);
									if (charBefore == '\\') {
										return EMPTY;
									}
								} catch (BadLocationException e) {
									PHPCorePlugin.log(e);
								}
							}

							// If we are in var definition:
							if (containerType != null) {
								if (VAR.equalsIgnoreCase(prevWord)
										|| PRIVATE.equalsIgnoreCase(prevWord)
										|| STATIC.equalsIgnoreCase(prevWord)
										|| PUBLIC.equalsIgnoreCase(prevWord)
										|| PROTECTED.equalsIgnoreCase(prevWord)) {
									return PHPModelUtils.getTypeField(
											containerType, elementName, true);
								}
								if (THIS.equalsIgnoreCase(elementName)) { //$NON-NLS-1$
									return new IModelElement[] { containerType };
								}
							}

							return getGlobalOrMethodFields(sourceModule,
									offset, elementName);
						}

						// If we are at class constant definition:
						if (containerType != null) {
							if (CONST.equalsIgnoreCase(prevWord)) { //$NON-NLS-1$
								return PHPModelUtils.getTypeField(
										containerType, elementName, true);
							}
						}

						// We are at class trigger:
						if (PAAMAYIM_NEKUDOTAIM.equals(nextWord)) { //$NON-NLS-1$
							return PHPModelUtils.getTypes(elementName,
									sourceModule, offset, null, null);
						}
						if (NS_SEPARATOR.equals(nextWord)) { //$NON-NLS-1$
							IDLTKSearchScope scope = SearchEngine
									.createSearchScope(sourceModule
											.getScriptProject());
							return PhpModelAccess.getDefault().findTypes(null,
									elementName, MatchRule.EXACT,
									Modifiers.AccNameSpace, 0, scope, null);
						}

						IType[] types = CodeAssistUtils.getTypesFor(
								sourceModule, statement, startPosition, offset);

						// Is it function or method:
						if (OPEN_BRACE.equals(nextWord)
								|| PHPPartitionTypes.isPHPDocState(tRegion
										.getType())) { //$NON-NLS-1$
							if (types != null && types.length > 0) {
								List<IMethod> methods = new LinkedList<IMethod>();
								for (IType t : types) {
									methods.addAll(Arrays.asList(PHPModelUtils
											.getTypeHierarchyMethod(t,
													elementName, true, null)));
								}
								return methods.toArray(new IMethod[methods
										.size()]);
							}
							return PHPModelUtils.getFunctions(elementName,
									sourceModule, offset, null, null);
						}
						if ("insteadof".equals(nextWord) //$NON-NLS-1$
								|| "as".equals(nextWord)) { //$NON-NLS-1$
							if (types != null && types.length > 0) {
								List<IMethod> methods = new LinkedList<IMethod>();
								for (IType t : types) {
									methods.addAll(Arrays.asList(PHPModelUtils
											.getTypeHierarchyMethod(t,
													elementName, true, null)));
								}
								return methods.toArray(new IMethod[methods
										.size()]);
							}
						}
						if (types != null && types.length > 0) {
							// Check whether this is a class constant:
							if (startPosition > 0) {
								if (PAAMAYIM_NEKUDOTAIM.equals(trigger)
										&& elementName.charAt(0) != '$') { //$NON-NLS-1$
									List<IModelElement> fields = new LinkedList<IModelElement>();
									for (IType t : types) {
										IField[] typeFields = PHPModelUtils
												.getTypeField(t, elementName,
														true);
										for (IField currentField : typeFields) {
											fields.add(currentField);
										}
									}
									return fields
											.toArray(new IModelElement[fields
													.size()]);
								}
							}

							// What can it be? Only class variables:
							// Set<IModelElement> fields = new
							// TreeSet<IModelElement>(
							// new SourceFieldComparator());
							final List<IField> fields = new ArrayList<IField>();
							for (IType t : types) {
								fields.addAll(Arrays
										.asList(getTypeHierarchyField(t, null,
												elementName, true, null)));
							}
							return fields.toArray(new IModelElement[fields
									.size()]);
						}

						// This can be only global constant, if we've reached
						// here:
						IField[] fields = PHPModelUtils.getFields(elementName,
								sourceModule, offset, null, null);
						if (fields != null && fields.length > 0) {
							return fields;
						}

						// Return class if nothing else found.
						return PHPModelUtils.getTypes(elementName,
								sourceModule, offset, null, null);
					}
				}
			}
		} catch (Exception e) {
			PHPCorePlugin.log(e);
		}
		return EMPTY;
	}

	public static IField[] getTypeHierarchyField(IType type,
			ITypeHierarchy hierarchy, String prefix, boolean exactName,
			IProgressMonitor monitor) throws CoreException {
		if (prefix == null) {
			throw new NullPointerException();
		}
		final List<IField> fields = new ArrayList<IField>();
		fields.addAll(Arrays.asList(PHPModelUtils.getTypeField(type, prefix,
				exactName)));
		if (type.getSuperClasses() != null && type.getSuperClasses().length > 0) {
			Set<IModelElement> fieldSet = new TreeSet<IModelElement>(
					new SourceFieldComparator());
			fieldSet.addAll(Arrays.asList(PHPModelUtils
					.getSuperTypeHierarchyField(type, hierarchy, prefix,
							exactName, monitor)));
			IField[] temp = fieldSet.toArray(new IField[fieldSet.size()]);
			for (IField field : temp) {
				fields.add(field);
			}
		}
		return fields.toArray(new IField[fields.size()]);
	}

	private static IModelElement[] getGeneralizationTypes(
			ISourceModule sourceModule, boolean isClassDeclaration,
			String generalization, String elementName, int offset)
			throws ModelException {
		if (EXTENDS.equalsIgnoreCase(generalization)) {
			if (isClassDeclaration) {
				return extractClasses(PHPModelUtils.getTypes(elementName,
						sourceModule, offset, null, null));
			}
			return extractInterfaces(PHPModelUtils.getTypes(elementName,
					sourceModule, offset, null, null));
		}
		if (IMPLEMENTS.equalsIgnoreCase(generalization)) { //$NON-NLS-1$ //$NON-NLS-2$
			return extractInterfaces(PHPModelUtils.getTypes(elementName,
					sourceModule, offset, null, null));
		}
		return null;
	}

	private static IModelElement[] getFunction(ISourceModule sourceModule,
			String elementName) throws ModelException {
		IMethod[] methods = ((AbstractSourceModule) sourceModule).getMethods();
		for (IMethod method : methods) {
			if (method.getElementName().equalsIgnoreCase(elementName)) {
				return new IModelElement[] { method };
			}
		}
		return EMPTY;
	}

	private static IModelElement[] getClass(ISourceModule sourceModule,
			String elementName) throws ModelException {
		IType[] types = sourceModule.getTypes();
		for (IType type : types) {
			if (type.getElementName().equalsIgnoreCase(elementName)) {
				return new IModelElement[] { type };
			}
		}
		return EMPTY;
	}

	private static IType[] extractInterfaces(IType[] types) {
		List<IType> result = new ArrayList<IType>(types.length);
		for (IType type : types) {
			try {
				if (PHPFlags.isInterface(type.getFlags())) {
					result.add(type);
				}
			} catch (ModelException e) {
				PHPCorePlugin.log(e);
			}
		}
		return (IType[]) result.toArray(new IType[result.size()]);
	}

	private static IType[] extractClasses(IType[] types) {
		List<IType> result = new ArrayList<IType>(types.length);
		for (IType type : types) {
			try {
				if (PHPFlags.isClass(type.getFlags())) {
					result.add(type);
				}
			} catch (ModelException e) {
				PHPCorePlugin.log(e);
			}
		}
		return (IType[]) result.toArray(new IType[result.size()]);
	}

	private static IModelElement[] getConstructorsIfAny(IType[] types)
			throws ModelException {
		List<IModelElement> result = new LinkedList<IModelElement>();
		for (IType type : types) {
			boolean hasConstructor = false;
			for (IMethod method : type.getMethods()) {
				if (method.isConstructor()) {
					result.add(method);
					hasConstructor = true;
					break;
				}
			}
			if (!hasConstructor) {
				result.add(type);
			}
		}
		return (IModelElement[]) result
				.toArray(new IModelElement[result.size()]);
	}

	/**
	 * Return workspace or method fields depending on current position: whether
	 * we are inside method or in global scope.
	 * 
	 * @param sourceModule
	 * @param offset
	 * @param prefix
	 * @param mask
	 * @return
	 * @throws ModelException
	 */
	private static IModelElement[] getGlobalOrMethodFields(
			ISourceModule sourceModule, int offset, String prefix)
			throws ModelException {
		try {
			IModelElement enclosingElement = sourceModule.getElementAt(offset);
			if (enclosingElement instanceof IField) {
				enclosingElement = enclosingElement.getParent();
			}
			if (enclosingElement instanceof IMethod) {
				IMethod method = (IMethod) enclosingElement;
				return PHPModelUtils
						.getMethodFields(method, prefix, true, null);
			}
		} catch (ModelException e) {
			PHPCorePlugin.log(e);
		}
		return PHPModelUtils
				.getFields(prefix, sourceModule, offset, null, null);
	}

	private static class SourceFieldComparator implements
			Comparator<IModelElement> {
		public int compare(IModelElement o1, IModelElement o2) {
			try {
				SourceRefElement e1 = (SourceRefElement) o1;
				SourceRefElement e2 = (SourceRefElement) o2;
				IType type1 = (IType) e1.getAncestor(IModelElement.TYPE);
				IType type2 = (IType) e2.getAncestor(IModelElement.TYPE);
				if (type1 != null && type2 != null && type1 != type2) {
					return -1;
				}
				if (e1.getSourceModule() == e2.getSourceModule()) {
					ISourceRange r1 = e1.getSourceRange();
					ISourceRange r2 = e2.getSourceRange();
					return (int) Math.signum(r1.getOffset() - r2.getOffset());
				} else {
					return -1;
				}
			} catch (ModelException e) {
				PHPCorePlugin.log(e);
			}
			return 0;
		}
	}
}
