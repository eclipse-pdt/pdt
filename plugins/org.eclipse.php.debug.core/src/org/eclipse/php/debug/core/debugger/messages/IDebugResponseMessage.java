/*******************************************************************************
 * Copyright (c) 2005, 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.debug.core.debugger.messages;

public interface IDebugResponseMessage extends IDebugMessage {

	/**
	 * Set the response id.
	 */
	public void setID(int id);

	/**
	 * Return the response id.
	 */
	public int getID();

	/**
	 * Set the response status.
	 */
	public void setStatus(int status);

	/**
	 * Return the response status.
	 */
	public int getStatus();
}
