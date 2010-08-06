package org.eclipse.php.internal.ui.provider;

import java.util.List;

import org.eclipse.dltk.core.IMethod;
import org.eclipse.dltk.ui.IModelContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;

public class PHPModelContentProvider implements IModelContentProvider {

	public PHPModelContentProvider() {
	}

	public void provideModelChanges(Object parentElement, List children,
			ITreeContentProvider iTreeContentProvider) {
		if (parentElement instanceof IMethod) {
			children.clear();
		}

	}

	public Object getParentElement(Object element,
			ITreeContentProvider iTreeContentProvider) {
		return null;
	}

}
