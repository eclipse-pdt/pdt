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

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.core.environment.EnvironmentPathUtils;
import org.eclipse.dltk.internal.core.ExternalProjectFragment;
import org.eclipse.dltk.internal.core.ProjectFragment;
import org.eclipse.dltk.internal.ui.navigator.ScriptExplorerContentProvider;
import org.eclipse.dltk.internal.ui.navigator.ScriptExplorerLabelProvider;
import org.eclipse.dltk.ui.DLTKPluginImages;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.php.internal.core.includepath.IncludePath;
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

		if (element instanceof IncludePath) {
			Object entry = ((IncludePath) element).getEntry();

			// An included PHP project
			if (entry instanceof IBuildpathEntry) {
				if (((IBuildpathEntry) entry).getEntryKind() == IBuildpathEntry.BPE_PROJECT) {
					return PHPPluginImages.get(PHPPluginImages.IMG_OBJS_PHP_PROJECT);

				}
				// A library
				if (((IBuildpathEntry) entry).getEntryKind() == IBuildpathEntry.BPE_LIBRARY) {
					return PHPPluginImages.get(PHPPluginImages.IMG_OBJS_LIBRARY);
				}
			}

			if (entry instanceof ExternalProjectFragment) {
				return PHPPluginImages.get(PHPPluginImages.IMG_OBJS_LIBRARY);
			}
			
			// Folder in the include path, should have "source-folder" image.
			if (entry instanceof IFolder) {
				return PHPPluginImages.get(PHPPluginImages.IMG_OBJS_PHPFOLDER_ROOT);
			}
			
			if (entry instanceof IResource) {
				return (getImage((IResource) entry));

			}
			return null;
		}

		if (element instanceof IResource) {
			modelElement = DLTKCore.create((IResource) element);
		}

		if (element instanceof IModelElement)
			modelElement = (IModelElement) element;

		if (modelElement != null) {
			IScriptProject project = modelElement.getScriptProject();
			if (!project.isOnBuildpath(modelElement)) {//not in build path, hence: hollow, non-pakg icons
				if (modelElement instanceof ISourceModule)
					return PHPPluginImages.get(PHPPluginImages.IMG_OBJS_CUNIT_RESOURCE);
				if (modelElement instanceof IProjectFragment || modelElement instanceof IScriptFolder)
					return PHPPluginImages.get(PHPPluginImages.IMG_OBJS_PHP_FOLDER);
			} else {//in build path ...
				if (element instanceof IScriptFolder)
					return PHPPluginImages.get(PHPPluginImages.IMG_OBJS_PHPFOLDER_ROOT);
			}
		}

		return super.getImage(element);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.dltk.internal.ui.navigator.ScriptExplorerLabelProvider#getText(java.lang.Object)
	 * 
	 * Override the default text - do not display a full path for a folder
	 */
	@Override
	public String getText(Object element) {
		if (element instanceof ExternalProjectFragment) {
			return ((ExternalProjectFragment) element).toStringWithAncestors();
		}

		if (element instanceof IncludePath) {
			Object entry = ((IncludePath) element).getEntry();

			// An included PHP project
			if (entry instanceof IBuildpathEntry) {
				if (((IBuildpathEntry) entry).getEntryKind() == IBuildpathEntry.BPE_PROJECT) {
					return ((IBuildpathEntry) entry).getPath().lastSegment();
				} else {
					IPath localPath = EnvironmentPathUtils.getLocalPath(((IBuildpathEntry) entry).getPath());
					return localPath.toOSString();
				}
			}
			if (entry instanceof ExternalProjectFragment) {
				return ((ExternalProjectFragment) entry).toStringWithAncestors();
			}

			if (entry instanceof IResource) {
				return (((IResource) entry).getFullPath().toString()).substring(1);
			}

			return null;
		}
		return super.getText(element);
	}

}
