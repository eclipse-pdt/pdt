/*******************************************************************************
 * Copyright (c) 2012, 2016, 2017 PDT Extension Group and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *     Kaloyan Raev - [501269] externalize strings
 *******************************************************************************/
package org.eclipse.php.composer.core.launch;

import java.util.SortedMap;
import java.util.TreeMap;

import org.eclipse.php.internal.debug.core.preferences.PHPexeItem;
import org.eclipse.php.internal.debug.core.preferences.PHPexes;

public class LaunchUtil {

	public static String getPHPExecutable() throws ExecutableNotFoundException {
		return getPHPExecutable(""); //$NON-NLS-1$
	}

	public static String getPHPExecutable(String debugger) throws ExecutableNotFoundException {
		// find the default PHP executable
		PHPexeItem defaultPhpExe = getDefaultPHPExeItem(debugger);

		// check if the SAPI type is CLI
		if (PHPexeItem.SAPI_CLI.equals(defaultPhpExe.getSapiType())) {
			// if yes - return it
			return defaultPhpExe.getExecutable().toString();
		}

		// otherwise try to find a PHP CLI executable
		PHPexeItem[] cliItems = PHPexes.getInstance().getCLIItems();
		if (cliItems.length == 0) {
			// if no PHP CLI executable then return the default one
			return defaultPhpExe.getExecutable().toString();
		}

		// sort the PHP CLI executable by version
		SortedMap<String, PHPexeItem> map = new TreeMap<>();
		for (PHPexeItem item : cliItems) {
			map.put(item.getVersion(), item);
		}

		// check if there is a PHP CLI executable with the same version as the
		// default PHP executable
		PHPexeItem phpExe = map.get(defaultPhpExe.getVersion());
		if (phpExe != null) {
			return phpExe.getExecutable().toString();
		}

		// otherwise return the PHP CLI executable with the greatest version
		phpExe = map.get(map.lastKey());
		return phpExe.getExecutable().toString();
	}

	private static PHPexeItem getDefaultPHPExeItem(String debugger) throws ExecutableNotFoundException {
		PHPexeItem phpExe = PHPexes.getInstance().getDefaultItem();

		if (phpExe != null) {
			return phpExe;
		}

		throw new ExecutableNotFoundException(Messages.LaunchUtil_CannotFindPhpExe);
	}
}
