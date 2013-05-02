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
package org.eclipse.php.internal.core.language;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.php.core.language.ILanguageModelProvider;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.project.ProjectOptions;

/**
 * Default initializer for standard PHP functions/classes
 * 
 * @author michael
 * 
 */
class DefaultLanguageModelProvider implements ILanguageModelProvider {

	private static final String LANGUAGE_LIBRARY_PATH = "$nl$/Resources/language/php"; //$NON-NLS-1$

	public IPath getPath(IScriptProject project) {
		try {
			return new Path(getLanguageLibraryPath(project,
					ProjectOptions.getPhpVersion(project)));
		} catch (Exception e) {
			Logger.logException(e);
			return null;
		}
	}

	public String getName() {
		return Messages.DefaultLanguageModelProvider_1;
	}

	private String getLanguageLibraryPath(IScriptProject project,
			PHPVersion phpVersion) {
		if (phpVersion == PHPVersion.PHP4) {
			return LANGUAGE_LIBRARY_PATH + "4"; //$NON-NLS-1$
		}
		if (phpVersion == PHPVersion.PHP5) {
			return LANGUAGE_LIBRARY_PATH + "5"; //$NON-NLS-1$
		}
		if (phpVersion == PHPVersion.PHP5_3) {
			return LANGUAGE_LIBRARY_PATH + "5.3"; //$NON-NLS-1$
		}
		return LANGUAGE_LIBRARY_PATH + "5.4"; //$NON-NLS-1$
	}

	public Plugin getPlugin() {
		return PHPCorePlugin.getDefault();
	}
}