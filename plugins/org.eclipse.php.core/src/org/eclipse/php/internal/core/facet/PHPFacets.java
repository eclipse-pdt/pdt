package org.eclipse.php.internal.core.facet;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.wst.common.project.facet.core.IFacetedProject;
import org.eclipse.wst.common.project.facet.core.IProjectFacet;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;
import org.eclipse.wst.common.project.facet.core.ProjectFacetsManager;

public class PHPFacets {

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
			final IFacetedProject faceted = ProjectFacetsManager
					.create(project);
			if (faceted != null) {
				IProjectFacet coreFacet = ProjectFacetsManager
						.getProjectFacet(PHPFacetsConstants.PHP_CORE_COMPONENT);
				IProjectFacet phpFacet = ProjectFacetsManager
						.getProjectFacet(PHPFacetsConstants.PHP_COMPONENT);
				if (faceted.hasProjectFacet(coreFacet)
						&& faceted.hasProjectFacet(phpFacet)) {
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
	public static PHPVersion getPhpVersionFromFacets(IProject project) {
		try {
			final IFacetedProject faceted = ProjectFacetsManager
					.create(project);
			if (faceted != null) {
				IProjectFacet phpFacet = ProjectFacetsManager
						.getProjectFacet(PHPFacetsConstants.PHP_COMPONENT);
				final IProjectFacetVersion version = faceted
						.getInstalledVersion(phpFacet);
				if (PHPFacetsConstants.PHP_COMPONENT_VERSION_4.equals(version
						.getVersionString())) {
					return PHPVersion.PHP4;
				}
				if (PHPFacetsConstants.PHP_COMPONENT_VERSION_5.equals(version
						.getVersionString())) {
					return PHPVersion.PHP5;
				}
				if (PHPFacetsConstants.PHP_COMPONENT_VERSION_5_3.equals(version
						.getVersionString())) {
					return PHPVersion.PHP5_3;
				}
				if (PHPFacetsConstants.PHP_COMPONENT_VERSION_5_4.equals(version
						.getVersionString())) {
					return PHPVersion.PHP5_4;
				}
			}
		} catch (CoreException ex) {
			// silently ignore
		}
		// defaults to php 5.4
		return PHPVersion.PHP5_4;
	}

	/**
	 * Returns the php facet version from given php version
	 * 
	 * @param version
	 *            php version
	 * @return the php facets version
	 */
	public static IProjectFacetVersion convertToFacetVersion(PHPVersion version) {
		IProjectFacet phpFacet = ProjectFacetsManager
				.getProjectFacet(PHPFacetsConstants.PHP_COMPONENT);
		switch (version) {
		case PHP4:
			return phpFacet
					.getVersion(PHPFacetsConstants.PHP_COMPONENT_VERSION_4);
		case PHP5:
			return phpFacet
					.getVersion(PHPFacetsConstants.PHP_COMPONENT_VERSION_5);
		case PHP5_3:
			return phpFacet
					.getVersion(PHPFacetsConstants.PHP_COMPONENT_VERSION_5_3);
		case PHP5_4:
		default:
			return phpFacet
					.getVersion(PHPFacetsConstants.PHP_COMPONENT_VERSION_5_4);
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
	public static void createFacetedProject(IProject project,
			PHPVersion phpVersion, IProgressMonitor monitor)
			throws CoreException {
		final IFacetedProject facetedProject = ProjectFacetsManager.create(
				project, true, monitor);

		// set the fixed facets (they will not be removable by the user)
		// the php.component facet will be set to the correct version
		// programmatically
		final Set<IProjectFacet> fixedFacets = new HashSet<IProjectFacet>();
		IProjectFacet coreFacet = ProjectFacetsManager
				.getProjectFacet(PHPFacetsConstants.PHP_CORE_COMPONENT);
		fixedFacets.add(coreFacet);
		IProjectFacet phpFacet = ProjectFacetsManager
				.getProjectFacet(PHPFacetsConstants.PHP_COMPONENT);
		fixedFacets.add(phpFacet);
		facetedProject.setFixedProjectFacets(fixedFacets);

		// install the fixed facets
		facetedProject.installProjectFacet(coreFacet.getDefaultVersion(), null,
				monitor);
		facetedProject.installProjectFacet(convertToFacetVersion(phpVersion),
				null, monitor);
	}
}
