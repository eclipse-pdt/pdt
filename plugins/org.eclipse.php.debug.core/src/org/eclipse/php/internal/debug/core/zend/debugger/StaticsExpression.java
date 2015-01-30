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

import java.text.MessageFormat;

/**
 * Expression for fetching class static members.
 * 
 * @author Bartlomiej Laczkowski
 */
public class StaticsExpression extends DefaultExpression {

	private static final String GET_STATICS = "eval(''if (class_exists(\"ReflectionClass\")) return (new ReflectionClass(\"{0}\"))->getStaticProperties(); else return array();'');"; //$NON-NLS-1$

	public StaticsExpression(String className) {
		super(MessageFormat.format(GET_STATICS, className));
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.debug.core.zend.debugger.DefaultExpression#createChildExpression(java.lang.String, java.lang.String)
	 */
	@Override
	public Expression createChildExpression(String endName,
			String endRepresentation) {
		return new StaticMemberExpression(this, endName, endRepresentation);
	}

}
