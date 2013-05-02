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
package org.eclipse.php.internal.debug.core.zend.debugger.messages;

import org.eclipse.php.debug.core.debugger.messages.IDebugMessage;

public abstract class DebugMessageImpl implements IDebugMessage {

	private String fEncoding;

	public String getTransferEncoding() {
		return fEncoding;
	}

	public void setTransferEncoding(String encoding) {
		fEncoding = encoding;
	}

	public String toString() {
		return new StringBuilder(this.getClass().getName().replaceFirst(
				".*\\.", "")).append(" [ID=").append(getType()).append(']') //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				.toString();
	}
}
