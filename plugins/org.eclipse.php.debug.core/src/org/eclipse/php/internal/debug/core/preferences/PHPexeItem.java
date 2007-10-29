/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.core.preferences;

import java.io.File;

public class PHPexeItem {

	private static final String[] fgCandidatePHPLocations = { "php", "php.exe" }; //$NON-NLS-1$ //$NON-NLS-2$

	String name;
	File location;
	File phpEXE;
	String version;
	boolean editable = true;
	String debuggerID;
	boolean isDefault;

	public PHPexeItem(String name, String path, String debuggerID) {
		this.name = name;
		this.debuggerID = debuggerID;
		setLocation(new File(path));
	}

	public PHPexeItem(String name, File phpExeFile, String debuggerID, boolean editable) {
		this.name = name;
		phpEXE = phpExeFile;
		this.editable = editable;
		location = phpExeFile.getParentFile();
		this.debuggerID = debuggerID;
	}

	public PHPexeItem() {
	}

	public String getDebuggerID() {
		return debuggerID;
	}

	public void setDebuggerID(String debuggerID) {
		this.debuggerID = debuggerID;
	}

	public File getLocation() {
		return location;
	}

	public void setLocation(File location) {
		this.location = location;
		phpEXE = findPHPExecutable(location);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static File findPHPExecutable(File phpLocation) {

		// Try each candidate in order.  The first one found wins.  Thus, the order
		// of fgCandidateJavaLocations is significant.
		for (String element : fgCandidatePHPLocations) {
			File javaFile = new File(phpLocation, element);
			if (javaFile.isFile()) {
				return javaFile;
			}
		}
		return null;
	}

	public File getPhpEXE() {
		return phpEXE;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final PHPexeItem other = (PHPexeItem) obj;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public String toString() {
		return new StringBuilder(name).append(" (path: ").append(location.getAbsolutePath()).append(")").toString();
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * Returns it this exe item is editable (e.g. a user defined item).
	 *
	 * @return True, if this item can be edited.
	 */
	public boolean isEditable() {
		return editable;
	}

	/**
	 * Returns if this item is the default exe item.
	 *
	 * @return if this item is the default exe item.
	 */
	public boolean isDefault() {
		return isDefault;
	}

	/**
	 * Set or un-set this item to be the default php exe item.
	 *
	 * @param isDefault the value to set
	 */
	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}
}
