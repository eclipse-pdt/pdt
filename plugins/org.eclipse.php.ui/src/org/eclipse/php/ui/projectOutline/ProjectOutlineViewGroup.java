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
package org.eclipse.php.ui.projectOutline;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.util.Assert;
import org.eclipse.php.internal.ui.actions.OpenEditorActionGroup;
import org.eclipse.php.ui.actions.SortAction;
import org.eclipse.php.ui.workingset.ViewActionGroup;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.actions.ActionContext;

public class ProjectOutlineViewGroup extends ViewActionGroup {

	public static final int PHP4 = 1;
	public static final int PHP5 = 2;

	protected ProjectOutlinePart fPart;
	//private ToggleAllAction toggleAllAction;
	private SortAction sortAction;
	private ToggleLinkingAction toggleLinking;
	private CollapseAllAction collapseAllAction;
	private OpenEditorActionGroup fOpenEditorActionGroup;

	public class ToggleAllAction extends Action {

		public ToggleAllAction(ViewActionGroup group) {
			super("", AS_CHECK_BOX); //$NON-NLS-1$
			Assert.isNotNull(group);
		}

		/**
		 * {@inheritDoc}
		 */
		public void run() {
			fPart.setShowAll(isChecked());
		}
	}

	public ProjectOutlineViewGroup(ProjectOutlinePart part) {
		this.fPart = part;

//		toggleAllAction = new ToggleAllAction(this); //$NON-NLS-1$
//		toggleAllAction.setText("Show All");

		sortAction = new SortAction(part.getViewer());

		collapseAllAction = new CollapseAllAction(part);

		toggleLinking = new ToggleLinkingAction(part);
		fOpenEditorActionGroup = new OpenEditorActionGroup(part);

		//		fillContextMenu(fPart.getViewSite().getActionBars().getMenuManager());
	}

	public void dispose() {
		fOpenEditorActionGroup.dispose();
		super.dispose();
	}

	public void fillContextMenu(IMenuManager menu) {
		super.fillContextMenu(menu);
		fOpenEditorActionGroup.fillContextMenu(menu);
		//toggleAllAction.setChecked(fPart.isShowAll());

		//		menu.add(toggleAllAction);
		//		menu.add(toggleLinking);
	}

	public void updateActions() {
	}

	public void setMode(int mode) {

	}

	void fillToolBar(IToolBarManager toolBar) {
		toolBar.add(collapseAllAction);
		toolBar.add(sortAction);
		toolBar.add(toggleLinking);
	}

	protected void fillMenu(IMenuManager menu) {
//		toggleAllAction.setChecked(fPart.isShowAll());
//		menu.add(toggleAllAction);
		//		menu.add(toggleLinking);
	}

	public void fillActionBars(IActionBars actionBars) {
		super.fillActionBars(actionBars);
		fOpenEditorActionGroup.fillActionBars(actionBars);
		fillToolBar(actionBars.getToolBarManager());
		fillMenu(actionBars.getMenuManager());
	}

	public void setContext(ActionContext context) {
		super.setContext(context);
		fOpenEditorActionGroup.setContext(context);
	}

	public void updateActionBars() {
		super.updateActionBars();
		fOpenEditorActionGroup.updateActionBars();
	}

}
