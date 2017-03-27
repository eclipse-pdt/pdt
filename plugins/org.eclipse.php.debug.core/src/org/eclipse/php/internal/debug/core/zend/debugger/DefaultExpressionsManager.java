/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.debug.core.zend.debugger;

import static org.eclipse.php.internal.debug.core.model.IPHPDataType.DataType.PHP_OBJECT;
import static org.eclipse.php.internal.debug.core.model.IPHPDataType.DataType.PHP_VIRTUAL_CLASS;

import java.util.*;

import org.eclipse.php.internal.debug.core.model.VariablesUtil;
import org.eclipse.php.internal.debug.core.preferences.PHPProjectPreferences;

/**
 * @author guy
 */
public class DefaultExpressionsManager implements ExpressionsManager {

	private static final Expression[] EMPTY_VARIABLE_ARRAY = new Expression[0];
	private static final byte[] ILLEGAL_VAR = { 'N' };

	private Debugger debugger;
	private Map<String, Object> hashResultDepthOne = new HashMap<String, Object>();
	private Map<String, byte[]> hashResultDepthZero = new HashMap<String, byte[]>();
	private ExpressionsValueDeserializer expressionValueDeserializer;
	private ExpressionsUtil fExpressionsUtil;

	/**
	 * Creates new DefaultExpressionsManager
	 */
	public DefaultExpressionsManager(Debugger debugger, String transferEncoding) {
		this.debugger = debugger;
		expressionValueDeserializer = new ExpressionsValueDeserializer(transferEncoding);
		fExpressionsUtil = ExpressionsUtil.getInstance(this);
	}

	public byte[] getExpressionValue(Expression expression, int depth) {
		if (!debugger.isActive()) {
			return ILLEGAL_VAR;
		}
		if (expression instanceof StackVariable) {
			return getStackVariableValue((StackVariable) expression, depth);
		}
		String[] name = minimizeArray(expression.getName());
		return getVariableValue(name, depth);
	}

	public boolean assignValue(Expression expression, String value, int depth) {
		String[] name = minimizeArray(expression.getName());
		String[] path = new String[name.length - 1];
		System.arraycopy(name, 1, path, 0, name.length - 1);
		boolean status = true;
		status = debugger.assignValue(name[0], value, depth, path);
		byte[] eValue = debugger.getVariableValue(name[0], depth, path);
		if (status) {
			String key = buildKey(name);
			if (depth == 1) {
				hashResultDepthOne.put(key, eValue);
			} else if (depth == 0) {
				hashResultDepthZero.put(key, eValue);
			}
		}
		return status;
	}

	public Expression[] getCurrentVariables(int depth) {
		Expression contextExpression = CurrentContextExpression.build(debugger);
		byte[] value = getExpressionValue(contextExpression, depth);
		ExpressionValue variableValue = expressionValueDeserializer.deserializer(contextExpression, value);
		Expression[] variables = variableValue.getOriChildren();
		if (variables == null || variables.length == 0) {
			return EMPTY_VARIABLE_ARRAY;
		}
		boolean hasThis = false;
		List<Expression> currentVariables = new ArrayList<Expression>();
		for (int i = 0; i < variables.length - 1; i++) {
			String s = variables[i].getFullName();
			// Skip $GLOBALS variable (since PHP 5.0.0)
			if (s.equals("$GLOBALS")) //$NON-NLS-1$
				continue;
			// Check if object context is active
			if (s.equals("$this")) //$NON-NLS-1$
				hasThis = true;
			currentVariables.add(variables[i]);
		}
		// Last one in the list is dummy for a current class name
		Expression dummyClass = variables[variables.length - 1];
		String className = (String) dummyClass.getValue().getValue();
		// Check if we are in static context
		if (!hasThis && !"0".equals(className)) { //$NON-NLS-1$
			Expression staticClassContext = fExpressionsUtil.fetchStaticContext(className);
			if (staticClassContext != null)
				currentVariables.add(staticClassContext);
		}
		variables = currentVariables.toArray(new Expression[currentVariables.size()]);
		// Sort by type (default order: this or class, locals, super globals)
		VariablesUtil.sortContextMembers(variables);
		hashResultDepthOne.put("LOCALS", variables); //$NON-NLS-1$
		return variables;
	}

