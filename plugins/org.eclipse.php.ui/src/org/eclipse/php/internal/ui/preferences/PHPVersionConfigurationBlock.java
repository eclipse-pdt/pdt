/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.preferences;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.php.core.PHPCoreConstants;
import org.eclipse.php.core.preferences.CorePreferenceConstants.Keys;
import org.eclipse.php.core.project.operation.PHPCreationDataModelProvider;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.ui.preferences.IStatusChangeListener;
import org.eclipse.php.ui.util.StatusInfo;
import org.eclipse.php.ui.util.ValuedCombo;
import org.eclipse.php.ui.util.ValuedCombo.Entry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.preferences.IWorkbenchPreferenceContainer;

/**
 * PHP version configuration block preferences page.
 */
public class PHPVersionConfigurationBlock extends OptionsConfigurationBlock {

	private static final Key PREF_PHP_VERSION = getPHPCoreKey(Keys.PHP_VERSION);
	private static final Key PREF_ASP_TAGS = getPHPCoreKey(Keys.EDITOR_USE_ASP_TAGS);
	private IStatus fTaskTagsStatus;
	private ValuedCombo versionCombo;
	private Button useAspTagsButton;
    private Label nameLabel;

	public PHPVersionConfigurationBlock(IStatusChangeListener context, IProject project, IWorkbenchPreferenceContainer container) {
		super(context, project, getKeys(), container);
	}

	public void setEnabled(boolean isEnabled) {
		versionCombo.setEnabled(isEnabled);
		useAspTagsButton.setEnabled(isEnabled);
        nameLabel.setEnabled(isEnabled);
	}

	private static Key[] getKeys() {
		return new Key[] {PREF_PHP_VERSION, PREF_ASP_TAGS};
	}

    // Accessed from the PHP project Wizard
	public Control createContents(Composite parent) {
		setShell(parent.getShell());
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout());
		createVersionContent(composite);
		createUseAspTagsContent(composite);
		unpackPHPVersion();
		unpackUseAspTags();
		validateSettings(null, null, null);
		return composite;
	}

	private void createUseAspTagsContent(Composite composite) {
		useAspTagsButton = new Button(composite, SWT.CHECK);
		useAspTagsButton.setText(PHPUIMessages.Preferences_php_editor_useAspTagsAsPhp_label);
		useAspTagsButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				setUseAspTagsValue(Boolean.toString(useAspTagsButton.getSelection()));
			}
		});
	}

	private Composite createVersionContent(Composite parent) {
		Composite composite = new Composite(parent, SWT.RESIZE);
		GridLayout layout = new GridLayout(2, false);
		layout.marginLeft = 0;
		layout.marginRight = 0;
		layout.horizontalSpacing = 0;
		layout.verticalSpacing = 0;
		layout.marginHeight = 0;
		layout.marginWidth= 0;

//		layout.marginHeight = Dialog.convertVerticalDLUsToPixels(fontMetrics, IDialogConstants.VERTICAL_MARGIN);
//		layout.marginWidth = Dialog.convertHorizontalDLUsToPixels(fontMetrics, IDialogConstants.HORIZONTAL_MARGIN);
//		layout.verticalSpacing = Dialog.convertVerticalDLUsToPixels(fontMetrics, IDialogConstants.VERTICAL_SPACING);
//		layout.horizontalSpacing = Dialog.convertHorizontalDLUsToPixels(fontMetrics, IDialogConstants.HORIZONTAL_SPACING);
		composite.setLayout(layout);
		
		
		nameLabel = new Label(composite, SWT.NONE);
		nameLabel.setText(PHPUIMessages.PHPVersionComboName);

		GC gc = new GC(nameLabel);
		gc.setFont(nameLabel.getFont());
//		FontMetrics fontMetrics = gc.getFontMetrics();
		gc.dispose();



		List entryList = prepareVersionEntryList();
		versionCombo = new ValuedCombo(composite, SWT.READ_ONLY, entryList);
		versionCombo.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				String selectedValue = versionCombo.getSelectionValue();
				setPhpVersionValue(selectedValue);
			}

			public void widgetDefaultSelected(SelectionEvent e) {
			}

		});

		return composite;
	}

	protected void validateSettings(Key changedKey, String oldValue, String newValue) {
		if (changedKey != null) {
			if (PREF_PHP_VERSION.equals(changedKey)) {
				fTaskTagsStatus = validatePHPVersion();
			}else if (PREF_ASP_TAGS.equals(changedKey)) {
				fTaskTagsStatus = validatePHPVersion();
			}else {
				return;
			}
		} else {
			fTaskTagsStatus = validatePHPVersion();
		}
		fContext.statusChanged(fTaskTagsStatus);
	}

	private IStatus validatePHPVersion() {
		return new StatusInfo();
	}

	private List prepareVersionEntryList() {
		ArrayList entryList = new ArrayList();
		for (int i = 0; i < PHPCreationDataModelProvider.PHP_VERSION_DESCRIPTIONS.length; i++) {
			String description = PHPCreationDataModelProvider.PHP_VERSION_DESCRIPTIONS[i];
			String value = PHPCreationDataModelProvider.PHP_VERSION_VALUES[i];
			Entry entry = new ValuedCombo.Entry(value, description);
			entryList.add(entry);
		}
		return entryList;
	}

	private void setPhpVersionValue(String value) {
		String[] values = PHPCreationDataModelProvider.PHP_VERSION_VALUES;
		for (int i = 0; i < values.length; i++) {
			if (values[i].equals(value)) {
				versionCombo.setText(PHPCreationDataModelProvider.PHP_VERSION_DESCRIPTIONS[i]);
				setValue(PREF_PHP_VERSION, values[i]);
				validateSettings(PREF_PHP_VERSION, null, null);
				return;
			}
		}
	}

	private void setUseAspTagsValue(String value) {
		setValue(PREF_ASP_TAGS, value);
		validateSettings(PREF_ASP_TAGS, null, null);
	}

	protected String[] getFullBuildDialogStrings(boolean workspaceSettings) {
		String title= PHPUIMessages.PHPVersionConfigurationBlock_needsbuild_title; 
		String message;
		if (workspaceSettings) {
			message= PHPUIMessages.PHPVersionConfigurationBlock_needsfullbuild_message; 
		} else {
			message= PHPUIMessages.PHPVersionConfigurationBlock_needsprojectbuild_message; 
		}
		return new String[] { title, message };
//		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.internal.ui.preferences.OptionsConfigurationBlock#updateControls()
	 */
	protected void updateControls() {
		unpackPHPVersion();
		unpackUseAspTags();
	}

	private void unpackPHPVersion() {
		String currTags = getValue(PREF_PHP_VERSION);
		versionCombo.selectValue(currTags);
	}

	private void unpackUseAspTags() {
		String value = getValue(PREF_ASP_TAGS);
		useAspTagsButton.setSelection(Boolean.valueOf(value).booleanValue());
	}
    
	//   Accessed from the PHP project Wizard 
    public String getPHPVersionValue() {
        return getValue(PREF_PHP_VERSION);       
    }
    
    //  Accessed from the PHP project Wizard
    public boolean getUseAspTagsValue() {
        return getBooleanValue(PREF_ASP_TAGS);
    }

}
