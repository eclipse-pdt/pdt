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
package org.eclipse.php.internal.core.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.*;

import org.eclipse.php.internal.core.phpModel.parser.ModelSupport;
import org.eclipse.php.internal.core.phpModel.parser.PHPCodeContext;
import org.eclipse.php.internal.core.phpModel.parser.PHPCodeDataFactory;
import org.eclipse.php.internal.core.phpModel.parser.VariableContextBuilder;
import org.eclipse.php.internal.core.phpModel.phpElementData.*;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPClassData.PHPInterfaceNameData;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPFunctionData.PHPFunctionParameter;

public class SerializationUtil {

	private static String currentFileName;

	public static void serialize(ICachable[] datas, DataOutputStream output) throws IOException {
		if (datas != null) {
			output.writeInt(datas.length);
			for (ICachable element : datas) {
				serialize((PHPFileData) element, output);
			}
		} else {
			output.writeInt(0);
		}
	}

	private static void serialize(PHPFileData fileData, DataOutputStream output) throws IOException {
		if (fileData != null) {
			currentFileName = fileData.getName();
			writeString(currentFileName, output);
			serialize(fileData.getUserData(), output);
			serialize(fileData.getClasses(), output);
			serialize(fileData.getFunctions(), output);
			serialize(fileData.getVariableTypeManager(), output);
			serialize(fileData.getIncludeFiles(), output);
			serialize(fileData.getConstants(), output);
			serialize(fileData.getMarkers(), output);
			serialize(fileData.getPHPBlocks(), output);
			serialize(fileData.getDocBlock(), output);
			output.writeLong(fileData.getCreationTimeLastModified());
		}
	}

	private static void serialize(UserData userData, DataOutputStream output) throws IOException {
		if (userData != null) {
			output.writeBoolean(true);
			output.writeInt(userData.getStartPosition());
			output.writeInt(userData.getEndPosition());
			output.writeInt(userData.getStopPosition());
			output.writeInt(userData.getStopLine());
		} else {
			output.writeBoolean(false);
		}
	}

	private static void serialize(PHPClassData[] classes, DataOutputStream output) throws IOException {
		if (classes != null) {
			output.writeInt(classes.length);
			for (PHPClassData element : classes) {
				serialize(element, output);
			}
		}
	}

	private static void serialize(PHPClassData classData, DataOutputStream output) throws IOException {
		if (classData != null) {
			writeString(classData.getName(), output);
			output.writeInt(classData.getModifiers());
			serialize(classData.getDocBlock(), output);
			serialize(classData.getUserData(), output);
			serialize(classData.getSuperClassData(), output);
			serialize(classData.getInterfacesNamesData(), output);
			serialize(classData.getVars(), output);
			serialize(classData.getConsts(), output);
			serialize(classData.getFunctions(), output);
		}
	}

	private static void serialize(PHPClassConstData[] consts, DataOutputStream output) throws IOException {
		if (consts != null) {
			output.writeInt(consts.length);
			for (PHPClassConstData element : consts) {
				serialize(element, output);
			}
		}
	}

	private static void serialize(PHPClassConstData data, DataOutputStream output) throws IOException {
		if (data != null) {
			writeString(data.getName(), output);
			writeString(data.getValue(), output);
			serialize(data.getDocBlock(), output);
			serialize(data.getUserData(), output);
		}
	}

	private static void serialize(PHPClassVarData[] vars, DataOutputStream output) throws IOException {
		if (vars != null) {
			output.writeInt(vars.length);
			for (PHPClassVarData element : vars) {
				serialize(element, output);
			}
		}
	}

	private static void serialize(PHPClassVarData var, DataOutputStream output) throws IOException {
		if (var != null) {
			writeString(var.getName(), output);
			output.writeInt(var.getModifiers());
			writeString(var.getClassType(), output);
			serialize(var.getDocBlock(), output);
			serialize(var.getUserData(), output);
		}
	}

	private static void serialize(PHPClassData.PHPInterfaceNameData[] interfacesNamesData, DataOutputStream output) throws IOException {
		if (interfacesNamesData != null) {
			output.writeInt(interfacesNamesData.length);
			for (PHPInterfaceNameData element : interfacesNamesData) {
				serialize(element, output);
			}
		}
	}

