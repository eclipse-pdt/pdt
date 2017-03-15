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
package org.eclipse.php.core.tests.errors;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.php.core.tests.PDTTUtils;
import org.eclipse.php.core.tests.PdttFile;
import org.eclipse.php.core.tests.TestUtils;
import org.eclipse.php.core.tests.runner.PDTTList.AfterList;
import org.eclipse.php.core.tests.runner.PDTTList.BeforeList;
import org.eclipse.php.core.PHPVersion;
import org.eclipse.php.internal.core.compiler.ast.parser.PhpProblemIdentifier;
import org.junit.Test;

abstract public class AbstractErrorReportingTests {
	protected IProject project;
	protected int count;
	protected final String[] fileNames;
	protected Map<String, IFile> files = new HashMap<String, IFile>();
	protected Map<String, PdttFile> pdttFiles = new HashMap<String, PdttFile>();

	public AbstractErrorReportingTests(String[] fileNames) {
		this.fileNames = fileNames;
	}

	@Test
	public void errors(String fileName) throws Exception {
		IFile file = files.get(fileName);

		StringBuilder buf = new StringBuilder();

		IMarker[] markers = file.findMarkers(getMarkerType(), true, IResource.DEPTH_ZERO);
		Arrays.sort(markers, new MarkerComparator());
		for (IMarker marker : markers) {
			buf.append("\n[line=");
			buf.append(marker.getAttribute(IMarker.LINE_NUMBER));
			buf.append(", start=");
			buf.append(marker.getAttribute(IMarker.CHAR_START));
			buf.append(", end=");
			buf.append(marker.getAttribute(IMarker.CHAR_END));
			buf.append("] ");
			buf.append(marker.getAttribute(IMarker.MESSAGE)).append('\n');
		}

		PDTTUtils.assertContents(pdttFiles.get(fileName).getExpected(), buf.toString());
	}

	protected IFile createFile(String data) throws Exception {
		return TestUtils.createFile(project, "test" + (++count) + ".php", data);
	}

	@AfterList
	public void tearDown() throws Exception {
		TestUtils.deleteProject(project);
	}

	@BeforeList
	public void setUpSuite() throws Exception {
		project = TestUtils.createProject("ErrorReportingTests");
		ResourcesPlugin.getWorkspace().getRoot().getProject(getPHPVersion() + "ErrorReportingTests");
		for (final String fileName : fileNames) {
			PdttFile pdttFile = new PdttFile(fileName);
			pdttFiles.put(fileName, pdttFile);
			files.put(fileName, createFile(pdttFile.getFile().trim()));
		}
		TestUtils.setProjectPhpVersion(project, getPHPVersion());
		// Perform full build to trigger errors check
		project.build(IncrementalProjectBuilder.FULL_BUILD, null);
		TestUtils.waitForIndexer();
	}

	abstract protected PHPVersion getPHPVersion();

	protected String getMarkerType() {
		return PhpProblemIdentifier.MARKER_TYPE_ID;
	}
}
