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
 *******************************************************************************/
package org.eclipse.php.composer.ui.dialogs;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.preference.PreferenceDialog;
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

@SuppressWarnings("restriction")
public class MissingExecutableDialog extends ErrorDialog {

	private final Shell shell;

	public MissingExecutableDialog(Shell parentShell, IStatus info) {
		super(parentShell, Messages.MissingExecutableDialog_Title, Messages.MissingExecutableDialog_Message, info,
				IStatus.CANCEL | IStatus.ERROR | IStatus.OK | IStatus.WARNING);
		shell = parentShell;
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite main = (Composite) super.createDialogArea(parent);

		Composite space = new Composite(main, SWT.NONE);
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, false, false);
		gridData.heightHint = 1;
		gridData.widthHint = 1;
		space.setLayoutData(gridData);
		Link link = createPreferencesLink(main);
		link.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
		return main;
	}

	private Link createPreferencesLink(Composite parent) {
		Link link = new Link(parent, SWT.NONE);
		link.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					PreferenceDialog preferenceDialog = PreferencesUtil.createPreferenceDialogOn(shell,
							PHPsPreferencePage.ID, new String[] {}, null);
					preferenceDialog.open();
					MissingExecutableDialog.this.close();
				} catch (Exception e2) {
					Logger.logException(e2);
				}
			}
		});
		link.setText(Messages.MissingExecutableDialog_LinkText);
		link.setToolTipText(Messages.MissingExecutableDialog_LinkToolTipText);
		Dialog.applyDialogFont(link);

		return link;
	}
}
