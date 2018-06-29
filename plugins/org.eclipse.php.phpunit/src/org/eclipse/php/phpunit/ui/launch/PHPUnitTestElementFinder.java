/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.phpunit.ui.launch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.IType;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.php.phpunit.PHPUnitMessages;
import org.eclipse.php.phpunit.model.PHPUnitSearchEngine;

public class PHPUnitTestElementFinder {

	private IType[] PHP_UNIT_CASE_CACHED;
	private IType[] PHP_UNIT_SUITE_CACHED;
	private IType[] PHP_UNIT_CASES_AND_SUITES;

	public IRunnableWithProgress search(final IScriptProject phpProject) {
		return pm -> {
			final PHPUnitSearchEngine searchEngine = new PHPUnitSearchEngine(phpProject);

			pm.beginTask(PHPUnitMessages.PHPUnitSearchEngine_Searching, IProgressMonitor.UNKNOWN);
			if (PHP_UNIT_CASE_CACHED == null) {
				List<IType> cases = searchEngine.findTestCaseBaseClasses(phpProject, false,
						SubMonitor.convert(pm, IProgressMonitor.UNKNOWN));
				updateCasesCache(cases);
			}
			if (PHP_UNIT_SUITE_CACHED == null) {
				List<IType> suites = searchEngine.findTestSuiteBaseClasses(phpProject, false,
						SubMonitor.convert(pm, IProgressMonitor.UNKNOWN));
				updateSuitesCache(suites);
			}
			pm.done();
		};
	}

	private void updateCasesCache(List<IType> types) {
		if (PHP_UNIT_CASE_CACHED == null) {
			PHP_UNIT_CASE_CACHED = types.toArray(new IType[0]);
			List<IType> casesList = Arrays.asList(PHP_UNIT_CASE_CACHED);
			List<IType> casesAndSuitesList = new ArrayList<>();
			if (PHP_UNIT_SUITE_CACHED != null) {
				List<IType> suitesList = Arrays.asList(PHP_UNIT_SUITE_CACHED);
				casesAndSuitesList.addAll(suitesList);
			}
			casesAndSuitesList.addAll(casesList);
			PHP_UNIT_CASES_AND_SUITES = casesAndSuitesList.toArray(new IType[casesAndSuitesList.size()]);
		}
	}

	private void updateSuitesCache(List<IType> types) {
		if (PHP_UNIT_SUITE_CACHED == null) {
			PHP_UNIT_SUITE_CACHED = types.toArray(new IType[0]);
			List<IType> suitesList = Arrays.asList(PHP_UNIT_SUITE_CACHED);
			List<IType> casesAndSuitesList = new ArrayList<>();
			if (PHP_UNIT_CASE_CACHED != null) {
				List<IType> casesList = Arrays.asList(PHP_UNIT_CASE_CACHED);
				casesAndSuitesList.addAll(casesList);
			}
			casesAndSuitesList.addAll(suitesList);
			PHP_UNIT_CASES_AND_SUITES = casesAndSuitesList.toArray(new IType[casesAndSuitesList.size()]);
		}
	}

	protected void cleareCaches() {
		PHP_UNIT_SUITE_CACHED = null;
		PHP_UNIT_CASE_CACHED = null;
		PHP_UNIT_CASES_AND_SUITES = null;
	}

	public IType[] getPHP_UNIT_CASE_CACHED() {
		return PHP_UNIT_CASE_CACHED;
	}

	public IType[] getPHP_UNIT_SUITE_CACHED() {
		return PHP_UNIT_SUITE_CACHED;
	}

	public IType[] getPHP_UNIT_CASES_AND_SUITES() {
		return PHP_UNIT_CASES_AND_SUITES;
	}

}
