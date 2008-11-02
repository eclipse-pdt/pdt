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

import org.eclipse.dltk.core.*;
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

		// handle the project fragment used for containing the language model
		if (element instanceof IProjectFragment) {
			try {
				List<Object> children = new ArrayList<Object>();

				// Create the constant node that will aggregate all of the PHP constants
				ConstantNode constantNode = new ConstantNode();
				children.add(constantNode);

				IModelElement[] projectFragmentContent = (IModelElement[]) getProjectFragmentContent((IProjectFragment) element);

				for (IModelElement modelElement : projectFragmentContent) {
					if (modelElement instanceof ExternalSourceModule) {
						IModelElement[] externalSourceModuleChildren = ((ExternalSourceModule) modelElement).getChildren();
						constantNode.addSourceModuleChildren(externalSourceModuleChildren);
						// filter the constants from the main view
						IModelElement[] elements = filterConstants(externalSourceModuleChildren);
						children.addAll(Arrays.asList(elements));
					}
				}

				Object[] array = children.toArray(new Object[children.size()]);
				return array;

			} catch (ModelException e) {
				Logger.logException(e);
			}
			// handle all method references
		} else if (element instanceof ISourceReference) {
			ISourceReference source = ((ISourceReference) element);
			return super.getChildren(source); 
		} else if (element instanceof ConstantNode) {
			return ((ConstantNode) element).getChildren();
		}

		return NO_CHILDREN;
	}

	/**
	 * Filter the constants from the main view (will appear under the constants node)
	 * @param externalSourceModuleChildren
	 * @return
	 */
	private IModelElement[] filterConstants(IModelElement[] externalSourceModuleChildren) {
		List<IModelElement> filteredList = new ArrayList<IModelElement>();
		for (IModelElement modelElement : externalSourceModuleChildren) {
			if(! (modelElement instanceof IField)  ){
				filteredList.add(modelElement);
			} 
		}
		return filteredList.toArray(new IModelElement[filteredList.size()]);
	}
}
