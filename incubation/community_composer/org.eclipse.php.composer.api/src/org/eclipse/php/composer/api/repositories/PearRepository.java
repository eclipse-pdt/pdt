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
package org.eclipse.php.composer.api.repositories;

public class PearRepository extends Repository implements Cloneable {

	public PearRepository() {
		super("pear");
	}
	
	/**
	 * Returns the vendor-alias
	 * @return the vendor-alias
	 */
	public String getVendorAlias() {
		return getAsString("vendor-alias");
	}

	/**
	 * Sets the vendor-alias
	 * 
	 * @param vendorAlias the vendor-alias to set
	 */
	public void setVendorAlias(String vendorAlias) {
		set("vendor-alias", vendorAlias);
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	public PearRepository clone() {
		PearRepository clone = new PearRepository();
		cloneProperties(clone);
		return clone;
	}
}
