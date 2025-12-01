/*******************************************************************************
 * Copyright (c) 2012-2018 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *     Martin Eisengardt <martin.eisengardt@fiducia.de>
 *******************************************************************************/
package org.eclipse.php.internal.core.facet;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.*;
import org.eclipse.php.core.PHPVersion;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.wst.common.project.facet.core.IFacetedProject;
import org.eclipse.wst.common.project.facet.core.IProjectFacet;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;
import org.eclipse.wst.common.project.facet.core.ProjectFacetsManager;

public class PHPFacets {

	/**
	 * Synchronizes the php version for facets
	 * 
	 * @param project
	 * @return the status of setting the version
	 */
	public static IStatus setFacetedVersion(IProject project, PHPVersion version) {
		if (isFacetedProject(project)) {
			try {
				final IProjectFacetVersion facetedVersion = convertToFacetVersion(version);
				final IProjectFacet phpFacet = ProjectFacetsManager.getProjectFacet(PHPFacetsConstants.PHP_COMPONENT);
				final IFacetedProject faceted = ProjectFacetsManager.create(project);
				if (!facetedVersion.equals(faceted.getInstalledVersion(phpFacet))) {
					final Set<IFacetedProject.Action> actions = new HashSet<>();
					actions.add(new IFacetedProject.Action(IFacetedProject.Action.Type.VERSION_CHANGE, facetedVersion,
							null));
					faceted.modify(actions, new NullProgressMonitor());
				}
			} catch (CoreException ex) {
				return new Status(IStatus.ERROR, PHPCorePlugin.ID, Messages.PHPFacets_SettingVersionFailed, ex);
			} catch (IllegalArgumentException ex) {
				return new Status(IStatus.ERROR, PHPCorePlugin.ID, Messages.PHPFacets_SettingVersionFailed, ex);
			}
		}
		return Status.OK_STATUS;
	}

	/**
	 * Returns true if the given project is a faceted project and the php core
	 * facet is installed
	 * 
	 * @param project
	 *            the project
	 * @return true if the php core facet is installed.
	 */
	public static boolean isFacetedProject(IProject project) {
		try {
			final IFacetedProject faceted = ProjectFacetsManager.create(project);
			if (faceted != null) {
				IProjectFacet coreFacet = ProjectFacetsManager.getProjectFacet(PHPFacetsConstants.PHP_CORE_COMPONENT);
				IProjectFacet phpFacet = ProjectFacetsManager.getProjectFacet(PHPFacetsConstants.PHP_COMPONENT);
				if (faceted.hasProjectFacet(coreFacet) && faceted.hasProjectFacet(phpFacet)) {
					return true;
				}
			}
		} catch (CoreException ex) {
			// silently ignore
		}
		return false;
	}

	/**
	 * Returns the php version from given faceted project; before invoking this
	 * method ensure that {@link #isFacetedProject(IProject)} return true.
	 * 
	 * @param project
	 *            the php faceted project
	 * @return the php version
	 */
	public static PHPVersion getPHPVersionFromFacets(IProject project) {
		try {
			final IFacetedProject faceted = ProjectFacetsManager.create(project);
			if (faceted != null) {
				IProjectFacet phpFacet = ProjectFacetsManager.getProjectFacet(PHPFacetsConstants.PHP_COMPONENT);
				final IProjectFacetVersion version = faceted.getInstalledVersion(phpFacet);
				if (PHPFacetsConstants.PHP_COMPONENT_VERSION_5.equals(version.getVersionString())) {
					return PHPVersion.PHP5;
				}
				if (PHPFacetsConstants.PHP_COMPONENT_VERSION_5_3.equals(version.getVersionString())) {
					return PHPVersion.PHP5_3;
				}
				if (PHPFacetsConstants.PHP_COMPONENT_VERSION_5_4.equals(version.getVersionString())) {
					return PHPVersion.PHP5_4;
				}
				if (PHPFacetsConstants.PHP_COMPONENT_VERSION_5_5.equals(version.getVersionString())) {
					return PHPVersion.PHP5_5;
				}
				if (PHPFacetsConstants.PHP_COMPONENT_VERSION_5_6.equals(version.getVersionString())) {
					return PHPVersion.PHP5_6;
				}
				if (PHPFacetsConstants.PHP_COMPONENT_VERSION_7.equals(version.getVersionString())) {
					return PHPVersion.PHP7_0;
				}
				if (PHPFacetsConstants.PHP_COMPONENT_VERSION_7_1.equals(version.getVersionString())) {
					return PHPVersion.PHP7_1;
				}
				if (PHPFacetsConstants.PHP_COMPONENT_VERSION_7_2.equals(version.getVersionString())) {
					return PHPVersion.PHP7_2;
				}
				if (PHPFacetsConstants.PHP_COMPONENT_VERSION_7_3.equals(version.getVersionString())) {
					return PHPVersion.PHP7_3;
				}
				if (PHPFacetsConstants.PHP_COMPONENT_VERSION_7_4.equals(version.getVersionString())) {
					return PHPVersion.PHP7_4;
				}
				if (PHPFacetsConstants.PHP_COMPONENT_VERSION_8_0.equals(version.getVersionString())) {
					return PHPVersion.PHP8_0;
				}
				if (PHPFacetsConstants.PHP_COMPONENT_VERSION_8_1.equals(version.getVersionString())) {
					return PHPVersion.PHP8_1;
				}
				if (PHPFacetsConstants.PHP_COMPONENT_VERSION_8_2.equals(version.getVersionString())) {
					return PHPVersion.PHP8_2;
				}
				if (PHPFacetsConstants.PHP_COMPONENT_VERSION_8_3.equals(version.getVersionString())) {
					return PHPVersion.PHP8_3;
				}
				if (PHPFacetsConstants.PHP_COMPONENT_VERSION_8_4.equals(version.getVersionString())) {
					return PHPVersion.PHP8_4;
				}
				if (PHPFacetsConstants.PHP_COMPONENT_VERSION_8_5.equals(version.getVersionString())) {
					return PHPVersion.PHP8_5;
				}
			}
		} catch (CoreException ex) {
			// silently ignore
		}
		// defaults to php latest
		return PHPVersion.getLatestVersion();
	}

