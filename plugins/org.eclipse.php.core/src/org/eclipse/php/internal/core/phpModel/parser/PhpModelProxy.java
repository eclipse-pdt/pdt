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
package org.eclipse.php.internal.core.phpModel.parser;

import org.eclipse.php.internal.core.phpModel.phpElementData.*;

public abstract class PhpModelProxy implements IPhpModel {

	protected IPhpModel model;

	public String getID() {
		return model.getID();
	}

	public CodeData[] getFileDatas() {
		return model.getFileDatas();
	}

	public PHPFileData getFileData(String fileName) {
		return model.getFileData(fileName);
	}

	public CodeData[] getFunctions() {
		return model.getFunctions();
	}

	public CodeData[] getFunction(String functionName) {
		return model.getFunction(functionName);
	}

	public CodeData[] getFunctions(String startsWith) {
		return model.getFunctions(startsWith);
	}

	public CodeData[] getClasses() {
		return model.getClasses();
	}

	public PHPClassData getClass(String fileName, String className) {
		return model.getClass(fileName, className);
	}

	public CodeData[] getClasses(String startsWith) {
		return model.getClasses(startsWith);
	}

	public CodeData[] getGlobalVariables(String fileName, String startsWith, boolean showVariablesFromOtherFiles) {
		return model.getGlobalVariables(fileName, startsWith, showVariablesFromOtherFiles);
	}

	public CodeData[] getVariables(String fileName, PHPCodeContext context, String startsWith, boolean showVariablesFromOtherFiles) {
		return model.getVariables(fileName, context, startsWith, showVariablesFromOtherFiles);
	}

	public String getVariableType(String fileName, PHPCodeContext context, String variableName, int line, boolean showObjectsFromOtherFiles) {
		return model.getVariableType(fileName, context, variableName, line, showObjectsFromOtherFiles);
	}

	public PHPConstantData getConstantData(String constantName) {
		return model.getConstantData(constantName);
	}

	public CodeData[] getConstants() {
		return model.getConstants();
	}

	public CodeData[] getConstants(String startsWith, boolean caseSensitive) {
		return model.getConstants(startsWith, caseSensitive);
	}

	public IPHPMarker[] getMarkers() {
		return model.getMarkers();
	}

	public void clean() {
		model.clean();
	}

	public void dispose() {
		model.dispose();
	}
}
