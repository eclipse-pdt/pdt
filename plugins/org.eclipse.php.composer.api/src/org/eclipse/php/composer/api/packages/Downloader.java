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

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;

public class Downloader extends AbstractDownloader {

	private HttpGet httpGet;

	public Downloader() {
		super();
	}

	public Downloader(String url) {
		super(url);
	}

	public InputStream download() {
		HttpClientBuilder clientBuilder = HttpClientBuilder.create().useSystemProperties();
		try {
			SSLContextBuilder builder = new SSLContextBuilder();
			builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
			SSLConnectionSocketFactory ssf = new SSLConnectionSocketFactory(builder.build());
			Registry<ConnectionSocketFactory> r = RegistryBuilder.<ConnectionSocketFactory> create()
					.register("http", PlainConnectionSocketFactory.getSocketFactory()).register("https", ssf).build(); //$NON-NLS-1$ //$NON-NLS-2$
			clientBuilder.setConnectionManager(new BasicHttpClientConnectionManager(r));
		} catch (Exception e) {
			for (DownloadListenerInterface listener : listeners) {
				listener.errorOccured(e);
			}
		}

		HttpClient client = clientBuilder.build();
		InputStream content = null;

		try {

			httpGet = new HttpGet(url);
			HttpResponse response = null;

			if (httpGet.isAborted()) {
				httpGet.abort();

				if (httpGet.isAborted()) {
					return null;
				}
			}

			response = client.execute(httpGet);

			if (httpGet.isAborted()) {
				for (DownloadListenerInterface listener : listeners) {
					listener.aborted(url);
				}
				return null;
			}

			HttpEntity entity = response.getEntity();
			content = entity.getContent();

			for (DownloadListenerInterface listener : listeners) {
				listener.dataReceived(content, url);
			}

		} catch (Exception e) {
			for (DownloadListenerInterface listener : listeners) {
				listener.errorOccured(e);
			}
		}
		return content;
	}

	public void abort() {
		if (httpGet != null) {
			httpGet.abort();
		}
	}
}
