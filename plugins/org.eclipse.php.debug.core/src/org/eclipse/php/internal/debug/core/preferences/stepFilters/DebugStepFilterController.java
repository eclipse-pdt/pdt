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
package org.eclipse.php.internal.debug.core.preferences.stepFilters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

import org.eclipse.core.runtime.*;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.php.internal.core.util.FileUtils;
import org.eclipse.php.internal.debug.core.IPHPDebugConstants;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.php.internal.ui.PHPUiPlugin;

/**
 * This Singleton class is used to bridge between the debugger and the Debug
 * Step filter Preferences.
 * 
 * @author yaronm
 */
public class DebugStepFilterController implements IDebugStepFilterPrefListener {
	private static final int MAX_CACHE_SIZE = 5000;
	private static DebugStepFilterController instance = null;
	private DebugStepFilter[] enabledFilters = null; // filters that are enabled
	private DebugStepFilter[] extendedFilters = null; // filters that come from
														// extension point
	private HashMap<String, Boolean> filtersCheckCache = new HashMap<String, Boolean>();

	private DebugStepFilterController() {
	}

	public static synchronized DebugStepFilterController getInstance() {
		if (instance == null) {
			instance = new DebugStepFilterController();
		}
		return instance;
	}

	/**
	 * Checks whether the given file path is filtered in the Debug Step Filters
	 * list
	 * 
	 * @param path
	 *            - a file path
	 */
	public boolean isFiltered(String path) {
		Boolean cachedFilterResult = filtersCheckCache.get(path);
		if (cachedFilterResult != null) {
			return cachedFilterResult;
		}

		if (enabledFilters == null) {
			enabledFilters = getAllEnabledFilters();
		}

		boolean filterResult = false;
		for (DebugStepFilter currentFilter : enabledFilters) {
			String filterPath = currentFilter.getPath();
			if (currentFilter.getType() == IStepFilterTypes.PATH_PATTERN) {
				if (filterPath.startsWith("*") && filterPath.endsWith("*")) {//*...* //$NON-NLS-1$ //$NON-NLS-2$
					if (path.contains(filterPath.substring(1, filterPath
							.length() - 1))) {
						filterResult = true;
						break;
					}
				} else if (filterPath.startsWith("*")) {//*... //$NON-NLS-1$
					if (path.endsWith(filterPath.substring(1))) {
						filterResult = true;
						break;
					}
				} else if (filterPath.endsWith("*")) {//...* //$NON-NLS-1$
					if (path.startsWith(filterPath.substring(0, filterPath
							.length() - 1))) {
						filterResult = true;
						break;
					}
				}// else, simply compare the exact path string
				else {// check if simple path pattern
					filterResult = FileUtils.checkIfEqualFilePaths(path,
							currentFilter.getPath());
					break;
				}
			} else {// no '*' in filter
				// check if the given path is inside a filtered container
				// (folder, project etc..)
				if ((currentFilter.getType() == IStepFilterTypes.PHP_PROJECT)
						|| (currentFilter.getType() == IStepFilterTypes.PHP_PROJECT_FOLDER)
						|| (currentFilter.getType() == IStepFilterTypes.PHP_INCLUDE_PATH_LIBRARY_FOLDER)
						|| (currentFilter.getType() == IStepFilterTypes.PHP_INCLUDE_PATH_LIBRARY)) {
					filterResult = FileUtils.checkIfContainerOfFile(
							currentFilter.getPath(), path);
					break;
				}
				// check if the given path is inside an include path variable
				// container
				else if ((currentFilter.getType() == IStepFilterTypes.PHP_INCLUDE_PATH_VAR)
						|| currentFilter.getType() == IStepFilterTypes.PHP_INCLUDE_PATH_VAR_FOLDER) {

					IPath resolvedVariablePath = DLTKCore
							.getResolvedVariablePath(new Path(currentFilter
									.getPath()));
					if (resolvedVariablePath != null) {
						String includePathVarPath = resolvedVariablePath
								.toOSString();
						filterResult = FileUtils.checkIfContainerOfFile(
								includePathVarPath, path);
					}
					break;
				}
			}
		}
		addToCache(path, filterResult);
		return filterResult;
	}

	private void addToCache(String path, boolean filterResult) {
		if (filtersCheckCache.size() > MAX_CACHE_SIZE) {
			filtersCheckCache.clear();
		}
		filtersCheckCache.put(path, filterResult);
	}

	// Returns ONLY enabled filters
	private DebugStepFilter[] getAllEnabledFilters() {
		IPreferenceStore store = PHPUiPlugin.getDefault().getPreferenceStore();
		String[] parsedFilters = parseList(store
				.getString(IPHPDebugConstants.PREF_STEP_FILTERS_LIST));

		ArrayList<DebugStepFilter> list = new ArrayList<DebugStepFilter>();
		for (int i = 0; i < parsedFilters.length; i++) {
			String[] tokens = parsedFilters[i].split("\\" //$NON-NLS-1$
					+ DebugStepFilter.FILTER_TOKENS_DELIM);
			if (tokens.length != 4) {
				return new DebugStepFilter[0];
			}
			DebugStepFilter tempFilter = new DebugStepFilter(Integer
					.parseInt(tokens[0]), Boolean.parseBoolean(tokens[1]),
					Boolean.parseBoolean(tokens[2]), tokens[3]);
			if (tempFilter.isEnabled()) {
				list.add(tempFilter);
			}
		}

		DebugStepFilter[] extendedFiltersList = getExtendedFiltersList();
		for (DebugStepFilter filterExtended : extendedFiltersList) {
			if (!list.contains(filterExtended)) {
				list.add(filterExtended);
			}
		}

		DebugStepFilter[] result = new DebugStepFilter[list.size()];
		list.toArray(result);
		return result;
	}

