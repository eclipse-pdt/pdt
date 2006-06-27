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
package org.eclipse.php.internal.ui;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.eclipse.osgi.util.NLS;

public final class PHPUIMessages extends NLS {

	private static final String BUNDLE_NAME = "org.eclipse.php.internal.ui.PHPUIMessages";//$NON-NLS-1$
	private static ResourceBundle fResourceBundle;

	public static String CodeAssistPreferencePage_autoActivationDelay;
	public static String CodeAssistPreferencePage_autoActivationDelayIntValue;
	public static String CodeAssistPreferencePage_autoActivationDelayPositive;
	public static String CodeAssistPreferencePage_autoActivationDisableSizeLimitLabel;
	public static String CodeAssistPreferencePage_autoActivationForClassNames;
	public static String CodeAssistPreferencePage_autoActivationForFunctionsKeywordsConstants;
	public static String CodeAssistPreferencePage_autoActivationForVariables;
	public static String CodeAssistPreferencePage_autoActivationLimitNumberIntValue;
	public static String CodeAssistPreferencePage_autoActivationLimitNumberPositive;
	public static String CodeAssistPreferencePage_autoActivationSectionLabel;
	public static String CodeAssistPreferencePage_autoActivationTriggersPHP;
	public static String CodeAssistPreferencePage_autoActivationTriggersPHPDoc;
	public static String CodeAssistPreferencePage_caseSensitiveForConstants;
	public static String CodeAssistPreferencePage_completionInserts;
	public static String CodeAssistPreferencePage_completionOverwrites;
	public static String CodeAssistPreferencePage_determineObjTypeFromOtherFiles;
	public static String CodeAssistPreferencePage_enableAutoActivation;
	public static String CodeAssistPreferencePage_filtersSectionLabel;
	public static String CodeAssistPreferencePage_insertSignleProposals;
	public static String CodeAssistPreferencePage_optionsSectionLabel;
	public static String CodeAssistPreferencePage_showClassNamesInGlobal;
	public static String CodeAssistPreferencePage_showConstantsAssist;
	public static String CodeAssistPreferencePage_showVariablesFromOtherFiles;
	public static String ColorPage_BoundryMaker;
	public static String ColorPage_CodeExample_0;
	public static String ColorPage_Comment;
	public static String ColorPage_Heredoc;
	public static String ColorPage_Keyword;
	public static String ColorPage_Normal;
	public static String ColorPage_Number;
	public static String ColorPage_Phpdoc;
	public static String ColorPage_TaskTag;
	public static String ColorPage_String;
	public static String ColorPage_Underline;
	public static String ColorPage_Variable;
	public static String CoreUtility_buildall_taskname;
	public static String CoreUtility_buildproject_taskname;
	public static String CoreUtility_job_title;
	public static String ElementValidator_cannotPerform;
	public static String ExceptionDialog_seeErrorLogMessage;
	public static String IncludePathDialogAccess_0;
	public static String InitializeAfterLoadJob_real_job_name;
	public static String InitializeAfterLoadJob_starter_job_name;
	public static String OptionalMessageDialog_dontShowAgain;
	public static String PHPAnnotationHover_multipleMarkersAtThisLine;
	public static String PHPEditor_codeassist_noCompletions;
	public static String PHPElementLabels_comma_string;
	public static String PHPElementLabels_concat_string;
	public static String PHPElementLabels_declseparator_string;
	public static String PHPElementProperties_name;
	public static String PHPImageLabelprovider_assert_wrongImage;
	public static String PHPPlugin_internal_error;
	public static String PHPUI_defaultDialogMessage;
	public static String PHPUIHelp_link_label;
	public static String PHPUIHelpContext_phpHelpCategory_label;
	public static String ResourceTransferDragAdapter_cannot_delete_files;
	public static String ResourceTransferDragAdapter_cannot_delete_resource;
	public static String ResourceTransferDragAdapter_moving_resource;
	public static String StatusBarUpdater_num_elements_selected;
	public static String EditorUtility_concatModifierStrings;

