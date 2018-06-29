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

public class PearRepository extends Repository implements Cloneable {

	public PearRepository() {
		super("pear"); //$NON-NLS-1$
	}

	/**
	 * Returns the vendor-alias
	 * 
	 * @return the vendor-alias
	 */
	public String getVendorAlias() {
		return getAsString("vendor-alias"); //$NON-NLS-1$
	}

	/**
	 * Sets the vendor-alias
	 * 
	 * @param vendorAlias
	 *            the vendor-alias to set
	 */
	public void setVendorAlias(String vendorAlias) {
		set("vendor-alias", vendorAlias); //$NON-NLS-1$
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public PearRepository clone() {
		PearRepository clone = new PearRepository();
		cloneProperties(clone);
		return clone;
	}
}
