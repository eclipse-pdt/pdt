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
 *******************************************************************************/
package org.eclipse.php.internal.debug.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.php.internal.debug.core.launching.PHPLaunchUtilities;
import org.eclipse.php.internal.debug.core.phpIni.PHPINIUtil;
import org.eclipse.php.internal.debug.core.preferences.PHPexeItem;
import org.eclipse.php.internal.debug.core.preferences.PHPexes;

/**
 * Class providing utility methods for PHP executables. Clients may use it to
 * execute commands with use of PHP executables or get some basic info about
 * provided PHP executable (name, version, SAPI type, etc.).
 * 
 * @author Bartlomiej Laczkowski
 */
public final class PHPExeUtil {

	/**
	 * Provides info about PHP executable (name, version, SAPI type, etc.).
	 */
	public static final class PHPExeInfo {

		private String version;
		private String sapiType;
		private File execFile;
		private String name;

		private PHPExeInfo(String name, String version, String sapiType, File execFile) {
			super();
			this.name = name;
			this.version = version;
			this.sapiType = sapiType;
			this.execFile = execFile;
		}

		public String getName() {
			return name;
		}

		public String getVersion() {
			return version;
		}

		public String getSapiType() {
			return sapiType;
		}

		public File getExecFile() {
			return execFile;
		}

	}

	/**
	 * Provides basic info about module installed (only its name and name of a
	 * group to which it belongs) on PHP executable in pair with some INI file.
	 */
	public static final class PHPModuleInfo {

		private String name;
		private String groupName;

		public PHPModuleInfo(String name, String groupName) {
			super();
			this.name = name;
			this.groupName = groupName;
		}

		public String getName() {
			return name;
		}

		public String getGroupName() {
			return groupName;
		}

	}

	public static final class PHPVersion {

		private int major = 0;
		private int minor = 0;
		private int service = 0;

		private PHPVersion(PHPexeItem phpExeItem) {
			build(phpExeItem.getVersion());
		}

		private void build(String version) {
			if (version == null)
				return;
			String[] parts = version.split("\\."); //$NON-NLS-1$
			try {
				major = Integer.valueOf(parts[0]);
				minor = Integer.valueOf(parts[1]);
				service = Integer.valueOf(parts[2]);
			} catch (ArrayIndexOutOfBoundsException e) {
				// skip
			}
		}

		public int getMajor() {
			return major;
		}

		public int getMinor() {
			return minor;
		}

		public int getService() {
			return service;
		}

	}

	private static final Pattern PATTERN_PHP_VERSION = Pattern.compile("PHP (\\d\\.\\d\\.\\d+).*? \\((.*?)\\)"); //$NON-NLS-1$

	private static final Map<File, PHPExeInfo> phpInfos = new HashMap<File, PHPExeInfo>();

	private PHPExeUtil() {
	};

	/**
	 * Executes given PHP command and returns output.
	 * 
	 * @param cmd
	 *            Command array
	 * @throws IOException
	 */
	public static String exec(String... cmd) throws IOException {
		String[] envParams = null;
		String env = PHPLaunchUtilities.getLibrarySearchPathEnv(new File(cmd[0]).getParentFile());
		if (env != null) {
			envParams = new String[] { env };
		}

		cmd = filterNulls(cmd);

		Process p = Runtime.getRuntime().exec(cmd, envParams);
		BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
		StringBuilder buf = new StringBuilder();
		String l;
		while ((l = r.readLine()) != null) {
			buf.append(l).append('\n');
		}
		return buf.toString();
	}

	/**
	 * Creates and returns PHP executable info.
	 * 
	 * @param executableFile
	 * @param loadDefaultINI
	 * @return PHP info
	 * @throws PHPExeException
	 */
	public synchronized static PHPExeInfo getPHPInfo(final File executableFile, boolean reload) throws PHPExeException {
		PHPExeInfo phpInfo = phpInfos.get(executableFile);
		if (phpInfo != null && !reload)
			return phpInfo;
		String version = null, sapiType = null, name = null;
		String exePath = executableFile == null ? "<null>" //$NON-NLS-1$
				: executableFile.getAbsolutePath();
		// Simple pre-check
		if (executableFile == null || !executableFile.exists()
				|| !executableFile.getName().toLowerCase().contains("php") //$NON-NLS-1$
				|| executableFile.isDirectory()) {
			return null;
		}
		try {
			PHPexes.changePermissions(executableFile);
			Matcher m;
			String output = fetchVersion(executableFile);
			m = PATTERN_PHP_VERSION.matcher(output);
			if (!m.find()) {
				throw new PHPExeException(MessageFormat.format("Cannot determine version of the PHP executable ({0}).", //$NON-NLS-1$
						exePath));
			}
			// Fetch version
			version = m.group(1);
			// Fetch SAPI type
			String sType = m.group(2);
			sapiType = null;
			if (sType.startsWith("cgi")) { //$NON-NLS-1$
				sapiType = PHPexeItem.SAPI_CGI;
			} else if (sType.startsWith("cli")) { //$NON-NLS-1$
				sapiType = PHPexeItem.SAPI_CLI;
			} else {
				throw new PHPExeException(MessageFormat.format("Cannot determine type of the PHP executable ({0}).", //$NON-NLS-1$
						exePath));
			}
			// Fetch default name
			name = "PHP " + version + " (" + sapiType + ")"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		} catch (IOException e) {
			throw new PHPExeException(MessageFormat.format("Invalid PHP executable: {0}.", exePath), e); //$NON-NLS-1$
		}
		phpInfo = new PHPExeInfo(name, version, sapiType, executableFile);
		phpInfos.put(executableFile, phpInfo);
		return phpInfo;
	}

