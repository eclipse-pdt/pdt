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

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IBreakpointManager;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.php.core.tests.PHPCoreTests;
import org.eclipse.php.internal.debug.core.model.PHPConditionalBreakpoint;
import org.eclipse.php.refactoring.core.test.FileUtils;
import org.junit.Before;
import org.junit.Test;

public class PHPMoveProcessorBreakPointTestCase {
	private IProject project1;

	@Before
	public void setUp() throws Exception {
		System.setProperty("disableStartupRunner", "true");
		PHPCoreTests.waitForIndexer();
		PHPCoreTests.waitForAutoBuild();

		project1 = FileUtils.createProject("TestProject1");

		IFolder folder = project1.getFolder("src");

		if (!folder.exists()) {
			folder.create(true, true, new NullProgressMonitor());
		}
		IFile file = folder.getFile("RunBreakPoint.php");

		InputStream source = new ByteArrayInputStream(
				"<?php class TestRenameClass{}?>".getBytes());

		if (!file.exists()) {
			file.create(source, true, new NullProgressMonitor());
		} else {
			file.setContents(source, IFile.FORCE, new NullProgressMonitor());
		}

//		IMarker marker = file
//				.createMarker("org.eclipse.php.debug.core.PHPConditionalBreakpointMarker");
//		marker.setAttribute("org.eclipse.wst.sse.ui.extensions.breakpoint.path", file.getFullPath().toString());
//		System.out.println(marker.getAttribute("org.eclipse.wst.sse.ui.extensions.breakpoint.path"));

		final IBreakpointManager breakpointManager = DebugPlugin.getDefault()
				.getBreakpointManager();

		Map<String, String> attributes = new HashMap<String, String>();
		attributes.put(IMarker.LOCATION, file.getFullPath().toString());
		attributes.put("org.eclipse.wst.sse.ui.extensions.breakpoint.path", file.getFullPath().toString());

		PHPConditionalBreakpoint bp = new PHPConditionalBreakpoint(file, 1, -1,
				-1, attributes);

//		bp.setMarker(marker);
		breakpointManager.addBreakpoint(bp);

		folder = project1.getFolder("src1");

		if (!folder.exists()) {
			folder.create(true, true, new NullProgressMonitor());
		}

		PHPCoreTests.waitForIndexer();
		PHPCoreTests.waitForAutoBuild();
	}

	@Test
	public void testMoveing() {
		PHPMoveProcessor processor = new PHPMoveProcessor(project1.getProject()
				.getFolder("src"));

		RefactoringStatus status = processor
				.checkInitialConditions(new NullProgressMonitor());

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
		
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e1) {
		}

		IFile file = project1.getFile("src1/src/RunBreakPoint.php");
		try {
			IMarker[] markers = file
					.findMarkers(
							"org.eclipse.php.debug.core.PHPConditionalBreakpointMarker",
							false, IResource.DEPTH_ONE);
			assertNotNull(markers);
			assertTrue(markers.length > 0);

			final IBreakpointManager breakpointManager = DebugPlugin
					.getDefault().getBreakpointManager();
			IBreakpoint bp = breakpointManager.getBreakpoint(markers[0]);

			assertTrue(bp instanceof PHPConditionalBreakpoint);
			assertEquals(1, ((PHPConditionalBreakpoint) bp).getLineNumber());

		} catch (CoreException e) {
			fail(e.getMessage());
		}

	}
}
