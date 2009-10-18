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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Preferences;
import org.eclipse.php.internal.core.PHPCoreConstants;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.wst.sse.core.internal.provisional.tasks.TaskTag;
import org.eclipse.wst.sse.core.utils.StringUtils;

/**
 * A task tags provider listens to any task-tags preferences changes and notify
 * any listener about the changes.
 * 
 * @author shalom
 */
public class TaskTagsProvider {

	private static TaskTagsProvider instance;

	private HashMap projectToTaskTagListener;
	private HashMap projectToPropagatorListeners;
	private boolean isInstalled;
	private PreferencesSupport preferencesSupport;
	private PreferencesPropagator preferencesPropagator;
	private static final String NODES_QUALIFIER = PHPCorePlugin.ID;
	private static final Preferences store = PHPCorePlugin.getDefault()
			.getPluginPreferences();

	/**
	 * Constructs a new TaskTagsProvider.
	 */
	private TaskTagsProvider() {
		install();
	}

	/**
	 * Returns a single instance of the TaskTagsProvider.
	 * 
	 * @return A TaskTagsProvider instance
	 */
	public static TaskTagsProvider getInstance() {
		if (instance == null) {
			instance = new TaskTagsProvider();
		}
		return instance;
	}

	/**
	 * Returns the Workspace task tags.
	 * 
	 * @return The task tags defined for the workspace preferences.
	 */
	public TaskTag[] getWorkspaceTaskTags() {
		String priorities = preferencesSupport
				.getWorkspacePreferencesValue(PHPCoreConstants.TASK_PRIORITIES);
		String tags = preferencesSupport
				.getWorkspacePreferencesValue(PHPCoreConstants.TASK_TAGS);
		TaskTag[] workspaceTags = getTagsAndPropertiesFrom(tags, priorities);
		return workspaceTags;
	}

	/**
	 * Returns true if the defined workspace task tags are case sensitive.
	 * 
	 * @return true if the defined workspace task tags are case sensitive; false
	 *         otherwise.
	 */
	public boolean isWorkspaceTagsCaseSensitive() {
		String caseSensitive = preferencesSupport
				.getWorkspacePreferencesValue(PHPCoreConstants.TASK_CASE_SENSITIVE);
		return PHPCoreConstants.ENABLED.equals(caseSensitive);
	}

	/**
	 * Returns the IProject's task tags. In case the given project does not have
	 * a specific task tags, null is returned (since the project uses the
	 * workspace definitions).
	 * 
	 * @param project
	 *            An IProject reference.
	 * @return The specific task tags for the given project, or null if the
	 *         project uses the workspace defined tags.
	 */
	public TaskTag[] getProjectTaskTags(IProject project) {
		String priorities = preferencesSupport
				.getProjectSpecificPreferencesValue(
						PHPCoreConstants.TASK_PRIORITIES, null, project);
		String projectTags = preferencesSupport
				.getProjectSpecificPreferencesValue(PHPCoreConstants.TASK_TAGS,
						null, project);
		if (projectTags == null || priorities == null) {
			return null;
		}
		TaskTag[] tags = getTagsAndPropertiesFrom(projectTags, priorities);
		return tags;
	}

	/**
	 * Returns true if the defined project's task tags are case sensitive. In
	 * case that the project does not have a specific settings, the return value
	 * is by isWorkspaceTagsCaseSensitive.
	 * 
	 * @param project
	 *            An IProject reference.
	 * @return true if the defined project's task tags are case sensitive; false
	 *         otherwise.
	 */
	public boolean getProjectTagsCaseSensitive(IProject project) {
		String caseSensitive = preferencesSupport
				.getProjectSpecificPreferencesValue(
						PHPCoreConstants.TASK_CASE_SENSITIVE, null, project);
		if (caseSensitive == null) {
			return isWorkspaceTagsCaseSensitive();
		}
		return PHPCoreConstants.ENABLED.equals(caseSensitive);
	}

	/**
	 * Adds a TaskTagsListener.
	 * 
	 * @param listener
	 *            A TaskTagsListener.
	 * @param project
	 *            A related project.
	 */
	public void addTaskTagsListener(ITaskTagsListener listener, IProject project) {
		projectToTaskTagListener.put(project, listener);
		installPropagatorListeners(project);
	}

	/**
	 * Removes a TaskTagsListener.
	 * 
	 * @param listener
	 *            A TaskTagsListener.
	 * @param project
	 *            A related project.
	 */
	public void removeTaskTagsListener(ITaskTagsListener listener,
			IProject project) {
		projectToTaskTagListener.remove(project);
		uninstallPropagatorListeners(project);
	}

