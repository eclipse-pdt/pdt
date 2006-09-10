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
package org.eclipse.php.debug.ui;

import org.eclipse.osgi.util.NLS;

/**
 * Strings used by PHP Core
 * 
 */
public class PHPDebugUIMessages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.php.debug.ui.PHPDebugUIPluginResources";//$NON-NLS-1$

	public static String EnableSetCondition_1;
	public static String EnterCondition_1;

	public static String InstalledPHPsBlock_16;
	public static String SetCondition_1;
	public static String ErrorCreatingBreakpoint_1;
	public static String BreakpointCreated_1;
	public static String MPresentation_Terminated_1;
	public static String MPresentation_PHP_APP_1;
	public static String MPresentation_Stepping_1;
	public static String MPresentation_Suspended_1;
	public static String MPresentation_SLineBreakpoint_1;
	public static String MPresentation_File_1;
	public static String MPresentation_ATLine_1;
	public static String PHPPrimitiveValueEditor_0;
	public static String PHPPrimitiveValueEditor_1;
	public static String PHPPrimitiveValueEditor_2;
	public static String PHPPrimitiveValueEditor_3;
	public static String PHPPrimitiveValueEditor_4;
	public static String PHPRunToLineAdapter_0;

	public static String PHPRunToLineAdapter_1;

	public static String PHPRunToLineAdapter_2;

	public static String BrowseWorkspace;
	public static String BrowseFilesystem;
	public static String WorkingDirectory;
	public static String PHP_File;
	public static String Browse;
	public static String Location;
	public static String PHP_Location_Message;
	public static String PHP_Location_No_Empty;
	public static String PHP_Location_Not_Exist;
	public static String PHP_Location_Not_File;
	public static String Project_Folder_Not_Exist;
	public static String Project_Folder_Not_Directory;
	public static String PHP_File_Not_Exist;
	public static String Select_Resource;
	public static String Select_Project_Folder;

	//  installed php exes	
	public static String PHPsPreferencePage_1;
	public static String PHPsPreferencePage_2;
	public static String PHPsPreferencePage_10;
	public static String PHPsPreferencePage_11;
	public static String PHPsPreferencePage_13;

	public static String InstalledPHPsBlock_0;
	public static String InstalledPHPsBlock_1;
	public static String InstalledPHPsBlock_2;
	public static String InstalledPHPsBlock_3;
	public static String InstalledPHPsBlock_4;
	public static String InstalledPHPsBlock_5;
	public static String InstalledPHPsBlock_6;
	public static String InstalledPHPsBlock_7;
	public static String InstalledPHPsBlock_8;
	public static String InstalledPHPsBlock_9;
	public static String InstalledPHPsBlock_10;
	public static String InstalledPHPsBlock_11;
	public static String InstalledPHPsBlock_12;
	public static String InstalledPHPsBlock_13;
	public static String InstalledPHPsBlock_14;
	public static String InstalledPHPsBlock_15;

	public static String PHPexe_executable_was_not_found_1;
	public static String PHPexe_ok_2;
	public static String PHPexe_Run_With_Debug_Info;

	public static String addPHPexeDialog_browse1;
	public static String addPHPexeDialog_duplicateName;
	public static String addPHPexeDialog_enterLocation;
	public static String addPHPexeDialog_enterName;
	public static String addPHPexeDialog_phpHome;
	public static String addPHPexeDialog_phpName;
	public static String addPHPexeDialog_locationNotExists;
	public static String addPHPexeDialog_pickPHPRootDialog_message;
	public static String AddPHPexeDialog_PHP_name_must_be_a_valid_file_name___0__1;
	public static String AddPHPexeDialog_0;

	public static String PHPexesComboBlock_1;
	public static String PHPexesComboBlock_2;
	public static String PHPexesComboBlock_3;

	public static String ShowPHPsPreferencePageTitle;

	public static String launch_failure_no_target;
	public static String launch_failure_no_config;

	public static String launch_failure_msg_title;
	public static String launch_failure_exec_msg_text;
	public static String launch_failure_server_msg_text;

	public static String launch_noexe_msg_title;
	public static String launch_noexe_msg_text;

	public static String PhpDebugPreferencePage_0;
	public static String PhpDebugPreferencePage_1;
	public static String PhpDebugPreferencePage_2;
	public static String PhpDebugPreferencePage_3;
	public static String PhpDebugPreferencePage_4;
	public static String PhpDebugPreferencePage_5;
	public static String PhpDebugPreferencePage_6;
	public static String PhpDebugPreferencePage_7;
	public static String PhpDebugPreferencePage_8;
	public static String PhpDebugPreferencePage_9;
	public static String PhpDebugPreferencePage_10;
	public static String PhpDebugPreferencePage_11;
	
	public static String PHPsComboBlock_NoPHPsTitle;
	public static String PHPsComboBlock_noPHPsMessage;
	public static String ShowView_errorTitle;

	public static String ConfigureProjectSettings;
	public static String ConfigureWorkspaceSettings;
	public static String EnableProjectSettings;
	public static String PropertyPreferencePage_01;
	public static String PropertyPreferencePage_02;

	public static String SourceNotFoundEditorInput_Source_not_found_for__0__2;
	public static String SourceNotFoundEditorInput_Source_Not_Found_1;

	static {
		// load message values from bundle file
		NLS.initializeMessages(BUNDLE_NAME, PHPDebugUIMessages.class);
	}

	private PHPDebugUIMessages() {
		// cannot create new instance
	}

}
