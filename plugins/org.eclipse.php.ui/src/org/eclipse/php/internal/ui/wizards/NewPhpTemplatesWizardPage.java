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
package org.eclipse.php.internal.ui.wizards;

import java.io.IOException;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.templates.ContextTypeRegistry;
import org.eclipse.jface.text.templates.TemplateContextType;
import org.eclipse.osgi.util.NLS;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.corext.template.php.CodeTemplateContextType;
import org.eclipse.php.internal.ui.preferences.PreferenceConstants;
import org.eclipse.php.internal.ui.viewsupport.ProjectTemplateStore;

public class NewPhpTemplatesWizardPage extends
		NewGenericFileTemplatesWizardPage {

	public NewPhpTemplatesWizardPage() {
		super(PHPUIMessages.newPhpFile_wizard_templatePage_title,
				PHPUIMessages.newPhpFile_wizard_templatePage_description);
	}

	protected String getTemplateContextTypeId() {
		return CodeTemplateContextType.NEW_FILE_CONTEXTTYPE;
	}

	protected String getUseTemplateMessage() {
		return PHPUIMessages.newPhpFile_wizard_templatePage_usePhpTemplate;
	}

	protected ContextTypeRegistry getTemplatesContextTypeRegistry() {
		return PHPUiPlugin.getDefault().getCodeTemplateContextRegistry();
	}

	protected String getTemplatesLocationMessage() {
		ContextTypeRegistry templateContextRegistry = getTemplatesContextTypeRegistry();
		TemplateContextType templateContextType = templateContextRegistry
				.getContextType(getTemplateContextTypeId());
		String name = templateContextType.getName();
		return NLS
				.bind(PHPUIMessages.newPhpFile_wizard_templatePage_phpTemplatesLocation,
						name);
	}

	protected String getPreferencePageId() {
		return "org.eclipse.php.ui.preferences.PHPCodeTemplatePreferencePage"; //$NON-NLS-1$
	}

	protected IPreferenceStore getPreferenceStore() {
		return PreferenceConstants.getPreferenceStore();
	}

	protected String getNewFileWizardTemplatePageHelpId() {
		return null;
	}

	@Override
	protected ProjectTemplateStore getTemplateStore() {

		IProject project = getProject();

		ProjectTemplateStore templateStore;
		if (ProjectTemplateStore.hasProjectSpecificTempates(project)) {
			templateStore = new ProjectTemplateStore(project);
		} else {
			templateStore = new ProjectTemplateStore(null);
		}

		try {
			templateStore.load();
		} catch (IOException e) {
			// Ignore the error.
		}
		return templateStore;

	}
}
