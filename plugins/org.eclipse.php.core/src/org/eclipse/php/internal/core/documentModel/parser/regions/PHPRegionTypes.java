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
package org.eclipse.php.internal.core.documentModel.parser.regions;

public interface PHPRegionTypes {
	static final String PHP_OPENTAG = "PHP_OPENTAG";

	static final String PHP_CLOSETAG = "PHP_CLOSETAG";

	static final String PHP_CONTENT = "PHP_CONTENT";

	//	static final String PHP_RESERVED_WORD = "PHP_RESERVED_WORD";

	static final String PHP_DIE = "PHP_DIE";

	//	static final String PHP_FILE = "PHP_FILE";

	//	static final String PHP_REFERENCE = "PHP_REFERENCE";

	static final String PHP_SEMICOLON = "PHP_SEMICOLON";

	static final String PHP_CASE = "PHP_CASE";

	static final String PHP_NUMBER = "PHP_NUMBER";
	//	static final String PHP_DNUMBER = "PHP_DNUMBER";

	static final String PHP_GLOBAL = "PHP_GLOBAL";

	static final String PHP_ARRAY = "PHP_ARRAY";

	//	static final String PHP_TILDA = "PHP_TILDA";

	static final String PHP_FINAL = "PHP_FINAL";

	//	static final String PHP_CLASS_C = "PHP_CLASS_C";

	static final String PHP_PAAMAYIM_NEKUDOTAYIM = "PHP_PAAMAYIM_NEKUDOTAYIM";

	static final String PHP_EXTENDS = "PHP_EXTENDS";

	static final String PHP_VAR_COMMENT = "PHP_VAR_COMMENT";

	static final String PHP_USE = "PHP_USE";

	//	static final String PHP_MINUS_EQUAL = "PHP_MINUS_EQUAL";

	//	static final String PHP_INT_CAST = "PHP_INT_CAST";

	//	static final String PHP_BOOLEAN_OR = "PHP_BOOLEAN_OR";

	static final String PHP_INCLUDE = "PHP_INCLUDE";

	static final String PHP_EMPTY = "PHP_EMPTY";

	//	static final String PHP_XOR_EQUAL = "PHP_XOR_EQUAL";

	static final String PHP_CLASS = "PHP_CLASS";

	//	static final String PHP_END_HEREDOC = "PHP_END_HEREDOC";

	static final String PHP_FOR = "PHP_FOR";

	static final String PHP_STRING = "PHP_STRING";

	//	static final String PHP_DIV = "PHP_DIV";

	//	static final String PHP_START_HEREDOC = "PHP_START_HEREDOC";

	//	static final String PHP_AT = "PHP_AT";

	static final String PHP_AS = "PHP_AS";

	//	static final String PHP_STRING_CAST = "PHP_STRING_CAST";

	static final String PHP_TRY = "PHP_TRY";

	static final String PHP_STATIC = "PHP_STATIC";

	//	static final String PHP_EQUAL = "PHP_EQUAL";

	static final String PHP_WHILE = "PHP_WHILE";

	//	static final String PHP_METHOD_C = "PHP_METHOD_C";

	//	static final String PHP_CLOSE_RECT = "PHP_CLOSE_RECT";

	//	static final String PHP_SR = "PHP_SR";

	static final String PHP_ENDFOREACH = "PHP_ENDFOREACH";

	//	static final String PHP_FUNC_C = "PHP_FUNC_C";

	static final String PHP_EVAL = "PHP_EVAL";

	static final String PHP_INSTANCEOF = "PHP_INSTANCEOF";

	//	static final String PHP_OPEN_RECT = "PHP_OPEN_RECT";

	//	static final String PHP_NEKUDA = "PHP_NEKUDA";

	//	static final String PHP_SL = "PHP_SL";

	//	static final String PHP_INC = "PHP_INC";

	//	static final String PHP_KOVA = "PHP_KOVA";

	//	static final String PHP_BOOLEAN_AND = "PHP_BOOLEAN_AND";

