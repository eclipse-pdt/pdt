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
import org.eclipse.php.profile.ui.ProfilerUIConstants;

/**
 * Abstract table sorter.
 */
public abstract class AbstractTableSorter extends ViewerSorter {

	private int fColumn = 0;
	private int fOrder = ProfilerUIConstants.SORT_NONE;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.ViewerSorter#compare(org.eclipse.jface.viewers
	 * .Viewer, java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(Viewer viewer, Object e1, Object e2) {
		if (fOrder == ProfilerUIConstants.SORT_ASCENDING) {
			return super.compare(viewer, e1, e2);
		} else if (fOrder == ProfilerUIConstants.SORT_DESCENDING) {
			return super.compare(viewer, e2, e1);
		} else {
			return 0;
		}
	}

	public int compare(double d1, double d2) {
		if (fOrder == ProfilerUIConstants.SORT_ASCENDING) {
			return Double.compare(d1, d2);
		} else if (fOrder == ProfilerUIConstants.SORT_DESCENDING) {
			return Double.compare(d2, d1);
		}
		return 0;
	}

	/**
	 * Sets the column id to be sorted
	 * 
	 * @param column
	 */
	public void setColumn(int column) {
		if (this.fColumn == column) {
			fOrder = ++fOrder % 3;
		} else {
			fOrder = ProfilerUIConstants.SORT_ASCENDING;
		}
		this.fColumn = column;
	}

	/**
	 * Returns column id that is beign sorted currently
	 * 
	 * @return column
	 */
	public int getColumn() {
		return fColumn;
	}

	/**
	 * Sets the sorting order
	 * 
	 * @param order
	 */
	public void setOrder(int order) {
		fOrder = order;
	}

	/**
	 * Returns the the current sorting order
	 * 
	 * @return order
	 */
	public int getOrder() {
		return fOrder;
	}
}
