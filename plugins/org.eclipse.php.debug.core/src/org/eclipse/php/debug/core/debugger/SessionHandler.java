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


public interface SessionHandler {

    /**
     * Asynchronic start
     * Returns true if successed sending the request, false otherwise.
     */
    public boolean start(StartResponseHandler responseHandler);

    /**
     * Synchronic start
     * Returns true if successed start.
     */
    public boolean start();

    /**
     * closing the debug session
     */
    public void closeDebugSession();

    // ---------------------------------------------------------------------------
    
    // Interface for started response handler.
    public static interface StartResponseHandler {

        public void started(boolean success);

    }
}
