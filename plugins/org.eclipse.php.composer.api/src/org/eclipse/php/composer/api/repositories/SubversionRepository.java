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

public class SubversionRepository extends VcsRepository implements Cloneable {

	public SubversionRepository() {
		super("svn");
	}

	public String getTrunkPath() {
		return getAsString("trunk-path");
	}

	public void setTrunkPath(String path) {
		set("trunk-path", path);
	}

	public String getBranchesPath() {
		return getAsString("branches-path");
	}

	public void setBranchesPath(String path) {
		set("branches-path", path);
	}

	public String getTagsPath() {
		return getAsString("tags-path");
	}

	public void setTagsPath(String path) {
		set("tags-path", path);
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
