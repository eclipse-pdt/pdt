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
package org.eclipse.php.internal.ui.util;

import org.eclipse.php.internal.core.PHPCoreConstants;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wst.common.frameworks.internal.datamodel.ui.DataModelSynchHelper;

public class PHPRootContextField {

	private Text rootContext;

	public PHPRootContextField(Composite parent,
			DataModelSynchHelper synchHelper) {
		Label rootContextLabel = new Label(parent, SWT.NULL);
		rootContextLabel.setText(PHPUIMessages.PHPRootContextField_0); 

		rootContext = new Text(parent, SWT.BORDER);

		rootContext.setLayoutData(getTextLayoutData());
		if (synchHelper != null) {
			synchHelper.synchText(rootContext,
					PHPCoreConstants.PHPOPTION_CONTEXT_ROOT,
					new Control[] { rootContextLabel });
		}
	}

	public void setValue(String value) {
		rootContext.setText(value);
	}

	public void setDefaultValue() {
		rootContext.setText(""); //$NON-NLS-1$
	}

	public String getValue() {
		String value = rootContext.getText();

		return value;
	}

	protected Object getTextLayoutData() {
		GridData gd = new GridData(GridData.HORIZONTAL_ALIGN_FILL
				| GridData.HORIZONTAL_ALIGN_BEGINNING);

		return gd;
	}

	public Text getRootContextText() {
		return rootContext;
	}
}
