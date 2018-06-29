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
package org.eclipse.php.composer.api;

import org.eclipse.php.composer.api.annotation.Name;
import org.eclipse.php.composer.api.objects.Autoload;
import org.eclipse.php.composer.api.objects.Distribution;
import org.eclipse.php.composer.api.objects.Source;

public abstract class DistributedPackage extends VersionedPackage {

	protected Autoload autoload = new Autoload();

	@Name("autoload-dev")
	protected Autoload autoloadDev = new Autoload();

	protected Distribution dist = new Distribution();
	protected Source source = new Source();

	/**
	 * Returns the <code>type</code> property.
	 * 
	 * @return the <code>type</code> value
	 */
	public String getType() {
		return getAsString("type"); //$NON-NLS-1$
	}

	/**
	 * Sets the <code>type</code> property.
	 * 
	 * @param type
	 *            new <code>type</code> value
	 */
	public void setType(String type) {
		set("type", type); //$NON-NLS-1$
	}

	/**
	 * Returns the <code>autoload</code> entity.
	 * 
	 * @return the <code>autoload</code> entity
	 */
	public Autoload getAutoload() {
		return autoload;
	}

	/**
	 * @return
	 */
	public Autoload getAutoloadDev() {
		return autoloadDev;
	}

	/**
	 * Returns the <code>dist</code> entity.
	 * 
	 * @return the <code>dist</code> entity
	 */
	public Distribution getDist() {
		return dist;
	}

	/**
	 * Returns the <code>source</code> entity.
	 * 
	 * @return the <code>source</code> entity
	 */
	public Source getSource() {
		return source;
	}

}
