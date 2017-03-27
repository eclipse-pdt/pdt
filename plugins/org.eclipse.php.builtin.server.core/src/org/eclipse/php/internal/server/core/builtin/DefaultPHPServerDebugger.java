/*******************************************************************************
 * Copyright (c) 2017 Alex Xu and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Alex Xu - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.server.core.builtin;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpServerConnection;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHttpRequest;
import org.apache.http.protocol.HttpContext;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.php.debug.core.debugger.parameters.IDebugParametersInitializer;
import org.eclipse.php.internal.debug.core.launching.PHPLaunchUtilities;
import org.eclipse.php.internal.debug.core.zend.debugger.DebugParametersInitializersRegistry;
import org.eclipse.php.internal.server.core.builtin.debugger.HttpReverseProxyServer;
import org.eclipse.php.internal.server.core.builtin.debugger.HttpReverseProxyServer.IHttpRequestHandler;
import org.eclipse.php.internal.server.core.builtin.debugger.PHPServerDebugTarget;

@SuppressWarnings("restriction")
public class DefaultPHPServerDebugger extends DefaultPHPServerRunner {

	private HttpReverseProxyServer proxyServer;

	@Override
	public void run(PHPServerRunnerConfiguration configuration, ILaunch launch, IProgressMonitor monitor)
			throws CoreException {
		super.run(configuration, launch, monitor);

		IDebugTarget target = new PHPServerDebugTarget(launch, launch.getProcesses()[0]);
		launch.addDebugTarget(target);

		IDebugParametersInitializer parametersInitializer = DebugParametersInitializersRegistry
				.getBestMatchDebugParametersInitializer(launch);
		String query = PHPLaunchUtilities.generateQuery(launch, parametersInitializer);

		proxyServer = new HttpReverseProxyServer(new HttpRequestHandler(getServerPort(), query));
		try {
			proxyServer.start(configuration.getPort());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public int getServerPort() {
		return super.getServerPort() + 1;
	}

	class HttpRequestHandler implements IHttpRequestHandler {

		private int fPort;
		private String fDebugQuery;

		HttpRequestHandler(int port, String debugQuery) {
			fPort = port;
			fDebugQuery = debugQuery;
		}

		@Override
		public void handle(HttpRequest request, HttpResponse response, HttpContext context)
				throws HttpException, IOException {
			HttpClient client = HttpClientBuilder.create().build();
			HttpResponse response1 = client.execute(new HttpHost("localhost", fPort), createHttpRequest(request)); //$NON-NLS-1$
			response.setEntity(response1.getEntity());
			response.setStatusCode(response1.getStatusLine().getStatusCode());
		}

		private HttpRequest createHttpRequest(HttpRequest request) throws UnsupportedEncodingException {
			String uri = request.getRequestLine().getUri();
			if (uri.indexOf('?') == -1) {
				uri = uri + '?' + fDebugQuery;
			} else {
				uri = uri + '&' + fDebugQuery;
			}
			HttpRequest newRequest = new BasicHttpRequest(request.getRequestLine().getMethod(), uri,
					request.getProtocolVersion());
			newRequest.setHeaders(request.getAllHeaders());
			newRequest.setParams(request.getParams());
			return newRequest;
		}

		@Override
		public void close(HttpServerConnection connection) throws IOException {
			if (connection != null) {
				connection.close();
			}
		}
	}

	@Override
	public void stop() {
		if (proxyServer != null) {
			proxyServer.stop();
		}
	}

}
