/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.profile.ui.filters;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.php.internal.core.util.preferences.IXMLPreferencesStorable;
import org.eclipse.php.internal.ui.util.SearchPattern;
import org.eclipse.php.profile.core.data.ProfilerClassData;
import org.eclipse.php.profile.core.data.ProfilerFileData;
import org.eclipse.php.profile.core.data.ProfilerFunctionData;
import org.eclipse.php.profile.ui.views.TreeElement;

/**
 * Execution statistics filter condition.
 */
public class ExecutionStatisticsFilterCondition extends ViewerFilter implements IXMLPreferencesStorable {

	// Attributes
	public static final String ATTR_FILE_NAME = "File Name"; //$NON-NLS-1$
	public static final String ATTR_FILE_TOTAL_TIME = "File Total Time"; //$NON-NLS-1$
	public static final String ATTR_CLASS_NAME = "Class Name"; //$NON-NLS-1$
	public static final String ATTR_CLASS_TOTAL_TIME = "Class Total Time"; //$NON-NLS-1$
	public static final String ATTR_FUNCTION_NAME = "Function Name"; //$NON-NLS-1$
	public static final String ATTR_FUNCTION_TOTAL_TIME = "Function Total Time"; //$NON-NLS-1$
	public static final String ATTR_FUNCTION_AVERAGE_OWN_TIME = "Function Average Own Time"; //$NON-NLS-1$
	public static final String ATTR_FUNCTION_OWN_TIME = "Function Own Time"; //$NON-NLS-1$
	public static final String ATTR_FUNCTION_OTHERS_TIME = "Function Others Time"; //$NON-NLS-1$
	public static final String ATTR_FUNCTION_CALLS_COUNT = "Function Calls Count"; //$NON-NLS-1$

	// Operators
	public static final String OP_EQUALS = "equals"; //$NON-NLS-1$
	public static final String OP_NOT_EQUALS = "not equals"; //$NON-NLS-1$
	public static final String OP_MATCHES = "matches"; //$NON-NLS-1$
	public static final String OP_DOESNT_MATCH = "doesn't match"; //$NON-NLS-1$
	public static final String OP_LESS_THAN = "less than"; //$NON-NLS-1$
	public static final String OP_MORE_THAN = "more than"; //$NON-NLS-1$

	private String fAttribute;
	private String fOperator;
	private String fValue;

	public ExecutionStatisticsFilterCondition() {
	}

	public ExecutionStatisticsFilterCondition(String attribute, String operator, String value) {
		fAttribute = attribute;
		fOperator = operator;
		fValue = value;
	}

	public String getAttribute() {
		return fAttribute;
	}

	public void setAttribute(String attribute) {
		fAttribute = attribute;
	}

	public String getOperator() {
		return fOperator;
	}

	public void setOperator(String operator) {
		fOperator = operator;
	}

	public String getValue() {
		return fValue;
	}

	public void setValue(String value) {
		fValue = value;
	}

	@Override
	public Map<String, Object> storeToMap() {
		HashMap<String, Object> map = new HashMap<>(3);
		map.put("attribute", fAttribute); //$NON-NLS-1$
		map.put("operator", fOperator); //$NON-NLS-1$
		map.put("value", fValue); //$NON-NLS-1$
		return map;
	}

	@Override
	public void restoreFromMap(Map<String, Object> map) {
		fAttribute = (String) map.get("attribute"); //$NON-NLS-1$
		fOperator = (String) map.get("operator"); //$NON-NLS-1$
		fValue = (String) map.get("value"); //$NON-NLS-1$
	}

	private boolean evalOperator(String operator, String str1, String str2) {
		if (OP_DOESNT_MATCH.equals(fOperator)) {
			return !SearchPattern.match(str2, str1, false, false);
		} else if (OP_MATCHES.equals(fOperator)) {
			return SearchPattern.match(str2, str1, false, false);
		} else if (OP_NOT_EQUALS.equals(fOperator)) {
			return !str2.equals(str1);
		} else if (OP_EQUALS.equals(fOperator)) {
			return str2.equals(str1);
		}
		return false;
	}

