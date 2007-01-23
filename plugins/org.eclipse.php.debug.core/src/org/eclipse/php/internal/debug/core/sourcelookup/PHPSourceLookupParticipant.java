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
package org.eclipse.php.internal.debug.core.sourcelookup;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.sourcelookup.AbstractSourceLookupParticipant;
import org.eclipse.php.internal.debug.core.model.PHPStackFrame;

/**
 * The PHP source lookup participant knows how to translate a 
 * PHP stack frame into a source file name 
 */
public class PHPSourceLookupParticipant extends AbstractSourceLookupParticipant {
    /* (non-Javadoc)
     * @see org.eclipse.debug.internal.core.sourcelookup.ISourceLookupParticipant#getSourceName(java.lang.Object)
     */
    public String getSourceName(Object object) throws CoreException {
        if (object instanceof PHPStackFrame) {
            return ((PHPStackFrame) object).getSourceName();
        }
        return null;
    }
}
