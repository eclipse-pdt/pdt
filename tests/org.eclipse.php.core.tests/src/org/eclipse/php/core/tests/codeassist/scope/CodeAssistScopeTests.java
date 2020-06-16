/*******************************************************************************
 * Copyright (c) 2009, 2018 IBM Corporation and others.
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *     Dawid Pakuła - Assist Scope Tests
 *******************************************************************************/
package org.eclipse.php.core.tests.codeassist.scope;

import static org.junit.Assert.fail;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.dltk.compiler.env.IModuleSource;
import org.eclipse.dltk.core.CompletionProposal;
import org.eclipse.dltk.core.CompletionRequestor;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.internal.core.ModelManager;
import org.eclipse.jface.text.IDocument;
import org.eclipse.php.core.PHPVersion;
import org.eclipse.php.core.codeassist.CompletionCompanion;
import org.eclipse.php.core.codeassist.ICompletionScope;
import org.eclipse.php.core.project.ProjectOptions;
import org.eclipse.php.core.tests.PdttFile;
import org.eclipse.php.core.tests.TestSuiteWatcher;
import org.eclipse.php.core.tests.TestUtils;
import org.eclipse.php.core.tests.TestUtils.ColliderType;
import org.eclipse.php.core.tests.codeassist.scope.CodeAssistScopePdttFile.ExpectedScope;
import org.eclipse.php.core.tests.runner.PDTTList;
import org.eclipse.php.core.tests.runner.PDTTList.AfterList;
import org.eclipse.php.core.tests.runner.PDTTList.BeforeList;
import org.eclipse.php.core.tests.runner.PDTTList.Parameters;
import org.eclipse.php.internal.core.codeassist.IPHPCompletionRequestor;
import org.eclipse.php.internal.core.documentModel.loader.PHPDocumentLoader;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.junit.After;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.RunWith;

@SuppressWarnings("all")
@RunWith(PDTTList.class)
public class CodeAssistScopeTests {

	@ClassRule
	public static TestWatcher watcher = new TestSuiteWatcher();

	private static class TestCompletionRequestor extends CompletionRequestor implements IPHPCompletionRequestor {

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
		}