	static final String PHP_ENDWHILE = "PHP_ENDWHILE";

	//	static final String PHP_STRING_VARNAME = "PHP_STRING_VARNAME";

	//	static final String PHP_DIV_EQUAL = "PHP_DIV_EQUAL";

	static final String PHP_BREAK = "PHP_BREAK";

	//	static final String PHP_DEFINE = "PHP_DEFINE";

	//	static final String PHP_BACKQUATE = "PHP_BACKQUATE";

	//	static final String PHP_AND_EQUAL = "PHP_AND_EQUAL";

	static final String PHP_DEFAULT = "PHP_DEFAULT";

	//	static final String PHP_SR_EQUAL = "PHP_SR_EQUAL";

	static final String PHP_VARIABLE = "PHP_VARIABLE";

	static final String PHP_ABSTRACT = "PHP_ABSTRACT";

	//	static final String PHP_SL_EQUAL = "PHP_SL_EQUAL";

	static final String PHP_PRINT = "PHP_PRINT";

	static final String PHP_CURLY_OPEN = "PHP_CURLY_OPEN";

	static final String PHP_ENDIF = "PHP_ENDIF";

	static final String PHP_ELSEIF = "PHP_ELSEIF";

	//	static final String PHP_MINUS = "PHP_MINUS";

	//	static final String PHP_IS_EQUAL = "PHP_IS_EQUAL";

	//	static final String PHP_UNSET_CAST = "PHP_UNSET_CAST";

	static final String PHP_HALT_COMPILER = "PHP_HALT_COMPILER";

	static final String PHP_INCLUDE_ONCE = "PHP_INCLUDE_ONCE";

	//	static final String PHP_BAD_CHARACTER = "PHP_BAD_CHARACTER";

	//	static final String PHP_OBJECT_CAST = "PHP_OBJECT_CAST";

	//	static final String PHP_OR_EQUAL = "PHP_OR_EQUAL";

	//	static final String PHP_INLINE_HTML = "PHP_INLINE_HTML";

	static final String PHP_NEW = "PHP_NEW";

	//	static final String PHP_SINGLE_QUATE = "PHP_SINGLE_QUATE";

	static final String PHP_UNSET = "PHP_UNSET";

	//	static final String PHP_MOD_EQUAL = "PHP_MOD_EQUAL";

	//	static final String PHP_DOLLAR = "PHP_DOLLAR";

	static final String PHP_ENDSWITCH = "PHP_ENDSWITCH";

	static final String PHP_FOREACH = "PHP_FOREACH";

	static final String PHP_IMPLEMENTS = "PHP_IMPLEMENTS";

	//	static final String PHP_NEKUDOTAIM = "PHP_NEKUDOTAIM";

	static final String PHP_CLONE = "PHP_CLONE";

	//	static final String PHP_EOF = "PHP_EOF";

	//	static final String PHP_PLUS = "PHP_PLUS";

	//	static final String PHP_NUM_STRING = "PHP_NUM_STRING";

	static final String PHP_ENDFOR = "PHP_ENDFOR";

	//	static final String PHP_IS_SMALLER_OR_EQUAL = "PHP_IS_SMALLER_OR_EQUAL";

	static final String PHP_REQUIRE_ONCE = "PHP_REQUIRE_ONCE";

	//	static final String PHP_LNUMBER = "PHP_LNUMBER";

	static final String PHP_FUNCTION = "PHP_FUNCTION";

	static final String PHP_PROTECTED = "PHP_PROTECTED";

	//	static final String PHP_QUATE = "PHP_QUATE";

	static final String PHP_PRIVATE = "PHP_PRIVATE";

	//	static final String PHP_IS_NOT_EQUAL = "PHP_IS_NOT_EQUAL";

	static final String PHP_ENDDECLARE = "PHP_ENDDECLARE";

	static final String PHP_CURLY_CLOSE = "PHP_CURLY_CLOSE";

	//	static final String PHP_PRECENT = "PHP_PRECENT";

	//	static final String PHP_PLUS_EQUAL = "PHP_PLUS_EQUAL";