	// Install propagator listeners for the given project.
	private void installPropagatorListeners(IProject project) {
		IPreferencesPropagatorListener[] listeners = new IPreferencesPropagatorListener[] {
				new InnerTaskTagsListener(project),
				new InnerTaskPrioritiesListener(project),
				new InnerTaskCaseListener(project) };
		preferencesPropagator.addPropagatorListener(listeners[0],
				PHPCoreConstants.TASK_TAGS);
		preferencesPropagator.addPropagatorListener(listeners[1],
				PHPCoreConstants.TASK_PRIORITIES);
		preferencesPropagator.addPropagatorListener(listeners[2],
				PHPCoreConstants.TASK_CASE_SENSITIVE);

		projectToPropagatorListeners.put(project, listeners);
	}

	// Uninstall propagator listeners for the given project.
	private void uninstallPropagatorListeners(IProject project) {
		IPreferencesPropagatorListener[] listeners = (IPreferencesPropagatorListener[]) projectToPropagatorListeners
				.get(project);
		if (listeners != null) {
			preferencesPropagator.removePropagatorListener(listeners[0],
					PHPCoreConstants.TASK_TAGS);
			preferencesPropagator.removePropagatorListener(listeners[1],
					PHPCoreConstants.TASK_PRIORITIES);
			preferencesPropagator.removePropagatorListener(listeners[2],
					PHPCoreConstants.TASK_CASE_SENSITIVE);
		}
		projectToPropagatorListeners.remove(project);
	}

	/**
	 * Install this task-tags provider.
	 */
	protected synchronized void install() {
		if (isInstalled) {
			return;
		}
		preferencesSupport = new PreferencesSupport(PHPCorePlugin.ID,
				PHPCorePlugin.getDefault().getPluginPreferences());
		projectToTaskTagListener = new HashMap();
		projectToPropagatorListeners = new HashMap();
		preferencesPropagator = PreferencePropagatorFactory
				.getPreferencePropagator(NODES_QUALIFIER, store);
		isInstalled = true;
	}

	/**
	 * Uninstall this task-tags provider.
	 */
	protected synchronized void uninstall() {
		if (!isInstalled) {
			return;
		}
		// Uninstall the propagator listeners
		Set keys = projectToPropagatorListeners.keySet();
		IProject[] projects = new IProject[keys.size()];
		keys.toArray(projects);
		for (IProject element : projects) {
			uninstallPropagatorListeners(element);
		}

		preferencesSupport = null;
		preferencesPropagator = null;
		projectToTaskTagListener = null;
		projectToPropagatorListeners = null;
		isInstalled = false;
	}

	// Notify a taskTagsChanged to all the listeners.
	private void notifyTaskTagChange(TaskTagsEvent event) {
		ITaskTagsListener[] allListeners = new ITaskTagsListener[projectToTaskTagListener
				.size()];
		projectToTaskTagListener.values().toArray(allListeners);
		for (ITaskTagsListener element : allListeners) {
			element.taskTagsChanged(event);
		}
	}

	// Notify a taskTagsChanged to all the listeners.
	private void notifyTaskPriorityChange(TaskTagsEvent event) {
		ITaskTagsListener[] allListeners = new ITaskTagsListener[projectToTaskTagListener
				.size()];
		projectToTaskTagListener.values().toArray(allListeners);
		for (ITaskTagsListener element : allListeners) {
			element.taskPrioritiesChanged(event);
		}
	}

	// Notify a taskTagsChanged to all the listeners.
	private void notifyTaskCaseChange(TaskTagsEvent event) {
		ITaskTagsListener[] allListeners = new ITaskTagsListener[projectToTaskTagListener
				.size()];
		projectToTaskTagListener.values().toArray(allListeners);
		for (ITaskTagsListener element : allListeners) {
			element.taskCaseChanged(event);
		}
	}

