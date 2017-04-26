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

import org.eclipse.osgi.util.NLS;

import com.ibm.icu.text.MessageFormat;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.php.internal.ui.actions.messages"; //$NON-NLS-1$
	public static String NamespaceGroupingActionGroup_0;
	public static String NamespaceGroupingActionGroup_2;
	public static String PHPRefactorActionGroup_1;
	public static String PHPRefactorActionGroup_3;
	public static String ReorgMoveAction_0;
	public static String ReorgMoveAction_1;
	public static String SelectionHandler_0;
	public static String DeleteAction_0;
	public static String GenerateIncludePathActionGroup_0;
	public static String LibraryFolderAction_Dialog_description;
	public static String LibraryFolderAction_Dialog_title;
	public static String LibraryFolderAction_UseAsLibraryFolder_label;
	public static String LibraryFolderAction_UseAsSourceFolder_label;
	public static String OpenTypeHierarchyAction_0;
	public static String OpenTypeHierarchyAction_3;
	public static String OpenCallHierarchyAction_0;
	public static String OpenViewActionGroup_ShowInLabel;
	public static String RemoveFromIncludepathAction_0;

	public static String OrganizeImportsAction_label;
	public static String OrganizeImportsAction_tooltip;
	public static String OrganizeImportsAction_description;
	public static String OrganizeImportsAction_multi_error_parse;
	public static String OrganizeImportsAction_multi_error_unresolvable;
	public static String OrganizeImportsAction_selectiondialog_title;
	public static String OrganizeImportsAction_selectiondialog_message;
	public static String OrganizeImportsAction_summary_added_singular;
	public static String OrganizeImportsAction_summary_added_plural;
	public static String OrganizeImportsAction_summary_removed_singular;
	public static String OrganizeImportsAction_summary_removed_plural;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}

	public static String format(String message, Object object) {
		return MessageFormat.format(message, new Object[] { object });
	}

	public static String format(String message, Object[] objects) {
		return MessageFormat.format(message, objects);
	}
}
