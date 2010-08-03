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

import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.util.PHPPluginImages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

public class IPVariableElementLabelProvider extends LabelProvider implements
		IColorProvider {

	private Image fZIPImage;
	private Image fFolderImage;
	private boolean fShowResolvedVariables;

	private Color fResolvedBackground;

	public IPVariableElementLabelProvider(boolean showResolvedVariables) {
		ImageRegistry reg = PHPUiPlugin.getDefault().getImageRegistry();
		fZIPImage = reg.get(PHPPluginImages.IMG_OBJS_EXTZIP);
		fFolderImage = PlatformUI.getWorkbench().getSharedImages().getImage(
				ISharedImages.IMG_OBJ_FOLDER);
		fShowResolvedVariables = showResolvedVariables;
		fResolvedBackground = null;
	}

	/*
	 * @see LabelProvider#getImage(java.lang.Object)
	 */
	public Image getImage(Object element) {
		if (element instanceof IPVariableElement) {
			IPVariableElement curr = (IPVariableElement) element;
			IPath path = curr.getPath();
			if (path.toFile().isFile()) {
				return fZIPImage;
			}
			return fFolderImage;
		}
		return super.getImage(element);
	}

	/*
	 * @see LabelProvider#getText(java.lang.Object)
	 */
	public String getText(Object element) {
		if (element instanceof IPVariableElement) {
			IPVariableElement curr = (IPVariableElement) element;
			String name = curr.getName();
			IPath path = curr.getPath();
			StringBuffer buf = new StringBuffer(name);
			if (curr.isReserved()) {
				buf.append(' ');
				buf.append(PHPUIMessages.CPVariableElementLabelProvider_reserved);
			}
			if (path != null) {
				buf.append(" - "); //$NON-NLS-1$
				if (!path.isEmpty()) {
					buf.append(path.toOSString());
				} else {
					buf.append(PHPUIMessages.CPVariableElementLabelProvider_empty);
				}
			}
			return buf.toString();
		}

		return super.getText(element);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.IColorProvider#getForeground(java.lang.Object)
	 */
	public Color getForeground(Object element) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.IColorProvider#getBackground(java.lang.Object)
	 */
	public Color getBackground(Object element) {
		if (element instanceof IPVariableElement) {
			IPVariableElement curr = (IPVariableElement) element;
			if (!fShowResolvedVariables && curr.isReserved()) {
				if (fResolvedBackground == null) {
					Display display = Display.getCurrent();
					fResolvedBackground = display
							.getSystemColor(SWT.COLOR_INFO_BACKGROUND);
				}
				return fResolvedBackground;
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#dispose()
	 */
	public void dispose() {
		super.dispose();
	}

}
