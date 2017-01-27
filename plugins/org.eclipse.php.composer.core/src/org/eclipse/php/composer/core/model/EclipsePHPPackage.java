/*******************************************************************************
 * Copyright (c) 2012, 2016, 2017 PDT Extension Group and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *     Kaloyan Raev - [501269] externalize strings
 *******************************************************************************/
package org.eclipse.php.composer.core.model;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.php.composer.api.ComposerPackage;
import org.eclipse.php.composer.api.objects.Autoload;
import org.eclipse.php.composer.core.log.Logger;

public class EclipsePHPPackage implements NamespaceResolverInterface, InstallableItem {
	private final ComposerPackage phpPackage;

	private IPath path;

	public EclipsePHPPackage(ComposerPackage phpPackage) {

		Assert.isNotNull(phpPackage);
		this.phpPackage = phpPackage;
	}

	@Override
	public IPath resolve(IResource resource) {
		Autoload autoload = phpPackage.getAutoload();

		if (autoload == null || autoload.getPsr0() == null || autoload.getPsr0().getFirst() == null) {
			Logger.debug("Unable to resolve namespace without autoload information " + phpPackage.getName()); //$NON-NLS-1$
			return null;
		}

		String targetDir = phpPackage.getTargetDir();
		IPath ns = null;
		IPath path = resource.getFullPath();
		IPath composerPath = getPath();

		IPath psr0Path = composerPath.append(autoload.getPsr0().getFirst().getNamespace());
		int segments = psr0Path.segmentCount();

		if (path.matchingFirstSegments(psr0Path) == segments) {

			if (targetDir != null && targetDir.length() > 0) {
				Path target = new Path(targetDir);
				ns = target.append(path.removeFirstSegments(psr0Path.segmentCount()));
			} else {
				ns = path.removeFirstSegments(psr0Path.segmentCount());
			}

		}

		return ns;
	}

	@Override
	public String getName() {
		return phpPackage.getName();
	}

	@Override
	public String getDescription() {
		return phpPackage.getDescription();
	}

	@Override
	public String getUrl() {
		return phpPackage.getHomepage();
	}

	public void setFullPath(String fullPath) {
		path = new Path(fullPath);
	}

	public IPath getPath() {
		return path;
	}

	public ComposerPackage getPhpPackage() {
		return phpPackage;
	}

	@Override
	public IPath reverseResolve(IProject project, String namespace) {
		// TODO Auto-generated method stub
		return null;
	}
}
