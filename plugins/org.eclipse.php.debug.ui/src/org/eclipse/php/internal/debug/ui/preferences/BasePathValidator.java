/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.debug.ui.preferences;

import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;

public class BasePathValidator implements IPageControlValidator {

	private String errorMessage;
	private boolean isValid;
	private Text fDefaultBasePath;

	public BasePathValidator(Text fDefaultBasePath) {
		this.fDefaultBasePath = fDefaultBasePath;

	}

	public Control getControl() {

		return fDefaultBasePath;
	}

	@Override
	public String getErrorMessage() {

		return errorMessage;
	}

	@Override
	public boolean isValid() {
		return isValid;
	}

	@Override
	public void validate() {
		if (fDefaultBasePath != null) {
			String textAsString = fDefaultBasePath.getText();
			if (textAsString.equalsIgnoreCase("")) { //$NON-NLS-1$
				this.errorMessage = Messages.BasePathValidator_1;
				this.fDefaultBasePath.setMessage(this.errorMessage);
				isValid = false;
			} else if (!textAsString.startsWith("/")) { //$NON-NLS-1$
				this.errorMessage = Messages.BasePathValidator_3;
				this.fDefaultBasePath.setMessage(this.errorMessage);
				isValid = false;

			} else {
				this.errorMessage = null;
				this.fDefaultBasePath.setMessage(Messages.BasePathValidator_4);
				isValid = true;
			}
		} else {
			isValid = false;
		}

	}
}
