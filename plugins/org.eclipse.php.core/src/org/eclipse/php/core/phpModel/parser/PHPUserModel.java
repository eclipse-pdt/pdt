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

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.php.core.phpModel.parser.codeDataDB.CodeDataDB;
import org.eclipse.php.core.phpModel.parser.codeDataDB.FilesCodeDataDB;
import org.eclipse.php.core.phpModel.parser.codeDataDB.GlobalVariablesCodeDataDB;
import org.eclipse.php.core.phpModel.parser.codeDataDB.TreeCodeDataDB;
import org.eclipse.php.core.phpModel.phpElementData.*;
import org.eclipse.php.core.util.Visitor;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;

public class PHPUserModel implements IPhpModel, IProjectModelListener {

	public static String ID = "PHPUserModel";

	private FilesCodeDataDB phpFileDataDB;
	private FilesCodeDataDB nonPhpFileDataDB;
	private CodeDataDB classesDB;
	private CodeDataDB functionsDB;
	private CodeDataDB constansDB;
	private CodeDataDB globalsVariablesDB;

	private List listeners = Collections.synchronizedList(new ArrayList(2));

	private IProject project;
	private PHPUserModelManager manager;

	public PHPUserModel() {
		phpFileDataDB = new FilesCodeDataDB();//new HashMap();
		nonPhpFileDataDB = new FilesCodeDataDB();
		classesDB = new TreeCodeDataDB();
		functionsDB = new TreeCodeDataDB();
		constansDB = new TreeCodeDataDB();
		globalsVariablesDB = new GlobalVariablesCodeDataDB();
	}

	public String getID() {
		return ID;
	}

