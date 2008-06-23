/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.phpModel.parser.php4;

import org.eclipse.core.resources.IProject;
import org.eclipse.php.internal.core.phpModel.parser.DefaultParserClient;
import org.eclipse.php.internal.core.phpModel.parser.PHPUserModel;
import org.eclipse.php.internal.core.util.collections.IntHashtable;


public class PHP4DefaultParserClient extends DefaultParserClient {

	private static IntHashtable errorsTable;

	public PHP4DefaultParserClient(PHPUserModel userModel, IProject project) {
		super(userModel, project);
	}

	protected int getStringTag() {
		return ParserConstants4.T_STRING;
	}

	protected int getVariableTag() {
		return ParserConstants4.T_VARIABLE;
	}

	protected int getEOFTag() {
		return ParserConstants4.EOF;
	}

	protected int getCONSTANT_ENCAPSED_STRINGTag() {
		return ParserConstants4.T_CONSTANT_ENCAPSED_STRING;
	}

	protected String getError(int tag) {
		if (errorsTable == null) {
			errorsTable = new IntHashtable();
			initErrorsTable();
		}
		return (String) errorsTable.get(tag);
	}

	private static void initErrorsTable() {
		errorsTable.put(ParserConstants4.T_INC, "++");
		errorsTable.put(ParserConstants4.T_DEC, "--");
		errorsTable.put(ParserConstants4.T_IS_IDENTICAL, "===");
		errorsTable.put(ParserConstants4.T_IS_NOT_IDENTICAL, "!==");
		errorsTable.put(ParserConstants4.T_IS_EQUAL, "==");
		errorsTable.put(ParserConstants4.T_IS_NOT_EQUAL, "!=");
		errorsTable.put(ParserConstants4.T_IS_SMALLER_OR_EQUAL, "<=+");
		errorsTable.put(ParserConstants4.T_IS_GREATER_OR_EQUAL, ">=+");
		errorsTable.put(ParserConstants4.T_PLUS_EQUAL, "+=");
		errorsTable.put(ParserConstants4.T_MINUS_EQUAL, "-=");
		errorsTable.put(ParserConstants4.T_MUL_EQUAL, "*=");
		errorsTable.put(ParserConstants4.T_DIV_EQUAL, "/=");
		errorsTable.put(ParserConstants4.T_CONCAT_EQUAL, ".=");
		errorsTable.put(ParserConstants4.T_MOD_EQUAL, "%=");
		errorsTable.put(ParserConstants4.T_SL_EQUAL, "<<=");
		errorsTable.put(ParserConstants4.T_SR_EQUAL, ">>=");
		errorsTable.put(ParserConstants4.T_AND_EQUAL, "&=");
		errorsTable.put(ParserConstants4.T_OR_EQUAL, "|+");
		errorsTable.put(ParserConstants4.T_XOR_EQUAL, "^=");
		errorsTable.put(ParserConstants4.T_BOOLEAN_OR, "||");
		errorsTable.put(ParserConstants4.T_BOOLEAN_AND, "&&");
		errorsTable.put(ParserConstants4.T_LOGICAL_OR, "OR");
		errorsTable.put(ParserConstants4.T_LOGICAL_AND, "AND");
		errorsTable.put(ParserConstants4.T_LOGICAL_XOR, "XOR");
		errorsTable.put(ParserConstants4.T_SL, "<<");
		errorsTable.put(ParserConstants4.T_SR, ">>");

		errorsTable.put(ParserConstants4.T_SEMICOLON, ";");
		errorsTable.put(ParserConstants4.T_NEKUDOTAIM, ":");
		errorsTable.put(ParserConstants4.T_COMMA, ",");
		errorsTable.put(ParserConstants4.T_NEKUDA, ".");
		errorsTable.put(ParserConstants4.T_OPEN_RECT, "[");
		errorsTable.put(ParserConstants4.T_CLOSE_RECT, "]");
		errorsTable.put(ParserConstants4.T_OPEN_PARENTHESE, "(");
		errorsTable.put(ParserConstants4.T_CLOSE_PARENTHESE, ")");

		errorsTable.put(ParserConstants4.T_OR, "|");
		errorsTable.put(ParserConstants4.T_KOVA, "^");
		errorsTable.put(ParserConstants4.T_REFERENCE, "&");
		errorsTable.put(ParserConstants4.T_PLUS, "+");
		errorsTable.put(ParserConstants4.T_MINUS, "-");
		errorsTable.put(ParserConstants4.T_DIV, "/");
		errorsTable.put(ParserConstants4.T_TIMES, "*");
		errorsTable.put(ParserConstants4.T_EQUAL, "=");
		errorsTable.put(ParserConstants4.T_PRECENT, "%");
		errorsTable.put(ParserConstants4.T_NOT, "!");
		errorsTable.put(ParserConstants4.T_TILDA, "~");
		errorsTable.put(ParserConstants4.T_DOLLAR, "$");
		errorsTable.put(ParserConstants4.T_RGREATER, "<");
		errorsTable.put(ParserConstants4.T_LGREATER, ">");
		errorsTable.put(ParserConstants4.T_QUESTION_MARK, "?");
		errorsTable.put(ParserConstants4.T_AT, "@");

		errorsTable.put(ParserConstants4.T_EXIT, "exit");
		errorsTable.put(ParserConstants4.T_FUNCTION, "function");
		errorsTable.put(ParserConstants4.T_CONST, "const");
		errorsTable.put(ParserConstants4.T_RETURN, "return");
		errorsTable.put(ParserConstants4.T_IF, "if");
		errorsTable.put(ParserConstants4.T_ELSEIF, "elseif");
		errorsTable.put(ParserConstants4.T_ENDIF, "endif");
		errorsTable.put(ParserConstants4.T_ELSE, "else");
		errorsTable.put(ParserConstants4.T_WHILE, "while");
		errorsTable.put(ParserConstants4.T_ENDWHILE, "endwhile");
		errorsTable.put(ParserConstants4.T_DO, "do");
		errorsTable.put(ParserConstants4.T_FOR, "for");
		errorsTable.put(ParserConstants4.T_ENDFOR, "endfor");
		errorsTable.put(ParserConstants4.T_FOREACH, "foreach");
		errorsTable.put(ParserConstants4.T_ENDFOREACH, "endforeach");
		errorsTable.put(ParserConstants4.T_AS, "as");
		errorsTable.put(ParserConstants4.T_SWITCH, "switch");
		errorsTable.put(ParserConstants4.T_ENDSWITCH, "endswitch");
		errorsTable.put(ParserConstants4.T_CASE, "case");
		errorsTable.put(ParserConstants4.T_DEFAULT, "default");
		errorsTable.put(ParserConstants4.T_BREAK, "break");
		errorsTable.put(ParserConstants4.T_CONTINUE, "continue");
		errorsTable.put(ParserConstants4.T_ECHO, "echo");
		errorsTable.put(ParserConstants4.T_PRINT, "print");
		errorsTable.put(ParserConstants4.T_CLASS, "class");
		errorsTable.put(ParserConstants4.T_OLD_FUNCTION, "old_function");
		errorsTable.put(ParserConstants4.T_EXTENDS, "extends");
		errorsTable.put(ParserConstants4.T_NEW, "new");
		errorsTable.put(ParserConstants4.T_EVAL, "eval");
		errorsTable.put(ParserConstants4.T_INCLUDE, "include");
		errorsTable.put(ParserConstants4.T_INCLUDE_ONCE, "include_once");
		errorsTable.put(ParserConstants4.T_REQUIRE, "require");
		errorsTable.put(ParserConstants4.T_REQUIRE_ONCE, "require_once");
		errorsTable.put(ParserConstants4.T_USE, "use");
		errorsTable.put(ParserConstants4.T_GLOBAL, "global");
		errorsTable.put(ParserConstants4.T_ISSET, "isset");
		errorsTable.put(ParserConstants4.T_EMPTY, "empty");
		errorsTable.put(ParserConstants4.T_STATIC, "static");
		errorsTable.put(ParserConstants4.T_UNSET, "unset");
		errorsTable.put(ParserConstants4.T_LIST, "array");
		errorsTable.put(ParserConstants4.T_VAR, "var");
		errorsTable.put(ParserConstants4.T_DECLARE, "declare");
		errorsTable.put(ParserConstants4.T_ENDDECLARE, "enddeclare");
		errorsTable.put(ParserConstants4.T_OBJECT_OPERATOR, "->");
		errorsTable.put(ParserConstants4.T_PAAMAYIM_NEKUDOTAYIM, "::");
		errorsTable.put(ParserConstants4.T_CURLY_CLOSE, "}");
		errorsTable.put(ParserConstants4.T_CURLY_OPEN, "{");
		errorsTable.put(ParserConstants4.T_DOUBLE_ARROW, "=>");
		errorsTable.put(ParserConstants4.T_DOLLAR_OPEN_CURLY_BRACES, "${");
	}

}