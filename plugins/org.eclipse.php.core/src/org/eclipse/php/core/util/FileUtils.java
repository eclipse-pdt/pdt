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
package org.eclipse.php.core.util;

import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

/**
 * File Utilities class.
 */
public class FileUtils {

	/**
	 * Checks if a file exists under the workspace root.
	 * 
	 * @param filePath
	 * @return True, if the file exists; False, otherwise.
	 */
	public static boolean fileExists(String filePath) {
		if (filePath == null || "".equals(filePath)) {
			return false;
		}
		boolean fileExists = false;
		try {
			IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
			IPath path = new Path(filePath);

			fileExists = root.exists(path);
		} catch (Exception e) {
		}
		return fileExists;
	}

}
