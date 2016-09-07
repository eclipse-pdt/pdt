/*******************************************************************************
 * Copyright (c) 2012, 2016 PDT Extension Group and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.ui.controller;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.php.composer.ui.ComposerUIPluginImages;
import org.eclipse.swt.graphics.Image;

import org.eclipse.php.composer.api.collection.UniqueJsonArray;

public class PathController extends LabelProvider implements IStructuredContentProvider {

	private UniqueJsonArray paths;
	protected Image pathImage = ComposerUIPluginImages.PACKAGE_FOLDER.createImage();

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		paths = (UniqueJsonArray) newInput;
	}

	@Override
	public Image getImage(Object element) {
		return pathImage;
	}

	@Override
	public Object[] getElements(Object inputElement) {
		return paths.toArray();
	}
}
