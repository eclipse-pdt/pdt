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
package org.eclipse.php.internal.ui.actions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.commands.operations.IUndoContext;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.ui.actions.SelectionDispatchAction;
import org.eclipse.jface.action.*;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.php.internal.ui.IContextMenuConstants;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.swt.events.MenuAdapter;
import org.eclipse.swt.events.MenuEvent;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IKeyBindingService;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ActionGroup;
import org.eclipse.ui.operations.UndoRedoActionGroup;
import org.eclipse.ui.part.Page;

public class RefactorActionGroup extends ActionGroup {

	public static final String MENU_ID = "org.eclipse.php.ui.refactoring.menu"; //$NON-NLS-1$

	/**
	 * Pop-up menu: id of the reorg group of the refactor sub menu (value
	 * <code>reorgGroup</code>).
	 * 
	 * @since 2.1
	 */
	public static final String GROUP_REORG = "reorgGroup"; //$NON-NLS-1$

	/**
	 * Pop-up menu: id of the type group of the refactor sub menu (value
	 * <code>typeGroup</code>).
	 * 
	 * @since 2.1
	 */
	public static final String GROUP_TYPE = "typeGroup"; //$NON-NLS-1$

	/**
	 * Pop-up menu: id of the coding group of the refactor sub menu (value
	 * <code>codingGroup</code>).
	 * 
	 * @since 2.1
	 */
	private IWorkbenchSite fSite;
	private PHPStructuredEditor fEditor;
	private String fGroupName = IContextMenuConstants.GROUP_REORGANIZE;

	private org.eclipse.dltk.ui.actions.SelectionDispatchAction fMoveAction;
	// private org.eclipse.dltk.ui.actions.SelectionDispatchAction
	// fRenameAction;

	private UndoRedoActionGroup fUndoRedoActionGroup;

	private List fEditorActions;

	private IKeyBindingService fKeyBindingService;

	private static class NoActionAvailable extends Action {
		public NoActionAvailable() {
			setEnabled(false);
			setText(PHPUIMessages.RefactorActionGroup_no_refactoring_available);
		}
	}

	private Action fNoActionAvailable = new NoActionAvailable();

	/**
	 * Creates a new <code>RefactorActionGroup</code>. The group requires that
	 * the selection provided by the part's selection provider is of type <code>
	 * org.eclipse.jface.viewers.IStructuredSelection</code> .
	 * 
	 * @param part
	 *            the view part that owns this action group
	 */
	public RefactorActionGroup(IViewPart part) {
		this(part.getSite(), part.getSite().getKeyBindingService());

		IUndoContext workspaceContext = (IUndoContext) ResourcesPlugin
				.getWorkspace().getAdapter(IUndoContext.class);
		fUndoRedoActionGroup = new UndoRedoActionGroup(part.getViewSite(),
				workspaceContext, true);
	}

	/**
	 * Creates a new <code>RefactorActionGroup</code>. The action requires that
	 * the selection provided by the page's selection provider is of type <code>
	 * org.eclipse.jface.viewers.IStructuredSelection</code> .
	 * 
	 * @param page
	 *            the page that owns this action group
	 */
	public RefactorActionGroup(Page page) {
		this(page.getSite(), null);
	}

	/**
	 * Note: This constructor is for internal use only. Clients should not call
	 * this constructor.
	 * 
	 * @param editor
	 *            the compilation unit editor
	 * @param groupName
	 *            the group name to add the actions to
	 */
	public RefactorActionGroup(PHPStructuredEditor editor, String groupName) {

		fSite = editor.getEditorPart().getEditorSite();
		fEditor = editor;
		fGroupName = groupName;
		ISelectionProvider provider = editor.getSelectionProvider();
		ISelection selection = provider.getSelection();
		fEditorActions = new ArrayList();

		fMoveAction = new org.eclipse.dltk.internal.ui.actions.refactoring.MoveAction(
				editor.getEditorSite());
		fMoveAction
				.setActionDefinitionId(IPHPEditorActionDefinitionIds.MOVE_ELEMENT);
		fMoveAction.update(selection);
		initAction(fMoveAction, provider, selection);
		editor.setAction("MoveElement", fMoveAction); //$NON-NLS-1$
		fEditorActions.add(fMoveAction);

		fKeyBindingService = editor.getEditorSite().getKeyBindingService();
	}

	private RefactorActionGroup(IWorkbenchSite site,
			IKeyBindingService keyBindingService) {

		fSite = site;
		ISelectionProvider provider = fSite.getSelectionProvider();
		ISelection selection = provider.getSelection();

		fMoveAction = new org.eclipse.dltk.internal.ui.actions.refactoring.MoveAction(
				site);
		fMoveAction
				.setActionDefinitionId(IPHPEditorActionDefinitionIds.MOVE_ELEMENT);
		initAction(fMoveAction, provider, selection);

		fKeyBindingService = keyBindingService;
	}

