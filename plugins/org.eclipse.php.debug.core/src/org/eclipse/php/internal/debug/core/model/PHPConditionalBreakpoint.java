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

import java.util.Map;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.osgi.util.NLS;
import org.eclipse.php.internal.debug.core.IPHPDebugConstants;
import org.eclipse.php.internal.debug.core.PHPDebugCoreMessages;

import com.ibm.icu.text.MessageFormat;

public class PHPConditionalBreakpoint extends PHPLineBreakpoint {

	private boolean fConditionEnabled = false;
	private String fCondition = ""; //$NON-NLS-1$

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
	public PHPConditionalBreakpoint(final IResource resource,
			final int lineNumber, final Map attributes) throws CoreException {
		IWorkspaceRunnable runnable = new IWorkspaceRunnable() {
			public void run(IProgressMonitor monitor) throws CoreException {
				IMarker marker = resource.createMarker(MARKER_ID);
				attributes.put(IBreakpoint.ENABLED, Boolean.TRUE);
				attributes.put(IBreakpoint.ID, getModelIdentifier());
				attributes.put(IMarker.MESSAGE, MessageFormat.format(
						PHPDebugCoreMessages.LineBreakPointMessage_1,
						new String[] { resource.getName(),
								Integer.toString(lineNumber) }));
				attributes.put(IPHPDebugConstants.ConditionEnabled,
						Boolean.FALSE);
				attributes.put(IPHPDebugConstants.Condition, ""); //$NON-NLS-1$
				attributes.put(IBreakpoint.PERSISTED, Boolean.FALSE);
				attributes.put(IMarker.LINE_NUMBER, new Integer(lineNumber));
				marker.setAttributes(attributes);
				setMarker(marker);
				setEnabled(true);
				register(true);
				setPersisted(true);
			}
		};
		resource.getWorkspace().run(runnable, null, IWorkspace.AVOID_UPDATE,
				null);
	}

	public void setMarker(IMarker marker) throws CoreException {
		super.setMarker(marker);
		fCondition = (String) marker.getAttribute(IPHPDebugConstants.Condition);
		Boolean enabled = (Boolean) marker
				.getAttribute(IPHPDebugConstants.ConditionEnabled);
		fConditionEnabled = enabled != null ? enabled.booleanValue() : false;
		addConditionToBP();
	}

	public void setConditionWithEnable(boolean enabled, String condition)
			throws CoreException {
		fCondition = condition;
		IMarker marker = getMarker();
		marker.setAttribute(IPHPDebugConstants.Condition, condition);
		fConditionEnabled = enabled;
		marker.setAttribute(IPHPDebugConstants.ConditionEnabled, new Boolean(
				enabled));
		int lineNumber = ((Integer) marker.getAttribute(IMarker.LINE_NUMBER))
				.intValue();
		if (enabled) {
			String message = NLS.bind(
					PHPDebugCoreMessages.ConditionalBreakPointMessage_1,
					new String[] { marker.getResource().getName(),
							Integer.toString(lineNumber) });
			message += NLS.bind(
					PHPDebugCoreMessages.ConditionalBreakPointMessage_2,
					new String[] { condition });
			marker.setAttribute(IMarker.MESSAGE, message);
		} else {
			marker.setAttribute(IMarker.MESSAGE, NLS.bind(
					PHPDebugCoreMessages.LineBreakPointMessage_1, new String[] {
							marker.getResource().getName(),
							Integer.toString(lineNumber) }));
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
