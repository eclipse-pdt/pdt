/*******************************************************************************
 * Copyright (c) 2012, 2016, 2017 PDT Extension Group and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *     Kaloyan Raev - [501269] externalize strings
 *******************************************************************************/
package org.eclipse.php.composer.api.packages;

import org.eclipse.php.composer.api.RepositoryPackage;

public class PackageDownloader extends DownloadClient {

	public PackageDownloader(String baseUrl) {
		super(baseUrl);
	}

	public RepositoryPackage loadPackage(String packageName) throws Exception {
		downloader.setUrl(createUrl(packageName));

		if (!downloader.getUrl().endsWith(".json")) { //$NON-NLS-1$
			downloader.setUrl(downloader.getUrl() + ".json"); //$NON-NLS-1$
		}

		return PackageHelper.getPackage(downloader.download());
	}

}
