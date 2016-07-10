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

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.nio.reactor.IOReactorException;
import org.apache.http.params.HttpParams;

/**
 * Used as an adapter for HttpAsyncClient until it becomes available
 * as a stable version in eclipse orbit updatesite. 
 * 
 * @see http://hc.apache.org/httpcomponents-asyncclient-dev/httpasyncclient/apidocs/overview-summary.html
 * @author Robert Gruendler <r.gruendler@gmail.com>
 */
public interface AsyncClientInterface {

	void start() throws IOReactorException;

	HttpParams getParams();

	void execute(HttpGet httpGet, FutureCallback<HttpResponse> futureCallback);

	void shutdown() throws InterruptedException;
	
	HttpClient getBackend();

}