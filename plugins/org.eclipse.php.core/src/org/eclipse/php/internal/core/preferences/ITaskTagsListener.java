/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
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

import java.util.EventListener;

/**
 * A TaskTagsEvent event gets fired whenever the task tags for the workspace or
 * for the project are modified. You can register an ITaskTagsListener to be
 * notified of any task tags updates.
 * 
 * @author shalom
 */
public interface ITaskTagsListener extends EventListener {

	/**
	 * This method gets called when a task tags names are changed.
	 * 
	 * @param event
	 *            A TaskTagsEvent object describing the change.
	 */
	public void taskTagsChanged(TaskTagsEvent event);

	/**
	 * This method gets called when a task tags priorities are changed.
	 * 
	 * @param event
	 *            A TaskTagsEvent object describing the change.
	 */
	public void taskPrioritiesChanged(TaskTagsEvent event);

	/**
	 * This method gets called when a task tags case sensitivity is changed.
	 * 
	 * @param event
	 *            A TaskTagsEvent object describing the change.
	 */
	public void taskCaseChanged(TaskTagsEvent event);

}
