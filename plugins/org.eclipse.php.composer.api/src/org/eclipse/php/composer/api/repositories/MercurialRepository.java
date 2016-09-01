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

public class MercurialRepository extends VcsRepository implements Cloneable {

	public MercurialRepository() {
		super("hg");
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	public MercurialRepository clone() {
		MercurialRepository clone = new MercurialRepository();
		cloneProperties(clone);
		return clone;
	}
}
