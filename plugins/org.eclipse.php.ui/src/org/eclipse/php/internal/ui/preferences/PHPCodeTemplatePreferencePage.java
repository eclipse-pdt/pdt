/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.preferences;

import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.php.internal.ui.preferences.PHPCodeTemplateBlock;
import org.eclipse.jface.text.templates.persistence.TemplatePersistenceData;
import org.eclipse.php.internal.ui.IPHPHelpContextIds;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.util.StatusUtil;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.preferences.IWorkbenchPreferenceContainer;

/*
 * The page to configure the code templates.
 */
public class PHPCodeTemplatePreferencePage extends PropertyAndPreferencePage {

	public static final String PREF_ID= "org.eclipse.php.ui.preferences.CodeTemplatePreferencePage"; //$NON-NLS-1$
	public static final String PROP_ID= "org.eclipse.php.ui.propertyPages.CodeTemplatePreferencePage"; //$NON-NLS-1$
	
	public static final String DATA_SELECT_TEMPLATE= "CodeTemplatePreferencePage.select_template"; //$NON-NLS-1$
	
	private PHPCodeTemplateBlock fCodeTemplateConfigurationBlock;

	public PHPCodeTemplatePreferencePage() {
		setPreferenceStore(PHPUiPlugin.getDefault().getPreferenceStore());
		setDescription(PHPUIMessages.getString("CodeTemplatesPreferencePage_title")); //$NON-NLS-1$

		// only used when page is shown programatically
		//TODO - setTitle(PreferencesMessages.CodeTemplatesPreferencePage_title);		 
	}

	/*
	 * @see PreferencePage#createControl(Composite)
	 */
	public void createControl(Composite parent) {
		IWorkbenchPreferenceContainer container= (IWorkbenchPreferenceContainer) getContainer();
		fCodeTemplateConfigurationBlock= new PHPCodeTemplateBlock(getNewStatusChangedListener(), getProject(), container);
		
		super.createControl(parent);
		//FIXME - HELP - 
		PlatformUI.getWorkbench().getHelpSystem().setHelp(getControl(), IPHPHelpContextIds.TEMPLATES_PREFERENCES);
	}	

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.ui.preferences.PropertyAndPreferencePage#createPreferenceContent(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createPreferenceContent(Composite composite) {
		return fCodeTemplateConfigurationBlock.createContents(composite);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.ui.preferences.PropertyAndPreferencePage#enableProjectSpecificSettings(boolean)
	 */
	protected void enableProjectSpecificSettings(boolean useProjectSpecificSettings) {
		super.enableProjectSpecificSettings(useProjectSpecificSettings);
		if (fCodeTemplateConfigurationBlock != null) {
			fCodeTemplateConfigurationBlock.useProjectSpecificSettings(useProjectSpecificSettings);
		}
	}
	
	/*
	 * @see IPreferencePage#performOk()
	 */
	public boolean performOk() {
		if (fCodeTemplateConfigurationBlock != null) {
			return fCodeTemplateConfigurationBlock.performOk(useProjectSettings());
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
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.DialogPage#dispose()
	 */
	public void dispose() {
		if (fCodeTemplateConfigurationBlock != null) {
			fCodeTemplateConfigurationBlock.dispose();
		}
		super.dispose();
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.ui.wizards.IStatusChangeListener#statusChanged(org.eclipse.core.runtime.IStatus)
	 */
	public void statusChanged(IStatus status) {
		setValid(!status.matches(IStatus.ERROR));
		StatusUtil.applyToStatusLine(this, status);		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.preference.IPreferencePage#performCancel()
	 */
	public boolean performCancel() {
		if (fCodeTemplateConfigurationBlock != null) {
			fCodeTemplateConfigurationBlock.performCancel();
		}
		return super.performCancel();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.ui.preferences.PropertyAndPreferencePage#hasProjectSpecificOptions(org.eclipse.core.resources.IProject)
	 */
	protected boolean hasProjectSpecificOptions(IProject project) {
		return fCodeTemplateConfigurationBlock.hasProjectSpecificOptions(project);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.ui.preferences.PropertyAndPreferencePage#getPreferencePageID()
	 */
	protected String getPreferencePageID() {
		return PREF_ID;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.ui.preferences.PropertyAndPreferencePage#getPropertyPageID()
	 */
	protected String getPropertyPageID() {
		return PROP_ID;
	}

	/*
	 * @see org.eclipse.jface.preference.PreferencePage#applyData(java.lang.Object)
	 */
	public void applyData(Object data) {
		if (data instanceof Map) {
			Object id= ((Map) data).get(DATA_SELECT_TEMPLATE);
			if (id instanceof String) {
				//final TemplatePersistenceData[] templates= fCodeTemplateConfigurationBlock.fTemplateStore.getTemplateData();
				TemplatePersistenceData template= null;
			/*	for (int index= 0; index < templates.length; index++) {
					template= templates[index];
					if (template.getId().equals(id)) {
						fCodeTemplateConfigurationBlock.postSetSelection(template);
						break;
					}
				}*/
			}
		}
		super.applyData(data);
	}
}
