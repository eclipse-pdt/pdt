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
package org.eclipse.php.internal.ui.editor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import org.eclipse.dltk.internal.ui.editor.DLTKEditorMessages;
import org.eclipse.dltk.ui.actions.IScriptEditorActionDefinitionIds;
import org.eclipse.jface.action.*;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.actions.GotoMatchingBracketAction;
import org.eclipse.php.internal.ui.actions.IPHPEditorActionDefinitionIds;
import org.eclipse.php.internal.ui.actions.PHPActionConstants;
import org.eclipse.php.internal.ui.actions.ToggleMarkOccurrencesAction;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.actions.RetargetAction;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.texteditor.RetargetTextEditorAction;
import org.eclipse.wst.html.ui.internal.edit.ui.ActionContributorHTML;
import org.eclipse.wst.sse.ui.internal.actions.StructuredTextEditorActionConstants;

/**
 * A PHPEditorActionBarContributor, which is a simple extension for
 * BasicTextEditorActionContributor. This class should not be used inside multi
 * page editor's ActionBarContributor, since cascaded init() call from the
 * ActionBarContributor will causes exception and it leads to lose whole
 * toolbars.
 * 
 * Instead, use SourcePageActionContributor for source page contributor of multi
 * page editor.
 * 
 * Note that this class is still valid for single page editor.
 */
public class ActionContributorForPhp extends ActionContributorHTML {

	private static final String[] EDITOR_IDS = {
			"org.eclipse.php.core.phpsource", "org.eclipse.wst.sse.ui.StructuredTextEditor" }; //$NON-NLS-1$ //$NON-NLS-2$

	private RetargetAction fRetargetShowPHPDoc;
	private List<RetargetAction> fPartListeners = new ArrayList<RetargetAction>();
	private RetargetTextEditorAction fShowPHPDoc;

	private RetargetTextEditorAction fGotoMatchingBracket;
	private RetargetTextEditorAction fOpenDeclaration;
	private RetargetTextEditorAction fOpenTypeHierarchy;
	private RetargetTextEditorAction fOpenCallHierarchy;
	private RetargetTextEditorAction fOpenHierarchy;

	private RetargetTextEditorAction fToggleComment = null;
	private RetargetTextEditorAction fAddBlockComment = null;
	private RetargetTextEditorAction fRemoveBlockComment = null;

	private ToggleMarkOccurrencesAction fMarkOccurrencesAction = null;

	protected MenuManager fFormatMenu = null;

	private MenuManager refactorMenu;

	protected RetargetTextEditorAction fAddDescription;

