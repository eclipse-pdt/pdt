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
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.php.internal.ui.IPHPHelpContextIds;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.util.ElementCreationProxy;
import org.eclipse.php.ui.preferences.IPHPFormatterConfigurationBlockWrapper;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.preferences.IWorkbenchPreferenceContainer;

/**
 * @author guy.g
 */
public class PHPFormatterPreferencePage extends PropertyAndPreferencePage {

	public static final String PREF_ID = "org.eclipse.php.ui.preferences.PHPFormatterPreferencePage"; //$NON-NLS-1$
	public static final String PROP_ID = "org.eclipse.php.ui.propertyPages.PHPFormatterPreferencePage"; //$NON-NLS-1$

	private IPHPFormatterConfigurationBlockWrapper fConfigurationBlock;
	private boolean hasExtensionsForPDT = false;

	public PHPFormatterPreferencePage() {
		setPreferenceStore(PHPUiPlugin.getDefault().getPreferenceStore());

		// only used when page is shown programatically
		setTitle(PHPUIMessages.PHPFormatterPreferencePage_title);
	}

	public void createControl(Composite parent) {
		IWorkbenchPreferenceContainer container = (IWorkbenchPreferenceContainer) getContainer();
		fConfigurationBlock = getFormatterPreferencesBlock();
		fConfigurationBlock.init(getNewStatusChangedListener(), getProject(),
				container);
		setDescription(fConfigurationBlock.getDescription());
		super.createControl(parent);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(parent,
				IPHPHelpContextIds.FORMATTER_PREFERENCES);
	}

	private IPHPFormatterConfigurationBlockWrapper getFormatterPreferencesBlock() {
		IPHPFormatterConfigurationBlockWrapper prefBlock = null;

		String formatterExtensionName = "org.eclipse.php.ui.phpFormatterPrefBlock"; //$NON-NLS-1$
		IConfigurationElement[] elements = Platform.getExtensionRegistry()
				.getConfigurationElementsFor(formatterExtensionName);
		for (int i = 0; i < elements.length; i++) {
			IConfigurationElement element = elements[i];
			if (element.getName().equals("block")) { //$NON-NLS-1$
				ElementCreationProxy ecProxy = new ElementCreationProxy(
						element, formatterExtensionName);
				prefBlock = (IPHPFormatterConfigurationBlockWrapper) ecProxy
						.getObject();
			}
		}

		if (prefBlock == null) {
			prefBlock = new PHPFormatterConfigurationWrapper();
		} else {
			hasExtensionsForPDT = true;
		}

		return prefBlock;
	}

	protected Control createPreferenceContent(Composite composite) {
		return fConfigurationBlock.createContents(composite);
	}

	@Override
	protected Control createContents(Composite parent) {
		Control control = super.createContents(parent);
		if (!hasExtensionsForPDT) {
			GridData data = new GridData(GridData.FILL, GridData.FILL, true,
					false);
			super.fConfigurationBlockControl.setLayoutData(data);
		}
		return control;
	}

	protected boolean hasProjectSpecificOptions(IProject project) {
		return fConfigurationBlock.hasProjectSpecificOptions(project);
	}

	protected String getPreferencePageID() {
		return PREF_ID;
	}

	protected String getPropertyPageID() {
		return PROP_ID;
	}

	protected void enableProjectSpecificSettings(
			boolean useProjectSpecificSettings) {
		if (fConfigurationBlock != null) {
			fConfigurationBlock
					.useProjectSpecificSettings(useProjectSpecificSettings);
		}
		super.enableProjectSpecificSettings(useProjectSpecificSettings);
	}

	protected void performDefaults() {
		super.performDefaults();
		if (fConfigurationBlock != null) {
			fConfigurationBlock.performDefaults();
		}
	}

	public boolean performOk() {
		if (fConfigurationBlock != null && !fConfigurationBlock.performOk()) {
			return false;
		}
		return super.performOk();
	}

	public void performApply() {
		if (fConfigurationBlock != null) {
			fConfigurationBlock.performApply();
		}
	}

	public void dispose() {
		if (fConfigurationBlock != null) {
			fConfigurationBlock.dispose();
		}
		super.dispose();
	}

	public void setElement(IAdaptable element) {
		super.setElement(element);
		setDescription(null); // no description for property page
	}

}
