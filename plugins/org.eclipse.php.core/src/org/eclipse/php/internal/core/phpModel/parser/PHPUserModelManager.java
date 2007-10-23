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
import org.eclipse.php.internal.core.util.DefaultCacheManager;

public class PHPUserModelManager {

	private final PHPUserModel userModel;

	private final IProject project;

	private IParserClientFactory parserClientFactory;
	private boolean buildNeeded = false;

	PHPUserModelManager(final IProject project, PHPUserModel userModel) {
		this.project = project;
		this.userModel = userModel;
		parserClientFactory = new UserModelParserClientFactoryVersionDependent(this);

		GlobalParsingManager.getInstance().addParserClient(parserClientFactory, project);

		//loading model from cache

		if (project.exists()) {//the project exist test is for the case we're dealing with the dummy project for external files.
			boolean modelLoaded = DefaultCacheManager.instance().load(project, this.userModel, false);

			//if the model was not loaded from the cache then a build is needed 
			if (!modelLoaded) {
				buildNeeded = true;
			}
		}
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

		GlobalParsingManager.getInstance().removeParserClient(parserClientFactory, project);

		parserClientFactory.dispose();
		parserClientFactory = null;

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

	public boolean isBuildNeeded() {
		//the buildNeeded is a one time flag it is true only if the model is loaded and there is no cache 
		//otherwise the build should be initiated according to the regular builders policy
		if (buildNeeded) {
			buildNeeded = false;
			return true;
		}
		return false;
	}

}
