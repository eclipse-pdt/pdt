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
import org.eclipse.core.runtime.IPath;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.internal.core.ExternalProjectFragment;
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
		
		if (element instanceof IncludePath){
			Object entry = ((IncludePath)element).getEntry();
			
			// An included PHP project
			if (entry instanceof IBuildpathEntry) {
				if (((IBuildpathEntry) entry).getEntryKind() == IBuildpathEntry.BPE_PROJECT) {
					return PHPPluginImages.get(PHPPluginImages.IMG_OBJS_PHP_PROJECT);

				}
				// A library
				if (((IBuildpathEntry) entry).getEntryKind() == IBuildpathEntry.BPE_LIBRARY) {
					return DLTKPluginImages.get(DLTKPluginImages.IMG_OBJS_EXTJAR_WSRC);
				}
			}

			if (entry instanceof ExternalProjectFragment) {
				return PHPPluginImages.get(PHPPluginImages.IMG_OBJS_LIBRARY) ; 
			}

			if (entry instanceof IResource) {
				return (  getImage((IResource) entry)) ;
				
			}
			return null;
		}
		
		if (element instanceof IResource) {
			modelElement = DLTKCore.create((IResource) element);
		}
		if (element instanceof IScriptFolder) {
			return PHPPluginImages.get(PHPPluginImages.IMG_OBJS_PHPFOLDER_ROOT);
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
		
		if (element instanceof IncludePath){
			Object entry = ((IncludePath)element).getEntry();
			
			// An included PHP project
			if (entry instanceof IBuildpathEntry) {
				if (((IBuildpathEntry) entry).getEntryKind() == IBuildpathEntry.BPE_PROJECT) {
					return ((IBuildpathEntry) entry).getPath().lastSegment();
				}
				String path = ((IBuildpathEntry) entry).getPath().toString();
				return path.substring(path.lastIndexOf(IPath.DEVICE_SEPARATOR) + 1);
			}
			if (entry instanceof ExternalProjectFragment) {
				 return ((ExternalProjectFragment) entry).toStringWithAncestors();
			}

			if (entry instanceof IResource) {
				return (((IResource) entry). getFullPath().toString()).substring(1);
			}
			
			return null;		
		}
//		if (element instanceof IFolder) {
//			String segments2 = ((IFolder) element).getLocation().toString();
//			
//			String[] segments = ((IFolder) element).getLocation().segments();
//			String name = null ;
//			for (String seg : segments) {
//				name += seg + "\\";
//			}
//			//name = name.
//			return name;
//		}
		return super.getText(element);
	}
	

}
