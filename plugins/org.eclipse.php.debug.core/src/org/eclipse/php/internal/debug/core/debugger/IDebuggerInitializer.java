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
package org.eclipse.php.internal.debug.core.debugger;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.ILaunch;

/**
 * An {@link IDebuggerInitializer} is the interface for all the debug initializers that handle debug 
 * requests and trigger a debug session by sending the correct information to the debug server.
 * 
 * @author shalom
 */
public interface IDebuggerInitializer {

	/**
	 * Start a debug session that is linked to the given {@link ILaunch}.
	 * 
	 * @param launch
	 * @throws DebugException
	 */
	void debug(ILaunch launch) throws DebugException;

}
