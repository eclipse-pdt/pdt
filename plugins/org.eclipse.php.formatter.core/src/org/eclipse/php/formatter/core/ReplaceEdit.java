/*******************************************************************************
 * Copyright (c) 2013 Zend Techologies Ltd.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Zend Technologies Ltd. - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.formatter.core;

/**
 * Represent a single document replacement
 * 
 * @author moshe, 2007
 */
public class ReplaceEdit {
	public final int offset;
	public final int length;
	public final String content;

	private static final String SPACE = " "; //$NON-NLS-1$
	private static final String NEW_LINE = System.getProperty("line.separator"); //$NON-NLS-1$

	public ReplaceEdit(int offset, int length, String replacement) {
		this.offset = offset;
		this.length = length;
		this.content = replacement;
	}

	public int getEnd() {
		return offset + length;
	}

	public String toString() {
		String result = content;
		if (NEW_LINE.equals(result)) {
			result = "NEW LINE"; //$NON-NLS-1$
		}

		if (SPACE.equals(result)) {
			result = "SPACE"; //$NON-NLS-1$
		}

		if ("".equals(result)) { //$NON-NLS-1$
			result = "DELETE"; //$NON-NLS-1$
		}
		return "REPLACE:[" + offset + "\t," + length + "] \t- \t< " + result //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				+ " >\n"; //$NON-NLS-1$
	}
}
