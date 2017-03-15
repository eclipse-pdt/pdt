/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.core.tests.searchEngine;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.index2.search.ISearchEngine;
import org.eclipse.dltk.core.index2.search.ISearchEngine.MatchRule;
import org.eclipse.dltk.core.index2.search.ISearchEngine.SearchFor;
import org.eclipse.dltk.core.index2.search.ISearchRequestor;
import org.eclipse.dltk.core.index2.search.ModelAccess;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.core.search.SearchEngine;
import org.eclipse.php.core.tests.PdttFile;
import org.eclipse.php.core.tests.TestUtils;
import org.eclipse.php.core.tests.runner.PDTTList;
import org.eclipse.php.core.tests.runner.PDTTList.AfterList;
import org.eclipse.php.core.tests.runner.PDTTList.BeforeList;
import org.eclipse.php.core.tests.runner.PDTTList.Parameters;
import org.eclipse.php.core.PHPVersion;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(PDTTList.class)
public class SearchFieldTests {

	// protected static final char SELECTION_CHAR = '|';
	@Parameters
	public static final Map<PHPVersion, String[]> TESTS = new LinkedHashMap<PHPVersion, String[]>();

	static {
		TESTS.put(PHPVersion.PHP5, new String[] { "/workspace/searchEngine/php5" });
		// TESTS.put(PHPVersion.PHP5_3, new String[] {
		// "/workspace/searchEngine/php5",
		// "/workspace/searchEngine/php53" });
	};

	protected IProject project;
	protected IFile testFile;
	protected PHPVersion phpVersion;

	public SearchFieldTests(PHPVersion version, String[] fileNames) {
		phpVersion = version;
	}

	@BeforeList
	public void setUpSuite() throws Exception {
		project = TestUtils.createProject("AutoSelectionEngine_" + phpVersion.toString());
		ResourcesPlugin.getWorkspace().getRoot().getProject("AutoSelectionEngine_" + phpVersion.toString());
		TestUtils.setProjectPhpVersion(project, phpVersion);
	}

	@AfterList
	public void tearDownSuite() throws Exception {
		TestUtils.deleteProject(project);
	}

	@After
	public void afterTest() throws Exception {
		if (testFile != null) {
			TestUtils.deleteFile(testFile);
		}
	}

	@Test
	public void search(String fileName) throws Exception {
		final PdttFile pdttFile = new PdttFile(fileName);

		List<String> paths = getSelection(pdttFile.getFile());
		int occurrences = Integer.parseInt(pdttFile.getExpected().trim());

		boolean proposalsEqual = true;
		if (paths.size() == occurrences) {
		} else {
			proposalsEqual = false;
		}

		if (!proposalsEqual) {
			StringBuilder errorBuf = new StringBuilder();
			errorBuf.append("\nEXPECTED OCCURRENCE TIMES:\n-----------------------------\n");
			errorBuf.append(pdttFile.getExpected());
			errorBuf.append("\nACTUAL OCCURRENCE TIMES:\n-----------------------------\n");
			errorBuf.append(paths.size());
			fail(errorBuf.toString());
		}
	}

	/**
	 * Creates test file with the specified content and calculates the source
	 * range for the selection. Selection characters themself are stripped off.
	 * 
	 * @param data
	 *            File data
	 * @return offset where's the offset character set.
	 * @throws Exception
	 */
	protected void createFile(String data) throws Exception {
		testFile = TestUtils.createFile(project, "test.php", data);
		TestUtils.waitForIndexer();
	}

	protected ISourceModule getSourceModule() {
		return DLTKCore.createSourceModuleFrom(testFile);
	}

	protected static int getSearchFlags(boolean includeInterp) {
		int flags = IDLTKSearchScope.SOURCES | IDLTKSearchScope.APPLICATION_LIBRARIES;
		if (includeInterp)
			flags |= IDLTKSearchScope.SYSTEM_LIBRARIES;
		return flags;
	}

	protected List<String> getSelection(String data) throws Exception {
		createFile(data);
		ISourceModule sourceModule = DLTKCore.createSourceModuleFrom(testFile);

		IDLTKSearchScope scope = SearchEngine.createSearchScope(sourceModule, getSearchFlags(false));
		ISearchEngine searchEngine = ModelAccess.getSearchEngine(scope.getLanguageToolkit());
		final List<String> paths = new ArrayList<String>();
		if (searchEngine != null) {
			ISearchRequestor requestor = new ISearchRequestor() {
				public void match(int elementType, int flags, int offset, int length, int nameOffset, int nameLength,
						String elementName, String metadata, String doc, String qualifier, String parent,
						ISourceModule sourceModule, boolean isReference) {

					paths.add(sourceModule.getPath().toString());
				}
			};

			searchEngine.search(IModelElement.FIELD, null, "$testField", 0, 0, 0, SearchFor.ALL_OCCURRENCES,
					MatchRule.EXACT, scope, requestor, new NullProgressMonitor());

		}
		return paths;
	}
}
