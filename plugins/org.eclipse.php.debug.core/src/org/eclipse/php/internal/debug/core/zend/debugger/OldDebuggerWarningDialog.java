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
package org.eclipse.php.internal.debug.core.zend.debugger;

import java.net.URL;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.browser.IWebBrowser;
import org.eclipse.ui.browser.IWorkbenchBrowserSupport;

public class OldDebuggerWarningDialog extends Dialog {

	public OldDebuggerWarningDialog(Shell parentShell) {
		super(parentShell);
	}

	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);

		getShell().setText(Messages.OldDebuggerWarningDialog_0);

		Label label = new Label(composite, SWT.NONE);
		label
				.setText(Messages.OldDebuggerWarningDialog_1);

		Link link = new Link(composite, SWT.NONE);
		link.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		link
				.setText(Messages.OldDebuggerWarningDialog_4);
		link.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				try {
					IWorkbenchBrowserSupport browserSupport = PlatformUI
							.getWorkbench().getBrowserSupport();
					IWebBrowser browser = browserSupport.getExternalBrowser();
					browser
							.openURL(new URL(
									"http://www.zend.com/en/products/studio/downloads")); //$NON-NLS-1$
				} catch (Exception e) {
				}
			}
		});
		link = new Link(composite, SWT.NONE);
		link.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		link
				.setText(Messages.OldDebuggerWarningDialog_6);
		link.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				try {
					IWorkbenchBrowserSupport browserSupport = PlatformUI
							.getWorkbench().getBrowserSupport();
					IWebBrowser browser = browserSupport.getExternalBrowser();
					browser.openURL(new URL(
							"http://www.zend.com/app-server/downloads")); //$NON-NLS-1$
				} catch (Exception e) {
				}
			}
		});

		final Button button = new Button(composite, SWT.CHECK);
		button.setText(Messages.OldDebuggerWarningDialog_9);
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				PHPDebugPlugin.getDefault().getPluginPreferences().setValue(
						"DontShowOlderDebuggerWarning", button.getSelection()); //$NON-NLS-1$
			}
		});

		return composite;
	}

	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
	}
}
