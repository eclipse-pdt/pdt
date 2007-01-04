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
package org.eclipse.php.debug.core.debugger;

public interface ExpressionsManager {

    public byte[] getExpressionValue(Expression expression, int depth);

    public boolean assignValue(Expression expression, String value, int depth);

    public Expression[] getLocalVariables();

    public Expression[] getGlobalVariables();

    public Expression[] getLocalVariables(int depth);

    public Expression[] getGlobalVariables(int depth);

    public Expression buildExpression(String name);

    public void update(Expression expression, int depth);
}