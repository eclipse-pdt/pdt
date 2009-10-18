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
package org.eclipse.php.internal.debug.core.launching;

import java.util.Enumeration;
import java.util.Vector;

import org.eclipse.debug.core.IStreamListener;
import org.eclipse.debug.core.model.IFlushableStreamMonitor;

public class DebugConsoleMonitor implements IFlushableStreamMonitor {
	private StringBuffer fContents = new StringBuffer();
	private Vector fListeners = new Vector(1);
	private boolean fBuffered = true;

	/**
	 * @see org.eclipse.debug.core.model.IStreamMonitor#addListener(org.eclipse.debug.core.IStreamListener)
	 */
	public void addListener(IStreamListener listener) {
		fListeners.add(listener);
	}

	/**
	 * @see org.eclipse.debug.core.model.IStreamMonitor#getContents()
	 */
	public String getContents() {
		return fContents.toString();
	}

	/**
	 * @see org.eclipse.debug.core.model.IStreamMonitor#removeListener(org.eclipse.debug.core.IStreamListener)
	 */
	public void removeListener(IStreamListener listener) {
		fListeners.remove(listener);
	}

	/**
	 * Appends the given message to this stream, and notifies listeners.
	 * 
	 * @param message
	 */
	public void append(String message) {
		if (isBuffered()) {
			fContents.append(message);
		}
		Enumeration enumObject = fListeners.elements();
		while (enumObject.hasMoreElements()) {
			IStreamListener listener = ((IStreamListener) enumObject
					.nextElement());
			listener.streamAppended(message, this);
		}
	}

	/**
	 * @see org.eclipse.debug.core.model.IFlushableStreamMonitor#flushContents()
	 */
	public void flushContents() {
		fContents.setLength(0);
	}

	/**
	 * @see org.eclipse.debug.core.model.IFlushableStreamMonitor#isBuffered()
	 */
	public boolean isBuffered() {
		return fBuffered;
	}

	/**
	 * @see org.eclipse.debug.core.model.IFlushableStreamMonitor#setBuffered(boolean)
	 */
	public void setBuffered(boolean buffer) {
		fBuffered = buffer;
	}
}
