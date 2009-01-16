/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.project.properties.handlers;

import org.eclipse.core.resources.IProject;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.preferences.CorePreferencesSupport;
import org.eclipse.php.internal.core.preferences.CorePreferenceConstants.Keys;

public class PhpVersionProjectPropertyHandler {
	
	private PhpVersionProjectPropertyHandler() {}

	public static PHPVersion getVersion(IProject project) {
		if (project != null) {
			return PHPVersion.byAlias(CorePreferencesSupport.getInstance().getPreferencesValue(Keys.PHP_VERSION, null, project));
		}
		return PHPVersion.PHP5; // default version
	}
	
	public static PHPVersion getVersion() {
		return PHPVersion.byAlias(CorePreferencesSupport.getInstance().getWorkspacePreferencesValue(Keys.PHP_VERSION));
	}
    
    public static boolean setVersion(PHPVersion version, IProject project) {
        return CorePreferencesSupport.getInstance().setProjectSpecificPreferencesValue(Keys.PHP_VERSION, version.getAlias(), project);
    }
}