	/*
	 * Returns the tags into the workspace tags list.
	 * 
	 * @param tags
	 * 
	 * @param priorities
	 */
	private TaskTag[] getTagsAndPropertiesFrom(String tagString,
			String priorityString) {
		String[] tags = StringUtils.unpack(tagString);
		String[] priorities = StringUtils.unpack(priorityString);
		List list = new ArrayList();

		for (String element : priorities) {
			Integer number = null;
			if (PHPCoreConstants.TASK_PRIORITY_HIGH.equals(element)) {
				number = new Integer(IMarker.PRIORITY_HIGH);
			} else if (PHPCoreConstants.TASK_PRIORITY_LOW.equals(element)) {
				number = new Integer(IMarker.PRIORITY_LOW);
			} else {
				number = new Integer(IMarker.PRIORITY_NORMAL);
			}
			list.add(number);
		}
		Integer[] allPriorities = new Integer[list.size()];
		list.toArray(allPriorities);

		TaskTag[] taskTags = new TaskTag[Math.min(tags.length,
				priorities.length)];
		for (int i = 0; i < taskTags.length; i++) {
			taskTags[i] = new TaskTag(tags[i], allPriorities[i].intValue());
		}
		return taskTags;
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////
	// ///////////////////// IPreferencesPropagatorListeners
	// ////////////////////////
	// ////////////////////////////////////////////////////////////////////////////////////////////

	private abstract class AbstractTasksListener implements
			IPreferencesPropagatorListener {

		private IProject project;

		public AbstractTasksListener(IProject project) {
			this.project = project;
		}

		public IProject getProject() {
			return project;
		}
	}

	// Listen to task-tags strings changes
	private class InnerTaskTagsListener extends AbstractTasksListener {

		public InnerTaskTagsListener(IProject project) {
			super(project);
		}

		public void preferencesEventOccured(PreferencesPropagatorEvent event) {
			String newValue = (String) event.getNewValue();
			if (newValue == null) {
				newValue = preferencesSupport
						.getWorkspacePreferencesValue(PHPCoreConstants.TASK_TAGS);
			}
			// Check that the other value are synchronized at this stage and
			// send an event only if they are.
			String priorities = preferencesSupport
					.getProjectSpecificPreferencesValue(
							PHPCoreConstants.TASK_PRIORITIES, null,
							getProject());
			if (priorities == null) {
				priorities = preferencesSupport
						.getWorkspacePreferencesValue(PHPCoreConstants.TASK_PRIORITIES);
			}
			if (StringUtils.occurrencesOf(priorities, ',') == StringUtils
					.occurrencesOf(newValue, ',')) {
				TaskTag[] taskTags = getTagsAndPropertiesFrom(newValue,
						priorities);
				IProject eventProject = (event.getSource() != null && event
						.getSource() instanceof IProject) ? (IProject) event
						.getSource() : null;
				TaskTagsEvent taskEvent = new TaskTagsEvent(
						TaskTagsProvider.this, eventProject, taskTags,
						getProjectTagsCaseSensitive(getProject()));
				notifyTaskTagChange(taskEvent);
			}
		}
	}

	// Listen to task-tags priorities changes
	private class InnerTaskPrioritiesListener extends AbstractTasksListener {

		public InnerTaskPrioritiesListener(IProject project) {
			super(project);
		}

		public void preferencesEventOccured(PreferencesPropagatorEvent event) {
			String newValue = (String) event.getNewValue();
			if (newValue == null) {
				newValue = preferencesSupport
						.getWorkspacePreferencesValue(PHPCoreConstants.TASK_PRIORITIES);
			}
			// Check that the other value are synchronized at this stage and
			// send an event only if they are.
			String tags = preferencesSupport
					.getProjectSpecificPreferencesValue(
							PHPCoreConstants.TASK_TAGS, null, getProject());
			if (tags == null) {
				tags = preferencesSupport
						.getWorkspacePreferencesValue(PHPCoreConstants.TASK_TAGS);
			}
			if (StringUtils.occurrencesOf(tags, ',') == StringUtils
					.occurrencesOf(newValue, ',')) {
				TaskTag[] taskTags = getTagsAndPropertiesFrom(tags, newValue);
				TaskTagsEvent taskEvent = new TaskTagsEvent(
						TaskTagsProvider.this, getProject(), taskTags,
						getProjectTagsCaseSensitive(getProject()));
				notifyTaskPriorityChange(taskEvent);
			}
		}
	}

	// Listen to task-tags case sensitivity changes
	private class InnerTaskCaseListener extends AbstractTasksListener {

		public InnerTaskCaseListener(IProject project) {
			super(project);
		}

		public void preferencesEventOccured(PreferencesPropagatorEvent event) {
			String tags = preferencesSupport
					.getProjectSpecificPreferencesValue(
							PHPCoreConstants.TASK_TAGS, null, getProject());
			String priorities = null;
			if (tags == null) {
				tags = preferencesSupport
						.getWorkspacePreferencesValue(PHPCoreConstants.TASK_TAGS);
				priorities = preferencesSupport
						.getWorkspacePreferencesValue(PHPCoreConstants.TASK_PRIORITIES);
			} else {
				priorities = preferencesSupport
						.getProjectSpecificPreferencesValue(
								PHPCoreConstants.TASK_PRIORITIES,
								"", getProject());//$NON-NLS-1$
			}
			if (StringUtils.occurrencesOf(priorities, ',') == StringUtils
					.occurrencesOf(tags, ',')) {
				TaskTag[] taskTags = getTagsAndPropertiesFrom(tags, priorities);
				TaskTagsEvent taskEvent = null;
				String newValue = (String) event.getNewValue();
				if (newValue == null) {
					newValue = preferencesSupport
							.getWorkspacePreferencesValue(PHPCoreConstants.TASK_CASE_SENSITIVE);
				}
				if (PHPCoreConstants.ENABLED.equals(newValue)) {
					taskEvent = new TaskTagsEvent(TaskTagsProvider.this,
							getProject(), taskTags, true);
				} else {
					taskEvent = new TaskTagsEvent(TaskTagsProvider.this,
							getProject(), taskTags, false);
				}
				notifyTaskCaseChange(taskEvent);
			}
		}
	}
}