	//	static final String PHP_error = "PHP_error";

	static final String PHP_ELSE = "PHP_ELSE";

	static final String PHP_DO = "PHP_DO";

	//	static final String PHP_RGREATER = "PHP_RGREATER";

	static final String PHP_CONTINUE = "PHP_CONTINUE";

	//	static final String PHP_IS_IDENTICAL = "PHP_IS_IDENTICAL";

	static final String PHP_ECHO = "PHP_ECHO";

	//	static final String PHP_DOUBLE_ARROW = "PHP_DOUBLE_ARROW";

	//	static final String PHP_CHARACTER = "PHP_CHARACTER";

	//	static final String PHP_TIMES = "PHP_TIMES";

	static final String PHP_REQUIRE = "PHP_REQUIRE";

	//	static final String PHP_ARRAY_CAST = "PHP_ARRAY_CAST";

	static final String PHP_CONSTANT_ENCAPSED_STRING = "PHP_CONSTANT_ENCAPSED_STRING";

	static final String PHP_ENCAPSED_AND_WHITESPACE = "PHP_ENCAPSED_AND_WHITESPACE";

	static final String WHITESPACE = "WHITESPACE";

	static final String PHP_SWITCH = "PHP_SWITCH";

	//	static final String PHP_DOUBLE_CAST = "PHP_DOUBLE_CAST";

	//	static final String PHP_LINE = "PHP_LINE";

	//	static final String PHP_BOOL_CAST = "PHP_BOOL_CAST";

	static final String PHP_CONST = "PHP_CONST";

	static final String PHP_PUBLIC = "PHP_PUBLIC";

	static final String PHP_RETURN = "PHP_RETURN";

	//	static final String PHP_IS_NOT_IDENTICAL = "PHP_IS_NOT_IDENTICAL";

	//	static final String PHP_IS_GREATER_OR_EQUAL = "PHP_IS_GREATER_OR_EQUAL";

	static final String PHP_LOGICAL_AND = "PHP_LOGICAL_AND";

	static final String PHP_INTERFACE = "PHP_INTERFACE";

	static final String PHP_EXIT = "PHP_EXIT";

	//	static final String PHP_DOLLAR_OPEN_CURLY_BRACES = "PHP_DOLLAR_OPEN_CURLY_BRACES";

	static final String PHP_LOGICAL_OR = "PHP_LOGICAL_OR";

	//	static final String PHP_CLOSE_PARENTHESE = "PHP_CLOSE_PARENTHESE";

	static final String PHP_NOT = "PHP_NOT";

	//	static final String PHP_CONCAT_EQUAL = "PHP_CONCAT_EQUAL";

	static final String PHP_LOGICAL_XOR = "PHP_LOGICAL_XOR";

	static final String PHP_ISSET = "PHP_ISSET";

	//	static final String PHP_QUESTION_MARK = "PHP_QUESTION_MARK";

	//	static final String PHP_OPEN_PARENTHESE = "PHP_OPEN_PARENTHESE";

	static final String PHP_LIST = "PHP_LIST";

	//	static final String PHP_OR = "PHP_OR";

	//	static final String PHP_COMMA = "PHP_COMMA";

	static final String PHP_CATCH = "PHP_CATCH";

	//	static final String PHP_DEC = "PHP_DEC";

	//	static final String PHP_MUL_EQUAL = "PHP_MUL_EQUAL";

	static final String PHP_VAR = "PHP_VAR";

	static final String PHP_THROW = "PHP_THROW";

	//	static final String PHP_LGREATER = "PHP_LGREATER";

	static final String PHP_IF = "PHP_IF";

	static final String PHP_DECLARE = "PHP_DECLARE";

	static final String PHP_OBJECT_OPERATOR = "PHP_OBJECT_OPERATOR";

	static final String PHP_SELF = "PHP_SELF";

	static final String PHPDOC_VAR = "PHPDOC_VAR";

	static final String PHPDOC_SEE = "PHPDOC_SEE";

