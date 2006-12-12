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
package org.eclipse.php.core.project.properties.handlers;

import org.eclipse.core.resources.IProject;
import org.eclipse.php.core.PHPCoreConstants;
import org.eclipse.php.core.preferences.CorePreferencesSupport;
import org.eclipse.php.core.preferences.CorePreferenceConstants.Keys;

public class PhpVersionProjectPropertyHandler {
	
	private PhpVersionProjectPropertyHandler() {}

	public static String getVersion(IProject project) {
		return project != null ? CorePreferencesSupport.getInstance().getPreferencesValue(Keys.PHP_VERSION, null, project) : PHPCoreConstants.PHP5;
	}
	
	public static String getVersion() {
		return CorePreferencesSupport.getInstance().getWorkspacePreferencesValue(Keys.PHP_VERSION);
	}
    
    public static boolean setVersion(String version, IProject project) {
        return CorePreferencesSupport.getInstance().setProjectSpecificPreferencesValue(Keys.PHP_VERSION, version, project);
    }
}