	private static void serialize(PHPClassData.PHPInterfaceNameData phpInterfaceNameData, DataOutputStream output) throws IOException {
		if (phpInterfaceNameData != null) {
			writeString(phpInterfaceNameData.getName(), output);
			serialize(phpInterfaceNameData.getUserData(), output);
		}
	}

	private static void serialize(PHPClassData.PHPSuperClassNameData superClassData, DataOutputStream output) throws IOException {
		if (superClassData != null) {
			writeString(superClassData.getName(), output);
			serialize(superClassData.getUserData(), output);
		}
	}

	private static void serialize(PHPDocBlock docBlock, DataOutputStream output) throws IOException {
		output.writeBoolean(docBlock != null);
		if (docBlock != null) {
			writeString(docBlock.getShortDescription(), output);
			writeString(docBlock.getLongDescription(), output);
			serialize(docBlock.getTagsAsArray(), output);
			output.writeInt(docBlock.getType());
			output.writeInt(docBlock.getStartPosition());
			output.writeInt(docBlock.getEndPosition());
		}
	}

	private static void serialize(PHPConstantData[] constants, DataOutputStream output) throws IOException {
		if (constants != null) {
			output.writeInt(constants.length);
			for (PHPConstantData element : constants) {
				serialize(element, output);
			}
		}
	}

	private static void serialize(PHPConstantData constant, DataOutputStream output) throws IOException {
		if (constant != null) {
			writeString(constant.getName(), output);
			writeString(constant.getValue(), output);
			serialize(constant.getUserData(), output);
			serialize(constant.getDocBlock(), output);
		}
	}

	private static void serialize(PHPDocTag[] tags, DataOutputStream output) throws IOException {
		if (tags != null) {
			output.writeInt(tags.length);
			for (PHPDocTag element : tags) {
				serialize(element, output);
			}
		}
	}

	private static void serialize(PHPDocTag tag, DataOutputStream output) throws IOException {
		if (tag != null) {
			output.writeInt(tag.getID());
			writeString(tag.getValue(), output);
		}
	}

	private static void serialize(PHPBlock[] phpBlocks, DataOutputStream output) throws IOException {
		if (phpBlocks != null) {
			output.writeInt(phpBlocks.length);
			for (PHPBlock element : phpBlocks) {
				serialize(element, output);
			}
		}
	}

	private static void serialize(PHPBlock phpBlock, DataOutputStream output) throws IOException {
		if (phpBlock != null) {
			serialize(phpBlock.getPHPStartTag(), output);
			serialize(phpBlock.getPHPEndTag(), output);
		}
	}

	private static void serialize(IPHPMarker[] markers, DataOutputStream output) throws IOException {
		if (markers != null) {
			output.writeInt(markers.length);
			for (IPHPMarker element : markers) {
				serialize(element, output);
			}
		}
	}

	private static void serialize(IPHPMarker marker, DataOutputStream output) throws IOException {
		if (marker != null) {
			writeString(marker.getDescription(), output);
			writeString(marker.getType(), output);
			if (marker instanceof PHPTask) {
				writeString(((PHPTask) marker).getTaskName(), output);
			}
			serialize(marker.getUserData(), output);
		}
	}

	private static void serialize(PHPVariablesTypeManager variableTypeManager, DataOutputStream output) throws IOException {
		Map contextsToVariables = variableTypeManager.getContextsToVariables();
		Iterator keys = contextsToVariables.keySet().iterator();
		output.writeInt(contextsToVariables.size());
		while (keys.hasNext()) {
			PHPCodeContext key = (PHPCodeContext) keys.next();
			PHPVariableData[] list = (PHPVariableData[]) contextsToVariables.get(key);
			serialize(key, output);
			output.writeInt(list.length);
			for (PHPVariableData element : list) {
				serialize(element, output);
			}
		}

		Map variables = variableTypeManager.getVariablesInstansiation();
		keys = variables.keySet().iterator();
		output.writeInt(variables.size());
		while (keys.hasNext()) {
			String key = (String) keys.next();
			List list = (List) variables.get(key);
			writeString(key, output);
			output.writeInt(list.size());
			for (int i = 0; i < list.size(); i++) {
				PHPVariableTypeData data = (PHPVariableTypeData) list.get(i);
				serialize(data, output);
			}
		}
	}

