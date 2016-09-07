/*******************************************************************************
 * Copyright (c) 2012, 2016 PDT Extension Group and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.api.packages;

import org.eclipse.php.composer.api.ComposerConstants;

public class AsyncPharDownloader extends AsyncDownloadClient {

	public AsyncPharDownloader() {
		super();
		downloader.setUrl(ComposerConstants.PHAR_URL);
	}

	public void addDownloadListener(DownloadListenerInterface listener) {
		downloader.addDownloadListener(listener);
	}

	public void removeDownloadListener(DownloadListenerInterface listener) {
		downloader.removeDownloadListener(listener);
	}

	public void download() {
		downloader.download();
	}
}
