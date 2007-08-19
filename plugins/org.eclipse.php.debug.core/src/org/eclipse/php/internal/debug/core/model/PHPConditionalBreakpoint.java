/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
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
import java.util.Map;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.php.internal.debug.core.IPHPConstants;
import org.eclipse.php.internal.debug.core.PHPDebugCoreMessages;

public class PHPConditionalBreakpoint extends PHPLineBreakpoint {

    private boolean fConditionEnabled = false;

    private String fCondition = "";

    public PHPConditionalBreakpoint() {
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
    public PHPConditionalBreakpoint(final IResource resource, final int lineNumber, final Map attributes) throws CoreException {
        IWorkspaceRunnable runnable = new IWorkspaceRunnable() {
            public void run(IProgressMonitor monitor) throws CoreException {
                IMarker marker = resource.createMarker("org.eclipse.php.debug.core.PHPConditionalBreakpointMarker");
/*                marker.setAttribute(IBreakpoint.ENABLED, Boolean.TRUE);
                marker.setAttribute(IBreakpoint.ID, getModelIdentifier());
                marker.setAttribute(IMarker.MESSAGE, MessageFormat.format(PHPDebugCoreMessages.LineBreakPointMessage_1, new String[] { resource.getName(), Integer.toString(lineNumber) }));
                marker.setAttribute(IPHPConstants.ConditionEnabled, Boolean.FALSE);
                marker.setAttribute(IPHPConstants.Condition, "");
                marker.setAttribute(IBreakpoint.PERSISTED, true);
                marker.setAttribute(IMarker.LINE_NUMBER, lineNumber);*/
                
        
                attributes.put(IBreakpoint.ENABLED, Boolean.TRUE);
                attributes.put(IBreakpoint.ID, getModelIdentifier());
                attributes.put(IMarker.MESSAGE, MessageFormat.format(PHPDebugCoreMessages.LineBreakPointMessage_1, new String[] { resource.getName(), Integer.toString(lineNumber) }));
                attributes.put(IPHPConstants.ConditionEnabled, Boolean.FALSE);
                attributes.put(IPHPConstants.Condition, "");
                attributes.put(IBreakpoint.PERSISTED, Boolean.FALSE);
                attributes.put(IMarker.LINE_NUMBER, new Integer(lineNumber));
                marker.setAttributes(attributes);
//                if (attributes != null) {
//                    Object attribute = attributes.get(IPHPConstants.Include_Storage);
//                    marker.setAttribute(IPHPConstants.Include_Storage, attribute);
//                    attribute = attributes.get("org.eclipse.wst.sse.ui.extensions.breakpoint.path");
//                    marker.setAttribute("org.eclipse.wst.sse.ui.extensions.breakpoint.path", attribute);
//                }
                setMarker(marker);
                setEnabled(true);
                register(true);
                setPersisted(true);
            }
        };
        resource.getWorkspace().run(runnable, null,IWorkspace.AVOID_UPDATE, null);
    }

    public void setMarker(IMarker marker) throws CoreException {
        super.setMarker(marker);
        fCondition = (String) marker.getAttribute(IPHPConstants.Condition);
        Boolean enabled = (Boolean) marker.getAttribute(IPHPConstants.ConditionEnabled);
        fConditionEnabled = enabled != null ? enabled.booleanValue() : false;
        addConditionToBP();
    }

    public void setConditionWithEnable(boolean enabled, String condition) throws CoreException {
        fCondition = condition;
        IMarker marker = getMarker();
        marker.setAttribute(IPHPConstants.Condition, condition);
        fConditionEnabled = enabled;
        marker.setAttribute(IPHPConstants.ConditionEnabled, new Boolean(enabled));
        int lineNumber = ((Integer) marker.getAttribute(IMarker.LINE_NUMBER)).intValue();
        if (enabled) {
        	String message = MessageFormat.format(PHPDebugCoreMessages.ConditionalBreakPointMessage_1, new String[] { marker.getResource().getName(), Integer.toString(lineNumber) });
        	message +=  MessageFormat.format(PHPDebugCoreMessages.ConditionalBreakPointMessage_2, new String[] {condition});
            marker.setAttribute(IMarker.MESSAGE, message);
        } else {
            marker.setAttribute(IMarker.MESSAGE, MessageFormat.format(PHPDebugCoreMessages.LineBreakPointMessage_1, new String[] { marker.getResource().getName(), Integer.toString(lineNumber) }));
        }
        addConditionToBP();
        setConditionChanged(true);
    }

    public String getCondition() {
        return fCondition;
    }

    public boolean isConditionEnabled() {
        return fConditionEnabled;
    }

    protected void addConditionToBP() {
        org.eclipse.php.internal.debug.core.zend.debugger.Breakpoint rbp = getRuntimeBreakpoint();
        if (isConditionEnabled()) {
            rbp.setConditionalFlag(true);
            rbp.setExpression(getCondition());
        } else {
            rbp.setConditionalFlag(false);
            rbp.setStaticFlag(true);
        }
    }

}
