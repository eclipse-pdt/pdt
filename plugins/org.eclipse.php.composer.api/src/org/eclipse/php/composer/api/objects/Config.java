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
package org.eclipse.php.composer.api.objects;

import org.eclipse.php.composer.api.collection.JsonArray;

/**
 * Represents a config entity in a composer package
 * 
 * @see http://getcomposer.org/doc/04-schema.md#config
 * @author Thomas Gossmann <gos.si>
 *
 */
public class Config extends JsonObject {

	/**
	 * Returns the <code>vendor-bin</code> property.
	 * 
	 * @return the <code>vendor-bin</code> property
	 */
	public String getVendorDir() {
		return getAsString("vendor-dir"); //$NON-NLS-1$
	}

	/**
	 * Sets the <code>vendor-bin</code> property.
	 * 
	 * @param vendorDir
	 *            the new <code>vendor-bin</code> value
	 */
	public void setVendorDir(String vendorDir) {
		set("vendor-dir", vendorDir); //$NON-NLS-1$
	}

	/**
	 * Returns the <code>bin-dir</code> property.
	 * 
	 * @return the <code>bin-dir</code> property
	 */
	public String getBinDir() {
		return getAsString("bin-dir"); //$NON-NLS-1$
	}

	/**
	 * Sets the <code>bin-dir</code> property.
	 * 
	 * @param binDir
	 *            the new <code>bin-dir</code> value
	 */
	public void setBinDir(String binDir) {
		set("bin-dir", binDir); //$NON-NLS-1$
	}

	/**
	 * Returns the <code>process-timeout</code> property.
	 * 
	 * @return the <code>process-timeout</code> property
	 */
	public Integer getProcessTimeout() {
		return getAsInteger("process-timeout"); //$NON-NLS-1$
	}

	/**
	 * Sets the <code>process-timeout</code> property.
	 * 
	 * @param processTimeout
	 *            the new <code>process-timeout</code> value
	 */
	public void setProcessTimeout(int processTimeout) {
		set("process-timeout", processTimeout); //$NON-NLS-1$
	}

	/**
	 * Returns the <code>github-protocols</code> property. If this property
	 * isn't present in the json, the default value
	 * <code>["git", "https", "http"]</code> is returned.
	 * 
	 * @return the <code>github-protocols</code> property
	 */
	public JsonArray getGithubProtocols() {
		JsonArray protocols = getAsArray("github-protocols"); //$NON-NLS-1$
		if (protocols == null) {
			protocols = new JsonArray();
			protocols.add("git"); //$NON-NLS-1$
			protocols.add("https"); //$NON-NLS-1$
			protocols.add("http"); //$NON-NLS-1$
		}
		return protocols;
	}

	/**
	 * Sets the <code>github-protocols</code> property.
	 * 
	 * @param githubProtocols
	 *            the new <code>github-protocols</code> value
	 */
	public void setGithubProtocols(JsonArray githubProtocols) {
		set("github-protocols", githubProtocols); //$NON-NLS-1$
	}

	/**
	 * Sets the <code>notify-on-install</code> property. If this property isn't
	 * present in the json, the default value <code>true</code> is returned.
	 * 
	 * @return the <code>notify-on-install</code> property
	 */
	public boolean getNotifyOnInstall() {
		if (has("notify-on-install")) { //$NON-NLS-1$
			return getAsBoolean("notify-on-install"); //$NON-NLS-1$
		} else {
			return true;
		}
	}

	/**
	 * Set the <code>notify-on-install</code> property.
	 * 
	 * @param notifyOnInstall
	 *            the new <code>notify-on-install</code> value
	 */
	public void setNotifyOnInstall(boolean notifyOnInstall) {
		set("notify-on-install", notifyOnInstall); //$NON-NLS-1$
	}
}
