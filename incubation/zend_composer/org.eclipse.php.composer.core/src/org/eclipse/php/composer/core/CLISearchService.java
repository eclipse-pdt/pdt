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
package org.eclipse.php.composer.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.php.composer.core.model.IPackage;

/**
 * @author Michal Niewrzal, 2014
 * 
 */
public class CLISearchService implements IPackageSearchService {

	private ComposerService composerService;
	private List<IPackage> packages;

	private int page = 1;
	private String pattern;
	private boolean hasNextPage = true;

	public CLISearchService(ComposerService composerService) {
		this.composerService = composerService;
		this.packages = Collections.synchronizedList(new ArrayList<IPackage>(
				512));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.zend.php.composer.core.IPackageSearchProvider#search(java.lang.String
	 * , int, org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public void search(String pattern, IProgressMonitor monitor)
			throws IOException {
		reset();

		if (pattern == null || pattern.isEmpty() || monitor.isCanceled()) {
			return;
		}

		this.pattern = pattern;

		List<IPackage> newPackages = composerService.search(pattern, page,
				monitor);

		if (composerService.getError() != null) {
			throw new IOException(composerService.getError());
		}

		packages.addAll(newPackages);
		page++;
	}

	@Override
	public void nextPage(IProgressMonitor monitor) throws IOException {
		if (!hasNextPage || monitor.isCanceled()) {
			return;
		}

		List<IPackage> newPackages = composerService.search(pattern, page,
				monitor);

		if (composerService.getError() != null) {
			throw new IOException(composerService.getError());
		}

		hasNextPage = shouldLookForNextPage(newPackages);
		if (hasNextPage) {
			packages.addAll(newPackages);
			page++;
		}
	}

	@Override
	public IPackage getPackageDetails(String name, IProgressMonitor monitor)
			throws IOException {
		IPackage p = composerService.show(name, monitor);
		if (composerService.getError() != null) {
			throw new IOException(composerService.getError());
		}
		return p;
	}

	@Override
	public boolean hasNextPage() {
		return hasNextPage;
	}

	private boolean shouldLookForNextPage(List<IPackage> searchedPackages) {
		if (searchedPackages == null || searchedPackages.isEmpty()
				|| packages.isEmpty()) {
			return false;
		}
		IPackage mainListLast = packages.get(packages.size() - 1);
		IPackage newListLast = searchedPackages
				.get(searchedPackages.size() - 1);
		return !mainListLast.equals(newListLast);
	}

	@Override
	public List<IPackage> getPackages() {
		return packages;
	}

	public void reset() {
		page = 1;
		pattern = null;
		hasNextPage = true;
		packages.clear();
	}

	@Override
	public void dispose() {
		reset();
	}

}
