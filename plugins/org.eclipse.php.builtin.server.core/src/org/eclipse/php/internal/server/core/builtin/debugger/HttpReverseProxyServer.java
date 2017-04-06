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
package org.eclipse.php.internal.server.core.builtin.debugger;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.http.ConnectionClosedException;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpServerConnection;
import org.apache.http.impl.DefaultBHttpServerConnection;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpProcessor;
import org.apache.http.protocol.HttpRequestHandler;
import org.apache.http.protocol.HttpService;
import org.apache.http.protocol.ImmutableHttpProcessor;
import org.apache.http.protocol.RequestConnControl;
import org.apache.http.protocol.RequestContent;
import org.apache.http.protocol.RequestExpectContinue;
import org.apache.http.protocol.RequestTargetHost;
import org.apache.http.protocol.RequestUserAgent;
import org.apache.http.protocol.UriHttpRequestHandlerMapper;

@SuppressWarnings("restriction")
public class HttpReverseProxyServer {

	private static final String HTTP_CONN_KEEPALIVE = "http.proxy.conn-keepalive"; //$NON-NLS-1$
	private static final String HTTP_IN_CONN = "HTTP_IN_CONN"; //$NON-NLS-1$

	private RequestListenerThread fThread;
	private IHttpRequestHandler fHandler;

	public HttpReverseProxyServer(IHttpRequestHandler handler) {
		fHandler = handler;
	}

	public void start(int port) throws Exception {
		fThread = new RequestListenerThread(port);
		fThread.setHttpRequestHandler(fHandler);
		fThread.setDaemon(false);
		fThread.start();
	}

	public void stop() {
		try {
			if (fThread != null) {
				fThread.stopServer();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public interface ConnectionClosedHandler {

		public void notifyConnectionClosed(HttpServerConnection connection);

	}

	public interface IHttpRequestHandler {

		public void handle(final HttpRequest request, final HttpResponse response, final HttpContext context)
				throws HttpException, IOException;

		public void close(HttpServerConnection connection) throws IOException;

	}

	static class ProxyHandler implements HttpRequestHandler, ConnectionClosedHandler {

		private IHttpRequestHandler fHttpRequestHandler;

		@Override
		public void handle(final HttpRequest request, final HttpResponse response, final HttpContext context)
				throws HttpException, IOException {
			if (fHttpRequestHandler != null) {
				fHttpRequestHandler.handle(request, response, context);
			}
		}

		public void setHttpRequestHandler(IHttpRequestHandler requestHandler) {
			fHttpRequestHandler = requestHandler;
		}

		@Override
		public void notifyConnectionClosed(HttpServerConnection connection) {
			try {
				if (fHttpRequestHandler != null) {
					fHttpRequestHandler.close(connection);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	static class RequestListenerThread extends Thread {

		private ServerSocket serversocket;
		private HttpService httpService;
		private ProxyHandler handler;
		private boolean isRunning = false;

		public RequestListenerThread(final int port) throws IOException {
			setName("PHP Debugger Proxy Server"); //$NON-NLS-1$
			this.serversocket = new ServerSocket(port);

			final HttpProcessor inhttpproc = new ImmutableHttpProcessor(new HttpRequestInterceptor[] {
					new RequestContent(), new RequestTargetHost(), new RequestConnControl(),
					new RequestUserAgent("PHP Debugger Proxy Server/1.1"), new RequestExpectContinue(true) }); //$NON-NLS-1$
			handler = new ProxyHandler();
			final UriHttpRequestHandlerMapper reqistry = new UriHttpRequestHandlerMapper();
			reqistry.register("*", handler); //$NON-NLS-1$

			this.httpService = new HttpService(inhttpproc, reqistry);
		}

		public void setHttpRequestHandler(IHttpRequestHandler requestHandler) {
			handler.setHttpRequestHandler(requestHandler);
		}

		@Override
		public void run() {
			isRunning = true;
			while (isRunning) {
				try {

					final int bufsize = 512 * 1024;
					// Set up incoming HTTP connection
					final Socket insocket = this.serversocket.accept();
					final DefaultBHttpServerConnection inconn = new DefaultBHttpServerConnection(bufsize);
					inconn.bind(insocket);

					// Start worker thread
					final Thread t = new ProxyThread(this.httpService, inconn, handler);
					t.setDaemon(true);
					t.start();
				} catch (final InterruptedIOException ex) {
					break;
				} catch (final IOException e) {
					break;
				}
			}
		}

		public void stopServer() throws IOException {
			if (serversocket != null) {
				isRunning = false;
				serversocket.close();
				serversocket = null;
			}
		}
	}

	static class ProxyThread extends Thread {

		private final HttpService httpservice;
		private final HttpServerConnection inconn;
		private final ProxyHandler handler;

		public ProxyThread(final HttpService httpservice, final HttpServerConnection inconn,
				final ProxyHandler handler) {
			super();
			this.httpservice = httpservice;
			this.inconn = inconn;
			this.handler = handler;
		}

		@Override
		public void run() {
			final HttpContext context = new BasicHttpContext(null);

			try {
				while (!Thread.interrupted()) {
					if (!this.inconn.isOpen()) {
						handler.notifyConnectionClosed(inconn);
						break;
					}
					context.setAttribute(HTTP_IN_CONN, this.inconn);
					this.httpservice.handleRequest(this.inconn, context);

					context.setAttribute(HTTP_CONN_KEEPALIVE, true);
					// final Boolean keepalive = (Boolean)
					// context.getAttribute(HTTP_CONN_KEEPALIVE);
					// if (!Boolean.TRUE.equals(keepalive)) {
					// handler.notifyConnectionClosed(inconn);
					// this.inconn.close();
					// break;
					// }
				}
			} catch (final ConnectionClosedException ex) {
			} catch (final IOException ex) {
			} catch (final HttpException ex) {
			} finally {
				handler.notifyConnectionClosed(inconn);
				try {
					this.inconn.shutdown();
				} catch (final IOException ignore) {
				}
			}
		}
	}

}
