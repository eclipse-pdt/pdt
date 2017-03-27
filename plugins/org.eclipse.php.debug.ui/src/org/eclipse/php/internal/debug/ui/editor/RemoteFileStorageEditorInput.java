/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.ui.editor;

import java.net.URI;

import org.eclipse.core.filesystem.URIUtil;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.dltk.internal.ui.editor.ExternalStorageEditorInput;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.php.internal.debug.core.zend.communication.RemoteFileStorage;
import org.eclipse.php.internal.debug.ui.Logger;
import org.eclipse.php.internal.debug.ui.PHPDebugUIImages;
import org.eclipse.php.internal.ui.editor.input.IPlatformIndependentPathEditorInput;
import org.eclipse.ui.IURIEditorInput;

/**
 * A remote file storage editor input is a storage editor input for a file
 * content that arrived as a stream from a PHP debug server.
 */
public class RemoteFileStorageEditorInput extends ExternalStorageEditorInput
		implements IURIEditorInput, IPlatformIndependentPathEditorInput {

	private URI storageURI;

	/**
	 * Constructs a new RemoteFileStorageEditorInput on a given
	 * {@link RemoteFileStorage}.
	 * 
	 * @param storage
	 *            A {@link RemoteFileStorage}
	 */
	public RemoteFileStorageEditorInput(RemoteFileStorage storage) {
		super(storage);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.dltk.internal.ui.editor.ExternalStorageEditorInput#
	 * getImageDescriptor()
	 */
	public ImageDescriptor getImageDescriptor() {
		return PHPDebugUIImages.getImageDescriptor(PHPDebugUIImages.IMG_OBJ_REMOTE_FILE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IURIEditorInput#getURI()
	 */
	public URI getURI() {
		if (storageURI == null) {
			try {
				RemoteFileStorage storage = (RemoteFileStorage) getStorage();
				storageURI = URIUtil.toURI(storage.getFileName());
			} catch (Exception e) {
				Logger.log(Logger.ERROR, "Could not determine the storage URI (Storage = " + getStorage() + ")"); //$NON-NLS-1$ //$NON-NLS-2$
			}
		}
		return storageURI;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.dltk.internal.ui.editor.ExternalStorageEditorInput#
	 * getToolTipText()
	 */
	public String getToolTipText() {
		return getStorage().getFullPath().toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.ui.editor.input.
	 * IPlatformIndependentPathEditorInput#getPath()
	 */
	public String getPath() {
		return ((RemoteFileStorage) getStorage()).getFileName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.dltk.internal.ui.editor.ExternalStorageEditorInput#hashCode()
	 */
	public int hashCode() {
		return getURI().hashCode();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.dltk.internal.ui.editor.ExternalStorageEditorInput#equals(
	 * java.lang.Object)
	 */
	public boolean equals(Object object) {
		if (object == this) {
			return true;
		}
		try {
			if (object instanceof IURIEditorInput) {
				IURIEditorInput uriEditorInput = (IURIEditorInput) object;
				URI otherURI = uriEditorInput.getURI();
				return otherURI != null && otherURI.equals(getURI());
			}
		} catch (Exception e) {
			DebugPlugin.log(e);
		}
		return false;
	}
}
