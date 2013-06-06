/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.eclipse.php.core.tests.filenetwork;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.Set;

public class FileUtil {

	public static void copyDirectory(File source, File target)
			throws IOException {
		copyDirectory(source, target, Collections.<File> emptySet());
	}

	public static void copyDirectory(File source, File target,
			Set<File> excludes) throws IOException {
		if (!target.exists()) {
			target.mkdirs();
		}
		final File[] files = source.listFiles();
		if (files == null) {
			throw new IllegalStateException("Source directory " + source
					+ " doesn't exist");
		}
		for (int i = 0; i < files.length; i++) {
			final File sourceChild = files[i];
			final String name = sourceChild.getName();
			if (name.equals("CVS") || name.equals(".svn"))
				continue;
			if (excludes.contains(sourceChild))
				continue;
			final File targetChild = new File(target, name);
			if (sourceChild.isDirectory()) {
				copyDirectory(sourceChild, targetChild, excludes);
			} else {
				if (".emptydir".equals(name)) {
					continue;
				}
				copyFile(sourceChild, targetChild);
			}
		}
	}

	/**
	 * Copy file from src (path to the original file) to dest (path to the
	 * destination file).
	 */
	public static void copyFile(File src, File dest) throws IOException {
		InputStream in = null;
		OutputStream out = null;
		byte[] buffer = new byte[12 * 1024];
		int read;

		try {
			in = new FileInputStream(src);

			try {
				out = new FileOutputStream(dest);

				while ((read = in.read(buffer)) != -1) {
					out.write(buffer, 0, read);
				}
			} finally {
				if (out != null) {
					out.close();
				}
			}
		} finally {
			if (in != null) {
				in.close();
			}
		}
	}

}
