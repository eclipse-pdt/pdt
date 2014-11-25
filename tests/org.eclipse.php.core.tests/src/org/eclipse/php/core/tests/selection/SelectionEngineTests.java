/*******************************************************************************
 * Copyright (c) 2009, 2014 IBM Corporation and others.
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

import java.io.ByteArrayInputStream;
import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ISourceRange;
import org.eclipse.dltk.core.SourceRange;
import org.eclipse.php.core.tests.PHPCoreTests;
import org.eclipse.php.core.tests.codeassist.CodeAssistPdttFile;
import org.eclipse.php.core.tests.codeassist.CodeAssistPdttFile.ExpectedProposal;
import org.eclipse.php.core.tests.runner.PDTTList;
import org.eclipse.php.core.tests.runner.PDTTList.AfterList;
import org.eclipse.php.core.tests.runner.PDTTList.BeforeList;
import org.eclipse.php.core.tests.runner.PDTTList.Parameters;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.project.PHPNature;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(PDTTList.class)
public class SelectionEngineTests {

	protected static final char SELECTION_CHAR = '|';
	@Parameters
	public static final Map<PHPVersion, String[]> TESTS = new LinkedHashMap<PHPVersion, String[]>();
	static {
		TESTS.put(PHPVersion.PHP5, new String[] { "/workspace/selection/php5" });
		TESTS.put(PHPVersion.PHP5_3, new String[] {
				"/workspace/selection/php5", "/workspace/selection/php53" });
		TESTS.put(PHPVersion.PHP5_4, new String[] {
				"/workspace/selection/php5", "/workspace/selection/php53",
				"/workspace/selection/php54" });
		TESTS.put(PHPVersion.PHP5_5, new String[] {
				"/workspace/selection/php5", "/workspace/selection/php53",
				"/workspace/selection/php54", "/workspace/selection/php55" });
		TESTS.put(PHPVersion.PHP5_6, new String[] {
				"/workspace/selection/php5", "/workspace/selection/php53",
				"/workspace/selection/php54", "/workspace/selection/php55",
				"/workspace/selection/php56" });
	};

	protected IProject project;
	protected IFile testFile;
	protected PHPVersion version;

	public SelectionEngineTests(PHPVersion version, String[] fileNames) {
		this.version = version;
	}

	@BeforeList
	public void setUpSuite() throws Exception {
		project = ResourcesPlugin.getWorkspace().getRoot()
				.getProject("AutoSelectionEngine_" + version.toString());
		if (project.exists()) {
			return;
		}

		project.create(null);
		project.open(null);

		// configure nature
		IProjectDescription desc = project.getDescription();
		desc.setNatureIds(new String[] { PHPNature.ID });
		project.setDescription(desc, null);
		PHPCoreTests.setProjectPhpVersion(project, version);
	}

	@AfterList
	public void tearDownSuite() throws Exception {
		project.close(null);
		project.delete(true, true, null);
		project = null;
	}

	@Test
	public void selection(String fileName) throws Exception {
		final CodeAssistPdttFile pdttFile = new CodeAssistPdttFile(fileName);

		IModelElement[] elements = getSelection(pdttFile.getFile());
		ExpectedProposal[] expectedProposals = pdttFile.getExpectedProposals();

		boolean proposalsEqual = true;
		if (elements.length == expectedProposals.length) {
			for (ExpectedProposal expectedProposal : pdttFile
					.getExpectedProposals()) {
				boolean found = false;
				for (IModelElement modelElement : elements) {
					if (modelElement.getElementType() == expectedProposal.type
							&& modelElement.getElementName().equalsIgnoreCase(
									expectedProposal.name)) {
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
				errorBuf.append('(').append(modelElement.getElementName())
						.append(")\n");
			}
			fail(errorBuf.toString());
		}
	}

	@After
	public void after() throws Exception {
		if (testFile != null) {
			testFile.delete(true, null);
			testFile = null;
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
	protected ISourceRange createFile(String data) throws Exception {
		int left = data.indexOf(SELECTION_CHAR);
		if (left == -1) {
			throw new IllegalArgumentException(
					"Selection characters are not set");
		}
		// replace the left character
		data = data.substring(0, left) + data.substring(left + 1);

		int right = data.indexOf(SELECTION_CHAR);
		if (right == -1) {
			throw new IllegalArgumentException("Selection is not closed");
		}
		data = data.substring(0, right) + data.substring(right + 1);

		testFile = project.getFile("test.php");
		testFile.create(new ByteArrayInputStream(data.getBytes()), true, null);
		project.refreshLocal(IResource.DEPTH_INFINITE, null);

		project.build(IncrementalProjectBuilder.FULL_BUILD, null);
		PHPCoreTests.waitForIndexer();
		// PHPCoreTests.waitForAutoBuild();

		return new SourceRange(left, right - left);
	}

	protected ISourceModule getSourceModule() {
		return DLTKCore.createSourceModuleFrom(testFile);
	}

	protected IModelElement[] getSelection(String data) throws Exception {
		ISourceRange range = createFile(data);
		ISourceModule sourceModule = DLTKCore.createSourceModuleFrom(testFile);
		IModelElement[] elements = sourceModule.codeSelect(range.getOffset(),
				range.getLength());
		return elements;
	}
}
