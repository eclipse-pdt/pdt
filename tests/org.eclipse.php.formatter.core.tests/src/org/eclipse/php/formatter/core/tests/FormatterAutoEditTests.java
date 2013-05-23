/*******************************************************************************
 * Copyright (c) 2013 Zend Techologies Ltd.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Zend Technologies Ltd. - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.formatter.core.tests;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import junit.extensions.TestSetup;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.dltk.compiler.problem.IProblem;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IProblemRequestor;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ScriptModelUtil;
import org.eclipse.jface.text.DocumentCommand;
import org.eclipse.jface.text.IDocument;
import org.eclipse.php.core.tests.PHPCoreTests;
import org.eclipse.php.core.tests.PdttFile;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.project.PHPNature;
import org.eclipse.php.internal.ui.autoEdit.MainAutoEditStrategy;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;

import org.eclipse.php.formatter.ui.preferences.ProfileManager;
import org.eclipse.php.formatter.ui.preferences.ProfileManager.Profile;


public class FormatterAutoEditTests extends FormatterTests {

	protected static final char OFFSET_CHAR = '|';

	protected static final Map<PHPVersion, String[]> TESTS = new LinkedHashMap<PHPVersion, String[]>();
	static {
		TESTS.put(PHPVersion.PHP5,
				new String[] { "/workspace/formatter-autoedit/php5" });
	};

	public static void setUpSuite() throws Exception {
		project = ResourcesPlugin.getWorkspace().getRoot().getProject(
				"FormatterAutoEditTests");
		if (project.exists()) {
			return;
		}

		project.create(null);
		project.open(null);

		// configure nature
		IProjectDescription desc = project.getDescription();
		desc.setNatureIds(new String[] { PHPNature.ID });
		project.setDescription(desc, null);

		for (PdttFile pdttFile : filesMap.keySet()) {
			IFile file = createFile(pdttFile.getFile().trim());
			filesMap.put(pdttFile, file);
		}

		project.refreshLocal(IResource.DEPTH_INFINITE, null);
		project.build(IncrementalProjectBuilder.FULL_BUILD, null);

		PHPCoreTests.waitForIndexer();
		PHPCoreTests.waitForAutoBuild();

		IScopeContext scopeContext = new InstanceScope();
		ProfileManager profileManager = new ProfileManager(
				new ArrayList<Profile>(), scopeContext);
		profileManager.clearAllSettings(scopeContext);
		profileManager.commitChanges(scopeContext);

	}

	public FormatterAutoEditTests(String description) {
		super(description);
	}

	public static Test suite() {

		TestSuite suite = new TestSuite("Formatter Auto Edit Tests");

		for (final PHPVersion phpVersion : TESTS.keySet()) {
			TestSuite phpVerSuite = new TestSuite(phpVersion.getAlias());

			for (String testsDirectory : TESTS.get(phpVersion)) {

				for (final String fileName : getPDTTFiles(testsDirectory,
						Activator.getDefault().getBundle())) {
					try {
						final PdttFile pdttFile = new PdttFile(Activator
								.getDefault().getBundle(), fileName);
						filesMap.put(pdttFile, null);

						phpVerSuite.addTest(new FormatterAutoEditTests("Windows" + " - /"
								+ phpVersion
								.getAlias() + " - /"
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
												data+",offset character is not set");
									}

									// replace the offset character
									data = data.substring(0, offset)
											+ data.substring(offset + 1);

									document.set(data);

									MainAutoEditStrategy indentLineAutoEditStrategy = new MainAutoEditStrategy();

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
