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

import org.eclipse.jface.window.Window;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPCodeData;
import org.eclipse.php.internal.ui.PHPElementLabelProvider;
import org.eclipse.php.internal.ui.util.EditorUtility;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;


public class OpenActionUtil {

	private OpenActionUtil() {
		// no instance.
	}

	/**
	 * Opens the editor on the given element and subsequently selects it.
	 */
	public static void open(Object element) throws PartInitException {
		open(element, true);
	}

	/**
	 * Opens the editor on the given element and subsequently selects it.
	 */
	public static void open(Object element, boolean activate) throws PartInitException {
		IEditorPart part = EditorUtility.openInEditor(element, activate);
		if (element instanceof PHPCodeData)
			EditorUtility.revealInEditor(part, (PHPCodeData) element);
		if(element instanceof TreeItem){
			TreeItem item = (TreeItem)element;
			if(item.getData() instanceof PHPCodeData)
			EditorUtility.revealInEditor(part, (PHPCodeData) item.getData());
		}
	}

	/**
	 * Shows a dialog for resolving an ambiguous php element.
	 * Utility method that can be called by subclasses.
	 */
	public static PHPCodeData selectPHPElement(PHPCodeData[] elements, Shell shell, String title, String message) {

		int nResults = elements.length;

		if (nResults == 0)
			return null;

		if (nResults == 1)
			return elements[0];

		int flags = PHPElementLabelProvider.SHOW_DEFAULT | PHPElementLabelProvider.SHOW_QUALIFIED | PHPElementLabelProvider.SHOW_ROOT;

		ElementListSelectionDialog dialog = new ElementListSelectionDialog(shell, new PHPElementLabelProvider(flags));
		dialog.setTitle(title);
		dialog.setMessage(message);
		dialog.setElements(elements);

		if (dialog.open() == Window.OK) {
			Object[] selection = dialog.getResult();
			if (selection != null && selection.length > 0) {
				nResults = selection.length;
				for (int i = 0; i < nResults; i++) {
					Object current = selection[i];
					if (current instanceof PHPCodeData)
						return (PHPCodeData) current;
				}
			}
		}
		return null;
	}
}
