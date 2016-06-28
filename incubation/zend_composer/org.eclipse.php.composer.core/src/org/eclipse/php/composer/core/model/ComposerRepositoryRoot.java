/*******************************************************************************
 * Copyright (c) 2014, 2016 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.core.model;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.php.composer.core.internal.model.adapters.ComposerJacksonMapper;
import org.eclipse.php.composer.core.internal.model.adapters.ComposerRepositoryPackageDeserializer;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Represents Composer repository definition from packages.json file.
 * 
 * @author Michal Niewrzal, 2014
 * 
 */
public class ComposerRepositoryRoot {

	@JsonDeserialize(using = ComposerRepositoryPackageDeserializer.class)
	private List<IRepositoryPackage> packages;

	public List<IRepositoryPackage> getPackages() {
		return packages;
	}

	public List<IRepositoryPackage> getPackages(String name) {
		List<IRepositoryPackage> machedPackages = new ArrayList<IRepositoryPackage>();
		for (IRepositoryPackage p : packages) {
			if (p.getName() != null && p.getName().equals(name)) {
				machedPackages.add(p);
			}
		}
		return machedPackages;
	}

	public static ComposerRepositoryRoot parse(InputStream inputStream)
			throws JsonParseException, JsonMappingException, IOException {
		return ComposerJacksonMapper.getMapper().readValue(inputStream,
				ComposerRepositoryRoot.class);
	}

}
