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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.text.IDocument;
import org.eclipse.php.internal.core.phpModel.parser.codeDataDB.CodeDataDB;
import org.eclipse.php.internal.core.phpModel.parser.codeDataDB.FilesCodeDataDB;
import org.eclipse.php.internal.core.phpModel.parser.codeDataDB.GlobalVariablesCodeDataDB;
import org.eclipse.php.internal.core.phpModel.parser.codeDataDB.TreeCodeDataDB;
import org.eclipse.php.internal.core.phpModel.phpElementData.*;

public class PHPUserModel implements IProjectModelListener, IPHPUserModel {

	private static final CodeData[] EMPTY = new CodeData[0];

	public static String ID = "PHPUserModel"; //$NON-NLS-1$

	private FilesCodeDataDB phpFileDataDB;
	private CodeDataDB classesDB;
	private CodeDataDB functionsDB;
	private CodeDataDB constantsDB;
	private CodeDataDB globalsVariablesDB;

	private List<ModelListener> listeners = Collections.synchronizedList(new ArrayList<ModelListener>(2));

	private PHPUserModelManager manager;

	public PHPUserModel() {
		phpFileDataDB = new FilesCodeDataDB();
		classesDB = new TreeCodeDataDB();
		functionsDB = new TreeCodeDataDB();
		constantsDB = new TreeCodeDataDB();
		globalsVariablesDB = new GlobalVariablesCodeDataDB();
	}

	public String getID() {
		return ID;
	}

