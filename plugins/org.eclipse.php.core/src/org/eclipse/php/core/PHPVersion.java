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
package org.eclipse.php.core;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * This enumeration defines supported PHP versions.
 */
public enum PHPVersion {

	PHP5("php5"), //$NON-NLS-1$

	/**
	 * This 5.3 is a major version - it should be 5.0, actually.
	 * <p>
	 * New in this version:
	 * <ul>
	 * <li><a href="http://www.php.net/language.namespaces">Namespaces</a>
	 * <li><a href="http://wiki.php.net/rfc/closures">Closures</a>
	 * <li><a href="http://www.php.net/oop5.late-static-bindings">Late Static
	 * Binding</a>
	 * </ul>
	 */
	PHP5_3("php5.3"), //$NON-NLS-1$
	PHP5_4("php5.4"), //$NON-NLS-1$
	PHP5_5("php5.5"), //$NON-NLS-1$
	PHP5_6("php5.6"), //$NON-NLS-1$
	PHP7_0("php7.0"), //$NON-NLS-1$
	PHP7_1("php7.1"); //$NON-NLS-1$

	private String alias;

	private static class Aliases {
		private static Map<String, PHPVersion> map = new HashMap<String, PHPVersion>();
	}

	PHPVersion(String alias) {
		this.alias = alias;
		Aliases.map.put(alias, this);
	}

	public String getAlias() {
		return alias;
	}

	public static PHPVersion byAlias(String alias) {
		return Aliases.map.get(alias);
	}

	public boolean isLessThan(PHPVersion phpVersion) {
		return ordinal() < phpVersion.ordinal();
	}

	public boolean isGreaterThan(PHPVersion phpVersion) {
		return ordinal() > phpVersion.ordinal();
	}

	public static Collection<PHPVersion> getAllVersions() {
		return Aliases.map.values();
	}

	public static PHPVersion[] supportedVersions() {
		return new PHPVersion[] { PHP7_1, PHP7_0, PHP5_6, PHP5_5, PHP5_4, PHP5_3, PHP5 };
	}

	/**
	 * 
	 * @return
	 */
	public static PHPVersion getLatestVersion() {
		return PHPVersion.PHP7_1;
	}

}
