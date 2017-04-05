/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.phpunit.model.providers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.core.IScriptModel;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.internal.ui.StandardModelElementContentProvider;
import org.eclipse.php.internal.core.PHPToolkitUtil;

public class PHP5ElementContentProvider extends StandardModelElementContentProvider {

	public PHP5ElementContentProvider() {
		super(true);
	}

	@Override
	public Object[] getElements(Object parent) {
		return (Object[]) parent;
	}

	@Override
	protected Object[] getScriptProjects(IScriptModel jm) throws ModelException {
		IScriptProject[] scriptProjects = jm.getScriptProjects();
		final List<IProject> projectList = new ArrayList<IProject>(scriptProjects.length);
		for (IScriptProject scriptProject : scriptProjects) {
			try {
				IProject project = scriptProject.getProject();
				if (PHPToolkitUtil.isPhpProject(project)) {
					projectList.add(project);
				}
			} catch (CoreException e) {
				throw new ModelException(e);
			}

		}
		return projectList.toArray();
	}

}