	public ActionContributorForPhp() {
		super();

		ResourceBundle resourceBundle = DLTKEditorMessages
				.getBundleForConstructedKeys();

		fRetargetShowPHPDoc = new RetargetAction(
				PHPActionConstants.SHOW_PHP_DOC, PHPUIMessages.ShowPHPDoc_label);
		fRetargetShowPHPDoc
				.setActionDefinitionId(IPHPEditorActionDefinitionIds.SHOW_PHPDOC);
		fPartListeners.add(fRetargetShowPHPDoc);

		fShowPHPDoc = new RetargetTextEditorAction(resourceBundle,
				"ShowPHPDoc."); //$NON-NLS-1$
		fShowPHPDoc
				.setActionDefinitionId(IPHPEditorActionDefinitionIds.SHOW_PHPDOC);

		//fAddDescription = new RetargetTextEditorAction(resourceBundle, PHPActionConstants.ADD_DESCRIPTION_NAME + "_"); //$NON-NLS-1$
		// fAddDescription.setActionDefinitionId(IPHPEditorActionDefinitionIds.ADD_DESCRIPTION);

		fGotoMatchingBracket = new RetargetTextEditorAction(resourceBundle,
				"GotoMatchingBracket."); //$NON-NLS-1$
		fGotoMatchingBracket
				.setActionDefinitionId(IPHPEditorActionDefinitionIds.GOTO_MATCHING_BRACKET);

		fOpenDeclaration = new RetargetTextEditorAction(resourceBundle,
				"OpenAction_declaration_"); //$NON-NLS-1$
		fOpenDeclaration
				.setActionDefinitionId(IPHPEditorActionDefinitionIds.OPEN_DECLARATION);

		fOpenTypeHierarchy = new RetargetTextEditorAction(resourceBundle,
				"OpenTypeHierarchy"); //$NON-NLS-1$
		fOpenTypeHierarchy
				.setActionDefinitionId(IPHPEditorActionDefinitionIds.OPEN_TYPE_HIERARCHY);

		fOpenCallHierarchy = new RetargetTextEditorAction(resourceBundle,
				"OpenCallHierarchy"); //$NON-NLS-1$
		fOpenCallHierarchy
				.setActionDefinitionId(IPHPEditorActionDefinitionIds.OPEN_CALL_HIERARCHY);

		fOpenHierarchy = new RetargetTextEditorAction(DLTKEditorMessages
				.getBundleForConstructedKeys(), "OpenHierarchy."); //$NON-NLS-1$
		fOpenHierarchy
				.setActionDefinitionId(IScriptEditorActionDefinitionIds.OPEN_HIERARCHY);

		// source commands
		fToggleComment = new RetargetTextEditorAction(resourceBundle, ""); //$NON-NLS-1$
		fToggleComment
				.setActionDefinitionId(IPHPEditorActionDefinitionIds.TOGGLE_COMMENT);
		fAddBlockComment = new RetargetTextEditorAction(resourceBundle, ""); //$NON-NLS-1$
		fAddBlockComment
				.setActionDefinitionId(IPHPEditorActionDefinitionIds.ADD_BLOCK_COMMENT);
		fRemoveBlockComment = new RetargetTextEditorAction(resourceBundle, ""); //$NON-NLS-1$
		fRemoveBlockComment
				.setActionDefinitionId(IPHPEditorActionDefinitionIds.REMOVE_BLOCK_COMMENT);

		fMarkOccurrencesAction = new ToggleMarkOccurrencesAction(resourceBundle);
	}

	protected void addToMenu(IMenuManager menu) {
		super.addToMenu(menu);
		// source commands
		String sourceMenuLabel = PHPUIMessages.SourceMenu_label;
		String sourceMenuId = "sourceMenuId"; //$NON-NLS-1$
		IMenuManager sourceMenu = new MenuManager(sourceMenuLabel, sourceMenuId);
		menu.insertAfter(IWorkbenchActionConstants.M_EDIT, sourceMenu);
		if (sourceMenu != null) {
			sourceMenu.add(fToggleComment);
			sourceMenu.add(fAddBlockComment);
			sourceMenu.add(fRemoveBlockComment);
		}
	}

	/*
	 * @see
	 * org.eclipse.ui.part.EditorActionBarContributor#contributeToMenu(org.eclipse
	 * .jface.action.IMenuManager)
	 */
	public void contributeToMenu(IMenuManager menu) {
		super.contributeToMenu(menu);
		IMenuManager gotoMenu = menu.findMenuUsingPath("navigate/goTo"); //$NON-NLS-1$
		menu.findMenuUsingPath("source"); //$NON-NLS-1$
		if (gotoMenu != null) {
			gotoMenu.add(new Separator("additions2")); //$NON-NLS-1$
			gotoMenu.appendToGroup("additions2", fGotoMatchingBracket); //$NON-NLS-1$
		}
		IMenuManager navigateMenu = menu
				.findMenuUsingPath(IWorkbenchActionConstants.M_NAVIGATE);
		if (navigateMenu != null) {
			navigateMenu.appendToGroup(IWorkbenchActionConstants.OPEN_EXT,
					fOpenDeclaration);
			navigateMenu.appendToGroup(IWorkbenchActionConstants.SHOW_EXT,
					fOpenHierarchy);

			// Work around for Bug 251074
			// The SSE's action contributor append the fOpentFileAction with the
			// same name "Open Declaration".
			// Do we really want to extends from SSE's action contributor?
			IContributionItem item = navigateMenu
					.remove(new ActionContributionItem(fOpenFileAction));
		}
	}

