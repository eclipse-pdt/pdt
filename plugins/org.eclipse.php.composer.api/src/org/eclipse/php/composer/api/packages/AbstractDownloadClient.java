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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

abstract public class AbstractDownloadClient {

	protected String baseUrl;
	protected boolean baseUrlParamEncoding = false;
	private Log log = LogFactory.getLog(AbstractDownloadClient.class);
	protected String filter = null;

	public AbstractDownloadClient() {
	}

	public AbstractDownloadClient(String baseUrl) {
		this();
		this.baseUrl = baseUrl;
	}

	public AbstractDownloadClient(String baseUrl, boolean baseUrlParamEncoding) {
		this();
		this.baseUrl = baseUrl;
		this.baseUrlParamEncoding = baseUrlParamEncoding;
	}

	/**
	 * Sets the base url. %s in the baseUrl will be replaced with the param.
	 * 
	 * @param baseUrl
	 */
	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrlParamEncoding(boolean baseUrlParamEncoding) {
		this.baseUrlParamEncoding = baseUrlParamEncoding;
	}

	public boolean getBaseUrlParamEncoding() {
		return baseUrlParamEncoding;
	}

	protected String createUrl(String param) {
		try {
			if (baseUrlParamEncoding) {
				param = URLEncoder.encode(param, StandardCharsets.UTF_8.name());
			}
			String url = String.format(baseUrl, param);

			if (filter != null) {
				if (url.contains("?")) { //$NON-NLS-1$
					url += "&type=" + filter; //$NON-NLS-1$
				} else {
					url += "?type=" + filter; //$NON-NLS-1$
				}
			}

			return url;
		} catch (UnsupportedEncodingException e) {
			log.error(e);
		}
		return null;
	}

	public void setFilter(String filter) throws UnsupportedEncodingException {
		this.filter = URLEncoder.encode(filter, StandardCharsets.UTF_8.name());
	}
}
