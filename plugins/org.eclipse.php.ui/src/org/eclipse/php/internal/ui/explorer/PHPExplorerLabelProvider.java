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
package org.eclipse.php.internal.ui.explorer;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.core.environment.EnvironmentPathUtils;
import org.eclipse.dltk.internal.core.ExternalProjectFragment;
import org.eclipse.dltk.internal.ui.navigator.ScriptExplorerContentProvider;
import org.eclipse.dltk.internal.ui.navigator.ScriptExplorerLabelProvider;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.php.core.compiler.PHPFlags;
import org.eclipse.php.internal.core.includepath.IncludePath;
import org.eclipse.php.internal.core.language.LanguageModelInitializer;
import org.eclipse.php.internal.ui.util.LabelProviderUtil;
import org.eclipse.php.internal.ui.util.PHPPluginImages;
import org.eclipse.swt.graphics.Image;

/**
 * 
 * @author apeled, nirc
 * 
 */
public class PHPExplorerLabelProvider extends ScriptExplorerLabelProvider {

	public PHPExplorerLabelProvider(ScriptExplorerContentProvider cp,
			IPreferenceStore store) {
		super(cp, store);
		super.setIsFlatLayout(false);
	}

	public void setIsFlatLayout(boolean state) {
		super.setIsFlatLayout(false);
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
				int entryKind = ((IBuildpathEntry) entry).getEntryKind();
				if (entryKind == IBuildpathEntry.BPE_PROJECT) {
					return PHPPluginImages
							.get(PHPPluginImages.IMG_OBJS_PHP_PROJECT);

				}
				// A library
				if (entryKind == IBuildpathEntry.BPE_LIBRARY
						|| entryKind == IBuildpathEntry.BPE_CONTAINER) {
					return PHPPluginImages
							.get(PHPPluginImages.IMG_OBJS_LIBRARY);
				}
			}

			if (entry instanceof ExternalProjectFragment) {
				return PHPPluginImages.get(PHPPluginImages.IMG_OBJS_LIBRARY);
			}

			// Folder in the include path, should have same image as in the PHP
			// Explorer .
			if (entry instanceof IFolder) {
				IModelElement createdScriptFolder = DLTKCore
						.create((IFolder) entry);
				if (null == createdScriptFolder)
					return getImage(entry);
				return getImage(createdScriptFolder);
			}

			if (entry instanceof IResource) {
				return (getImage((IResource) entry));

			}
			return null;
		}

		if (element instanceof IResource) {
			modelElement = DLTKCore.create((IResource) element);
		} else if (element instanceof IModelElement) {
			modelElement = (IModelElement) element;
		}

		if (modelElement != null) {
			IScriptProject project = modelElement.getScriptProject();
			if (!project.isOnBuildpath(modelElement)) {// not in build path,
				// hence: hollow,
				// non-pakg icons
				if (modelElement.getElementType() == IModelElement.SOURCE_MODULE)
					return PHPPluginImages
							.get(PHPPluginImages.IMG_OBJS_CUNIT_RESOURCE);
				if (modelElement.getElementType() == IModelElement.PROJECT_FRAGMENT
						|| modelElement.getElementType() == IModelElement.SCRIPT_FOLDER)
					return PHPPluginImages
							.get(PHPPluginImages.IMG_OBJS_PHP_FOLDER);
			} else {// in build path ...
				if (modelElement.getElementType() == IModelElement.SCRIPT_FOLDER
						|| element instanceof IFolder)
					return PHPPluginImages
							.get(PHPPluginImages.IMG_OBJS_PHPFOLDER_ROOT);
			}
		}
		try {
			if (element instanceof IType
					&& PHPFlags.isTrait(((IType) element).getFlags())) {
				return PHPPluginImages.get(PHPPluginImages.IMG_OBJS_TRAIT);
			}
		} catch (ModelException e) {
		}

		if (element != null) {
			for (ILabelProvider provider : TreeContentProviderRegistry
					.getInstance().getLabelProviders()) {
				Image image = provider.getImage(element);

				if (image != null) {
					return image;
				}
			}
		}

		return super.getImage(element);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.dltk.internal.ui.navigator.ScriptExplorerLabelProvider#getText
	 * (java.lang.Object)
	 * 
	 * Override the default text - do not display a full path for a folder
	 */
	@Override
	public String getText(Object element) {

		if (element instanceof ExternalProjectFragment) {
			ExternalProjectFragment fragment = (ExternalProjectFragment) element;
			String name = LanguageModelInitializer
					.getPathName(EnvironmentPathUtils.getLocalPath(fragment
							.getPath()));
			if (name != null) {
				return name;
			}
			return fragment.toStringWithAncestors();
		}
		// https://bugs.eclipse.org/bugs/show_bug.cgi?id=295256
		if (element instanceof IProjectFragment) {
			IProjectFragment fragment = (IProjectFragment) element;
			return fragment.getElementName();
		}
		// end
		if (element instanceof IncludePath) {
			Object entry = ((IncludePath) element).getEntry();

			// An included PHP project
			if (entry instanceof IBuildpathEntry) {
				IBuildpathEntry iBuildpathEntry = (IBuildpathEntry) entry;
				if (iBuildpathEntry.getEntryKind() == IBuildpathEntry.BPE_PROJECT) {
					return iBuildpathEntry.getPath().lastSegment();
				}
				if (iBuildpathEntry.getEntryKind() == IBuildpathEntry.BPE_CONTAINER) {
					return getEntryDescription(element, iBuildpathEntry);
				} else {
					String result = LabelProviderUtil.getVariableName(
							iBuildpathEntry.getPath(),
							iBuildpathEntry.getEntryKind());
					if (result == null) {
						IPath localPath = EnvironmentPathUtils
								.getLocalPath(iBuildpathEntry.getPath());
						return localPath.toOSString();
					}
					return result;
				}
			}
			if (entry instanceof ExternalProjectFragment) {
				return ((ExternalProjectFragment) entry)
						.toStringWithAncestors();
			}

			if (entry instanceof IResource) {
				return (((IResource) entry).getFullPath().toString())
						.substring(1);
			}

			return null;
		}

		if (element != null) {
			for (ILabelProvider provider : TreeContentProviderRegistry
					.getInstance().getLabelProviders()) {
				String label = provider.getText(element);

				if (label != null) {
					return label;
				}
			}
		}

		return super.getText(element);
	}

	/**
	 * @param element
	 * @param iBuildpathEntry
	 * @return the name of the container description
	 */
	private String getEntryDescription(Object element,
			IBuildpathEntry iBuildpathEntry) {
		IProject project = ((IncludePath) element).getProject();
		IScriptProject scriptProject = DLTKCore.create(project);
		IBuildpathContainer buildpathContainer = null;
		try {
			buildpathContainer = DLTKCore.getBuildpathContainer(
					iBuildpathEntry.getPath(), scriptProject);
		} catch (ModelException e) {
			// no matching container - return the path
		}
		if (buildpathContainer != null) {
			return buildpathContainer.getDescription();
		}
		return iBuildpathEntry.getPath().toOSString();
	}

}
