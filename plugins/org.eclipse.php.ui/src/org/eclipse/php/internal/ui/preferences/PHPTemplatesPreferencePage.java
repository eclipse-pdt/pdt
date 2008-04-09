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
package org.eclipse.php.internal.ui.preferences;

import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.ui.texteditor.templates.TemplatePreferencePage;

public class PHPTemplatesPreferencePage extends TemplatePreferencePage {

	
	public PHPTemplatesPreferencePage() {
		setPreferenceStore(PreferenceConstants.getPreferenceStore());
		setTemplateStore(PHPUiPlugin.getDefault().getTemplateStore());
		setContextTypeRegistry(PHPUiPlugin.getDefault().getTemplateContextRegistry());
	}
	
	protected boolean isShowFormatterSetting() {
		return false;
	}

}
