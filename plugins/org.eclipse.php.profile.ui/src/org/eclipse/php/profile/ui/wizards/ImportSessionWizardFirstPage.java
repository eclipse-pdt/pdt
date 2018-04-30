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
import org.eclipse.jface.viewers.*;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.osgi.util.NLS;
import org.eclipse.php.internal.ui.IPHPHelpContextIds;
import org.eclipse.php.profile.ui.PHPProfileUIMessages;
import org.eclipse.php.profile.ui.ProfilerUIConstants;
import org.eclipse.php.profile.ui.ProfilerUiPlugin;
import org.eclipse.php.profile.ui.preferences.PreferenceKeys;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.PlatformUI;

/**
 * Import session wizard first page.
 */
public class ImportSessionWizardFirstPage extends WizardPage {

	private Listener fieldModifyListener = new Listener() {
		@Override
		public void handleEvent(Event e) {
			boolean valid = validatePage();
			setPageComplete(valid);
		}
	};
	private Text fSourceField;
	private Button fSourceBtn;
	private String fSourceFile;
	private int fSourceType;

	public ImportSessionWizardFirstPage() {
		super(PHPProfileUIMessages.getString("ImportSessionWizardPage1.0")); //$NON-NLS-1$
		setTitle(PHPProfileUIMessages.getString("ImportSessionWizardPage1.1")); //$NON-NLS-1$
		setDescription(PHPProfileUIMessages.getString("ImportSessionWizardPage1.7")); //$NON-NLS-1$
		setPageComplete(false);
	}

	public String getSourceFile() {
		return fSourceFile;
	}

	public int getSourceType() {
		return fSourceType;
	}

	@Override
	public void createControl(Composite parent) {
		initializeDialogUnits(parent);
		try {
			fSourceType = ProfilerUiPlugin.getDefault().getDialogSettings().getInt(PreferenceKeys.IMPORT_TYPE);
		} catch (NumberFormatException e) {
			fSourceType = ProfilerUIConstants.ECLIPSE_TYPE;
		}

		Composite composite = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		layout.verticalSpacing = 10;
		layout.numColumns = 3;
		composite.setLayout(layout);
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		composite.setFont(parent.getFont());

		Label label = new Label(composite, SWT.NONE);
		label.setText(PHPProfileUIMessages.getString("ImportSessionWizardPage1.8")); //$NON-NLS-1$
		ComboViewer typeCombo = new ComboViewer(composite, SWT.READ_ONLY);
		typeCombo.setContentProvider(ArrayContentProvider.getInstance());
		typeCombo.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(Object element) {
				if (element instanceof Integer) {
					switch (Integer.valueOf((Integer) element)) {
					case ProfilerUIConstants.ECLIPSE_TYPE:
						return PHPProfileUIMessages.getString("ImportSessionWizardPage1.9"); //$NON-NLS-1$
					case ProfilerUIConstants.XDEBUG_TYPE:
						return PHPProfileUIMessages.getString("ImportSessionWizardPage1.10"); //$NON-NLS-1$
					}
				}
				return super.getText(element);
			}
		});
		typeCombo.setInput(new Integer[] { ProfilerUIConstants.ECLIPSE_TYPE, ProfilerUIConstants.XDEBUG_TYPE });
		typeCombo.addSelectionChangedListener(new ISelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				if (event.getStructuredSelection() != null
						&& event.getStructuredSelection().getFirstElement() instanceof Integer) {
					fSourceType = (Integer) event.getStructuredSelection().getFirstElement();
					ProfilerUiPlugin.getDefault().getDialogSettings().put(PreferenceKeys.IMPORT_TYPE, fSourceType);
				}
			}
		});
		typeCombo.setSelection(new StructuredSelection(fSourceType));
		GridData gridData = new GridData();
		gridData.horizontalSpan = 2;
		typeCombo.getCombo().setLayoutData(gridData);

		label = new Label(composite, SWT.NONE);
		label.setText(PHPProfileUIMessages.getString("ImportSessionWizardPage1.2")); //$NON-NLS-1$

		fSourceField = new Text(composite, SWT.BORDER);
		fSourceField.addListener(SWT.Modify, fieldModifyListener);
		fSourceField.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		fSourceBtn = new Button(composite, SWT.NONE);
		fSourceBtn.setText(PHPProfileUIMessages.getString("ImportSessionWizardPage1.3")); //$NON-NLS-1$
		fSourceBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog dialog = new FileDialog(fSourceBtn.getShell(), SWT.OPEN);
				if (fSourceType == ProfilerUIConstants.ECLIPSE_TYPE) {
					dialog.setFilterExtensions(new String[] { "*.xml", "*.*" }); //$NON-NLS-1$ //$NON-NLS-2$
				}
				dialog.setText(PHPProfileUIMessages.getString("ImportSessionWizardPage1.4")); //$NON-NLS-1$

				String dirName = new File(fSourceField.getText().trim()).getParent();
				if (dirName != null && dirName.length() > 0 && new File(dirName).exists()) {
					dialog.setFilterPath(new Path(dirName).toOSString());
				}

				String file = dialog.open();
				if (file != null) {
					fSourceField.setText(file);
				}
			}
		});

		setPageComplete(validatePage());

		setErrorMessage(null);
		setMessage(null);

		setControl(composite);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(getControl(), IPHPHelpContextIds.IMPORTING_PROFILE_SESSIONS);
	}

	protected boolean validatePage() {
		String sourceFile = fSourceField.getText().trim();
		if (sourceFile.length() == 0) {
			setMessage(null);
			setErrorMessage(PHPProfileUIMessages.getString("ImportSessionWizardPage1.5")); //$NON-NLS-1$
			return false;
		}
		if (!new File(sourceFile).exists()) {
			setMessage(null);
			setErrorMessage(NLS.bind(PHPProfileUIMessages.getString("ImportSessionWizardPage1.6"), sourceFile)); //$NON-NLS-1$
			return false;
		}
		fSourceFile = sourceFile;

		setErrorMessage(null);
		setMessage(null);
		return true;
	}
}
