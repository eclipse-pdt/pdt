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

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.php.internal.ui.IPHPHelpContextIds;

public class PHPEditorPreferenceEmptyPage extends AbstractEmptyPreferencePage {

	public PHPEditorPreferenceEmptyPage() {
		super();
	}

	public PHPEditorPreferenceEmptyPage(String title) {
		super();
	}

	public PHPEditorPreferenceEmptyPage(String title, ImageDescriptor image) {
		super();
	}

	@Override
	public String getBodyText() {
		return PreferencesMessages.PHPEditorPreferencePage_body_text;
	}

	@Override
	protected String getPreferenceHelpId() {
		return IPHPHelpContextIds.EDITOR_PREFERENCES;
	}

	@Override
	protected String getPropertiesHelpId() {
		return IPHPHelpContextIds.EDITOR_PROPERTIES;
	}

}