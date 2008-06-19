package org.eclipse.php.internal.core.compiler.ast.parser.php5;

import org.eclipse.php.internal.core.util.collections.IntHashtable;

public abstract class Php5TokenNames {

	private static IntHashtable token2Name = new IntHashtable();

	static {
		token2Name.put(Php5ParserConstants.T_INC, "++"); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_DEC, "--"); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_IS_IDENTICAL, "==="); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_IS_NOT_IDENTICAL, "!=="); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_IS_EQUAL, "=="); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_IS_NOT_EQUAL, "!="); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_IS_SMALLER_OR_EQUAL, "<=+"); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_IS_GREATER_OR_EQUAL, ">=+"); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_PLUS_EQUAL, "+="); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_MINUS_EQUAL, "-="); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_MUL_EQUAL, "*="); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_DIV_EQUAL, "/="); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_CONCAT_EQUAL, ".="); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_MOD_EQUAL, "%="); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_SL_EQUAL, "<<="); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_SR_EQUAL, ">>="); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_AND_EQUAL, "&="); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_OR_EQUAL, "|+"); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_XOR_EQUAL, "^="); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_BOOLEAN_OR, "||"); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_BOOLEAN_AND, "&&"); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_LOGICAL_OR, "OR"); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_LOGICAL_AND, "AND"); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_LOGICAL_XOR, "XOR"); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_SL, "<<"); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_SR, ">>"); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_SEMICOLON, ";"); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_NEKUDOTAIM, ":"); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_COMMA, ","); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_NEKUDA, "."); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_OPEN_RECT, "["); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_CLOSE_RECT, "]"); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_OPEN_PARENTHESE, "("); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_CLOSE_PARENTHESE, ")"); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_OR, "|"); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_KOVA, "^"); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_REFERENCE, "&"); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_PLUS, "+"); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_MINUS, "-"); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_DIV, "/"); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_TIMES, "*"); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_EQUAL, "="); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_PRECENT, "%"); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_NOT, "!"); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_TILDA, "~"); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_DOLLAR, "$"); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_RGREATER, "<"); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_LGREATER, ">"); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_QUESTION_MARK, "?"); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_AT, "@"); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_EXIT, "exit"); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_FUNCTION, "function"); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_CONST, "const"); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_RETURN, "return"); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_IF, "if"); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_ELSEIF, "elseif"); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_ENDIF, "endif"); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_ELSE, "else"); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_WHILE, "while"); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_ENDWHILE, "endwhile"); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_DO, "do"); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_FOR, "for"); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_ENDFOR, "endfor"); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_FOREACH, "foreach"); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_ENDFOREACH, "endforeach"); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_AS, "as"); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_SWITCH, "switch"); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_ENDSWITCH, "endswitch"); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_CASE, "case"); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_DEFAULT, "default"); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_BREAK, "break"); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_CONTINUE, "continue"); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_ECHO, "echo"); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_PRINT, "print"); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_CLASS, "class"); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_TRY, "try"); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_CATCH, "catch"); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_THROW, "throw"); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_INSTANCEOF, "instanceof"); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_INTERFACE, "interface"); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_IMPLEMENTS, "implements"); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_ABSTRACT, "abstract"); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_FINAL, "final"); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_PRIVATE, "private"); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_PROTECTED, "protected"); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_PUBLIC, "public"); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_EXTENDS, "extends"); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_NEW, "new"); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_EVAL, "eval"); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_INCLUDE, "include"); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_INCLUDE_ONCE, "include_once"); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_REQUIRE, "require"); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_REQUIRE_ONCE, "require_once"); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_USE, "use"); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_GLOBAL, "global"); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_ISSET, "isset"); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_EMPTY, "empty"); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_STATIC, "static"); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_UNSET, "unset"); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_LIST, "array"); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_VAR, "var"); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_DECLARE, "declare"); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_ENDDECLARE, "enddeclare"); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_OBJECT_OPERATOR, "->"); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_PAAMAYIM_NEKUDOTAYIM, "::"); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_CURLY_CLOSE, "}"); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_CURLY_OPEN, "{"); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_DOUBLE_ARROW, "=>"); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_DOLLAR_OPEN_CURLY_BRACES, "${"); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_STRING, "identifier"); //$NON-NLS-1$
		token2Name.put(Php5ParserConstants.T_VARIABLE, "variable"); //$NON-NLS-1$
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
