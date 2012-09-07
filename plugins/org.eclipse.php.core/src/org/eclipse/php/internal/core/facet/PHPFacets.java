package org.eclipse.php.internal.core.facet;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.wst.common.project.facet.core.IFacetedProject;
import org.eclipse.wst.common.project.facet.core.IProjectFacet;
import org.eclipse.wst.common.project.facet.core.ProjectFacetsManager;

public class PHPFacets {

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
				new NullProgressMonitor());
		// Fetch and activate the correct php version
		switch (phpVersion) {
		case PHP4:
			facetedProject.installProjectFacet(phpFacet
					.getVersion(PHPFacetsConstants.PHP_COMPONENT_VERSION_4),
					null, monitor);
			break;
		case PHP5:
			facetedProject.installProjectFacet(phpFacet
					.getVersion(PHPFacetsConstants.PHP_COMPONENT_VERSION_5),
					null, monitor);
			break;
		case PHP5_3:
			facetedProject.installProjectFacet(phpFacet
					.getVersion(PHPFacetsConstants.PHP_COMPONENT_VERSION_5_3),
					null, monitor);
			break;
		case PHP5_4:
		default:
			facetedProject.installProjectFacet(phpFacet
					.getVersion(PHPFacetsConstants.PHP_COMPONENT_VERSION_5_4),
					null, monitor);
			break;
		}
	}
}
