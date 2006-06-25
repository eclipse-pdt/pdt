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
package org.eclipse.php.internal.ui.actions;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.eclipse.osgi.util.NLS;

public final class ActionMessages extends NLS {
	private static final String BUNDLE_FOR_CONSTRUCTED_KEYS= "org.eclipse.ui.texteditor.ConstructedTextEditorMessages"; //$NON-NLS-1$
	private static ResourceBundle fgBundleForConstructedKeys= ResourceBundle.getBundle(BUNDLE_FOR_CONSTRUCTED_KEYS);
	private static final String BUNDLE_NAME = "org.eclipse.php.internal.ui.actions.ActionMessages";//$NON-NLS-1$
	private static ResourceBundle fResourceBundle;

	private ActionMessages() {
		// Do not instantiate
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

	

	static {
		NLS.initializeMessages(BUNDLE_NAME, ActionMessages.class);
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
}