	public synchronized void clear() {
		phpFileDataDB.clear();
		classesDB.clear();
		functionsDB.clear();
		constansDB.clear();
		globalsVariablesDB.clear();
		fireDataCleared();
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * NOTE ! this method should get the file name and not the full path !!!
	 *
	 * @param startsWith
	 * @return the file data who's
	 */
	public CodeData[] getPHPFilesData(String startsWith) {
		//        PHPFileData[] all = new PHPFileData[phpFileDataDB.size()];
		//        phpFileDataDB.values().toArray(all);
		//        Arrays.sort(all, new Comparator() {
		//            public int compare(Object o, Object o1) {
		//                String name1 = new File(((PHPFileData) o).getName()).getName();
		//                String name2 = new File(((PHPFileData) o1).getName()).getName();
		//                return name1.compareToIgnoreCase(name2);
		//            }
		//        });

		List list = phpFileDataDB.asList();
		PHPFileData[] all = new PHPFileData[list.size()];
		list.toArray(all);
		return ModelSupport.getFileDataStartingWith(all, startsWith);
	}

	public CodeData[] getNonPHPFiles(String startsWith) {
		//        File[] all = new File[nonPhpFileDataDB.size()];
		//        nonPhpFileDataDB.values().toArray(all);
		//        Arrays.sort(all, new Comparator() {
		//            public int compare(Object o, Object o1) {
		//                String name1 = ((File) o).getName();
		//                String name2 = ((File) o1).getName();
		//                return name1.compareToIgnoreCase(name2);
		//            }
		//        });
		List list = nonPhpFileDataDB.asList();
		ProjectFileCodeData[] all = new ProjectFileCodeData[list.size()];
		list.toArray(all);
		return ModelSupport.getFileDataStartingWith(all, startsWith);//FileSStartingWith(all, startsWith);
	}

	public PHPFileData getFileData(String fileName) {
		return (PHPFileData) phpFileDataDB.getUniqCodeData(fileName);
	}

	public PHPFileData[] getFileDatas() {
		List list = phpFileDataDB.asList();
		PHPFileData[] allFileData = new PHPFileData[list.size()];
		list.toArray(allFileData);
		return allFileData;
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
		for (int i = 0; i < classes.length; i++) {
			classesDB.addCodeData(classes[i]);
		}

		// add functions
		PHPFunctionData[] functions = fileData.getFunctions();
		for (int i = 0; i < functions.length; i++) {
			functionsDB.addCodeData(functions[i]);
		}

		PHPConstantData[] constans = fileData.getConstants();
		for (int i = 0; i < constans.length; i++) {
			constansDB.addCodeData(constans[i]);
		}

		// add global variables
		PHPVariableData[] globalsVariables = fileData.getVariableTypeManager().getVariables(ModelSupport.EMPTY_CONTEXT);
		if (globalsVariables != null) {
			for (int i = 0; i < globalsVariables.length; i++) {
				globalsVariablesDB.addCodeData(globalsVariables[i]);
			}
		}

		if (oldExists) {
			fireFileDataChanged(fileData);
		} else {
			fireFileDataAdded(fileData);
		}
	}

	public synchronized void insert(File file) {
		nonPhpFileDataDB.addCodeData(new ProjectFileCodeData(file.getPath(), ""));
	}

	class ProjectFileCodeData extends AbstractCodeData implements ComparableName {

		private String comparableName;

		public ProjectFileCodeData(String name, String description) {
			super(name, description);
			comparableName = new File(name).getName();
		}

		public void accept(Visitor v) {
		}

		public int compareTo(Object o) {
			return comparableName.compareToIgnoreCase(((ComparableName) o).getComparableName());
		}

		public String getComparableName() {
			return comparableName;
		}
	}

	public synchronized void delete(String fileName) {
		PHPFileData fileData = (PHPFileData) phpFileDataDB.getUniqCodeData(fileName);
		if (fileData != null) {
			delete(fileData);
			nonPhpFileDataDB.removeCodeData(fileData);
			fireFileDataRemoved(fileData);
		}
	}

	/*private synchronized void deleteDirectory(File file) {
	 String fileName = file.getPath();
	 String dName = fileName + VirtualFileSystem.instance.getFileSystem(file).getSeparator();

	 CodeData[] allFilesData = getPHPFilesData();
	 Arrays.sort(allFilesData);

	 CodeData[] filesDataToDelete = ModelSupport.getCodeDataStartingWith(allFilesData, dName);
	 for (int i = 0; i < filesDataToDelete.length; i++) {
	 deleteFile(filesDataToDelete[i].getName());
	 }
	 }

	 private synchronized void deleteFile(String fileName) {
	 PHPFileData fileData = (PHPFileData) phpFileDataDB.get(fileName);
	 if (fileData != null) {
	 delete(fileData);
	 fireFileDataRemoved(fileData);
	 }
	 }*/

	protected synchronized void delete(PHPFileData fileData) {
		// remove classes
		PHPClassData[] classData = fileData.getClasses();
		for (int i = 0; i < classData.length; i++) {
			classesDB.removeCodeData(classData[i]);
		}

		// remove functions
		PHPFunctionData[] functionData = fileData.getFunctions();
		for (int i = 0; i < functionData.length; i++) {
			functionsDB.removeCodeData(functionData[i]);
		}

		// remove functions
		PHPConstantData[] constans = fileData.getConstants();
		for (int i = 0; i < constans.length; i++) {
			constansDB.removeCodeData(constans[i]);
		}

		// remove globalVariables
		PHPVariableData[] globalsVariables = fileData.getVariableTypeManager().getVariables(ModelSupport.EMPTY_CONTEXT);
		if (globalsVariables != null) {
			for (int i = 0; i < globalsVariables.length; i++) {
				globalsVariablesDB.removeCodeData(globalsVariables[i]);
			}
		}

		phpFileDataDB.removeCodeData(fileData);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public CodeData[] getFunctions() {
		List list = functionsDB.asList();
		PHPFunctionData[] rv = new PHPFunctionData[list.size()];
		list.toArray(rv);
		return rv;
	}

	public CodeData[] getFunction(String functionName) {
		List list = functionsDB.getCodeData(functionName);
		if (list == null || list.size() == 0) {
			return PHPCodeDataFactory.EMPTY_CODE_DATA_ARRAY;
		}
		PHPFunctionData[] rv = new PHPFunctionData[list.size()];
		list.toArray(rv);
		return rv;
	}

	public CodeData[] getFunctions(String startsWith) {
		return ModelSupport.getCodeDataStartingWith(getFunctions(), startsWith);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public CodeData[] getClasses() {
		List list = classesDB.asList();
		PHPClassData[] rv = new PHPClassData[list.size()];
		list.toArray(rv);
		return rv;
	}

	public PHPClassData getClass(String fileName, String className) {
		List classes = classesDB.getCodeData(className);
		if (classes == null || classes.size() == 0) {
			return null;
		}
		for (int i = 0; i < classes.size(); i++) {
			PHPClassData curr = (PHPClassData) classes.get(i);
			if (curr.getUserData().getFileName().equals(fileName)) {
				return curr;
			}
		}
		return (PHPClassData) classes.get(0);
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
				rv = PHPCodeDataFactory.EMPTY_CODE_DATA_ARRAY;
			}
			return ModelSupport.getCodeDataStartingWith(rv, startsWith);
		}

		List list = globalsVariablesDB.asList();
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
			rv = PHPCodeDataFactory.EMPTY_CODE_DATA_ARRAY;
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

		String superClassName = (classData.getSuperClassData() != null) ? classData.getSuperClassData().getName() : null;
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
		Iterator iterator = phpFileDataDB.asList().iterator();
		while (iterator.hasNext()) {
			fileData = (PHPFileData) iterator.next();
			if (fileData.getName().equals(fileName)) {
				continue; // no point in looking again...
			}
			variablesTypeManager = fileData.getVariableTypeManager();

			typeData = variablesTypeManager.getVariableTypeData(context, variableName, fileData.getUserData().getStopLine());
			if (typeData != null) {
				return typeData.getType();
			}
		}
		return null;
	}

	public PHPConstantData getConstantData(String constantName) {
		List list = constansDB.getCodeData(constantName);
		if (list != null && list.size() > 0) {
			return (PHPConstantData) list.get(0);
		}
		return null;
	}

	public CodeData[] getConstants() {
		List list = constansDB.asList();
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

	public void addModelListener(ModelListener l) {
		if (!listeners.contains(l))
			listeners.add(l);
	}

	public List getModelListenerList() {
		return this.listeners;
	}

	public void removeModelListener(ModelListener l) {
		listeners.remove(l);
	}

	private void fireFileDataChanged(PHPFileData fileData) {
		int size = listeners.size();
		for (int i = 0; i < size; i++) {
			ModelListener curr = (ModelListener) listeners.get(i);
			curr.fileDataChanged(fileData);
		}
	}

	private void fireFileDataRemoved(PHPFileData fileData) {
		int size = listeners.size();
		for (int i = 0; i < size; i++) {
			ModelListener curr = (ModelListener) listeners.get(i);
			curr.fileDataRemoved(fileData);
		}
	}

	private void fireFileDataAdded(PHPFileData fileData) {
		int size = listeners.size();
		for (int i = 0; i < size; i++) {
			ModelListener curr = (ModelListener) listeners.get(i);
			curr.fileDataAdded(fileData);
		}
	}

	private void fireDataCleared() {
		int size = listeners.size();
		for (int i = 0; i < size; i++) {
			ModelListener curr = (ModelListener) listeners.get(i);
			curr.dataCleared();
		}
	}

	public void clean() {
		this.clear();
	}

	public void dispose() {
		//		GlobalParsingManager.getInstance().removeParserClient(parserClient, project);
		manager.dispose();
	}

	public void initialize(IProject project) {
		manager = new PHPUserModelManager(project, this);
	}

	public void fileAdded(IFile file) {
	}

	public void fileRemoved(IFile file) {
		manager.fileRemoved(file);
	}

	public void fileChanged(IFile file, IStructuredDocument sDocument) {
	}
}