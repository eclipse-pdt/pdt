/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.debug.core.sourcelookup;

import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.debug.core.sourcelookup.AbstractSourceLookupDirector;
import org.eclipse.debug.core.sourcelookup.ISourceLookupParticipant;
import org.eclipse.debug.internal.ui.views.launch.SourceNotFoundEditorInput;

/**
 * PHP source lookup director. For PHP source lookup there is one source lookup
 * participant.
 */
public class PHPSourceLookupDirector extends AbstractSourceLookupDirector {

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.debug.internal.core.sourcelookup.ISourceLookupDirector#
	 * initializeParticipants()
	 */
	public void initializeParticipants() {
		addParticipants(new ISourceLookupParticipant[] { new PHPSourceLookupParticipant() });
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.sourcelookup.AbstractSourceLookupDirector#
	 * getSourceElement(java.lang.Object)
	 */
	public Object getSourceElement(Object element) {
		Object sourceElement = super.getSourceElement(element);
		if (sourceElement == null && element instanceof IStackFrame) {
			sourceElement = new SourceNotFoundEditorInput((IStackFrame) element);
		}
		if (sourceElement == null) {
			if (element instanceof IStackFrame) {
				sourceElement = new SourceNotFoundEditorInput((IStackFrame) element);
			}
		}
		return sourceElement;
	}

}
