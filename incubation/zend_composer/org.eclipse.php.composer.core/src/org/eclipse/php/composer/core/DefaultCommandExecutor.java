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
import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.core.net.proxy.IProxyData;
import org.eclipse.core.net.proxy.IProxyService;
import org.eclipse.core.runtime.IProgressMonitor;

/**
 * @author Wojciech Galanciak, 2013
 * 
 */
public class DefaultCommandExecutor extends CommandExecutor implements ICommandExecutor {

	private static final String HTTP = "http"; //$NON-NLS-1$
	private static final String NO_PROXY = "no_proxy"; //$NON-NLS-1$
	private static final String HTTP_PROXY = "HTTP_PROXY"; //$NON-NLS-1$

	public int run(IProgressMonitor monitor) throws IOException {
		configureProxy();
		return super.run(monitor);
	}

	protected void configureProxy() {
		String httpProxy = System.getenv(HTTP_PROXY);
		if (httpProxy == null) {
			httpProxy = System.getenv(HTTP_PROXY.toLowerCase());
		}
		IProxyService proxyService = ComposerCorePlugin.getProxyService();
		if (httpProxy == null && proxyService != null) {
			IProxyData[] data = proxyService.getProxyData();
			if (data != null) {
				for (IProxyData proxyData : data) {
					if (HTTP.equalsIgnoreCase(proxyData.getType()) && proxyData.getPort() != -1
							&& proxyData.getHost() != null) {
						try {
							URL url = new URL(HTTP, proxyData.getHost(), proxyData.getPort(), ""); //$NON-NLS-1$
							String[] noProxyHosts = proxyService.getNonProxiedHosts();
							StringBuilder noProxy = new StringBuilder();
							for (String host : noProxyHosts) {
								if (noProxy.length() > 0) {
									noProxy.append(","); //$NON-NLS-1$
								}
								noProxy.append(host);
							}
							if (noProxy.length() > 0) {
								setEnvironmentVar(NO_PROXY, noProxy.toString());
							}
							String proxy = url.toString();
							setEnvironmentVar(HTTP_PROXY.toLowerCase(), proxy);
							setEnvironmentVar(HTTP_PROXY, proxy);
							break;
						} catch (MalformedURLException e) {
							ComposerCorePlugin.logError(e);
						}
					}
				}
			}
		}
	}

}