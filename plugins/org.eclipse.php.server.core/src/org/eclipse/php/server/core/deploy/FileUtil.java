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
package org.eclipse.php.server.core.deploy;

import java.io.*;
import java.net.URL;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.*;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.osgi.util.NLS;
import org.eclipse.php.server.core.Activator;
import org.eclipse.php.server.core.Logger;
import org.eclipse.php.server.core.Messages;
import org.eclipse.php.server.core.Server;
import org.eclipse.swt.widgets.Display;

/**
 * Utility class with an assortment of useful file methods.
 */
public class FileUtil {
	// size of the buffer
	private static final int BUFFER = 10240;

	// the buffer
	private static byte[] buf = new byte[BUFFER];

	/**
	 * FileUtil cannot be created. Use static methods.
	 */
	private FileUtil() {
		super();
	}

	/**
	 * Publish the project files into the server.
	 * 
	 * @param server
	 * @param configuration
	 * @param monitor
	 */
	public static boolean publish(Server server, IProject project, ILaunchConfiguration configuration, IProgressMonitor monitor) throws CoreException {
		String contextRoot = configuration.getAttribute(Server.CONTEXT_ROOT, (String) null);
		IPath to = new Path(server.getDocumentRoot());
		if (contextRoot != null && !contextRoot.equals("")) {
			to = to.append(contextRoot);
		}

		String source = project.getLocation().toOSString();
		String dest = to.toOSString();
		return smartCopyDirectory(source, dest, monitor);
	}

	/**
	 * Copies a directory from a to b.
	 *
	 * @param from java.lang.String
	 * @param to java.lang.String
	 * @param monitor a progress monitor, or <code>null</code>
	 */
	public static void copyDirectory(String from, String to, IProgressMonitor monitor) {
		try {
			File fromDir = new File(from);
			File toDir = new File(to);

			File[] files = fromDir.listFiles();

			toDir.mkdirs();

			// cycle through files
			int size = files.length;
			monitor = ProgressUtil.getMonitorFor(monitor);
			monitor.beginTask(NLS.bind(Messages.getString("FileUtil.copying"), new String[] { from, to }), size * 50); //$NON-NLS-1$

			for (int i = 0; i < size; i++) {
				File current = files[i];
				String fromFile = current.getAbsolutePath();
				String toFile = to;
				if (!toFile.endsWith(File.separator))
					toFile += File.separator;
				toFile += current.getName();
				if (current.isFile()) {
					copyFile(fromFile, toFile);
					monitor.worked(50);
				} else if (current.isDirectory()) {
					monitor.subTask(NLS.bind(Messages.getString("FileUtil.copying"), new String[] { fromFile, toFile })); //$NON-NLS-1$
					copyDirectory(fromFile, toFile, ProgressUtil.getSubMonitorFor(monitor, 50));
				}
				if (monitor.isCanceled())
					return;
			}
			monitor.done();
		} catch (Exception e) {
			Logger.logException(Messages.getString("FileUtil.errorCopyingDirectory"), e); //$NON-NLS-1$
		}
	}

	/**
	 * Copies a file from a to b. Closes the input stream after use.
	 *
	 * @param in java.io.InputStream
	 * @param to java.lang.String
	 * @return a status
	 */
	public static IStatus copyFile(InputStream in, String to) {
		OutputStream out = null;

		try {
			out = new FileOutputStream(to);

			int avail = in.read(buf);
			while (avail > 0) {
				out.write(buf, 0, avail);
				avail = in.read(buf);
			}
			return new Status(IStatus.OK, Activator.PLUGIN_ID, 0, NLS.bind(Messages.getString("FileUtil.copying"), new String[] { to }), null); //$NON-NLS-1$
		} catch (Exception e) {
			Logger.logException(Messages.getString("FileUtil.errorCopyingFile"), e); //$NON-NLS-1$
			return new Status(IStatus.ERROR, Activator.PLUGIN_ID, 0, NLS.bind(Messages.getString("FileUtil.errorCopyingFileTo"), new String[] { to, e.getLocalizedMessage() }), e); //$NON-NLS-1$
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (Exception ex) {
				// ignore
			}
			try {
				if (out != null)
					out.close();
			} catch (Exception ex) {
				// ignore
			}
		}
	}

	/**
	 * Copies a file from a to b.
	 *
	 * @param from java.lang.String
	 * @param to java.lang.String
	 * @return a status
	 */
	public static IStatus copyFile(String from, String to) {
		try {
			return copyFile(new FileInputStream(from), to);
		} catch (Exception e) {
			Logger.logException(Messages.getString("FileUtil.errorCopyingFile"), e); //$NON-NLS-1$
			return new Status(IStatus.ERROR, Activator.PLUGIN_ID, 0, NLS.bind(Messages.getString("FileUtil.errorCopyingFileTo"), new String[] { to, e.getLocalizedMessage() }), e); //$NON-NLS-1$
		}
	}

	/**
	 * Copy a file from a to b.
	 *
	 * @param from java.net.URL
	 * @param to java.lang.String
	 * @return a status
	 */
	public static IStatus copyFile(URL from, String to) {
		try {
			return copyFile(from.openStream(), to);
		} catch (Exception e) {
			Logger.logException(Messages.getString("FileUtil.errorCopyingFile"), e); //$NON-NLS-1$
			return new Status(IStatus.ERROR, Activator.PLUGIN_ID, 0, NLS.bind(Messages.getString("FileUtil.errorCopyingFileTo"), new String[] { to, e.getLocalizedMessage() }), e); //$NON-NLS-1$
		}
	}

