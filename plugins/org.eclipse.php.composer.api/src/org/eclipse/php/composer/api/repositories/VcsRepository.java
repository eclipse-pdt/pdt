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

public class VcsRepository extends Repository implements Cloneable {

	public VcsRepository() {
		super("vcs"); //$NON-NLS-1$
	}

	public VcsRepository(String type) {
		super(type);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public VcsRepository clone() {
		VcsRepository clone = new VcsRepository();
		cloneProperties(clone);
		return clone;
	}
}
