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
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.php.composer.core.internal.model.PackagistDetailedPackage;
import org.eclipse.php.composer.core.internal.model.adapters.ComposerJacksonMapper;
import org.eclipse.php.composer.core.model.IPackage;
import org.eclipse.php.composer.core.model.PackagistSearchRoot;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Michal Niewrzal, 2014
 * 
 */
public class PackagistSearchService implements IPackageSearchService {

	public static final String PACKAGIST_REPO = "https://packagist.org/"; //$NON-NLS-1$

	private static final String SEARCH_PATTERN = PACKAGIST_REPO + "search.json?q=%s"; //$NON-NLS-1$

	private static final String DETAILS_PATTERN = PACKAGIST_REPO + "packages/%s.json"; //$NON-NLS-1$

	private List<IPackage> packages;

	private String nextUrl;

	public PackagistSearchService() {
		this.packages = Collections.synchronizedList(new ArrayList<IPackage>(512));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.zend.php.composer.core.IPackageSearchProvider#search(java.lang.String
	 * , org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public void search(String pattern, IProgressMonitor monitor) throws IOException {
		reset();

		if (pattern == null || pattern.isEmpty() || monitor.isCanceled()) {
			return;
		}

		String searchUrl = String.format(SEARCH_PATTERN, URLEncoder.encode(pattern, "UTF-8")); //$NON-NLS-1$

		updateResults(searchUrl);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.zend.php.composer.core.IPackageSearchProvider#nextPage(org.eclipse
	 * .core.runtime.IProgressMonitor)
	 */
	@Override
	public void nextPage(IProgressMonitor monitor) throws IOException {
		updateResults(nextUrl);
	}

	private PackagistSearchRoot getPackagesRoot(String packagesUrl) throws MalformedURLException, IOException {
		if (packagesUrl == null) {
			return null;
		}

		String input = getUrlStream(packagesUrl);
		if (input == null) {
			return null;
		}
		PackagistSearchRoot packagesRoot = ComposerJacksonMapper.getMapper().readValue(input,
				PackagistSearchRoot.class);
		return packagesRoot;
	}

	private void updateResults(String url) throws MalformedURLException, IOException {
		PackagistSearchRoot packagesRoot = getPackagesRoot(url);
		if (packagesRoot == null) {
			return;
		}

		packages.addAll(packagesRoot.getResults());
		nextUrl = packagesRoot.getNext();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zend.php.composer.core.IPackageSearchProvider#hasNextPage()
	 */
	@Override
	public boolean hasNextPage() {
		return nextUrl != null;
	}

	@Override
	public IPackage getPackageDetails(String name, IProgressMonitor monitor) throws IOException {
		String url = String.format(DETAILS_PATTERN, name);

		String input = getUrlStream(url);
		if (input == null) {
			return null;
		}
		ObjectMapper objectMapper = ComposerJacksonMapper.getMapper();
		PackagistDetailedPackage detailedPackage = null;
		try {
			detailedPackage = objectMapper.readValue(input, PackagistDetailedPackage.class);
		} catch (JsonParseException e) {
			detailedPackage = new PackagistDetailedPackage();
		}
		return detailedPackage;
	}

	private String getUrlStream(String url) throws IOException {
		return HttpHelper.executeGetRequest(url, null, null, 200);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zend.php.composer.core.IPackageSearchProvider#reset()
	 */
	@Override
	public void reset() {
		packages.clear();
		nextUrl = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zend.php.composer.core.IPackageSearchProvider#getPackages()
	 */
	@Override
	public List<IPackage> getPackages() {
		return packages;
	}

	@Override
	public void dispose() {
		packages.clear();
	}

}
