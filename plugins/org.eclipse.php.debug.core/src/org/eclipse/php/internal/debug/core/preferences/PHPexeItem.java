/*******************************************************************************
 * Copyright (c) 2009,2014 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *     Dawid Pakuła [339547]
 *******************************************************************************/
package org.eclipse.php.internal.debug.core.preferences;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.php.internal.core.IUniqueIdentityElement;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.UniqueIdentityElementUtil;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.php.internal.debug.core.PHPExeException;
import org.eclipse.php.internal.debug.core.PHPExeUtil;
import org.eclipse.php.internal.debug.core.PHPExeUtil.PHPExeInfo;
import org.eclipse.php.internal.debug.core.phpIni.PHPINIUtil;

/**
 * A PHP executable item. An item has a name, version, path, configuration file
 * path and a debugger ID. It can be editable or non-editable in case it was
 * loaded from an extension point.
 * 
 * @author shalom, michael
 */
public class PHPexeItem implements IUniqueIdentityElement, Cloneable {

	public static final String SAPI_CLI = "CLI"; //$NON-NLS-1$
	public static final String SAPI_CGI = "CGI"; //$NON-NLS-1$

	public static final String ID_PREFIX = "php-exe"; //$NON-NLS-1$

	protected String sapiType;
	protected String name;
	protected File config;
	protected File detectedConfig;
	protected File executable;
	protected String version;
	protected boolean editable = true;
	protected boolean loadDefaultINI = false;
	protected String debuggerID;
	protected boolean isDefault;

	private String uniqueId;
	/**
	 * store the php version list that use this PHPexeItem as default PHPexeItem
	 */
	private List<PHPVersion> defaultForPHPVersionList = new ArrayList<PHPVersion>();

	/**
	 * Constructs a new PHP executable item.
	 */
	public PHPexeItem() {
		createUniqueId();
	}

	/**
	 * Constructs a new PHP executable item.
	 * 
	 * @param name
	 * @param executable
	 * @param iniLocation
	 * @param debuggerID
	 * @param editable
	 */
	public PHPexeItem(String name, File executable, File iniLocation,
			String debuggerID, boolean editable) {
		this.name = name;
		this.executable = executable;
		this.config = iniLocation;
		this.debuggerID = debuggerID;
		this.editable = editable;
		detectFromPHPExe();
		createUniqueId();
	}

	/**
	 * Constructs a new PHP executable item.
	 * 
	 * @param name
	 *            PHP executable nice name (like: PHP 5.3 CGI)
	 * @param executable
	 *            PHP executable file
	 * @param config
	 *            The configuration file (php.ini) location (can be null)
	 * @param debuggerID
	 *            ID of debugger (see org.eclipse.php.debug.core.phpDebuggers
	 *            extension point)
	 * @param loadDefaultINI
	 *            Disable php "-n" usage
	 */
	public PHPexeItem(String name, String executable, String config,
			String debuggerID, boolean loadDefaultINI) {
		this.name = name;
		this.debuggerID = debuggerID;
		this.executable = new File(executable);
		if (config != null && config.length() > 0) {
			this.config = new File(config);
		}
		this.loadDefaultINI = loadDefaultINI;
		detectFromPHPExe();
		createUniqueId();
	}

	private final void createUniqueId() {
		setUniqueId(UniqueIdentityElementUtil.generateId(ID_PREFIX));
	}

	final void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	protected void detectFromPHPExe() {
		PHPExeInfo phpInfo;
		try {
			phpInfo = PHPExeUtil.getPHPInfo(executable, false);
		} catch (PHPExeException e) {
			PHPDebugPlugin
					.logErrorMessage("Could not detect PHP executable info during PHP exe item creation: " //$NON-NLS-1$
							+ e.getMessage());
			return;
		}
		if (name == null)
			name = phpInfo.getName();
		if (sapiType == null)
			sapiType = phpInfo.getSapiType();
		if (detectedConfig == null)
			detectedConfig = phpInfo.getSystemINIFile();
		if (version == null)
			version = phpInfo.getVersion();
	}

	@Override
	public String getUniqueId() {
		return uniqueId;
	}

	/**
	 * Returns the debugger ID set for this item.
	 * 
	 * @return The debugger ID.
	 */
	public String getDebuggerID() {
		return debuggerID;
	}

	/**
	 * Set the debugger ID that can use this item.
	 * 
	 * @param debuggerID
	 *            A debugger ID.
	 */
	public void setDebuggerID(String debuggerID) {
		this.debuggerID = debuggerID;
	}

	/**
	 * Returns the configuration file path. The returned value can be null in
	 * case the value was not set. In this case, the ini location is assumed to
	 * be next to the php executable.
	 * 
	 * @return The configuration file location.
	 */
	public File getINILocation() {
		return config;
	}

	/**
	 * Returns the detected configuration file path.
	 * 
	 * @return The detected configuration file location.
	 */
	public File getDetectedINILocation() {
		return detectedConfig;
	}

