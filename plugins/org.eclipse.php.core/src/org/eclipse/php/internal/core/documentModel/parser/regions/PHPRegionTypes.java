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
package org.eclipse.php.internal.core.documentModel.parser.regions;

public interface PHPRegionTypes {
	static final String PHP_OPENTAG = "PHP_OPENTAG"; //$NON-NLS-1$

	static final String PHP_CLOSETAG = "PHP_CLOSETAG"; //$NON-NLS-1$

	static final String PHP_CONTENT = "PHP_CONTENT"; //$NON-NLS-1$

	//	static final String PHP_RESERVED_WORD = "PHP_RESERVED_WORD";

	static final String PHP_DIE = "PHP_DIE"; //$NON-NLS-1$

	//	static final String PHP_FILE = "PHP_FILE";

	//	static final String PHP_REFERENCE = "PHP_REFERENCE";

	static final String PHP_SEMICOLON = "PHP_SEMICOLON"; //$NON-NLS-1$

	static final String PHP_CASE = "PHP_CASE"; //$NON-NLS-1$

	static final String PHP_NUMBER = "PHP_NUMBER"; //$NON-NLS-1$
	//	static final String PHP_DNUMBER = "PHP_DNUMBER";

	static final String PHP_GLOBAL = "PHP_GLOBAL"; //$NON-NLS-1$

	static final String PHP_ARRAY = "PHP_ARRAY"; //$NON-NLS-1$

	//	static final String PHP_TILDA = "PHP_TILDA";

	static final String PHP_FINAL = "PHP_FINAL"; //$NON-NLS-1$

	//	static final String PHP_CLASS_C = "PHP_CLASS_C";

	static final String PHP_PAAMAYIM_NEKUDOTAYIM = "PHP_PAAMAYIM_NEKUDOTAYIM"; //$NON-NLS-1$
	
	static final String PHP_NS_SEPARATOR = "PHP_NS_SEPARATOR"; //$NON-NLS-1$

	static final String PHP_EXTENDS = "PHP_EXTENDS"; //$NON-NLS-1$

	static final String PHP_VAR_COMMENT = "PHP_VAR_COMMENT"; //$NON-NLS-1$

	static final String PHP_NAMESPACE = "PHP_NAMESPACE"; //$NON-NLS-1$
	
	static final String PHP_USE = "PHP_USE"; //$NON-NLS-1$

	//	static final String PHP_MINUS_EQUAL = "PHP_MINUS_EQUAL";

	//	static final String PHP_INT_CAST = "PHP_INT_CAST";

	//	static final String PHP_BOOLEAN_OR = "PHP_BOOLEAN_OR";

	static final String PHP_INCLUDE = "PHP_INCLUDE"; //$NON-NLS-1$

	static final String PHP_EMPTY = "PHP_EMPTY"; //$NON-NLS-1$

	//	static final String PHP_XOR_EQUAL = "PHP_XOR_EQUAL";

	static final String PHP_CLASS = "PHP_CLASS"; //$NON-NLS-1$

	//	static final String PHP_END_HEREDOC = "PHP_END_HEREDOC";

	static final String PHP_FOR = "PHP_FOR"; //$NON-NLS-1$

	static final String PHP_STRING = "PHP_STRING"; //$NON-NLS-1$

	//	static final String PHP_DIV = "PHP_DIV";

	//	static final String PHP_START_HEREDOC = "PHP_START_HEREDOC";

	//	static final String PHP_AT = "PHP_AT";

	static final String PHP_AS = "PHP_AS"; //$NON-NLS-1$

	//	static final String PHP_STRING_CAST = "PHP_STRING_CAST";

	static final String PHP_TRY = "PHP_TRY"; //$NON-NLS-1$

	static final String PHP_STATIC = "PHP_STATIC"; //$NON-NLS-1$

	//	static final String PHP_EQUAL = "PHP_EQUAL";

	static final String PHP_WHILE = "PHP_WHILE"; //$NON-NLS-1$

	//	static final String PHP_METHOD_C = "PHP_METHOD_C";

	//	static final String PHP_CLOSE_RECT = "PHP_CLOSE_RECT";

