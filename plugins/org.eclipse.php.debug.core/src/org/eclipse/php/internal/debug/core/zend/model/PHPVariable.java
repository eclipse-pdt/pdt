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

import static org.eclipse.php.internal.debug.core.model.IVariableFacet.Facet.*;

import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.debug.ui.actions.IWatchExpressionFactoryAdapter;
import org.eclipse.php.internal.debug.core.Logger;
import org.eclipse.php.internal.debug.core.model.IVariableFacet;
import org.eclipse.php.internal.debug.core.model.PHPDebugElement;
import org.eclipse.php.internal.debug.core.zend.debugger.DefaultExpression;
import org.eclipse.php.internal.debug.core.zend.debugger.Expression;
import org.eclipse.php.internal.debug.core.zend.debugger.ExpressionsManager;

/**
 * A variable in a PHP stack frame
 */
public class PHPVariable extends PHPDebugElement implements IVariable {

	private Expression variable;
	private PHPValue value;
	private boolean hasChanged = false;
	private boolean global = false;
	private String name = null;

	/**
	 * Constructs a variable contained in the given stack frame with the given
	 * name.
	 * 
	 */
	public PHPVariable(PHPDebugTarget target, Expression variable) {
		super(target);
		this.variable = variable;
		this.global = false;
		this.value = new PHPValue(target, variable);
		this.hasChanged = target.storeValue(variable);
	}

	/**
	 * Constructs a variable contained in the given stack frame with the given
	 * name for a global variable.
	 * 
	 */
	public PHPVariable(PHPDebugTarget target, Expression variable,
			boolean global) {
		super(target);
		this.variable = variable;
		this.global = global;
		this.value = new PHPValue(target, variable, global);
		this.hasChanged = target.storeValue(variable);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IVariable#getValue()
	 */
	public IValue getValue() throws DebugException {
		return value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IVariable#getName()
	 */
	public String getName() throws DebugException {
		if (name == null) {
			String endName = variable.getLastName();
			if (variable.hasFacet(KIND_OBJECT_MEMBER)) {
				int idx = endName.lastIndexOf(':');
				if (idx != -1)
					endName = endName.substring(idx + 1);
			} else if (variable.hasFacet(KIND_ARRAY_MEMBER)) {
				endName = '[' + endName + ']';
			}
			name = endName;
		}
		return name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IVariable#getReferenceTypeName()
	 */
	public String getReferenceTypeName() throws DebugException {
		return value.getReferenceTypeName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IVariable#hasValueChanged()
	 */
	public boolean hasValueChanged() throws DebugException {
		return hasChanged;
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
		ExpressionsManager expressionManager = debugTarget
				.getExpressionManager();
		Expression changeVar = variable;
		if (global) {
			String exp = "$GLOBALS[\"" + variable.getFullName().substring(1) //$NON-NLS-1$
					+ "\"]"; //$NON-NLS-1$
			changeVar = new DefaultExpression(exp);
		}
		boolean status = expressionManager
				.assignValue(changeVar, expression, 1);
		if (!status) {
			Logger.debugMSG("[" + this //$NON-NLS-1$
					+ "] PHPValue: Problem changing variable value"); //$NON-NLS-1$
		}
		expressionManager.update(changeVar, 1);
		value.updateValue(changeVar.getValue());
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
		if (variable.hasFacet(MOD_STATIC) || variable.hasFacet(VIRTUAL_CLASS))
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
	public boolean verifyValue(String expression) throws DebugException {
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

	public Object getAdapter(@SuppressWarnings("rawtypes") Class adapter) {
		if (adapter == IWatchExpressionFactoryAdapter.class) {
			return new WatchExpressionFactoryAdapter();
		}
		if (adapter == Expression.class || adapter == IVariableFacet.class) {
			return variable;
		}
		return super.getAdapter(adapter);
	}

}
