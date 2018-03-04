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
 * HTML report wizard first page.
 */
public class HTMLReportWizardFirstPage extends WizardPage {

	private ProfilerDB fSession;
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
	private Button fCommonBtn;
	private Button fExecutionStatisticsBtn;
	private Button fExecutionFlowBtn;
	private boolean fExportCommonInfo;
	private boolean fExportExecutionStatistics;
	private boolean fExportExecutionFlow;

	public HTMLReportWizardFirstPage(ProfilerDB session) {
		super(PHPProfileUIMessages.getString("HTMLReportWizardPage1.0")); //$NON-NLS-1$
		setTitle(PHPProfileUIMessages.getString("HTMLReportWizardPage1.1")); //$NON-NLS-1$
		setDescription(PHPProfileUIMessages.getString("HTMLReportWizardPage1.16")); //$NON-NLS-1$
		fSession = session;
	}

	public ProfilerDB getSession() {
		return fSession;
	}

	public String getTargetFile() {
		return fTargetFile;
	}

	public boolean isExportCommonInfo() {
		return fExportCommonInfo;
	}

	public boolean isExportExecutionStatistics() {
		return fExportExecutionStatistics;
	}

	public boolean isExportExecutionFlow() {
		return fExportExecutionFlow;
	}

	private void createSelectSessionGroup(Composite parent) {
		Label label = new Label(parent, SWT.NONE);
		label.setText(PHPProfileUIMessages.getString("HTMLReportWizardPage1.2")); //$NON-NLS-1$

		fTableViewer = new TableViewer(parent, SWT.BORDER | SWT.SINGLE);
		GridData gridData = new GridData(GridData.FILL_BOTH);
		gridData.heightHint = convertHeightInCharsToPixels(8);
		fTableViewer.getControl().setLayoutData(gridData);
		fTableViewer.setContentProvider(new ProfileSessionsContentProvider());
		fTableViewer.setLabelProvider(new ProfileSessionsLabelProvider());

		fTableViewer.setInput(ProfileSessionsManager.getSessions());

		if (fSession != null) {
			fTableViewer.setSelection(new StructuredSelection(fSession));
		}
	}

	private void createConfigureGroup(Composite parent) {
		Group group = new Group(parent, SWT.NONE);
		group.setText(PHPProfileUIMessages.getString("HTMLReportWizardPage1.3")); //$NON-NLS-1$
		GridLayout layout = new GridLayout();
		layout.marginHeight = 10;
		layout.marginWidth = 10;
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		group.setLayout(layout);
		group.setLayoutData(data);

		fCommonBtn = new Button(group, SWT.CHECK);
		fCommonBtn.setText(PHPProfileUIMessages.getString("HTMLReportWizardPage1.4")); //$NON-NLS-1$
		fCommonBtn.setSelection(true);
		fCommonBtn.setEnabled(false);

		fExecutionStatisticsBtn = new Button(group, SWT.CHECK);
		fExecutionStatisticsBtn.setText(PHPProfileUIMessages.getString("HTMLReportWizardPage1.5")); //$NON-NLS-1$
		fExecutionStatisticsBtn.setSelection(true);

		fExecutionFlowBtn = new Button(group, SWT.CHECK);
		fExecutionFlowBtn.setText(PHPProfileUIMessages.getString("HTMLReportWizardPage1.6")); //$NON-NLS-1$
		fExecutionFlowBtn.setSelection(true);
	}

	private void createTargetFileGroup(Composite parent) {
		Group group = new Group(parent, SWT.NONE);
		group.setText(PHPProfileUIMessages.getString("HTMLReportWizardPage1.7")); //$NON-NLS-1$
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.marginHeight = 10;
		layout.marginWidth = 10;
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		group.setLayout(layout);
		group.setLayoutData(data);

		Label label = new Label(group, SWT.NONE);
		label.setText(PHPProfileUIMessages.getString("HTMLReportWizardPage1.8")); //$NON-NLS-1$
		data = new GridData();
		data.horizontalSpan = 2;
		label.setLayoutData(data);

		fTargetFileField = new Text(group, SWT.BORDER);
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalIndent = convertWidthInCharsToPixels(2);
		fTargetFileField.setLayoutData(data);
		fTargetFileField.addListener(SWT.Modify, fieldModifyListener);

		fTargetBtn = new Button(group, SWT.NONE);
		fTargetBtn.setText(PHPProfileUIMessages.getString("HTMLReportWizardPage1.9")); //$NON-NLS-1$
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
				dialog.setText(PHPProfileUIMessages.getString("HTMLReportWizardPage1.10")); //$NON-NLS-1$
				dialog.setFilterExtensions(new String[] { "*.html" }); //$NON-NLS-1$

				String dirName = new File(fTargetFileField.getText().trim()).getParent();
				if (dirName != null && dirName.length() > 0 && new File(dirName).exists()) {
					dialog.setFilterPath(new Path(dirName).toOSString());
				}

				String selectedFile = dialog.open();
				if (selectedFile != null) {
					int dotIndex = selectedFile.lastIndexOf('.');
					if (dotIndex == -1) {
						selectedFile += ".html"; //$NON-NLS-1$
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
		createConfigureGroup(composite);
		createTargetFileGroup(composite);

		setPageComplete(validatePage());

		setErrorMessage(null);
		setMessage(null);

		setControl(composite);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(getControl(), IPHPHelpContextIds.EXPORTING_HTML_REPORT);
	}

	protected boolean validatePage() {
		IStructuredSelection selection = (IStructuredSelection) fTableViewer.getSelection();
		Object element = selection.getFirstElement();
		if (element == null) {
			setErrorMessage(null);
			setMessage(PHPProfileUIMessages.getString("HTMLReportWizardPage1.11")); //$NON-NLS-1$
			return false;
		}
		fSession = (ProfilerDB) element;

		fExportCommonInfo = fCommonBtn.getSelection();
		fExportExecutionStatistics = fExecutionStatisticsBtn.getSelection();
		fExportExecutionFlow = fExecutionFlowBtn.getSelection();

		String targetFile = fTargetFileField.getText().trim();
		if (targetFile.length() == 0) {
			setErrorMessage(null);
			setMessage(PHPProfileUIMessages.getString("HTMLReportWizardPage1.12")); //$NON-NLS-1$
			return false;
		}
		File file = new File(targetFile);
		if (file.isDirectory()) {
			setMessage(null);
			setErrorMessage(PHPProfileUIMessages.getString("HTMLReportWizardPage1.13")); //$NON-NLS-1$
			return false;
		}
		File parentFile = file.getParentFile();
		if (parentFile == null) {
			setMessage(null);
			setErrorMessage(PHPProfileUIMessages.getString("HTMLReportWizardPage1.14")); //$NON-NLS-1$
			return false;
		}
		if (!parentFile.isDirectory()) {
			setMessage(null);
			setErrorMessage(NLS.bind(PHPProfileUIMessages.getString("HTMLReportWizardPage1.15"), file.getParent())); //$NON-NLS-1$
			return false;
		}
		fTargetFile = targetFile;

		setErrorMessage(null);
		setMessage(null);
		return true;
	}
}
