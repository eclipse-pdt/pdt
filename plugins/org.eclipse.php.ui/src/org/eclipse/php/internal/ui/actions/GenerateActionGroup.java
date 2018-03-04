/*******************************************************************************
 * Copyright (c) 2017 Alex Xu and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Alex Xu - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.actions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.IHandler;
import org.eclipse.dltk.ui.actions.DLTKActionConstants;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.php.internal.ui.IContextMenuConstants;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.actions.ActionGroup;
import org.eclipse.ui.handlers.IHandlerActivation;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.texteditor.ITextEditorActionConstants;
import org.eclipse.ui.texteditor.IUpdate;

/**
 * Action group that adds the source and generate actions to a part's context
 * menu and installs handlers for the corresponding global menu actions.
 */
public class GenerateActionGroup extends ActionGroup {

	/**
	 * Pop-up menu: id of the source sub menu (value
	 * <code>org.eclipse.dltk.ui.source.menu</code>).
	 */
	public static final String MENU_ID = "org.eclipse.php.ui.source.menu"; //$NON-NLS-1$

	/**
	 * Pop-up menu: id of the generate group of the source sub menu (value
	 * <code>generateGroup</code>).
	 */
	public static final String GROUP_GENERATE = "generateGroup"; //$NON-NLS-1$

	/**
	 * Pop-up menu: id of the code group of the source sub menu (value
	 * <code>codeGroup</code>).
	 */
	public static final String GROUP_CODE = "codeGroup"; //$NON-NLS-1$

	/**
	 * Pop-up menu: id of the externalize group of the source sub menu (value
	 * <code>externalizeGroup</code>).
	 */
	public static final String GROUP_EXTERNALIZE = "externalizeGroup"; //$NON-NLS-1$

	/**
	 * Pop-up menu: id of the comment group of the source sub menu (value
	 * <code>commentGroup</code>).
	 */
	public static final String GROUP_COMMENT = "commentGroup"; //$NON-NLS-1$

	/**
	 * Pop-up menu: id of the edit group of the source sub menu (value
	 * <code>editGroup</code>).
	 */
	public static final String GROUP_EDIT = "editGroup"; //$NON-NLS-1$

	private PHPStructuredEditor fEditor;
	private IWorkbenchSite fSite;
	private String fGroupName = IContextMenuConstants.GROUP_REORGANIZE;
	private List<ISelectionChangedListener> fRegisteredSelectionListeners;

	private OrganizeUseStatementsAction fOrganizeUseStatementsAction;

	private static final String QUICK_MENU_ID = "org.eclipse.php.ui.edit.text.php.source.quickMenu"; //$NON-NLS-1$

	private final ISelectionProvider fSelectionProvider;

	private IHandlerActivation fQuickAccessHandlerActivation;
	private IHandlerService fHandlerService;

	/**
	 * Note: This constructor is for internal use only. Clients should not call this
	 * constructor.
	 * 
	 * @param editor
	 *            the script editor
	 * @param groupName
	 *            the group name to add the action to
	 */
	public GenerateActionGroup(PHPStructuredEditor editor, String groupName) {
		fSite = editor.getSite();
		fSelectionProvider = fSite.getSelectionProvider();
		fEditor = editor;
		fGroupName = groupName;

		fOrganizeUseStatementsAction = new OrganizeUseStatementsAction(editor);
		fOrganizeUseStatementsAction.setActionDefinitionId(IPHPEditorActionDefinitionIds.ORGANIZE_USE_STATEMENT);
		editor.setAction("OrganizeUseStatements", fOrganizeUseStatementsAction); //$NON-NLS-1$
	}

	// private FormatAction formatAction;

	public GenerateActionGroup(IViewPart part) {
		this(part.getSite(), null);

		installQuickAccessAction();
	}

	public GenerateActionGroup(IWorkbenchSite site, ISelectionProvider selectionProvider) {
		fSite = site;
		fSelectionProvider = selectionProvider == null ? fSite.getSelectionProvider() : selectionProvider;
		ISelection selection = fSelectionProvider.getSelection();

		fOrganizeUseStatementsAction = new OrganizeUseStatementsAction(site);
		fOrganizeUseStatementsAction.setActionDefinitionId(IPHPEditorActionDefinitionIds.ORGANIZE_USE_STATEMENT);
		fOrganizeUseStatementsAction.update(selection);

		SelectionDispatchAction[] actions = new SelectionDispatchAction[] { fOrganizeUseStatementsAction };

		for (int i = 0; i < actions.length; i++) {
			SelectionDispatchAction action = actions[i];
			registerSelectionListener(fSelectionProvider, action);
			if (selectionProvider != null) {
				action.setSpecialSelectionProvider(fSelectionProvider);
			}
		}
	}

	private void installQuickAccessAction() {
		fHandlerService = fSite.getService(IHandlerService.class);
		if (fHandlerService != null) {
			IHandler handler = new PHPQuickMenuCreator(null) {
				@Override
				protected void fillMenu(IMenuManager menu) {
					fillQuickMenu(menu);
				}
			}.createHandler();

			fQuickAccessHandlerActivation = fHandlerService.activateHandler(QUICK_MENU_ID, handler);
		}
	}