	/**
	 * Returns the php facet version from given php version
	 * 
	 * @param version
	 *            php version
	 * @return the php facets version
	 */
	public static IProjectFacetVersion convertToFacetVersion(PHPVersion version) {
		IProjectFacet phpFacet = ProjectFacetsManager.getProjectFacet(PHPFacetsConstants.PHP_COMPONENT);
		switch (version) {
		case PHP5:
			return phpFacet.getVersion(PHPFacetsConstants.PHP_COMPONENT_VERSION_5);
		case PHP5_3:
			return phpFacet.getVersion(PHPFacetsConstants.PHP_COMPONENT_VERSION_5_3);
		case PHP5_4:
			return phpFacet.getVersion(PHPFacetsConstants.PHP_COMPONENT_VERSION_5_4);
		case PHP5_5:
			return phpFacet.getVersion(PHPFacetsConstants.PHP_COMPONENT_VERSION_5_5);
		case PHP5_6:
			return phpFacet.getVersion(PHPFacetsConstants.PHP_COMPONENT_VERSION_5_6);
		case PHP7_0:
			return phpFacet.getVersion(PHPFacetsConstants.PHP_COMPONENT_VERSION_7);
		case PHP7_1:
			return phpFacet.getVersion(PHPFacetsConstants.PHP_COMPONENT_VERSION_7_1);
		case PHP7_2:
			return phpFacet.getVersion(PHPFacetsConstants.PHP_COMPONENT_VERSION_7_2);
		case PHP7_3:
			return phpFacet.getVersion(PHPFacetsConstants.PHP_COMPONENT_VERSION_7_3);
		case PHP7_4:
			return phpFacet.getVersion(PHPFacetsConstants.PHP_COMPONENT_VERSION_7_4);
		case PHP8_0:
			return phpFacet.getVersion(PHPFacetsConstants.PHP_COMPONENT_VERSION_8_0);
		case PHP8_1:
			return phpFacet.getVersion(PHPFacetsConstants.PHP_COMPONENT_VERSION_8_1);
		case PHP8_2:
			return phpFacet.getVersion(PHPFacetsConstants.PHP_COMPONENT_VERSION_8_2);
		case PHP8_3:
			return phpFacet.getVersion(PHPFacetsConstants.PHP_COMPONENT_VERSION_8_3);
		case PHP8_4:
			return phpFacet.getVersion(PHPFacetsConstants.PHP_COMPONENT_VERSION_8_4);
		case PHP8_5:
		default:
			return phpFacet.getVersion(PHPFacetsConstants.PHP_COMPONENT_VERSION_8_5);
		}
	}

	/**
	 * Adds PHP facet for project
	 * 
	 * @param project
	 *            Project to add facet for
	 * @param phpVersion
	 *            PHP version to properly set facet
	 * @param monitor
	 * @throws CoreException
	 */
	public static void createFacetedProject(IProject project, PHPVersion phpVersion, IProgressMonitor monitor)
			throws CoreException {
		final IFacetedProject facetedProject = ProjectFacetsManager.create(project, true, monitor);

		// set the fixed facets (they will not be removable by the user)
		// the php.component facet will be set to the correct version
		// programmatically
		final Set<IProjectFacet> fixedFacets = new HashSet<>();
		IProjectFacet coreFacet = ProjectFacetsManager.getProjectFacet(PHPFacetsConstants.PHP_CORE_COMPONENT);
		fixedFacets.add(coreFacet);
		IProjectFacet phpFacet = ProjectFacetsManager.getProjectFacet(PHPFacetsConstants.PHP_COMPONENT);
		fixedFacets.add(phpFacet);
		facetedProject.setFixedProjectFacets(fixedFacets);

		// install the fixed facets
		if (!facetedProject.hasProjectFacet(coreFacet.getDefaultVersion())) {
			facetedProject.installProjectFacet(coreFacet.getDefaultVersion(), null, monitor);
		}
		if (!facetedProject.hasProjectFacet(phpFacet)) {
			facetedProject.installProjectFacet(convertToFacetVersion(phpVersion), null, monitor);
		}
	}

	/**
	 * Returns the faceted version of the core facet
	 * 
	 * @return core facet
	 */
	public static IProjectFacetVersion getCoreVersion() {
		IProjectFacet coreFacet = ProjectFacetsManager.getProjectFacet(PHPFacetsConstants.PHP_CORE_COMPONENT);
		return coreFacet.getDefaultVersion();
	}

}
