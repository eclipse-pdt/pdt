/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.phpunit;

import java.text.MessageFormat;

import org.eclipse.osgi.util.NLS;

public class PHPUnitMessages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.php.phpunit.PHPUnitMessages"; //$NON-NLS-1$
	public static String CounterPanel_Errors;
	public static String CounterPanel_Failures;
	public static String CounterPanel_Runs;
	public static String EnableStackFilterAction_Description;
	public static String EnableStackFilterAction_Name;
	public static String OpenEditorAction_Cant_Open;
	public static String OpenEditorAction_Error;
	public static String OpenEditorAction_Go_Call;
	public static String OpenEditorAction_Go_Class;
	public static String OpenEditorAction_Go_File;
	public static String OpenEditorAction_Go_Func;
	public static String OpenEditorAction_Go_Method;
	public static String OpenEditorAction_Go_Occurence;
	public static String PHPUnitConnection_Launching;
	public static String PHPUnitConnection_Previous_session_exists;
	public static String PHPUnitConnection_Unable_to_run;
	public static String PHPUnitLaunchConfigurationDelegate_Bad_Config;
	public static String PHPUnitLaunchConfigurationDelegate_Launching;
	public static String PHPUnitLaunchConfigurationDelegate_no_composer_dependency;
	public static String PHPUnitLaunchConfigurationDelegate_no_phar;
	public static String PHPUnitLaunchConfigurationTab_2;
	public static String PHPUnitLaunchConfigurationTab_3;
	public static String PHPUnitLaunchConfigurationTab_5;
	public static String PHPUnitLaunchConfigurationTab_6;
	public static String PHPUnitLaunchConfigurationTab_7;
	public static String PHPUnitLaunchConfigurationTab_Additional;
	public static String PHPUnitLaunchConfigurationTab_Bad_Project_Name;
	public static String PHPUnitLaunchConfigurationTab_Browse;
	public static String PHPUnitLaunchConfigurationTab_Choose_Container;
	public static String PHPUnitLaunchConfigurationTab_Choose_Project;
	public static String PHPUnitLaunchConfigurationTab_Choose_Test;
	public static String PHPUnitLaunchConfigurationTab_Class;
	public static String PHPUnitLaunchConfigurationTab_Container_Selection;
	public static String PHPUnitLaunchConfigurationTab_Coverage;
	public static String PHPUnitLaunchConfigurationTab_Elements;
	public static String PHPUnitLaunchConfigurationTab_Execution_parameters;
	public static String PHPUnitLaunchConfigurationTab_Generate_Report;
	public static String PHPUnitLaunchConfigurationTab_No_Container;
	public static String PHPUnitLaunchConfigurationTab_No_Element_To_Test;
	public static String PHPUnitLaunchConfigurationTab_No_Project;
	public static String PHPUnitLaunchConfigurationTab_No_Test;
	public static String PHPUnitLaunchConfigurationTab_Preferences_link;
	public static String PHPUnitLaunchConfigurationTab_Project;
	public static String PHPUnitLaunchConfigurationTab_Project_No_Tests;
	public static String PHPUnitLaunchConfigurationTab_Project_Not_PHP;
	public static String PHPUnitLaunchConfigurationTab_Project_Selection;
	public static String PHPUnitLaunchConfigurationTab_Project_unavailable;
	public static String PHPUnitLaunchConfigurationTab_Run_Container;
	public static String PHPUnitLaunchConfigurationTab_Search;
	public static String PHPUnitLaunchConfigurationTab_Test;
	public static String PHPUnitLaunchConfigurationTab_Test_Selection;
	public static String PHPUnitLaunchConfigurationTab_Unable_find_dependencies;
	public static String PHPUnitLaunchConfigurationTab_Use_composer;
	public static String PHPUnitLaunchConfigurationTab_Use_global_phar;
	public static String PHPUnitLaunchShortcut_New_Configuration;
	public static String PHPUnitLaunchShortcut_Unable_To_Generate;
	public static String PHPUnitPreferencePage_Coverage;
	public static String PHPUnitPreferencePage_Generate_Report;
	public static String PHPUnitPreferencePage_Name;
	public static String PHPUnitPreferencePage_Phpunit_phar;
	public static String PHPUnitPreferencePage_Port;
	public static String PHPUnitPreferencePage_PortOccupied;
	public static String PHPUnitPreferencePage_TransformedXMLOutput0;
	public static String PHPUnitSearchEngine_Searching;
	public static String PHPUnitTestException_0;
	public static String PHPUnitTestException_1;
	public static String PHPUnitValidator_Class_Exists;
	public static String PHPUnitValidator_Class_Invalid;
	public static String PHPUnitValidator_Empty_Class_Name;
	public static String PHPUnitValidator_File_Exists;
	public static String PHPUnitValidator_File_Not_PHP;
	public static String PHPUnitValidator_Folder_Name_Empty;
	public static String PHPUnitValidator_Folder_No_Suites;
	public static String PHPUnitValidator_Folder_Not_Accessible;
	public static String PHPUnitValidator_Folder_Not_Exists;
	public static String PHPUnitValidator_Multiple_Elements;
	public static String PHPUnitValidator_No_Container;
	public static String PHPUnitValidator_No_Element;
	public static String PHPUnitValidator_No_Element_In_Project;
	public static String PHPUnitValidator_No_FileName;
	public static String PHPUnitValidator_No_Project;
	public static String PHPUnitValidator_No_Suites;
	public static String PHPUnitValidator_Not_Accessible;
	public static String PHPUnitValidator_Not_Exists;
	public static String PHPUnitValidator_Not_Folder;
	public static String PHPUnitValidator_Not_In_Project;
	public static String PHPUnitValidator_Not_Project;
	public static String PHPUnitValidator_Not_Test;
	public static String PHPUnitValidator_Project_Not_Accessible;
	public static String PHPUnitValidator_Project_Not_PHP;
	public static String PHPUnitValidator_Unknown_Element;
	public static String PHPUnitView_Cant_Rerun;
	public static String PHPUnitView_Collapse_Name;
	public static String PHPUnitView_Collapse_ToolTip;
	public static String PHPUnitView_Expand_Name;
	public static String PHPUnitView_Expand_ToolTip;
	public static String PHPUnitView_Failures_Name;
	public static String PHPUnitView_Failures_Tooltip;
	public static String PHPUnitView_Layout;
	public static String PHPUnitView_Orient_Auto;
	public static String PHPUnitView_Orient_Horizont;
	public static String PHPUnitView_Orient_Vertical;
	public static String PHPUnitView_Rerun_Config;
	public static String PHPUnitView_Rerun_Error;
	public static String PHPUnitView_Rerun_Error_Message;
	public static String PHPUnitView_Run_Error;
	public static String PHPUnitView_Run_SocketError;
	public static String PHPUnitView_Run_ToolTip;
	public static String PHPUnitView_Stop_Name;
	public static String PHPUnitView_Stop_ToolTip;
	public static String PHPUnitView_Tab_Coverage;
	public static String PHPUnitView_Tab_Trace;
	public static String PHPUnitView_Tab_Diff;
	public static String PHPUnitWizard_Error;
	public static String PHPUnitWizard_Error_Title;
	public static String PHPUnitWizard_Title;
	public static String PHPUnitWizardPage_0;
	public static String PHPUnitWizardPage_1;
	public static String PHPUnitWizardPage_10;
	public static String PHPUnitWizardPage_11;
	public static String PHPUnitWizardPage_15;
	public static String PHPUnitWizardPage_2;
	public static String PHPUnitWizardPage_20;
	public static String PHPUnitWizardPage_21;
	public static String PHPUnitWizardPage_3;
	public static String PHPUnitWizardPage_4;
	public static String PHPUnitWizardPage_5;
	public static String PHPUnitWizardPage_6;
	public static String RerunAction_Debug;
	public static String RerunAction_Run;
	public static String ScrollLockAction_Name;
	public static String ScrollLockAction_ToolTip;
	public static String ShowNextFailureAction_Name;
	public static String ShowNextFailureAction_ToolTip;
	public static String ShowPreviousFailureAction_Name;
	public static String ShowPreviousFailureAction_ToolTip;
	public static String TestCaseWizard_0;
	public static String TestCaseWizardPage_0;
	public static String TestCaseWizardPage_1;
	public static String TestCaseWizardPage_2;
	public static String TestCaseWizardPage_3;
	public static String TestCaseWizardPage_4;
	public static String TestCaseWizardPage_5;
	public static String TestLabelProvider_0;
	public static String TestLabelProvider_1;
	public static String TestSuiteWizard_0;
	public static String TestSuiteWizardPage_2;
	public static String TestSuiteWizardPage_4;
	public static String TestSuiteWizardPage_5;

	static {
		NLS.initializeMessages(BUNDLE_NAME, PHPUnitMessages.class);
	}

	private PHPUnitMessages() {
	}

	public static String format(String message, Object object) {
		return MessageFormat.format(message, new Object[] { object });
	}

	public static String format(String message, Object[] objects) {
		return MessageFormat.format(message, objects);
	}

}
