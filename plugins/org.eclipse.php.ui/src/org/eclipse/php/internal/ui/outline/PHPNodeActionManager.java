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
package org.eclipse.php.internal.ui.outline;

import org.eclipse.dltk.internal.core.ModelElement;
import org.eclipse.dltk.internal.ui.actions.CompositeActionGroup;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.wst.html.ui.internal.contentoutline.HTMLNodeActionManager;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;

/*
 * This class is used to propagate the context menu in the PHP Outline view
 */
public class PHPNodeActionManager extends HTMLNodeActionManager {
	// An action group used for generating the standard options
	private CompositeActionGroup fActionGroup = new CompositeActionGroup();

	public PHPNodeActionManager(IStructuredModel model, Viewer viewer) {
		super(model, viewer);
	}

	public void fillContextMenu(IMenuManager menuManager, ISelection selection) {
		// check the selection type - only if the underlying resource is a
		// ModelElement
		// the standard options are propagated
		if (selection instanceof TreeSelection) {
			Object firstElement = ((TreeSelection) selection).getFirstElement();
			if (firstElement instanceof ModelElement) {
				fActionGroup.fillContextMenu(menuManager);
				return;
			}
		}
		// if the selection is not a ModelElement - generate the HTML options
		super.fillContextMenu(menuManager, selection);
	}
}
