/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.core.model;

import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.php.internal.debug.core.Logger;
import org.eclipse.php.internal.debug.core.debugger.DefaultExpression;
import org.eclipse.php.internal.debug.core.debugger.Expression;
import org.eclipse.php.internal.debug.core.debugger.ExpressionsManager;

/**
 * A variable in a PHP stack frame
 */
public class PHPVariable extends PHPDebugElement implements IVariable {

    private Expression variable;

    private PHPValue pValue;

    private boolean hasChanged = false;

    private boolean global = false;

    /**
     * Constructs a variable contained in the given stack frame with the given
     * name.
     * 
     */
    public PHPVariable(PHPDebugTarget target, Expression var) {
        super(target);
        variable = var;
        global = false;
        pValue = new PHPValue(target, variable);
    }

    /**
     * Constructs a variable contained in the given stack frame with the given
     * name for a global variable.
     * 
     */
    public PHPVariable(PHPDebugTarget target, Expression var, boolean global) {
        super(target);
        variable = var;
        this.global = global;
        pValue = new PHPValue(target, variable, global);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.debug.core.model.IVariable#getValue()
     */
    public IValue getValue() throws DebugException { 
        hasChanged = false;
        return pValue;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.debug.core.model.IVariable#getName()
     */
    public String getName() throws DebugException {
        return (variable.getLastName());
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.debug.core.model.IVariable#getReferenceTypeName()
     */
    public String getReferenceTypeName() throws DebugException {
        return pValue.getReferenceTypeName();
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
     * @see org.eclipse.debug.core.model.IValueModification#setValue(java.lang.String)
     */
    public void setValue(String expression) throws DebugException {
        PHPDebugTarget debugTarget = (PHPDebugTarget) getDebugTarget();
        ExpressionsManager expressionManager = debugTarget.getExpressionManager();
        Expression changeVar = variable;
        if (global) {
            String exp = "$GLOBALS[\"" + variable.getFullName().substring(1) + "\"]";
            changeVar = new DefaultExpression(exp);
        }
        boolean status = expressionManager.assignValue(changeVar, expression, 1);
        if (!status) {
            Logger.debugMSG("[" + this + "] PHPValue: Problem changing variable value");
        }
        expressionManager.update(changeVar, 1);
        pValue.updateValue(changeVar.getValue());
        fireChangeEvent(DebugEvent.CONTENT);

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.debug.core.model.IValueModification#setValue(org.eclipse.debug.core.model.IValue)
     */
    public void setValue(IValue value) throws DebugException {
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.debug.core.model.IValueModification#supportsValueModification()
     */
    public boolean supportsValueModification() {
        //		return pValue.isPrimative();
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.debug.core.model.IValueModification#verifyValue(java.lang.String)
     */
    public boolean verifyValue(String expression) throws DebugException {
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.debug.core.model.IValueModification#verifyValue(org.eclipse.debug.core.model.IValue)
     */
    public boolean verifyValue(IValue value) throws DebugException {
        return true;
    }

	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof PHPVariable)) {
			return false;
		}
		PHPVariable variable = (PHPVariable)obj;
		
		if (!variable.getDebugTarget().equals(getDebugTarget())) {
			return false;
		}
		
		if (!variable.variable.getFullName().equals(this.variable.getFullName())) {
			return false;
		}
		
		IValue myValue = null;
		IValue otherValue = null;
		try {
			myValue = getValue();
			otherValue = variable.getValue();
		} catch (DebugException e) {
		}
		if (myValue == otherValue || (myValue != null && myValue.equals(otherValue))) {
			return true;
		}
		return false;
	}

	public int hashCode() {
		int valueHash = 0;
		valueHash = pValue.hashCode();
		return getDebugTarget().hashCode() + valueHash+ variable.getFullName().hashCode();
	}
}
