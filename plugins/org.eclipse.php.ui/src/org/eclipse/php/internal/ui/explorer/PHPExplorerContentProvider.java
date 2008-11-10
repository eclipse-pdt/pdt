/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.explorer;

import java.util.ArrayList;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.internal.core.ExternalProjectFragment;
import org.eclipse.dltk.internal.ui.navigator.ScriptExplorerContentProvider;
import org.eclipse.dltk.internal.ui.scriptview.BuildPathContainer;
import org.eclipse.php.internal.ui.Logger;

/**
 * 
 * 
 * @author apeled, nirc
 *
 */
public class PHPExplorerContentProvider extends ScriptExplorerContentProvider /*implements IResourceChangeListener*/{

	public PHPExplorerContentProvider(boolean provideMembers) {
		super(provideMembers);
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		try {
		// Handles SourceModule and downwards as well as ExternalProjectFragments (i.e language model)
		if (parentElement instanceof ISourceModule || !(parentElement instanceof IOpenable) || parentElement instanceof ExternalProjectFragment) {
			if (parentElement instanceof IFolder) {
				return ((IFolder) parentElement).members();
		}
			return super.getChildren(parentElement);
		}

			if (parentElement instanceof IOpenable) {
				IResource resource = ((IOpenable) parentElement).getResource();
				if (resource instanceof IContainer) {

					IResource[] resChildren = ((IContainer) resource).members();
					ArrayList<Object> returnChlidren = new ArrayList<Object>();

					for (IResource resource2 : resChildren) {
						IModelElement modelElement = DLTKCore.create(resource2);
						if (modelElement != null) {
							returnChlidren.add(modelElement);
						} else {
							returnChlidren.add(resource2);
						}
					}
					// Adding External libraries to the treeview :
					if (parentElement instanceof IScriptProject) {
						Object[] projectChildren = getProjectFragments((IScriptProject) parentElement);
						for (Object modelElement : projectChildren) {
							//adding only "external" fragments (so we won't double-add source-folders we get on the resource-wise visiting
							if (modelElement instanceof BuildPathContainer) {
								returnChlidren.add(modelElement);
							}
						}
					}
					return returnChlidren.toArray();
				}
			}
		} catch (CoreException e) {
			Logger.logException(e);
		}
		return NO_CHILDREN;
	}

}