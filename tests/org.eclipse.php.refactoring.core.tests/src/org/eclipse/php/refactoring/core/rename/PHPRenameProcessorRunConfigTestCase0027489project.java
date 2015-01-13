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

import java.io.ByteArrayInputStream;
import java.io.InputStream;

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
import org.eclipse.php.core.tests.PHPCoreTests;
import org.eclipse.php.refactoring.core.test.FileUtils;

public class PHPRenameProcessorRunConfigTestCase0027489project extends
		AbstractRenameRefactoringTest {
	private IProject project1;

	private String configFileCont = "<?xml version='1.0' encoding='UTF-8' standalone='no'?> "
			+ "<launchConfiguration type='org.eclipse.php.debug.core.launching.PHPExeLaunchConfigurationType'>"
			+ "<stringAttribute key='ATTR_FILE' value='/TestProject1/src/RenameRunConfigTest0027489proj.php'/>"
			+ "<stringAttribute key='ATTR_FILE_FULL_PATH' value='C:\\src\\RenameRunConfigTest0027489proj.php'/>"
			+ "<stringAttribute key='ATTR_LOCATION' value='C:\\php-cgi.exe'/> "
			+ "<stringAttribute key='debugOutputEncoding' value='UTF-8'/>"
			+ "<stringAttribute key='debugTransferEncoding' value='UTF-8'/>"
			+ "<booleanAttribute key='firstLineBreakpoint' value='true'/>"
			+ "<stringAttribute key='org.eclipse.php.debug.core.PHP_Project' value='/TestProject1'/>"
			+ "<booleanAttribute key='org.eclipse.php.debug.core.RunWithDebugInfo' value='true'/>"
			+ "<stringAttribute key='org.eclipse.php.debug.coreconfiguration_delegate_class' value='org.eclipse.php.internal.debug.core.launching.PHPExecutableLaunchDelegate'/>"
			+ "<stringAttribute key='org.eclipse.php.debug.corephp_debugger_id' value='org.eclipse.php.debug.core.zendDebugger'/>"
			+ "<stringAttribute key='php_debug_type' value='php_exe_script_debug'/>"
			+ "</launchConfiguration>";
	private ILaunchConfiguration config;

	private IFile configFile;

	@Override
	protected void setUp() throws Exception {
		System.setProperty("disableStartupRunner", "true");
		PHPCoreTests.waitForIndexer();
		PHPCoreTests.waitForAutoBuild();

		project1 = FileUtils.createProject("TestProject1");

		IFolder folder = project1.getFolder("src");

		if (!folder.exists()) {
			folder.create(true, true, new NullProgressMonitor());
		}
		IFile file = folder.getFile("RenameRunConfigTest0027489proj.php");

		InputStream source = new ByteArrayInputStream(
				"<?php class TestRenameClass{}?>".getBytes());

		if (!file.exists()) {
			file.create(source, true, new NullProgressMonitor());
		} else {
			file.setContents(source, IFile.FORCE, new NullProgressMonitor());
		}

		source = new ByteArrayInputStream(configFileCont.getBytes());

		configFile = project1.getFile("TestConfig0027489proj.launch");
		if (!configFile.exists()) {
			configFile.create(source, IFile.FORCE, new NullProgressMonitor());
		} else {
			configFile.setContents(source, IFile.FORCE,
					new NullProgressMonitor());
		}

		config = DebugPlugin.getDefault().getLaunchManager()
				.getLaunchConfiguration(configFile);
		ILaunchConfigurationWorkingCopy workingCopy = config.getWorkingCopy();
		workingCopy.setAttribute("ATTR_FILE_FULL_PATH", file.getLocation()
				.toString());
		workingCopy.doSave();

		PHPCoreTests.waitForIndexer();
		PHPCoreTests.waitForAutoBuild();
	}

	public void testRename() {

		RenameFolderProcessor processor = new RenameFolderProcessor(project1);

		RefactoringStatus status = processor
				.checkInitialConditions(new NullProgressMonitor());

		assertEquals(IStatus.OK, status.getSeverity());

		processor.setUpdateRefernces(true);
		processor.setNewElementName("TestProject2");

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
			config = DebugPlugin.getDefault().getLaunchManager()
					.getLaunchConfiguration(configFile);

			String path = config.getAttribute("ATTR_FILE", "");
			assertEquals(
					"/TestProject2/src/RenameRunConfigTest0027489proj.php",
					path);

			path = config.getAttribute("ATTR_FILE_FULL_PATH", "");
			assertEquals(project1.getFile(
					"/src/RenameRunConfigTest0027489proj.php").getLocation()
					.toString(), path);
		} catch (CoreException e) {
			fail(e.getMessage());
		}
	}

	@Override
	protected String getTestDirectory() {
		return "";
	}

	@Override
	protected void tearDown() throws Exception {
		project1.delete(IResource.FOLDER, new NullProgressMonitor());
	}
}
