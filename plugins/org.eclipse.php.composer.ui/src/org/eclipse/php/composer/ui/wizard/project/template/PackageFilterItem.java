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
package org.eclipse.php.composer.ui.wizard.project.template;

import org.eclipse.php.composer.api.MinimalPackage;

/**
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
class PackageFilterItem {

	protected boolean isChecked;
	protected MinimalPackage item;
	protected String[] versions;
	protected String selectedVersion = null;
	
	public PackageFilterItem(MinimalPackage pkg) {
		isChecked = false;
		item = pkg;
	}
	
	public MinimalPackage getPackage() {
		return item;
	}
	
	public boolean isChecked() {
		return isChecked;
	}
	
	public void setChecked(boolean checked) {
		isChecked = checked;
	}

	public void setVersions(String[] versionInput) {
		versions = versionInput;
	}
	
	public String[] getVersions() {
		return versions;
	}

	public void setSelectedVersion(String text) {
		selectedVersion = text;
	}
	
	public String getSelectedVersion() {
		return selectedVersion;
	}
}