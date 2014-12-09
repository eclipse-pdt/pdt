/*******************************************************************************
 * Copyright (c) 2009,2014 IBM Corporation and others.
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

/**
 * Expression representing class static member.
 * 
 * @author Bartlomiej Laczkowski
 */
public class StaticMemberExpression extends DefaultExpression {

	protected StaticMemberExpression(Expression parent, String name,
			String representation) {
		super(parent, name, representation);
	}

}
