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
package org.eclipse.php.core.tests.codeassist;

import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.dltk.core.CompletionProposal;
import org.eclipse.dltk.core.CompletionRequestor;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.php.core.tests.PHPCoreTests;
import org.eclipse.php.core.tests.PdttFile;
import org.eclipse.php.core.tests.codeassist.CodeAssistPdttFile.ExpectedProposal;
import org.eclipse.php.core.tests.runner.PDTTList;
import org.eclipse.php.core.tests.runner.PDTTList.AfterList;
import org.eclipse.php.core.tests.runner.PDTTList.BeforeList;
import org.eclipse.php.core.tests.runner.PDTTList.Parameters;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.codeassist.AliasType;
import org.eclipse.php.internal.core.project.PHPNature;
import org.eclipse.php.internal.core.typeinference.FakeConstructor;
import org.eclipse.wst.validation.ValidationFramework;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(PDTTList.class)
public class CodeAssistTests {

	@Parameters
	public static final Map<PHPVersion, String[]> TESTS = new LinkedHashMap<PHPVersion, String[]>();

	static {

		TESTS.put(PHPVersion.PHP5, new String[] { "/workspace/codeassist/php5/exclusive", "/workspace/codeassist/php5",
				"/workspace/codeassist/php5/classExclusive" });
		TESTS.put(PHPVersion.PHP5_3,
				new String[] { "/workspace/codeassist/php5", "/workspace/codeassist/php5/classExclusive",
						"/workspace/codeassist/php53/exclusive", "/workspace/codeassist/php53/classExclusive",
						"/workspace/codeassist/php53" });
		TESTS.put(PHPVersion.PHP5_4,
				new String[] { "/workspace/codeassist/php5", "/workspace/codeassist/php5/classExclusive",
						"/workspace/codeassist/php53", "/workspace/codeassist/php53/classExclusive",
						"/workspace/codeassist/php54", "/workspace/codeassist/php54/classExclusive" });
		TESTS.put(PHPVersion.PHP5_5, new String[] { "/workspace/codeassist/php5", "/workspace/codeassist/php53",
				"/workspace/codeassist/php54", "/workspace/codeassist/php55" });
		TESTS.put(PHPVersion.PHP5_6, new String[] { "/workspace/codeassist/php5", "/workspace/codeassist/php53",
				"/workspace/codeassist/php54", "/workspace/codeassist/php55", "/workspace/codeassist/php56" });
		TESTS.put(PHPVersion.PHP7_0,
				new String[] { "/workspace/codeassist/php5", "/workspace/codeassist/php53",
						"/workspace/codeassist/php54", "/workspace/codeassist/php55", "/workspace/codeassist/php56",
						"/workspace/codeassist/php7" });
	};

	protected static final String DEFAULT_CURSOR = "|";

	protected IProject project;
	protected IFile testFile;
	protected List<IFile> otherFiles = null;
	protected PHPVersion version;

	public CodeAssistTests(PHPVersion version, String[] fileNames) {
		this.version = version;
	}

	@BeforeList
	public void setUpSuite() throws Exception {
		project = ResourcesPlugin.getWorkspace().getRoot().getProject("CodeAssistTests_" + version.toString());
		if (project.exists()) {
			return;
		}

		if (ResourcesPlugin.getWorkspace().isAutoBuilding()) {
			ResourcesPlugin.getWorkspace().getDescription().setAutoBuilding(false);
		}

		project.create(null);
		project.open(null);

		// configure nature
		IProjectDescription desc = project.getDescription();
		desc.setNatureIds(new String[] { PHPNature.ID });
		project.setDescription(desc, null);

		// WTP validator can be disabled during code assist tests
		ValidationFramework.getDefault().suspendValidation(project, true);
		PHPCoreTests.setProjectPhpVersion(project, version);

		PHPCoreTests.index(project);
	}

	@AfterList
	public void tearDownSuite() throws Exception {
		PHPCoreTests.removeIndex(project);
		project.close(null);
		project.delete(true, true, null);
		project = null;

		if (!ResourcesPlugin.getWorkspace().isAutoBuilding()) {
			ResourcesPlugin.getWorkspace().getDescription().setAutoBuilding(true);
		}
	}

