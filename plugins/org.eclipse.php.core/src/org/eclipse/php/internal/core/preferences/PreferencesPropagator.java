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

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.INodeChangeListener;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.IPreferenceChangeListener;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.NodeChangeEvent;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.PreferenceChangeEvent;
import org.eclipse.core.runtime.preferences.InstanceScope;

/**
 * PreferencesPropagator for propagation of preferences events that arrive as a
 * result from changes in the workspace preferences store and in the
 * project-specific preferences nodes.
 * 
 * @author shalom
 */
public class PreferencesPropagator extends AbstractPreferencesPropagator {

	private Map<IProject, ProjectPreferencesPropagator> projectToPropagator;
	private Map<IProject, ProjectScope> projectToScope;
	private Map<IProject, INodeChangeListener> projectToNodeListener;
	private Map<IPreferenceChangeListener, IEclipsePreferences> preferenceChangeListeners;
	private String nodeQualifier;

	private IPreferenceChangeListener propertyChangeListener;

	/**
	 * Constructs a new PreferencesPropagator.
	 */
	protected PreferencesPropagator(String nodeQualifier) {
		this.nodeQualifier = nodeQualifier;
		install();
	}

	/**
	 * Adds an IPreferencesPropagatorListener with a preferences key to listen
	 * to.
	 * 
	 * @param listener
	 *            An IPreferencesPropagatorListener.
	 * @param preferencesKey
	 *            The preferences key that will screen the relevant changes.
	 */
	public void addPropagatorListener(IPreferencesPropagatorListener listener, String preferencesKey) {
		addNodeListener(listener.getProject(), getProjectScope(listener.getProject()));
		if (isProjectSpecific(listener.getProject(), preferencesKey)) {
			addToProjectPropagator(listener, preferencesKey);
		} else {
			super.addPropagatorListener(listener, preferencesKey);
		}

	}

	/**
	 * Removes an IPreferencesPropagatorListener that was assigned to listen to
	 * the given preferences key.
	 * 
	 * @param listener
	 *            An IPreferencesPropagatorListener.
	 * @param preferencesKey
	 *            The preferences key that is the screening key for the
	 *            IPreferencesPropagatorListener.
	 */
	public void removePropagatorListener(IPreferencesPropagatorListener listener, String preferencesKey) {
		if (isProjectSpecific(listener.getProject(), preferencesKey)) {
			removeFromProjectPropagator(listener, preferencesKey);
		} else {
			super.removePropagatorListener(listener, preferencesKey);
		}
	}

	/**
	 * Sets a list of listeners for the given preferences key. This list will
	 * replace any previous list of listeners for the key.
	 * 
	 * @param listeners
	 *            A List of listeners that contains
	 *            IPreferencesPropagatorListeners.
	 * @param preferencesKey
	 *            The preferences key that will screen the relevant changes.
	 */
	public void setPropagatorListeners(List<IPreferencesPropagatorListener> listeners, String preferencesKey) {
		super.setPropagatorListeners(listeners, preferencesKey);
	}

	/**
	 * Install the preferences propagator.
	 */
	protected synchronized void install() {
		if (isInstalled) {
			return;
		}
		projectToPropagator = new HashMap<IProject, ProjectPreferencesPropagator>();
		projectToScope = new HashMap<IProject, ProjectScope>();
		projectToNodeListener = new HashMap<IProject, INodeChangeListener>();
		preferenceChangeListeners = new HashMap<IPreferenceChangeListener, IEclipsePreferences>();
		propertyChangeListener = new WorkspacePropertyChangeListener();
		InstanceScope.INSTANCE.getNode(nodeQualifier).addPreferenceChangeListener(propertyChangeListener);
		super.install();
	}

	/**
	 * Uninstall the preferences propagator.
	 */
	protected synchronized void uninstall() {
		if (!isInstalled) {
			return;
		}
		InstanceScope.INSTANCE.getNode(nodeQualifier).removePreferenceChangeListener(propertyChangeListener);
		// remove the node listeners
		Iterator<IProject> projects = projectToNodeListener.keySet().iterator();
		try {
			while (projects.hasNext()) {
				IProject project = projects.next();
				ProjectScope scope = projectToScope.get(project);
				IEclipsePreferences node = scope.getNode(nodeQualifier);
				if (node != null) {
					((IEclipsePreferences) node.parent()).removeNodeChangeListener(projectToNodeListener.get(project));
				}
			}
		} catch (Exception e) {
		}
		// uninstall the project propagators
		Iterator<ProjectPreferencesPropagator> propagators = projectToPropagator.values().iterator();
		while (propagators.hasNext()) {
			propagators.next().uninstall();
		}

		preferenceChangeListeners = null;
		projectToScope = null;
		projectToPropagator = null;
		projectToNodeListener = null;
		super.uninstall();
	}

