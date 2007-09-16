package org.eclipse.php.internal.ui;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.eclipse.osgi.util.NLS;

public class PHPUIMessages {
	private static final String BUNDLE_NAME = "org.eclipse.php.internal.ui.PHPUIMessages";
	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);
	private static ResourceBundle fResourceBundle;
	private static final String BUNDLE_FOR_CONSTRUCTED_KEYS = "org.eclipse.ui.texteditor.ConstructedTextEditorMessages"; //$NON-NLS-1$
	private static ResourceBundle fgBundleForConstructedKeys = ResourceBundle.getBundle(BUNDLE_FOR_CONSTRUCTED_KEYS);

	private PHPUIMessages() {
	}

	/**
	 * Returns the message bundle which contains constructed keys.
	 *
	 * @since 3.1
	 * @return the message bundle
	 */
	public static ResourceBundle getBundleForConstructedKeys() {
		return fgBundleForConstructedKeys;
	}

	static {
		NLS.initializeMessages(BUNDLE_NAME, PHPUIMessages.class);
	}

	public static ResourceBundle getResourceBundle() {
		try {
			if (fResourceBundle == null)
				fResourceBundle = ResourceBundle.getBundle(BUNDLE_NAME);
		} catch (MissingResourceException x) {
			fResourceBundle = null;
		}
		return fResourceBundle;
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return "";
		}
	}

	// ----- Internal UI ------

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
	public static String CodeAssistPreferencePage_showNonStrictOptions;
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
	public static String PHPEditorTextHoverDescriptor_cannot_create_message_decorator_error;
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
	public static String NewVariableEntryDialog_variable_non_existent_location;

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
	public static String typingPage_smartTab_title;
	public static String PHPEditorPreferencePage_typing_smartTab;
	public static String SmartTypingConfigurationBlock_tabs_message_tooltip;
	public static String SmartTypingConfigurationBlock_tabs_message_tab_text;
	public static String SmartTypingConfigurationBlock_tabs_message_others_text;
	public static String typingPage_autoClose_string;
	public static String typingPage_autoClose_brackets;
	public static String typingPage_autoClose_braces;
	public static String typingPage_autoClose_phpDoc_and_commens;
	public static String typingPage_autoAdd_phpDoc_tags;
	public static String typingPage_autoAdd_phpClose_tags;

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
	public static String NewPHPManualSiteDialog_siteNotSpecified;
	public static String NewPHPManualSiteDialog_incorrectUrl;
	public static String NewPHPManualSiteDialog_nameAlreadyInUse;
	public static String NewPHPManualSiteDialog_updateTitle;
	public static String NewPHPManualSiteDialog_newTitle;
	public static String NewPHPManualSiteDialog_chooseDir;
	public static String NewPHPManualSiteDialog_chooseCHMFile;
	public static String NewPHPManualSiteDialog_fileDoesntExist;
	public static String NewPHPManualSiteDialog_remoteSiteURL;
	public static String NewPHPManualSiteDialog_localDirectory;
	public static String NewPHPManualSiteDialog_windowsCHMFile;
	public static String NewPHPManualSiteDialog_fileExtension;
	public static String NewPHPManualSiteDialog_dirDoesntExist;

	public static String PHPBasePreferencePage_description;
	public static String PHPBasePreferencePage_doubleclick_action;
	public static String PHPBasePreferencePage_doubleclick_gointo;
	public static String PHPBasePreferencePage_doubleclick_expand;

	public static String PHPVersionGroup_OptionBlockTitle;
	public static String PHPVersionGroup_EnableProjectSettings;
	public static String PHPVersionGroup_ConfigWorkspaceSettings;
	public static String PHPProjectCreationWizard_PageTile;
	public static String PHPProjectCreationWizard_LoadPagesFailure;

	//  ----- Folding ------

	public static String DefaultPHPFoldingPreferenceBlock_title;
	public static String DefaultPHPFoldingPreferenceBlock_classes;
	public static String DefaultPHPFoldingPreferenceBlock_includes;
	public static String DefaultPHPFoldingPreferenceBlock_functions;
	public static String DefaultPHPFoldingPreferenceBlock_PHPdoc;
	public static String EmptyPHPFoldingPreferenceBlock_emptyCaption;

	//  ----- Action messages ------

	public static String OpenWithMenu_label;
	public static String RefactorMenu_label;
	public static String SourceMenu_label;

	public static String OpenAction_label;
	public static String OpenAction_tooltip;
	public static String OpenAction_description;
	public static String OpenAction_declaration_label;
	public static String OpenAction_select_element;
	public static String OpenAction_error_title;
	public static String OpenAction_error_message;
	public static String OpenAction_error_messageArgs;
	public static String OpenAction_error_messageProblems;
	public static String OpenAction_error_messageBadSelection;

	public static String ShowInExplorerViewAction_label;
	public static String ShowInExplorerViewAction_description;
	public static String ShowInExplorerViewAction_tooltip;
	public static String ShowInExplorerViewAction_dialog_title;
	public static String ShowInExplorerViewAction_error_message;
	public static String ShowInNavigatorView_label;
	public static String ShowInNavigatorView_dialog_title;
	public static String ShowInNavigatorView_dialog_message;
	public static String ShowInNavigatorView_error_activation_failed;

	public static String FormatAllAction_label;
	public static String FormatAllAction_tooltip;
	public static String FormatAllAction_description;
	public static String FormatAllAction_status_description;
	public static String FormatAllAction_multi_status_title;
	public static String FormatAllAction_error_title;
	public static String FormatAllAction_error_message;
	public static String FormatAllAction_operation_description;
	public static String FormatAllAction_failedvalidateedit_title;
	public static String FormatAllAction_failedvalidateedit_message;
	public static String FormatAllAction_noundo_title;
	public static String FormatAllAction_noundo_message;

	public static String NewWizardsActionGroup_new;
	public static String OpenProjectAction_dialog_title;
	public static String OpenProjectAction_dialog_message;
	public static String OpenProjectAction_error_message;
	public static String OpenPHPPerspectiveAction_dialog_title;
	public static String OpenPHPPerspectiveAction_error_open_failed;

	public static String RefreshAction_label;
	public static String RefreshAction_toolTip;
	public static String RefreshAction_progressMessage;
	public static String RefreshAction_error_title;
	public static String RefreshAction_error_message;
	public static String RefreshAction_locationDeleted_title;
	public static String RefreshAction_locationDeleted_message;

	public static String ActionUtil_not_possible;
	public static String SelectAllAction_label;
	public static String SelectAllAction_tooltip;
	public static String ToggleLinkingAction_label;
	public static String ToggleLinkingAction_tooltip;
	public static String ToggleLinkingAction_description;
	public static String ReorgMoveAction_3;
	public static String ReorgMoveAction_4;
	public static String MoveAction_text;
	public static String MoveAction_Move;
	public static String MoveAction_select;
	public static String RefactorActionGroup_no_refactoring_available;

	public static String RenameAction_rename;
	public static String RenameAction_unavailable;
	public static String RenameAction_text;

	public static String RenamePHPElementAction_exception;
	public static String RenamePHPElementAction_not_available;
	public static String RenamePHPElementAction_name;

	public static String OpenTypeAction_errorMessage;
	public static String OpenTypeAction_errorTitle;

	public static String CutAction_text;
	public static String copyAction_description;
	public static String copyAction_destination_label;
	public static String copyAction_name;
	public static String deleteAction_checkDeletion;
	public static String deleteAction_confirm_title;
	public static String deleteAction_confirmReadOnly;
	public static String deleteAction_description;

	public static String moveAction_checkMove;
	public static String moveAction_error_readOnly;
	public static String moveAction_label;
	public static String moveAction_name;
	public static String moveAction_destination_label;
	public static String CopyResourcesToClipboardAction_copy;
	public static String CopyToClipboardAction_4;
	public static String CopyToClipboardAction_5;
	public static String RefactoringAction_refactoring;
	public static String RefactoringAction_exception;
	public static String RefactoringAction_disabled;
	public static String CutSourceReferencesToClipboardAction_cut;
	public static String DeleteResourceAction_delete;
	public static String DeleteResourceAction_exception;
	public static String PasteSourceReferencesFromClipboardAction_paste1;
	public static String PasteSourceReferencesFromClipboardAction_exception;

	public static String CopyToClipboardProblemDialog_title;
	public static String CopyToClipboardProblemDialog_message;
	public static String PasteResourcesFromClipboardAction_error_title;
	public static String PasteResourcesFromClipboardAction_error_message;

	public static String PasteAction_text;
	public static String PasteAction_desc;
	public static String DeleteAction_text;
	public static String DeleteAction_desc;

	public static String CopyToClipboardAction_text;
	public static String CopyToClipboardAction_desc;

	public static String BuildAction_label;

	public static String RemoveBlockCommentAction_label;
	public static String AddBlockCommentAction_label;
	public static String ToggleCommentAction_label;
	public static String UncommentAction_label;
	public static String CommentAction_label;
	public static String OpenFunctionsManualAction_label;

	public static String ConfigureIncludePathAction_label;
	public static String ConfigureIncludePathAction_tooltip;
	public static String ConfigureIncludePathAction_description;