	/**
	 * Set the PHP ini location.
	 * 
	 * @param location
	 *            The ini location (can be null).
	 */
	public void setINILocation(File location) {
		this.config = location;
	}

	/**
	 * Returns SAPI type of this PHP executable
	 * 
	 * @return
	 */
	public String getSapiType() {
		return sapiType;
	}

	/**
	 * Sets SAPI type of this PHP executable
	 * 
	 * @param sapiType
	 */
	public void setSapiType(String sapiType) {
		this.sapiType = sapiType;
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
	 * @param name
	 *            The name of the item.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the php executable file.
	 * 
	 * @return The php executable file.iniLocation
	 */
	public File getExecutable() {
		return executable;
	}

	/**
	 * Sets the php executable path. Setting the path also sets the executable
	 * directory and reset the ini location to null.
	 * 
	 * @return The php executable file.
	 * @throws IllegalArgumentException
	 *             in case the file is null.
	 */
	public void setExecutable(File executable) {
		if (executable == null) {
			throw new IllegalArgumentException("PHP executable path is null"); //$NON-NLS-1$
		}
		if (executable.equals(this.executable)) {
			return;
		}
		this.executable = executable;
		this.config = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((executable == null) ? 0 : executable.hashCode());
		result = prime * result + ((config == null) ? 0 : config.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
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
		if (config == null) {
			if (other.config != null) {
				return false;
			}
		} else if (!config.equals(other.config)) {
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
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuilder buf = new StringBuilder(name);
		buf.append(" [path: ").append(executable.getAbsolutePath()); //$NON-NLS-1$
		buf.append(", config file: ").append(config.getAbsolutePath()); //$NON-NLS-1$
		buf.append(", sapi: ").append(sapiType); //$NON-NLS-1$
		buf.append("]"); //$NON-NLS-1$
		return buf.toString();
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
	 * @param version
	 *            The item's version.
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
	 * @return return loadDefault
	 */
	public boolean isLoadDefaultINI() {
		return loadDefaultINI;
	}

	/**
	 * If true PHPLaunchUtilities ignore "-n"
	 * 
	 * @param loadDefaultINI
	 */
	public void setLoadDefaultINI(boolean loadDefaultINI) {
		this.loadDefaultINI = loadDefaultINI;
	}

	/**
	 * Set or un-set this item to be the default php executable item.
	 * 
	 * @param isDefault
	 *            the value to set
	 */
	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}

	public void setDefaultForPHPVersion(PHPexes phpexes, PHPVersion phpVersion) {
		phpexes.setItemDefaultForPHPVersion(this, phpVersion);
	}

	void addPHPVersionToDefaultList(PHPVersion phpVersion) {
		defaultForPHPVersionList.add(phpVersion);
	}

	void removePHPVersionToDefaultList(PHPVersion phpVersion) {
		defaultForPHPVersionList.remove(phpVersion);
	}

	public int geDefaultForPHPVersionSize() {
		return defaultForPHPVersionList.size();
	}

	public PHPVersion getPHPVersionAtDefaultList(int index) {
		assert geDefaultForPHPVersionSize() > index;
		return defaultForPHPVersionList.get(index);
	}

	/**
	 * Executes the file in the context of the project
	 * 
	 * @param project
	 * @param scriptFile
	 *            Path to the PHP script
	 * @return true if execution success
	 */
	public boolean execPhpScript(IProject project, String scriptFile) {
		boolean status = false;

		if (executable == null) {
			throw new IllegalStateException("PHP executable path is null"); //$NON-NLS-1$
		}
		File tempPHPIni = PHPINIUtil.createPhpIniByProject(
				getDetectedINILocation(), project);

		try {
			PHPexes.changePermissions(executable);
			PHPExeUtil
					.exec(executable.getAbsolutePath(),
							this.loadDefaultINI ? "" : "-n", "-c", tempPHPIni.getParentFile().getAbsolutePath(), "-v", scriptFile); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		} catch (IOException e) {
			DebugPlugin.log(e);
			status = false;
		}

		return status;
	}

	/**
	 * Creates editable working copy of this item.
	 * 
	 * @return working copy of this item
	 */
	public PHPexeItem makeCopy() {
		PHPexeItem copy = new PHPexeItem();
		// Unique ID should always be the same for copy
		copy.uniqueId = uniqueId;
		copy.sapiType = sapiType;
		copy.name = name;
		if (config != null)
			copy.config = new File(config.toURI());
		if (detectedConfig != null)
			copy.detectedConfig = new File(detectedConfig.toURI());
		if (executable != null)
			copy.executable = new File(executable.toURI());
		copy.version = version;
		copy.editable = editable;
		copy.loadDefaultINI = loadDefaultINI;
		copy.debuggerID = debuggerID;
		copy.isDefault = isDefault;
		copy.defaultForPHPVersionList = new ArrayList<PHPVersion>(
				defaultForPHPVersionList);
		return copy;
	}

}
