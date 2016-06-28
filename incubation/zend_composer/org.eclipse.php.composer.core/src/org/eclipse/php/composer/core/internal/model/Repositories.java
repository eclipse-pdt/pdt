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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.php.composer.core.ComposerCorePlugin;
import org.eclipse.php.composer.core.internal.model.adapters.ComposerJacksonMapper;
import org.eclipse.php.composer.core.model.IRepositories;
import org.eclipse.php.composer.core.model.IRepository;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * @author Wojciech Galanciak, 2013
 * 
 */
public class Repositories extends ModelElement implements IRepositories {

	private List<IRepository> repositories;

	protected boolean isDirty;
	private boolean isArray = true;

	public Repositories() {
		this.repositories = new ArrayList<IRepository>();
	}

	@Override
	public List<IRepository> getRepositories() {
		return repositories;
	}

	@Override
	public void removeRepository(IRepository repository, boolean update) {
		repositories.remove(repository);
		if (update) {
			this.isDirty = true;
			updateModel();
		}
	}

	@Override
	public void addRepository(IRepository repository, boolean update) {
		repositories.add(repository);
		if (update) {
			this.isDirty = true;
			updateModel();
		}
	}

	@Override
	public void setRepositories(List<IRepository> repositories) {
		this.repositories = repositories;
	}

	@Override
	public String serialize() {
		try {
			return ComposerJacksonMapper.getMapper().writeValueAsString(this);
		} catch (JsonProcessingException e) {
			ComposerCorePlugin.logError(e);
		}
		return null;
	}

	@Override
	public boolean isDirty() {
		if (isDirty) {
			return true;
		}
		for (IRepository repo : repositories) {
			if (repo.isDirty()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public IRepositories deserialize(String repositories) {
		try {
			return ComposerJacksonMapper.getMapper().readValue(repositories, IRepositories.class);
		} catch (IOException e) {
			ComposerCorePlugin.logError(e);
		}
		return null;
	}

	@Override
	public boolean isArray() {
		return isArray;
	}

	public void setArray(boolean isArray) {
		this.isArray = isArray;
	}

}