	/**
	 * Returns info description by calling 'php -i' command in pair with the
	 * appropriate php configuration file location.
	 * 
	 * @param exec
	 * @param tmpIni
	 * @param skipSystemIni
	 * @return PHP executable info
	 * @throws IOException
	 */
	public static String getInfo(PHPexeItem phpExeItem) {
		PHPVersion phpVersion = new PHPVersion(phpExeItem);
		File exec = phpExeItem.getExecutable();
		try {
			if (phpVersion.getMajor() >= 5)
				return PHPExeUtil.exec(exec.getAbsolutePath(), phpExeItem.isLoadDefaultINI() ? null : "-n", "-c", //$NON-NLS-1$ //$NON-NLS-2$
						getINIFile(exec).getAbsolutePath(), "-i"); //$NON-NLS-1$
			else
				return PHPExeUtil.exec(phpExeItem.getExecutable().getAbsolutePath(), "-c", //$NON-NLS-1$
						getINIFile(exec).getAbsolutePath(), "-i"); //$NON-NLS-1$
		} catch (IOException e) {
			Logger.logException(e);
			return "";
		}
	}

	/**
	 * Checks and outputs list of modules installed on top of given PHP
	 * executable item.
	 * 
	 * @param phpExeItem
	 * @return list of installed module names
	 */
	public static List<PHPModuleInfo> getModules(PHPexeItem phpExeItem) {
		List<PHPModuleInfo> modules = new ArrayList<PHPExeUtil.PHPModuleInfo>();
		String result;
		PHPVersion phpVersion = new PHPVersion(phpExeItem);
		File exec = phpExeItem.getExecutable();
		try {
			if (phpVersion.getMajor() >= 5)
				result = PHPExeUtil.exec(exec.getAbsolutePath(), phpExeItem.isLoadDefaultINI() ? null : "-n", "-c", //$NON-NLS-1$ //$NON-NLS-2$
						getINIFile(exec).getAbsolutePath(), "-m"); //$NON-NLS-1$
			else
				result = PHPExeUtil.exec(phpExeItem.getExecutable().getAbsolutePath(), "-c", //$NON-NLS-1$
						getINIFile(exec).getAbsolutePath(), "-m"); //$NON-NLS-1$
		} catch (IOException e) {
			// empty list
			return modules;
		}
		Scanner scanner = new Scanner(result);
		String currentGroup = null;
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine().trim();
			if (line.startsWith("[")) { //$NON-NLS-1$
				currentGroup = line;
				continue;
			}
			if (!line.isEmpty())
				modules.add(new PHPModuleInfo(line, currentGroup));
		}
		scanner.close();
		return modules;
	}

	/**
	 * Checks if module with given name is installed on top of provided PHP
	 * executable item (group name for a module is not taken into account).
	 * 
	 * @param phpExeItem
	 * @param moduleName
	 * @return <code>true</code> if installed, <code>false</code> otherwise
	 */
	public static boolean hasModule(PHPexeItem phpExeItem, String moduleName) {
		List<PHPModuleInfo> modules = getModules(phpExeItem);
		for (PHPModuleInfo module : modules)
			if (module.getName().equalsIgnoreCase(moduleName))
				return true;
		return false;
	}

	/**
	 * Checks if a module with given name and belonging to specific group is
	 * installed on top of PHP executable item.
	 * 
	 * @param phpExeItem
	 * @param moduleName
	 * @param groupName
	 * @return <code>true</code> if installed, <code>false</code> otherwise
	 */
	public static boolean hasModule(PHPexeItem phpExeItem, String moduleName, String groupName) {
		List<PHPModuleInfo> modules = getModules(phpExeItem);
		for (PHPModuleInfo module : modules)
			if (module.getName().equalsIgnoreCase(moduleName) && module.getGroupName().equalsIgnoreCase(groupName))
				return true;
		return false;
	}

	/**
	 * Returns version description by calling 'php -v' command in pair with the
	 * appropriate php configuration file location.
	 * 
	 * @param exec
	 * @param tmpIni
	 * @param skipSystemIni
	 * @return PHP executable version description
	 * @throws IOException
	 */
	public static String fetchVersion(File exec) throws IOException {
		File emptyIni = PHPINIUtil.createTemporaryPHPINIFile();
		return PHPExeUtil.exec(exec.getAbsolutePath(), "-c", //$NON-NLS-1$ //$NON-NLS-2$
				emptyIni.getParentFile().getAbsolutePath(), "-v"); //$NON-NLS-1$
	}

	private static File getINIFile(File exec) {
		return PHPINIUtil.createTemporaryPHPINIFile(PHPINIUtil.findPHPIni(exec.getAbsolutePath()));
	}

	/**
	 * OSX doesn't like empty argument, so due bug 472349 we allow and filter
	 * null values
	 * 
	 * @param cmd
	 * @return
	 */
	private static String[] filterNulls(String[] cmd) {
		ArrayList<String> result = new ArrayList<String>(cmd.length);
		for (String el : cmd) {
			if (el != null) {
				result.add(el);
			}
		}
		return result.toArray(new String[result.size()]);
	}

}
