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
package org.eclipse.php.internal.core.preferences;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.php.internal.core.Logger;

public class PreferencesSupport {

	private Map<IProject, ProjectScope> projectToScope;
	private String nodeQualifier;

	/**
	 * Constructs a new PreferencesSupport.
	 * 
	 * @param nodeQualifier
	 *            A string qualifier for the node (for example:
	 *            PHPCorePlugin.ID)
	 * @param preferenceStore
	 *            The relevant preferences store.
	 */
	public PreferencesSupport(String nodeQualifier) {
		this.nodeQualifier = nodeQualifier;
		projectToScope = new HashMap<IProject, ProjectScope>();
	}

	/**
	 * Returns the project-specific value, or null if there is no node for the
	 * project scope.
	 * 
	 * @param key
	 *            The preferences key
	 * @param def
	 *            The default value to return.
	 * @param project
	 *            The IProject
	 * @return The project-specific value for the given key.
	 */
	public String getProjectSpecificPreferencesValue(String key, String def, IProject project) {
		assert project != null;
		IEclipsePreferences node = getPreferences(project);
		if (node != null) {
			return node.get(key, def);
		}
		return null;
	}

	/**
	 * Returns the project-specific value, or null if there is no node for the
	 * project scope.
	 * 
	 * @param key
	 *            The preferences key
	 * @param def
	 *            The default value to return.
	 * @param project
	 *            The IProject
	 * @return The project-specific value for the given key.
	 */
	public boolean getProjectSpecificBooleanPreferencesValue(String key, boolean def, IProject project) {
		assert project != null;

		IEclipsePreferences node = getPreferences(project);
		if (node != null) {
			return node.getBoolean(key, def);
		}
		return def;
	}

	private IEclipsePreferences getPreferences(IProject project) {
		assert project != null;
		ProjectScope scope = projectToScope.get(project);
		if (scope == null) {
			scope = new ProjectScope(project);
			projectToScope.put(project, scope);
		}
		return scope.getNode(nodeQualifier);
	}

	/**
	 * Returns the value for the key by first searching for it as a
	 * project-specific and if it is undefined as such, search it as a workspace
	 * property.
	 * 
	 * @param key
	 *            The preferences key.
	 * @param def
	 *            The default value to return.
	 * @param project
	 *            The IProject (may be null).
	 * @return Returns the value for the key.
	 * @see #getProjectSpecificPreferencesValue(String, String, IProject)
	 * @see #getWorkspacePreferencesValue(String)
	 * @see #getWorkspacePreferencesValue(String, String)
	 */
	public String getPreferencesValue(String key, String def, IProject project) {
		if (project == null) {
			return getWorkspacePreferencesValue(key);
		}
		String projectSpecificPreferencesValue = getProjectSpecificPreferencesValue(key, def, project);
		if (projectSpecificPreferencesValue == null) {
			return getWorkspacePreferencesValue(key);
		}

		return projectSpecificPreferencesValue;
	}

	/**
	 * Returns the value for the key, as found in the preferences store.
	 * 
	 * @param key
	 * @return
	 */
	public String getWorkspacePreferencesValue(String key) {
		return Platform.getPreferencesService().getString(nodeQualifier, key, null, null);
	}

	/**
	 * Returns the value for the key, as found in the given preferences store.
	 * 
	 * @param qualifier
	 * @param key
	 * @return
	 */
	public static String getWorkspacePreferencesValue(String qualifier, String key) {
		return Platform.getPreferencesService().getString(qualifier, key, null, null);
	}

	/**
	 * Returns the project-specific value, or null if there is no node for the
	 * project scope.
	 * 
	 * @param key
	 *            The preferences key
	 * @param value
	 *            The preference value.
	 * @param project
	 *            The IProject
	 * @return boolean When the value was set.
	 */
	public boolean setProjectSpecificPreferencesValue(String key, String value, IProject project) {
		assert project != null;
		if (!project.exists()) {
			return false;
		}
		ProjectScope scope = projectToScope.get(project);
		if (scope == null) {
			scope = new ProjectScope(project);
			projectToScope.put(project, scope);
		}
		IEclipsePreferences node = scope.getNode(nodeQualifier);
		if (node != null) {
			node.put(key, value);
			try {
				node.flush();
			} catch (Exception e) {
				Logger.logException(e);
			}
			return true;
		}
		return false;
	}

}
