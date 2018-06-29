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
