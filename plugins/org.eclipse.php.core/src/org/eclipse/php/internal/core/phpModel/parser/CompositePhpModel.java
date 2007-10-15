package org.eclipse.php.internal.core.phpModel.parser;

import java.util.*;

import org.eclipse.php.internal.core.phpModel.phpElementData.*;

public abstract class CompositePhpModel implements IPhpModel {

	private IPhpModel[] models = new IPhpModel[0];

	//	private static CodeData[] mergeResults(List results) {
	//		if (results.size() == 0) {
	//			return new CodeData[0];
	//		}
	//		Set mergedResults = new TreeSet();
	//
	//		for (Iterator i = results.iterator(); i.hasNext();) {
	//			CodeData[] result = (CodeData[]) i.next();
	//			for (int j = 0; j < result.length; ++j) {
	//				mergedResults.add(result[j]);
	//			}
	//		}
	//		return (CodeData[]) mergedResults.toArray(new CodeData[mergedResults.size()]);
	//	}

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
		Set tempResult = new TreeSet();

		for (int i = 0; i < models.length; i++) {
			CodeData[] res = models[i].getFileDatas();
			if (res != null) {
				for (int j = 0; j < res.length; ++j)
					tempResult.add(res[j]);
			}
		}
		return (CodeData[]) tempResult.toArray(new CodeData[tempResult.size()]);
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
		Set tempResult = new TreeSet();

		for (int i = 0; i < models.length; i++) {
			CodeData[] res = models[i].getClasses();
			if (res != null) {
				for (int j = 0; j < res.length; ++j)
					tempResult.add(res[j]);
			}
		}
		return (CodeData[]) tempResult.toArray(new CodeData[tempResult.size()]);
	}

	public CodeData[] getClasses(String startsWith) {
		Set tempResult = new TreeSet();

		for (int i = 0; i < models.length; i++) {
			CodeData[] res = models[i].getClasses(startsWith);
			if (res != null) {
				for (int j = 0; j < res.length; ++j)
					tempResult.add(res[j]);
			}
		}
		return (CodeData[]) tempResult.toArray(new CodeData[tempResult.size()]);
	}

	public CodeData[] getClass(String className) {
		Set tempResult = new TreeSet();

		for (int i = 0; i < models.length; i++) {
			CodeData[] res = models[i].getClass(className);
			if (res != null) {
				for (int j = 0; j < res.length; ++j)
					tempResult.add(res[j]);
			}
		}
		return (CodeData[]) tempResult.toArray(new CodeData[tempResult.size()]);
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
		Set tempResult = new TreeSet();

		for (int i = 0; i < models.length; i++) {
			CodeData[] res = models[i].getFunctions();
			if (res != null) {
				for (int j = 0; j < res.length; ++j)
					tempResult.add(res[j]);
			}
		}
		return (CodeData[]) tempResult.toArray(new CodeData[tempResult.size()]);
	}

	public CodeData[] getFunctions(String startsWith) {
		Set tempResult = new TreeSet();

		for (int i = 0; i < models.length; i++) {
			CodeData[] res = models[i].getFunctions(startsWith);
			if (res != null) {
				for (int j = 0; j < res.length; ++j)
					tempResult.add(res[j]);
			}
		}
		return (CodeData[]) tempResult.toArray(new CodeData[tempResult.size()]);
	}

	public CodeData[] getFunction(String functionName) {
		Set tempResult = new TreeSet();

		for (int i = 0; i < models.length; i++) {
			CodeData[] res = models[i].getFunction(functionName);
			if (res != null) {
				for (int j = 0; j < res.length; ++j)
					tempResult.add(res[j]);
			}
		}
		return (CodeData[]) tempResult.toArray(new CodeData[tempResult.size()]);
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
		Set tempResult = new TreeSet();

		for (int i = 0; i < models.length; i++) {
			CodeData[] res = models[i].getConstants();
			if (res != null) {
				for (int j = 0; j < res.length; ++j)
					tempResult.add(res[j]);
			}
		}
		return (CodeData[]) tempResult.toArray(new CodeData[tempResult.size()]);
	}

	public CodeData[] getConstants(String startsWith, boolean caseSensitive) {
		Set tempResult = new TreeSet();

		for (int i = 0; i < models.length; i++) {
			CodeData[] res = models[i].getConstants(startsWith, caseSensitive);
			if (res != null) {
				for (int j = 0; j < res.length; ++j)
					tempResult.add(res[j]);
			}
		}
		return (CodeData[]) tempResult.toArray(new CodeData[tempResult.size()]);
	}

	public CodeData[] getConstant(String constantName) {
		Set tempResult = new TreeSet();

		for (int i = 0; i < models.length; i++) {
			CodeData[] res = models[i].getConstant(constantName);
			if (res != null) {
				for (int j = 0; j < res.length; ++j)
					tempResult.add(res[j]);
			}
		}
		return (CodeData[]) tempResult.toArray(new CodeData[tempResult.size()]);
	}

	public PHPConstantData getConstant(String fileName, String constantName) {
		for (int i = 0; i < models.length; i++) {
			PHPConstantData exactConstant = models[i].getConstant(fileName, constantName);
			// if filename is matching - just return the constant.
			if (exactConstant != null && exactConstant.getUserData() != null && exactConstant.getUserData().getFileName().equals(fileName)) {
				return exactConstant;
			}
		}
		return null;
	}

	public CodeData[] getGlobalVariables(String fileName, String startsWith, boolean showVariablesFromOtherFiles) {
		Set tempResult = new TreeSet();

		for (int i = 0; i < models.length; i++) {
			CodeData[] res = models[i].getGlobalVariables(fileName, startsWith, showVariablesFromOtherFiles);
			if (res != null) {
				for (int j = 0; j < res.length; ++j)
					tempResult.add(res[j]);
			}
		}
		return (CodeData[]) tempResult.toArray(new CodeData[tempResult.size()]);
	}

	public CodeData[] getVariables(String fileName, PHPCodeContext context, String startsWith, boolean showVariablesFromOtherFiles) {
		Set tempResult = new TreeSet();

		for (int i = 0; i < models.length; i++) {
			CodeData[] res = models[i].getVariables(fileName, context, startsWith, showVariablesFromOtherFiles);
			if (res != null) {
				for (int j = 0; j < res.length; ++j)
					tempResult.add(res[j]);
			}
		}
		return (CodeData[]) tempResult.toArray(new CodeData[tempResult.size()]);
	}

	public String getVariableType(String fileName, PHPCodeContext context, String variableName, int line, boolean showObjectsFromOtherFiles) {
		for (int i = 0; i < models.length; i++) {
			String res = models[i].getVariableType(fileName, context, variableName, line, showObjectsFromOtherFiles);
			if (res != null && !res.equals("")) { //$NON-NLS-1$
				return res;
			}
		}
		return null;
	}

	public IPHPMarker[] getMarkers() {
		Set tempResult = new TreeSet();

		for (int i = 0; i < models.length; i++) {
			IPHPMarker[] res = models[i].getMarkers();
			if (res != null) {
				for (int j = 0; j < res.length; ++j)
					tempResult.add(res[j]);
			}
		}
		return (IPHPMarker[]) tempResult.toArray(new IPHPMarker[tempResult.size()]);
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