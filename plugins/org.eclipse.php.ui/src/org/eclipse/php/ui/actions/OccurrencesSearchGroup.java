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
package org.eclipse.php.ui.actions;

import org.eclipse.core.runtime.Assert;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.internal.ui.editor.ModelTextSelection;
import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.dltk.ui.IContextMenuConstants;
import org.eclipse.dltk.ui.actions.SelectionDispatchAction;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.php.internal.ui.actions.IPHPEditorActionDefinitionIds;
import org.eclipse.php.internal.ui.actions.PHPActionConstants;
import org.eclipse.php.internal.ui.actions.SelectionConverter;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionGroup;
import org.eclipse.ui.keys.IBindingService;
import org.eclipse.ui.texteditor.ITextEditorActionConstants;

/**
 * Action group that adds the occurrences in file actions to a context menu and
 * the global menu bar.
 * 
 * <p>
 * This class may be instantiated; it is not intended to be subclassed.
 * </p>
 * 
 * @since 3.1
 */
public class OccurrencesSearchGroup extends ActionGroup {

	private IWorkbenchSite fSite;
	private PHPStructuredEditor fEditor;
	private IActionBars fActionBars;
	private String fGroupId;

	private FindMethodExitOccurrencesAction fMethodExitOccurrencesAction;

	/**
	 * Creates a new <code>ImplementorsSearchGroup</code>. The group requires
	 * that the selection provided by the site's selection provider is of type
	 * <code>org.eclipse.jface.viewers.IStructuredSelection</code>.
	 * 
	 * @param site
	 *            the view part that owns this action group
	 */
	public OccurrencesSearchGroup(IWorkbenchSite site) {
		fSite = site;
		fGroupId = IContextMenuConstants.GROUP_SEARCH;

		fMethodExitOccurrencesAction = new FindMethodExitOccurrencesAction(site);
		fMethodExitOccurrencesAction
				.setActionDefinitionId(IPHPEditorActionDefinitionIds.SEARCH_METHOD_EXIT_OCCURRENCES);

		// register the actions as selection listeners
		ISelectionProvider provider = fSite.getSelectionProvider();
		ISelection selection = provider.getSelection();
		registerAction(fMethodExitOccurrencesAction, provider, selection);
	}

	/**
	 * Note: This constructor is for internal use only. Clients should not call
	 * this constructor.
	 * 
	 * @param editor
	 *            the Java editor
	 */
	public OccurrencesSearchGroup(PHPStructuredEditor editor) {
		fEditor = editor;
		fSite = fEditor.getSite();
		fGroupId = ITextEditorActionConstants.GROUP_FIND;
		fMethodExitOccurrencesAction = new FindMethodExitOccurrencesAction(fEditor);
		fMethodExitOccurrencesAction
				.setActionDefinitionId(IPHPEditorActionDefinitionIds.SEARCH_METHOD_EXIT_OCCURRENCES);
		fEditor.setAction("ExitOccurrencesAction", fMethodExitOccurrencesAction); //$NON-NLS-1$
	}

	private void registerAction(SelectionDispatchAction action, ISelectionProvider provider, ISelection selection) {
		action.update(selection);
		provider.addSelectionChangedListener(action);
	}

	/*
	 * Method declared on ActionGroup.
	 */
	@Override
	public void fillContextMenu(IMenuManager manager) {
		String menuText = "SearchMessages.group_occurrences"; //$NON-NLS-1$
		String shortcut = getShortcutString();
		if (shortcut != null) {
			menuText = menuText + '\t' + shortcut;
		}

		MenuManager javaSearchMM = new MenuManager(menuText, IContextMenuConstants.GROUP_SEARCH);
		javaSearchMM.add(new Action() {
		});
		javaSearchMM.addMenuListener(new IMenuListener() {
			@Override
			public void menuAboutToShow(IMenuManager mm) {
				mm.removeAll();
				updateActionsInPhpEditor();
				addAction(fMethodExitOccurrencesAction, mm);
				if (mm.isEmpty()) {
					mm.add(new Action("SearchMessages.group_occurrences_quickMenu_noEntriesAvailable") { //$NON-NLS-1$
						@Override
						public boolean isEnabled() {
							return false;
						}
					});
				}
			}

			private void addAction(Action action, IMenuManager mm) {
				if (action.isEnabled()) {
					mm.add(action);
				}
			}
		});
		manager.appendToGroup(fGroupId, javaSearchMM);
	}

	private void updateActionsInPhpEditor() {
		if (fEditor == null) {
			return;
		}

		IModelElement element = SelectionConverter.getInput(fEditor);
		if (element == null) {
			return;
		}

		ITextSelection textSelection = (ITextSelection) fEditor.getSelectionProvider().getSelection();
		IDocument document = DLTKUIPlugin.getDocumentProvider().getDocument(fEditor.getEditorInput());
		ModelTextSelection phpSelection = new ModelTextSelection(element, document, textSelection.getOffset(),
				textSelection.getLength());
		fMethodExitOccurrencesAction.update(phpSelection);
	}

	private String getShortcutString() {
		IBindingService bindingService = PlatformUI.getWorkbench().getAdapter(IBindingService.class);
		if (bindingService == null) {
			return null;
		}
		return bindingService
				.getBestActiveBindingFormattedFor(IPHPEditorActionDefinitionIds.SEARCH_OCCURRENCES_IN_FILE_QUICK_MENU);
	}

	/*
	 * Method declared on ActionGroup.
	 */
	@Override
	public void fillActionBars(IActionBars actionBars) {
		Assert.isNotNull(actionBars);
		super.fillActionBars(actionBars);
		fActionBars = actionBars;
		updateGlobalActionHandlers();
	}

	/*
	 * Method declared on ActionGroup.
	 */
	@Override
	public void dispose() {
		ISelectionProvider provider = fSite.getSelectionProvider();
		if (provider != null) {
			disposeAction(fMethodExitOccurrencesAction, provider);
		}
		super.dispose();
		fMethodExitOccurrencesAction = null;
		updateGlobalActionHandlers();
	}

	private void updateGlobalActionHandlers() {
		if (fActionBars != null) {
			fActionBars.setGlobalActionHandler(PHPActionConstants.FIND_METHOD_EXIT_OCCURRENCES,
					fMethodExitOccurrencesAction);

		}
	}

	private void disposeAction(ISelectionChangedListener action, ISelectionProvider provider) {
		if (action != null) {
			provider.removeSelectionChangedListener(action);
		}
	}
}
