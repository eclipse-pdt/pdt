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
package org.eclipse.php.internal.ui;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Widget;

/**
 * 
 * Ported from package org.eclipse.jdt.internal.ui.viewsupport
 * 
 * A selection provider for view parts with more that one viewer. Tracks the
 * focus of the viewers to provide the correct selection.
 * 
 */
@Deprecated
public class SelectionProviderMediator implements IPostSelectionProvider {

	private class InternalListener implements ISelectionChangedListener, FocusListener {
		/*
		 * @see ISelectionChangedListener#selectionChanged
		 */
		public void selectionChanged(SelectionChangedEvent event) {
			doSelectionChanged(event);
		}

		/*
		 * @see FocusListener#focusGained
		 */
		public void focusGained(FocusEvent e) {
			doFocusChanged(e.widget);
		}

		/*
		 * @see FocusListener#focusLost
		 */
		public void focusLost(FocusEvent e) {
			// do not reset due to focus behavior on GTK
			// fViewerInFocus= null;
		}
	}

	private class InternalPostSelectionListener implements ISelectionChangedListener {
		public void selectionChanged(SelectionChangedEvent event) {
			doPostSelectionChanged(event);
		}

	}

	private StructuredViewer[] fViewers;

	private StructuredViewer fViewerInFocus;
	private ListenerList fSelectionChangedListeners;
	private ListenerList fPostSelectionChangedListeners;

	/**
	 * @param viewers
	 *            All viewers that can provide a selection
	 * @param viewerInFocus
	 *            the viewer currently in focus or <code>null</code>
	 */
	public SelectionProviderMediator(StructuredViewer[] viewers, StructuredViewer viewerInFocus) {
		Assert.isNotNull(viewers);
		fViewers = viewers;
		InternalListener listener = new InternalListener();
		fSelectionChangedListeners = new ListenerList();
		fPostSelectionChangedListeners = new ListenerList();
		fViewerInFocus = viewerInFocus;

		for (int i = 0; i < fViewers.length; i++) {
			StructuredViewer viewer = fViewers[i];
			viewer.addSelectionChangedListener(listener);
			viewer.addPostSelectionChangedListener(new InternalPostSelectionListener());
			Control control = viewer.getControl();
			control.addFocusListener(listener);
		}
	}

	private void doFocusChanged(Widget control) {
		for (int i = 0; i < fViewers.length; i++) {
			if (fViewers[i].getControl() == control) {
				propagateFocusChanged(fViewers[i]);
				return;
			}
		}
	}

	final void doPostSelectionChanged(SelectionChangedEvent event) {
		ISelectionProvider provider = event.getSelectionProvider();
		if (provider == fViewerInFocus) {
			firePostSelectionChanged();
		}
	}

	final void doSelectionChanged(SelectionChangedEvent event) {
		ISelectionProvider provider = event.getSelectionProvider();
		if (provider == fViewerInFocus) {
			fireSelectionChanged();
		}
	}

	final void propagateFocusChanged(StructuredViewer viewer) {
		if (viewer != fViewerInFocus) { // OK to compare by identity
			fViewerInFocus = viewer;
			fireSelectionChanged();
			firePostSelectionChanged();
		}
	}

	private void fireSelectionChanged() {
		if (fSelectionChangedListeners != null) {
			SelectionChangedEvent event = new SelectionChangedEvent(this, getSelection());

			Object[] listeners = fSelectionChangedListeners.getListeners();
			for (int i = 0; i < listeners.length; i++) {
				ISelectionChangedListener listener = (ISelectionChangedListener) listeners[i];
				listener.selectionChanged(event);
			}
		}
	}

	private void firePostSelectionChanged() {
		if (fPostSelectionChangedListeners != null) {
			SelectionChangedEvent event = new SelectionChangedEvent(this, getSelection());

			Object[] listeners = fPostSelectionChangedListeners.getListeners();
			for (int i = 0; i < listeners.length; i++) {
				ISelectionChangedListener listener = (ISelectionChangedListener) listeners[i];
				listener.selectionChanged(event);
			}
		}
	}

	/*
	 * @see ISelectionProvider#addSelectionChangedListener
	 */
	public void addSelectionChangedListener(ISelectionChangedListener listener) {
		fSelectionChangedListeners.add(listener);
	}

	/*
	 * @see ISelectionProvider#removeSelectionChangedListener
	 */
	public void removeSelectionChangedListener(ISelectionChangedListener listener) {
		fSelectionChangedListeners.remove(listener);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.jface.viewers.IPostSelectionProvider#
	 * addPostSelectionChangedListener
	 * (org.eclipse.jface.viewers.ISelectionChangedListener)
	 */
	public void addPostSelectionChangedListener(ISelectionChangedListener listener) {
		fPostSelectionChangedListeners.add(listener);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.jface.viewers.IPostSelectionProvider#
	 * removePostSelectionChangedListener
	 * (org.eclipse.jface.viewers.ISelectionChangedListener)
	 */
	public void removePostSelectionChangedListener(ISelectionChangedListener listener) {
		fPostSelectionChangedListeners.remove(listener);
	}

	/*
	 * @see ISelectionProvider#getSelection
	 */
	public ISelection getSelection() {
		if (fViewerInFocus != null) {
			return fViewerInFocus.getSelection();
		}
		return StructuredSelection.EMPTY;
	}

	/*
	 * @see ISelectionProvider#setSelection
	 */
	public void setSelection(ISelection selection) {
		if (fViewerInFocus != null) {
			fViewerInFocus.setSelection(selection);
		}
	}

	public void setSelection(ISelection selection, boolean reveal) {
		if (fViewerInFocus != null) {
			fViewerInFocus.setSelection(selection, reveal);
		}
	}

	/**
	 * Returns the viewer in focus or null if no viewer has the focus
	 */
	public StructuredViewer getViewerInFocus() {
		return fViewerInFocus;
	}
}
