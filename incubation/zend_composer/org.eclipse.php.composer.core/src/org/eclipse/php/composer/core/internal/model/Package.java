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
package org.eclipse.php.composer.core.internal.model;

import java.util.Collections;
import java.util.List;

import org.eclipse.php.composer.core.PackageType;
import org.eclipse.php.composer.core.model.IPackage;

/**
 * @author Wojciech Galanciak, 2013
 * 
 */
public class Package implements IPackage {

	private String name;
	private String versionConstraint;

	private transient String description;
	private transient PackageType type = PackageType.REQUIRE;
	private transient List<String> versions = Collections.emptyList();

	public Package(String name) {
		this(name, ""); //$NON-NLS-1$
	}

	public Package(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public PackageType getType() {
		return type;
	}

	@Override
	public void setType(PackageType type) {
		this.type = type;
	}

	@Override
	public List<String> getVersions() {
		return versions;
	}

	@Override
	public void setVersions(List<String> versions) {
		this.versions = versions;
	}

	@Override
	public String getVersionConstraint() {
		return versionConstraint;
	}

	@Override
	public void setVersionConstraint(String versionConstraint) {
		this.versionConstraint = versionConstraint;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof IPackage) {
			IPackage p = (IPackage) obj;
			return getName().equals(p.getName());
		}
		return false;
	}

}
