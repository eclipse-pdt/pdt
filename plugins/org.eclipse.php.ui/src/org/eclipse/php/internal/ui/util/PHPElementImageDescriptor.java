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
package org.eclipse.php.internal.ui.util;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.resource.CompositeImageDescriptor;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;

public class PHPElementImageDescriptor extends CompositeImageDescriptor {

	/** Flag to render the runnable adornment. */
	public static final int RUNNABLE = 0x010;

	/** Flag to render the warning adornment. */
	public static final int WARNING = 0x020;

	/** Flag to render the error adornment. */
	public static final int ERROR = 0x040;

	/** Flag to render the 'override' adornment. */
	public static final int OVERRIDES = 0x080;

	/** Flag to render the 'constructor' adornment. */
	public static final int CONSTRUCTOR = 0x200;

	/** Flag to render the 'static' adornment. */
	public static final int STATIC = 0x001;

	/** Flag to render the 'constant' adornment. */
	public static final int CONSTANT = 0x002;

	/** Flag to render the 'abstract' adornment. */
	public static final int ABSTRACT = 0x004;

	/** Flag to render the 'final' adornment. */
	public static final int FINAL = 0x008;

	/**
	 * Flag to render the 'deprecated' adornment.
	 * 
	 * @since 3.0
	 */
	public final static int DEPRECATED = 0x400;
	public static final Point SMALL_SIZE = new Point(16, 16);
	public static final Point BIG_SIZE = new Point(22, 16);

	private ImageDescriptor fBaseImage;
	private int fFlags;
	private Point fSize;

	/**
	 * Creates a new PHPElementImageDescriptor.
	 * 
	 * @param baseImage
	 *            an image descriptor used as the base image
	 * @param flags
	 *            flags indicating which adornments are to be rendered. See
	 *            <code>setAdornments</code> for valid values.
	 * @param size
	 *            the size of the resulting image
	 * @see #setAdornments(int)
	 */
	public PHPElementImageDescriptor(ImageDescriptor baseImage, int flags,
			Point size) {
		fBaseImage = baseImage;
		Assert.isNotNull(fBaseImage);
		fFlags = flags;
		Assert.isTrue(fFlags >= 0);
		fSize = size;
		Assert.isNotNull(fSize);
	}

	/**
	 * Sets the descriptors adornments. Valid values are: <code>ABSTRACT</code>,
	 * <code>FINAL</code>, </code>STATIC<code>, </code>RUNNABLE<code>, </code>
	 * WARNING<code>,
	 * </code>ERROR<code>, </code>OVERRIDDES
	 * <code>, <code>CONSTRUCTOR</code>, <code>DEPRECATED</code>, or any
	 * combination of those.
	 * 
	 * @param adornments
	 *            the image descriptors adornments
	 */
	public void setAdornments(int adornments) {
		Assert.isTrue(adornments >= 0);
		fFlags = adornments;
	}

	/**
	 * Returns the current adornments.
	 * 
	 * @return the current adornments
	 */
	public int getAdronments() {
		return fFlags;
	}

	/**
	 * Sets the size of the image created by calling <code>createImage()</code>.
	 * 
	 * @param size
	 *            the size of the image returned from calling
	 *            <code>createImage()</code>
	 * @see ImageDescriptor#createImage()
	 */
	public void setImageSize(Point size) {
		Assert.isNotNull(size);
		Assert.isTrue(size.x >= 0 && size.y >= 0);
		fSize = size;
	}

	/**
	 * Returns the size of the image created by calling
	 * <code>createImage()</code>.
	 * 
	 * @return the size of the image created by calling
	 *         <code>createImage()</code>
	 * @see ImageDescriptor#createImage()
	 */
	public Point getImageSize() {
		return new Point(fSize.x, fSize.y);
	}

	protected Point getSize() {
		return fSize;
	}

	public boolean equals(Object object) {
		if (object == null
				|| !PHPElementImageDescriptor.class.equals(object.getClass()))
			return false;

		PHPElementImageDescriptor other = (PHPElementImageDescriptor) object;
		return (fBaseImage.equals(other.fBaseImage) && fFlags == other.fFlags && fSize
				.equals(other.fSize));
	}

	public int hashCode() {
		return fBaseImage.hashCode() | fFlags | fSize.hashCode();
	}

	protected void drawCompositeImage(int width, int height) {
		ImageData bg = getImageData(fBaseImage);

		if ((fFlags & DEPRECATED) != 0) { // over the full image
			Point size = getSize();
			ImageData data = getImageData(PHPPluginImages.DESC_OVR_DEPRECATED);
			drawImage(data, 0, size.y - data.height);
		}
		drawImage(bg, 0, 0);

		drawTopRight();
		drawBottomRight();
		drawBottomLeft();

	}

	private ImageData getImageData(ImageDescriptor descriptor) {
		ImageData data = descriptor.getImageData();
		if (data == null) {
			data = DEFAULT_IMAGE_DATA;
			PHPUiPlugin
					.logErrorMessage("Image data not available: " + descriptor.toString()); //$NON-NLS-1$
		}
		return data;
	}

	private void drawTopRight() {
		int x = getSize().x;
		if ((fFlags & ABSTRACT) != 0) {
			ImageData data = getImageData(PHPPluginImages.DESC_OVR_ABSTRACT);
			x -= data.width;
			drawImage(data, x, 0);
		}
		if ((fFlags & CONSTANT) != 0) {
			ImageData data = getImageData(PHPPluginImages.DESC_OVR_CONSTANT);
			x -= data.width;
			drawImage(data, x, 0);
		}
		if ((fFlags & FINAL) != 0) {
			ImageData data = getImageData(PHPPluginImages.DESC_OVR_FINAL);
			x -= data.width;
			drawImage(data, x, 0);
		}
		if ((fFlags & STATIC) != 0) {
			ImageData data = getImageData(PHPPluginImages.DESC_OVR_STATIC);
			x -= data.width;
			drawImage(data, x, 0);
		}
	}

	private void drawBottomRight() {
		Point size = getSize();
		int x = size.x;
		int flags = fFlags;

		if ((flags & OVERRIDES) != 0) {
			ImageData data = getImageData(PHPPluginImages.DESC_OVR_OVERRIDES);
			x -= data.width;
			drawImage(data, x, size.y - data.height);
		}
		if ((flags & RUNNABLE) != 0) {
			ImageData data = getImageData(PHPPluginImages.DESC_OVR_RUN);
			x -= data.width;
			drawImage(data, x, size.y - data.height);
		}
	}

	private void drawBottomLeft() {
		Point size = getSize();
		int x = 0;
		if ((fFlags & ERROR) != 0) {
			ImageData data = getImageData(PHPPluginImages.DESC_OVR_ERROR);
			drawImage(data, x, size.y - data.height);
			x += data.width;
		}
		if ((fFlags & WARNING) != 0) {
			ImageData data = getImageData(PHPPluginImages.DESC_OVR_WARNING);
			drawImage(data, x, size.y - data.height);
			x += data.width;
		}

	}
}
