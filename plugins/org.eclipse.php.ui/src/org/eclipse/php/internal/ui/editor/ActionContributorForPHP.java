/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
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
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.actions.*;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.actions.RetargetAction;
import org.eclipse.ui.editors.text.TextEditorActionContributor;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.texteditor.ITextEditorActionConstants;
import org.eclipse.ui.texteditor.ITextEditorActionDefinitionIds;
import org.eclipse.ui.texteditor.RetargetTextEditorAction;

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
public class ActionContributorForPHP extends TextEditorActionContributor {

	private static final String[] EDITOR_IDS = { "org.eclipse.php.core.phpsource" }; //$NON-NLS-1$

	private List<RetargetAction> fPartListeners = new ArrayList<>();
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

	public ActionContributorForPHP() {
		super();

		ResourceBundle resourceBundle = DLTKEditorMessages.getBundleForConstructedKeys();

		fShowPHPDoc = new RetargetTextEditorAction(resourceBundle, "Editor.ShowToolTip."); //$NON-NLS-1$
		fShowPHPDoc.setActionDefinitionId(ITextEditorActionDefinitionIds.SHOW_INFORMATION);

		fGotoMatchingBracket = new RetargetTextEditorAction(resourceBundle, "GotoMatchingBracket."); //$NON-NLS-1$
		fGotoMatchingBracket.setActionDefinitionId(IPHPEditorActionDefinitionIds.GOTO_MATCHING_BRACKET);

		fOpenDeclaration = new RetargetTextEditorAction(resourceBundle, "OpenAction_declaration_"); //$NON-NLS-1$
		fOpenDeclaration.setActionDefinitionId(IPHPEditorActionDefinitionIds.OPEN_DECLARATION);

		fOpenTypeHierarchy = new RetargetTextEditorAction(resourceBundle, "OpenTypeHierarchy"); //$NON-NLS-1$
		fOpenTypeHierarchy.setActionDefinitionId(IPHPEditorActionDefinitionIds.OPEN_TYPE_HIERARCHY);

		fOpenCallHierarchy = new RetargetTextEditorAction(resourceBundle, "OpenCallHierarchy"); //$NON-NLS-1$
		fOpenCallHierarchy.setActionDefinitionId(IPHPEditorActionDefinitionIds.OPEN_CALL_HIERARCHY);

		fOpenHierarchy = new RetargetTextEditorAction(DLTKEditorMessages.getBundleForConstructedKeys(),
				"OpenHierarchy."); //$NON-NLS-1$
		fOpenHierarchy.setActionDefinitionId(IScriptEditorActionDefinitionIds.OPEN_HIERARCHY);

		// source commands
		fToggleComment = new RetargetTextEditorAction(resourceBundle, ""); //$NON-NLS-1$
		fToggleComment.setActionDefinitionId(IPHPEditorActionDefinitionIds.TOGGLE_COMMENT);
		fAddBlockComment = new RetargetTextEditorAction(resourceBundle, ""); //$NON-NLS-1$
		fAddBlockComment.setActionDefinitionId(IPHPEditorActionDefinitionIds.ADD_BLOCK_COMMENT);
		fRemoveBlockComment = new RetargetTextEditorAction(resourceBundle, ""); //$NON-NLS-1$
		fRemoveBlockComment.setActionDefinitionId(IPHPEditorActionDefinitionIds.REMOVE_BLOCK_COMMENT);

		fMarkOccurrencesAction = new ToggleMarkOccurrencesAction(resourceBundle);
	}

	protected void addToMenu(IMenuManager menu) {
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
	 * @see org.eclipse.ui.part.EditorActionBarContributor#contributeToMenu(org.
	 * eclipse .jface.action.IMenuManager)
	 */
	@Override
	public void contributeToMenu(IMenuManager menu) {
		super.contributeToMenu(menu);
		IMenuManager gotoMenu = menu.findMenuUsingPath("navigate/goTo"); //$NON-NLS-1$
		menu.findMenuUsingPath("source"); //$NON-NLS-1$
		if (gotoMenu != null) {
			gotoMenu.add(new Separator("additions2")); //$NON-NLS-1$
			gotoMenu.appendToGroup("additions2", fGotoMatchingBracket); //$NON-NLS-1$
		}
		IMenuManager navigateMenu = menu.findMenuUsingPath(IWorkbenchActionConstants.M_NAVIGATE);
		if (navigateMenu != null) {
			navigateMenu.appendToGroup(IWorkbenchActionConstants.OPEN_EXT, fOpenDeclaration);
			navigateMenu.appendToGroup(IWorkbenchActionConstants.SHOW_EXT, fOpenHierarchy);

		}

		IMenuManager editMenu = menu.findMenuUsingPath(IWorkbenchActionConstants.M_EDIT);
		if (editMenu != null) {
			editMenu.appendToGroup(ITextEditorActionConstants.GROUP_INFORMATION, fShowPHPDoc);
		}
	}

