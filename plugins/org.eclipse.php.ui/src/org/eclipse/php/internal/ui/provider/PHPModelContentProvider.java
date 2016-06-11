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

				if (!(next instanceof IType || next instanceof IMethod) && !isFieldWithChildren(next)) {
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
