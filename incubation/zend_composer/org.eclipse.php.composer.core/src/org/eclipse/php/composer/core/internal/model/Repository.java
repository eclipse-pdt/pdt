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

import org.eclipse.php.composer.core.model.IRepository;
import org.eclipse.php.composer.core.model.IRepositoryPackage;
import org.eclipse.php.composer.core.model.RepositoryType;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Wojciech Galanciak, 2013
 * 
 */
public class Repository extends ModelElement implements IRepository {

	private RepositoryType type;
	private String url;
	private IRepositoryPackage repositoryPackage;

	private String key;

	protected boolean isDirty;

	public Repository() {
	}

	public Repository(RepositoryType type) {
		this.type = type;
	}

	@Override
	public void setRepositoryPackage(IRepositoryPackage pkg) {
		this.repositoryPackage = pkg;
		this.isDirty = true;
		updateModel();
	}

	@Override
	public void setUrl(String url) {
		this.url = url;
		this.isDirty = true;
		updateModel();
	}

	@Override
	public String getUrl() {
		return url;
	}

	@Override
	public void setType(RepositoryType type) {
		this.type = type;
	}

	@Override
	public RepositoryType getType() {
		return type;
	}

	@Override
	@JsonProperty("package")
	public IRepositoryPackage getRepositoryPackage() {
		return repositoryPackage;
	}

	@Override
	public boolean isDirty() {
		boolean result = isDirty;
		if (!result) {
			result = getRepositoryPackage().isDirty();
		}
		return result;
	}

	public IRepository deserialize(String repository) {
		return null;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof IRepository) {
			IRepository repo = (IRepository) obj;
			if (getType() != repo.getType()) {
				return false;
			}
			if (getType() == null) {
				return this == obj;
			}
			switch (getType()) {
			case PACKAGE:
				return getRepositoryPackage().equals(repo.getRepositoryPackage());
			default:
				return getUrl().equals(repo.getUrl());
			}
		}
		return false;
	}

	@Override
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
