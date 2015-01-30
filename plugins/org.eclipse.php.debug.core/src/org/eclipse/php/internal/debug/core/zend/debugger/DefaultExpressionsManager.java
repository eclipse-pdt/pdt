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

import java.text.MessageFormat;
import java.util.*;

import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.php.internal.debug.core.zend.model.PHPDebugTarget;

/**
 * @author guy
 */
public class DefaultExpressionsManager implements ExpressionsManager {

	private static final Expression[] EMPTY_VARIABLE_ARRAY = new Expression[0];
	private static final byte[] ILLEGAL_VAR = { 'N' };

	private final static String GET_CURRENT_CONTEXT = "eval('if (isset($this)) {$this;}; return array_merge(get_defined_vars(), array(constant(\\'__CLASS__\\')));')"; //$NON-NLS-1$

	private Debugger debugger;
	private Map<String, Object> hashResultDepthOne = new HashMap<String, Object>();
	private Map<String, byte[]> hashResultDepthZero = new HashMap<String, byte[]>();
	private String[] currentContextPath = new String[] { GET_CURRENT_CONTEXT };
	private ExpressionsValueDeserializer expressionValueDeserializer;

	/**
	 * Creates new DefaultExpressionsManager
	 */
	public DefaultExpressionsManager(Debugger debugger, String transferEncoding) {
		this.debugger = debugger;
		expressionValueDeserializer = new ExpressionsValueDeserializer(
				transferEncoding);
	}

	public static ExpressionsManager getCurrent() {
		IDebugTarget debugTarget = PHPDebugPlugin.getActiveDebugTarget();
		if (debugTarget != null && debugTarget instanceof PHPDebugTarget) {
			PHPDebugTarget phpDebugTarget = (PHPDebugTarget) debugTarget;
			return phpDebugTarget.getExpressionManager();
		}
		return null;
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
		if (expression instanceof StaticMemberExpression) {
			String member = expression.getLastName();
			Expression changeStatic = new DefaultExpression(
					MessageFormat.format("eval(''self::${0}={1};'')", member, //$NON-NLS-1$
							value));
			update(changeStatic, 1);
		} else
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
		byte[] value = getVariableValue(currentContextPath, depth);
		ExpressionValue variableValue = expressionValueDeserializer
				.deserializer(null, value);
		Expression[] variables = variableValue.getOriChildren();
		if (variables == null) {
			variables = EMPTY_VARIABLE_ARRAY;
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
		if (!hasThis && className!= null && !className.isEmpty()) {
			Expression statics = new StaticsExpression(className);
			update(statics, 1);
			Expression[] staticVariables = statics.getValue().getChildren();
			if (staticVariables != null)
				currentVariables.addAll(Arrays.asList(staticVariables));
		}
		variables = currentVariables.toArray(new Expression[currentVariables
				.size()]);
		hashResultDepthOne.put("LOCALS", variables); //$NON-NLS-1$
		return variables;
	}

	public Expression buildExpression(String name) {
		return new DefaultExpression(name);
	}

	public void update(Expression expression, int depth) {
		byte[] value = getExpressionValue(expression, depth);
		expression.setValue(expressionValueDeserializer.deserializer(
				expression, value));
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
		StringBuffer buffer = new StringBuffer(name.length * 5);
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