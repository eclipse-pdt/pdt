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
package org.eclipse.php.internal.ui.preferences;

import java.util.ArrayList;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.php.internal.ui.IPHPHelpContextIds;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.util.Messages;
import org.eclipse.php.internal.ui.util.StatusInfo;
import org.eclipse.php.internal.ui.util.StatusUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.PlatformUI;

public class PHPProjectLayoutPreferencePage extends PropertyAndPreferencePage {

	public static final String PREF_ID = "org.eclipse.php.ui.preferences.PHPProjectLayoutPreferencePage"; //$NON-NLS-1$
	public static final String PROP_ID = "org.eclipse.php.ui.propertyPages.PHPProjectLayoutPreferencePage"; //$NON-NLS-1$

	private static final String SRCBIN_FOLDERS_IN_NEWPROJ = PreferenceConstants.SRCBIN_FOLDERS_IN_NEWPROJ;
	private static final String SRCBIN_SRCNAME = PreferenceConstants.SRCBIN_SRCNAME;
	private static final String SRCBIN_BINNAME = PreferenceConstants.SRCBIN_BINNAME;

	private ArrayList fRadioButtons;
	private Button fProjectAsSourceFolder;
	private Button fFoldersAsSourceFolder;

	private Label fSrcFolderNameLabel;
	private Label fBinFolderNameLabel;

	private ArrayList fTextControls;
	private Text fSrcFolderNameText;
	private Text fBinFolderNameText;

	private SelectionListener fSelectionListener;
	private ModifyListener fModifyListener;

	public PHPProjectLayoutPreferencePage() {
		setPreferenceStore(PHPUiPlugin.getDefault().getPreferenceStore());

		// only used when page is shown programatically
		// setTitle(PHPUIMessages.getString("PHPBuildPreferencePage_title"));
		setDescription(PreferencesMessages.NewPHPProjectPreferencePage_description);

		fRadioButtons = new ArrayList();
		fTextControls = new ArrayList();

		fSelectionListener = new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
			}

