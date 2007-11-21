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

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.*;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.phpModel.IPHPLanguageModel;
import org.eclipse.php.internal.core.phpModel.phpElementData.*;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPFunctionData.PHPFunctionParameter;

public abstract class PHPLanguageModel implements IPHPLanguageModel {

	protected PHPFunctionData[] functions = PHPCodeDataFactory.EMPTY_FUNCTIONS_DATA_ARRAY;

	protected Map functionsHash = new HashMap(3000);

	protected PHPClassData[] classes = PHPCodeDataFactory.EMPTY_CLASS_DATA_ARRAY;

	protected Map classesHash = new HashMap(10);

	protected PHPConstantData[] constants = PHPCodeDataFactory.EMPTY_CONSTANT_DATA_ARRAY;

	protected IPHPMarker[] markers = PHPCodeDataFactory.EMPTY_MARKERS_DATA_ARRAY;

	protected PHPVariableData[] phpVariables;

	protected PHPVariableData[] serverVariables;

	protected PHPVariableData[] sessionVariables;

	protected PHPVariableData[] classVariables;

	public PHPLanguageModel(PHPLanguageManager languageManager) {
		loadFile(languageManager);
		initVariables();
	}

	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public PHPVariableData[] getPHPVariables() {
		return phpVariables;
	}

	public PHPVariableData[] getServerVariables() {
		return serverVariables;
	}

	public PHPVariableData[] getSessionVariables() {
		return sessionVariables;
	}

	public PHPVariableData[] getClassVariables() {
		return classVariables;
	}

	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public CodeData[] getFunctions() {
		return functions;
	}

	public CodeData[] getFunction(String functionName) {
		PHPFunctionData function = getFunction(null, functionName);
		if (function == null) {
			return PHPCodeDataFactory.EMPTY_CODE_DATA_ARRAY;
		}
		return new PHPFunctionData[] { function };
	}

	public PHPFunctionData getFunction(String fileName, String functionName) {
		return (PHPFunctionData) functionsHash.get(functionName);
	}

	public CodeData[] getFunctions(String startsWith) {
		return ModelSupport.getCodeDataStartingWith(getFunctions(), startsWith);
	}

	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public CodeData[] getClasses() {
		return classes;
	}

	public CodeData[] getClasses(String startsWith) {
		return ModelSupport.getCodeDataStartingWith(classes, startsWith);
	}

	public CodeData[] getConstants() {
		return constants;
	}

	public CodeData[] getConstants(String startsWith, boolean caseSensitive) {
		return caseSensitive ? ModelSupport.getCodeDataStartingWithCS(getConstants(), startsWith) : ModelSupport.getCodeDataStartingWith(getConstants(), startsWith);
	}

	public PHPConstantData getConstant(String fileName, String constantName) {
		return null;
	}

	// ////////////////////////////////////////////////////////////////////////////////////////

	public CodeData[] getFileDatas() {
		return null;
	}

	public CodeData[] getNonPHPFiles(String startsWith) {
		return null;
	}

	public PHPFileData getFileData(String fileName) {
		return null;
	}

	public PHPClassData getClass(String fileName, String className) {
		return (PHPClassData) classesHash.get(getNormalizedString(className));
	}

	public CodeData[] getClass(String className) {
		PHPClassData classs = getClass(null, className);
		if (classs == null) {
			return PHPCodeDataFactory.EMPTY_CODE_DATA_ARRAY;
		}
		return new CodeData[] { classs };
	}

	public CodeData[] getGlobalVariables(String fileName, String startsWith, boolean showVariablesFromOtherFiles) {
		return phpVariables;
	}

	public CodeData[] getVariables(String fileName, PHPCodeContext context, String startsWith, boolean showVariablesFromOtherFiles) {
		String className = context.getContainerClassName();
		if (className == null || className.equals("")) {
			return phpVariables;
		}
		return ModelSupport.merge(phpVariables, classVariables);
	}

	public String getVariableType(String fileName, PHPCodeContext context, String variableName, int line, boolean showObjectsFromOtherFiles) {
		return null;
	}

