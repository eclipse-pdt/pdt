/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.project.build;

import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.phpModel.parser.PHPProjectModel;
import org.eclipse.php.internal.core.phpModel.parser.PHPWorkspaceModelManager;
import org.eclipse.php.internal.core.project.PHPNature;
import org.eclipse.php.core.project.build.IPHPBuilderExtension;

public class DefaultPHPBuilderExtension implements IPHPBuilderExtension {

	public DefaultPHPBuilderExtension() {
	}
	
	public boolean isEnabled() {
		return true;
	}

	public void startupOnInitialize(IncrementalProjectBuilder builder) {
	}

	public void clean(IncrementalProjectBuilder builder, IProgressMonitor monitor) throws CoreException {
		cleanBuild(builder.getProject());
	}

	public IProject[] build(IncrementalProjectBuilder builder, int kind, Map args, IProgressMonitor monitor) throws CoreException {
		if (kind == IncrementalProjectBuilder.FULL_BUILD) {
			fullBuild(builder, monitor);
			return null;
		}
		
		IResourceDelta delta = builder.getDelta(builder.getProject());
		if (delta == null) {
			return null;
		}

		buildDelta(delta, monitor);
		
		return null;
	}

	private void fullBuild(IncrementalProjectBuilder builder, IProgressMonitor monitor) {
		try {
			IProject project = builder.getProject();
			project.accept(new FullPhpProjectBuildVisitor(monitor));
			PHPWorkspaceModelManager.getInstance().fireProjectModelChanged(project);
		} catch (CoreException e) {
			PHPCorePlugin.log(e);
			return;
		}
	}

	private void buildDelta(IResourceDelta delta, IProgressMonitor monitor) throws CoreException {
		// the visitor does the work.
		delta.accept(new PhpResourceDeltaBuildVisitor());
	}

	private void cleanBuild(IProject project) {
		try {
			if (!project.hasNature(PHPNature.ID)) {
				return;
			}
		} catch (CoreException e) {
			PHPCorePlugin.log(e);
			return;
		}

		PHPProjectModel modelForProject = PHPWorkspaceModelManager.getInstance().getModelForProject(project);
		if (modelForProject != null) {
			modelForProject.clear();
			return;
		}
	}
}