	private void registerSelectionListener(ISelectionProvider provider, ISelectionChangedListener listener) {
		if (fRegisteredSelectionListeners == null) {
			fRegisteredSelectionListeners = new ArrayList<>(20);
		}
		provider.addSelectionChangedListener(listener);
		fRegisteredSelectionListeners.add(listener);
	}

	private void installFormatAction() {
		// if
		// (!ScriptFormatterManager.hasFormatterFor(fEditor.getLanguageToolkit().getNatureId()))
		// {
		// return;
		// }
		// Action action = new
		// TextOperationAction(DLTKEditorMessages.getBundleForConstructedKeys(),
		// "Format.", fEditor, //$NON-NLS-1$
		// ISourceViewer.FORMAT);
		// action.setActionDefinitionId(IScriptEditorActionDefinitionIds.FORMAT);
		// fEditor.setAction(DLTKActionConstants.FORMAT, action);
		// fEditor.markAsStateDependentAction(DLTKActionConstants.FORMAT, true);
		// fEditor.markAsSelectionDependentAction(DLTKActionConstants.FORMAT,
		// true);
	}

	@Override
	public void fillActionBars(IActionBars actionBar) {
		super.fillActionBars(actionBar);
		setGlobalActionHandlers(actionBar);
	}

	@Override
	public void fillContextMenu(IMenuManager menu) {
		super.fillContextMenu(menu);
		String menuText = "Source"; //$NON-NLS-1$
		// if (fQuickAccessAction != null) {
		// menuText = fQuickAccessAction.addShortcut(menuText);
		// }
		IMenuManager subMenu = new MenuManager(menuText, MENU_ID);
		int added = 0;
		if (isEditorOwner()) {
			added = fillEditorSubMenu(subMenu);
		} else {
			added = fillViewSubMenu(subMenu);
		}
		if (added > 0) {
			menu.appendToGroup(fGroupName, subMenu);
		}
	}

	/**
	 * @since 2.0
	 */
	protected void fillQuickMenu(IMenuManager menu) {
		if (isEditorOwner()) {
			fillEditorSubMenu(menu);
		} else {
			fillViewSubMenu(menu);
		}
	}

	protected int fillEditorSubMenu(IMenuManager source) {
		int added = 0;
		source.add(new Separator(GROUP_COMMENT));
		added += addEditorAction(source, DLTKActionConstants.ADD_BLOCK_COMMENT);
		added += addEditorAction(source, DLTKActionConstants.REMOVE_BLOCK_COMMENT);
		added += addEditorAction(source, DLTKActionConstants.TOGGLE_COMMENT);
		added += addEditorAction(source, DLTKActionConstants.COMMENT);
		added += addEditorAction(source, DLTKActionConstants.UNCOMMENT);
		source.add(new Separator(GROUP_EDIT));
		added += addEditorAction(source, DLTKActionConstants.FORMAT);
		added += addEditorAction(source, DLTKActionConstants.FORMAT_ELEMENT);
		added += addEditorAction(source, DLTKActionConstants.INDENT);
		source.add(new Separator());
		added += addEditorAction(source, ITextEditorActionConstants.SHIFT_LEFT);
		added += addEditorAction(source, ITextEditorActionConstants.SHIFT_RIGHT);
		source.add(new Separator(GROUP_GENERATE));
		source.add(new Separator(GROUP_CODE));
		source.add(new Separator(GROUP_EXTERNALIZE));
		return added;
	}

	private int fillViewSubMenu(IMenuManager source) {
		source.add(fOrganizeUseStatementsAction);
		return 1;
	}

	@Override
	public void dispose() {
		if (fQuickAccessHandlerActivation != null && fHandlerService != null) {
			fHandlerService.deactivateHandler(fQuickAccessHandlerActivation);
		}
		fEditor = null;
		super.dispose();
	}

	protected void setGlobalActionHandlers(IActionBars bars) {
		bars.setGlobalActionHandler(PHPActionConstants.ORGANIZE_USE_STATEMENTS, fOrganizeUseStatementsAction);
		bars.updateActionBars();
	}

	protected final IAction getAction(ITextEditor editor, String actionId) {
		return (editor == null || actionId == null ? null : editor.getAction(actionId));
	}

	protected int addAction(IMenuManager menu, String groupName, IAction action) {
		if (action != null && action.isEnabled()) {
			menu.appendToGroup(groupName, action);
			return 1;
		}
		return 0;
	}

	protected int addEditorAction(IMenuManager menu, String groupName, String actionID) {
		if (fEditor == null) {
			return 0;
		}
		IAction action = fEditor.getAction(actionID);
		if (action == null) {
			return 0;
		}
		if (action instanceof IUpdate) {
			((IUpdate) action).update();
		}
		if (action.isEnabled()) {
			menu.appendToGroup(groupName, action);
			return 1;
		}
		return 0;
	}

	protected int addEditorAction(IMenuManager menu, String actionID) {
		if (fEditor == null) {
			return 0;
		}
		IAction action = fEditor.getAction(actionID);
		if (action == null) {
			return 0;
		}
		if (action instanceof IUpdate) {
			((IUpdate) action).update();
		}
		if (action.isEnabled()) {
			menu.add(action);
			return 1;
		}
		return 0;
	}

	private boolean isEditorOwner() {
		return fEditor != null;
	}
}
