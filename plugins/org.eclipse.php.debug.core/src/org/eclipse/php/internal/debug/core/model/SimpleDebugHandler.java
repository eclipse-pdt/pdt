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
/**
 *
 */
package org.eclipse.php.internal.debug.core.model;

import org.eclipse.php.debug.core.debugger.IDebugHandler;
import org.eclipse.php.internal.debug.core.Logger;
import org.eclipse.php.internal.debug.core.zend.debugger.DebugError;

public abstract class SimpleDebugHandler implements IDebugHandler {

	public void ready(String fileName, int lineNumber) {
		Logger.debugMSG("[" + this + "] PHPSimpleDebugHandler: ready: " + fileName + " " + lineNumber);
	}

	public void debugChanged() {
		Logger.debugMSG("[" + this + "] PHPSimpleDebugHandler: debugChanged");
	}

	public void debuggerErrorOccured(DebugError debugError) {
		Logger.debugMSG("[" + this + "] PHPSimpleDebugHandler: debuggerErrorOccured: " + debugError);
	}

	public void sessionStarted(String remoteFile, String uri, String query, String options) {
		Logger.debugMSG("[" + this + "] PHPSimpleDebugHandler: sessionStarted ");
		Logger.debugMSG("[" + this + "] PHPSimpleDebugHandler:        fileName: " + remoteFile);
		Logger.debugMSG("[" + this + "] PHPSimpleDebugHandler:        uri: " + uri);
		Logger.debugMSG("[" + this + "] PHPSimpleDebugHandler:        query: " + query);
		Logger.debugMSG("[" + this + "]  PHPSimpleDebugHandler:       options: " + options);
	}

	public void sessionEnded() {
		Logger.debugMSG("[" + this + "] PHPSimpleDebugHandler: sessionEnded");
	}

	public void connectionTimedout() {
		Logger.debugMSG("[" + this + "] PHPSimpleDebugHandler: connectionTimedout");
	}

	public void multipleBindOccured() {
		Logger.debugMSG("[" + this + "] PHPSimpleDebugHandler: multipleBindOccured");
	}

	public void handleScriptEnded() {
		Logger.debugMSG("[" + this + "] PHPSimpleDebugHandler: handleScriptEnded");
	}

	public void connectionEstablished() {
		Logger.debugMSG("[" + this + "] PHPSimpleDebugHandler: connectionEstablished");
	}

	public void connectionClosed() {
		Logger.debugMSG("[" + this + "] PHPSimpleDebugHandler: connectionClosed");
	}

	public void newOutput(String output) {
		Logger.debugMSG("[" + this + "] PHPSimpleDebugHandler: newOutput " + output);
	}

	public void newHeaderOutput(String output) {
		Logger.debugMSG("[" + this + "] PHPSimpleDebugHandler: newHeaderOutput " + output);
	}

	public void parsingErrorOccured(DebugError debugError) {
		Logger.debugMSG("[" + this + "] PHPSimpleDebugHandler: parsingErrorOccured " + debugError);
	}

	public void wrongDebugServer() {
		Logger.debugMSG("[" + this + "] PHPSimpleDebugHandler: wrongDebugServer");
	}

	public String toString() {
		String className = getClass().getName();
		className = className.substring(className.lastIndexOf('.') + 1);
		return className + "@" + Integer.toHexString(hashCode());
	}
}