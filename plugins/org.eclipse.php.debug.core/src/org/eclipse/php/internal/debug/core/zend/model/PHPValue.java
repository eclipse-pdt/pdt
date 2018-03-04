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

	private final class VariablesContainer {

		private Map<String, IVariable> fAllCurrentVariables = null;
		private Map<String, IVariable> fAllPreviousVariables = null;
		private Map<String, IVirtualPartition> fAllCurrentPartitions = new LinkedHashMap<>();
		private Map<String, IVirtualPartition> fAllPreviousPartitions = new LinkedHashMap<>();
		private IVariable[] fVariables = null;
		private boolean fIsOutdated = true;

		IVariable[] getVariables() {
			if (fIsOutdated) {
				updateVariables();
				// Check if we should divide it into partitions
				if (fExpressionValue.getDataType() == DataType.PHP_ARRAY
						&& fAllCurrentVariables.size() >= ARRAY_PARTITION_BOUNDARY) {
					createPartitions();
					fVariables = fAllCurrentPartitions.values().toArray(new IVariable[fAllCurrentPartitions.size()]);
				} else {
					fVariables = fAllCurrentVariables.values().toArray(new IVariable[fAllCurrentVariables.size()]);
				}
				fIsOutdated = false;
			}
			return fVariables;
		}

		void markOutdated() {
			fIsOutdated = true;
		}

		/**
		 * Merges incoming variable. Merge is done by means of checking if related child
		 * variable existed in "one step back" state of a container. If related variable
		 * existed, it is updated with the use of the most recent descriptor and
		 * returned instead of the incoming one.
		 * 
		 * @param variable
		 * @param descriptor
		 * @return merged variable
		 */
		private IVariable merge(IVariable variable) {
			if (fAllPreviousVariables == null) {
				return variable;
			}
			if (!(variable instanceof PHPVariable)) {
				return variable;
			}
			PHPVariable incoming = (PHPVariable) variable;
			if (incoming.getFullName().isEmpty()) {
				return incoming;
			}
			IVariable stored = fAllPreviousVariables.get(incoming.getFullName());
			if (stored != null) {
				((PHPVariable) stored).update(incoming.getExpression());
				return stored;
			}
			return variable;
		}

		private void updateVariables() {
			fAllPreviousVariables = fAllCurrentVariables;
			fAllCurrentVariables = new LinkedHashMap<>();
			PHPDebugTarget debugTarget = (PHPDebugTarget) getDebugTarget();
			ExpressionsManager expressionManager = debugTarget.getExpressionManager();
			Expression variable = fExpression;
			expressionManager.update(variable, 1);
			fExpressionValue = variable.getValue();
			Expression[] children = fExpressionValue.getChildren();
			if (children != null) {
				fAllCurrentVariables = new LinkedHashMap<>();
				for (int i = 0; i < children.length; i++) {
					IVariable incoming = new PHPVariable((PHPDebugTarget) getDebugTarget(), children[i]);
					fAllCurrentVariables.put(((PHPVariable) incoming).getFullName(), merge(incoming));
				}
			}
		}

		private void createPartitions() {
			int numChild = fAllCurrentVariables.size();
			int partitions = (int) Math.ceil(numChild / (double) 100);
			fAllPreviousPartitions = fAllCurrentPartitions;
			fAllCurrentPartitions = new LinkedHashMap<>();
			for (int i = 0; i < partitions; i++) {
				int startIndex = i * ARRAY_PARTITION_BOUNDARY;
				int endIndex = (i + 1) * ARRAY_PARTITION_BOUNDARY - 1;
				if (endIndex > numChild) {
					endIndex = numChild - 1;
				}
				final IVariable[] vars = Arrays.copyOfRange(
						fAllCurrentVariables.values().toArray(new IVariable[fAllCurrentVariables.size()]), startIndex,
						endIndex + 1);
				IVariableProvider variableProvider = new IVariableProvider() {
					@Override
					public IVariable[] getVariables() throws DebugException {
						return vars;
					}
				};
				String partitionId = String.valueOf(startIndex) + '-' + String.valueOf(endIndex);
				IVirtualPartition partition = fAllPreviousPartitions.get(partitionId);
				if (partition != null) {
					partition.setProvider(variableProvider);
					fAllCurrentPartitions.put(partitionId, partition);
				} else {
					fAllCurrentPartitions.put(partitionId,
							new VirtualPartition(PHPValue.this, variableProvider, startIndex, endIndex));
				}
			}
		}

	}

	private static final int ARRAY_PARTITION_BOUNDARY = 100;
	protected Expression fExpression;
	protected ExpressionValue fExpressionValue;
	protected VariablesContainer fVariablesContainer;

	public PHPValue(PHPDebugTarget target, Expression expression) {
		super(target);
		fExpressionValue = expression.getValue();
		fExpression = expression;
		fVariablesContainer = new VariablesContainer();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IValue#isAllocated()
	 */
	@Override
	public boolean isAllocated() throws DebugException {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IValue#getReferenceTypeName()
	 */
	@Override
	public String getReferenceTypeName() throws DebugException {
		return getDataType().getText().toUpperCase();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IValue#hasVariables()
	 */
	@Override
	public synchronized boolean hasVariables() throws DebugException {
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
	public synchronized DataType getDataType() {
		return fExpressionValue.getDataType();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IValue#getValueString()
	 */
	@Override
	public synchronized String getValueString() throws DebugException {
		return fExpressionValue.getValueAsString();
	}

	public synchronized String getValueDetail() throws DebugException {
		return ExpressionsUtil.getInstance(((PHPDebugTarget) getDebugTarget()).getExpressionManager())
				.getValueDetail(fExpression);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IValue#getVariables()
	 */
	@Override
	public synchronized IVariable[] getVariables() throws DebugException {
		return fVariablesContainer.getVariables();
	}

	public synchronized String getValue() throws DebugException {
		return String.valueOf(fExpressionValue.getValue());
	}

	protected synchronized Expression getExpression() {
		return fExpression;
	}

	protected synchronized void update(Expression expression) {
		// Bind to new expression
		fExpression = expression;
		fExpressionValue = fExpression.getValue();
		fVariablesContainer.markOutdated();
	}

}
