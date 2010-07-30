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

import org.eclipse.debug.core.model.IDebugTarget;

/**
 * Interface which debug targets need to implement to allow access to shared
 * facilities within PDT.
 */
public interface IPHPDebugTarget extends IDebugTarget {

	/**
	 * return the Output Buffer containing the output from the debuggee
	 * 
	 * @return DebugOutput containing the output.
	 */
	public DebugOutput getOutputBuffer();

	/**
	 * return true if the debug target is waiting for the next debug session. This
	 * concept is applicable currently only to the xdebug implementation, zend
	 * implementation will always return false.
	 * 
	 * @return true if xdebug is waiting for the next debug session.
	 */
	public boolean isWaiting();
}
