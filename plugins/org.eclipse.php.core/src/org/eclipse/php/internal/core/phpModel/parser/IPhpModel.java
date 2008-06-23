/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.phpModel.parser;

import org.eclipse.core.resources.IProject;
import org.eclipse.php.internal.core.phpModel.phpElementData.*;

public interface IPhpModel {

	void initialize(IProject project);

	void clear();

	void dispose();

	String getID();

	CodeData[] getFileDatas();

	PHPFileData getFileData(String fileName);

	CodeData[] getClasses();

	CodeData[] getClasses(String startsWith);

	CodeData[] getClass(String className);

	PHPClassData getClass(String fileName, String className);

	CodeData[] getConstants();

	CodeData[] getConstants(String startsWith, boolean caseSensitive);

	CodeData[] getConstant(String constantName);

	PHPConstantData getConstant(String fileName, String constantName);

	CodeData[] getFunctions();

	CodeData[] getFunctions(String startsWith);

	CodeData[] getFunction(String functionName);

	PHPFunctionData getFunction(String fileName, String functionName);

	CodeData[] getGlobalVariables(String fileName, String startsWith, boolean showVariablesFromOtherFiles);

	CodeData[] getVariables(String fileName, PHPCodeContext context, String startsWith, boolean showVariablesFromOtherFiles);

	String getVariableType(String fileName, PHPCodeContext context, String variableName, int line, boolean showObjectsFromOtherFiles);

	IPHPMarker[] getMarkers();

}
