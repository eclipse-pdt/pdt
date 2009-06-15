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
package org.eclipse.php.core.tests.codeassist;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import junit.framework.Protectable;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestResult;
import junit.framework.TestSuite;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.dltk.core.CompletionProposal;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.php.core.tests.AbstractProjectSuite;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.project.ProjectOptions;

public class CodeAssistProjectTests extends AbstractProjectSuite {

	private static final String PROJECT_BASE = "codeassist_prj"; //$NON-NLS-1$
	protected static final Map<String, PHPVersion> TEST_DIRS = new HashMap<String, PHPVersion>();
	static {
		TEST_DIRS.put("php5", PHPVersion.PHP5);
	}

	private String projectName;
	private PHPVersion phpVersion;

	public CodeAssistProjectTests(String projectName, PHPVersion phpVersion) {
		super(projectName);
		this.projectName = projectName;
		this.phpVersion = phpVersion;
	}
	
	public File getSourceWorkspacePath() {
		return new File(super.getSourceWorkspacePath(), PROJECT_BASE);
	}
	
	public void run(final TestResult result) {
		Protectable p = new Protectable() {
			public void protect() throws Exception {
				setUpSuite();
				CodeAssistProjectTests.super.run(result);
				tearDownSuite();
			}
		};
		result.runProtected(this, p);
	}

	public void setUpSuite() throws Exception {
		deleteProject(projectName);
		IScriptProject project = setUpScriptProject(projectName);
		ProjectOptions.setPhpVersion(phpVersion, project.getProject());
	}

	public void tearDownSuite() throws Exception {
		deleteProject(projectName);
	}
	
	public static Test suite() {
		TestSuite suite = new TestSuite("Code Assist Project Tests");
		for (final String testProject : TEST_DIRS.keySet()) {
			
			PHPVersion phpVersion = TEST_DIRS.get(testProject);
			final CodeAssistProjectTests projectTests = new CodeAssistProjectTests(testProject, phpVersion);
			
			for (final File file : new File(projectTests.getSourceWorkspacePath(), testProject).listFiles()) {
				final String baseName = file.getName();
				if (!baseName.toLowerCase().endsWith(".pdtt")) {
					continue;
				}
				try {
					projectTests.addTest(new TestCase("/" + testProject + "/" + baseName) {
						protected void runTest() throws Throwable {
							CodeAssistPdttFile pdttFile = new CodeAssistPdttFile(file.getAbsolutePath());

							String data = pdttFile.getFile();
							int offset = data.lastIndexOf(CodeAssistTests.OFFSET_CHAR);
							if (offset == -1) {
								throw new IllegalArgumentException("Offset character is not set");
							}
							// replace the offset character
							data = data.substring(0, offset) + data.substring(offset + 1);
							
							IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(testProject);
							IFile workspaceFile = project.getFile("test.php");
							if (workspaceFile.exists()) {
								workspaceFile.setContents(new ByteArrayInputStream(data.getBytes()), IResource.FORCE, null);
							} else {
								workspaceFile.create(new ByteArrayInputStream(data.getBytes()), true, null);
							}
							
							waitUntilIndexesReady();
							
							ISourceModule sourceModule = (ISourceModule) DLTKCore.create(workspaceFile);
							CompletionProposal[] proposals = CodeAssistTests.getProposals(sourceModule, offset);
							CodeAssistTests.compareProposals(proposals, pdttFile);
						}
					});
				} catch (final Exception e) {
					projectTests.addTest(new TestCase(baseName) { // dummy test indicating PDTT file parsing failure
							protected void runTest() throws Throwable {
								throw e;
							}
						});
				}
			}
			
			suite.addTest(projectTests);
		}
		return suite;
	}
}
