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

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * Execution flow content provider.
 */
public class ExecutionFlowContentProvider implements ITreeContentProvider {

	private static final Object[] EMPTY_SET = new Object[0];

	@Override
	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof ExecutionFlowTreeElement) {
			return ((ExecutionFlowTreeElement) parentElement).getChildren();
		}
		return EMPTY_SET;
	}

	@Override
	public Object getParent(Object element) {
		if (element instanceof ExecutionFlowTreeElement) {
			return ((ExecutionFlowTreeElement) element).getParent();
		}
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		if (element instanceof ExecutionFlowTreeElement) {
			return ((ExecutionFlowTreeElement) element).hasChildren();
		}
		return false;
	}

	@Override
	public Object[] getElements(Object inputElement) {
		return getChildren(inputElement);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// TODO Auto-generated method stub
	}
}
