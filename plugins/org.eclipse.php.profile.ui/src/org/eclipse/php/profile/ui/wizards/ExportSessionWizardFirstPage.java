/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.profile.ui.wizards;

import java.io.File;

import org.eclipse.core.runtime.Path;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.osgi.util.NLS;
import org.eclipse.php.internal.ui.IPHPHelpContextIds;
import org.eclipse.php.profile.core.engine.ProfileSessionsManager;
import org.eclipse.php.profile.core.engine.ProfilerDB;
import org.eclipse.php.profile.ui.PHPProfileUIMessages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.PlatformUI;

/**
 * Export session wizard first page.
 */
public class ExportSessionWizardFirstPage extends WizardPage {

	private Listener fieldModifyListener = new Listener() {
		@Override
		public void handleEvent(Event e) {
			boolean valid = validatePage();
			setPageComplete(valid);
		}
	};
	private Text fTargetFileField;
	private Button fTargetBtn;
	private String fTargetFile;
	private TableViewer fTableViewer;
	private ProfilerDB fInitSession;
	private ProfilerDB[] fSessions;

	public ExportSessionWizardFirstPage(ProfilerDB session) {
		super(PHPProfileUIMessages.getString("ExportSessionWizardPage1.0")); //$NON-NLS-1$
		setTitle(PHPProfileUIMessages.getString("ExportSessionWizardPage1.1")); //$NON-NLS-1$
		setDescription(PHPProfileUIMessages.getString("ExportSessionWizardPage1.12")); //$NON-NLS-1$
		setPageComplete(false);
		fInitSession = session;
	}

	public String getTargetFile() {
		return fTargetFile;
	}

	public ProfilerDB[] getSessions() {
		return fSessions;
	}

	private void createSelectSessionGroup(Composite parent) {
		Label label = new Label(parent, SWT.NONE);
		label.setText(PHPProfileUIMessages.getString("ExportSessionWizardPage1.2")); //$NON-NLS-1$

		fTableViewer = new TableViewer(parent, SWT.BORDER | SWT.MULTI);
		GridData gridData = new GridData(GridData.FILL_BOTH);
		gridData.heightHint = convertHeightInCharsToPixels(8);
		fTableViewer.getControl().setLayoutData(gridData);
		fTableViewer.setContentProvider(new ProfileSessionsContentProvider());
		fTableViewer.setLabelProvider(new ProfileSessionsLabelProvider());

		fTableViewer.setInput(ProfileSessionsManager.getSessions());

		if (fInitSession != null) {
			fTableViewer.setSelection(new StructuredSelection(fInitSession));
		}
	}

	private void createTargetFileGroup(Composite parent) {
		Group group = new Group(parent, SWT.NONE);
		group.setText(PHPProfileUIMessages.getString("ExportSessionWizardPage1.3")); //$NON-NLS-1$
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.marginHeight = 10;
		layout.marginWidth = 10;
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		group.setLayout(layout);
		group.setLayoutData(data);

		Label label = new Label(group, SWT.NONE);
		label.setText(PHPProfileUIMessages.getString("ExportSessionWizardPage1.4")); //$NON-NLS-1$
		data = new GridData();
		data.horizontalSpan = 2;
		label.setLayoutData(data);

		fTargetFileField = new Text(group, SWT.BORDER);
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalIndent = convertWidthInCharsToPixels(2);
		fTargetFileField.setLayoutData(data);
		fTargetFileField.addListener(SWT.Modify, fieldModifyListener);

		fTargetBtn = new Button(group, SWT.NONE);
		fTargetBtn.setText(PHPProfileUIMessages.getString("ExportSessionWizardPage1.5")); //$NON-NLS-1$
		fTargetBtn.addSelectionListener(new SelectionAdapter() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org. eclipse
			 * .swt.events.SelectionEvent)
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog dialog = new FileDialog(getShell(), SWT.SAVE);
				dialog.setText(PHPProfileUIMessages.getString("ExportSessionWizardPage1.6")); //$NON-NLS-1$
				dialog.setFilterExtensions(new String[] { "*.xml" }); //$NON-NLS-1$

				String dirName = new File(fTargetFileField.getText().trim()).getParent();
				if (dirName != null && dirName.length() > 0 && new File(dirName).exists()) {
					dialog.setFilterPath(new Path(dirName).toOSString());
				}

				String selectedFile = dialog.open();
				if (selectedFile != null) {
					int dotIndex = selectedFile.lastIndexOf('.');
					if (dotIndex == -1) {
						selectedFile += ".xml"; //$NON-NLS-1$
					}
					fTargetFileField.setText(selectedFile);
				}
			}
		});
	}

	@Override
	public void createControl(Composite parent) {
		initializeDialogUnits(parent);

		Composite composite = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		layout.verticalSpacing = 10;
		composite.setLayout(layout);
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		composite.setFont(parent.getFont());

		createSelectSessionGroup(composite);
		createTargetFileGroup(composite);

		setPageComplete(validatePage());

		setErrorMessage(null);
		setMessage(null);

		setControl(composite);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(getControl(), IPHPHelpContextIds.EXPORTING_PROFILE_SESSIONS);
	}

	protected boolean validatePage() {
		IStructuredSelection selection = (IStructuredSelection) fTableViewer.getSelection();
		Object[] elements = selection.toArray();
		if (elements == null || elements.length == 0) {
			setErrorMessage(null);
			setMessage(PHPProfileUIMessages.getString("ExportSessionWizardPage1.7")); //$NON-NLS-1$
			return false;
		}
		fSessions = new ProfilerDB[elements.length];
		System.arraycopy(elements, 0, fSessions, 0, elements.length);

		String targetFile = fTargetFileField.getText().trim();
		if (targetFile.length() == 0) {
			setErrorMessage(null);
			setMessage(PHPProfileUIMessages.getString("ExportSessionWizardPage1.8")); //$NON-NLS-1$
			return false;
		}
		File file = new File(targetFile);
		if (file.isDirectory()) {
			setMessage(null);
			setErrorMessage(PHPProfileUIMessages.getString("ExportSessionWizardPage1.9")); //$NON-NLS-1$
			return false;
		}
		File parentFile = file.getParentFile();
		if (parentFile == null) {
			setMessage(null);
			setErrorMessage(PHPProfileUIMessages.getString("ExportSessionWizardPage1.10")); //$NON-NLS-1$
			return false;
		}
		if (!parentFile.isDirectory()) {
			setMessage(null);
			setErrorMessage(NLS.bind(PHPProfileUIMessages.getString("ExportSessionWizardPage1.11"), file.getParent())); //$NON-NLS-1$
			return false;
		}
		fTargetFile = targetFile;

		setErrorMessage(null);
		setMessage(null);
		return true;
	}
}