	/*
	 * Add the listener to a ProjectPreferencesPropagator. Create a new
	 * propagator if needed.
	 */
	private void addToProjectPropagator(IPreferencesPropagatorListener listener, String preferencesKey) {
		ProjectPreferencesPropagator propagator = projectToPropagator.get(listener.getProject());
		if (propagator == null) {
			propagator = new ProjectPreferencesPropagator(listener.getProject(), nodeQualifier);
			projectToPropagator.put(listener.getProject(), propagator);
		}
		propagator.addPropagatorListener(listener, preferencesKey);
	}

	/*
	 * Removes a listener from a ProjectPreferencesPropagator. If not
	 * ProjectPreferencesPropagator exists, nothing will happen.
	 */
	private void removeFromProjectPropagator(IPreferencesPropagatorListener listener, String preferencesKey) {
		ProjectPreferencesPropagator propagator = projectToPropagator.get(listener.getProject());
		if (propagator != null) {
			propagator.removePropagatorListener(listener, preferencesKey);
		}
	}

	/*
	 * Returns true if the given project has a specific settings for the given
	 * key; false, otherwise.
	 */
	private boolean isProjectSpecific(IProject project, String preferencesKey) {
		ProjectScope projectScope = getProjectScope(project);
		IPath location = projectScope.getLocation();
		if (location != null && new File(location.toOSString()).exists()) {
			return projectScope.getNode(nodeQualifier).get(preferencesKey, null) != null;
		}
		return false;
	}

	/*
	 * Returns a ProjectScope for the given IProject.
	 */
	private ProjectScope getProjectScope(IProject project) {
		ProjectScope scope = projectToScope.get(project);
		if (scope == null) {
			scope = new ProjectScope(project);
			projectToScope.put(project, scope);
		}
		return scope;
	}

	/*
	 * Adds a node listener to the parent node of the project preferences scope.
	 */
	private void addNodeListener(IProject project, ProjectScope projectScope) {
		IEclipsePreferences node = projectScope.getNode(nodeQualifier);
		if (node != null) {
			// https://bugs.eclipse.org/bugs/show_bug.cgi?id=510530
			// A INodeChangeListener can already exist in
			// "projectToNodeListener" (i.e. nodeListener != null) when a
			// project is deleted and recreated (a unique IProject object
			// seems to be cached per project and "survives" creations and
			// deletions of this project), so reuse it...
			INodeChangeListener nodeListener = projectToNodeListener.get(project);
			if (nodeListener == null) {
				nodeListener = new InnerNodeChangeListener(project);
			} else {
				// by security
				((IEclipsePreferences) node.parent()).removeNodeChangeListener(nodeListener);
			}
			// ...and don't forget to add it to the newest parent node
			((IEclipsePreferences) node.parent()).addNodeChangeListener(nodeListener);
			projectToNodeListener.put(project, nodeListener);
			if (!preferenceChangeListeners.containsValue(node)) {
				IPreferenceChangeListener changeListener = new NodePreferenceChangeListener(project);
				node.addPreferenceChangeListener(changeListener);
				preferenceChangeListeners.put(changeListener, node);
			}
		}
	}

	private void notifyEvent(PreferencesPropagatorEvent event, String propertyKey, IProject project) {
		synchronized (lock) {
			if (project != null) {
				// The event arrived from the NodePreferenceChangeListener when
				// we moved to a project-specific settings.
				ProjectPreferencesPropagator ppp = projectToPropagator.get(project);
				if (ppp == null) {
					ppp = new ProjectPreferencesPropagator(project, nodeQualifier);
					projectToPropagator.put(project, ppp);
				}
				ppp.notifyPropagatorEvent(event);
			} else {
				List<IPreferencesPropagatorListener> list = getPropagatorListeners(propertyKey);
				if (list == null)
					return;
				Iterator<IPreferencesPropagatorListener> iterator = list.iterator();
				while (iterator.hasNext()) {
					iterator.next().preferencesEventOccured(event);
				}
			}
		}
	}

	/*
	 * An inner node change listener that should listen to any change in the
	 * project-scope parent node and should make all the needed listener swaps.
	 */
	private class InnerNodeChangeListener implements IEclipsePreferences.INodeChangeListener {

