/*******************************************************************************
 * Copyright (c) 2007 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.core.preferences.stepFilters;

import java.util.ArrayList;
import java.util.StringTokenizer;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.php.debug.core.preferences.IStepFiltersExtender;
import org.eclipse.php.internal.debug.core.IPHPDebugConstants;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.util.ElementCreationProxy;

/**
 * This Singleton class is used to bridge between the debugger and the
 * Debug Step filter Preferences.
 * @author yaronm
 *
 */
public class DebugStepFilterController implements IDebugStepFilterPrefListener {
	private static DebugStepFilterController instance = new DebugStepFilterController();
	private DebugStepFilter[] activeFilters = null;
	private DebugStepFilter[] extendedFilters = null;

	private DebugStepFilterController() {
	}

	public static DebugStepFilterController getInstance() {
		if (instance == null) {
			instance = new DebugStepFilterController();
		}
		return instance;
	}

	public boolean isFiltered(String path) {
		if (activeFilters == null) {
			activeFilters = getAllActiveFilters();
		}

		for (DebugStepFilter currentFilter : activeFilters) {
			String filterPath = currentFilter.getPath();
			if (filterPath.startsWith("*") && filterPath.endsWith("*")) {//*...*
				if (path.contains(filterPath.substring(1, filterPath.length() - 1))) {
					return true;
				}
			} else if (filterPath.startsWith("*")) {//*...
				if (path.endsWith(filterPath.substring(1))) {
					return true;
				}
			} else if (filterPath.endsWith("*")) {//...*
				if (path.startsWith(filterPath.substring(0, filterPath.length() - 1))) {
					return true;
				}
			} else {//no '*'
				//check if they have same container (project or project_folder
				if (currentFilter.getType().equals(IStepFilterTypes.PHP_PROJECT) || currentFilter.getType().equals(IStepFilterTypes.PHP_PROJECT_FOLDER)) {
					if (path.toLowerCase().startsWith(currentFilter.getPath().toLowerCase())) {
						return true;
					}
					//check if simple path pattern
				} else if (path.equalsIgnoreCase(filterPath)) {
					return true;
				}
			}
		}
		return false;
	}

	//Returns ONLY active filters
	private DebugStepFilter[] getAllActiveFilters() {
		IPreferenceStore store = PHPUiPlugin.getDefault().getPreferenceStore();
		String[] parsedFilters = parseList(store.getString(IPHPDebugConstants.PREF_STEP_FILTERS_LIST));

		ArrayList<DebugStepFilter> list = new ArrayList<DebugStepFilter>();
		for (int i = 0; i < parsedFilters.length; i++) {
			String[] tokens = parsedFilters[i].split(";");
			if (tokens.length < 3) {
				return new DebugStepFilter[0];
			}
			DebugStepFilter tempFilter = new DebugStepFilter(tokens[0], Boolean.parseBoolean(tokens[1]), Boolean.parseBoolean(tokens[2]), tokens[3]);
			if (tempFilter.isActive()) {
				list.add(tempFilter);
			}
		}

		DebugStepFilter[] result = new DebugStepFilter[list.size()];
		list.toArray(result);
		return result;
	}

	//Parses the comma separated string into an array of strings
	private String[] parseList(String listString) {
		ArrayList<String> list = new ArrayList<String>();
		StringTokenizer tokenizer = new StringTokenizer(listString, ","); //$NON-NLS-1$
		while (tokenizer.hasMoreTokens()) {
			String token = tokenizer.nextToken();
			list.add(token);
		}
		return (String[]) list.toArray(new String[list.size()]);
	}

	public void debugStepFilterModified(DebugStepFilterEvent event) {
		if (event.isDebugStepFilterEnabled()) {
			ArrayList<DebugStepFilter> list = new ArrayList<DebugStepFilter>();
			DebugStepFilter[] newFilters = event.getNewFilters();
			for (DebugStepFilter newFilter : newFilters) {
				if (newFilter.isActive()) {
					list.add(newFilter);
				}
			}
			activeFilters = new DebugStepFilter[list.size()];
			list.toArray(activeFilters);
		}
	}

	private void readExtensionsList() {
		IStepFiltersExtender extender = null;
		ArrayList<DebugStepFilter> listToAdd = new ArrayList<DebugStepFilter>();
		String stepFilterExtensionName = "org.eclipse.php.debug.core.phpDebugStepFilters"; //$NON-NLS-1$
		IConfigurationElement[] elements = Platform.getExtensionRegistry().getConfigurationElementsFor(stepFilterExtensionName);
		for (IConfigurationElement element : elements) {
			if (element.getName().equals("step_filter")) { //$NON-NLS-1$
				ElementCreationProxy ecProxy = new ElementCreationProxy(element, stepFilterExtensionName);
				extender = (IStepFiltersExtender) ecProxy.getObject();
			}

			String[] pathsToFilter = extender.getStepFiltersToAdd();
			if (pathsToFilter == null) {
				return;
			}
			
			for (String pathToAdd : pathsToFilter) {
				if ((pathToAdd != null) && pathToAdd.length() > 0) {
					listToAdd.add(new DebugStepFilter(IStepFilterTypes.PATH_PATTERN, true, true, pathToAdd));
				}
			}
		}
		extendedFilters = new DebugStepFilter[listToAdd.size()];
		listToAdd.toArray(extendedFilters);
	}

	public DebugStepFilter[] getExtendedFiltersList() {
		if (extendedFilters == null) {
			readExtensionsList();
		}
		return extendedFilters;
	}
}