/*******************************************************************************
 * Copyright (c) 2009, 2016 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.core.preferences;

import java.util.HashMap;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.dltk.annotations.NonNull;
import org.eclipse.wst.sse.core.internal.provisional.tasks.TaskTag;

public class TaskPatternsProvider {

	@NonNull
	private static final TaskPatternsProvider instance = new TaskPatternsProvider();
	private boolean isInitialized = false;

	private TaskTagsProvider provider;

	private Pattern[] workspacePatterns;
	private HashMap<IProject, Pattern[]> projectsPatterns;
	private HashMap<IProject, ITaskTagsListener> projectToListener;

	private TaskPatternsProvider() {
		provider = TaskTagsProvider.getInstance();
		projectsPatterns = new HashMap<>();
		projectToListener = new HashMap<>();
	}

	public static @NonNull TaskPatternsProvider getInstance() {
		return instance;
	}

	@SuppressWarnings("null")
	public synchronized @NonNull Pattern[] getPatternsForProject(IProject project) {
		registerProject(project);
		Pattern[] patterns = projectsPatterns.get(project);
		if (patterns != null) {
			return patterns;
		}
		return workspacePatterns;
	}

	private void initPatternsDB() {
		if (isInitialized) {
			return;
		}
		isInitialized = true;

		workspacePatterns = createPatterns(provider.getWorkspaceTaskTags(), provider.isWorkspaceTagsCaseSensitive());
		final IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();

		if (projects == null) {
			return;
		}

		for (int i = 0; i < projects.length; i++) {
			IProject project = projects[i];
			registerProject(project);
		}
	}

	@SuppressWarnings("null")
	public synchronized @NonNull Pattern[] getPatternsForWorkspace() {
		initPatternsDB();
		return workspacePatterns;
	}

	// If necessary, initialize the needed listeners on the given project and
	// update its patterns.
	public static void registerProject(IProject project) {
		synchronized (instance) {
			if (project == null) {
				return;
			}
			instance.initPatternsDB();
			if (!instance.projectToListener.containsKey(project)) {
				// Add to the project patterns
				TaskTag[] tags = instance.provider.getProjectTaskTags(project);
				boolean caseSensitive = instance.provider.getProjectTagsCaseSensitive(project);
				if (tags != null) {
					Pattern[] patterns = createPatterns(tags, caseSensitive);
					instance.projectsPatterns.put(project, patterns);
				}
				// Add a listener for this project
				ITaskTagsListener tagsListener = new TaskTagsListener();
				instance.projectToListener.put(project, tagsListener);
				instance.provider.addTaskTagsListener(tagsListener, project);
			}
		}
	}

	public static void unregisterProject(IProject project) {
		synchronized (instance) {
			if (!instance.isInitialized || project == null) {
				return;
			}
			instance.provider.removeTaskTagsListener(project);
			instance.projectsPatterns.remove(project);
			instance.projectToListener.remove(project);
		}
	}

	private static Pattern[] createPatterns(TaskTag[] workspaceTaskTags, boolean caseSensitive) {
		Pattern[] patterns = new Pattern[workspaceTaskTags.length];
		for (int i = 0; i < workspaceTaskTags.length; i++) {
			TaskTag tag = workspaceTaskTags[i];
			String tagString = tag.getTag();
			// https://bugs.eclipse.org/bugs/show_bug.cgi?id=492373
			// Best approximation to fit the jflex highlighting rule
			// "@"[a-zA-Z-]+ since tagString can start with a @ or any
			// other character.
			if (caseSensitive) {
				patterns[i] = Pattern.compile("(?<![a-zA-Z-])" + Pattern.quote(tagString) + "(?![a-zA-Z-])"); //$NON-NLS-1$ //$NON-NLS-2$
			} else {
				patterns[i] = Pattern.compile("(?<![a-zA-Z-])" + Pattern.quote(tagString) + "(?![a-zA-Z-])", //$NON-NLS-1$ //$NON-NLS-2$
						Pattern.CASE_INSENSITIVE);
			}
		}

		return patterns;
	}

	private synchronized void taskTagsChanged(IProject project, TaskTag[] tags, boolean caseSensitive) {
		if (project == null) {
			workspacePatterns = createPatterns(tags, caseSensitive);
			return;
		}
		Pattern[] patterns = createPatterns(tags, caseSensitive);
		projectsPatterns.put(project, patterns);
	}

	/*
	 * A task tags listener
	 */
	private static class TaskTagsListener implements ITaskTagsListener {

		@Override
		public void taskTagsChanged(TaskTagsEvent event) {
			instance.taskTagsChanged(event.getProject(), event.getTaskTags(), event.isCaseSensitive());
		}

		@Override
		public void taskPrioritiesChanged(TaskTagsEvent event) {
		}

		@Override
		public void taskCaseChanged(TaskTagsEvent event) {
			instance.taskTagsChanged(event.getProject(), event.getTaskTags(), event.isCaseSensitive());
		}

	}
}
