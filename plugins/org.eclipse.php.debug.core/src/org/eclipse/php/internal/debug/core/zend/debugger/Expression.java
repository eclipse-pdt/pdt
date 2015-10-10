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
package org.eclipse.php.internal.debug.core.zend.debugger;

import org.eclipse.php.internal.debug.core.model.IVariableFacet;

public interface Expression extends IVariableFacet {

	public String[] getName();

	public String getLastName();

	public String getFullName();

	public void setValue(ExpressionValue value);

	public ExpressionValue getValue();

	public Expression createChildExpression(String name, String representation, Facet... facet);

}