/*******************************************************************************
 * Copyright (c) 2018 Dawid Pakuła and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Dawid Pakuła - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.internal.core.util;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.php.composer.core.ComposerPlugin;
import org.eclipse.php.composer.core.facet.FacetManager;
import org.eclipse.php.composer.core.resources.IComposerProject;
import org.eclipse.php.core.util.INamespaceResolver;

public class NamespaceResolver implements INamespaceResolver {

	private IProject project;

	@Override
	public String resolveNamespace(IPath folder) {
		IComposerProject composerProject = ComposerPlugin.getDefault().getComposerProject(project);

		return composerProject.getNamespace(folder);
	}

	@Override
	public IPath resolveLocation(IPath target, String namespace) {
		IComposerProject composerProject = ComposerPlugin.getDefault().getComposerProject(project);

		return composerProject.getNamespaceDir(target, namespace);
	}

	@Override
	public boolean isSupported() {
		return FacetManager.hasComposerFacet(project);
	}

	@Override
	public void init(IProject project) {
		this.project = project;
	}

}
