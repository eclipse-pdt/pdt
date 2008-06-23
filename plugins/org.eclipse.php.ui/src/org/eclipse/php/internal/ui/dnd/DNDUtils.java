/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.dnd;

import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.*;
import org.eclipse.ui.internal.EditorStack;
import org.eclipse.ui.internal.WorkbenchPage;

public class DNDUtils {
	private static final String DROP_TARGET_ID = "DropTarget"; //$NON-NLS-1$
	private static final IWindowListener WINDOW_LISTENER = new IWindowListener() {

		public void windowActivated(IWorkbenchWindow window) {
		}

		public void windowClosed(IWorkbenchWindow window) {
		}

		public void windowDeactivated(IWorkbenchWindow window) {
		}

		public void windowOpened(IWorkbenchWindow window) {
			windowEnableExternalDrop(window);
		}
	};
	private static final IPageListener PAGE_LISTENER = new IPageListener() {

		public void pageActivated(IWorkbenchPage page) {
		}

		public void pageClosed(IWorkbenchPage page) {
		}

		public void pageOpened(IWorkbenchPage page) {
			pageEnableExternalDrop(page);
		}
	};
	private static final IPropertyChangeListener PART_LISTENER = new IPropertyChangeListener() {

		public void propertyChange(PropertyChangeEvent event) {
			int i = 1;
		}
	};

	public static void initEditorSiteExternalDrop() {
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if (window != null)
			windowEnableExternalDrop(window);

		PlatformUI.getWorkbench().addWindowListener(WINDOW_LISTENER);
	}

	/**
	 * @param window
	 */
	public static void windowEnableExternalDrop(IWorkbenchWindow window) {
		IWorkbenchPage activePage = window.getActivePage();
		if (activePage != null)
			pageEnableExternalDrop(activePage);
		window.addPageListener(PAGE_LISTENER);
	}

	/**
	 * @param activePage
	 */
	public static void pageEnableExternalDrop(IWorkbenchPage activePage) {
		Control[] children = ((WorkbenchPage) activePage).getClientComposite().getChildren();
		for (int i = 0; i < children.length; i++) {
			if (children[i] instanceof Composite) {
				Control[] subChildren = ((Composite) children[i]).getChildren();
				for (int j = 0; j < subChildren.length; j++) {
					if (subChildren[j] instanceof CTabFolder) {
						Object data = ((CTabFolder) subChildren[j]).getData();
						if (data instanceof EditorStack) {
							enableExternalDrop(subChildren[j]);
						}
					}
				}
			}
		}
		activePage.addPropertyChangeListener(PART_LISTENER);
	}

	/**
	 * @param control
	 */
	public static void enableExternalDrop(Control control) {
		if (control.getData(DROP_TARGET_ID) != null)
			return; // already assigned
		DropTarget target = new DropTarget(control, DND.DROP_COPY);
		ExternalFileDropAdapter transferDropAdapter = ExternalFileDropAdapter.getInstance();
		target.setTransfer(new Transfer[] { transferDropAdapter.getTransfer() });
		target.addDropListener(transferDropAdapter);
		target.addListener(SWT.Dispose, new Listener() {
			public void handleEvent(Event event) {
				// reinitialize drop listener
				initEditorSiteExternalDrop();
			}
		});

	}

}
