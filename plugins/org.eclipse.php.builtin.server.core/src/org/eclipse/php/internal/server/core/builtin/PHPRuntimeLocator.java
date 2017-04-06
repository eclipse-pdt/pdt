/*******************************************************************************
 * Copyright (c) 2017 Alex Xu and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Alex Xu - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.server.core.builtin;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.php.internal.debug.core.PHPExeUtil;
import org.eclipse.php.internal.debug.core.PHPExeUtil.PHPModuleInfo;
import org.eclipse.php.internal.debug.core.debugger.AbstractDebuggerConfiguration;
import org.eclipse.php.internal.debug.core.preferences.PHPDebuggersRegistry;
import org.eclipse.php.internal.debug.core.preferences.PHPexeItem;
import org.eclipse.php.internal.debug.core.preferences.PHPexes;
import org.eclipse.wst.server.core.IRuntimeType;
import org.eclipse.wst.server.core.IRuntimeWorkingCopy;
import org.eclipse.wst.server.core.ServerCore;
import org.eclipse.wst.server.core.model.RuntimeLocatorDelegate;

@SuppressWarnings("restriction")
public class PHPRuntimeLocator extends RuntimeLocatorDelegate {

	private static final String[] PHP_CANDIDATE_BIN = { "php", "php-cli", //$NON-NLS-1$ //$NON-NLS-2$
			"php-cgi", "php.exe", "php-cli.exe", "php-cgi.exe" }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$

	protected static final String[] runtimeTypes = new String[] { "org.eclipse.php.server.runtime.54", //$NON-NLS-1$
			"org.eclipse.php.server.runtime.55", "org.eclipse.php.server.runtime.56", //$NON-NLS-1$ //$NON-NLS-2$
			"org.eclipse.php.server.runtime.70", "org.eclipse.php.server.runtime.71" }; //$NON-NLS-1$ //$NON-NLS-2$

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.wst.server.core.model.IRuntimeFactoryDelegate#
	 * getKnownRuntimes()
	 */
	@Override
	public void searchForRuntimes(IPath path, IRuntimeSearchListener listener, IProgressMonitor monitor) {

		final List<File> locations = new ArrayList<File>();
		final List<PHPexeItem> found = new ArrayList<PHPexeItem>();

		searchForRuntimes2(path.toFile(), locations, monitor);

		if (!locations.isEmpty()) {
			monitor.setTaskName(Messages.PHPRuntimeLocator_Processing_search_results);
			Iterator<File> iter2 = locations.iterator();
			while (iter2.hasNext()) {
				if (monitor.isCanceled())
					break;
				File location = iter2.next();
				PHPexeItem phpExe = new PHPexeItem(null, location, null, null, true);
				if (phpExe.getName() == null)
					continue;
				String nameCopy = new String(phpExe.getName());
				monitor.subTask(MessageFormat.format(Messages.PHPRuntimeLocator_Fetching_php_exe_info, nameCopy));
				List<PHPModuleInfo> modules = PHPExeUtil.getModules(phpExe);
				AbstractDebuggerConfiguration[] debuggers = PHPDebuggersRegistry.getDebuggersConfigurations();
				for (AbstractDebuggerConfiguration debugger : debuggers) {
					for (PHPModuleInfo m : modules)
						if (m.getName().equalsIgnoreCase(debugger.getModuleId())) {
							phpExe.setDebuggerID(debugger.getDebuggerId());
							break;
						}
				}
				if (phpExe.getDebuggerID() == null)
					phpExe.setDebuggerID(PHPDebuggersRegistry.NONE_DEBUGGER_ID);
				phpExe.setName(nameCopy);
				phpExe.setLoadDefaultINI(true);
				if (phpExe.getExecutable() != null) {
					found.add(phpExe);
				}
			}
		}

		for (PHPexeItem phpExe : found) {
			IRuntimeWorkingCopy runtime = getRuntimeFromDir(phpExe, monitor);
			if (runtime != null) {
				PHPexes.getInstance().addItem(phpExe);
				listener.runtimeFound(runtime);
			}
		}
	}

	/**
	 * Searches the specified directory recursively for installed PHP
	 * executables, adding each detected executable to the <code>found</code>
	 * list. Any directories specified in the <code>ignore</code> are not
	 * traversed.
	 * 
	 * @param directory
	 * @param found
	 * @param types
	 * @param ignore
	 */
	protected void searchForRuntimes2(final File directory, final List<File> found, final IProgressMonitor monitor) {
		if (monitor.isCanceled())
			return;
		// Search the root directory
		List<File> foundExecs = findPHPExecutable(directory);
		if (!foundExecs.isEmpty()) {
			found.addAll(foundExecs);
			monitor.setTaskName(MessageFormat.format(Messages.PHPRuntimeLocator_Searching_with_found, found.size()));
		}
		final String[] names = directory.list();
		if (names == null)
			return;
		final List<File> subDirs = new ArrayList<File>();
		for (String element : names) {
			if (monitor.isCanceled())
				return;
			final File file = new File(directory, element);
			if (file.isDirectory()) {
				try {
					monitor.subTask(MessageFormat.format(Messages.PHPRuntimeLocator_14, file.getCanonicalPath()));
				} catch (final IOException e) {
				}
				if (monitor.isCanceled())
					return;
				subDirs.add(file);
			}
		}
		while (!subDirs.isEmpty()) {
			final File subDir = subDirs.remove(0);
			searchForRuntimes2(subDir, found, monitor);
			if (monitor.isCanceled())
				return;
		}
	}

	/**
	 * Locate a PHP executable file in the PHP location given to this method.
	 * The location should be a directory. The search is done for php and
	 * php.exe only.
	 * 
	 * @param phpLocation
	 *            A directory that might hold a PHP executable.
	 * @return A PHP executable file.
	 */
	private static List<File> findPHPExecutable(File phpLocation) {
		List<File> found = new ArrayList<File>(0);
		for (String element : PHP_CANDIDATE_BIN) {
			File phpExecFile = new File(phpLocation, element);
			if (phpExecFile.exists() && !phpExecFile.isDirectory()) {
				found.add(phpExecFile);
			}
		}
		return found;
	}

	protected static IRuntimeWorkingCopy getRuntimeFromDir(PHPexeItem phpExe, IProgressMonitor monitor) {
		for (int i = 0; i < runtimeTypes.length; i++) {
			try {
				IRuntimeType runtimeType = ServerCore.findRuntimeType(runtimeTypes[i]);
				String absolutePath = phpExe.getExecutable().getParent();
				String id = absolutePath.replace(File.separatorChar, '_').replace(':', '-');
				IRuntimeWorkingCopy runtime = runtimeType.createRuntime(id, monitor);

				runtime.setName(phpExe.getName());
				runtime.setLocation(new Path(absolutePath));
				PHPRuntime phpRuntime = (PHPRuntime) runtime.loadAdapter(PHPRuntime.class, monitor);
				if (phpRuntime != null)
					phpRuntime.setExecutableInstall(phpExe);
				IStatus status = runtime.validate(monitor);
				if (status == null || status.getSeverity() != IStatus.ERROR)
					return runtime;
				Trace.trace(Trace.FINER, "False runtime found at " + absolutePath + ": " + status.getMessage()); //$NON-NLS-1$ //$NON-NLS-2$
			} catch (Exception e) {
				Trace.trace(Trace.SEVERE, "Could not find runtime", e); //$NON-NLS-1$
			}
		}
		return null;
	}

}
