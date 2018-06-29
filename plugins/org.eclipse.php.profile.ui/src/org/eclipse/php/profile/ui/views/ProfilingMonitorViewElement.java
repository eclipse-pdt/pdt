/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.profile.ui.views;

import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.views.IViewDescriptor;
import org.eclipse.ui.views.IViewRegistry;

/**
 * Profiling monitor view element.
 */
public class ProfilingMonitorViewElement {

	private ProfilingMonitorElement fRoot;
	private String fViewId;
	private String fViewLabel;
	private Image fViewImage;

	public ProfilingMonitorViewElement(ProfilingMonitorElement root, String viewId) throws PartInitException {
		fRoot = root;
		fViewId = viewId;

		IViewRegistry viewRegistry = WorkbenchPlugin.getDefault().getViewRegistry();
		IViewDescriptor viewDescriptor = viewRegistry.find(viewId);

		fViewImage = viewDescriptor.getImageDescriptor().createImage();
		fViewLabel = viewDescriptor.getLabel();
	}

	public ProfilingMonitorElement getParent() {
		return fRoot;
	}

	public String getViewId() {
		return fViewId;
	}

	public Image getViewImage() {
		return fViewImage;
	}

	public String getViewLabe() {
		return fViewLabel;
	}
}
