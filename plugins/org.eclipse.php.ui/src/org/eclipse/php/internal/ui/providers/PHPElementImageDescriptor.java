package org.eclipse.php.internal.ui.providers;

import org.eclipse.dltk.ui.DLTKPluginImages;
import org.eclipse.dltk.ui.ScriptElementImageDescriptor;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.php.internal.ui.util.PHPPluginImages;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;

public class PHPElementImageDescriptor extends ScriptElementImageDescriptor {
	/** Flag to render the 'override' adornment. */
	public final static int OVERRIDES = 0x080;

	/** Flag to render the 'implements' adornment. */
	public final static int IMPLEMENTS = 0x100;

	ImageDescriptor fBaseImage;
	private int fFlags;

	public PHPElementImageDescriptor(ImageDescriptor baseImageDescriptor,
			int flags, Point size) {
		super(baseImageDescriptor, flags, size);
		fBaseImage = baseImageDescriptor;
		fFlags = flags;
	}

	private ImageData getImageData(ImageDescriptor descriptor) {

		if (this.fBaseImage != null) {
			ImageData data = descriptor.getImageData(); // see bug 51965:
			// getImageData can
			// return null
			if (data == null) {
				data = DEFAULT_IMAGE_DATA;
				System.err
						.println("Image data not available: " + descriptor.toString()); //$NON-NLS-1$
				// DLTKUIPlugin.logErrorMessage("Image data not available: " + descriptor.toString()); //$NON-NLS-1$
			}
			return data;
		} else {
			System.err
					.println("Image data not available: " + descriptor.toString()); //$NON-NLS-1$
			return DEFAULT_IMAGE_DATA;
		}
	}

	protected void drawCompositeImage(int width, int height) {
		ImageData bg = getImageData(fBaseImage);

		if (bg != null) {
			drawImage(bg, 0, 0);
		}
		drawTopRight();
		drawBottomRight();
		drawBottomLeft();
	}

	protected void drawBottomLeft() {
		Point size = getSize();
		int x = 0;
		if ((fFlags & ERROR) != 0) {
			ImageData data = getImageData(DLTKPluginImages.DESC_OVR_ERROR);
			drawImage(data, x, size.y - data.height);
			x += data.width;
		}
		if ((fFlags & WARNING) != 0) {
			ImageData data = getImageData(DLTKPluginImages.DESC_OVR_WARNING);
			drawImage(data, x, size.y - data.height);
			x += data.width;
		}

	}

	protected void drawTopRight() {
		Point pos = new Point(getSize().x, 0);
		if ((fFlags & ABSTRACT) != 0) {
			addTopRightImage(DLTKPluginImages.DESC_OVR_ABSTRACT, pos);
		}
		if ((fFlags & CONSTRUCTOR) != 0) {
			addTopRightImage(DLTKPluginImages.DESC_OVR_CONSTRUCTOR, pos);
		}
		if ((fFlags & FINAL) != 0) {
			addTopRightImage(PHPPluginImages.DESC_OVR_CONSTANT, pos);
		}
		if ((fFlags & STATIC) != 0) {
			addTopRightImage(DLTKPluginImages.DESC_OVR_STATIC, pos);
		}

	}

	private void drawBottomRight() {
		Point size = getSize();
		Point pos = new Point(size.x, size.y);

		int flags = fFlags;

		if ((flags & OVERRIDES) != 0) {
			addBottomRightImage(PHPPluginImages.DESC_OVR_OVERRIDES, pos);
		}
		if ((flags & IMPLEMENTS) != 0) {
			addBottomRightImage(PHPPluginImages.DESC_OVR_IMPLEMENTS, pos);
		}
	}

	private void addBottomRightImage(ImageDescriptor desc, Point pos) {
		ImageData data = getImageData(desc);
		int x = pos.x - data.width;
		int y = pos.y - data.height;
		if (x >= 0 && y >= 0) {
			drawImage(data, x, y);
			pos.x = x;
		}
	}

	protected void addTopRightImage(ImageDescriptor desc, Point pos) {
		ImageData data = getImageData(desc);
		int x = pos.x - data.width;
		if (x >= 0) {
			drawImage(data, x, pos.y);
			pos.x = x;
		}
	}
}
