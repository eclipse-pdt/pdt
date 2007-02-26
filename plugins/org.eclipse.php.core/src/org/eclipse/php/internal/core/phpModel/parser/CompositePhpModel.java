package org.eclipse.php.internal.core.phpModel.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.php.internal.core.phpModel.phpElementData.*;

public abstract class CompositePhpModel implements IPhpModel {

	private IPhpModel[] models = new IPhpModel[0];

	private static CodeData[] mergeResults(ArrayList results) {
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

	public CodeData[] getFileDatas() {
		ArrayList tempResultList = new ArrayList();

		for (int i = 0; i < models.length; i++) {
			CodeData[] res = models[i].getFileDatas();
			if (res != null && res.length > 0) {
				tempResultList.add(res);
			}
		}
		return mergeResults(tempResultList);
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

	public CodeData[] getClasses() {
		ArrayList tempResultList = new ArrayList();

		for (int i = 0; i < models.length; i++) {
			CodeData[] res = models[i].getClasses();
			if (res != null && res.length > 0) {
				tempResultList.add(res);
			}
		}
		return mergeResults(tempResultList);
	}

	public CodeData[] getClasses(String startsWith) {
		ArrayList tempResultList = new ArrayList();

		for (int i = 0; i < models.length; i++) {
			CodeData[] res = models[i].getClasses(startsWith);
			if (res != null && res.length > 0) {
				tempResultList.add(res);
			}
		}
		return mergeResults(tempResultList);
	}

	public CodeData[] getClass(String className) {
		List result = new ArrayList();
		for (int i = 0; i < models.length; i++) {
			CodeData[] classes = models[i].getClass(className);
			if (classes != null && classes.length > 0) {
				result.addAll(Arrays.asList(classes));
			}
		}
		return (CodeData[]) result.toArray(new CodeData[result.size()]);
	}

	public PHPClassData getClass(String fileName, String className) {

		for (int i = 0; i < models.length; i++) {
			PHPClassData exactClass = models[i].getClass(fileName, className);
			// if filename is matching - just return the class.
			if (exactClass != null && exactClass.getUserData() != null && exactClass.getUserData().getFileName().equals(fileName)) {
				return exactClass;
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
		return mergeResults(tempResultList);
	}

	public CodeData[] getFunctions(String startsWith) {
		ArrayList tempResultList = new ArrayList();

		for (int i = 0; i < models.length; i++) {
			CodeData[] res = models[i].getFunctions(startsWith);
			if (res != null && res.length > 0) {
				tempResultList.add(res);
			}
		}
		return mergeResults(tempResultList);
	}

	public CodeData[] getFunction(String functionName) {
		ArrayList tempResultList = new ArrayList();

		for (int i = 0; i < models.length; i++) {
			CodeData[] res = models[i].getFunction(functionName);
			if (res != null && res.length > 0) {
				tempResultList.add(res);
			}
		}
		return mergeResults(tempResultList);
	}

	public PHPFunctionData getFunction(String fileName, String functionName) {
		for (int i = 0; i < models.length; i++) {
			PHPFunctionData exactFunction = models[i].getFunction(fileName, functionName);
			// if filename is matching - just return the function.
			if (exactFunction != null && exactFunction.getUserData() != null && exactFunction.getUserData().getFileName().equals(fileName)) {
				return exactFunction;
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
		return mergeResults(tempResultList);
	}

	public CodeData[] getConstants(String startsWith, boolean caseSensitive) {
		ArrayList tempResultList = new ArrayList();

		for (int i = 0; i < models.length; i++) {
			CodeData[] res = models[i].getConstants(startsWith, caseSensitive);
			if (res != null && res.length > 0) {
				tempResultList.add(res);
			}
		}
		return mergeResults(tempResultList);
	}

	public CodeData[] getConstant(String className) {
		List result = new ArrayList();
		for (int i = 0; i < models.length; i++) {
			CodeData[] classes = models[i].getConstant(className);
			if (classes != null && classes.length > 0) {
				result.addAll(Arrays.asList(classes));
			}
		}
		return (CodeData[]) result.toArray(new CodeData[result.size()]);
	}

	public PHPConstantData getConstant(String fileName, String constantName) {
		for (int i = 0; i < models.length; i++) {
			PHPConstantData exactConstant = models[i].getConstant(fileName, constantName);
			// if filename is matching - just return the function.
			if (exactConstant != null && exactConstant.getUserData() != null && exactConstant.getUserData().getFileName().equals(fileName)) {
				return exactConstant;
			}
		}
		return null;
	}

	public CodeData[] getGlobalVariables(String fileName, String startsWith, boolean showVariablesFromOtherFiles) {
		ArrayList tempResultList = new ArrayList();

		for (int i = 0; i < models.length; i++) {
			CodeData[] res = models[i].getGlobalVariables(fileName, startsWith, showVariablesFromOtherFiles);
			if (res != null && res.length > 0) {
				tempResultList.add(res);
			}
		}
		return mergeResults(tempResultList);
	}

	public CodeData[] getVariables(String fileName, PHPCodeContext context, String startsWith, boolean showVariablesFromOtherFiles) {
		ArrayList tempResultList = new ArrayList();

		for (int i = 0; i < models.length; i++) {
			CodeData[] res = models[i].getVariables(fileName, context, startsWith, showVariablesFromOtherFiles);
			if (res != null && res.length > 0) {
				tempResultList.add(res);
			}
		}
		return mergeResults(tempResultList);
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

	public IPHPMarker[] getMarkers() {
		ArrayList tempResultList = new ArrayList();
		int length = 0;

		for (int i = 0; i < models.length; i++) {
			IPHPMarker[] res = models[i].getMarkers();
			if (res != null && res.length > 0) {
				tempResultList.add(res);
				length += res.length;
			}
		}

		IPHPMarker[] res = new IPHPMarker[length];
		int index = 0;
		for (int i = 0; i < tempResultList.size(); i++) {
			IPHPMarker[] currArray = (IPHPMarker[]) tempResultList.get(i);
			System.arraycopy(currArray, 0, res, index, currArray.length);
			index += currArray.length;
		}
		return res;
	}

	public void clear() {
		for (int i = 0; i < models.length; i++) {
			models[i].clear();
		}
	}

	public void dispose() {
		for (int i = 0; i < models.length; i++) {
			models[i].dispose();
		}
	}

	public CompositePhpModel() {
		super();
	}

}