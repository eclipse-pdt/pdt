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
/**
 *
 */
package org.eclipse.php.internal.debug.core.model;

import org.eclipse.php.debug.core.debugger.IDebugHandler;
import org.eclipse.php.internal.debug.core.Logger;
import org.eclipse.php.internal.debug.core.zend.debugger.DebugError;

public abstract class SimpleDebugHandler implements IDebugHandler {

	@Override
	public void ready(String fileName, int lineNumber) {
		Logger.debugMSG("PHPSimpleDebugHandler: ready: " + fileName + " " //$NON-NLS-1$ //$NON-NLS-2$
				+ lineNumber);
	}

	@Override
	public void debugChanged() {
		Logger.debugMSG("PHPSimpleDebugHandler: debugChanged"); //$NON-NLS-1$
	}

	@Override
	public void debuggerErrorOccured(DebugError debugError) {
		Logger.debugMSG("PHPSimpleDebugHandler: debuggerErrorOccured: " //$NON-NLS-1$
				+ debugError);
	}

	@Override
	public void sessionStarted(String remoteFile, String uri, String query, String options) {
		Logger.debugMSG("PHPSimpleDebugHandler: sessionStarted "); //$NON-NLS-1$
		Logger.debugMSG("                       fileName: " + remoteFile); //$NON-NLS-1$
		Logger.debugMSG("                       uri: " + uri); //$NON-NLS-1$
		Logger.debugMSG("                       query: " + query); //$NON-NLS-1$
		Logger.debugMSG("                       options: " + options); //$NON-NLS-1$
	}

	@Override
	public void sessionEnded() {
		Logger.debugMSG("PHPSimpleDebugHandler: sessionEnded"); //$NON-NLS-1$
	}

	@Override
	public void connectionTimedout() {
		Logger.debugMSG("PHPSimpleDebugHandler: connectionTimedout"); //$NON-NLS-1$
	}

	@Override
	public void multipleBindOccured() {
		Logger.debugMSG("PHPSimpleDebugHandler: multipleBindOccured"); //$NON-NLS-1$
	}

	@Override
	public void handleScriptEnded() {
		Logger.debugMSG("PHPSimpleDebugHandler: handleScriptEnded"); //$NON-NLS-1$
	}

	@Override
	public void connectionEstablished() {
		Logger.debugMSG("PHPSimpleDebugHandler: connectionEstablished"); //$NON-NLS-1$
	}

	@Override
	public void connectionClosed() {
		Logger.debugMSG("PHPSimpleDebugHandler: connectionClosed"); //$NON-NLS-1$
	}

	@Override
	public void newOutput(String output) {
		Logger.debugMSG("PHPSimpleDebugHandler: newOutput " + output); //$NON-NLS-1$
	}

	@Override
	public void newHeaderOutput(String output) {
		Logger.debugMSG("PHPSimpleDebugHandler: newHeaderOutput " + output); //$NON-NLS-1$
	}

	@Override
	public void parsingErrorOccured(DebugError debugError) {
		Logger.debugMSG("PHPSimpleDebugHandler: parsingErrorOccured " //$NON-NLS-1$
				+ debugError);
	}

	@Override
	public void wrongDebugServer() {
		Logger.debugMSG("PHPSimpleDebugHandler: wrongDebugServer"); //$NON-NLS-1$
	}
}