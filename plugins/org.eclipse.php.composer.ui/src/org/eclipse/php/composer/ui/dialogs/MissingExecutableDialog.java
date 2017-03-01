/*******************************************************************************
 * Copyright (c) 2012, 2016, 2017 PDT Extension Group and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *     Kaloyan Raev - [501269] externalize strings
 *     Kaloyan Raev - [511744] Wizard freezes if no PHP executable is configured
 *******************************************************************************/
package org.eclipse.php.composer.ui.dialogs;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.osgi.util.NLS;
import org.eclipse.php.composer.core.log.Logger;
import org.eclipse.php.internal.debug.ui.preferences.phps.PHPsPreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.PreferencesUtil;

public class MissingExecutableDialog extends MessageDialog {

	private final Shell shell;

	public MissingExecutableDialog(Shell parentShell, IStatus info) {
		super(parentShell, Messages.MissingExecutableDialog_Title, null,
				NLS.bind(Messages.MissingExecutableDialog_Message, info.getMessage()), MessageDialog.WARNING,
				new String[] { Messages.MissingExecutableDialog_ConfigureButtonLabel, Messages.MissingExecutableDialog_CancelButtonLabel }, 0);
		shell = parentShell;
	}

	@Override
	protected Control createMessageArea(Composite parent) {
		Composite main = (Composite) super.createMessageArea(parent);

		Composite space = new Composite(main, SWT.NONE);
		space.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));

		Link link = createPreferencesLink(main);
		link.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));

		return main;
	}

	@Override
	protected void buttonPressed(int buttonId) {
		if (buttonId == 0) {
			openPreferences();
		} else {
			super.buttonPressed(buttonId);
		}
	}

	private Link createPreferencesLink(Composite parent) {
		Link link = new Link(parent, SWT.NONE);
		link.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				openPreferences();
			}
		});
		link.setText(Messages.MissingExecutableDialog_LinkText);
		link.setToolTipText(Messages.MissingExecutableDialog_LinkToolTipText);
		Dialog.applyDialogFont(link);

		return link;
	}

	private void openPreferences() {
		try {
			PreferenceDialog preferenceDialog = PreferencesUtil.createPreferenceDialogOn(shell, PHPsPreferencePage.ID,
					new String[] {}, null);
			preferenceDialog.open();
			setReturnCode(OK);
			close();
		} catch (Exception e2) {
			Logger.logException(e2);
		}
	}
}
