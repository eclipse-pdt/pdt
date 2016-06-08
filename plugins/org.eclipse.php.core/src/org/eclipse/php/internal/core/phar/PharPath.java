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
package org.eclipse.php.internal.core.phar;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

public class PharPath {

	private String pharName;
	private String folder;
	private String file;

	private PharPath(String pharName, String folder, String file) {
		super();
		this.pharName = pharName;
		this.folder = folder;
		this.file = file;
	}

	public String getPharName() {
		return pharName;
	}

	public void setPharName(String pharName) {
		this.pharName = pharName;
	}

	public String getFolder() {
		return folder;
	}

	public void setFolder(String folder) {
		this.folder = folder;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public boolean isPhar() {
		return (folder == null || StringUtils.isBlank(folder)) && (file == null || StringUtils.isBlank(file));
	}

	public static PharPath getPharPath(IPath path) {
		if (!isPharPath(path)) {
			return null;
		}
		String pharName;
		String folder = PharConstants.EMPTY_STRING;
		String file = PharConstants.EMPTY_STRING;
		if (PharConstants.WINDOWS) {
			path = path.setDevice(null);
		} else {
			path = new Path(path.toString().substring(PharConstants.PHAR_PREFIX.length()));
		}

		String pathString = path.toString();
		int index = pathString.indexOf(PharConstants.PHAR_EXTENSION_WITH_DOT);
		if (index >= 0) {
			index += PharConstants.PHAR_EXTENSION_WITH_DOT.length();
			if (PharConstants.WINDOWS && pathString.startsWith(PharConstants.SPLASH)) {
				pharName = pathString.substring(1, index);
			} else {
				pharName = pathString.substring(0, index);
			}

			pathString = pathString.substring(index);
			path = new Path(pathString);
			if (path.segmentCount() > 0) {
				file = path.lastSegment();
				path = path.removeLastSegments(1);
				if (path.segmentCount() > 0) {
					folder = path.toString().substring(1);
				}

			}

			return new PharPath(pharName, folder, file);
		}
		return null;
	}

	public static boolean isPharPath(IPath path) {
		if (path.toString().startsWith(PharConstants.PHAR_PREFIX)) {
			return true;
		}
		return false;
	}

}
