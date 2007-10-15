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
package org.eclipse.php.internal.core.phpModel.parser.php5;

import org.eclipse.core.resources.IProject;
import org.eclipse.php.internal.core.phpModel.parser.DefaultParserClient;
import org.eclipse.php.internal.core.phpModel.parser.PHPUserModel;
import org.eclipse.php.internal.core.util.collections.IntHashtable;


public class PHP5DefaultParserClient extends DefaultParserClient {

	private static IntHashtable errorsTable;

	public PHP5DefaultParserClient(PHPUserModel userModel, IProject project) {
		super(userModel, project);
	}


	protected int getStringTag() {
		return ParserConstants5.T_STRING;
	}

	protected int getVariableTag() {
		return ParserConstants5.T_VARIABLE;
	}

	protected int getEOFTag() {
		return ParserConstants5.EOF;
	}

	protected int getCONSTANT_ENCAPSED_STRINGTag() {
		return ParserConstants5.T_CONSTANT_ENCAPSED_STRING;
	}

	protected String getError(int tag) {
		if (errorsTable == null) {
			errorsTable = new IntHashtable();
			initErrorsTable();
		}
		return (String) errorsTable.get(tag);
	}

	private static void initErrorsTable() {
		errorsTable.put(ParserConstants5.T_INC, "++"); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_DEC, "--"); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_IS_IDENTICAL, "==="); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_IS_NOT_IDENTICAL, "!=="); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_IS_EQUAL, "=="); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_IS_NOT_EQUAL, "!="); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_IS_SMALLER_OR_EQUAL, "<=+"); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_IS_GREATER_OR_EQUAL, ">=+"); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_PLUS_EQUAL, "+="); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_MINUS_EQUAL, "-="); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_MUL_EQUAL, "*="); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_DIV_EQUAL, "/="); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_CONCAT_EQUAL, ".="); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_MOD_EQUAL, "%="); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_SL_EQUAL, "<<="); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_SR_EQUAL, ">>="); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_AND_EQUAL, "&="); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_OR_EQUAL, "|+"); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_XOR_EQUAL, "^="); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_BOOLEAN_OR, "||"); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_BOOLEAN_AND, "&&"); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_LOGICAL_OR, "OR"); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_LOGICAL_AND, "AND"); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_LOGICAL_XOR, "XOR"); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_SL, "<<"); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_SR, ">>"); //$NON-NLS-1$

		errorsTable.put(ParserConstants5.T_SEMICOLON, ";"); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_NEKUDOTAIM, ":"); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_COMMA, ","); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_NEKUDA, "."); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_OPEN_RECT, "["); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_CLOSE_RECT, "]"); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_OPEN_PARENTHESE, "("); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_CLOSE_PARENTHESE, ")"); //$NON-NLS-1$

		errorsTable.put(ParserConstants5.T_OR, "|"); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_KOVA, "^"); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_REFERENCE, "&"); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_PLUS, "+"); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_MINUS, "-"); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_DIV, "/"); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_TIMES, "*"); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_EQUAL, "="); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_PRECENT, "%"); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_NOT, "!"); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_TILDA, "~"); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_DOLLAR, "$"); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_RGREATER, "<"); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_LGREATER, ">"); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_QUESTION_MARK, "?"); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_AT, "@"); //$NON-NLS-1$

		errorsTable.put(ParserConstants5.T_EXIT, "exit"); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_FUNCTION, "function"); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_CONST, "const"); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_RETURN, "return"); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_IF, "if"); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_ELSEIF, "elseif"); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_ENDIF, "endif"); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_ELSE, "else"); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_WHILE, "while"); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_ENDWHILE, "endwhile"); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_DO, "do"); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_FOR, "for"); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_ENDFOR, "endfor"); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_FOREACH, "foreach"); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_ENDFOREACH, "endforeach"); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_AS, "as"); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_SWITCH, "switch"); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_ENDSWITCH, "endswitch"); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_CASE, "case"); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_DEFAULT, "default"); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_BREAK, "break"); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_CONTINUE, "continue"); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_ECHO, "echo"); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_PRINT, "print"); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_CLASS, "class"); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_TRY, "try"); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_CATCH, "catch"); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_THROW, "throw"); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_INSTANCEOF, "instanceof"); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_INTERFACE, "interface"); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_IMPLEMENTS, "implements"); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_ABSTRACT, "abstract"); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_FINAL, "final"); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_PRIVATE, "private"); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_PROTECTED, "protected"); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_PUBLIC, "public"); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_EXTENDS, "extends"); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_NEW, "new"); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_EVAL, "eval"); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_INCLUDE, "include"); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_INCLUDE_ONCE, "include_once"); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_REQUIRE, "require"); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_REQUIRE_ONCE, "require_once"); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_USE, "use"); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_GLOBAL, "global"); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_ISSET, "isset"); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_EMPTY, "empty"); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_STATIC, "static"); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_UNSET, "unset"); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_LIST, "array"); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_VAR, "var"); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_DECLARE, "declare"); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_ENDDECLARE, "enddeclare"); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_OBJECT_OPERATOR, "->"); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_PAAMAYIM_NEKUDOTAYIM, "::"); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_CURLY_CLOSE, "}"); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_CURLY_OPEN, "{"); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_DOUBLE_ARROW, "=>"); //$NON-NLS-1$
		errorsTable.put(ParserConstants5.T_DOLLAR_OPEN_CURLY_BRACES, "${"); //$NON-NLS-1$
	}

}