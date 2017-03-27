/*******************************************************************************
 * Copyright (c) 2009, 2016 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.server.core.builtin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.osgi.util.NLS;
import org.eclipse.wst.server.core.util.PublishHelper;

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
	 * Copys a directory from a to b.
	 *
	 * @param from
	 *            java.lang.String
	 * @param to
	 *            java.lang.String
	 * @param monitor
	 *            a progress monitor, or <code>null</code>
	 */
	public static void copyDirectory(String from, String to, IProgressMonitor monitor) {
		try {
			File fromDir = new File(from);
			File toDir = new File(to);

			File[] files = fromDir.listFiles();

			toDir.mkdir();

			// cycle through files
			int size = files.length;
			monitor = ProgressUtil.getMonitorFor(monitor);
			monitor.beginTask(NLS.bind(Messages.copyingTask, new String[] { from, to }), size * 50);

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
					monitor.subTask(NLS.bind(Messages.copyingTask, new String[] { fromFile, toFile }));
					copyDirectory(fromFile, toFile, ProgressUtil.getSubMonitorFor(monitor, 50));
				}
				if (monitor.isCanceled())
					return;
			}
			monitor.done();
		} catch (Exception e) {
			Trace.trace(Trace.SEVERE, "Error copying directory", e);
		}
	}

	/**
	 * Copy a file from a to b. Closes the input stream after use.
	 *
	 * @param in
	 *            java.io.InputStream
	 * @param to
	 *            java.lang.String
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
			return Status.OK_STATUS;
		} catch (Exception e) {
			Trace.trace(Trace.SEVERE, "Error copying file", e);
			return new Status(IStatus.ERROR, PHPServerPlugin.PLUGIN_ID, 0,
					NLS.bind(Messages.errorCopyingFile, new String[] { to, e.getLocalizedMessage() }), e);
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
	 * Copy a file from a to b.
	 *
	 * @param from
	 *            java.lang.String
	 * @param to
	 *            java.lang.String
	 * @return a status
	 */
	public static IStatus copyFile(String from, String to) {
		try {
			return copyFile(new FileInputStream(from), to);
		} catch (Exception e) {
			Trace.trace(Trace.SEVERE, "Error copying file", e);
			return new Status(IStatus.ERROR, PHPServerPlugin.PLUGIN_ID, 0,
					NLS.bind(Messages.errorCopyingFile, new String[] { to, e.getLocalizedMessage() }), e);
		}
	}

	/**
	 * Copy a file from a to b.
	 *
	 * @param from
	 *            java.net.URL
	 * @param to
	 *            java.lang.String
	 * @return a status
	 */
	public static IStatus copyFile(URL from, String to) {
		try {
			return copyFile(from.openStream(), to);
		} catch (Exception e) {
			Trace.trace(Trace.SEVERE, "Error copying file", e);
			return new Status(IStatus.ERROR, PHPServerPlugin.PLUGIN_ID, 0,
					NLS.bind(Messages.errorCopyingFile, new String[] { to, e.getLocalizedMessage() }), e);
		}
	}

	/**
	 * Copys a directory from a to b, only modifying as needed and deleting old
	 * files and directories.
	 *
	 * @param from
	 *            a directory
	 * @param to
	 *            a directory
	 * @param monitor
	 *            a progress monitor
	 * @deprecated will be removed in next release
	 */
	public static void smartCopyDirectory(String from, String to, IProgressMonitor monitor) {
		try {
			File fromDir = new File(from);
			File toDir = new File(to);

			File[] fromFiles = fromDir.listFiles();
			int fromSize = fromFiles.length;

			monitor = ProgressUtil.getMonitorFor(monitor);
			monitor.beginTask(NLS.bind(Messages.copyingTask, new String[] { from, to }), 550);

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

					// delete file if it can't be found or isn't the correct
					// type
					if (!found) {
						if (isDir)
							PublishHelper.deleteDirectory(toFiles[i], null);
						else
							toFiles[i].delete();
					}
					if (monitor.isCanceled())
						return;
				}
			} else {
				if (toDir.isFile())
					toDir.delete();
				toDir.mkdir();
			}
			monitor.worked(50);

			// cycle through files and only copy when it doesn't exist
			// or is newer
			toFiles = toDir.listFiles();
			int toSize = toFiles.length;
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
						monitor.subTask(NLS.bind(Messages.copyingTask, new String[] { fromFile, toFile }));
						smartCopyDirectory(fromFile, toFile, ProgressUtil.getSubMonitorFor(monitor, dw));
					}
				}
				if (monitor.isCanceled())
					return;
			}
			monitor.worked(500 - dw * toSize);
			monitor.done();
		} catch (Exception e) {
			Trace.trace(Trace.SEVERE, "Error smart copying directory " + from + " - " + to, e);
		}
	}
}