	@Override
	public void init(IActionBars bars, IWorkbenchPage page) {
		super.init(bars, page);

		bars.setGlobalActionHandler(
				IPHPEditorActionDefinitionIds.TOGGLE_MARK_OCCURRENCES,
				fMarkOccurrencesAction);
	}

	/*
	 * @see EditorActionBarContributor#setActiveEditor(IEditorPart)
	 */
	public void setActiveEditor(IEditorPart part) {
		super.setActiveEditor(part);

		ITextEditor editor = null;
		if (part instanceof ITextEditor)
			editor = (ITextEditor) part;

		fMarkOccurrencesAction.setEditor(editor);

		fShowPHPDoc.setAction(getAction(editor, "ShowPHPDoc")); //$NON-NLS-1$
		fGotoMatchingBracket.setAction(getAction(editor,
				GotoMatchingBracketAction.GOTO_MATCHING_BRACKET));
		fOpenDeclaration.setAction(getAction(editor,
				IPHPEditorActionDefinitionIds.OPEN_DECLARATION));
		fOpenTypeHierarchy.setAction(getAction(editor,
				IPHPEditorActionDefinitionIds.OPEN_TYPE_HIERARCHY));
		getActionBars().setGlobalActionHandler(
				PHPActionConstants.OPEN_TYPE_HIERARCHY,
				getAction(editor,
						IPHPEditorActionDefinitionIds.OPEN_TYPE_HIERARCHY));
		fOpenHierarchy.setAction(getAction(editor,
				IScriptEditorActionDefinitionIds.OPEN_HIERARCHY));
		fOpenCallHierarchy.setAction(getAction(editor,
				IPHPEditorActionDefinitionIds.OPEN_CALL_HIERARCHY));
		getActionBars().setGlobalActionHandler(
				PHPActionConstants.OPEN_CALL_HIERARCHY,
				getAction(editor,
						IPHPEditorActionDefinitionIds.OPEN_CALL_HIERARCHY));

		fToggleComment
				.setAction(getAction(
						editor,
						StructuredTextEditorActionConstants.ACTION_NAME_TOGGLE_COMMENT));
		fAddBlockComment
				.setAction(getAction(
						editor,
						StructuredTextEditorActionConstants.ACTION_NAME_ADD_BLOCK_COMMENT));
		fRemoveBlockComment
				.setAction(getAction(
						editor,
						StructuredTextEditorActionConstants.ACTION_NAME_REMOVE_BLOCK_COMMENT));
		fToggleComment.setEnabled(editor != null && editor.isEditable());
		fAddBlockComment.setEnabled(editor != null && editor.isEditable());
		fRemoveBlockComment.setEnabled(editor != null && editor.isEditable());

		if (part instanceof PHPStructuredEditor) {
			PHPStructuredEditor phpEditor = (PHPStructuredEditor) part;
			phpEditor.getActionGroup().fillActionBars(getActionBars());
		}
	}

	/*
	 * @see IEditorActionBarContributor#dispose()
	 */
	public void dispose() {
		Iterator<RetargetAction> e = fPartListeners.iterator();
		while (e.hasNext())
			getPage().removePartListener(e.next());
		fPartListeners.clear();

		if (fRetargetShowPHPDoc != null) {
			fRetargetShowPHPDoc.dispose();
			fRetargetShowPHPDoc = null;
		}

		setActiveEditor(null);
		super.dispose();
	}

	protected String[] getExtensionIDs() {
		return EDITOR_IDS;
	}
}
