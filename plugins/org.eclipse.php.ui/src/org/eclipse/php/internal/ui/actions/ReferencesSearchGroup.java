/*******************************************************************************
 * Copyright (c) 2000, 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.eclipse.php.internal.ui.actions;

import java.util.Iterator;

import org.eclipse.core.runtime.Assert;
import org.eclipse.dltk.internal.ui.callhierarchy.SearchUtil;
import org.eclipse.dltk.internal.ui.search.SearchMessages;
import org.eclipse.dltk.ui.IContextMenuConstants;
import org.eclipse.dltk.ui.actions.DLTKActionConstants;
import org.eclipse.dltk.ui.actions.IScriptEditorActionDefinitionIds;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.IWorkingSet;
import org.eclipse.ui.actions.ActionGroup;
import org.eclipse.ui.texteditor.ITextEditorActionConstants;

/**
 * Action group that adds the search for references actions to a context menu
 * and the global menu bar.
 * 
 * <p>
 * This class may be instantiated; it is not intended to be subclassed.
 * </p>
 */
public class ReferencesSearchGroup extends ActionGroup {

	private static final String MENU_TEXT = SearchMessages.group_references;

	private IWorkbenchSite fSite;

	private PHPStructuredEditor fEditor;

	private IActionBars fActionBars;

	private String fGroupId;

	private FindReferencesAction fFindReferencesAction;

	private FindReferencesInProjectAction fFindReferencesInProjectAction;

	private FindReferencesInHierarchyAction fFindReferencesInHierarchyAction;

	private FindReferencesInWorkingSetAction fFindReferencesInWorkingSetAction;

	/**
	 * Creates a new <code>ReferencesSearchGroup</code>. The group requires that
	 * the selection provided by the site's selection provider is of type <code>
	 * org.eclipse.jface.viewers.IStructuredSelection</code>.
	 * 
	 * @param site
	 *            the view part that owns this action group
	 */
	public ReferencesSearchGroup(IWorkbenchSite site) {
		fSite = site;
		fGroupId = IContextMenuConstants.GROUP_SEARCH;

		fFindReferencesAction = new FindReferencesAction(site);
		fFindReferencesAction.setActionDefinitionId(
				IScriptEditorActionDefinitionIds.SEARCH_REFERENCES_IN_WORKSPACE);

		fFindReferencesInProjectAction = new FindReferencesInProjectAction(
				site);
		fFindReferencesInProjectAction.setActionDefinitionId(
				IScriptEditorActionDefinitionIds.SEARCH_REFERENCES_IN_PROJECT);

		fFindReferencesInHierarchyAction = new FindReferencesInHierarchyAction(
				site);
		fFindReferencesInHierarchyAction.setActionDefinitionId(
				IScriptEditorActionDefinitionIds.SEARCH_REFERENCES_IN_HIERARCHY);

		fFindReferencesInWorkingSetAction = new FindReferencesInWorkingSetAction(
				site);
		fFindReferencesInWorkingSetAction.setActionDefinitionId(
				IScriptEditorActionDefinitionIds.SEARCH_REFERENCES_IN_WORKING_SET);

		// register the actions as selection listeners
		ISelectionProvider provider = fSite.getSelectionProvider();
		ISelection selection = provider.getSelection();
		registerAction(fFindReferencesAction, provider, selection);
		registerAction(fFindReferencesInProjectAction, provider, selection);
		registerAction(fFindReferencesInHierarchyAction, provider, selection);
		registerAction(fFindReferencesInWorkingSetAction, provider, selection);
	}

	/**
	 * Note: This constructor is for internal use only. Clients should not call
	 * this constructor.
	 * 
	 * @param editor
	 *            the Script editor
	 */
	public ReferencesSearchGroup(PHPStructuredEditor editor) {
		Assert.isNotNull(editor);
		fEditor = editor;
		fSite = fEditor.getSite();
		fGroupId = ITextEditorActionConstants.GROUP_FIND;

		fFindReferencesAction = new FindReferencesAction(editor);
		fFindReferencesAction.setActionDefinitionId(
				IScriptEditorActionDefinitionIds.SEARCH_REFERENCES_IN_WORKSPACE);
		fEditor.setAction("SearchReferencesInWorkspace", fFindReferencesAction); //$NON-NLS-1$

		fFindReferencesInProjectAction = new FindReferencesInProjectAction(
				fEditor);
		fFindReferencesInProjectAction.setActionDefinitionId(
				IScriptEditorActionDefinitionIds.SEARCH_REFERENCES_IN_PROJECT);
		fEditor.setAction("SearchReferencesInProject", //$NON-NLS-1$
				fFindReferencesInProjectAction);

		fFindReferencesInHierarchyAction = new FindReferencesInHierarchyAction(
				fEditor);
		fFindReferencesInHierarchyAction.setActionDefinitionId(
				IScriptEditorActionDefinitionIds.SEARCH_REFERENCES_IN_HIERARCHY);
		fEditor.setAction("SearchReferencesInHierarchy", //$NON-NLS-1$
				fFindReferencesInHierarchyAction);

		fFindReferencesInWorkingSetAction = new FindReferencesInWorkingSetAction(
				fEditor);
		fFindReferencesInWorkingSetAction.setActionDefinitionId(
				IScriptEditorActionDefinitionIds.SEARCH_REFERENCES_IN_WORKING_SET);
		fEditor.setAction("SearchReferencesInWorkingSet", //$NON-NLS-1$
				fFindReferencesInWorkingSetAction);
	}

