/*******************************************************************************
 * Copyright (c) 2007 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.editor.input;

import java.io.File;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;
import org.eclipse.ui.editors.text.ILocationProvider;

/**
 * This Editor Input class should be used mainly for Untitled PHP Documents
 * @see org.eclipse.ui.internal.editors.text.NonExistingFileEditorInput
 */
public class NonExistingPHPFileEditorInput implements IEditorInput, ILocationProvider {

	private static int fgNonExisting = 0;

	private IFileStore fFileStore;
	private String fName;

	public NonExistingPHPFileEditorInput(IFileStore fileStore, String namePrefix) {
		Assert.isNotNull(fileStore);
		Assert.isTrue(EFS.SCHEME_FILE.equals(fileStore.getFileSystem().getScheme()));
		fFileStore = fileStore;
		++fgNonExisting;
		fName = namePrefix + fgNonExisting + ".php"; //$NON-NLS-1$
	}

	public NonExistingPHPFileEditorInput(IPath fullPath) {
		Assert.isNotNull(fullPath);
		IFileStore fileStore = null;

		String pathName = fullPath.toString();
		if (pathName.endsWith(".php")) { //$NON-NLS-1$
			fileStore = EFS.getLocalFileSystem().getStore(fullPath.removeLastSegments(1));
			fName = fullPath.lastSegment(); //$NON-NLS-1$
		} else {
			fileStore = EFS.getLocalFileSystem().getStore(fullPath);
			++fgNonExisting;
			fName = "PHPDocument" + fgNonExisting + ".php"; //$NON-NLS-1$ //$NON-NLS-2$
		}
		Assert.isTrue(EFS.SCHEME_FILE.equals(fileStore.getFileSystem().getScheme()));
		fFileStore = fileStore;
	}

	/*
	 * @see org.eclipse.ui.IEditorInput#exists()
	 */
	public boolean exists() {
		return false;
	}

	/*
	 * @see org.eclipse.ui.IEditorInput#getImageDescriptor()
	 */
	public ImageDescriptor getImageDescriptor() {
		return null;
	}

	/*
	 * @see org.eclipse.ui.IEditorInput#getName()
	 */
	public String getName() {
		return fName;
	}

	/*
	 * @see org.eclipse.ui.IEditorInput#getPersistable()
	 */
	public IPersistableElement getPersistable() {
		return null;
	}

	/*
	 * @see org.eclipse.ui.IEditorInput#getToolTipText()
	 */
	public String getToolTipText() {
		return fName;
	}

	/*
	 * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
	 */
	public Object getAdapter(Class adapter) {
		if (ILocationProvider.class == adapter) {
			return this;
		}
//		if (IResource.class == adapter) {			
//			return ExternalFilesRegistry.getInstance().getFileEntry(getPath().toOSString());
//		}
		return Platform.getAdapterManager().getAdapter(this, adapter);
	}

	public IPath getPath() {
		return getPath(this);
	}

	/*
	 * @see org.eclipse.ui.editors.text.ILocationProvider#getPath(java.lang.Object)
	 */
	public IPath getPath(Object element) {
		if (element instanceof NonExistingPHPFileEditorInput) {
			NonExistingPHPFileEditorInput input = (NonExistingPHPFileEditorInput) element;
			String path = input.fFileStore.toURI().getPath();
			if (path.endsWith(".php")) { //$NON-NLS-1$
				return new Path(path);
			} else {
				return new Path(path + File.separatorChar + fName);
			}
		}
		return null;
	}

	/*
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object o) {
		if (o == this)
			return true;

		if (o instanceof NonExistingPHPFileEditorInput) {
			NonExistingPHPFileEditorInput input = (NonExistingPHPFileEditorInput) o;
			return fFileStore.equals(input.fFileStore) && fName.equals(input.fName);
		}

		return false;
	}

	/*
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return fFileStore.hashCode();
	}
}