	private static void serialize(PHPVariableTypeData data, DataOutputStream output) throws IOException {
		//        serialize(data.getVariable(), output);
		writeString(data.getType(), output);
		output.writeInt(data.getLine());
		output.writeInt(data.getPosition());
		output.writeBoolean(data.isUserDocumentation());
	}

	private static void serialize(PHPCodeContext key, DataOutputStream output) throws IOException {
		writeString(key.getContainerClassName(), output);
		writeString(key.getContainerFunctionName(), output);
	}

	private static void serialize(PHPIncludeFileData[] includeFiles, DataOutputStream output) throws IOException {
		if (includeFiles != null) {
			output.writeInt(includeFiles.length);
			for (PHPIncludeFileData element : includeFiles) {
				serialize(element, output);
			}
		}
	}

	private static void serialize(PHPIncludeFileData includeFile, DataOutputStream output) throws IOException {
		if (includeFile != null) {
			writeString(includeFile.getIncludingType(), output);
			serialize((PHPCodeData) includeFile, output);
		}
	}

	private static void serialize(PHPVariableData phpVariableData, DataOutputStream output) throws IOException {
		if (phpVariableData != null) {
			writeString(phpVariableData.getName(), output);
			output.writeBoolean(phpVariableData.isGlobal());
			serialize(phpVariableData.getDocBlock(), output);
			serialize(phpVariableData.getUserData(), output);
		}
	}

	private static void serialize(PHPCodeData phpCodeData, DataOutputStream output) throws IOException {
		if (phpCodeData != null) {
			writeString(phpCodeData.getName(), output);
			serialize(phpCodeData.getDocBlock(), output);
			serialize(phpCodeData.getUserData(), output);
		}
	}

	private static void serialize(PHPFunctionData[] functions, DataOutputStream output) throws IOException {
		if (functions != null) {
			output.writeInt(functions.length);
			for (PHPFunctionData element : functions) {
				serialize(element, output);
			}
		}
	}

	private static void serialize(PHPFunctionData function, DataOutputStream output) throws IOException {
		if (function != null) {
			writeString(function.getName(), output);
			output.writeInt(function.getModifiers());
			serialize(function.getDocBlock(), output);
			serialize(function.getUserData(), output);
			serialize(function.getParameters(), output);
			writeString(function.getReturnType(), output);
		}
	}

	private static void serialize(PHPFunctionData.PHPFunctionParameter[] parameters, DataOutputStream output) throws IOException {
		if (parameters != null) {
			output.writeInt(parameters.length);
			for (PHPFunctionParameter element : parameters) {
				serialize(element, output);
			}
		}
	}

	private static void serialize(PHPFunctionData.PHPFunctionParameter parameter, DataOutputStream output) throws IOException {
		if (parameter != null) {
			writeString(parameter.getName(), output);
			serialize(parameter.getUserData(), output);
			output.writeBoolean(parameter.isReference());
			output.writeBoolean(parameter.isConst());
			writeString(parameter.getClassType(), output);
			writeString(parameter.getDefaultValue(), output);
		}
	}

	//////////////////////////////////////////////////////////////////////////
	////

	public static PHPFileData[] deserializePHPFileDataArray(DataInputStream inputStream) throws IOException {
		int size = inputStream.readInt();
		PHPFileData[] datas = new PHPFileData[size];
		for (int i = 0; i < size; i++) {
			datas[i] = deserializePHPFileData(inputStream);
		}
		return datas;
	}

