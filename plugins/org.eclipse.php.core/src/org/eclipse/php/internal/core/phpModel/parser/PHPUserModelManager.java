/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.phpModel.parser;

import java.io.File;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.php.internal.core.phpModel.parser.management.GlobalParsingManager;
import org.eclipse.php.internal.core.phpModel.parser.management.UserModelParserClientFactoryVersionDependent;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPFileData;
import org.eclipse.php.internal.core.util.DefaultCacheManager;

public class PHPUserModelManager {

	private final PHPUserModel userModel;
	private PHPUserModel cachedUserModel;

	private final IProject project;
	private IParserClientFactory parserClientFactoryForAdd;
	private IParserClientFactory parserClientFactoryForChange;

	PHPUserModelManager(IProject project, PHPUserModel userModel) {
		this.project = project;
		this.userModel = userModel;
		
		parserClientFactoryForAdd = new UserModelParserClientFactoryVersionDependent(this, IParserClientFactory.fileAdded);
		parserClientFactoryForChange = new UserModelParserClientFactoryVersionDependent(this, IParserClientFactory.fileChanged);
		
		GlobalParsingManager.getInstance().addParserClient(parserClientFactoryForAdd, project);
		GlobalParsingManager.getInstance().addParserClient(parserClientFactoryForChange, project);
		
		// Create a cached user model without initialization
		cachedUserModel = new PHPUserModel();
		DefaultCacheManager.instance().load(project, cachedUserModel, false);
	}

	public void dispose() {

		IPath location = project.getLocation();
		if (location == null) {
			// The project was removed from the workspace.
			return;
		}
		File file = location.toFile();
		if (file.exists()) {
			DefaultCacheManager.instance().save(project, userModel, false);
		}
		
		GlobalParsingManager.getInstance().removeParserClient(parserClientFactoryForAdd, project);
		GlobalParsingManager.getInstance().removeParserClient(parserClientFactoryForChange, project);
		
		parserClientFactoryForAdd.dispose();
		parserClientFactoryForChange.dispose();
		parserClientFactoryForAdd = null;
		parserClientFactoryForChange = null;
		
		cachedUserModel.dispose();
		cachedUserModel = null;
	}

	public void fileRemoved(IFile file) {
		userModel.delete(file.getFullPath().toString());
	}

	public synchronized void clean() {
		userModel.clear();
	}

	public IProject getProject() {
		return project;
	}

	public PHPUserModel getUserModel() {
		return userModel;
	}

	public boolean shouldParse(String fileName) {
		PHPFileData fileData = cachedUserModel.getFileData(fileName);
		if (fileData != null && fileData.isValid()) {
			userModel.insert(fileData);
			cachedUserModel.delete(fileName);
			return false;
		}
		return true;
	}

}
