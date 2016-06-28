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
package org.eclipse.php.composer.core.utils;

import java.io.File;
import java.io.IOException;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.Path;
import org.eclipse.php.composer.core.ComposerCorePlugin;
import org.eclipse.php.composer.core.ComposerService;
import org.eclipse.php.composer.core.internal.model.adapters.ComposerJacksonMapper;
import org.eclipse.php.composer.core.model.ComposerRoot;
import org.eclipse.php.composer.core.model.IRepositories;
import org.eclipse.php.composer.core.model.IRepository;
import org.eclipse.php.composer.core.model.ModelFactory;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

/**
 * 
 * @author Michal Niewrzal, 2014
 * 
 */
public class RepositoriesHandler {

	private static final String PACKAGIST_PARAM = "packagist"; //$NON-NLS-1$

	private File composerFile;
	private ComposerRoot composerRoot;

	public RepositoriesHandler(IContainer project) {
		IFile file = project.getFile(new Path(ComposerService.COMPOSER_JSON));

		this.composerFile = file.getLocation().toFile();
		try {
			this.composerRoot = ComposerJacksonMapper.getMapper().readValue(composerFile, ComposerRoot.class);
		} catch (JsonParseException e) {
			ComposerCorePlugin.logError(e);
		} catch (JsonMappingException e) {
			ComposerCorePlugin.logError(e);
		} catch (IOException e) {
			ComposerCorePlugin.logError(e);
		}
	}

	public void addRepository(boolean disablePackagist, IRepository... repositories) {
		if (composerRoot == null) {
			return;
		}

		IRepositories repositoriesNode = composerRoot.getRepositoriesProperty();
		if (repositoriesNode == null) {
			return;
		}

		for (IRepository repository : repositories) {
			repositoriesNode.addRepository(repository, false);
		}

		disablePackagist(disablePackagist, repositoriesNode);

		serialize();
	}

	public void removeRepository(boolean disablePackagist, IRepository... repositories) {
		if (composerRoot == null) {
			return;
		}

		IRepositories repositoriesNode = composerRoot.getRepositoriesProperty();

		for (IRepository repository : repositories) {
			repositoriesNode.removeRepository(repository, false);
		}

		disablePackagist(disablePackagist, repositoriesNode);

		serialize();
	}

	private void disablePackagist(boolean disable, IRepositories repositoriesNode) {
		for (Object object : repositoriesNode.getRepositories().toArray()) {
			IRepository repository = (IRepository) object;
			if (!disable && repository.getType() == null && repository.getParameters().containsKey(PACKAGIST_PARAM)) {
				repositoriesNode.removeRepository(repository, false);
			}
		}
		if (disable) {
			IRepository repository = ModelFactory.createRepository(null);
			repository.getParameters().put(PACKAGIST_PARAM, Boolean.FALSE);
			repositoriesNode.addRepository(repository, false);
		}
	}

	private void serialize() {
		try {
			ComposerJacksonMapper.getMapper().writeValue(composerFile, composerRoot);
		} catch (JsonGenerationException e) {
			ComposerCorePlugin.logError(e);
		} catch (JsonMappingException e) {
			ComposerCorePlugin.logError(e);
		} catch (IOException e) {
			ComposerCorePlugin.logError(e);
		}
	}

}
