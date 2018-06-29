/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.ui.util;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;

/**
 */
public class ImageImageDescriptor extends ImageDescriptor {

	private Image fImage;

	/**
	 * Constructor for ImagImageDescriptor.
	 */
	public ImageImageDescriptor(Image image) {
		super();
		fImage = image;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ImageDescriptor#getImageData()
	 */
	@Override
	public ImageData getImageData() {
		return fImage.getImageData();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Object#equals(Object)
	 */
	@Override
	public boolean equals(Object obj) {
		return (obj != null) && getClass().equals(obj.getClass()) && fImage.equals(((ImageImageDescriptor) obj).fImage);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return fImage.hashCode();
	}

}
