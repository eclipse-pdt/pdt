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

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.core.model.LineBreakpoint;
import org.eclipse.osgi.util.NLS;
import org.eclipse.php.internal.debug.core.IPHPDebugConstants;
import org.eclipse.php.internal.debug.core.PHPDebugCoreMessages;
import org.eclipse.wst.sse.ui.internal.StructuredResourceMarkerAnnotationModel;

/**
 * PHP line breakpoint
 */
public class PHPLineBreakpoint extends LineBreakpoint {

	public static final String MARKER_ID = "org.eclipse.php.debug.core.PHPConditionalBreakpointMarker"; //$NON-NLS-1$
	protected org.eclipse.php.internal.debug.core.zend.debugger.Breakpoint fBreakpoint;
	protected boolean fConditionChanged = false;

	public PHPLineBreakpoint() {
	}

	/**
	 * Constructs a line breakpoint on the given resource at the given line
	 * number.
	 * 
	 * @param resource
	 *            file on which to set the breakpoint
	 * @param lineNumber
	 *            1-based line number of the breakpoint
	 * @throws CoreException
	 *             if unable to create the breakpoint
	 */
	public PHPLineBreakpoint(final IResource resource, final int lineNumber)
			throws CoreException {

		IWorkspaceRunnable runnable = new IWorkspaceRunnable() {
			public void run(IProgressMonitor monitor) throws CoreException {
				IMarker marker = resource.createMarker(MARKER_ID);
				marker.setAttribute(IBreakpoint.ENABLED, Boolean.TRUE);
				marker.setAttribute(IMarker.LINE_NUMBER, lineNumber);
				marker.setAttribute(IBreakpoint.ID, getModelIdentifier());
				marker.setAttribute(IMarker.MESSAGE, NLS.bind(
						PHPDebugCoreMessages.LineBreakPointMessage_1,
						new String[] { resource.getName(),
								Integer.toString(lineNumber) }));
				setMarker(marker);
				setEnabled(true);
				register(true);
			}
		};
		run(getMarkerRule(resource), runnable);
	}

	protected void register(boolean register) throws CoreException {
		if (register) {
			DebugPlugin.getDefault().getBreakpointManager().addBreakpoint(this);
		} else {
			setRegistered(false);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IBreakpoint#getModelIdentifier()
	 */
	public String getModelIdentifier() {
		return IPHPDebugConstants.ID_PHP_DEBUG_CORE;
	}

	protected void createRuntimeBreakpoint(IMarker marker) throws CoreException {

		String fileName = (String) marker
				.getAttribute(StructuredResourceMarkerAnnotationModel.SECONDARY_ID_KEY);
		if (fileName == null) {
			fileName = (String) marker.getAttribute(IMarker.LOCATION);
		}

		Integer lineNumber = (Integer) marker.getAttribute(IMarker.LINE_NUMBER);
		fBreakpoint = new org.eclipse.php.internal.debug.core.zend.debugger.Breakpoint(
				fileName, (lineNumber.intValue()));
		fBreakpoint.setEnable(true);
	}

	public org.eclipse.php.internal.debug.core.zend.debugger.Breakpoint getRuntimeBreakpoint() {
		return fBreakpoint;
	}

	public void setMarker(IMarker marker) throws CoreException {
		super.setMarker(marker);
		createRuntimeBreakpoint(marker);
	}

	public void setEnabled(boolean enabled) throws CoreException {
		super.setEnabled(enabled);
	}

	public void setConditionChanged(boolean conditionChanged) {
		fConditionChanged = conditionChanged;
	}

	public boolean isConditionChanged() {
		return fConditionChanged;
	}

}
