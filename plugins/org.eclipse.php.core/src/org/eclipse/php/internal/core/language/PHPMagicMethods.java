/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.core.language;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.php.internal.core.PHPVersion;

public class PHPMagicMethods {

	private static final String[] MAGIC_METHODS = { "__get", "__set", "__call", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			"__sleep", "__wakeup", }; //$NON-NLS-1$ //$NON-NLS-2$
	private static final String[] MAGIC_METHODS_PHP5 = { "__isset", "__unset", //$NON-NLS-1$ //$NON-NLS-2$
			"__toString", "__set_state", "__clone", "__autoload", }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
	private static final String[] MAGIC_METHODS_PHP5_3 = { "__callstatic", //$NON-NLS-1$
			"__invoke", }; //$NON-NLS-1$

	public static String[] getMethods(PHPVersion phpVersion) {
		List<String> methods = new LinkedList<String>();
		methods.addAll(Arrays.asList(MAGIC_METHODS));
		if (phpVersion.isGreaterThan(PHPVersion.PHP4)) {
			methods.addAll(Arrays.asList(MAGIC_METHODS_PHP5));
		}
		if (phpVersion.isGreaterThan(PHPVersion.PHP5)) {
			methods.addAll(Arrays.asList(MAGIC_METHODS_PHP5_3));
		}
		return (String[]) methods.toArray(new String[methods.size()]);
	}
}