	public static String ProjectsWorkbookPage_projects_label;
	public static String ProjectsWorkbookPage_projects_add_button;
	public static String ProjectsWorkbookPage_projects_edit_button;
	public static String ProjectsWorkbookPage_projects_remove_button;
	public static String ProjectsWorkbookPage_chooseProjects_message;
	public static String ProjectsWorkbookPage_chooseProjects_title;

	public static String CPListLabelProvider_none;
	public static String CPListLabelProvider_all;
	public static String CPListLabelProvider_unknown_element_label;
	public static String CPListLabelProvider_new;
	public static String CPListLabelProvider_container;
	public static String CPListLabelProvider_twopart;
	public static String CPListLabelProvider_willbecreated;
	public static String CPListLabelProvider_unbound_library;
	public static String CPListLabelProvider_systemlibrary;
	public static String CPListLabelProvider_non_modifiable_attribute;

	public static String LibrariesWorkbookPage_libraries_label;
	public static String LibrariesWorkbookPage_libraries_remove_button;
	public static String LibrariesWorkbookPage_libraries_addzip_button;
	public static String LibrariesWorkbookPage_libraries_addextzip_button;
	public static String LibrariesWorkbookPage_libraries_addvariable_button;
	public static String LibrariesWorkbookPage_libraries_addlibrary_button;
	public static String LibrariesWorkbookPage_libraries_addincludepathfolder_button;
	public static String LibrariesWorkbookPage_libraries_edit_button;
	public static String LibrariesWorkbookPage_NewIncludePathDialog_new_title;
	public static String LibrariesWorkbookPage_NewIncludePathDialog_edit_title;
	public static String LibrariesWorkbookPage_NewIncludePathDialog_description;
	public static String LibrariesWorkbookPage_exclusion_added_title;
	public static String LibrariesWorkbookPage_exclusion_added_message;
	public static String LibrariesWorkbookPage_configurecontainer_error_title;
	public static String LibrariesWorkbookPage_configurecontainer_error_message;

	public static String IncludePathsBlock_tab_projects;
	public static String IncludePathsBlock_tab_libraries;
	public static String IncludePathsBlock_tab_order;
	public static String IncludePathsBlock_includepath_label;
	public static String IncludePathsBlock_includepath_up_button;
	public static String IncludePathsBlock_includepath_down_button;
	public static String IncludePathsBlock_includepath_checkall_button;
	public static String IncludePathsBlock_includepath_uncheckall_button;
	public static String IncludePathsBlock_warning_EntryMissing;
	public static String IncludePathsBlock_warning_EntriesMissing;
	public static String IncludePathsBlock_operationdesc_project;
	public static String IncludePathsBlock_operationdesc_php;

	public static String IncludePathsPropertyPage_error_message;
	public static String IncludePathsPropertyPage_error_title;
	public static String IncludePathsPropertyPage_job_title;
	public static String IncludePathsPropertyPage_no_php_project_message;
	public static String IncludePathsPropertyPage_closed_project_message;
	public static String IncludePathsPropertyPage_unsavedchanges_title;
	public static String IncludePathsPropertyPage_unsavedchanges_message;
	public static String IncludePathsPropertyPage_unsavedchanges_button_save;
	public static String IncludePathsPropertyPage_unsavedchanges_button_discard;
	public static String IncludePathsPropertyPage_unsavedchanges_button_ignore;

	public static String NewContainerDialog_error_invalidpath;
	public static String NewContainerDialog_error_enterpath;
	public static String NewContainerDialog_error_pathexists;
	public static String IncludePathDialogAccess_ExistingSourceFolderDialog_new_title;
	public static String IncludePathDialogAccess_ExistingSourceFolderDialog_new_description;
	public static String IncludePathDialogAccess_ExistingPHPFolderDialog_new_title;
	public static String IncludePathDialogAccess_ExistingPHPFolderDialog_new_description;
	public static String IncludePathDialogAccess_ZIPArchiveDialog_new_title;
	public static String IncludePathDialogAccess_ZIPArchiveDialog_new_description;
	public static String IncludePathDialogAccess_ZIPArchiveDialog_edit_title;
	public static String IncludePathDialogAccess_ZIPArchiveDialog_edit_description;
	public static String IncludePathDialogAccess_ExtZIPArchiveDialog_new_title;
	public static String IncludePathDialogAccess_ExtZIPArchiveDialog_edit_title;

