package org.eclipse.php.core.tests.performance;

import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.core.index2.search.ISearchEngine.MatchRule;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.core.search.SearchEngine;
import org.eclipse.php.internal.core.PHPLanguageToolkit;
import org.eclipse.php.internal.core.model.PhpModelAccess;
import org.eclipse.test.performance.Dimension;
import org.eclipse.test.performance.PerformanceTestCase;

public class PHPModelAccessTest extends PerformanceTestCase {

	private static final Dimension[] dimensions = new Dimension[] {
			Dimension.CPU_TIME, Dimension.USED_JAVA_HEAP };
	private static final int TESTS_NUM = 20;
	private PhpModelAccess modelAccess;
	private IDLTKSearchScope scope;

	protected void setUp() throws Exception {
		super.setUp();
		modelAccess = PhpModelAccess.getDefault();
		scope = SearchEngine.createWorkspaceScope(PHPLanguageToolkit
				.getDefault());
	}

	public void testSearchAllTypes() {
		tagAsSummary("Search all types", dimensions);
		for (int i = 0; i < TESTS_NUM; i++) {
			startMeasuring();
			modelAccess.findTypes("", MatchRule.PREFIX, 0, 0, scope, null);
			stopMeasuring();
		}
		commitMeasurements();
		assertPerformance();
	}

	public void testSearchAllFunctions() {
		tagAsSummary("Search all functions", dimensions);
		for (int i = 0; i < TESTS_NUM; i++) {
			startMeasuring();
			modelAccess.findMethods("", MatchRule.PREFIX, Modifiers.AccGlobal,
					0, scope, null);
			stopMeasuring();
		}
		commitMeasurements();
		assertPerformance();
	}

	public void testSearchGlobalVariables() {
		tagAsSummary("Search all global variables", dimensions);
		for (int i = 0; i < TESTS_NUM; i++) {
			startMeasuring();
			modelAccess.findFields("", MatchRule.PREFIX, Modifiers.AccGlobal,
					0, scope, null);
			stopMeasuring();
		}
		commitMeasurements();
		assertPerformance();
	}

	public void testSearchIncludeStatements() {
		tagAsSummary("Search all include statements", dimensions);
		for (int i = 0; i < TESTS_NUM; i++) {
			startMeasuring();
			modelAccess.findIncludes("", MatchRule.PREFIX, scope, null);
			stopMeasuring();
		}
		commitMeasurements();
		assertPerformance();
	}
}
