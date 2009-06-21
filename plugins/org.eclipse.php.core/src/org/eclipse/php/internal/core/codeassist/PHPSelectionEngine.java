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
import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ast.references.SimpleReference;
import org.eclipse.dltk.ast.references.TypeReference;
import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.dltk.codeassist.IAssistParser;
import org.eclipse.dltk.codeassist.ScriptSelectionEngine;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.core.search.SearchEngine;
import org.eclipse.dltk.core.search.SearchPattern;
import org.eclipse.dltk.internal.core.AbstractSourceModule;
import org.eclipse.dltk.internal.core.SourceRefElement;
import org.eclipse.dltk.ti.IContext;
import org.eclipse.dltk.ti.ISourceModuleContext;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.compiler.PHPFlags;
import org.eclipse.php.internal.core.compiler.ast.nodes.*;
import org.eclipse.php.internal.core.compiler.ast.parser.ASTUtils;
import org.eclipse.php.internal.core.documentModel.parser.PHPRegionContext;
import org.eclipse.php.internal.core.documentModel.parser.regions.IPhpScriptRegion;
import org.eclipse.php.internal.core.documentModel.parser.regions.PHPRegionTypes;
import org.eclipse.php.internal.core.documentModel.partitioner.PHPPartitionTypes;
import org.eclipse.php.internal.core.project.ProjectOptions;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;
import org.eclipse.php.internal.core.typeinference.PHPTypeInferenceUtils;
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

	public IAssistParser getParser() {
		return null;
	}

	public IModelElement[] select(org.eclipse.dltk.compiler.env.ISourceModule sourceUnit, int offset, int end) {
		
		if (end < offset) {
			end = offset + 1;
		}

		ISourceModule sourceModule = (ISourceModule) sourceUnit.getModelElement();
		phpVersion = ProjectOptions.getPhpVersion(sourceModule.getScriptProject().getProject());

		// First, try to resolve using AST (if we have parsed it well):
		try {
			IModelElement[] elements = internalASTResolve(sourceModule, offset, end);
			if (elements != null) {
				Collection<IModelElement> filtered = PHPModelUtils.filterElements(sourceModule, Arrays.asList(elements));
				return (IModelElement[]) filtered.toArray(new IModelElement[filtered.size()]);
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
					structuredModel = StructuredModelManager.getModelManager().getExistingModelForRead(file);
					if (structuredModel != null) {
						document = structuredModel.getStructuredDocument();
					} else {
						document = StructuredModelManager.getModelManager().createStructuredDocumentFor(file);
					}
				} else {
					document = StructuredModelManager.getModelManager().createNewStructuredDocumentFor(file);
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
			IModelElement[] elements = internalResolve(document, sourceModule, offset, end);
			if (elements != null) {
				Collection<IModelElement> filtered = PHPModelUtils.filterElements(sourceModule, Arrays.asList(elements));
				return (IModelElement[]) filtered.toArray(new IModelElement[filtered.size()]);
			}
		}

		return EMPTY;
	}

	private IModelElement[] internalASTResolve(ISourceModule sourceModule, int offset, int end) throws ModelException {

		String source = sourceModule.getSource();
		offset = PHPTextSequenceUtilities.readIdentifierStartIndex(source, offset, true);
		end = PHPTextSequenceUtilities.readIdentifierEndIndex(source, end, true);

		int methodEnd = PHPTextSequenceUtilities.getMethodEndIndex(source, end);
		if (methodEnd != -1) {
			end = methodEnd;
		}

		ModuleDeclaration parsedUnit = SourceParserUtil.getModuleDeclaration(sourceModule, null);

		ASTNode node = ASTUtils.findMinimalNode(parsedUnit, offset, end);
		if (node != null) {

			IContext context = ASTUtils.findContext(sourceModule, parsedUnit, node);
			if (context != null) {

				// Function call:
				if (node instanceof PHPCallExpression) {
					PHPCallExpression callExpression = (PHPCallExpression) node;
					if (callExpression.getReceiver() != null) {
						IEvaluatedType receiverType = PHPTypeInferenceUtils.resolveExpression(sourceModule, parsedUnit, context, callExpression.getReceiver());
						if (receiverType != null) {
							IModelElement[] elements = PHPTypeInferenceUtils.getModelElements(receiverType, (ISourceModuleContext) context, offset);
							List<IModelElement> methods = new LinkedList<IModelElement>();
							if (elements != null) {
								for (IModelElement element : elements) {
									if (element instanceof IType) {
										IType type = (IType) element;
										methods.addAll(Arrays.asList(CodeAssistUtils.getTypeMethods(type, callExpression.getName(), CodeAssistUtils.EXACT_NAME)));
									}
								}
							}
							return methods.toArray(new IModelElement[methods.size()]);
						}
					} else {
						SimpleReference callName = callExpression.getCallName();
						String methodName = callName instanceof FullyQualifiedReference ? ((FullyQualifiedReference)callName).getFullyQualifiedName() : callName.getName();
						return PHPModelUtils.getFunctions(methodName, sourceModule, offset);
					}
				}
				// Static field or constant access:
				else if (node instanceof StaticDispatch) {
					StaticDispatch dispatch = (StaticDispatch) node;
					String fieldName = null;
					if (dispatch instanceof StaticConstantAccess) {
						fieldName = ((StaticConstantAccess) dispatch).getConstant().getName();
					} else if (dispatch instanceof StaticFieldAccess) {
						ASTNode field = ((StaticFieldAccess) dispatch).getField();
						if (field instanceof VariableReference) {
							fieldName = ((VariableReference) field).getName();
						}
					}
					if (fieldName != null && dispatch.getDispatcher() != null) {
						IEvaluatedType dispatcherType = PHPTypeInferenceUtils.resolveExpression(sourceModule, parsedUnit, context, dispatch.getDispatcher());
						if (dispatcherType != null) {
							IModelElement[] elements = PHPTypeInferenceUtils.getModelElements(dispatcherType, (ISourceModuleContext) context, offset);
							List<IModelElement> fields = new LinkedList<IModelElement>();
							if (elements != null) {
								for (IModelElement element : elements) {
									if (element instanceof IType) {
										IType type = (IType) element;
										try {
											IField typeField = PHPModelUtils.getTypeField(type, fieldName);
											if (typeField != null) {
												fields.add(typeField);
											}
										} catch (ModelException e) {
											PHPCorePlugin.log(e);
										}
									}
								}
							}
							return fields.toArray(new IModelElement[fields.size()]);
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
					if (fieldName != null && fieldAccess.getDispatcher() != null) {
						IEvaluatedType dispatcherType = PHPTypeInferenceUtils.resolveExpression(sourceModule, parsedUnit, context, fieldAccess.getDispatcher());
						if (dispatcherType != null) {
							IModelElement[] elements = PHPTypeInferenceUtils.getModelElements(dispatcherType, (ISourceModuleContext) context, offset);
							List<IModelElement> fields = new LinkedList<IModelElement>();
							if (elements != null) {
								for (IModelElement element : elements) {
									if (element instanceof IType) {
										IType type = (IType) element;
										try {
											IField typeField = PHPModelUtils.getTypeField(type, fieldName);
											if (typeField != null) {
												fields.add(typeField);
											}
											typeField = PHPModelUtils.getTypeField(type, '$' + fieldName);
											if (typeField != null) {
												fields.add(typeField);
											}
										} catch (ModelException e) {
											PHPCorePlugin.log(e);
										}
									}
								}
							}
							return fields.toArray(new IModelElement[fields.size()]);
						}
					}
				}
				else if (node instanceof NamespaceReference) {
					String name = ((NamespaceReference) node).getName();
					return PHPModelUtils.getNamespaceOf(name + NamespaceReference.NAMESPACE_SEPARATOR, sourceModule, offset);
				}
				// Class/Interface reference:
				else if (node instanceof TypeReference) {
					String name = (node instanceof FullyQualifiedReference) ? ((FullyQualifiedReference) node).getFullyQualifiedName() : ((TypeReference) node).getName();
					IType[] types = PHPModelUtils.getTypes(name, sourceModule, offset);
					if (types == null || types.length == 0) {
						// This can be a constant or namespace in PHP 5.3:
						types = PHPModelUtils.getNamespaces(name, SearchPattern.R_EXACT_MATCH, SearchEngine.createSearchScope(sourceModule.getScriptProject()));
						if (types == null || types.length == 0) {
							return PHPModelUtils.getFields(name, sourceModule, offset);
						}
					}
					return types;
				}
				else if (node instanceof ClassInstanceCreation) {
					ClassInstanceCreation newNode = (ClassInstanceCreation) node;
					Expression className = newNode.getClassName();
					if (className instanceof SimpleReference) {
						String name = (className instanceof FullyQualifiedReference) ? ((FullyQualifiedReference) className).getFullyQualifiedName() : ((SimpleReference) className).getName();
						return getConstructorsIfAny(extractClasses(PHPModelUtils.getTypes(name, sourceModule, offset)));
					}
				}
			}
		}
		return null;
	}

	private IModelElement[] internalResolve(IStructuredDocument sDoc, ISourceModule sourceModule, int offset, int end) {
		try {
			IStructuredDocumentRegion sRegion = sDoc.getRegionAtCharacterOffset(offset);
			if (sRegion != null) {
				ITextRegion tRegion = sRegion.getRegionAtCharacterOffset(offset);

				ITextRegionCollection container = sRegion;
				if (tRegion instanceof ITextRegionContainer) {
					container = (ITextRegionContainer) tRegion;
					tRegion = container.getRegionAtCharacterOffset(offset);
				}

				if (tRegion.getType() == PHPRegionContext.PHP_CONTENT) {
					IPhpScriptRegion phpScriptRegion = (IPhpScriptRegion) tRegion;
					tRegion = phpScriptRegion.getPhpToken(offset - container.getStartOffset() - phpScriptRegion.getStart());

					// Determine element name:
					int elementStart = container.getStartOffset() + phpScriptRegion.getStart() + tRegion.getStart();
					TextSequence statement = PHPTextSequenceUtilities.getStatement(elementStart + tRegion.getLength(), sRegion, true);
					int endPosition = PHPTextSequenceUtilities.readBackwardSpaces(statement, statement.length());
					int startPosition = PHPTextSequenceUtilities.readIdentifierStartIndex(phpVersion, statement, endPosition, true);
					String elementName = statement.subSequence(startPosition, endPosition).toString();

					// Determine previous word:
					int prevWordEnd = PHPTextSequenceUtilities.readBackwardSpaces(statement, startPosition);
					int prevWordStart = PHPTextSequenceUtilities.readIdentifierStartIndex(phpVersion, statement, prevWordEnd, false);
					String prevWord = statement.subSequence(prevWordStart, prevWordEnd).toString();

					// Determine next word:
					ITextRegion nextRegion = tRegion;
					do {
						nextRegion = phpScriptRegion.getPhpToken(nextRegion.getEnd());
						if (!PHPPartitionTypes.isPHPCommentState(nextRegion.getType()) && nextRegion.getType() != PHPRegionTypes.WHITESPACE) {
							break;
						}
					} while (nextRegion.getEnd() < phpScriptRegion.getLength());

					String nextWord = sDoc.get(container.getStartOffset() + phpScriptRegion.getStart() + nextRegion.getStart(), nextRegion.getTextLength());

					if (elementName.length() > 0) {
						IType containerType = PHPModelUtils.getCurrentType(sourceModule, offset);
						if (containerType == null) {
							containerType = PHPModelUtils.getCurrentNamespace(sourceModule, offset);
						}

						// If we are in function declaration:
						if (FUNCTION.equalsIgnoreCase(prevWord)) { //$NON-NLS-1$
							if (containerType != null) {
								IMethod typeMethod = PHPModelUtils.getTypeMethod(containerType, elementName);
								if (typeMethod != null) {
									return new IMethod[] { typeMethod };
								}
								return EMPTY;
							}
							return getFunction(sourceModule, elementName);
						}

						// If we are in class declaration:
						if (CLASS.equalsIgnoreCase(prevWord) || INTERFACE.equalsIgnoreCase(prevWord)) { //$NON-NLS-1$ //$NON-NLS-2$
							if (containerType != null) {
								if (containerType.getElementName().equalsIgnoreCase(elementName)) {
									containerType = PHPModelUtils.getCurrentNamespace(sourceModule, offset);
								}
								IType typeType = PHPModelUtils.getTypeType(containerType, elementName);
								if (typeType != null) {
									return new IType[] { typeType };
								}
								return EMPTY;
							}
							return getClass(sourceModule, elementName);
						}

						// Class instantiation:
						if (NEW.equalsIgnoreCase(prevWord)) { //$NON-NLS-1$
							return getConstructorsIfAny(extractClasses(PHPModelUtils.getTypes(elementName, sourceModule, offset)));
						}

						// Handle extends and implements:
						// Check that the statement suites the condition. If class or interface keywords don't appear in the beginning of the statement or they are alone there.
						boolean isClassDeclaration = false;
						if (statement.length() > 6 && (CLASS.equals(statement.subSequence(0, 5).toString()) && (isClassDeclaration = true) || statement.length() > 10 && INTERFACE.equals(statement.subSequence(0, 9).toString()))) { //$NON-NLS-1$ //$NON-NLS-2$

							IModelElement[] generalizationTypes = getGeneralizationTypes(sourceModule, isClassDeclaration, prevWord, elementName, offset);
							if (generalizationTypes != null) {
								return generalizationTypes;
							}

							// Multiple extensions and implementations:
							int listStartPosition = PHPTextSequenceUtilities.readIdentifierListStartIndex(statement, endPosition);

							// Determine pre-list word:
							int preListWordEnd = PHPTextSequenceUtilities.readBackwardSpaces(statement, listStartPosition);
							int preListWordStart = PHPTextSequenceUtilities.readIdentifierStartIndex(statement, preListWordEnd, false);
							String preListWord = statement.subSequence(preListWordStart, preListWordEnd).toString();

							generalizationTypes = getGeneralizationTypes(sourceModule, isClassDeclaration, preListWord, elementName, offset);
							if (generalizationTypes != null) {
								return generalizationTypes;
							}
						}

						// Previous trigger:
						String trigger = null;
						if (startPosition > 2) {
							trigger = statement.subSequence(startPosition - 2, startPosition).toString();
						}

						// If this is variable:
						if (elementName.charAt(0) == '$' && !PAAMAYIM_NEKUDOTAIM.equals(trigger)) { //$NON-NLS-1$
							// Don't show escaped variables within PHP string:
							if (PHPPartitionTypes.isPHPQuotesState(tRegion.getType())) {
								try {
									char charBefore = sDoc.get(elementStart - 2, 1).charAt(0);
									if (charBefore == '\\') {
										return EMPTY;
									}
								} catch (BadLocationException e) {
									PHPCorePlugin.log(e);
								}
							}

							// If we are in var definition:
							if (containerType != null) {
								if (VAR.equalsIgnoreCase(prevWord) || PRIVATE.equalsIgnoreCase(prevWord) || STATIC.equalsIgnoreCase(prevWord) || PUBLIC.equalsIgnoreCase(prevWord) || PROTECTED.equalsIgnoreCase(prevWord)) { //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
									IField typeField = PHPModelUtils.getTypeField(containerType, elementName);
									if (typeField != null) {
										return new IField[] { typeField };
									}
									return EMPTY;
								}
								if (THIS.equalsIgnoreCase(elementName)) { //$NON-NLS-1$
									return new IModelElement[] { containerType };
								}
							}

							return getGlobalOrMethodFields(sourceModule, offset, elementName);
						}

						// If we are at class constant definition:
						if (containerType != null) {
							if (CONST.equalsIgnoreCase(prevWord)) { //$NON-NLS-1$
								IField typeField = PHPModelUtils.getTypeField(containerType, elementName);
								if (typeField != null) {
									return new IField[] { typeField };
								}
								return EMPTY;
							}
						}

						// We are at class trigger:
						if (PAAMAYIM_NEKUDOTAIM.equals(nextWord)) { //$NON-NLS-1$
							return PHPModelUtils.getTypes(elementName, sourceModule, offset);
						}
						if (NS_SEPARATOR.equals(nextWord)) { //$NON-NLS-1$
							return PHPModelUtils.getNamespaces(elementName, SearchPattern.R_EXACT_MATCH, SearchEngine.createSearchScope(sourceModule.getScriptProject()));
						}

						IType[] types = CodeAssistUtils.getTypesFor(sourceModule, statement, startPosition, offset);

						// Is it function or method:
						if (OPEN_BRACE.equals(nextWord) || PHPPartitionTypes.isPHPDocState(tRegion.getType())) { //$NON-NLS-1$
							if (types != null && types.length > 0) {
								List<IMethod> methods = new LinkedList<IMethod>();
								for (IType t : types) {
									methods.addAll(Arrays.asList(CodeAssistUtils.getTypeMethods(t, elementName, CodeAssistUtils.EXACT_NAME)));
								}
								return methods.toArray(new IMethod[methods.size()]);
							}
							return PHPModelUtils.getFunctions(elementName, sourceModule, offset);
						}

						if (types != null && types.length > 0) {
							// Check whether this is a class constant:
							if (startPosition > 0) {
								if (PAAMAYIM_NEKUDOTAIM.equals(trigger) && elementName.charAt(0) != '$') { //$NON-NLS-1$
									List<IModelElement> fields = new LinkedList<IModelElement>();
									for (IType t : types) {
										IField typeField = PHPModelUtils.getTypeField(t, elementName);
										if (typeField != null) {
											fields.add(typeField);
										}
									}
									return fields.toArray(new IModelElement[fields.size()]);
								}
							}

							// What can it be? Only class variables:
							Set<IModelElement> fields = new TreeSet<IModelElement>(new Comparator<IModelElement>() {
								public int compare(IModelElement o1, IModelElement o2) {
									try {
										ISourceRange r1 = ((SourceRefElement)o1).getSourceRange();
										ISourceRange r2 = ((SourceRefElement)o2).getSourceRange();
										return (int) Math.signum(r1.getOffset() - r2.getOffset());
									} catch (ModelException e) {
										PHPCorePlugin.log(e);
									}
									return 0;
								}
							});
							for (IType t : types) {
								fields.addAll(Arrays.asList(CodeAssistUtils.getTypeFields(t, elementName, CodeAssistUtils.EXACT_NAME)));
							}
							return fields.toArray(new IModelElement[fields.size()]);
						}

						// This can be only global constant, if we've reached here:
						IField[] fields = PHPModelUtils.getFields(elementName, sourceModule, offset);
						if (fields != null && fields.length > 0) {
							return fields;
						}

						// Return class if nothing else found.
						return PHPModelUtils.getTypes(elementName, sourceModule, offset);
					}
				}
			}
		} catch (Exception e) {
			PHPCorePlugin.log(e);
		}
		return EMPTY;
	}

	private static IModelElement[] getGeneralizationTypes(ISourceModule sourceModule, boolean isClassDeclaration, String generalization, String elementName, int offset) throws ModelException {
		if (EXTENDS.equalsIgnoreCase(generalization)) {
			if (isClassDeclaration) {
				return extractClasses(PHPModelUtils.getTypes(elementName, sourceModule, offset));
			}
			return extractInterfaces(PHPModelUtils.getTypes(elementName, sourceModule, offset));
		}
		if (IMPLEMENTS.equalsIgnoreCase(generalization)) { //$NON-NLS-1$ //$NON-NLS-2$
			return extractInterfaces(PHPModelUtils.getTypes(elementName, sourceModule, offset));
		}
		return null;
	}

	private static IModelElement[] getFunction(ISourceModule sourceModule, String elementName) throws ModelException {
		IMethod[] methods = ((AbstractSourceModule)sourceModule).getMethods();
		for (IMethod method : methods) {
			if (method.getElementName().equalsIgnoreCase(elementName)) {
				return new IModelElement[] { method };
			}
		}
		return EMPTY;
	}

	private static IModelElement[] getClass(ISourceModule sourceModule, String elementName) throws ModelException {
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
	
	private static IModelElement[] getConstructorsIfAny(IType[] types) throws ModelException {
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
		return (IModelElement[]) result.toArray(new IModelElement[result.size()]);
	}
	
	/**
	 * Return workspace or method fields depending on current position: whether we are inside method or in global scope.
	 * @param sourceModule
	 * @param offset
	 * @param prefix
	 * @param mask
	 * @return
	 * @throws ModelException 
	 */
	private static IModelElement[] getGlobalOrMethodFields(ISourceModule sourceModule, int offset, String prefix) throws ModelException {
		try {
			IModelElement enclosingElement = sourceModule.getElementAt(offset);
			if (enclosingElement instanceof IField) {
				enclosingElement = enclosingElement.getParent();
			}
			if (enclosingElement instanceof IMethod) {
				IMethod method = (IMethod) enclosingElement;
				return CodeAssistUtils.getMethodFields(method, prefix, CodeAssistUtils.EXACT_NAME);
			}
		} catch (ModelException e) {
			PHPCorePlugin.log(e);
		}
		return PHPModelUtils.getFields(prefix, sourceModule, offset);
	}
}
