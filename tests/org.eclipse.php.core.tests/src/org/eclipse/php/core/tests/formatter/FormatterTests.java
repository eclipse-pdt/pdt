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
package org.eclipse.php.core.tests.formatter;

import java.io.ByteArrayInputStream;
import java.util.LinkedHashMap;
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
import org.eclipse.dltk.compiler.problem.IProblem;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IBuffer;
import org.eclipse.dltk.core.IProblemRequestor;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ScriptModelUtil;
import org.eclipse.dltk.core.WorkingCopyOwner;
import org.eclipse.jface.text.IDocument;
import org.eclipse.php.core.tests.AbstractPDTTTest;
import org.eclipse.php.core.tests.PHPCoreTests;
import org.eclipse.php.core.tests.PdttFile;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.format.PhpFormatProcessorImpl;
import org.eclipse.php.internal.core.project.PHPNature;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;

public class FormatterTests extends AbstractPDTTTest {

	protected static final Map<PHPVersion, String[]> TESTS = new LinkedHashMap<PHPVersion, String[]>();
	static {
		TESTS.put(PHPVersion.PHP5, new String[] { "/workspace/formatter/php5" });
		TESTS.put(PHPVersion.PHP5_4, new String[] {
				"/workspace/formatter/php5", "/workspace/formatter/php54" });
	};

	protected static Map<PdttFile, IFile> filesMap = new LinkedHashMap<PdttFile, IFile>();
	protected static IProject project;
	protected static int count;

	public static void setUpSuite() throws Exception {
		project = ResourcesPlugin.getWorkspace().getRoot()
				.getProject("FormatterTests");
		if (project.exists()) {
			return;
		}

		project.create(null);
		project.open(null);

		// configure nature
		IProjectDescription desc = project.getDescription();
		desc.setNatureIds(new String[] { PHPNature.ID });
		project.setDescription(desc, null);

		WorkingCopyOwner.setPrimaryBufferProvider(new WorkingCopyOwner() {
			public IBuffer createBuffer(ISourceModule workingCopy) {
				ISourceModule original = workingCopy.getPrimary();
				IResource resource = original.getResource();
				if (resource != null) {
					if (resource instanceof IFile) {
						return new DocumentAdapter(workingCopy,
								(IFile) resource);
					}
				}
				return DocumentAdapter.NULL;
			}
		});

		for (PdttFile pdttFile : filesMap.keySet()) {
			IFile file = createFile(pdttFile.getFile().trim());
			filesMap.put(pdttFile, file);
		}

		project.refreshLocal(IResource.DEPTH_INFINITE, null);
		project.build(IncrementalProjectBuilder.FULL_BUILD, null);

		PHPCoreTests.waitForIndexer();
		PHPCoreTests.waitForAutoBuild();
	}

	public static void tearDownSuite() throws Exception {
		project.close(null);
		project.delete(true, true, null);
		project = null;
	}

	public FormatterTests(String description) {
		super(description);
	}

	public static Test suite() {

		TestSuite suite = new TestSuite("Formatter Tests");

		for (final PHPVersion phpVersion : TESTS.keySet()) {
			TestSuite phpVerSuite = new TestSuite(phpVersion.getAlias());

			for (String testsDirectory : TESTS.get(phpVersion)) {

				for (final String fileName : getPDTTFiles(testsDirectory)) {
					try {
						final PdttFile pdttFile = new PdttFile(fileName);
						filesMap.put(pdttFile, null);

						phpVerSuite.addTest(new FormatterTests(phpVersion
								.getAlias() + " - /" + fileName) {

							protected void setUp() throws Exception {
								PHPCoreTests.setProjectPhpVersion(project,
										phpVersion);
							}

							protected void runTest() throws Throwable {

								IFile file = filesMap.get(pdttFile);
								ISourceModule modelElement = (ISourceModule) DLTKCore
										.create(file);
								if (ScriptModelUtil.isPrimary(modelElement))
									modelElement.becomeWorkingCopy(
											new IProblemRequestor() {

												public void acceptProblem(
														IProblem problem) {
													// TODO Auto-generated
													// method stub

												}

												public void beginReporting() {
													// TODO Auto-generated
													// method stub

												}

												public void endReporting() {
													// TODO Auto-generated
													// method stub

												}

												public boolean isActive() {
													// TODO Auto-generated
													// method stub
													return false;
												}
											}, null);
								IStructuredModel modelForEdit = StructuredModelManager
										.getModelManager()
										.getModelForEdit(file);
								try {
									IDocument document = modelForEdit
											.getStructuredDocument();
									String beforeFormat = document.get();

									PhpFormatProcessorImpl formatter = new PhpFormatProcessorImpl();
									formatter.formatDocument(document, 0,
											document.getLength());
									assertContents(pdttFile.getExpected(),
											document.get());

									// change the document text as was before
									// the formatting
									document.set(beforeFormat);
									modelForEdit.save();
								} finally {
									if (modelForEdit != null) {
										modelForEdit.releaseFromEdit();
									}
								}
							}
						});
					} catch (final Exception e) {
						phpVerSuite.addTest(new TestCase(fileName) { // dummy
																		// test
																		// indicating
																		// PDTT
																		// file
																		// parsing
																		// failure
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

	protected static IFile createFile(String data) throws Exception {
		IFile testFile = project.getFile("test" + (++count) + ".php");
		testFile.create(new ByteArrayInputStream(data.getBytes()), true, null);
		return testFile;
	}
}
