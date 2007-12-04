/***********************************************************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html Contributors: Zend and IBM - Initial implementation
 **********************************************************************************************************************/
package org.eclipse.php.internal.core.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.documentModel.DOMModelForPHP;
import org.eclipse.php.internal.core.documentModel.parser.PHPRegionContext;
import org.eclipse.php.internal.core.documentModel.parser.regions.IPhpScriptRegion;
import org.eclipse.php.internal.core.documentModel.parser.regions.PHPRegionTypes;
import org.eclipse.php.internal.core.documentModel.partitioner.PHPPartitionTypes;
import org.eclipse.php.internal.core.phpModel.parser.*;
import org.eclipse.php.internal.core.phpModel.phpElementData.*;
import org.eclipse.php.internal.core.util.text.PHPTextSequenceUtilities;
import org.eclipse.php.internal.core.util.text.TextSequence;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;
import org.eclipse.wst.sse.core.internal.provisional.text.*;
import org.eclipse.wst.sse.core.internal.util.ProjectResolver;

public class CodeDataResolver {

	private static final CodeData[] EMPTY = {};

	private static CodeDataResolver instance;

	private CodeDataResolver() {
	}

	public static CodeDataResolver getInstance() {
		if (instance == null)
			instance = new CodeDataResolver();
		return instance;
	}

	public CodeData[] resolve(IFile file, int offset) throws IOException, CoreException {
		IStructuredModel model = null;
		try {
			model = StructuredModelManager.getModelManager().getModelForRead(file);
			if (model instanceof DOMModelForPHP) {
				DOMModelForPHP phpModel = (DOMModelForPHP) model;
				return resolve(model.getStructuredDocument(), offset, phpModel);
			}
			return EMPTY;
		} finally {
			if (model != null)
				model.releaseFromRead();
		}
	}

	public CodeData[] resolve(IProject project, File file, int offset) throws IOException {
		IStructuredDocument document = StructuredModelManager.getModelManager().createStructuredDocumentFor(file.getAbsolutePath(), new FileInputStream(file), new ProjectResolver(project));
		return resolve(document, offset);
	}

	/**
	 * This method resolved PHP code data which is under the specified offset in the document.
	 *
	 * @param sDoc Document instance
	 * @param offset Absolute offset in the document
	 * @return Array of resolved code datas, or empty array if offset doesn't point to PHP element (or in case of error)
	 */
	public CodeData[] resolve(IStructuredDocument sDoc, int offset) {
		IStructuredModel sModel = StructuredModelManager.getModelManager().getExistingModelForRead(sDoc);
		try {
			if (sModel instanceof DOMModelForPHP) {
				DOMModelForPHP phpModel = (DOMModelForPHP) sModel;
				return resolve(sDoc, offset, phpModel);
			}
		} finally {
			if (sModel != null) {
				sModel.releaseFromRead();
			}
		}
		return EMPTY;
	}

	/**
	 * This method resolved PHP code data which is under the specified offset in the document.
	 *
	 * @param sDoc Document instance
	 * @param offset Absolute offset in the document
	 * @param phpModel Instance of PHP DOM Model
	 * @return Array of resolved code datas, or empty array if offset doesn't point to PHP element (or in case of error).
	 */
	public CodeData[] resolve(IStructuredDocument sDoc, int offset, DOMModelForPHP phpModel) {
		final PHPProjectModel projectModel = phpModel.getProjectModel();
		final PHPFileData fileData = phpModel.getFileData(true);
		if (projectModel == null || fileData == null)
			return EMPTY;
		return resolve(sDoc, offset, projectModel, fileData);
	}

