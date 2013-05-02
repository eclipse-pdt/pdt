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
 * StackLayer.java
 *
 */

package org.eclipse.php.internal.debug.core.zend.debugger;

import java.util.HashMap;
import java.util.Map;

/**
 * @author guy
 */
public class StackLayer {

	private int depth;

	private String callerFileName;
	private int callerLineNumber;
	private String callerFunctionName;

	private String resolvedCallerFileName;
	private String resolvedCalledFileName;
	private String calledFileName;
	private int calledLineNumber;
	private String calledFunctionName;

	private Expression[] variables;
	private Map<String, byte[]> unresolvedVariables;

	private ExpressionsValueDeserializer expressionValueDeserializer;

	/**
	 * Creates new StackLayer
	 */
	public StackLayer(String transferEncoding) {
		unresolvedVariables = new HashMap<String, byte[]>();
		expressionValueDeserializer = new ExpressionsValueDeserializer(
				transferEncoding);
	}

	/**
	 * Creates new StackLayer
	 */
	public StackLayer(int depth, String callerFileName, int callerLineNumber,
			String callerFunctionName, String calledFileName,
			int calledLineNumber, String calledFunctionName,
			String transferEncoding) {
		this(transferEncoding);

		this.depth = depth;
		setCallerFileName(callerFileName);
		setCallerLineNumber(callerLineNumber);
		setCallerFunctionName(callerFunctionName);

		setCalledFileName(calledFileName);
		setCalledLineNumber(calledLineNumber);
		setCalledFunctionName(calledFunctionName);
	}

	public final int getDepth() {
		return depth;
	}

	public final String getCallerFileName() {
		return callerFileName;
	}

	public final void setCallerFileName(String callerFileName) {
		this.callerFileName = callerFileName;
	}

	public final int getCallerLineNumber() {
		return callerLineNumber;
	}

	public final void setCallerLineNumber(int callerLineNumber) {
		this.callerLineNumber = callerLineNumber;
	}

	public final String getCallerFunctionName() {
		return callerFunctionName;
	}

	public final void setCallerFunctionName(String callerFunctionName) {
		this.callerFunctionName = callerFunctionName;
	}

	public final String getCalledFileName() {
		return calledFileName;
	}

	public final void setCalledFileName(String calledFileName) {
		this.calledFileName = calledFileName;
	}

	public final String getResolvedCallerFileName() {
		return resolvedCallerFileName;
	}

	public final void setResolvedCallerFileName(String resolvedCallerFileName) {
		this.resolvedCallerFileName = resolvedCallerFileName;
	}

	public final String getResolvedCalledFileName() {
		return resolvedCalledFileName;
	}

	public final void setResolvedCalledFileName(String resolvedCalledFileName) {
		this.resolvedCalledFileName = resolvedCalledFileName;
	}

	public final int getCalledLineNumber() {
		return calledLineNumber;
	}

	public final void setCalledLineNumber(int calledLineNumber) {
		this.calledLineNumber = calledLineNumber;
	}

	public final String getCalledFunctionName() {
		return calledFunctionName;
	}

	public final void setCalledFunctionName(String calledFunctionName) {
		this.calledFunctionName = calledFunctionName;
	}

	public void addVariable(String variableName, byte[] value) {
		unresolvedVariables.put(variableName, value);
	}

	public Expression[] getVariables() {
		if (variables == null) {
			variables = new Expression[unresolvedVariables.size()];
			int i = 0;
			for (String variableName : unresolvedVariables.keySet()) {
				StackVariable variable = new DefaultStackVariable(variableName,
						depth);
				variable.setValue(expressionValueDeserializer.deserializer(
						variable, unresolvedVariables.get(variableName)));
				variables[i++] = variable;
			}
		}
		return variables;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer(20);
		buffer.append(toStringCalledFunctionLine());
		buffer
				.append("  " + callerFileName + " line " //$NON-NLS-1$ //$NON-NLS-2$
						+ (callerLineNumber + 1));
		return buffer.toString();
	}

	public String toStringCalledFunctionLine() {
		if (getCallerFunctionName() == null
				|| getCallerFunctionName().equals("")) { //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}
		StringBuffer buffer = new StringBuffer(getCallerFunctionName() + '(');
		Expression[] variables = getVariables();
		for (int i = 0; i < variables.length; i++) {
			Expression expression = variables[i];
			buffer.append(" $"); //$NON-NLS-1$
			buffer.append(expression.getLastName());
			if (i != variables.length - 1) {
				buffer.append(',');
			}
		}
		buffer.append(" )"); //$NON-NLS-1$
		return buffer.toString();
	}

	private static class DefaultStackVariable extends DefaultExpression
			implements StackVariable {

		private int stackDepth;

		/**
		 * Creates new DefaultStackVariable
		 */
		DefaultStackVariable(String name, int stackDepth) {
			super(name);
			this.stackDepth = stackDepth;
		}

		public int getStackDepth() {
			return stackDepth;
		}

		public Expression createChildExpression(String endName,
				String endRepresentation) {
			return new DefaultStackVariable(this, endName, stackDepth,
					endRepresentation);
		}

		private DefaultStackVariable(StackVariable parent, String name,
				int stackDepth, String representation) {
			super(parent, name, representation);
			this.stackDepth = stackDepth;
		}

	}

}