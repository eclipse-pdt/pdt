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
package org.eclipse.php.internal.debug.core.zend.debugger.messages;

import org.eclipse.php.debug.core.debugger.messages.IDebugResponseMessage;

public abstract class DebugMessageResponseImpl extends DebugMessageImpl implements IDebugResponseMessage {

	private int id;
	private int status;

	/**
	 * Set the response id.
	 */
	@Override
	public void setID(int id) {
		this.id = id;
	}

	/**
	 * Return the response id.
	 */
	@Override
	public int getID() {
		return this.id;
	}

	/**
	 * Set the response status.
	 */
	@Override
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * Return the response status.
	 */
	@Override
	public int getStatus() {
		return this.status;
	}
}
