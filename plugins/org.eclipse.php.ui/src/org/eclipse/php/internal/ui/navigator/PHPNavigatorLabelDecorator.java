/*******************************************************************************
 * Copyright (c) 2016 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.navigator;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.ui.DLTKPluginImages;
import org.eclipse.jface.viewers.*;
import org.eclipse.php.core.PHPToolkitUtil;
import org.eclipse.php.core.libfolders.LibraryFolderManager;
import org.eclipse.php.internal.ui.Logger;
import org.eclipse.php.internal.ui.util.PHPPluginImages;

public class PHPNavigatorLabelDecorator implements ILightweightLabelDecorator {

	private LibraryFolderManager lfm = LibraryFolderManager.getInstance();

	@Override
	public void decorate(Object element, IDecoration decoration) {
		if (element instanceof IFolder) {
			IDecorationContext context = decoration.getDecorationContext();
			if (context instanceof DecorationContext) {
				((DecorationContext) context).putProperty(IDecoration.ENABLE_REPLACE, Boolean.TRUE);
			}
			IFolder folder = (IFolder) element;
			IScriptProject project = DLTKCore.create(folder.getProject());
			try {
				if (PHPToolkitUtil.isPHPProject(folder.getProject()) && project.isOnBuildpath(folder)) {
					if (lfm.isInLibraryFolder(folder)) {
						decoration.addOverlay(PHPPluginImages.DESC_OBJS_PHP_LIBFOLDER, IDecoration.REPLACE);
					} else {
						decoration.addOverlay(DLTKPluginImages.DESC_OBJS_PACKFRAG_ROOT, IDecoration.REPLACE);
					}
				}
			} catch (CoreException e) {
				Logger.logException(e);
			}
		}
	}

	@Override
	public void addListener(ILabelProviderListener listener) {
	}

	@Override
	public void dispose() {
	}

	@Override
	public boolean isLabelProperty(Object element, String property) {
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {
	}

}
