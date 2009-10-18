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

	/**
	 * Generate and return a unique debug session id.
	 * 
	 * @return A session id
	 */
	public static int generateSessionID() {
		return getInstance().safeGenerateID();
	}

	/**
	 * Returns the last generated session id.
	 * 
	 * @return The last generated id.
	 * @see #generateSessionID()
	 */
	public static int getLastGenerated() {
		return getInstance().safeGetLastGenerated();
	}

	private synchronized int safeGenerateID() {
		int id = sessionID++;
		return id;
	}

	private synchronized int safeGetLastGenerated() {
		return sessionID - 1;
	}
}
