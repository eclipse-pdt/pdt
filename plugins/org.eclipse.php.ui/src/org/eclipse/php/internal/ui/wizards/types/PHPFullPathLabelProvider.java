/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.wizards.types;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.internal.core.ScriptFolder;
import org.eclipse.dltk.internal.core.search.DLTKSearchTypeNameMatch;
import org.eclipse.dltk.ui.DLTKPluginImages;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.php.core.compiler.PHPFlags;
import org.eclipse.php.internal.ui.Logger;
import org.eclipse.php.internal.ui.util.PHPPluginImages;
import org.eclipse.swt.graphics.Image;

public class PHPFullPathLabelProvider extends LabelProvider implements ILabelDecorator {

	private Image fInterfaceImage;
	private Image fClassImage;
	private Image fTraitImage;
	private Image fMethodImage;

	public PHPFullPathLabelProvider() {
		fInterfaceImage = PHPPluginImages.get(PHPPluginImages.IMG_OBJS_INTERFACE);
		fClassImage = DLTKPluginImages.get(DLTKPluginImages.IMG_OBJS_CLASS);
		fTraitImage = PHPPluginImages.get(PHPPluginImages.IMG_OBJS_TRAIT);
		fMethodImage = DLTKPluginImages.get(DLTKPluginImages.IMG_METHOD_PUBLIC);
	}

	@Override
	public String getText(Object element) {
		if (element != null) {
			String elementName = ""; //$NON-NLS-1$
			String fileName = ""; //$NON-NLS-1$
			if (element instanceof DLTKSearchTypeNameMatch) {
				DLTKSearchTypeNameMatch typeMatch = (DLTKSearchTypeNameMatch) element;
				IType type = typeMatch.getType();
				elementName = type.getElementName();
				fileName = getFullName(type.getSourceModule());
			} else if (element instanceof IType) {
				IType sourceElement = (IType) element;
				elementName = sourceElement.getElementName();
				fileName = getFullName(sourceElement.getSourceModule());
			} else if (element instanceof IMember) {
				IMember sourceElement = (IMember) element;
				elementName = sourceElement.getElementName();
				fileName = getFullName(sourceElement.getSourceModule());
			}

			StringBuilder result = new StringBuilder(elementName);
			result.append(" - "); //$NON-NLS-1$
			result.append(fileName);
			return result.toString();
		}
		return ""; //$NON-NLS-1$
	}

	@Override
	public Image getImage(Object element) {
		Image result = null;
		if (element != null) {
			IType type = null;
			if (element instanceof DLTKSearchTypeNameMatch) {
				DLTKSearchTypeNameMatch typeMatch = (DLTKSearchTypeNameMatch) element;
				type = typeMatch.getType();
			}
			if (element instanceof IType) {
				type = (IType) element;
			}
			if (type != null) {
				try {
					if (PHPFlags.isClass(type.getFlags())) {
						result = fClassImage;
					} else if (PHPFlags.isInterface(type.getFlags())) {
						result = fInterfaceImage;
					} else if (PHPFlags.isTrait(type.getFlags())) {
						result = fTraitImage;
					}
				} catch (ModelException e) {
					Logger.logException(e);
				}
			}
			if (element instanceof IMethod) {
				result = fMethodImage;
			}

		}
		return result;
	}

	private String getFullName(ISourceModule sourceModule) {
		IPath path = null;
		if (sourceModule.getParent() instanceof ScriptFolder) {
			path = new Path(sourceModule.getParent().getElementName()).append(new Path(sourceModule.getElementName()));
		} else {
			path = new Path(sourceModule.getElementName());
		}
		return path.toString();
	}

	@Override
	public Image decorateImage(Image image, Object element) {
		return image;
	}

	@Override
	public String decorateText(String text, Object element) {
		return text;
	}

}