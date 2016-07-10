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
package org.eclipse.php.composer.api.httpclient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.SystemDefaultHttpClient;
import org.apache.http.nio.reactor.IOReactorException;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

/**
 * Used as an adapter for HttpAsyncClient until it becomes available as a stable
 * version in eclipse orbit updatesite.
 * 
 * @see http://hc.apache.org/httpcomponents-asyncclient-dev/httpasyncclient/apidocs/overview-summary.html
 * @author Robert Gruendler <r.gruendler@gmail.com>
 */
public class HttpAsyncClient implements AsyncClientInterface {

	private HttpClient client;
	private HttpParams params;
	private Log log = LogFactory.getLog(HttpAsyncClient.class);
	private GetThread thread;

	public void start() throws IOReactorException {
		client = new SystemDefaultHttpClient();
		params = new BasicHttpParams();
	}

	public HttpParams getParams() {
		return params;
	}

	public void execute(HttpGet httpGet, FutureCallback<HttpResponse> futureCallback) {

		try {
			if (httpGet == null) {
				throw new RuntimeException("Cannot issue GET request with HttpGet object");
			}
			log.info("Executing GET request to " + httpGet.getURI().toString());
			thread = new GetThread(client, httpGet, futureCallback);
			thread.start();
		} catch (Exception e) {
			log.error(e.getMessage());
			futureCallback.failed(e);
		}
	}

	public void shutdown() throws InterruptedException {
		if (client != null) {
			client.getConnectionManager().shutdown();
		}
	}

	static class GetThread extends Thread {

		private final HttpClient httpClient;
		private final HttpContext context;
		private final HttpGet httpget;
		private FutureCallback<HttpResponse> callback;

		public GetThread(HttpClient httpClient, HttpGet httpget, FutureCallback<HttpResponse> futureCallback) {
			this.httpClient = httpClient;
			this.callback = futureCallback;
			this.context = new BasicHttpContext();
			this.httpget = httpget;
		}

		@Override
		public void interrupt() {
			super.interrupt();
			if (callback != null) {
				callback.cancelled();
			}
		}

		@Override
		public void run() {
			try {
				HttpResponse response = httpClient.execute(httpget, context);
				if (callback != null) {
					callback.completed(response);
				}
			} catch (Exception e) {
				if (callback != null) {
					callback.failed(e);
				}
				httpget.abort();
			}
		}
	}

	public HttpClient getBackend() {
		return client;
	}
}
