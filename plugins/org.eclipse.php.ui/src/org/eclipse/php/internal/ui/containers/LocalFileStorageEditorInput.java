/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.containers;

import org.eclipse.php.internal.core.containers.LocalFileStorage;
import org.eclipse.ui.IEditorInput;

public class LocalFileStorageEditorInput extends StorageEditorInput {

	/**
	 * Constructs an editor input for the given storage
	 */
	public LocalFileStorageEditorInput(LocalFileStorage storage) {
		super(storage);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IEditorInput#exists()
	 */
	public boolean exists() {
		return ((LocalFileStorage) getStorage()).getFile().exists();
	}

	/**
	 * @see IEditorInput#getToolTipText()
	 */
	public String getToolTipText() {
		return getStorage().getFullPath().toOSString();//use OS String when local storage
	}

}
