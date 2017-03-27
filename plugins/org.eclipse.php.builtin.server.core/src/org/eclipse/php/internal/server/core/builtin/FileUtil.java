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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.osgi.util.NLS;

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
			Trace.trace(Trace.SEVERE, "Error copying directory", e); //$NON-NLS-1$
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
			Trace.trace(Trace.SEVERE, "Error copying file", e); //$NON-NLS-1$
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
			Trace.trace(Trace.SEVERE, "Error copying file", e); //$NON-NLS-1$
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
			Trace.trace(Trace.SEVERE, "Error copying file", e); //$NON-NLS-1$
			return new Status(IStatus.ERROR, PHPServerPlugin.PLUGIN_ID, 0,
					NLS.bind(Messages.errorCopyingFile, new String[] { to, e.getLocalizedMessage() }), e);
		}
	}

}
