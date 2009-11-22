package org.eclipse.php.core.tests.performance;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.php.core.tests.PHPCoreTests;
import org.eclipse.test.performance.Dimension;
import org.eclipse.test.performance.PerformanceTestCase;

public class BuildProjectTest extends PerformanceTestCase {

	private static final Dimension[] dimensions = new Dimension[] {
			Dimension.CPU_TIME, Dimension.USED_JAVA_HEAP };

	public void testBuildProject() throws Exception {
		tagAsSummary("Build '" + AllTests.PROJECT + "' project", dimensions);

		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(
				AllTests.PROJECT);
		startMeasuring();
		project.refreshLocal(IResource.DEPTH_INFINITE, null);
		PHPCoreTests.waitForIndexer();
		stopMeasuring();

		commitMeasurements();
		assertPerformance();
	}
}
