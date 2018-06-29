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

import java.io.File;
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
 * Execution statistics filter string.
 */
public class ExecutionStatisticsFilterString extends ViewerFilter implements IXMLPreferencesStorable {

	public static final String FILTER_BY_FILE_NAME = "File name"; //$NON-NLS-1$
	public static final String FILTER_BY_CLASS_NAME = "Class name"; //$NON-NLS-1$
	public static final String FILTER_BY_FUNCTION_NAME = "Function name"; //$NON-NLS-1$

	private String fString;
	private String fFilterBy;
	private boolean fIsCaseSensitive;

	public ExecutionStatisticsFilterString() {
	}

	public ExecutionStatisticsFilterString(String string, String filterBy, boolean isCaseSensitive) {
		fString = string;
		fFilterBy = filterBy;
		fIsCaseSensitive = isCaseSensitive;
	}

	public String getString() {
		return fString;
	}

	public void setString(String string) {
		fString = string;
	}

	public String getFilterBy() {
		return fFilterBy;
	}

	public void setFilterBy(String filterBy) {
		fFilterBy = filterBy;
	}

	public boolean isCaseSensitive() {
		return fIsCaseSensitive;
	}

	public void setCaseSensitive(boolean isCaseSensitive) {
		fIsCaseSensitive = isCaseSensitive;
	}

	@Override
	public Map<String, Object> storeToMap() {
		Map<String, Object> map = new HashMap<>(3);
		map.put("string", fString); //$NON-NLS-1$
		map.put("case-sensitive", new Boolean(fIsCaseSensitive)); //$NON-NLS-1$
		map.put("filter-by", fFilterBy); //$NON-NLS-1$
		return map;
	}

	@Override
	public void restoreFromMap(Map<String, Object> map) {
		fString = (String) map.get("string"); //$NON-NLS-1$
		fIsCaseSensitive = Boolean.getBoolean((String) map.get("case-sensitive")); //$NON-NLS-1$
		fFilterBy = (String) map.get("filter-by"); //$NON-NLS-1$
	}

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		Object data = ((TreeElement) element).getData();
		if (FILTER_BY_FILE_NAME.equals(fFilterBy) && data instanceof ProfilerFileData) {
			return SearchPattern.match(fString, new File(((ProfilerFileData) data).getName()).getName(),
					fIsCaseSensitive, false);
		} else if (FILTER_BY_CLASS_NAME.equals(fFilterBy) && data instanceof ProfilerClassData) {
			return SearchPattern.match(fString, ((ProfilerClassData) data).getName(), fIsCaseSensitive, false);
		} else if (FILTER_BY_FUNCTION_NAME.equals(fFilterBy) && data instanceof ProfilerFunctionData) {
			return SearchPattern.match(fString, ((ProfilerFunctionData) data).getFunctionName(), fIsCaseSensitive,
					false);
		}
		return true;
	}
}
