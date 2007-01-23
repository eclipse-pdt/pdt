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
package org.eclipse.php.internal.debug.core.model;

import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.DebugElement;
import org.eclipse.php.internal.debug.core.IPHPConstants;

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
    
    public String toString() {
    	String className = getClass().getName();
    	className = className.substring(className.lastIndexOf('.') + 1);
    	return className + "@" + Integer.toHexString(hashCode());
    }
}
