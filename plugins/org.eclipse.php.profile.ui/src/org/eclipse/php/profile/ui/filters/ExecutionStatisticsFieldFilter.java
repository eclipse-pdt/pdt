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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.php.internal.core.util.preferences.IXMLPreferencesStorable;
import org.eclipse.php.profile.ui.ProfilerUIConstants;
import org.eclipse.php.profile.ui.views.ExecutionStatisticsSorter;

/**
 * Execution statistics field filter.
 */
public class ExecutionStatisticsFieldFilter extends ViewerFilter implements IXMLPreferencesStorable {

	// Descriptors
	public static final String DESC_HIGHEST = "Highest"; //$NON-NLS-1$
	public static final String DESC_LOWEST = "Lowest"; //$NON-NLS-1$

	// Fields
	public static final String FIELD_CALLS_COUNT = "Calls Count"; //$NON-NLS-1$
	public static final String FIELD_AVERAGE_OWN_TIME = "Average Own Time"; //$NON-NLS-1$
	public static final String FIELD_OWN_TIME = "Own Time"; //$NON-NLS-1$
	public static final String FIELD_OTHERS_TIME = "Others Time"; //$NON-NLS-1$
	public static final String FIELD_TOTAL_TIME = "Total Time"; //$NON-NLS-1$

	private String fDescriptor;
	private int fNumber;
	private String fField;

	private ExecutionStatisticsSorter fSorter;

	public ExecutionStatisticsFieldFilter() {
		fSorter = new ExecutionStatisticsSorter();
	}

	public ExecutionStatisticsFieldFilter(String descriptor, int number, String field) {
		fDescriptor = descriptor;
		fNumber = number;
		fField = field;
		fSorter = new ExecutionStatisticsSorter();
	}

	public String getDescriptor() {
		return fDescriptor;
	}

	public void setDescriptor(String descriptor) {
		fDescriptor = descriptor;
	}

	public int getNumber() {
		return fNumber;
	}

	public void setNumber(int number) {
		fNumber = number;
	}

	public String getField() {
		return fField;
	}

	public void setField(String field) {
		fField = field;
	}

	@Override
	public Map<String, Object> storeToMap() {
		Map<String, Object> map = new HashMap<>(3);
		map.put("descriptor", fDescriptor); //$NON-NLS-1$
		map.put("number", fNumber); //$NON-NLS-1$
		map.put("field", fField); //$NON-NLS-1$
		return map;
	}

	@Override
	public void restoreFromMap(Map<String, Object> map) {
		fDescriptor = (String) map.get("descriptor"); //$NON-NLS-1$
		fNumber = Integer.parseInt((String) map.get("number")); //$NON-NLS-1$
		fField = (String) map.get("field"); //$NON-NLS-1$
	}

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		return true;
	}

	@Override
	public Object[] filter(Viewer viewer, Object parent, Object[] elements) {
		if (FIELD_AVERAGE_OWN_TIME.equals(fField)) {
			fSorter.setColumn(0);
		} else if (FIELD_CALLS_COUNT.equals(fField)) {
			fSorter.setColumn(1);
		} else if (FIELD_OTHERS_TIME.equals(fField)) {
			fSorter.setColumn(2);
		} else if (FIELD_OWN_TIME.equals(fField)) {
			fSorter.setColumn(3);
		} else if (FIELD_TOTAL_TIME.equals(fField)) {
			fSorter.setColumn(4);
		}

		if (DESC_HIGHEST.equals(fDescriptor)) {
			fSorter.setOrder(ProfilerUIConstants.SORT_DESCENDING);
		} else if (DESC_LOWEST.equals(fDescriptor)) {
			fSorter.setOrder(ProfilerUIConstants.SORT_ASCENDING);
		}

		elements = elements.clone(); // we can't modify the elements of the
										// model.
		fSorter.sort(viewer, elements);

		ArrayList<Object> resultElements = new ArrayList<>(elements.length);
		int countElements = 0;
		for (int i = 0; i < elements.length && countElements < fNumber; ++i) {
			resultElements.add(elements[i]);
			if (i > 0 && fSorter.compare(viewer, elements[i - 1], elements[i]) == 0) {
				continue;
			}
			countElements++;
		}

		return resultElements.toArray(new Object[resultElements.size()]);
	}
}