		private IProject project;

		public InnerNodeChangeListener(IProject project) {
			this.project = project;
		}

		/*
		 * When a node is added, there is a move for a project-specific
		 * prefernces, thus, we should divert all the listeners for the project
		 * to the ProjectPreferencesPropagator.
		 */
		public void added(NodeChangeEvent event) {
			IEclipsePreferences pNode = null;
			if (event.getChild() instanceof IEclipsePreferences) {
				pNode = (IEclipsePreferences) event.getChild();
			} else {
				return;
			}
			if (!preferenceChangeListeners.containsValue(pNode)) {
				IPreferenceChangeListener changeListener = new NodePreferenceChangeListener(project);
				pNode.addPreferenceChangeListener(changeListener);
				preferenceChangeListeners.put(changeListener, pNode);
			}
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.core.runtime.preferences.IEclipsePreferences$INodeChangeListener#removed(org.eclipse.core.runtime.preferences.IEclipsePreferences.NodeChangeEvent)
		 */
		public void removed(NodeChangeEvent event) {

			Object childNode = event.getChild();
			if (preferenceChangeListeners.containsValue(childNode)) {
				// search for the listener to be removed
				Iterator<IPreferenceChangeListener> keys = preferenceChangeListeners.keySet().iterator();
				while (keys.hasNext()) {
					Object key = keys.next();
					IEclipsePreferences aNode = preferenceChangeListeners.get(key);
					if (aNode == childNode) {
						preferenceChangeListeners.remove(key);
						return;
					}
				}
			}
		}
	}

	/*
	 * An inner preferences node change listener
	 */
	public class NodePreferenceChangeListener implements IPreferenceChangeListener {

		private IProject project;

		public NodePreferenceChangeListener(IProject project) {
			this.project = project;
		}

		public void preferenceChange(PreferenceChangeEvent event) {
			String key = event.getKey();
			String newValue = (String) event.getNewValue();
			if (newValue == null) {
				// We are moving from the project scope to the workspace scope
				removeFromProjectPropagator(key, project);
			} else {
				// We are moving from the workspace to the project specific
				// preferences
				moveToProjectPropagator(key, project);
			}
			PreferencesPropagatorEvent e = new PreferencesPropagatorEvent(event.getSource(), event.getOldValue(),
					event.getNewValue(), event.getKey());
			notifyEvent(e, key, (newValue == null) ? null : project);
		}
	}

	/*
	 * Move the listeners to the project preferences propagator.
	 */
	private void moveToProjectPropagator(String key, IProject project) {
		List<IPreferencesPropagatorListener> list = null;
		IPreferencesPropagatorListener[] listeners = null;
		synchronized (lock) {
			list = getPropagatorListeners(key);
			if (list == null) {
				return;
			}
			listeners = new IPreferencesPropagatorListener[list.size()];
			list.toArray(listeners);
		}
		for (IPreferencesPropagatorListener listener : listeners) {
			if (project.equals(listener.getProject())) {
				// Move the listener from this propagator to the project
				// specific preferences propagator
				super.removePropagatorListener(listener, key);
				addToProjectPropagator(listener, key);
			}
		}
	}

	/*
	 * Remove the listeners from the project preferences propagator.
	 */
	private void removeFromProjectPropagator(String key, IProject project) {
		synchronized (lock) {
			ProjectPreferencesPropagator propagator = projectToPropagator.get(project);
			if (propagator != null) {
				// Remove the listeners from the project propagator and place
				// them in this propagator.
				List<IPreferencesPropagatorListener> listeners = propagator.removePropagatorListeners(key);
				if (listeners != null && listeners.size() > 0) {
					Iterator<IPreferencesPropagatorListener> iter = listeners.iterator();
					while (iter.hasNext()) {
						addPropagatorListener(iter.next(), key);
					}
				}
			}
		}
	}

	/*
	 * An inner IPropertyChangeListener that listens to the workspace changes
	 * and notify all the registered listeners.
	 */
	private class WorkspacePropertyChangeListener implements IPreferenceChangeListener {

		@Override
		public void preferenceChange(PreferenceChangeEvent event) {
			// Collect and notify the listeners that are defined to listen to
			// workspace preferences changes
			// for the arrived event.
			PreferencesPropagatorEvent e = new PreferencesPropagatorEvent(event.getSource(), event.getOldValue(),
					event.getNewValue(), event.getKey());
			notifyEvent(e, event.getKey(), null);
		}
	}
}
