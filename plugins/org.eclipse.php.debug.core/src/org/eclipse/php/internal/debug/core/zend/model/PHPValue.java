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
import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.php.internal.debug.core.model.IPHPDataType;
import org.eclipse.php.internal.debug.core.model.IVirtualPartition;
import org.eclipse.php.internal.debug.core.model.IVirtualPartition.IVariableProvider;
import org.eclipse.php.internal.debug.core.model.PHPDebugElement;
import org.eclipse.php.internal.debug.core.model.VirtualPartition;
import org.eclipse.php.internal.debug.core.zend.debugger.Expression;
import org.eclipse.php.internal.debug.core.zend.debugger.ExpressionValue;
import org.eclipse.php.internal.debug.core.zend.debugger.ExpressionsManager;
import org.eclipse.php.internal.debug.core.zend.debugger.ExpressionsUtil;

/**
 * Value of a PHP variable.
 */
public class PHPValue extends PHPDebugElement implements IValue, IPHPDataType {

	private static final int ARRAY_PARTITION_BOUNDARY = 100;
	protected Expression fExpression;
	protected ExpressionValue fExpressionValue;
	protected IVariable[] fCurrentVariables = null;
	protected IVariable[] fPreviousVariables = null;
	protected Map<String, IVirtualPartition> fCurrentPartitions = new LinkedHashMap<>();
	protected Map<String, IVirtualPartition> fPreviousPartitions = new LinkedHashMap<>();

	public PHPValue(PHPDebugTarget target, Expression expression) {
		super(target);
		fExpressionValue = expression.getValue();
		fExpression = expression;
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
	 * @see org.eclipse.debug.core.model.IValue#hasVariables()
	 */
	public boolean hasVariables() throws DebugException {
		switch (fExpressionValue.getDataType()) {
		case PHP_ARRAY:
		case PHP_OBJECT:
		case PHP_VIRTUAL_CLASS:
			return fExpressionValue.getChildrenCount() > 0;
		default:
			break;
		}
		return false;
	}

	@Override
	public DataType getDataType() {
		return fExpressionValue.getDataType();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IValue#getReferenceTypeName()
	 */
	public String getReferenceTypeName() throws DebugException {
		return getDataType().getText().toUpperCase();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IValue#getValueString()
	 */
	public String getValueString() throws DebugException {
		return fExpressionValue.getValueAsString();
	}

	public String getValueDetail() throws DebugException {
		return ExpressionsUtil.getInstance(((PHPDebugTarget) getDebugTarget()).getExpressionManager())
				.getValueDetail(fExpression);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IValue#getVariables()
	 */
	public synchronized IVariable[] getVariables() throws DebugException {
		if (fCurrentVariables == null) {
			requestVariables();
			if (fCurrentVariables == null) {
				fCurrentVariables = new IVariable[0];
			}
			// Check if we should divide it into partitions
			if (fExpressionValue.getDataType() == DataType.PHP_ARRAY
					&& fCurrentVariables.length >= ARRAY_PARTITION_BOUNDARY) {
				createPartitions();
			}
		}
		if (!hasPartitions()) {
			return fCurrentVariables;
		}
		return fCurrentPartitions.values().toArray(new IVariable[fCurrentPartitions.size()]);
	}

	public String getValue() throws DebugException {
		return String.valueOf(fExpressionValue.getValue());
	}

	public void updateValue(ExpressionValue value) {
		fExpressionValue = value;
		createVariables(fExpressionValue);
	}

	protected Expression getExpression() {
		return fExpression;
	}

	/**
	 * Checks if there are multiple partitions with variables.
	 * 
	 * @return <code>true</code> if there are multiple partitions with
	 *         variables, <code>false</code> otherwise
	 */
	protected boolean hasPartitions() {
		return fCurrentPartitions.size() > 0;
	}

	protected void update(Expression expression) {
		// Reset variables state
		fPreviousVariables = fCurrentVariables;
		fCurrentVariables = null;
		// Bind to new expression
		fExpression = expression;
		fExpressionValue = fExpression.getValue();
	}

	/**
	 * Merges incoming variable. Merge is done by means of checking if related
	 * child variable existed in "one step back" state of a container. If
	 * related variable existed, it is updated with the use of the most recent
	 * descriptor and returned instead of the incoming one.
	 * 
	 * @param variable
	 * @param descriptor
	 * @return merged variable
	 */
	protected IVariable merge(IVariable variable) {
		if (fPreviousVariables == null)
			return variable;
		if (!(variable instanceof PHPVariable))
			return variable;
		PHPVariable incoming = (PHPVariable) variable;
		if (incoming.getFullName().isEmpty())
			return incoming;
		for (IVariable stored : fPreviousVariables) {
			if (stored instanceof PHPVariable) {
				PHPVariable previous = (PHPVariable) stored;
				if (previous.getFullName().equals(incoming.getFullName())) {
					((PHPVariable) stored).update(incoming.getExpression());
					return stored;
				}
			}
		}
		return variable;
	}

	private void requestVariables() {
		PHPDebugTarget debugTarget = (PHPDebugTarget) getDebugTarget();
		ExpressionsManager expressionManager = debugTarget.getExpressionManager();
		Expression variable = fExpression;
		expressionManager.update(variable, 1);
		fExpressionValue = variable.getValue();
		createVariables(fExpressionValue);
	}

	private void createVariables(ExpressionValue value) {
		Expression[] children = value.getChildren();
		if (children != null) {
			fCurrentVariables = new PHPVariable[children.length];
			for (int i = 0; i < children.length; i++) {
				IVariable incoming = new PHPVariable((PHPDebugTarget) getDebugTarget(), children[i]);
				fCurrentVariables[i] = merge(incoming);
			}
		}
	}

	private void createPartitions() {
		int numChild = fCurrentVariables.length;
		int partitions = (int) Math.ceil(numChild / (double) 100);
		fPreviousPartitions = fCurrentPartitions;
		fCurrentPartitions = new LinkedHashMap<>();
		for (int i = 0; i < partitions; i++) {
			int startIndex = i * ARRAY_PARTITION_BOUNDARY;
			int endIndex = (i + 1) * ARRAY_PARTITION_BOUNDARY - 1;
			if (endIndex > numChild) {
				endIndex = numChild - 1;
			}
			final IVariable[] vars = Arrays.copyOfRange(fCurrentVariables, startIndex, endIndex + 1);
			IVariableProvider variableProvider = new IVariableProvider() {
				@Override
				public IVariable[] getVariables() throws DebugException {
					return vars;
				}
			};
			String partitionId = String.valueOf(startIndex) + '-' + String.valueOf(endIndex);
			IVirtualPartition partition = fPreviousPartitions.get(partitionId);
			if (partition != null) {
				partition.setProvider(variableProvider);
				fCurrentPartitions.put(partitionId, partition);
			} else {
				fCurrentPartitions.put(partitionId, new VirtualPartition(this, variableProvider, startIndex, endIndex));
			}
		}
	}

}
