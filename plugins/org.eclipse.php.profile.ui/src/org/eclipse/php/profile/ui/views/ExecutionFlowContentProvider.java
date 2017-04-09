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

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * Execution flow content provider.
 */
public class ExecutionFlowContentProvider implements ITreeContentProvider {

	private static final Object[] EMPTY_SET = new Object[0];

	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof ExecutionFlowTreeElement) {
			return ((ExecutionFlowTreeElement) parentElement).getChildren();
		}
		return EMPTY_SET;
	}

	public Object getParent(Object element) {
		if (element instanceof ExecutionFlowTreeElement) {
			return ((ExecutionFlowTreeElement) element).getParent();
		}
		return null;
	}

	public boolean hasChildren(Object element) {
		if (element instanceof ExecutionFlowTreeElement) {
			return ((ExecutionFlowTreeElement) element).hasChildren();
		}
		return false;
	}

	public Object[] getElements(Object inputElement) {
		return getChildren(inputElement);
	}

	public void dispose() {
		// TODO Auto-generated method stub
	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// TODO Auto-generated method stub
	}
}
