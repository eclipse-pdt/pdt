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
import org.eclipse.php.internal.ui.IPHPHelpContextIds;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.util.StatusUtil;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.preferences.IWorkbenchPreferenceContainer;

/*
 * The page to configure the code templates.
 */
public class PHPCodeTemplatePreferencePage extends PropertyAndPreferencePage {

	public static final String PREF_ID = "org.eclipse.php.ui.preferences.PHPCodeTemplatePreferencePage"; //$NON-NLS-1$
	public static final String PROP_ID = "org.eclipse.php.ui.propertyPages.PHPCodeTemplatePreferencePage"; //$NON-NLS-1$

	public static final String DATA_SELECT_TEMPLATE = "CodeTemplatePreferencePage.select_template"; //$NON-NLS-1$

	private PHPCodeTemplateBlock fCodeTemplateConfigurationBlock;

	public PHPCodeTemplatePreferencePage() {
		setDescription(PHPUIMessages.CodeTemplatesPreferencePage_title); 
	}

	/*
	 * @see PreferencePage#createControl(Composite)
	 */
	public void createControl(Composite parent) {
		IWorkbenchPreferenceContainer container = (IWorkbenchPreferenceContainer) getContainer();
		fCodeTemplateConfigurationBlock = new PHPCodeTemplateBlock(
				getNewStatusChangedListener(), getProject(), container);

		super.createControl(parent);
		// FIXME - HELP -
		PlatformUI.getWorkbench().getHelpSystem().setHelp(getControl(),
				IPHPHelpContextIds.TEMPLATES_PREFERENCES);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.php.internal.ui.preferences.PropertyAndPreferencePage#
	 * createPreferenceContent(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createPreferenceContent(Composite composite) {
		return fCodeTemplateConfigurationBlock.createContents(composite);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.php.internal.ui.preferences.PropertyAndPreferencePage#
	 * enableProjectSpecificSettings(boolean)
	 */
	protected void enableProjectSpecificSettings(
			boolean useProjectSpecificSettings) {
		super.enableProjectSpecificSettings(useProjectSpecificSettings);
		if (fCodeTemplateConfigurationBlock != null) {
			fCodeTemplateConfigurationBlock
					.useProjectSpecificSettings(useProjectSpecificSettings);
		}
	}

	/*
	 * @see IPreferencePage#performOk()
	 */
	public boolean performOk() {
		if (fCodeTemplateConfigurationBlock != null) {
			return fCodeTemplateConfigurationBlock
					.performOk(useProjectSettings());
		}
		return true;
	}

	/*
	 * @see PreferencePage#performDefaults()
	 */
	protected void performDefaults() {
		super.performDefaults();
		if (fCodeTemplateConfigurationBlock != null) {
			fCodeTemplateConfigurationBlock.performDefaults();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.DialogPage#dispose()
	 */
	public void dispose() {
		if (fCodeTemplateConfigurationBlock != null) {
			fCodeTemplateConfigurationBlock.dispose();
		}
		super.dispose();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.internal.ui.wizards.IStatusChangeListener#statusChanged
	 * (org.eclipse.core.runtime.IStatus)
	 */
	public void statusChanged(IStatus status) {
		setValid(!status.matches(IStatus.ERROR));
		StatusUtil.applyToStatusLine(this, status);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.preference.IPreferencePage#performCancel()
	 */
	public boolean performCancel() {
		if (fCodeTemplateConfigurationBlock != null) {
			fCodeTemplateConfigurationBlock.performCancel();
		}
		return super.performCancel();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.php.internal.ui.preferences.PropertyAndPreferencePage#
	 * hasProjectSpecificOptions(org.eclipse.core.resources.IProject)
	 */
	protected boolean hasProjectSpecificOptions(IProject project) {
		return fCodeTemplateConfigurationBlock
				.hasProjectSpecificOptions(project);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.php.internal.ui.preferences.PropertyAndPreferencePage#
	 * getPreferencePageID()
	 */
	protected String getPreferencePageID() {
		return PREF_ID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.php.internal.ui.preferences.PropertyAndPreferencePage#
	 * getPropertyPageID()
	 */
	protected String getPropertyPageID() {
		return PROP_ID;
	}
}
