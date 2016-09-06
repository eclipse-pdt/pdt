/*******************************************************************************
 * Copyright (c) 2012, 2016 PDT Extension Group and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.core.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.php.composer.core.ComposerPlugin;
import org.eclipse.php.composer.core.ComposerPreferenceConstants;

public class PreferenceInitializer extends AbstractPreferenceInitializer {

	@Override
	public void initializeDefaultPreferences() {
		IEclipsePreferences scope = DefaultScope.INSTANCE.getNode(ComposerPlugin.ID);
		scope.put(ComposerPreferenceConstants.PHP_EXECUTABLE, ""); //$NON-NLS-1$
		scope.putBoolean(ComposerPreferenceConstants.USE_PROJECT_PHAR, true);
		scope.put(ComposerPreferenceConstants.COMPOSER_PHAR, ""); //$NON-NLS-1$
		scope.putBoolean(ComposerPreferenceConstants.SAVEACTION_BUILDPATH, false);
		scope.putBoolean(ComposerPreferenceConstants.SAVEACTION_UPDATE, false);
	}
}
