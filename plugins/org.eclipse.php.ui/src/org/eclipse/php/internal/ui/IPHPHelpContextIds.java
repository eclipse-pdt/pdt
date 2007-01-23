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


public interface IPHPHelpContextIds {
	public static final String PREFIX = PHPUiPlugin.ID + '.';

	// Actions

	public static final String OPEN_CLASS_WIZARD_ACTION = PREFIX + "open_class_wizard_action"; //$NON-NLS-1$

	public static final String OPEN_PROJECT_WIZARD_ACTION = PREFIX + "open_project_wizard_action"; //$NON-NLS-1$

	public static final String FORMAT_ALL = PREFIX + "format_all_action"; //$NON-NLS-1$

	public static final String OPEN_PROJECT_ACTION = PREFIX + "open_project_action"; //$NON-NLS-1$

	public static final String OPEN_TYPE_ACTION = PREFIX + "open_type_action"; //$NON-NLS-1$

	public static final String MOVE_ACTION = PREFIX + "move_action"; //$NON-NLS-1$
	public static final String OPEN_ACTION = PREFIX + "open_action"; //$NON-NLS-1$
	public static final String REFRESH_ACTION = PREFIX + "refresh_action"; //$NON-NLS-1$
	public static final String RENAME_ACTION = PREFIX + "rename_action"; //$NON-NLS-1$
	public static final String SHOW_IN_NAVIGATOR_VIEW_ACTION = PREFIX + "show_in_navigator_action"; //$NON-NLS-1$
	public static final String CUT_ACTION = PREFIX + "cut_action"; //$NON-NLS-1$	
	public static final String COPY_ACTION = PREFIX + "copy_action"; //$NON-NLS-1$	
	public static final String PASTE_ACTION = PREFIX + "paste_action"; //$NON-NLS-1$	
	public static final String DELETE_ACTION = PREFIX + "delete_action"; //$NON-NLS-1$	
	public static final String SELECT_ALL_ACTION = PREFIX + "select_all_action"; //$NON-NLS-1$
	public static final String COLLAPSE_ALL_ACTION = PREFIX + "open_type_hierarchy_action"; //$NON-NLS-1$
	public static final String GOTO_RESOURCE_ACTION = PREFIX + "goto_resource_action"; //$NON-NLS-1$
	public static final String LINK_EDITOR_ACTION = PREFIX + "link_editor_action"; //$NON-NLS-1$
	public static final String GO_INTO_TOP_LEVEL_TYPE_ACTION = PREFIX + "go_into_top_level_type_action"; //$NON-NLS-1$
	public static final String COMPARE_WITH_HISTORY_ACTION = PREFIX + "compare_with_history_action"; //$NON-NLS-1$
	public static final String REPLACE_WITH_PREVIOUS_FROM_HISTORY_ACTION = PREFIX + "replace_with_previous_from_history_action"; //$NON-NLS-1$
	public static final String REPLACE_WITH_HISTORY_ACTION = PREFIX + "replace_with_history_action"; //$NON-NLS-1$
	public static final String ADD_FROM_HISTORY_ACTION = PREFIX + "add_from_history_action"; //$NON-NLS-1$
	public static final String EDIT_WORKING_SET_ACTION = PREFIX + "edit_working_set_action"; //$NON-NLS-1$
	public static final String CLEAR_WORKING_SET_ACTION = PREFIX + "clear_working_set_action"; //$NON-NLS-1$
	public static final String PHP_TYPING_PREFERENCE_PAGE = PREFIX + "clear_working_set_action"; //$NON-NLS-1$

	public static final String GOTO_NEXT_ERROR_ACTION = PREFIX + "goto_next_error_action"; //$NON-NLS-1$	
	public static final String GOTO_PREVIOUS_ERROR_ACTION = PREFIX + "goto_previous_error_action"; //$NON-NLS-1$	
	public static final String SHOW_QUALIFIED_NAMES_ACTION = PREFIX + "show_qualified_names_action"; //$NON-NLS-1$	
	public static final String FORMAT_ACTION = PREFIX + "format_action"; //$NON-NLS-1$	
	public static final String COMMENT_ACTION = PREFIX + "comment_action"; //$NON-NLS-1$	
	public static final String UNCOMMENT_ACTION = PREFIX + "uncomment_action"; //$NON-NLS-1$	

