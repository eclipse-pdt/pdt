/*******************************************************************************
 * Copyright (c) 2013, 2014 Zend Techologies Ltd.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Zend Technologies Ltd. - initial API and implementation
 *     Dawid Pakuła - convert to JUnit4
 *******************************************************************************/
package org.eclipse.php.formatter.core.tests;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
import org.eclipse.php.core.tests.PDTTUtils;
import org.eclipse.php.core.tests.PHPCoreTests;
import org.eclipse.php.core.tests.PdttFile;
import org.eclipse.php.core.tests.runner.AbstractPDTTRunner.Context;
import org.eclipse.php.core.tests.runner.PDTTList;
import org.eclipse.php.core.tests.runner.PDTTList.AfterList;
import org.eclipse.php.core.tests.runner.PDTTList.BeforeList;
import org.eclipse.php.core.tests.runner.PDTTList.Parameters;
import org.eclipse.php.formatter.core.Logger;
import org.eclipse.php.formatter.ui.preferences.ProfileManager;
import org.eclipse.php.formatter.ui.preferences.ProfileManager.CustomProfile;
import org.eclipse.php.formatter.ui.preferences.ProfileManager.Profile;
import org.eclipse.php.formatter.ui.preferences.ProfileStore;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.project.PHPNature;
import org.eclipse.php.ui.format.PHPFormatProcessorProxy;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.osgi.framework.Bundle;

@RunWith(PDTTList.class)
public class FormatterTests {

	@Parameters(recursive = true)
	public static final Map<PHPVersion, String[]> TESTS = new LinkedHashMap<PHPVersion, String[]>();
	static {
		TESTS.put(PHPVersion.PHP5, new String[] { "/workspace/formatter/php5" });
		TESTS.put(PHPVersion.PHP5_3,
				new String[] { "/workspace/formatter/php53" });
		TESTS.put(PHPVersion.PHP5_4,
				new String[] { "/workspace/formatter/php54" });
		TESTS.put(PHPVersion.PHP5_5,
				new String[] { "/workspace/formatter/php55" });
		TESTS.put(PHPVersion.PHP5_6,
				new String[] { "/workspace/formatter/php56" });
	};
	protected static int suiteCounter = 0;

	@Context
	public static Bundle getContext() {
		return Activator.getDefault().getBundle();
	}

	protected Map<String, IFile> files = new LinkedHashMap<String, IFile>();
	protected Map<String, PdttFile> pdttFiles = new LinkedHashMap<String, PdttFile>();
	protected final String[] fileNames;
	protected IProject project;
	protected int count;
	protected final PHPVersion phpVersion;
	protected final IScopeContext scopeContext;
	protected final ProfileManager profileManager;
	protected final String xmlFile;

	public FormatterTests(PHPVersion version, String[] fileNames)
			throws Exception {
		this.phpVersion = version;
		this.fileNames = fileNames;
		Bundle bundle = getContext();
		if (fileNames.length > 0) {
			IPath path = new Path(fileNames[0]);
			path = path.removeLastSegments(1);
			String[] formatterConfigurationFile = PDTTUtils.getFiles(
					path.toString(), bundle, ".xml");

			if (formatterConfigurationFile.length > 0) {
				xmlFile = formatterConfigurationFile[0];
			} else {
				xmlFile = null;
			}
		} else {
			xmlFile = null;
		}
		scopeContext = InstanceScope.INSTANCE;
		profileManager = new ProfileManager(new ArrayList<Profile>(),
				scopeContext);
	}

	@BeforeList
	public void setUpSuite() throws Exception {
		project = ResourcesPlugin.getWorkspace().getRoot()
				.getProject("FormatterTests_" + suiteCounter++);

		project.create(null);
		project.open(null);

		// configure nature
		IProjectDescription desc = project.getDescription();
		desc.setNatureIds(new String[] { PHPNature.ID });
		project.setDescription(desc, null);

		for (String fileName : fileNames) {
			PdttFile pdttFile = new PdttFile(getContext(), fileName);
			IFile file = createFile(pdttFile.getFile().trim());
			files.put(fileName, file);
			pdttFiles.put(fileName, pdttFile);
		}

		project.refreshLocal(IResource.DEPTH_INFINITE, null);
		project.build(IncrementalProjectBuilder.FULL_BUILD, null);

		PHPCoreTests.setProjectPhpVersion(project, phpVersion);

		PHPCoreTests.waitForIndexer();
		PHPCoreTests.waitForAutoBuild();

		profileManager.clearAllSettings(scopeContext);
		profileManager.commitChanges(scopeContext);

		if (xmlFile != null) {
			// apply configuration to the formatter configuration
			// manager
			String abcolutXmlFilePath = null;
			try {
				URL url = FileLocator.find(getContext(), new Path(xmlFile),
						null);
				URL resolved = FileLocator.resolve(url);
				IPath path = Path.fromOSString(resolved.getFile());
				abcolutXmlFilePath = path.toString();
			} catch (Exception e) {
				Logger.logException(e);
			}

			final File file = new File(abcolutXmlFilePath);
			assertTrue("Formatter Configuration Not Found " + file.toString(),
					file.exists());

			List<Profile> profiles = null;
			try {

				profiles = ProfileStore.readProfilesFromFile(file);
			} catch (CoreException e) {
				Logger.logException(
						"Error while reading profile configuration xml file", e);
			}

			// should be only one profile in file
			if (profiles != null && profiles.size() > 0) {
				// update formatter configuration profile
				CustomProfile profile = (CustomProfile) profiles.iterator()
						.next();
				profileManager.addProfile(profile);
				profileManager.setSelected(profile);
				profileManager.commitChanges(scopeContext);

			}
		} else {

		}
	}

	@AfterList
	public void tearDownSuite() throws Exception {
		setDefaultFormatter(scopeContext, profileManager);
		project.close(null);
		project.delete(true, true, null);
		project = null;
	}

	@Test
	public void formatter(String fileName) throws Exception {
		IFile file = files.get(fileName);

		IStructuredModel modelForEdit = StructuredModelManager
				.getModelManager().getModelForEdit(file);
		try {
			IDocument document = modelForEdit.getStructuredDocument();
			String beforeFormat = document.get();

			PHPFormatProcessorProxy formatter = new PHPFormatProcessorProxy();
			formatter.formatDocument(document, 0, document.getLength());

			PDTTUtils.assertContents(pdttFiles.get(fileName).getExpected(),
					document.get());

			// change the document text as
			// was
			// before
			// the formatting
			document.set(beforeFormat);
			modelForEdit.save();
		} finally {
			if (modelForEdit != null) {
				modelForEdit.releaseFromEdit();
			}
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

	protected IFile createFile(String data) throws Exception {
		IFile testFile = project.getFile("test" + (++count) + ".php");
		testFile.create(new ByteArrayInputStream(data.getBytes()), true, null);
		return testFile;
	}
}
