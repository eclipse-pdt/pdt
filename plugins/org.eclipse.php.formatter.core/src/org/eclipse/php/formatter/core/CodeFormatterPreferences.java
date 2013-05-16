/*******************************************************************************
 * Copyright (c) 2013 Zend Techologies Ltd.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Zend Technologies Ltd. - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.formatter.core;

import java.util.HashMap;
import java.util.Map;

/**
 * @author moshe, 2007
 */
public class CodeFormatterPreferences {

	public static final int TAB = 1;
	public static final int SPACE = 2;
	public static final int MIXED = 3;

	public static final String TRUE = "true"; //$NON-NLS-1$
	public static final String FALSE = "false"; //$NON-NLS-1$

	public static final char TAB_CHAR = '\t';
	public static final char SPACE_CHAR = ' ';
	public static final char COMMA = ',';

	public static final byte SAME_LINE = 0;
	public static final byte NEXT_LINE = 1;
	public static final byte NEXT_LINE_INDENT = 2;

	public char indentationChar;
	public int indentationSize;
	public int tabSize;

	public boolean insert_space_after_opening_paren_in_while;
	public boolean insert_space_before_opening_paren_in_while;
	public boolean insert_space_before_closing_paren_in_while;

	public boolean insert_space_before_opening_paren_in_switch;
	public boolean insert_space_after_opening_paren_in_switch;
	public boolean insert_space_before_closing_paren_in_switch;
	public boolean insert_space_before_opening_brace_in_switch;
	public byte brace_position_for_switch;
	public boolean indent_statements_within_switch;
	public boolean insert_space_after_switch_default;
	public boolean insert_space_after_switch_case_value;
	public boolean indent_statements_within_case;
	public boolean indent_break_statements_within_case;

	public byte brace_position_for_block;
	public boolean insert_space_before_opening_brace_in_block;
	public boolean insert_space_after_closing_brace_in_block;
	public boolean indent_statements_within_block;
	public boolean insert_space_before_semicolon;

	public boolean insert_space_before_assignment;
	public boolean insert_space_after_assignment;
	public boolean insert_space_before_binary_operation;
	public boolean insert_space_after_binary_operation;
	public boolean insert_space_before_postfix_expression;
	public boolean insert_space_after_postfix_expression;
	public boolean insert_space_before_prefix_expression;
	public boolean insert_space_after_prefix_expression;
	public boolean insert_space_before_unary_expression;
	public boolean insert_space_after_unary_expression;

	public boolean insert_space_before_cast_type;
	public boolean insert_space_after_cast_type;
	public boolean insert_space_after_cast_expression;

	public boolean insert_space_after_conditional_colon;
	public boolean insert_space_before_conditional_colon;
	public boolean insert_space_after_conditional_question_mark;
	public boolean insert_space_before_conditional_question_mark;

	public boolean insert_space_before_opening_paren_in_catch;
	public boolean insert_space_after_opening_paren_in_catch;
	public boolean insert_space_before_closing_paren_in_catch;

	public boolean insert_space_before_comma_in_implements;
	public boolean insert_space_after_comma_in_implements;
	public byte brace_position_for_class;
	public boolean insert_space_before_opening_brace_in_class;

	public boolean insert_space_before_opening_paren_in_function;
	public boolean insert_space_after_opening_paren_in_function;
	public boolean insert_space_before_comma_in_function;
	public boolean insert_space_after_comma_in_function;
	public boolean insert_space_before_closing_paren_in_function;
	public boolean insert_space_between_empty_paren_in_function;
	public boolean insert_space_before_arrow_in_method_invocation;
	public boolean insert_space_after_arrow_in_method_invocation;
	public boolean insert_space_before_coloncolon_in_method_invocation;
	public boolean insert_space_after_coloncolon_in_method_invocation;

	public boolean insert_space_before_arrow_in_field_access;
	public boolean insert_space_after_arrow_in_field_access;
	public boolean insert_space_before_coloncolon_in_field_access;
	public boolean insert_space_after_coloncolon_in_field_access;

	public boolean insert_space_before_open_paren_in_for;
	public boolean insert_space_after_open_paren_in_for;
	public boolean insert_space_before_close_paren_in_for;
	public boolean insert_space_before_comma_in_for;
	public boolean insert_space_after_comma_in_for;
	public boolean insert_space_before_semicolon_in_for;
	public boolean insert_space_after_semicolon_in_for;

	public boolean insert_space_before_open_paren_in_foreach;
	public boolean insert_space_after_open_paren_in_foreach;
	public boolean insert_space_before_close_paren_in_foreach;
	public boolean insert_space_before_arrow_in_foreach;
	public boolean insert_space_after_arrow_in_foreach;

	public boolean insert_space_before_comma_in_class_variable;
	public boolean insert_space_after_comma_in_class_variable;
	public boolean insert_space_before_comma_in_class_constant;
	public boolean insert_space_after_comma_in_class_constant;

	public boolean insert_space_before_opening_bracket_in_array;
	public boolean insert_space_after_opening_bracket_in_array;
	public boolean insert_space_before_closing_bracket_in_array;
	public boolean insert_space_between_empty_brackets;

	public boolean insert_space_before_opening_paren_in_array;
	public boolean insert_space_after_opening_paren_in_array;
	public boolean insert_space_before_closing_paren_in_array;
	public boolean insert_space_before_list_comma_in_array;
	public boolean insert_space_after_list_comma_in_array;
	public boolean insert_space_before_arrow_in_array;
	public boolean insert_space_after_arrow_in_array;

	public boolean insert_space_before_opening_paren_in_list;
	public boolean insert_space_after_opening_paren_in_list;
	public boolean insert_space_before_closing_paren_in_list;
	public boolean insert_space_before_comma_in_list;
	public boolean insert_space_after_comma_in_list;

	public boolean insert_space_before_opening_paren_in_function_declaration;
	public boolean insert_space_after_opening_paren_in_function_declaration;
	public boolean insert_space_between_empty_paren_in_function_declaration;
	public boolean insert_space_before_closing_paren_in_function_declaration;
	public boolean insert_space_before_comma_in_function_declaration;
	public boolean insert_space_after_comma_in_function_declaration;
	public byte brace_position_for_function;
	public boolean insert_space_before_opening_brace_in_function;

	public boolean insert_space_before_opening_paren_in_if;
	public boolean insert_space_after_opening_paren_in_if;
	public boolean insert_space_before_closing_paren_in_if;

	public boolean insert_space_before_opening_paren_in_declare;
	public boolean insert_space_after_opening_paren_in_declare;
	public boolean insert_space_before_closing_paren_in_declare;

	public boolean indent_statements_within_type_declaration;
	public boolean indent_statements_within_function;
	public boolean indent_empty_lines;

	public boolean insert_space_before_comma_in_static;
	public boolean insert_space_after_comma_in_static;
	public boolean insert_space_before_comma_in_global;
	public boolean insert_space_after_comma_in_global;
	public boolean insert_space_before_comma_in_echo;
	public boolean insert_space_after_comma_in_echo;

	public boolean insert_space_after_open_paren_in_parenthesis_expression;
	public boolean insert_space_before_close_paren_in_parenthesis_expression;

	public int blank_line_preserve_empty_lines;
	public int blank_line_before_class_declaration;
	public int blank_line_before_constant_declaration;
	public int blank_line_before_field_declaration;
	public int blank_line_before_method_declaration;
	public int blank_line_at_begin_of_method;
	public int blank_line_at_end_of_class;
	public int blank_line_at_end_of_method;
	// namespace
	public int blank_lines_before_namespace;
	public int blank_lines_after_namespace;
	public int blank_lines_before_use_statements;
	public int blank_lines_after_use_statements;
	public int blank_lines_between_use_statements;
	public int blank_lines_between_namespaces;

	public boolean new_line_in_empty_class_body;
	public boolean new_line_in_empty_method_body;
	public boolean new_line_in_empty_block;
	public boolean new_line_after_open_array_parenthesis;
	public boolean new_line_before_close_array_parenthesis_array;
	public boolean new_line_for_empty_statement;
	public int new_line_in_second_invoke;

	public int line_wrap_line_split;
	public int line_wrap_wrapped_lines_indentation;
	public int line_wrap_array_init_indentation;
	public int line_wrap_superclass_in_type_declaration_line_wrap_policy;
	public int line_wrap_superclass_in_type_declaration_indent_policy;
	public boolean line_wrap_superclass_in_type_declaration_force_split;

	public int line_wrap_superinterfaces_in_type_declaration_line_wrap_policy;
	public int line_wrap_superinterfaces_in_type_declaration_indent_policy;
	public boolean line_wrap_superinterfaces_in_type_declaration_force_split;

	public int line_wrap_parameters_in_method_declaration_line_wrap_policy;
	public int line_wrap_parameters_in_method_declaration_indent_policy;
	public boolean line_wrap_parameters_in_method_declaration_force_split;

	public int line_wrap_arguments_in_method_invocation_line_wrap_policy;
	public int line_wrap_arguments_in_method_invocation_indent_policy;
	public boolean line_wrap_arguments_in_method_invocation_force_split;

	public int line_wrap_arguments_in_allocation_expression_line_wrap_policy;
	public int line_wrap_arguments_in_allocation_expression_indent_policy;
	public boolean line_wrap_arguments_in_allocation_expression_force_split;

	public int line_wrap_binary_expression_line_wrap_policy;
	public int line_wrap_binary_expression_indent_policy;
	public boolean line_wrap_binary_expression_force_split;

	public int line_wrap_conditional_expression_line_wrap_policy;
	public int line_wrap_conditional_expression_indent_policy;
	public boolean line_wrap_conditional_expression_force_split;

	public int line_wrap_expressions_in_array_init_line_wrap_policy;
	public int line_wrap_expressions_in_array_init_indent_policy;
	public boolean line_wrap_expressions_in_array_init_force_split;

