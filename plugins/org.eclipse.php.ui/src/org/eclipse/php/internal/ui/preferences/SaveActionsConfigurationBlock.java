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

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.preferences.util.Key;
import org.eclipse.php.internal.ui.util.StatusInfo;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.preferences.IWorkbenchPreferenceContainer;

public class SaveActionsConfigurationBlock extends
		PHPCoreOptionsConfigurationBlock {

	private static final int INDENT_VALUE = 20;
	private static final Key PREF_REMOVE_TRAILING_WHITESPACES = new Key(
			PHPUiPlugin.ID,
			PreferenceConstants.FORMAT_REMOVE_TRAILING_WHITESPACES);
	private static final Key PREF_REMOVE_TRAILING_WHITESPACES_ALL = new Key(
			PHPUiPlugin.ID,
			PreferenceConstants.FORMAT_REMOVE_TRAILING_WHITESPACES_ALL);
	private static final Key PREF_REMOVE_TRAILING_WHITESPACES_IGNORE_EMPTY = new Key(
			PHPUiPlugin.ID,
			PreferenceConstants.FORMAT_REMOVE_TRAILING_WHITESPACES_IGNORE_EMPTY);

	private Button doCleanupCheckbox;
	private Button all;
	private Button ignoreEmpty;

	private boolean fRemoveWhitespaces;
	private boolean fAllLines;
	private boolean fIgnoreEmptyLines;

	private IStatus fSaveActionsStatus;
	private Group removeWhitespacesGroup;

	public SaveActionsConfigurationBlock(IStatusChangeListener context,
			IProject project, IWorkbenchPreferenceContainer container) {
		super(context, project, getKeys(), container);
		fSaveActionsStatus = new StatusInfo();
	}

	private static Key[] getKeys() {
		return new Key[] { PREF_REMOVE_TRAILING_WHITESPACES,
				PREF_REMOVE_TRAILING_WHITESPACES_ALL,
				PREF_REMOVE_TRAILING_WHITESPACES_IGNORE_EMPTY };
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

		// GridData
		GridData data = new GridData(SWT.FILL, SWT.TOP, true, false);

		whiteSpacesComposite.setLayoutData(data);

		doCleanupCheckbox = new Button(whiteSpacesComposite, SWT.CHECK);
		doCleanupCheckbox
				.setText(PHPUIMessages.SaveActionsConfigurationBlock_0); 
		doCleanupCheckbox.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
			}

			public void widgetSelected(SelectionEvent e) {
				fRemoveWhitespaces = !fRemoveWhitespaces;
				updateValues();
			}
		});
		removeWhitespacesGroup = createGroup(2, whiteSpacesComposite, ""); //$NON-NLS-1$
		// indent the radio buttons group
		data = new GridData();
		data.horizontalIndent += INDENT_VALUE;
		removeWhitespacesGroup.setLayoutData(data);

		all = new Button(removeWhitespacesGroup, SWT.RADIO);
		all.setText(PHPUIMessages.SaveActionsConfigurationBlock_2); 
		all.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
			}

			public void widgetSelected(SelectionEvent e) {
				fAllLines = true;
				fIgnoreEmptyLines = false;
				updateValues();
			}
		});
		ignoreEmpty = new Button(removeWhitespacesGroup, SWT.RADIO);
		ignoreEmpty.setText(PHPUIMessages.SaveActionsConfigurationBlock_3); 
		ignoreEmpty.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
			}

			public void widgetSelected(SelectionEvent e) {
				fAllLines = false;
				fIgnoreEmptyLines = true;
				updateValues();
			}
		});
		initValues();
		updateValues();

		return whiteSpacesComposite;
	}

	protected void updateControls() {
		initValues();
		updateValues();
	}

	private void initValues() {
		String remove = getValue(PREF_REMOVE_TRAILING_WHITESPACES);
		String all = getValue(PREF_REMOVE_TRAILING_WHITESPACES_ALL);
		String ignore = getValue(PREF_REMOVE_TRAILING_WHITESPACES_IGNORE_EMPTY);

		fRemoveWhitespaces = Boolean.valueOf(remove).booleanValue();
		fAllLines = Boolean.valueOf(all).booleanValue();
		fIgnoreEmptyLines = Boolean.valueOf(ignore).booleanValue();
	}

	private void updateValues() {
		doCleanupCheckbox.setSelection(fRemoveWhitespaces);
		removeWhitespacesGroup.setEnabled(fRemoveWhitespaces);
		if (fRemoveWhitespaces) {
			all.setEnabled(true);
			ignoreEmpty.setEnabled(true);
			all.setSelection(fAllLines);
			ignoreEmpty.setSelection(fIgnoreEmptyLines);
		}
	}

	/*
	 * Convenience method to create a group
	 */
	protected Group createGroup(int numColumns, Composite parent, String text) {
		final Group group = new Group(parent, SWT.NONE);
		group.setFont(parent.getFont());
		group.setLayoutData(createGridData(numColumns,
				GridData.FILL_HORIZONTAL, SWT.DEFAULT));

		final GridLayout layout = new GridLayout(numColumns, false);
		group.setLayout(layout);
		group.setText(text);
		return group;
	}

	/*
	 * Convenience method to create a GridData.
	 */
	protected static GridData createGridData(int numColumns, int style,
			int widthHint) {
		final GridData gd = new GridData(style);
		gd.horizontalSpan = numColumns;
		gd.widthHint = widthHint;
		return gd;
	}

	@Override
	protected String[] getFullBuildDialogStrings(boolean workspaceSettings) {
		return null;
	}

	@Override
	protected void validateSettings(Key changedKey, String oldValue,
			String newValue) {
		if (changedKey != null) {
		} else {
			fSaveActionsStatus = new StatusInfo();
		}

		fContext.statusChanged(fSaveActionsStatus);
	}

	private void savePreferences() {
		setValue(PREF_REMOVE_TRAILING_WHITESPACES, fRemoveWhitespaces);
		setValue(PREF_REMOVE_TRAILING_WHITESPACES_ALL, fAllLines);
		setValue(PREF_REMOVE_TRAILING_WHITESPACES_IGNORE_EMPTY,
				fIgnoreEmptyLines);
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

}
