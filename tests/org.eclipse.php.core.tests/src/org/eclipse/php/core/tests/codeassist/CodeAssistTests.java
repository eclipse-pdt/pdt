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
package org.eclipse.php.core.tests.codeassist;

import java.io.ByteArrayInputStream;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import junit.extensions.TestSetup;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.dltk.core.CompletionProposal;
import org.eclipse.dltk.core.CompletionRequestor;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.php.core.tests.AbstractPDTTTest;
import org.eclipse.php.core.tests.PHPCoreTests;
import org.eclipse.php.core.tests.codeassist.CodeAssistPdttFile.ExpectedProposal;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.project.PHPNature;

public class CodeAssistTests extends AbstractPDTTTest {

	protected static final char OFFSET_CHAR = '|';
	protected static final Map<PHPVersion, String[]> TESTS = new LinkedHashMap<PHPVersion, String[]>();
	static {
		TESTS.put(PHPVersion.PHP5, new String[] { "/workspace/codeassist/php5/exclusive", "/workspace/codeassist/php5" });
		TESTS.put(PHPVersion.PHP5_3, new String[] { "/workspace/codeassist/php5", "/workspace/codeassist/php53" });
	};

	protected static IProject project;
	protected static IFile testFile;

	public static void setUpSuite() throws Exception {
		project = ResourcesPlugin.getWorkspace().getRoot().getProject("CodeAssistTests");
		if (project.exists()) {
			return;
		}

		project.create(null);
		project.open(null);

		// configure nature
		IProjectDescription desc = project.getDescription();
		desc.setNatureIds(new String[] { PHPNature.ID });
		project.setDescription(desc, null);
	}

	public static void tearDownSuite() throws Exception {
		project.close(null);
		project.delete(true, true, null);
		project = null;
	}

	public CodeAssistTests(String description) {
		super(description);
	}

	public static Test suite() {

		TestSuite suite = new TestSuite("Auto Code Assist Tests");

		for (final PHPVersion phpVersion : TESTS.keySet()) {
			TestSuite phpVerSuite = new TestSuite(phpVersion.getAlias());
			
			for (String testsDirectory : TESTS.get(phpVersion)) {

				for (final String fileName : getPDTTFiles(testsDirectory)) {
					try {
						final CodeAssistPdttFile pdttFile = new CodeAssistPdttFile(fileName);
						phpVerSuite.addTest(new CodeAssistTests(phpVersion.getAlias() + " - /" + fileName) {

							protected void setUp() throws Exception {
								PHPCoreTests.setProjectPhpVersion(project, phpVersion);
								pdttFile.applyPreferences();
							}

							protected void tearDown() throws Exception {
								if (testFile != null) {
									testFile.delete(true, null);
									testFile = null;
								}
							}

							protected void runTest() throws Throwable {
								CompletionProposal[] proposals = getProposals(pdttFile.getFile());
								compareProposals(proposals, pdttFile);
							}
						});
					} catch (final Exception e) {
						phpVerSuite.addTest(new TestCase(fileName) { // dummy test indicating PDTT file parsing failure
								protected void runTest() throws Throwable {
									throw e;
								}
							});
					}
				}
			}
			suite.addTest(phpVerSuite);
		}

		// Create a setup wrapper
		TestSetup setup = new TestSetup(suite) {
			protected void setUp() throws Exception {
				setUpSuite();
			}

			protected void tearDown() throws Exception {
				tearDownSuite();
			}
		};
		return setup;
	}

	/**
	 * Creates test file with the specified content and calculates the offset at 
	 * OFFSET_CHAR. Offset character itself is stripped off.
	 * 
	 * @param data File data
	 * @return offset where's the offset character set. 
	 * @throws Exception
	 */
	protected static int createFile(String data) throws Exception {
		int offset = data.lastIndexOf(OFFSET_CHAR);
		if (offset == -1) {
			throw new IllegalArgumentException("Offset character is not set");
		}

		// replace the offset character
		data = data.substring(0, offset) + data.substring(offset + 1);

		testFile = project.getFile("test.php");
		testFile.create(new ByteArrayInputStream(data.getBytes()), true, null);
		project.refreshLocal(IResource.DEPTH_INFINITE, null);
		project.build(IncrementalProjectBuilder.FULL_BUILD, null);

		PHPCoreTests.waitForIndexer();
		PHPCoreTests.waitForAutoBuild();

		return offset;
	}

	protected static ISourceModule getSourceModule() {
		return DLTKCore.createSourceModuleFrom(testFile);
	}

	public static CompletionProposal[] getProposals(String data) throws Exception {
		int offset = createFile(data);
		return getProposals(offset);
	}
	
	public static CompletionProposal[] getProposals(int offset) throws ModelException {
		return getProposals(getSourceModule(), offset);
	}
	
	public static CompletionProposal[] getProposals(ISourceModule sourceModule, int offset) throws ModelException {
		final List<CompletionProposal> proposals = new LinkedList<CompletionProposal>();
		sourceModule.codeComplete(offset, new CompletionRequestor() {
			public void accept(CompletionProposal proposal) {
				proposals.add(proposal);
			}
		});
		return (CompletionProposal[]) proposals.toArray(new CompletionProposal[proposals.size()]);
	}
	
	public static void compareProposals(CompletionProposal[] proposals, CodeAssistPdttFile pdttFile) throws Exception {
		ExpectedProposal[] expectedProposals = pdttFile.getExpectedProposals();

		boolean proposalsEqual = true;
		if (proposals.length == expectedProposals.length) {
			for (ExpectedProposal expectedProposal : pdttFile.getExpectedProposals()) {
				boolean found = false;
				for (CompletionProposal proposal : proposals) {
					IModelElement modelElement = proposal.getModelElement();
					if (modelElement == null) {
						if (new String(proposal.getName()).equalsIgnoreCase(expectedProposal.name)) { // keyword
							found = true;
							break;
						}
					} else if (modelElement.getElementType() == expectedProposal.type && modelElement.getElementName().equalsIgnoreCase(expectedProposal.name)) {
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
			errorBuf.append("\nEXPECTED COMPLETIONS LIST:\n-----------------------------\n");
			errorBuf.append(pdttFile.getExpected());
			errorBuf.append("\nACTUAL COMPLETIONS LIST:\n-----------------------------\n");
			for (CompletionProposal p : proposals) {
				IModelElement modelElement = p.getModelElement();
				if (modelElement == null || modelElement.getElementName() == null) {
					errorBuf.append("keyword(").append(p.getName()).append(")\n");
				} else {
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
					errorBuf.append('(').append(modelElement.getElementName()).append(")\n");
				}
			}
			fail(errorBuf.toString());
		}
	}
}
