/*******************************************************************************
 * Copyright (c) 2011 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.ui.tests.formatter.autoedit;

import junit.extensions.TestSetup;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.eclipse.core.resources.IFile;
import org.eclipse.dltk.compiler.problem.IProblem;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IProblemRequestor;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ScriptModelUtil;
import org.eclipse.jface.text.DocumentCommand;
import org.eclipse.jface.text.IAutoEditStrategy;
import org.eclipse.jface.text.IDocument;
import org.eclipse.php.core.tests.PHPCoreTests;
import org.eclipse.php.core.tests.PdttFile;
import org.eclipse.php.core.tests.formatter.FormatterTests;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.ui.autoEdit.MainAutoEditStrategy;
import org.eclipse.php.ui.tests.PHPUiTests;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;

public class FormatterAutoEditTests extends FormatterTests {

	protected static final char OFFSET_CHAR = '|';

	static {
		TESTS.put(PHPVersion.PHP5,
				new String[] { "/workspace/formatter-autoedit" });
	};

	public FormatterAutoEditTests(String description) {
		super(description);
	}

	public static Test suite() {

		TestSuite suite = new TestSuite("Formatter Auto Edit Tests");

		for (final PHPVersion phpVersion : TESTS.keySet()) {
			TestSuite phpVerSuite = new TestSuite(phpVersion.getAlias());

			for (String testsDirectory : TESTS.get(phpVersion)) {

				for (final String fileName : getPDTTFiles(testsDirectory,
						PHPUiTests.getDefault().getBundle())) {
					try {
						final PdttFile pdttFile = new PdttFile(PHPUiTests
								.getDefault().getBundle(), fileName);
						filesMap.put(pdttFile, null);

						phpVerSuite.addTest(new FormatterAutoEditTests("/"
								+ fileName) {

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
									String data = document.get();
									int offset = data.lastIndexOf(OFFSET_CHAR);
									if (offset == -1) {
										throw new IllegalArgumentException(
												"Offset character is not set");
									}

									// replace the offset character
									data = data.substring(0, offset)
											+ data.substring(offset + 1);

									document.set(data);

									IAutoEditStrategy indentLineAutoEditStrategy = new MainAutoEditStrategy();

									DocumentCommand cmd = new DocumentCommand() {

									};

									cmd.offset = offset;
									cmd.length = 0;
									if (pdttFile.getOther() != null) {
										cmd.text = pdttFile.getOther();
									} else {
										cmd.text = "\n";
									}

									cmd.doit = true;
									cmd.shiftsCaret = true;
									cmd.caretOffset = -1;

									indentLineAutoEditStrategy
											.customizeDocumentCommand(document,
													cmd);
									document.replace(cmd.offset, cmd.length,
											cmd.text);

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

}
