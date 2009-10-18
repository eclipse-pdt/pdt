/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.ui.preferences;

import org.eclipse.php.internal.ui.util.PHPManualSite;

/**
 * @author seva Stores meta data for {@link PHPManualSite}
 */
public class PHPManualConfig {
	private String label;
	private String url;
	private String extension;
	private boolean contributed;

	public PHPManualConfig(String label, String url, String extension,
			boolean contributed) {
		this.label = label;
		this.url = url;
		this.contributed = contributed;
		this.extension = extension;
	}

	public boolean isContributed() {
		return contributed;
	}

	public String getLabel() {
		return label;
	}

	public String getUrl() {
		return url;
	}

	public String getExtension() {
		return extension;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if (super.equals(obj))
			return true;
		if (!(obj instanceof PHPManualConfig))
			return false;
		PHPManualConfig other = (PHPManualConfig) obj;
		if (!getLabel().equals(other.getLabel()))
			return false;
		if (!getUrl().equals(other.getUrl()))
			return false;
		if (!getExtension().equals(other.getExtension()))
			return false;
		return true;
	}

}