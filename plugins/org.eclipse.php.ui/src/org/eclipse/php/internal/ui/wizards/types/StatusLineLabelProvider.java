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

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.internal.core.ExternalSourceModule;
import org.eclipse.dltk.internal.core.SourceType;
import org.eclipse.dltk.internal.core.search.DLTKSearchTypeNameMatch;
import org.eclipse.dltk.ui.DLTKPluginImages;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.php.core.compiler.PHPFlags;
import org.eclipse.php.internal.ui.Logger;
import org.eclipse.php.internal.ui.util.PHPPluginImages;
import org.eclipse.swt.graphics.Image;

public class StatusLineLabelProvider extends LabelProvider {
	private Image fInterfaceImage;
	private Image fClassImage;

	public StatusLineLabelProvider() {
		fInterfaceImage = PHPPluginImages.get(PHPPluginImages.IMG_OBJS_INTERFACE);

		fClassImage = DLTKPluginImages.get(DLTKPluginImages.IMG_OBJS_CLASS);
	}

	@Override
	public String getText(Object element) {
		if (element != null) {
			String elementName = ""; //$NON-NLS-1$
			String projectName = ""; //$NON-NLS-1$
			IType type = null;
			if (element instanceof DLTKSearchTypeNameMatch) {
				DLTKSearchTypeNameMatch typeMatch = (DLTKSearchTypeNameMatch) element;
				type = typeMatch.getType();
				elementName = type.getElementName();

			} else if (element instanceof SourceType) {
				type = (IType) element;
				elementName = type.getElementName();
				projectName = type.getResource().getProject().getName();
			}

			if (type != null) {
				IResource member = ResourcesPlugin.getWorkspace().getRoot().findMember(type.getPath());
				if (member != null) {
					projectName = member.getProject().getName();
				} else if (type.getParent() instanceof ExternalSourceModule) {
					IModelElement parent = type.getParent();
					do {
						parent = parent.getParent();
					} while (parent != null && parent.getElementType() != IModelElement.SCRIPT_PROJECT);
					if (parent instanceof IScriptProject) {
						projectName = ((IScriptProject) parent).getElementName();
					}
				}
			}

			StringBuilder result = new StringBuilder(elementName);
			result.append(" - "); //$NON-NLS-1$
			result.append(projectName);
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
			if (element instanceof SourceType) {
				type = (IType) element;
			}
			if (type == null) {
				return null;
			}
			try {
				if (PHPFlags.isClass(type.getFlags())) {
					result = fClassImage;
				} else if (PHPFlags.isInterface(type.getFlags())) {
					result = fInterfaceImage;
				}
			} catch (ModelException e) {
				Logger.logException(e);
			}
		}
		return result;
	}
}