/*******************************************************************************
 * Copyright (c) 2009, 2016 IBM Corporation and others.
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
import org.eclipse.dltk.annotations.NonNull;
import org.eclipse.dltk.annotations.Nullable;
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

	private HashMap<IProject, ITaskTagsListener> projectToTaskTagListener;
	private HashMap<IProject, IPreferencesPropagatorListener[]> projectToPropagatorListeners;
	private boolean isInstalled;
	private PreferencesSupport preferencesSupport;
	private PreferencesPropagator preferencesPropagator;
	private static final String NODES_QUALIFIER = PHPCorePlugin.ID;

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
	public @NonNull static TaskTagsProvider getInstance() {
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
	public @NonNull TaskTag[] getWorkspaceTaskTags() {
		String priorities = preferencesSupport.getWorkspacePreferencesValue(PHPCoreConstants.TASK_PRIORITIES);
		String tags = preferencesSupport.getWorkspacePreferencesValue(PHPCoreConstants.TASK_TAGS);
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
		String caseSensitive = preferencesSupport.getWorkspacePreferencesValue(PHPCoreConstants.TASK_CASE_SENSITIVE);
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
	public @Nullable TaskTag[] getProjectTaskTags(IProject project) {
		if (project == null) {
			return null;
		}
		String priorities = preferencesSupport.getProjectSpecificPreferencesValue(PHPCoreConstants.TASK_PRIORITIES,
				null, project);
		String projectTags = preferencesSupport.getProjectSpecificPreferencesValue(PHPCoreConstants.TASK_TAGS, null,
				project);
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
		if (project == null) {
			return isWorkspaceTagsCaseSensitive();
		}
		String caseSensitive = preferencesSupport
				.getProjectSpecificPreferencesValue(PHPCoreConstants.TASK_CASE_SENSITIVE, null, project);
		if (caseSensitive == null) {
			return isWorkspaceTagsCaseSensitive();
		}
		return PHPCoreConstants.ENABLED.equals(caseSensitive);
	}

	/**
	 * Sets a TaskTagsListener to a related project.
	 * 
	 * @param listener
	 *            A TaskTagsListener.
	 * @param project
	 *            A related project.
	 */
	public void addTaskTagsListener(ITaskTagsListener listener, IProject project) {
		if (listener == null || project == null) {
			return;
		}
		projectToTaskTagListener.put(project, listener);
		installPropagatorListeners(project);
	}

	/**
	 * Removes the TaskTagsListener attached to a related project. Don't use
	 * this method directly but use
	 * {@link TaskPatternsProvider#unregisterProject(IProject)} to clean up
	 * everything correctly.
	 * 
	 * @param project
	 *            A related project.
	 */
	public void removeTaskTagsListener(IProject project) {
		if (project == null) {
			return;
		}
		projectToTaskTagListener.remove(project);
		uninstallPropagatorListeners(project);
	}

	// Install propagator listeners for the given project.
	private void installPropagatorListeners(IProject project) {
		IPreferencesPropagatorListener[] listeners = new IPreferencesPropagatorListener[] {
				new InnerTaskTagsListener(project), new InnerTaskPrioritiesListener(project),
				new InnerTaskCaseListener(project) };
		preferencesPropagator.addPropagatorListener(listeners[0], PHPCoreConstants.TASK_TAGS);
		preferencesPropagator.addPropagatorListener(listeners[1], PHPCoreConstants.TASK_PRIORITIES);
		preferencesPropagator.addPropagatorListener(listeners[2], PHPCoreConstants.TASK_CASE_SENSITIVE);

		projectToPropagatorListeners.put(project, listeners);
	}

	// Uninstall propagator listeners for the given project.
	private void uninstallPropagatorListeners(IProject project) {
		IPreferencesPropagatorListener[] listeners = (IPreferencesPropagatorListener[]) projectToPropagatorListeners
				.get(project);
		if (listeners != null) {
			preferencesPropagator.removePropagatorListener(listeners[0], PHPCoreConstants.TASK_TAGS);
			preferencesPropagator.removePropagatorListener(listeners[1], PHPCoreConstants.TASK_PRIORITIES);
			preferencesPropagator.removePropagatorListener(listeners[2], PHPCoreConstants.TASK_CASE_SENSITIVE);
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
		preferencesSupport = new PreferencesSupport(PHPCorePlugin.ID);
		projectToTaskTagListener = new HashMap<IProject, ITaskTagsListener>();
		projectToPropagatorListeners = new HashMap<IProject, IPreferencesPropagatorListener[]>();
		preferencesPropagator = PreferencePropagatorFactory.getPreferencePropagator(NODES_QUALIFIER);
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
		Set<IProject> keys = projectToPropagatorListeners.keySet();
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
		ITaskTagsListener[] allListeners = new ITaskTagsListener[projectToTaskTagListener.size()];
		projectToTaskTagListener.values().toArray(allListeners);
		for (ITaskTagsListener element : allListeners) {
			element.taskTagsChanged(event);
		}
	}

	// Notify a taskTagsChanged to all the listeners.
	private void notifyTaskPriorityChange(TaskTagsEvent event) {
		ITaskTagsListener[] allListeners = new ITaskTagsListener[projectToTaskTagListener.size()];
		projectToTaskTagListener.values().toArray(allListeners);
		for (ITaskTagsListener element : allListeners) {
			element.taskPrioritiesChanged(event);
		}
	}

	// Notify a taskTagsChanged to all the listeners.
	private void notifyTaskCaseChange(TaskTagsEvent event) {
		ITaskTagsListener[] allListeners = new ITaskTagsListener[projectToTaskTagListener.size()];
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
	private static TaskTag[] getTagsAndPropertiesFrom(String tagString, String priorityString) {
		String[] tags = StringUtils.unpack(tagString);
		String[] priorities = StringUtils.unpack(priorityString);
		List<Integer> list = new ArrayList<Integer>();

		for (String element : priorities) {
			Integer number = null;
			if (PHPCoreConstants.TASK_PRIORITY_HIGH.equals(element)) {
				number = Integer.valueOf(IMarker.PRIORITY_HIGH);
			} else if (PHPCoreConstants.TASK_PRIORITY_LOW.equals(element)) {
				number = Integer.valueOf(IMarker.PRIORITY_LOW);
			} else {
				number = Integer.valueOf(IMarker.PRIORITY_NORMAL);
			}
			list.add(number);
		}
		Integer[] allPriorities = new Integer[list.size()];
		list.toArray(allPriorities);

		TaskTag[] taskTags = new TaskTag[Math.min(tags.length, priorities.length)];
		for (int i = 0; i < taskTags.length; i++) {
			taskTags[i] = new TaskTag(tags[i], allPriorities[i].intValue());
		}
		return taskTags;
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////
	// ///////////////////// IPreferencesPropagatorListeners
	// ////////////////////////
	// ////////////////////////////////////////////////////////////////////////////////////////////

	private abstract class AbstractTasksListener implements IPreferencesPropagatorListener {

		private IProject project;

		public AbstractTasksListener(@NonNull IProject project) {
			this.project = project;
		}

		public @NonNull IProject getProject() {
			return project;
		}
	}

	// Listen to task-tags strings changes
	private class InnerTaskTagsListener extends AbstractTasksListener {

		public InnerTaskTagsListener(@NonNull IProject project) {
			super(project);
		}

		public void preferencesEventOccured(PreferencesPropagatorEvent event) {
			String tags = preferencesSupport.getProjectSpecificPreferencesValue(PHPCoreConstants.TASK_TAGS, null,
					getProject());
			String priorities = preferencesSupport.getProjectSpecificPreferencesValue(PHPCoreConstants.TASK_PRIORITIES,
					null, getProject());
			String caseSensitive = preferencesSupport
					.getProjectSpecificPreferencesValue(PHPCoreConstants.TASK_CASE_SENSITIVE, null, getProject());
			String newValue = (String) event.getNewValue();
			// if project settings are not fully updated (i.e. user newly
			// enabled project task tags settings), use workspace settings
			if (tags == null || priorities == null || caseSensitive == null || newValue == null) {
				tags = preferencesSupport.getWorkspacePreferencesValue(PHPCoreConstants.TASK_TAGS);
				priorities = preferencesSupport.getWorkspacePreferencesValue(PHPCoreConstants.TASK_PRIORITIES);
				caseSensitive = preferencesSupport.getWorkspacePreferencesValue(PHPCoreConstants.TASK_CASE_SENSITIVE);
				newValue = tags;
			}
			if (StringUtils.occurrencesOf(newValue, ',') == StringUtils.occurrencesOf(priorities, ',')) {
				TaskTag[] taskTags = getTagsAndPropertiesFrom(newValue, priorities);
				TaskTagsEvent taskEvent = new TaskTagsEvent(TaskTagsProvider.this, getProject(), taskTags,
						PHPCoreConstants.ENABLED.equals(caseSensitive));
				notifyTaskTagChange(taskEvent);
			}
		}
	}

	// Listen to task-tags priorities changes
	private class InnerTaskPrioritiesListener extends AbstractTasksListener {

		public InnerTaskPrioritiesListener(@NonNull IProject project) {
			super(project);
		}

		public void preferencesEventOccured(PreferencesPropagatorEvent event) {
			String tags = preferencesSupport.getProjectSpecificPreferencesValue(PHPCoreConstants.TASK_TAGS, null,
					getProject());
			String priorities = preferencesSupport.getProjectSpecificPreferencesValue(PHPCoreConstants.TASK_PRIORITIES,
					null, getProject());
			String caseSensitive = preferencesSupport
					.getProjectSpecificPreferencesValue(PHPCoreConstants.TASK_CASE_SENSITIVE, null, getProject());
			String newValue = (String) event.getNewValue();
			// if project settings are not fully updated (i.e. user newly
			// enabled project task tags settings), use workspace settings
			if (tags == null || priorities == null || caseSensitive == null || newValue == null) {
				tags = preferencesSupport.getWorkspacePreferencesValue(PHPCoreConstants.TASK_TAGS);
				priorities = preferencesSupport.getWorkspacePreferencesValue(PHPCoreConstants.TASK_PRIORITIES);
				caseSensitive = preferencesSupport.getWorkspacePreferencesValue(PHPCoreConstants.TASK_CASE_SENSITIVE);
				newValue = priorities;
			}
			if (StringUtils.occurrencesOf(tags, ',') == StringUtils.occurrencesOf(newValue, ',')) {
				TaskTag[] taskTags = getTagsAndPropertiesFrom(tags, newValue);
				TaskTagsEvent taskEvent = new TaskTagsEvent(TaskTagsProvider.this, getProject(), taskTags,
						PHPCoreConstants.ENABLED.equals(caseSensitive));
				notifyTaskPriorityChange(taskEvent);
			}
		}
	}

	// Listen to task-tags case sensitivity changes
	private class InnerTaskCaseListener extends AbstractTasksListener {

		public InnerTaskCaseListener(@NonNull IProject project) {
			super(project);
		}

		public void preferencesEventOccured(PreferencesPropagatorEvent event) {
			String tags = preferencesSupport.getProjectSpecificPreferencesValue(PHPCoreConstants.TASK_TAGS, null,
					getProject());
			String priorities = preferencesSupport.getProjectSpecificPreferencesValue(PHPCoreConstants.TASK_PRIORITIES,
					null, getProject());
			String caseSensitive = preferencesSupport
					.getProjectSpecificPreferencesValue(PHPCoreConstants.TASK_CASE_SENSITIVE, null, getProject());
			String newValue = (String) event.getNewValue();
			// if project settings are not fully updated (i.e. user newly
			// enabled project task tags settings), use workspace settings
			if (tags == null || priorities == null || caseSensitive == null || newValue == null) {
				tags = preferencesSupport.getWorkspacePreferencesValue(PHPCoreConstants.TASK_TAGS);
				priorities = preferencesSupport.getWorkspacePreferencesValue(PHPCoreConstants.TASK_PRIORITIES);
				caseSensitive = preferencesSupport.getWorkspacePreferencesValue(PHPCoreConstants.TASK_CASE_SENSITIVE);
				newValue = caseSensitive;
			}
			if (StringUtils.occurrencesOf(tags, ',') == StringUtils.occurrencesOf(priorities, ',')) {
				TaskTag[] taskTags = getTagsAndPropertiesFrom(tags, priorities);
				TaskTagsEvent taskEvent = new TaskTagsEvent(TaskTagsProvider.this, getProject(), taskTags,
						PHPCoreConstants.ENABLED.equals(newValue));
				notifyTaskCaseChange(taskEvent);
			}
		}
	}
}
