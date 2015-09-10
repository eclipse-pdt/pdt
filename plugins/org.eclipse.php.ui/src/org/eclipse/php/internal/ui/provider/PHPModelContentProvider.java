package org.eclipse.php.internal.ui.provider;

import java.util.List;

import org.eclipse.dltk.core.*;
import org.eclipse.dltk.ui.IModelContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.php.internal.ui.PHPUiPlugin;

public class PHPModelContentProvider implements IModelContentProvider {

	public PHPModelContentProvider() {
	}

	@Override
	public void provideModelChanges(Object parentElement, List children, ITreeContentProvider iTreeContentProvider) {
		if (parentElement instanceof IMethod) {
			for (Object next : children.toArray()) {

				if (!(next instanceof IType || next instanceof IMethod)
						&& !isFieldWithChildren(next)) {
					children.remove(next);
				}
			}
		}
	}

	private boolean isFieldWithChildren(Object obj) {
		if (obj instanceof IField) {
			IField field = (IField) obj;
			try {
				for (IModelElement child : field.getChildren()) {
					if (child.getElementType() == IModelElement.METHOD
							|| child.getElementType() == IModelElement.TYPE) {
						return true;
					}
				}
			} catch (ModelException e) {
				PHPUiPlugin.log(e);
			}
		}
		return false;
	}

	@Override
	public Object getParentElement(Object element, ITreeContentProvider iTreeContentProvider) {
		return null;
	}

}
