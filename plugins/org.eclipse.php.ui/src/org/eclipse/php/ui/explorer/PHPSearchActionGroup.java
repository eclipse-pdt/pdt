/**
 * Copyright (c) 2007 Zend Technologies
 * 
 */
package org.eclipse.php.ui.explorer;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.php.ui.IContextMenuConstants;
import org.eclipse.search.internal.ui.OpenSearchDialogAction;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.actions.ActionGroup;

/**
 * @author seva
 * A virtual action which adds the Search item to a context menu (Used in ExplorerPart)
 */
public class PHPSearchActionGroup extends ActionGroup {
	private OpenSearchDialogAction action;

	/**
	 * Constructs the class.
	 */
	public PHPSearchActionGroup() {
		action = new OpenSearchDialogAction();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.actions.ActionGroup#fillActionBars(org.eclipse.ui.IActionBars)
	 */
	public void fillActionBars(IActionBars actionBar) {
		super.fillActionBars(actionBar);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.actions.ActionGroup#fillContextMenu(org.eclipse.jface.action.IMenuManager)
	 */
	public void fillContextMenu(IMenuManager menu) {
		super.fillContextMenu(menu);
		menu.appendToGroup(IContextMenuConstants.GROUP_OPEN, action);
	}
}