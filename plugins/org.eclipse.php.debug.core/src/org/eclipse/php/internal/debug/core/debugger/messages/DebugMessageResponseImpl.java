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
package org.eclipse.php.internal.debug.core.debugger.messages;

import org.eclipse.php.debug.core.debugger.messages.IDebugResponseMessage;

public abstract class DebugMessageResponseImpl extends DebugMessageImpl implements IDebugResponseMessage {

	private int id;
	private int status;

	/**
	 * Set the response id.
	 */
	public void setID(int id) {
		this.id = id;
	}

	/**
	 * Return the response id.
	 */
	public int getID() {
		return this.id;
	}

	/**
	 * Set the response status.
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * Return the response status.
	 */
	public int getStatus() {
		return this.status;
	}
}