	public static String NewVariableEntryDialog_title;
	public static String NewVariableEntryDialog_vars_extend;
	public static String NewVariableEntryDialog_configbutton_label;
	public static String NewVariableEntryDialog_vars_label;
	public static String NewVariableEntryDialog_ExtensionDialog_title;
	public static String NewVariableEntryDialog_ExtensionDialog_description;
	public static String NewVariableEntryDialog_info_isfolder;
	public static String NewVariableEntryDialog_info_noselection;
	public static String NewVariableEntryDialog_info_selected;

	public static String EditVariableEntryDialog_title;
	public static String EditVariableEntryDialog_filename_varlabel;
	public static String EditVariableEntryDialog_filename_variable_button;
	public static String EditVariableEntryDialog_filename_external_varbutton;
	public static String EditVariableEntryDialog_extvardialog_title;
	public static String EditVariableEntryDialog_extvardialog_description;
	public static String EditVariableEntryDialog_filename_error_notvalid;
	public static String EditVariableEntryDialog_filename_error_filenotexists;
	public static String EditVariableEntryDialog_filename_error_varnotexists;
	public static String EditVariableEntryDialog_filename_error_deviceinpath;
	public static String EditVariableEntryDialog_filename_warning_varempty;
	public static String EditVariableEntryDialog_filename_error_alreadyexists;

	public static String VariableBlock_vars_label;
	public static String VariableBlock_vars_add_button;
	public static String VariableBlock_vars_edit_button;
	public static String VariableBlock_vars_remove_button;
	public static String VariableBlock_operation_desc;
	public static String VariableBlock_job_description;
	public static String VariableBlock_needsbuild_title;
	public static String VariableBlock_needsbuild_message;
	public static String VariablePathDialogField_variabledialog_title;
	public static String CPVariableElementLabelProvider_reserved;
	public static String CPVariableElementLabelProvider_empty;

	public static String EditVariableEntryDialog_filename_empty;

	public static String VariableCreationDialog_titlenew;
	public static String VariableCreationDialog_titleedit;
	public static String VariableCreationDialog_name_label;
	public static String VariableCreationDialog_path_label;
	public static String VariableCreationDialog_path_file_button;
	public static String VariableCreationDialog_path_dir_button;
	public static String VariableCreationDialog_error_entername;
	public static String VariableCreationDialog_error_whitespace;
	public static String VariableCreationDialog_error_invalidname;
	public static String VariableCreationDialog_error_nameexists;
	public static String VariableCreationDialog_error_invalidpath;
	public static String VariableCreationDialog_warning_pathnotexists;
	public static String VariableCreationDialog_extjardialog_text;
	public static String VariableCreationDialog_extdirdialog_text;
	public static String VariableCreationDialog_extdirdialog_message;

	public static String IncludePathContainerWizard_pagecreationerror_title;
	public static String IncludePathContainerWizard_pagecreationerror_message;
	public static String IncludePathContainerWizard_new_title;
	public static String IncludePathContainerWizard_edit_title;

	public static String IncludePathContainerDefaultPage_title;
	public static String IncludePathContainerDefaultPage_description;
	public static String IncludePathContainerDefaultPage_path_label;
	public static String IncludePathContainerDefaultPage_path_error_enterpath;
	public static String IncludePathContainerDefaultPage_path_error_invalidpath;
	public static String IncludePathContainerDefaultPage_path_error_needssegment;
	public static String IncludePathContainerDefaultPage_path_error_alreadyexists;

