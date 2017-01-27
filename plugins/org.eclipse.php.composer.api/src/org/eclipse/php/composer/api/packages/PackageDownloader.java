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
