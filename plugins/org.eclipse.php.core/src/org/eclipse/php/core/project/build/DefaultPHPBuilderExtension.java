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
package org.eclipse.php.core.project.build;

import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.php.core.PHPCorePlugin;
import org.eclipse.php.core.phpModel.parser.PHPProjectModel;
import org.eclipse.php.core.phpModel.parser.PHPWorkspaceModelManager;
import org.eclipse.php.core.project.PHPNature;

public class DefaultPHPBuilderExtension implements IPHPBuilderExtension {

	private PHPIncrementalProjectBuilder containingBuilder;

	public DefaultPHPBuilderExtension() {
	}

	public PHPIncrementalProjectBuilder getContainingBuilder() {
		return containingBuilder;
	}

	public void setContainingBuilder(PHPIncrementalProjectBuilder containingBuilder) {
		if (containingBuilder == null) {
			throw new IllegalArgumentException("PHP Incremental Project builder must be non-null value"); //$NON-NLS-1$
		}
		this.containingBuilder = containingBuilder;
	}
	
	public boolean isEnabled() {
		return true;
	}

	public void startupOnInitialize() {
	}

	public void clean(IProgressMonitor monitor) throws CoreException {
		cleanBuild();
	}

	public IProject[] build(int kind, Map args, IProgressMonitor monitor) throws CoreException {
		if (kind == IncrementalProjectBuilder.FULL_BUILD) {
			fullBuild();
			return null;
		}

		IResourceDelta delta = containingBuilder.getDelta(containingBuilder.getProject());
		if (delta == null) {
			return null;
		}

		buildDelta(delta, monitor);
		return null;
	}

	private void fullBuild() {
		try {
			containingBuilder.getProject().accept(new FullPhpProjectBuildVisitor());
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
			modelForProject.clean();
			return;
		}
	}

	private void cleanBuild() {
		cleanBuild(containingBuilder.getProject());
	}
}