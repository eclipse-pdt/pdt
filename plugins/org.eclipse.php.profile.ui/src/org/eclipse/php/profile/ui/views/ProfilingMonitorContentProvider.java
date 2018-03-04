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
 * Profiler monitor content provider.
 */
public class ProfilingMonitorContentProvider implements ITreeContentProvider {

	private static final Object[] EMPTY_SET = new Object[0];

	@Override
	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof ProfilingMonitorElement[]) {
			return (Object[]) parentElement;
		}
		if (parentElement instanceof ProfilingMonitorElement) {
			return ((ProfilingMonitorElement) parentElement).getChildren();
		}
		return EMPTY_SET;
	}

	@Override
	public Object getParent(Object element) {
		if (element instanceof ProfilingMonitorViewElement) {
			return ((ProfilingMonitorViewElement) element).getParent();
		}
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		if (element instanceof ProfilingMonitorElement || element instanceof ProfilingMonitorElement[]) {
			return true;
		}
		return false;
	}

	@Override
	public Object[] getElements(Object inputElement) {
		return getChildren(inputElement);
	}

	@Override
	public void dispose() {
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}
}