	private static PHPFileData deserializePHPFileData(DataInputStream inputStream) throws IOException {
		String fileName = readString(inputStream);
		currentFileName = fileName;
		UserData userData = deserializeUserData(inputStream);
		PHPClassData[] classes = deserializeClassDataArray(inputStream);
		PHPFunctionData[] functions = deserializeFunctionDataArray(inputStream);
		PHPVariablesTypeManager variablesTypeManager = deserializeVariableTypeManager(inputStream);
		PHPIncludeFileData[] includeFiles = deserializeIncludeDataArray(inputStream);
		PHPConstantData[] constants = deserializeConstantDataArray(inputStream);
		IPHPMarker[] markers = deserializeMarkersDataArray(inputStream);
		PHPBlock[] phpBlocks = deserializeBlockArray(inputStream);
		PHPDocBlock docBlock = deserializeDocBlock(inputStream);
		long lastModified = inputStream.readLong();
		PHPFileData data = PHPCodeDataFactory.createPHPFileData(fileName, userData, classes, functions, variablesTypeManager, includeFiles, constants, markers, phpBlocks, docBlock, lastModified);
		for (PHPClassData element : classes) {
			element.setContainer(data);
		}
		for (PHPFunctionData element : functions) {
			element.setContainer(data);
		}
		for (PHPIncludeFileData element : includeFiles) {
			element.setContainer(data);
		}
		for (PHPConstantData element : constants) {
			element.setContainer(data);
		}

		return data;
	}

	private static UserData deserializeUserData(DataInputStream input) throws IOException {
		boolean hasUserData = input.readBoolean();
		UserData userData = null;
		if (hasUserData) {
			int startPosition = input.readInt();
			int endPosition = input.readInt();
			int stopPosition = input.readInt();
			int stopLine = input.readInt();
			userData = PHPCodeDataFactory.createUserData(currentFileName, startPosition, endPosition, stopPosition, stopLine);
		}
		return userData;
	}

	private static PHPClassData[] deserializeClassDataArray(DataInputStream inputStream) throws IOException {
		int size = inputStream.readInt();
		PHPClassData[] datas = PHPCodeDataFactory.EMPTY_CLASS_DATA_ARRAY;
		if (size > 0) {
			datas = new PHPClassData[size];
			for (int i = 0; i < datas.length; i++) {
				datas[i] = deserializeClassData(inputStream);
			}
		}
		return datas;
	}

	private static PHPClassData deserializeClassData(DataInputStream inputStream) throws IOException {
		String name = readString(inputStream);
		int modifier = inputStream.readInt();
		PHPDocBlock docBlock = deserializeDocBlock(inputStream);
		UserData userData = deserializeUserData(inputStream);
		PHPClassData.PHPSuperClassNameData superClass = deserializeSuperClass(inputStream);
		PHPClassData.PHPInterfaceNameData[] interfaces = deserializeInterfacesArray(inputStream);
		PHPClassVarData[] vars = deserializeClassVarDataArray(inputStream);
		PHPClassConstData[] consts = deserializeClassConstArray(inputStream);
		PHPFunctionData[] functions = deserializeFunctionDataArray(inputStream);
		PHPClassData rv = PHPCodeDataFactory.createPHPClassData(name, modifier, docBlock, userData, superClass, interfaces, vars, consts, functions);
		for (PHPClassVarData element : vars) {
			element.setContainer(rv);
		}
		for (PHPClassConstData element : consts) {
			element.setContainer(rv);
		}
		for (PHPFunctionData element : functions) {
			element.setContainer(rv);
		}
		return rv;
	}

	private static PHPClassConstData[] deserializeClassConstArray(DataInputStream inputStream) throws IOException {
		int size = inputStream.readInt();
		PHPClassConstData[] datas = PHPCodeDataFactory.EMPTY_CLASS_CONST_DATA_ARRAY;
		if (size > 0) {
			datas = new PHPClassConstData[size];
			for (int i = 0; i < datas.length; i++) {
				datas[i] = deserializeClassConst(inputStream);
			}
		}
		return datas;
	}

	private static PHPClassConstData deserializeClassConst(DataInputStream inputStream) throws IOException {
		String name = readString(inputStream);
		String value = readString(inputStream);
		PHPDocBlock docBlock = deserializeDocBlock(inputStream);
		UserData userData = deserializeUserData(inputStream);
		return PHPCodeDataFactory.createPHPClassConstData(name, value, docBlock, userData);
	}

