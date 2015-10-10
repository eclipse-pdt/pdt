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

package org.eclipse.php.internal.ui.editor.adapter;

import java.util.Collection;
import java.util.Iterator;

import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.wst.html.ui.internal.contentoutline.JFaceNodeAdapterForHTML;
import org.eclipse.wst.sse.core.internal.provisional.INodeNotifier;
import org.eclipse.wst.xml.ui.internal.contentoutline.JFaceNodeAdapterFactory;
import org.w3c.dom.Node;

/**
 * This class was created in order to call a different refresh job then the
 * super class
 * 
 * @author guy.g
 * 
 */
public class JFaceNodeAdapterForPHP extends JFaceNodeAdapterForHTML {

	JFaceNodeAdapterFactory fAdapterFactory;
	private RefreshStructureJob fRefreshJob = null;

	public JFaceNodeAdapterForPHP(JFaceNodeAdapterFactory adapterFactory) {
		super(adapterFactory);
		fAdapterFactory = adapterFactory;
	}

	private synchronized RefreshStructureJob getRefreshJob() {
		if (fRefreshJob == null)
			fRefreshJob = new RefreshStructureJob();
		return fRefreshJob;
	}

	public void notifyChanged(INodeNotifier notifier, int eventType, Object changedFeature, Object oldValue,
			Object newValue, int pos) {

		if (notifier instanceof Node) {
			Collection listeners = fAdapterFactory.getListeners();
			Iterator iterator = listeners.iterator();

			while (iterator.hasNext()) {
				Object listener = iterator.next();
				if ((listener instanceof StructuredViewer) && (eventType == INodeNotifier.STRUCTURE_CHANGED
						|| eventType == INodeNotifier.CONTENT_CHANGED || (eventType == INodeNotifier.CHANGE))) {
					// refresh on structural and "unknown" changes
					StructuredViewer structuredViewer = (StructuredViewer) listener;

					if (structuredViewer.getControl() != null) {
						getRefreshJob().refresh(structuredViewer, (Node) notifier);
					}
				}
			}
		}
	}
}
