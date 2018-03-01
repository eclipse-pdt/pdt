/*******************************************************************************
 * Copyright (c) 2009, 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.server.ui;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.*;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin implements ISelectionListener {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.eclipse.php.server.ui"; //$NON-NLS-1$

	// Debug mode identifier
	public static final boolean isDebugMode;

	static {
		String value = Platform.getDebugOption(PLUGIN_ID + "/debug"); //$NON-NLS-1$
		isDebugMode = value != null && value.equalsIgnoreCase("true"); //$NON-NLS-1$
	}

	public static IStructuredSelection currentSelection;

	// The shared instance
	private static Activator plugin;

	/**
	 * The constructor
	 */
	public Activator() {
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.
	 * BundleContext )
	 */
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		final IWorkbench workbench = getWorkbench();

		// make sure this is called only via the UI thread
		workbench.getDisplay().asyncExec(new Runnable() {
			@Override
			public void run() {
				IWorkbenchWindow iww = workbench.getActiveWorkbenchWindow();
				if (iww != null) {
					ISelectionService iss = iww.getSelectionService();

					ISelection s = iss.getSelection();
					if (s instanceof IStructuredSelection) {
						currentSelection = ((IStructuredSelection) s);
					}

					iss.addSelectionListener(Activator.this);
				}
			}

		});
	}

	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			currentSelection = ((IStructuredSelection) selection);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.
	 * BundleContext )
	 */
	@Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	public Image getImage(String path) {
		Image image = getImageRegistry().get(path);
		if (image == null) {
			getImageRegistry().put(path, getImageDescriptor(path));
			image = getImageRegistry().get(path);
		}

		return image;
	}

	/**
	 * Returns an image descriptor for the image file at the given plug-in
	 * relative path
	 * 
	 * @param path
	 *            the path
	 * @return the image descriptor
	 */
	public ImageDescriptor getImageDescriptor(String path) {
		ImageDescriptor descriptor = getImageRegistry().getDescriptor(path);
		if (descriptor == null) {
			getImageRegistry().put(path, imageDescriptorFromPlugin(PLUGIN_ID, path));
			descriptor = getImageRegistry().getDescriptor(path);
		}
		return descriptor;
	}

	public static void logError(Throwable e) {
		getDefault().getLog().log(new Status(IStatus.ERROR, PLUGIN_ID, e.getMessage(), e));
	}

}