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

import org.eclipse.php.internal.ui.IPHPHelpContextIds;
import org.eclipse.php.internal.ui.PHPUiConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.PlatformUI;

/**
 * 
 * @author guy.g
 * 
 */
public class PHPContentAssistPreferencePage extends
		AbstractMultiBlockPreferencePage {

	protected void setDescription() {
		setDescription(""); //$NON-NLS-1$
	}

	protected void setPreferenceStore() {
		setPreferenceStore(PreferenceConstants.getPreferenceStore());
	}

	protected String getPreferencePageID() {
		return PHPUiConstants.CONTENT_ASSIST_PROFERENCE_PAGE;
	}

	public void performHelp() {
		PlatformUI.getWorkbench().getHelpSystem().setHelp(getControl(),
				IPHPHelpContextIds.CODE_ASSIST_PREFERENCES);
		getControl().notifyListeners(SWT.Help, new Event());
	}

}
