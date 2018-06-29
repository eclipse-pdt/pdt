/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.debug.core.xdebug.dbgp;

import org.eclipse.debug.core.model.IBreakpoint;

public interface DBGpBreakpointFacade {
	public DBGpBreakpoint createDBGpBreakpoint(IBreakpoint breakpoint);

	public boolean supportsBreakpoint(IBreakpoint bp);

	public IBreakpoint findBreakpointHit(String filename, int lineno, String exception);

	public String getBreakpointModelID();
}
