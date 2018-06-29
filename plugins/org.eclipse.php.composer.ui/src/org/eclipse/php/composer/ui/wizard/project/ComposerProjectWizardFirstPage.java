/*******************************************************************************
 * Copyright (c) 2012, 2016, 2017 PDT Extension Group and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *     Kaloyan Raev - [501269] externalize strings
 *******************************************************************************/
package org.eclipse.php.composer.ui.wizard.project;

import java.util.Observable;

import org.eclipse.php.composer.ui.ComposerUIPlugin;
import org.eclipse.php.composer.ui.wizard.AbstractValidator;
import org.eclipse.php.composer.ui.wizard.AbstractWizardFirstPage;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.PlatformUI;

public class ComposerProjectWizardFirstPage extends AbstractWizardFirstPage {

	public BasicSettingsGroup settingsGroup;

	public ComposerProjectWizardFirstPage() {
		super(Messages.ComposerProjectWizardFirstPage_Name);
		setPageComplete(false);
		setTitle(Messages.ComposerProjectWizardFirstPage_Title);
		setDescription(Messages.ComposerProjectWizardFirstPage_Description);
	}

	@Override
	protected void beforeLocationGroup() {
		settingsGroup = new BasicSettingsGroup(composite, getShell());
		settingsGroup.addObserver(this);
		settingsGroup.addObserver(validator);
	}

	@Override
	protected void finishControlSetup() {
	}

	@Override
	public void initPage() {

	}

	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof BasicSettingsGroup) {
			updatePackageFromSettingsGroup(settingsGroup);
		}
	}

	@Override
	protected AbstractValidator getValidator() {
		return new Validator(this);
	}

	@Override
	protected void afterLocationGroup() {

	}

	@Override
	protected void setHelpContext(Control container) {
		PlatformUI.getWorkbench().getHelpSystem().setHelp(container,
				ComposerUIPlugin.PLUGIN_ID + "." + "help_project_wizard_basic"); //$NON-NLS-1$ //$NON-NLS-2$
	}
}
