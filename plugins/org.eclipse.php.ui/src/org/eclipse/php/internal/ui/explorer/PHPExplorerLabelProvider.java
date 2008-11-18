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

import org.eclipse.core.resources.IResource;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.internal.core.ExternalProjectFragment;
import org.eclipse.dltk.internal.ui.navigator.ScriptExplorerContentProvider;
import org.eclipse.dltk.internal.ui.navigator.ScriptExplorerLabelProvider;
import org.eclipse.dltk.ui.DLTKPluginImages;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.php.internal.ui.util.PHPPluginImages;
import org.eclipse.swt.graphics.Image;

/**
 * 
 * @author apeled, nirc
 *
 */
public class PHPExplorerLabelProvider extends ScriptExplorerLabelProvider {

	public PHPExplorerLabelProvider(ScriptExplorerContentProvider cp, IPreferenceStore store) {
		super(cp, store);
	}

	@Override
	public Image getImage(Object element) {
		IModelElement modelElement = null;
		if (element instanceof ExternalProjectFragment) {
			return PHPPluginImages.get(PHPPluginImages.IMG_OBJS_LIBRARY);
		}
		
		if (element instanceof IResource) {
			modelElement = DLTKCore.create((IResource) element);
		}
		if (element instanceof IScriptFolder) {
			modelElement = (IScriptFolder) element;
		}

		if (modelElement != null) {
			IScriptProject project = modelElement.getScriptProject();
			if (modelElement instanceof ISourceModule && !project.isOnBuildpath(modelElement)) {
				return PHPPluginImages.get(PHPPluginImages.IMG_OBJS_CUNIT_RESOURCE);
			}
			if ((modelElement instanceof IProjectFragment || modelElement instanceof IScriptFolder) && project.isOnBuildpath(modelElement)) {
				return DLTKPluginImages.get(DLTKPluginImages.IMG_OBJS_PACKAGE);
			}
		}

		return super.getImage(element);
	}

}
