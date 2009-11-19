package org.eclipse.php.core.tests.performance;

import junit.framework.Assert;

import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.index2.search.ISearchEngine.MatchRule;
import org.eclipse.dltk.core.search.SearchEngine;
import org.eclipse.php.internal.core.PHPLanguageToolkit;
import org.eclipse.php.internal.core.model.PhpModelAccess;
import org.eclipse.test.performance.Dimension;
import org.eclipse.test.performance.PerformanceTestCase;

public class TypeHierarchyTest extends PerformanceTestCase {

	private IType[] exceptionType;

	protected void setUp() throws Exception {
		super.setUp();

		exceptionType = PhpModelAccess.getDefault().findTypes(
				"Zend_Exception",
				MatchRule.EXACT,
				0,
				0,
				SearchEngine.createWorkspaceScope(PHPLanguageToolkit
						.getDefault()), null);

		Assert.assertNotNull(exceptionType);
		Assert.assertEquals(exceptionType.length, 1);
	}

	public void testSuperTypeHierarchy() throws Exception {

		tagAsSummary("Building Super Type Hierarchy for 'Zend_Exception'",
				Dimension.CPU_TIME);
		for (int i = 0; i < 10; i++) {
			startMeasuring();
			exceptionType[0].newSupertypeHierarchy(null);
			stopMeasuring();
		}
		commitMeasurements();
		assertPerformance();
	}

	public void testTypeHierarchy() throws Exception {

		tagAsSummary("Building Type Hierarchy for 'Zend_Exception'",
				Dimension.CPU_TIME);
		for (int i = 0; i < 10; i++) {
			startMeasuring();
			exceptionType[0].newTypeHierarchy(null);
			stopMeasuring();
		}
		commitMeasurements();
		assertPerformance();
	}
}
