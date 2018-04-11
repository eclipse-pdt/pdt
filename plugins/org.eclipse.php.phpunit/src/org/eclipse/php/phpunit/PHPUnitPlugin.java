/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.phpunit;

import java.net.URL;

import org.eclipse.core.runtime.*;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.Bundle;

public class PHPUnitPlugin extends AbstractUIPlugin {

	private static final IPath ICONS_PATH = new Path("$nl$/icons/full"); //$NON-NLS-1$

	// The shared instance.
	private static PHPUnitPlugin instance;

	public static final String ID = "org.eclipse.php.phpunit"; //$NON-NLS-1$

	public static final String CONFIG_TYPE = ID + ".PHPUnitLaunchConfigurationType"; //$NON-NLS-1$
	public static final String ICON_MAIN = "main.png"; //$NON-NLS-1$
	public static final String ELEMENT_PATH_ATTR = ID + ".elementPath"; //$NON-NLS-1$

	public PHPUnitPlugin() {
		instance = this;
	}

	public static Image createImage(final String path) {
		return getImageDescriptor(path).createImage();
	}

	/**
	 * Returns the shared instance.
	 */
	public static PHPUnitPlugin getDefault() {
		return instance;
	}

	/**
	 * Returns an image descriptor for the image file at the given plug-in relative
	 * path.
	 * 
	 * @param relativePath
	 *                         the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(final String relativePath) {
		final IPath path = ICONS_PATH.append(relativePath);
		return createImageDescriptor(getDefault().getBundle(), path);
	}

	public static ImageDescriptor createImageDescriptor(final Bundle bundle, final IPath path) {
		final URL url = FileLocator.find(bundle, path, null);
		if (url != null) {
			return ImageDescriptor.createFromURL(url);
		}
		return ImageDescriptor.getMissingImageDescriptor();
	}

	public static void log(final IStatus status) {
		getDefault().getLog().log(status);
	}

	public static void log(final Throwable e) {
		log(new Status(IStatus.ERROR, ID, IStatus.ERROR, "Error", e)); //$NON-NLS-1$
	}

}
