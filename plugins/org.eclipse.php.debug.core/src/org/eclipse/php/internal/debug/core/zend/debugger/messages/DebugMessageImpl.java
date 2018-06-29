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

import org.eclipse.php.debug.core.debugger.messages.IDebugMessage;

public abstract class DebugMessageImpl implements IDebugMessage {

	private String fEncoding;

	@Override
	public String getTransferEncoding() {
		return fEncoding;
	}

	@Override
	public void setTransferEncoding(String encoding) {
		fEncoding = encoding;
	}

	@Override
	public String toString() {
		return new StringBuilder(this.getClass().getName().replaceFirst(".*\\.", "")).append(" [ID=").append(getType()) //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				.append(']').toString();
	}
}