	public CodeData[] getConstant(String constantName) {
		return null;
	}

	public IPHPMarker[] getMarkers() {
		return markers;
	}

	public void clear() {
	}

	public void dispose() {
	}

	public void initialize(IProject project) {
	}

	//////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a trimmed string changed to lower case
	 */
	private final String getNormalizedString(final String name) {
		return name.trim().toLowerCase();
	}

	private void initVariables() {

		phpVariables = new PHPVariableData[] { PHPCodeDataFactory.createPHPVariableData("_GET", null, null), PHPCodeDataFactory.createPHPVariableData("_POST", null, null), PHPCodeDataFactory.createPHPVariableData("_COOKIE", null, null),
			PHPCodeDataFactory.createPHPVariableData("_SESSION", null, null), PHPCodeDataFactory.createPHPVariableData("_SERVER", null, null), PHPCodeDataFactory.createPHPVariableData("_ENV", null, null), PHPCodeDataFactory.createPHPVariableData("_REQUEST", null, null),
			PHPCodeDataFactory.createPHPVariableData("_FILES", null, null), PHPCodeDataFactory.createPHPVariableData("GLOBALS", null, null), PHPCodeDataFactory.createPHPVariableData("HTTP_GET_VARS", null, null), PHPCodeDataFactory.createPHPVariableData("HTTP_POST_VARS", null, null),
			PHPCodeDataFactory.createPHPVariableData("HTTP_COOKIE_VARS", null, null), PHPCodeDataFactory.createPHPVariableData("HTTP_SESSION_VARS", null, null), PHPCodeDataFactory.createPHPVariableData("HTTP_SERVER_VARS", null, null),
			PHPCodeDataFactory.createPHPVariableData("HTTP_ENV_VARS", null, null), PHPCodeDataFactory.createPHPVariableData("HTTP_POST_FILES", null, null) };
		Arrays.sort(phpVariables);

		serverVariables = new PHPVariableData[] { PHPCodeDataFactory.createPHPVariableData("DOCUMENT_ROOT", null, null), PHPCodeDataFactory.createPHPVariableData("HTTP_ACCEPT", null, null), PHPCodeDataFactory.createPHPVariableData("HTTP_ACCEPT_ENCODING", null, null),
			PHPCodeDataFactory.createPHPVariableData("HTTP_ACCEPT_LANGUAGE", null, null), PHPCodeDataFactory.createPHPVariableData("HTTP_CONNECTION", null, null), PHPCodeDataFactory.createPHPVariableData("HTTP_HOST", null, null),
			PHPCodeDataFactory.createPHPVariableData("HTTP_USER_AGENT", null, null), PHPCodeDataFactory.createPHPVariableData("REMOTE_ADDR", null, null), PHPCodeDataFactory.createPHPVariableData("SCRIPT_FILENAME", null, null), PHPCodeDataFactory.createPHPVariableData("SERVER_NAME", null, null),
			PHPCodeDataFactory.createPHPVariableData("GATEWAY_INTERFACE", null, null), PHPCodeDataFactory.createPHPVariableData("REQUEST_METHOD", null, null), PHPCodeDataFactory.createPHPVariableData("QUERY_STRING", null, null), PHPCodeDataFactory.createPHPVariableData("REQUEST_URI", null, null),
			PHPCodeDataFactory.createPHPVariableData("SCRIPT_NAME", null, null), PHPCodeDataFactory.createPHPVariableData("PHP_SELF", null, null), PHPCodeDataFactory.createPHPVariableData("PATH", null, null), PHPCodeDataFactory.createPHPVariableData("REMOTE_PORT", null, null),
			PHPCodeDataFactory.createPHPVariableData("SERVER_ADDR", null, null), PHPCodeDataFactory.createPHPVariableData("SERVER_ADMIN", null, null), PHPCodeDataFactory.createPHPVariableData("SERVER_PORT", null, null), PHPCodeDataFactory.createPHPVariableData("SERVER_SIGNATURE", null, null),
			PHPCodeDataFactory.createPHPVariableData("SERVER_SOFTWARE", null, null), PHPCodeDataFactory.createPHPVariableData("SERVER_PROTOCOL", null, null), PHPCodeDataFactory.createPHPVariableData("PATH_TRANSLATED", null, null), };
		Arrays.sort(serverVariables);

		classVariables = new PHPVariableData[] { PHPCodeDataFactory.createPHPVariableData("this", null, null) };
		Arrays.sort(classVariables);

		sessionVariables = new PHPVariableData[] { PHPCodeDataFactory.createPHPVariableData("SID", null, null) };

	}

