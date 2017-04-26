/*******************************************************************************
 * Copyright (c) 2017 Alex Xu and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Alex Xu - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.preferences;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.php.internal.ui.IPHPHelpContextIds;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.preferences.IWorkbenchPreferenceContainer;

public class PHPValidationPreferencePage extends PropertyAndPreferencePage {

	public static final String PREF_ID = "org.eclipse.php.ui.preferences.PHPValidationPreferencePage"; //$NON-NLS-1$
	public static final String PROP_ID = "org.eclipse.php.ui.propertyPages.PHPValidationPreferencePage"; //$NON-NLS-1$

	private PHPValidationConfigurationBlock fConfigurationBlock;

	public PHPValidationPreferencePage() {
		setPreferenceStore(PHPUiPlugin.getDefault().getPreferenceStore());

		setTitle(PHPUIMessages.PHPInterpreterPreferencePage_title);
	}

	@Override
	public void createControl(Composite parent) {
		IWorkbenchPreferenceContainer container = (IWorkbenchPreferenceContainer) getContainer();
		fConfigurationBlock = new PHPValidationConfigurationBlock(getNewStatusChangedListener(), getProject(),
				container);

		super.createControl(parent);

		PlatformUI.getWorkbench().getHelpSystem().setHelp(parent, IPHPHelpContextIds.VALIDATION_PROPERTIES);

	}

	@Override
	protected Control createPreferenceContent(Composite composite) {
		return fConfigurationBlock.createContents(composite);
	}

	@Override
	protected boolean hasProjectSpecificOptions(IProject project) {
		return fConfigurationBlock.hasProjectSpecificOptions(project);
	}

	@Override
	protected String getPreferencePageID() {
		return PREF_ID;
	}

	@Override
	protected String getPropertyPageID() {
		return PROP_ID;
	}

	@Override
	protected void enableProjectSpecificSettings(boolean useProjectSpecificSettings) {
		if (fConfigurationBlock != null) {
			fConfigurationBlock.useProjectSpecificSettings(useProjectSpecificSettings);
		}
		super.enableProjectSpecificSettings(useProjectSpecificSettings);
	}

	@Override
	protected void performDefaults() {
		super.performDefaults();
		if (fConfigurationBlock != null) {
			fConfigurationBlock.performDefaults();
		}
	}

	@Override
	public boolean performOk() {
		if (fConfigurationBlock != null && !fConfigurationBlock.performOk()) {
			return false;
		}
		return super.performOk();
	}

	@Override
	public void performApply() {
		if (fConfigurationBlock != null) {
			fConfigurationBlock.performApply();
		}
	}

	@Override
	public void dispose() {
		if (fConfigurationBlock != null) {
			fConfigurationBlock.dispose();
		}
		super.dispose();
	}

	@Override
	public void setElement(IAdaptable element) {
		super.setElement(element);
		setDescription(null); // no description for property page
	}

}
