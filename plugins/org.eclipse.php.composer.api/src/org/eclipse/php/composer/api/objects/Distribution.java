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

/**
 * Represents a <code>dist</code> entity in a package
 * 
 * @see http://getcomposer.org/doc/05-repositories.md#package-2
 * @author Thomas Gossmann <gos.si>
 *
 */
public class Distribution extends Storage {

	/**
	 * Returns the <code>shasum</code> property.
	 * 
	 * @return the <code>shasum</code> value
	 */
	public String getShaSum() {
		return getAsString("shasum"); //$NON-NLS-1$
	}

	/**
	 * Sets the <code>shasum</code> property.
	 * 
	 * @param type
	 *            the new <code>shasum</code> value
	 */
	public void setShaSum(String shaSum) {
		set("shasum", shaSum); //$NON-NLS-1$
	}
}
