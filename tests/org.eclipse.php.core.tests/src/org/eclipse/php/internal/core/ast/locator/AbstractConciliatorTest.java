/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
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
 *******************************************************************************/
package org.eclipse.php.internal.core.ast.locator;

import static org.junit.Assert.fail;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.php.core.PHPVersion;
import org.eclipse.php.core.ast.nodes.ASTNode;
import org.eclipse.php.core.ast.nodes.ASTParser;
import org.eclipse.php.core.ast.nodes.Program;
import org.eclipse.php.core.project.ProjectOptions;
import org.eclipse.php.core.tests.TestUtils;
import org.eclipse.php.internal.core.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;

public abstract class AbstractConciliatorTest {

	protected static IProject project;
	protected static PHPVersion phpVersion = null;
	protected IFile file;

	@BeforeClass
	public static void setUpSuite() throws Exception {
		project = phpVersion != null ? createProject("projectConciliator", phpVersion)
				: createProject("projectConciliator");
	}

	@AfterClass
	public static void tearDownSuite() throws Exception {
		TestUtils.deleteProject(project);
	}

	public static IProject createProject(String name) {
		IProject project = TestUtils.createProject(name);
		try {
			project.build(IncrementalProjectBuilder.FULL_BUILD, null);
		} catch (CoreException e) {
			Logger.logException(e);
		}
		TestUtils.waitForIndexer();
		return project;
	}

	public static IProject createProject(String name, PHPVersion version) {
		IProject project = TestUtils.createProject(name);
		try {
			TestUtils.setProjectPHPVersion(project, version);
		} catch (CoreException e) {
			Logger.logException(e);
		}
		TestUtils.waitForIndexer();
		return project;
	}

	@After
	public void tearDown() {
		TestUtils.deleteFile(file);
	}

	public Program createProgramFromSource(IFile file) throws Exception {
		ISourceModule source = DLTKCore.createSourceModuleFrom(file);
		return createProgramFromSource(source);
	}

	public Program createProgramFromSource(ISourceModule source) throws Exception {
		IResource resource = source.getResource();
		IProject project = null;
		if (resource instanceof IFile) {
			project = ((IFile) resource).getProject();
		}
		ASTParser newParser;
		if (project != null) {
			newParser = ASTParser.newParser(project, source);
		} else {
			newParser = ASTParser.newParser(ProjectOptions.getDefaultPHPVersion(),
					ProjectOptions.getDefaultIsSupportingASPTags(), ProjectOptions.getDefaultUseShortTags(), source);
		}
		return newParser.createAST(null);
	}

	protected void setFileContent(String content) {
		file = TestUtils.createFile(project, "test.php", content);
		// Wait for indexer...
		TestUtils.waitForIndexer();

		try {
			// XXX Workaround to make test more stable
			file.touch(new NullProgressMonitor());
			TestUtils.waitForIndexer();
		} catch (CoreException e) {
		}
	}

	protected Program createProgram(IFile file) {
		ISourceModule sourceModule = DLTKCore.createSourceModuleFrom(file);
		Program program = null;
		try {
			program = createProgramFromSource(sourceModule);

		} catch (Exception e) {
			fail(e.getMessage());
		}
		return program;
	}

	protected ASTNode locateNode(Program program, int start, int end) {
		ASTNode locateNode = NodeFinder.perform(program, start, end);
		return locateNode;
	}

}