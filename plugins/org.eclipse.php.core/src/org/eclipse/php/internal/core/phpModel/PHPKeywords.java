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
package org.eclipse.php.internal.core.phpModel;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.php.internal.core.phpModel.parser.PHPVersion;
import org.eclipse.php.internal.core.project.properties.handlers.PhpVersionProjectPropertyHandler;



public class PHPKeywords {

	private static final String OPEN_BLOCK_SUFFIX = " {";
	private static final String PAAMAYIM_NEKUDOTAYIM_SUFFIX = "::";
	private static final String WS_QUOTES_SEMICOLON_SUFFIX = " '';";
	private static final String EMPTY_SUFFIX = "";
	private static final String COLON_SUFFIX = ":";
	private static final String WHITESPACE_COLON_SUFFIX = " :";
	private static final String SEMICOLON_SUFFIX = ";";
	private static final String WHITESPACE_PARENTESES_SUFFIX = " ()";
	private static final String WHITESPACE_SUFFIX = " ";
	private static final String PARENTESES_SUFFIX = "()";

	public static class KeywordData {
		public String name;
		public String suffix;
		public int suffixOffset;
		public boolean isClassKeyword;

		public KeywordData(String name, String suffix, int suffixOffset) {
			this.name = name;
			this.suffix = suffix;
			this.suffixOffset = suffixOffset;
		}
		
		public KeywordData(String name, String suffix, int suffixOffset, boolean isClassKeyword) {
			this.name = name;
			this.suffix = suffix;
			this.suffixOffset = suffixOffset;
			this.isClassKeyword = isClassKeyword;
		}

