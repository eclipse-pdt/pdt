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
package org.eclipse.php.internal.ui.util;

import org.eclipse.php.internal.core.PHPCoreConstants;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.wst.common.frameworks.internal.datamodel.ui.DataModelSynchHelper;
import org.eclipse.wst.xml.ui.internal.preferences.EncodingSettings;

public class PHPEncodingField {

	EncodingSettings encodingSettings;

	public PHPEncodingField(Composite parent, DataModelSynchHelper synchHelper) {
		encodingSettings = new PhpEncodingSettings(parent, PHPUIMessages.getString("PHPEncodingField.0")); //$NON-NLS-1$
		if (synchHelper != null)
			synchHelper.synchCombo(encodingSettings.getEncodingCombo(), PHPCoreConstants.PHPOPTION_DEFAULT_ENCODING, new Control[] {});

	}

	public void setValue(String value) {
		encodingSettings.setIANATag(value);
	}

	public void setDefaultValue() {
		encodingSettings.resetToDefaultEncoding();
	}

	public String getValue() {
		String value = encodingSettings.getIANATag();

		return value;
	}

	public void setLayoutData(Object layoutData) {
		encodingSettings.setLayoutData(layoutData);
	}
}
