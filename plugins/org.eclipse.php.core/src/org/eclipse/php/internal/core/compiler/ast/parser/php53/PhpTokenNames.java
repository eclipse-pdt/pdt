/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.compiler.ast.parser.php53;

import org.eclipse.php.internal.core.util.collections.IntHashtable;

public abstract class PhpTokenNames {

	private static IntHashtable token2Name = new IntHashtable();

	static {
		token2Name.put(PhpParserConstants.T_INC, "++"); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_DEC, "--"); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_IS_IDENTICAL, "==="); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_IS_NOT_IDENTICAL, "!=="); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_IS_EQUAL, "=="); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_IS_NOT_EQUAL, "!="); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_IS_SMALLER_OR_EQUAL, "<=+"); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_IS_GREATER_OR_EQUAL, ">=+"); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_PLUS_EQUAL, "+="); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_MINUS_EQUAL, "-="); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_MUL_EQUAL, "*="); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_DIV_EQUAL, "/="); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_CONCAT_EQUAL, ".="); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_MOD_EQUAL, "%="); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_SL_EQUAL, "<<="); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_SR_EQUAL, ">>="); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_AND_EQUAL, "&="); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_OR_EQUAL, "|+"); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_XOR_EQUAL, "^="); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_BOOLEAN_OR, "||"); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_BOOLEAN_AND, "&&"); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_LOGICAL_OR, "OR"); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_LOGICAL_AND, "AND"); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_LOGICAL_XOR, "XOR"); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_SL, "<<"); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_SR, ">>"); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_SEMICOLON, ";"); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_NEKUDOTAIM, ":"); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_COMMA, ","); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_NEKUDA, "."); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_OPEN_RECT, "["); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_CLOSE_RECT, "]"); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_OPEN_PARENTHESE, "("); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_CLOSE_PARENTHESE, ")"); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_OR, "|"); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_KOVA, "^"); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_REFERENCE, "&"); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_PLUS, "+"); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_MINUS, "-"); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_DIV, "/"); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_TIMES, "*"); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_EQUAL, "="); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_PRECENT, "%"); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_NOT, "!"); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_TILDA, "~"); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_DOLLAR, "$"); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_RGREATER, "<"); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_LGREATER, ">"); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_QUESTION_MARK, "?"); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_AT, "@"); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_EXIT, "exit"); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_FUNCTION, "function"); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_CONST, "const"); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_RETURN, "return"); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_IF, "if"); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_ELSEIF, "elseif"); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_ENDIF, "endif"); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_ELSE, "else"); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_WHILE, "while"); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_ENDWHILE, "endwhile"); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_DO, "do"); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_FOR, "for"); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_ENDFOR, "endfor"); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_FOREACH, "foreach"); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_ENDFOREACH, "endforeach"); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_AS, "as"); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_SWITCH, "switch"); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_ENDSWITCH, "endswitch"); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_CASE, "case"); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_DEFAULT, "default"); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_BREAK, "break"); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_CONTINUE, "continue"); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_GOTO, "goto"); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_ECHO, "echo"); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_PRINT, "print"); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_CLASS, "class"); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_TRY, "try"); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_CATCH, "catch"); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_THROW, "throw"); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_INSTANCEOF, "instanceof"); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_INTERFACE, "interface"); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_IMPLEMENTS, "implements"); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_ABSTRACT, "abstract"); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_FINAL, "final"); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_PRIVATE, "private"); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_PROTECTED, "protected"); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_PUBLIC, "public"); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_EXTENDS, "extends"); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_NEW, "new"); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_EVAL, "eval"); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_INCLUDE, "include"); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_INCLUDE_ONCE, "include_once"); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_REQUIRE, "require"); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_REQUIRE_ONCE, "require_once"); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_NAMESPACE, "namespace"); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_USE, "use"); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_GLOBAL, "global"); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_ISSET, "isset"); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_EMPTY, "empty"); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_STATIC, "static"); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_UNSET, "unset"); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_LIST, "array"); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_VAR, "var"); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_DECLARE, "declare"); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_ENDDECLARE, "enddeclare"); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_OBJECT_OPERATOR, "->"); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_PAAMAYIM_NEKUDOTAYIM, "::"); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_NS_SEPARATOR, "\\"); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_CURLY_CLOSE, "}"); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_CURLY_OPEN, "{"); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_DOUBLE_ARROW, "=>"); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_DOLLAR_OPEN_CURLY_BRACES, "${"); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_STRING, "identifier"); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.T_VARIABLE, "variable"); //$NON-NLS-1$
		token2Name.put(PhpParserConstants.EOF, "EOF"); //$NON-NLS-1$
	}
	
	/**
	 * Returns token name by its number
	 * @param token id
	 * @return token name
	 */
	public static String getName(int token) {
		return (String) token2Name.get(token);
	}
}