	public synchronized void clear() {
		phpFileDataDB.clear();
		classesDB.clear();
		functionsDB.clear();
		constantsDB.clear();
		globalsVariablesDB.clear();
		fireDataCleared();
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public CodeData[] getFileDatas() {
		List<CodeData> list = phpFileDataDB.asList();
		PHPFileData[] allFileData = new PHPFileData[list.size()];
		list.toArray(allFileData);
		return allFileData;
	}

	public PHPFileData getFileData(String fileName) {
		return (PHPFileData) phpFileDataDB.getUniqCodeData(fileName);
	}

	public synchronized void insert(PHPFileData fileData) {
		String fileName = fileData.getName();
		PHPFileData oldData = (PHPFileData) phpFileDataDB.getUniqCodeData(fileName);
		boolean oldExists = oldData != null;
		if (oldExists) {
			delete(oldData);
		}

		phpFileDataDB.addCodeData(fileData);

		// add classes
		PHPClassData[] classes = fileData.getClasses();
		for (PHPClassData element : classes) {
			classesDB.addCodeData(element);
		}

		// add functions
		PHPFunctionData[] functions = fileData.getFunctions();
		for (PHPFunctionData element : functions) {
			functionsDB.addCodeData(element);
		}

		PHPConstantData[] constans = fileData.getConstants();
		for (PHPConstantData element : constans) {
			constantsDB.addCodeData(element);
		}

		// add global variables
		PHPVariableData[] globalsVariables = fileData.getVariableTypeManager().getVariables(ModelSupport.EMPTY_CONTEXT);
		if (globalsVariables != null) {
			for (PHPVariableData element : globalsVariables) {
				globalsVariablesDB.addCodeData(element);
			}
		}

		if (oldExists) {
			fireFileDataChanged(fileData);
		} else {
			fireFileDataAdded(fileData);
		}
	}

	public synchronized void delete(String fileName) {
		PHPFileData fileData = (PHPFileData) phpFileDataDB.getUniqCodeData(fileName);
		if (fileData != null) {
			delete(fileData);
			fireFileDataRemoved(fileData);
		}
	}

	protected synchronized void delete(PHPFileData fileData) {
		// remove classes
		PHPClassData[] classData = fileData.getClasses();
		for (PHPClassData element : classData) {
			classesDB.removeCodeData(element);
		}

		// remove functions
		PHPFunctionData[] functionData = fileData.getFunctions();
		for (PHPFunctionData element : functionData) {
			functionsDB.removeCodeData(element);
		}

		// remove functions
		PHPConstantData[] constans = fileData.getConstants();
		for (PHPConstantData element : constans) {
			constantsDB.removeCodeData(element);
		}

		// remove globalVariables
		PHPVariableData[] globalsVariables = fileData.getVariableTypeManager().getVariables(ModelSupport.EMPTY_CONTEXT);
		if (globalsVariables != null) {
			for (PHPVariableData element : globalsVariables) {
				globalsVariablesDB.removeCodeData(element);
			}
		}

		phpFileDataDB.removeCodeData(fileData);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public CodeData[] getFunctions() {
		List<CodeData> list = functionsDB.asList();
		PHPFunctionData[] rv = new PHPFunctionData[list.size()];
		list.toArray(rv);
		return rv;
	}

	public CodeData[] getFunction(String functionName) {
		Collection<CodeData> functions = functionsDB.getCodeData(functionName);
		if (functions == null || functions.size() == 0) {
			return PHPCodeDataFactory.EMPTY_CODE_DATA_ARRAY;
		}
		PHPFunctionData[] rv = new PHPFunctionData[functions.size()];
		functions.toArray(rv);
		return rv;
	}

	public PHPFunctionData getFunction(String fileName, String functionName) {
		Collection<CodeData> functions = functionsDB.getCodeData(functionName);
		if (functions == null || functions.size() == 0) {
			return null;
		}
		for (CodeData codeData : functions) {
			PHPFunctionData curr = (PHPFunctionData) codeData;
			if (curr.getUserData().getFileName().equals(fileName)) {
				return curr;
			}
		}
		return (PHPFunctionData) functions.iterator().next();
	}

	public CodeData[] getFunctions(String startsWith) {
		return ModelSupport.getCodeDataStartingWith(getFunctions(), startsWith);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public CodeData[] getClasses() {
		List<CodeData> list = classesDB.asList();
		PHPClassData[] rv = new PHPClassData[list.size()];
		list.toArray(rv);
		return rv;
	}

	public PHPClassData getClass(String fileName, String className) {
		Collection<CodeData> classes = classesDB.getCodeData(className);
		if (classes == null || classes.size() == 0) {
			return null;
		}
		for (CodeData codeData : classes) {
			PHPClassData curr = (PHPClassData) codeData;
			if (curr.getUserData().getFileName().equals(fileName)) {
				return curr;
			}
		}
		return (PHPClassData) classes.iterator().next();
	}

	public CodeData[] getClass(String className) {
		Collection<CodeData> classes = classesDB.getCodeData(className);
		if (classes == null)
			return EMPTY;
		return classes.toArray(new CodeData[classes.size()]);
	}

	public CodeData[] getClasses(String startsWith) {
		return ModelSupport.getCodeDataStartingWith(getClasses(), startsWith);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public CodeData[] getGlobalVariables(String fileName, String startsWith, boolean showVariablesFromOtherFiles) {
		if (!showVariablesFromOtherFiles) {
			PHPFileData fileData = getFileData(fileName);
			CodeData[] rv = null;
			if (fileData != null) {
				rv = fileData.getVariableTypeManager().getVariables(ModelSupport.EMPTY_CONTEXT);
			}
			if (rv == null) {
				rv = EMPTY;
			}
			return ModelSupport.getCodeDataStartingWith(rv, startsWith);
		}

		List<CodeData> list = globalsVariablesDB.asList();
		CodeData[] rv = new CodeData[list.size()];
		list.toArray(rv);
		return ModelSupport.getCodeDataStartingWith(rv, startsWith);
	}

	public CodeData[] getVariables(String fileName, PHPCodeContext context, String startsWith, boolean showVariablesFromOtherFiles) {
		if (context.equals(ModelSupport.EMPTY_CONTEXT)) {
			return getGlobalVariables(fileName, startsWith, showVariablesFromOtherFiles);
		}
		PHPFileData fileData = getFileData(fileName);
		CodeData[] rv = null;
		if (fileData != null) {
			rv = fileData.getVariableTypeManager().getVariables(context);
		}
		if (rv == null) {
			rv = EMPTY;
		}
		return ModelSupport.getCodeDataStartingWith(rv, startsWith);
	}

	private String getClassVariableType(PHPClassData classData, String variableName, int line) {
		if (classData == null) {
			return null;
		}
		String fileName = classData.getUserData().getFileName();
		PHPFileData fileData = (PHPFileData) phpFileDataDB.getUniqCodeData(fileName);
		if (fileData == null) {
			// we cant find the file return.
			return null;
		}
		PHPVariablesTypeManager variablesTypeManager = fileData.getVariableTypeManager();
		PHPCodeContext context = ModelSupport.createContext(classData.getName(), "");
		// search in this context.
		PHPVariableTypeData typeData = variablesTypeManager.getVariableTypeData(context, variableName, line);
		if (typeData != null) {
			return typeData.getType();
		}

		String superClassName = classData.getSuperClassData() != null ? classData.getSuperClassData().getName() : null;
		if (superClassName != null) {
			String rv = getClassVariableType(getClass(fileName, superClassName), variableName, line);
			return rv;
		}
		return null;
	}

	public String getVariableType(String fileName, PHPCodeContext context, String variableName, int line, boolean showObjectsFromOtherFiles) {
		PHPFileData fileData = (PHPFileData) phpFileDataDB.getUniqCodeData(fileName);
		if (fileData == null) {
			// we cant find the file return.
			return null;
		}

		if (variableName.length() > 0 && variableName.charAt(0) == '$') {
			variableName = variableName.substring(1);
		}

		PHPVariablesTypeManager variablesTypeManager = fileData.getVariableTypeManager();

		// search in this context.
		PHPVariableTypeData typeData = variablesTypeManager.getVariableTypeData(context, variableName, line);
		if (typeData != null) {
			return typeData.getType();
		}

		// if we are inside a class function check if it is a class variables.
		String className = context.getContainerClassName();
		if (className != null && !className.equals("")) {
			// we are inside a class.
			String classVariableType = getClassVariableType(getClass(fileName, className), variableName, line);
			if (classVariableType != null && classVariableType.length() > 0) {
				return classVariableType;
			}
		}

		if (!context.equals(ModelSupport.EMPTY_CONTEXT)) {
			// if we cant find the variable or its not global return.
			PHPVariableData variable = variablesTypeManager.getVariable(context, variableName);
			if (variable == null || !variable.isGlobal()) {
				return null;
			}
			// search in this file with global context.
			context = ModelSupport.EMPTY_CONTEXT;
			typeData = variablesTypeManager.getVariableTypeData(context, variableName, fileData.getUserData().getStopLine());
			if (typeData != null) {
				return typeData.getType();
			}
		}

		if (!showObjectsFromOtherFiles) {
			return null;
		}

		// Look in the other phpFileDataDB in global context.
		List<CodeData> list = Collections.synchronizedList(phpFileDataDB.asList());
		synchronized (list) {
			for (CodeData globalFileData : list) {
				if (fileData.getName().equals(fileName)) {
					continue; // no point in looking again...
				}
				variablesTypeManager = ((PHPFileData) globalFileData).getVariableTypeManager();

				typeData = variablesTypeManager.getVariableTypeData(context, variableName, fileData.getUserData().getStopLine());
				if (typeData != null) {
					return typeData.getType();
				}
			}
		}
		return null;
	}

	public CodeData[] getConstant(String constantName) {
		Collection<CodeData> constants = constantsDB.getCodeData(constantName);
		if (constants != null && constants.size() > 0) {
			return constants.toArray(new CodeData[constants.size()]);
		}
		return EMPTY;
	}

	public PHPConstantData getConstant(String fileName, String constantName) {
		Collection<CodeData> constants = constantsDB.getCodeData(constantName);
		if (constants == null || constants.size() == 0) {
			return null;
		}
		for (CodeData codeData : constants) {
			PHPConstantData curr = (PHPConstantData) codeData;
			if (curr.getUserData().getFileName().equals(fileName)) {
				return curr;
			}
		}
		return (PHPConstantData) constants.iterator().next();
	}

	public CodeData[] getConstants() {
		List<CodeData> list = constantsDB.asList();
		PHPConstantData[] rv = new PHPConstantData[list.size()];
		list.toArray(rv);
		return rv;
	}

	public CodeData[] getConstants(String startsWith, boolean caseSensitive) {
		return caseSensitive ? ModelSupport.getCodeDataStartingWithCS(getConstants(), startsWith) : ModelSupport.getCodeDataStartingWith(getConstants(), startsWith);
	}

	//////////////////////////////////////////////////////////////////////////////////////
	public IPHPMarker[] getMarkers() {
		return null;
	}

	//////////////////////////////////////////////////////////////////////////////////////

	//	private ParserClient parserClient;

	/** (non-Javadoc)
	 * @see org.eclipse.php.internal.core.phpModel.parser.IPHPUserModel#addModelListener(org.eclipse.php.internal.core.phpModel.parser.ModelListener)
	 */
	public void addModelListener(ModelListener l) {
		if (!listeners.contains(l))
			listeners.add(l);
	}

	/** (non-Javadoc)
	 * @see org.eclipse.php.internal.core.phpModel.parser.IPHPUserModel#getModelListenerList()
	 */
	public List<ModelListener> getModelListenerList() {
		return this.listeners;
	}

	/** (non-Javadoc)
	 * @see org.eclipse.php.internal.core.phpModel.parser.IPHPUserModel#removeModelListener(org.eclipse.php.internal.core.phpModel.parser.ModelListener)
	 */
	public void removeModelListener(ModelListener l) {
		listeners.remove(l);
	}

	private void fireFileDataChanged(PHPFileData fileData) {
		for (int i = 0; i < listeners.size(); i++) {
			ModelListener curr = listeners.get(i);
			curr.fileDataChanged(fileData);
		}
	}

	private void fireFileDataRemoved(PHPFileData fileData) {
		int size = listeners.size();
		for (int i = 0; i < size; i++) {
			ModelListener curr = listeners.get(i);
			curr.fileDataRemoved(fileData);
		}
	}

	private void fireFileDataAdded(PHPFileData fileData) {
		int size = listeners.size();
		for (int i = 0; i < size; i++) {
			ModelListener curr = listeners.get(i);
			curr.fileDataAdded(fileData);
		}
	}

	private void fireDataCleared() {
		int size = listeners.size();
		for (int i = 0; i < size; i++) {
			ModelListener curr = listeners.get(i);
			curr.dataCleared();
		}
	}

	public void dispose() {
		//		GlobalParsingManager.getInstance().removeParserClient(parserClient, project);
		if (manager != null) {
			manager.dispose();
			manager = null;
		}
	}

	public void initialize(IProject project) {
		manager = new PHPUserModelManager(project, this);
	}

	public void fileAdded(IFile file) {
	}

	public void fileRemoved(IFile file) {
		manager.fileRemoved(file);
	}

	public void fileChanged(IFile file, IDocument sDocument) {
	}

	public boolean isBuildNeeded() {
		return manager.isBuildNeeded();
	}

}