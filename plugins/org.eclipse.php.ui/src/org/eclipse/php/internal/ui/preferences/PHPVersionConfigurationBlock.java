/*******************************************************************************
 * Copyright (c) 2009, 2016 IBM Corporation and others.
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
import java.util.HashMap;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.php.core.PHPVersion;
import org.eclipse.php.internal.core.preferences.CorePreferenceConstants.Keys;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.PHPUiConstants;
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
public class PHPVersionConfigurationBlock extends PHPCoreOptionsConfigurationBlock {

	private static final Key PREF_PHP_VERSION = getPHPCoreKey(Keys.PHP_VERSION);
	private static final Key PREF_ASP_TAGS = getPHPCoreKey(Keys.EDITOR_USE_ASP_TAGS);
	private static final Key PREF_SHORT_TAGS = getPHPCoreKey(Keys.EDITOR_USE_SHORT_TAGS);
	private static HashMap<String, String> phpVersionNames;
	private IStatus fTaskTagsStatus;
	protected ValuedCombo versionCombo;
	protected Button useShortTagsButton;
	protected Label nameLabel;

	protected PHPVersion minimumVersion = null;

	private boolean hideShortTags;

	public PHPVersionConfigurationBlock(IStatusChangeListener context, IProject project,
			IWorkbenchPreferenceContainer container) {
		super(context, project, getKeys(), container);
	}

	public PHPVersionConfigurationBlock(IStatusChangeListener context, IProject project,
			IWorkbenchPreferenceContainer container, boolean hideShortTags) {
		this(context, project, container);
		this.hideShortTags = hideShortTags;
	}

	public void setMinimumVersion(PHPVersion version) {
		this.minimumVersion = version;
	}

	public void setEnabled(boolean isEnabled) {
		versionCombo.setEnabled(isEnabled);
		if (!hideShortTags) {
			useShortTagsButton.setEnabled(isEnabled);
		}
		nameLabel.setEnabled(isEnabled);
	}

	private static Key[] getKeys() {
		return new Key[] { PREF_PHP_VERSION, PREF_SHORT_TAGS };
	}

	// Accessed from the PHP project Wizard
	@Override
	public Control createContents(Composite parent) {
		setShell(parent.getShell());
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		composite.setLayout(layout);
		createVersionContent(composite);
		unpackPHPVersion();
		if (!hideShortTags) {
			createUseShortTagsContent(composite);
			unpackUseShortTags();
		}
		validateSettings(null, null, null);
		return composite;
	}

	private void createUseShortTagsContent(Composite composite) {
		useShortTagsButton = new Button(composite, SWT.CHECK | SWT.RIGHT);
		useShortTagsButton.setText(PHPUIMessages.Preferences_php_editor_useShortTagsAsPhp_label);
		useShortTagsButton.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				setUseShortTagsValue(Boolean.toString(useShortTagsButton.getSelection()));
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

		composite.setLayout(layout);

		nameLabel = new Label(composite, SWT.NONE);
		nameLabel.setText(PHPUIMessages.PHPVersionComboName);

		GC gc = new GC(nameLabel);
		gc.setFont(nameLabel.getFont());
		// FontMetrics fontMetrics = gc.getFontMetrics();
		gc.dispose();

		List<Entry> entryList = prepareVersionEntryList();
		versionCombo = new ValuedCombo(composite, SWT.READ_ONLY, entryList);
		versionCombo.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String selectedValue = versionCombo.getSelectionValue();
				setPhpVersionValue(selectedValue);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}

		});

		return composite;
	}

	@Override
	protected void validateSettings(Key changedKey, String oldValue, String newValue) {
		if (!areSettingsEnabled()) {
			return;
		}

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

	private List<Entry> prepareVersionEntryList() {
		ArrayList<Entry> entryList = new ArrayList<Entry>();
		initPHPVersionNames();
		for (PHPVersion version : PHPVersion.supportedVersions()) {
			if (minimumVersion != null && version.isLessThan(minimumVersion)) {
				continue;
			}
			String description = version.getAlias();
			if (phpVersionNames.containsKey(version.getAlias())) {
				description = phpVersionNames.get(version.getAlias());
			}
			Entry entry = new ValuedCombo.Entry(version.getAlias(), description);
			entryList.add(entry);
		}
		return entryList;
	}

	private static void initPHPVersionNames() {
		if (phpVersionNames == null) {
			IConfigurationElement[] configurationElementsFor = Platform.getExtensionRegistry()
					.getConfigurationElementsFor(PHPUiConstants.PHP_VERSION_INFO_EXTPOINT_ID);
			phpVersionNames = new HashMap<String, String>();
			for (IConfigurationElement verInfo : configurationElementsFor) {
				StringBuilder sb = new StringBuilder(verInfo.getAttribute(PHPUiConstants.PHP_VERSION_INFO_ATTR_NAME));
				IConfigurationElement[] list = verInfo.getChildren(PHPUiConstants.PHP_VERSION_INFO_FEATURE_NAME);
				if (list.length > 0) {
					sb.append(" ("); //$NON-NLS-1$
					int length = 0;
					for (int f = 0; list.length > f; f++) {
						if (length > 30) {
							break;
						}
						if (f > 0) {
							sb.append(", ");
							length += 2;
						}
						String feature = list[f].getAttribute(PHPUiConstants.PHP_VERSION_INFO_ATTR_NAME);
						sb.append(feature);
						length += feature.length();
					}

					sb.append(",...)"); //$NON-NLS-1$
				}

				phpVersionNames.put(verInfo.getAttribute(PHPUiConstants.PHP_VERSION_INFO_ATTR_VERSION), sb.toString());
			}
		}
	}

	private void setPhpVersionValue(String value) {
		for (PHPVersion version : PHPVersion.supportedVersions()) {
			if (version.getAlias().equals(value)) {
				setValue(PREF_PHP_VERSION, version.getAlias());
				validateSettings(PREF_PHP_VERSION, null, null);
				versionCombo.selectValue(value);
				return;
			}
		}
	}

	private void setUseShortTagsValue(String value) {
		setValue(PREF_SHORT_TAGS, value);
		validateSettings(PREF_SHORT_TAGS, null, null);
	}

	@Override
	protected String[] getFullBuildDialogStrings(boolean workspaceSettings) {
		String title = PHPUIMessages.PHPVersionConfigurationBlock_needsbuild_title;
		String message;
		if (workspaceSettings) {
			message = PHPUIMessages.PHPVersionConfigurationBlock_needsfullbuild_message;
		} else {
			message = PHPUIMessages.PHPVersionConfigurationBlock_needsprojectbuild_message;
		}
		return new String[] { title, message };
		// return null;
	}

	@Override
	protected void updateControls() {
		unpackPHPVersion();
		if (!hideShortTags) {
			unpackUseShortTags();
		}
	}

	private void unpackPHPVersion() {
		String currTags = getValue(PREF_PHP_VERSION);
		boolean wasValueSelected = versionCombo.selectValue(currTags);
		if (!wasValueSelected && !versionCombo.getEntryList().isEmpty()) {
			// https://bugs.eclipse.org/bugs/show_bug.cgi?id=493263
			// In case the value could not be set (for example for an
			// invalid or an obsolete PHP version), we force the selection
			// of the top-most value of versionCombo, so it will later
			// trigger a project rebuild if the user validates this value.
			setPhpVersionValue(versionCombo.getEntryList().get(0).getValue());
		}
	}

	private void unpackUseShortTags() {
		String value = getValue(PREF_SHORT_TAGS);
		useShortTagsButton.setSelection(Boolean.valueOf(value).booleanValue());
	}

	// Accessed from the PHP project Wizard
	public PHPVersion getPHPVersionValue() {
		return PHPVersion.byAlias(getValue(PREF_PHP_VERSION));
	}

	private PHPVersion getOldPHPVersionValue() {
		return getPHPVersionValue();
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
