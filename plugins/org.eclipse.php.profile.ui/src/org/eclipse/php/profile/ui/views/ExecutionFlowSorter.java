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

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;

/**
 * Execution flow sorter.
 */
public class ExecutionFlowSorter extends ViewerSorter {

	public static int BY_ORDER = 0;
	public static int BY_DURATION_TIME = 2;

	private int fMode = BY_ORDER;

	public ExecutionFlowSorter() {
	}

	public ExecutionFlowSorter(int mode) {
		fMode = mode;
	}

	public void setMode(int mode) {
		fMode = mode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.ViewerSorter#compare(org.eclipse.jface.viewers
	 * .Viewer, java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(Viewer viewer, Object e1, Object e2) {
		ExecutionFlowTreeElement c1 = (ExecutionFlowTreeElement) e1;
		ExecutionFlowTreeElement c2 = (ExecutionFlowTreeElement) e2;

		if (fMode == BY_DURATION_TIME) {
			return Double.compare(c1.getDuration(), c2.getDuration());
		} else { // BY_ORDER
			return Double.compare(c1.getOrderID(), c2.getOrderID());
		}
	}
}
