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

import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.core.model.IDebugTarget;

/**
 * Common interface for PHP exception breakpoints.
 * 
 * @author Bartlomiej Laczkowski
 */
public interface IPHPExceptionBreakpoint extends IBreakpoint {

	/**
	 * PHP exception breakpoint type.
	 */
	public enum Type {
		EXCEPTION, ERROR;
	}

	/**
	 * Returns PHP exception class or error name.
	 * 
	 * @return PHP exception class or error name
	 */
	public String getExceptionName();

	/**
	 * Returns line number that corresponds to given debug target.
	 * 
	 * @return the lineNumber
	 */
	public int getLine(IDebugTarget target);

	/**
	 * Returns the breakpoint ID that corresponds to given debug target.
	 * 
	 * @return the breakpoint ID
	 */
	public int getId(IDebugTarget target);

	/**
	 * Returns type of this breakpoint.
	 * 
	 * @return type of this breakpoint
	 */
	public Type getType();

	/**
	 * Sets debug target related line number for this breakpoint (exception
	 * breakpoint was hit).
	 * 
	 * @param target
	 *            related debug target
	 * @param line
	 *            breakpoint line number
	 */
	public void setLine(IDebugTarget target, int line);

	/**
	 * Sets debug target related breakpoint id (exception breakpoint was
	 * registered).
	 * 
	 * @param target
	 *            related debug target
	 * @param line
	 *            breakpoint line number
	 */
	public void setId(IDebugTarget target, int id);

}
