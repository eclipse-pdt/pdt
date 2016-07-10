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

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.X509TrustManager;


/**
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 * @author Thomas Gossmann <gos.si>
 * 
 */
public abstract class AbstractDownloader {
	protected String url;
	
	protected List<DownloadListenerInterface> listeners = new ArrayList<DownloadListenerInterface>();
	
//	private DownloadListenerInterface downloadListener;
//	
//	private InputStream downloadContent;
//	private Exception downloadError;

	public AbstractDownloader() {
		init();
	}
	
	public AbstractDownloader(String url) {
		this();
		this.setUrl(url);
	}
	
	protected void init() {
	}
	
	public void addDownloadListener(DownloadListenerInterface listener) {
		listeners.add(listener);
	}

	public void removeDownloadListener(DownloadListenerInterface listener) {
		listeners.remove(listener);
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	protected class ComposerTrustManager implements X509TrustManager {
		
		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}
		
		public void checkServerTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
			
		}
		
		public void checkClientTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
			
		}
	}

}
