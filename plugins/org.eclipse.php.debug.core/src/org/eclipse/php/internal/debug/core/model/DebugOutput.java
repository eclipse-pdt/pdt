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
package org.eclipse.php.internal.debug.core.model;

public class DebugOutput {

	private static final String CONTENT_TYPE_HEADER = "Content-Type:"; //$NON-NLS-1$

	private StringBuffer fOutput = new StringBuffer();
	private int fUpdateCount = 0;
	private String contentType;

	/**
	 * Append debug output data
	 * 
	 * @param data
	 */
	public void append(String data) {
		fOutput.append(data);
		fUpdateCount++;
	}

	/**
	 * Adds debug output header
	 * 
	 * @param header
	 *            String
	 */
	public void appendHeader(String header) {
		if (header != null) {
			if (header.startsWith(CONTENT_TYPE_HEADER)) {
				String contentType = header.substring(
						CONTENT_TYPE_HEADER.length()).trim().toLowerCase();
				int i = contentType.indexOf(';');
				if (i > 0) {
					this.contentType = contentType.substring(0, i).trim();
					// String[] parameters =
					// contentType.substring(i+1).split(";");
					// for (String param : parameters) {
					// int k = param.indexOf('=');
					// if (k > 0) {
					// String key = param.substring(0, k).trim();
					// String value = param.substring(k+1).trim();
					// }
					// }
				} else {
					this.contentType = contentType;
				}
			}
		}
		fOutput.append(header);
		fUpdateCount++;
	}

	/**
	 * Returns how many times this debug output was updated with additional
	 * data.
	 * 
	 * @return
	 */
	public int getUpdateCount() {
		return fUpdateCount;
	}

	/**
	 * Returns content type determined from debug output header, or
	 * <code>null</code> in case if it couldn't be detected (Content-Type header
	 * was missing?)
	 * 
	 * @see #appendHeader(String)
	 * @return
	 */
	public String getContentType() {
		return contentType;
	}

	public String toString() {
		return fOutput.toString();
	}
}
