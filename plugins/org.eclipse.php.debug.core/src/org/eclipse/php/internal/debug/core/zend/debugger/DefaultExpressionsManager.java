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
/*
 * DefaultExpressionsManager.java
 *
 */

package org.eclipse.php.internal.debug.core.zend.debugger;

import java.util.HashMap;
import java.util.Map;

/**
 * @author guy
 */
public class DefaultExpressionsManager implements ExpressionsManager {

	private static final Expression[] EMPTY_VARIABLE_ARRAY = new Expression[0];
	private static final byte[] ILLEGAL_VAR = { 'N' };

	private Debugger debugger;
	private Map hashResultDepthOne = new HashMap();
	private Map hashResultDepthZero = new HashMap();
	// private Map globalHashResultDepthOne = new HashMap();
	private String[] localsVariablePath = new String[] { "get_defined_vars()" }; //$NON-NLS-1$
	private String[] globalVariablePath = new String[] { "$GLOBALS" }; //$NON-NLS-1$
	private ExpressionsValueDeserializer expressionValueDeserializer;

	/**
	 * Creates new DefaultExpressionsManager
	 */
	public DefaultExpressionsManager(Debugger debugger, String transferEncoding) {
		this.debugger = debugger;
		expressionValueDeserializer = new ExpressionsValueDeserializer(
				transferEncoding);
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
		boolean status = debugger.assignValue(name[0], value, depth, path);
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

	private static String buildKey(String[] name) {
		StringBuffer buffer = new StringBuffer(name.length * 5); // 5 as the
																	// average
																	// size of
																	// variable
																	// name
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

	public Expression[] getLocalVariables() {
		return getLocalVariables(1);
	}

	public Expression[] getLocalVariables(int depth) {
		byte[] value = getVariableValue(localsVariablePath, depth);
		ExpressionValue variableValue = expressionValueDeserializer
				.deserializer(null, value);

		Expression[] localVariables = variableValue.getOriChildren();
		if (localVariables == null) {
			localVariables = EMPTY_VARIABLE_ARRAY;
		}

		// search for globals variable
		for (int i = 0; i < localVariables.length; i++) {
			String s = localVariables[i].getFullName();
			if (s.equals("$GLOBALS")) { //$NON-NLS-1$

				// remove globals from array
				Expression[] newLocals = new Expression[localVariables.length - 1];
				System.arraycopy(localVariables, 0, newLocals, 0, i);
				System.arraycopy(localVariables, i + 1, newLocals, i,
						localVariables.length - i - 1);
				localVariables = newLocals;
				break;
			}
		}

		hashResultDepthOne.put("LOCALS", localVariables); //$NON-NLS-1$

		return localVariables;
	}

	public Expression[] getGlobalVariables() {
		return getGlobalVariables(1);
	}

	public Expression[] getGlobalVariables(int depth) {
		byte[] value = getVariableValue(globalVariablePath, depth);
		ExpressionValue variableValue = expressionValueDeserializer
				.deserializer(null, value);

		Expression[] globalVariables = variableValue.getChildren();
		if (globalVariables == null) {
			globalVariables = EMPTY_VARIABLE_ARRAY;
		}

		// search for globals variable.
		for (int i = 0; i < globalVariables.length; i++) {
			String s = globalVariables[i].getFullName();
			if (s.equals("$GLOBALS")) { //$NON-NLS-1$

				// remove globals from array
				Expression[] newGlobals = new Expression[globalVariables.length - 1];
				System.arraycopy(globalVariables, 0, newGlobals, 0, i);
				System.arraycopy(globalVariables, i + 1, newGlobals, i,
						globalVariables.length - i - 1);
				globalVariables = newGlobals;
				break;
			}
		}

		hashResultDepthOne.put("GlOBAS", globalVariables); //$NON-NLS-1$

		return globalVariables;
	}

	private byte[] getStackVariableValue(StackVariable variable, int depth) {
		int layer = variable.getStackDepth();

		String[] name = variable.getName();

		String[] path = new String[name.length - 1];
		System.arraycopy(name, 1, path, 0, name.length - 1);

		return debugger.getStackVariableValue(layer, name[0], depth, path);
	}

	public void clear() {
		hashResultDepthOne.clear();
		hashResultDepthZero.clear();
	}

	public Expression buildExpression(String name) {
		return new DefaultExpression(name);
	}

	public void update(Expression expression, int depth) {
		byte[] value = getExpressionValue(expression, depth);
		expression.setValue(expressionValueDeserializer.deserializer(
				expression, value));
	}
}