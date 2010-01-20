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

public class RefactorableFileEditorInput implements IFileEditorInput, IPathEditorInput, IURIEditorInput,
IPersistableElement {
	private boolean isRefactor = false;
	private FileEditorInput innerEidtorInput;
	public RefactorableFileEditorInput(IFile file) {
		this.innerEidtorInput = new FileEditorInput(file);
	}

	/* (non-Javadoc)
	 * Method declared on IPersistableElement.
	 */
	public String getFactoryId() {
		return FileEditorInputFactory.getFactoryId();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IPathEditorInput#getPath()
	 */
	public IPath getPath() {
		return innerEidtorInput.getPath();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IURIEditorInput#getURI()
	 */
	public URI getURI() {
		return innerEidtorInput.getURI();
	}

	/* (non-Javadoc)
	 * Method declared on Object.
	 */
	public int hashCode() {
		return innerEidtorInput.hashCode();
	}

	/* (non-Javadoc)
	 * Method declared on IPersistableElement.
	 */
	public void saveState(IMemento memento) {
		FileEditorInputFactory.saveState(memento, innerEidtorInput);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return innerEidtorInput.toString(); //$NON-NLS-1$ //$NON-NLS-2$
	}

	public void setFile(IFile file) {
		this.innerEidtorInput = new FileEditorInput(file);
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
		return innerEidtorInput.equals(obj);
	}

	public IFile getFile() {
		return innerEidtorInput.getFile();
	}

	public IStorage getStorage() throws CoreException {
		return innerEidtorInput.getStorage();
	}

	public boolean exists() {
		return innerEidtorInput.exists();
	}

	public ImageDescriptor getImageDescriptor() {
		return innerEidtorInput.getImageDescriptor();
	}

	public String getName() {
		return innerEidtorInput.getName();
	}

	public IPersistableElement getPersistable() {
		//if the file has been deleted,return null will make this EidtorInput be removed from NavigationHistory
		if(!innerEidtorInput.getFile().exists())
			return null;
		return innerEidtorInput.getPersistable();
	}

	public String getToolTipText() {
		return innerEidtorInput.getToolTipText();
	}

	public Object getAdapter(Class adapter) {
		return innerEidtorInput.getAdapter(adapter);
	}
}
