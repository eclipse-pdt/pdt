/*******************************************************************************
 * Copyright (c) 2012, 2016, 2017 PDT Extension Group and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *     Kaloyan Raev - [501269] externalize strings
 *******************************************************************************/
package org.eclipse.php.composer.api.packages;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AsyncPackageSearch extends AsyncDownloadClient {

	private int pageLimit = 3;
	protected List<PackageSearchListenerInterface> listeners = new ArrayList<PackageSearchListenerInterface>();
	private Map<String, Integer> counters = new HashMap<String, Integer>();
	private Map<String, Boolean> aborts = new HashMap<String, Boolean>();
	private Log log = LogFactory.getLog(AsyncPackageSearch.class);

	public AsyncPackageSearch() {
		super();
		init();
	}

	public AsyncPackageSearch(String baseUrl) {
		super(baseUrl, true);
		init();
	}

	private void init() {
		downloader.addDownloadListener(new DownloadListenerAdapater() {
			private String getQuery(String url) {
				try {
					return URLDecoder.decode(url.replaceFirst(".+q=([^?&]+).*", "$1"), StandardCharsets.UTF_8.name()); //$NON-NLS-1$ //$NON-NLS-2$
				} catch (UnsupportedEncodingException e) {
					log.error(e);
				}

				return null;
			}

			public void dataReceived(InputStream content, String url) {
				try {
					// parse query from url
					String query = getQuery(url);

					if (isAborted(query)) {
						return;
					}

					SearchResult result = PackageHelper.getSearchResult(content);

					int counter = getCounter(query);

					if (result != null && result.results != null) {
						for (PackageSearchListenerInterface listener : listeners) {
							listener.packagesFound(result.results, query, result);
						}
					}

					if (result != null && result.next != null && result.next.length() > 0 && counter < pageLimit) {
						downloader.setUrl(result.next);
						downloader.download();
						counters.put(query, counter + 1);
					}
				} catch (Exception e) {
					for (PackageSearchListenerInterface listener : listeners) {
						listener.errorOccured(e);
					}
				}
			}

			public void aborted(String url) {
				aborts.put(getQuery(url), true);
				for (PackageSearchListenerInterface listener : listeners) {
					listener.aborted(url);
				}
			}
		});
	}

	public void addPackageSearchListener(PackageSearchListenerInterface listener) {
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	public void removePackageSearchListener(PackageSearchListenerInterface listener) {
		listeners.remove(listener);
	}

	private int getCounter(String query) {
		if (!counters.containsKey(query)) {
			counters.put(query, 1);
		}
		return counters.get(query);
	}

	private boolean isAborted(String query) {
		if (!aborts.containsKey(query)) {
			aborts.put(query, false);
		}

		return aborts.get(query);
	}

	public int search(String query) {
		// reset counter + abort state
		counters.put(query, 1);
		aborts.put(query, false);

		downloader.setUrl(createUrl(query));
		return downloader.download();
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
