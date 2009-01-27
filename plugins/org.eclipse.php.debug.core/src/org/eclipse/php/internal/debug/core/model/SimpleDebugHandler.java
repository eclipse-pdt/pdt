/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
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
		Logger.debugMSG("PHPSimpleDebugHandler: ready: " + fileName + " " + lineNumber);
	}

	public void debugChanged() {
		Logger.debugMSG("PHPSimpleDebugHandler: debugChanged");
	}

	public void debuggerErrorOccured(DebugError debugError) {
		Logger.debugMSG("PHPSimpleDebugHandler: debuggerErrorOccured: " + debugError);
	}

	public void sessionStarted(String remoteFile, String uri, String query, String options) {
		Logger.debugMSG("PHPSimpleDebugHandler: sessionStarted ");
		Logger.debugMSG("                       fileName: " + remoteFile);
		Logger.debugMSG("                       uri: " + uri);
		Logger.debugMSG("                       query: " + query);
		Logger.debugMSG("                       options: " + options);
	}

	public void sessionEnded() {
		Logger.debugMSG("PHPSimpleDebugHandler: sessionEnded");
	}

	public void connectionTimedout() {
		Logger.debugMSG("PHPSimpleDebugHandler: connectionTimedout");
	}

	public void multipleBindOccured() {
		Logger.debugMSG("PHPSimpleDebugHandler: multipleBindOccured");
	}

	public void handleScriptEnded() {
		Logger.debugMSG("PHPSimpleDebugHandler: handleScriptEnded");
	}

	public void connectionEstablished() {
		Logger.debugMSG("PHPSimpleDebugHandler: connectionEstablished");
	}

	public void connectionClosed() {
		Logger.debugMSG("PHPSimpleDebugHandler: connectionClosed");
	}

	public void newOutput(String output) {
		Logger.debugMSG("PHPSimpleDebugHandler: newOutput " + output);
	}

	public void newHeaderOutput(String output) {
		Logger.debugMSG("PHPSimpleDebugHandler: newHeaderOutput " + output);
	}

	public void parsingErrorOccured(DebugError debugError) {
		Logger.debugMSG("PHPSimpleDebugHandler: parsingErrorOccured " + debugError);
	}

	public void wrongDebugServer() {
		Logger.debugMSG("PHPSimpleDebugHandler: wrongDebugServer");
	}
}