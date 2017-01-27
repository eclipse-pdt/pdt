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
package org.eclipse.php.composer.api;

import org.eclipse.php.composer.api.entities.Version;

/**
 * Represents a dependency entry in require or require-dev
 * 
 * @see http://getcomposer.org/doc/04-schema.md#require
 * @see http://getcomposer.org/doc/04-schema.md#require-dev
 * @author Thomas Gossmann <gos.si>
 *
 */
public class VersionedPackage extends MinimalPackage {

	protected transient Version detailedVersion = null;

	public Version getDetailedVersion() {
		if (detailedVersion == null) {
			detailedVersion = new Version(getVersion());
		}
		return detailedVersion;
	}

	/**
	 * Returns the version.
	 * 
	 * @return the version
	 */
	public String getVersion() {
		return getAsString("version"); //$NON-NLS-1$
	}

	/**
	 * Sets the version.
	 * 
	 * @param version
	 *            the version to set
	 */
	public void setVersion(String version) {
		set("version", version); //$NON-NLS-1$
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	public VersionedPackage clone() {
		VersionedPackage clone = new VersionedPackage();
		cloneProperties(clone);
		return clone;
	}
}