	/**
	 * This method resolved PHP code data which is under the specified offset in the document.
	 *
	 * @param sDoc Document instance
	 * @param offset Absolute offset in the document
	 * @param phpModel Instance of PHP DOM Model
	 * @return Array of resolved code datas, or empty array if offset doesn't point to PHP element (or in case of
	 *         error).
	 */
	public CodeData[] resolve(IStructuredDocument sDoc, int offset, PHPProjectModel projectModel, PHPFileData fileData) {
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
					int startPosition = PHPTextSequenceUtilities.readIdentifierStartIndex(statement, endPosition, true);
					String elementName = statement.subSequence(startPosition, endPosition).toString();

					// Determine previous word:
					int prevWordEnd = PHPTextSequenceUtilities.readBackwardSpaces(statement, startPosition);
					int prevWordStart = PHPTextSequenceUtilities.readIdentifierStartIndex(statement, prevWordEnd, false);
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
						String fileName = fileData != null ? fileData.getName() : null;

						PHPClassData classData = fileData != null ? PHPFileDataUtilities.getContainerClassData(fileData, offset) : null;

						// If we are in function declaration:
						if ("function".equalsIgnoreCase(prevWord)) { //$NON-NLS-1$
							if (classData != null) {
								return toArray(projectModel.getClassFunctionData(fileName, classData.getName(), elementName));
							}
							return toArray(projectModel.getFunction(fileName, elementName));
						}

						// If we are in class declaration:
						if ("class".equalsIgnoreCase(prevWord) || "interface".equalsIgnoreCase(prevWord)) { //$NON-NLS-1$ //$NON-NLS-2$
							return toArray(projectModel.getClass(fileName, elementName));
						}

						CodeData[] matchingClasses = getMatchingClasses(elementName, projectModel, fileName);

						// Class instantiation:
						if ("new".equalsIgnoreCase(prevWord)) { //$NON-NLS-1$
							return matchingClasses;
						}

						// Handle extends and implements:
						// Check that the statement suites the condition. If class or interface keywords don't appear in the beginning of the statement or they are alone there.
						if (statement.length() > 6 && ("class".equals(statement.subSequence(0, 5).toString()) || statement.length() > 10 && "interface".equals(statement.subSequence(0, 9).toString()))) { //$NON-NLS-1$ //$NON-NLS-2$

							if ("extends".equalsIgnoreCase(prevWord) || "implements".equalsIgnoreCase(prevWord)) { //$NON-NLS-1$ //$NON-NLS-2$
								return matchingClasses;
							}

							// Multiple extensions and implementations:

							final int listStartPosition = PHPTextSequenceUtilities.readIdentifierListStartIndex(statement, endPosition);

							// Determine pre-list word:
							final int preListWordEnd = PHPTextSequenceUtilities.readBackwardSpaces(statement, listStartPosition);
							final int preListWordStart = PHPTextSequenceUtilities.readIdentifierStartIndex(statement, preListWordEnd, false);
							final String preListWord = statement.subSequence(preListWordStart, preListWordEnd).toString();

							if ("extends".equalsIgnoreCase(preListWord) || "implements".equalsIgnoreCase(preListWord)) { //$NON-NLS-1$ //$NON-NLS-2$
								return matchingClasses;
							}
						}

						// Previous trigger:
						String trigger = null;
						if (startPosition > 2) {
							trigger = statement.subSequence(startPosition - 2, startPosition).toString();
						}

						// If this is variable:
						if (elementName.charAt(0) == '$' && !"::".equals(trigger)) { //$NON-NLS-1$
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

							elementName = elementName.substring(1);

							// If we are in var definition:
							if (classData != null) {
								if ("var".equalsIgnoreCase(prevWord) || "private".equalsIgnoreCase(prevWord) || "static".equalsIgnoreCase(prevWord) || "public".equalsIgnoreCase(prevWord) || "protected".equalsIgnoreCase(prevWord)) { //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
									return filterExact(classData.getVars(), elementName);
								}
								if ("this".equalsIgnoreCase(elementName)) { //$NON-NLS-1$
									return toArray(classData);
								}
							}

							PHPCodeContext context = ModelSupport.createContext(fileData, elementStart);
							CodeData[] res = filterExact(projectModel.getVariables(fileName, context, elementName, true), elementName);

							// Update variable position by assigning UserData:
							if (res.length == 1 && res[0].getUserData() == null && res[0] instanceof PHPVariableData) {
								PHPVariableData v = (PHPVariableData) res[0];
								Object variableContext = VariableContextBuilder.createVariableContext(elementName, context);

								// Find last assignment of variable:
								List instantiations = (List) fileData.getVariableTypeManager().getVariablesInstansiation().get(variableContext);
								if (instantiations != null && instantiations.size() > 0) {
									PHPVariableTypeData typeData = (PHPVariableTypeData) instantiations.get(instantiations.size() - 1);
									int varStartPosition = typeData.getPosition();

									// Search backwards for the variable itself:
									sRegion = sDoc.getRegionAtCharacterOffset(varStartPosition);
									if (sRegion != null) {
										tRegion = sRegion.getRegionAtCharacterOffset(varStartPosition);
										container = sRegion;
										if (tRegion instanceof ITextRegionContainer) {
											container = (ITextRegionContainer) tRegion;
											tRegion = container.getRegionAtCharacterOffset(varStartPosition);
										}
										if (tRegion != null && tRegion.getType() == PHPRegionContext.PHP_CONTENT) {
											phpScriptRegion = (IPhpScriptRegion) tRegion;
											tRegion = phpScriptRegion.getPhpToken(varStartPosition - container.getStartOffset() - phpScriptRegion.getStart());
											ITextRegion prevRegion = tRegion;
											do {
												prevRegion = phpScriptRegion.getPhpToken(prevRegion.getStart() - 1);
												if (prevRegion.getType() == PHPRegionTypes.PHP_VARIABLE) {
													break;
												}
											} while (prevRegion.getStart() > 0);

											if (prevRegion.getType() == PHPRegionTypes.PHP_VARIABLE) {
												varStartPosition = container.getStartOffset() + phpScriptRegion.getStart() + prevRegion.getStart();
												int varEndPosition = varStartPosition + elementName.length();
												UserData userData = PHPCodeDataFactory.createUserData(fileName, varStartPosition, varEndPosition, varEndPosition, typeData.getLine());
												res[0] = PHPCodeDataFactory.createPHPVariableData(v.getName(), v.getDocBlock(), userData);
											}
										}
									}
								}
							}
							return res;
						}

						// If we are at class constant definition:
						if (classData != null) {
							if ("const".equalsIgnoreCase(prevWord)) { //$NON-NLS-1$
								return filterExact(classData.getConsts(), elementName);
							}
						}

						// We are at class trigger:
						if ("::".equals(nextWord)) { //$NON-NLS-1$
							return matchingClasses;
						}

						String className = getClassName(projectModel, fileData, statement, startPosition, offset, sDoc.getLineOfOffset(offset));
						CodeData[] allClassDatas = null;
						if (className != null) {
							String[] classNames = className.split("\\|");
							for (String realClassName : classNames) {
								realClassName = realClassName.trim();
								CodeData[] classDatas = getMatchingClasses(realClassName, projectModel, fileName);
								if (allClassDatas == null) {
									allClassDatas = classDatas;
								} else {
									allClassDatas = ModelSupport.merge(allClassDatas, classDatas);
								}
							}
						}

						if (allClassDatas == null) {
							allClassDatas = EMPTY;
						}

						// Is it function or method:
						if ("(".equals(nextWord) || PHPPartitionTypes.isPHPDocState(tRegion.getType())) { //$NON-NLS-1$
							CodeData[] result = null;
							if (allClassDatas.length > 0) {
								for (CodeData currentClassData : allClassDatas) {
									result = ModelSupport.merge(result, toArray(projectModel.getClassFunctionData(fileName, currentClassData.getName(), elementName)));
								}
							} else {
								result = projectModel.getFilteredFunctions(fileName, elementName);
								if (result == null || result.length == 0)
									result = projectModel.getFunction(elementName);
							}
							return result == null ? EMPTY : result;
						}

						if (allClassDatas.length > 0) {
							// Check whether this is a class constant:
							if (startPosition > 0) {
								if ("::".equals(trigger) && elementName.charAt(0) != '$') { //$NON-NLS-1$
									CodeData[] result = null;
									for (int i = 0; i < allClassDatas.length; ++i) {
										result = ModelSupport.merge(result, toArray(projectModel.getClassConstsData(fileName, className, elementName)));
									}
									return result == null ? EMPTY : result;
								}
							}

							// What can it be? Only class variables:
							CodeData[] result = null;
							if (elementName.charAt(0) == '$')
								elementName = elementName.substring(1);
							for (CodeData currentClassData : allClassDatas) {
								// String fileName = classDatas[i].isUserCode() ?
								// classDatas[i].getUserData().getFileName() : "";
								result = ModelSupport.merge(result, toArray(projectModel.getClassVariablesData(fileName, currentClassData.getName(), elementName)));
							}
							return result == null ? EMPTY : result;
						}

						// This can be only global constant, if we've reached here:
						CodeData[] result = projectModel.getFilteredConstants(fileName, elementName);
						if (result == null || result.length == 0) {
							result = projectModel.getConstant(elementName);
						}

						// Return class if nothing else found.
						if (result == null || result.length == 0) {
							result = matchingClasses;
						}

						return result == null ? EMPTY : result;
					}
				}
			}
		} catch (Exception e) {
			PHPCorePlugin.log(e);
		}
		return EMPTY;
	}

	/**
	 * @param className
	 * @param projectModel
	 * @param fileData
	 * @return matching classes
	 */
	private CodeData[] getMatchingClasses(String className, PHPProjectModel projectModel, String fileName) {
		CodeData[] matchingClasses = projectModel.getFilteredClasses(fileName, className);
		if (matchingClasses == null || matchingClasses.length == 0)
			matchingClasses = projectModel.getClass(className);
		return matchingClasses;
	}

	private CodeData[] filterExact(CodeData[] result, String searchName) {
		List filtered = new ArrayList();
		if (result != null) {
			for (int i = 0; i < result.length; ++i) {
				if (searchName.equalsIgnoreCase(result[i].getName())) {
					filtered.add(result[i]);
				}
			}
		}
		return (CodeData[]) filtered.toArray(new CodeData[filtered.size()]);
	}

	/**
	 * Returns the class containing the PHP element
	 *
	 * @param projectModel PHP project model
	 * @param fileData Current file data
	 * @param startPosition PHP element start position
	 * @param offset Absolute offset in the document
	 * @param line Line number which corresponds to the offset
	 */
	private String getClassName(PHPProjectModel projectModel, PHPFileData fileData, TextSequence statement, int startPosition, int offset, int line) {
		if (startPosition < 2) {
			return null;
		}

		startPosition = PHPTextSequenceUtilities.readBackwardSpaces(statement, startPosition); // read whitespace

		if (startPosition < 2) {
			return null;
		}

		boolean isClassTriger = false;

		String triggerText = statement.subSequence(startPosition - 2, startPosition).toString();
		if ("->".equals(triggerText)) { //$NON-NLS-1$
		} else if ("::".equals(triggerText)) { //$NON-NLS-1$
			isClassTriger = true;
		} else {
			return null;
		}

		int propertyEndPosition = PHPTextSequenceUtilities.readBackwardSpaces(statement, startPosition - 2);
		int lastObjectOperator = PHPTextSequenceUtilities.getPrivousTriggerIndex(statement, propertyEndPosition);

		if (lastObjectOperator == -1) {
			// if there is no "->" or "::" in the left sequence then we need to calc the object type
			return innerGetClassName(projectModel, fileData, statement, propertyEndPosition, isClassTriger, offset, line);
		}

		int propertyStartPosition = PHPTextSequenceUtilities.readForwardSpaces(statement, lastObjectOperator + 2);
		String propertyName = statement.subSequence(propertyStartPosition, propertyEndPosition).toString();
		String className = getClassName(projectModel, fileData, statement, propertyStartPosition, offset, line);

		int bracketIndex = propertyName.indexOf('(');

		if (bracketIndex == -1) {
			// meaning its a class variable and not a function
			return getVarType(projectModel, fileData, className, propertyName, offset, line);
		}

		String functionName = propertyName.substring(0, bracketIndex).trim();
		return getFunctionReturnType(projectModel, fileData, className, functionName);
	}

	/**
	 * getting an instance and finding its type.
	 */
	private String innerGetClassName(PHPProjectModel projectModel, PHPFileData fileData, TextSequence statementText, int propertyEndPosition, boolean isClassTriger, int offset, int line) {
		if (fileData == null) {
			return null;
		}
		int classNameStart = PHPTextSequenceUtilities.readIdentifierStartIndex(statementText, propertyEndPosition, true);
		String className = statementText.subSequence(classNameStart, propertyEndPosition).toString();
		if (isClassTriger) {
			if (className.equals("self")) { //$NON-NLS-1$
				PHPClassData classData = PHPFileDataUtilities.getContainerClassData(fileData, offset - 6); // the
				// offset
				// before
				// self::
				if (classData != null) {
					return classData.getName();
				}
			} else if (className.equals("parent")) { //$NON-NLS-1$
				PHPClassData classData = PHPFileDataUtilities.getContainerClassData(fileData, offset - 8); // the
				// offset
				// before
				// parent::
				if (classData != null) {
					return projectModel.getSuperClassName(fileData.getName(), classData.getName());
				}
			}
			return className;
		}
		// if its object call calc the object type.
		if (className.length() > 0 && className.charAt(0) == '$') {
			// set the new statement start location as the original (absolute) one
			int statementStart = statementText.getOriginalOffset(0);
			return PHPFileDataUtilities.getVariableType(fileData, className, statementStart, line, projectModel, true);
		}
		// if its function call calc the return type.
		if (statementText.charAt(propertyEndPosition - 1) == ')') {
			int functionNameEnd = getFunctionNameEndOffset(statementText, propertyEndPosition - 1);
			int functionNameStart = PHPTextSequenceUtilities.readIdentifierStartIndex(statementText, functionNameEnd, false);

			String functionName = statementText.subSequence(functionNameStart, functionNameEnd).toString();
			PHPClassData classData = PHPFileDataUtilities.getContainerClassData(fileData, offset);
			if (classData != null) { // if its a clss function
				return getFunctionReturnType(projectModel, fileData, classData.getName(), functionName);
			}
			// if its a non class function
			PHPFunctionData[] functions = fileData.getFunctions();
			for (PHPFunctionData function : functions) {
				if (function.getName().equals(functionName)) {
					return function.getReturnType();
				}
			}
		}
		return null;
	}

	/**
	 * this function searches the sequence from the right closing bracket ")" and finding the position of the left "("
	 * the offset has to be the offset of the "("
	 */
	private int getFunctionNameEndOffset(TextSequence statementText, int offset) {
		if (statementText.charAt(offset) != ')') {
			return 0;
		}
		int currChar = offset;
		int bracketsNum = 1;
		while (bracketsNum != 0 && currChar >= 0) {
			currChar--;
			if (statementText.charAt(currChar) == ')') {
				bracketsNum++;
			} else if (statementText.charAt(currChar) == '(') {
				bracketsNum--;
			}
		}
		return currChar;
	}

	/**
	 * finding the type of the class variable.
	 */
	private String getVarType(PHPProjectModel projectModel, PHPFileData fileData, String className, String varName, int statementStart, int line) {
		String tempType = PHPFileDataUtilities.getVariableType(fileData.getName(), "this;*" + varName, statementStart, line, projectModel.getPHPUserModel(), true); //$NON-NLS-1$
		if (tempType != null) {
			return tempType;
		}
		CodeData classVar = projectModel.getClassVariablesData(fileData.getName(), className, varName);
		if (classVar != null) {
			if (classVar instanceof PHPClassVarData) {
				return ((PHPClassVarData) classVar).getClassType();
			}
			return null;
		}

		// checking if the var bellongs to one of the class's ancestor

		PHPClassData classData = projectModel.getClass(fileData.getName(), className);

		if (classData == null) {
			return null;
		}
		PHPClassData.PHPSuperClassNameData superClassNameData = classData.getSuperClassData();
		if (superClassNameData == null) {
			return null;
		}
		return getVarType(projectModel, fileData, superClassNameData.getName(), varName, statementStart, line);
	}

	/**
	 * finding the return type of the function.
	 */
	private String getFunctionReturnType(PHPProjectModel projectModel, PHPFileData fileData, String className, String functionName) {
		CodeData classFunction = projectModel.getClassFunctionData(fileData.getName(), className, functionName);
		if (classFunction != null) {
			if (classFunction instanceof PHPFunctionData) {
				return ((PHPFunctionData) classFunction).getReturnType();
			}
			return null;
		}

		// checking if the function bellongs to one of the class's ancestor
		PHPClassData classData = projectModel.getClass(fileData.getName(), className);

		if (classData == null) {
			return null;
		}
		String rv = null;
		PHPClassData.PHPSuperClassNameData superClassNameData = classData.getSuperClassData();
		if (superClassNameData != null) {
			rv = getFunctionReturnType(projectModel, fileData, superClassNameData.getName(), functionName);
		}

		// checking if its a non-class function from within the file
		if (rv == null) {
			CodeData[] functions = fileData.getFunctions();
			for (CodeData function : functions) {
				if (function.getName().equals(functionName)) {
					if (function instanceof PHPFunctionData) {
						rv = ((PHPFunctionData) function).getReturnType();
					}
				}
			}
		}

		// checking if its a non-class function from within the project
		if (rv == null) {
			CodeData[] functions = projectModel.getFunctions();
			for (CodeData function : functions) {
				if (function.getName().equals(functionName)) {
					if (function instanceof PHPFunctionData) {
						rv = ((PHPFunctionData) function).getReturnType();
					}
				}
			}
		}
		return rv;
	}

	private CodeData[] toArray(CodeData codeData) {
		if (codeData != null) {
			return new CodeData[] { codeData };
		}
		return EMPTY;
	}
}
