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

import static org.eclipse.php.internal.debug.core.model.IPHPDataType.DataType.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.php.internal.debug.core.model.IPHPDataType;
import org.eclipse.php.internal.debug.core.preferences.PHPProjectPreferences;

public class ExpressionValue implements IPHPDataType {

	public static final ExpressionValue NULL_VALUE = new ExpressionValue(PHP_NULL, null, "null", null); //$NON-NLS-1$

	protected DataType type;
	protected Expression[] children;
	protected int childrenCount = 0;
	protected Object value;
	protected String valueAsString;

	public ExpressionValue(DataType type, Object value, String valueAsString, Expression[] children) {
		this.type = type;
		this.value = value;
		this.valueAsString = valueAsString;
		this.children = children;
	}

	public ExpressionValue(DataType type, Object value, String valueAsString, Expression[] children,
			int childrenCount) {
		this.type = type;
		this.value = value;
		this.valueAsString = valueAsString;
		this.children = children;
		this.childrenCount = childrenCount;
	}

	public DataType getDataType() {
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
		return type == PHP_NULL;
	}

	public boolean isPrimitive() {
		return type == PHP_NULL || (type != PHP_ARRAY && type != PHP_OBJECT && type != PHP_VIRTUAL_CLASS);
	}

}