	public int line_wrap_assignments_expression_line_wrap_policy;
	public int line_wrap_assignments_expression_indent_policy;
	public boolean line_wrap_assignments_expression_force_split;

	public int line_wrap_compact_if_line_wrap_policy;
	public int line_wrap_compact_if_indent_policy;
	public boolean line_wrap_compact_if_force_split;

	public boolean control_statement_insert_newline_before_else_and_elseif_in_if;
	public boolean control_statement_insert_newline_before_catch_in_try;
	public boolean control_statement_insert_newline_before_while_in_do;
	public boolean control_statement_keep_then_on_same_line;
	public boolean control_statement_keep_simple_if_on_one_line;
	public boolean control_statement_keep_else_on_same_line;
	public boolean control_statement_keep_else_if_on_same_line;
	public boolean control_statement_keep_guardian_on_one_line;

	// comments

	public boolean comment_clear_blank_lines_in_javadoc_comment;
	public boolean comment_clear_blank_lines_in_block_comment;
	public boolean comment_new_lines_at_block_boundaries;
	public boolean comment_new_lines_at_javadoc_boundaries;
	public boolean comment_format_javadoc_comment;
	public boolean comment_format_line_comment;
	public boolean comment_format_line_comment_starting_on_first_column;
	public boolean comment_format_block_comment;
	public boolean comment_format_header;
	public boolean comment_format_html;
	public boolean comment_format_source;
	public boolean comment_indent_parameter_description;
	public boolean comment_indent_root_tags;
	public boolean comment_insert_empty_line_before_root_tags;
	public boolean comment_insert_new_line_for_parameter;
	public boolean comment_preserve_white_space_between_code_and_line_comments;
	public int comment_line_length;

	public boolean use_tags;
	public char[] disabling_tag;
	public char[] enabling_tag;
	public final static char[] DEFAULT_DISABLING_TAG = "@formatter:off".toCharArray(); //$NON-NLS-1$
	public final static char[] DEFAULT_ENABLING_TAG = "@formatter:on".toCharArray(); //$NON-NLS-1$

	public boolean never_indent_block_comments_on_first_column;
	public boolean never_indent_line_comments_on_first_column;
	public boolean join_lines_in_comments;

	public CodeFormatterPreferences() {
	}

	public CodeFormatterPreferences(Map<String, Object> preferences) {
		setPreferencesValues(preferences);
	}

