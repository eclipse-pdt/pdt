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
package org.eclipse.php.refactoring.core.move;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IBreakpointManager;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.debug.core.model.PHPConditionalBreakpoint;
import org.eclipse.php.refactoring.core.test.FileUtils;
import org.junit.After;
import org.junit.Test;

public class PHPMoveProcessorBreakPointTestCase {

	private IProject project1;

	@After
	public void tearDown() throws Exception {
		project1.delete(IResource.FORCE, new NullProgressMonitor());
	}

	@Test
	public void testMoveing() {
		IWorkspaceRunnable workspaceRunnable = new IWorkspaceRunnable() {
			@Override
			public void run(IProgressMonitor monitor) throws CoreException {

				System.setProperty("disableStartupRunner", "true");
				project1 = FileUtils.createProject("TestProject1");
				IFolder folder = project1.getFolder("src");
				if (!folder.exists()) {
					folder.create(true, true, new NullProgressMonitor());
				}
				IFile file = folder.getFile("RunBreakPoint.php");
				InputStream source = new ByteArrayInputStream("<?php class TestRenameClass{}?>".getBytes());
				if (!file.exists()) {
					file.create(source, true, new NullProgressMonitor());
				} else {
					file.setContents(source, IFile.FORCE, new NullProgressMonitor());
				}
				Map<String, String> attributes = new HashMap<String, String>();
				attributes.put(IMarker.LOCATION, file.getFullPath().toString());
				attributes.put("org.eclipse.wst.sse.ui.extensions.breakpoint.path", file.getFullPath().toString());
				new PHPConditionalBreakpoint(file, 1, -1, -1, attributes);
				folder = project1.getFolder("src1");
				if (!folder.exists()) {
					folder.create(true, true, new NullProgressMonitor());
				}
				project1.build(IncrementalProjectBuilder.INCREMENTAL_BUILD, monitor);

				PHPMoveProcessor processor = new PHPMoveProcessor(project1.getProject().getFolder("src"));
				RefactoringStatus status = processor.checkInitialConditions(new NullProgressMonitor());
				assertEquals(IStatus.OK, status.getSeverity());
				processor.setDestination(project1.getFolder("src1"));
				processor.setUpdateReferences(true);
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
				project1.build(IncrementalProjectBuilder.INCREMENTAL_BUILD, monitor);
				IFile file2 = project1.getFile("src1/src/RunBreakPoint.php");
				try {
					IMarker[] markers = file2.findMarkers("org.eclipse.php.debug.core.PHPConditionalBreakpointMarker",
							false, IResource.DEPTH_ONE);
					assertNotNull(markers);
					assertTrue(markers.length > 0);
					final IBreakpointManager breakpointManager = DebugPlugin.getDefault().getBreakpointManager();
					IBreakpoint breakpoint = breakpointManager.getBreakpoint(markers[0]);
					assertTrue(breakpoint instanceof PHPConditionalBreakpoint);
					assertEquals(1, ((PHPConditionalBreakpoint) breakpoint).getLineNumber());
				} catch (CoreException e) {
					fail(e.getMessage());
				}
			}
		};
		try {
			ResourcesPlugin.getWorkspace().run(workspaceRunnable, ResourcesPlugin.getWorkspace().getRoot(),
					IWorkspace.AVOID_UPDATE, null);
		} catch (CoreException e) {
			Logger.logException(e);
		}

	}
}
