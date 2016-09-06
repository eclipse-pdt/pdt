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
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.php.composer.core.ComposerPlugin;
import org.eclipse.php.composer.core.ComposerPreferenceConstants;

public class PreferenceInitializer extends AbstractPreferenceInitializer {

	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore prefs = ComposerPlugin.getDefault().getPreferenceStore();
		prefs.setDefault(ComposerPreferenceConstants.PHP_EXECUTABLE, ""); //$NON-NLS-1$
		prefs.setDefault(ComposerPreferenceConstants.USE_PROJECT_PHAR, true);
		prefs.setDefault(ComposerPreferenceConstants.COMPOSER_PHAR, ""); //$NON-NLS-1$
		prefs.setDefault(ComposerPreferenceConstants.SAVEACTION_BUILDPATH, false);
		prefs.setDefault(ComposerPreferenceConstants.SAVEACTION_UPDATE, false);
	}
}