	// ////////////////////////////////////////////////////////////////////////////////////////

	// ////////////////////////////////////////////////////////////////////////////////////////

	protected void loadFile(PHPLanguageManager languageManager) {
		try {
			final PHPParserManager phpParserManager = languageManager.createPHPParserManager();

			// parse the specific language model
			String phpFunctionPath = languageManager.getPHPFunctionPath();
			Reader reader = new InputStreamReader(FileLocator.openStream(PHPCorePlugin.getDefault().getBundle(), new Path(phpFunctionPath), false));
			ParserClient innerParserClient = new InnerParserClient();

			ParserExecuter executer = new ParserExecuter(phpParserManager, null, innerParserClient, phpFunctionPath, reader, new Pattern[0], 0, false);
			executer.run();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	protected class InnerParserClient implements ParserClient {

		private String className = "";

		private List functionsList = new ArrayList(3000);

		private List classConstsList = new ArrayList();

		private List classVarsList = new ArrayList();

		private List classFunctionsList = new ArrayList();

		private List classesList = new ArrayList();

		private List constansList = new ArrayList(2000);

		private List functionParametersList = new ArrayList();

		public void dispose() {
			functionsList.clear();
			functionsList = null;

			classConstsList.clear();
			classConstsList = null;

			classVarsList.clear();
			classVarsList = null;

			classFunctionsList.clear();
			classFunctionsList = null;

			classesList.clear();
			classesList = null;

			constansList.clear();
			constansList = null;

			functionParametersList.clear();
			functionParametersList = null;
		}

		public void handleFunctionParameter(String classType, String variableName, boolean isReference, boolean isConst, String defaultValue, int startPosition, int endPosition, int stopPosition, int lineNumber) {
			variableName = variableName.substring(1);
			functionParametersList.add(PHPCodeDataFactory.createPHPFunctionParameter(variableName, null, isReference, isConst, classType, defaultValue));
		}

		public void hadleClassDeclarationStarts(String className, int startPosition) {
			this.className = className;
		}

		private PHPFunctionData.PHPFunctionParameter getParameter(PHPFunctionData.PHPFunctionParameter[] parameters, String parameterName) {
			if (parameterName == null || parameterName.length() == 0) {
				return null;
			}
			if (parameterName.charAt(0) == '&') {
				parameterName = parameterName.substring(1);
			}
			if (parameterName.charAt(0) == '$') {
				parameterName = parameterName.substring(1);
			}
			for (PHPFunctionParameter element : parameters) {
				if (parameterName.equalsIgnoreCase(element.getName())) {
					return element;
				}
			}
			return null;
		}

		public void handleFunctionDeclarationStarts(String functionName) {
		}

		public void handleFunctionDeclaration(String functionName, boolean isClassFunction, int modifier, PHPDocBlock docInfo, int startPosition, int stopPosition, int lineNumber) {
			PHPFunctionData.PHPFunctionParameter[] parameters = new PHPFunctionData.PHPFunctionParameter[functionParametersList.size()];
			functionParametersList.toArray(parameters);
			functionParametersList.clear();

			String returnType = null;

			if (docInfo != null) {
				Iterator it = docInfo.getTags(PHPDocTag.PARAM);
				while (it.hasNext()) {
					PHPDocTag param = (PHPDocTag) it.next();
					String arg = param.getValue().trim();
					String[] values = arg.split(" ");
					String name = null;
					String type = null;

					int length = values.length > 2 ? 2 : values.length;
					for (int i = 0; i < length; i++) {
						if (values[i].startsWith("$")) {
							name = values[i];
						} else {
							type = values[i];
						}
					}
					if (name == null) {
						name = values[0];
					}
					PHPFunctionData.PHPFunctionParameter parameter = getParameter(parameters, name);

					if (parameter == null) {
						type = values[0];
						name = values.length > 1 ? values[1] : null;
						parameter = getParameter(parameters, type);
					}
					// update parameter.
					if (parameter != null && type != null && type.length() > 0) {
						String originalClassType = parameter.getClassType();
						if (originalClassType == null || originalClassType.length() == 0) {
							parameter.setClassType(type);
						}
					}
				}
				Iterator returnIt = docInfo.getTags(PHPDocTag.RETURN);
				returnType = returnIt.hasNext() ? (String) ((PHPDocTag) returnIt.next()).getValue() : null;
			}

			if (returnType == null) {
				if (isClassFunction && functionName.equals(className)) {
					returnType = className;
				} else {
					returnType = "void";
				}
			}

			PHPFunctionData functionData = PHPCodeDataFactory.createPHPFuctionData(functionName, modifier, docInfo, null, parameters, returnType);
			if (isClassFunction) {
				classFunctionsList.add(functionData);
			} else {
				functionsList.add(functionData);
			}
		}

		public void handleFunctionDeclarationEnds(String functionName, boolean isClassFunction, int endPosition) {
		}

		public void handleClassDeclaration(String className, int modifier, String superClassName, String interfacesNames, PHPDocBlock docInfo, int startPosition, int stopPosition, int lineNumber) {
			PHPClassData.PHPSuperClassNameData superClassData;
			if (superClassName != null) {
				int index = superClassName.indexOf(']');
				superClassName = superClassName.substring(index + 1);
				superClassData = PHPCodeDataFactory.createPHPSuperClassNameData(superClassName, null);
			} else {
				superClassData = PHPCodeDataFactory.createPHPSuperClassNameData(superClassName, null);
			}

			PHPClassData.PHPInterfaceNameData[] interfaces;
			if (interfacesNames != null) {
				String[] interfacesNamesArray = interfacesNames.split(",");
				interfaces = new PHPClassData.PHPInterfaceNameData[interfacesNamesArray.length];
				for (int i = 0; i < interfacesNamesArray.length; i++) {
					String interfaceName = interfacesNamesArray[i];
					int index = interfaceName.indexOf(']');
					interfaceName = interfaceName.substring(index + 1);
					interfaces[i] = PHPCodeDataFactory.createPHPInterfaceNameData(interfaceName, null);
				}
			} else {
				interfaces = PHPCodeDataFactory.EMPTY_INTERFACES_DATA_ARRAY;
			}

			PHPClassData classData = PHPCodeDataFactory.createPHPClassData(className, modifier, docInfo, null, superClassData, interfaces, PHPCodeDataFactory.EMPTY_CLASS_VAR_DATA_ARRAY, PHPCodeDataFactory.EMPTY_CLASS_CONST_DATA_ARRAY, PHPCodeDataFactory.EMPTY_FUNCTIONS_DATA_ARRAY);

			classesList.add(classData);
		}

		public void handleClassDeclarationEnds(String className, int endPosition) {
			if (classesList.size() > 0) {
				PHPCodeDataFactory.PHPClassDataImp classData = (PHPCodeDataFactory.PHPClassDataImp) classesList.get(classesList.size() - 1);
				if (classData.getName().equals(className)) {
					PHPClassConstData[] consts = new PHPClassConstData[classConstsList.size()];
					classConstsList.toArray(consts);
					Arrays.sort(consts);

					PHPClassVarData[] vars = new PHPClassVarData[classVarsList.size()];
					classVarsList.toArray(vars);
					Arrays.sort(vars);

					PHPFunctionData[] func = new PHPFunctionData[classFunctionsList.size()];
					classFunctionsList.toArray(func);
					Arrays.sort(func);

					for (int i = 0; i < consts.length; ++i) {
						((PHPCodeDataFactory.PHPClassConstDataImp) consts[i]).setContainer(classData);
					}
					for (PHPClassVarData element : vars) {
						((PHPCodeDataFactory.PHPClassVarDataImp) element).setContainer(classData);
					}
					for (PHPFunctionData element : func) {
						((PHPCodeDataFactory.PHPFunctionDataImp) element).setContainer(classData);
					}

					classData.setConsts(consts);
					classData.setFunctions(func);
					classData.setVars(vars);
				}
			}

			classConstsList.clear();
			classVarsList.clear();
			classFunctionsList.clear();

			this.className = "";
		}

		public void handleClassVariablesDeclaration(String variables, int modifier, PHPDocBlock docInfo, int startPosition, int endPosition, int stopPosition) {
			String classType = null;
			if (docInfo != null) {
				Iterator it = docInfo.getTags(PHPDocTag.VAR);
				while (it.hasNext()) {
					PHPDocTag varTag = (PHPDocTag) it.next();
					String value = varTag.getValue().trim();
					String[] values = value.split(" ");
					classType = values[0];
				}
			}
			StringTokenizer t = new StringTokenizer(variables, ",", false);
			while (t.hasMoreTokens()) {
				String var = t.nextToken().substring(1);
				PHPClassVarData classVarData = PHPCodeDataFactory.createPHPClassVarData(var, modifier, classType, docInfo, null);
				handleObjectInstansiation(var, classType, null, 0, 0, false);
				classVarsList.add(classVarData);
			}
		}

		public void handleClassConstDeclaration(String constName, String value, PHPDocBlock docInfo, int startPosition, int endPosition, int stopPosition) {
			PHPClassConstData classConstData = PHPCodeDataFactory.createPHPClassConstData(constName, value, docInfo, null);
			classConstsList.add(classConstData);
		}

		public void handleIncludedFile(String includingType, String includeFileName, PHPDocBlock docInfo, int startPosition, int endPosition, int stopPosition, int lineNumber) {
		}

		public void haveReturnValue() {
		}

		public void handleObjectInstansiation(String variableName, String className, String ctorArrguments, int line, int startPosition, boolean isUserDocumentation) {
		}

		public void handleVariableName(String variableName, int line) {
		}

		public void handleGlobalVar(String variableName) {
		}

		public void handleStaticVar(String variableName) {
		}

		public void startParsing(String fileName) {
		}

		public void finishParsing(int lastPosition, int lastLine, long lastModified) {
			PHPFunctionData[] arrayFunctions = new PHPFunctionData[functionsList.size()];
			functionsList.toArray(arrayFunctions);
			Arrays.sort(arrayFunctions);
			functions = arrayFunctions;
			for (PHPFunctionData element : arrayFunctions) {
				functionsHash.put(element.getName(), element);
			}

			PHPClassData[] arrayClasses = new PHPClassData[classesList.size()];
			classesList.toArray(arrayClasses);
			Arrays.sort(arrayClasses);
			classes = arrayClasses;
			for (PHPClassData element : arrayClasses) {
				classesHash.put(getNormalizedString(element.getName()), element);
			}

			PHPConstantData[] arratConstans = new PHPConstantData[constansList.size()];
			constansList.toArray(arratConstans);
			Arrays.sort(arratConstans);
			constants = arratConstans;
		}

		public void handleDefine(String name, String value, PHPDocBlock docInfo, int startPosition, int endPosition, int stopPosition) {
			if (name.startsWith("\"") || name.startsWith("\'")) {
				name = name.substring(1);
			}
			if (name.endsWith("\"") || name.endsWith("\'")) {
				name = name.substring(0, name.length() - 1);
			}

			constansList.add(PHPCodeDataFactory.createPHPConstantData(name, value, null, docInfo));
		}

		public void handleError(String description, int startPosition, int endPosition, int lineNumber) {
		}

		public void handleSyntaxError(int currToken, String currText, short[] rowOfProbe, int startPosition, int endPosition, int lineNumber) {
		}

		public void handleTask(String taskName, String description, int startPosition, int endPosition, int lineNumber) {
		}

		public void handlePHPStart(int startOffset, int endOffset) {
		}

		public void handlePHPEnd(int startOffset, int endOffset) {
		}

		public void setFirstDocBlock(PHPDocBlock docBlock) {
		}

	}

}
