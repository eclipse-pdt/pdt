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
