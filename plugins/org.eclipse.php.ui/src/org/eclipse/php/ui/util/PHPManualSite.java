/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.ui.util;

public class PHPManualSite {

	protected String url;
	protected PHPManualDirector director;
	protected String extension;

	public PHPManualSite(String url, String extension) {
		this.url = url;
		this.extension = extension;
	}

	public PHPManualDirector getDirector() {
		return director;
	}

	public void setDirector(PHPManualDirector director) {
		this.director = director;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
