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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.php.internal.debug.core.preferences.PHPProjectPreferences;

public class ExpressionValue {

	public static int NULL_TYPE = 0;
	public static int INT_TYPE = 1;
	public static int STRING_TYPE = 2;
	public static int BOOLEAN_TYPE = 3;
	public static int DOUBLE_TYPE = 4;
	public static int ARRAY_TYPE = 5;
	public static int OBJECT_TYPE = 6;
	public static int RESOURCE_TYPE = 7;

	public static final ExpressionValue NULL_VALUE = new ExpressionValue(
			NULL_TYPE, null, "null", null); //$NON-NLS-1$

	protected int type;
	protected Expression[] children;
	protected Object value;
	protected String valueAsString;

	public ExpressionValue(int type, Object value, String valueAsString,
			Expression[] children) {
		this.type = type;
		this.value = value;
		this.valueAsString = valueAsString;
		this.children = children;
	}

	public int getType() {
		return type;
	}

	public Object getValue() {
		return value;
	}

	public String getValueAsString() {
		return valueAsString;
	}

	public Expression[] getOriChildren() {
		return children;
	}

	public Expression[] getChildren() {
		return sort(children);
	}

	public static Expression[] sort(Expression[] children) {
		if (PHPProjectPreferences.isSortByName()) {
			List<Expression> list = new ArrayList<Expression>();
			for (Expression expression : children) {
				list.add(expression);
			}
			Collections.sort(list, new Comparator<Expression>() {
				public int compare(Expression o1, Expression o2) {
					return o1.getName()[o1.getName().length - 1].compareTo(o2
							.getName()[o1.getName().length - 1]);
				}
			});
			return list.toArray(new Expression[list.size()]);
		} else {
			return children;
		}
	}

	public boolean isNull() {
		return type == NULL_TYPE;
	}

	public boolean isPrimitive() {
		return type == NULL_TYPE || (type != ARRAY_TYPE && type != OBJECT_TYPE);
	}

}