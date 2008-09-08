/*******************************************************************************
 * Copyright (c) 2008 xored software, Inc.  
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html  
 *
 * Contributors:
 *     xored software, Inc. - initial API and Implementation (Andrei Sobolev)
 *******************************************************************************/
package org.eclipse.php.internal.core;

import org.eclipse.core.runtime.Preferences;
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.php.internal.core.compiler.ast.parser.PHPTodoTaskPreferences;

public class PHPTodoPreferenceInitializer extends AbstractPreferenceInitializer {

	public PHPTodoPreferenceInitializer() {
	}

	public void initializeDefaultPreferences() {
		Preferences store = PHPCorePlugin.getDefault().getPluginPreferences();
		PHPTodoTaskPreferences.initializeDefaultValues(store);
	}
}
