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
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.php.internal.core.util.preferences.IXMLPreferencesStorable;
import org.eclipse.php.internal.core.util.preferences.XMLPreferencesReader;
import org.eclipse.php.internal.core.util.preferences.XMLPreferencesWriter;
import org.eclipse.php.internal.ui.preferences.util.Key;
import org.eclipse.php.profile.ui.ProfilerUiPlugin;
import org.eclipse.php.profile.ui.preferences.PreferenceKeys;
import org.eclipse.ui.preferences.IWorkingCopyManager;

/**
 * Execution statistics filter registry.
 */
public class ExecutionStatisticsFiltersRegistry {

	static class XMLPreferencesReaderUI extends XMLPreferencesReader {

		/**
		 * Reads a map of elements from the IPreferenceStore by a given key.
		 * 
		 * @param store
		 * @param prefsKey
		 * @return
		 */
		@SuppressWarnings("unchecked")
		public static Map<String, Object>[] read(IPreferenceStore store, String prefsKey) {
			List<Map<String, Object>> maps = new ArrayList<>();
			StringTokenizer st = new StringTokenizer(store.getString(prefsKey), String.valueOf(DELIMITER));
			while (st.hasMoreTokens()) {
				maps.add(read(st.nextToken(), false));
			}
			return maps.toArray(new Map[maps.size()]);
		}

	}

	static class XMLPreferencesWriterUI extends XMLPreferencesWriter {

		/**
		 * Writes a group of IXMLPreferencesStorables to the given the project
		 * properties.
		 * 
		 * @param prefsKey
		 *            The key to store by.
		 * @param objects
		 *            The IXMLPreferencesStorables to store.
		 * @param projectScope
		 *            The project Scope
		 * @param workingCopyManager
		 */
		public static void write(Key prefsKey, IXMLPreferencesStorable[] objects, ProjectScope projectScope,
				IWorkingCopyManager workingCopyManager) {
			StringBuilder sb = new StringBuilder();
			appendDelimitedString(sb, objects);
			prefsKey.setStoredValue(projectScope, sb.toString(), workingCopyManager);

		}

		/**
		 * Writes an IXMLPreferencesStorables to the given IPreferenceStore.
		 * 
		 * @param store
		 *            An IPreferenceStore instance
		 * @param prefsKey
		 *            The key to store by.
		 * @param object
		 *            The IXMLPreferencesStorables to store.
		 */
		public static void write(IPreferenceStore store, String prefsKey, IXMLPreferencesStorable object) {
			StringBuilder sb = new StringBuilder();
			write(sb, object.storeToMap());
			store.setValue(prefsKey, sb.toString());
		}

		/**
		 * Writes a group of IXMLPreferencesStorables to the given IPreferenceStore.
		 * 
		 * @param store
		 *            An IPreferenceStore instance
		 * @param prefsKey
		 *            The key to store by.
		 * @param objects
		 *            The IXMLPreferencesStorables to store.
		 */
		public static void write(IPreferenceStore store, String prefsKey, IXMLPreferencesStorable[] objects) {
			StringBuilder sb = new StringBuilder();
			appendDelimitedString(sb, objects);
			store.setValue(prefsKey, sb.toString());
		}
	}

	private static final String EXTENSION_POINT_NAME = "phpProfilerExecutionStatisticsFilters"; //$NON-NLS-1$

	private static final String FILTER_TAG = "filter"; //$NON-NLS-1$
	private static final String ID_ATTRIBUTE = "id"; //$NON-NLS-1$
	private static final String NAME_ATTRIBUTE = "name"; //$NON-NLS-1$

	private static final String STANDARD_FILTER_TAG = "standardFilter"; //$NON-NLS-1$
	private static final String FILTER_STRING_TAG = "filterString"; //$NON-NLS-1$
	private static final String STRING_ATTRIBUTE = "string"; //$NON-NLS-1$
	private static final String CASE_SENSITIVE_ATTRIBUTE = "caseSensitive"; //$NON-NLS-1$
	private static final String FILTER_BY_ATTRIBUTE = "filterBy"; //$NON-NLS-1$

	private static final String FIELD_FILTER_TAG = "fieldFilter"; //$NON-NLS-1$
	private static final String DESCRIPTOR_ATTRIBUTE = "descriptor"; //$NON-NLS-1$
	private static final String NUMBER_ATTRIBUTE = "number"; //$NON-NLS-1$
	private static final String FIELD_ATTRIBUTE = "field"; //$NON-NLS-1$

	private static final String ADVANCED_FILTER_TAG = "advancedFilter"; //$NON-NLS-1$
	private static final String CONDITION_TAG = "condition"; //$NON-NLS-1$
	private static final String ATTRIBUTE_ATTRIBUTE = "attribute"; //$NON-NLS-1$
	private static final String OPERATOR_ATTRIBUTE = "operator"; //$NON-NLS-1$
	private static final String VALUE_ATTRIBUTE = "value"; //$NON-NLS-1$

