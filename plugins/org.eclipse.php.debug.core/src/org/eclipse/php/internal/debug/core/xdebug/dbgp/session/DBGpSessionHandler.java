/*******************************************************************************
 * Copyright (c) 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.core.xdebug.dbgp.session;

import org.eclipse.core.runtime.ListenerList;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.DBGpLogger;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.model.DBGpTarget;

public class DBGpSessionHandler {

	public static final String IDEKEY_PREFIX = "ECLIPSE_DBGP";

	private static DBGpSessionHandler globalSessionHandler;
	private static Object sessionHandlerCreator = new Object();

	private ListenerList listeners = new ListenerList();

	private int sessionCount = 0;
	private Object sessionCountMonitor = new Object();

	/**
	 * Returns a singleton instance of the DBGpSessionHandler.
	 * 
	 * @return A global instance of the DBGpSessionHandler.
	 */
	public static DBGpSessionHandler getInstance() {
		synchronized (sessionHandlerCreator) {
			if (globalSessionHandler == null) {
				globalSessionHandler = new DBGpSessionHandler();
			}
		}
		return globalSessionHandler;
	}
	
	/**
	 * 
	 * @param session
	 * @param ideKey
	 * @param sessionId
	 * @return
	 */
	public boolean isCorrectSession(DBGpSession session, DBGpTarget target) {
		boolean isCorrect = false;
		if (session.getIdeKey().equals(target.getIdeKey())) {
			// ok it is for this ide, so was a session id passed in ?
			if (session.getSessionId() != null) {
				// we are looking for a target with this session Id
				if (session.getSessionId().equals(target.getSessionID())) {
					isCorrect = true;
				}
			} else {

				// no session id, so only match if we have no session id
				if (target.getSessionID() == null) {
					isCorrect = true;
				}
			}
		}
		if (DBGpLogger.debugSession()) {
			DBGpLogger.debug("session test:" + session.getIdeKey() + "=" + target.getIdeKey() + ", " + session.getSessionId() + "=" + target.getSessionID() + " == " + isCorrect);
		}
		return isCorrect;
	}

	public String getIDEKey() {
		// fixed for now
		return IDEKEY_PREFIX;
	}

	public String generateSessionId() {
		long currentTime = System.currentTimeMillis();
		StringBuffer id = new StringBuffer(Long.toString(currentTime));
		synchronized (sessionCountMonitor) {
			sessionCount++;
			id.append(sessionCount);
		}
		return id.toString();
	}

	/**
	 * we synchronize on this to stop the background thread from
	 * being able to fire sessionAdded events until we know that
	 * there isn't a session already waiting. At which point we
	 * add a listener and the next fire will include this listener.
	 * @param l
	 * @param ideKey
	 * @param fileName
	 * @return
	 */
	public synchronized void addSessionListener(IDBGpSessionListener l) {
		listeners.add(l);
	}

	public void removeSessionListener(IDBGpSessionListener l) {
		listeners.remove(l);
	}

	public synchronized boolean fireSessionAdded(DBGpSession session) {
		boolean allocated = false;
		Object[] copiedListeners = listeners.getListeners();
		if (DBGpLogger.debugSession()) {
			DBGpLogger.debug("firing to " + copiedListeners.length + " active debug targets");
		}
		for (int i = 0; i < copiedListeners.length && !allocated; i++) {
			IDBGpSessionListener l = (IDBGpSessionListener) copiedListeners[i];
			allocated = l.SessionCreated(session);
		}
		return allocated;
	}
}
