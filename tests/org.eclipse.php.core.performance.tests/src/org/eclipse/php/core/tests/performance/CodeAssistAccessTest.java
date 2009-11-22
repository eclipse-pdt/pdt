package org.eclipse.php.core.tests.performance;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.dltk.core.CompletionProposal;
import org.eclipse.dltk.core.CompletionRequestor;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.test.performance.Dimension;
import org.eclipse.test.performance.PerformanceTestCase;

public class CodeAssistAccessTest extends PerformanceTestCase {

	private static final int TESTS_NUM = 20;
	private ISourceModule sourceModule;
	private int offset;

	protected void setUp() throws Exception {
		super.setUp();

		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(
				AllTests.PROJECT);
		IFile file = project.getFile("bin/zf.php");
		sourceModule = (ISourceModule) DLTKCore.create(file);
		offset = sourceModule.getSourceRange().getLength() - 1;
	}

	public void testCodeAssist() throws Exception {
		tagAsGlobalSummary("Code assist in global scope", Dimension.CPU_TIME);
		for (int i = 0; i < TESTS_NUM; i++) {
			startMeasuring();
			sourceModule.codeComplete(offset, new CompletionRequestor() {
				public void accept(CompletionProposal proposal) {
				}
			}, 10000);
			stopMeasuring();
		}
		commitMeasurements();
		assertPerformance();
	}
}