		@Override
		public void accept(CompletionProposal proposal) {

		}

	}

	@Parameters
	public static final Map<PHPVersion, String[]> TESTS = new LinkedHashMap<>();
	public static final String DEFAULT_CURSOR = "|";

	static {
		TESTS.put(PHPVersion.PHP5, new String[] { "/workspace/codeassist_scope/php5" });
		TESTS.put(PHPVersion.PHP5_3,
				new String[] { "/workspace/codeassist_scope/php5", "/workspace/codeassist_scope/php53" });
		TESTS.put(PHPVersion.PHP5_4, new String[] { "/workspace/codeassist_scope/php5",
				"/workspace/codeassist_scope/php53", "/workspace/codeassist_scope/php54" });
		TESTS.put(PHPVersion.PHP5_5,
				new String[] { "/workspace/codeassist_scope/php5", "/workspace/codeassist_scope/php53",
						"/workspace/codeassist_scope/php54", "/workspace/codeassist_scope/php55" });
		TESTS.put(PHPVersion.PHP5_6,
				new String[] { "/workspace/codeassist_scope/php5", "/workspace/codeassist_scope/php53",
						"/workspace/codeassist_scope/php54", "/workspace/codeassist_scope/php55",
						"/workspace/codeassist_scope/php56" });
		TESTS.put(PHPVersion.PHP7_0,
				new String[] { "/workspace/codeassist_scope/php5", "/workspace/codeassist_scope/php53",
						"/workspace/codeassist_scope/php54", "/workspace/codeassist_scope/php55",
						"/workspace/codeassist_scope/php56", "/workspace/codeassist_scope/php7" });
		TESTS.put(PHPVersion.PHP7_1,
				new String[] { "/workspace/codeassist_scope/php5", "/workspace/codeassist_scope/php53",
						"/workspace/codeassist_scope/php54", "/workspace/codeassist_scope/php55",
						"/workspace/codeassist_scope/php56", "/workspace/codeassist_scope/php7",
						"/workspace/codeassist_scope/php71" });
		TESTS.put(PHPVersion.PHP7_2,
				new String[] { "/workspace/codeassist_scope/php5", "/workspace/codeassist_scope/php53",
						"/workspace/codeassist_scope/php54", "/workspace/codeassist_scope/php55",
						"/workspace/codeassist_scope/php56", "/workspace/codeassist_scope/php7",
						"/workspace/codeassist_scope/php71", "/workspace/codeassist_scope/php72" });
		TESTS.put(PHPVersion.PHP7_3,
				new String[] { "/workspace/codeassist_scope/php5", "/workspace/codeassist_scope/php53",
						"/workspace/codeassist_scope/php54", "/workspace/codeassist_scope/php55",
						"/workspace/codeassist_scope/php56", "/workspace/codeassist_scope/php7",
						"/workspace/codeassist_scope/php71", "/workspace/codeassist_scope/php72" });
		TESTS.put(PHPVersion.PHP7_4,
				new String[] { "/workspace/codeassist_scope/php5", "/workspace/codeassist_scope/php53",
						"/workspace/codeassist_scope/php54", "/workspace/codeassist_scope/php55",
						"/workspace/codeassist_scope/php56", "/workspace/codeassist_scope/php7",
						"/workspace/codeassist_scope/php71", "/workspace/codeassist_scope/php72",
						"/workspace/codeassist_scope/php74" });
	};

	private IProject project;
	private IFile testFile;
	private List<IFile> otherFiles = null;
	private PHPVersion version;

	public CodeAssistScopeTests(PHPVersion version, String[] fileNames) {
		this.version = version;
	}

	@BeforeList
	public void setUpSuite() throws Exception {
		TestUtils.disableColliders(ColliderType.ALL);
		ModelManager.getModelManager().getIndexManager().disable();
		project = TestUtils.createProject("CodeAssistScopeTests_" + version.toString());
		ProjectOptions.setPHPVersion(version, project);
	}

	@AfterList
	public void tearDownSuite() throws Exception {
		TestUtils.deleteProject(project);
		TestUtils.enableColliders(ColliderType.ALL);
		ModelManager.getModelManager().getIndexManager().enable();
	}

	@Test
	public void assist(final String fileName) throws Exception {
		final CodeAssistScopePdttFile pdttFile = new CodeAssistScopePdttFile(fileName);
		pdttFile.applyPreferences();

		final int offset = createFiles(pdttFile);

		ICompletionScope[] scopes = getScopes(DLTKCore.createSourceModuleFrom(testFile), offset);
		compareScopes(scopes, pdttFile);
	}

	private ICompletionScope[] getScopes(ISourceModule sourceModule, int offset) throws ModelException {
		IStructuredDocument document = (IStructuredDocument) new PHPDocumentLoader().createNewStructuredDocument();
		String content = new String(sourceModule.getSourceAsCharArray());
		document.set(content);

		final List<ICompletionScope> scopes = new LinkedList<>();
		CompletionCompanion companion = new CompletionCompanion(new TestCompletionRequestor(document, offset),
				(IModuleSource) sourceModule, offset);

		ICompletionScope scope = companion.getScope();
		do {
			scopes.add(scope);
			scope = scope.getParent();
		} while (scope != null);

		return scopes.toArray(new ICompletionScope[scopes.size()]);
	}

	private void compareScopes(ICompletionScope[] proposals, CodeAssistScopePdttFile pdttFile) throws Exception {
		ExpectedScope[] expectedProposals = pdttFile.getExpectedScopes();
		boolean proposalsEqual = true;
		if (proposals.length == expectedProposals.length) {
			int pos = 0;
			for (ICompletionScope proposal : proposals) {
				ExpectedScope compare = expectedProposals[pos++];

				if (!compare.equals(proposal)) {
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
			for (ICompletionScope p : proposals) {
				errorBuf.append(p.toString()).append('\n');
			}
			fail(errorBuf.toString());
		}
	}

	@After
	public void deleteFiles() {
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
		this.otherFiles = new ArrayList<>(otherFiles.length);
		int i = 0;
		for (String otherFileContent : otherFiles) {
			IFile tmp = TestUtils.createFile(project, String.format("test%s.php", i), otherFileContent);
			this.otherFiles.add(i, tmp);
			i++;
		}
		// TestUtils.waitForIndexer();
		return offset;
	}

	private String getCursor(PdttFile pdttFile) {
		Map<String, String> config = pdttFile.getConfig();
		return config.get("cursor");
	}
}
