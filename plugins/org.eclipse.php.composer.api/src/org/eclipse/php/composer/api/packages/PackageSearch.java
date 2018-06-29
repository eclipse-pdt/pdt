/*******************************************************************************
 * Copyright (c) 2012, 2016 PDT Extension Group and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.api.packages;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.php.composer.api.MinimalPackage;

public class PackageSearch extends DownloadClient {

	private int pageLimit = 3;

	public PackageSearch(String baseUrl) {
		super(baseUrl, true);
	}

	public List<MinimalPackage> search(String query) throws Exception {
		List<MinimalPackage> packages = new LinkedList<>();
		SearchResult result = loadPackages(createUrl(query));

		if (result != null && result.results != null) {
			packages.addAll(result.results);
		}

		int current = 1;

		while (current < pageLimit && result != null && result.next != null && result.next.length() > 0) {
			result = loadPackages(result.next);

			if (result.results != null && result.results.size() > 0) {
				packages.addAll(result.results);
			}
			current++;
		}

		return packages;
	}

	private SearchResult loadPackages(String url) throws Exception {
		downloader.setUrl(url);
		return PackageHelper.getSearchResult(downloader.download());
	}

	/**
	 * @return the pageLimit
	 */
	public int getPageLimit() {
		return pageLimit;
	}

	/**
	 * @param pageLimit
	 *            the pageLimit to set
	 */
	public void setPageLimit(int pageLimit) {
		this.pageLimit = pageLimit;
	}
}
