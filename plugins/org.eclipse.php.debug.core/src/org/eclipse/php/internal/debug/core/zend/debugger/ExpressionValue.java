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

	public static final int NULL_TYPE = 0;
	public static final int INT_TYPE = 1;
	public static final int STRING_TYPE = 2;
	public static final int BOOLEAN_TYPE = 3;
	public static final int DOUBLE_TYPE = 4;
	public static final int ARRAY_TYPE = 5;
	public static final int OBJECT_TYPE = 6;
	public static final int RESOURCE_TYPE = 7;
	public static final int VIRTUAL_CLASS_TYPE = 8;

	public static final ExpressionValue NULL_VALUE = new ExpressionValue(
			NULL_TYPE, null, "null", null); //$NON-NLS-1$

	protected int type;
	protected Expression[] children;
	protected int childrenCount = 0;
	protected Object value;
	protected String valueAsString;

	public ExpressionValue(int type, Object value, String valueAsString,
			Expression[] children) {
		this.type = type;
		this.value = value;
		this.valueAsString = valueAsString;
		this.children = children;
	}

	public ExpressionValue(int type, Object value, String valueAsString,
			Expression[] children, int childrenCount) {
		this.type = type;
		this.value = value;
		this.valueAsString = valueAsString;
		this.children = children;
		this.childrenCount = childrenCount;
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
		/*
		 * TODO - sorting like this should be removed in the future after adding
		 * standardized sorters for variables in VariablesUtil class.
		 */
		return sort(children);
	}

	public int getChildrenCount() {
		return childrenCount;
	}

	public static Expression[] sort(Expression[] children) {
		if (PHPProjectPreferences.isSortByName()) {
			List<Expression> list = new ArrayList<Expression>();
			for (Expression expression : children) {
				list.add(expression);
			}
			Collections.sort(list, new Comparator<Expression>() {
				public int compare(Expression o1, Expression o2) {
					String o1name = o1.getLastName();
					int o1idx = o1name.lastIndexOf(':');
					if (o1idx != -1)
						o1name = o1name.substring(o1idx + 1);
					String o2name = o2.getLastName();
					int o2idx = o2name.lastIndexOf(':');
					if (o2idx != -1)
						o2name = o2name.substring(o2idx + 1);
					return o1name.compareToIgnoreCase(o2name);
				}
			});
			return list.toArray(new Expression[list.size()]);
		}
		return children;
	}

	public boolean isNull() {
		return type == NULL_TYPE;
	}

	public boolean isPrimitive() {
		return type == NULL_TYPE
				|| (type != ARRAY_TYPE && type != OBJECT_TYPE && type != VIRTUAL_CLASS_TYPE);
	}

}