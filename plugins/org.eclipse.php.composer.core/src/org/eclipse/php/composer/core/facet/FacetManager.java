/*******************************************************************************
 * Copyright (c) 2012, 2016 PDT Extension Group and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.core.facet;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.php.composer.core.log.Logger;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.facet.PHPFacets;
import org.eclipse.php.internal.core.facet.PHPFacetsConstants;
import org.eclipse.wst.common.project.facet.core.IFacetedProject;
import org.eclipse.wst.common.project.facet.core.IProjectFacet;
import org.eclipse.wst.common.project.facet.core.ProjectFacetsManager;

@SuppressWarnings("restriction")
public class FacetManager {

	public static IFacetedProject installFacets(IProject project, PHPVersion version, IProgressMonitor monitor) {
		try {

			if (monitor == null) {
				monitor = new NullProgressMonitor();
			}

			final IFacetedProject facetedProject = ProjectFacetsManager.create(project, true, monitor);

			if (facetedProject == null) {
				Logger.log(Logger.ERROR, "Unable to create faceted composer project.");
				return null;
			}

			IProjectFacet coreFacet = ProjectFacetsManager.getProjectFacet(PHPFacetsConstants.PHP_CORE_COMPONENT);
			IProjectFacet composerFacet = ProjectFacetsManager
					.getProjectFacet(ComposerFacetConstants.COMPOSER_COMPONENT);

			// install the fixed facets

			if (!facetedProject.hasProjectFacet(coreFacet)) {
				facetedProject.installProjectFacet(coreFacet.getDefaultVersion(), null, monitor);
				facetedProject.installProjectFacet(PHPFacets.convertToFacetVersion(version), null, monitor);
			}

			if (!facetedProject.hasProjectFacet(composerFacet)) {
				facetedProject.installProjectFacet(
						composerFacet.getVersion(ComposerFacetConstants.COMPOSER_COMPONENT_VERSION_1), composerFacet,
						monitor);
			}

			return facetedProject;

		} catch (CoreException ex) {
			Logger.logException(ex.getMessage(), ex);
		}

		return null;
	}

	public static void uninstallFacets(IProject project, IProgressMonitor monitor) {
		try {
			if (monitor == null) {
				monitor = new NullProgressMonitor();
			}

			final IFacetedProject facetedProject = ProjectFacetsManager.create(project, true, monitor);

			IProjectFacet composerFacet = ProjectFacetsManager
					.getProjectFacet(ComposerFacetConstants.COMPOSER_COMPONENT);

			facetedProject.uninstallProjectFacet(
					composerFacet.getVersion(ComposerFacetConstants.COMPOSER_COMPONENT_VERSION_1), composerFacet,
					monitor);
		} catch (CoreException ex) {
			Logger.logException(ex.getMessage(), ex);
		}
	}

}
