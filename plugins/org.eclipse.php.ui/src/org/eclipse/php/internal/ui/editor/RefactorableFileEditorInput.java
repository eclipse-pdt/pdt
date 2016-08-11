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
package org.eclipse.php.internal.ui.editor;

import java.net.URI;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.*;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.FileEditorInputFactory;

public class RefactorableFileEditorInput
		implements IFileEditorInput, IPathEditorInput, IURIEditorInput, IPersistableElement {
	private boolean isRefactor = false;
	private FileEditorInput innerEditorInput;

	public RefactorableFileEditorInput(IFile file) {
		this.innerEditorInput = new FileEditorInput(file);
	}

	/*
	 * (non-Javadoc) Method declared on IPersistableElement.
	 */
	public String getFactoryId() {
		return FileEditorInputFactory.getFactoryId();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IPathEditorInput#getPath()
	 */
	public IPath getPath() {
		return innerEditorInput.getPath();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IURIEditorInput#getURI()
	 */
	public URI getURI() {
		return innerEditorInput.getURI();
	}

	/*
	 * (non-Javadoc) Method declared on Object.
	 */
	public int hashCode() {
		return innerEditorInput.hashCode();
	}

	/*
	 * (non-Javadoc) Method declared on IPersistableElement.
	 */
	public void saveState(IMemento memento) {
		FileEditorInputFactory.saveState(memento, innerEditorInput);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return innerEditorInput.toString(); // $NON-NLS-1$ //$NON-NLS-2$
	}

	public void setFile(IFile file) {
		this.innerEditorInput = new FileEditorInput(file);
	}

	public boolean isRefactor() {
		return isRefactor;
	}

	public void setRefactor(boolean isRefactor) {
		this.isRefactor = isRefactor;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof IFileEditorInput)) {
			return false;
		}
		return innerEditorInput.equals(obj);
	}

	public IFile getFile() {
		return innerEditorInput.getFile();
	}

	public IStorage getStorage() throws CoreException {
		return innerEditorInput.getStorage();
	}

	public boolean exists() {
		return innerEditorInput.exists();
	}

	public ImageDescriptor getImageDescriptor() {
		return innerEditorInput.getImageDescriptor();
	}

	public String getName() {
		return innerEditorInput.getName();
	}

	public IPersistableElement getPersistable() {
		// if the file has been deleted,return null will make this EidtorInput
		// be removed from NavigationHistory
		if (!innerEditorInput.getFile().exists())
			return null;
		return innerEditorInput.getPersistable();
	}

	public String getToolTipText() {
		return innerEditorInput.getToolTipText();
	}

	public Object getAdapter(Class adapter) {
		return innerEditorInput.getAdapter(adapter);
	}
}
