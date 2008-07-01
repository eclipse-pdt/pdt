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
import org.eclipse.php.internal.ui.actions.*;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.actions.RetargetAction;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.texteditor.RetargetTextEditorAction;
import org.eclipse.wst.html.ui.internal.edit.ui.ActionContributorHTML;
import org.eclipse.wst.xml.ui.internal.XMLUIMessages;

/**
 * A PHPEditorActionBarContributor, which is a simple extension for
 * BasicTextEditorActionContributor.
 * This class should not be used inside multi page editor's
 * ActionBarContributor, since cascaded init() call from the
 * ActionBarContributor will causes exception and it leads to lose whole
 * toolbars.
 * 
 * Instead, use SourcePageActionContributor for source page contributor of
 * multi page editor.
 * 
 * Note that this class is still valid for single page editor.
 */
public class ActionContributorForPhp extends ActionContributorHTML {

	private static final String[] EDITOR_IDS = {"org.eclipse.php.core.phpsource", "org.eclipse.wst.sse.ui.StructuredTextEditor"}; //$NON-NLS-1$ //$NON-NLS-2$
	
	private RetargetAction fRetargetShowPHPDoc;
	private List fPartListeners = new ArrayList();
	private RetargetTextEditorAction fShowPHPDoc;
	private RetargetTextEditorAction fGotoMatchingBracket;
	private RetargetTextEditorAction fOpenDeclaration;
	private RetargetTextEditorAction fOpenTypeHierarchy;
	private RetargetTextEditorAction fOpenCallHierarchy;
	private RetargetTextEditorAction fOpenHierarchy;
	private RetargetTextEditorAction fRename;
	private RetargetTextEditorAction fMove;
	private RetargetTextEditorAction fAddPHPDoc;
	private ToggleMarkOccurrencesAction fMarkOccurrences; // Registers as a global action

	protected MenuManager fFormatMenu = null;

	private MenuManager refactorMenu;
	private MenuManager sourceMenu ;
	
	public ActionContributorForPhp() {
		super();

		ResourceBundle b = PHPUIMessages.getBundleForConstructedKeys();

		fRetargetShowPHPDoc = new RetargetAction(PHPActionConstants.SHOW_PHP_DOC, PHPUIMessages.getString("ShowPHPDoc_label"));
		fRetargetShowPHPDoc.setActionDefinitionId(IPHPEditorActionDefinitionIds.SHOW_PHPDOC);
		fPartListeners.add(fRetargetShowPHPDoc);

		fShowPHPDoc = new RetargetTextEditorAction(b, "ShowPHPDoc."); //$NON-NLS-1$
		fShowPHPDoc.setActionDefinitionId(IPHPEditorActionDefinitionIds.SHOW_PHPDOC);

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

		fAddPHPDoc = new RetargetTextEditorAction(b, "AddPHPDoc."); //$NON-NLS-1$
		fAddPHPDoc.setActionDefinitionId(IPHPEditorActionDefinitionIds.ADD_PHP_DOC);
		
		fMarkOccurrences = new ToggleMarkOccurrencesAction(b);
		fMarkOccurrences.setActionDefinitionId(IPHPEditorActionDefinitionIds.TOGGLE_MARK_OCCURRENCES);

		// the refactor menu, add the menu itself to add all refactor actions
		this.refactorMenu = new MenuManager(PHPUIMessages.getString("ActionContributorJSP_0"), RefactorActionGroup.MENU_ID); //$NON-NLS-1$
		refactorMenu.add(this.fRename);
		refactorMenu.add(this.fMove);
		
	}
	
	protected void addToMenu(IMenuManager menu) {
		super.addToMenu(menu);
		menu.insertAfter(IWorkbenchActionConstants.M_EDIT, this.refactorMenu);
		
		
	}
	

	/*
	 * @see org.eclipse.ui.part.EditorActionBarContributor#contributeToMenu(org.eclipse.jface.action.IMenuManager)
	 */
	public void contributeToMenu(IMenuManager menu) {
		super.contributeToMenu(menu);

		IMenuManager gotoMenu = menu.findMenuUsingPath("navigate/goTo"); //$NON-NLS-1$
		
		if (gotoMenu != null) {
			gotoMenu.add(new Separator("additions2")); //$NON-NLS-1$
			gotoMenu.appendToGroup("additions2", fGotoMatchingBracket); //$NON-NLS-1$
		}
		IMenuManager navigateMenu = menu.findMenuUsingPath(IWorkbenchActionConstants.M_NAVIGATE);
		if (navigateMenu != null) {
			navigateMenu.appendToGroup(IWorkbenchActionConstants.SHOW_EXT, fOpenHierarchy);
		}
		//FIXME - need to add to the right menue and fix label/text issue.
		IMenuManager sourceMenu = menu.findMenuUsingPath(IWorkbenchActionConstants.M_NAVIGATE); //$NON-NLS-1$
		if (sourceMenu != null) {
			sourceMenu.appendToGroup(IWorkbenchActionConstants.SHOW_EXT, fAddPHPDoc);
		}
	}

	/*
	 * @see EditorActionBarContributor#setActiveEditor(IEditorPart)
	 */
	public void setActiveEditor(IEditorPart part) {
		super.setActiveEditor(part);

		ITextEditor editor = null;
		if (part instanceof ITextEditor)
			editor = (ITextEditor) part;

		fAddPHPDoc.setAction(getAction(editor, IPHPEditorActionDefinitionIds.ADD_DESCRIPTION )); //$NON-NLS-1$
		fShowPHPDoc.setAction(getAction(editor, "ShowPHPDoc")); //$NON-NLS-1$
		fGotoMatchingBracket.setAction(getAction(editor, GotoMatchingBracketAction.GOTO_MATCHING_BRACKET));
		fOpenDeclaration.setAction(getAction(editor, IPHPEditorActionDefinitionIds.OPEN_DECLARATION));
		fOpenTypeHierarchy.setAction(getAction(editor, IPHPEditorActionDefinitionIds.OPEN_TYPE_HIERARCHY));
		fOpenHierarchy.setAction(getAction(editor, IScriptEditorActionDefinitionIds.OPEN_HIERARCHY));
		fOpenTypeHierarchy.setAction(getAction(editor, IPHPEditorActionDefinitionIds.OPEN_CALL_HIERARCHY));
		fMarkOccurrences.setEditor(editor);
		if (part instanceof PHPStructuredEditor) {
			PHPStructuredEditor phpEditor = (PHPStructuredEditor) part;
			phpEditor.getActionGroup().fillActionBars(getActionBars());
		}
	}

	public void setViewerSpecificContributionsEnabled(boolean enabled) {
		super.setViewerSpecificContributionsEnabled(enabled);
		this.fRename.setEnabled(enabled);
		this.fMove.setEnabled(enabled);
		this.fAddPHPDoc.setEnabled(enabled);
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


	protected String[] getExtensionIDs() {
		return EDITOR_IDS;
	}	
}
