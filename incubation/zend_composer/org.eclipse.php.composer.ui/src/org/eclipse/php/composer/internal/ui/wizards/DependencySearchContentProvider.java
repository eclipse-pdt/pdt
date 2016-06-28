/*******************************************************************************
 * Copyright (c) 2014, 2016 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.internal.ui.wizards;

import java.io.IOException;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.viewers.ILazyContentProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.php.composer.core.IPackageSearchService;
import org.eclipse.php.composer.core.model.IPackage;

/**
 * @author Michal Niewrzal, 2014
 * 
 *         Content provider for search results viewer. Support virtual style in
 *         viewer.
 * 
 */
public class DependencySearchContentProvider implements
		IStructuredContentProvider, ILazyContentProvider {

	private IPackageSearchService searchService;
	private TableViewer tableViewer;
	private Object lastElement;

	public DependencySearchContentProvider(IPackageSearchService searchProvider) {
		this.searchService = searchProvider;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java
	 * .lang.Object)
	 */
	@Override
	public Object[] getElements(Object inputElement) {
		return getPackages().toArray();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface
	 * .viewers.Viewer, java.lang.Object, java.lang.Object)
	 */
	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		if (viewer instanceof TableViewer) {
			this.tableViewer = (TableViewer) viewer;
		} else {
			this.tableViewer = null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.ILazyContentProvider#updateElement(int)
	 */
	@Override
	public void updateElement(int index) {
		if (tableViewer == null || tableViewer.isBusy()
				|| getPackages().size() <= index) {
			return;
		}

		IPackage composerPackage = getPackages().get(index);
		tableViewer.replace(composerPackage, index);

		if (isLastElement(index, composerPackage)) {
			lastElement = composerPackage;
			notifyEndOfList();
		}
	}

	private boolean isLastElement(int index, IPackage composerPackage) {
		return index == (getPackages().size() - 1)
				&& !composerPackage.equals(lastElement);
	}

	/**
	 * Starts search process.
	 * 
	 * @param pattern
	 *            the pattern
	 * @param monitor
	 *            the monitor
	 * @throws IOException
	 */
	public void search(String pattern, IProgressMonitor monitor)
			throws IOException {
		searchService.reset();

		searchService.search(pattern, monitor);
	}

	public boolean hasNextPage() {
		return searchService.hasNextPage();
	}

	/**
	 * Looks for next page search results.
	 * 
	 * @param monitor
	 *            the monitor
	 * @throws IOException
	 */
	public void nextPage(IProgressMonitor monitor) throws IOException {
		searchService.nextPage(monitor);
	}

	/**
	 * Will be trigered when last item will be displayed. Should be implemented
	 * by client to be notified about end of list.
	 */
	protected void notifyEndOfList() {

	}

	public int getNumberOfElements() {
		return getPackages().size();
	}

	public List<IPackage> getPackages() {
		return searchService.getPackages();
	}

	public void setSearchService(IPackageSearchService searchService) {
		if (this.searchService != null) {
			this.lastElement = null;
			this.searchService.reset();
		}
		this.searchService = searchService;
	}

	@Override
	public void dispose() {
		searchService.dispose();
	}

}
