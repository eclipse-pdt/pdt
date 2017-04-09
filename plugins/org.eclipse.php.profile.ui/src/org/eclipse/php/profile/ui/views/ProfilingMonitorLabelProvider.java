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
package org.eclipse.php.profile.ui.views;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.osgi.util.NLS;
import org.eclipse.php.profile.core.engine.ProfilerDB;
import org.eclipse.php.profile.ui.PHPProfileUIMessages;
import org.eclipse.php.profile.ui.ProfilerUIImages;
import org.eclipse.swt.graphics.Image;

/**
 * Profiling monitor label provider.
 */
public class ProfilingMonitorLabelProvider extends LabelProvider {

	private Image fProcessImage;

	public ProfilingMonitorLabelProvider() {
		fProcessImage = ProfilerUIImages
				.get(ProfilerUIImages.IMG_OBJ_PROCESS);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.LabelProvider#getImage(java.lang.Object)
	 */
	public Image getImage(Object element) {
		if (element instanceof ProfilingMonitorElement) {
			return fProcessImage;
		}
		if (element instanceof ProfilingMonitorViewElement) {
			return ((ProfilingMonitorViewElement) element).getViewImage();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.LabelProvider#getText(java.lang.Object)
	 */
	public String getText(Object element) {
		if (element instanceof ProfilingMonitorElement) {
			ProfilerDB db = ((ProfilingMonitorElement) element).getProfilerDB();
			String uri = db.getGlobalData().getURI();
			if (uri.length() == 0) {
				uri = db.getGlobalData().getPath();
			}
			return NLS
					.bind(
							PHPProfileUIMessages
									.getString("ProfilingMonitorLabelProvider.0"), uri, db.getProfileDate()); //$NON-NLS-1$
		}
		if (element instanceof ProfilingMonitorViewElement) {
			return ((ProfilingMonitorViewElement) element).getViewLabe();
		}
		return super.getText(element);
	}
}
