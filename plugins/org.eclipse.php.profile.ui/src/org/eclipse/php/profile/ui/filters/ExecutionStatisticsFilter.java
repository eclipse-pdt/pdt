/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.profile.ui.filters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.php.internal.core.util.preferences.IXMLPreferencesStorable;

/**
 * Execution statistics filter.
 */
public class ExecutionStatisticsFilter extends ViewerFilter implements IXMLPreferencesStorable {

	private String fName;
	private String fId;
	private ExecutionStatisticsFilterString fFilterString;
	private ExecutionStatisticsFieldFilter fFieldFilter;
	private ExecutionStatisticsFilterCondition[] fFilterConditions;
	private boolean fIsRemovable;

	public ExecutionStatisticsFilter() {
		fIsRemovable = true;
	}

	public String getName() {
		return fName;
	}

	public String getId() {
		return fId;
	}

	public boolean isRemovable() {
		return fIsRemovable;
	}

	public void setRemovable(boolean removable) {
		fIsRemovable = removable;
	}

	public ExecutionStatisticsFilterString getFilterString() {
		return fFilterString;
	}

	public ExecutionStatisticsFieldFilter getFieldFilter() {
		return fFieldFilter;
	}

	public ExecutionStatisticsFilterCondition[] getFilterConditions() {
		return fFilterConditions;
	}

	public void setName(String name) {
		fName = name;
	}

	public void setId(String id) {
		fId = id;
	}

	public void setFilterString(ExecutionStatisticsFilterString filterString) {
		fFilterString = filterString;
	}

	public void setFieldFilter(ExecutionStatisticsFieldFilter fieldFilter) {
		fFieldFilter = fieldFilter;
	}

	public void setFilterConditions(ExecutionStatisticsFilterCondition[] filterConditions) {
		fFilterConditions = filterConditions;
	}

	@Override
	public Map<String, Object> storeToMap() {
		Map<String, Object> map = new HashMap<>(4);
		map.put("name", fName); //$NON-NLS-1$
		if (fFilterString != null) {
			map.put("filterString", fFilterString.storeToMap()); //$NON-NLS-1$
		}
		if (fFieldFilter != null) {
			map.put("fieldFilter", fFieldFilter.storeToMap()); //$NON-NLS-1$
		}
		if (fFilterConditions != null) {
			for (int i = 0; i < fFilterConditions.length; ++i) {
				map.put("condition" + i, fFilterConditions[i].storeToMap()); //$NON-NLS-1$
			}
		}

		Map<String, Object> filter = new HashMap<>(1);
		filter.put("filter", map); //$NON-NLS-1$
		return filter;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void restoreFromMap(Map<String, Object> map) {
		Map<?, ?> filter = (Map<?, ?>) map.get("filter"); //$NON-NLS-1$
		if (filter != null) {
			fName = (String) filter.get("name"); //$NON-NLS-1$
			Object obj = filter.get("filterString"); //$NON-NLS-1$
			if (obj != null) {
				fFilterString = new ExecutionStatisticsFilterString();
				fFilterString.restoreFromMap((Map<String, Object>) obj);
			}
			obj = filter.get("fieldFilter"); //$NON-NLS-1$
			if (obj != null) {
				fFieldFilter = new ExecutionStatisticsFieldFilter();
				fFieldFilter.restoreFromMap((Map<String, Object>) obj);
			}
			List<ExecutionStatisticsFilterCondition> conditions = new ArrayList<>();
			for (int i = 0; (obj = filter.get("condition" + i)) != null; ++i) { //$NON-NLS-1$
				ExecutionStatisticsFilterCondition cond = new ExecutionStatisticsFilterCondition();
				cond.restoreFromMap((Map<String, Object>) obj);
				conditions.add(cond);
			}
			if (conditions.size() > 0) {
				fFilterConditions = conditions.toArray(new ExecutionStatisticsFilterCondition[conditions.size()]);
			}
		}
	}

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		boolean select = true;
		if (fFilterString != null) {
			select &= fFilterString.select(viewer, parentElement, element);
		}
		if (select && fFilterConditions != null) {
			for (int i = 0; select && i < fFilterConditions.length; ++i) {
				select &= fFilterConditions[i].select(viewer, parentElement, element);
			}
		}
		return select;
	}

	@Override
	public Object[] filter(Viewer viewer, Object parent, Object[] elements) {
		Object[] filteredElements = super.filter(viewer, parent, elements);
		if (fFieldFilter != null) {
			filteredElements = fFieldFilter.filter(viewer, parent, filteredElements);
		}
		return filteredElements;
	}
}
