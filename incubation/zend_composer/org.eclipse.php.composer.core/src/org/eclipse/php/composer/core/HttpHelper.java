/*******************************************************************************
 * Copyright (c) 2013, 2016 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.core;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Set;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;
import org.eclipse.core.net.proxy.IProxyData;
import org.eclipse.core.net.proxy.IProxyService;

/**
 * @author Wojciech Galanciak, 2013
 * 
 */
public class HttpHelper {

	public static String executeGetRequest(String url,
			Map<String, String> parameters, Map<String, String> cookies,
			int expectedCode) {
		try {
			HttpClient client = createHttpClient(url,
					ComposerCorePlugin.getProxyService());
			HttpMethodBase method = createGetRequest(url, parameters);
			setCookies(method, cookies);
			if (method != null) {
				int statusCode = -1;
				try {
					statusCode = client.executeMethod(method);
					if (statusCode == expectedCode) {
						String responseContent = new String(
								method.getResponseBody());
						return responseContent;
					}
				} finally {
					method.releaseConnection();
				}
			}
		} catch (IOException e) {
			ComposerCorePlugin.logError(e);
		} catch (URISyntaxException e) {
			ComposerCorePlugin.logError(e);
		}

		return null;
	}

	public static HttpMethodBase createGetRequest(String url,
			Map<String, String> params) {
		GetMethod method = new GetMethod(url);
		if (params != null) {
			NameValuePair[] query = new NameValuePair[params.size()];
			Set<String> keyList = params.keySet();
			int i = 0;
			for (String key : keyList) {
				query[i++] = new NameValuePair(key, params.get(key));
			}
			method.setQueryString(query);
		}
		return method;
	}

	public static HttpMethodBase setCookies(HttpMethodBase method,
			Map<String, String> params) {
		if (params != null) {
			StringBuilder builder = new StringBuilder();
			Set<String> keyList = params.keySet();
			for (String key : keyList) {
				builder.append(key);
				builder.append("="); //$NON-NLS-1$
				builder.append(params.get(key));
				builder.append(";"); //$NON-NLS-1$
			}
			String value = builder.toString();
			if (value.length() > 0) {
				value = value.substring(0, value.length() - 1);
				method.setRequestHeader("Cookie", value); //$NON-NLS-1$
			}
		}
		return method;
	}

	private static HttpClient createHttpClient(String url,
			IProxyService proxyService) throws IOException, URISyntaxException {
		HttpClient httpClient = new HttpClient();

		if (proxyService.isProxiesEnabled()) {
			IProxyData[] proxyData = proxyService.select(new URI(url));

			if (proxyData.length != 0) {
				IProxyData proxy = proxyData[0];
				String proxyHostName = proxy.getHost();
				if (proxyHostName != null) {
					int portNumber = proxy.getPort();
					if (portNumber == -1) {
						portNumber = 80;
					}
					httpClient.getHostConfiguration().setProxy(proxyHostName,
							portNumber);
					if (proxy.isRequiresAuthentication()) {
						String userName = proxy.getUserId();
						if (userName != null) {
							String password = proxy.getPassword();
							Credentials credentials = new UsernamePasswordCredentials(
									userName, password);
							httpClient.getState().setProxyCredentials(
									new AuthScope(null, AuthScope.ANY_PORT,
											null, AuthScope.ANY_SCHEME),
									credentials);
						}
					}
				}
			}
		}

		httpClient.getHttpConnectionManager().getParams()
				.setConnectionTimeout(15000);

		return httpClient;
	}

}
