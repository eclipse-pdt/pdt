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

import static org.eclipse.php.internal.debug.core.model.IPHPDataType.DataType.*;
import static org.eclipse.php.internal.debug.core.model.IVariableFacet.Facet.*;

import java.text.MessageFormat;

import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.debug.ui.actions.IWatchExpressionFactoryAdapter;
import org.eclipse.php.internal.debug.core.Logger;
import org.eclipse.php.internal.debug.core.model.IPHPDataType;
import org.eclipse.php.internal.debug.core.model.IVariableFacet;
import org.eclipse.php.internal.debug.core.model.PHPDebugElement;
import org.eclipse.php.internal.debug.core.zend.debugger.Expression;
import org.eclipse.php.internal.debug.core.zend.debugger.ExpressionsManager;

/**
 * A variable in a PHP stack frame
 */
public class PHPVariable extends PHPDebugElement implements IVariable, IPHPDataType {

	private Expression fExpression;
	private PHPValue fValue;
	private boolean fHasChanged = false;
	private String fName = null;

	/**
	 * Constructs a variable contained in the given stack frame with the given
	 * name.
	 * 
	 */
	public PHPVariable(PHPDebugTarget target, Expression expression) {
		super(target);
		this.fExpression = expression;
		this.fValue = new PHPValue(target, expression);
	}

	@Override
	public DataType getDataType() {
		return fExpression.getValue().getDataType();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IVariable#getValue()
	 */
	public IValue getValue() throws DebugException {
		return fValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IVariable#getName()
	 */
	public String getName() throws DebugException {
		if (fName == null) {
			String endName = fExpression.getLastName();
			if (fExpression.hasFacet(KIND_OBJECT_MEMBER)) {
				int idx = endName.lastIndexOf(':');
				if (idx != -1)
					endName = endName.substring(idx + 1);
			} else if (fExpression.hasFacet(KIND_ARRAY_MEMBER)) {
				endName = '[' + endName + ']';
			}
			fName = endName;
		}
		return fName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IVariable#getReferenceTypeName()
	 */
	public String getReferenceTypeName() throws DebugException {
		return fValue.getReferenceTypeName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IVariable#hasValueChanged()
	 */
	public boolean hasValueChanged() throws DebugException {
		return fHasChanged;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.core.model.IValueModification#setValue(java.lang.String
	 * )
	 */
	public void setValue(String expression) throws DebugException {
		PHPDebugTarget debugTarget = (PHPDebugTarget) getDebugTarget();
		ExpressionsManager expressionManager = debugTarget.getExpressionManager();
		Expression changeVar = fExpression;
		if (fValue.getExpression().getValue().getDataType() == PHP_STRING) {
			expression = MessageFormat.format("\"{0}\"", expression); //$NON-NLS-1$
		}
		boolean status = expressionManager.assignValue(changeVar, expression, 1);
		if (!status) {
			Logger.debugMSG("[" + this //$NON-NLS-1$
					+ "] PHPValue: Problem changing variable value"); //$NON-NLS-1$
		}
		expressionManager.update(changeVar, 1);
		fValue.updateValue(changeVar.getValue());
		fireChangeEvent(DebugEvent.CONTENT);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.core.model.IValueModification#setValue(org.eclipse.
	 * debug.core.model.IValue)
	 */
	public void setValue(IValue value) throws DebugException {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.core.model.IValueModification#supportsValueModification
	 * ()
	 */
	public boolean supportsValueModification() {
		// Not supported yet
		if (fExpression.hasFacet(MOD_STATIC) || fExpression.hasFacet(VIRTUAL_CLASS))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.core.model.IValueModification#verifyValue(java.lang
	 * .String)
	 */
	public boolean verifyValue(String value) throws DebugException {
		switch (fExpression.getValue().getDataType()) {
		case PHP_BOOL: {
			if (!value.equalsIgnoreCase(String.valueOf(false)) && !value.equalsIgnoreCase(String.valueOf(true))) {
				return false;
			}
			break;
		}
		case PHP_FLOAT:
		case PHP_INT: {
			try {
				Double.parseDouble(value);
				return true;
			} catch (NumberFormatException nfe) {
				return false;
			}
		}
		default:
			break;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.core.model.IValueModification#verifyValue(org.eclipse
	 * .debug.core.model.IValue)
	 */
	public boolean verifyValue(IValue value) throws DebugException {
		return true;
	}

	@SuppressWarnings("unchecked")
	public Object getAdapter(@SuppressWarnings("rawtypes") Class adapter) {
		if (adapter == IWatchExpressionFactoryAdapter.class) {
			return new WatchExpressionFactoryAdapter();
		}
		if (adapter == Expression.class || adapter == IVariableFacet.class) {
			return fExpression;
		}
		return super.getAdapter(adapter);
	}

	protected Expression getExpression() {
		return fExpression;
	}

	protected String getFullName() {
		return fExpression.getFullName();
	}

	protected void update(Expression expression) {
		// Get previous data type
		DataType previousDataType = getDataType();
		// Catch previous value string if there is any
		String previousValueString = null;
		if (fValue != null) {
			previousValueString = fExpression.getValue().getValueAsString();
		}
		// Bind new expression
		fExpression = expression;
		// Reset name
		fName = null;
		// Update value
		if (fValue != null && fValue.getDataType() == previousDataType) {
			fValue.update(expression);
		} else {
			fValue = new PHPValue((PHPDebugTarget) getDebugTarget(), fExpression);
		}
		// Check if value has changed
		if (previousValueString != null) {
			fHasChanged = !previousValueString.equals(fExpression.getValue().getValueAsString());
		}
	}

}
