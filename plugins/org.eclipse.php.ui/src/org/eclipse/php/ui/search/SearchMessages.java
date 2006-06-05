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
package org.eclipse.php.ui.search;

import org.eclipse.osgi.util.NLS;

public class SearchMessages extends NLS {

	private static final String BUNDLE_NAME= "org.eclipse.php.ui.search.SearchMessages";//$NON-NLS-1$

	private SearchMessages() {
		// Do not instantiate
	}

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

	static {
		NLS.initializeMessages(BUNDLE_NAME, SearchMessages.class);
	}
}
