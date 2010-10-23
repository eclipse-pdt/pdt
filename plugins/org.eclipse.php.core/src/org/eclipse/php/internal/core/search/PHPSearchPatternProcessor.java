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
package org.eclipse.php.internal.core.search;

import org.eclipse.dltk.compiler.CharOperation;
import org.eclipse.dltk.core.search.SearchPatternProcessor;

public class PHPSearchPatternProcessor extends SearchPatternProcessor {

	private static final String TYPE_DELIMITER = "\\"; //$NON-NLS-1$
	private static final String OBJ_CALL_DELIMITER = "->"; //$NON-NLS-1$
	private static final String STATIC_CALL_DELIMITER = "::"; //$NON-NLS-1$

	public char[] extractDeclaringTypeQualification(String pattern) {
		int pos = pattern.indexOf(OBJ_CALL_DELIMITER);
		int pos2 = pattern.indexOf(STATIC_CALL_DELIMITER);
		pos = pos == -1 ? pos2 : (pos2 == -1 ? pos : Math.min(pos, pos2));
		if (pos != -1) {
			final String type = pattern.substring(0, pos);
			return extractTypeQualification(type);
		}
		return null;
	}

	public char[] extractDeclaringTypeSimpleName(String pattern) {
		int pos = pattern.indexOf(OBJ_CALL_DELIMITER);
		int pos2 = pattern.indexOf(STATIC_CALL_DELIMITER);
		pos = pos == -1 ? pos2 : (pos2 == -1 ? pos : Math.min(pos, pos2));
		if (pos != -1) {
			final String type = pattern.substring(0, pos);
			return extractTypeChars(type).toCharArray();
		}
		return null;
	}

	public char[] extractSelector(String pattern) {
		int pos = pattern.indexOf(OBJ_CALL_DELIMITER);
		int pos2 = pattern.indexOf(STATIC_CALL_DELIMITER);
		pos = pos == -1 ? pos2 : (pos2 == -1 ? pos : Math.min(pos, pos2));
		if (pos != -1) {
			final int begin = pos + 2; // method delimiter length (either -> or
										// ::)
			if (begin < pattern.length()) {
				final char[] result = new char[pattern.length() - begin];
				pattern.getChars(begin, pattern.length(), result, 0);
				return result;
			}
		}
		return pattern.toCharArray();
	}

	/**
	 * @since 2.2
	 */
	private String extractPHPTypeChars(String pattern) {
		final int pos = pattern.lastIndexOf(TYPE_DELIMITER);
		if (pos != -1) {
			final int begin = pos + TYPE_DELIMITER.length();
			if (begin < pattern.length()) {
				return pattern.substring(begin);
			}
		}
		return pattern;
	}

	/**
	 * @since 2.2
	 */
	private String extractPHPTypeQualification(String pattern) {
		final int pos = pattern.lastIndexOf(TYPE_DELIMITER);
		if (pos != -1) {
			final char[] result = new char[pos];
			pattern.getChars(0, pos, result, 0);
			return new String(CharOperation.replace(result,
					TYPE_DELIMITER.toCharArray(), new char[] { '$' }));
		}
		return null;
	}

	public String getDelimiterReplacementString() {
		return TYPE_DELIMITER;
	}

	public ITypePattern parseType(String patternString) {
		return new TypePatten(extractPHPTypeQualification(patternString),
				extractPHPTypeChars(patternString));

	}
}
