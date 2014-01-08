/*******************************************************************************
 * Copyright (c) 2014 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.workingset;

import org.eclipse.dltk.internal.ui.workingsets.WorkingSetFilterActionGroup;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.IWorkingSet;

/**
 * Overrides the DLTK working set filter action group for using the
 * {@link PHPWorkingSetFilter} filter instead of the DLTK working set filter.
 * 
 * @author Kaloyan Raev
 */
public class PHPWorkingSetFilterActionGroup extends WorkingSetFilterActionGroup {

	private final PHPWorkingSetFilter fWorkingSetFilter;

	public PHPWorkingSetFilterActionGroup(IWorkbenchPartSite site,
			IPropertyChangeListener changeListener) {
		super(site, changeListener);
		fWorkingSetFilter = new PHPWorkingSetFilter();
	}

	public PHPWorkingSetFilterActionGroup(Shell shell, IWorkbenchPage page,
			IPropertyChangeListener changeListener) {
		super(shell, page, changeListener);
		fWorkingSetFilter = new PHPWorkingSetFilter();
	}

	@Override
	public boolean isFiltered(Object parent, Object object) {
		if (fWorkingSetFilter == null)
			return false;
		return !fWorkingSetFilter.select(null, parent, object);
	}

	@Override
	public void setWorkingSet(IWorkingSet workingSet, boolean refreshViewer) {
		fWorkingSetFilter.setWorkingSet(workingSet);
		super.setWorkingSet(workingSet, refreshViewer);
	}

	@Override
	public ViewerFilter getWorkingSetFilter() {
		return fWorkingSetFilter;
	}

}
