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
package org.eclipse.php.internal.debug.core.phpIni;

import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.internal.filesystem.local.LocalFile;
import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.core.environment.EnvironmentPathUtils;
import org.eclipse.php.internal.core.includepath.IncludePath;
import org.eclipse.php.internal.core.util.PHPSearchEngine;
import org.eclipse.php.internal.debug.core.Logger;
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

	private static void modifyDebuggerExtensionPath(File phpIniFile,
			String extensionPath) {
		try {
			INIFileModifier m = new INIFileModifier(phpIniFile);
			if (Platform.OS_WIN32.equals(Platform.getOS())) {
				if (m.removeAllEntries(ZEND_EXTENSION_TS,
						".*\\.\\\\ZendDebugger\\.dll.*")) { //$NON-NLS-1$
					m.addEntry(ZEND_EXTENSION_TS, extensionPath);
				}
			} else {
				if (m.removeAllEntries(ZEND_EXTENSION,
						".*\\./ZendDebugger\\.so.*")) { //$NON-NLS-1$
					m.addEntry(ZEND_EXTENSION, extensionPath);
				}
			}
			m.close();
		} catch (IOException e) {
			PHPDebugPlugin.log(e);
		}
	}

	private static void modifyExtensionDir(File phpIniFile, String extensionPath) {
		try {
			INIFileModifier m = new INIFileModifier(phpIniFile);
			m.addEntry("extension_dir", extensionPath, true); //$NON-NLS-1$
			m.close();
		} catch (IOException e) {
			PHPDebugPlugin.log(e);
		}
	}

	/**
	 * Adds/Creates the php ini file according to the project include path
	 * settings.
	 * 
	 * @param phpIniFile
	 *            null or already existing ini file
	 * @param project
	 * @return the ini file
	 */
	public static File createPhpIniByProject(File phpIniFile, IProject project) {

		File tempIniFile = createTemporaryPHPINIFile(phpIniFile);

		// Modify include path:
		if (project != null) {
			IncludePath[] path = PHPSearchEngine.buildIncludePath(project);
			List<String> includePath = new ArrayList<String>(path.length);
			for (IncludePath pathObject : path) {
				if (pathObject.isBuildpath()) {
					IBuildpathEntry entry = (IBuildpathEntry) pathObject
							.getEntry();
					if (entry.getEntryKind() == IBuildpathEntry.BPE_VARIABLE) {
						IPath entryPath = DLTKCore
								.getResolvedVariablePath(entry.getPath());
						includePath.add(entryPath.toFile().getAbsolutePath());
					} else if (entry.getEntryKind() == IBuildpathEntry.BPE_PROJECT
							|| entry.getEntryKind() == IBuildpathEntry.BPE_SOURCE
							|| entry.getEntryKind() == IBuildpathEntry.BPE_LIBRARY) {
						if (entry.getEntryKind() == IBuildpathEntry.BPE_PROJECT) {
							IWorkspaceRoot root = ResourcesPlugin
									.getWorkspace().getRoot();
							IResource resource = root.findMember(entry
									.getPath());
							IModelElement scriptProject = DLTKCore
									.create(resource);
							if (scriptProject instanceof IScriptProject) {
								try {
									IProjectFragment[] projectFragments = ((IScriptProject) scriptProject)
											.getProjectFragments();
									for (IProjectFragment projectFragment : projectFragments) {
										if (projectFragment.getResource() instanceof IFolder
												|| projectFragment
														.getResource() instanceof IProject)
											addIncludePath(includePath,
													projectFragment.getPath());
									}

								} catch (ModelException e) {
								}
							}
						} else {
							addIncludePath(includePath, entry.getPath());
						}
					} else if (entry.getEntryKind() == IBuildpathEntry.BPE_CONTAINER) {
						try {
							// Retries the local paths from the container
							final IScriptProject scriptProject = DLTKCore
									.create(project);
							final IBuildpathContainer buildpathContainer = DLTKCore
									.getBuildpathContainer(entry.getPath(),
											scriptProject);
							if (buildpathContainer != null) {
								final IBuildpathEntry[] buildpathEntries = buildpathContainer
										.getBuildpathEntries();
								if (buildpathEntries != null) {
									for (IBuildpathEntry iBuildpathEntry : buildpathEntries) {
										final IPath localPath = EnvironmentPathUtils
												.getLocalPath(iBuildpathEntry
														.getPath());
										includePath.add(localPath.toOSString());
									}

								}
							}

						} catch (ModelException e) {
							Logger.logException(e);
						}
					}
				} else if (pathObject.getEntry() instanceof IContainer) {
					IContainer container = (IContainer) pathObject.getEntry();
					IPath location = container.getLocation();
					if (location != null) {
						includePath.add(location.toOSString());
					}
				}
			}
			modifyIncludePath(tempIniFile,
					includePath.toArray(new String[includePath.size()]));
		}
		return tempIniFile;
	}

	private static void addIncludePath(List<String> includePath, IPath path) {
		IPath entryPath = EnvironmentPathUtils.getLocalPath(path);
		IResource resource = ResourcesPlugin.getWorkspace().getRoot()
				.findMember(entryPath);
		if (resource != null) {
			IPath location = resource.getLocation();
			if (location != null) {
				includePath.add(location.toOSString());
			}
		} else {
			includePath.add(entryPath.toOSString());
		}
	}

	/**
	 * Make some preparations before debug session:
	 * <ul>
	 * <li>Adds include path
	 * <li>Modifies Zend Debugger path in the PHP configuration file
	 * </ul>
	 * 
	 * @param phpIniFile
	 *            PHP configuration file instance
	 * @param phpExePath
	 *            Path to the PHP executable
	 * @param project
	 *            Current project
	 * @return created temporary PHP configuration file
	 */
	public static File prepareBeforeDebug(File phpIniFile, String phpExePath,
			IProject project) {
		File tempIniFile = createTemporaryPHPINIFile(phpIniFile);

		tempIniFile = createPhpIniByProject(phpIniFile, project);

		// Modify Zend Debugger extension entry:
		if (phpIniFile != null) {
			File debuggerFile = new File(
					phpIniFile.getParentFile(),
					Platform.OS_WIN32.equals(Platform.getOS()) ? "ZendDebugger.dll" : "ZendDebugger.so"); //$NON-NLS-1$ //$NON-NLS-2$
			if (debuggerFile.exists()) {
				modifyDebuggerExtensionPath(tempIniFile,
						debuggerFile.getAbsolutePath());
			}
			modifyExtensionDir(tempIniFile,
					new File(debuggerFile.getParentFile(), "ext") //$NON-NLS-1$
							.getAbsolutePath());
		}

		if (PHPDebugPlugin.DEBUG) {
			System.out.println("\nPHP.ini contents:\n---------------------"); //$NON-NLS-1$
			try {
				BufferedReader r = new BufferedReader(new FileReader(
						tempIniFile));
				String line;
				while ((line = r.readLine()) != null) {
					System.out.println(line);
				}
				r.close();
				System.out.println();
			} catch (IOException e) {
			}
		}

		return tempIniFile;
	}

	/**
	 * Creates temporary PHP configuration file and returns its instance of
	 * <code>null</code> in case of error. This file will be removed when the
	 * program exits.
	 * 
	 * @return temporary PHP configuration file instance
	 */
	public static File createTemporaryPHPINIFile() {
		return createTemporaryPHPINIFile(null);
	}

	/**
	 * Creates temporary PHP configuration file and returns its instance of
	 * <code>null</code> in case of error. This file will be removed when the
	 * program exits.
	 * 
	 * @param originalPHPIniFile
	 *            If specified - its contents will be copied to the temporary
	 *            file
	 * @return temporary PHP configuration file instance
	 */
	public static File createTemporaryPHPINIFile(File originalPHPIniFile) {
		File phpIniFile = null;
		try {
			// Create temporary directory:
			File tempDir = new File(
					System.getProperty("java.io.tmpdir"), "zend_debug"); //$NON-NLS-1$ //$NON-NLS-2$
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
				new LocalFile(originalPHPIniFile).copy(
						new LocalFile(phpIniFile), EFS.OVERWRITE,
						new NullProgressMonitor());
			}

			appendDefaultPHPIniContent(phpIniFile);
		} catch (Exception e) {
			PHPDebugPlugin.log(e);
		}
		return phpIniFile;
	}

	private static void appendDefaultPHPIniContent(File phpIniFile)
			throws IOException {

		FileWriter fw = new FileWriter(phpIniFile, true);

		// TODO expose default php.ini in PHP properties
		fw.append("\ndate.timezone= \"") //$NON-NLS-1$
				.append(Calendar.getInstance().getTimeZone().getID())
				.append("\"\n"); //$NON-NLS-1$
		fw.append("memory_limit = \"256M\"\n"); //$NON-NLS-1$
		fw.close();

	}

	/**
	 * Locate and return a PHP configuration file path for the given PHP
	 * executable. The locating is done by trying to return a PHP configuration
	 * file that is located next to the executable. The return value can be null
	 * in case it fails to locate a valid file.
	 * 
	 * @param phpExe
	 *            The PHP executable path.
	 * @return A PHP configuration file path, or <code>null</code> if it fails.
	 */
	public static File findPHPIni(String phpExe) {
		File phpExeFile = new File(phpExe);
		File phpIniFile = new File(phpExeFile.getParentFile(), PHP_INI_FILE);

		if (!phpIniFile.exists() || !phpIniFile.canRead()) {
			// Try to detect via library:
			try {
				Process p = Runtime.getRuntime().exec(
						new String[] { phpExeFile.getAbsolutePath(), "-i" }); //$NON-NLS-1$
				BufferedReader r = new BufferedReader(new InputStreamReader(
						p.getInputStream()));
				String l;
				while ((l = r.readLine()) != null) {
					int i = l.indexOf(" => "); //$NON-NLS-1$
					if (i > 0) {
						String key = l.substring(0, i);
						String value = l.substring(i + 4);
						if ("Loaded Configuration File".equals(key)) { //$NON-NLS-1$
							phpIniFile = new File(value);
							break;
						}
					}
				}
				r.close();
			} catch (IOException e) {
			}
		}

		if (phpIniFile.exists() && phpIniFile.canRead()) {
			return phpIniFile;
		}

		return null;
	}
}
