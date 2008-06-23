/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
/**
 * 
 */
package org.eclipse.php.internal.ui;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.php.internal.core.phpModel.PHPModelUtil;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPCodeData;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPFileData;
import org.eclipse.php.internal.ui.util.PHPElementImageProvider;
import org.eclipse.swt.graphics.Image;

public class PHPElementContainerLabelProvider extends LabelProvider {

	// TODO getImage

	public Image getImage(final Object element) {
		Object imageElement = PHPModelUtil.getResource(element);
		if (imageElement == null)
			imageElement = element;
		return (new PHPElementImageProvider()).getImageLabel(imageElement, 0);
	}

	public String getText(final Object element) {
		return getText(element, new StringBuffer());
	}

	public String getText(final Object element, final StringBuffer buf) {

		if (element instanceof PHPFileData) {
			final String label = ((PHPFileData) element).getName();
			final IFile file = (IFile) PHPModelUtil.getResource(element);
			buf.ensureCapacity(buf.capacity() + label.length());
			buf.insert(0, label);
			if (file == null)
				return buf.toString();
			return buf.toString();
		}
		if (element instanceof PHPCodeData) {
			final PHPCodeData codeData = (PHPCodeData) element;
			PHPCodeData container = PHPModelUtil.getPHPFileContainer(codeData);
			return getText(container, buf);
		}
		return ""; //$NON-NLS-1$

	}
}