	public Expression buildExpression(String name) {
		return new DefaultExpression(name);
	}

	public void update(Expression expression, int depth) {
		if (expression.getValue().getDataType() == PHP_VIRTUAL_CLASS)
			return;
		byte[] value = getExpressionValue(expression, depth);
		ExpressionValue expressionValue = expressionValueDeserializer.deserializer(expression, value);
		// Workaround for fetching static members for objects
		if (expressionValue.getDataType() == PHP_OBJECT && CurrentContextExpression.supportsStaticContext(debugger)) {
			Expression[] expressionStaticNodes = fExpressionsUtil
					.fetchStaticMembers((String) expressionValue.getValue());
			List<Expression> allNodes = new ArrayList<Expression>();
			allNodes.addAll(Arrays.asList(expressionStaticNodes));
			allNodes.addAll(Arrays.asList(expressionValue.getChildren()));
			expressionValue = new ExpressionValue(PHP_OBJECT, expressionValue.getValue(),
					expressionValue.getValueAsString(), allNodes.toArray(new Expression[allNodes.size()]),
					expressionValue.getChildrenCount() + expressionStaticNodes.length);
		}
		// Sort object members by type & name
		if (!PHPProjectPreferences.isSortByName() && expressionValue.getDataType() == PHP_OBJECT)
			VariablesUtil.sortObjectMembers(expressionValue.getOriChildren());
		expression.setValue(expressionValue);
	}

	private byte[] getVariableValue(String[] name, int depth) {
		String key = buildKey(name);
		if (hashResultDepthOne.containsKey(key)) {
			return (byte[]) hashResultDepthOne.get(key);
		}
		if (depth == 0 && hashResultDepthZero.containsKey(key)) {
			return (byte[]) hashResultDepthZero.get(key);
		}
		String[] path = new String[name.length - 1];
		System.arraycopy(name, 1, path, 0, name.length - 1);
		byte[] value = debugger.getVariableValue(name[0], depth, path);
		if (value != null) {
			if (depth == 1) {
				hashResultDepthOne.put(key, value);
			} else if (depth == 0) {
				hashResultDepthZero.put(key, value);
			}
		} else {
			value = new byte[] { 'N' };
		}
		return value;
	}

	private byte[] getStackVariableValue(StackVariable variable, int depth) {
		int layer = variable.getStackDepth();
		String[] name = variable.getName();
		String[] path = new String[name.length - 1];
		System.arraycopy(name, 1, path, 0, name.length - 1);
		return debugger.getStackVariableValue(layer, name[0], depth, path);
	}

	private static String buildKey(String[] name) {
		// 5 as the average size of variable name
		StringBuilder buffer = new StringBuilder(name.length * 5);
		for (int i = 0; i < name.length; i++) {
			buffer.append(name[i]);
			buffer.append(' ');
		}
		return buffer.toString();
	}

	private static String[] minimizeArray(String[] name) {
		String firstName = name[0];
		if (firstName.startsWith("$GLOBALS[GLOBALS]")) { //$NON-NLS-1$
			firstName = "$GLOBALS" + firstName.substring(17); //$NON-NLS-1$
			name[0] = firstName;
			return minimizeArray(name);
		}
		if (name.length < 2) {
			return name;
		}
		if (name[0].equals("get_defined_vars()")) { //$NON-NLS-1$
			if (name[1].equals("GLOBALS")) { //$NON-NLS-1$
				String[] newName = new String[name.length - 1];
				newName[0] = "$GLOBALS"; //$NON-NLS-1$
				System.arraycopy(name, 2, newName, 1, name.length - 2);
				return minimizeArray(newName);
			}
		}
		if (name[0].equals("$GLOBALS") && name[1].equals("GLOBALS")) { //$NON-NLS-1$ //$NON-NLS-2$
			String[] newName = new String[name.length - 1];
			newName[0] = name[0];
			System.arraycopy(name, 2, newName, 1, name.length - 2);
			return minimizeArray(newName);
		}
		return name;
	}

}