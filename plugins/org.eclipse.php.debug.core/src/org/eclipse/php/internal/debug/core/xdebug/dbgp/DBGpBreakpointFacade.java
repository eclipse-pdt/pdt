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
package org.eclipse.php.internal.debug.core.xdebug.dbgp;

import org.eclipse.core.resources.IFile;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IBreakpoint;

public interface DBGpBreakpointFacade {
	public DBGpBreakpoint createDBGpBreakpoint(IBreakpoint breakpoint);

	public boolean supportsBreakpoint(IBreakpoint bp);

	public IBreakpoint createRunToLineBreakpoint(IFile fileName, int lineNumber)
			throws DebugException;

	public IBreakpoint findBreakpointHit(String filename, int lineno);

	public String getBreakpointModelID();
}
