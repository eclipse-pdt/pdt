/*******************************************************************************
 * Copyright (c) 2015 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.ui.preferences.phps;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.MessageFormat;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.window.Window;
import org.eclipse.php.internal.debug.core.PHPExeUtil;
import org.eclipse.php.internal.debug.core.preferences.PHPexeItem;
import org.eclipse.php.internal.debug.core.preferences.PHPexes;
import org.eclipse.php.internal.debug.ui.Logger;
import org.eclipse.php.internal.debug.ui.wizards.PHPExeEditDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.ui.PlatformUI;

/**
 * Class intended to verify/validate a set of provided PHP executables. In case
 * of any problems, the appropriate error message will be shown.
 * 
 * @author Bartlomiej Laczkowski
 */
public class PHPExeVerifier extends Job {

	private static final String WIN_VC_DOWNLOAD = "http://www.microsoft.com/en-us/download/details.aspx?id=30679"; //$NON-NLS-1$

	private final PHPexeItem[] exeItems;

	private PHPExeVerifier(PHPexeItem[] exeItems) {
		super(""); //$NON-NLS-1$
		setSystem(true);
		setUser(false);
		this.exeItems = exeItems;
	}

	private static class ErrorDialog extends MessageDialog {

		private PHPexeItem exeItem;

		public ErrorDialog(PHPexeItem exeItem) {
			super(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
					Messages.PHPExeVerifier_PHP_executable_verification, null, null, MessageDialog.ERROR,
					new String[] { "OK" }, 0); //$NON-NLS-1$
			this.exeItem = exeItem;
		}

		@Override
		protected Control createMessageArea(Composite composite) {
			Image image = getImage();
			if (image != null) {
				imageLabel = new Label(composite, SWT.NULL);
				image.setBackground(imageLabel.getBackground());
				imageLabel.setImage(image);
				GridDataFactory.fillDefaults().align(SWT.CENTER, SWT.BEGINNING).applyTo(imageLabel);
			}
			Composite descriptionComposite = new Composite(composite, SWT.NONE);
			GridLayout layout = new GridLayout(1, true);
			layout.marginHeight = 0;
			layout.marginWidth = 0;
			layout.verticalSpacing = 10;
			descriptionComposite.setLayout(layout);
			// create message
			Link messageError = new Link(descriptionComposite, SWT.WRAP);
			messageError.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			messageError.setText(MessageFormat.format(Messages.PHPExeVerifier_Unable_to_verify_PHP_exe_error_message,
					exeItem.getName()));
			messageError.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					PHPExeEditDialog dialog = new PHPExeEditDialog(getShell(), exeItem,
							PHPexes.getInstance().getAllItems());
					if (dialog.open() != Window.OK) {
						return;
					}
					PHPexes.getInstance().save();
				};
			});
			GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING).grab(true, false)
					.hint(convertHorizontalDLUsToPixels(IDialogConstants.MINIMUM_MESSAGE_AREA_WIDTH), SWT.DEFAULT)
					.applyTo(messageError);

			Link messageReason = new Link(descriptionComposite, SWT.WRAP);
			messageReason.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			messageReason.setText(Messages.PHPExeVerifier_Unable_to_verify_PHP_exe_reason_message);
			messageReason.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					try {
						PlatformUI.getWorkbench().getBrowserSupport().getExternalBrowser()
								.openURL(new URL(WIN_VC_DOWNLOAD));
					} catch (Exception ex) {
					}
				};
			});
			GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING).grab(true, false)
					.hint(convertHorizontalDLUsToPixels(IDialogConstants.MINIMUM_MESSAGE_AREA_WIDTH), SWT.DEFAULT)
					.applyTo(messageReason);

			return composite;
		}

	}

	/**
	 * Verifies provided PHP executables.
	 * 
	 * @param exeItems
	 */
	public static void verify(PHPexeItem... exeItems) {
		(new PHPExeVerifier(exeItems)).schedule();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.jobs.Job#run(org.eclipse.core.runtime.
	 * IProgressMonitor)
	 */
	@Override
	protected IStatus run(IProgressMonitor monitor) {
		monitor.beginTask(Messages.PHPExeVerifier_Verifying_PHP_exes, exeItems.length);
		for (int i = 0; i < exeItems.length; i++) {
			verify(exeItems[i]);
			monitor.worked(1);
		}
		monitor.done();
		return Status.OK_STATUS;
	}

	private void verify(final PHPexeItem exeItem) {
		/*
		 * Check if PHP executable version info can be fetched in Win OS. If the
		 * data can not be fetched, then it probably means that Microsoft Visual
		 * C++ Redistributables are not installed.
		 */
		if (Platform.getOS().equals(Platform.OS_WIN32)) {
			File exeLocation = exeItem.getExecutable();
			if (exeLocation == null || !exeLocation.exists() || !exeLocation.getName().contains("php")) { //$NON-NLS-1$
				return;
			}
			boolean valid = true;
			try {
				String version = PHPExeUtil.fetchVersion(exeLocation);
				if (version == null || version.isEmpty()) {
					valid = false;
				}
			} catch (IOException e) {
				valid = false;
				Logger.logException("Failed to verify PHP executable: ", e); //$NON-NLS-1$
			}
			if (!valid) {
				PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
					@Override
					public void run() {
						(new ErrorDialog(exeItem)).open();
					}
				});
			}
		}
	}

}
