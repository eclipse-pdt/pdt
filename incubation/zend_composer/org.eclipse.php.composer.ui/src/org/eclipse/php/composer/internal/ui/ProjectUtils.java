/*******************************************************************************
 * Copyright (c) 2013, 2016 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.internal.ui;

import org.eclipse.core.resources.IContainer;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.php.composer.core.ComposerService;
import org.eclipse.php.composer.core.ComposerService.Status;
import org.eclipse.php.composer.internal.ui.preferences.ComposerPreferencePage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.PreferencesUtil;

/**
 * @author Wojciech Galanciak, 2013
 * 
 */
public class ProjectUtils {

	/**
	 * Check if specified project is Composer project and if workspace settings
	 * allows to execute Composer command:
	 * <ul>
	 * <li>check if php exec is set</li>
	 * <li>check if composer.phat is set</li>
	 * </ul>
	 * 
	 * If any of requirements is not matched then Composer preferences page is
	 * opened.
	 * 
	 * @param project
	 * @return <code>true</code> if composer can be executed, otherwise return
	 *         <code>false</code>
	 */
	public static boolean checkProject(IContainer root) {
		Status status = ComposerService.isAvailable(root);
		switch (status) {
		case NO_PHP_EXEC:
		case NO_COMPOSER_PHAR:
			PreferenceDialog dialog = PreferencesUtil.createPreferenceDialogOn(
					PlatformUI.getWorkbench().getActiveWorkbenchWindow()
							.getShell(), ComposerPreferencePage.ID,
					new String[] { ComposerPreferencePage.ID }, null);
			if (dialog.open() == Window.OK) {
				return ComposerService.isAvailable(root) == Status.OK;
			}
			break;
		default:
			return true;
		}
		return false;
	}

}
