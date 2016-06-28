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

import java.util.List;

import org.eclipse.php.composer.core.PackageType;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Wojciech Galanciak, 2013
 *
 */
public interface IPackage {

	String getName();

	void setName(String name);

	String getDescription();
	
	void setDescription(String description);
	
	PackageType getType();

	void setType(PackageType type);

	List<String> getVersions();

	void setVersions(List<String> versions);

	@JsonProperty("version")
	String getVersionConstraint();

	void setVersionConstraint(String versionConstraint);

}