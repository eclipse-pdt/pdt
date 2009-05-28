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
package org.eclipse.php.internal.debug.core.xdebug.dbgp.session;

public class Event {
	private boolean raised = false;

	public synchronized void waitForEvent() throws InterruptedException {
		while (!raised) {
			wait();
		}
		raised = false;
	}

	/**
	 * Signal the event has occurred.
	 */
	public synchronized void signalEvent() {
		raised = true;
		notifyAll();
	}

	/**
	 * Reset the signalled state of the event. Use for non autoresettable events
	 */
	public synchronized void reset() {
		raised = false;
	}
}
