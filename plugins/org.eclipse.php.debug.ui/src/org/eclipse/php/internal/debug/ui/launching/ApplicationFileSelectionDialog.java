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
package org.eclipse.php.internal.debug.ui.launching;

import org.eclipse.core.runtime.Preferences;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.php.internal.debug.ui.PHPDebugUIPlugin;
import org.eclipse.php.internal.debug.ui.model.ExtendedWorkbenchContentProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;

//import com.ibm.mrclean.project.FlexibleProjectUtils;

public class ApplicationFileSelectionDialog extends ElementTreeSelectionDialog {

	private static final String SHOW_EXTERNAL_FILES = "ApplicationFileSelectionDialog_showExternalFiles"; //$NON-NLS-1$
	protected String[] fExtensions;
	protected String[] fRequiredNatures;
	private Button fExternalFilesBt;
	private boolean fAllowExternalFiles;
	private Preferences fStore;

	/**
	 * FilteredFileSelectionDialog constructor comment.
	 * 
	 * @param parent
	 *            Shell
	 * @param title
	 *            String
	 * @param message
	 *            String
	 * @parent extensions String[]
	 * @param allowMultiple
	 *            boolean
	 * @param allowExternalFiles
	 *            Allows selection from an external files that are currently
	 *            opened in the editor
	 */
	public ApplicationFileSelectionDialog(Shell parent,
			ILabelProvider labelProvider, String title, String message,
			String[] extensions, String[] requiredNatures,
			boolean allowMultiple, boolean allowExternalFiles) {
		super(parent, labelProvider, new ExtendedWorkbenchContentProvider(
				allowExternalFiles));
		this.fAllowExternalFiles = allowExternalFiles;
		setShellStyle(SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.RESIZE);
		setTitle(title);
		if (title == null)
			setTitle(Messages.ApplicationFileSelectionDialog_2); 
		if (message == null)
			message = Messages.ApplicationFileSelectionDialog_1; 
		setMessage(message);
		setAllowMultiple(allowMultiple);

		if (extensions != null) {
			addFilter(new ApplicationFileViewerFilter(requiredNatures,
					extensions));
		}
		PHPDebugUIPlugin debugPlugin = PHPDebugUIPlugin.getDefault();
		if (debugPlugin != null) {
			fStore = debugPlugin.getPluginPreferences();
		}
	}

	/**
	 * Returns an array of supported extensions.
	 * 
	 * @return
	 */
	public String[] getExtensions() {
		return fExtensions;
	}

	/**
	 * Set the supported extensions.
	 * 
	 * @param extensions
	 */
	public void setExtensions(String[] extensions) {
		fExtensions = extensions;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.dialogs.ElementTreeSelectionDialog#createDialogArea(org
	 * .eclipse.swt.widgets.Composite)
	 */
	protected Control createDialogArea(Composite parent) {
		if (!fAllowExternalFiles) {
			return super.createDialogArea(parent);
		}
		Font font = parent.getFont();
		Composite composite = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout(1, false);
		layout.marginHeight = 0;
		layout.marginLeft = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_MARGIN);
		layout.horizontalSpacing = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_SPACING);
		composite.setLayout(layout);
		composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		composite.setFont(font);

		// Attach the regular dialog area
		super.createDialogArea(composite);

		// Attach the checkbox
		fExternalFilesBt = new Button(composite, SWT.CHECK);
		fExternalFilesBt.setText(Messages.ApplicationFileSelectionDialog_0);
		GridData data = new GridData();
		data.grabExcessHorizontalSpace = true;
		data.horizontalIndent = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_MARGIN);
		fExternalFilesBt.setLayoutData(data);

		// Add a listener
		fExternalFilesBt.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				updateView(fExternalFilesBt.getSelection());
			}
		});

		// Set the current state as saved in the preferences.
		String shouldShowExternals = fStore.getString(SHOW_EXTERNAL_FILES);
		if (shouldShowExternals.length() == 0) {
			fExternalFilesBt.setSelection(true);
			updateView(true);
		} else {
			boolean show = Boolean.valueOf(shouldShowExternals).booleanValue();
			fExternalFilesBt.setSelection(show);
			updateView(show);
		}

		return composite;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.dialogs.SelectionStatusDialog#okPressed()
	 */
	protected void okPressed() {
		// Save the external files visibility state into the preferences.
		if (fExternalFilesBt != null) {
			fStore.setValue(SHOW_EXTERNAL_FILES, Boolean
					.toString(fExternalFilesBt.getSelection()));
		}
		super.okPressed();
	}

	/*
	 * Update the tree view.
	 */
	private void updateView(boolean showExternalFiles) {
		((ExtendedWorkbenchContentProvider) getTreeViewer()
				.getContentProvider())
				.setProvideExternalFiles(showExternalFiles);
		getTreeViewer().refresh();
	}
}