	static final String PHP_COMMENT = "PHP_COMMENT";
	
	static final String PHP_COMMENT_START = "PHP_COMMENT_START";
	
	static final String PHP_COMMENT_END = "PHP_COMMENT_END";

	static final String PHP_LINE_COMMENT = "PHP_LINE_COMMENT";
	
	static final String PHPDOC_COMMENT = "PHPDOC_COMMENT";
	
	static final String PHPDOC_COMMENT_START = "PHPDOC_COMMENT_START";
	
	static final String PHPDOC_COMMENT_END = "PHPDOC_COMMENT_END";
	
	static final String PHPDOC_NAME = "PHPDOC_NAME";

	static final String PHPDOC_DESC = "PHPDOC_DESC";

	static final String PHPDOC_TODO = "PHPDOC_TODO";

	static final String PHPDOC_LINK = "PHPDOC_LINK";

	static final String PHPDOC_EXAMPLE = "PHPDOC_EXAMPLE";

	static final String PHPDOC_LICENSE = "PHPDOC_LICENSE";

	static final String PHPDOC_PACKAGE = "PHPDOC_PACKAGE";

	static final String PHPDOC_VERSION = "PHPDOC_VERSION";

	static final String PHPDOC_ABSTRACT = "PHPDOC_ABSTRACT";

	static final String PHPDOC_INTERNAL = "PHPDOC_INTERNAL";

	static final String PHPDOC_TUTORIAL = "PHPDOC_TUTORIAL";

	static final String PHPDOC_USES = "PHPDOC_USES";

	static final String PHPDOC_CATEGORY = "PHPDOC_CATEGORY";

	static final String UNKNOWN_TOKEN = "UNKNOWN_TOKEN";

	static final String PHPDOC_FINAL = "PHPDOC_FINAL";

	static final String PHPDOC_SINCE = "PHPDOC_SINCE";

	static final String PHPDOC_PARAM = "PHPDOC_PARAM";

	static final String PHPDOC_MAGIC = "PHPDOC_MAGIC";

	static final String PHPDOC_RETURN = "PHPDOC_RETURN";

	static final String PHPDOC_AUTHOR = "PHPDOC_AUTHOR";

	static final String PHPDOC_ACCESS = "PHPDOC_ACCESS";

	static final String PHPDOC_IGNORE = "PHPDOC_IGNORE";

	static final String PHPDOC_THROWS = "PHPDOC_THROWS";

	static final String PHPDOC_STATIC = "PHPDOC_STATIC";

	static final String PHPDOC_GLOBAL = "PHPDOC_GLOBAL";

	static final String PHPDOC_SUBPACKAGE = "PHPDOC_SUBPACKAGE";

	static final String PHPDOC_FILESOURCE = "PHPDOC_FILESOURCE";

	static final String PHPDOC_EXCEPTION = "PHPDOC_EXCEPTION";

	static final String PHPDOC_COPYRIGHT = "PHPDOC_COPYRIGHT";

	static final String PHPDOC_STATICVAR = "PHPDOC_STATICVAR";

	static final String PHPDOC_DEPRECATED = "PHPDOC_DEPRECATED";

	static final String PHP_HEREDOC_TAG = "PHP_HEREDOC_TAG";

	static final String PHP_TOKEN = "PHP_TOKEN";

	static final String PHP__FUNCTION__ = "PHP__FUNCTION__";

	static final String PHP_CASTING = "PHP_CASTING";

	static final String PHP__FILE__ = "PHP__FILE__";

	static final String PHP__LINE__ = "PHP__LINE__";

	static final String PHP_OPERATOR = "PHP_OPERATOR";

	static final String PHP_PARENT = "PHP_PARENT";

	static final String PHP__CLASS__ = "PHP__CLASS__";

	static final String PHP__METHOD__ = "PHP__METHOD__";

	static final String PHP_FROM = "PHP_FROM";

	static final String PHP_TRUE = "PHP_TRUE";

	static final String PHP_FALSE = "PHP_FALSE";

	static final String TASK = "TASK";
}
