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

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.php.composer.api.RepositoryPackage;

public class AsyncPackageDownloader extends AsyncDownloadClient {

	private List<PackageListenerInterface> pkgListeners = new ArrayList<>();

	public AsyncPackageDownloader(String baseUrl) {
		super(baseUrl);
	}

	public void addPackageListener(PackageListenerInterface listener) {
		if (!pkgListeners.contains(listener)) {
			pkgListeners.add(listener);
		}
	}

	public void removePackageListener(PackageListenerInterface listener) {
		pkgListeners.remove(listener);
	}

	protected void notifyPackageListener(Object obj) {
		for (PackageListenerInterface listener : pkgListeners) {
			if (obj instanceof RepositoryPackage) {
				listener.packageLoaded((RepositoryPackage) obj);
			} else if (obj instanceof Exception) {
				listener.errorOccured((Exception) obj);
			}
		}
	}

	public int loadPackage(String packageName) {
		downloader.setUrl(createUrl(packageName));
		if (downloadListener == null) {
			downloadListener = new DownloadListenerAdapater() {
				@Override
				public void dataReceived(InputStream content, String url) {
					try {
						notifyPackageListener(PackageHelper.getPackage(content));
					} catch (Exception e) {
						notifyPackageListener(e);
					}
				}

				@Override
				public void errorOccured(Exception e) {
					notifyPackageListener(e);
				}
			};
			downloader.addDownloadListener(downloadListener);
		}

		return downloader.download();
	}
}
