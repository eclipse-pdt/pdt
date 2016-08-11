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

import java.util.EventObject;

import org.eclipse.core.resources.IProject;
import org.eclipse.dltk.annotations.NonNull;
import org.eclipse.dltk.annotations.Nullable;
import org.eclipse.wst.sse.core.internal.provisional.tasks.TaskTag;

/**
 * A TaskTagsEvent event gets delivered whenever a changes is made to the task
 * tags list of the workspace or the project.
 * <P>
 * The event source is always the TaskTagsProvider reference.
 * 
 * @author shalom
 */
public class TaskTagsEvent extends EventObject {

	private TaskTag[] tags;
	private IProject project;
	private boolean isCaseSensitive;

	/**
	 * Constructs a new TaskTagsEvent.
	 * 
	 * @param provider
	 *            The source of the event (always a TaskTagsProvider)
	 * @param project
	 *            The project that is effected by the task tags change (null
	 *            indicates that the change is in the workspace settings)
	 * @param tags
	 *            The updated task tags.
	 * @param isCaseSensitive
	 *            Indicate that the tags should be compiled as case-sensitive.
	 */
	public TaskTagsEvent(@NonNull TaskTagsProvider provider, @Nullable IProject project, @NonNull TaskTag[] tags,
			boolean isCaseSensitive) {
		super(provider);
		this.project = project;
		this.tags = tags;
		this.isCaseSensitive = isCaseSensitive;
	}

	/**
	 * Returns the IProject that was effected by the task tags change. The
	 * method will return null to indicate that the change was made in the
	 * workspace settings.
	 * 
	 * @return The effected IProject, or null if the effect is on the workspace.
	 */
	public @Nullable IProject getProject() {
		return project;
	}

	/**
	 * Returns the updated task tags.
	 * 
	 * @return
	 */
	public @NonNull TaskTag[] getTaskTags() {
		return tags;
	}

	/**
	 * Returns true if the task tags are case sensitive.
	 * 
	 * @return The case sensitivity state of the task tags for the defined
	 *         IProject or workspace.
	 */
	public boolean isCaseSensitive() {
		return isCaseSensitive;
	}

	public String toString() {
		StringBuilder buffer = new StringBuilder();
		buffer.append("[TaskTagsEvent: project = ");//$NON-NLS-1$
		if (project == null) {
			buffer.append("null");//$NON-NLS-1$
		} else {
			buffer.append('"');
			buffer.append(project);
			buffer.append('"');
		}
		buffer.append(", TaskTags[");//$NON-NLS-1$
		buffer.append(tags.length);
		buffer.append("] = {");//$NON-NLS-1$
		for (int i = 0; i < tags.length; i++) {
			if (tags[i] == null) {
				buffer.append("null");//$NON-NLS-1$
			} else {
				buffer.append('"');
				buffer.append(tags[i]);
				buffer.append('"');
			}
			if (i + 1 < tags.length) {
				buffer.append(", ");//$NON-NLS-1$
			}
		}
		buffer.append('}');
		buffer.append(", Case-Sensitive = ");//$NON-NLS-1$
		buffer.append(isCaseSensitive());
		buffer.append(']');
		return buffer.toString();
	}
}
