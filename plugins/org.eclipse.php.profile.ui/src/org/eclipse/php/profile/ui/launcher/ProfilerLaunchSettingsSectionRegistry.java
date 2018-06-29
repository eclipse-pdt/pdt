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
package org.eclipse.php.profile.ui.launcher;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.*;
import org.eclipse.php.profile.ui.ProfilerUiPlugin;

/**
 * Profiler launch settings sections registry.
 * 
 * @author Bartlomiej Laczkowski
 */
public class ProfilerLaunchSettingsSectionRegistry {

	private static List<Entry> entries;

	private class Entry {

		IConfigurationElement element;
		String id;
		String overridenSectionId;
		String profilerId;
		String launchTypeId;

		public Entry(IConfigurationElement element, String id, String overridenSectionId, String debuggerId,
				String launchTypeId) {
			this.element = element;
			this.id = id;
			this.profilerId = debuggerId;
			this.launchTypeId = launchTypeId;
			this.overridenSectionId = overridenSectionId;
		}

	}

	private static final String PROP_ID = "id"; //$NON-NLS-1$
	private static final String PROP_OVERRIDES = "overrides"; //$NON-NLS-1$
	private static final String PROP_PROFILER_ID = "profilerId"; //$NON-NLS-1$
	private static final String PROP_LAUNCH_TYPE_ID = "launchTypeId"; //$NON-NLS-1$
	private static final String PROP_SECTION = "section"; //$NON-NLS-1$

	/**
	 * The name of extension point to read settings sections from
	 */
	public static final String EXTENSION_POINT_ID = ProfilerUiPlugin.getDefault().getBundle().getSymbolicName()
			+ ".phpProfilerLaunchSettingsSections"; //$NON-NLS-1$

	/**
	 * The default instance for reading registry
	 */
	private static ProfilerLaunchSettingsSectionRegistry instance;

	/**
	 * Returns all loaded entries.
	 */
	private static final List<Entry> getEntries() {
		if (entries == null) {
			entries = getDefault().readFromExtensionPoint();
		}
		return entries;
	}

	/**
	 * Gets settings section by combination of corresponding profiler ID and launch
	 * type ID.
	 * 
	 * @param profilerId
	 * @param launchTypeId
	 * @return settings section
	 */
	public static final IProfilerLaunchSettingsSection getSection(String profilerId, String launchTypeId) {
		for (Entry entry : getEntries()) {
			if (entry.profilerId.equals(profilerId) && entry.launchTypeId.equals(launchTypeId)) {
				IProfilerLaunchSettingsSection settingsSection = null;
				try {
					settingsSection = (IProfilerLaunchSettingsSection) createInstance(entry.element, PROP_SECTION,
							IProfilerLaunchSettingsSection.class);
				} catch (CoreException e) {
					ProfilerUiPlugin.log(e);
				}
				return settingsSection;
			}
		}
		return null;
	}

	private static ProfilerLaunchSettingsSectionRegistry getDefault() {
		if (instance == null) {
			instance = new ProfilerLaunchSettingsSectionRegistry();
		}
		return instance;
	}

	private List<Entry> readFromExtensionPoint() {
		final List<Entry> entries = new ArrayList<>();
		IConfigurationElement[] configurationElements = Platform.getExtensionRegistry()
				.getConfigurationElementsFor(EXTENSION_POINT_ID);
		for (final IConfigurationElement element : configurationElements) {
			String id = element.getAttribute(PROP_ID);
			String overridenSectionId = element.getAttribute(PROP_OVERRIDES);
			String profilerId = element.getAttribute(PROP_PROFILER_ID);
			String launchTypeId = element.getAttribute(PROP_LAUNCH_TYPE_ID);
			entries.add(new Entry(element, id, overridenSectionId, profilerId, launchTypeId));
		}
		return filterEntries(entries);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static Object createInstance(IConfigurationElement element, String propertyName, Class instanceClass)
			throws CoreException {
		final Object object = element.createExecutableExtension(propertyName);
		if (!instanceClass.isAssignableFrom(object.getClass())) {
			String message = String.format("Invalid typecast for %s", //$NON-NLS-1$
					element.getAttribute(propertyName));
			IStatus status = new Status(IStatus.ERROR, ProfilerUiPlugin.getDefault().getBundle().getSymbolicName(),
					message);
			throw new CoreException(status);
		}
		return object;
	}

	private List<Entry> filterEntries(List<Entry> entries) {
		List<Entry> topHierarchyEntries = new ArrayList<>();
		for (Entry entryToCheck : entries) {
			boolean isTopHierarchy = true;
			for (Entry entry : entries) {
				if (entryToCheck.id.equals(entry.overridenSectionId)) {
					isTopHierarchy = false;
					break;
				}
			}
			if (isTopHierarchy) {
				topHierarchyEntries.add(entryToCheck);
			}
		}
		return topHierarchyEntries;
	}

}
