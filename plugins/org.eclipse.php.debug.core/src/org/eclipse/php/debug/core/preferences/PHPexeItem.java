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
package org.eclipse.php.debug.core.preferences;

import java.io.File;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;


public class PHPexeItem {

	private static final char fgSeparator = File.separatorChar;

	private static final String[] fgCandidatePHPLocations = { "php", //$NON-NLS-2$ //$NON-NLS-1$
		"php.exe", //$NON-NLS-2$ //$NON-NLS-1$
		//		"jre" + fgSeparator + "bin" + fgSeparator + "javaw",          //$NON-NLS-3$ //$NON-NLS-2$ //$NON-NLS-1$
		//		"jre" + fgSeparator + "bin" + fgSeparator + "javaw.exe",      //$NON-NLS-3$ //$NON-NLS-2$ //$NON-NLS-1$									
		//		"bin" + fgSeparator + "java",                                 //$NON-NLS-2$ //$NON-NLS-1$
		//		"bin" + fgSeparator + "java.exe",                             //$NON-NLS-2$ //$NON-NLS-1$
		//		"jre" + fgSeparator + "bin" + fgSeparator + "java",           //$NON-NLS-3$ //$NON-NLS-2$ //$NON-NLS-1$
		//		"jre" + fgSeparator + "bin" + fgSeparator + "java.exe"};
	};

	String name;
	File location;
	File phpEXE;
	String version;
	boolean editable=true;
	

	public PHPexeItem(String name, String path) {
		this.name = name;
		setLocation( new File(path));
	}

	public PHPexeItem(String name, File phpExeFile,boolean editable) {
		this.name = name;
		phpEXE=phpExeFile;
		this.editable=editable;
		location=phpExeFile.getParentFile();
	}

	public PHPexeItem() {
	}

	public File getLocation() {
		return location;
	}

	public void setLocation(File location) {
		this.location = location;
		phpEXE=findPHPExecutable(location);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public static File findPHPExecutable(File phpLocation) {

		// Try each candidate in order.  The first one found wins.  Thus, the order
		// of fgCandidateJavaLocations is significant.
		for (int i = 0; i < fgCandidatePHPLocations.length; i++) {
			File javaFile = new File(phpLocation, fgCandidatePHPLocations[i]);
			if (javaFile.isFile()) {
				return javaFile;
			}
		}
		return null;
	}

	public File getPhpEXE() {
		return phpEXE;
	}

	public boolean equals(Object other) {
		if (other==this)
			return true;
		if (!(other instanceof PHPexeItem))
			return false;
		PHPexeItem item2=(PHPexeItem)other;
		return item2.name.equals(name) && item2.location.equals(location);
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public boolean isEditable() {
		return editable;
	}
}