	public void setPreferencesValues(Map<String, Object> preferences) {
		String indentChar = ((String) preferences.get("indentationChar")); //$NON-NLS-1$
		if (indentChar != null) {
			indentationChar = indentChar.charAt(0);
		}

		indentationSize = getIntValue(preferences,
				CodeFormatterConstants.FORMATTER_INDENTATION_SIZE);
		tabSize = getIntValue(preferences,
				CodeFormatterConstants.FORMATTER_TAB_SIZE);

		insert_space_after_opening_paren_in_while = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_OPENING_PAREN_IN_WHILE);
		insert_space_before_opening_paren_in_while = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_OPENING_PAREN_IN_WHILE);
		insert_space_before_closing_paren_in_while = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_CLOSING_PAREN_IN_WHILE);

		insert_space_before_opening_paren_in_switch = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_OPENING_PAREN_IN_SWITCH);
		insert_space_after_opening_paren_in_switch = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_OPENING_PAREN_IN_SWITCH);
		insert_space_before_closing_paren_in_switch = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_CLOSING_PAREN_IN_SWITCH);
		insert_space_before_opening_brace_in_switch = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_OPENING_BRACE_IN_SWITCH);
		brace_position_for_switch = getByteValue(preferences,
				CodeFormatterConstants.FORMATTER_BRACE_POSITION_FOR_SWITCH);
		indent_statements_within_switch = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INDENT_SWITCHSTATEMENTS_COMPARE_TO_SWITCH);
		insert_space_after_switch_default = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_COLON_IN_DEFAULT);
		insert_space_after_switch_case_value = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_COLON_IN_CASE);
		indent_statements_within_case = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INDENT_SWITCHSTATEMENTS_COMPARE_TO_CASES);
		indent_break_statements_within_case = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INDENT_BREAKS_COMPARE_TO_CASES);

		brace_position_for_block = getByteValue(preferences,
				CodeFormatterConstants.FORMATTER_BRACE_POSITION_FOR_BLOCK);
		insert_space_before_opening_brace_in_block = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_OPENING_BRACE_IN_BLOCK);
		insert_space_after_closing_brace_in_block = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_CLOSING_BRACE_IN_BLOCK);
		indent_statements_within_block = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INDENT_STATEMENTS_COMPARE_TO_BLOCK);
		insert_space_before_semicolon = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_SEMICOLON);

		insert_space_before_assignment = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_ASSIGNMENT_OPERATOR);
		insert_space_after_assignment = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_ASSIGNMENT_OPERATOR);
		insert_space_before_binary_operation = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_BINARY_OPERATOR);
		insert_space_after_binary_operation = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_BINARY_OPERATOR);
		insert_space_before_postfix_expression = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_POSTFIX_OPERATOR);
		insert_space_after_postfix_expression = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_POSTFIX_OPERATOR);
		insert_space_before_prefix_expression = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_PREFIX_OPERATOR);
		insert_space_after_prefix_expression = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_PREFIX_OPERATOR);
		insert_space_before_unary_expression = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_UNARY_OPERATOR);
		insert_space_after_unary_expression = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_UNARY_OPERATOR);

		insert_space_before_cast_type = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_OPENING_PAREN_IN_CAST);
		insert_space_after_cast_type = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_CLOSING_PAREN_IN_CAST);
		insert_space_after_cast_expression = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_CLOSING_PAREN_IN_CAST);

		insert_space_after_conditional_colon = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_COLON_IN_CONDITIONAL);
		insert_space_before_conditional_colon = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_COLON_IN_CONDITIONAL);
		insert_space_after_conditional_question_mark = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_QUESTION_IN_CONDITIONAL);
		insert_space_before_conditional_question_mark = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_QUESTION_IN_CONDITIONAL);

		insert_space_before_opening_paren_in_catch = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_OPENING_PAREN_IN_CATCH);
		insert_space_after_opening_paren_in_catch = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_OPENING_PAREN_IN_CATCH);
		insert_space_before_closing_paren_in_catch = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_CLOSING_PAREN_IN_CATCH);

		insert_space_before_comma_in_implements = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_COMMA_IN_SUPERINTERFACES);
		insert_space_after_comma_in_implements = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_COMMA_IN_SUPERINTERFACES);
		brace_position_for_class = getByteValue(
				preferences,
				CodeFormatterConstants.FORMATTER_BRACE_POSITION_FOR_TYPE_DECLARATION);
		insert_space_before_opening_brace_in_class = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_OPENING_BRACE_IN_TYPE_DECLARATION);

		insert_space_before_opening_paren_in_function = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_OPENING_PAREN_IN_METHOD_INVOCATION);
		insert_space_after_opening_paren_in_function = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_OPENING_PAREN_IN_METHOD_INVOCATION);
		insert_space_before_comma_in_function = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_COMMA_IN_METHOD_INVOCATION_ARGUMENTS);
		insert_space_after_comma_in_function = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_COMMA_IN_METHOD_INVOCATION_ARGUMENTS);
		insert_space_before_closing_paren_in_function = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_CLOSING_PAREN_IN_METHOD_INVOCATION);
		insert_space_between_empty_paren_in_function = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BETWEEN_EMPTY_PARENS_IN_METHOD_INVOCATION);
		insert_space_before_arrow_in_method_invocation = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_ARROW_IN_METHOD_INVOCATION);
		insert_space_after_arrow_in_method_invocation = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_ARROW_IN_METHOD_INVOCATION);
		insert_space_before_coloncolon_in_method_invocation = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_COLONCOLON_IN_METHOD_INVOCATION);
		insert_space_after_coloncolon_in_method_invocation = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_COLONCOLON_IN_METHOD_INVOCATION);

		insert_space_before_arrow_in_field_access = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_ARROW_IN_FIELD_ACCESS);
		insert_space_after_arrow_in_field_access = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_ARROW_IN_FIELD_ACCESS);
		insert_space_before_coloncolon_in_field_access = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_COLONCOLON_IN_FIELD_ACCESS);
		insert_space_after_coloncolon_in_field_access = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_COLONCOLON_IN_FIELD_ACCESS);

		insert_space_before_open_paren_in_for = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_OPENING_PAREN_IN_FOR);
		insert_space_after_open_paren_in_for = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_OPENING_PAREN_IN_FOR);
		insert_space_before_close_paren_in_for = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_CLOSING_PAREN_IN_FOR);
		insert_space_before_comma_in_for = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_COMMA_IN_FOR);
		insert_space_after_comma_in_for = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_COMMA_IN_FOR);
		insert_space_before_semicolon_in_for = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_SEMICOLON_IN_FOR);
		insert_space_after_semicolon_in_for = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_SEMICOLON_IN_FOR);

		insert_space_before_open_paren_in_foreach = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_OPENING_PAREN_IN_FOREACH);
		insert_space_after_open_paren_in_foreach = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_OPENING_PAREN_IN_FOREACH);
		insert_space_before_close_paren_in_foreach = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_CLOSING_PAREN_IN_FOREACH);
		insert_space_before_arrow_in_foreach = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_ARROW_IN_FOREACH);
		insert_space_after_arrow_in_foreach = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_ARROW_IN_FOREACH);

		insert_space_before_comma_in_class_variable = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_COMMA_IN_MULTIPLE_FIELD_DECLARATIONS);
		insert_space_after_comma_in_class_variable = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_COMMA_IN_MULTIPLE_FIELD_DECLARATIONS);
		insert_space_before_comma_in_class_constant = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_COMMA_IN_MULTIPLE_CONSTANT_DECLARATIONS);
		insert_space_after_comma_in_class_constant = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_COMMA_IN_MULTIPLE_CONSTANT_DECLARATIONS);

		insert_space_before_opening_bracket_in_array = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_OPENING_BRACKET_IN_ARRAY_REFERENCE);
		insert_space_after_opening_bracket_in_array = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_OPENING_BRACKET_IN_ARRAY_REFERENCE);
		insert_space_before_closing_bracket_in_array = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_CLOSING_BRACKET_IN_ARRAY_REFERENCE);
		insert_space_between_empty_brackets = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BETWEEN_EMPTY_BRACKETS_IN_ARRAY_TYPE_REFERENCE);

		insert_space_before_opening_paren_in_array = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_OPENING_PAREN_IN_ARRAY_CREATION);
		insert_space_after_opening_paren_in_array = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_OPENING_PAREN_IN_ARRAY_CREATION);
		insert_space_before_closing_paren_in_array = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_CLOSING_PAREN_IN_ARRAY_CREATION);
		insert_space_before_list_comma_in_array = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_COMMA_IN_ARRAY_CREATION);
		insert_space_after_list_comma_in_array = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_COMMA_IN_ARRAY_CREATION);
		insert_space_before_arrow_in_array = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_ARROW_IN_ARRAY_CREATION);
		insert_space_after_arrow_in_array = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_ARROW_IN_ARRAY_CREATION);

		insert_space_before_opening_paren_in_list = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_OPENING_PAREN_IN_LIST);
		insert_space_after_opening_paren_in_list = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_OPENING_PAREN_IN_LIST);
		insert_space_before_closing_paren_in_list = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_CLOSING_PAREN_IN_LIST);
		insert_space_before_comma_in_list = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_COMMA_IN_LIST);
		insert_space_after_comma_in_list = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_COMMA_IN_LIST);

		insert_space_before_opening_paren_in_function_declaration = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_OPENING_PAREN_IN_METHOD_DECLARATION);
		insert_space_after_opening_paren_in_function_declaration = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_OPENING_PAREN_IN_METHOD_DECLARATION);
		insert_space_between_empty_paren_in_function_declaration = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BETWEEN_EMPTY_PARENS_IN_METHOD_DECLARATION);
		insert_space_before_closing_paren_in_function_declaration = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_CLOSING_PAREN_IN_METHOD_DECLARATION);
		insert_space_before_comma_in_function_declaration = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_COMMA_IN_METHOD_DECLARATION_PARAMETERS);
		insert_space_after_comma_in_function_declaration = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_COMMA_IN_METHOD_DECLARATION_PARAMETERS);
		brace_position_for_function = getByteValue(
				preferences,
				CodeFormatterConstants.FORMATTER_BRACE_POSITION_FOR_METHOD_DECLARATION);
		insert_space_before_opening_brace_in_function = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_OPENING_BRACE_IN_METHOD_DECLARATION);

		insert_space_before_opening_paren_in_if = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_OPENING_PAREN_IN_IF);
		insert_space_after_opening_paren_in_if = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_OPENING_PAREN_IN_IF);
		insert_space_before_closing_paren_in_if = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_CLOSING_PAREN_IN_IF);

		insert_space_before_opening_paren_in_declare = getBooleanValue(
				preferences, "insert_space_before_opening_paren_in_declare"); //$NON-NLS-1$
		insert_space_after_opening_paren_in_declare = getBooleanValue(
				preferences, "insert_space_after_opening_paren_in_declare"); //$NON-NLS-1$
		insert_space_before_closing_paren_in_declare = getBooleanValue(
				preferences, "insert_space_before_closing_paren_in_declare"); //$NON-NLS-1$

		indent_statements_within_type_declaration = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INDENT_BODY_DECLARATIONS_COMPARE_TO_TYPE_HEADER);
		indent_statements_within_function = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INDENT_STATEMENTS_COMPARE_TO_BODY);
		indent_empty_lines = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INDENT_EMPTY_LINES);

		insert_space_before_comma_in_static = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_COMMA_IN_STATIC);
		insert_space_after_comma_in_static = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_COMMA_IN_STATIC);
		insert_space_before_comma_in_global = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_COMMA_IN_GLOBAL);
		insert_space_after_comma_in_global = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_COMMA_IN_GLOBAL);
		insert_space_before_comma_in_echo = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_COMMA_IN_ECHO);
		insert_space_after_comma_in_echo = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_COMMA_IN_ECHO);

		insert_space_after_open_paren_in_parenthesis_expression = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_OPENING_PAREN_IN_PARENTHESIZED_EXPRESSION);
		insert_space_before_close_paren_in_parenthesis_expression = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_CLOSING_PAREN_IN_PARENTHESIZED_EXPRESSION);

		blank_line_preserve_empty_lines = getIntValue(
				preferences,
				CodeFormatterConstants.FORMATTER_NUMBER_OF_EMPTY_LINES_TO_PRESERVE);
		blank_line_before_class_declaration = getIntValue(
				preferences,
				CodeFormatterConstants.FORMATTER_BLANK_LINES_BETWEEN_TYPE_DECLARATIONS);
		blank_line_before_constant_declaration = getIntValue(preferences,
				CodeFormatterConstants.FORMATTER_BLANK_LINES_BEFORE_MEMBER_TYPE);
		blank_line_before_field_declaration = getIntValue(preferences,
				CodeFormatterConstants.FORMATTER_BLANK_LINES_BEFORE_FIELD);
		blank_line_before_method_declaration = getIntValue(preferences,
				CodeFormatterConstants.FORMATTER_BLANK_LINES_BEFORE_METHOD);
		blank_line_at_begin_of_method = getIntValue(
				preferences,
				CodeFormatterConstants.FORMATTER_BLANK_LINES_AT_BEGINNING_OF_METHOD_BODY);
		blank_line_at_end_of_class = getIntValue(
				preferences,
				CodeFormatterConstants.FORMATTER_BLANK_LINES_AT_END_OF_CLASS_BODY);
		blank_line_at_end_of_method = getIntValue(
				preferences,
				CodeFormatterConstants.FORMATTER_BLANK_LINES_AT_END_OF_METHOD_BODY);

		// namespace
		blank_lines_before_namespace = getIntValue(preferences,
				CodeFormatterConstants.FORMATTER_BLANK_LINES_BEFORE_NAMESPACE);
		blank_lines_after_namespace = getIntValue(preferences,
				CodeFormatterConstants.FORMATTER_BLANK_LINES_AFTER_NAMESPACE);
		blank_lines_before_use_statements = getIntValue(
				preferences,
				CodeFormatterConstants.FORMATTER_BLANK_LINES_BEFORE_USE_STATEMENTS);
		blank_lines_after_use_statements = getIntValue(
				preferences,
				CodeFormatterConstants.FORMATTER_BLANK_LINES_AFTER_USE_STATEMENTS);
		blank_lines_between_use_statements = getIntValue(
				preferences,
				CodeFormatterConstants.FORMATTER_BLANK_LINES_BETWEEN_USE_STATEMENTS);
		blank_lines_between_namespaces = getIntValue(preferences,
				CodeFormatterConstants.FORMATTER_BLANK_LINES_BETWEEN_NAMESPACES);

		// new line
		new_line_in_empty_class_body = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_NEW_LINE_IN_EMPTY_TYPE_DECLARATION);
		new_line_in_empty_method_body = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_NEW_LINE_IN_EMPTY_METHOD_BODY);
		new_line_in_empty_block = getBooleanValue(preferences,
				CodeFormatterConstants.FORMATTER_INSERT_NEW_LINE_IN_EMPTY_BLOCK);
		new_line_for_empty_statement = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_PUT_EMPTY_STATEMENT_ON_NEW_LINE);
		new_line_after_open_array_parenthesis = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_NEW_LINE_AFTER_OPENING_BRACE_IN_ARRAY_INITIALIZER);
		new_line_before_close_array_parenthesis_array = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_NEW_LINE_BEFORE_CLOSING_BRACE_IN_ARRAY_INITIALIZER);

		new_line_in_second_invoke = getIntValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_NEW_LINE_IN_SECOND_METHOD_INVOKE);

		// line wrapping
		line_wrap_line_split = getIntValue(preferences,
				CodeFormatterConstants.FORMATTER_LINE_SPLIT);
		line_wrap_wrapped_lines_indentation = getIntValue(preferences,
				CodeFormatterConstants.FORMATTER_CONTINUATION_INDENTATION);
		line_wrap_array_init_indentation = getIntValue(
				preferences,
				CodeFormatterConstants.FORMATTER_CONTINUATION_INDENTATION_FOR_ARRAY_INITIALIZER);

		line_wrap_superclass_in_type_declaration_line_wrap_policy = getIntValue(
				preferences,
				CodeFormatterConstants.FORMATTER_ALIGNMENT_FOR_SUPERCLASS_IN_TYPE_DECLARATION_LINE_WRAP_POLICY);
		line_wrap_superclass_in_type_declaration_indent_policy = getIntValue(
				preferences,
				CodeFormatterConstants.FORMATTER_ALIGNMENT_FOR_SUPERCLASS_IN_TYPE_DECLARATION_INDENT_POLICY);
		line_wrap_superclass_in_type_declaration_force_split = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_ALIGNMENT_FOR_SUPERCLASS_IN_TYPE_DECLARATION_FORCE_SPLIT);

		line_wrap_superinterfaces_in_type_declaration_line_wrap_policy = getIntValue(
				preferences,
				CodeFormatterConstants.FORMATTER_ALIGNMENT_FOR_SUPERINTERFACES_IN_TYPE_DECLARATION_LINE_WRAP_POLICY);
		line_wrap_superinterfaces_in_type_declaration_indent_policy = getIntValue(
				preferences,
				CodeFormatterConstants.FORMATTER_ALIGNMENT_FOR_SUPERINTERFACES_IN_TYPE_DECLARATION_INDENT_POLICY);
		line_wrap_superinterfaces_in_type_declaration_force_split = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_ALIGNMENT_FOR_SUPERINTERFACES_IN_TYPE_DECLARATION_FORCE_SPLIT);

		line_wrap_parameters_in_method_declaration_line_wrap_policy = getIntValue(
				preferences,
				CodeFormatterConstants.FORMATTER_ALIGNMENT_FOR_PARAMETERS_IN_METHOD_DECLARATION_LINE_WRAP_POLICY);
		line_wrap_parameters_in_method_declaration_indent_policy = getIntValue(
				preferences,
				CodeFormatterConstants.FORMATTER_ALIGNMENT_FOR_PARAMETERS_IN_METHOD_DECLARATION_INDENT_POLICY);
		line_wrap_parameters_in_method_declaration_force_split = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_ALIGNMENT_FOR_PARAMETERS_IN_METHOD_DECLARATION_FORCE_SPLIT);

		line_wrap_arguments_in_method_invocation_line_wrap_policy = getIntValue(
				preferences,
				CodeFormatterConstants.FORMATTER_ALIGNMENT_FOR_ARGUMENTS_IN_METHOD_INVOCATION_LINE_WRAP_POLICY);
		line_wrap_arguments_in_method_invocation_indent_policy = getIntValue(
				preferences,
				CodeFormatterConstants.FORMATTER_ALIGNMENT_FOR_ARGUMENTS_IN_METHOD_INVOCATION_INDENT_POLICY);
		line_wrap_arguments_in_method_invocation_force_split = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_ALIGNMENT_FOR_ARGUMENTS_IN_METHOD_INVOCATION_FORCE_SPLIT);

		line_wrap_arguments_in_allocation_expression_line_wrap_policy = getIntValue(
				preferences,
				CodeFormatterConstants.FORMATTER_ALIGNMENT_FOR_ARGUMENTS_IN_ALLOCATION_EXPRESSION_LINE_WRAP_POLICY);
		line_wrap_arguments_in_allocation_expression_indent_policy = getIntValue(
				preferences,
				CodeFormatterConstants.FORMATTER_ALIGNMENT_FOR_ARGUMENTS_IN_ALLOCATION_EXPRESSION_INDENT_POLICY);
		line_wrap_arguments_in_allocation_expression_force_split = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_ALIGNMENT_FOR_ARGUMENTS_IN_ALLOCATION_EXPRESSION_FORCE_SPLIT);

		line_wrap_binary_expression_line_wrap_policy = getIntValue(
				preferences,
				CodeFormatterConstants.FORMATTER_ALIGNMENT_FOR_BINARY_EXPRESSION_LINE_WRAP_POLICY);
		line_wrap_binary_expression_indent_policy = getIntValue(
				preferences,
				CodeFormatterConstants.FORMATTER_ALIGNMENT_FOR_BINARY_EXPRESSION_INDENT_POLICY);
		line_wrap_binary_expression_force_split = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_ALIGNMENT_FOR_BINARY_EXPRESSION_FORCE_SPLIT);

		line_wrap_conditional_expression_line_wrap_policy = getIntValue(
				preferences,
				CodeFormatterConstants.FORMATTER_ALIGNMENT_FOR_CONDITIONAL_EXPRESSION_LINE_WRAP_POLICY);
		line_wrap_conditional_expression_indent_policy = getIntValue(
				preferences,
				CodeFormatterConstants.FORMATTER_ALIGNMENT_FOR_CONDITIONAL_EXPRESSION_INDENT_POLICY);
		line_wrap_conditional_expression_force_split = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_ALIGNMENT_FOR_CONDITIONAL_EXPRESSION_FORCE_SPLIT);

		line_wrap_expressions_in_array_init_line_wrap_policy = getIntValue(
				preferences,
				CodeFormatterConstants.FORMATTER_ALIGNMENT_FOR_EXPRESSIONS_IN_ARRAY_INITIALIZER_LINE_WRAP_POLICY);
		line_wrap_expressions_in_array_init_indent_policy = getIntValue(
				preferences,
				CodeFormatterConstants.FORMATTER_ALIGNMENT_FOR_EXPRESSIONS_IN_ARRAY_INITIALIZER_INDENT_POLICY);
		line_wrap_expressions_in_array_init_force_split = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_ALIGNMENT_FOR_EXPRESSIONS_IN_ARRAY_INITIALIZER_FORCE_SPLIT);

		line_wrap_assignments_expression_line_wrap_policy = getIntValue(
				preferences,
				CodeFormatterConstants.FORMATTER_ALIGNMENT_FOR_ASSIGNMENT_LINE_WRAP_POLICY);
		line_wrap_assignments_expression_indent_policy = getIntValue(
				preferences,
				CodeFormatterConstants.FORMATTER_ALIGNMENT_FOR_ASSIGNMENT_INDENT_POLICY);
		line_wrap_assignments_expression_force_split = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_ALIGNMENT_FOR_ASSIGNMENT_FORCE_SPLIT);

		line_wrap_compact_if_line_wrap_policy = getIntValue(
				preferences,
				CodeFormatterConstants.FORMATTER_ALIGNMENT_FOR_COMPACT_IF_LINE_WRAP_POLICY);
		line_wrap_compact_if_indent_policy = getIntValue(
				preferences,
				CodeFormatterConstants.FORMATTER_ALIGNMENT_FOR_COMPACT_IF_INDENT_POLICY);
		line_wrap_compact_if_force_split = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_ALIGNMENT_FOR_COMPACT_IF_FORCE_SPLIT);

		// control statements
		control_statement_insert_newline_before_else_and_elseif_in_if = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_NEW_LINE_BEFORE_ELSE_AND_ELSEIF_IN_IF_STATEMENT);
		control_statement_insert_newline_before_catch_in_try = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_NEW_LINE_BEFORE_CATCH_IN_TRY_STATEMENT);
		control_statement_insert_newline_before_while_in_do = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_INSERT_NEW_LINE_BEFORE_WHILE_IN_DO_STATEMENT);
		control_statement_keep_then_on_same_line = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_KEEP_THEN_STATEMENT_ON_SAME_LINE);
		control_statement_keep_simple_if_on_one_line = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_KEEP_SIMPLE_IF_ON_ONE_LINE);
		control_statement_keep_else_on_same_line = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_KEEP_ELSE_STATEMENT_ON_SAME_LINE);
		control_statement_keep_else_if_on_same_line = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_KEEP_ELSEIF_STATEMENT_ON_SAME_LINE);
		control_statement_keep_guardian_on_one_line = getBooleanValue(
				preferences,
				CodeFormatterConstants.FORMATTER_KEEP_GUARDIAN_CLAUSE_ON_ONE_LINE);

		// comments

		final Object commentFormatJavadocCommentOption = preferences
				.get(CodeFormatterConstants.FORMATTER_COMMENT_FORMAT_JAVADOC_COMMENT);
		if (commentFormatJavadocCommentOption != null) {
			this.comment_format_javadoc_comment = CodeFormatterConstants.TRUE
					.equals(commentFormatJavadocCommentOption);
		}
		final Object commentFormatBlockCommentOption = preferences
				.get(CodeFormatterConstants.FORMATTER_COMMENT_FORMAT_BLOCK_COMMENT);
		if (commentFormatBlockCommentOption != null) {
			this.comment_format_block_comment = CodeFormatterConstants.TRUE
					.equals(commentFormatBlockCommentOption);
		}
		final Object commentFormatLineCommentOption = preferences
				.get(CodeFormatterConstants.FORMATTER_COMMENT_FORMAT_LINE_COMMENT);
		if (commentFormatLineCommentOption != null) {
			this.comment_format_line_comment = CodeFormatterConstants.TRUE
					.equals(commentFormatLineCommentOption);
		}
		final Object formatLineCommentStartingOnFirstColumnOption = preferences
				.get(CodeFormatterConstants.FORMATTER_COMMENT_FORMAT_LINE_COMMENT_STARTING_ON_FIRST_COLUMN);
		if (formatLineCommentStartingOnFirstColumnOption != null) {
			this.comment_format_line_comment_starting_on_first_column = CodeFormatterConstants.TRUE
					.equals(formatLineCommentStartingOnFirstColumnOption);
		}
		final Object commentFormatHeaderOption = preferences
				.get(CodeFormatterConstants.FORMATTER_COMMENT_FORMAT_HEADER);
		if (commentFormatHeaderOption != null) {
			this.comment_format_header = CodeFormatterConstants.TRUE
					.equals(commentFormatHeaderOption);
		}
		final Object commentFormatHtmlOption = preferences
				.get(CodeFormatterConstants.FORMATTER_COMMENT_FORMAT_HTML);
		if (commentFormatHtmlOption != null) {
			this.comment_format_html = CodeFormatterConstants.TRUE
					.equals(commentFormatHtmlOption);
		}
		final Object commentFormatSourceOption = preferences
				.get(CodeFormatterConstants.FORMATTER_COMMENT_FORMAT_SOURCE);
		if (commentFormatSourceOption != null) {
			this.comment_format_source = CodeFormatterConstants.TRUE
					.equals(commentFormatSourceOption);
		}
		final Object commentIndentParameterDescriptionOption = preferences
				.get(CodeFormatterConstants.FORMATTER_COMMENT_INDENT_PARAMETER_DESCRIPTION);
		if (commentIndentParameterDescriptionOption != null) {
			this.comment_indent_parameter_description = CodeFormatterConstants.TRUE
					.equals(commentIndentParameterDescriptionOption);
		}
		final Object commentIndentRootTagsOption = preferences
				.get(CodeFormatterConstants.FORMATTER_COMMENT_INDENT_ROOT_TAGS);
		if (commentIndentRootTagsOption != null) {
			this.comment_indent_root_tags = CodeFormatterConstants.TRUE
					.equals(commentIndentRootTagsOption);
		}
		final Object commentInsertEmptyLineBeforeRootTagsOption = preferences
				.get(CodeFormatterConstants.FORMATTER_COMMENT_INSERT_EMPTY_LINE_BEFORE_ROOT_TAGS);
		if (commentInsertEmptyLineBeforeRootTagsOption != null) {
			this.comment_insert_empty_line_before_root_tags = CodeFormatterConstants.TRUE
					.equals(commentInsertEmptyLineBeforeRootTagsOption);
		}
		final Object commentInsertNewLineForParameterOption = preferences
				.get(CodeFormatterConstants.FORMATTER_COMMENT_INSERT_NEW_LINE_FOR_PARAMETER);
		if (commentInsertNewLineForParameterOption != null) {
			this.comment_insert_new_line_for_parameter = CodeFormatterConstants.TRUE
					.equals(commentInsertNewLineForParameterOption);
		}
		final Object commentPreserveWhiteSpaceBetweenCodeAndLineCommentsOption = preferences
				.get(CodeFormatterConstants.FORMATTER_COMMENT_PRESERVE_WHITE_SPACE_BETWEEN_CODE_AND_LINE_COMMENT);
		if (commentPreserveWhiteSpaceBetweenCodeAndLineCommentsOption != null) {
			this.comment_preserve_white_space_between_code_and_line_comments = CodeFormatterConstants.TRUE
					.equals(commentPreserveWhiteSpaceBetweenCodeAndLineCommentsOption);
		}
		final Object commentClearBlankLinesInJavadocCommentOption = preferences
				.get(CodeFormatterConstants.FORMATTER_COMMENT_CLEAR_BLANK_LINES_IN_JAVADOC_COMMENT);
		if (commentClearBlankLinesInJavadocCommentOption != null) {
			this.comment_clear_blank_lines_in_javadoc_comment = CodeFormatterConstants.TRUE
					.equals(commentClearBlankLinesInJavadocCommentOption);
		}
		final Object commentClearBlankLinesInBlockCommentOption = preferences
				.get(CodeFormatterConstants.FORMATTER_COMMENT_CLEAR_BLANK_LINES_IN_BLOCK_COMMENT);
		if (commentClearBlankLinesInBlockCommentOption != null) {
			this.comment_clear_blank_lines_in_block_comment = CodeFormatterConstants.TRUE
					.equals(commentClearBlankLinesInBlockCommentOption);
		}
		final Object commentLineLengthOption = preferences
				.get(CodeFormatterConstants.FORMATTER_COMMENT_LINE_LENGTH);
		if (commentLineLengthOption != null) {
			try {
				this.comment_line_length = Integer
						.parseInt((String) commentLineLengthOption);
			} catch (NumberFormatException e) {
				this.comment_line_length = -1;
			} catch (ClassCastException e) {
				this.comment_line_length = -1;
			}
		}
		final Object commentNewLinesAtBlockBoundariesOption = preferences
				.get(CodeFormatterConstants.FORMATTER_COMMENT_NEW_LINES_AT_BLOCK_BOUNDARIES);
		if (commentNewLinesAtBlockBoundariesOption != null) {
			this.comment_new_lines_at_block_boundaries = CodeFormatterConstants.TRUE
					.equals(commentNewLinesAtBlockBoundariesOption);
		}
		final Object commentNewLinesAtJavadocBoundariesOption = preferences
				.get(CodeFormatterConstants.FORMATTER_COMMENT_NEW_LINES_AT_JAVADOC_BOUNDARIES);
		if (commentNewLinesAtJavadocBoundariesOption != null) {
			this.comment_new_lines_at_javadoc_boundaries = CodeFormatterConstants.TRUE
					.equals(commentNewLinesAtJavadocBoundariesOption);
		}
		final Object neverIndentBlockCommentOnFirstColumnOption = preferences
				.get(CodeFormatterConstants.FORMATTER_NEVER_INDENT_BLOCK_COMMENTS_ON_FIRST_COLUMN);
		if (neverIndentBlockCommentOnFirstColumnOption != null) {
			this.never_indent_block_comments_on_first_column = CodeFormatterConstants.TRUE
					.equals(neverIndentBlockCommentOnFirstColumnOption);
		}
		final Object neverIndentLineCommentOnFirstColumnOption = preferences
				.get(CodeFormatterConstants.FORMATTER_NEVER_INDENT_LINE_COMMENTS_ON_FIRST_COLUMN);
		if (neverIndentLineCommentOnFirstColumnOption != null) {
			this.never_indent_line_comments_on_first_column = CodeFormatterConstants.TRUE
					.equals(neverIndentLineCommentOnFirstColumnOption);
		}
		final Object joinLinesInCommentsOption = preferences
				.get(CodeFormatterConstants.FORMATTER_JOIN_LINES_IN_COMMENTS);
		if (joinLinesInCommentsOption != null) {
			this.join_lines_in_comments = CodeFormatterConstants.TRUE
					.equals(joinLinesInCommentsOption);
		}
		final Object useTags = preferences
				.get(CodeFormatterConstants.FORMATTER_USE_ON_OFF_TAGS);
		if (useTags != null) {
			this.use_tags = CodeFormatterConstants.TRUE.equals(useTags);
		}
		final Object disableTagOption = preferences
				.get(CodeFormatterConstants.FORMATTER_DISABLING_TAG);
		if (disableTagOption != null) {
			if (disableTagOption instanceof String) {
				String stringValue = (String) disableTagOption;
				int idx = stringValue.indexOf('\n');
				if (idx == 0) {
					this.disabling_tag = null;
				} else {
					String tag = idx < 0 ? stringValue.trim() : stringValue
							.substring(0, idx).trim();
					if (tag.length() == 0) {
						this.disabling_tag = null;
					} else {
						this.disabling_tag = tag.toCharArray();
					}
				}
			}
		}
		final Object enableTagOption = preferences
				.get(CodeFormatterConstants.FORMATTER_ENABLING_TAG);
		if (enableTagOption != null) {
			if (enableTagOption instanceof String) {
				String stringValue = (String) enableTagOption;
				int idx = stringValue.indexOf('\n');
				if (idx == 0) {
					this.enabling_tag = null;
				} else {
					String tag = idx < 0 ? stringValue.trim() : stringValue
							.substring(0, idx).trim();
					if (tag.length() == 0) {
						this.enabling_tag = null;
					} else {
						this.enabling_tag = tag.toCharArray();
					}
				}
			}
		}
	}

	private int getIntValue(Map<String, Object> preferences, String prefName) {
		String value = (String) preferences.get(prefName);
		if (value != null) {
			return Integer.parseInt(value);
		}
		return 0;
	}

	private byte getByteValue(Map<String, Object> preferences, String key) {
		String value = (String) preferences.get(key);
		return Byte.parseByte(value);
	}

	private boolean getBooleanValue(Map<String, Object> preferences, String key) {
		return TRUE.equals(preferences.get(key));
	}

	public static CodeFormatterPreferences getDefaultPreferences() {
		return new PHPDefaultFormatterPreferences().initValues();
	}

	public String toString() {
		return getMap().toString();
	}

	public Map<String, Object> getMap() {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("indentationChar", String.valueOf(indentationChar)); //$NON-NLS-1$
		options.put(CodeFormatterConstants.FORMATTER_INDENTATION_SIZE,
				String.valueOf(indentationSize));
		options.put(CodeFormatterConstants.FORMATTER_TAB_SIZE,
				String.valueOf(tabSize));

		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_OPENING_PAREN_IN_WHILE,
				insert_space_after_opening_paren_in_while ? TRUE : FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_OPENING_PAREN_IN_WHILE,
				insert_space_before_opening_paren_in_while ? TRUE : FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_CLOSING_PAREN_IN_WHILE,
				insert_space_before_closing_paren_in_while ? TRUE : FALSE);

		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_OPENING_PAREN_IN_SWITCH,
				insert_space_before_opening_paren_in_switch ? TRUE : FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_OPENING_PAREN_IN_SWITCH,
				insert_space_after_opening_paren_in_switch ? TRUE : FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_CLOSING_PAREN_IN_SWITCH,
				insert_space_before_closing_paren_in_switch ? TRUE : FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_OPENING_BRACE_IN_SWITCH,
				insert_space_before_opening_brace_in_switch ? TRUE : FALSE);
		options.put(CodeFormatterConstants.FORMATTER_BRACE_POSITION_FOR_SWITCH,
				Byte.toString(brace_position_for_switch));
		options.put(
				CodeFormatterConstants.FORMATTER_INDENT_SWITCHSTATEMENTS_COMPARE_TO_SWITCH,
				indent_statements_within_switch ? TRUE : FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_COLON_IN_DEFAULT,
				insert_space_after_switch_default ? TRUE : FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_COLON_IN_CASE,
				insert_space_after_switch_case_value ? TRUE : FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_INDENT_SWITCHSTATEMENTS_COMPARE_TO_CASES,
				indent_statements_within_case ? TRUE : FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_INDENT_BREAKS_COMPARE_TO_CASES,
				indent_break_statements_within_case ? TRUE : FALSE);

		options.put(CodeFormatterConstants.FORMATTER_BRACE_POSITION_FOR_BLOCK,
				String.valueOf(brace_position_for_block));
		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_OPENING_BRACE_IN_BLOCK,
				insert_space_before_opening_brace_in_block ? TRUE : FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_CLOSING_BRACE_IN_BLOCK,
				insert_space_after_closing_brace_in_block ? TRUE : FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_INDENT_STATEMENTS_COMPARE_TO_BLOCK,
				indent_statements_within_block ? TRUE : FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_SEMICOLON,
				insert_space_before_semicolon ? TRUE : FALSE);

		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_ASSIGNMENT_OPERATOR,
				insert_space_before_assignment ? TRUE : FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_ASSIGNMENT_OPERATOR,
				insert_space_after_assignment ? TRUE : FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_BINARY_OPERATOR,
				insert_space_before_binary_operation ? TRUE : FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_BINARY_OPERATOR,
				insert_space_after_binary_operation ? TRUE : FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_POSTFIX_OPERATOR,
				insert_space_after_postfix_expression ? TRUE : FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_POSTFIX_OPERATOR,
				insert_space_before_postfix_expression ? TRUE : FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_PREFIX_OPERATOR,
				insert_space_before_prefix_expression ? TRUE : FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_PREFIX_OPERATOR,
				insert_space_after_prefix_expression ? TRUE : FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_UNARY_OPERATOR,
				insert_space_before_unary_expression ? TRUE : FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_UNARY_OPERATOR,
				insert_space_after_unary_expression ? TRUE : FALSE);

		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_OPENING_PAREN_IN_CAST,
				insert_space_before_cast_type ? TRUE : FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_CLOSING_PAREN_IN_CAST,
				insert_space_after_cast_type ? TRUE : FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_CLOSING_PAREN_IN_CAST,
				insert_space_after_cast_expression ? TRUE : FALSE);

		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_COLON_IN_CONDITIONAL,
				insert_space_after_conditional_colon ? TRUE : FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_COLON_IN_CONDITIONAL,
				insert_space_before_conditional_colon ? TRUE : FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_QUESTION_IN_CONDITIONAL,
				insert_space_after_conditional_question_mark ? TRUE : FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_QUESTION_IN_CONDITIONAL,
				insert_space_before_conditional_question_mark ? TRUE : FALSE);

		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_OPENING_PAREN_IN_CATCH,
				insert_space_before_opening_paren_in_catch ? TRUE : FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_OPENING_PAREN_IN_CATCH,
				insert_space_after_opening_paren_in_catch ? TRUE : FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_CLOSING_PAREN_IN_CATCH,
				insert_space_before_closing_paren_in_catch ? TRUE : FALSE);

		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_COMMA_IN_SUPERINTERFACES,
				insert_space_before_comma_in_implements ? TRUE : FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_COMMA_IN_SUPERINTERFACES,
				insert_space_after_comma_in_implements ? TRUE : FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_BRACE_POSITION_FOR_TYPE_DECLARATION,
				String.valueOf(brace_position_for_class));
		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_OPENING_BRACE_IN_TYPE_DECLARATION,
				insert_space_before_opening_brace_in_class ? TRUE : FALSE);

		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_OPENING_PAREN_IN_METHOD_INVOCATION,
				insert_space_before_opening_paren_in_function ? TRUE : FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_OPENING_PAREN_IN_METHOD_INVOCATION,
				insert_space_after_opening_paren_in_function ? TRUE : FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_COMMA_IN_METHOD_INVOCATION_ARGUMENTS,
				insert_space_before_comma_in_function ? TRUE : FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_COMMA_IN_METHOD_INVOCATION_ARGUMENTS,
				insert_space_after_comma_in_function ? TRUE : FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_CLOSING_PAREN_IN_METHOD_INVOCATION,
				insert_space_before_closing_paren_in_function ? TRUE : FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BETWEEN_EMPTY_PARENS_IN_METHOD_INVOCATION,
				insert_space_between_empty_paren_in_function ? TRUE : FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_ARROW_IN_METHOD_INVOCATION,
				insert_space_before_arrow_in_method_invocation ? TRUE : FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_ARROW_IN_METHOD_INVOCATION,
				insert_space_after_arrow_in_method_invocation ? TRUE : FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_COLONCOLON_IN_METHOD_INVOCATION,
				insert_space_before_coloncolon_in_method_invocation ? TRUE
						: FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_COLONCOLON_IN_METHOD_INVOCATION,
				insert_space_after_coloncolon_in_method_invocation ? TRUE
						: FALSE);

		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_ARROW_IN_FIELD_ACCESS,
				insert_space_before_arrow_in_field_access ? TRUE : FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_ARROW_IN_FIELD_ACCESS,
				insert_space_after_arrow_in_field_access ? TRUE : FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_COLONCOLON_IN_FIELD_ACCESS,
				insert_space_before_coloncolon_in_field_access ? TRUE : FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_COLONCOLON_IN_FIELD_ACCESS,
				insert_space_after_coloncolon_in_field_access ? TRUE : FALSE);

		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_OPENING_PAREN_IN_FOR,
				insert_space_before_open_paren_in_for ? TRUE : FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_OPENING_PAREN_IN_FOR,
				insert_space_after_open_paren_in_for ? TRUE : FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_CLOSING_PAREN_IN_FOR,
				insert_space_before_close_paren_in_for ? TRUE : FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_COMMA_IN_FOR,
				insert_space_before_comma_in_for ? TRUE : FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_COMMA_IN_FOR,
				insert_space_after_comma_in_for ? TRUE : FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_SEMICOLON_IN_FOR,
				insert_space_before_semicolon_in_for ? TRUE : FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_SEMICOLON_IN_FOR,
				insert_space_after_semicolon_in_for ? TRUE : FALSE);

		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_OPENING_PAREN_IN_FOREACH,
				insert_space_before_open_paren_in_foreach ? TRUE : FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_OPENING_PAREN_IN_FOREACH,
				insert_space_after_open_paren_in_foreach ? TRUE : FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_CLOSING_PAREN_IN_FOREACH,
				insert_space_before_close_paren_in_foreach ? TRUE : FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_ARROW_IN_FOREACH,
				insert_space_before_arrow_in_foreach ? TRUE : FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_ARROW_IN_FOREACH,
				insert_space_after_arrow_in_foreach ? TRUE : FALSE);

		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_COMMA_IN_MULTIPLE_FIELD_DECLARATIONS,
				insert_space_before_comma_in_class_variable ? TRUE : FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_COMMA_IN_MULTIPLE_FIELD_DECLARATIONS,
				insert_space_after_comma_in_class_variable ? TRUE : FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_COMMA_IN_MULTIPLE_CONSTANT_DECLARATIONS,
				insert_space_before_comma_in_class_constant ? TRUE : FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_COMMA_IN_MULTIPLE_CONSTANT_DECLARATIONS,
				insert_space_after_comma_in_class_constant ? TRUE : FALSE);

		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_OPENING_BRACKET_IN_ARRAY_REFERENCE,
				insert_space_before_opening_bracket_in_array ? TRUE : FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_OPENING_BRACKET_IN_ARRAY_REFERENCE,
				insert_space_after_opening_bracket_in_array ? TRUE : FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_CLOSING_BRACKET_IN_ARRAY_REFERENCE,
				insert_space_before_closing_bracket_in_array ? TRUE : FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BETWEEN_EMPTY_BRACKETS_IN_ARRAY_TYPE_REFERENCE,
				insert_space_between_empty_brackets ? TRUE : FALSE);

		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_OPENING_PAREN_IN_ARRAY_CREATION,
				insert_space_before_opening_paren_in_array ? TRUE : FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_OPENING_PAREN_IN_ARRAY_CREATION,
				insert_space_after_opening_paren_in_array ? TRUE : FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_CLOSING_PAREN_IN_ARRAY_CREATION,
				insert_space_before_closing_paren_in_array ? TRUE : FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_COMMA_IN_ARRAY_CREATION,
				insert_space_before_list_comma_in_array ? TRUE : FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_COMMA_IN_ARRAY_CREATION,
				insert_space_after_list_comma_in_array ? TRUE : FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_ARROW_IN_ARRAY_CREATION,
				insert_space_before_arrow_in_array ? TRUE : FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_ARROW_IN_ARRAY_CREATION,
				insert_space_after_arrow_in_array ? TRUE : FALSE);

		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_OPENING_PAREN_IN_LIST,
				insert_space_before_opening_paren_in_list ? TRUE : FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_OPENING_PAREN_IN_LIST,
				insert_space_after_opening_paren_in_list ? TRUE : FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_CLOSING_PAREN_IN_LIST,
				insert_space_before_closing_paren_in_list ? TRUE : FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_COMMA_IN_LIST,
				insert_space_before_comma_in_list ? TRUE : FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_COMMA_IN_LIST,
				insert_space_after_comma_in_list ? TRUE : FALSE);

		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_OPENING_PAREN_IN_METHOD_DECLARATION,
				insert_space_before_opening_paren_in_function_declaration ? TRUE
						: FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_OPENING_PAREN_IN_METHOD_DECLARATION,
				insert_space_after_opening_paren_in_function_declaration ? TRUE
						: FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BETWEEN_EMPTY_PARENS_IN_METHOD_DECLARATION,
				insert_space_between_empty_paren_in_function_declaration ? TRUE
						: FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_CLOSING_PAREN_IN_METHOD_DECLARATION,
				insert_space_before_closing_paren_in_function_declaration ? TRUE
						: FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_COMMA_IN_METHOD_DECLARATION_PARAMETERS,
				insert_space_before_comma_in_function_declaration ? TRUE
						: FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_COMMA_IN_METHOD_DECLARATION_PARAMETERS,
				insert_space_after_comma_in_function_declaration ? TRUE : FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_BRACE_POSITION_FOR_METHOD_DECLARATION,
				String.valueOf(brace_position_for_function));
		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_OPENING_BRACE_IN_METHOD_DECLARATION,
				insert_space_before_opening_brace_in_function ? TRUE : FALSE);

		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_OPENING_PAREN_IN_IF,
				insert_space_before_opening_paren_in_if ? TRUE : FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_OPENING_PAREN_IN_IF,
				insert_space_after_opening_paren_in_if ? TRUE : FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_CLOSING_PAREN_IN_IF,
				insert_space_before_closing_paren_in_if ? TRUE : FALSE);

		options.put("insert_space_before_opening_paren_in_declare", //$NON-NLS-1$
				insert_space_before_opening_paren_in_declare ? TRUE : FALSE);
		options.put("insert_space_after_opening_paren_in_declare", //$NON-NLS-1$
				insert_space_after_opening_paren_in_declare ? TRUE : FALSE);
		options.put("insert_space_before_closing_paren_in_declare", //$NON-NLS-1$
				insert_space_before_closing_paren_in_declare ? TRUE : FALSE);

		options.put(
				CodeFormatterConstants.FORMATTER_INDENT_BODY_DECLARATIONS_COMPARE_TO_TYPE_HEADER,
				indent_statements_within_type_declaration ? TRUE : FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_INDENT_STATEMENTS_COMPARE_TO_BODY,
				indent_statements_within_function ? TRUE : FALSE);
		options.put(CodeFormatterConstants.FORMATTER_INDENT_EMPTY_LINES,
				indent_empty_lines ? TRUE : FALSE);

		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_COMMA_IN_STATIC,
				insert_space_before_comma_in_static ? TRUE : FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_COMMA_IN_STATIC,
				insert_space_after_comma_in_static ? TRUE : FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_COMMA_IN_GLOBAL,
				insert_space_before_comma_in_global ? TRUE : FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_COMMA_IN_GLOBAL,
				insert_space_after_comma_in_global ? TRUE : FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_COMMA_IN_ECHO,
				insert_space_before_comma_in_echo ? TRUE : FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_COMMA_IN_ECHO,
				insert_space_after_comma_in_echo ? TRUE : FALSE);

		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_OPENING_PAREN_IN_PARENTHESIZED_EXPRESSION,
				insert_space_after_open_paren_in_parenthesis_expression ? TRUE
						: FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_CLOSING_PAREN_IN_PARENTHESIZED_EXPRESSION,
				insert_space_before_close_paren_in_parenthesis_expression ? TRUE
						: FALSE);

		options.put(
				CodeFormatterConstants.FORMATTER_NUMBER_OF_EMPTY_LINES_TO_PRESERVE,
				String.valueOf(blank_line_preserve_empty_lines));
		options.put(
				CodeFormatterConstants.FORMATTER_BLANK_LINES_BETWEEN_TYPE_DECLARATIONS,
				String.valueOf(blank_line_before_class_declaration));
		options.put(
				CodeFormatterConstants.FORMATTER_BLANK_LINES_BEFORE_MEMBER_TYPE,
				String.valueOf(blank_line_before_constant_declaration));
		options.put(CodeFormatterConstants.FORMATTER_BLANK_LINES_BEFORE_FIELD,
				String.valueOf(blank_line_before_field_declaration));
		options.put(CodeFormatterConstants.FORMATTER_BLANK_LINES_BEFORE_METHOD,
				String.valueOf(blank_line_before_method_declaration));
		options.put(
				CodeFormatterConstants.FORMATTER_BLANK_LINES_AT_BEGINNING_OF_METHOD_BODY,
				String.valueOf(blank_line_at_begin_of_method));
		options.put(
				CodeFormatterConstants.FORMATTER_BLANK_LINES_AT_END_OF_CLASS_BODY,
				String.valueOf(blank_line_at_end_of_class));
		options.put(
				CodeFormatterConstants.FORMATTER_BLANK_LINES_AT_END_OF_METHOD_BODY,
				String.valueOf(blank_line_at_end_of_method));

		// namespace
		options.put(
				CodeFormatterConstants.FORMATTER_BLANK_LINES_BEFORE_NAMESPACE,
				String.valueOf(blank_lines_before_namespace));
		options.put(
				CodeFormatterConstants.FORMATTER_BLANK_LINES_AFTER_NAMESPACE,
				String.valueOf(blank_lines_after_namespace));
		options.put(
				CodeFormatterConstants.FORMATTER_BLANK_LINES_BEFORE_USE_STATEMENTS,
				String.valueOf(blank_lines_before_use_statements));
		options.put(
				CodeFormatterConstants.FORMATTER_BLANK_LINES_AFTER_USE_STATEMENTS,
				String.valueOf(blank_lines_after_use_statements));
		options.put(
				CodeFormatterConstants.FORMATTER_BLANK_LINES_BETWEEN_USE_STATEMENTS,
				String.valueOf(blank_lines_between_use_statements));
		options.put(
				CodeFormatterConstants.FORMATTER_BLANK_LINES_BETWEEN_NAMESPACES,
				String.valueOf(blank_lines_between_namespaces));

		// new line
		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_NEW_LINE_IN_EMPTY_TYPE_DECLARATION,
				new_line_in_empty_class_body ? TRUE : FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_NEW_LINE_IN_EMPTY_METHOD_BODY,
				new_line_in_empty_method_body ? TRUE : FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_NEW_LINE_IN_EMPTY_BLOCK,
				new_line_in_empty_block ? TRUE : FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_PUT_EMPTY_STATEMENT_ON_NEW_LINE,
				new_line_for_empty_statement ? TRUE : FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_NEW_LINE_AFTER_OPENING_BRACE_IN_ARRAY_INITIALIZER,
				new_line_after_open_array_parenthesis ? TRUE : FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_NEW_LINE_BEFORE_CLOSING_BRACE_IN_ARRAY_INITIALIZER,
				new_line_before_close_array_parenthesis_array ? TRUE : FALSE);

		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_NEW_LINE_IN_SECOND_METHOD_INVOKE,
				String.valueOf(new_line_in_second_invoke));

		// line wrapping
		options.put(CodeFormatterConstants.FORMATTER_LINE_SPLIT,
				String.valueOf(line_wrap_line_split));
		options.put(CodeFormatterConstants.FORMATTER_CONTINUATION_INDENTATION,
				String.valueOf(line_wrap_wrapped_lines_indentation));
		options.put(
				CodeFormatterConstants.FORMATTER_CONTINUATION_INDENTATION_FOR_ARRAY_INITIALIZER,
				String.valueOf(line_wrap_array_init_indentation));

		options.put(
				CodeFormatterConstants.FORMATTER_ALIGNMENT_FOR_SUPERCLASS_IN_TYPE_DECLARATION_LINE_WRAP_POLICY,
				String.valueOf(line_wrap_superclass_in_type_declaration_line_wrap_policy));
		options.put(
				CodeFormatterConstants.FORMATTER_ALIGNMENT_FOR_SUPERCLASS_IN_TYPE_DECLARATION_INDENT_POLICY,
				String.valueOf(line_wrap_superclass_in_type_declaration_indent_policy));
		options.put(
				CodeFormatterConstants.FORMATTER_ALIGNMENT_FOR_SUPERCLASS_IN_TYPE_DECLARATION_FORCE_SPLIT,
				line_wrap_superclass_in_type_declaration_force_split ? TRUE
						: FALSE);

		options.put(
				CodeFormatterConstants.FORMATTER_ALIGNMENT_FOR_SUPERINTERFACES_IN_TYPE_DECLARATION_LINE_WRAP_POLICY,
				String.valueOf(line_wrap_superinterfaces_in_type_declaration_line_wrap_policy));
		options.put(
				CodeFormatterConstants.FORMATTER_ALIGNMENT_FOR_SUPERINTERFACES_IN_TYPE_DECLARATION_INDENT_POLICY,
				String.valueOf(line_wrap_superinterfaces_in_type_declaration_indent_policy));
		options.put(
				CodeFormatterConstants.FORMATTER_ALIGNMENT_FOR_SUPERINTERFACES_IN_TYPE_DECLARATION_FORCE_SPLIT,
				line_wrap_superinterfaces_in_type_declaration_force_split ? TRUE
						: FALSE);

		options.put(
				CodeFormatterConstants.FORMATTER_ALIGNMENT_FOR_PARAMETERS_IN_METHOD_DECLARATION_LINE_WRAP_POLICY,
				String.valueOf(line_wrap_parameters_in_method_declaration_line_wrap_policy));
		options.put(
				CodeFormatterConstants.FORMATTER_ALIGNMENT_FOR_PARAMETERS_IN_METHOD_DECLARATION_INDENT_POLICY,
				String.valueOf(line_wrap_parameters_in_method_declaration_indent_policy));
		options.put(
				CodeFormatterConstants.FORMATTER_ALIGNMENT_FOR_PARAMETERS_IN_METHOD_DECLARATION_FORCE_SPLIT,
				line_wrap_parameters_in_method_declaration_force_split ? TRUE
						: FALSE);

		options.put(
				CodeFormatterConstants.FORMATTER_ALIGNMENT_FOR_ARGUMENTS_IN_METHOD_INVOCATION_LINE_WRAP_POLICY,
				String.valueOf(line_wrap_arguments_in_method_invocation_line_wrap_policy));
		options.put(
				CodeFormatterConstants.FORMATTER_ALIGNMENT_FOR_ARGUMENTS_IN_METHOD_INVOCATION_INDENT_POLICY,
				String.valueOf(line_wrap_arguments_in_method_invocation_indent_policy));
		options.put(
				CodeFormatterConstants.FORMATTER_ALIGNMENT_FOR_ARGUMENTS_IN_METHOD_INVOCATION_FORCE_SPLIT,
				line_wrap_arguments_in_method_invocation_force_split ? TRUE
						: FALSE);

		options.put(
				CodeFormatterConstants.FORMATTER_ALIGNMENT_FOR_ARGUMENTS_IN_ALLOCATION_EXPRESSION_LINE_WRAP_POLICY,
				String.valueOf(line_wrap_arguments_in_allocation_expression_line_wrap_policy));
		options.put(
				CodeFormatterConstants.FORMATTER_ALIGNMENT_FOR_ARGUMENTS_IN_ALLOCATION_EXPRESSION_INDENT_POLICY,
				String.valueOf(line_wrap_arguments_in_allocation_expression_indent_policy));
		options.put(
				CodeFormatterConstants.FORMATTER_ALIGNMENT_FOR_ARGUMENTS_IN_ALLOCATION_EXPRESSION_FORCE_SPLIT,
				line_wrap_arguments_in_allocation_expression_force_split ? TRUE
						: FALSE);

		options.put(
				CodeFormatterConstants.FORMATTER_ALIGNMENT_FOR_BINARY_EXPRESSION_LINE_WRAP_POLICY,
				String.valueOf(line_wrap_binary_expression_line_wrap_policy));
		options.put(
				CodeFormatterConstants.FORMATTER_ALIGNMENT_FOR_BINARY_EXPRESSION_INDENT_POLICY,
				String.valueOf(line_wrap_binary_expression_indent_policy));
		options.put(
				CodeFormatterConstants.FORMATTER_ALIGNMENT_FOR_BINARY_EXPRESSION_FORCE_SPLIT,
				line_wrap_binary_expression_force_split ? TRUE : FALSE);

		options.put(
				CodeFormatterConstants.FORMATTER_ALIGNMENT_FOR_CONDITIONAL_EXPRESSION_LINE_WRAP_POLICY,
				String.valueOf(line_wrap_conditional_expression_line_wrap_policy));
		options.put(
				CodeFormatterConstants.FORMATTER_ALIGNMENT_FOR_CONDITIONAL_EXPRESSION_INDENT_POLICY,
				String.valueOf(line_wrap_conditional_expression_indent_policy));
		options.put(
				CodeFormatterConstants.FORMATTER_ALIGNMENT_FOR_CONDITIONAL_EXPRESSION_FORCE_SPLIT,
				line_wrap_conditional_expression_force_split ? TRUE : FALSE);

		options.put(
				CodeFormatterConstants.FORMATTER_ALIGNMENT_FOR_EXPRESSIONS_IN_ARRAY_INITIALIZER_LINE_WRAP_POLICY,
				String.valueOf(line_wrap_expressions_in_array_init_line_wrap_policy));
		options.put(
				CodeFormatterConstants.FORMATTER_ALIGNMENT_FOR_EXPRESSIONS_IN_ARRAY_INITIALIZER_INDENT_POLICY,
				String.valueOf(line_wrap_expressions_in_array_init_indent_policy));
		options.put(
				CodeFormatterConstants.FORMATTER_ALIGNMENT_FOR_EXPRESSIONS_IN_ARRAY_INITIALIZER_FORCE_SPLIT,
				line_wrap_expressions_in_array_init_force_split ? TRUE : FALSE);

		options.put(
				CodeFormatterConstants.FORMATTER_ALIGNMENT_FOR_ASSIGNMENT_LINE_WRAP_POLICY,
				String.valueOf(line_wrap_assignments_expression_line_wrap_policy));
		options.put(
				CodeFormatterConstants.FORMATTER_ALIGNMENT_FOR_ASSIGNMENT_INDENT_POLICY,
				String.valueOf(line_wrap_assignments_expression_indent_policy));
		options.put(
				CodeFormatterConstants.FORMATTER_ALIGNMENT_FOR_ASSIGNMENT_FORCE_SPLIT,
				line_wrap_assignments_expression_force_split ? TRUE : FALSE);

		options.put(
				CodeFormatterConstants.FORMATTER_ALIGNMENT_FOR_COMPACT_IF_LINE_WRAP_POLICY,
				String.valueOf(line_wrap_compact_if_line_wrap_policy));
		options.put(
				CodeFormatterConstants.FORMATTER_ALIGNMENT_FOR_COMPACT_IF_INDENT_POLICY,
				String.valueOf(line_wrap_compact_if_indent_policy));
		options.put(
				CodeFormatterConstants.FORMATTER_ALIGNMENT_FOR_COMPACT_IF_FORCE_SPLIT,
				line_wrap_compact_if_force_split ? TRUE : FALSE);

		// control statements
		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_NEW_LINE_BEFORE_ELSE_AND_ELSEIF_IN_IF_STATEMENT,
				control_statement_insert_newline_before_else_and_elseif_in_if ? TRUE
						: FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_NEW_LINE_BEFORE_CATCH_IN_TRY_STATEMENT,
				control_statement_insert_newline_before_catch_in_try ? TRUE
						: FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_INSERT_NEW_LINE_BEFORE_WHILE_IN_DO_STATEMENT,
				control_statement_insert_newline_before_while_in_do ? TRUE
						: FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_KEEP_THEN_STATEMENT_ON_SAME_LINE,
				control_statement_keep_then_on_same_line ? TRUE : FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_KEEP_SIMPLE_IF_ON_ONE_LINE,
				control_statement_keep_simple_if_on_one_line ? TRUE : FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_KEEP_ELSE_STATEMENT_ON_SAME_LINE,
				control_statement_keep_else_on_same_line ? TRUE : FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_KEEP_ELSEIF_STATEMENT_ON_SAME_LINE,
				control_statement_keep_else_if_on_same_line ? TRUE : FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_KEEP_GUARDIAN_CLAUSE_ON_ONE_LINE,
				control_statement_keep_guardian_on_one_line ? TRUE : FALSE);
		// comments
		options.put(
				CodeFormatterConstants.FORMATTER_COMMENT_CLEAR_BLANK_LINES_IN_BLOCK_COMMENT,
				this.comment_clear_blank_lines_in_block_comment ? CodeFormatterConstants.TRUE
						: CodeFormatterConstants.FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_COMMENT_CLEAR_BLANK_LINES_IN_JAVADOC_COMMENT,
				this.comment_clear_blank_lines_in_javadoc_comment ? CodeFormatterConstants.TRUE
						: CodeFormatterConstants.FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_COMMENT_NEW_LINES_AT_BLOCK_BOUNDARIES,
				this.comment_new_lines_at_block_boundaries ? CodeFormatterConstants.TRUE
						: CodeFormatterConstants.FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_COMMENT_NEW_LINES_AT_JAVADOC_BOUNDARIES,
				this.comment_new_lines_at_javadoc_boundaries ? CodeFormatterConstants.TRUE
						: CodeFormatterConstants.FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_COMMENT_FORMAT_BLOCK_COMMENT,
				this.comment_format_block_comment ? CodeFormatterConstants.TRUE
						: CodeFormatterConstants.FALSE);
		options.put(CodeFormatterConstants.FORMATTER_COMMENT_FORMAT_HEADER,
				this.comment_format_header ? CodeFormatterConstants.TRUE
						: CodeFormatterConstants.FALSE);
		options.put(CodeFormatterConstants.FORMATTER_COMMENT_FORMAT_HTML,
				this.comment_format_html ? CodeFormatterConstants.TRUE
						: CodeFormatterConstants.FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_COMMENT_FORMAT_JAVADOC_COMMENT,
				this.comment_format_javadoc_comment ? CodeFormatterConstants.TRUE
						: CodeFormatterConstants.FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_COMMENT_FORMAT_LINE_COMMENT,
				this.comment_format_line_comment ? CodeFormatterConstants.TRUE
						: CodeFormatterConstants.FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_COMMENT_FORMAT_LINE_COMMENT_STARTING_ON_FIRST_COLUMN,
				this.comment_format_line_comment_starting_on_first_column ? CodeFormatterConstants.TRUE
						: CodeFormatterConstants.FALSE);
		options.put(CodeFormatterConstants.FORMATTER_COMMENT_FORMAT_SOURCE,
				this.comment_format_source ? CodeFormatterConstants.TRUE
						: CodeFormatterConstants.FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_COMMENT_INDENT_PARAMETER_DESCRIPTION,
				this.comment_indent_parameter_description ? CodeFormatterConstants.TRUE
						: CodeFormatterConstants.FALSE);
		options.put(CodeFormatterConstants.FORMATTER_COMMENT_INDENT_ROOT_TAGS,
				this.comment_indent_root_tags ? CodeFormatterConstants.TRUE
						: CodeFormatterConstants.FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_COMMENT_INSERT_EMPTY_LINE_BEFORE_ROOT_TAGS,
				this.comment_insert_empty_line_before_root_tags ? CodeFormatterConstants.TRUE
						: CodeFormatterConstants.FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_COMMENT_INSERT_NEW_LINE_FOR_PARAMETER,
				this.comment_insert_new_line_for_parameter ? CodeFormatterConstants.TRUE
						: CodeFormatterConstants.FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_COMMENT_PRESERVE_WHITE_SPACE_BETWEEN_CODE_AND_LINE_COMMENT,
				this.comment_preserve_white_space_between_code_and_line_comments ? CodeFormatterConstants.TRUE
						: CodeFormatterConstants.FALSE);
		options.put(CodeFormatterConstants.FORMATTER_COMMENT_LINE_LENGTH,
				Integer.toString(this.comment_line_length));

		options.put(
				CodeFormatterConstants.FORMATTER_NEVER_INDENT_BLOCK_COMMENTS_ON_FIRST_COLUMN,
				this.never_indent_block_comments_on_first_column ? CodeFormatterConstants.TRUE
						: CodeFormatterConstants.FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_NEVER_INDENT_LINE_COMMENTS_ON_FIRST_COLUMN,
				this.never_indent_line_comments_on_first_column ? CodeFormatterConstants.TRUE
						: CodeFormatterConstants.FALSE);
		options.put(CodeFormatterConstants.FORMATTER_JOIN_LINES_IN_COMMENTS,
				this.join_lines_in_comments ? CodeFormatterConstants.TRUE
						: CodeFormatterConstants.FALSE);
		options.put(
				CodeFormatterConstants.FORMATTER_DISABLING_TAG,
				this.disabling_tag == null ? CodeFormatterConstants.EMPTY_STRING
						: new String(this.disabling_tag));
		options.put(CodeFormatterConstants.FORMATTER_ENABLING_TAG,
				this.enabling_tag == null ? CodeFormatterConstants.EMPTY_STRING
						: new String(this.enabling_tag));
		options.put(CodeFormatterConstants.FORMATTER_USE_ON_OFF_TAGS,
				this.use_tags ? CodeFormatterConstants.TRUE
						: CodeFormatterConstants.FALSE);

		return options;
	}

}
