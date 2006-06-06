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
package org.eclipse.php.internal.ui.actions;

import java.util.Iterator;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.php.internal.ui.IPHPHelpContextIds;
import org.eclipse.php.ui.PHPUiPlugin;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.PlatformUI;


public class CutAction extends SelectionDispatchAction {

	private CopyToClipboardAction fCopyToClipboardAction;

	public CutAction(IWorkbenchSite site, Clipboard clipboard, SelectionDispatchAction pasteAction) {
		super(site);
		setText(ActionMessages.CutAction_text);
		fCopyToClipboardAction = new CopyToClipboardAction(site, clipboard, pasteAction);

		ISharedImages workbenchImages = PHPUiPlugin.getDefault().getWorkbench().getSharedImages();
		setDisabledImageDescriptor(workbenchImages.getImageDescriptor(ISharedImages.IMG_TOOL_CUT_DISABLED));
		setImageDescriptor(workbenchImages.getImageDescriptor(ISharedImages.IMG_TOOL_CUT));
		setHoverImageDescriptor(workbenchImages.getImageDescriptor(ISharedImages.IMG_TOOL_CUT));

		update(getSelection());
		PlatformUI.getWorkbench().getHelpSystem().setHelp(this, IPHPHelpContextIds.CUT_ACTION);
	}

	public void selectionChanged(IStructuredSelection selection) {
		if (!selection.isEmpty()) {
			// cannot cut top-level types. this deletes the cu and then you cannot paste because the cu is gone.
			if (!containsOnlyElementsInsidePHPFiles(selection)) {
				setEnabled(false);
				return;
			}
			fCopyToClipboardAction.selectionChanged(selection);
			setEnabled(fCopyToClipboardAction.isEnabled() && ActionUtils.isDeleteAvailable(selection));
		} else
			setEnabled(false);
	}

	private static boolean containsOnlyElementsInsidePHPFiles(IStructuredSelection selection) {
		for (Iterator iter = selection.iterator(); iter.hasNext();) {
			Object object = iter.next();
			if (!ActionUtils.isInsidePHPFile(object))
				return false;
		}
		return true;
	}

	public void run(IStructuredSelection selection) {
		selectionChanged(selection);
		if (isEnabled()) {
			fCopyToClipboardAction.run(selection);

		}
	}
}