		public int hashCode() {
			return name.hashCode();
		}

		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			KeywordData other = (KeywordData) obj;
			if (name == null) {
				if (other.name != null) {
					return false;
				}
			} else if (!name.equals(other.name)) {
				return false;
			}
			return true;
		}
	}

	/**
	 * Sorted array of PHP4 keywords and their auto-complete information.
	 */
	private static final KeywordData[] keywordsPhp4 = {
		new KeywordData("and", WHITESPACE_SUFFIX, 1),
		new KeywordData("array", PARENTESES_SUFFIX, 1),
		new KeywordData("as", WHITESPACE_SUFFIX, 1),
		new KeywordData("break", EMPTY_SUFFIX, 0),
		new KeywordData("case", WHITESPACE_COLON_SUFFIX, 2),
		new KeywordData("class", WHITESPACE_SUFFIX, 1),
		new KeywordData("const", WHITESPACE_SUFFIX, 1, true),
		new KeywordData("continue", EMPTY_SUFFIX, 0),
		new KeywordData("declare", PARENTESES_SUFFIX, 1),
		new KeywordData("default", COLON_SUFFIX, 1),
		new KeywordData("die", PARENTESES_SUFFIX, 1),
		new KeywordData("do", WHITESPACE_SUFFIX, 1),
		new KeywordData("echo", WHITESPACE_SUFFIX, 1),
		new KeywordData("elseif", WHITESPACE_PARENTESES_SUFFIX, 2),
		new KeywordData("else", WHITESPACE_SUFFIX, 1),
		new KeywordData("empty", PARENTESES_SUFFIX, 1),
		new KeywordData("enddeclare", SEMICOLON_SUFFIX, 1),
		new KeywordData("endforeach", SEMICOLON_SUFFIX, 1),
		new KeywordData("endfor", SEMICOLON_SUFFIX, 1),
		new KeywordData("endif", SEMICOLON_SUFFIX, 1),
		new KeywordData("endswitch", SEMICOLON_SUFFIX, 1),
		new KeywordData("endwhile", SEMICOLON_SUFFIX, 1),
		new KeywordData("eval", PARENTESES_SUFFIX, 1),
		new KeywordData("exit", PARENTESES_SUFFIX, 1),
		new KeywordData("extends", WHITESPACE_SUFFIX, 1),
		new KeywordData("false", EMPTY_SUFFIX, 0),
		new KeywordData("foreach", WHITESPACE_PARENTESES_SUFFIX, 2),
		new KeywordData("for", WHITESPACE_PARENTESES_SUFFIX, 2),
		new KeywordData("function", WHITESPACE_SUFFIX, 1, true),
		new KeywordData("global", WHITESPACE_SUFFIX, 1),
		new KeywordData("if", WHITESPACE_PARENTESES_SUFFIX, 2),
		new KeywordData("include_once", WS_QUOTES_SEMICOLON_SUFFIX, 2),
		new KeywordData("include", WS_QUOTES_SEMICOLON_SUFFIX, 2),
		new KeywordData("isset", PARENTESES_SUFFIX, 1),
		new KeywordData("list", PARENTESES_SUFFIX, 1),
		new KeywordData("new", WHITESPACE_SUFFIX, 1),
		new KeywordData("null", EMPTY_SUFFIX, 0),
		new KeywordData("old_function", WHITESPACE_SUFFIX, 1),
		new KeywordData("or", WHITESPACE_SUFFIX, 1),
		new KeywordData("parent", PAAMAYIM_NEKUDOTAYIM_SUFFIX, 2),
		new KeywordData("print", WHITESPACE_SUFFIX, 1),
		new KeywordData("require_once", WS_QUOTES_SEMICOLON_SUFFIX, 2),
		new KeywordData("require", WS_QUOTES_SEMICOLON_SUFFIX, 2),
		new KeywordData("return", WHITESPACE_SUFFIX, 1),
		new KeywordData("static", WHITESPACE_SUFFIX, 1, true),
		new KeywordData("switch", WHITESPACE_PARENTESES_SUFFIX, 2),
		new KeywordData("true", EMPTY_SUFFIX, 0),
		new KeywordData("unset", PARENTESES_SUFFIX, 1),
		new KeywordData("var", WHITESPACE_SUFFIX, 1, true),
		new KeywordData("while", WHITESPACE_PARENTESES_SUFFIX, 2),
		new KeywordData("xor", WHITESPACE_SUFFIX, 1),
	};
	
	/**
	 * Sorted array of PHP5 keywords and their auto-complete information.
	 */
	private static final KeywordData[] keywordsPhp5 = {
		new KeywordData("abstract", WHITESPACE_SUFFIX, 1, true),
		new KeywordData("and", WHITESPACE_SUFFIX, 1),
		new KeywordData("array", PARENTESES_SUFFIX, 1),
		new KeywordData("as", WHITESPACE_SUFFIX, 1),
		new KeywordData("break", EMPTY_SUFFIX, 0),
		new KeywordData("case", WHITESPACE_COLON_SUFFIX, 2),
		new KeywordData("catch", WHITESPACE_PARENTESES_SUFFIX, 2),
		new KeywordData("class", WHITESPACE_SUFFIX, 1),
		new KeywordData("clone", WHITESPACE_SUFFIX, 1),
		new KeywordData("const", WHITESPACE_SUFFIX, 1, true),
		new KeywordData("continue", EMPTY_SUFFIX, 0),
		new KeywordData("declare", PARENTESES_SUFFIX, 1),
		new KeywordData("default", COLON_SUFFIX, 1),
		new KeywordData("die", PARENTESES_SUFFIX, 1),
		new KeywordData("do", WHITESPACE_SUFFIX, 1),
		new KeywordData("echo", WHITESPACE_SUFFIX, 1),
		new KeywordData("elseif", WHITESPACE_PARENTESES_SUFFIX, 2),
		new KeywordData("else", WHITESPACE_SUFFIX, 1),
		new KeywordData("empty", PARENTESES_SUFFIX, 1),
		new KeywordData("enddeclare", SEMICOLON_SUFFIX, 1),
		new KeywordData("endforeach", SEMICOLON_SUFFIX, 1),
		new KeywordData("endfor", SEMICOLON_SUFFIX, 1),
		new KeywordData("endif", SEMICOLON_SUFFIX, 1),
		new KeywordData("endswitch", SEMICOLON_SUFFIX, 1),
		new KeywordData("endwhile", SEMICOLON_SUFFIX, 1),
		new KeywordData("eval", PARENTESES_SUFFIX, 1),
		new KeywordData("exit", PARENTESES_SUFFIX, 1),
		new KeywordData("extends", WHITESPACE_SUFFIX, 1),
		new KeywordData("false", EMPTY_SUFFIX, 0),
		new KeywordData("final", WHITESPACE_SUFFIX, 1),
		new KeywordData("foreach", WHITESPACE_PARENTESES_SUFFIX, 2),
		new KeywordData("for", WHITESPACE_PARENTESES_SUFFIX, 2),
		new KeywordData("function", WHITESPACE_SUFFIX, 1, true),
		new KeywordData("global", WHITESPACE_SUFFIX, 1),
		new KeywordData("if", WHITESPACE_PARENTESES_SUFFIX, 2),
		new KeywordData("implements", WHITESPACE_SUFFIX, 1),
		new KeywordData("include_once", WS_QUOTES_SEMICOLON_SUFFIX, 2),
		new KeywordData("include", WS_QUOTES_SEMICOLON_SUFFIX, 2),
		new KeywordData("instanceof", WHITESPACE_SUFFIX, 1),
		new KeywordData("interface", WHITESPACE_SUFFIX, 1),
		new KeywordData("isset", PARENTESES_SUFFIX, 1),
		new KeywordData("list", PARENTESES_SUFFIX, 1),
		new KeywordData("new", WHITESPACE_SUFFIX, 1),
		new KeywordData("null", EMPTY_SUFFIX, 0),
		new KeywordData("or", WHITESPACE_SUFFIX, 1),
		new KeywordData("parent", PAAMAYIM_NEKUDOTAYIM_SUFFIX, 2),
		new KeywordData("print", WHITESPACE_SUFFIX, 1),
		new KeywordData("private", WHITESPACE_SUFFIX, 1, true),
		new KeywordData("protected", WHITESPACE_SUFFIX, 1, true),
		new KeywordData("public", WHITESPACE_SUFFIX, 1, true),
		new KeywordData("require_once", WS_QUOTES_SEMICOLON_SUFFIX, 2),
		new KeywordData("require", WS_QUOTES_SEMICOLON_SUFFIX, 2),
		new KeywordData("return", WHITESPACE_SUFFIX, 1),
		new KeywordData("self", PAAMAYIM_NEKUDOTAYIM_SUFFIX, 2),
		new KeywordData("static", WHITESPACE_SUFFIX, 1, true),
		new KeywordData("switch", WHITESPACE_PARENTESES_SUFFIX, 2),
		new KeywordData("throw", WHITESPACE_SUFFIX, 1),
		new KeywordData("true", EMPTY_SUFFIX, 0),
		new KeywordData("try", OPEN_BLOCK_SUFFIX, 2),
		new KeywordData("unset", PARENTESES_SUFFIX, 1),
		new KeywordData("var", WHITESPACE_SUFFIX, 1, true),
		new KeywordData("while", WHITESPACE_PARENTESES_SUFFIX, 2),
		new KeywordData("xor", WHITESPACE_SUFFIX, 1),
	};
	
	private static KeywordData[] getKeywordDataByProject(IProject project) {
		if (PHPVersion.PHP4.equals(PhpVersionProjectPropertyHandler.getVersion(project))) {
			return keywordsPhp4;
		}
		return keywordsPhp5;
	}
	
	public static Collection<KeywordData> findByPrefix(IProject project, String prefix) {
		KeywordData[] keywordData = getKeywordDataByProject(project);
		List<KeywordData> result = new LinkedList<KeywordData>();
		for (KeywordData data : keywordData) {
			if (data.name.startsWith(prefix)) {
				result.add(data);
			}
		}
		return result;
	}
	
	public static Collection<String> findNamesByPrefix(IProject project, String prefix) {
		KeywordData[] keywordData = getKeywordDataByProject(project);
		List<String> result = new LinkedList<String>();
		for (KeywordData data : keywordData) {
			if (data.name.startsWith(prefix)) {
				result.add(data.name);
			}
		}
		return result;
	}
}