			public void widgetSelected(SelectionEvent e) {
				controlChanged(e.widget);
			}
		};

		fModifyListener = new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				controlModified(e.widget);
			}
		};
	}

	/*
	 * @see
	 * org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets
	 * .Composite)
	 */
	public void createControl(Composite parent) {
		super.createControl(parent);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(getControl(),
				IPHPHelpContextIds.NEW_PROJECT_LAYOUT_PREFERENCES);
	}

	private void controlModified(Widget widget) {
		if (widget == fSrcFolderNameText || widget == fBinFolderNameText) {
			validateFolders();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.jdt.internal.ui.preferences.PropertyAndPreferencePage#
	 * createPreferenceContent(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createPreferenceContent(Composite parent) {
		initializeDialogUnits(parent);

		Composite result = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.marginHeight = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_MARGIN);
		layout.marginWidth = 0;
		layout.verticalSpacing = convertVerticalDLUsToPixels(10);
		layout.horizontalSpacing = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_SPACING);
		layout.numColumns = 2;
		result.setLayout(layout);

		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;

		Group sourceFolderGroup = new Group(result, SWT.NONE);
		layout = new GridLayout();
		layout.numColumns = 2;
		sourceFolderGroup.setLayout(layout);
		sourceFolderGroup.setLayoutData(gd);
		sourceFolderGroup
				.setText(PreferencesMessages.NewPHPProjectPreferencePage_sourcefolder_label);

		int indent = 0;

		fProjectAsSourceFolder = addRadioButton(
				sourceFolderGroup,
				PreferencesMessages.NewPHPProjectPreferencePage_sourcefolder_project,
				SRCBIN_FOLDERS_IN_NEWPROJ, IPreferenceStore.FALSE, indent);
		fProjectAsSourceFolder.addSelectionListener(fSelectionListener);

		fFoldersAsSourceFolder = addRadioButton(
				sourceFolderGroup,
				PreferencesMessages.NewPHPProjectPreferencePage_sourcefolder_folder,
				SRCBIN_FOLDERS_IN_NEWPROJ, IPreferenceStore.TRUE, indent);
		fFoldersAsSourceFolder.addSelectionListener(fSelectionListener);

		indent = convertWidthInCharsToPixels(4);

		fSrcFolderNameLabel = new Label(sourceFolderGroup, SWT.NONE);
		fSrcFolderNameLabel
				.setText(PreferencesMessages.NewPHPProjectPreferencePage_folders_src);
		fSrcFolderNameText = addTextControl(sourceFolderGroup,
				fSrcFolderNameLabel, SRCBIN_SRCNAME, indent);
		fSrcFolderNameText.addModifyListener(fModifyListener);

		fBinFolderNameLabel = new Label(sourceFolderGroup, SWT.NONE);
		fBinFolderNameLabel
				.setText(PreferencesMessages.NewPHPProjectPreferencePage_folders_public);
		fBinFolderNameText = addTextControl(sourceFolderGroup,
				fBinFolderNameLabel, SRCBIN_BINNAME, indent);
		fBinFolderNameText.addModifyListener(fModifyListener);

		validateFolders();

		return result;
	}

	private Button addRadioButton(Composite parent, String label, String key,
			String value, int indent) {
		GridData gd = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		gd.horizontalSpan = 2;
		gd.horizontalIndent = indent;

		Button button = new Button(parent, SWT.RADIO);
		button.setText(label);
		button.setData(new String[] { key, value });
		button.setLayoutData(gd);

		button.setSelection(value.equals(getPreferenceStore().getString(key)));

		fRadioButtons.add(button);
		return button;
	}

	private Text addTextControl(Composite parent, Label labelControl,
			String key, int indent) {
		GridData gd = new GridData();
		gd.horizontalIndent = indent;

		labelControl.setLayoutData(gd);

		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.widthHint = convertWidthInCharsToPixels(30);

		Text text = new Text(parent, SWT.SINGLE | SWT.BORDER);
		text.setText(getPreferenceStore().getString(key));
		text.setData(key);
		text.setLayoutData(gd);

		fTextControls.add(text);
		return text;
	}

	private void validateFolders() {
		boolean useFolders = fFoldersAsSourceFolder.getSelection();

		fSrcFolderNameText.setEnabled(useFolders);
		fBinFolderNameText.setEnabled(useFolders);
		fSrcFolderNameLabel.setEnabled(useFolders);
		fBinFolderNameLabel.setEnabled(useFolders);
		if (useFolders) {
			String srcName = fSrcFolderNameText.getText();
			String binName = fBinFolderNameText.getText();
			if (srcName.length() + binName.length() == 0) {
				updateStatus(new StatusInfo(
						IStatus.ERROR,
						PreferencesMessages.NewPHPProjectPreferencePage_folders_error_namesempty));
				return;
			}
			IWorkspace workspace = PHPUiPlugin.getWorkspace();
			IProject dmy = workspace.getRoot().getProject("project"); //$NON-NLS-1$

			IStatus status;
			IPath srcPath = dmy.getFullPath().append(srcName);
			if (srcName.length() != 0) {
				status = workspace.validatePath(srcPath.toString(),
						IResource.FOLDER);
				if (!status.isOK()) {
					String message = Messages
							.format(
									PreferencesMessages.NewPHPProjectPreferencePage_folders_error_invalidsrcname,
									status.getMessage());
					updateStatus(new StatusInfo(IStatus.ERROR, message));
					return;
				}
			}
			IPath binPath = dmy.getFullPath().append(binName);
			if (binName.length() != 0) {
				status = workspace.validatePath(binPath.toString(),
						IResource.FOLDER);
				if (!status.isOK()) {
					String message = Messages
							.format(
									PreferencesMessages.NewPHPProjectPreferencePage_folders_error_invalidbinname,
									status.getMessage());
					updateStatus(new StatusInfo(IStatus.ERROR, message));
					return;
				}
			}
			// IClasspathEntry entry= JavaCore.newSourceEntry(srcPath);
			// status= JavaConventions.validateClasspath(JavaCore.create(dmy),
			// new IClasspathEntry[] { entry }, binPath);
			// if (!status.isOK()) {
			// String message=
			// PreferencesMessages.NewJavaProjectPreferencePage_folders_error_invalidcp;
			// updateStatus(new StatusInfo(IStatus.ERROR, message));
			// return;
			// }
		}
		updateStatus(new StatusInfo()); // set to OK
	}

	@Override
	protected boolean supportsProjectSpecificOptions() {
		// project specific preferences is not relevant in this case
		return false;
	}

	private void updateStatus(IStatus status) {
		setValid(!status.matches(IStatus.ERROR));
		StatusUtil.applyToStatusLine(this, status);
	}

	private void controlChanged(Widget widget) {
		if (widget == fFoldersAsSourceFolder
				|| widget == fProjectAsSourceFolder) {
			validateFolders();
		}
	}

	public static void initDefaults(IPreferenceStore store) {
		store.setDefault(SRCBIN_FOLDERS_IN_NEWPROJ, false);
		store.setDefault(SRCBIN_SRCNAME, "application"); //$NON-NLS-1$
		store.setDefault(SRCBIN_BINNAME, "public"); //$NON-NLS-1$
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.jdt.internal.ui.preferences.PropertyAndPreferencePage#
	 * hasProjectSpecificOptions(org.eclipse.core.resources.IProject)
	 */
	protected boolean hasProjectSpecificOptions(IProject project) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.jdt.internal.ui.preferences.PropertyAndPreferencePage#
	 * getPreferencePageID()
	 */
	protected String getPreferencePageID() {
		return PREF_ID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.jdt.internal.ui.preferences.PropertyAndPreferencePage#
	 * getPropertyPageID()
	 */
	protected String getPropertyPageID() {
		return PROP_ID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.jdt.internal.ui.preferences.PropertyAndPreferencePage#
	 * enableProjectSpecificSettings(boolean)
	 */
	protected void enableProjectSpecificSettings(
			boolean useProjectSpecificSettings) {
		super.enableProjectSpecificSettings(useProjectSpecificSettings);
	}

	/*
	 * @see org.eclipse.jface.preference.IPreferencePage#performDefaults()
	 */
	protected void performDefaults() {
		IPreferenceStore store = getPreferenceStore();

		for (int i = 0; i < fRadioButtons.size(); i++) {
			Button button = (Button) fRadioButtons.get(i);
			String[] info = (String[]) button.getData();
			button
					.setSelection(info[1].equals(store
							.getDefaultString(info[0])));
		}
		for (int i = 0; i < fTextControls.size(); i++) {
			Text text = (Text) fTextControls.get(i);
			String key = (String) text.getData();
			text.setText(store.getDefaultString(key));
		}

		validateFolders();
		super.performDefaults();

	}

	/*
	 * @see org.eclipse.jface.preference.IPreferencePage#performOk()
	 */
	public boolean performOk() {
		IPreferenceStore store = getPreferenceStore();
		for (int i = 0; i < fRadioButtons.size(); i++) {
			Button button = (Button) fRadioButtons.get(i);
			if (button.getSelection()) {
				String[] info = (String[]) button.getData();
				store.setValue(info[0], info[1]);
			}
		}
		for (int i = 0; i < fTextControls.size(); i++) {
			Text text = (Text) fTextControls.get(i);
			String key = (String) text.getData();
			store.setValue(key, text.getText());
		}

		PHPUiPlugin.getDefault().savePluginPreferences();
		return super.performOk();

	}

	/*
	 * @see org.eclipse.jface.preference.IPreferencePage#performApply()
	 */
	public void performApply() {
		performOk();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jdt.internal.ui.preferences.PropertyAndPreferencePage#setElement
	 * (org.eclipse.core.runtime.IAdaptable)
	 */
	public void setElement(IAdaptable element) {
		super.setElement(element);
		setDescription(null); // no description for property page
	}

}
