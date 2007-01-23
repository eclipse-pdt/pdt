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

import org.eclipse.php.debug.core.debugger.messages.IDebugMessage;

public abstract class DebugMessageImpl implements IDebugMessage {
	
	private String fEncoding;

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.debug.core.debugger.messages.IDebugMessage#getEncoding()
	 */
	public String getTransferEncoding() {
		return fEncoding;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.debug.core.debugger.messages.IDebugMessage#setEncoding(java.lang.String)
	 */
	public void setTransferEncoding(String encoding) {
		fEncoding = encoding;
	}
}