	private static ExecutionStatisticsFilter[] fFilters;

	private ExecutionStatisticsFiltersRegistry() {
	}

	public static ExecutionStatisticsFilter[] getFilters() {
		if (fFilters == null) {
			List<ExecutionStatisticsFilter> filters = new ArrayList<>();
			getFilters(filters);
			fFilters = filters.toArray(new ExecutionStatisticsFilter[filters.size()]);
		}
		return fFilters;
	}

	public static void getFilters(List<ExecutionStatisticsFilter> filters) {
		initFromPreferences(filters);
		if (filters.isEmpty()) {
			initFromExtensions(filters);
		}
	}

	public static ExecutionStatisticsFilter getFilterByName(String name) {
		if (fFilters == null) {
			getFilters();
		}
		for (int i = 0; i < fFilters.length; ++i) {
			if (fFilters[i].getName().equals(name)) {
				return fFilters[i];
			}
		}
		return null;
	}

	public static void saveFilters(ExecutionStatisticsFilter[] filters) {
		fFilters = filters;
		XMLPreferencesWriterUI.write(ProfilerUiPlugin.getDefault().getPreferenceStore(),
				PreferenceKeys.EXECUTION_STATISTICS_VIEW_FILTERS, filters);
	}

	private static void initFromPreferences(List<ExecutionStatisticsFilter> filters) {
		Map<String, Object>[] maps = XMLPreferencesReaderUI.read(ProfilerUiPlugin.getDefault().getPreferenceStore(),
				PreferenceKeys.EXECUTION_STATISTICS_VIEW_FILTERS);
		if (maps.length > 0) {
			for (int i = 0; i < maps.length; ++i) {
				ExecutionStatisticsFilter filter = new ExecutionStatisticsFilter();
				filter.restoreFromMap(maps[i]);
				filters.add(filter);
			}
		}
	}

	/**
	 * Initialize filters contributed by extensions.
	 * 
	 * @param List
	 *            result
	 */
	private static void initFromExtensions(List<ExecutionStatisticsFilter> filters) {
		IConfigurationElement[] elements = Platform.getExtensionRegistry()
				.getConfigurationElementsFor(ProfilerUiPlugin.ID, EXTENSION_POINT_NAME);

		for (IConfigurationElement element : elements) {
			if (FILTER_TAG.equals(element.getName())) {
				IConfigurationElement[] children = element.getChildren();

				if (children.length > 0) {
					ExecutionStatisticsFilter filter = new ExecutionStatisticsFilter();
					filter.setName(element.getAttribute(NAME_ATTRIBUTE));
					filter.setId(element.getAttribute(ID_ATTRIBUTE));
					filter.setRemovable(false);

					for (int j = 0; j < children.length; ++j) {
						if (STANDARD_FILTER_TAG.equals(children[j].getName())) {
							IConfigurationElement[] filterStringElements = children[j].getChildren(FILTER_STRING_TAG);
							if (filterStringElements.length > 0) {
								filter.setFilterString(new ExecutionStatisticsFilterString(
										filterStringElements[0].getAttribute(STRING_ATTRIBUTE),
										filterStringElements[0].getAttribute(FILTER_BY_ATTRIBUTE),
										"true".equals(filterStringElements[0].getAttribute(CASE_SENSITIVE_ATTRIBUTE)))); //$NON-NLS-1$
							}

							IConfigurationElement[] fieldFilterElements = children[j].getChildren(FIELD_FILTER_TAG);
							if (fieldFilterElements.length > 0) {
								filter.setFieldFilter(new ExecutionStatisticsFieldFilter(
										fieldFilterElements[0].getAttribute(DESCRIPTOR_ATTRIBUTE),
										Integer.parseInt(fieldFilterElements[0].getAttribute(NUMBER_ATTRIBUTE)),
										fieldFilterElements[0].getAttribute(FIELD_ATTRIBUTE)));
							}

						} else if (ADVANCED_FILTER_TAG.equals(children[j].getName())) {
							IConfigurationElement[] conditionElements = children[j].getChildren(CONDITION_TAG);
							List<ExecutionStatisticsFilterCondition> conditions = new ArrayList<>(
									conditionElements.length);
							for (int c = 0; c < conditionElements.length; ++c) {
								conditions.add(new ExecutionStatisticsFilterCondition(
										conditionElements[c].getAttribute(ATTRIBUTE_ATTRIBUTE),
										conditionElements[c].getAttribute(OPERATOR_ATTRIBUTE),
										conditionElements[c].getAttribute(VALUE_ATTRIBUTE)));
							}
							filter.setFilterConditions(
									conditions.toArray(new ExecutionStatisticsFilterCondition[conditions.size()]));
						}
					}
					filters.add(filter);
				}
			}
		}
	}
}
