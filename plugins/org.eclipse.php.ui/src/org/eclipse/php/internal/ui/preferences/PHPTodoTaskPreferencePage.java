/*******************************************************************************
 * Copyright (c) 2008 xored software, Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     xored software, Inc. - initial API and Implementation (Alex Panchenko)
 *******************************************************************************/
package org.eclipse.php.internal.ui.preferences;

import org.eclipse.core.runtime.Preferences;
import org.eclipse.dltk.ui.preferences.TodoTaskAbstractPreferencePage;
import org.eclipse.php.internal.core.PHPCorePlugin;

public class PHPTodoTaskPreferencePage extends TodoTaskAbstractPreferencePage {

	protected String getHelpId() {
		return null;
	}

	protected void setDescription() {
		setDescription("Strings indicating tasks in PHP comments.");
	}

	protected Preferences getPluginPreferences() {
		return PHPCorePlugin.getDefault().getPluginPreferences();
	}

}