	@Test
	public void assist(String fileName) throws Exception {
		final CodeAssistPdttFile pdttFile = new CodeAssistPdttFile(fileName);
		pdttFile.applyPreferences();
		CompletionProposal[] proposals = getProposals(pdttFile);
		compareProposals(proposals, pdttFile);
	}

	@After
	public void after() throws Exception {
		if (testFile != null) {
			PHPCoreTests.removeIndex(testFile);
			testFile.delete(true, null);
			testFile = null;
		}
		if (otherFiles != null) {
			for (IFile file : otherFiles) {
				if (file != null) {
					PHPCoreTests.removeIndex(file);
					file.delete(true, null);
				}
			}
			otherFiles = null;
		}
	}

	/**
	 * Creates test file with the specified content and calculates the offset at
	 * OFFSET_CHAR. Offset character itself is stripped off.
	 * 
	 * @param data
	 *            File data
	 * @return offset where's the offset character set.
	 * @throws Exception
	 */
	protected int createFile(PdttFile pdttFile) throws Exception {
		final String cursor = getCursor(pdttFile) != null ? getCursor(pdttFile) : DEFAULT_CURSOR;
		String data = pdttFile.getFile();
		String[] otherFiles = pdttFile.getOtherFiles();
		int offset = data.lastIndexOf(cursor);
		if (offset == -1) {
			throw new IllegalArgumentException("Offset character is not set");
		}

		// replace the offset character
		data = data.substring(0, offset) + data.substring(offset + 1);
		testFile = project.getFile("test.php");
		testFile.create(new ByteArrayInputStream(data.getBytes()), true, null);
		PHPCoreTests.index(testFile);
		this.otherFiles = new ArrayList<IFile>(otherFiles.length);
		int i = 0;
		for (String otherFileContent : otherFiles) {
			IFile tmp = project.getFile(String.format("test%s.php", i));
			tmp.create(new ByteArrayInputStream(otherFileContent.getBytes()), true, null);
			this.otherFiles.add(i, tmp);
			PHPCoreTests.index(tmp);
			i++;
		}

		PHPCoreTests.waitForIndexer();
		// PHPCoreTests.waitForAutoBuild();

		return offset;
	}

	protected ISourceModule getSourceModule() {
		return DLTKCore.createSourceModuleFrom(testFile);
	}

	public CompletionProposal[] getProposals(PdttFile pdttFile) throws Exception {
		int offset = createFile(pdttFile);
		return getProposals(offset);
	}

	public CompletionProposal[] getProposals(int offset) throws ModelException {
		return getProposals(getSourceModule(), offset);
	}

	public static CompletionProposal[] getProposals(ISourceModule sourceModule, int offset) throws ModelException {
		final List<CompletionProposal> proposals = new LinkedList<CompletionProposal>();
		sourceModule.codeComplete(offset, new CompletionRequestor() {
			public void accept(CompletionProposal proposal) {
				proposals.add(proposal);
			}
		});
		return proposals.toArray(new CompletionProposal[proposals.size()]);
	}

	private static String getCursor(PdttFile pdttFile) {
		Map<String, String> config = pdttFile.getConfig();
		return config.get("cursor");
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
					} else if (modelElement.getElementType() == expectedProposal.type) {
						if (modelElement instanceof AliasType) {
							if (((AliasType) modelElement).getAlias().equals(expectedProposal.name)) {

								found = true;
								break;
							}

						} else if ((modelElement instanceof FakeConstructor)
								&& (modelElement.getParent() instanceof AliasType)) {
							if (((AliasType) modelElement.getParent()).getAlias().equals(expectedProposal.name)) {

								found = true;
								break;
							}

						} else {
							if (modelElement.getElementName().equalsIgnoreCase(expectedProposal.name)) {
								found = true;
								break;
							}
						}
					} else if (modelElement.getElementType() == expectedProposal.type
							&& new String(proposal.getName()).equalsIgnoreCase(expectedProposal.name)) {
						// for phar include
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
					if (modelElement instanceof AliasType) {
						errorBuf.append('(').append(((AliasType) modelElement).getAlias()).append(")\n");
					} else {
						errorBuf.append('(').append(modelElement.getElementName()).append(")\n");
					}
				}
			}
			fail(errorBuf.toString());
		}
	}
}
