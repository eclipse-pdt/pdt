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
package org.eclipse.php.debug.core.debugger;

import org.eclipse.php.internal.debug.core.debugger.DebugError;
import org.eclipse.php.internal.debug.core.debugger.IRemoteDebugger;
import org.eclipse.php.internal.debug.core.model.PHPDebugTarget;

public interface IDebugHandler {

    public void sessionStarted(String fileName, String uri, String query, String options);

    public void sessionEnded();

    public void connectionTimedout();

    public void multipleBindOccured();

    public void handleScriptEnded();

    public void connectionEstablished();

    public void connectionClosed();

    public void newOutput(String output);

    public void newHeaderOutput(String output);

    public void parsingErrorOccured(DebugError debugError);

    public void wrongDebugServer();

    public void ready(String fileName, int lineNumber);

    public void debugChanged();

    public void debuggerErrorOccured(DebugError debugError);

    public IRemoteDebugger getRemoteDebugger();
    
    public void setDebugTarget (PHPDebugTarget debugTarget);
    
    public PHPDebugTarget getDebugTarget();
}