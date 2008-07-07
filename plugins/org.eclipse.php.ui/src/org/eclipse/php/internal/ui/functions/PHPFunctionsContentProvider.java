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
package org.eclipse.php.internal.ui.functions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IProjectFragment;
import org.eclipse.dltk.core.ISourceReference;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.internal.core.ExternalSourceModule;
import org.eclipse.dltk.internal.ui.StandardModelElementContentProvider;
import org.eclipse.php.internal.ui.Logger;

public class PHPFunctionsContentProvider extends StandardModelElementContentProvider {

	public PHPFunctionsContentProvider() {
		super(true);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.dltk.internal.ui.StandardModelElementContentProvider#getChildren(java.lang.Object)
	 */
	@Override
	public Object[] getChildren(Object element) {
		List<Object> children = new ArrayList<Object>();
		// handle the project fragment used for containing the language model
		if (element instanceof IProjectFragment) {
			try {
				IModelElement[] projectFragmentContent = (IModelElement[]) getProjectFragmentContent((IProjectFragment) element);
				for (IModelElement modelElement : projectFragmentContent) {
					if (modelElement instanceof ExternalSourceModule) {
						children.addAll(Arrays.asList(((ExternalSourceModule) modelElement).getChildren()));
					}
				}
			} catch (ModelException e) {
				Logger.logException(e);
			}
			// handle all method references
		} else if (element instanceof ISourceReference) {
			ISourceReference source = ((ISourceReference) element);
			return super.getChildren(source);
		}

		Object[] array = children.toArray(new Object[children.size()]);
		return array;
	}
}
