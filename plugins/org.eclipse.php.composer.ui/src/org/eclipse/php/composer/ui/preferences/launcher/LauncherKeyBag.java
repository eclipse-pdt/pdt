/*******************************************************************************
 * Copyright (c) 2012, 2016 PDT Extension Group and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.ui.preferences.launcher;

import org.eclipse.php.internal.ui.preferences.util.Key;

public interface LauncherKeyBag {

	public Key[] getAllKeys();

	public Key getPHPExecutableKey();

	public Key getScriptKey();

	public Key getUseProjectKey();

}