	private boolean evalOperator(String operator, double d1, double d2) {
		if (OP_LESS_THAN.equals(fOperator)) {
			return d1 < d2;
		} else if (OP_MORE_THAN.equals(fOperator)) {
			return d1 > d2;
		} else if (OP_NOT_EQUALS.equals(fOperator)) {
			return d1 != d2;
		} else if (OP_EQUALS.equals(fOperator)) {
			return d1 == d2;
		}
		return false;
	}

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		TreeElement treeElement = (TreeElement) element;
		Object data = treeElement.getData();

		if (ATTR_FILE_NAME.equals(fAttribute)) {
			if (data instanceof ProfilerFileData) {
				ProfilerFileData fileData = (ProfilerFileData) data;
				return evalOperator(fOperator, fileData.getName(), fValue);
			}
		} else if (ATTR_FILE_TOTAL_TIME.equals(fAttribute)) {
			if (data instanceof ProfilerFileData) {
				ProfilerFileData fileData = (ProfilerFileData) data;
				return evalOperator(fOperator, fileData.getTotalOwnTime(), Double.parseDouble(fValue));
			}
		} else if (ATTR_CLASS_NAME.equals(fAttribute)) {
			if (data instanceof ProfilerClassData) {
				ProfilerClassData classData = (ProfilerClassData) data;
				return evalOperator(fOperator, classData.getName(), fValue);
			}
		} else if (ATTR_CLASS_TOTAL_TIME.equals(fAttribute)) {
			if (data instanceof ProfilerClassData) {
				ProfilerClassData classData = (ProfilerClassData) data;
				return evalOperator(fOperator, classData.getTotalOwnTime(), Double.parseDouble(fValue));
			}
		} else if (ATTR_FUNCTION_NAME.equals(fAttribute)) {
			if (data instanceof ProfilerFunctionData) {
				ProfilerFunctionData functionData = (ProfilerFunctionData) data;
				return evalOperator(fOperator, functionData.getFunctionName(), fValue);
			}
		} else if (ATTR_FUNCTION_AVERAGE_OWN_TIME.equals(fAttribute)) {
			if (data instanceof ProfilerFunctionData) {
				ProfilerFunctionData functionData = (ProfilerFunctionData) data;
				return evalOperator(fOperator, functionData.getOwnTime() / functionData.getCallsCount(),
						Double.parseDouble(fValue));
			}
		} else if (ATTR_FUNCTION_OWN_TIME.equals(fAttribute)) {
			if (data instanceof ProfilerFunctionData) {
				ProfilerFunctionData functionData = (ProfilerFunctionData) data;
				return evalOperator(fOperator, functionData.getOwnTime(), Double.parseDouble(fValue));
			}
		} else if (ATTR_FUNCTION_OTHERS_TIME.equals(fAttribute)) {
			if (data instanceof ProfilerFunctionData) {
				ProfilerFunctionData functionData = (ProfilerFunctionData) data;
				return evalOperator(fOperator, functionData.getTotalTime() - functionData.getOwnTime(),
						Double.parseDouble(fValue));
			}
		} else if (ATTR_FUNCTION_TOTAL_TIME.equals(fAttribute)) {
			if (data instanceof ProfilerFunctionData) {
				ProfilerFunctionData functionData = (ProfilerFunctionData) data;
				return evalOperator(fOperator, functionData.getTotalTime(), Double.parseDouble(fValue));
			}
		} else if (ATTR_FUNCTION_CALLS_COUNT.equals(fAttribute)) {
			if (data instanceof ProfilerFunctionData) {
				ProfilerFunctionData functionData = (ProfilerFunctionData) data;
				return evalOperator(fOperator, functionData.getCallsCount(), Double.parseDouble(fValue));
			}
		}

		return true;
	}
}