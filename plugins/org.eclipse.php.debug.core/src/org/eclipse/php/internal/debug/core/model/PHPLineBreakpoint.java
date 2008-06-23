/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.core.model;

import java.text.MessageFormat;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.core.model.LineBreakpoint;
import org.eclipse.php.internal.debug.core.IPHPDebugConstants;
import org.eclipse.php.internal.debug.core.PHPDebugCoreMessages;

/**
 * PHP line breakpoint
 */
public class PHPLineBreakpoint extends LineBreakpoint {

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
    public PHPLineBreakpoint(final IResource resource, final int lineNumber) throws CoreException {

        IWorkspaceRunnable runnable = new IWorkspaceRunnable() {
            public void run(IProgressMonitor monitor) throws CoreException {
                IMarker marker = resource.createMarker("org.eclipse.php.debug.core.PHPConditionalBreakpointMarker");
                marker.setAttribute(IBreakpoint.ENABLED, Boolean.TRUE);
                marker.setAttribute(IMarker.LINE_NUMBER, lineNumber);
                marker.setAttribute(IBreakpoint.ID, getModelIdentifier());
                marker.setAttribute(IMarker.MESSAGE, MessageFormat.format(PHPDebugCoreMessages.LineBreakPointMessage_1, new String[] { resource.getName(), Integer.toString(lineNumber) }));
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
        IResource resource = marker.getResource();       
        String fileName = "";
        if (resource instanceof IWorkspaceRoot) {
            fileName = (String)marker.getAttribute(IPHPDebugConstants.STORAGE_FILE); 
        }else {
            IFile file = (IFile) resource;
            IPath path = file.getFullPath();
            fileName = path.lastSegment();
        }
        Integer lineNumber = (Integer) marker.getAttribute(IMarker.LINE_NUMBER);
        fBreakpoint = new org.eclipse.php.internal.debug.core.zend.debugger.Breakpoint(fileName, (lineNumber.intValue()));
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
