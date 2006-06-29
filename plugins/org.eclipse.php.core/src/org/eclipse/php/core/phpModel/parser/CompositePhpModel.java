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

import java.util.ArrayList;
import java.util.Arrays;

import org.eclipse.php.core.phpModel.phpElementData.CodeData;
import org.eclipse.php.core.phpModel.phpElementData.IPHPMarker;
import org.eclipse.php.core.phpModel.phpElementData.PHPClassData;
import org.eclipse.php.core.phpModel.phpElementData.PHPConstantData;
import org.eclipse.php.core.phpModel.phpElementData.PHPFileData;

public abstract class CompositePhpModel implements IPhpModel {

	private IPhpModel[] models = new IPhpModel[0];

	public void addModel(IPhpModel newModel) {
		if (indexOf(newModel.getID()) != -1) {
			return;
		}
		IPhpModel[] tmp = new IPhpModel[models.length + 1];
		System.arraycopy(models, 0, tmp, 0, models.length);
		tmp[tmp.length - 1] = newModel;
		models = tmp;
	}

	public IPhpModel remove(String modelId) {
		if (modelId == null) {
			return null;
		}
		int index = indexOf(modelId);

		if (index == -1) {
			return null;
		}
		IPhpModel[] tmp = new IPhpModel[models.length - 1];
		for (int i = 0; i < index; i++) {
			tmp[i] = models[i];
		}
		for (int i = index + 1; i < models.length; i++) {
			tmp[i - 1] = models[i];
		}
		IPhpModel rv = models[index];
		models = tmp;
		return rv;
	}

	public IPhpModel getModel(String modelId) {
		int index = indexOf(modelId);
		if (index == -1) {
			return null;
		}
		return models[index];
	}

	public IPhpModel[] getModels() {
		return models;
	}

	private int indexOf(String modelId) {
		for (int i = 0; i < models.length; i++) {
			if (models[i].getID().equals(modelId)) {
				return i;
			}
		}
		return -1;
	}

	public CodeData[] getPHPFilesData(String startsWith) {
		ArrayList tempResultList = new ArrayList();

		for (int i = 0; i < models.length; i++) {
			CodeData[] res = models[i].getPHPFilesData(startsWith);
			if (res != null && res.length > 0) {
				tempResultList.add(res);
			}
		}
		return mergResults(tempResultList);
	}

	public CodeData[] getNonPHPFiles(String startsWith) {
		ArrayList tempResultList = new ArrayList();

		for (int i = 0; i < models.length; i++) {
			CodeData[] res = models[i].getNonPHPFiles(startsWith);
			if (res != null && res.length > 0) {
				tempResultList.add(res);
			}
		}
		return mergResults(tempResultList);
	}

	public PHPFileData getFileData(String fileName) {
		for (int i = 0; i < models.length; i++) {
			PHPFileData res = models[i].getFileData(fileName);
			if (res != null) {
				return res;
			}
		}
		return null;
	}

	public CodeData[] getFunctions() {
		ArrayList tempResultList = new ArrayList();

		for (int i = 0; i < models.length; i++) {
			CodeData[] res = models[i].getFunctions();
			if (res != null && res.length > 0) {
				tempResultList.add(res);
			}
		}
		return mergResults(tempResultList);
	}

	public CodeData[] getFunction(String functionName) {
		ArrayList tempResultList = new ArrayList();

		for (int i = 0; i < models.length; i++) {
			CodeData[] res = models[i].getFunction(functionName);
			if (res != null && res.length > 0) {
				tempResultList.add(res);
			}
		}
		return mergResults(tempResultList);
	}

	public CodeData[] getFunctions(String startsWith) {
		ArrayList tempResultList = new ArrayList();

		for (int i = 0; i < models.length; i++) {
			CodeData[] res = models[i].getFunctions(startsWith);
			if (res != null && res.length > 0) {
				tempResultList.add(res);
			}
		}
		return mergResults(tempResultList);
	}

	public CodeData[] getClasses() {
		ArrayList tempResultList = new ArrayList();

		for (int i = 0; i < models.length; i++) {
			CodeData[] res = models[i].getClasses();
			if (res != null && res.length > 0) {
				tempResultList.add(res);
			}
		}
		return mergResults(tempResultList);
	}

