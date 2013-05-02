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
package org.eclipse.php.internal.core.util;

import java.io.*;

import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.php.internal.core.CoreMessages;

/**
 * File Utilities class.
 */
public class FileUtils {

	/**
	 * Checks if a resource exists under the workspace root.
	 * 
	 * @param resourcePath
	 * @return True, if the file exists; False, otherwise.
	 */
	public static boolean resourceExists(String resourcePath) {
		if (resourcePath == null || "".equals(resourcePath)) { //$NON-NLS-1$
			return false;
		}
		boolean resourceExists = false;
		try {
			IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
			IPath path = new Path(resourcePath);

			resourceExists = root.exists(path);
		} catch (Exception e) {
		}
		return resourceExists;
	}

	/**
	 * Fetch the entire contents of a text file, and return it in a String. This
	 * style of implementation does not throw Exceptions to the caller.
	 * 
	 * @param file
	 *            is a file which already exists and can be read.
	 */
	static public String getContents(File file) throws IOException {
		StringBuffer contents = new StringBuffer();

		BufferedReader input = null;
		try {
			// FileReader always assumes default encoding is OK!
			input = new BufferedReader(new FileReader(file));
			String line = null;

			while ((line = input.readLine()) != null) {
				contents.append(line);
				contents.append(System.getProperty("line.separator")); //$NON-NLS-1$
			}
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (input != null) {
					input.close();
				}
			} catch (IOException ex) {
				throw ex;
			}
		}
		return contents.toString();
	}

	/**
	 * Change the contents of text file in its entirety, overwriting any
	 * existing text.
	 * 
	 * This style of implementation throws all exceptions to the caller.
	 * 
	 * @param file
	 *            is an existing file which can be written to.
	 * @throws IllegalArgumentException
	 *             if param does not comply.
	 * @throws FileNotFoundException
	 *             if the file does not exist.
	 * @throws IOException
	 *             if problem encountered during write.
	 */
	static public void setContents(File file, String contents)
			throws FileNotFoundException, IOException {
		if (file == null) {
			throw new IllegalArgumentException(CoreMessages
					.getString("FileUtils_2")); //$NON-NLS-1$
		}
		if (!file.exists()) {
			throw new FileNotFoundException(CoreMessages
					.getString("FileUtils_3") //$NON-NLS-1$
					+ file);
		}
		if (!file.isFile()) {
			throw new IllegalArgumentException(CoreMessages
					.getString("FileUtils_4") //$NON-NLS-1$
					+ file);
		}
		if (!file.canWrite()) {
			throw new IllegalArgumentException(CoreMessages
					.getString("FileUtils_5") //$NON-NLS-1$
					+ file);
		}

		Writer output = null;
		try {
			// FileWriter always assumes default encoding is OK!
			output = new BufferedWriter(new FileWriter(file));
			output.write(contents);
		} finally {
			if (output != null)
				output.close();
		}
	}

	/**
	 * Checks if the first given path string is the container of the second
	 * given file's path
	 * 
	 * @param containerPathString
	 * @param filePathString
	 * @return
	 */
	public static boolean checkIfContainerOfFile(String containerPathString,
			String filePathString) {
		Path containerFilterPath = new Path(containerPathString.toLowerCase());
		Path filePath = new Path(filePathString.toLowerCase());
		if (containerFilterPath.segmentCount() > filePath.segmentCount()) {
			return false;// container has more segments than file itself
		}

		if (((containerFilterPath.getDevice() == null) && (filePath.getDevice() != null))
				|| ((containerFilterPath.getDevice() != null) && (filePath
						.getDevice() == null))) {
			return false;
		}

		if ((containerFilterPath.getDevice() != null)
				&& !containerFilterPath.getDevice().toLowerCase().equals(
						filePath.getDevice().toLowerCase())) {
			return false;
		}

		for (int i = 0; i < containerFilterPath.segmentCount(); i++) {
			if (!containerFilterPath.segment(i).equals(filePath.segment(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Checks the 2 given files paths are equal using device and segments
	 * comparison
	 * 
	 * @param filePathStr1
	 * @param filePathStr2
	 * @return
	 */
	public static boolean checkIfEqualFilePaths(String filePathStr1,
			String filePathStr2) {
		Path filePath1 = new Path(filePathStr1.toLowerCase());
		Path filePath2 = new Path(filePathStr2.toLowerCase());
		if (filePath1.segmentCount() != filePath2.segmentCount()) {
			return false;// container has more segments than file itself
		}

		if (((filePath1.getDevice() == null) && (filePath2.getDevice() != null))
				|| ((filePath1.getDevice() != null) && (filePath2.getDevice() == null))) {
			return false;
		}

		if ((filePath1.getDevice() != null)
				&& !filePath1.getDevice().toLowerCase().equals(
						filePath2.getDevice().toLowerCase())) {
			return false;
		}

		for (int i = 0; i < filePath1.segmentCount(); i++) {
			if (!filePath1.segment(i).equals(filePath2.segment(i))) {
				return false;
			}
		}
		return true;
	}
}
