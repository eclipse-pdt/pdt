/**
 * Copyright (c) 2007 Zend Technologies
 * 
 */
package org.eclipse.php.internal.ui.preferences;

import org.eclipse.php.ui.util.PHPManualSite;

/**
 * @author seva
 * Stores meta data for {@link PHPManualSite}
 */
public class PHPManualConfig {
	private String label;
	private String url;
	private String extension;
	private boolean contributed;

	public PHPManualConfig(String label, String url, String extension, boolean contributed) {
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

	/* (non-Javadoc)
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