/*******************************************************************************
 * Copyright (c) 2015 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.util;

/**
 * <p>
 * Final instance of this class can be passed to runnable started in a separate
 * thread (i.e. <code>Display.syncExec()</code>) and act as a container that
 * give a possibility to set/get wrapped object value in different threads.
 * </p>
 * 
 * @author Bartlomiej Laczkowski
 */
public final class SyncObject<T> {

	T object = null;

	/**
	 * Sets the wrapped object.
	 * 
	 * @param object
	 */
	public final void set(T object) {
		this.object = object;
	}

	/**
	 * Gets wrapped object.
	 * 
	 * @return wrapped object instance
	 */
	public final T get() {
		return this.object;
	}

}