	//	static final String PHP_SR = "PHP_SR";

	static final String PHP_ENDFOREACH = "PHP_ENDFOREACH"; //$NON-NLS-1$

	//	static final String PHP_FUNC_C = "PHP_FUNC_C";

	static final String PHP_EVAL = "PHP_EVAL"; //$NON-NLS-1$

	static final String PHP_INSTANCEOF = "PHP_INSTANCEOF"; //$NON-NLS-1$

	//	static final String PHP_OPEN_RECT = "PHP_OPEN_RECT";

	//	static final String PHP_NEKUDA = "PHP_NEKUDA";

	//	static final String PHP_SL = "PHP_SL";

	//	static final String PHP_INC = "PHP_INC";

	//	static final String PHP_KOVA = "PHP_KOVA";

	//	static final String PHP_BOOLEAN_AND = "PHP_BOOLEAN_AND";

	static final String PHP_ENDWHILE = "PHP_ENDWHILE"; //$NON-NLS-1$

	//	static final String PHP_STRING_VARNAME = "PHP_STRING_VARNAME";

	//	static final String PHP_DIV_EQUAL = "PHP_DIV_EQUAL";

	static final String PHP_BREAK = "PHP_BREAK"; //$NON-NLS-1$

	//	static final String PHP_DEFINE = "PHP_DEFINE";

	//	static final String PHP_BACKQUATE = "PHP_BACKQUATE";

	//	static final String PHP_AND_EQUAL = "PHP_AND_EQUAL";

	static final String PHP_DEFAULT = "PHP_DEFAULT"; //$NON-NLS-1$

	//	static final String PHP_SR_EQUAL = "PHP_SR_EQUAL";

	static final String PHP_VARIABLE = "PHP_VARIABLE"; //$NON-NLS-1$

	static final String PHP_ABSTRACT = "PHP_ABSTRACT"; //$NON-NLS-1$

	//	static final String PHP_SL_EQUAL = "PHP_SL_EQUAL";

	static final String PHP_PRINT = "PHP_PRINT"; //$NON-NLS-1$

	static final String PHP_CURLY_OPEN = "PHP_CURLY_OPEN"; //$NON-NLS-1$

	static final String PHP_ENDIF = "PHP_ENDIF"; //$NON-NLS-1$

	static final String PHP_ELSEIF = "PHP_ELSEIF"; //$NON-NLS-1$

	//	static final String PHP_MINUS = "PHP_MINUS";

	//	static final String PHP_IS_EQUAL = "PHP_IS_EQUAL";

	//	static final String PHP_UNSET_CAST = "PHP_UNSET_CAST";

	static final String PHP_HALT_COMPILER = "PHP_HALT_COMPILER"; //$NON-NLS-1$

	static final String PHP_INCLUDE_ONCE = "PHP_INCLUDE_ONCE"; //$NON-NLS-1$

	//	static final String PHP_BAD_CHARACTER = "PHP_BAD_CHARACTER";

	//	static final String PHP_OBJECT_CAST = "PHP_OBJECT_CAST";

	//	static final String PHP_OR_EQUAL = "PHP_OR_EQUAL";

	//	static final String PHP_INLINE_HTML = "PHP_INLINE_HTML";

	static final String PHP_NEW = "PHP_NEW"; //$NON-NLS-1$

	//	static final String PHP_SINGLE_QUATE = "PHP_SINGLE_QUATE";

	static final String PHP_UNSET = "PHP_UNSET"; //$NON-NLS-1$

	//	static final String PHP_MOD_EQUAL = "PHP_MOD_EQUAL";

	//	static final String PHP_DOLLAR = "PHP_DOLLAR";

	static final String PHP_ENDSWITCH = "PHP_ENDSWITCH"; //$NON-NLS-1$

	static final String PHP_FOREACH = "PHP_FOREACH"; //$NON-NLS-1$

	static final String PHP_IMPLEMENTS = "PHP_IMPLEMENTS"; //$NON-NLS-1$

	//	static final String PHP_NEKUDOTAIM = "PHP_NEKUDOTAIM";

	static final String PHP_CLONE = "PHP_CLONE"; //$NON-NLS-1$

