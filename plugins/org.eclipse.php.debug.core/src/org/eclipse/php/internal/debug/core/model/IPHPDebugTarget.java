/*******************************************************************************
 * Copyright (c) 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.core.model;

import org.eclipse.debug.core.model.IDebugTarget;

/**
 * Interface which debug targets need to implement to 
 * allow access to shared facilities within PDT.
 */
public interface IPHPDebugTarget extends IDebugTarget {
	
	/**
	 * return the Output Buffer containing the output from the debuggee
	 * @return DebugOutput containing the output.
	 */
	public DebugOutput getOutputBuffer();
}
