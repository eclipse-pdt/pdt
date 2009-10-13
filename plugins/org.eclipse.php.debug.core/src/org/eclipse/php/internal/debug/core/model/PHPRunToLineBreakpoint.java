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
package org.eclipse.php.internal.debug.core.model;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.php.internal.debug.core.zend.debugger.Breakpoint;

/**
 * A run to line breakpoint.
 */
public class PHPRunToLineBreakpoint extends PHPLineBreakpoint {

	private IFile fSourceFile;

	/**
	 * Constructs a run-to-line breakpoint in the given PHP program.
	 * 
	 * @param resource
	 *            PHP source file
	 * @param lineNumber
	 *            line to run to
	 * @exception DebugException
	 *                if unable to create the breakpoint
	 */
	public PHPRunToLineBreakpoint(final IFile resource, final int lineNumber)
			throws DebugException {
		IWorkspaceRunnable runnable = new IWorkspaceRunnable() {
			public void run(IProgressMonitor monitor) throws CoreException {
				// associate with workspace root to avoid drawing in editor
				// ruler
				fSourceFile = resource;
				IMarker marker = ResourcesPlugin.getWorkspace().getRoot()
						.createMarker(MARKER_ID);
				marker.setAttribute(IBreakpoint.ENABLED, Boolean.TRUE);
				marker.setAttribute(IMarker.LINE_NUMBER, lineNumber);
				marker.setAttribute(IBreakpoint.ID, getModelIdentifier());
				setMarker(marker);
				setRegistered(false);
				setPersisted(false);

			}
		};
		run(getMarkerRule(resource), runnable);
	}

	protected void createRuntimeBreakpoint(IMarker marker) throws CoreException {
		IFile file = getSourceFile();
		IPath path = file.getFullPath();
		String fileName = path.lastSegment();
		Integer lineNumber = (Integer) marker.getAttribute(IMarker.LINE_NUMBER);
		fBreakpoint = new org.eclipse.php.internal.debug.core.zend.debugger.Breakpoint(
				fileName, (lineNumber.intValue()/*-1*/));
		fBreakpoint.setLifeTime(Breakpoint.ZEND_ONETIME_BREAKPOINT);
	}

	/**
	 * Returns whether this breakpoint is a run-to-line breakpoint
	 * 
	 * @return whether this breakpoint is a run-to-line breakpoint
	 */
	public boolean isRunToLineBreakpoint() {
		return true;
	}

	/**
	 * Returns the source file this breakpoint is contained in.
	 * 
	 * @return the source file this breakpoint is contained in
	 */
	public IFile getSourceFile() {
		return fSourceFile;
	}
}
