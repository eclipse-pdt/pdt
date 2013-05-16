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
package org.eclipse.php.formatter.ui.preferences;

import org.eclipse.php.formatter.core.CodeFormatterPreferences;
import org.eclipse.php.formatter.core.CodeFormatterVisitor;
import org.eclipse.php.formatter.core.ICodeFormatterPreferencesInitializer;

/**
 * Utility class for populating a <code>CodeFormatterPreferences</code> with
 * Zend Framework default formatter settings
 */
final public class PSR2FormatterPreferences implements
		ICodeFormatterPreferencesInitializer {

	public PSR2FormatterPreferences() {
	}

	public CodeFormatterPreferences initValues() {
		CodeFormatterPreferences preferences = new CodeFormatterPreferences();

		preferences.indentationChar = CodeFormatterPreferences.SPACE_CHAR;
		preferences.indentationSize = 4;
		preferences.tabSize = 4;

		preferences.brace_position_for_block = CodeFormatterPreferences.SAME_LINE;
		preferences.brace_position_for_function = CodeFormatterPreferences.NEXT_LINE;
		preferences.brace_position_for_switch = CodeFormatterPreferences.SAME_LINE;
		preferences.brace_position_for_class = CodeFormatterPreferences.NEXT_LINE;

		preferences.indent_statements_within_type_declaration = true;
		preferences.indent_break_statements_within_case = true;
		preferences.indent_empty_lines = false;
		preferences.indent_statements_within_block = true;
		preferences.indent_statements_within_function = true;
		preferences.indent_statements_within_case = true;
		preferences.indent_statements_within_switch = true;
		preferences.insert_space_after_arrow_in_array = true;
		preferences.insert_space_after_arrow_in_field_access = false;
		preferences.insert_space_after_arrow_in_foreach = true;
		preferences.insert_space_after_arrow_in_method_invocation = false;
		preferences.insert_space_after_assignment = true;
		preferences.insert_space_after_binary_operation = true;
		preferences.insert_space_after_closing_brace_in_block = true;
		preferences.insert_space_after_cast_expression = true;
		preferences.insert_space_after_conditional_colon = true;
		preferences.insert_space_after_coloncolon_in_field_access = false;
		preferences.insert_space_after_coloncolon_in_method_invocation = false;
		preferences.insert_space_after_comma_in_echo = true;
		preferences.insert_space_after_comma_in_for = true;
		preferences.insert_space_after_comma_in_global = true;
		preferences.insert_space_after_comma_in_list = true;
		preferences.insert_space_after_comma_in_function_declaration = true;
		preferences.insert_space_after_comma_in_function = true;
		preferences.insert_space_after_comma_in_class_constant = true;
		preferences.insert_space_after_comma_in_class_variable = true;
		preferences.insert_space_after_comma_in_static = true;
		preferences.insert_space_after_comma_in_implements = true;
		preferences.insert_space_after_opening_bracket_in_array = false;
		preferences.insert_space_after_opening_paren_in_array = false;
		preferences.insert_space_before_cast_type = false;
		preferences.insert_space_after_opening_paren_in_catch = false;
		preferences.insert_space_after_open_paren_in_for = false;
		preferences.insert_space_after_open_paren_in_foreach = false;
		preferences.insert_space_after_opening_paren_in_if = false;
		preferences.insert_space_after_opening_paren_in_list = false;
		preferences.insert_space_after_opening_paren_in_function_declaration = false;
		preferences.insert_space_after_opening_paren_in_function = false;
		preferences.insert_space_after_opening_paren_in_switch = false;
		preferences.insert_space_after_opening_paren_in_while = false;
		preferences.insert_space_after_postfix_expression = false;
		preferences.insert_space_after_prefix_expression = true;
		preferences.insert_space_after_conditional_question_mark = true;
		preferences.insert_space_after_semicolon_in_for = true;
		preferences.insert_space_after_unary_expression = true;
		preferences.insert_space_before_arrow_in_array = true;
		preferences.insert_space_before_arrow_in_field_access = false;
		preferences.insert_space_before_arrow_in_foreach = true;
		preferences.insert_space_before_arrow_in_method_invocation = false;
		preferences.insert_space_before_assignment = true;
		preferences.insert_space_before_binary_operation = true;
		preferences.insert_space_before_closing_bracket_in_array = false;
		preferences.insert_space_before_closing_paren_in_array = false;
		preferences.insert_space_after_cast_type = false;
		preferences.insert_space_before_closing_paren_in_catch = false;
		preferences.insert_space_before_close_paren_in_for = false;
		preferences.insert_space_before_close_paren_in_foreach = false;
		preferences.insert_space_before_closing_paren_in_if = false;
		preferences.insert_space_before_closing_paren_in_list = false;
		preferences.insert_space_before_closing_paren_in_function_declaration = false;
		preferences.insert_space_before_closing_paren_in_function = false;
		preferences.insert_space_before_closing_paren_in_switch = false;
		preferences.insert_space_before_closing_paren_in_while = false;
		preferences.insert_space_after_switch_case_value = false;
		preferences.insert_space_before_conditional_colon = true;
		preferences.insert_space_after_switch_default = false;
		preferences.insert_space_before_coloncolon_in_field_access = false;
		preferences.insert_space_before_coloncolon_in_method_invocation = false;
		preferences.insert_space_before_list_comma_in_array = false;
		preferences.insert_space_before_comma_in_echo = false;
		preferences.insert_space_before_comma_in_for = false;
		preferences.insert_space_before_comma_in_global = false;
		preferences.insert_space_before_comma_in_list = false;
		preferences.insert_space_before_comma_in_function_declaration = false;
		preferences.insert_space_before_comma_in_function = false;
		preferences.insert_space_before_comma_in_class_constant = false;
		preferences.insert_space_before_comma_in_class_variable = false;
		preferences.insert_space_before_comma_in_static = false;
		preferences.insert_space_before_comma_in_implements = false;
		preferences.insert_space_before_opening_brace_in_block = true;
		preferences.insert_space_before_opening_brace_in_function = true;
		preferences.insert_space_before_opening_brace_in_switch = true;
		preferences.insert_space_before_opening_brace_in_class = true;
		preferences.insert_space_before_opening_bracket_in_array = false;
		preferences.insert_space_before_opening_paren_in_array = false;
		preferences.insert_space_before_opening_paren_in_catch = true;
		preferences.insert_space_before_open_paren_in_for = true;
		preferences.insert_space_before_open_paren_in_foreach = true;
		preferences.insert_space_before_opening_paren_in_if = true;
		preferences.insert_space_before_opening_paren_in_list = true;
		preferences.insert_space_before_opening_paren_in_function_declaration = false;
		preferences.insert_space_before_opening_paren_in_function = false;
		preferences.insert_space_before_opening_paren_in_switch = true;
		preferences.insert_space_before_opening_paren_in_while = true;
		preferences.insert_space_before_postfix_expression = true;
		preferences.insert_space_before_prefix_expression = false;
		preferences.insert_space_before_conditional_question_mark = true;
		preferences.insert_space_before_semicolon = false;
		preferences.insert_space_before_semicolon_in_for = false;
		preferences.insert_space_before_unary_expression = false;
		preferences.insert_space_between_empty_brackets = false;
		preferences.insert_space_between_empty_paren_in_function_declaration = false;
		preferences.insert_space_between_empty_paren_in_function = false;
		preferences.insert_space_after_opening_paren_in_declare = false;
		preferences.insert_space_before_closing_paren_in_declare = false;
		preferences.insert_space_before_opening_paren_in_declare = false;

		preferences.line_wrap_wrapped_lines_indentation = 1;
		preferences.line_wrap_array_init_indentation = 1;
		preferences.line_wrap_superinterfaces_in_type_declaration_line_wrap_policy = 1;
		preferences.line_wrap_parameters_in_method_declaration_line_wrap_policy = 1;
		preferences.line_wrap_arguments_in_method_invocation_line_wrap_policy = 1;
		preferences.line_wrap_binary_expression_line_wrap_policy = 1;
		preferences.line_wrap_arguments_in_allocation_expression_line_wrap_policy = 1;
		// preferences.line_wrap_expressions_in_array_init_line_wrap_policy = 1;

		preferences.new_line_in_second_invoke = 3;

		preferences.new_line_in_empty_class_body = true;

		preferences.line_wrap_line_split = 9999;

		preferences.blank_lines_after_namespace = 1;

		// blank line
		preferences.blank_line_preserve_empty_lines = 1;
		preferences.blank_line_before_class_declaration = 1;
		preferences.blank_line_before_constant_declaration = 1;
		preferences.blank_line_before_field_declaration = 1;
		preferences.blank_line_before_method_declaration = 1;

		// comments

		preferences.comment_clear_blank_lines_in_block_comment = false;
		preferences.comment_clear_blank_lines_in_javadoc_comment = false;
		preferences.comment_format_block_comment = true;
		preferences.comment_format_javadoc_comment = true;
		preferences.comment_format_line_comment = true;
		preferences.comment_format_line_comment_starting_on_first_column = true;
		preferences.comment_format_header = false;
		preferences.comment_format_html = true;
		preferences.comment_format_source = true;
		preferences.comment_indent_parameter_description = true;
		preferences.comment_indent_root_tags = true;
		preferences.comment_insert_empty_line_before_root_tags = true;
		preferences.comment_insert_new_line_for_parameter = true;
		preferences.comment_new_lines_at_block_boundaries = true;
		preferences.comment_new_lines_at_javadoc_boundaries = true;
		preferences.comment_line_length = -1;
		preferences.comment_preserve_white_space_between_code_and_line_comments = false;
		preferences.never_indent_block_comments_on_first_column = false;
		preferences.never_indent_line_comments_on_first_column = false;
		preferences.join_lines_in_comments = true;
		preferences.use_tags = false;
		preferences.disabling_tag = CodeFormatterPreferences.DEFAULT_DISABLING_TAG;
		preferences.enabling_tag = CodeFormatterPreferences.DEFAULT_ENABLING_TAG;

		// for array initializer
		preferences.line_wrap_expressions_in_array_init_force_split = true;
		preferences.line_wrap_expressions_in_array_init_line_wrap_policy = CodeFormatterVisitor.WRAP_ALL_ELEMENTS;
		preferences.insert_space_after_list_comma_in_array = false;
		preferences.new_line_before_close_array_parenthesis_array = true;
		return preferences;
	}
}
