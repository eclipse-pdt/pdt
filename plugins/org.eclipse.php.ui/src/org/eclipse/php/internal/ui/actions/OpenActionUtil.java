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

import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.internal.ui.editor.EditorUtility;
import org.eclipse.dltk.ui.ModelElementLabelProvider;
import org.eclipse.jface.window.Window;
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
	 * 
	 * @throws ModelException
	 */
	public static void open(Object element) throws PartInitException,
			ModelException {
		open(element, true);
	}

	/**
	 * Opens the editor on the given element and subsequently selects it.
	 * 
	 * @param element
	 *            Element to open
	 * @param activate
	 *            Activate editor
	 * @throws PartInitException
	 * @throws ModelException
	 */
	public static void open(Object element, boolean activate)
			throws PartInitException, ModelException {
		IEditorPart part = EditorUtility.openInEditor(element, activate);
		if (element instanceof IModelElement)
			EditorUtility.revealInEditor(part, ((IModelElement) element));
		if (element instanceof TreeItem) {
			TreeItem item = (TreeItem) element;
			if (item.getData() instanceof IModelElement)
				EditorUtility.revealInEditor(part, ((IModelElement) (item
						.getData())));
		}
	}

	/**
	 * Shows a dialog for resolving an ambiguous php element. Utility method
	 * that can be called by subclasses.
	 */
	public static IModelElement selectPHPElement(IModelElement[] elements,
			Shell shell, String title, String message) {

		int nResults = elements.length;

		if (nResults == 0)
			return null;

		if (nResults == 1)
			return elements[0];

		int flags = ModelElementLabelProvider.SHOW_DEFAULT
				| ModelElementLabelProvider.SHOW_QUALIFIED
				| ModelElementLabelProvider.SHOW_ROOT;

		ElementListSelectionDialog dialog = new ElementListSelectionDialog(
				shell, new ModelElementLabelProvider(flags));
		dialog.setTitle(title);
		dialog.setMessage(message);
		dialog.setElements(elements);

		if (dialog.open() == Window.OK) {
			Object[] selection = dialog.getResult();
			if (selection != null && selection.length > 0) {
				nResults = selection.length;
				for (int i = 0; i < nResults; i++) {
					Object current = selection[i];
					if (current instanceof ISourceModule)
						return (IModelElement) current;
				}
			}
		}
		return null;
	}
}