	private static PHPClassVarData[] deserializeClassVarDataArray(DataInputStream inputStream) throws IOException {
		int size = inputStream.readInt();
		PHPClassVarData[] datas = PHPCodeDataFactory.EMPTY_CLASS_VAR_DATA_ARRAY;
		if (size > 0) {
			datas = new PHPClassVarData[size];
			for (int i = 0; i < datas.length; i++) {
				datas[i] = deserializeClassVarData(inputStream);
			}
		}
		return datas;
	}

	private static PHPClassVarData deserializeClassVarData(DataInputStream inputStream) throws IOException {
		String name = readString(inputStream);
		int modifier = inputStream.readInt();
		String classType = readString(inputStream);
		PHPDocBlock docBlock = deserializeDocBlock(inputStream);
		UserData userData = deserializeUserData(inputStream);
		return PHPCodeDataFactory.createPHPClassVarData(name, modifier, classType, docBlock, userData);
	}

	private static PHPClassData.PHPInterfaceNameData[] deserializeInterfacesArray(DataInputStream inputStream) throws IOException {
		int size = inputStream.readInt();
		PHPClassData.PHPInterfaceNameData[] datas = PHPCodeDataFactory.EMPTY_INTERFACES_DATA_ARRAY;
		if (size > 0) {
			datas = new PHPClassData.PHPInterfaceNameData[size];
			for (int i = 0; i < datas.length; i++) {
				datas[i] = deserializeInterfaces(inputStream);
			}
		}
		return datas;
	}

	private static PHPClassData.PHPInterfaceNameData deserializeInterfaces(DataInputStream inputStream) throws IOException {
		String name = readString(inputStream);
		UserData userData = deserializeUserData(inputStream);
		return PHPCodeDataFactory.createPHPInterfaceNameData(name, userData);
	}

	private static PHPClassData.PHPSuperClassNameData deserializeSuperClass(DataInputStream inputStream) throws IOException {
		String name = readString(inputStream);
		UserData userData = deserializeUserData(inputStream);
		return PHPCodeDataFactory.createPHPSuperClassNameData(name, userData);
	}

	private static PHPDocBlock deserializeDocBlock(DataInputStream inputStream) throws IOException {
		boolean exist = inputStream.readBoolean();
		if (exist) {
			String shortDescription = readString(inputStream);
			String longDescription = readString(inputStream);
			PHPDocTag[] tags = deserializePHPDocTagArray(inputStream);
			int type = inputStream.readInt();
			int startPosition = inputStream.readInt();
			int endPosition = inputStream.readInt();
			PHPDocBlockImp rv = new PHPDocBlockImp(shortDescription, longDescription, tags, type);
			rv.setStartPosition(startPosition);
			rv.setEndPosition(endPosition);
			return rv;
		}
		return null;
	}

	private static PHPBlock[] deserializeBlockArray(DataInputStream inputStream) throws IOException {
		int size = inputStream.readInt();
		PHPBlock[] datas = PHPCodeDataFactory.EMPTY_PHP_BLOCK_ARRAY;
		if (size > 0) {
			datas = new PHPBlock[size];
			for (int i = 0; i < datas.length; i++) {
				datas[i] = deserializeBlock(inputStream);
			}
		}
		return datas;
	}

	private static PHPBlock deserializeBlock(DataInputStream inputStream) throws IOException {
		UserData startTag = deserializeUserData(inputStream);
		UserData endTag = deserializeUserData(inputStream);
		return new PHPBlock(startTag, endTag);
	}

	private static IPHPMarker[] deserializeMarkersDataArray(DataInputStream inputStream) throws IOException {
		int size = inputStream.readInt();
		IPHPMarker[] datas = PHPCodeDataFactory.EMPTY_MARKERS_DATA_ARRAY;
		if (size > 0) {
			datas = new IPHPMarker[size];
			for (int i = 0; i < datas.length; i++) {
				datas[i] = deserializeMarkerData(inputStream);
			}
		}
		return datas;
	}

