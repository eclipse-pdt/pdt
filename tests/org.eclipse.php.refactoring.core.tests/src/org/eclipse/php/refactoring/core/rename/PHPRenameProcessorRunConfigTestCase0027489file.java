/*******************************************************************************
 * Copyright (c) 2005, 2015 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.refactoring.core.rename;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.php.core.ast.nodes.Program;
import org.eclipse.php.core.tests.TestUtils;
import org.eclipse.php.refactoring.core.test.AbstractRefactoringTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PHPRenameProcessorRunConfigTestCase0027489file extends AbstractRefactoringTest {
	private IProject project1;

	private String configFileCont = "<?xml version='1.0' encoding='UTF-8' standalone='no'?> "
			+ "<launchConfiguration type='org.eclipse.php.debug.core.launching.PHPExeLaunchConfigurationType'>"
			+ "<stringAttribute key='ATTR_FILE' value='/TestProject1/source/RenameRunConfigTest0027489file.php'/>"
			+ "<stringAttribute key='ATTR_FILE_FULL_PATH' value='C:\\src\\RenameRunConfigTest0027489file'/>"
			+ "<stringAttribute key='ATTR_LOCATION' value='C:\\php-cgi.exe'/> "
			+ "<stringAttribute key='debugOutputEncoding' value='UTF-8'/>"
			+ "<stringAttribute key='debugTransferEncoding' value='UTF-8'/>"
			+ "<booleanAttribute key='firstLineBreakpoint' value='true'/>"
			+ "<stringAttribute key='org.eclipse.php.debug.core.PHP_Project' value='/TestProject1'/>"
			+ "<booleanAttribute key='org.eclipse.php.debug.core.RunWithDebugInfo' value='true'/>"
			+ "<stringAttribute key='org.eclipse.php.debug.coreconfiguration_delegate_class' value='org.eclipse.php.internal.debug.core.launching.PHPExecutableLaunchDelegate'/>"
			+ "<stringAttribute key='org.eclipse.php.debug.corephp_debugger_id' value='org.eclipse.php.debug.core.zendDebugger'/>"
			+ "<stringAttribute key='php_debug_type' value='php_exe_script_debug'/>" + "</launchConfiguration>";
	private ILaunchConfiguration config;

	private IFile configFile;

	@Before
	public void setUp() throws Exception {
		project1 = TestUtils.createProject("TestProject1");
		IFolder folder = TestUtils.createFolder(project1, "source");
		IFile file = TestUtils.createFile(folder, "RenameRunConfigTest0027489file.php",
				"<?php class TestRenameClass{}?>");

		configFile = TestUtils.createFile(project1, "TestConfig0027489file.launch", configFileCont);

		config = DebugPlugin.getDefault().getLaunchManager().getLaunchConfiguration(configFile);
		ILaunchConfigurationWorkingCopy workingCopy = config.getWorkingCopy();
		workingCopy.setAttribute("ATTR_FILE_FULL_PATH", file.getLocation().toString());
		workingCopy.doSave();

		TestUtils.waitForIndexer();
	}

	@Test
	public void testRename() throws Exception {

		IFile file = project1.getProject().getFile("source/RenameRunConfigTest0027489file.php");

		Program program = createProgram(file);
		RenameFileProcessor processor = new RenameFileProcessor(file, program);

		RefactoringStatus status;
		try {
			status = processor.checkInitialConditions(new NullProgressMonitor());
			assertEquals(IStatus.OK, status.getSeverity());
		} catch (OperationCanceledException e1) {
			fail(e1.getMessage());
		} catch (CoreException e1) {
			fail(e1.getMessage());
		}

		processor.setUpdateRefernces(true);
		processor.setNewElementName("RenameRunConfigTest0027489file1.php");

		try {
			Change change = processor.createChange(new NullProgressMonitor());
			change.perform(new NullProgressMonitor());
		} catch (OperationCanceledException e) {
			fail(e.getMessage());
		} catch (CoreException e) {
			fail(e.getMessage());
		} catch (Exception e) {
			fail(e.getMessage());
		}

		try {
			config = DebugPlugin.getDefault().getLaunchManager().getLaunchConfiguration(configFile);

			String path = config.getAttribute("ATTR_FILE", "");
			assertEquals("/TestProject1/source/RenameRunConfigTest0027489file1.php", path);

			path = config.getAttribute("ATTR_FILE_FULL_PATH", "");
			assertEquals(project1.getFile("/source/RenameRunConfigTest0027489file1.php").getLocation().toString(),
					path);
		} catch (CoreException e) {
			fail(e.getMessage());
		}
	}

	@After
	public void tearDown() throws Exception {
		project1.delete(IResource.FORCE, new NullProgressMonitor());
	}
}
