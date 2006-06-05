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
/*
 * StackLayer.java
 *
 * Created on 22 מרץ 2001, 19:02
 */

package org.eclipse.php.debug.core.debugger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author guy
 */
public class StackLayer {

    private int depth;

    private String callerFileName;
    private int callerLineNumber;
    private String callerFunctionName;

    private String calledFileName;
    private int calledLineNumber;
    private String calledFunctionName;

    private List variables;

    /**
     * Creates new StackLayer
     */
    public StackLayer() {
        variables = new ArrayList(6);
    }

    /**
     * Creates new StackLayer
     */
    public StackLayer(int depth, String callerFileName, int callerLineNumber, String callerFunctionName, String calledFileName, int calledLineNumber, String calledFunctionName) {
        this();

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

    public void addVariable(String variableName, String value) {
        StackVariable variable = new DefaultStackVariable(variableName, getDepth());
        variable.setValue(ExpressionsValueDeserializer.deserializer(variable, value));

        variables.add(variable);
    }

    public void addVariable(Expression expression) {
        variables.add(expression);
    }

    public int getNumberOfVariables() {
        return variables.size();
    }

    public Expression[] getVariables() {
        Expression[] arrayVariables = new Expression[variables.size()];
        variables.toArray(arrayVariables);
        return arrayVariables;
    }

    public Iterator iterator() {
        return variables.iterator();
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer(20);
        buffer.append(toStringCalledFunctionLine());
        buffer.append("  " + callerFileName + " line " + (callerLineNumber + 1));
        return buffer.toString();
    }

    public String toStringCalledFunctionLine() {
        if (getCallerFunctionName() == null || getCallerFunctionName().equals("")) {
            return "";
        }
        StringBuffer buffer = new StringBuffer(getCallerFunctionName() + '(');
        for (int i = 0; i < variables.size(); i++) {
            Expression expression = (Expression) variables.get(i);
            buffer.append(" $");
            buffer.append(expression.getLastName());
            if (i != variables.size() - 1) {
                buffer.append(',');
            }
        }
        buffer.append(" )");
        return buffer.toString();
    }

    private static class DefaultStackVariable extends DefaultExpression implements StackVariable {

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

        public Expression createChildExpression(String endName, String endRepresentation) {
            return new DefaultStackVariable(this, endName, stackDepth, endRepresentation);
        }

        private DefaultStackVariable(StackVariable parent, String name, int stackDepth, String representation) {
            super(parent, name, representation);
            this.stackDepth = stackDepth;
        }

    }

}