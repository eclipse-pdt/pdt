package org.eclipse.php.internal.core.phpModel.parser;

import java.util.*;

import org.eclipse.php.internal.core.phpModel.phpElementData.*;

public abstract class CompositePhpModel implements IPhpModel {

	private Map<String, IPhpModel> models = new LinkedHashMap<String, IPhpModel>();

	public void addModel(IPhpModel newModel) {
		models.put(newModel.getID(), newModel);
	}

	public IPhpModel remove(String modelId) {
		return models.remove(modelId);
	}

	public IPhpModel getModel(String modelId) {
		return models.get(modelId);
	}

	public IPhpModel[] getModels() {
		return models.values().toArray(new IPhpModel[models.size()]);
	}

	public CodeData[] getFileDatas() {
		CodeData[] merged = null;
		for (IPhpModel model : models.values()) {
			CodeData[] res = model.getFileDatas();
			merged = ModelSupport.merge(merged, res);
		}
		return merged;
	}

	public PHPFileData getFileData(String fileName) {
		for (IPhpModel model : models.values()) {
			PHPFileData res = model.getFileData(fileName);
			if (res != null) {
				return res;
			}
		}
		return null;
	}

	public CodeData[] getClasses() {
		CodeData[] merged = null;
		for (IPhpModel model : models.values()) {
			CodeData[] res = model.getClasses();
			merged = ModelSupport.merge(merged, res);
		}
		return merged;
	}

	public CodeData[] getClasses(String startsWith) {
		CodeData[] merged = null;
		for (IPhpModel model : models.values()) {
			CodeData[] res = model.getClasses(startsWith);
			merged = ModelSupport.merge(merged, res);
		}
		return merged;
	}

	public CodeData[] getClass(String className) {
		CodeData[] merged = null;
		for (IPhpModel model : models.values()) {
			CodeData[] res = model.getClass(className);
			merged = ModelSupport.merge(merged, res);
		}
		return merged;
	}

	public PHPClassData getClass(String fileName, String className) {
		for (IPhpModel model : models.values()) {
			PHPClassData exactClass = model.getClass(fileName, className);
			// if filename is matching - just return the class.
			if (exactClass != null && exactClass.getUserData() != null && exactClass.getUserData().getFileName().equals(fileName)) {
				return exactClass;
			}
		}
		return null;
	}

	public CodeData[] getFunctions() {
		CodeData[] merged = null;
		for (IPhpModel model : models.values()) {
			CodeData[] res = model.getFunctions();
			merged = ModelSupport.merge(merged, res);
		}
		return merged;
	}

	public CodeData[] getFunctions(String startsWith) {
		CodeData[] merged = null;
		for (IPhpModel model : models.values()) {
			CodeData[] res = model.getFunctions(startsWith);
			merged = ModelSupport.merge(merged, res);
		}
		return merged;
	}

	public CodeData[] getFunction(String functionName) {
		CodeData[] merged = null;
		for (IPhpModel model : models.values()) {
			CodeData[] res = model.getFunction(functionName);
			merged = ModelSupport.merge(merged, res);
		}
		return merged;
	}

	public PHPFunctionData getFunction(String fileName, String functionName) {
		for (IPhpModel element : models.values()) {
			PHPFunctionData exactFunction = element.getFunction(fileName, functionName);
			// if filename is matching - just return the function.
			if (exactFunction != null && exactFunction.getUserData() != null && exactFunction.getUserData().getFileName().equals(fileName)) {
				return exactFunction;
			}
		}
		return null;
	}

	public CodeData[] getConstants() {
		CodeData[] merged = null;
		for (IPhpModel model : models.values()) {
			CodeData[] res = model.getConstants();
			merged = ModelSupport.merge(merged, res);
		}
		return merged;
	}

	public CodeData[] getConstants(String startsWith, boolean caseSensitive) {
		CodeData[] merged = null;
		for (IPhpModel model : models.values()) {
			CodeData[] res = model.getConstants(startsWith, caseSensitive);
			merged = ModelSupport.merge(merged, res);
		}
		return merged;
	}

	public CodeData[] getConstant(String constantName) {
		CodeData[] merged = null;
		for (IPhpModel model : models.values()) {
			CodeData[] res = model.getConstant(constantName);
			merged = ModelSupport.merge(merged, res);
		}
		return merged;
	}

	public PHPConstantData getConstant(String fileName, String constantName) {
		for (IPhpModel element : models.values()) {
			PHPConstantData exactConstant = element.getConstant(fileName, constantName);
			// if filename is matching - just return the constant.
			if (exactConstant != null && exactConstant.getUserData() != null && exactConstant.getUserData().getFileName().equals(fileName)) {
				return exactConstant;
			}
		}
		return null;
	}

	public CodeData[] getGlobalVariables(String fileName, String startsWith, boolean showVariablesFromOtherFiles) {
		CodeData[] merged = null;
		for (IPhpModel model : models.values()) {
			CodeData[] res = model.getGlobalVariables(fileName, startsWith, showVariablesFromOtherFiles);
			merged = ModelSupport.merge(merged, res);
		}
		return merged;
	}

	public CodeData[] getVariables(String fileName, PHPCodeContext context, String startsWith, boolean showVariablesFromOtherFiles) {
		CodeData[] merged = null;
		for (IPhpModel model : models.values()) {
			CodeData[] res = model.getVariables(fileName, context, startsWith, showVariablesFromOtherFiles);
			merged = ModelSupport.merge(merged, res);
		}
		return merged;
	}

	public String getVariableType(String fileName, PHPCodeContext context, String variableName, int line, boolean showObjectsFromOtherFiles) {
		for (IPhpModel element : models.values()) {
			String res = element.getVariableType(fileName, context, variableName, line, showObjectsFromOtherFiles);
			if (res != null && !res.equals("")) { //$NON-NLS-1$
				return res;
			}
		}
		return null;
	}

	public IPHPMarker[] getMarkers() {
		List<IPHPMarker> tempResult = new ArrayList<IPHPMarker>();

		for (IPhpModel model : models.values()) {
			IPHPMarker[] res = model.getMarkers();
			tempResult.addAll(Arrays.asList(res));
		}
		return tempResult.toArray(new IPHPMarker[tempResult.size()]);
	}

	public void clear() {
		for (IPhpModel model : models.values()) {
			model.clear();
		}
	}

	public void dispose() {
		for (IPhpModel model : models.values()) {
			model.dispose();
		}
	}

	public CompositePhpModel() {
		super();
	}

}