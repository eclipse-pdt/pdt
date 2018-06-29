/*******************************************************************************
 * Copyright (c) 2015 Zend Technologies and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.ui;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.resource.CompositeImageDescriptor;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;

/**
 * Debug UI composite image descriptor.
 * 
 * @author Bartlomiej Laczkowski
 */
public class PHPDebugUICompositeImageDescriptor extends CompositeImageDescriptor {

	public static final Point SIZE_16x16 = new Point(16, 16);
	public static final Point SIZE_22x16 = new Point(22, 16);

	public static final int TOP_RIGHT = 0x0;
	public static final int TOP_LEFT = 0x1;
	public static final int BOTTOM_RIGHT = 0x2;
	public static final int BOTTOM_LEFT = 0x3;

	private ImageDescriptor baseImage;
	private ImageDescriptor overlayImage;
	private int overlayPlacement;
	private Point size;

	/**
	 * Creates new composite image descriptor.
	 * 
	 * @param baseImage
	 * @param overlayImage
	 * @param overlayPlacement
	 */
	public PHPDebugUICompositeImageDescriptor(ImageDescriptor baseImage, ImageDescriptor overlayImage,
			int overlayPlacement) {
		this(baseImage, overlayImage, overlayPlacement, SIZE_16x16);
	}

	/**
	 * Creates new composite image descriptor.
	 * 
	 * @param baseImage
	 * @param overlayImage
	 * @param overlayPlacement
	 * @param size
	 */
	public PHPDebugUICompositeImageDescriptor(ImageDescriptor baseImage, ImageDescriptor overlayImage,
			int overlayPlacement, Point size) {
		this.baseImage = baseImage;
		Assert.isNotNull(this.baseImage);
		this.overlayImage = overlayImage;
		Assert.isNotNull(this.overlayImage);
		this.overlayPlacement = overlayPlacement;
		Assert.isTrue(this.overlayPlacement >= 0);
		this.size = size;
		Assert.isNotNull(this.size);
	}

	/**
	 * Sets the size of the image created by this descriptor.
	 * 
	 * @param size
	 *            the size of the image
	 */
	public void setImageSize(Point size) {
		Assert.isNotNull(size);
		Assert.isTrue(size.x >= 0 && size.y >= 0);
		this.size = size;
	}

	/**
	 * Returns the size of the image created by this descriptor.
	 * 
	 * @return created image size
	 */
	public Point getImageSize() {
		return new Point(size.x, size.y);
	}

	@Override
	public boolean equals(Object object) {
		if (object == null || !PHPDebugUICompositeImageDescriptor.class.equals(object.getClass())) {
			return false;
		}
		PHPDebugUICompositeImageDescriptor other = (PHPDebugUICompositeImageDescriptor) object;
		return (baseImage.equals(other.baseImage) && overlayImage.equals(other.overlayImage)
				&& overlayPlacement == other.overlayPlacement);
	}

	@Override
	public int hashCode() {
		return baseImage.hashCode() | overlayImage.hashCode() | overlayPlacement | size.hashCode();
	}

	@Override
	protected Point getSize() {
		return size;
	}

	@Override
	protected void drawCompositeImage(int width, int height) {
		ImageData bg = getImageData(baseImage);
		drawImage(bg, 0, 0);
		drawOverlay();
	}

	private void drawOverlay() {
		ImageData data = getImageData(overlayImage);
		int x;
		switch (overlayPlacement) {
		case TOP_RIGHT: {
			x = size.x;
			x -= data.width;
			drawImage(data, x, 0);
			break;
		}
		case TOP_LEFT: {
			x = 0;
			drawImage(data, x, 0);
			break;
		}
		case BOTTOM_RIGHT: {
			x = size.x;
			x -= data.width;
			drawImage(data, x, size.y - data.height);
			break;
		}
		case BOTTOM_LEFT: {
			x = 0;
			drawImage(data, x, size.y - data.height);
			break;
		}
		default:
			break;
		}
	}

	private ImageData getImageData(ImageDescriptor descriptor) {
		ImageData data = descriptor.getImageData();
		if (data == null) {
			data = DEFAULT_IMAGE_DATA;
			PHPDebugUIPlugin.logErrorMessage("Image data not available: " + descriptor.toString()); //$NON-NLS-1$
		}
		return data;
	}

}
