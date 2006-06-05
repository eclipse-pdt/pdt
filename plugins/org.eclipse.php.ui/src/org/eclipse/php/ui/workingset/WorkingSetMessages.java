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
package org.eclipse.php.ui.workingset;

import org.eclipse.osgi.util.NLS;

public final class WorkingSetMessages extends NLS {

	private static final String BUNDLE_NAME = "org.eclipse.php.ui.workingset.WorkingSetMessages";//$NON-NLS-1$

	private WorkingSetMessages() {
		// Do not instantiate
	}

	public static String AbstractWorkingSetPage_workingSet_name;
	public static String AbstractWorkingSetPage_warning_nameMustNotBeEmpty;
	public static String AbstractWorkingSetPage_warning_workingSetExists;
	public static String AbstractWorkingSetPage_warning_nameWhitespace;

	public static String PHPWorkingSetPage_title;
	public static String PHPWorkingSetPage_workingSet_name;
	public static String PHPWorkingSetPage_workingSet_description;
	public static String PHPWorkingSetPage_workingSet_content;
	public static String PHPWorkingSetPage_warning_nameMustNotBeEmpty;
	public static String PHPWorkingSetPage_warning_workingSetExists;
	public static String PHPWorkingSetPage_warning_resourceMustBeChecked;
	public static String PHPWorkingSetPage_warning_nameWhitespace;
	public static String PHPWorkingSetPage_projectClosedDialog_message;
	public static String PHPWorkingSetPage_projectClosedDialog_title;
	public static String PHPWorkingSetPage_selectAll_label;
	public static String PHPWorkingSetPage_selectAll_toolTip;
	public static String PHPWorkingSetPage_deselectAll_label;
	public static String PHPWorkingSetPage_deselectAll_toolTip;

	public static String SelectWorkingSetAction_text;
	public static String SelectWorkingSetAction_toolTip;

	public static String EditWorkingSetAction_text;
	public static String EditWorkingSetAction_toolTip;
	public static String EditWorkingSetAction_error_nowizard_title;
	public static String EditWorkingSetAction_error_nowizard_message;

	public static String ClearWorkingSetAction_text;
	public static String ClearWorkingSetAction_toolTip;

	public static String ConfigureWorkingSetAction_label;
	public static String ViewActionGroup_show_label;
	public static String ViewActionGroup_projects_label;
	public static String ViewActionGroup_workingSets_label;

	public static String WorkingSetModel_histroy_name;
	public static String WorkingSetModel_others_name;

	public static String WorkingSetConfigurationDialog_title;
	public static String WorkingSetConfigurationDialog_message;
	public static String WorkingSetConfigurationDialog_new_label;
	public static String WorkingSetConfigurationDialog_edit_label;
	public static String WorkingSetConfigurationDialog_remove_label;
	public static String WorkingSetConfigurationDialog_up_label;
	public static String WorkingSetConfigurationDialog_down_label;
	public static String WorkingSetConfigurationDialog_selectAll_label;
	public static String WorkingSetConfigurationDialog_deselectAll_label;

	public static String OpenCloseWorkingSetAction_close_label;
	public static String OpenCloseWorkingSetAction_close_error_title;
	public static String OpenCloseWorkingSetAction_close_error_message;
	public static String OpenCloseWorkingSetAction_open_label;
	public static String OpenCloseWorkingSetAction_open_error_title;
	public static String OpenCloseWorkingSetAction_open_error_message;
	public static String OpenPropertiesWorkingSetAction_label;
	public static String RemoveWorkingSetElementAction_label;

	static {
		NLS.initializeMessages(BUNDLE_NAME, WorkingSetMessages.class);
	}
}