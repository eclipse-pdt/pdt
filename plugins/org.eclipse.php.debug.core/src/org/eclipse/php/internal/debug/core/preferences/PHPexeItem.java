/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.debug.core.preferences;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IProject;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.php.internal.debug.core.launching.PHPLaunchUtilities;
import org.eclipse.php.internal.debug.core.phpIni.PHPINIUtil;

/**
 * A PHP executable item. An item has a name, version, path, configuration file
 * path and a debugger ID. It can be editable or non-editable in case it was
 * loaded from an extension point.
 * 
 * @author shalom, michael
 */
public class PHPexeItem {

	public static final String SAPI_CLI = "CLI"; //$NON-NLS-1$
	public static final String SAPI_CGI = "CGI"; //$NON-NLS-1$

	private static final Pattern PHP_VERSION = Pattern
			.compile("PHP (\\d\\.\\d\\.\\d+).*? \\((.*?)\\)"); //$NON-NLS-1$
	private static final Pattern PHP_CLI_CONFIG = Pattern
			.compile("Configuration File \\(php.ini\\) Path => (.*)"); //$NON-NLS-1$
	private static final Pattern PHP_CGI_CONFIG = Pattern
			.compile("Configuration File \\(php.ini\\) Path </td><td class=\"v\">(.*?)</td>"); //$NON-NLS-1$

	private String sapiType;
	private String name;
	private File config;
	private File detectedConfig;
	private File executable;
	private String version;
	private boolean editable = true;
	private String debuggerID;
	private boolean isDefault;
	/**
	 * store the php version list that use this PHPexeItem as default PHPexeItem
	 */
	private List<PHPVersion> defaultForPHPVersionList = new ArrayList<PHPVersion>();

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
	 */
	public PHPexeItem(String name, String executable, String config,
			String debuggerID) {
		this.name = name;
		this.debuggerID = debuggerID;
		this.executable = new File(executable);
		if (config != null && config.length() > 0) {
			this.config = new File(config);
		}

		detectFromPHPExe();
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
	}

	/**
	 * Constructs a new PHP executable item.
	 */
	public PHPexeItem() {
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

		detectFromPHPExe();
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
	 * Detects various things like: type, version, default configuration file,
	 * etc. from the PHP binary
	 */
	protected void detectFromPHPExe() {
		if (executable == null) {
			throw new IllegalStateException("PHP executable path is null"); //$NON-NLS-1$
		}

		// Create empty configuration file:
		File tempPHPIni = PHPINIUtil.createTemporaryPHPINIFile();

		try {
			PHPexes.changePermissions(executable);

			// Detect version and type:
			String output = exec(
					executable.getAbsolutePath(),
					"-n", "-c", tempPHPIni.getParentFile().getAbsolutePath(), "-v"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			Matcher m = PHP_VERSION.matcher(output);
			if (m.find()) {
				initFields(m);
			} else {
				output = exec(
						executable.getAbsolutePath(),
						"-c", tempPHPIni.getParentFile().getAbsolutePath(), "-v"); //$NON-NLS-1$ //$NON-NLS-2$
				m = PHP_VERSION.matcher(output);
				if (m.find()) {
					initFields(m);
				} else {
					PHPDebugPlugin
							.logErrorMessage("Can't determine version of the PHP executable"); //$NON-NLS-1$
					// this.executable = null;
					return;
				}
				return;
			}

			// Detect default PHP.ini location:
			if (detectedConfig == null) {
				output = exec(
						executable.getAbsolutePath(),
						"-n", "-c", tempPHPIni.getParentFile().getAbsolutePath(), "-i"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				if (sapiType == SAPI_CLI) {
					m = PHP_CLI_CONFIG.matcher(output);
				} else if (sapiType == SAPI_CGI) {
					m = PHP_CGI_CONFIG.matcher(output);
				}
				if (m.find()) {
					String configDir = m.group(1);
					detectedConfig = new File(configDir.trim(), "php.ini"); //$NON-NLS-1$
					if (!detectedConfig.exists()) {
						detectedConfig = null;
					}
				} else {
					PHPDebugPlugin
							.logErrorMessage("Can't determine PHP.ini location of the PHP executable"); //$NON-NLS-1$
					// this.executable = null;
					return;
				}
			}
		} catch (IOException e) {
			DebugPlugin.log(e);
		}
	}

	private void initFields(Matcher m) {
		version = m.group(1);
		String type = m.group(2);
		if (type.startsWith("cgi")) { //$NON-NLS-1$
			sapiType = SAPI_CGI;
		} else if (type.startsWith("cli")) { //$NON-NLS-1$
			sapiType = SAPI_CLI;
		} else {
			PHPDebugPlugin
					.logErrorMessage("Can't determine type of the PHP executable"); //$NON-NLS-1$
			// this.executable = null;
			return;
		}

		if (name == null) {
			name = "PHP " + version + " (" + sapiType + ")"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		}
	}

	/**
	 * Executes given command
	 * 
	 * @param cmd
	 *            Command array
	 * @throws IOException
	 */
	private static String exec(String... cmd) throws IOException {
		String[] envParams = null;
		String env = PHPLaunchUtilities
				.getLibrarySearchPathEnv(new File(cmd[0]).getParentFile());
		if (env != null) {
			envParams = new String[] { env };
		}

		Process p = Runtime.getRuntime().exec(cmd, envParams);
		BufferedReader r = new BufferedReader(new InputStreamReader(
				p.getInputStream()));
		StringBuilder buf = new StringBuilder();
		String l;
		while ((l = r.readLine()) != null) {
			buf.append(l).append('\n');
		}
		return buf.toString();
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
			exec(executable.getAbsolutePath(),
					"-n", "-c", tempPHPIni.getParentFile().getAbsolutePath(), "-v", scriptFile); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		} catch (IOException e) {
			DebugPlugin.log(e);
			status = false;
		}

		return status;
	}

}