	// Parses the comma separated string into an array of strings
	private String[] parseList(String listString) {
		ArrayList<String> list = new ArrayList<String>();
		StringTokenizer tokenizer = new StringTokenizer(listString,
				DebugStepFilter.FILTERS_PREF_LIST_DELIM); 
		while (tokenizer.hasMoreTokens()) {
			String token = tokenizer.nextToken();
			list.add(token);
		}
		return (String[]) list.toArray(new String[list.size()]);
	}

	public void debugStepFilterModified(DebugStepFilterEvent event) {
		filtersCheckCache.clear();
		ArrayList<DebugStepFilter> list = new ArrayList<DebugStepFilter>();
		DebugStepFilter[] newFilters = event.getNewFilters();
		for (DebugStepFilter newFilter : newFilters) {
			if (newFilter.isEnabled()) {
				list.add(newFilter);
			}
		}
		enabledFilters = new DebugStepFilter[list.size()];
		list.toArray(enabledFilters);
	}

	private void readExtensionsList() {
		ArrayList<DebugStepFilter> listToAdd = new ArrayList<DebugStepFilter>();
		String stepFilterExtensionName = "org.eclipse.php.debug.core.phpDebugStepFilters"; //$NON-NLS-1$
		IConfigurationElement[] elements = Platform.getExtensionRegistry()
				.getConfigurationElementsFor(stepFilterExtensionName);
		for (IConfigurationElement element : elements) {
			if (element.getName().equals("stepFilter")) { //$NON-NLS-1$
				Boolean isEnabled = null;
				String path = null;
				int filterType = 0;
				try {
					isEnabled = Boolean.parseBoolean(element
							.getAttribute("enabled")); //$NON-NLS-1$
					path = element.getAttribute("path"); //$NON-NLS-1$
					filterType = getFilterTypeId(element.getAttribute("type")); //$NON-NLS-1$
				} catch (InvalidRegistryObjectException ire) {
					PHPDebugPlugin.log(ire);
					return;
				}
				listToAdd.add(new DebugStepFilter(filterType, isEnabled, true,
						path));
			}

		}
		extendedFilters = new DebugStepFilter[listToAdd.size()];
		listToAdd.toArray(extendedFilters);
	}

	// this is a bit complexed comparison method , but it only occurs for
	// extension points one-time
	private int getFilterTypeId(String attribute) {
		if (attribute.equals("PHP_PROJECT")) { //$NON-NLS-1$
			return IStepFilterTypes.PHP_PROJECT;
		}
		if (attribute.equals("PHP_PROJECT_FILE")) { //$NON-NLS-1$
			return IStepFilterTypes.PHP_PROJECT_FILE;
		}
		if (attribute.equals("PHP_PROJECT_FOLDER")) { //$NON-NLS-1$
			return IStepFilterTypes.PHP_PROJECT_FOLDER;
		}
		if (attribute.equals("PATH_PATTERN")) { //$NON-NLS-1$
			return IStepFilterTypes.PATH_PATTERN;
		}
		if (attribute.equals("PHP_INCLUDE_PATH_VAR")) { //$NON-NLS-1$
			return IStepFilterTypes.PHP_INCLUDE_PATH_VAR;
		}
		if (attribute.equals("PHP_INCLUDE_PATH_VAR_FILE")) { //$NON-NLS-1$
			return IStepFilterTypes.PHP_INCLUDE_PATH_VAR_FILE;
		}
		if (attribute.equals("PHP_INCLUDE_PATH_FOLDER")) { //$NON-NLS-1$
			return IStepFilterTypes.PHP_INCLUDE_PATH_VAR_FOLDER;
		}
		if (attribute.equals("PHP_INCLUDE_PATH_LIBRARY")) { //$NON-NLS-1$
			return IStepFilterTypes.PHP_INCLUDE_PATH_LIBRARY;
		}
		if (attribute.equals("PHP_INCLUDE_PATH_LIBRARY_FILE")) { //$NON-NLS-1$
			return IStepFilterTypes.PHP_INCLUDE_PATH_LIBRARY_FILE;
		}
		if (attribute.equals("PHP_INCLUDE_PATH_LIBRARY_FOLDER")) { //$NON-NLS-1$
			return IStepFilterTypes.PHP_INCLUDE_PATH_LIBRARY_FOLDER;
		}
		return 0;
	}

	public DebugStepFilter[] getExtendedFiltersList() {
		if (extendedFilters == null) {
			readExtensionsList();
		}
		return extendedFilters;
	}
}