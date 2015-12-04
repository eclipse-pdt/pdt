/*******************************************************************************
 * Copyright (c) 2015 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.core.model;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.model.Breakpoint;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.php.internal.core.ast.nodes.ReturnStatement;
import org.eclipse.php.internal.debug.core.IPHPDebugConstants;
import org.eclipse.php.internal.debug.core.Logger;

/**
 * PHP exception breakpoint.
 * 
 * @author Bartlomiej Laczkowski
 */
public class PHPExceptionBreakpoint extends Breakpoint {

	public static final String MARKER_ID = "org.eclipse.php.debug.core.PHPExceptionBreakpointMarker"; //$NON-NLS-1$

	private String exceptionName;
	private int lineNumber = -1;

	/**
	 * Creates new "common" PHP exception breakpoint which internal data will be
	 * set with the use of related persistent marker, see
	 * {@link PHPExceptionBreakpoint#setMarker(IMarker)}.
	 */
	public PHPExceptionBreakpoint() {
		super();
	}

	/**
	 * Creates new "common" PHP exception breakpoint. This constructor should be
	 * used when new type of exception breakpoint should be added and registered
	 * by breakpoint manager.
	 * 
	 * @param exceptionName
	 *            - exception type name
	 */
	public PHPExceptionBreakpoint(String exceptionName) {
		this.exceptionName = exceptionName;
		try {
			IMarker marker = ResourcesPlugin.getWorkspace().getRoot().createMarker(MARKER_ID);
			marker.setAttribute(IBreakpoint.PERSISTED, Boolean.TRUE);
			marker.setAttribute(IBreakpoint.ENABLED, Boolean.TRUE);
			marker.setAttribute(IBreakpoint.ID, getModelIdentifier());
			marker.setAttribute(IMarker.MESSAGE, exceptionName);
			setMarker(marker);
			setEnabled(true);
		} catch (CoreException e) {
			Logger.logException(e);
		}
	}

	/**
	 * Creates dedicated PHP exception breakpoint that can provide additional
	 * data like line number. This constructor should be used to create
	 * "temporary" breakpoint that can be attached to particular stack
	 * frame/thread after the exception breakpoint hit.
	 * 
	 * @param exceptionName
	 *            - exception type name
	 * @param lineNumber
	 *            - line number for the breakpoint hit
	 */
	public PHPExceptionBreakpoint(String exceptionName, int lineNumber) {
		this.exceptionName = exceptionName;
		this.lineNumber = lineNumber;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IBreakpoint#getModelIdentifier()
	 */
	@Override
	public String getModelIdentifier() {
		return IPHPDebugConstants.ID_PHP_DEBUG_CORE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.Breakpoint#setMarker(org.eclipse.core.
	 * resources.IMarker)
	 */
	@Override
	public void setMarker(IMarker marker) throws CoreException {
		super.setMarker(marker);
		exceptionName = marker.getAttribute(IMarker.MESSAGE, null);
	}

	/**
	 * {@link ReturnStatement} PHP exception class name.
	 * 
	 * @return PHP exception class name
	 */
	public String getExceptionName() {
		return exceptionName;
	}

	/**
	 * @return the lineNumber
	 */
	public int getLineNumber() {
		return lineNumber;
	}

}
