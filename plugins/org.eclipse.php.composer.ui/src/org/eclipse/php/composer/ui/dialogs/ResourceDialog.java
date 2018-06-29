/*******************************************************************************
 * Copyright (c) 2012, 2016 PDT Extension Group and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.ui.dialogs;

import java.util.List;

import org.eclipse.dltk.internal.ui.wizards.TypedViewerFilter;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.CheckedTreeSelectionDialog;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.dialogs.SelectionStatusDialog;
import org.eclipse.ui.model.BaseWorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;

public class ResourceDialog {

	private static void configure(SelectionStatusDialog dialog, String title, String message) {
		dialog.setTitle(title);
		dialog.setMessage(message);
		dialog.setHelpAvailable(false);
	}

	@SuppressWarnings("rawtypes")
	public static CheckedTreeSelectionDialog createMulti(Shell parent, String title, String message, Class[] filter,
			Object input, List selectedElements) {
		CheckedTreeSelectionDialog diag = new CheckedTreeSelectionDialog(parent, new WorkbenchLabelProvider(),
				new BaseWorkbenchContentProvider());

		configure(diag, title, message);

		if (filter.length > 0) {
			diag.addFilter(new TypedViewerFilter(filter));
		}

		diag.setInput(input);

		if (selectedElements != null) {
			diag.setInitialElementSelections(selectedElements);
		}
		return diag;
	}

	@SuppressWarnings("rawtypes")
	public static ElementTreeSelectionDialog createSingle(Shell parent, String title, String message, Class[] filter,
			Object input, List selectedElements) {

		ElementTreeSelectionDialog diag = new ElementTreeSelectionDialog(parent, new WorkbenchLabelProvider(),
				new BaseWorkbenchContentProvider());

		configure(diag, title, message);

		if (filter.length > 0) {
			diag.addFilter(new TypedViewerFilter(filter));
		}

		diag.setInput(input);

		if (selectedElements != null) {
			diag.setInitialElementSelections(selectedElements);
		}
		return diag;
	}

}
