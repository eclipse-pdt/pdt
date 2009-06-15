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
package org.eclipse.php.core.tests.mixin;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedList;

import junit.framework.AssertionFailedError;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestResult;
import junit.framework.TestSuite;

import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.dltk.core.mixin.IMixinElement;
import org.eclipse.dltk.core.mixin.MixinModel;
import org.eclipse.dltk.core.search.IDLTKSearchConstants;
import org.eclipse.dltk.core.tests.model.AbstractDLTKSearchTests;
import org.eclipse.php.core.tests.PHPCoreTests;
import org.eclipse.php.core.tests.IAssertion;
import org.eclipse.php.internal.core.PHPLanguageToolkit;
import org.eclipse.php.internal.core.mixin.PHPMixinElementInfo;

public class MixinTests extends TestSuite {
	
	private MixinTest tests;
	
	public static Test suite() {
		return new MixinTests("/workspace/mixin");
	}
	
	@SuppressWarnings("unchecked")
	public MixinTests(String testsDirectory) {
		super(testsDirectory);

		final MixinModel model = new MixinModel(PHPLanguageToolkit.getDefault());
		try {

			tests = new MixinTest("PHP Mixin Tests");

			Enumeration<String> entryPaths = PHPCoreTests.getDefault().getBundle().getEntryPaths(testsDirectory);
			while (entryPaths.hasMoreElements()) {
				final String path = (String) entryPaths.nextElement();
				URL entry = PHPCoreTests.getDefault().getBundle().getEntry(path);
				try {
					entry.openStream().close();
				} catch (Exception e) {
					continue;
				}
				int pos = path.lastIndexOf('/');
				final String name = (pos >= 0 ? path.substring(pos + 1) : path);

				if (!name.endsWith(".php")) {
					continue;
				}

				addTest(new TestCase(name) {

					private Collection<IAssertion> assertions = new LinkedList<IAssertion>();

					public void setUp() {
					}

					class GetElementAssertion implements IAssertion {

						private final String key;

						public GetElementAssertion(String key) {
							this.key = key;
						}

						public void check() throws Exception {
							IMixinElement mixinElement = model.get(key);
							if (mixinElement == null) {
								throw new AssertionFailedError("Key " + key + " not found");
							}

							Object[] allObjects = mixinElement.getAllObjects();

							if (allObjects == null || allObjects.length == 0) {
								throw new AssertionFailedError("Key " + key + " has null or empty object set");
							}

							for (int i = 0; i < allObjects.length; i++) {
								if (allObjects[i] == null) {
									throw new AssertionFailedError("Key " + key + " has null object at index " + i);
								}
								PHPMixinElementInfo info = (PHPMixinElementInfo) allObjects[i];
								if (info.getObject() == null) {
									throw new AssertionFailedError("Key " + key + " has info with a null object at index " + i + " (kind=" + info.getKind() + ")");
								}
							}
						}
					}

					protected void runTest() throws Throwable {
						String content = loadContent(path);
						String[] lines = content.split("\n");
						int lineOffset = 0;
						for (int i = 0; i < lines.length; i++) {
							String line = lines[i].trim();
							if (line.startsWith("/* MIXIN")) {
								for (int j = i + 1; j < lines.length; ++j) {
									line = lines[j].trim();
									if ("*/".equals(line)) {
										break;
									}
									assertions.add(new GetElementAssertion(line));
								}
							}
							lineOffset += lines[i].length() + 1;
						}

						Assert.isLegal(assertions.size() > 0);

						tests.executeTest(assertions);
					}
				});
			}
		} finally {
			model.stop();
		}
	}

	public void run(TestResult result) {

		setupSuite();

		super.run(result);

		tearDownSuite();
	}

	protected void setupSuite() {
		try {
			tests.setUpSuite();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void tearDownSuite() {
		try {
			tests.tearDownSuite();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static InputStream openResource(String path) throws IOException {
		URL url = PHPCoreTests.getDefault().getBundle().getEntry(path);
		return new BufferedInputStream(url.openStream());		
	}

	private String loadContent(String path) throws IOException {
		StringBuffer buffer = new StringBuffer();
		InputStream input = null;
		try {
			input = openResource(path);
			char buff[] = new char[4096];
			int len = 0;
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			while ((len = reader.read(buff)) != -1) {
				if (len > 0) {
					buffer.append(buff, 0, len);
				}
			}
		} finally {
			if (input != null) {
				input.close();
			}
		}
		String content = buffer.toString();
		return content;
	}

	class MixinTest extends AbstractDLTKSearchTests implements IDLTKSearchConstants {

		private static final String SRC_PROJECT = "mixin";

		public MixinTest(String name) {
			super(PHPCoreTests.PLUGIN_ID, name);
		}
		
		public void setUpSuite() throws Exception {
			super.setUpSuite();
			up();
			ResourcesPlugin.getWorkspace().build(IncrementalProjectBuilder.FULL_BUILD, new NullProgressMonitor());
			waitForAutoBuild();
			waitUntilIndexesReady();
		}
		
		private void up() throws Exception {
			if (SCRIPT_PROJECT == null) {
				SCRIPT_PROJECT = setUpScriptProject(SRC_PROJECT);
			}
		}
		
		public void tearDownSuite() throws Exception {
			deleteProject(SRC_PROJECT);
			super.tearDownSuite();
		}

		public void executeTest(Collection<IAssertion> assertions) throws Exception {
			for (Iterator<IAssertion> iter = assertions.iterator(); iter.hasNext();) {
				IAssertion assertion = iter.next();
				assertion.check();
			}
		}
	}
}
