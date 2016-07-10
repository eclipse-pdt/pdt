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

import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.nio.reactor.IOReactorException;
import org.eclipse.php.composer.api.httpclient.AsyncClientInterface;
import org.eclipse.php.composer.api.httpclient.DefaultHttpAsyncClient;
import org.eclipse.php.composer.api.httpclient.FutureCallback;

public class AsyncDownloader extends AbstractDownloader {

	private List<HttpGet> httpGets;
	private AsyncClientInterface client;
	private int lastSlot;
	private Log log = LogFactory.getLog(AsyncDownloader.class);

	public AsyncDownloader() {
		super();
	}

	public AsyncDownloader(String url) {
		super(url);
	}

	protected void init() {
		super.init();
		httpGets = new ArrayList<HttpGet>();

		// start http async client
		try {
			client = new DefaultHttpAsyncClient();
			client.start();
			HttpClientParams.setRedirecting(client.getParams(), false);
		} catch (IOReactorException e) {
			log.error(e.getMessage());
		}
	}

	/**
	 * Starts the async download. The returned number is the internal slot for
	 * this download transfer, which can be used as parameter in abort to stop
	 * this specific transfer.
	 * 
	 * @return slot
	 */
	public int download() {
		try {
			// if (url.startsWith("https://packagist.org") ||
			// url.startsWith("https://getcomposer.org")) {
			if (url.startsWith("https://")) {
				registerSSLContext(client.getBackend());
			}

			final HttpGet httpGet = new HttpGet(url);

			if (httpGet.isAborted()) {
				httpGet.abort();
			}

			client.execute(httpGet, new FutureCallback<HttpResponse>() {

				public void failed(Exception e) {
					for (DownloadListenerInterface listener : listeners) {
						listener.errorOccured(e);
					}
				}

				public void completed(HttpResponse response) {
					for (DownloadListenerInterface listener : listeners) {
						try {
							listener.dataReceived(response.getEntity().getContent(), httpGet.getURI().toString());
						} catch (Exception e) {
							listener.errorOccured(e);
						}
					}
				}

				public void cancelled() {
					for (DownloadListenerInterface listener : listeners) {
						listener.aborted(httpGet.getURI().toString());
					}
				}
			});

			httpGets.add(httpGet);
			// client.shutdown();

			lastSlot = httpGets.size() - 1;

			return lastSlot;

		} catch (Exception e) {
			for (DownloadListenerInterface listener : listeners) {
				listener.errorOccured(e);
			}
		}

		return -1;
	}

	private void registerSSLContext(HttpClient client) throws Exception {

		X509TrustManager tm = new ComposerTrustManager();
		SSLContext ctx = SSLContext.getInstance("TLS");
		ctx.init(null, new TrustManager[] { tm }, null);
		SSLSocketFactory ssf = new SSLSocketFactory(ctx, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		ClientConnectionManager ccm = client.getConnectionManager();
		SchemeRegistry sr = ccm.getSchemeRegistry();
		sr.register(new Scheme("https", 443, ssf));

		// implementation for HttpAsyncClient
		/*
		 * X509TrustManager tm = new ComposerTrustManager(); SSLContext ctx =
		 * SSLContext.getInstance("TLS"); ctx.init(null, new TrustManager[]{tm},
		 * null); SSLLayeringStrategy sls = new SSLLayeringStrategy(ctx,
		 * SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		 * ClientAsyncConnectionManager ccm = client.getConnectionManager();
		 * AsyncSchemeRegistry sr = ccm.getSchemeRegistry(); sr.register(new
		 * AsyncScheme("https", 443, sls));
		 */
	}

	/**
	 * Aborts the last transfer
	 */
	public void abort() {
		abort(lastSlot);
	}

	/**
	 * Aborts a transfer at the given slot
	 * 
	 * @param slot
	 */
	public void abort(int slot) {
		try {
			HttpGet httpGet = httpGets.get(slot);
			httpGet.abort();
			abortListeners(httpGet.getURI().toString());
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	protected void abortListeners(String url) {
		for (DownloadListenerInterface listener : listeners) {
			listener.aborted(url);
		}
	}

	/**
	 * Shuts down the download client
	 */
	public void shutdown() {
		try {
			client.shutdown();
		} catch (InterruptedException e) {
			log.error(e.getMessage());
		}
	}
}
