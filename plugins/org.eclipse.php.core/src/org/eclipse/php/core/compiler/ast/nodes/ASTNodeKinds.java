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
package org.eclipse.php.core.compiler.ast.nodes;

public interface ASTNodeKinds {

	int ARRAY_ACCESS = 0;
	int ARRAY_CREATION = 1;
	int ARRAY_ELEMENT = 2;
	int ASSIGNMENT = 3;
	int AST_ERROR = 4;
	int BACK_TICK_EXPRESSION = 5;
	int BLOCK = 6;
	int BREAK_STATEMENT = 7;
	int CAST_EXPRESSION = 8;
	int CATCH_CLAUSE = 9;
	int STATIC_CONSTANT_ACCESS = 10;
	int CLASS_CONSTANT_DECLARATION = 11;
	int CLASS_DECLARATION = 12;
	int CLASS_INSTANCE_CREATION = 13;
	int CLASS_NAME = 14;
	int CLONE_EXPRESSION = 15;
	int COMMENT = 16;
	int CONDITIONAL_EXPRESSION = 17;
	int CONTINUE_STATEMENT = 18;
	int DECLARE_STATEMENT = 19;
	int DO_STATEMENT = 20;
	int ECHO_STATEMENT = 21;
	int EMPTY_STATEMENT = 22;
	int EXPRESSION_STATEMENT = 23;
	int FIELD_ACCESS = 24;
	int FIELD_DECLARATION = 25;
	int FOR_EACH_STATEMENT = 26;
	int FORMAL_PARAMETER = 27;
	int FOR_STATEMENT = 28;
	int FUNCTION_DECLARATION = 29;
	int FUNCTION_INVOCATION = 30;
	int FUNCTION_NAME = 31;
	int GLOBAL_STATEMENT = 32;
	int IDENTIFIER = 33;
	int IF_STATEMENT = 34;
	int IGNORE_ERROR = 35;
	int INCLUDE = 36;
	int INFIX_EXPRESSION = 37;
	int IN_LINE_HTML = 38;
	int INSTANCE_OF_EXPRESSION = 39;
	int INTERFACE_DECLARATION = 40;
	int LIST_VARIABLE = 41;
	int METHOD_DECLARATION = 42;
	int METHOD_INVOCATION = 43;
	int POSTFIX_EXPRESSION = 44;
	int PREFIX_EXPRESSION = 45;
	int PROGRAM = 46;
	int QUOTE = 47;
	int REFERENCE = 48;
	int REFLECTION_VARIABLE = 49;
	int RETURN_STATEMENT = 50;
	int SCALAR = 51;
	int STATIC_FIELD_ACCESS = 52;
	int STATIC_METHOD_INVOCATION = 53;
	int STATIC_STATEMENT = 54;
	int SWITCH_CASE = 55;
	int SWITCH_STATEMENT = 56;
	int THROW_STATEMENT = 57;
	int TRY_STATEMENT = 58;
	int UNARY_OPERATION = 59;
	int VARIABLE = 60;
	int WHILE_STATEMENT = 61;
	int PARENTHESIS_EXPRESSION = 62;
	int FORMAL_PARAMETER_BYREF = 63;
	int REFLECTION_CALL_EXPRESSION = 64;
	int REFLECTION_ARRAY_ACCESS = 65;
	int REFLECTION_STATIC_METHOD_INVOCATION = 66;
	int PHP_DOC_BLOCK = 67;
	int PHP_DOC_TAG = 68;
	int NAMESPACE_DECLARATION = 69;
	int USE_STATEMENT = 70;
	int GOTO_LABEL = 71;
	int GOTO_STATEMENT = 72;
	int LAMBDA_FUNCTION = 73;
	int YIELD_STATEMENT = 74;
	int FINALLY_CLAUSE = 75;
	int ANONYMOUS_CLASS_DECLARATION = 76;
	int RETURN_TYPE = 77;

}
