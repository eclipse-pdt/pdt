/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
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
import org.eclipse.php.internal.ui.actions.GotoMatchingBracketAction;
import org.eclipse.php.internal.ui.actions.IPHPEditorActionDefinitionIds;
import org.eclipse.php.internal.ui.actions.PHPActionConstants;
import org.eclipse.php.internal.ui.actions.ToggleMarkOccurrencesAction;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.actions.RetargetAction;
import org.eclipse.ui.editors.text.TextEditorActionContributor;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.texteditor.RetargetTextEditorAction;

/**
 * A PHPEditorActionBarContributor, which is a simple extension for
 * BasicTextEditorActionContributor.
 */
public class PHPEditorActionBarContributor extends TextEditorActionContributor {

	private RetargetAction fRetargetShowPHPDoc;
	private List fPartListeners = new ArrayList();
	private RetargetTextEditorAction fShowPHPDoc;

	protected RetargetTextEditorAction fFormatActiveElements = null;
	protected RetargetTextEditorAction fFormatDocument = null;
	private RetargetTextEditorAction fGotoMatchingBracket;
	private RetargetTextEditorAction fOpenDeclaration;
	private RetargetTextEditorAction fOpenTypeHierarchy;
	private RetargetTextEditorAction fOpenCallHierarchy;
	private RetargetTextEditorAction fOpenHierarchy;
	private RetargetTextEditorAction fRename;
	private RetargetTextEditorAction fMove;
	private ToggleMarkOccurrencesAction fMarkOccurrences; // Registers as a global action

	protected MenuManager fFormatMenu = null;

	public final static String FORMAT_ACTIVE_ELEMENTS = "org.eclipse.wst.sse.ui.format.active.elements";//$NON-NLS-1$
	public final static String FORMAT_DOCUMENT = "org.eclipse.wst.sse.ui.format.document";//$NON-NLS-1$

	/** The global actions to be connected with PHP editor actions */
	private final static String[] PHPEDITOR_ACTIONS = { "org.eclipse.php.ui.actions.RemoveBlockComment", //$NON-NLS-1$
		"org.eclipse.php.ui.actions.ToggleCommentAction", //$NON-NLS-1$
		"org.eclipse.php.ui.actions.AddBlockComment", "FormatDocument", //$NON-NLS-1$ //$NON-NLS-2$
		IPHPEditorActionDefinitionIds.OPEN_DECLARATION, "org.eclipse.php.ui.actions.AddDescriptionAction" ,IPHPEditorActionDefinitionIds.OPEN_TYPE_HIERARCHY, IPHPEditorActionDefinitionIds.OPEN_CALL_HIERARCHY, "FormatActiveElements", //$NON-NLS-1$
		IPHPEditorActionDefinitionIds.RENAME_ELEMENT, IPHPEditorActionDefinitionIds.MOVE_ELEMENT

	}; //$NON-NLS-1$

	// private ToggleCommentAction fToggleCommentAction;

	public PHPEditorActionBarContributor() {
		super();

		ResourceBundle b = PHPUIMessages.getBundleForConstructedKeys();

		fRetargetShowPHPDoc = new RetargetAction(PHPActionConstants.SHOW_PHP_DOC, PHPUIMessages.getString("ShowPHPDoc_label"));
		fRetargetShowPHPDoc.setActionDefinitionId(IPHPEditorActionDefinitionIds.SHOW_PHPDOC);
		markAsPartListener(fRetargetShowPHPDoc);

		fShowPHPDoc = new RetargetTextEditorAction(b, "ShowPHPDoc."); //$NON-NLS-1$
		fShowPHPDoc.setActionDefinitionId(IPHPEditorActionDefinitionIds.SHOW_PHPDOC);

		fFormatDocument = new RetargetTextEditorAction(b, ""); //$NON-NLS-1$
		fFormatDocument.setActionDefinitionId(FORMAT_DOCUMENT);

		fFormatActiveElements = new RetargetTextEditorAction(b, ""); //$NON-NLS-1$
		fFormatActiveElements.setActionDefinitionId(FORMAT_ACTIVE_ELEMENTS);

		fGotoMatchingBracket = new RetargetTextEditorAction(b, "GotoMatchingBracket."); //$NON-NLS-1$
		fGotoMatchingBracket.setActionDefinitionId(IPHPEditorActionDefinitionIds.GOTO_MATCHING_BRACKET);

		fOpenDeclaration = new RetargetTextEditorAction(b, "OpenAction_declaration_"); //$NON-NLS-1$
		fOpenDeclaration.setActionDefinitionId(IPHPEditorActionDefinitionIds.OPEN_DECLARATION);

		fOpenTypeHierarchy = new RetargetTextEditorAction(b, "OpenTypeHierarchy"); //$NON-NLS-1$
		fOpenTypeHierarchy.setActionDefinitionId(IPHPEditorActionDefinitionIds.OPEN_TYPE_HIERARCHY);
		
		fOpenCallHierarchy = new RetargetTextEditorAction(b, "OpenCallHierarchy"); //$NON-NLS-1$
		fOpenCallHierarchy.setActionDefinitionId(IPHPEditorActionDefinitionIds.OPEN_CALL_HIERARCHY);

		fOpenHierarchy = new RetargetTextEditorAction(DLTKEditorMessages.getBundleForConstructedKeys(), "OpenHierarchy."); //$NON-NLS-1$
		fOpenHierarchy.setActionDefinitionId(IScriptEditorActionDefinitionIds.OPEN_HIERARCHY);

		fRename = new RetargetTextEditorAction(b, ""); //$NON-NLS-1$
		fRename.setActionDefinitionId(IPHPEditorActionDefinitionIds.RENAME_ELEMENT);

		fMove = new RetargetTextEditorAction(b, ""); //$NON-NLS-1$
		fMove.setActionDefinitionId(IPHPEditorActionDefinitionIds.MOVE_ELEMENT);

		fMarkOccurrences = new ToggleMarkOccurrencesAction(b);
		fMarkOccurrences.setActionDefinitionId(IPHPEditorActionDefinitionIds.TOGGLE_MARK_OCCURRENCES);

		//		fFormatMenu = new MenuManager("Format");
		//		fFormatMenu.add(fFormatDocument);
		//		fFormatMenu.add(fFormatActiveElements);

	}

