package org.eclipse.php.internal.ui.editor;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IPersistableElement;

public class RefactorableFileEditorInput implements IFileEditorInput{
	private boolean isRefactor = false;
	private IFileEditorInput innerEidtorInput;
	public RefactorableFileEditorInput(IFileEditorInput innerEidtorInput) {
		this.innerEidtorInput = innerEidtorInput;
	}

	public void setInnerEidtorInput(IFileEditorInput innerEidtorInput) {
		this.innerEidtorInput = innerEidtorInput;
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
