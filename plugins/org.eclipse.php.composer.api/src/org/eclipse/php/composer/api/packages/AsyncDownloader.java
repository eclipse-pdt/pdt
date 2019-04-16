/*******************************************************************************
 * Copyright (c) 2012, 2016, 2017, 2019 PDT Extension Group and others.
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

import java.net.URI;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

public class AsyncDownloader extends AbstractDownloader {

	public final static int TIMEOUT_SECONDS = 30;
	private int lastSlot = 1;
	private Log log = LogFactory.getLog(AsyncDownloader.class);
	private PoolingHttpClientConnectionManager connectionManager;
	private Map<Integer, Connection> connections = new HashMap<>();

	private class Connection implements Runnable {

		private String url;

		private Thread thread;

		public Connection(String url) {
			super();
			this.url = url;
		}

		public void closed() {
			abortListeners(url);
		}

		@Override
		public void run() {

			if (Thread.currentThread().isInterrupted()) {
				closed();
				return;
			}

			try {
				URI uri = URI.create(url);
				// https://bugs.eclipse.org/bugs/show_bug.cgi?id=546208
				//
				// Previous connection code didn't take care about proxy
				// authentication, even if SystemDefaultRoutePlanner would
				// "luckily" be able to find the right proxy.
				// All proxy authentication informations (and the manual
				// proxy definitions) are part of the proxy data settings stored
				// and managed by eclipse, and can be retrieved
				// by using IProxyService and a service tracker.
				// Also calling directly planner.determineRoute() won't take
				// care about all the proxy authentication handling,
				// see InternalHttpClient.doExecute().
				//
				// final HttpGet httpGet = new HttpGet(uri);
				// httpGet.addHeader("Accept", "*/*"); //$NON-NLS-1$
				// //$NON-NLS-2$
				// httpGet.addHeader("User-Agent", getClass().getName());
				// //$NON-NLS-1$
				// httpGet.addHeader("Host", uri.getHost()); //$NON-NLS-1$
				// HttpHost host = new HttpHost(uri.getHost(), uri.getPort(),
				// uri.getScheme());
				// SystemDefaultRoutePlanner planner = new
				// SystemDefaultRoutePlanner(ProxySelector.getDefault());
				// HttpClientContext context = HttpClientContext.create();
				// HttpRoute route = planner.determineRoute(host, httpGet,
				// context);

				HttpClientBuilder httpClientBuilder = ProxyHelper.createHttpClientBuilder(uri);

				httpClientBuilder.setUserAgent("org.eclipse.php.composer.api"); //$NON-NLS-1$
				httpClientBuilder.setConnectionManager(connectionManager);
				RequestConfig.Builder requestBuilder = RequestConfig.custom();
				requestBuilder = requestBuilder.setConnectTimeout(TIMEOUT_SECONDS * 1000)
						.setConnectionRequestTimeout(TIMEOUT_SECONDS * 1000).setSocketTimeout(TIMEOUT_SECONDS * 1000);
				httpClientBuilder.setDefaultRequestConfig(requestBuilder.build());

				CloseableHttpClient httpClient = httpClientBuilder.build();
				HttpHost target = new HttpHost(uri.getHost(), uri.getPort(), uri.getScheme());
				HttpGet httpGet = new HttpGet(uri);

				CloseableHttpResponse response = null;

				try {
					if (Thread.currentThread().isInterrupted()) {
						closed();
						return;
					}

					response = httpClient.execute(target, httpGet);

					if (Thread.currentThread().isInterrupted()) {
						httpGet.abort();
						closed();
						return;
					}

					if (response.getStatusLine().getStatusCode() >= 300) {
						throw new HttpResponseException(response.getStatusLine().getStatusCode(),
								response.getStatusLine().getReasonPhrase());
					}

					HttpEntity entity = response.getEntity();

					if (Thread.currentThread().isInterrupted()) {
						closed();
						return;
					}
					if (entity == null) {
						throw new ClientProtocolException("Response contains no content"); //$NON-NLS-1$

					}
					try {
						synchronized (AsyncDownloader.this) {
							for (DownloadListenerInterface listener : listeners) {
								try {
									if (Thread.currentThread().isInterrupted()) {
										closed();
										return;
									}

									listener.dataReceived(response.getEntity().getContent(),
											httpGet.getURI().toString());
								} catch (Exception e) {
									listener.errorOccured(e);
								}
							}
						}
					} finally {
						EntityUtils.consume(entity);
					}
				} finally {
					if (response != null) {
						response.close();
					}
					// Closing httpClient also shuts the
					// PoolingHttpClientConnectionManager "connectionManager"
					// down, don't do that.
					// httpClient.close();
				}

			} catch (Exception ex) {
				synchronized (AsyncDownloader.this) {
					for (DownloadListenerInterface listener : listeners) {
						listener.errorOccured(ex);
					}
				}
			}

		}

		public void abort() {
			synchronized (this) {
				if (thread != null && thread.isAlive()) {
					thread.interrupt();
				}
			}
		}

		public void start() {
			this.thread = new Thread(this);
			this.thread.start();
		}

	}

	public AsyncDownloader() {
		super();
	}

	public AsyncDownloader(String url) {
		super(url);
	}

	@Override
	protected void init() {
		super.init();

		try {
			SSLContextBuilder builder = new SSLContextBuilder();
			builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());

			SSLConnectionSocketFactory ssf = new SSLConnectionSocketFactory(builder.build());
			Registry<ConnectionSocketFactory> r = RegistryBuilder.<ConnectionSocketFactory> create()
					.register("http", PlainConnectionSocketFactory.getSocketFactory()).register("https", ssf).build(); //$NON-NLS-1$ //$NON-NLS-2$
			connectionManager = new PoolingHttpClientConnectionManager(r);
		} catch (NoSuchAlgorithmException e) {
			log.error("Exception during init", e); //$NON-NLS-1$
		} catch (KeyManagementException e) {
			log.error("Exception during init", e); //$NON-NLS-1$
		} catch (KeyStoreException e) {
			log.error("Exception during init", e); //$NON-NLS-1$
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
			int slot = ++this.lastSlot;

			Connection connection = new Connection(url);
			connection.start();
			connections.put(slot, connection);
			return slot;
		} catch (Exception e) {

			for (DownloadListenerInterface listener : listeners) {
				listener.errorOccured(e);
			}

		}

		return -1;
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
			Connection conn = connections.get(slot);
			if (conn != null) {
				conn.abort();
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	protected void abortListeners(String url) {
		synchronized (this) {
			for (DownloadListenerInterface listener : listeners) {
				listener.aborted(url);
			}
		}
	}

	/**
	 * Shuts down the download client
	 */
	public void shutdown() {
		if (connectionManager != null) {
			for (Connection conn : connections.values()) {
				if (conn != null) {
					conn.abort();
				}
			}
			connectionManager.shutdown();
		}
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		shutdown();
	}
}
