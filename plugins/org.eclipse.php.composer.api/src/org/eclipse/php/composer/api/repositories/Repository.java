/*******************************************************************************
 * Copyright (c) 2012, 2016, 2017 PDT Extension Group and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *     Kaloyan Raev - [501269] externalize strings
 *******************************************************************************/
package org.eclipse.php.composer.api.repositories;

import org.eclipse.php.composer.api.MinimalPackage;

public abstract class Repository extends MinimalPackage implements Cloneable {

	public Repository(String type) {
		set("type", type); //$NON-NLS-1$
	}

	/**
	 * Returns the <code>type</code> property.
	 * 
	 * @return the <code>type</code> property
	 */
	public String getType() {
		return getAsString("type"); //$NON-NLS-1$
	}

	/**
	 * Returns the <code>url</code> property.
	 * 
	 * @return the <code>url</code> property
	 */
	public String getUrl() {
		return getAsString("url"); //$NON-NLS-1$
	}

	public void setUrl(String url) {
		set("url", url); //$NON-NLS-1$
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Repository clone() {
		Repository clone = RepositoryFactory.create(getType());
		cloneProperties(clone);
		return clone;
	}
}
