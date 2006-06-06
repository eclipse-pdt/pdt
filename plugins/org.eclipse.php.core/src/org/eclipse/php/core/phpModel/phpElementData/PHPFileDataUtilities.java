/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.core.phpModel.phpElementData;


import org.eclipse.php.core.phpModel.parser.ModelSupport;
import org.eclipse.php.core.phpModel.parser.PHPCodeContext;
import org.eclipse.php.core.phpModel.parser.PHPUserModel;

public final class PHPFileDataUtilities {

	private PHPFileDataUtilities() {
	}

	/**
	 * return the code data (class or function) that wrap the position.
	 *
	 * @param fileData The file data to search in.
	 * @param position The position in the document.
	 * @return return the code data (class or function) that wrap the position or null.
	 */
	public static CodeData getCodeData(PHPFileData fileData, int position) {
		if (position < 0 || fileData == null) {
			return null;
		}
		// check if the position is in one of the classes.
		PHPClassData[] classes = fileData.getClasses();
		PHPClassData classRv = null;
		PHPFunctionData functionRv = null;
		for (int i = 0; i < classes.length; i++) {
			if (contains(classes[i].getUserData(), position)) {
				if (classRv == null) {
					classRv = classes[i];
				} else {
					// if the old class we found includes the new one then the
					// new one is more relevant
					if (contains(classRv.getUserData(), classes[i].getUserData().getStartPosition())) {
						classRv = classes[i];
						functionRv = null;
					} else {
						continue;
					}
				}
				// the position is in the class check if it is in one of its methods
				PHPFunctionData[] functions = classes[i].getFunctions();
				for (int j = 0; j < functions.length; j++) {
					if (contains(functions[j].getUserData(), position)) {
						if (functionRv == null) {
							functionRv = functions[j];
						} else {
							// if the old function we found includes the new one
							// then the new one is more relevant
							if (contains(functionRv.getUserData(), functions[j].getUserData().getStartPosition())) {
								functionRv = functions[j];
							}
						}
					}
				}
			}
		}

		// check if the position is in one of the functions.
		PHPFunctionData[] functions = fileData.getFunctions();
		for (int j = 0; j < functions.length; j++) {
			if (contains(functions[j].getUserData(), position)) {
				if (functionRv == null) {
					functionRv = functions[j];
				} else {
					// if the old function we found includes the new one then
					// the new one is more relevant
					if (contains(functionRv.getUserData(), functions[j].getUserData().getStartPosition())) {
						functionRv = functions[j];
					}
				}
			}
		}
		if (functionRv != null) {
			return functionRv;
		}
		if (classRv != null) {
			return classRv;
		}
		return null;
	}

	public static PHPClassData getContainerClassDada(PHPFileData fileData, int position) {
		if (position < 0 || fileData == null) {
			return null;
		}
		PHPClassData[] classes = fileData.getClasses();
		for (int i = 0; i < classes.length; i++) {
			if (contains(classes[i].getUserData(), position)) {
				return classes[i];
			}
		}
		return null;
	}

	private static boolean contains(UserData userData, int position) {
		return position > userData.getStartPosition() && position <= userData.getEndPosition();
	}

	/**
	 * Returns the position of the nearest php open tag position.
	 */
	public static int getPHPStart(int offset, PHPFileData fileData) {
		PHPBlock[] phpBlocks = fileData.getPHPBlocks();
		for (int i = phpBlocks.length - 1; i >= 0; i--) {
			if (phpBlocks[i].getPHPStartTag().getStartPosition() < offset) {
				return phpBlocks[i].getPHPStartTag().getStartPosition();
			}
		}
		return 0;
	}

	public static String getVariableType(String fileName, String variableName, int position, int line, PHPUserModel userModel, boolean showObjectsFromOtherFiles) {
		String className;
		PHPFileData fileData = userModel.getFileData(fileName);
		if (variableName.equals("$this")) {
			PHPClassData classData = getContainerClassDada(fileData, position);
			if (classData != null) {
				className = classData.getName();
			} else {
				className = ""; // we are not inside a class.
			}
		} else {
			int currentLine = line + 1;
			PHPCodeContext context = ModelSupport.createContext(fileData, position);
			className = userModel.getVariableType(fileName, context, variableName, currentLine, showObjectsFromOtherFiles);
		}
		return className;
	}

}