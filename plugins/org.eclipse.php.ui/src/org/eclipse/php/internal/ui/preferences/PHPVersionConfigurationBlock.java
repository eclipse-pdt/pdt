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
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.preferences.CorePreferenceConstants.Keys;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.preferences.util.Key;
import org.eclipse.php.internal.ui.util.StatusInfo;
import org.eclipse.php.internal.ui.util.ValuedCombo;
import org.eclipse.php.internal.ui.util.ValuedCombo.Entry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.preferences.IWorkbenchPreferenceContainer;

/**
 * PHP version configuration block preferences page.
 */
public class PHPVersionConfigurationBlock extends
		PHPCoreOptionsConfigurationBlock {

	public static final String[] PHP_VERSION_VALUES = {
			PHPVersion.PHP4.getAlias(), PHPVersion.PHP5.getAlias(),
			PHPVersion.PHP5_3.getAlias() };

	public static final String[] PHP_VERSION_DESCRIPTIONS = {
			PHPUIMessages.getString("PHPCreationDataModelProvider.0"), PHPUIMessages.getString("PHPCreationDataModelProvider.1"), PHPUIMessages.getString("PHPCreationDataModelProvider.2") }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

	private static final Key PREF_PHP_VERSION = getPHPCoreKey(Keys.PHP_VERSION);
	private static final Key PREF_ASP_TAGS = getPHPCoreKey(Keys.EDITOR_USE_ASP_TAGS);
	private static final Key PREF_SHORT_TAGS = getPHPCoreKey(Keys.EDITOR_USE_SHORT_TAGS);
	private IStatus fTaskTagsStatus;
	protected ValuedCombo versionCombo;
	protected Button useShortTagsButton;
	protected Label nameLabel;

	public PHPVersionConfigurationBlock(IStatusChangeListener context,
			IProject project, IWorkbenchPreferenceContainer container) {
		super(context, project, getKeys(), container);
	}

	public void setEnabled(boolean isEnabled) {
		versionCombo.setEnabled(isEnabled);
		useShortTagsButton.setEnabled(isEnabled);
		nameLabel.setEnabled(isEnabled);
	}

	private static Key[] getKeys() {
		return new Key[] { PREF_PHP_VERSION, PREF_SHORT_TAGS };
	}

	// Accessed from the PHP project Wizard
	public Control createContents(Composite parent) {
		setShell(parent.getShell());
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		composite.setLayout(layout);
		createVersionContent(composite);
		createUseShortTagsContent(composite);
		unpackPHPVersion();
		unpackUseShortTags();
		validateSettings(null, null, null);
		return composite;
	}

	private void createUseShortTagsContent(Composite composite) {
		useShortTagsButton = new Button(composite, SWT.CHECK | SWT.RIGHT);
		useShortTagsButton.setText(PHPUIMessages
				.getString("Preferences_php_editor_useShortTagsAsPhp_label"));
		useShortTagsButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				setUseShortTagsValue(Boolean.toString(useShortTagsButton
						.getSelection()));
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
		layout.marginWidth = 0;

		// layout.marginHeight = Dialog.convertVerticalDLUsToPixels(fontMetrics,
		// IDialogConstants.VERTICAL_MARGIN);
		// layout.marginWidth =
		// Dialog.convertHorizontalDLUsToPixels(fontMetrics,
		// IDialogConstants.HORIZONTAL_MARGIN);
		// layout.verticalSpacing =
		// Dialog.convertVerticalDLUsToPixels(fontMetrics,
		// IDialogConstants.VERTICAL_SPACING);
		// layout.horizontalSpacing =
		// Dialog.convertHorizontalDLUsToPixels(fontMetrics,
		// IDialogConstants.HORIZONTAL_SPACING);
		composite.setLayout(layout);

		nameLabel = new Label(composite, SWT.NONE);
		nameLabel.setText(PHPUIMessages.getString("PHPVersionComboName"));

		GC gc = new GC(nameLabel);
		gc.setFont(nameLabel.getFont());
		// FontMetrics fontMetrics = gc.getFontMetrics();
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

	protected void validateSettings(Key changedKey, String oldValue,
			String newValue) {
		if (changedKey != null) {
			if (PREF_PHP_VERSION.equals(changedKey)) {
				fTaskTagsStatus = validatePHPVersion();
			} else if (PREF_SHORT_TAGS.equals(changedKey)) {
				fTaskTagsStatus = validatePHPVersion();
			} else {
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
		for (int i = 0; i < PHP_VERSION_DESCRIPTIONS.length; i++) {
			String description = PHP_VERSION_DESCRIPTIONS[i];
			String value = PHP_VERSION_VALUES[i];
			Entry entry = new ValuedCombo.Entry(value, description);
			entryList.add(entry);
		}
		return entryList;
	}

	private void setPhpVersionValue(String value) {
		String[] values = PHP_VERSION_VALUES;
		for (int i = 0; i < values.length; i++) {
			if (values[i].equals(value)) {
				versionCombo.setText(PHP_VERSION_DESCRIPTIONS[i]);
				setValue(PREF_PHP_VERSION, values[i]);
				validateSettings(PREF_PHP_VERSION, null, null);
				return;
			}
		}
	}

	private void setUseShortTagsValue(String value) {
		setValue(PREF_SHORT_TAGS, value);
		validateSettings(PREF_SHORT_TAGS, null, null);
	}

	protected String[] getFullBuildDialogStrings(boolean workspaceSettings) {
		String title = PHPUIMessages
				.getString("PHPVersionConfigurationBlock_needsbuild_title");
		String message;
		if (workspaceSettings) {
			message = PHPUIMessages
					.getString("PHPVersionConfigurationBlock_needsfullbuild_message");
		} else {
			message = PHPUIMessages
					.getString("PHPVersionConfigurationBlock_needsprojectbuild_message");
		}
		return new String[] { title, message };
		// return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.jdt.internal.ui.preferences.OptionsConfigurationBlock#
	 * updateControls()
	 */
	protected void updateControls() {
		unpackPHPVersion();
		unpackUseShortTags();
	}

	private void unpackPHPVersion() {
		String currTags = getValue(PREF_PHP_VERSION);
		versionCombo.selectValue(currTags);
	}

	private void unpackUseShortTags() {
		String value = getValue(PREF_SHORT_TAGS);
		useShortTagsButton.setSelection(Boolean.valueOf(value).booleanValue());
	}

	// Accessed from the PHP project Wizard
	public PHPVersion getPHPVersionValue() {
		return PHPVersion.byAlias(getValue(PREF_PHP_VERSION));
	}

	// Accessed from the PHP project Wizard
	public boolean getUseShortTagsValue() {
		return getBooleanValue(PREF_SHORT_TAGS);
	}

	// Accessed from the PHP project Wizard
	public boolean getUseAspTagsValue() {
		return getBooleanValue(PREF_ASP_TAGS);
	}
}
