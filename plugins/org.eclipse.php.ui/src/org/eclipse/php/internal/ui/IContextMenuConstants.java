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
package org.eclipse.php.internal.ui;

import org.eclipse.ui.IWorkbenchActionConstants;

public interface IContextMenuConstants {

	/**
	 * Pop-up menu: name of group for goto actions (value
	 * <code>"group.open"</code>).
	 * <p>
	 * Examples for open actions are:
	 * <ul>
	 * <li>Go Into</li>
	 * <li>Go To</li>
	 * </ul>
	 * </p>
	 */
	public static final String GROUP_GOTO = "group.goto"; //$NON-NLS-1$
	/**
	 * Pop-up menu: name of group for open actions (value
	 * <code>"group.open"</code>).
	 * <p>
	 * Examples for open actions are:
	 * <ul>
	 * <li>Open To</li>
	 * <li>Open With</li>
	 * </ul>
	 * </p>
	 */
	public static final String GROUP_OPEN = "group.open"; //$NON-NLS-1$

	/**
	 * Pop-up menu: name of group for show actions (value
	 * <code>"group.show"</code>).
	 * <p>
	 * Examples for show actions are:
	 * <ul>
	 * <li>Show in Navigator</li>
	 * <li>Show in Type Hierarchy</li>
	 * </ul>
	 * </p>
	 */
	public static final String GROUP_SHOW = "group.show"; //$NON-NLS-1$

	/**
	 * Pop-up menu: name of group for new actions (value
	 * <code>"group.new"</code>).
	 * <p>
	 * Examples for new actions are:
	 * <ul>
	 * <li>Create new class</li>
	 * <li>Create new interface</li>
	 * </ul>
	 * </p>
	 */
	public static final String GROUP_NEW = "group.new"; //$NON-NLS-1$

	/**
	 * Pop-up menu: name of group for build actions (value
	 * <code>"group.build"</code>).
	 */
	public static final String GROUP_BUILD = "group.build"; //$NON-NLS-1$
	/**
	 * Pop-up menu: name of group for reorganize actions (value
	 * <code>"group.reorganize"</code>).
	 */
	public static final String GROUP_REORGANIZE = IWorkbenchActionConstants.GROUP_REORGANIZE;

	/**
	 * Pop-up menu: name of group for source actions. This is an alias for
	 * <code>GROUP_GENERATE</code> to be more consistent with main menu bar
	 * structure.
	 * 
	 * @since 2.0
	 */
	public static final String GROUP_SOURCE = "group.source"; //$NON-NLS-1$

	/**
	 * Pop-up menu: name of group for additional actions (value
	 * <code>"additions"</code>).
	 */
	public static final String GROUP_ADDITIONS = "additions"; //$NON-NLS-1$

	/**
	 * Pop-up menu: name of group for properties actions (value
	 * <code>"group.properties"</code>).
	 */
	public static final String GROUP_PROPERTIES = "group.properties"; //$NON-NLS-1$

}
