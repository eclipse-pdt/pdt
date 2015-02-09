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

import java.util.Arrays;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.php.internal.debug.core.model.PHPDebugElement;
import org.eclipse.php.internal.debug.core.model.VirtualPartition;
import org.eclipse.php.internal.debug.core.model.VirtualPartition.IVariableProvider;
import org.eclipse.php.internal.debug.core.zend.debugger.DefaultExpression;
import org.eclipse.php.internal.debug.core.zend.debugger.Expression;
import org.eclipse.php.internal.debug.core.zend.debugger.ExpressionValue;
import org.eclipse.php.internal.debug.core.zend.debugger.ExpressionsManager;

/**
 * Value of a PHP variable.
 */
public class PHPValue extends PHPDebugElement implements IValue {

	private static final String[] VARIABLE_TYPES = { "NULL", "INT", "STRING", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			"BOOLEAN", "DOUBLE", "ARRAY", "OBJECT", "RESOURCE", "CLASS" }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
	private static final int ARRAY_PARTITION_BOUNDARY = 100;
	private Expression fVariable;
	private ExpressionValue fValue;
	private boolean fGlobal;
	private IVariable[] fChildren;

	public PHPValue(PHPDebugTarget target, Expression variable, boolean global) {
		super(target);

		fValue = variable.getValue();
		fVariable = variable;
		fGlobal = global;
	}

	public PHPValue(PHPDebugTarget target, Expression variable) {
		this(target, variable, false);
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
		return fValue.getValueAsString();
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
			// Check if we should divide it into partitions
			if (fValue.getType() == ExpressionValue.ARRAY_TYPE
					&& fChildren.length >= ARRAY_PARTITION_BOUNDARY) {
				createPartitions();
			}
		}
		return fChildren;
	}

	private void createPartitions() {
		int numChild = fChildren.length;
		int partitions = (int) Math.ceil(numChild / (double) 100);
		IVariable[] children = new IVariable[partitions];
		for (int i = 0; i < partitions; i++) {
			int startIndex = i * ARRAY_PARTITION_BOUNDARY;
			int endIndex = (i + 1) * ARRAY_PARTITION_BOUNDARY - 1;
			if (endIndex > numChild) {
				endIndex = numChild - 1;
			}
			final IVariable[] vars = Arrays.copyOfRange(fChildren,
					startIndex, endIndex + 1);
			IVariable var = new VirtualPartition(this,
					new IVariableProvider() {
						@Override
						public IVariable[] getVariables()
								throws DebugException {
							return vars;
						}
					}, startIndex, endIndex);
			children[i] = var;
		}
		fChildren = children;
	}

	private void requestVariables() {
		PHPDebugTarget debugTarget = (PHPDebugTarget) getDebugTarget();
		ExpressionsManager expressionManager = debugTarget
				.getExpressionManager();
		Expression variable = fVariable;
		if (fGlobal) {
			String exp = "$GLOBALS[\"" + fVariable.getFullName().substring(1) //$NON-NLS-1$
					+ "\"]"; //$NON-NLS-1$
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
		boolean isArrayOrObject = fValue.getType() == 5
				|| fValue.getType() == 6 || fValue.getType() == 8;
		if (!isArrayOrObject) {
			return false;
		}
		return fValue.getChildrenCount() > 0;
	}

	public void updateValue(ExpressionValue value) {
		fValue = value;
		initChildren(fValue);
	}

	public boolean isPrimitive() {
		return fValue.isPrimitive();
	}

	public Expression getVariable() {
		return fVariable;
	}

}
