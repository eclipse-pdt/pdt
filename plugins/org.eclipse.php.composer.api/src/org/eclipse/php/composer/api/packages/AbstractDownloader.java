/*******************************************************************************
 * Copyright (c) 2012, 2016 PDT Extension Group and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.api.packages;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 * @author Thomas Gossmann <gos.si>
 * 
 */
public abstract class AbstractDownloader {
	protected String url;

	protected List<DownloadListenerInterface> listeners = new ArrayList<>();

	// private DownloadListenerInterface downloadListener;
	//
	// private InputStream downloadContent;
	// private Exception downloadError;

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

}
