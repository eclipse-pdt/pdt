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
package org.eclipse.php.internal.ui.preferences.includepath;

import org.eclipse.core.resources.IResource;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IBuildpathEntry;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IScriptFolder;
import org.eclipse.dltk.internal.ui.wizards.buildpath.BPListElement;
import org.eclipse.dltk.internal.ui.wizards.buildpath.BPListLabelProvider;
import org.eclipse.dltk.ui.DLTKPluginImages;
import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.dltk.ui.ScriptElementImageDescriptor;
import org.eclipse.dltk.ui.ScriptElementImageProvider;
import org.eclipse.dltk.ui.viewsupport.ImageDescriptorRegistry;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.php.internal.ui.phar.wizard.PharUIUtil;
import org.eclipse.php.internal.ui.util.LabelProviderUtil;
import org.eclipse.php.internal.ui.util.PHPPluginImages;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

public class PHPIPListLabelProvider extends BPListLabelProvider {
	private String fInvalidLabel;
	private ImageDescriptorRegistry fRegistry;

	public PHPIPListLabelProvider() {
		super();
		fInvalidLabel = IncludePathMessages.CPListLabelProvider_invalid;
		fRegistry = DLTKUIPlugin.getImageDescriptorRegistry();
	}

	protected ImageDescriptor getCPListElementBaseImage(BPListElement cpentry) {

		if (cpentry.getEntryKind() == IBuildpathEntry.BPE_LIBRARY) {
			return DLTKPluginImages
					.getDescriptor(DLTKPluginImages.IMG_OBJS_LIBRARY);
		} else if (cpentry.getEntryKind() == IBuildpathEntry.BPE_SOURCE) {
			// handling Folder special case - need to decide if it's in the
			// build path or not.
			ImageDescriptor folderBaseImage = getFolderBaseImage(cpentry
					.getResource());
			if (null != folderBaseImage)
				return folderBaseImage;
		}
		return super.getCPListElementBaseImage(cpentry);

	}

	@Override
	public Image getImage(Object element) {
		if (element instanceof BPListElement) {
			BPListElement cpentry = (BPListElement) element;
			ImageDescriptor imageDescriptor = getCPListElementBaseImage(cpentry);
			if (imageDescriptor != null) {
				if (PharUIUtil.isInvalidPharBuildEntry(cpentry)) {
					imageDescriptor = new ScriptElementImageDescriptor(
							imageDescriptor,
							ScriptElementImageDescriptor.ERROR,
							ScriptElementImageProvider.SMALL_SIZE);
				}
				return fRegistry.get(imageDescriptor);
			}
		}
		return super.getImage(element);
	}

	@Override
	public String getCPListElementText(BPListElement cpentry) {
		String result = LabelProviderUtil.getVariableName(cpentry.getPath(),
				cpentry.getEntryKind());
		if (result == null) {
			result = super.getCPListElementText(cpentry);
		}
		if (PharUIUtil.isInvalidPharBuildEntry(cpentry)) {
			result = result + fInvalidLabel;
		}
		return result;
	}

	private static ImageDescriptor getFolderBaseImage(IResource resource) {
		IModelElement modelElement = DLTKCore.create(resource);

		if (null != modelElement) {
			if (modelElement instanceof IScriptFolder)
				return PHPPluginImages.DESC_OBJS_PHPFOLDER_ROOT;
		} else {
			return PlatformUI.getWorkbench().getSharedImages()
					.getImageDescriptor(ISharedImages.IMG_OBJ_FOLDER);
		}
		return null;
	}
}
