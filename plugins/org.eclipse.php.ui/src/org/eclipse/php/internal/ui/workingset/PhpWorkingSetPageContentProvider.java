/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.eclipse.php.internal.ui.workingset;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.core.IScriptModel;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.internal.ui.StandardModelElementContentProvider;

class PhpWorkingSetPageContentProvider extends
		StandardModelElementContentProvider {

	public boolean hasChildren(Object element) {

		if (element instanceof IProject && !((IProject) element).isAccessible())
			return false;

		return super.hasChildren(element);
	}

	public Object[] getChildren(Object parentElement) {
		try {
			if (parentElement instanceof IScriptModel)
				return concatenate(super.getChildren(parentElement),
						getForeignProjects((IScriptModel) parentElement));

			if (parentElement instanceof IProject)
				return ((IProject) parentElement).members();

			if (parentElement instanceof IScriptProject)
				return ((IScriptProject) parentElement).getProject().members(
						IContainer.FOLDER);

			return super.getChildren(parentElement);
		} catch (CoreException e) {
			return NO_CHILDREN;
		}
	}

	private Object[] getForeignProjects(IScriptModel model)
			throws ModelException {
		return model.getForeignResources();
	}
}
