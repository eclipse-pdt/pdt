/**
 *
 */
package org.eclipse.php.internal.core.phpModel.parser;

import java.util.*;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Path;
import org.eclipse.php.core.documentModel.IWorkspaceModelListener;
import org.eclipse.php.internal.core.phpModel.phpElementData.*;

/**
 * @author seva, 2007
 * TODO add documentation
 */
public class FolderFilteredUserModel implements ModelListener, IWorkspaceModelListener, IPHPUserModel {

	String id;
	IWorkspaceModelListener listener;
	PHPUserModel model;
	String containerPath;

	private List<ModelListener> listeners = Collections.synchronizedList(new ArrayList<ModelListener>(2));

	FolderFilteredUserModel(PHPUserModel model, IContainer container) {
		this.model = model;
		id = container.getFullPath().toString();
		containerPath = id + "/"; //$NON-NLS-1$
	}

	public PHPClassData getClass(String fileName, String className) {
		CodeData candidate = model.getClass(fileName, className);
		return (PHPClassData) getFiltered(candidate);
	}

	public PHPConstantData getConstant(String fileName, String constantName) {
		CodeData candidate = model.getConstant(fileName, constantName);
		return (PHPConstantData) getFiltered(candidate);
	}

	public CodeData[] getGlobalVariables(String fileName, String startsWith, boolean showVariablesFromOtherFiles) {
		CodeData[] candidates = model.getGlobalVariables(fileName, startsWith, showVariablesFromOtherFiles);
		return filter(candidates);
	}

	public CodeData[] getVariables(String fileName, PHPCodeContext context, String startsWith, boolean showVariablesFromOtherFiles) {
		CodeData[] candidates = model.getVariables(fileName, context, startsWith, showVariablesFromOtherFiles);
		return filter(candidates);
	}

	public CodeData[] getClass(String className) {
		CodeData[] candidates = model.getClass(className);
		return filter(candidates);
	}

	public CodeData[] getConstant(String constantName) {
		CodeData[] candidates = model.getConstant(constantName);
		return filter(candidates);
	}

	public CodeData[] getConstants(String startsWith, boolean caseSensitive) {
		CodeData[] candidates = model.getConstants(startsWith, caseSensitive);
		return filter(candidates);
	}

	public PHPFunctionData getFunction(String fileName, String functionName) {
		CodeData candidate = model.getFunction(fileName, functionName);
		return (PHPFunctionData) getFiltered(candidate);
	}

	public CodeData[] getFunction(String functionName) {
		CodeData[] candidates = model.getFunction(functionName);
		return filter(candidates);
	}

	public CodeData[] getFunctions() {
		CodeData[] candidates = model.getFunctions();
		return filter(candidates);
	}

	public CodeData[] getFunctions(String startsWith) {
		CodeData[] candidates = model.getFunctions(startsWith);
		return filter(candidates);
	}

	public CodeData[] getClasses(String startsWith) {
		CodeData[] candidates = model.getClasses(startsWith);
		return filter(candidates);
	}

	public CodeData[] getClasses() {
		CodeData[] candidates = model.getClasses();
		return filter(candidates);
	}

	public CodeData[] getConstants() {
		CodeData[] candidates = model.getConstants();
		return filter(candidates);
	}

	public PHPFileData getFileData(String fileName) {
		PHPFileData fileData = model.getFileData(fileName);
		return (PHPFileData) getFiltered(fileData);
	}

	public CodeData[] getFileDatas() {
		return filter(model.getFileDatas());
	}

	public String getVariableType(String fileName, PHPCodeContext context, String variableName, int line, boolean showObjectsFromOtherFiles) {
		return model.getVariableType(fileName, context, variableName, line, showObjectsFromOtherFiles);
	}

	public String getID() {
		return id;
	}

	public void initialize(IProject project) {
		PHPWorkspaceModelManager.getInstance().addWorkspaceModelListener(project.getName(), this);
		model.addModelListener(this);
	}

	public void dispose() {
		PHPWorkspaceModelManager.getInstance().removeWorkspaceModelListener(new Path(containerPath).segment(0), this);
		model.removeModelListener(this);
	}

	public void projectModelAdded(IProject project) {
		updateModel(project);
	}

	public void projectModelRemoved(IProject project) {
		PHPUserModel model = new PHPUserModel();
		updateModelWrapper(model);
	}

	public void projectModelChanged(IProject project) {
	}

	public void dataCleared() {
		for (ModelListener listener : getModelListenerList()) {
			listener.dataCleared();
		}
	}

	public void fileDataAdded(PHPFileData fileData) {
		if (!select(fileData)) {
			return;
		}
		for (ModelListener listener : getModelListenerList()) {
			listener.fileDataAdded(fileData);
		}
	}

	public void fileDataChanged(PHPFileData fileData) {
		if (!select(fileData)) {
			return;
		}
		for (ModelListener listener : getModelListenerList()) {
			listener.fileDataChanged(fileData);
		}
	}

	public void fileDataRemoved(PHPFileData fileData) {
		if (!select(fileData)) {
			return;
		}
		for (ModelListener listener : getModelListenerList()) {
			listener.fileDataRemoved(fileData);
		}
	}

	private void updateModel(IProject project) {
		IPhpProjectModel projectModel = PHPWorkspaceModelManager.getInstance().getModelForProject(project);
		PHPUserModel userModel = projectModel == null ? new PHPUserModel() : (PHPUserModel) projectModel.getModel(PHPUserModel.ID);
		updateModelWrapper(userModel);
	}

	private void updateModelWrapper(PHPUserModel model) {
		this.model.removeModelListener(this);
		this.model = model;
		this.model.addModelListener(this);
	}

	private CodeData[] filter(Collection<CodeData> candidates) {
		if (candidates.size() == 0) {
			return ModelSupport.EMPTY_DATA;
		}
		// avoid concurrent modification:
		candidates = new ArrayList<CodeData>(candidates);
		List<CodeData> filtered = new ArrayList<CodeData>(candidates.size());
		for (CodeData candidate : candidates) {
			if (select(candidate)) {
				filtered.add(candidate);
			}
		}
		return filtered.toArray(new CodeData[filtered.size()]);
	}

	private CodeData[] filter(CodeData[] candidates) {
		return filter(Arrays.asList(candidates));
	}

	private CodeData getFiltered(CodeData candidate) {
		if (select(candidate)) {
			return candidate;
		}
		return null;
	}

	private boolean select(CodeData candidate) {
		return candidate != null && candidate.isUserCode() && candidate.getUserData().getFileName().startsWith(containerPath);
	}

	public void addModelListener(ModelListener l) {
		if (!listeners.contains(l))
			listeners.add(l);
	}

	public List<ModelListener> getModelListenerList() {
		return listeners;
	}

	public void removeModelListener(ModelListener l) {
		listeners.remove(l);
	}

	public void clear() {
	}

	public IPHPMarker[] getMarkers() {
		return model.getMarkers();
	}

}