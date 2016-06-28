/*******************************************************************************
 * Copyright (c) 2015, 2016 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.core;

import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.php.internal.debug.core.preferences.PHPexeItem;
import org.eclipse.php.internal.debug.core.preferences.PHPexes;
import org.osgi.framework.Bundle;

public class ComposerPreferences extends AbstractPreferenceInitializer {

	public static final String COMPOSER_PHAR = "composer.phar"; //$NON-NLS-1$

	public static final String REPOSITORIES_NODE = "repositories"; //$NON-NLS-1$
	public static final String PHP_EXEC_NODE = "phpexec"; //$NON-NLS-1$
	public static final String COMPOSER_PHAR_NODE = "composerPhar"; //$NON-NLS-1$
	public static final String UPDATE_COMPOSER_PHAR_NODE = "updateComposerPhar"; //$NON-NLS-1$

	@Override
	public void initializeDefaultPreferences() {
		IEclipsePreferences defaultPreferences = DefaultScope.INSTANCE.getNode(ComposerCorePlugin.PLUGIN_ID);

		defaultPreferences.put(COMPOSER_PHAR_NODE, getDefaultComposerPhar());

		PHPexeItem[] items = PHPexes.getInstance().getCLIItems();

		for (PHPexeItem item : items) {
			if (item.isDefault()) {
				defaultPreferences.put(PHP_EXEC_NODE, item.getName());
				break;
			}
		}
	}

	public static String get(String key) {
		String value = InstanceScope.INSTANCE.getNode(ComposerCorePlugin.PLUGIN_ID).get(key, null);
		if (value == null) {
			value = DefaultScope.INSTANCE.getNode(ComposerCorePlugin.PLUGIN_ID).get(key, null);
		}
		return value;
	}

	public static boolean getBoolean(String key, boolean def) {
		return InstanceScope.INSTANCE.getNode(ComposerCorePlugin.PLUGIN_ID).getBoolean(key, def);
	}

	public static String getDefaultComposerPhar() {
		Bundle bundle = Platform.getBundle(ComposerCorePlugin.PLUGIN_ID);
		return Platform.getStateLocation(bundle).append(COMPOSER_PHAR).toFile().getAbsolutePath();
	}

}
