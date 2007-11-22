package org.eclipse.php.internal.debug.core.phpIni;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.internal.filesystem.local.LocalFile;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.php.internal.core.project.IIncludePathEntry;
import org.eclipse.php.internal.core.project.options.includepath.IncludePathVariableManager;
import org.eclipse.php.internal.core.util.PHPSearchEngine;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;

public class PHPINIUtil {

	private static final String PHP_INI_FILE = "php.ini"; //$NON-NLS-1$
	private static final String INCLUDE_PATH = "include_path"; //$NON-NLS-1$
	private static final String ZEND_EXTENSION = "zend_extension"; //$NON-NLS-1$
	private static final String ZEND_EXTENSION_TS = "zend_extension_ts"; //$NON-NLS-1$

	private static void modifyIncludePath(File phpIniFile, String[] includePath) {
		try {
			INIFileModifier m = new INIFileModifier(phpIniFile);
			m.removeAllEntries(INCLUDE_PATH);
			StringBuilder valueBuf = new StringBuilder("."); //$NON-NLS-1$
			for (String path : includePath) {
				valueBuf.append(File.pathSeparatorChar).append(path);
			}
			m.addEntry(INCLUDE_PATH, valueBuf.toString());
			m.close();
		} catch (IOException e) {
			PHPDebugPlugin.log(e);
		}
	}

	private static void modifyDebuggerExtensionPath(File phpIniFile, String extensionPath) {
		try {
			INIFileModifier m = new INIFileModifier(phpIniFile);
			if (Platform.OS_WIN32.equals(Platform.getOS())) {
				if (m.removeAllEntries(ZEND_EXTENSION_TS, ".*\\.\\\\ZendDebugger\\.dll.*")) { //$NON-NLS-1$
					m.addEntry(ZEND_EXTENSION_TS, extensionPath);
				}
			} else {
				if (m.removeAllEntries(ZEND_EXTENSION, ".*\\./ZendDebugger\\.so.*")) { //$NON-NLS-1$
					m.addEntry(ZEND_EXTENSION, extensionPath);
				}
			}
			m.close();
		} catch (IOException e) {
			PHPDebugPlugin.log(e);
		}
	}

	/**
	 * Make some preparations before debug session:
	 * <ul>
	 * 	<li>Adds include path
	 * 	<li>Modifies Zend Debugger path in the PHP configuration file
	 * </ul>
	 *
	 * @param phpIniFile PHP configuration file instance
	 * @param phpExePath Path to the PHP executable
	 * @param project Current project
	 */
	public static void prepareBeforeDebug(File phpIniFile, String phpExePath, IProject project) {
		// Modify include path:
		if (project != null) {
			Object[] path = PHPSearchEngine.buildIncludePath(project);
			List<String> includePath = new ArrayList<String>(path.length);
			for (Object pathObject : path) {
				if (pathObject instanceof IIncludePathEntry) {
					IIncludePathEntry entry = (IIncludePathEntry) pathObject;
					IPath entryPath = entry.getPath();
					if (entry.getEntryKind() == IIncludePathEntry.IPE_VARIABLE) {
						entryPath = IncludePathVariableManager.instance().resolveVariablePath(entryPath.toString());
					}
					if (entryPath != null) {
						includePath.add(entryPath.toFile().getAbsolutePath());
					}
				} else if (pathObject instanceof IContainer) {
					IContainer container = (IContainer) pathObject;
					IPath location = container.getLocation();
					if (location != null) {
						includePath.add(location.toOSString());
					}
				} else {
					includePath.add(pathObject.toString());
				}
			}
			modifyIncludePath(phpIniFile, includePath.toArray(new String[includePath.size()]));
		}

		// Modify Zend Debugger extension entry:
		File phpExeDir = new File(phpExePath).getParentFile();
		if (phpExeDir != null) {
			File debuggerFile = new File(phpExeDir, Platform.OS_WIN32.equals(Platform.getOS()) ? "ZendDebugger.dll" : "ZendDebugger.so"); //$NON-NLS-1$ //$NON-NLS-2$
			if (debuggerFile.exists()) {
				modifyDebuggerExtensionPath(phpIniFile, debuggerFile.getAbsolutePath());
			}
		}
	}

	/**
	 * Creates temporary PHP configuration file and returns its instance of <code>null</code> in case of error.
	 * This file will be removed when the program exits.
	 *
	 * @return temporary PHP configuration file instance
	 */
	public static File createTemporaryPHPINIFile() {
		return createTemporaryPHPINIFile(null);
	}

	/**
	 * Creates temporary PHP configuration file and returns its instance of <code>null</code> in case of error.
	 * This file will be removed when the program exits.
	 *
	 * @param originalPHPIniFile If specified - its contents will be copied to the temporary file
	 * @return temporary PHP configuration file instance
	 */
	public static File createTemporaryPHPINIFile(File originalPHPIniFile) {
		File phpIniFile = null;
		try {
			// Create temporary directory:
			File tempDir = new File(System.getProperty("java.io.tmpdir"), "zend_debug"); //$NON-NLS-1$ //$NON-NLS-2$
			if (!tempDir.exists()) {
				tempDir.mkdir();
				tempDir.deleteOnExit();
			}
			tempDir = File.createTempFile("session", null, tempDir); //$NON-NLS-1$
			tempDir.delete(); // delete temp file
			tempDir.mkdir();
			tempDir.deleteOnExit();

			// Create empty configuration file:
			phpIniFile = new File(tempDir, PHP_INI_FILE);
			phpIniFile.createNewFile();
			phpIniFile.deleteOnExit();

			if (originalPHPIniFile != null && originalPHPIniFile.exists()) {
				new LocalFile(originalPHPIniFile).copy(new LocalFile(phpIniFile), EFS.OVERWRITE, new NullProgressMonitor());
			}
		} catch (Exception e) {
			PHPDebugPlugin.log(e);
		}
		return phpIniFile;
	}

	/**
	 * Locate and return a PHP configuration file path for the given PHP executable.
	 * The locating is done by trying to return a PHP configuration file that is located next to the executable.
	 * The return value can be null in case it fails to locate a valid file.
	 *
	 * @param phpExe The PHP executable path.
	 * @return A PHP configuration file path, or <code>null</code> if it fails.
	 */
	public static File findPHPIni(String phpExe) {
		File phpExeFile = new File(phpExe);
		File phpIniFile = new File(phpExeFile.getParentFile(), PHP_INI_FILE);
		if (phpIniFile.exists() && phpIniFile.canRead()) {
			return phpIniFile;
		}
		return null;
	}
}
