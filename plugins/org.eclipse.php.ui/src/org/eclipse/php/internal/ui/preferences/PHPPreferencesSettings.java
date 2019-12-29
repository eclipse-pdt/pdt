/*******************************************************************************
 * Copyright (c) 2019 Dawid Pakuła and others.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Dawid Pakuła - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.preferences;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.php.internal.core.PHPCoreConstants;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.preferences.PreferencesSupport;
import org.eclipse.php.internal.ui.actions.CodeGenerationSettings;

public class PHPPreferencesSettings {
	public static CodeGenerationSettings getCodeGenerationSettings(IProject project) {
		CodeGenerationSettings res = new CodeGenerationSettings();
		PreferencesSupport preferencesSupport = new PreferencesSupport(PHPCorePlugin.ID);
		res.createComments = DefaultScope.INSTANCE.getNode(PHPCorePlugin.ID)
				.getBoolean(PHPCoreConstants.CODEGEN_ADD_COMMENTS, false);
		String tmp = preferencesSupport.getProjectSpecificPreferencesValue(PHPCoreConstants.FORMATTER_TAB_SIZE, null,
				project);
		if (tmp == null) {
			tmp = preferencesSupport.getWorkspacePreferencesValue(PHPCoreConstants.FORMATTER_TAB_SIZE);
		}
		if (tmp != null) {
			res.tabWidth = Integer.valueOf(tmp).intValue();
		} else {
			res.tabWidth = 4;
		}
		tmp = null;
		tmp = preferencesSupport.getProjectSpecificPreferencesValue(PHPCoreConstants.FORMATTER_INDENTATION_SIZE, null,
				project);
		if (tmp == null) {
			tmp = preferencesSupport.getWorkspacePreferencesValue(PHPCoreConstants.FORMATTER_INDENTATION_SIZE);
		}
		if (tmp != null) {
			res.indentWidth = Integer.valueOf(tmp).intValue();
		} else {
			res.indentWidth = 4;
		}
		return res;
	}

}