	public static String IncludePathVariablesPreferencePage_title;
	public static String IncludePathVariablesPreferencePage_description;
	public static String IncludePathVariablesPreferencePage_savechanges_title;
	public static String IncludePathVariablesPreferencePage_savechanges_message;

	
	public static String IncludePathContainerSelectionPage_title;
	public static String IncludePathContainerSelectionPage_description;
	public static String MultipleFolderSelectionDialog_button;

	
	
	
	public static String OpenType_matchingResources;
	public static String OpenType_instructionText;
	public static String OpenType_DialogTitle;
	public static String OpenType_GroupFilterTitle;
	public static String OpenType_ConstantsFilterCheckboxName;
	public static String OpenType_FunctionsFilterCheckboxName;
	public static String OpenType_ClassesFilterCheckboxName;
	
	

	public static String PHPOutlinePage_Sort_label;
	public static String PHPOutlinePage_Sort_tooltip;
	public static String PHPOutlinePage_Sort_description;
	public static String PHPOutlinePage_mode_php;
	public static String PHPOutlinePage_mode_html;
	public static String PHPOutlinePage_mode_mixed;	
	public static String PHPOutlinePage_show_groups;	

	public static String IncludePathDialogAccess_IncludePathFolderDialog_new_title;
	public static String IncludePathDialogAccess_IncludePathFolderDialog_new_description;

	public static String PHPEditorPreferencePage_folding_title;
	public static String FoldingConfigurationBlock_enable;
	public static String FoldingConfigurationBlock_combo_caption;
	public static String FoldingConfigurationBlock_error_not_exist;
	public static String FoldingConfigurationBlock_info_no_preferences;
	public static String typingPage_autoClose_title;
	public static String typingPage_autoClose_string;
	public static String typingPage_autoClose_brackets;
	public static String typingPage_autoClose_braces;
	public static String typingPage_autoClose_phpDoc_and_commens;
	public static String typingPage_autoAdd_phpDoc_tags;
	
	public static String newPhpFile_create;
	public static String newPhpFile_openning;
	public static String newPhpFile_wizard_templatePage_title;
	public static String newPhpFile_wizard_templatePage_description;
	public static String newPhpFile_wizard_templatePage_usePhpTemplate;
	public static String newPhpFile_wizard_templatePage_phpTemplatesLocation;
	
	public static String PropertyAndPreferencePage_useprojectsettings_label;
	public static String PropertyAndPreferencePage_useworkspacesettings_change;
	public static String PropertyAndPreferencePage_showprojectspecificsettings_label;
	
	public static String ProjectSelectionDialog_title;
	public static String ProjectSelectionDialog_desciption;
	public static String ProjectSelectionDialog_filter;
	
	public static String TodoTaskPreferencePage_description;
	public static String TodoTaskPreferencePage_title;
	public static String TodoTaskConfigurationBlock_tasks_default;
	public static String TodoTaskConfigurationBlock_markers_tasks_high_priority;
	public static String TodoTaskConfigurationBlock_markers_tasks_normal_priority;
	public static String TodoTaskConfigurationBlock_markers_tasks_low_priority;
	public static String TodoTaskConfigurationBlock_markers_tasks_add_button;
	public static String TodoTaskConfigurationBlock_markers_tasks_edit_button;
	public static String TodoTaskConfigurationBlock_markers_tasks_remove_button;
	public static String TodoTaskConfigurationBlock_markers_tasks_setdefault_button;
	public static String TodoTaskConfigurationBlock_markers_tasks_name_column;
	public static String TodoTaskConfigurationBlock_markers_tasks_priority_column;
	public static String TodoTaskConfigurationBlock_casesensitive_label;
	public static String TodoTaskConfigurationBlock_needsbuild_title;
	public static String TodoTaskConfigurationBlock_needsfullbuild_message;
	public static String TodoTaskConfigurationBlock_needsprojectbuild_message;
	public static String TodoTaskInputDialog_new_title;
	public static String TodoTaskInputDialog_edit_title;
	public static String TodoTaskInputDialog_name_label;
	public static String TodoTaskInputDialog_priority_high;
	public static String TodoTaskInputDialog_priority_normal;
	public static String TodoTaskInputDialog_priority_low;
	public static String TodoTaskInputDialog_priority_label;
	public static String TodoTaskInputDialog_error_enterName;
	public static String TodoTaskInputDialog_error_comma;
	public static String TodoTaskInputDialog_error_entryExists;
	public static String TodoTaskInputDialog_error_noSpace;
	
