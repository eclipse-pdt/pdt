/*******************************************************************************
 * Copyright (c) 2015 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.ui.wizards;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.php.internal.ui.wizards.CompositeFragment;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.PreferencesUtil;

/**
 * Debugger setting section for unsupported debugger types.
 * 
 * @author Bartlomiej Laczkowski
 */
@SuppressWarnings("restriction")
public class DebuggerUnsupportedSettingsSection implements
		IDebuggerSettingsSection {

	private static final String DEBUGGERS_PAGE_ID = "org.eclipse.php.debug.ui.installedDebuggersPage"; //$NON-NLS-1$

	protected CompositeFragment compositeFragment;
	protected Composite settingsComposite;

	/**
	 * Creates new unsupported settings section.
	 */
	public DebuggerUnsupportedSettingsSection(
			CompositeFragment compositeFragment) {
		this.compositeFragment = compositeFragment;
		this.settingsComposite = createComposite();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.debug.ui.wizards.IDebuggerSettingsSection#
	 * getComposite()
	 */
	@Override
	public Composite getComposite() {
		return settingsComposite;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.internal.debug.ui.wizards.IDebuggerSettingsSection#performOK
	 * ()
	 */
	@Override
	public boolean performOK() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.debug.ui.wizards.IDebuggerSettingsSection#
	 * performCancel()
	 */
	@Override
	public boolean performCancel() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.internal.debug.ui.wizards.IDebuggerSettingsSection#validate
	 * ()
	 */
	@Override
	public void validate() {
		// Reset state
		compositeFragment.setMessage(compositeFragment.getDescription(),
				IMessageProvider.NONE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.internal.debug.ui.wizards.IDebuggerSettingsSection#canTest
	 * ()
	 */
	@Override
	public boolean canTest() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.debug.ui.wizards.IDebuggerSettingsSection#
	 * performTest()
	 */
	@Override
	public void performTest() {
		// Nothing to test
	}

	private Composite createComposite() {
		// Main composite
		Composite settingsComposite = new Composite(compositeFragment, SWT.NONE);
		GridLayout scLayout = new GridLayout();
		scLayout.marginHeight = 0;
		scLayout.marginWidth = 0;
		settingsComposite.setLayout(scLayout);
		GridData scGridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		scGridData.horizontalSpan = 2;
		settingsComposite.setLayoutData(scGridData);
		Label unsupportedLabel = new Label(settingsComposite, SWT.NONE);
		unsupportedLabel
				.setText(Messages.DebuggerUnsupportedSettingsSection_Settings_unsupported_for_debugger_type);
		Link link = new Link(settingsComposite, SWT.NONE);
		link.setText(Messages.DebuggerUnsupportedSettingsSection_Check_global_settings_in_preferences);
		link.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				openLink();
			}
		});
		return settingsComposite;
	}

	private void openLink() {
		PreferenceDialog dialog = PreferencesUtil.createPreferenceDialogOn(
				PlatformUI.getWorkbench().getDisplay().getActiveShell(),
				DEBUGGERS_PAGE_ID, null, null);
		dialog.open();
	}

}
