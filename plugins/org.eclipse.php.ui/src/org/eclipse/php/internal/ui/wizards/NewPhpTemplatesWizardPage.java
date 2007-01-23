/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.wizards;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.templates.ContextTypeRegistry;
import org.eclipse.jface.text.templates.TemplateContextType;
import org.eclipse.osgi.util.NLS;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.editor.templates.PHPTemplateContextTypeIds;
import org.eclipse.php.internal.ui.preferences.PreferenceConstants;

public class NewPhpTemplatesWizardPage extends NewGenericFileTemplatesWizardPage {
	
	public NewPhpTemplatesWizardPage() {
		super(PHPUIMessages.newPhpFile_wizard_templatePage_title, PHPUIMessages.newPhpFile_wizard_templatePage_description);
	}

	protected String getTemplateContextTypeId() {
		return PHPTemplateContextTypeIds.NEW_PHP;
	}

	protected String getUseTemplateMessage() {
		return PHPUIMessages.newPhpFile_wizard_templatePage_usePhpTemplate;
	}
	
	protected ContextTypeRegistry getTemplatesContextTypeRegistry() {
		return PHPUiPlugin.getDefault().getTemplateContextRegistry();
	}

	protected String getTemplatesLocationMessage() {
		ContextTypeRegistry templateContextRegistry = getTemplatesContextTypeRegistry();
		TemplateContextType templateContextType = templateContextRegistry.getContextType(getTemplateContextTypeId());
		String name = templateContextType.getName();
		return NLS.bind(PHPUIMessages.newPhpFile_wizard_templatePage_phpTemplatesLocation, name);
	}

	protected String getPreferencePageId() {
		return "org.eclipse.php.ui.preferences.ui.PHPTemplatesPreferencePage"; //$NON-NLS-1$
	}

	protected IPreferenceStore getPreferenceStore() {
		return PreferenceConstants.getPreferenceStore();
	}

	protected String getNewFileWizardTemplatePageHelpId() {
		return null;
	}
}
