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
package org.eclipse.php.internal.core.compiler.ast.parser.php53;

import org.eclipse.php.internal.core.util.collections.IntHashtable;

public abstract class PhpTokenNames {

	private static IntHashtable token2Name = new IntHashtable();

	static {
		token2Name.put(CompilerParserConstants.T_INC, "++"); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_DEC, "--"); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_IS_IDENTICAL, "==="); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_IS_NOT_IDENTICAL, "!=="); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_IS_EQUAL, "=="); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_IS_NOT_EQUAL, "!="); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_IS_SMALLER_OR_EQUAL, "<=+"); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_IS_GREATER_OR_EQUAL, ">=+"); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_PLUS_EQUAL, "+="); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_MINUS_EQUAL, "-="); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_MUL_EQUAL, "*="); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_DIV_EQUAL, "/="); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_CONCAT_EQUAL, ".="); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_MOD_EQUAL, "%="); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_SL_EQUAL, "<<="); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_SR_EQUAL, ">>="); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_AND_EQUAL, "&="); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_OR_EQUAL, "|+"); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_XOR_EQUAL, "^="); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_BOOLEAN_OR, "||"); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_BOOLEAN_AND, "&&"); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_LOGICAL_OR, "OR"); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_LOGICAL_AND, "AND"); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_LOGICAL_XOR, "XOR"); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_SL, "<<"); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_SR, ">>"); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_SEMICOLON, ";"); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_NEKUDOTAIM, ":"); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_COMMA, ","); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_NEKUDA, "."); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_OPEN_RECT, "["); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_CLOSE_RECT, "]"); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_OPEN_PARENTHESE, "("); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_CLOSE_PARENTHESE, ")"); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_OR, "|"); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_KOVA, "^"); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_REFERENCE, "&"); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_PLUS, "+"); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_MINUS, "-"); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_DIV, "/"); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_TIMES, "*"); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_EQUAL, "="); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_PRECENT, "%"); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_NOT, "!"); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_TILDA, "~"); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_DOLLAR, "$"); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_RGREATER, "<"); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_LGREATER, ">"); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_QUESTION_MARK, "?"); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_AT, "@"); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_EXIT, "exit"); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_FUNCTION, "function"); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_CONST, "const"); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_RETURN, "return"); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_IF, "if"); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_ELSEIF, "elseif"); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_ENDIF, "endif"); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_ELSE, "else"); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_WHILE, "while"); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_ENDWHILE, "endwhile"); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_DO, "do"); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_FOR, "for"); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_ENDFOR, "endfor"); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_FOREACH, "foreach"); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_ENDFOREACH, "endforeach"); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_AS, "as"); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_SWITCH, "switch"); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_ENDSWITCH, "endswitch"); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_CASE, "case"); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_DEFAULT, "default"); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_BREAK, "break"); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_CONTINUE, "continue"); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_GOTO, "goto"); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_ECHO, "echo"); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_PRINT, "print"); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_CLASS, "class"); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_TRY, "try"); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_CATCH, "catch"); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_THROW, "throw"); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_INSTANCEOF, "instanceof"); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_INTERFACE, "interface"); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_IMPLEMENTS, "implements"); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_ABSTRACT, "abstract"); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_FINAL, "final"); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_PRIVATE, "private"); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_PROTECTED, "protected"); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_PUBLIC, "public"); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_EXTENDS, "extends"); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_NEW, "new"); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_EVAL, "eval"); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_INCLUDE, "include"); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_INCLUDE_ONCE, "include_once"); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_REQUIRE, "require"); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_REQUIRE_ONCE, "require_once"); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_NAMESPACE, "namespace"); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_USE, "use"); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_GLOBAL, "global"); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_ISSET, "isset"); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_EMPTY, "empty"); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_STATIC, "static"); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_UNSET, "unset"); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_LIST, "array"); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_VAR, "var"); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_DECLARE, "declare"); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_ENDDECLARE, "enddeclare"); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_OBJECT_OPERATOR, "->"); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_PAAMAYIM_NEKUDOTAYIM, "::"); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_NS_SEPARATOR, "\\"); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_CURLY_CLOSE, "}"); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_CURLY_OPEN, "{"); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_DOUBLE_ARROW, "=>"); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_DOLLAR_OPEN_CURLY_BRACES, "${"); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_STRING, "identifier"); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.T_VARIABLE, "variable"); //$NON-NLS-1$
		token2Name.put(CompilerParserConstants.EOF, "EOF"); //$NON-NLS-1$
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
