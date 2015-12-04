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
package org.eclipse.php.internal.debug.core.xdebug.breakpoints;

import org.eclipse.core.resources.IFile;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.php.internal.debug.core.model.IPHPExceptionBreakpoint;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.DBGpBreakpoint;

/**
 * DBGp exception breakpoint.
 * 
 * @author Bartlomiej Laczkowski
 */
public class DBGpExceptionBreakpoint implements DBGpBreakpoint {

	private IPHPExceptionBreakpoint bp;

	/**
	 * Creates new DBGp exception breakpoint.
	 */
	public DBGpExceptionBreakpoint(IPHPExceptionBreakpoint bp) {
		this.bp = bp;
	}

	@Override
	public int getID() {
		return 0;
	}

	@Override
	public void setID(int id) {
		// ignore
	}

	@Override
	public IBreakpoint getBreakpoint() {
		return bp;
	}

	@Override
	public void setBreakpoint(IBreakpoint breakpoint) {
		// ignore
	}

	@Override
	public IFile getIFile() {
		return null;
	}

	@Override
	public String getFileName() {
		return null;
	}

	@Override
	public int getLineNumber() {
		return 0;
	}

	@Override
	public boolean isException() {
		return true;
	}

	@Override
	public boolean isConditional() {
		return false;
	}

	@Override
	public boolean isConditionEnabled() {
		return false;
	}

	@Override
	public boolean hasConditionChanged() {
		return false;
	}

	@Override
	public void resetConditionChanged() {
		// ignore
	}

	@Override
	public String getExpression() {
		return null;
	}

	@Override
	public String getException() {
		return bp.getExceptionName();
	}

}