	public PHPClassData getClass(String fileName, String className) {
		for (int i = 0; i < models.length; i++) {
			PHPClassData res = models[i].getClass(fileName, className);
			if (res != null) {
				return res;
			}
		}
		return null;
	}

	public CodeData[] getClasses(String startsWith) {
		ArrayList tempResultList = new ArrayList();

		for (int i = 0; i < models.length; i++) {
			CodeData[] res = models[i].getClasses(startsWith);
			if (res != null && res.length > 0) {
				tempResultList.add(res);
			}
		}
		return mergResults(tempResultList);
	}

	public CodeData[] getGlobalVariables(String fileName, String startsWith, boolean showVariablesFromOtherFiles) {
		ArrayList tempResultList = new ArrayList();

		for (int i = 0; i < models.length; i++) {
			CodeData[] res = models[i].getGlobalVariables(fileName, startsWith, showVariablesFromOtherFiles);
			if (res != null && res.length > 0) {
				tempResultList.add(res);
			}
		}
		return mergResults(tempResultList);
	}

	public CodeData[] getVariables(String fileName, PHPCodeContext context, String startsWith, boolean showVariablesFromOtherFiles) {
		ArrayList tempResultList = new ArrayList();

		for (int i = 0; i < models.length; i++) {
			CodeData[] res = models[i].getVariables(fileName, context, startsWith, showVariablesFromOtherFiles);
			if (res != null && res.length > 0) {
				tempResultList.add(res);
			}
		}
		return mergResults(tempResultList);
	}

	public String getVariableType(String fileName, PHPCodeContext context, String variableName, int line, boolean showObjectsFromOtherFiles) {
		for (int i = 0; i < models.length; i++) {
			String res = models[i].getVariableType(fileName, context, variableName, line, showObjectsFromOtherFiles);
			if (res != null && !res.equals("")) {
				return res;
			}
		}
		return null;
	}

	public PHPConstantData getConstantData(String constantName) {
		for (int i = 0; i < models.length; i++) {
			PHPConstantData res = models[i].getConstantData(constantName);
			if (res != null) {
				return res;
			}
		}
		return null;
	}

	public CodeData[] getConstants() {
		ArrayList tempResultList = new ArrayList();

		for (int i = 0; i < models.length; i++) {
			CodeData[] res = models[i].getConstants();
			if (res != null && res.length > 0) {
				tempResultList.add(res);
			}
		}
		return mergResults(tempResultList);
	}

	public CodeData[] getConstants(String startsWith, boolean caseSensitive) {
		ArrayList tempResultList = new ArrayList();

		for (int i = 0; i < models.length; i++) {
			CodeData[] res = models[i].getConstants(startsWith, caseSensitive);
			if (res != null && res.length > 0) {
				tempResultList.add(res);
			}
		}
		return mergResults(tempResultList);
	}

	public IPHPMarker[] getMarkers() {
		ArrayList tempResultList = new ArrayList();
		int length = 0;

		for (int i = 0; i < models.length; i++) {
			IPHPMarker[] res = models[i].getMarkers();
			if (res != null && res.length > 0) {
				tempResultList.add(res);
				length +=res.length;
			}
		}
		
		IPHPMarker[] res = new IPHPMarker[length];
		int index = 0;
		for(int i= 0 ; i < tempResultList.size() ; i++){
			IPHPMarker[] currArray = (IPHPMarker[])tempResultList.get(i);
			System.arraycopy(currArray, 0, res, index, currArray.length);
			index+=currArray.length;
		}
		return res;
	}

	public void clean() {
		for (int i = 0; i < models.length; i++) {
			models[i].clean();
		}
	}

	public void dispose() {
		for (int i = 0; i < models.length; i++) {
			models[i].dispose();
		}
	}

	private CodeData[] mergResults(ArrayList results) {
		if (results.size() == 0) {
			return new CodeData[0];
		}
		CodeData[] res = (CodeData[]) results.get(0);
		Arrays.sort(res);

		for (int i = 1; i < results.size(); i++) {
			CodeData[] res1 = (CodeData[]) results.get(i);
			Arrays.sort(res1);
			res = ModelSupport.merge(res, res1);
		}
		return res;

	}
}
