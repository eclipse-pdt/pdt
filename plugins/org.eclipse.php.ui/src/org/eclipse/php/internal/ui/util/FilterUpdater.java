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

import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.swt.widgets.Control;

public class FilterUpdater implements IResourceChangeListener {

	private StructuredViewer fViewer;

	public FilterUpdater(StructuredViewer viewer) {
		Assert.isNotNull(viewer);
		fViewer = viewer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.resources.IResourceChangeListener#resourceChanged(org
	 * .eclipse.core.resources.IResourceChangeEvent)
	 */
	public void resourceChanged(IResourceChangeEvent event) {
		IResourceDelta delta = event.getDelta();
		if (delta == null)
			return;

		IResourceDelta[] projDeltas = delta
				.getAffectedChildren(IResourceDelta.CHANGED);
		for (int i = 0; i < projDeltas.length; i++) {
			IResourceDelta pDelta = projDeltas[i];
			if ((pDelta.getFlags() & IResourceDelta.DESCRIPTION) != 0) {
				final Control ctrl = fViewer.getControl();
				if (ctrl != null && !ctrl.isDisposed()) {
					// async is needed due to bug 33783
					ctrl.getDisplay().asyncExec(new Runnable() {
						public void run() {
							if (!ctrl.isDisposed())
								fViewer.refresh(false);
						}
					});
				}
			}
		}
	}
}
