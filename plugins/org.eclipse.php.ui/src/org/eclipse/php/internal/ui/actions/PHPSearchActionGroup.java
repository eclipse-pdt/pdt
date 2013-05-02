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

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.action.*;
import org.eclipse.php.internal.ui.IContextMenuConstants;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.php.internal.ui.preferences.PreferenceConstants;
import org.eclipse.php.ui.actions.OccurrencesSearchGroup;
import org.eclipse.search.internal.ui.OpenSearchDialogAction;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.actions.ActionContext;
import org.eclipse.ui.actions.ActionGroup;
import org.eclipse.ui.texteditor.ITextEditorActionConstants;

/**
 * @author seva A virtual action which adds the Search item to a context menu
 *         (Used in ExplorerPart)
 */
public class PHPSearchActionGroup extends ActionGroup {
	private OpenSearchDialogAction action;
	private OccurrencesSearchGroup fOccurrencesGroup;
	private final PHPStructuredEditor fEditor;

	/**
	 * Constructs the class.
	 * 
	 * @param part
	 */
	public PHPSearchActionGroup(PHPStructuredEditor editor) {
		Assert.isNotNull(editor);
		this.fEditor = editor;

		action = new OpenSearchDialogAction();
		fOccurrencesGroup = new OccurrencesSearchGroup(editor);
	}

	/**
	 * Constructs the class.
	 * 
	 * @param part
	 */
	public PHPSearchActionGroup(IWorkbenchSite site) {
		fEditor = null;
		action = new OpenSearchDialogAction();
		fOccurrencesGroup = new OccurrencesSearchGroup(site);
	}

	@Override
	public void setContext(ActionContext context) {
		fOccurrencesGroup.setContext(context);
		super.setContext(context);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.actions.ActionGroup#fillActionBars(org.eclipse.ui.IActionBars
	 * )
	 */
	public void fillActionBars(IActionBars actionBar) {
		fOccurrencesGroup.fillActionBars(actionBar);
		super.fillActionBars(actionBar);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.actions.ActionGroup#fillContextMenu(org.eclipse.jface.
	 * action.IMenuManager)
	 */
	public void fillContextMenu(IMenuManager menu) {
		super.fillContextMenu(menu);
		if (!PreferenceConstants.getPreferenceStore().getBoolean(
				PreferenceConstants.SEARCH_USE_REDUCED_MENU)) {
			IMenuManager target = menu;
			IMenuManager searchSubMenu = null;
			if (fEditor != null) {
				String groupName = "SearchMessages.group_search"; //$NON-NLS-1$
				searchSubMenu = new MenuManager(groupName,
						ITextEditorActionConstants.GROUP_FIND);
				searchSubMenu.add(new GroupMarker(
						ITextEditorActionConstants.GROUP_FIND));
				target = searchSubMenu;
			}

			if (searchSubMenu != null) {
				fOccurrencesGroup.fillContextMenu(target);
				searchSubMenu.add(new Separator());
			}

			// no other way to find out if we have added items.
			if (searchSubMenu != null && searchSubMenu.getItems().length > 2) {
				menu.appendToGroup(ITextEditorActionConstants.GROUP_FIND,
						searchSubMenu);
			}
		}

		IContributionItem item = menu.find(IContextMenuConstants.GROUP_OPEN);
		if (item != null) {
			menu.appendToGroup(IContextMenuConstants.GROUP_OPEN, action);
		}
	}

	@Override
	public void dispose() {
		fOccurrencesGroup.dispose();
		super.dispose();
	}

}