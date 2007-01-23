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
package org.eclipse.php.internal.debug.core.launching;

import org.eclipse.debug.core.model.IStreamMonitor;
import org.eclipse.debug.core.model.IStreamsProxy;

/**
 * 
 */
public class PHPStreamsProxy implements IStreamsProxy {

    private DebugConsoleMonitor fConsoleMonitor = new DebugConsoleMonitor();

    /**
     * @see org.eclipse.debug.core.model.IStreamsProxy#getErrorStreamMonitor()
     */
    public IStreamMonitor getErrorStreamMonitor() {
        return null;
    }

    /**
     * @see org.eclipse.debug.core.model.IStreamsProxy#getErrorStreamMonitor()
     */
    public IStreamMonitor getOutputStreamMonitor() {
        return null;
    }

    public IStreamMonitor getConsoleStreamMonitor() {
        return fConsoleMonitor;
    }

    /**
     * @see org.eclipse.debug.core.model.IStreamsProxy#write(java.lang.String)
     */
    public void write(String input) {
    }

}
