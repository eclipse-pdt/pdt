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
package org.eclipse.php.composer.ui.preferences.launcher;

import org.eclipse.php.internal.ui.preferences.util.Key;

@SuppressWarnings("restriction")
public interface LauncherKeyBag {
	
	public Key[] getAllKeys();
	public Key getPHPExecutableKey();
	public Key getScriptKey();
	public Key getUseProjectKey();

}
