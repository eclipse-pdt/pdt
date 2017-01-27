/*******************************************************************************
 * Copyright (c) 2012, 2016, 2017 PDT Extension Group and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *     Kaloyan Raev - [501269] externalize strings
 *******************************************************************************/
package org.eclipse.php.composer.ui.preferences;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.php.composer.ui.ComposerUIPlugin;
import org.eclipse.php.composer.ui.preferences.launcher.LauncherConfigurationBlock;
import org.eclipse.php.composer.ui.preferences.launcher.LauncherKeyBag;
import org.eclipse.php.internal.ui.preferences.PropertyAndPreferencePage;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.preferences.IWorkbenchPreferenceContainer;

public class ComposerPreferencePage extends PropertyAndPreferencePage {

	public static final String PREF_ID = "org.eclipse.php.composer.ui.preferences.ComposerPreferencePage"; //$NON-NLS-1$
	public static final String PROP_ID = "org.eclipse.php.composer.ui.propertyPages.ComposerPreferencePage"; //$NON-NLS-1$

	private LauncherConfigurationBlock configurationBlock;

	public ComposerPreferencePage() {
		setTitle(Messages.ComposerPreferencePage_Title);
		setDescription(null);
		setPreferenceStore(ComposerUIPlugin.getDefault().getCorePreferenceStore());

	}

	@Override
	public void createControl(Composite parent) {

		IWorkbenchPreferenceContainer container = (IWorkbenchPreferenceContainer) getContainer();
		LauncherKeyBag bag = new ComposerLauncherBag();
		configurationBlock = new ComposerConfigurationBlock(getNewStatusChangedListener(), getProject(), container,
				bag);
		super.createControl(parent);
	}

	@Override
	public void init(IWorkbench workbench) {
	}

	@Override
	public IPreferenceStore getPreferenceStore() {
		return ComposerUIPlugin.getDefault().getCorePreferenceStore();
	}

	protected void enableProjectSpecificSettings(boolean useProjectSpecificSettings) {
		if (configurationBlock != null) {
			configurationBlock.useProjectSpecificSettings(useProjectSpecificSettings);
		}
		super.enableProjectSpecificSettings(useProjectSpecificSettings);
	}

	@Override
	protected void performDefaults() {
		super.performDefaults();
		if (configurationBlock != null) {
			configurationBlock.performDefaults();
		}
	}

	@Override
	public boolean performOk() {
		if (configurationBlock != null && !configurationBlock.performOk()) {
			return false;
		}
		return super.performOk();
	}

	@Override
	public void performHelp() {
		PlatformUI.getWorkbench().getHelpSystem().setHelp(getControl(),
				ComposerUIPlugin.PLUGIN_ID + "." + "help_project_wizard_basic"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	@Override
	protected Control createPreferenceContent(Composite composite) {
		return configurationBlock.createContents(composite);
	}

	@Override
	protected boolean hasProjectSpecificOptions(IProject project) {
		return configurationBlock.hasProjectSpecificOptions(project);
	}

	@Override
	protected String getPreferencePageID() {
		return PREF_ID;
	}

	@Override
	protected String getPropertyPageID() {
		return PROP_ID;
	}

	public void dispose() {
		if (configurationBlock != null) {
			configurationBlock.dispose();
		}
		super.dispose();
	}
}
