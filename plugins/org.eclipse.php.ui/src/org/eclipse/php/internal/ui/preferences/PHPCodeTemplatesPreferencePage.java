/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.preferences;

import org.eclipse.php.internal.ui.IPHPHelpContextIds;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.texteditor.templates.TemplatePreferencePage;

public class PHPCodeTemplatesPreferencePage extends TemplatePreferencePage {

	
	public PHPCodeTemplatesPreferencePage() {
		setPreferenceStore(PreferenceConstants.getPreferenceStore());
		setTemplateStore(PHPUiPlugin.getDefault().getTemplateStore());
		setContextTypeRegistry(PHPUiPlugin.getDefault().getTemplateContextRegistry());
	}
	
	public void performHelp() {
		PlatformUI.getWorkbench().getHelpSystem().setHelp(getControl(), IPHPHelpContextIds.TEMPLATES_PREFERENCES);
		getControl().notifyListeners(SWT.Help, new Event());
    }
}
