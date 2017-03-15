/*******************************************************************************
 * Copyright (c) 2009, 2014, 2016 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *     Dawid Pakuła - convert to JUnit4
 *******************************************************************************/
package org.eclipse.php.core.tests.selection;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ISourceRange;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.SourceRange;
import org.eclipse.php.core.tests.PdttFile;
import org.eclipse.php.core.tests.TestSuiteWatcher;
import org.eclipse.php.core.tests.TestUtils;
import org.eclipse.php.core.tests.codeassist.CodeAssistPdttFile;
import org.eclipse.php.core.tests.codeassist.CodeAssistPdttFile.ExpectedProposal;
import org.eclipse.php.core.tests.runner.PDTTList;
import org.eclipse.php.core.tests.runner.PDTTList.AfterList;
import org.eclipse.php.core.tests.runner.PDTTList.BeforeList;
import org.eclipse.php.core.tests.runner.PDTTList.Parameters;
import org.eclipse.php.core.PHPVersion;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;
import org.junit.After;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.RunWith;

@RunWith(PDTTList.class)
public class SelectionEngineTests {

	@ClassRule
	public static TestWatcher watcher = new TestSuiteWatcher();

	protected static final String SELECTION_CHAR = "|";
	@Parameters
	public static final Map<PHPVersion, String[]> TESTS = new LinkedHashMap<PHPVersion, String[]>();

	static {
		TESTS.put(PHPVersion.PHP5, new String[] { "/workspace/selection/php5" });
		TESTS.put(PHPVersion.PHP5_3, new String[] { "/workspace/selection/php5", "/workspace/selection/php53" });
		TESTS.put(PHPVersion.PHP5_4, new String[] { "/workspace/selection/php5", "/workspace/selection/php53",
				"/workspace/selection/php54" });
		TESTS.put(PHPVersion.PHP5_5, new String[] { "/workspace/selection/php5", "/workspace/selection/php53",
				"/workspace/selection/php54", "/workspace/selection/php55" });
		TESTS.put(PHPVersion.PHP5_6, new String[] { "/workspace/selection/php5", "/workspace/selection/php53",
				"/workspace/selection/php54", "/workspace/selection/php55", "/workspace/selection/php56" });
		TESTS.put(PHPVersion.PHP7_0,
				new String[] { "/workspace/selection/php5", "/workspace/selection/php53", "/workspace/selection/php54",
						"/workspace/selection/php55", "/workspace/selection/php56", "/workspace/selection/php7" });
		TESTS.put(PHPVersion.PHP7_1,
				new String[] { "/workspace/selection/php5", "/workspace/selection/php53", "/workspace/selection/php54",
						"/workspace/selection/php55", "/workspace/selection/php56", "/workspace/selection/php7",
						"/workspace/selection/php71" });
	};

	protected IProject project;
	protected IFile testFile = null;
	protected List<IFile> otherFiles = new ArrayList<IFile>();
	protected PHPVersion version;

	public SelectionEngineTests(PHPVersion version, String[] fileNames) {
		this.version = version;
	}

	@BeforeList
	public void setUpSuite() throws Exception {
		project = TestUtils.createProject("AutoSelectionEngine_" + version.toString());
		TestUtils.setProjectPhpVersion(project, version);
	}

	@AfterList
	public void tearDownSuite() throws Exception {
		TestUtils.deleteProject(project);
	}

	@Test
	public void selection(String fileName) throws Exception {
		final CodeAssistPdttFile pdttFile = new CodeAssistPdttFile(fileName);

		IModelElement[] elements = getSelection(pdttFile);
		ExpectedProposal[] expectedProposals = pdttFile.getExpectedProposals();

		boolean addFileAndNamespace = pdttFile.getOtherFiles().length > 0;
		boolean proposalsEqual = true;
		if (elements.length == expectedProposals.length) {
			for (ExpectedProposal expectedProposal : pdttFile.getExpectedProposals()) {
				boolean found = false;
				for (IModelElement modelElement : elements) {
					if (modelElement.getElementType() == expectedProposal.type
							&& getProposalName(modelElement, addFileAndNamespace)
									.equalsIgnoreCase(expectedProposal.name)) {
						found = true;
						break;
					}
				}
				if (!found) {
					proposalsEqual = false;
					break;
				}
			}
		} else {
			proposalsEqual = false;
		}

		if (!proposalsEqual) {
			StringBuilder errorBuf = new StringBuilder();
			errorBuf.append("\nEXPECTED ELEMENTS LIST:\n-----------------------------\n");
			errorBuf.append(pdttFile.getExpected());
			errorBuf.append("\nACTUAL ELEMENTS LIST:\n-----------------------------\n");
			for (IModelElement modelElement : elements) {
				switch (modelElement.getElementType()) {
				case IModelElement.FIELD:
					errorBuf.append("field");
					break;
				case IModelElement.METHOD:
					errorBuf.append("method");
					break;
				case IModelElement.TYPE:
					errorBuf.append("type");
					break;
				}
				errorBuf.append('(').append(getProposalName(modelElement, addFileAndNamespace)).append(")\n");
			}
			fail(errorBuf.toString());
		}
	}

	@After
	public void after() throws Exception {
		if (testFile != null) {
			TestUtils.deleteFile(testFile);
			testFile = null;
		}
		for (IFile otherFile : otherFiles) {
			TestUtils.deleteFile(otherFile);
		}
		otherFiles.clear();
	}

	protected String getProposalName(IModelElement modelElement, boolean addFileAndNamespace) {
		StringBuilder out = new StringBuilder();
		if (addFileAndNamespace) {
			IType namespace = PHPModelUtils.getCurrentNamespace(modelElement);
			String namespaceName = namespace != null ? namespace.getElementName() : "";
			out.append(modelElement.getPath().lastSegment()).append('|').append(namespaceName).append('|')
					.append(modelElement.getElementName());
		} else {
			out.append(modelElement.getElementName());
		}
		return out.toString();
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
	protected ISourceRange createFile(PdttFile pdttFile) throws Exception {
		final String cursor = getCursor(pdttFile) != null ? getCursor(pdttFile) : SELECTION_CHAR;
		String data = pdttFile.getFile();
		int left = data.indexOf(cursor);
		if (left == -1) {
			throw new IllegalArgumentException("Selection characters are not set");
		}
		// replace the left character
		data = data.substring(0, left) + data.substring(left + 1);

		int right = data.indexOf(cursor);
		if (right == -1) {
			throw new IllegalArgumentException("Selection is not closed");
		}
		data = data.substring(0, right) + data.substring(right + 1);

		testFile = TestUtils.createFile(project, "FILE.php", data);

		return new SourceRange(left, right - left);
	}

	protected ISourceModule getSourceModule() {
		return DLTKCore.createSourceModuleFrom(testFile);
	}

	protected IModelElement[] getSelection(CodeAssistPdttFile pdttFile) throws Exception {
		ISourceRange range = createFile(pdttFile);
		for (int i = 0, len = pdttFile.getOtherFiles().length; i < len; i++) {
			otherFiles.add(TestUtils.createFile(project, "FILE" + i + ".php", pdttFile.getOtherFile(i)));
		}
		TestUtils.waitForIndexer();
		ISourceModule sourceModule = getSourceModule();
		IModelElement[] elements = sourceModule.codeSelect(range.getOffset(), range.getLength());
		return elements;
	}

	private static String getCursor(PdttFile pdttFile) {
		Map<String, String> config = pdttFile.getConfig();
		return config.get("cursor");
	}

}