	//	static final String PHP_EOF = "PHP_EOF";

	//	static final String PHP_PLUS = "PHP_PLUS";

	//	static final String PHP_NUM_STRING = "PHP_NUM_STRING";

	static final String PHP_ENDFOR = "PHP_ENDFOR"; //$NON-NLS-1$

	//	static final String PHP_IS_SMALLER_OR_EQUAL = "PHP_IS_SMALLER_OR_EQUAL";

	static final String PHP_REQUIRE_ONCE = "PHP_REQUIRE_ONCE"; //$NON-NLS-1$

	//	static final String PHP_LNUMBER = "PHP_LNUMBER";

	static final String PHP_FUNCTION = "PHP_FUNCTION"; //$NON-NLS-1$

	static final String PHP_PROTECTED = "PHP_PROTECTED"; //$NON-NLS-1$

	//	static final String PHP_QUATE = "PHP_QUATE";

	static final String PHP_PRIVATE = "PHP_PRIVATE"; //$NON-NLS-1$

	//	static final String PHP_IS_NOT_EQUAL = "PHP_IS_NOT_EQUAL";

	static final String PHP_ENDDECLARE = "PHP_ENDDECLARE"; //$NON-NLS-1$

	static final String PHP_CURLY_CLOSE = "PHP_CURLY_CLOSE"; //$NON-NLS-1$

	//	static final String PHP_PRECENT = "PHP_PRECENT";

	//	static final String PHP_PLUS_EQUAL = "PHP_PLUS_EQUAL";

	//	static final String PHP_error = "PHP_error";

	static final String PHP_ELSE = "PHP_ELSE"; //$NON-NLS-1$

	static final String PHP_DO = "PHP_DO"; //$NON-NLS-1$

	//	static final String PHP_RGREATER = "PHP_RGREATER";

	static final String PHP_CONTINUE = "PHP_CONTINUE"; //$NON-NLS-1$
	
	static final String PHP_GOTO = "PHP_GOTO"; //$NON-NLS-1$

	//	static final String PHP_IS_IDENTICAL = "PHP_IS_IDENTICAL";

	static final String PHP_ECHO = "PHP_ECHO"; //$NON-NLS-1$

	//	static final String PHP_DOUBLE_ARROW = "PHP_DOUBLE_ARROW";

	//	static final String PHP_CHARACTER = "PHP_CHARACTER";

	//	static final String PHP_TIMES = "PHP_TIMES";

	static final String PHP_REQUIRE = "PHP_REQUIRE"; //$NON-NLS-1$

	//	static final String PHP_ARRAY_CAST = "PHP_ARRAY_CAST";

	static final String PHP_CONSTANT_ENCAPSED_STRING = "PHP_CONSTANT_ENCAPSED_STRING"; //$NON-NLS-1$

	static final String PHP_ENCAPSED_AND_WHITESPACE = "PHP_ENCAPSED_AND_WHITESPACE"; //$NON-NLS-1$

	static final String WHITESPACE = "WHITESPACE"; //$NON-NLS-1$

	static final String PHP_SWITCH = "PHP_SWITCH"; //$NON-NLS-1$

	//	static final String PHP_DOUBLE_CAST = "PHP_DOUBLE_CAST";

	//	static final String PHP_LINE = "PHP_LINE";

	//	static final String PHP_BOOL_CAST = "PHP_BOOL_CAST";

	static final String PHP_CONST = "PHP_CONST"; //$NON-NLS-1$

	static final String PHP_PUBLIC = "PHP_PUBLIC"; //$NON-NLS-1$

	static final String PHP_RETURN = "PHP_RETURN"; //$NON-NLS-1$

	//	static final String PHP_IS_NOT_IDENTICAL = "PHP_IS_NOT_IDENTICAL";

	//	static final String PHP_IS_GREATER_OR_EQUAL = "PHP_IS_GREATER_OR_EQUAL";

	static final String PHP_LOGICAL_AND = "PHP_LOGICAL_AND"; //$NON-NLS-1$

	static final String PHP_INTERFACE = "PHP_INTERFACE"; //$NON-NLS-1$

	static final String PHP_EXIT = "PHP_EXIT"; //$NON-NLS-1$

