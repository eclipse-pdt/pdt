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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Expression value for objects.
 * 
 * @author Bartlomiej Laczkowski
 */
public class ObjectExpressionValue extends ExpressionValue {

	private boolean extended = false;

	/**
	 * Creates new expression value for OBJECT_TYPE.
	 * 
	 * @param value
	 * @param valueAsString
	 * @param children
	 */
	public ObjectExpressionValue(Object value, String valueAsString,
			Expression[] children) {
		super(OBJECT_TYPE, value, valueAsString, children);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.debug.core.zend.debugger.ExpressionValue#
	 * getOriChildren()
	 */
	@Override
	public Expression[] getOriChildren() {
		if (!extended)
			prependStatics();
		return children;
	}

	/**
	 * Prepends static properties to the array with child elements.
	 */
	protected void prependStatics() {
		List<Expression> extension = new ArrayList<Expression>();
		Expression[] statics = fetchStatics();
		if (statics != null)
			extension.addAll(Arrays.asList(statics));
		if (children != null)
			extension.addAll(Arrays.asList(children));
		children = extension.toArray(new Expression[extension.size()]);
		extended = true;
	}

	/**
	 * Fetches static properties for given object.
	 * 
	 * @return an array of static properties
	 */
	protected Expression[] fetchStatics() {
		String className = (String) getValue();
		ExpressionsManager expressionsManager = DefaultExpressionsManager
				.getCurrent();
		if (expressionsManager != null) {
			Expression statics = new StaticsExpression(className);
			expressionsManager.update(statics, 1);
			return statics.getValue().getChildren();
		}
		return null;
	}

}
