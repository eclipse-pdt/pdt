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

import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.DebugElement;
import org.eclipse.php.debug.core.IPHPConstants;

/**
 * Common function of PHP debug model elements
 */
public abstract class PHPDebugElement extends DebugElement {

    /**
     * Constructs a new debug element contained in the given debug target.
     * 
     * @param target
     *            debug target (PHP)
     */
    public PHPDebugElement(PHPDebugTarget target) {
        super(target);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.debug.core.model.IDebugElement#getModelIdentifier()
     */
    public String getModelIdentifier() {
        return IPHPConstants.ID_PHP_DEBUG_CORE;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.debug.core.model.IDebugElement#getLaunch()
     */
    public ILaunch getLaunch() {
        return getDebugTarget().getLaunch();
    }

    /**
     * Fires a debug event
     * 
     * @param event
     *            the event to be fired
     */
    public void fireEvent(DebugEvent event) {
        DebugPlugin.getDefault().fireDebugEventSet(new DebugEvent[] { event });
    }

    /**
     * Fires a <code>CREATE</code> event for this element.
     */
    public void fireCreationEvent() {
        fireEvent(new DebugEvent(this, DebugEvent.CREATE));
    }

    /**
     * Fires a <code>RESUME</code> event for this element with the given
     * detail.
     * 
     * @param detail
     *            event detail code
     */
    public void fireResumeEvent(int detail) {
        fireEvent(new DebugEvent(this, DebugEvent.RESUME, detail));
    }

    /**
     * Fires a <code>SUSPEND</code> event for this element with the given
     * detail.
     * 
     * @param detail
     *            event detail code
     */
    public void fireSuspendEvent(int detail) {
        fireEvent(new DebugEvent(this, DebugEvent.SUSPEND, detail));
    }

    /**
     * Fires a <code>TERMINATE</code> event for this element.
     */
    public void fireTerminateEvent() {
        fireEvent(new DebugEvent(this, DebugEvent.TERMINATE));
    }
}
