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

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.IPreferenceChangeListener;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.PreferenceChangeEvent;
import org.eclipse.php.internal.core.PHPCorePlugin;

/**
 * ProjectPreferencesPropagator for propagation of preferences events that
 * arrive as a result from changes in the the project-specific preferences
 * nodes.
 * 
 * @author shalom
 */
public class ProjectPreferencesPropagator extends AbstractPreferencesPropagator {

	private IProject project;
	private IPreferenceChangeListener preferenceChangeListener;
	private ProjectScope scope;
	private String nodeQualifier;

	/**
	 * Constructs a new ProjectPreferencesPropagator.
	 * 
	 * @param project
	 *            The project to monitor.
	 * @param nodeQualifier
	 *            The plugin identifier
	 */
	public ProjectPreferencesPropagator(IProject project, String nodeQualifier) {
		this.project = project;
		this.nodeQualifier = nodeQualifier;
		install();
	}

	/**
	 * Install the preferences propagator.
	 */
	protected synchronized void install() {
		if (isInstalled) {
			return;
		}
		scope = new ProjectScope(project);
		preferenceChangeListener = new InnerPreferenceChangeListener();
		scope.getNode(nodeQualifier).addPreferenceChangeListener(
				preferenceChangeListener);
		super.install();
	}

	/**
	 * Uninstall the preferences propagator.
	 */
	protected synchronized void uninstall() {
		if (!isInstalled) {
			return;
		}
		try {
			IEclipsePreferences prefNode = scope.getNode(nodeQualifier);
			prefNode.removePreferenceChangeListener(preferenceChangeListener);
		} catch (Exception e) {
			// do nothing
		} finally {
			scope = null;
			preferenceChangeListener = null;
			super.uninstall();
		}
	}

	/**
	 * Removes and returns the list of listeners assigned to the preferences
	 * key, or null if non exists.
	 * 
	 * @param preferencesKey
	 *            The key that the listeners listen to.
	 * @return The list of listeners assigned for the key, or null if non
	 *         exists.
	 */
	public List removePropagatorListeners(String preferencesKey) {
		return (List) listenersMap.remove(preferencesKey);
	}

	/**
	 * Notify a PreferencesPropagatorEvent to all the relevant listeners.
	 */
	public void notifyPropagatorEvent(PreferencesPropagatorEvent event) {
		notifyEvent((String) event.getKey(), event.getOldValue(), event
				.getNewValue());
	}

	/*
	 * Notify a PreferenceChangeEvent to all the relevant listeners.
	 */
	private void notifyPropagatorEvent(PreferenceChangeEvent event) {
		notifyEvent(event.getKey(), event.getOldValue(), event.getNewValue());
	}

	private void notifyEvent(String key, Object oldValue, Object newValue) {
		List listeners = getPropagatorListeners(key);
		if (listeners != null) {
			// We assume that null value in the new-value means that the user
			// selected and applied a move
			// between the project-specific to the workspace preferences.
			// In this case, we take the workspace preferences and compare with
			// the old value. We notify the
			// event only if the values differ.
			if (newValue == null) {
				// Take the value from the workspace preferences store.
				newValue = getWorkspaceProperty(key);
				if (newValue != null && newValue.equals(oldValue)) {
					return;
				}
			}
			PreferencesPropagatorEvent e = new PreferencesPropagatorEvent(
					project, oldValue, newValue, key);

			// Notify
			IPreferencesPropagatorListener[] allListeners = new IPreferencesPropagatorListener[listeners
					.size()];
			listeners.toArray(allListeners);
			for (IPreferencesPropagatorListener element : allListeners) {
				element.preferencesEventOccured(e);
			}
		}
	}

	/*
	 * Returns a property value defined under the PHPCorePlugin preferences
	 * store.
	 * 
	 * @param id The property id.
	 * 
	 * @return The String value of the property.
	 */
	public String getWorkspaceProperty(String id) {
		return PHPCorePlugin.getDefault().getPluginPreferences().getString(id);
	}

	/*
	 * Inner listener for the project scope preferences changes.
	 */
	private class InnerPreferenceChangeListener implements
			IPreferenceChangeListener {

		public void preferenceChange(PreferenceChangeEvent event) {
			notifyPropagatorEvent(event);
		}
	}

}
