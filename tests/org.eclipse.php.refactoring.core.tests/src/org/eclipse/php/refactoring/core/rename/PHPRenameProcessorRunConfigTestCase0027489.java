/*******************************************************************************
 * Copyright (c) 2005, 2015 Zend Technologies and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
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
import org.eclipse.php.core.tests.TestUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PHPRenameProcessorRunConfigTestCase0027489 {
	private IProject project1;

	private String configFileCont = "<?xml version='1.0' encoding='UTF-8' standalone='no'?> "
			+ "<launchConfiguration type='org.eclipse.php.debug.core.launching.PHPExeLaunchConfigurationType'>"
			+ "<stringAttribute key='ATTR_FILE' value='/TestProject1/src/RenameRunConfigTest0027489.php'/>"
			+ "<stringAttribute key='ATTR_FILE_FULL_PATH' value='C:\\src\\RenameRunConfigTest0027489'/>"
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

		IFolder folder = TestUtils.createFolder(project1, "src");
		IFile file = TestUtils.createFile(folder, "RenameRunConfigTest0027489.php",
				"<?php class TestRenameClass{}?>");
		folder.getFile("RenameRunConfigTest0027489.php");

		configFile = TestUtils.createFile(project1, "TestConfig0027489.launch", configFileCont);

		config = DebugPlugin.getDefault().getLaunchManager().getLaunchConfiguration(configFile);
		ILaunchConfigurationWorkingCopy workingCopy = config.getWorkingCopy();
		workingCopy.setAttribute("ATTR_FILE_FULL_PATH", file.getLocation().toString());
		workingCopy.doSave();

		TestUtils.waitForIndexer();
	}

	@After
	public void tearDown() throws Exception {
		project1.delete(IResource.FORCE, new NullProgressMonitor());
	}

	@Test
	public void testRename() {
		RenameFolderProcessor processor = new RenameFolderProcessor(project1.getProject().getFolder("src"));

		RefactoringStatus status = processor.checkInitialConditions(new NullProgressMonitor());

		assertEquals(IStatus.OK, status.getSeverity());

		processor.setUpdateRefernces(true);
		processor.setNewElementName("src1");

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
			assertEquals("/TestProject1/src1/RenameRunConfigTest0027489.php", path);

			path = config.getAttribute("ATTR_FILE_FULL_PATH", "");
			assertEquals(project1.getFile("/src1/RenameRunConfigTest0027489.php").getLocation().toString(), path);
		} catch (CoreException e) {
			fail(e.getMessage());
		}
	}
}