	public static final String TOGGLE_COMMENT_ACTION = PREFIX + "toggle_comment_action"; //$NON-NLS-1$
	public static final String ADD_BLOCK_COMMENT_ACTION = PREFIX + "add_block_comment_action"; //$NON-NLS-1$
	public static final String REMOVE_BLOCK_COMMENT_ACTION = PREFIX + "remove_block_comment_action"; //$NON-NLS-1$
	public static final String CONTENT_ASSIST_ACTION = PREFIX + "content_assist_action"; //$NON-NLS-1$	
	public static final String PARAMETER_HINTS_ACTION = PREFIX + "parameter_hints_action"; //$NON-NLS-1$	
	public static final String SHOW_OUTLINE_ACTION = PREFIX + "show_outline_action"; //$NON-NLS-1$	
	public static final String SELECT_WORKING_SET_ACTION = PREFIX + "select_working_set_action"; //$NON-NLS-1$	
	
	
	public static final String SORTING_OUTLINE_ACTION = PREFIX + "sorting_outline_action"; //$NON-NLS-1$	

	// view parts
	public static final String EXPLORER_VIEW = PREFIX + "phpexplorer_view_context"; //$NON-NLS-1$

	public static final String INCLUDE_PATH_PROPERTY_PAGE = PREFIX + "include_path_property_page_context"; //$NON-NLS-1$

	public static final String INCLUDE_PATH_BLOCK = PREFIX + "include_paths_context"; //$NON-NLS-1$

	public static final String IP_VARIABLES_PREFERENCE_PAGE= 		PREFIX + "ip_variables_preference_page_context"; //$NON-NLS-1$
	
	// Wizard pages
	public static final String NEW_PHPPROJECT_WIZARD_PAGE = PREFIX + "new_phpproject_wizard_page_context"; //$NON-NLS-1$
	public static final String NEW_PHPFILE_WIZARD_PAGE = PREFIX + "new_phpfile_wizard_page_context"; //$NON-NLS-1$
	public static final String NEW_CLASS_WIZARD_PAGE = PREFIX + "new_class_wizard_page_context"; //$NON-NLS-1$

	public static final String CUSTOM_FILTERS_DIALOG = PREFIX + "open_custom_filters_dialog_context"; //$NON-NLS-1$
	public static final String NEW_CONTAINER_DIALOG = PREFIX + "new_container_dialog_context"; //$NON-NLS-1$	
	public static final String NEW_VARIABLE_ENTRY_DIALOG = PREFIX + "new_variable_dialog_context"; //$NON-NLS-1$
	public static final String CHOOSE_VARIABLE_DIALOG = PREFIX + "choose_variable_dialog_context"; //$NON-NLS-1$	
	public static final String SOURCE_ATTACHMENT_BLOCK = PREFIX + "source_attachment_context"; //$NON-NLS-1$
	public static final String VARIABLE_CREATION_DIALOG = PREFIX + "variable_creation_dialog_context"; //$NON-NLS-1$	
	public static final String INCLUDEPATH_CONTAINER_DEFAULT_PAGE = PREFIX + "includepath_container_default_page_context"; //$NON-NLS-1$

	public static final String PHP_EDITOR_PREFERENCE_PAGE= PREFIX + "php_editor_preference_page_context"; //$NON-NLS-1$
	
	public static final String PHP_WORKING_SET_PAGE= PREFIX + "php_working_set_page_context"; //$NON-NLS-1$
	public static final String PHP_MANUAL_PREFERENCE_PAGE = PREFIX + "php_manual_preference_page_context"; //$NON-NLS-1$

}
