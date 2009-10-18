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
package org.eclipse.php.internal.ui.editor.templates;

import org.eclipse.dltk.ui.templates.ScriptTemplateAccess;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.php.internal.ui.PHPUiPlugin;

/**
 * Provides access to the PHP template store.
 */
public class PhpTemplateAccess extends ScriptTemplateAccess {
	// Template
	private static final String CUSTOM_TEMPLATES_KEY = "org.eclipse.php.Templates"; //$NON-NLS-1$

	private static PhpTemplateAccess instance;

	public static PhpTemplateAccess getInstance() {
		if (instance == null) {
			instance = new PhpTemplateAccess();
		}

		return instance;
	}

	/*
	 * @see
	 * org.eclipse.dltk.ui.templates.ScriptTemplateAccess#getPreferenceStore()
	 */
	protected IPreferenceStore getPreferenceStore() {
		return PHPUiPlugin.getDefault().getPreferenceStore();
	}

	/*
	 * @see
	 * org.eclipse.dltk.ui.templates.ScriptTemplateAccess#getContextTypeId()
	 */
	protected String getContextTypeId() {
		return PhpTemplateContextType.PHP_CONTEXT_TYPE_ID;
	}

	/*
	 * @see
	 * org.eclipse.dltk.ui.templates.ScriptTemplateAccess#getCustomTemplatesKey
	 * ()
	 */
	protected String getCustomTemplatesKey() {
		return CUSTOM_TEMPLATES_KEY;
	}
}
