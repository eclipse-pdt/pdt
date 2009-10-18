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
package org.eclipse.php.internal.debug.ui.breakpoint;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.resource.CompositeImageDescriptor;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.php.internal.debug.ui.PHPDebugUIImages;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;

/**
 * An image descriptor for the PHP conditional breakpoints.
 * 
 * @author shalom
 * @since 26/09/2006
 */
public class PHPBreakpointImageDescriptor extends CompositeImageDescriptor {

	/** Flag to render the enabled breakpoint adornment */
	public final static int ENABLED = 0x0020;
	/** Flag to render the conditional breakpoint adornment */
	public final static int CONDITIONAL = 0x0040;

	private ImageDescriptor fBaseImage;
	private int fFlags;
	private Point fSize;

	/**
	 * Constructs a new PHPBreakpointImageDescriptor.
	 * 
	 * @param baseImage
	 *            an image descriptor used as the base image.
	 * @param flags
	 *            flags indicating which adornments are to be rendered.
	 * @see #ENABLED
	 * @see #CONDITIONAL
	 */
	public PHPBreakpointImageDescriptor(ImageDescriptor baseImage, int flags) {
		fBaseImage = baseImage;
		Assert.isNotNull(fBaseImage);
		fFlags = flags;
		Assert.isTrue(fFlags >= 0);
	}

	/**
	 * @see Object#equals(java.lang.Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof PHPBreakpointImageDescriptor)) {
			return false;
		}

		PHPBreakpointImageDescriptor other = (PHPBreakpointImageDescriptor) object;
		return (getBaseImage().equals(other.getBaseImage()) && getFlags() == other
				.getFlags());
	}

	/**
	 * @see Object#hashCode()
	 */
	public int hashCode() {
		return getBaseImage().hashCode() | getFlags();
	}

	protected void drawCompositeImage(int width, int height) {
		ImageData bg = getBaseImage().getImageData();
		if (bg == null) {
			bg = DEFAULT_IMAGE_DATA;
		}
		drawImage(bg, 0, 0);
		drawOverlays();
	}

	private void drawOverlays() {
		int flags = getFlags();
		int x = 0;
		int y = 0;
		ImageData data = null;
		if ((flags & CONDITIONAL) != 0) {
			if ((flags & ENABLED) != 0) {
				data = getImageData(PHPDebugUIImages.IMG_OVR_CONDITIONAL_BREAKPOINT);
			} else {
				data = getImageData(PHPDebugUIImages.IMG_OVR_CONDITIONAL_BREAKPOINT_DISABLED);
			}
			x = 0;
			y = 0;
			drawImage(data, x, y);
		}
	}

	private ImageData getImageData(String imageDescriptorKey) {
		return PHPDebugUIImages.getImageDescriptor(imageDescriptorKey)
				.getImageData();
	}

	protected Point getSize() {
		if (fSize == null) {
			ImageData data = getBaseImage().getImageData();
			setSize(new Point(data.width, data.height));
		}
		return fSize;
	}

	protected ImageDescriptor getBaseImage() {
		return fBaseImage;
	}

	protected void setBaseImage(ImageDescriptor baseImage) {
		fBaseImage = baseImage;
	}

	protected int getFlags() {
		return fFlags;
	}

	protected void setFlags(int flags) {
		fFlags = flags;
	}

	protected void setSize(Point size) {
		fSize = size;
	}

}