	/**
	 * Recursively deletes a directory.
	 *
	 * @param dir java.io.File
	 * @param monitor a progress monitor, or <code>null</code>
	 */
	public static void deleteDirectory(File dir, IProgressMonitor monitor) {
		try {
			if (!dir.exists() || !dir.isDirectory())
				return;

			File[] files = dir.listFiles();
			int size = files.length;
			monitor = ProgressUtil.getMonitorFor(monitor);
			monitor.beginTask(NLS.bind(Messages.getString("FileUtil.deleting"), new String[] { dir.getAbsolutePath() }), size * 10); //$NON-NLS-1$

			// cycle through files
			for (int i = 0; i < size; i++) {
				File current = files[i];
				if (current.isFile()) {
					current.delete();
					monitor.worked(10);
				} else if (current.isDirectory()) {
					monitor.subTask(NLS.bind(Messages.getString("FileUtil.deleting"), new String[] { current.getAbsolutePath() })); //$NON-NLS-1$
					deleteDirectory(current, ProgressUtil.getSubMonitorFor(monitor, 10));
				}
			}
			dir.delete();
		} catch (Exception e) {
			Logger.logException(Messages.getString("FileUtil.errorDeletingDirectory") + dir.getAbsolutePath(), e); //$NON-NLS-1$
		} finally {
			monitor.done();
		}
	}

	/**
	 * Copys a directory from a to b, only modifying as needed
	 * and deleting old files and directories.
	 *
	 * @param from a directory
	 * @param to a directory
	 * @param monitor a progress monitor
	 * @return true, only if the copy was successful
	 */
	public static boolean smartCopyDirectory(String from, String to, IProgressMonitor monitor) {
		try {
			File fromDir = new File(from);
			File toDir = new File(to);

			File[] fromFiles = fromDir.listFiles();
			int fromSize = fromFiles.length;

			monitor = ProgressUtil.getMonitorFor(monitor);
			monitor.beginTask(NLS.bind(Messages.getString("FileUtil.copying"), new String[] { from, to }), 550); //$NON-NLS-1$

			File[] toFiles = null;

			// delete old files and directories from this directory
			if (toDir.exists() && toDir.isDirectory()) {
				toFiles = toDir.listFiles();
				int toSize = toFiles.length;

				// check if this exact file exists in the new directory
				for (int i = 0; i < toSize; i++) {
					String name = toFiles[i].getName();
					boolean isDir = toFiles[i].isDirectory();
					boolean found = false;
					for (int j = 0; j < fromSize; j++) {
						if (name.equals(fromFiles[j].getName()) && isDir == fromFiles[j].isDirectory())
							found = true;
					}

					// delete file if it can't be found or isn't the correct type
					/*					if (!found) {
					 if (isDir)
					 deleteDirectory(toFiles[i], new NullProgressMonitor());
					 else
					 toFiles[i].delete();
					 } */
					if (monitor.isCanceled())
						return false;
				}
			} else {
				if (toDir.isFile()) {
					toDir.delete();
				}
				if (!toDir.mkdirs()) {
					monitor.done();
					Display.getDefault().asyncExec(new Runnable() {
						public void run() {
							ErrorDialog.openError(Display.getDefault().getActiveShell(), "Publish Error", "Error while publishing files to the server.", new Status(IStatus.ERROR, Activator.PLUGIN_ID, 0, "Make sure you have write permissions on the server's publish directory.", null));
						}
					});
					return false;
				}
			}
			monitor.worked(50);

			// cycle through files and only copy when it doesn't exist
			// or is newer
			toFiles = toDir.listFiles();

			int toSize = 0;
			if (toFiles != null)
				toSize = toFiles.length;

			//int toSize = toFiles.length;
			int dw = 0;
			if (toSize > 0)
				dw = 500 / toSize;

			for (int i = 0; i < fromSize; i++) {
				File current = fromFiles[i];

				// check if this is a new or newer file
				boolean copy = true;
				if (!current.isDirectory()) {
					String name = current.getName();
					long mod = current.lastModified();
					for (int j = 0; j < toSize; j++) {
						if (name.equals(toFiles[j].getName()) && mod <= toFiles[j].lastModified())
							copy = false;
					}
				}

				if (copy) {
					String fromFile = current.getAbsolutePath();
					String toFile = to;
					if (!toFile.endsWith(File.separator))
						toFile += File.separator;
					toFile += current.getName();
					if (current.isFile()) {
						copyFile(fromFile, toFile);
						monitor.worked(dw);
					} else if (current.isDirectory()) {
						monitor.subTask(NLS.bind(Messages.getString("FileUtil.copying"), new String[] { fromFile, toFile })); //$NON-NLS-1$
						if (!smartCopyDirectory(fromFile, toFile, ProgressUtil.getSubMonitorFor(monitor, dw))) {
							monitor.done();
							return false;
						}
					}
				}
				if (monitor.isCanceled())
					return false;
			}
			monitor.worked(500 - dw * toSize);
			monitor.done();
			return true;
		} catch (Exception e) {
			Logger.logException("Error smart copying directory " + from + " - " + to, e); //$NON-NLS-1$ //$NON-NLS-2$
			return false;
		}
	}
}