	//	static final String PHP_DOLLAR_OPEN_CURLY_BRACES = "PHP_DOLLAR_OPEN_CURLY_BRACES";

	static final String PHP_LOGICAL_OR = "PHP_LOGICAL_OR"; //$NON-NLS-1$

	//	static final String PHP_CLOSE_PARENTHESE = "PHP_CLOSE_PARENTHESE";

	static final String PHP_NOT = "PHP_NOT"; //$NON-NLS-1$

	//	static final String PHP_CONCAT_EQUAL = "PHP_CONCAT_EQUAL";

	static final String PHP_LOGICAL_XOR = "PHP_LOGICAL_XOR"; //$NON-NLS-1$

	static final String PHP_ISSET = "PHP_ISSET"; //$NON-NLS-1$

	//	static final String PHP_QUESTION_MARK = "PHP_QUESTION_MARK";

	//	static final String PHP_OPEN_PARENTHESE = "PHP_OPEN_PARENTHESE";

	static final String PHP_LIST = "PHP_LIST"; //$NON-NLS-1$

	//	static final String PHP_OR = "PHP_OR";

	//	static final String PHP_COMMA = "PHP_COMMA";

	static final String PHP_CATCH = "PHP_CATCH"; //$NON-NLS-1$

	//	static final String PHP_DEC = "PHP_DEC";

	//	static final String PHP_MUL_EQUAL = "PHP_MUL_EQUAL";

	static final String PHP_VAR = "PHP_VAR"; //$NON-NLS-1$

	static final String PHP_THROW = "PHP_THROW"; //$NON-NLS-1$

	//	static final String PHP_LGREATER = "PHP_LGREATER";

	static final String PHP_IF = "PHP_IF"; //$NON-NLS-1$

	static final String PHP_DECLARE = "PHP_DECLARE"; //$NON-NLS-1$

	static final String PHP_OBJECT_OPERATOR = "PHP_OBJECT_OPERATOR"; //$NON-NLS-1$

	static final String PHP_SELF = "PHP_SELF"; //$NON-NLS-1$

	static final String PHPDOC_VAR = "PHPDOC_VAR"; //$NON-NLS-1$

	static final String PHPDOC_SEE = "PHPDOC_SEE"; //$NON-NLS-1$

	static final String PHP_COMMENT = "PHP_COMMENT"; //$NON-NLS-1$

	static final String PHP_COMMENT_START = "PHP_COMMENT_START"; //$NON-NLS-1$

	static final String PHP_COMMENT_END = "PHP_COMMENT_END"; //$NON-NLS-1$

	static final String PHP_LINE_COMMENT = "PHP_LINE_COMMENT"; //$NON-NLS-1$

	static final String PHPDOC_COMMENT = "PHPDOC_COMMENT"; //$NON-NLS-1$

	static final String PHPDOC_COMMENT_START = "PHPDOC_COMMENT_START"; //$NON-NLS-1$

	static final String PHPDOC_COMMENT_END = "PHPDOC_COMMENT_END"; //$NON-NLS-1$

	static final String PHPDOC_NAME = "PHPDOC_NAME"; //$NON-NLS-1$

	static final String PHPDOC_DESC = "PHPDOC_DESC"; //$NON-NLS-1$

	static final String PHPDOC_TODO = "PHPDOC_TODO"; //$NON-NLS-1$

	static final String PHPDOC_LINK = "PHPDOC_LINK"; //$NON-NLS-1$

	static final String PHPDOC_EXAMPLE = "PHPDOC_EXAMPLE"; //$NON-NLS-1$

	static final String PHPDOC_LICENSE = "PHPDOC_LICENSE"; //$NON-NLS-1$

	static final String PHPDOC_PACKAGE = "PHPDOC_PACKAGE"; //$NON-NLS-1$

	static final String PHPDOC_VERSION = "PHPDOC_VERSION"; //$NON-NLS-1$

	static final String PHPDOC_ABSTRACT = "PHPDOC_ABSTRACT"; //$NON-NLS-1$

	static final String PHPDOC_INTERNAL = "PHPDOC_INTERNAL"; //$NON-NLS-1$

