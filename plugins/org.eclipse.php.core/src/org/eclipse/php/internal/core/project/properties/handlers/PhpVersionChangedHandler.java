/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.project.properties.handlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.php.core.documentModel.IWorkspaceModelListener;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.phpModel.parser.PHPWorkspaceModelManager;
import org.eclipse.php.internal.core.preferences.*;
import org.eclipse.php.internal.core.preferences.CorePreferenceConstants.Keys;

public class PhpVersionChangedHandler implements IWorkspaceModelListener {

	private HashMap projectListeners = new HashMap();
	private HashMap preferencesPropagatorListeners = new HashMap();

	private static PhpVersionChangedHandler instance = null;
	private PreferencesPropagator preferencesPropagator;
	private static final String NODES_QUALIFIER = PHPCorePlugin.ID;
	private static final IPreferenceStore store = PHPCorePlugin.getDefault().getPreferenceStore();

	private PhpVersionChangedHandler() {
		preferencesPropagator = PreferencePropagatorFactory.getInstance().getPreferencePropagator(NODES_QUALIFIER, store);
	}

	public static PhpVersionChangedHandler getInstance() {
		if (instance == null) {
			instance = new PhpVersionChangedHandler();
		}
		return instance;
	}

	public synchronized void projectModelAdded(IProject project) {
		if (project == null || projectListeners.get(project) != null) {
			return;
		}
		PHPWorkspaceModelManager.getInstance().addWorkspaceModelListener(project.getName(), instance);
		projectListeners.put(project, new ArrayList());

		//register as a listener to the PP on this project
		PreferencesPropagatorListener listener = new PreferencesPropagatorListener(project);
		preferencesPropagatorListeners.put(project, listener);
		preferencesPropagator.addPropagatorListener(listener, Keys.PHP_VERSION);
	}

	public synchronized void projectModelRemoved(IProject project) {
		projectListeners.remove(project);
		PreferencesPropagatorListener listener = (PreferencesPropagatorListener) preferencesPropagatorListeners.get(project);
		preferencesPropagator.removePropagatorListener(listener, Keys.PHP_VERSION);
		preferencesPropagatorListeners.remove(project);
	}

	public void projectModelChanged(IProject project) {
	}

	private void projectVersionChanged(IProject project, PreferencesPropagatorEvent event) {
		ArrayList listeners = (ArrayList) projectListeners.get(project);
		if (listeners != null) {
			for (Iterator iter = listeners.iterator(); iter.hasNext();) {
				IPreferencesPropagatorListener listener = (IPreferencesPropagatorListener) iter.next();
				listener.preferencesEventOccured(event);
			}
		}
	}

	private class PreferencesPropagatorListener implements IPreferencesPropagatorListener {

		private IProject project;

		public PreferencesPropagatorListener(IProject project) {
			this.project = project;
		}

		public void preferencesEventOccured(PreferencesPropagatorEvent event) {
			if (event.getNewValue() == null) {
				// We take the workspace settings since there was a move from project-specific to workspace setings.
				String newValue = PreferencesSupport.getWorkspacePreferencesValue((String) event.getKey(), PHPCorePlugin.getDefault().getPreferenceStore());
				if (newValue == null || newValue.equals(event.getOldValue())) {
					return; // No need to send a notification
				}
				event = new PreferencesPropagatorEvent(event.getSource(), event.getOldValue(), newValue, event.getKey());
			} else if (event.getOldValue() == null) {
				// In this case there was a move from the workspace setting to a project-specific setting. 
				// At this stage the new value of the project-specific will always be as the workspace, so there is
				// no need to send a notification.
				String preferencesValue = PreferencesSupport.getWorkspacePreferencesValue((String) event.getKey(), PHPCorePlugin.getDefault().getPreferenceStore());
				if (preferencesValue != null && preferencesValue.equals(event.getNewValue())) {
					return; // No need to send a notification
				}
			}
			projectVersionChanged(project, event);
		}

		public IProject getProject() {
			return project;
		}

	}

	public void addPhpVersionChangedListener(IPreferencesPropagatorListener listener) {
		IProject project = listener.getProject();
		ArrayList listeners = (ArrayList) projectListeners.get(project);
		if (listeners == null) {
			projectModelAdded(project);
			listeners = (ArrayList) projectListeners.get(project);
		}
		listeners.add(listener);
	}

	public void removePhpVersionChangedListener(IPreferencesPropagatorListener listener) {
		IProject project = listener.getProject();
		ArrayList listeners = (ArrayList) projectListeners.get(project);
		if (listeners != null) {
			listeners.remove(listener);
		}
	}

}