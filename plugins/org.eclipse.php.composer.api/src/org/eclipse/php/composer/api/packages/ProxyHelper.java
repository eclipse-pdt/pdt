/*******************************************************************************
 * Copyright (c) 2019 The Eclipse Foundation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Yatta Solutions - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.api.packages;

import java.net.URI;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.eclipse.core.net.proxy.IProxyData;
import org.eclipse.core.net.proxy.IProxyService;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

/**
 * See also classes org.eclipse.epp.internal.mpc.core.util.HttpUtil and
 * org.eclipse.epp.internal.mpc.core.util.ProxyHelper from Marketplace Client
 * Project (epp.mpc).
 * 
 * @author Carsten Reckord
 * @author Thiery Blind
 * 
 * @see https://github.com/eclipse/epp.mpc/blob/master/org.eclipse.epp.mpc.core/src/org/eclipse/epp/internal/mpc/core/util/HttpUtil.java
 * @see https://github.com/eclipse/epp.mpc/blob/master/org.eclipse.epp.mpc.core/src/org/eclipse/epp/internal/mpc/core/util/ProxyHelper.java
 */
public class ProxyHelper {
	@SuppressWarnings("rawtypes")
	private static ServiceTracker proxyServiceTracker;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static synchronized void acquireProxyService(BundleContext context) {
		if (proxyServiceTracker == null) {
			proxyServiceTracker = new ServiceTracker(context, IProxyService.class.getName(), null);
			proxyServiceTracker.open();
		}
	}

	public static synchronized void releaseProxyService() {
		if (proxyServiceTracker != null) {
			proxyServiceTracker.close();
			proxyServiceTracker = null;
		}
	}

	public static synchronized IProxyService getProxyService() {
		return proxyServiceTracker == null ? null : (IProxyService) proxyServiceTracker.getService();
	}

	public static IProxyData getProxyData(URI uri) {
		final IProxyService proxyService = getProxyService();
		if (proxyService != null) {
			return doGetProxyData(proxyService, uri);
		}
		return null;
	}

	private static IProxyData doGetProxyData(final IProxyService proxyService, URI uri) {
		if (uri.getHost() == null || uri.getScheme() == null) {
			return null;
		}
		final IProxyData[] proxyData = proxyService.select(uri);
		if (proxyData == null) {
			return null;
		}
		for (IProxyData pd : proxyData) {
			if (pd != null && pd.getHost() != null) {
				return pd;
			}
		}
		return null;
	}

	public static HttpClientBuilder createHttpClientBuilder(URI baseUri) {
		HttpClientBuilder hcBuilder = HttpClients.custom();

		if (baseUri != null) {
			configureProxy(hcBuilder, baseUri);
		}

		return hcBuilder;
	}

	public static void configureProxy(HttpClientBuilder hcBuilder, URI uri) {
		final IProxyData proxyData = getProxyData(uri);
		if (proxyData != null && !IProxyData.SOCKS_PROXY_TYPE.equals(proxyData.getType())) {
			HttpHost proxy = new HttpHost(proxyData.getHost(), proxyData.getPort());
			DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);
			hcBuilder.setRoutePlanner(routePlanner);
			if (proxyData.isRequiresAuthentication()) {
				CredentialsProvider provider = new BasicCredentialsProvider();
				provider.setCredentials(new AuthScope(proxyData.getHost(), proxyData.getPort()),
						new UsernamePasswordCredentials(proxyData.getUserId(), proxyData.getPassword()));
				hcBuilder.setDefaultCredentialsProvider(provider);
			}
		}
	}
}
