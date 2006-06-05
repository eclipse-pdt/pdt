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
package org.eclipse.php.core.util;

import java.io.Serializable;

/**
 * An interface for any cachable php data. 
 */
public interface ICachable extends Serializable {

    /**
     * @return the object identifing this Cachable.
     */
    Object getIdentifier();

    /**
     * @return true if this Cachable is to be used, false if it has expired.
     */
    boolean isValid();

}
