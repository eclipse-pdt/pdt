package org.eclipse.php.core.tests.performance;

import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.core.index2.search.ISearchEngine.MatchRule;
import org.eclipse.dltk.core.search.SearchEngine;
import org.eclipse.php.internal.core.PHPLanguageToolkit;
import org.eclipse.php.internal.core.model.PhpModelAccess;
import org.eclipse.test.performance.Dimension;
import org.eclipse.test.performance.PerformanceTestCase;

public class PHPModelAccessTest extends PerformanceTestCase {

	private PhpModelAccess modelAccess;

	protected void setUp() throws Exception {
		super.setUp();
		modelAccess = PhpModelAccess.getDefault();
	}

	public void testSearchAllTypes() {
		tagAsSummary("Search All Types", Dimension.CPU_TIME);
		for (int i = 0; i < 10; i++) {
			startMeasuring();
			modelAccess.findTypes("", MatchRule.PREFIX, 0, 0, SearchEngine
					.createWorkspaceScope(PHPLanguageToolkit.getDefault()),
					null);
			stopMeasuring();
		}
		commitMeasurements();
		assertPerformance();
	}

	public void testSearchAllFunctions() {
		tagAsSummary("Search All Functions", Dimension.CPU_TIME);
		for (int i = 0; i < 10; i++) {
			startMeasuring();
			modelAccess.findMethods("", MatchRule.PREFIX, Modifiers.AccGlobal,
					0, SearchEngine.createWorkspaceScope(PHPLanguageToolkit
							.getDefault()), null);
			stopMeasuring();
		}
		commitMeasurements();
		assertPerformance();
	}

	public void testSearchGlobalVariables() {
		tagAsSummary("Search All Global Variables", Dimension.CPU_TIME);
		for (int i = 0; i < 10; i++) {
			startMeasuring();
			modelAccess.findFields("", MatchRule.PREFIX, Modifiers.AccGlobal,
					0, SearchEngine.createWorkspaceScope(PHPLanguageToolkit
							.getDefault()), null);
			stopMeasuring();
		}
		commitMeasurements();
		assertPerformance();
	}
}