	@Override
	public void init(IActionBars bars, IWorkbenchPage page) {
		super.init(bars, page);

		bars.setGlobalActionHandler(IPHPEditorActionDefinitionIds.TOGGLE_MARK_OCCURRENCES, fMarkOccurrencesAction);
	}

	/*
	 * @see EditorActionBarContributor#setActiveEditor(IEditorPart)
	 */
	@Override
	public void setActiveEditor(IEditorPart part) {
		super.setActiveEditor(part);

		ITextEditor editor = null;
		if (part instanceof ITextEditor) {
			editor = (ITextEditor) part;
		}

		if (editor != null) {
			OrganizeUseStatementsAction organizeUseStatementsAction = new OrganizeUseStatementsAction(part);
			organizeUseStatementsAction.setActionDefinitionId(IPHPEditorActionDefinitionIds.ORGANIZE_USE_STATEMENT);
			organizeUseStatementsAction.setEnabled(editor.isEditable());
			editor.setAction("OrganizeUseStatements", organizeUseStatementsAction); //$NON-NLS-1$
		}

		fMarkOccurrencesAction.setEditor(editor);

		fShowPHPDoc.setAction(getAction(editor, "ShowPHPDoc")); //$NON-NLS-1$
		fGotoMatchingBracket.setAction(getAction(editor, GotoMatchingBracketAction.GOTO_MATCHING_BRACKET));
		fOpenDeclaration.setAction(getAction(editor, IPHPEditorActionDefinitionIds.OPEN_DECLARATION));
		fOpenTypeHierarchy.setAction(getAction(editor, IPHPEditorActionDefinitionIds.OPEN_TYPE_HIERARCHY));
		getActionBars().setGlobalActionHandler(PHPActionConstants.OPEN_TYPE_HIERARCHY,
				getAction(editor, IPHPEditorActionDefinitionIds.OPEN_TYPE_HIERARCHY));
		fOpenHierarchy.setAction(getAction(editor, IScriptEditorActionDefinitionIds.OPEN_HIERARCHY));
		fOpenCallHierarchy.setAction(getAction(editor, IPHPEditorActionDefinitionIds.OPEN_CALL_HIERARCHY));
		getActionBars().setGlobalActionHandler(PHPActionConstants.OPEN_CALL_HIERARCHY,
				getAction(editor, IPHPEditorActionDefinitionIds.OPEN_CALL_HIERARCHY));

		/*
		 * fToggleComment.setAction(getAction(editor,
		 * StructuredTextEditorActionConstants.ACTION_NAME_TOGGLE_COMMENT));
		 * fAddBlockComment .setAction(getAction(editor,
		 * StructuredTextEditorActionConstants.ACTION_NAME_ADD_BLOCK_COMMENT));
		 * fRemoveBlockComment .setAction(getAction(editor,
		 * StructuredTextEditorActionConstants.ACTION_NAME_REMOVE_BLOCK_COMMENT)
		 * );
		 */
		fToggleComment.setEnabled(editor != null && editor.isEditable());
		fAddBlockComment.setEnabled(editor != null && editor.isEditable());
		fRemoveBlockComment.setEnabled(editor != null && editor.isEditable());
		fShowPHPDoc.setAction(getAction(editor, ITextEditorActionConstants.SHOW_INFORMATION));

		if (part instanceof PHPStructuredEditor) {
			PHPStructuredEditor phpEditor = (PHPStructuredEditor) part;
			phpEditor.getActionGroup().fillActionBars(getActionBars());
		}
	}

	/*
	 * @see IEditorActionBarContributor#dispose()
	 */
	@Override
	public void dispose() {
		Iterator<RetargetAction> e = fPartListeners.iterator();
		while (e.hasNext()) {
			getPage().removePartListener(e.next());
		}
		fPartListeners.clear();

		setActiveEditor(null);
		super.dispose();
	}

}
