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
/**
 *
 */
package org.eclipse.php.internal.core.util;

import java.util.regex.Pattern;

import org.eclipse.core.runtime.IPath;

/**
 * This class gives workaround of the fact Path cannot handle relative paths (./
 * and ../). It allows to replace .. & . with || & . and backwards.
 * 
 * @author seva
 * 
 */
public class IncludeStringHack {

	private static final Pattern DOUBLE_DOT_WITH_SLASH = Pattern
			.compile("\\.{2}([\\/])"); //$NON-NLS-1$
	private static final String DOUBLE_PIPE_WITH_SLASH_REPLACEMENT = "||$1"; //$NON-NLS-1$
	private static final Pattern DOUBLE_DOT_AT_END = Pattern.compile("\\.{2}$"); //$NON-NLS-1$
	private static final String DOUBLE_PIPE_AT_END_REPLACEMENT = "||"; //$NON-NLS-1$
	private static final Pattern SINGLE_DOT_WITH_SLASH = Pattern
			.compile("\\.{1}([\\/])"); //$NON-NLS-1$
	private static final String SINGLE_PIPE_WITH_SLASH_REPLACEMENT = "|$1"; //$NON-NLS-1$
	private static final Pattern SINGLE_DOT_AT_END = Pattern.compile("\\.{1}$"); //$NON-NLS-1$
	private static final String SINGLE_PIPE_AT_END_REPLACEMENT = "|"; //$NON-NLS-1$

	private static final Pattern DOUBLE_PIPE_WITH_SLASH = Pattern
			.compile("\\|{2}([\\/])"); //$NON-NLS-1$
	private static final String DOUBLE_DOT_WITH_SLASH_REPLACEMENT = "..$1"; //$NON-NLS-1$
	private static final Pattern DOUBLE_PIPE_AT_END = Pattern
			.compile("\\|{2}$"); //$NON-NLS-1$
	private static final String DOUBLE_DOT_AT_END_REPLACEMENT = ".."; //$NON-NLS-1$
	private static final Pattern SINGLE_PIPE_WITH_SLASH = Pattern
			.compile("\\|{1}([\\/])"); //$NON-NLS-1$
	private static final String SINGLE_DOT_WITH_SLASH_REPLACEMENT = ".$1"; //$NON-NLS-1$
	private static final Pattern SINGLE_PIPE_AT_END = Pattern
			.compile("\\|{1}$"); //$NON-NLS-1$
	private static final String SINGLE_DOT_AT_END_REPLACEMENT = "."; //$NON-NLS-1$

	public static String hack(String includeString) {
		includeString = DOUBLE_DOT_WITH_SLASH.matcher(includeString)
				.replaceAll(DOUBLE_PIPE_WITH_SLASH_REPLACEMENT);
		includeString = DOUBLE_DOT_AT_END.matcher(includeString).replaceAll(
				DOUBLE_PIPE_AT_END_REPLACEMENT);
		includeString = SINGLE_DOT_WITH_SLASH.matcher(includeString)
				.replaceAll(SINGLE_PIPE_WITH_SLASH_REPLACEMENT);
		includeString = SINGLE_DOT_AT_END.matcher(includeString).replaceAll(
				SINGLE_PIPE_AT_END_REPLACEMENT);
		return includeString;
	}

	public static String unhack(String includeString) {
		includeString = DOUBLE_PIPE_WITH_SLASH.matcher(includeString)
				.replaceAll(DOUBLE_DOT_WITH_SLASH_REPLACEMENT);
		includeString = DOUBLE_PIPE_AT_END.matcher(includeString).replaceAll(
				DOUBLE_DOT_AT_END_REPLACEMENT);
		includeString = SINGLE_PIPE_WITH_SLASH.matcher(includeString)
				.replaceAll(SINGLE_DOT_WITH_SLASH_REPLACEMENT);
		includeString = SINGLE_PIPE_AT_END.matcher(includeString).replaceAll(
				SINGLE_DOT_AT_END_REPLACEMENT);
		return includeString;
	}

	public static boolean isHacked(IPath includePath) {
		for (int i = 0; i < includePath.segmentCount(); ++i) {
			if (includePath.segment(i).equals("|") || includePath.segment(i).equals(DOUBLE_PIPE_AT_END_REPLACEMENT)) { //$NON-NLS-1$
				return true;
			}
		}
		return false;
	}
}
