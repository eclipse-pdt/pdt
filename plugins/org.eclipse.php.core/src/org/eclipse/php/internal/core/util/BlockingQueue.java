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
package org.eclipse.php.internal.core.util;

import java.util.ArrayList;

/**
 * A BlockingQueue is a simple queue that blocks the thread that calls it when
 * trying to queue-out from an empty queue.
 */
public class BlockingQueue {

	static Object emptyObject = new Object();

	private ArrayList content;
	private boolean releaseFlag = false;

	public BlockingQueue() {
		this(100);
	}

	public BlockingQueue(int size) {
		content = new ArrayList(size);
	}

	/**
	 * Add en element to the queue.
	 */
	public synchronized void queueIn(Object obj) {
		content.add(obj);
		notify();
	}

	/**
	 * remove en element to the queue, or wait ustil there is an available
	 * element.
	 */
	public synchronized Object queueOut() throws InterruptedException {
		return queueOut(0);
	}

	public synchronized Object queueOut(long timeout)
			throws InterruptedException {
		releaseFlag = false;
		Object rv = emptyObject;
		do {
			if (content.isEmpty()) {
				wait(timeout);
				if (releaseFlag) {
					throw new InterruptedException();
				}
			}
			if (content.isEmpty()) {
				// System.out.println("content is empty, i dont know why  " +
				// Thread.currentThread());
			} else {
				rv = content.remove(0);
			}
		} while (rv == emptyObject);

		if (rv == null) {
			// System.out.println("returning null");
		}
		return rv;
	}

	public synchronized boolean isEmpty() {
		return content.isEmpty();
	}

	public synchronized void releaseReaders() {
		releaseFlag = true;
		notifyAll();
	}

	public synchronized void clear() {
		content.clear();
	}

	public synchronized boolean remove(Object obj) {
		int index = content.indexOf(obj);
		if (index > -1) {
			content.set(index, emptyObject);
			return true;
		}
		return false;
	}

	public synchronized boolean moveToFront(Object obj) {
		boolean rv = content.remove(obj);
		if (rv) {
			content.add(0, obj);
		}
		return rv;
	}

	public synchronized boolean contains(Object obj) {
		return content.contains(obj);
	}

	public synchronized Object top() {
		Object rv = content.isEmpty() ? null : content.get(0);
		return rv;
	}

	public synchronized Object tail() {
		int size = content.size();
		Object rv = (size == 0) ? null : content.get(size - 1);
		return rv;
	}
}