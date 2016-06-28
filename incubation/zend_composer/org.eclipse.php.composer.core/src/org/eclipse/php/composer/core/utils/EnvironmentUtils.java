/*******************************************************************************
 * Copyright (c) 2013, 2016 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.core.utils;

import org.eclipse.core.runtime.Platform;

/**
 * @author Wojciech Galanciak, 2013
 * 
 */
public class EnvironmentUtils {

	public static boolean isLinux() {
		return Platform.getOS().equals(Platform.OS_LINUX);
	}

	public static boolean isWindows() {
		return Platform.getOS().equals(Platform.OS_WIN32);
	}

	public static boolean isMacOSX() {
		return Platform.getOS().equals(Platform.OS_MACOSX);
	}

}
