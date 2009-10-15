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
package org.eclipse.php.internal.debug.core.zend.model;

import java.util.StringTokenizer;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.php.internal.debug.core.model.PHPDebugElement;
import org.eclipse.php.internal.debug.core.zend.debugger.DefaultExpression;
import org.eclipse.php.internal.debug.core.zend.debugger.Expression;
import org.eclipse.php.internal.debug.core.zend.debugger.ExpressionValue;
import org.eclipse.php.internal.debug.core.zend.debugger.ExpressionsManager;

/**
 * Value of a PHP variable.
 */
public class PHPValue extends PHPDebugElement implements IValue {

	private static final String[] VARIABLE_TYPES = { "NULL", "INT", "STRING",
			"BOOLEAN", "DOUBLE", "ARRAY", "OBJECT", "RESOURCE" };

	private PHPDebugTarget fDebugTarget;
	private Expression fVariable;
	private ExpressionValue fValue;
	private boolean fGlobal;
	private IVariable[] fChildren;

	public PHPValue(PHPDebugTarget target, Expression var) {
		super(target);

		fDebugTarget = target;
		fValue = var.getValue();
		fVariable = var;
		fGlobal = false;

		initChildren(fValue);
	}

	public PHPValue(PHPDebugTarget target, Expression variable, boolean global) {
		super(target);
		fValue = variable.getValue();
		fVariable = variable;
		fGlobal = global;

		initChildren(fValue);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IValue#getReferenceTypeName()
	 */
	public String getReferenceTypeName() throws DebugException {
		return VARIABLE_TYPES[fValue.getType()];
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IValue#getValueString()
	 */
	public String getValueString() throws DebugException {
		StringBuffer string = new StringBuffer();
		StringTokenizer tokenizer = new StringTokenizer(fValue
				.getValueAsString(), "\\", true);
		String token;
		while (tokenizer.hasMoreTokens()) {
			token = tokenizer.nextToken();
			if (token.length() > 1) {
				string.append(token);
			} else {
				string.append(token);
				if (tokenizer.hasMoreTokens())
					tokenizer.nextToken();
			}
		}
		String rString = string.toString();
		return rString;
	}

	public String getValue() throws DebugException {
		return (String) (fValue.getValue());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IValue#isAllocated()
	 */
	public boolean isAllocated() throws DebugException {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IValue#getVariables()
	 */
	public IVariable[] getVariables() throws DebugException {
		if (fChildren == null) {
			requestVariables();
			if (fChildren == null) {
				fChildren = new IVariable[0];
			}
		}
		return fChildren;
	}

	private void requestVariables() {
		PHPDebugTarget debugTarget = (PHPDebugTarget) getDebugTarget();
		ExpressionsManager expressionManager = debugTarget
				.getExpressionManager();
		Expression variable = fVariable;
		if (fGlobal) {
			String exp = "$GLOBALS[\"" + fVariable.getFullName().substring(1)
					+ "\"]";
			variable = new DefaultExpression(exp);
		}
		expressionManager.update(variable, 1);
		fValue = variable.getValue();

		initChildren(fValue);
	}

	private void initChildren(ExpressionValue value) {
		fChildren = null;
		Expression[] children = value.getChildren();
		if (children != null) {
			fChildren = new PHPVariable[children.length];
			for (int i = 0; i < children.length; i++) {
				fChildren[i] = new PHPVariable(
						(PHPDebugTarget) getDebugTarget(), children[i], fGlobal);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IValue#hasVariables()
	 */
	public boolean hasVariables() throws DebugException {
		// if childVariables is null, we assume we do have
		// some variables, it's just they need to be got.
		boolean hasVars = (fChildren == null || fChildren.length > 0);
		return hasVars;
	}

	public void updateValue(ExpressionValue value) {
		fValue = value;
		initChildren(fValue);
	}

	public boolean isPrimative() {
		return fValue.isPrimitive();
	}
}
