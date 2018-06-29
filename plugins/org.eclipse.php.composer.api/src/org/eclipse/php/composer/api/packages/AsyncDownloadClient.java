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

public class AsyncDownloadClient extends AbstractDownloadClient {

	protected AsyncDownloader downloader = new AsyncDownloader();
	protected DownloadListenerInterface downloadListener = null;

	public AsyncDownloadClient() {

	}

	public AsyncDownloadClient(String baseUrl) {
		super(baseUrl);
	}

	public AsyncDownloadClient(String baseUrl, boolean baseUrlParamEncoding) {
		super(baseUrl, baseUrlParamEncoding);
	}

	public void abort() {
		downloader.abort();
	}

	public void abort(int slot) {
		downloader.abort(slot);
	}
}
