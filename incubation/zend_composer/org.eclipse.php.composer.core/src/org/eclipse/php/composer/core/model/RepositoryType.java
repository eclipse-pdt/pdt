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
package org.eclipse.php.composer.core.model;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author Wojciech Galanciak, 2013
 * 
 */
public enum RepositoryType {

	COMPOSER("composer"), //$NON-NLS-1$

	GIT("git"), //$NON-NLS-1$

	VCS("vcs"), //$NON-NLS-1$

	SVN("svn"), //$NON-NLS-1$

	PEAR("pear"), //$NON-NLS-1$

	PACKAGE("package"); //$NON-NLS-1$

	private String name;

	private RepositoryType(String name) {
		this.name = name;
	}

	@JsonValue
	public String getName() {
		return name;
	}

	public static RepositoryType byName(String name) {
		if (name == null) {
			return null;
		}
		RepositoryType[] values = values();
		for (RepositoryType type : values) {
			if (name.equalsIgnoreCase(type.getName())) {
				return type;
			}
		}
		return null;
	}

}
