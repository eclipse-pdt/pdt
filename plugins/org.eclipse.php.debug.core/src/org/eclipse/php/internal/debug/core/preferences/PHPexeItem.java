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

/**
 * A PHP executable item.
 * An item has a name, version, path, ini path and a debugger ID.
 * It can be editable or non-editable in case it was loaded from an extension point.
 * 
 * @author shalom
 */
public class PHPexeItem {

	private static final String[] fgCandidatePHPLocations = { "php", "php.exe" }; //$NON-NLS-1$ //$NON-NLS-2$
	private String name;
	private File executableDirectory;
	private File iniLocation;
	private File phpExecutable;
	private String version;
	private boolean editable = true;
	private String debuggerID;
	private boolean isDefault;

	/**
	 * Constructs a new PHP executable item.
	 * 
	 * @param name
	 * @param phpDirectoryPath
	 * @param iniPath The php.ini location (can be null)
	 * @param debuggerID
	 */
	public PHPexeItem(String name, String phpDirectoryPath, String iniPath, String debuggerID) {
		this.name = name;
		this.debuggerID = debuggerID;
		setExecutableDirectory(new File(phpDirectoryPath));
		if (iniPath != null && iniPath.length() > 0) {
			setINILocation(new File(iniPath));
		}
	}

	/**
	 * Constructs a new PHP executable item.
	 * 
	 * @param name
	 * @param phpExecutable
	 * @param iniLocation
	 * @param debuggerID
	 * @param editable
	 */
	public PHPexeItem(String name, File phpExecutable, File iniLocation, String debuggerID, boolean editable) {
		this.name = name;
		this.phpExecutable = phpExecutable;
		this.editable = editable;
		this.executableDirectory = phpExecutable.getParentFile();
		this.iniLocation = iniLocation;
		this.debuggerID = debuggerID;
	}

	/**
	 * Constructs a new PHP executable item.
	 */
	public PHPexeItem() {
	}

	/**
	 * Returns the debugger ID set for this item.
	 * @return The debugger ID.
	 */
	public String getDebuggerID() {
		return debuggerID;
	}

	/**
	 * Set the debugger ID that can use this item.
	 * 
	 * @param debuggerID A debugger ID.
	 */
	public void setDebuggerID(String debuggerID) {
		this.debuggerID = debuggerID;
	}

	/**
	 * Returns the PHP executable directory.
	 * 
	 * @return The executable directory.
	 */
	public File getExecutableDirectory() {
		return executableDirectory;
	}

	/**
	 * Returns the php.ini path.
	 * The returned value can be null in case the value was not set. In this case, the ini location 
	 * is assumed to be next to the php executable.
	 * 
	 * @return The php.ini location.
	 */
	public File getINILocation() {
		return iniLocation;
	}

	/**
	 * Sets the PHP executable directory.
	 * Setting this directory will also cause for the php executable to be changed and 
	 * the ini location to be set to null.
	 * 
	 * @param location The executable location.
	 * @see #findPHPExecutable(File)
	 */
	public void setExecutableDirectory(File location) {
		this.executableDirectory = location;
		phpExecutable = findPHPExecutable(location);
		this.iniLocation = null;
	}

	/**
	 * Set the PHP ini location.
	 * 
	 * @param location The ini location (can be null).
	 */
	public void setINILocation(File location) {
		this.iniLocation = location;
	}

	/**
	 * Returns the name of this PHP executable item.
	 * 
	 * @return The name of the item.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of this item.
	 * 
	 * @param name The name of the item.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Locate a PHP executable file in the PHP location given to this method.
	 * The location should be a directory.
	 * The search is done for php and php.exe only.
	 * 
	 * @param phpLocation A directory that might hold a PHP executable.
	 * @return A PHP executable file.
	 */
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

	/**
	 * Returns the php executable file.
	 * 
	 * @return The php executable file.
	 */
	public File getPhpExecutable() {
		return phpExecutable;
	}

	/**
	 * Sets the php executable path.
	 * Setting the path also sets the executable directory and reset the ini location to null.
	 * 
	 * @return The php executable file.
	 * @throws IllegalArgumentException in case the file is null.
	 */
	public void setPhpExecutable(File phpExecutable) {
		if (phpExecutable == null) {
			throw new IllegalArgumentException("PHP executable path is null");
		}
		this.phpExecutable = phpExecutable;
		this.executableDirectory = phpExecutable.getParentFile();
		this.iniLocation = null;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((phpExecutable == null) ? 0 : phpExecutable.hashCode());
		result = prime * result + ((iniLocation == null) ? 0 : iniLocation.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final PHPexeItem other = (PHPexeItem) obj;
		if (executableDirectory == null) {
			if (other.executableDirectory != null) {
				return false;
			}
		} else if (!executableDirectory.equals(other.executableDirectory)) {
			return false;
		}
		if (iniLocation == null) {
			if (other.iniLocation != null) {
				return false;
			}
		} else if (!iniLocation.equals(other.iniLocation)) {
			return false;
		}
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new StringBuilder(name).append(" (path: ").append(executableDirectory.getAbsolutePath()).append(")").toString();
	}

	/**
	 * Returns the version of the item.
	 * 
	 * @return The item's version.
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * Sets the version of the item.
	 * 
	 * @param version The item's version.
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * Returns if this item is editable (e.g. a user defined item).
	 *
	 * @return True, if this item can be edited.
	 */
	public boolean isEditable() {
		return editable;
	}

	/**
	 * Returns if this item is the default item.
	 *
	 * @return if this item is the default item.
	 */
	public boolean isDefault() {
		return isDefault;
	}

	/**
	 * Set or un-set this item to be the default php executable item.
	 *
	 * @param isDefault the value to set
	 */
	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}
}
