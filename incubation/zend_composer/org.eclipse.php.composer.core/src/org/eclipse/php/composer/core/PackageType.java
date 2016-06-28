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
package org.eclipse.php.composer.core;

/**
 * Set of Composer package dependencies types.
 * 
 * @author Wojciech Galanciak, 2013
 * 
 */
public enum PackageType {

	REQUIRE("require"), //$NON-NLS-1$

	REQUIRE_DEV("require-dev"), //$NON-NLS-1$

	CONFLICT("conflict"), //$NON-NLS-1$

	REPLACE("replace"), //$NON-NLS-1$

	PROVIDE("provide"); //$NON-NLS-1$

	private String name;

	private PackageType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public static PackageType byName(String name) {
		if (name == null) {
			return null;
		}
		PackageType[] values = values();
		for (PackageType type : values) {
			if (name.equalsIgnoreCase(type.getName())) {
				return type;
			}
		}
		return null;
	}

}
