/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.ui.explorer;

import org.eclipse.osgi.util.NLS;

public final class ExplorerMessages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.php.ui.explorer.ExplorerMessages";//$NON-NLS-1$

	private ExplorerMessages() {
		// Do not instantiate
	}

	public static String DragAdapter_deleting;
	public static String DragAdapter_problem;
	public static String DragAdapter_problemTitle;
	public static String DragAdapter_refreshing;
	public static String DropAdapter_alreadyExists;
	public static String DropAdapter_errorSame;
	public static String DropAdapter_errorSubfolder;
	public static String DropAdapter_errorTitle;
	public static String DropAdapter_errorMessage;
	public static String DropAdapter_question;
	public static String ShowInNavigator_description;
	public static String ShowInNavigator_error;
	public static String ShowInNavigator_label;
	public static String PHPExplorer_filters;
	public static String PHPExplorer_gotoTitle;
	public static String PHPExplorer_openPerspective;
	public static String PHPExplorer_refactoringTitle;
	public static String PHPExplorer_referencedLibs;
	public static String PHPExplorer_binaryProjects;
	public static String PHPExplorer_title;
	public static String PHPExplorer_toolTip;
	public static String PHPExplorer_toolTip2;
	public static String PHPExplorer_openWith;
	public static String PHPExplorer_element_not_present;
	public static String PHPExplorer_filteredDialog_title;
	public static String PHPExplorer_notFound;
	public static String PHPExplorer_removeFilters;
	public static String SelectionTransferDropAdapter_error_title;
	public static String SelectionTransferDropAdapter_error_message;
	public static String SelectionTransferDropAdapter_dialog_title;
	public static String SelectionTransferDropAdapter_dialog_preview_label;
	public static String SelectionTransferDropAdapter_dialog_question;
	public static String CollapseAllAction_label;
	public static String CollapseAllAction_tooltip;
	public static String CollapseAllAction_description;
	public static String PHPExplorerPart_workspace;

	static {
		NLS.initializeMessages(BUNDLE_NAME, ExplorerMessages.class);
	}

}
