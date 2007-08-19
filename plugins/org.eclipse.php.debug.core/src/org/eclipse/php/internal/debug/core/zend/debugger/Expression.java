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
package org.eclipse.php.internal.debug.core.zend.debugger;

public interface Expression {

    public String[] getName();

    public String getLastName();

    public String getFullName();

    public void setValue(ExpressionValue value);

    public ExpressionValue getValue();

    public Expression createChildExpression(String name, String representation);

}