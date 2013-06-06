/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.eclipse.php.core.tests.filenetwork;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.eclipse.dltk.core.DLTKCore;

public class DltkUtil {

	/**
	 * Generate a display string from the given String.
	 * 
	 * @param inputString
	 *            the given input string
	 * 
	 */
	public static String displayString(String inputString) {
		return displayString(inputString, 0);
	}

	/**
	 * Generate a display string from the given String. It converts:
	 * <ul>
	 * <li>\t to \t</li>
	 * <li>\r to \\r</li>
	 * <li>\n to \n</li>
	 * <li>\b to \\b</li>
	 * <li>\f to \\f</li>
	 * <li>\" to \\\"</li>
	 * <li>\' to \\'</li>
	 * <li>\\ to \\\\</li>
	 * <li>All other characters are unchanged.</li>
	 * </ul>
	 * This method doesn't convert \r\n to \n.
	 * <p>
	 * Example of use: <o>
	 * <li>
	 * 
	 * <pre>
	 * input string = &quot;abc\ndef\tghi&quot;,
	 * indent = 3
	 * result = &quot;\&quot;\t\t\tabc\\n&quot; +
	 * 			&quot;\t\t\tdef\tghi\&quot;&quot;
	 * </pre>
	 * 
	 * </li>
	 * <li>
	 * 
	 * <pre>
	 * input string = &quot;abc\ndef\tghi\n&quot;,
	 * indent = 3
	 * result = &quot;\&quot;\t\t\tabc\\n&quot; +
	 * 			&quot;\t\t\tdef\tghi\\n\&quot;&quot;
	 * </pre>
	 * 
	 * </li>
	 * <li>
	 * 
	 * <pre>
	 * input string = &quot;abc\r\ndef\tghi\r\n&quot;,
	 * indent = 3
	 * result = &quot;\&quot;\t\t\tabc\\r\\n&quot; +
	 * 			&quot;\t\t\tdef\tghi\\r\\n\&quot;&quot;
	 * </pre>
	 * 
	 * </li>
	 * </ol>
	 * </p>
	 * 
	 * @param inputString
	 *            the given input string
	 * @param indent
	 *            number of tabs are added at the begining of each line.
	 * 
	 * @return the displayed string
	 */
	public static String displayString(String inputString, int indent) {
		return displayString(inputString, indent, false);
	}

	public static String displayString(String inputString, int indent,
			boolean shift) {
		if (inputString == null)
			return "null";
		int length = inputString.length();
		StringBuffer buffer = new StringBuffer(length);
		java.util.StringTokenizer tokenizer = new java.util.StringTokenizer(
				inputString, "\n\r", true);
		for (int i = 0; i < indent; i++)
			buffer.append("\t");
		if (shift)
			indent++;
		buffer.append("\"");
		while (tokenizer.hasMoreTokens()) {

			String token = tokenizer.nextToken();
			if (token.equals("\r")) {
				buffer.append("\\r");
				if (tokenizer.hasMoreTokens()) {
					token = tokenizer.nextToken();
					if (token.equals("\n")) {
						buffer.append("\\n");
						if (tokenizer.hasMoreTokens()) {
							buffer.append("\" + \n");
							for (int i = 0; i < indent; i++)
								buffer.append("\t");
							buffer.append("\"");
						}
						continue;
					}
					buffer.append("\" + \n");
					for (int i = 0; i < indent; i++)
						buffer.append("\t");
					buffer.append("\"");
				} else {
					continue;
				}
			} else if (token.equals("\n")) {
				buffer.append("\\n");
				if (tokenizer.hasMoreTokens()) {
					buffer.append("\" + \n");
					for (int i = 0; i < indent; i++)
						buffer.append("\t");
					buffer.append("\"");
				}
				continue;
			}

			StringBuffer tokenBuffer = new StringBuffer();
			for (int i = 0; i < token.length(); i++) {
				char c = token.charAt(i);
				switch (c) {
				case '\r':
					tokenBuffer.append("\\r");
					break;
				case '\n':
					tokenBuffer.append("\\n");
					break;
				case '\b':
					tokenBuffer.append("\\b");
					break;
				case '\t':
					tokenBuffer.append("\t");
					break;
				case '\f':
					tokenBuffer.append("\\f");
					break;
				case '\"':
					tokenBuffer.append("\\\"");
					break;
				case '\'':
					tokenBuffer.append("\\'");
					break;
				case '\\':
					tokenBuffer.append("\\\\");
					break;
				default:
					tokenBuffer.append(c);
				}
			}
			buffer.append(tokenBuffer.toString());
		}
		buffer.append("\"");
		return buffer.toString();
	}

	public static String convertToIndependantLineDelimiter(String source) {
		if (source.indexOf('\n') == -1 && source.indexOf('\r') == -1)
			return source;
		StringBuffer buffer = new StringBuffer();
		for (int i = 0, length = source.length(); i < length; i++) {
			char car = source.charAt(i);
			if (car == '\r') {
				buffer.append('\n');
				if (i < length - 1 && source.charAt(i + 1) == '\n') {
					i++; // skip \n after \r
				}
			} else {
				buffer.append(car);
			}
		}
		return buffer.toString();
	}

	/**
	 * Unzip the contents of the given zip in the given directory (create it if
	 * it doesn't exist)
	 */
	public static void unzip(String zipPath, String destDirPath)
			throws IOException {

		InputStream zipIn = new FileInputStream(zipPath);
		byte[] buf = new byte[8192];
		File destDir = new File(destDirPath);
		ZipInputStream zis = new ZipInputStream(zipIn);
		FileOutputStream fos = null;
		try {
			ZipEntry zEntry;
			while ((zEntry = zis.getNextEntry()) != null) {
				// if it is empty directory, create it
				if (zEntry.isDirectory()) {
					new File(destDir, zEntry.getName()).mkdirs();
					continue;
				}
				// if it is a file, extract it
				File outFile = new File(destDir, zEntry.getName());
				// create directory for a file
				outFile.getParentFile().mkdirs();
				// write file
				fos = new FileOutputStream(outFile);
				int n = 0;
				while ((n = zis.read(buf)) >= 0) {
					fos.write(buf, 0, n);
				}
				fos.close();
			}
		} catch (IOException ioe) {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException ioe2) {
				}
			}
		} finally {
			try {
				zipIn.close();
				if (zis != null)
					zis.close();
			} catch (IOException ioe) {
			}
		}
	}

	public static void copyFiles(String from, String to) {
		File file = new File(from);
		File toFolder = new File(to);
		if (!file.exists() || !file.isDirectory()) {
			return;
		}
		File[] files = file.listFiles();
		for (int i = 0; i < files.length; i++) {
			File child = new File(toFolder, files[i].getName());
			if (files[i].isDirectory()) {
				child.mkdir();
				copyFiles(files[i].getAbsolutePath(), child.getAbsolutePath());
			} else if (files[i].isFile()) {
				BufferedInputStream input;
				try {
					input = new BufferedInputStream(new FileInputStream(
							files[i]));
					org.eclipse.dltk.compiler.util.Util.copy(child, input);
					input.close();
				} catch (FileNotFoundException e) {
					if (DLTKCore.DEBUG) {
						e.printStackTrace();
					}
				} catch (IOException e) {
					if (DLTKCore.DEBUG) {
						e.printStackTrace();
					}
				}
			}
			child.setLastModified(files[i].lastModified());
		}
	}
}
