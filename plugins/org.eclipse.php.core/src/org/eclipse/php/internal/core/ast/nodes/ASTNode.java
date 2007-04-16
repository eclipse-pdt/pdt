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
package org.eclipse.php.internal.core.ast.nodes;

/**
 * Abstract superclass of all Abstract Syntax Tree (AST) node types. <p>
 * An AST node represents a PHP source code construct, such
 * as a name, type, expression, statement, or declaration.<p>
 * ASTs do not contain cycles. <p>
 * 
 * @see Visitable
 * @author Modhe S., Roy G. ,2007
 * </p>
 */
public abstract class ASTNode implements Visitable {

	public static final int ARRAY_ACCESS = 0;
	public static final int ARRAY_CREATION = 1;
	public static final int ARRAY_ELEMENT = 2;
	public static final int ASSIGNMENT = 3;
	public static final int AST_ERROR = 4;
	public static final int BACK_TICK_EXPRESSION = 5;
	public static final int BLOCK = 6;
	public static final int BREAK_STATEMENT = 7;
	public static final int CAST_EXPRESSION = 8;
	public static final int CATCH_CLAUSE = 9;
	public static final int STATIC_CONSTANT_ACCESS = 10;
	public static final int CLASS_CONSTANT_DECLARATION = 11;
	public static final int CLASS_DECLARATION = 12;
	public static final int CLASS_INSTANCE_CREATION = 13;
	public static final int CLASS_NAME = 14;
	public static final int CLONE_EXPRESSION = 15;
	public static final int COMMENT = 16;
	public static final int CONDITIONAL_EXPRESSION = 17;
	public static final int CONTINUE_STATEMENT = 18;
	public static final int DECLARE_STATEMENT = 19;
	public static final int DO_STATEMENT = 20;
	public static final int ECHO_STATEMENT = 21;
	public static final int EMPTY_STATEMENT = 22;
	public static final int EXPRESSION_STATEMENT = 23;
	public static final int FIELD_ACCESS = 24;
	public static final int FIELD_DECLARATION = 25;
	public static final int FOR_EACH_STATEMENT = 26;
	public static final int FORMAL_PARAMETER = 27;
	public static final int FOR_STATEMENT = 28;
	public static final int FUNCTION_DECLARATION = 29;
	public static final int FUNCTION_INVOCATION = 30;
	public static final int FUNCTION_NAME = 31;
	public static final int GLOBAL_STATEMENT = 32;
	public static final int IDENTIFIER = 33;
	public static final int IF_STATEMENT = 34;
	public static final int IGNORE_ERROR = 35;
	public static final int INCLUDE = 36;
	public static final int INFIX_EXPRESSION = 37;
	public static final int IN_LINE_HTML = 38;
	public static final int INSTANCE_OF_EXPRESSION = 39;
	public static final int INTERFACE_DECLARATION = 40;
	public static final int LIST_VARIABLE = 41;
	public static final int METHOD_DECLARATION = 42;
	public static final int METHOD_INVOCATION = 43;
	public static final int POSTFIX_EXPRESSION = 44;
	public static final int PREFIX_EXPRESSION = 45;
	public static final int PROGRAM = 46;
	public static final int QUOTE = 47;
	public static final int REFERENCE = 48;
	public static final int REFLECTION_VARIABLE = 49;
	public static final int RETURN_STATEMENT = 50;
	public static final int SCALAR = 51;
	public static final int STATIC_FIELD_ACCESS = 52;
	public static final int STATIC_METHOD_INVOCATION = 53;
	public static final int STATIC_STATEMENT = 54;
	public static final int SWITCH_CASE = 55;
	public static final int SWITCH_STATEMENT = 56;
	public static final int THROW_STATEMENT = 57;
	public static final int TRY_STATEMENT = 58;
	public static final int UNARY_OPERATION = 59;
	public static final int VARIABLE = 60;
	public static final int WHILE_STATEMENT = 61;

	private final int start;
	private final int length;
	private ASTNode parent;

	public ASTNode(int start, int end) {
		this.start = start;
		this.length = end - start;
	}

	public int getLength() {
		return length;
	}

	public int getStart() {
		return start;
	}

	public int getEnd() {
		return start + length;
	}

	public abstract int getType();

	public String toString() {
		final StringBuffer buffer = new StringBuffer();
		toString(buffer, "");
		return buffer.toString();
	}

	/**
	 * Appends the start, length parameters to the buffer
	 */
	protected void appendInterval(StringBuffer buffer) {
		buffer.append(" start='").append(start).append("' length='").append(length).append("'");
	}

	/**
	 * Formats a given string to an XML file
	 * @param input 
	 * @return String the formatted string
	 */
	protected static String getXmlStringValue(String input) {
		String escapedString = input;
		escapedString = escapedString.replaceAll("&", "&amp;");
		escapedString = escapedString.replaceAll(">", "&gt;");
		escapedString = escapedString.replaceAll("<", "&lt;");
		escapedString = escapedString.replaceAll("'", "&apos;");
		return escapedString;
	}

	public ASTNode getParent() {
		return parent;
	}

	public void setParent(ASTNode parent) {
		this.parent = parent;
	}
}
