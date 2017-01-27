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
package org.eclipse.php.composer.api.repositories;

public class SubversionRepository extends VcsRepository implements Cloneable {

	public SubversionRepository() {
		super("svn"); //$NON-NLS-1$
	}

	public String getTrunkPath() {
		return getAsString("trunk-path"); //$NON-NLS-1$
	}

	public void setTrunkPath(String path) {
		set("trunk-path", path); //$NON-NLS-1$
	}

	public String getBranchesPath() {
		return getAsString("branches-path"); //$NON-NLS-1$
	}

	public void setBranchesPath(String path) {
		set("branches-path", path); //$NON-NLS-1$
	}

	public String getTagsPath() {
		return getAsString("tags-path"); //$NON-NLS-1$
	}

	public void setTagsPath(String path) {
		set("tags-path", path); //$NON-NLS-1$
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	public SubversionRepository clone() {
		SubversionRepository clone = new SubversionRepository();
		cloneProperties(clone);
		return clone;
	}
}
