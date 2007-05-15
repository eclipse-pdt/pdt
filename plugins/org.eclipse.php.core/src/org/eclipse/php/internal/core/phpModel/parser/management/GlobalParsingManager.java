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
package org.eclipse.php.internal.core.phpModel.parser.management;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.php.internal.core.phpModel.parser.IParserClientFactory;
import org.eclipse.php.internal.core.phpModel.parser.PHPProjectModel;
import org.eclipse.php.internal.core.phpModel.parser.PHPWorkspaceModelManager;
import org.eclipse.php.internal.core.resources.ExternalFilesRegistry;
import org.eclipse.php.internal.core.util.project.observer.IProjectClosedObserver;
import org.eclipse.php.internal.core.util.project.observer.ProjectRemovedObserversAttacher;

public class GlobalParsingManager {

	private static GlobalParsingManager instance;
	private Map project2ParsingManagerMap = new HashMap();

	private GlobalParsingManager() {
	}

	public static GlobalParsingManager getInstance() {
		if (instance == null) {
			instance = new GlobalParsingManager();
		}

		return instance;
	}

	public boolean addParserClient(IParserClientFactory parserClientFactory, IProject project) {
		ProjectParsingManager projectParsingManager = getProjectParsingManager(project);
		if (projectParsingManager == null) {
			return false;
		}
		projectParsingManager.addParserClient(parserClientFactory);
		return true;
	}

	public void removeParserClient(IParserClientFactory parserClientFactory, IProject project) {
		ProjectParsingManager projectParsingManager = getProjectParsingManager(project);
		if (projectParsingManager == null) {
			return;
		}
		projectParsingManager.removeParserClient(parserClientFactory);
	}

	private ProjectParsingManager getProjectParsingManager(IProject project) {
		Object object = project2ParsingManagerMap.get(project);
		if (object == null) {
			ProjectParsingManager projectParsingManager = new ProjectParsingManager(project);
			project2ParsingManagerMap.put(project, projectParsingManager);
			PHPProjectModel projectModel = null;
			if (project != null && !ExternalFilesRegistry.getInstance().getExternalFilesProject().equals(project)) {
				register2RemoveManagerWhenProjectCloses(project);
				projectModel = PHPWorkspaceModelManager.getInstance().getModelForProject(project);
			} else {
				projectModel = PHPWorkspaceModelManager.getDefaultPHPProjectModel();
			}
			projectModel.addProjectModelListener(projectParsingManager);
			return projectParsingManager;
		}
		return (ProjectParsingManager) object;
	}

	private void register2RemoveManagerWhenProjectCloses(final IProject project) {
		ProjectRemovedObserversAttacher.getInstance().addProjectClosedObserver(project, new IProjectClosedObserver() {
			public void closed() {
				ProjectParsingManager projectParsingManager = (ProjectParsingManager) project2ParsingManagerMap.remove(project);
				if (projectParsingManager == null) {
					return;
				}
				projectParsingManager.dispose();
			}
		});
	}
}
