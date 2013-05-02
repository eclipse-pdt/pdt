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
package org.eclipse.php.internal.ui.actions;

public class PHPActionConstants {

	// Navigate menu

	/**
	 * Navigate menu: name of standard Goto Type global action (value
	 * <code>"org.eclipse.php.ui.actions.GoToType"</code>).
	 */
	public static final String GOTO_TYPE = "org.eclipse.php.ui.actions.GoToType"; //$NON-NLS-1$

	/**
	 * Navigate menu: name of standard Open global action (value
	 * <code>"org.eclipse.php.ui.actions.Open"</code>).
	 */
	public static final String OPEN = "org.eclipse.php.ui.actions.Open"; //$NON-NLS-1$

	/**
	 * Navigate menu: name of standard Open Type Hierarchy global action (value
	 * <code>"org.eclipse.php.ui.actions.OpenTypeHierarchy"</code>).
	 */
	public static final String OPEN_TYPE_HIERARCHY = "org.eclipse.php.ui.actions.OpenTypeHierarchy"; //$NON-NLS-1$

	/**
	 * Navigate menu: name of standard Open Type Hierarchy global action (value
	 * <code>"org.eclipse.php.ui.actions.OpenTypeHierarchy"</code>).
	 */
	public static final String OPEN_CALL_HIERARCHY = "org.eclipse.php.ui.actions.OpenCallHierarchy"; //$NON-NLS-1$

	/**
	 * Navigate menu: name of standard Show in Navigator View global action
	 * (value <code>"org.eclipse.php.ui.actions.ShowInNaviagtorView"</code>).
	 */
	public static final String SHOW_IN_NAVIGATOR_VIEW = "org.eclipse.php.ui.actions.ShowInNaviagtorView"; //$NON-NLS-1$

	/**
	 * Edit menu: name of standard Code Assist global action (value
	 * <code>"org.eclipse.php.ui.actions.ContentAssist"</code>).
	 */
	public static final String CONTENT_ASSIST = "org.eclipse.php.ui.actions.ContentAssist"; //$NON-NLS-1$

	// Source menu

	/**
	 * Source menu: name of standard Comment global action (value
	 * <code>"org.eclipse.php.ui.actions.Comment"</code>).
	 */

	public static final String COMMENT = "org.eclipse.php.ui.actions.Comment"; //$NON-NLS-1$

	/**
	 * Source menu: name of standard Uncomment global action (value
	 * <code>"org.eclipse.php.ui.actions.Uncomment"</code>).
	 */
	public static final String UNCOMMENT = "org.eclipse.php.ui.actions.Uncomment"; //$NON-NLS-1$

	/**
	 * Source menu: name of standard ToggleComment global action (value
	 * <code>"org.eclipse.php.ui.actions.ToggleComment"</code>).
	 * 
	 * @since 3.0
	 */
	public static final String TOGGLE_COMMENT = "org.eclipse.php.ui.actions.ToggleComment"; //$NON-NLS-1$

	/**
	 * Source menu: name of standard Add PHP Doc global action (value
	 * <code>"org.eclipse.php.ui.actions.AddDescriptionAction"</code>).
	 * 
	 * @since 3.0
	 */
	public static final String ADD_DESCRIPTION = "org.eclipse.php.ui.actions.AddDescriptionAction"; //$NON-NLS-1$

	/**
	 * Source menu: name of standard Add PHP Doc global action (value
	 * <code>"AddDescription"</code>).
	 * 
	 * @since 3.0
	 */
	public static final String ADD_DESCRIPTION_NAME = "AddDescription"; //$NON-NLS-1$

	/**
	 * Source menu: name of standard Block Comment global action (value
	 * <code>"org.eclipse.php.ui.actions.AddBlockComment"</code>).
	 * 
	 * @since 3.0
	 */
	public static final String ADD_BLOCK_COMMENT = "org.eclipse.php.ui.actions.AddBlockComment"; //$NON-NLS-1$

	/**
	 * Source menu: name of standard Block Uncomment global action (value
	 * <code>"org.eclipse.php.ui.actions.RemoveBlockComment"</code>).
	 * 
	 * @since 3.0
	 */
	public static final String REMOVE_BLOCK_COMMENT = "org.eclipse.php.ui.actions.RemoveBlockComment"; //$NON-NLS-1$

	/**
	 * Source menu: name of standard Format global action (value
	 * <code>"org.eclipse.php.ui.actions.Format"</code>).
	 */
	public static final String FORMAT = "org.eclipse.php.ui.actions.Format"; //$NON-NLS-1$

	/**
	 * Source menu: name of standard Format Element global action (value
	 * <code>"org.eclipse.php.ui.actions.FormatElement"</code>).
	 * 
	 * @since 3.0
	 */
	public static final String FORMAT_ELEMENT = "org.eclipse.php.ui.actions.FormatElement"; //$NON-NLS-1$

	/**
	 * Refactor menu: name of standard Move Element global action (value
	 * <code>"org.eclipse.php.ui.actions.Move"</code>).
	 */
	public static final String MOVE = "org.eclipse.php.ui.actions.Move"; //$NON-NLS-1$

	/**
	 * Refactor menu: name of standard Rename Element global action (value
	 * <code>"org.eclipse.php.ui.actions.Rename"</code>).
	 */
	public static final String RENAME = "org.eclipse.php.ui.actions.Rename"; //$NON-NLS-1$

	// Edit menu

	/**
	 * Edit menu: name of standard Show PHPDoc global action (value
	 * <code>"org.eclipse.jdt.php.actions.ShowPHPDoc"</code>).
	 */
	public static final String SHOW_PHP_DOC = "org.eclipse.php.ui.actions.ShowPHPDoc"; //$NON-NLS-1$

	/**
	 * Search menu: name of standard method exit occurrences global action
	 * (value <code>"org.eclipse.php.ui.actions.MethodExitOccurrences"</code>).
	 * 
	 * @since 3.4
	 */
	public static final String FIND_METHOD_EXIT_OCCURRENCES = "org.eclipse.php.ui.actions.MethodExitOccurrences"; 		 //$NON-NLS-1$

	/**
	 * Name of toggle mark occurrences global action (value
	 * <code>"org.eclipse.php.ui.actions.toggleMarkOccurrences"</code>).
	 * 
	 * @since 3.4
	 */
	public static final String TOGGLE_MARK_OCCURRENCES = "org.eclipse.php.ui.actions.toggleMarkOccurrences"; //$NON-NLS-1$
}