	private static void initAction(SelectionDispatchAction action,
			ISelectionProvider provider, ISelection selection) {
		action.update(selection);
		provider.addSelectionChangedListener(action);
	}

	/*
	 * (non-Javadoc) Method declared in ActionGroup
	 */
	public void fillActionBars(IActionBars actionBars) {
		super.fillActionBars(actionBars);
		actionBars.setGlobalActionHandler(PHPActionConstants.MOVE, fMoveAction);
		if (fUndoRedoActionGroup != null) {
			fUndoRedoActionGroup.fillActionBars(actionBars);
		}
	}

	/**
	 * Retargets the File actions with the corresponding refactoring actions.
	 * 
	 * @param actionBars
	 *            the action bar to register the move and rename action with
	 */
	public void retargetFileMenuActions(IActionBars actionBars) {
		actionBars.setGlobalActionHandler(ActionFactory.MOVE.getId(),
				fMoveAction);
	}

	/*
	 * (non-Javadoc) Method declared in ActionGroup
	 */
	public void fillContextMenu(IMenuManager menu) {
		super.fillContextMenu(menu);
		addRefactorSubmenu(menu);
	}

	/*
	 * @see ActionGroup#dispose()
	 */
	public void dispose() {
		ISelectionProvider provider = fSite.getSelectionProvider();
		disposeAction(fMoveAction, provider);
		if (fUndoRedoActionGroup != null) {
			fUndoRedoActionGroup.dispose();
		}
		super.dispose();
	}

	private void disposeAction(ISelectionChangedListener action,
			ISelectionProvider provider) {
		if (action != null)
			provider.removeSelectionChangedListener(action);
	}

	private void addRefactorSubmenu(IMenuManager menu) {
		String menuText = PHPUIMessages.RefactorMenu_label;

		IMenuManager refactorSubmenu = (IMenuManager) menu.find(MENU_ID);
		if (refactorSubmenu == null)
			refactorSubmenu = new MenuManager(menuText, MENU_ID);
		if (fEditor != null) {
			ISourceModule element = SelectionConverter.getInput(fEditor);
			if (element != null && ActionUtils.isPHPSource(element)) {
				refactorSubmenu.addMenuListener(new IMenuListener() {
					public void menuAboutToShow(IMenuManager manager) {
						refactorMenuShown(manager);
					}
				});
				fillMenu(menu, refactorSubmenu);
			}

		} else {
			fillMenu(menu, refactorSubmenu);
		}
	}

	private void fillMenu(IMenuManager menu, IMenuManager refactorSubmenu) {
		if (fillRefactorMenu(refactorSubmenu) > 0)
			menu.appendToGroup(fGroupName, refactorSubmenu);
		else
			refactorSubmenu.add(fNoActionAvailable);
	}

	private int fillRefactorMenu(IMenuManager refactorSubmenu) {
		int added = 0;
		refactorSubmenu.add(new Separator(GROUP_REORG));
		added += addAction(refactorSubmenu, fMoveAction);
		refactorSubmenu.add(new Separator(GROUP_TYPE));
		return added;
	}

	private int addAction(IMenuManager menu, IAction action) {
		if (action != null && action.isEnabled()) {
			menu.add(action);
			return 1;
		}
		return 0;
	}

	private void refactorMenuShown(final IMenuManager refactorSubmenu) {
		// we know that we have an MenuManager since we created it in
		// addRefactorSubmenu.
		Menu menu = ((MenuManager) refactorSubmenu).getMenu();
		menu.addMenuListener(new MenuAdapter() {
			public void menuHidden(MenuEvent e) {
				refactorMenuHidden(refactorSubmenu);
			}
		});
		ITextSelection textSelection = (ITextSelection) fEditor
				.getSelectionProvider().getSelection();

		for (Iterator iter = fEditorActions.iterator(); iter.hasNext();) {
			SelectionDispatchAction action = (SelectionDispatchAction) iter
					.next();
			action.update(textSelection);
		}
	}

	private void refactorMenuHidden(IMenuManager manager) {
		ITextSelection textSelection = (ITextSelection) fEditor
				.getSelectionProvider().getSelection();
		for (Iterator iter = fEditorActions.iterator(); iter.hasNext();) {
			SelectionDispatchAction action = (SelectionDispatchAction) iter
					.next();
			action.update(textSelection);
		}
	}

}
