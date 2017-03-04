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

import org.eclipse.dltk.core.search.IDLTKSearchConstants;
import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.dltk.ui.IDLTKUILanguageToolkit;
import org.eclipse.php.internal.ui.PHPUILanguageToolkit;
import org.eclipse.php.internal.ui.dialogs.OpenTypeSelectionDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.SelectionDialog;

/**
 * Open type in hierarchy action.
 * 
 * @author nirc
 */
public class OpenTypeAction extends org.eclipse.dltk.ui.actions.OpenTypeAction {

	@Override
	protected IDLTKUILanguageToolkit getUILanguageToolkit() {
		return PHPUILanguageToolkit.getInstance();
	}

	@Override
	protected SelectionDialog createDialog() {
		final Shell parent = DLTKUIPlugin.getActiveWorkbenchShell();
		OpenTypeSelectionDialog dialog = new OpenTypeSelectionDialog(parent, true,
				PlatformUI.getWorkbench().getProgressService(), null, IDLTKSearchConstants.TYPE);
		dialog.setTitle(getOpenTypeDialogTitle());
		dialog.setMessage(getOpenTypeDialogMessage());
		return dialog;
	}
}
