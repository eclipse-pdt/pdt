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

	/**
	 * Creates new PHP exception breakpoint.
	 */
	public PHPExceptionBreakpoint(String exceptionName) {
		this.exceptionName = exceptionName;
		try {
			IMarker marker = ResourcesPlugin.getWorkspace().getRoot().createMarker(MARKER_ID);
			marker.setAttribute(IBreakpoint.ENABLED, Boolean.TRUE);
			marker.setAttribute(IBreakpoint.ID, getModelIdentifier());
			marker.setAttribute(IMarker.MESSAGE, exceptionName);
			setMarker(marker);
			setEnabled(true);
		} catch (CoreException e) {
			Logger.logException(e);
		}
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

	/**
	 * {@link ReturnStatement} PHP exception class name.
	 * 
	 * @return PHP exception class name
	 */
	public String getExceptionName() {
		return exceptionName;
	}

}
