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
package org.eclipse.php.internal.debug.core.xdebug.breakpoints;

import org.eclipse.core.resources.IProject;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.debug.core.sourcelookup.ISourceContainer;
import org.eclipse.debug.internal.ui.views.launch.SourceNotFoundEditorInput;
import org.eclipse.php.internal.core.util.FileUtils;
import org.eclipse.php.internal.debug.core.IPHPDebugConstants;
import org.eclipse.php.internal.debug.core.model.IPHPExceptionBreakpoint;
import org.eclipse.php.internal.debug.core.model.PHPLineBreakpoint;
import org.eclipse.php.internal.debug.core.sourcelookup.containers.PHPCompositeSourceContainer;
import org.eclipse.php.internal.debug.core.xdebug.IDELayer;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.DBGpBreakpoint;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.DBGpBreakpointFacade;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.DBGpLogger;
import org.eclipse.php.internal.debug.core.zend.debugger.Breakpoint;

public class PdtLayer implements IDELayer, DBGpBreakpointFacade {

	public String getBreakpointModelID() {
		return IPHPDebugConstants.ID_PHP_DEBUG_CORE;
	}

	public Object sourceNotFound(Object debugElement) {
		Object obj = null;
		if (debugElement instanceof IStackFrame) {
			obj = new SourceNotFoundEditorInput((IStackFrame) debugElement);
		}
		return obj;
	}

	public ISourceContainer getSourceContainer(IProject resource, ILaunchConfiguration launchConfig) {
		return new PHPCompositeSourceContainer(resource, launchConfig);
	}

	public DBGpBreakpoint createDBGpBreakpoint(IBreakpoint breakpoint) {
		if (breakpoint instanceof PHPLineBreakpoint) {
			return new DBGpLineBreakpoint((PHPLineBreakpoint) breakpoint);
		}
		if (breakpoint instanceof IPHPExceptionBreakpoint) {
			return new DBGpExceptionBreakpoint((IPHPExceptionBreakpoint) breakpoint);
		}
		return null;
	}

	public IBreakpoint findBreakpointHit(String sourceFileLocation, int lineno, String exception) {
		IBreakpoint bpFound = null;
		IBreakpoint[] breakpoints = DebugPlugin.getDefault().getBreakpointManager()
				.getBreakpoints(getBreakpointModelID());
		for (int i = 0; i < breakpoints.length; i++) {
			IBreakpoint breakpoint = breakpoints[i];
			if (supportsBreakpoint(breakpoint)) {
				if (breakpoint instanceof PHPLineBreakpoint && exception.isEmpty()) {
					PHPLineBreakpoint lineBreakpoint = (PHPLineBreakpoint) breakpoint;
					Breakpoint zBP = lineBreakpoint.getRuntimeBreakpoint();
					String bpFileLocation = zBP.getFileName();
					int bLineNumber = zBP.getLineNumber();
					try {
						if (bLineNumber == lineno && FileUtils.isSameFile(bpFileLocation, sourceFileLocation)) {
							bpFound = breakpoint;
							if (DBGpLogger.debugBP()) {
								DBGpLogger.debug("breakpoint at " + sourceFileLocation + "(" //$NON-NLS-1$ //$NON-NLS-2$
										+ lineno + ") found"); //$NON-NLS-1$
							}
						}
					} catch (Exception e) {
						/*
						 * Ignore as some path descriptors might be illegal for
						 * this check e.g. DLTK external library scripts (see
						 * next step).
						 */
					}
					// TODO - support for DLTK external libraries
				} else if (breakpoint instanceof IPHPExceptionBreakpoint) {
					IPHPExceptionBreakpoint exBreakpoint = (IPHPExceptionBreakpoint) breakpoint;
					if (exBreakpoint.getExceptionName().equals(exception)) {
						bpFound = exBreakpoint;
						if (DBGpLogger.debugBP()) {
							DBGpLogger.debug("exception breakpoint at " + sourceFileLocation + "(" //$NON-NLS-1$ //$NON-NLS-2$
									+ lineno + ") found"); //$NON-NLS-1$
						}
					}
				}
			}
		}
		return bpFound;
	}

	public boolean supportsBreakpoint(IBreakpoint bp) {
		if (bp.getModelIdentifier().equals(getBreakpointModelID())) {
			// TODO: Improvement: Breakpoint: better support for breakpoint
			// rejection
			// ok it is a PHP breakpoint, but are there any other restrictions
			// we could impose ?
			// look at BreakpointSet for more info on what PHPIDE does
			return true;
		}
		return false;
	}

	public String getSystemDebugProperty() {
		return "org.eclipse.php.debug.ui.activeDebugging"; //$NON-NLS-1$
	}

}