//  ----- UI ------

	public static String PHPTemplateStore_error_message_nameEmpty;
	public static String PHPTemplateStore_error_title;

//  ----- Working Set ------

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

//  ----- Filter ------

	public static String CustomFiltersDialog_title;
	public static String CustomFiltersDialog_patternInfo;
	public static String CustomFiltersDialog_enableUserDefinedPattern;
	public static String CustomFiltersDialog_filterList_label;
	public static String CustomFiltersDialog_description_label;
	public static String CustomFiltersDialog_SelectAllButton_label;
	public static String CustomFiltersDialog_DeselectAllButton_label;
	public static String OpenCustomFiltersDialogAction_text;
	public static String FilterDescriptor_filterDescriptionCreationError_message;
	public static String FilterDescriptor_filterCreationError_title;
	public static String FilterDescriptor_filterCreationError_message;

//  ----- Explorer ------

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

	// ------ Search -----

	public static String SearchLabelProvider_exact_singular;
	public static String SearchLabelProvider_exact_noCount;
	public static String SearchLabelProvider_exact_and_potential_plural;
	public static String SearchLabelProvider_potential_singular;
	public static String SearchLabelProvider_potential_noCount;
	public static String SearchLabelProvider_potential_plural;
	public static String SearchLabelProvider_exact_plural;
	public static String group_search;
	public static String group_declarations;
	public static String group_references;
	public static String group_readReferences;
	public static String group_writeReferences;
	public static String group_implementors;
	public static String group_occurrences;
	public static String group_occurrences_quickMenu_noEntriesAvailable;
	public static String Search_Error_phpElementAccess_title;
	public static String Search_Error_phpElementAccess_message;
	public static String Search_Error_search_title;
	public static String Search_Error_search_message;
	public static String Search_Error_search_notsuccessful_message;
	public static String Search_Error_search_notsuccessful_title;
	public static String Search_Error_openEditor_title;
	public static String Search_Error_openEditor_message;
	public static String Search_Error_codeResolve;
	public static String SearchElementSelectionDialog_title;
	public static String SearchElementSelectionDialog_message;
	public static String SearchPage_searchFor_label;
	public static String SearchPage_searchFor_class;
	public static String SearchPage_searchFor_function;
	public static String SearchPage_searchFor_constant;
	public static String SearchPage_searchFor_variable;
	public static String SearchPage_expression_label;
	public static String SearchPage_expression_caseSensitive;
	public static String SearchUtil_workingSetConcatenation;
	public static String Search_FindDeclarationAction_label;
	public static String Search_FindDeclarationAction_tooltip;
	public static String Search_FindDeclarationsInProjectAction_label;
	public static String Search_FindDeclarationsInProjectAction_tooltip;
	public static String Search_FindDeclarationsInWorkingSetAction_label;
	public static String Search_FindDeclarationsInWorkingSetAction_tooltip;
	public static String Search_FindHierarchyDeclarationsAction_label;
	public static String Search_FindHierarchyDeclarationsAction_tooltip;
	public static String Search_FindImplementorsAction_label;
	public static String Search_FindImplementorsAction_tooltip;
	public static String Search_FindImplementorsInProjectAction_label;
	public static String Search_FindImplementorsInProjectAction_tooltip;
	public static String Search_FindImplementorsInWorkingSetAction_label;
	public static String Search_FindImplementorsInWorkingSetAction_tooltip;
	public static String Search_FindReferencesAction_label;
	public static String Search_FindReferencesAction_tooltip;
	public static String Search_FindReferencesAction_BinPrimConstWarnDialog_title;
	public static String Search_FindReferencesAction_BinPrimConstWarnDialog_message;
	public static String Search_FindReferencesInProjectAction_label;
	public static String Search_FindReferencesInProjectAction_tooltip;
	public static String Search_FindReferencesInWorkingSetAction_label;
	public static String Search_FindReferencesInWorkingSetAction_tooltip;
	public static String Search_FindHierarchyReferencesAction_label;
	public static String Search_FindHierarchyReferencesAction_tooltip;
	public static String Search_FindReadReferencesAction_label;
	public static String Search_FindReadReferencesAction_tooltip;
	public static String Search_FindReadReferencesInProjectAction_label;
	public static String Search_FindReadReferencesInProjectAction_tooltip;
	public static String Search_FindReadReferencesInWorkingSetAction_label;
	public static String Search_FindReadReferencesInWorkingSetAction_tooltip;
	public static String Search_FindReadReferencesInHierarchyAction_label;
	public static String Search_FindReadReferencesInHierarchyAction_tooltip;
	public static String Search_FindWriteReferencesAction_label;
	public static String Search_FindWriteReferencesAction_tooltip;
	public static String Search_FindWriteReferencesInProjectAction_label;
	public static String Search_FindWriteReferencesInProjectAction_tooltip;
	public static String Search_FindWriteReferencesInWorkingSetAction_label;
	public static String Search_FindWriteReferencesInWorkingSetAction_tooltip;
	public static String Search_FindWriteReferencesInHierarchyAction_label;
	public static String Search_FindWriteReferencesInHierarchyAction_tooltip;
	public static String Search_FindOccurrencesInFile_shortLabel;
	public static String Search_FindOccurrencesInFile_label;
	public static String Search_FindOccurrencesInFile_tooltip;
	public static String FindOccurrencesEngine_noSource_text;
	public static String FindOccurrencesEngine_cannotParse_text;
	public static String OccurrencesFinder_no_element;
	public static String OccurrencesFinder_no_binding;
	public static String OccurrencesFinder_searchfor;
	public static String OccurrencesFinder_label_singular;
	public static String OccurrencesFinder_label_plural;
	public static String ExceptionOccurrencesFinder_no_exception;
	public static String ExceptionOccurrencesFinder_searchfor;
	public static String ExceptionOccurrencesFinder_label_singular;
	public static String ExceptionOccurrencesFinder_label_plural;
	public static String ImplementOccurrencesFinder_invalidTarget;
	public static String ImplementOccurrencesFinder_searchfor;
	public static String ImplementOccurrencesFinder_label_singular;
	public static String ImplementOccurrencesFinder_label_plural;
	public static String WorkspaceScope;
	public static String WorkingSetScope;
	public static String SelectionScope;
	public static String EnclosingProjectsScope;
	public static String EnclosingProjectScope;
	public static String ProjectScope;
	public static String HierarchyScope;
	public static String PHPElementAction_operationUnavailable_title;
	public static String PHPElementAction_operationUnavailable_generic;
	public static String PHPSearchOperation_singularOccurrencesPostfix;
	public static String PHPSearchOperation_pluralOccurrencesPostfix;
	public static String PHPElementAction_typeSelectionDialog_title;
	public static String PHPElementAction_typeSelectionDialog_message;
	public static String PHPElementAction_error_open_message;

	public static String PHPSearchResultPage_sortByName;
	public static String PHPSearchResultPage_sortByPath;
	public static String PHPSearchResultPage_open_editor_error_title;
	public static String PHPSearchResultPage_open_editor_error_message;
	public static String PHPSearchResultPage_sortByParentName;
	public static String PHPSearchResultPage_filtered_message;
	public static String PHPSearchResultPage_sortBylabel;
	public static String PHPSearchResultPage_error_marker;
	public static String PHPSearchResultPage_groupby_project;
	public static String PHPSearchResultPage_groupby_project_tooltip;
	public static String PHPSearchResultPage_filteredWithCount_message;
	public static String PHPSearchResultPage_groupby_file;
	public static String PHPSearchResultPage_groupby_file_tooltip;
	public static String PHPSearchResultPage_groupby_type;
	public static String PHPSearchResultPage_groupby_type_tooltip;
	public static String PHPSearchQuery_task_label;
	public static String PHPSearchQuery_label;
	public static String PHPSearchQuery_error_unsupported_pattern;
	public static String PHPSearchQuery_status_ok_message;
	public static String PHPSearchQuery_error_participant_estimate;
	public static String PHPSearchQuery_error_participant_search;
	public static String SearchParticipant_error_noID;
	public static String SearchParticipant_error_noNature;
	public static String SearchParticipant_error_noClass;
	public static String SearchParticipant_error_classCast;
	public static String MatchFilter_ImportFilter_name;
	public static String MatchFilter_ImportFilter_actionLabel;
	public static String MatchFilter_ImportFilter_description;
	public static String MatchFilter_WriteFilter_name;
	public static String MatchFilter_WriteFilter_actionLabel;
	public static String MatchFilter_WriteFilter_description;
	public static String MatchFilter_ReadFilter_name;
	public static String MatchFilter_ReadFilter_actionLabel;
	public static String MatchFilter_ReadFilter_description;
	public static String MatchFilter_ErasureFilter_name;
	public static String MatchFilter_ErasureFilter_actionLabel;
	public static String MatchFilter_ErasureFilter_description;
	public static String MatchFilter_InexactFilter_name;
	public static String MatchFilter_InexactFilter_actionLabel;
	public static String MatchFilter_InexactFilter_description;
	public static String MethodExitsFinder_no_return_type_selected;
	public static String TextSearchLabelProvider_matchCountFormat;
	public static String FiltersDialog_title;
	public static String FiltersDialog_filters_label;
	public static String FiltersDialog_description_label;
	public static String FiltersDialog_limit_label;
	public static String FiltersDialog_limit_error;
	public static String FiltersDialogAction_label;
	public static String PHPEngine_searching;

	// ----- Editor ---

	public static String PHP_Editor_FoldingMenu_name;
	public static String PHPStructuredEditor_Source;
	public static String ShowPHPDoc_label;
	public static String HoverFocus_message;
	public static String HoverFocus_decoration;

	public static String GotoMatchingBracket_label;
	public static String GotoMatchingBracket_error_invalidSelection;
	public static String GotoMatchingBracket_error_noMatchingBracket;
	public static String GotoMatchingBracket_error_bracketOutsideSelectedElement;

	//PHP Editor Preference page
	public static String PHPEditorPreferencePage_smartCaretPositioning;
	public static String PHPEditorPreferencePage_prefEditorMessage;
	public static String PHPEditorPreferencePage_prefEditorTooltip;

	public static String PHPAppearancePreferencePage_showMehodsReturnType;
	public static String PHPAppearancePreferencePage_appearanceHeader;

	public static String ConvertToPDTProjectAction_convert_to_PDT_project_title;
	public static String ConvertToPDTProjectAction_convert_to_PDT_project_tooltip;
	public static String ConvertToPDTProjectAction_convert_to_PDT_project_description;
	public static String ConvertToPDTProjectAction_converting_project_job_title;

	public static String RemoveFromIncludePathAction_remove_from_include_path_title;
	public static String RemoveFromIncludePathAction_remove_from_include_path_tooltip;
	public static String RemoveFromIncludePathAction_remove_from_include_path_desc;
	public static String RemoveFromIncludePathAction_remove_from_include_path_job;

	public static String UntitledPHPEditor_saveError;
	public static String UntitledPHPEditor_documentCannotBeSaved;
	
	public static String PerspectiveManager_Switch_Dialog_Title;
	public static String PerspectiveManager_Switch_Dialog_Message;
	public static String PerspectiveManager_PerspectiveError_Title;
	public static String PerspectiveManager_PerspectiveError_Message;

}
