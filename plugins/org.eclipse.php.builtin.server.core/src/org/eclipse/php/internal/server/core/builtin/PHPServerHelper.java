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

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;

/**
 * Utility class for methods that are used by more that one version of PHP
 * Server. Use of these methods makes it clear that more than one version will
 * be impacted by changes.
 *
 */
public class PHPServerHelper {

	/**
	 * Reads the from the specified InputStream and returns the result as a
	 * String. Each line is terminated by &quot;\n&quot;. Returns whatever is
	 * read regardless of any errors that occurs while reading.
	 * 
	 * @param stream
	 *            InputStream for the contents to be read
	 * @return contents read
	 * @throws IOException
	 *             if error occurs closing the stream
	 */
	public static String getFileContents(InputStream stream) throws IOException {
		BufferedReader br = null;
		StringBuffer sb = new StringBuffer();
		try {
			br = new BufferedReader(new InputStreamReader(stream));
			String temp = br.readLine();
			while (temp != null) {
				sb.append(temp).append("\n"); //$NON-NLS-1$
				temp = br.readLine();
			}
		} catch (Exception e) {
			Trace.trace(Trace.WARNING, "Could not load file contents.", e); //$NON-NLS-1$
		} finally {
			if (br != null)
				br.close();
		}
		return sb.toString();
	}

	/**
	 * Gets the base directory for this server. This directory is used as the
	 * "base" property for the server.
	 * 
	 * @param ps
	 *            PHPServer from which to derive the base directory directory.
	 *            Only used to get the temp directory if needed.
	 * @return path to base directory
	 */
	public static IPath getStandardBaseDirectory(PHPServer ps) {
		String baseDir = ps.getDocumentRootDirectory();
		// If test mode and no instance directory specified, use temporary
		// directory
		if (baseDir == null) {
			PHPServerBehaviour tsb = (PHPServerBehaviour) ps.getServer().loadAdapter(PHPServerBehaviour.class, null);
			return tsb.getTempDirectory();
		}
		IPath path = new Path(baseDir);
		if (!path.isAbsolute()) {
			IPath rootPath = ResourcesPlugin.getWorkspace().getRoot().getLocation();
			path = rootPath.append(path);
		}
		// Return specified instance directory
		return path;
	}

	/**
	 * Creates the specified deployment directory if it does not already exist.
	 * It will include a default ROOT web application using the specified
	 * web.xml.
	 * 
	 * @param deployDir
	 *            path to deployment directory to create
	 * @param webxml
	 *            web.xml context to use for the ROOT web application.
	 * @return result status of the operation
	 */
	public static IStatus createDeploymentDirectory(IPath deployDir) {
		if (Trace.isTraceEnabled())
			Trace.trace(Trace.FINER, "Creating deployment directory at " + deployDir.toOSString()); //$NON-NLS-1$

		File temp = deployDir.toFile();
		if (!temp.exists())
			temp.mkdirs();

		return Status.OK_STATUS;
	}
}