	public static String PHPTextHover_createTextHover;
	public static String PHPTextHover_makeStickyHint;
	public static String PHPEditorPreferencePage_hoverTab_title;
	public static String PHPEditorHoverConfigurationBlock_annotationRollover;
	public static String PHPEditorHoverConfigurationBlock_hoverPreferences;
	public static String PHPEditorHoverConfigurationBlock_enabled;
	public static String PHPEditorHoverConfigurationBlock_keyModifier;
	public static String PHPEditorHoverConfigurationBlock_description;
	public static String PHPEditorHoverConfigurationBlock_modifierIsNotValid;
	public static String PHPEditorHoverConfigurationBlock_modifierIsNotValidForHover;
	public static String PHPEditorHoverConfigurationBlock_duplicateModifier;
	public static String PHPEditorHoverConfigurationBlock_nameColumnTitle;
	public static String PHPEditorHoverConfigurationBlock_modifierColumnTitle;
	public static String PHPEditorHoverConfigurationBlock_delimiter;
	public static String PHPEditorHoverConfigurationBlock_insertDelimiterAndModifierAndDelimiter;
	public static String PHPEditorHoverConfigurationBlock_insertModifierAndDelimiter;
	public static String PHPEditorHoverConfigurationBlock_insertDelimiterAndModifier;
	public static String PHPEditorHoverConfigurationBlock_showAffordance;
	
	public static String PHPFormatterPreferencePage_description;
	public static String PHPFormatterPreferencePage_title;
		
	public static String Preferences_php_editor_useAspTagsAsPhp_label;

	public static String PHPInterpreterPreferencePage_description;
	public static String PHPInterpreterPreferencePage_title;
	public static String PHPVersionComboName;
	public static String PHPVersionConfigurationBlock_needsbuild_title;
	public static String PHPVersionConfigurationBlock_needsfullbuild_message;
	public static String PHPVersionConfigurationBlock_needsprojectbuild_message;
	
	public static String PHPManualConfigurationBlock_new;
	public static String PHPManualConfigurationBlock_edit;
	public static String PHPManualConfigurationBlock_remove;
	public static String PHPManualConfigurationBlock_default;
	public static String PHPManualConfigurationBlock_siteName;
	public static String PHPManualConfigurationBlock_url;
	public static String PHPManualConfigurationBlock_fileExtension;
	public static String PHPManualConfigurationBlock_openInNewBrowser;
	public static String NewPHPManualSiteDialog_name;
	public static String NewPHPManualSiteDialog_url;
	public static String NewPHPManualSiteDialog_fileExtension;
	public static String NewPHPManualSiteDialog_siteOrUrlNotSpecified;
	public static String NewPHPManualSiteDialog_incorrectUrl;
	public static String NewPHPManualSiteDialog_nameAlreadyInUse;
	public static String NewPHPManualSiteDialog_urlAlreadyInUse;
	public static String NewPHPManualSiteDialog_updateTitle;
	public static String NewPHPManualSiteDialog_newTitle;
	public static String NewPHPManualSiteDialog_chooseDir;
	
	public static String PHPBasePreferencePage_description;
	public static String PHPBasePreferencePage_doubleclick_action;
	public static String PHPBasePreferencePage_doubleclick_gointo;
	public static String PHPBasePreferencePage_doubleclick_expand;
	
	static {
		NLS.initializeMessages(BUNDLE_NAME, PHPUIMessages.class);
	}

	private PHPUIMessages() {
		// Do not instantiate
	}
	
	public static ResourceBundle getResourceBundle() {
		try {
			if (fResourceBundle == null)
				fResourceBundle = ResourceBundle.getBundle(BUNDLE_NAME);
		}
		catch (MissingResourceException x) {
			fResourceBundle = null;
		}
		return fResourceBundle;
	}

    public static String PHPVersionGroup_OptionBlockTitle;
    public static String PHPVersionGroup_EnableProjectSettings;
    public static String PHPVersionGroup_ConfigWorkspaceSettings;
    public static String PHPProjectCreationWizard_PageTile;
}