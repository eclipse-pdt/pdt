/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.debug.core.zend.debugger;

public interface ExpressionsManager {

	public byte[] getExpressionValue(Expression expression, int depth);

	public boolean assignValue(Expression expression, String value, int depth);

	public Expression[] getCurrentVariables(int depth);

	public Expression buildExpression(String name);

	public void update(Expression expression, int depth);

}