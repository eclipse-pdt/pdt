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

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.preferences.util.Key;
import org.eclipse.php.internal.ui.util.StatusInfo;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.preferences.IWorkbenchPreferenceContainer;

public class SaveActionsConfigurationBlock extends PHPCoreOptionsConfigurationBlock {

	private static final int INDENT_VALUE = 20;
	private static final Key PREF_REMOVE_TRAILING_WHITESPACES = new Key(PHPUiPlugin.ID,
			PreferenceConstants.FORMAT_REMOVE_TRAILING_WHITESPACES);
	private static final Key PREF_REMOVE_TRAILING_WHITESPACES_ALL = new Key(PHPUiPlugin.ID,
			PreferenceConstants.FORMAT_REMOVE_TRAILING_WHITESPACES_ALL);
	private static final Key PREF_REMOVE_TRAILING_WHITESPACES_IGNORE_EMPTY = new Key(PHPUiPlugin.ID,
			PreferenceConstants.FORMAT_REMOVE_TRAILING_WHITESPACES_IGNORE_EMPTY);

	private static final Key PREF_FORMAT_ON_SAVE = new Key(PHPUiPlugin.ID, PreferenceConstants.FORMAT_ON_SAVE);

	private Button removeTrailingWsCheckbox;
	private Button allCheckbox;
	private Button ignoreEmptyCheckbox;
	private Button formatOnSaveCheckbox;

	private boolean fRemoveWhitespaces;
	private boolean fAllLines;
	private boolean fIgnoreEmptyLines;
	private boolean fFormatOnSave;

	private IStatus fSaveActionsStatus;
	private Group removeWhitespacesGroup;

	private static Key[] getKeys() {
		return new Key[] { PREF_REMOVE_TRAILING_WHITESPACES, PREF_REMOVE_TRAILING_WHITESPACES_ALL,
				PREF_REMOVE_TRAILING_WHITESPACES_IGNORE_EMPTY, PREF_FORMAT_ON_SAVE };
	}

	public SaveActionsConfigurationBlock(IStatusChangeListener context, IProject project,
			IWorkbenchPreferenceContainer container) {
		super(context, project, getKeys(), container);
		fSaveActionsStatus = new StatusInfo();
	}

	@Override
	protected Control createContents(Composite parent) {
		setShell(parent.getShell());

		Composite removeWhiteSpaceComposite = createRemoveWhiteSpacesContent(parent);

		validateSettings(null, null, null);

		return removeWhiteSpaceComposite;
	}

	private Composite createRemoveWhiteSpacesContent(Composite parent) {
		Composite whiteSpacesComposite = new Composite(parent, SWT.NONE);
		// GridLayout
		GridLayout layout = new GridLayout();
		layout.marginTop = 5;
		layout.marginBottom = 5;

		whiteSpacesComposite.setLayout(layout);
		whiteSpacesComposite.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));

		removeTrailingWsCheckbox = new Button(whiteSpacesComposite, SWT.CHECK);
		removeTrailingWsCheckbox.setText(PHPUIMessages.SaveActionsConfigurationBlock_0);
		removeTrailingWsCheckbox.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				fRemoveWhitespaces = !fRemoveWhitespaces;
				updateValues();
			}
		});
		removeWhitespacesGroup = createGroup(2, whiteSpacesComposite, ""); //$NON-NLS-1$
		// indent the radio buttons group
		GridData data = new GridData();
		data.horizontalIndent += INDENT_VALUE;
		removeWhitespacesGroup.setLayoutData(data);

		allCheckbox = new Button(removeWhitespacesGroup, SWT.RADIO);
		allCheckbox.setText(PHPUIMessages.SaveActionsConfigurationBlock_2);
		allCheckbox.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				fAllLines = true;
				fIgnoreEmptyLines = false;
				updateValues();
			}
		});
		ignoreEmptyCheckbox = new Button(removeWhitespacesGroup, SWT.RADIO);
		ignoreEmptyCheckbox.setText(PHPUIMessages.SaveActionsConfigurationBlock_3);
		ignoreEmptyCheckbox.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				fAllLines = false;
				fIgnoreEmptyLines = true;
				updateValues();
			}
		});

		formatOnSaveCheckbox = new Button(whiteSpacesComposite, SWT.CHECK);
		formatOnSaveCheckbox.setText(PHPUIMessages.SaveActionsConfigurationBlock_1);
		formatOnSaveCheckbox.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				fFormatOnSave = !fFormatOnSave;
				updateValues();
			}
		});

		updateControls();

		return whiteSpacesComposite;
	}

	@Override
	protected void updateControls() {
		initValues();
		updateValues();
	}

	private void initValues() {
		String remove = getValue(PREF_REMOVE_TRAILING_WHITESPACES);
		String all = getValue(PREF_REMOVE_TRAILING_WHITESPACES_ALL);
		String ignore = getValue(PREF_REMOVE_TRAILING_WHITESPACES_IGNORE_EMPTY);
		String format = getValue(PREF_FORMAT_ON_SAVE);

		fRemoveWhitespaces = Boolean.valueOf(remove).booleanValue();
		fAllLines = Boolean.valueOf(all).booleanValue();
		fIgnoreEmptyLines = Boolean.valueOf(ignore).booleanValue();
		fFormatOnSave = Boolean.valueOf(format).booleanValue();
	}

	private void updateValues() {
		removeTrailingWsCheckbox.setSelection(fRemoveWhitespaces);
		removeWhitespacesGroup.setEnabled(fRemoveWhitespaces);
		if (fRemoveWhitespaces) {
			allCheckbox.setSelection(fAllLines);
			ignoreEmptyCheckbox.setSelection(fIgnoreEmptyLines);
		}
		allCheckbox.setEnabled(fRemoveWhitespaces);
		ignoreEmptyCheckbox.setEnabled(fRemoveWhitespaces);
		formatOnSaveCheckbox.setSelection(fFormatOnSave);
	}

	/*
	 * Convenience method to create a group
	 */
	protected Group createGroup(int numColumns, Composite parent, String text) {
		final Group group = new Group(parent, SWT.NONE);
		group.setFont(parent.getFont());
		group.setLayoutData(GridDataFactory.swtDefaults().create());

		final GridLayout layout = new GridLayout(numColumns, false);
		group.setLayout(layout);
		group.setText(text);
		return group;
	}

	@Override
	protected void validateSettings(Key changedKey, String oldValue, String newValue) {
		if (!areSettingsEnabled()) {
			return;
		}

		if (changedKey != null) {
		} else {
			fSaveActionsStatus = new StatusInfo();
		}

		fContext.statusChanged(fSaveActionsStatus);
	}

	private void savePreferences() {
		setValue(PREF_REMOVE_TRAILING_WHITESPACES, fRemoveWhitespaces);
		setValue(PREF_REMOVE_TRAILING_WHITESPACES_ALL, fAllLines);
		setValue(PREF_REMOVE_TRAILING_WHITESPACES_IGNORE_EMPTY, fIgnoreEmptyLines);
		setValue(PREF_FORMAT_ON_SAVE, fFormatOnSave);
	}

	@Override
	public boolean performApply() {
		savePreferences();
		return super.performApply();
	}

	@Override
	public boolean performOk() {
		savePreferences();
		return super.performOk();
	}

	@Override
	protected String[] getFullBuildDialogStrings(boolean workspaceSettings) {
		return null;
	}

}
