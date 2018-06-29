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
public class PHPContentAssistPreferencePage extends AbstractMultiBlockPreferencePage {

	@Override
	protected void setDescription() {
		setDescription(""); //$NON-NLS-1$
	}

	@Override
	protected void setPreferenceStore() {
		setPreferenceStore(PreferenceConstants.getPreferenceStore());
	}

	@Override
	protected String getPreferencePageID() {
		return PHPUiConstants.CONTENT_ASSIST_PROFERENCE_PAGE;
	}

	@Override
	public void performHelp() {
		PlatformUI.getWorkbench().getHelpSystem().setHelp(getControl(), IPHPHelpContextIds.CODE_ASSIST_PREFERENCES);
		getControl().notifyListeners(SWT.Help, new Event());
	}

}