	private static IPHPMarker deserializeMarkerData(DataInputStream inputStream) throws IOException {
		String description = readString(inputStream);
		String type = readString(inputStream);
		boolean isTask = IPHPMarker.TASK.equals(type);
		String taskName = null;
		if (isTask) {
			taskName = readString(inputStream);
		}
		UserData userData = deserializeUserData(inputStream);
		return isTask ? new PHPTask(taskName, description, userData) : new PHPMarker(type, description, userData);
	}

	private static PHPConstantData[] deserializeConstantDataArray(DataInputStream inputStream) throws IOException {
		int size = inputStream.readInt();
		PHPConstantData[] datas = PHPCodeDataFactory.EMPTY_CONSTANT_DATA_ARRAY;
		if (size > 0) {
			datas = new PHPConstantData[size];
			for (int i = 0; i < datas.length; i++) {
				datas[i] = deserializeConstantData(inputStream);
			}
		}
		return datas;
	}

	private static PHPConstantData deserializeConstantData(DataInputStream inputStream) throws IOException {
		String name = readString(inputStream);
		String value = readString(inputStream);
		UserData userData = deserializeUserData(inputStream);
		PHPDocBlock docBlock = deserializeDocBlock(inputStream);
		return PHPCodeDataFactory.createPHPConstantData(name, value, userData, docBlock);
	}

	private static PHPIncludeFileData[] deserializeIncludeDataArray(DataInputStream inputStream) throws IOException {
		int size = inputStream.readInt();
		PHPIncludeFileData[] datas = PHPCodeDataFactory.EMPTY_INCLUDE_DATA_ARRAY;
		if (size > 0) {
			datas = new PHPIncludeFileData[size];
			for (int i = 0; i < datas.length; i++) {
				datas[i] = deserializeIncludeData(inputStream);
			}
		}
		return datas;
	}

	private static PHPIncludeFileData deserializeIncludeData(DataInputStream inputStream) throws IOException {
		String includingType = readString(inputStream);
		String name = readString(inputStream);
		PHPDocBlock docBlock = deserializeDocBlock(inputStream);
		UserData userData = deserializeUserData(inputStream);
		return PHPCodeDataFactory.createPHPIncludeFileData(includingType, name, docBlock, userData);
	}

	private static PHPVariablesTypeManager deserializeVariableTypeManager(DataInputStream inputStream) throws IOException {
		int size = inputStream.readInt();
		Map/*<PHPCodeContext,PHPVariableData[]>*/contextsToVariables = new HashMap/*<PHPCodeContext,PHPVariableData[]>*/(size);
		for (int i = 0; i < size; i++) {
			PHPCodeContext codeContext = deserializeCodeContext(inputStream);
			PHPVariableData[] list = deserializePHPVariableDataArray(inputStream);
			contextsToVariables.put(codeContext, list);
		}

		size = inputStream.readInt();
		Map/*<String,List<PHPVariableTypeData>>*/variables = new HashMap/*<String,List<PHPVariableTypeData>>*/(size);
		for (int i = 0; i < size; i++) {
			String key = readString(inputStream);
			int listSize = inputStream.readInt();
			List/*<PHPVariableTypeData>*/list = new ArrayList/*<PHPVariableTypeData>*/(listSize);
			for (int j = 0; j < listSize; j++) {
				list.add(deserializePHPVariableTypeData(inputStream));
			}
			variables.put(key, list);
		}
		if(variables.isEmpty()){
			return PHPCodeDataFactory.EMPTY_PHP_VARIABLES_TYPE_MANAGER;
		}
		return VariableContextBuilder.createPHPVariablesTypeManager(contextsToVariables, variables);
	}

	private static PHPVariableTypeData deserializePHPVariableTypeData(DataInputStream inputStream) throws IOException {
		String type = readString(inputStream);
		int line = inputStream.readInt();
		int position = inputStream.readInt();
		boolean userDocumentation = inputStream.readBoolean();
		return VariableContextBuilder.createVariableTypeData(type, line, position, userDocumentation);
	}

	private static PHPVariableData[] deserializePHPVariableDataArray(DataInputStream inputStream) throws IOException {
		PHPVariableData[] datas = new PHPVariableData[inputStream.readInt()];
		for (int i = 0; i < datas.length; i++) {
			datas[i] = deserializePHPVariableData(inputStream);
		}
		return datas;
	}

