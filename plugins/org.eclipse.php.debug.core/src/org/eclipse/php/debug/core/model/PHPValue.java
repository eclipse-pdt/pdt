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
package org.eclipse.php.debug.core.model;

import java.util.StringTokenizer;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.php.debug.core.debugger.DefaultExpression;
import org.eclipse.php.debug.core.debugger.Expression;
import org.eclipse.php.debug.core.debugger.ExpressionValue;
import org.eclipse.php.debug.core.debugger.ExpressionsManager;

/**
 * Value of a PHP variable.
 */
public class PHPValue extends PHPDebugElement implements IValue {

    private ExpressionValue fValue;
    private boolean fHasChildren = false;
    private IVariable[] fChildren = new IVariable[0];
    private Expression fVariable;
    private boolean fGlobal;

    private String[] types = { "NULL", "INT", "STRING", "BOOLEAN", "DOUBLE", "ARRAY", "OBJECT", "RESOURCE" };

    public PHPValue(PHPDebugTarget target, Expression var) {
        super(target);
        fValue = var.getValue();
        fVariable = var;
        fGlobal = false;
        processChildren(fValue);

    }

    public PHPValue(PHPDebugTarget target, Expression var, boolean global) {
        super(target);
        fValue = var.getValue();
        fVariable = var;
        fGlobal = global;
        processChildren(fValue);

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.debug.core.model.IValue#getReferenceTypeName()
     */
    public String getReferenceTypeName() throws DebugException {
        return types[fValue.getType()];
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.debug.core.model.IValue#getValueString()
     */
    public String getValueString() throws DebugException {
        StringBuffer string = new StringBuffer();
        StringTokenizer tokenizer = new StringTokenizer(fValue.getValueAsString(), "\\", true);
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
        if (fChildren.length == 0) {
            PHPDebugTarget debugTarget = (PHPDebugTarget) getDebugTarget();
            ExpressionsManager expressionManager = debugTarget.getExpressionManager();
            Expression var = fVariable;
            if (fGlobal) {
                String exp = "$GLOBALS[\"" + fVariable.getFullName().substring(1) + "\"]";
                var = new DefaultExpression(exp);
            }
            expressionManager.update(var, 1);
            fValue = var.getValue();
            processChildren(fValue);
        }
        return fChildren;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.debug.core.model.IValue#hasVariables()
     */
    public boolean hasVariables() throws DebugException {
        return fHasChildren;
    }

    public void updateValue(ExpressionValue value) {
        fValue = value;
        processChildren(fValue);
    }

    public boolean isPrimative() {
        return fValue.isPrimitive();
    }

    private void processChildren(ExpressionValue value) {
        Expression[] eChildren = value.getChildren();
        if (eChildren == null)
            return;
        if (eChildren.length == 0) {
            fHasChildren = true;
            fChildren = new IVariable[0];
            return;
        }

        fHasChildren = true;
        fChildren = new PHPVariable[eChildren.length];
        for (int i = 0; i < eChildren.length; i++) {
            fChildren[i] = new PHPVariable((PHPDebugTarget) getDebugTarget(), eChildren[i], fGlobal);
        }

    }
}
