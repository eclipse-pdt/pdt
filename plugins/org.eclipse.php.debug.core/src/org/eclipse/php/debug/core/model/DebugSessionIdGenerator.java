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
package org.eclipse.php.debug.core.model;

/**
 * Thread safe debug session id generator.
 * 
 * @author Shalom Gibly
 */
public class DebugSessionIdGenerator {

	private int sessionID = 1000;
	private static DebugSessionIdGenerator instance;

	private DebugSessionIdGenerator() {
		super();
	}

	private static DebugSessionIdGenerator getInstance() {
		if (instance == null) {
			instance = new DebugSessionIdGenerator();
		}
		return instance;
	}

	private int safeGenerateID() {
		int id;
		synchronized (this) {
			id = sessionID++;
		}
		return id;
	}

	/**
	 * Generate and return a unique debug session id.
	 * 
	 * @return A session id
	 */
	public static int generateSessionID() {
		return getInstance().safeGenerateID();
	}

}
