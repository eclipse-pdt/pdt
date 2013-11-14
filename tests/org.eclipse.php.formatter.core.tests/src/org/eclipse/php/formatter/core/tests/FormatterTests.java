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

import java.io.ByteArrayInputStream;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
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
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Region;
import org.eclipse.php.core.tests.AbstractPDTTTest;
import org.eclipse.php.core.tests.PHPCoreTests;
import org.eclipse.php.core.tests.PdttFile;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.project.PHPNature;
import org.eclipse.php.ui.format.PHPFormatProcessorProxy;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;
import org.osgi.framework.Bundle;

import org.eclipse.php.formatter.core.Logger;
import org.eclipse.php.formatter.ui.preferences.ProfileManager;
import org.eclipse.php.formatter.ui.preferences.ProfileStore;
import org.eclipse.php.formatter.ui.preferences.ProfileManager.CustomProfile;
import org.eclipse.php.formatter.ui.preferences.ProfileManager.Profile;

public class FormatterTests extends AbstractPDTTTest {

	protected static final Map<PHPVersion, String[]> TESTS = new LinkedHashMap<PHPVersion, String[]>();
	static {
		TESTS
				.put(PHPVersion.PHP5,
						new String[] { "/workspace/formatter/php5" });
		TESTS.put(PHPVersion.PHP5_3,
				new String[] { "/workspace/formatter/php53" });
		TESTS.put(PHPVersion.PHP5_4,
				new String[] { "/workspace/formatter/php54" });
		TESTS.put(PHPVersion.PHP5_5,
				new String[] { "/workspace/formatter/php55" });
	};

	protected static Map<PdttFile, IFile> filesMap = new LinkedHashMap<PdttFile, IFile>();
	protected static IProject project;
	protected static int count;

	public static void setUpSuite() throws Exception {
		project = ResourcesPlugin.getWorkspace().getRoot().getProject(
				"FormatterTests");
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

			Bundle bundle = Activator.getDefault().getBundle();
			String xmlConfigurationFile = null;
			for (String testingDirectory : TESTS.get(phpVersion)) {
				TestSuite phpVerSuite = new TestSuite(phpVersion.getAlias());

				String[] testingDirectories = getTestingDirectories(bundle,
						testingDirectory);
				for (String testsDirectory : testingDirectories) {

					TestSuite formatterConfigurationSuite = new TestSuite(
							testsDirectory);

					// read formatter configuration file from current pdtt
					// directory
					String[] formatterConfigurationFile = getFiles(
							testsDirectory, bundle, ".xml");

					if (formatterConfigurationFile.length > 0) {
						xmlConfigurationFile = formatterConfigurationFile[0];
					} else {
						xmlConfigurationFile = null;
					}

					boolean filesFound = false;
					for (final String fileName : getPDTTFiles(testsDirectory,
							bundle)) {
						filesFound = true;
						try {
							final PdttFile pdttFile = new PdttFile(Activator.getDefault().getBundle(), fileName);
							filesMap.put(pdttFile, null);

							formatterConfigurationSuite
									.addTest(new FormatterTests(phpVersion
											.getAlias() + " - /" + fileName) {

										protected void setUp() throws Exception {
											PHPCoreTests.setProjectPhpVersion(
													project, phpVersion);
										}

										protected void runTest()
												throws Throwable {

											IFile file = filesMap.get(pdttFile);

											IStructuredModel modelForEdit = StructuredModelManager
													.getModelManager()
													.getModelForEdit(file);
											try {
												IDocument document = modelForEdit
														.getStructuredDocument();
												String beforeFormat = document
														.get();

												IRegion region = new Region(0,
														document.getLength());

												PHPFormatProcessorProxy formatter = new PHPFormatProcessorProxy();
												formatter.formatDocument(document, 0, document.getLength());

												assertContents(pdttFile
														.getExpected(),
														document.get());

												// change the document text as
												// was
												// before
												// the formatting
												document.set(beforeFormat);
												modelForEdit.save();
											} finally {
												if (modelForEdit != null) {
													modelForEdit
															.releaseFromEdit();
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
										protected void runTest()
												throws Throwable {
											throw e;
										}
									});
						}
					}
					if(filesFound) {
						TestSetup setup = new ConfigurableTestSetup(
								formatterConfigurationSuite, xmlConfigurationFile);
						phpVerSuite.addTest(setup);
					}
				}
				suite.addTest(phpVerSuite);
			}

		}

		return suite;
	}

	static class ConfigurableTestSetup extends TestSetup {
		private String xmlFile;
		private ProfileManager profileManager;
		IScopeContext scopeContext;

		public ConfigurableTestSetup(Test test, String xmlFile) {
			super(test);
			this.xmlFile = xmlFile;
		}

		protected void setUp() throws Exception {
			setUpSuite();

			if (xmlFile != null) {
				scopeContext = new InstanceScope();
				profileManager = new ProfileManager(new ArrayList<Profile>(),
						scopeContext);

				// apply configuration to the formatter configuration
				// manager
				String abcolutXmlFilePath = null;
				try {
					URL url = FileLocator.find(Activator.getDefault().getBundle(), new Path(xmlFile), null);
					URL resolved = FileLocator.resolve(url);
					IPath path = Path.fromOSString(resolved.getFile());
					abcolutXmlFilePath = path.toString();
				} catch (Exception e) {
					Logger.logException(e);
				}

				final File file = new File(abcolutXmlFilePath);
				assertTrue("Formatter Configuration Not Found " + file.toString(), file.exists());

				List<Profile> profiles = null;
				try {

					profiles = ProfileStore.readProfilesFromFile(file);
				} catch (CoreException e) {
					Logger
							.logException(
									"Error while reading profile configuration xml file",
									e);
				}

				//should be only one profile in file
				if (profiles != null && profiles.size() > 0) {
					// update formatter configuration profile
					CustomProfile profile = (CustomProfile) profiles.iterator()
							.next();
					profileManager.addProfile(profile);
					profileManager.setSelected(profile);
					profileManager.commitChanges(scopeContext);

				}
			}
		}

		protected void tearDown() throws Exception {
			if (xmlFile != null) {
				setDefaultFormatter(scopeContext, profileManager);
			}
			tearDownSuite();
		}
	}

	private static void setDefaultFormatter(IScopeContext scopeContext,
			ProfileManager profileManager) {
		profileManager.clearAllSettings(scopeContext);
		if (profileManager.getSelected().getID() != ProfileManager.PHP_PROFILE) {
			profileManager.setSelected(profileManager
					.getProfile(ProfileManager.PHP_PROFILE));
		}
		profileManager.commitChanges(scopeContext);
	}

	protected static IFile createFile(String data) throws Exception {
		IFile testFile = project.getFile("test" + (++count) + ".php");
		testFile.create(new ByteArrayInputStream(data.getBytes()), true, null);
		return testFile;
	}

	protected static String[] getTestingDirectories(Bundle bundle,
			String testsDirectory) {
		Enumeration<String> entryPaths = bundle.getEntryPaths(testsDirectory);
		List<String> files = new LinkedList<String>();
		if (entryPaths != null) {
			while (entryPaths.hasMoreElements()) {
				final String path = (String) entryPaths.nextElement();
				URL entry = bundle.getEntry(path);
				// check whether the directory is readable:
				try {

					// TODO check if accessible directory
					files.add(path);

				} catch (Exception e) {
					continue;
				}
			}
		}
		return (String[]) files.toArray(new String[files.size()]);
	}
}
