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
package org.eclipse.php.core.phpModel.parser;

import org.eclipse.core.resources.IProject;
import org.eclipse.php.core.phpModel.phpElementData.CodeData;
import org.eclipse.php.core.phpModel.phpElementData.IPHPMarker;
import org.eclipse.php.core.phpModel.phpElementData.PHPClassData;
import org.eclipse.php.core.phpModel.phpElementData.PHPConstantData;
import org.eclipse.php.core.phpModel.phpElementData.PHPFileData;

public interface IPhpModel  {
	
	public String getID();
	
	public CodeData[] getPHPFilesData(String startsWith);
	
	public CodeData[] getNonPHPFiles(String startsWith);
	
	public PHPFileData getFileData(String fileName);

	public CodeData[] getFunctions();
	
	public CodeData[] getFunction(String functionName);
	
	public CodeData[] getFunctions(String startsWith);
	
	public CodeData[] getClasses();
	
	public PHPClassData getClass(String fileName, String className);
	
	public CodeData[] getClasses(String startsWith);
	
	public CodeData[] getGlobalVariables(String fileName, String startsWith, boolean showVariablesFromOtherFiles);
	
	public CodeData[] getVariables(String fileName, PHPCodeContext context, String startsWith, boolean showVariablesFromOtherFiles);
	
	public String getVariableType(String fileName, PHPCodeContext context, String variableName, int line, boolean showObjectsFromOtherFiles);
	
	public PHPConstantData getConstantData(String constantName);
	
	public CodeData[] getConstants();
	
	public CodeData[] getConstants(String startsWith, boolean caseSensitive);
	
	public IPHPMarker[] getMarkers();
	
	public void clean();
	
	public void dispose();
	
	public void initialize(IProject project);
}
