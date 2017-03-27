/*******************************************************************************
 * Copyright (c) 2015 Zend Techologies Ltd.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Zend Technologies Ltd. - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.formatter.core;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.php.formatter.core.CodeFormatterConstants;
import org.eclipse.php.formatter.core.profiles.PSR2FormatterPreferences;

public class CodeFormatterPreferenceInitializer extends AbstractPreferenceInitializer {

	public CodeFormatterPreferenceInitializer() {
	}

	@Override
	public void initializeDefaultPreferences() {
		IEclipsePreferences node = DefaultScope.INSTANCE.getNode(FormatterCorePlugin.PLUGIN_ID);
		node.put(CodeFormatterConstants.FORMATTER_PROFILE, PSR2FormatterPreferences.ID);
	}

}
