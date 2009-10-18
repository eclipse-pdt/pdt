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
import java.util.regex.Pattern;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.wst.sse.core.internal.provisional.tasks.TaskTag;

public class TaskPatternsProvider {

	private static TaskPatternsProvider instance;

	private TaskTagsProvider provider;

	private Pattern[] workspacePatterns;
	private HashMap projectsPatterns;
	private HashMap projectToListener;

	private TaskPatternsProvider() {
		provider = TaskTagsProvider.getInstance();
		projectsPatterns = new HashMap();
		projectToListener = new HashMap();
		initPatternsDB();
	}

	public static TaskPatternsProvider getInstance() {
		if (instance == null) {
			instance = new TaskPatternsProvider();
		}
		return instance;
	}

	public Pattern[] getPatternsForProject(IProject project) {
		registerProject(project);
		Pattern[] patterns = (Pattern[]) projectsPatterns.get(project);
		if (patterns != null) {
			return patterns;
		}
		patterns = workspacePatterns;
		return patterns;
	}

	private void initPatternsDB() {
		workspacePatterns = createPatterns(provider.getWorkspaceTaskTags(),
				provider.isWorkspaceTagsCaseSensitive());
		final IProject[] projects = ResourcesPlugin.getWorkspace().getRoot()
				.getProjects();

		if (projects == null) {
			return;
		}

		for (int i = 0; i < projects.length; i++) {
			IProject project = projects[i];
			registerProject(project);
		}
	}

	public Pattern[] getPetternsForWorkspace() {
		return workspacePatterns;
	}

	// If necessary, initialise the needed listeners on the given project and
	// update its patterns.
	private void registerProject(IProject project) {
		if (projectToListener.get(project) == null) {
			// Add to the project patterns
			TaskTag[] tags = provider.getProjectTaskTags(project);
			boolean caseSensitive = provider
					.getProjectTagsCaseSensitive(project);
			if (tags != null) {
				Pattern[] patterns = createPatterns(tags, caseSensitive);
				projectsPatterns.put(project, patterns);
			}
			// Add a listener for this project
			ITaskTagsListener tagsListener = new TaskTagsListener();
			provider.addTaskTagsListener(tagsListener, project);
			projectToListener.put(project, tagsListener);
		}
	}

	private Pattern[] createPatterns(TaskTag[] workspaceTaskTags,
			boolean caseSensitive) {
		Pattern[] patterns = new Pattern[workspaceTaskTags.length];
		for (int i = 0; i < workspaceTaskTags.length; i++) {
			TaskTag tag = workspaceTaskTags[i];
			String tagString = tag.getTag();
			if (caseSensitive) {
				patterns[i] = Pattern.compile(tagString, Pattern.LITERAL);
			} else {
				patterns[i] = Pattern.compile(tagString,
						Pattern.CASE_INSENSITIVE | Pattern.LITERAL);
			}
		}

		return patterns;
	}

	private void taskTagsChanged(IProject project, TaskTag[] tags,
			boolean caseSensitive) {
		if (project == null) {
			workspacePatterns = createPatterns(tags, caseSensitive);
			return;
		}
		if (tags == null) {
			projectsPatterns.remove(project);
			return;
		}
		Pattern[] patterns = createPatterns(tags, caseSensitive);
		projectsPatterns.put(project, patterns);
	}

	/*
	 * A task tags listener
	 */
	private class TaskTagsListener implements ITaskTagsListener {

		public void taskTagsChanged(TaskTagsEvent event) {
			TaskPatternsProvider.this.taskTagsChanged(event.getProject(), event
					.getTaskTags(), event.isCaseSensitive());
		}

		public void taskPrioritiesChanged(TaskTagsEvent event) {
		}

		public void taskCaseChanged(TaskTagsEvent event) {
			TaskPatternsProvider.this.taskTagsChanged(event.getProject(), event
					.getTaskTags(), event.isCaseSensitive());
		}

	}
}