	static final String PHPDOC_TUTORIAL = "PHPDOC_TUTORIAL"; //$NON-NLS-1$

	static final String PHPDOC_METHOD = "PHPDOC_METHOD"; //$NON-NLS-1$

	static final String PHPDOC_PROPERTY = "PHPDOC_PROPERTY"; //$NON-NLS-1$

	static final String PHPDOC_USES = "PHPDOC_USES"; //$NON-NLS-1$

	static final String PHPDOC_CATEGORY = "PHPDOC_CATEGORY"; //$NON-NLS-1$

	static final String UNKNOWN_TOKEN = "UNKNOWN_TOKEN"; //$NON-NLS-1$

	static final String PHPDOC_FINAL = "PHPDOC_FINAL"; //$NON-NLS-1$

	static final String PHPDOC_SINCE = "PHPDOC_SINCE"; //$NON-NLS-1$

	static final String PHPDOC_PARAM = "PHPDOC_PARAM"; //$NON-NLS-1$

	static final String PHPDOC_MAGIC = "PHPDOC_MAGIC"; //$NON-NLS-1$

	static final String PHPDOC_RETURN = "PHPDOC_RETURN"; //$NON-NLS-1$

	static final String PHPDOC_AUTHOR = "PHPDOC_AUTHOR"; //$NON-NLS-1$

	static final String PHPDOC_ACCESS = "PHPDOC_ACCESS"; //$NON-NLS-1$

	static final String PHPDOC_IGNORE = "PHPDOC_IGNORE"; //$NON-NLS-1$

	static final String PHPDOC_THROWS = "PHPDOC_THROWS"; //$NON-NLS-1$

	static final String PHPDOC_STATIC = "PHPDOC_STATIC"; //$NON-NLS-1$

	static final String PHPDOC_GLOBAL = "PHPDOC_GLOBAL"; //$NON-NLS-1$

	static final String PHPDOC_SUBPACKAGE = "PHPDOC_SUBPACKAGE"; //$NON-NLS-1$

	static final String PHPDOC_FILESOURCE = "PHPDOC_FILESOURCE"; //$NON-NLS-1$

	static final String PHPDOC_EXCEPTION = "PHPDOC_EXCEPTION"; //$NON-NLS-1$

	static final String PHPDOC_COPYRIGHT = "PHPDOC_COPYRIGHT"; //$NON-NLS-1$

	static final String PHPDOC_STATICVAR = "PHPDOC_STATICVAR"; //$NON-NLS-1$

	static final String PHPDOC_DEPRECATED = "PHPDOC_DEPRECATED"; //$NON-NLS-1$

	static final String PHP_HEREDOC_TAG = "PHP_HEREDOC_TAG"; //$NON-NLS-1$

	static final String PHP_TOKEN = "PHP_TOKEN"; //$NON-NLS-1$

	static final String PHP__FUNCTION__ = "PHP__FUNCTION__"; //$NON-NLS-1$

	static final String PHP_CASTING = "PHP_CASTING"; //$NON-NLS-1$

	static final String PHP__FILE__ = "PHP__FILE__"; //$NON-NLS-1$
	
	static final String PHP__DIR__ = "PHP__DIR__"; //$NON-NLS-1$
	
	static final String PHP__NAMESPACE__ = "PHP__NAMESPACE__"; //$NON-NLS-1$

	static final String PHP__LINE__ = "PHP__LINE__"; //$NON-NLS-1$

	static final String PHP_OPERATOR = "PHP_OPERATOR"; //$NON-NLS-1$

	static final String PHP_PARENT = "PHP_PARENT"; //$NON-NLS-1$

	static final String PHP__CLASS__ = "PHP__CLASS__"; //$NON-NLS-1$

	static final String PHP__METHOD__ = "PHP__METHOD__"; //$NON-NLS-1$

	static final String PHP_FROM = "PHP_FROM"; //$NON-NLS-1$

	static final String PHP_TRUE = "PHP_TRUE"; //$NON-NLS-1$

	static final String PHP_FALSE = "PHP_FALSE"; //$NON-NLS-1$

	static final String TASK = "TASK"; //$NON-NLS-1$
}
