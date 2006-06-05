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
package org.eclipse.php.debug.core.sourcelookup;

import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.debug.core.sourcelookup.AbstractSourceLookupDirector;
import org.eclipse.debug.core.sourcelookup.ISourceLookupParticipant;

/**
 * PHP source lookup director. For PHP source lookup there is one source
 * lookup participant. 
 */
public class PHPSourceLookupDirector extends AbstractSourceLookupDirector {
    /* (non-Javadoc)
     * @see org.eclipse.debug.internal.core.sourcelookup.ISourceLookupDirector#initializeParticipants()
     */
    public void initializeParticipants() {
        addParticipants(new ISourceLookupParticipant[] { new PHPSourceLookupParticipant() });
    }

    public Object getSourceElement(Object element) {
        Object obj = super.getSourceElement(element);
        if (obj == null) {
            if (element instanceof IStackFrame) {
                obj = new PHPSourceNotFoundInput((IStackFrame) element);
            }
        }
        return obj;
    }

}
