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
		errorsTable.put(ParserConstants5.T_INC, "++");
		errorsTable.put(ParserConstants5.T_DEC, "--");
		errorsTable.put(ParserConstants5.T_IS_IDENTICAL, "===");
		errorsTable.put(ParserConstants5.T_IS_NOT_IDENTICAL, "!==");
		errorsTable.put(ParserConstants5.T_IS_EQUAL, "==");
		errorsTable.put(ParserConstants5.T_IS_NOT_EQUAL, "!=");
		errorsTable.put(ParserConstants5.T_IS_SMALLER_OR_EQUAL, "<=+");
		errorsTable.put(ParserConstants5.T_IS_GREATER_OR_EQUAL, ">=+");
		errorsTable.put(ParserConstants5.T_PLUS_EQUAL, "+=");
		errorsTable.put(ParserConstants5.T_MINUS_EQUAL, "-=");
		errorsTable.put(ParserConstants5.T_MUL_EQUAL, "*=");
		errorsTable.put(ParserConstants5.T_DIV_EQUAL, "/=");
		errorsTable.put(ParserConstants5.T_CONCAT_EQUAL, ".=");
		errorsTable.put(ParserConstants5.T_MOD_EQUAL, "%=");
		errorsTable.put(ParserConstants5.T_SL_EQUAL, "<<=");
		errorsTable.put(ParserConstants5.T_SR_EQUAL, ">>=");
		errorsTable.put(ParserConstants5.T_AND_EQUAL, "&=");
		errorsTable.put(ParserConstants5.T_OR_EQUAL, "|+");
		errorsTable.put(ParserConstants5.T_XOR_EQUAL, "^=");
		errorsTable.put(ParserConstants5.T_BOOLEAN_OR, "||");
		errorsTable.put(ParserConstants5.T_BOOLEAN_AND, "&&");
		errorsTable.put(ParserConstants5.T_LOGICAL_OR, "OR");
		errorsTable.put(ParserConstants5.T_LOGICAL_AND, "AND");
		errorsTable.put(ParserConstants5.T_LOGICAL_XOR, "XOR");
		errorsTable.put(ParserConstants5.T_SL, "<<");
		errorsTable.put(ParserConstants5.T_SR, ">>");

		errorsTable.put(ParserConstants5.T_SEMICOLON, ";");
		errorsTable.put(ParserConstants5.T_NEKUDOTAIM, ":");
		errorsTable.put(ParserConstants5.T_COMMA, ",");
		errorsTable.put(ParserConstants5.T_NEKUDA, ".");
		errorsTable.put(ParserConstants5.T_OPEN_RECT, "[");
		errorsTable.put(ParserConstants5.T_CLOSE_RECT, "]");
		errorsTable.put(ParserConstants5.T_OPEN_PARENTHESE, "(");
		errorsTable.put(ParserConstants5.T_CLOSE_PARENTHESE, ")");

		errorsTable.put(ParserConstants5.T_OR, "|");
		errorsTable.put(ParserConstants5.T_KOVA, "^");
		errorsTable.put(ParserConstants5.T_REFERENCE, "&");
		errorsTable.put(ParserConstants5.T_PLUS, "+");
		errorsTable.put(ParserConstants5.T_MINUS, "-");
		errorsTable.put(ParserConstants5.T_DIV, "/");
		errorsTable.put(ParserConstants5.T_TIMES, "*");
		errorsTable.put(ParserConstants5.T_EQUAL, "=");
		errorsTable.put(ParserConstants5.T_PRECENT, "%");
		errorsTable.put(ParserConstants5.T_NOT, "!");
		errorsTable.put(ParserConstants5.T_TILDA, "~");
		errorsTable.put(ParserConstants5.T_DOLLAR, "$");
		errorsTable.put(ParserConstants5.T_RGREATER, "<");
		errorsTable.put(ParserConstants5.T_LGREATER, ">");
		errorsTable.put(ParserConstants5.T_QUESTION_MARK, "?");
		errorsTable.put(ParserConstants5.T_AT, "@");

		errorsTable.put(ParserConstants5.T_EXIT, "exit");
		errorsTable.put(ParserConstants5.T_FUNCTION, "function");
		errorsTable.put(ParserConstants5.T_CONST, "const");
		errorsTable.put(ParserConstants5.T_RETURN, "return");
		errorsTable.put(ParserConstants5.T_IF, "if");
		errorsTable.put(ParserConstants5.T_ELSEIF, "elseif");
		errorsTable.put(ParserConstants5.T_ENDIF, "endif");
		errorsTable.put(ParserConstants5.T_ELSE, "else");
		errorsTable.put(ParserConstants5.T_WHILE, "while");
		errorsTable.put(ParserConstants5.T_ENDWHILE, "endwhile");
		errorsTable.put(ParserConstants5.T_DO, "do");
		errorsTable.put(ParserConstants5.T_FOR, "for");
		errorsTable.put(ParserConstants5.T_ENDFOR, "endfor");
		errorsTable.put(ParserConstants5.T_FOREACH, "foreach");
		errorsTable.put(ParserConstants5.T_ENDFOREACH, "endforeach");
		errorsTable.put(ParserConstants5.T_AS, "as");
		errorsTable.put(ParserConstants5.T_SWITCH, "switch");
		errorsTable.put(ParserConstants5.T_ENDSWITCH, "endswitch");
		errorsTable.put(ParserConstants5.T_CASE, "case");
		errorsTable.put(ParserConstants5.T_DEFAULT, "default");
		errorsTable.put(ParserConstants5.T_BREAK, "break");
		errorsTable.put(ParserConstants5.T_CONTINUE, "continue");
		errorsTable.put(ParserConstants5.T_ECHO, "echo");
		errorsTable.put(ParserConstants5.T_PRINT, "print");
		errorsTable.put(ParserConstants5.T_CLASS, "class");
		errorsTable.put(ParserConstants5.T_TRY, "try");
		errorsTable.put(ParserConstants5.T_CATCH, "catch");
		errorsTable.put(ParserConstants5.T_THROW, "throw");
		errorsTable.put(ParserConstants5.T_INSTANCEOF, "instanceof");
		errorsTable.put(ParserConstants5.T_INTERFACE, "interface");
		errorsTable.put(ParserConstants5.T_IMPLEMENTS, "implements");
		errorsTable.put(ParserConstants5.T_ABSTRACT, "abstract");
		errorsTable.put(ParserConstants5.T_FINAL, "final");
		errorsTable.put(ParserConstants5.T_PRIVATE, "private");
		errorsTable.put(ParserConstants5.T_PROTECTED, "protected");
		errorsTable.put(ParserConstants5.T_PUBLIC, "public");
		errorsTable.put(ParserConstants5.T_EXTENDS, "extends");
		errorsTable.put(ParserConstants5.T_NEW, "new");
		errorsTable.put(ParserConstants5.T_EVAL, "eval");
		errorsTable.put(ParserConstants5.T_INCLUDE, "include");
		errorsTable.put(ParserConstants5.T_INCLUDE_ONCE, "include_once");
		errorsTable.put(ParserConstants5.T_REQUIRE, "require");
		errorsTable.put(ParserConstants5.T_REQUIRE_ONCE, "require_once");
		errorsTable.put(ParserConstants5.T_USE, "use");
		errorsTable.put(ParserConstants5.T_GLOBAL, "global");
		errorsTable.put(ParserConstants5.T_ISSET, "isset");
		errorsTable.put(ParserConstants5.T_EMPTY, "empty");
		errorsTable.put(ParserConstants5.T_STATIC, "static");
		errorsTable.put(ParserConstants5.T_UNSET, "unset");
		errorsTable.put(ParserConstants5.T_LIST, "array");
		errorsTable.put(ParserConstants5.T_VAR, "var");
		errorsTable.put(ParserConstants5.T_DECLARE, "declare");
		errorsTable.put(ParserConstants5.T_ENDDECLARE, "enddeclare");
		errorsTable.put(ParserConstants5.T_OBJECT_OPERATOR, "->");
		errorsTable.put(ParserConstants5.T_PAAMAYIM_NEKUDOTAYIM, "::");
		errorsTable.put(ParserConstants5.T_CURLY_CLOSE, "}");
		errorsTable.put(ParserConstants5.T_CURLY_OPEN, "{");
		errorsTable.put(ParserConstants5.T_DOUBLE_ARROW, "=>");
		errorsTable.put(ParserConstants5.T_DOLLAR_OPEN_CURLY_BRACES, "${");
	}

}