	/*
	 * @see org.eclipse.ui.part.EditorActionBarContributor#contributeToMenu(org.eclipse.jface.action.IMenuManager)
	 */
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
			navigateMenu.appendToGroup(IWorkbenchActionConstants.SHOW_EXT, fOpenHierarchy);
		}
	}

	protected final void markAsPartListener(RetargetAction action) {
		fPartListeners.add(action);
	}

	/*
	 * @see IEditorActionBarContributor#init(IActionBars, IWorkbenchPage)
	 */
	public void init(IActionBars bars, IWorkbenchPage page) {
		Iterator e = fPartListeners.iterator();
		while (e.hasNext())
			page.addPartListener((RetargetAction) e.next());

		super.init(bars, page);

		bars.setGlobalActionHandler(PHPActionConstants.SHOW_PHP_DOC, fShowPHPDoc);
		bars.setGlobalActionHandler(PHPActionConstants.TOGGLE_MARK_OCCURRENCES, fMarkOccurrences);
	}

	/*
	 * @see EditorActionBarContributor#setActiveEditor(IEditorPart)
	 */
	public void setActiveEditor(IEditorPart part) {
		super.setActiveEditor(part);

		ITextEditor editor = null;
		if (part instanceof ITextEditor)
			editor = (ITextEditor) part;

		fShowPHPDoc.setAction(getAction(editor, "ShowPHPDoc")); //$NON-NLS-1$
		fGotoMatchingBracket.setAction(getAction(editor, GotoMatchingBracketAction.GOTO_MATCHING_BRACKET));
		fFormatDocument.setAction(getAction(editor, "FormatDocument")); //$NON-NLS-1$
		fFormatActiveElements.setAction(getAction(editor, "FormatActiveElements")); //$NON-NLS-1$
		fOpenDeclaration.setAction(getAction(editor, IPHPEditorActionDefinitionIds.OPEN_DECLARATION));
		fOpenTypeHierarchy.setAction(getAction(editor, IPHPEditorActionDefinitionIds.OPEN_TYPE_HIERARCHY));
		fOpenHierarchy.setAction(getAction(editor, IScriptEditorActionDefinitionIds.OPEN_HIERARCHY));
		fOpenTypeHierarchy.setAction(getAction(editor, IPHPEditorActionDefinitionIds.OPEN_CALL_HIERARCHY));
		fMarkOccurrences.setEditor(editor);
		if (part instanceof PHPStructuredEditor) {
			PHPStructuredEditor phpEditor = (PHPStructuredEditor) part;
			phpEditor.getActionGroup().fillActionBars(getActionBars());
		}

		IActionBars actionBars = getActionBars();
		if (actionBars == null)
			return;

		for (int i = 0; i < PHPEDITOR_ACTIONS.length; i++)
			actionBars.setGlobalActionHandler(PHPEDITOR_ACTIONS[i], getAction(editor, PHPEDITOR_ACTIONS[i]));
	}

	/*
	 * @see IEditorActionBarContributor#dispose()
	 */
	public void dispose() {

		Iterator e = fPartListeners.iterator();
		while (e.hasNext())
			getPage().removePartListener((RetargetAction) e.next());
		fPartListeners.clear();

		if (fRetargetShowPHPDoc != null) {
			fRetargetShowPHPDoc.dispose();
			fRetargetShowPHPDoc = null;
		}

		setActiveEditor(null);
		super.dispose();
	}
}