	private static PHPVariableData deserializePHPVariableData(DataInputStream inputStream) throws IOException {
		String name = readString(inputStream);
		boolean isGlobal = inputStream.readBoolean();
		PHPDocBlock docBlock = deserializeDocBlock(inputStream);
		UserData userData = deserializeUserData(inputStream);
		return PHPCodeDataFactory.createPHPVariableData(name, isGlobal, docBlock, userData);
	}

	private static PHPCodeContext deserializeCodeContext(DataInputStream inputStream) throws IOException {
		String className = readString(inputStream);
		String functionName = readString(inputStream);
		return ModelSupport.createContext(className, functionName);
	}

	private static PHPFunctionData[] deserializeFunctionDataArray(DataInputStream inputStream) throws IOException {
		int size = inputStream.readInt();
		PHPFunctionData[] datas = PHPCodeDataFactory.EMPTY_FUNCTIONS_DATA_ARRAY;
		if (size > 0) {
			datas = new PHPFunctionData[size];
			for (int i = 0; i < datas.length; i++) {
				datas[i] = deserializeFunctionData(inputStream);
			}
		}
		return datas;
	}

	private static PHPFunctionData deserializeFunctionData(DataInputStream inputStream) throws IOException {
		String name = readString(inputStream);
		int modifier = inputStream.readInt();
		PHPDocBlock docBlock = deserializeDocBlock(inputStream);
		UserData userData = deserializeUserData(inputStream);
		PHPFunctionData.PHPFunctionParameter[] parameter = deserializeFunctionsParameterArray(inputStream);
		String returnType = readString(inputStream);
		PHPFunctionData rv = PHPCodeDataFactory.createPHPFuctionData(name, modifier, docBlock, userData, parameter, returnType);
		for (PHPFunctionParameter element : parameter) {
			element.setContainer(rv);
		}
		return rv;
	}

	private static PHPFunctionData.PHPFunctionParameter[] deserializeFunctionsParameterArray(DataInputStream inputStream) throws IOException {
		int size = inputStream.readInt();
		PHPFunctionData.PHPFunctionParameter[] datas = PHPCodeDataFactory.EMPTY_FUNCTION_PARAMETER_DATA_ARRAY;
		if (size > 0) {
			datas = new PHPFunctionData.PHPFunctionParameter[size];
			for (int i = 0; i < datas.length; i++) {
				datas[i] = deserializeFunctionsParameter(inputStream);
			}
		}
		return datas;
	}

	private static PHPFunctionData.PHPFunctionParameter deserializeFunctionsParameter(DataInputStream inputStream) throws IOException {
		String name = readString(inputStream);
		UserData userData = deserializeUserData(inputStream);
		boolean isReference = inputStream.readBoolean();
		boolean isConst = inputStream.readBoolean();
		String classType = readString(inputStream);
		String defaultValue = readString(inputStream);
		return PHPCodeDataFactory.createPHPFunctionParameter(name, userData, isReference, isConst, classType, defaultValue);
	}

	private static PHPDocTag[] deserializePHPDocTagArray(DataInputStream input) throws IOException {
		int size = input.readInt();
		PHPDocTag[] datas = PHPCodeDataFactory.EMPTY_PHP_DOC_TAG;
		if (size > 0) {
			datas = new PHPDocTag[size];
			for (int i = 0; i < datas.length; i++) {
				datas[i] = deserializePHPDocTag(input);
			}
		}
		return datas;
	}

	private static PHPDocTag deserializePHPDocTag(DataInputStream input) throws IOException {
		PHPDocTag tag = new BasicPHPDocTag(input.readInt(), readString(input));
		return tag;
	}

	private static String readString(DataInputStream in) throws IOException {
		boolean exist = in.readBoolean();
		if (exist) {
			return in.readUTF();
		}
		return null;
	}

	private static void writeString(String str, DataOutputStream out) throws IOException {
		out.writeBoolean(str != null);
		if (str != null) {
			out.writeUTF(str);
		}
	}
}
