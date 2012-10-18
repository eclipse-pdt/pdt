package org.eclipse.php.internal.ui.viewsupport;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.ui.ScriptElementImageProvider;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.php.core.compiler.PHPFlags;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.util.PHPPluginImages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;

/**
 * Image registry that keeps its images on the local file system.
 * 
 * @since 3.4
 */
public class ImagesOnFileSystemRegistry {

	private static final String IMAGE_DIR = "pdt-images"; //$NON-NLS-1$

	private HashMap fURLMap;
	private final File fTempDir;
	private final ScriptElementImageProvider fImageProvider;
	private int fImageCount;

	public ImagesOnFileSystemRegistry() {
		fURLMap = new HashMap();
		fTempDir = getTempDir();
		fImageProvider = new ScriptElementImageProvider() {
			@Override
			public ImageDescriptor getBaseImageDescriptor(
					IModelElement element, int renderFlags) {
				// TODO Auto-generated method stub
				if (element.getElementType() == IModelElement.TYPE) {
					IType type = (IType) element;
					try {
						if (PHPFlags.isTrait(type.getFlags())) {
							return PHPPluginImages.DESC_OBJS_TRAIT;
						}
					} catch (ModelException e) {
					}
				}
				return super.getBaseImageDescriptor(element, renderFlags);
			}
		};
		fImageCount = 0;
	}

	private File getTempDir() {
		try {
			File imageDir = PHPUiPlugin.getDefault().getStateLocation()
					.append(IMAGE_DIR).toFile();
			if (imageDir.exists()) {
				// has not been deleted on previous shutdown
				delete(imageDir);
			}
			if (!imageDir.exists()) {
				imageDir.mkdir();
			}
			if (!imageDir.isDirectory()) {
				PHPUiPlugin
						.logErrorMessage("Failed to create image directory " + imageDir.toString()); //$NON-NLS-1$
				return null;
			}
			return imageDir;
		} catch (IllegalStateException e) {
			// no state location
			return null;
		}
	}

	private void delete(File file) {
		if (file.isDirectory()) {
			File[] listFiles = file.listFiles();
			for (int i = 0; i < listFiles.length; i++) {
				delete(listFiles[i]);
			}
		}
		file.delete();
	}

	public URL getImageURL(IModelElement element) {
		ImageDescriptor descriptor = fImageProvider.getScriptImageDescriptor(
				element, ScriptElementImageProvider.OVERLAY_ICONS
						| ScriptElementImageProvider.SMALL_ICONS);
		if (descriptor == null)
			return null;
		return getImageURL(descriptor);
	}

	public URL getImageURL(ImageDescriptor descriptor) {
		if (fTempDir == null)
			return null;

		URL url = (URL) fURLMap.get(descriptor);
		if (url != null)
			return url;

		File imageFile = getNewFile();
		ImageData imageData = descriptor.getImageData();
		if (imageData == null) {
			return null;
		}

		ImageLoader loader = new ImageLoader();
		loader.data = new ImageData[] { imageData };
		loader.save(imageFile.getAbsolutePath(), SWT.IMAGE_PNG);

		try {
			url = imageFile.toURI().toURL();
			fURLMap.put(descriptor, url);
			return url;
		} catch (MalformedURLException e) {
			PHPUiPlugin.log(e);
		}
		return null;
	}

	private File getNewFile() {
		File file;
		do {
			file = new File(fTempDir, String.valueOf(getImageCount()) + ".png"); //$NON-NLS-1$
		} while (file.exists());
		return file;
	}

	private synchronized int getImageCount() {
		return fImageCount++;
	}

	public void dispose() {
		if (fTempDir != null) {
			delete(fTempDir);
		}
		fURLMap = null;
	}
}
