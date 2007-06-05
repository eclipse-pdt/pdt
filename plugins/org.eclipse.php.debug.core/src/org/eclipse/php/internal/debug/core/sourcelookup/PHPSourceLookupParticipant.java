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

import java.io.File;

import org.eclipse.core.internal.filesystem.local.LocalFile;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.sourcelookup.AbstractSourceLookupParticipant;
import org.eclipse.php.internal.core.resources.ExternalFilesRegistry;
import org.eclipse.php.internal.debug.core.model.PHPStackFrame;
import org.eclipse.ui.ide.FileStoreEditorInput;

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

	public Object[] findSourceElements(Object object) throws CoreException {
		try {
			Object[] sourceElements = super.findSourceElements(object);
			if (sourceElements == EMPTY) {
				// If the lookup returned an empty elements array, check if the source is outside the workspace.
				if (object instanceof PHPStackFrame) {
					PHPStackFrame stackFrame = (PHPStackFrame) object;
					String fileName = stackFrame.getAbsoluteFileName();
					File file = new File(fileName);
					if (!file.exists()) {
						return EMPTY;
					}
					if (ExternalFilesRegistry.getInstance().isEntryExist(fileName)) {
						LocalFile locFile = new LocalFile(new File(fileName));
						final FileStoreEditorInput externalInput = new FileStoreEditorInput(locFile);
						IStorage storage = null;//externalInput.getStorage();
						if (storage != null) {
							return new Object[] { storage };
						} else {
							return EMPTY;
						}
					}

				}
			}
			return sourceElements;
		} catch (CoreException ce) {
			// Check if the lookup failed because the source is outside the workspace.
			return EMPTY;
		}
	}

}
