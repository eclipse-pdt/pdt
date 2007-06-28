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


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.eclipse.php.internal.core.phpModel.phpElementData.PHPVariableData;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPVariableTypeData;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPVariablesTypeManager;

public class VariableContextBuilder {

	private Map contextsToVariables;
	private Map variablesInstansiation;

	public VariableContextBuilder() {
		contextsToVariables = new HashMap();
		variablesInstansiation = new HashMap();
	}

	/**
	 * If the variable name exists in the model return the variable with the same name,
	 * otherwise create a new variable, add it to the model and return it.
	 */
	public PHPVariableData addVariable(PHPCodeContext context, String variableName) {
		variableName = removeDollar(variableName);
		PHPVariableData variable = getVariable(context, variableName);
		if (variable != null) {
			return variable;
		}

		List list = (List) contextsToVariables.get(context);
		if (list == null) {
			list = new ArrayList(6);
			contextsToVariables.put(context, list);
		}
		variable = PHPCodeDataFactory.createPHPVariableData(variableName, null, null);
		list.add(variable);
		return variable;
	}

	/**
	 * If a variable with the same name doesn't exists in the model add it,
	 * otherwise don�t do nothing
	 */
	public void addVariable(PHPCodeContext context, PHPVariableData variable) {
		if (getVariable(context, variable.getName()) != null) {
			return;
		}
		List list = (List) contextsToVariables.get(context);
		if (list == null) {
			list = new ArrayList(6);
			contextsToVariables.put(context, list);
		}
		list.add(variable);
	}

	public void addObjectInstantiation(PHPCodeContext context, String variableName, String objectType, boolean isUserDocumentation, int lineNumber, int position) {
		variableName = removeDollar(variableName);
		Object variableContext = createVariableContext(variableName, context);
		List list = (List) variablesInstansiation.get(variableContext);
		if (list == null) {
			list = new ArrayList(4);
			variablesInstansiation.put(variableContext, list);
		}
		list.add(new VariableTypeDataImp(objectType, lineNumber, position, isUserDocumentation));
	}

	public PHPVariablesTypeManager getPHPVariablesTypeManager() {
		final Map vars = new HashMap(contextsToVariables.size());
		final Iterator iterator = contextsToVariables.entrySet().iterator();
		
		while (iterator.hasNext()) {
			final Map.Entry next = (Entry) iterator.next();
			Object key = next.getKey();
			List contextList = (List) next.getValue();
			PHPVariableData[] contextVars = new PHPVariableData[contextList.size()];
			contextList.toArray(contextVars);
			Arrays.sort(contextVars);
			vars.put(key, contextVars);
		}
		return new PHPVariablesTypeManagerImp(vars, variablesInstansiation);
	}

	private PHPVariableData getVariable(PHPCodeContext context, String variableName) {
		variableName = removeDollar(variableName);
		List list = (List) contextsToVariables.get(context);
		if (list == null) {
			return null;
		}
		Iterator iterator = list.iterator();
		while (iterator.hasNext()) {
			PHPVariableData curr = (PHPVariableData) iterator.next();
			if (curr.getName().equals(variableName)) {
				return curr;
			}
		}
		return null;
	}

	private static String createVariableContext(String variabletName, PHPCodeContext context) {
		StringBuffer buffer = new StringBuffer(16);
		buffer.append(variabletName);
		buffer.append(';');
		buffer.append(context.getContainerClassName());
		buffer.append(';');
		buffer.append(context.getContainerFunctionName());
		return buffer.toString();
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////

	private static class PHPVariablesTypeManagerImp implements PHPVariablesTypeManager, Serializable {

		private Map contextsToVariables;
		private Map variablesInstansiation;

		private PHPVariablesTypeManagerImp(Map contextsToVariables, Map variablesInstansiation) {
			this.contextsToVariables = contextsToVariables;
			this.variablesInstansiation = variablesInstansiation;
		}

		public PHPVariableData[] getVariables(PHPCodeContext context) {
			return (PHPVariableData[]) contextsToVariables.get(context);
		}

		public PHPVariableData getVariable(PHPCodeContext context, String variableName) {
			variableName = removeDollar(variableName);
			PHPVariableData[] variables = (PHPVariableData[]) contextsToVariables.get(context);
			if (variables == null) {
				return null;
			}
			for (int i = 0; i < variables.length; i++) {
				if (variables[i].getName().equalsIgnoreCase(variableName)) {
					return variables[i];
				}
			}
			return null;
		}

		public Map getContextsToVariables() {
			return contextsToVariables;
		}

		public Map getVariablesInstansiation() {
			return variablesInstansiation;
		}

		public PHPVariableTypeData getVariableTypeData(PHPCodeContext context, String variableName, int line) {
			variableName = removeDollar(variableName);
			Object variablrContext = createVariableContext(variableName, context);
			List list = (List) variablesInstansiation.get(variablrContext);
			if (list == null) {
				return null;
			}
			PHPVariableTypeData lastKnowenData = null;
			for (int i = list.size() - 1; i >= 0; i -= 1) {
				PHPVariableTypeData curr = (PHPVariableTypeData) list.get(i);
				if (curr.getLine() <= line) {
					if (lastKnowenData == null) {
						if (curr.getType() != null) {
							return curr;
						}
						lastKnowenData = curr;
					} else {
						if (curr.getType() != null) {
							if (curr.isUserDocumentation()) {
								return curr;
							}
							return lastKnowenData;
						}
					}
				}
			}
			return lastKnowenData;
		}

		public PHPVariableTypeData getVariableTypeDataByPosition(PHPCodeContext context, String variableName, int position) {
			variableName = removeDollar(variableName);
			Object variablrContext = createVariableContext(variableName, context);
			List list = (List) variablesInstansiation.get(variablrContext);
			if (list == null) {
				return null;
			}
			PHPVariableTypeData lastKnowenData = null;
			for (int i = list.size() - 1; i >= 0; i -= 1) {
				PHPVariableTypeData curr = (PHPVariableTypeData) list.get(i);
				if (curr.getPosition() <= position) {
					if (lastKnowenData == null) {
						if (curr.getType() != null) {
							return curr;
						}
						lastKnowenData = curr;
					} else {
						if (curr.getType() != null) {
							if (curr.isUserDocumentation()) {
								return curr;
							}
							return lastKnowenData;
						}
					}
				}
			}
			return lastKnowenData;
		}
	}

	private static String removeDollar(String variableName) {
		if (variableName.length() > 0 && variableName.charAt(0) == '$') {
			variableName = variableName.substring(1);
		}
		return variableName;
	}

	public static VariableTypeDataImp createVariableTypeData(String type, int line, int position, boolean isUserDocumentation) {
		return new VariableTypeDataImp(type, line, position, isUserDocumentation);
	}

	public static PHPVariablesTypeManagerImp createPHPVariablesTypeManager(Map contextsToVariables, Map variablesInstansiation) {
		return new PHPVariablesTypeManagerImp(contextsToVariables, variablesInstansiation);
	}

	private static final class VariableTypeDataImp implements PHPVariableTypeData {

		private String type;
		private int line;
		private int position;
		private boolean isUserDocumentation;

		VariableTypeDataImp(String type, int line, int position, boolean isUserDocumentation) {
			this.type = type;
			this.line = line;
			this.position = position;
			this.isUserDocumentation = isUserDocumentation;
		}

		public String getType() {
			return type;
		}

		public int getLine() {
			return line;
		}

		public int getPosition() {
			return position;
		}

		public boolean isUserDocumentation() {
			return isUserDocumentation;
		}

	}
}