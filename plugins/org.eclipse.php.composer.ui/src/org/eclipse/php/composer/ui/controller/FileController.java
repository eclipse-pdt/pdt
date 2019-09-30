/*******************************************************************************
 * Copyright (c) 2012, 2016 PDT Extension Group and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.ui.controller;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.php.composer.api.entities.AbstractJsonArray;
import org.eclipse.php.composer.ui.ComposerUIPluginImages;
import org.eclipse.swt.graphics.Image;

public class FileController extends LabelProvider implements IStructuredContentProvider {

	private AbstractJsonArray<Object> paths;
	protected Image pathImage = ComposerUIPluginImages.PACKAGE_FOLDER.createImage();
	protected Image pageImage = ComposerUIPluginImages.PAGE.createImage();

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		paths = (AbstractJsonArray<Object>) newInput;
	}

	@Override
	public Image getImage(Object element) {
		if (element.toString().endsWith("/")) {
			return pathImage;
		}

		return pageImage;
	}

	@Override
	public Object[] getElements(Object inputElement) {
		return paths.toArray();
	}
}
