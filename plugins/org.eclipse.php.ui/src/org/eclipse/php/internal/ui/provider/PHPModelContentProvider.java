package org.eclipse.php.internal.ui.provider;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.dltk.core.*;
import org.eclipse.dltk.ui.IModelContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.php.internal.ui.PHPUiPlugin;

public class PHPModelContentProvider implements IModelContentProvider {

	public PHPModelContentProvider() {
	}

	@Override
	public void provideModelChanges(Object parentElement, List children,
			ITreeContentProvider iTreeContentProvider) {
		if (parentElement instanceof IMethod) {
			Iterator<?> childrenIterator = children.iterator();
			List<Object> innerObjects = new ArrayList<Object>();
			while (childrenIterator.hasNext()) {
				Object next = childrenIterator.next();

				collectInnerMembers(next, innerObjects);
				if (!(next instanceof IType || next instanceof IMethod)) {
					childrenIterator.remove();
				}
			}
			children.addAll(innerObjects);
		}
	}

	private void collectInnerMembers(Object obj, List<Object> innerObjects) {
		if (obj instanceof IField) {
			IField field = (IField) obj;
			try {
				for (IModelElement child : field.getChildren()) {
					if (child.getElementType() == IModelElement.METHOD
							|| child.getElementType() == IModelElement.TYPE) {
						innerObjects.add(child);
					}
				}
			} catch (ModelException e) {
				PHPUiPlugin.log(e);
			}
		}
	}

	@Override
	public Object getParentElement(Object element,
			ITreeContentProvider iTreeContentProvider) {
		return null;
	}

}