	private void registerAction(SelectionDispatchAction action,
			ISelectionProvider provider, ISelection selection) {
		action.update(selection);
		provider.addSelectionChangedListener(action);
	}

	/**
	 * Note: this method is for internal use only. Clients should not call this
	 * method.
	 * 
	 * @return the menu label
	 */
	protected String getName() {
		return MENU_TEXT;
	}

	/*
	 * (non-Javadoc) Method declared in ActionGroup
	 */
	public void fillActionBars(IActionBars actionBars) {
		Assert.isNotNull(actionBars);
		super.fillActionBars(actionBars);
		fActionBars = actionBars;
		updateGlobalActionHandlers();
	}

	private void addAction(IAction action, IMenuManager manager) {
		if (action.isEnabled()) {
			manager.add(action);
		}
	}

	private void addWorkingSetAction(IWorkingSet[] workingSets,
			IMenuManager manager) {
		FindAction action;
		if (fEditor != null)
			action = new WorkingSetFindAction(fEditor,
					new FindReferencesInWorkingSetAction(fEditor, workingSets),
					SearchUtil.toString(workingSets));
		else
			action = new WorkingSetFindAction(fSite,
					new FindReferencesInWorkingSetAction(fSite, workingSets),
					SearchUtil.toString(workingSets));
		action.update(getContext().getSelection());
		addAction(action, manager);
	}

	/*
	 * (non-Javadoc) Method declared on ActionGroup.
	 */
	public void fillContextMenu(IMenuManager manager) {
		MenuManager javaSearchMM = new MenuManager(getName(),
				IContextMenuConstants.GROUP_SEARCH);
		addAction(fFindReferencesAction, javaSearchMM);
		addAction(fFindReferencesInProjectAction, javaSearchMM);
		addAction(fFindReferencesInHierarchyAction, javaSearchMM);

		javaSearchMM.add(new Separator());

		Iterator iter = SearchUtil.getLRUWorkingSets().sortedIterator();
		while (iter.hasNext()) {
			addWorkingSetAction((IWorkingSet[]) iter.next(), javaSearchMM);
		}
		addAction(fFindReferencesInWorkingSetAction, javaSearchMM);

		if (!javaSearchMM.isEmpty())
			manager.appendToGroup(fGroupId, javaSearchMM);
	}

	/*
	 * Overrides method declared in ActionGroup
	 */
	public void dispose() {
		ISelectionProvider provider = fSite.getSelectionProvider();
		if (provider != null) {
			disposeAction(fFindReferencesAction, provider);
			disposeAction(fFindReferencesInProjectAction, provider);
			disposeAction(fFindReferencesInHierarchyAction, provider);
			disposeAction(fFindReferencesInWorkingSetAction, provider);
		}
		fFindReferencesAction = null;
		fFindReferencesInProjectAction = null;
		fFindReferencesInHierarchyAction = null;
		fFindReferencesInWorkingSetAction = null;
		updateGlobalActionHandlers();
		super.dispose();
	}

	private void updateGlobalActionHandlers() {
		if (fActionBars != null) {
			fActionBars.setGlobalActionHandler(
					DLTKActionConstants.FIND_REFERENCES_IN_WORKSPACE,
					fFindReferencesAction);
			fActionBars.setGlobalActionHandler(
					DLTKActionConstants.FIND_REFERENCES_IN_PROJECT,
					fFindReferencesInProjectAction);
			fActionBars.setGlobalActionHandler(
					DLTKActionConstants.FIND_REFERENCES_IN_HIERARCHY,
					fFindReferencesInHierarchyAction);
			fActionBars.setGlobalActionHandler(
					DLTKActionConstants.FIND_REFERENCES_IN_WORKING_SET,
					fFindReferencesInWorkingSetAction);
		}
	}

	private void disposeAction(ISelectionChangedListener action,
			ISelectionProvider provider) {
		if (action != null)
			provider.removeSelectionChangedListener(action);
	}
}
