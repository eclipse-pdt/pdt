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

import org.eclipse.php.debug.core.debugger.messages.IDebugRequestMessage;

public abstract class DebugMessageRequestImpl extends DebugMessageImpl implements IDebugRequestMessage {

	private int id;

	/**
	 * Set the request id.
	 */
	@Override
	public void setID(int id) {
		this.id = id;
	}

	/**
	 * Return the request id.
	 */
	@Override
	public int getID() {
		return this.id;
	}
}
