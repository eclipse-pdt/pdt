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

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.dltk.compiler.problem.IProblem;
import org.eclipse.dltk.core.CompletionProposal;
import org.eclipse.dltk.core.CompletionRequestor;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.php.core.tests.PdttFile;
import org.eclipse.php.core.tests.TestSuiteWatcher;
import org.eclipse.php.core.tests.TestUtils;
import org.eclipse.php.core.tests.codeassist.CodeAssistPdttFile.ExpectedProposal;
import org.eclipse.php.core.tests.runner.PDTTList;
import org.eclipse.php.core.tests.runner.PDTTList.AfterList;
import org.eclipse.php.core.tests.runner.PDTTList.BeforeList;
import org.eclipse.php.core.tests.runner.PDTTList.Parameters;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.codeassist.AliasType;
import org.eclipse.php.internal.core.codeassist.IPHPCompletionRequestor;
import org.eclipse.php.internal.core.documentModel.loader.PHPDocumentLoader;
import org.eclipse.php.internal.core.typeinference.FakeConstructor;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.RunWith;

@SuppressWarnings("all")
@RunWith(PDTTList.class)
public class CodeAssistTests {

	@ClassRule
	public static TestWatcher watcher = new TestSuiteWatcher();

	private static abstract class TestCompletionRequestor extends CompletionRequestor
			implements IPHPCompletionRequestor {

		private IStructuredDocument document;
		private int offset;

		public TestCompletionRequestor(IStructuredDocument document, int offset) {
			this.document = document;
			this.offset = offset;
		}

		@Override
		public IDocument getDocument() {
			return document;
		}

		@Override
		public boolean isExplicit() {
			return true;
		}

		@Override
		public int getOffset() {
			return offset;
		}

		@Override
		public void setOffset(int offset) {
			this.offset = offset;
		}

		@Override
		public boolean filter(int flag) {
			return false;
		}

		@Override
		public void addFlag(int flag) {
			// ignore
		}

	}

	@Parameters
	public static final Map<PHPVersion, String[]> TESTS = new LinkedHashMap<PHPVersion, String[]>();
	public static final String DEFAULT_CURSOR = "|";

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
		TESTS.put(PHPVersion.PHP7_1,
				new String[] { "/workspace/codeassist/php5", "/workspace/codeassist/php53",
						"/workspace/codeassist/php54", "/workspace/codeassist/php55", "/workspace/codeassist/php56",
						"/workspace/codeassist/php7", "/workspace/codeassist/php71" });
	};

	private IProject project;
	private IFile testFile;
	private List<IFile> otherFiles = null;
	private PHPVersion version;

	public CodeAssistTests(PHPVersion version, String[] fileNames) {
		this.version = version;
	}

	@BeforeList
	public void setUpSuite() throws Exception {
		project = TestUtils.createProject("CodeAssistTests_" + version.toString());
		TestUtils.setProjectPhpVersion(project, version);
	}

	@AfterList
	public void tearDownSuite() throws Exception {
		TestUtils.deleteProject(project);
	}

	@Test
	public void assist(final String fileName) throws Exception {
		final CodeAssistPdttFile pdttFile = new CodeAssistPdttFile(fileName);
		pdttFile.applyPreferences();
		final int offset = createFiles(pdttFile);
		CompletionProposal[] proposals = getProposals(DLTKCore.createSourceModuleFrom(testFile), offset);
		compareProposals(proposals, pdttFile);
		deleteFiles();
	}

	private void deleteFiles() {
		if (testFile != null) {
			TestUtils.deleteFile(testFile);
		}
		if (otherFiles != null) {
			for (IFile file : otherFiles) {
				if (file != null)
					TestUtils.deleteFile(file);
			}
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
	private int createFiles(PdttFile pdttFile) throws Exception {
		final String cursor = getCursor(pdttFile) != null ? getCursor(pdttFile) : DEFAULT_CURSOR;
		String data = pdttFile.getFile();
		String[] otherFiles = pdttFile.getOtherFiles();
		int offset = data.lastIndexOf(cursor);
		if (offset == -1) {
			throw new IllegalArgumentException("Offset character is not set");
		}
		// Replace the offset character
		data = data.substring(0, offset) + data.substring(offset + 1);
		String fileName = Paths.get(pdttFile.getFileName()).getFileName().toString();
		fileName = fileName.substring(0, fileName.indexOf('.'));
		testFile = TestUtils.createFile(project, fileName + ".php", data);
		this.otherFiles = new ArrayList<IFile>(otherFiles.length);
		int i = 0;
		for (String otherFileContent : otherFiles) {
			IFile tmp = TestUtils.createFile(project, String.format("test%s.php", i), otherFileContent);
			this.otherFiles.add(i, tmp);
			i++;
		}
		TestUtils.waitForIndexer();
		return offset;
	}

	private CompletionProposal[] getProposals(ISourceModule sourceModule, int offset) throws ModelException {
		IStructuredDocument document = (IStructuredDocument) new PHPDocumentLoader().createNewStructuredDocument();
		String content = new String(sourceModule.getSourceAsCharArray());
		document.set(content);
		final List<CompletionProposal> proposals = new LinkedList<CompletionProposal>();
		sourceModule.codeComplete(offset, new TestCompletionRequestor(document, offset) {
			public void accept(CompletionProposal proposal) {
				proposals.add(proposal);
			}

			@Override
			public void completionFailure(IProblem problem) {
				Logger.log(Logger.ERROR, problem.getMessage());
			}
		}, 60000);
		return proposals.toArray(new CompletionProposal[proposals.size()]);
	}

	private void compareProposals(CompletionProposal[] proposals, CodeAssistPdttFile pdttFile) throws Exception {
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

	private String getCursor(PdttFile pdttFile) {
		Map<String, String> config = pdttFile.getConfig();
		return config.get("cursor");
	}
}
