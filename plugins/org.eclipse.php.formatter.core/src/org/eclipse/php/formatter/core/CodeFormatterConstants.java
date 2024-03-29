/*******************************************************************************
 * Copyright (c) 2013-2019 Zend Techologies Ltd.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Zend Technologies Ltd. - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.formatter.core;

import java.util.Map;

import org.eclipse.php.internal.formatter.core.FormatterCorePlugin;

/**
 * Constants used to set up the options of the code formatter.
 * <p>
 * This class is not intended to be instantiated or subclassed by clients.
 * </p>
 * 
 * @since 3.0
 */
public class CodeFormatterConstants {

	/**
	 * A named preference that controls which profile is used by the code
	 * formatter.
	 * <p>
	 * Value is of type <code>String</code>.
	 * </p>
	 * 
	 * @since 3.7
	 */
	public static final String FORMATTER_PROFILE = "formatterProfile"; //$NON-NLS-1$

	/**
	 * <pre>
	 * FORMATTER / Value to set an option to false.
	 * </pre>
	 * 
	 * @since 3.0
	 */
	public static final String FALSE = "false"; //$NON-NLS-1$

	/**
	 * <pre>
	 * FORMATTER / Option for alignment of arguments in allocation expression
	 *     - option id:         &quot;org.eclipse.php.formatter.core.alignment_for_arguments_in_allocation_expression&quot;
	 *     - possible values:   values returned by &lt;code&gt;createAlignmentValue(boolean, int, int)&lt;/code&gt; call
	 *     - default:           createAlignmentValue(false, WRAP_COMPACT, INDENT_DEFAULT)
	 * </pre>
	 * 
	 * @see #createAlignmentValue(boolean, int, int)
	 * @since 3.0
	 */
	public static final String FORMATTER_ALIGNMENT_FOR_ARGUMENTS_IN_ALLOCATION_EXPRESSION_KEY = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.alignment_for_arguments_in_allocation_expression"; //$NON-NLS-1$
	public static final String FORMATTER_ALIGNMENT_FOR_ARGUMENTS_IN_ALLOCATION_EXPRESSION_LINE_WRAP_POLICY = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.alignment_for_arguments_in_allocation_expression_line_wrap_policy"; //$NON-NLS-1$
	public static final String FORMATTER_ALIGNMENT_FOR_ARGUMENTS_IN_ALLOCATION_EXPRESSION_INDENT_POLICY = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.alignment_for_arguments_in_allocation_expression_indent_policy"; //$NON-NLS-1$
	public static final String FORMATTER_ALIGNMENT_FOR_ARGUMENTS_IN_ALLOCATION_EXPRESSION_FORCE_SPLIT = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.alignment_for_arguments_in_allocation_expression_force_split"; //$NON-NLS-1$

	public static final String FORMATTER_ALIGNMENT_FOR_ARGUMENTS_IN_MATCH_EXPRESSION_KEY = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.alignment_for_arguments_in_match_expression"; //$NON-NLS-1$
	public static final String FORMATTER_ALIGNMENT_FOR_ARGUMENTS_IN_MATCH_EXPRESSION_LINE_WRAP_POLICY = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.alignment_for_arguments_in_match_expression_line_wrap_policy"; //$NON-NLS-1$
	public static final String FORMATTER_ALIGNMENT_FOR_ARGUMENTS_IN_MATCH_EXPRESSION_INDENT_POLICY = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.alignment_for_arguments_in_match_expression_indent_policy"; //$NON-NLS-1$
	public static final String FORMATTER_ALIGNMENT_FOR_ARGUMENTS_IN_MATCH_EXPRESSION_FORCE_SPLIT = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.alignment_for_arguments_in_match_expression_force_split"; //$NON-NLS-1$

	public static final String FORMATTER_ALIGNMENT_FOR_ARGUMENTS_IN_ATTRIBUTE_GROUP_KEY = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.alignment_for_arguments_in_attribute_group"; //$NON-NLS-1$
	public static final String FORMATTER_ALIGNMENT_FOR_ARGUMENTS_IN_ATTRIBUTE_GROUP_LINE_WRAP_POLICY = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.alignment_for_arguments_in_attribute_group_line_wrap_policy"; //$NON-NLS-1$
	public static final String FORMATTER_ALIGNMENT_FOR_ARGUMENTS_IN_ATTRIBUTE_GROUP_INDENT_POLICY = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.alignment_for_arguments_in_attribute_group_indent_policy"; //$NON-NLS-1$
	public static final String FORMATTER_ALIGNMENT_FOR_ARGUMENTS_IN_ATTRIBUTE_GROUP_FORCE_SPLIT = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.alignment_for_arguments_in_attribute_group_force_split"; //$NON-NLS-1$

	/**
	 * <pre>
	 * FORMATTER / Option for alignment of arguments in method invocation
	 *     - option id:         &quot;org.eclipse.php.formatter.core.alignment_for_arguments_in_method_invocation&quot;
	 *     - possible values:   values returned by &lt;code&gt;createAlignmentValue(boolean, int, int)&lt;/code&gt; call
	 *     - default:           createAlignmentValue(false, WRAP_COMPACT, INDENT_DEFAULT)
	 * </pre>
	 * 
	 * @see #createAlignmentValue(boolean, int, int)
	 * @since 3.0
	 */
	public static final String FORMATTER_ALIGNMENT_FOR_ARGUMENTS_IN_METHOD_INVOCATION_KEY = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.alignment_for_arguments_in_method_invocation"; //$NON-NLS-1$
	public static final String FORMATTER_ALIGNMENT_FOR_ARGUMENTS_IN_METHOD_INVOCATION_LINE_WRAP_POLICY = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.alignment_for_arguments_in_method_invocation_line_wrap_policy"; //$NON-NLS-1$
	public static final String FORMATTER_ALIGNMENT_FOR_ARGUMENTS_IN_METHOD_INVOCATION_INDENT_POLICY = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.alignment_for_arguments_in_method_invocation_indent_policy"; //$NON-NLS-1$
	public static final String FORMATTER_ALIGNMENT_FOR_ARGUMENTS_IN_METHOD_INVOCATION_FORCE_SPLIT = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.alignment_for_arguments_in_method_invocation_force_split"; //$NON-NLS-1$
	public static final String FORMATTER_ALIGNMENT_FOR_ASSIGNMENT_LINE_WRAP_POLICY = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.alignment_for_assignment_line_wrap_policy"; //$NON-NLS-1$
	public static final String FORMATTER_ALIGNMENT_FOR_ASSIGNMENT_INDENT_POLICY = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.alignment_for_assignment_indent_policy"; //$NON-NLS-1$
	public static final String FORMATTER_ALIGNMENT_FOR_ASSIGNMENT_FORCE_SPLIT = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.alignment_for_assignment_force_split"; //$NON-NLS-1$
	/**
	 * <pre>
	 * FORMATTER / Option for alignment of binary expression
	 *     - option id:         &quot;org.eclipse.php.formatter.core.alignment_for_binary_expression&quot;
	 *     - possible values:   values returned by &lt;code&gt;createAlignmentValue(boolean, int, int)&lt;/code&gt; call
	 *     - default:           createAlignmentValue(false, WRAP_COMPACT, INDENT_DEFAULT)
	 * </pre>
	 * 
	 * @see #createAlignmentValue(boolean, int, int)
	 * @since 3.0
	 */
	public static final String FORMATTER_ALIGNMENT_FOR_BINARY_EXPRESSION_KEY = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.alignment_for_binary_expression"; //$NON-NLS-1$
	public static final String FORMATTER_ALIGNMENT_FOR_BINARY_EXPRESSION_LINE_WRAP_POLICY = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.alignment_for_binary_expression_line_wrap_policy"; //$NON-NLS-1$
	public static final String FORMATTER_ALIGNMENT_FOR_BINARY_EXPRESSION_INDENT_POLICY = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.alignment_for_binary_expression_indent_policy"; //$NON-NLS-1$
	public static final String FORMATTER_ALIGNMENT_FOR_BINARY_EXPRESSION_FORCE_SPLIT = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.alignment_for_binary_expression_force_split"; //$NON-NLS-1$

	public static final String FORMATTER_ALIGNMENT_FOR_COMPACT_IF_LINE_WRAP_POLICY = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.alignment_for_compact_if_line_wrap_policy"; //$NON-NLS-1$
	public static final String FORMATTER_ALIGNMENT_FOR_COMPACT_IF_INDENT_POLICY = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.alignment_for_compact_if_indent_policy"; //$NON-NLS-1$
	public static final String FORMATTER_ALIGNMENT_FOR_COMPACT_IF_FORCE_SPLIT = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.alignment_for_compact_if_force_split"; //$NON-NLS-1$
	public static final String FORMATTER_ALIGNMENT_FOR_CONDITIONAL_EXPRESSION_LINE_WRAP_POLICY = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.alignment_for_conditional_expression_line_wrap_policy"; //$NON-NLS-1$
	public static final String FORMATTER_ALIGNMENT_FOR_CONDITIONAL_EXPRESSION_INDENT_POLICY = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.alignment_for_conditional_expression_indent_policy"; //$NON-NLS-1$
	public static final String FORMATTER_ALIGNMENT_FOR_CONDITIONAL_EXPRESSION_FORCE_SPLIT = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.alignment_for_conditional_expression_force_split"; //$NON-NLS-1$

	/**
	 * <pre>
	 * FORMATTER / Option for alignment of expressions in array initializer
	 *     - option id:         &quot;org.eclipse.php.formatter.core.alignment_for_expressions_in_array_initializer&quot;
	 *     - possible values:   values returned by &lt;code&gt;createAlignmentValue(boolean, int, int)&lt;/code&gt; call
	 *     - default:           createAlignmentValue(false, WRAP_COMPACT, INDENT_DEFAULT)
	 * </pre>
	 * 
	 * @see #createAlignmentValue(boolean, int, int)
	 * @since 3.0
	 */
	public static final String FORMATTER_ALIGNMENT_FOR_EXPRESSIONS_IN_ARRAY_INITIALIZER_KEY = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.alignment_for_expressions_in_array_initializer"; //$NON-NLS-1$
	public static final String FORMATTER_ALIGNMENT_FOR_EXPRESSIONS_IN_ARRAY_INITIALIZER_LINE_WRAP_POLICY = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.alignment_for_expressions_in_array_initializer_line_wrap_policy"; //$NON-NLS-1$
	public static final String FORMATTER_ALIGNMENT_FOR_EXPRESSIONS_IN_ARRAY_INITIALIZER_INDENT_POLICY = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.alignment_for_expressions_in_array_initializer_indent_policy"; //$NON-NLS-1$
	public static final String FORMATTER_ALIGNMENT_FOR_EXPRESSIONS_IN_ARRAY_INITIALIZER_FORCE_SPLIT = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.alignment_for_expressions_in_array_initializer_force_split"; //$NON-NLS-1$

	/**
	 * <pre>
	 * FORMATTER / Option for alignment of parameters in method declaration
	 *     - option id:         &quot;org.eclipse.php.formatter.core.alignment_for_parameters_in_method_declaration&quot;
	 *     - possible values:   values returned by &lt;code&gt;createAlignmentValue(boolean, int, int)&lt;/code&gt; call
	 *     - default:           createAlignmentValue(false, WRAP_COMPACT, INDENT_DEFAULT)
	 * </pre>
	 * 
	 * @see #createAlignmentValue(boolean, int, int)
	 * @since 3.0
	 */
	public static final String FORMATTER_ALIGNMENT_FOR_PARAMETERS_IN_METHOD_DECLARATION_KEY = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.alignment_for_parameters_in_method_declaration"; //$NON-NLS-1$
	public static final String FORMATTER_ALIGNMENT_FOR_PARAMETERS_IN_METHOD_DECLARATION_LINE_WRAP_POLICY = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.alignment_for_parameters_in_method_declaration_line_wrap_policy"; //$NON-NLS-1$
	public static final String FORMATTER_ALIGNMENT_FOR_PARAMETERS_IN_METHOD_DECLARATION_INDENT_POLICY = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.alignment_for_parameters_in_method_declaration_indent_policy"; //$NON-NLS-1$
	public static final String FORMATTER_ALIGNMENT_FOR_PARAMETERS_IN_METHOD_DECLARATION_FORCE_SPLIT = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.alignment_for_parameters_in_method_declaration_force_split"; //$NON-NLS-1$
	public static final String FORMATTER_ALIGNMENT_FOR_SUPERCLASS_IN_TYPE_DECLARATION_LINE_WRAP_POLICY = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.alignment_for_superclass_in_type_declaration_line_wrap_policy"; //$NON-NLS-1$
	public static final String FORMATTER_ALIGNMENT_FOR_SUPERCLASS_IN_TYPE_DECLARATION_INDENT_POLICY = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.alignment_for_superclass_in_type_declaration_indent_policy"; //$NON-NLS-1$
	public static final String FORMATTER_ALIGNMENT_FOR_SUPERCLASS_IN_TYPE_DECLARATION_FORCE_SPLIT = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.alignment_for_superclass_in_type_declaration_force_split"; //$NON-NLS-1$

	/**
	 * <pre>
	 * FORMATTER / Option for alignment of superinterfaces in type declaration
	 *     - option id:         &quot;org.eclipse.php.formatter.core.alignment_for_superinterfaces_in_type_declaration&quot;
	 *     - possible values:   values returned by &lt;code&gt;createAlignmentValue(boolean, int, int)&lt;/code&gt; call
	 *     - default:           createAlignmentValue(false, WRAP_COMPACT, INDENT_DEFAULT)
	 * </pre>
	 * 
	 * @see #createAlignmentValue(boolean, int, int)
	 * @since 3.0
	 */
	public static final String FORMATTER_ALIGNMENT_FOR_SUPERINTERFACES_IN_TYPE_DECLARATION_KEY = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.alignment_for_superinterfaces_in_type_declaration"; //$NON-NLS-1$
	public static final String FORMATTER_ALIGNMENT_FOR_SUPERINTERFACES_IN_TYPE_DECLARATION_LINE_WRAP_POLICY = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.alignment_for_superinterfaces_in_type_declaration_line_wrap_policy"; //$NON-NLS-1$
	public static final String FORMATTER_ALIGNMENT_FOR_SUPERINTERFACES_IN_TYPE_DECLARATION_INDENT_POLICY = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.alignment_for_superinterfaces_in_type_declaration_indent_policy"; //$NON-NLS-1$
	public static final String FORMATTER_ALIGNMENT_FOR_SUPERINTERFACES_IN_TYPE_DECLARATION_FORCE_SPLIT = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.alignment_for_superinterfaces_in_type_declaration_force_split"; //$NON-NLS-1$

	/**
	 * <pre>
	 * FORMATTER / Option to add blank lines at the beginning of the method body
	 *     - option id:         &quot;org.eclipse.php.formatter.core.number_of_blank_lines_at_beginning_of_method_body&quot;
	 *     - possible values:   &quot;&lt;n&gt;&quot;, where n is zero or a positive integer
	 *     - default:           &quot;0&quot;
	 * </pre>
	 * 
	 * @since 3.0
	 */
	public static final String FORMATTER_BLANK_LINES_AT_BEGINNING_OF_METHOD_BODY = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.number_of_blank_lines_at_beginning_of_method_body"; //$NON-NLS-1$
	public static final String FORMATTER_BLANK_LINES_AT_END_OF_CLASS_BODY = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.number_of_blank_lines_at_end_of_class_body"; //$NON-NLS-1$
	public static final String FORMATTER_BLANK_LINES_AT_END_OF_METHOD_BODY = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.number_of_blank_lines_at_end_of_method_body"; //$NON-NLS-1$

	public static final String FORMATTER_BLANK_LINES_BEFORE_NAMESPACE = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.number_of_blank_lines_before_namespace"; //$NON-NLS-1$
	public static final String FORMATTER_BLANK_LINES_AFTER_NAMESPACE = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.number_of_blank_lines_after_namespace"; //$NON-NLS-1$
	public static final String FORMATTER_BLANK_LINES_BEFORE_USE_STATEMENTS = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.number_of_blank_lines_before_use_statements"; //$NON-NLS-1$
	public static final String FORMATTER_BLANK_LINES_AFTER_USE_STATEMENTS = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.number_of_blank_lines_after_use_statements"; //$NON-NLS-1$
	public static final String FORMATTER_BLANK_LINES_BETWEEN_USE_STATEMENTS = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.number_of_blank_lines_between_use_statements"; //$NON-NLS-1$
	public static final String FORMATTER_BLANK_LINES_BETWEEN_NAMESPACES = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.number_of_blank_lines_between_namespaces"; //$NON-NLS-1$
	/**
	 * <pre>
	 * FORMATTER / Option to add blank lines before a field declaration
	 *     - option id:         &quot;org.eclipse.php.formatter.core.blank_lines_before_field&quot;
	 *     - possible values:   &quot;&lt;n&gt;&quot;, where n is zero or a positive integer
	 *     - default:           &quot;0&quot;
	 * </pre>
	 * 
	 * @since 3.0
	 */
	public static final String FORMATTER_BLANK_LINES_BEFORE_FIELD = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.blank_lines_before_field"; //$NON-NLS-1$

	/**
	 * <pre>
	 * FORMATTER / Option to add blank lines before a member type declaration
	 *     - option id:         &quot;org.eclipse.php.formatter.core.blank_lines_before_member_type&quot;
	 *     - possible values:   &quot;&lt;n&gt;&quot;, where n is zero or a positive integer
	 *     - default:           &quot;0&quot;
	 * </pre>
	 * 
	 * @since 3.0
	 */
	public static final String FORMATTER_BLANK_LINES_BEFORE_MEMBER_TYPE = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.blank_lines_before_member_type"; //$NON-NLS-1$
	/**
	 * <pre>
	 * FORMATTER / Option to add blank lines before a method declaration
	 *     - option id:         &quot;org.eclipse.php.formatter.core.blank_lines_before_method&quot;
	 *     - possible values:   &quot;&lt;n&gt;&quot;, where n is zero or a positive integer
	 *     - default:           &quot;0&quot;
	 * </pre>
	 * 
	 * @since 3.0
	 */
	public static final String FORMATTER_BLANK_LINES_BEFORE_METHOD = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.blank_lines_before_method"; //$NON-NLS-1$

	/**
	 * <pre>
	 * FORMATTER / Option to add blank lines between type declarations
	 *     - option id:         &quot;org.eclipse.php.formatter.core.blank_lines_between_type_declarations&quot;
	 *     - possible values:   &quot;&lt;n&gt;&quot;, where n is zero or a positive integer
	 *     - default:           &quot;0&quot;
	 * </pre>
	 * 
	 * @since 3.0
	 */
	public static final String FORMATTER_BLANK_LINES_BETWEEN_TYPE_DECLARATIONS = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.blank_lines_between_type_declarations"; //$NON-NLS-1$

	/**
	 * <pre>
	 * FORMATTER / Option to position the braces of a block
	 *     - option id:         &quot;org.eclipse.php.formatter.core.brace_position_for_block&quot;
	 *     - possible values:   { END_OF_LINE, NEXT_LINE, NEXT_LINE_SHIFTED, NEXT_LINE_ON_WRAP }
	 *     - default:           END_OF_LINE
	 * </pre>
	 * 
	 * @see #END_OF_LINE
	 * @see #NEXT_LINE
	 * @see #NEXT_LINE_SHIFTED
	 * @see #NEXT_LINE_ON_WRAP
	 * @since 3.0
	 */
	public static final String FORMATTER_BRACE_POSITION_FOR_BLOCK = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.brace_position_for_block"; //$NON-NLS-1$

	/**
	 * <pre>
	 * FORMATTER / Option to position the braces of a method declaration
	 *     - option id:         &quot;org.eclipse.php.formatter.core.brace_position_for_method_declaration&quot;
	 *     - possible values:   { END_OF_LINE, NEXT_LINE, NEXT_LINE_SHIFTED, NEXT_LINE_ON_WRAP }
	 *     - default:           END_OF_LINE
	 * </pre>
	 * 
	 * @see #END_OF_LINE
	 * @see #NEXT_LINE
	 * @see #NEXT_LINE_SHIFTED
	 * @see #NEXT_LINE_ON_WRAP
	 * @since 3.0
	 */
	public static final String FORMATTER_BRACE_POSITION_FOR_METHOD_DECLARATION = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.brace_position_for_method_declaration"; //$NON-NLS-1$

	/**
	 * <pre>
	 * FORMATTER / Option to position the braces of a lambda function declaration
	 *     - option id:         &quot;org.eclipse.php.formatter.core.brace_position_for_lambda_function_declaration&quot;
	 *     - possible values:   { END_OF_LINE, NEXT_LINE, NEXT_LINE_SHIFTED, NEXT_LINE_ON_WRAP }
	 *     - default:           END_OF_LINE
	 * </pre>
	 * 
	 * @see #END_OF_LINE
	 * @see #NEXT_LINE
	 * @see #NEXT_LINE_SHIFTED
	 * @see #NEXT_LINE_ON_WRAP
	 * @since 3.3
	 */
	public static final String FORMATTER_BRACE_POSITION_FOR_LAMBDA_FUNCTION_DECLARATION = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.brace_position_for_lambda_function_declaration"; //$NON-NLS-1$
	/**
	 * <pre>
	 * FORMATTER / Option to position the braces of a switch statement
	 *     - option id:         &quot;org.eclipse.php.formatter.core.brace_position_for_switch&quot;
	 *     - possible values:   { END_OF_LINE, NEXT_LINE, NEXT_LINE_SHIFTED, NEXT_LINE_ON_WRAP }
	 *     - default:           END_OF_LINE
	 * </pre>
	 * 
	 * @see #END_OF_LINE
	 * @see #NEXT_LINE
	 * @see #NEXT_LINE_SHIFTED
	 * @see #NEXT_LINE_ON_WRAP
	 * @since 3.0
	 */
	public static final String FORMATTER_BRACE_POSITION_FOR_SWITCH = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.brace_position_for_switch"; //$NON-NLS-1$
	/**
	 * @see #END_OF_LINE
	 * @see #NEXT_LINE
	 * @see #NEXT_LINE_SHIFTED
	 * @see #NEXT_LINE_ON_WRAP
	 * @since 3.0
	 * @since 8.0
	 */
	public static final String FORMATTER_BRACE_POSITION_FOR_MATCH = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.brace_position_for_match"; //$NON-NLS-1$
	/**
	 * <pre>
	 * FORMATTER / Option to position the braces of a type declaration
	 *     - option id:         &quot;org.eclipse.php.formatter.core.brace_position_for_type_declaration&quot;
	 *     - possible values:   { END_OF_LINE, NEXT_LINE, NEXT_LINE_SHIFTED, NEXT_LINE_ON_WRAP }
	 *     - default:           END_OF_LINE
	 * </pre>
	 * 
	 * @see #END_OF_LINE
	 * @see #NEXT_LINE
	 * @see #NEXT_LINE_SHIFTED
	 * @see #NEXT_LINE_ON_WRAP
	 * @since 3.0
	 */
	public static final String FORMATTER_BRACE_POSITION_FOR_TYPE_DECLARATION = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.brace_position_for_type_declaration"; //$NON-NLS-1$

	public static final String FORMATTER_INSERT_NEW_LINE_IN_SECOND_METHOD_INVOKE = "org.eclipse.php.core.formatter.insert_new_line_in_function_invoke"; //$NON-NLS-1$

	/**
	 * <pre>
	 * FORMATTER / Option to indent body declarations compare to its enclosing type header
	 *     - option id:         &quot;org.eclipse.php.formatter.core.indent_body_declarations_compare_to_type_header&quot;
	 *     - possible values:   { TRUE, FALSE }
	 *     - default:           TRUE
	 * </pre>
	 * 
	 * @see #TRUE
	 * @see #FALSE
	 * @since 3.0
	 */
	public static final String FORMATTER_INDENT_BODY_DECLARATIONS_COMPARE_TO_TYPE_HEADER = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.indent_body_declarations_compare_to_type_header"; //$NON-NLS-1$
	/**
	 * <pre>
	 * FORMATTER / Option to indent breaks compare to cases
	 *     - option id:         &quot;org.eclipse.php.formatter.core.indent_breaks_compare_to_cases&quot;
	 *     - possible values:   { TRUE, FALSE }
	 *     - default:           TRUE
	 * </pre>
	 * 
	 * @see #TRUE
	 * @see #FALSE
	 * @since 3.0
	 */
	public static final String FORMATTER_INDENT_BREAKS_COMPARE_TO_CASES = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.indent_breaks_compare_to_cases"; //$NON-NLS-1$
	/**
	 * <pre>
	 * FORMATTER / Option to indent empty lines
	 *     - option id:         &quot;org.eclipse.php.formatter.core.indent_empty_lines&quot;
	 *     - possible values:   { TRUE, FALSE }
	 *     - default:           FALSE
	 * </pre>
	 * 
	 * @see #TRUE
	 * @see #FALSE
	 * @since 3.2
	 */
	public static final String FORMATTER_INDENT_EMPTY_LINES = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.indent_empty_lines"; //$NON-NLS-1$
	/**
	 * <pre>
	 * FORMATTER / Option to indent heredocs and nowdocs
	 *     - option id:         &quot;org.eclipse.php.formatter.core.indent_heredocs&quot;
	 *     - possible values:   { TRUE, FALSE }
	 *     - default:           TRUE
	 * </pre>
	 * 
	 * @see #TRUE
	 * @see #FALSE
	 * @since PHP 7.3
	 */
	public static final String FORMATTER_INDENT_HEREDOCS = FormatterCorePlugin.PLUGIN_ID + ".formatter.indent_heredocs"; //$NON-NLS-1$
	/**
	 * <pre>
	 * FORMATTER / Option to indent statements inside a block
	 *     - option id:         &quot;org.eclipse.php.formatter.core.indent_statements_compare_to_block&quot;
	 *     - possible values:   { TRUE, FALSE }
	 *     - default:           TRUE
	 * </pre>
	 * 
	 * @see #TRUE
	 * @see #FALSE
	 * @since 3.0
	 */
	public static final String FORMATTER_INDENT_STATEMENTS_COMPARE_TO_BLOCK = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.indent_statements_compare_to_block"; //$NON-NLS-1$
	/**
	 * <pre>
	 * FORMATTER / Option to indent statements inside the body of a method or a constructor
	 *     - option id:         &quot;org.eclipse.php.formatter.core.indent_statements_compare_to_body&quot;
	 *     - possible values:   { TRUE, FALSE }
	 *     - default:           TRUE
	 * </pre>
	 * 
	 * @see #TRUE
	 * @see #FALSE
	 * @since 3.0
	 */
	public static final String FORMATTER_INDENT_STATEMENTS_COMPARE_TO_BODY = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.indent_statements_compare_to_body"; //$NON-NLS-1$
	/**
	 * <pre>
	 * FORMATTER / Option to indent switch statements compare to cases
	 *     - option id:         &quot;org.eclipse.php.formatter.core.indent_switchstatements_compare_to_cases&quot;
	 *     - possible values:   { TRUE, FALSE }
	 *     - default:           TRUE
	 * </pre>
	 * 
	 * @see #TRUE
	 * @see #FALSE
	 * @since 3.0
	 */
	public static final String FORMATTER_INDENT_SWITCHSTATEMENTS_COMPARE_TO_CASES = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.indent_switchstatements_compare_to_cases"; //$NON-NLS-1$

	/**
	 * <pre>
	 * FORMATTER / Option to indent switch statements compare to switch
	 *     - option id:         &quot;org.eclipse.php.formatter.core.indent_switchstatements_compare_to_switch&quot;
	 *     - possible values:   { TRUE, FALSE }
	 *     - default:           TRUE
	 * </pre>
	 * 
	 * @see #TRUE
	 * @see #FALSE
	 * @since 3.0
	 */
	public static final String FORMATTER_INDENT_SWITCHSTATEMENTS_COMPARE_TO_SWITCH = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.indent_switchstatements_compare_to_switch"; //$NON-NLS-1$

	public static final String FORMATTER_INDENT_MATCH_ARMS_COMPARE_TO_MATCH = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.indent_match_arms_compare_to_match"; //$NON-NLS-1$

	/**
	 * <pre>
	 * FORMATTER / Option to specify the equivalent number of spaces that represents one indentation
	 *     - option id:         &quot;org.eclipse.php.formatter.core.indentation.size&quot;
	 *     - possible values:   &quot;&lt;n&gt;&quot;, where n is zero or a positive integer
	 *     - default:           &quot;4&quot;
	 * </pre>
	 * <p>
	 * This option is used only if the tab char is set to MIXED.
	 * </p>
	 * 
	 * @see #FORMATTER_TAB_CHAR
	 * @since 3.1
	 */
	public static final String FORMATTER_INDENTATION_SIZE = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.indentation.size"; //$NON-NLS-1$

	/**
	 * <pre>
	 * FORMATTER / Option to insert a new line after the opening brace in an array initializer
	 *     - option id:         &quot;org.eclipse.php.formatter.core.insert_new_line_after_opening_brace_in_array_initializer&quot;
	 *     - possible values:   { INSERT, DO_NOT_INSERT }
	 *     - default:           DO_NOT_INSERT
	 * </pre>
	 * 
	 * @see JavaCore#INSERT
	 * @see JavaCore#DO_NOT_INSERT
	 * @since 3.0
	 */
	public static final String FORMATTER_INSERT_NEW_LINE_AFTER_OPENING_BRACE_IN_ARRAY_INITIALIZER = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_new_line_after_opening_brace_in_array_initializer";//$NON-NLS-1$

	/**
	 * <pre>
	 * FORMATTER / Option to insert a new line before the catch keyword in try statement
	 *     - option id:         &quot;org.eclipse.php.formatter.core.insert_new_line_before_catch_in_try_statement&quot;
	 *     - possible values:   { INSERT, DO_NOT_INSERT }
	 *     - default:           DO_NOT_INSERT
	 * </pre>
	 * 
	 * @see JavaCore#INSERT
	 * @see JavaCore#DO_NOT_INSERT
	 * @since 3.0
	 */
	public static final String FORMATTER_INSERT_NEW_LINE_BEFORE_CATCH_IN_TRY_STATEMENT = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_new_line_before_catch_in_try_statement"; //$NON-NLS-1$
	/**
	 * <pre>
	 * FORMATTER / Option to insert a new line before the closing brace in an array initializer
	 *     - option id:         &quot;org.eclipse.php.formatter.core.insert_new_line_before_closing_brace_in_array_initializer&quot;
	 *     - possible values:   { INSERT, DO_NOT_INSERT }
	 *     - default:           DO_NOT_INSERT
	 * </pre>
	 * 
	 * @see JavaCore#INSERT
	 * @see JavaCore#DO_NOT_INSERT
	 * @since 3.0
	 */
	public static final String FORMATTER_INSERT_NEW_LINE_BEFORE_CLOSING_BRACE_IN_ARRAY_INITIALIZER = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_new_line_before_closing_brace_in_array_initializer";//$NON-NLS-1$
	/**
	 * <pre>
	 * FORMATTER / Option to insert a new line before the else and elseif keywords in if statement
	 *     - option id:         &quot;org.eclipse.php.formatter.core.insert_new_line_before_else_in_if_statement&quot;
	 *     - possible values:   { INSERT, DO_NOT_INSERT }
	 *     - default:           DO_NOT_INSERT
	 * </pre>
	 * 
	 * @see JavaCore#INSERT
	 * @see JavaCore#DO_NOT_INSERT
	 * @since 3.0
	 */
	public static final String FORMATTER_INSERT_NEW_LINE_BEFORE_ELSE_AND_ELSEIF_IN_IF_STATEMENT = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_new_line_before_else_in_if_statement"; //$NON-NLS-1$

	/**
	 * <pre>
	 * FORMATTER / Option to insert a new line before the finally keyword in try statement
	 *     - option id:         &quot;org.eclipse.php.formatter.core.insert_new_line_before_finally_in_try_statement&quot;
	 *     - possible values:   { INSERT, DO_NOT_INSERT }
	 *     - default:           DO_NOT_INSERT
	 * </pre>
	 * 
	 * @see JavaCore#INSERT
	 * @see JavaCore#DO_NOT_INSERT
	 * @since 3.0
	 */
	public static final String FORMATTER_INSERT_NEW_LINE_BEFORE_FINALLY_IN_TRY_STATEMENT = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_new_line_before_finally_in_try_statement"; //$NON-NLS-1$
	/**
	 * <pre>
	 * FORMATTER / Option to insert a new line before while in do statement
	 *     - option id:         &quot;org.eclipse.php.formatter.core.insert_new_line_before_while_in_do_statement&quot;
	 *     - possible values:   { INSERT, DO_NOT_INSERT }
	 *     - default:           DO_NOT_INSERT
	 * </pre>
	 * 
	 * @see JavaCore#INSERT
	 * @see JavaCore#DO_NOT_INSERT
	 * @since 3.0
	 */
	public static final String FORMATTER_INSERT_NEW_LINE_BEFORE_WHILE_IN_DO_STATEMENT = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_new_line_before_while_in_do_statement"; //$NON-NLS-1$

	/**
	 * <pre>
	 * FORMATTER / Option to insert a new line in an empty block
	 *     - option id:         &quot;org.eclipse.php.formatter.core.insert_new_line_in_empty_block&quot;
	 *     - possible values:   { INSERT, DO_NOT_INSERT }
	 *     - default:           INSERT
	 * </pre>
	 * 
	 * @see JavaCore#INSERT
	 * @see JavaCore#DO_NOT_INSERT
	 * @since 3.0
	 */
	public static final String FORMATTER_INSERT_NEW_LINE_IN_EMPTY_BLOCK = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_new_line_in_empty_block"; //$NON-NLS-1$

	/**
	 * <pre>
	 * FORMATTER / Option to insert a new line in an empty method body
	 *     - option id:         &quot;org.eclipse.php.formatter.core.insert_new_line_in_empty_method_body&quot;
	 *     - possible values:   { INSERT, DO_NOT_INSERT }
	 *     - default:           INSERT
	 * </pre>
	 * 
	 * @see JavaCore#INSERT
	 * @see JavaCore#DO_NOT_INSERT
	 * @since 3.0
	 */
	public static final String FORMATTER_INSERT_NEW_LINE_IN_EMPTY_METHOD_BODY = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_new_line_in_empty_method_body"; //$NON-NLS-1$
	/**
	 * <pre>
	 * FORMATTER / Option to insert a new line in an empty type declaration
	 *     - option id:         &quot;org.eclipse.php.formatter.core.insert_new_line_in_empty_type_declaration&quot;
	 *     - possible values:   { INSERT, DO_NOT_INSERT }
	 *     - default:           INSERT
	 * </pre>
	 * 
	 * @see JavaCore#INSERT
	 * @see JavaCore#DO_NOT_INSERT
	 * @since 3.0
	 */
	public static final String FORMATTER_INSERT_NEW_LINE_IN_EMPTY_TYPE_DECLARATION = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_new_line_in_empty_type_declaration"; //$NON-NLS-1$

	/**
	 * <pre>
	 * FORMATTER / Option to insert a space after an assignment operator
	 *     - option id:         &quot;org.eclipse.php.formatter.core.insert_space_after_assignment_operator&quot;
	 *     - possible values:   { INSERT, DO_NOT_INSERT }
	 *     - default:           INSERT
	 * </pre>
	 * 
	 * @see JavaCore#INSERT
	 * @see JavaCore#DO_NOT_INSERT
	 * @since 3.0
	 */
	public static final String FORMATTER_INSERT_SPACE_AFTER_ASSIGNMENT_OPERATOR = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_after_assignment_operator"; //$NON-NLS-1$

	/**
	 * <pre>
	 * FORMATTER / Option to insert a space after a binary operator
	 *     - option id:         &quot;org.eclipse.php.formatter.core.insert_space_after_binary_operator&quot;
	 *     - possible values:   { INSERT, DO_NOT_INSERT }
	 *     - default:           INSERT
	 * </pre>
	 * 
	 * @see JavaCore#INSERT
	 * @see JavaCore#DO_NOT_INSERT
	 * @since 3.0
	 */
	public static final String FORMATTER_INSERT_SPACE_AFTER_BINARY_OPERATOR = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_after_binary_operator"; //$NON-NLS-1$

	/**
	 * <pre>
	 * FORMATTER / Option to insert a space after the closing brace of a block
	 *     - option id:         &quot;org.eclipse.php.formatter.core.insert_space_after_closing_brace_in_block&quot;
	 *     - possible values:   { INSERT, DO_NOT_INSERT }
	 *     - default:           INSERT
	 * </pre>
	 * 
	 * @see JavaCore#INSERT
	 * @see JavaCore#DO_NOT_INSERT
	 * @since 3.0
	 */
	public static final String FORMATTER_INSERT_SPACE_AFTER_CLOSING_BRACE_IN_BLOCK = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_after_closing_brace_in_block"; //$NON-NLS-1$
	/**
	 * <pre>
	 * FORMATTER / Option to insert a space after the closing parenthesis of a cast expression
	 *     - option id:         &quot;org.eclipse.php.formatter.core.insert_space_after_closing_paren_in_cast&quot;
	 *     - possible values:   { INSERT, DO_NOT_INSERT }
	 *     - default:           INSERT
	 * </pre>
	 * 
	 * @see JavaCore#INSERT
	 * @see JavaCore#DO_NOT_INSERT
	 * @since 3.0
	 */
	public static final String FORMATTER_INSERT_SPACE_AFTER_CLOSING_PAREN_IN_CAST = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_after_closing_paren_in_cast"; //$NON-NLS-1$

	/**
	 * <pre>
	 * FORMATTER / Option to insert a space after the colon in a conditional expression
	 *     - option id:         &quot;org.eclipse.php.formatter.core.insert_space_after_colon_in_conditional&quot;
	 *     - possible values:   { INSERT, DO_NOT_INSERT }
	 *     - default:           INSERT
	 * </pre>
	 * 
	 * @see JavaCore#INSERT
	 * @see JavaCore#DO_NOT_INSERT
	 * @since 3.0
	 */
	public static final String FORMATTER_INSERT_SPACE_AFTER_COLON_IN_CONDITIONAL = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_after_colon_in_conditional"; //$NON-NLS-1$

	/**
	 * <pre>
	 * FORMATTER / Option to insert a space after the comma in the initializations of a for statement
	 *     - option id:         &quot;org.eclipse.php.formatter.core.insert_space_after_comma_in_for_inits&quot;
	 *     - possible values:   { INSERT, DO_NOT_INSERT }
	 *     - default:           INSERT
	 * </pre>
	 * 
	 * @see JavaCore#INSERT
	 * @see JavaCore#DO_NOT_INSERT
	 * @since 3.0
	 */
	public static final String FORMATTER_INSERT_SPACE_AFTER_COMMA_IN_FOR = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_after_comma_in_for_inits"; //$NON-NLS-1$
	/**
	 * <pre>
	 * FORMATTER / Option to insert a space after the comma in the parameters of a method declaration
	 *     - option id:         &quot;org.eclipse.php.formatter.core.insert_space_after_comma_in_method_declaration_parameters&quot;
	 *     - possible values:   { INSERT, DO_NOT_INSERT }
	 *     - default:           INSERT
	 * </pre>
	 * 
	 * @see JavaCore#INSERT
	 * @see JavaCore#DO_NOT_INSERT
	 * @since 3.0
	 */
	public static final String FORMATTER_INSERT_SPACE_AFTER_COMMA_IN_METHOD_DECLARATION_PARAMETERS = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_after_comma_in_method_declaration_parameters"; //$NON-NLS-1$

	public static final String FORMATTER_INSERT_SPACE_AFTER_COMMA_IN_ATTRIBUTE_GROUP = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_after_comma_in_attribute_group"; //$NON-NLS-1$

	public static final String FORMATTER_INSERT_SPACE_BEFORE_COMMA_IN_ATTRIBUTE_GROUP = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_before_comma_in_attribute_group"; //$NON-NLS-1$

	public static final String FORMATTER_INSERT_SPACE_AFTER_ATTRIBUTE_GROUP_START = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_after_attribute_group_start"; //$NON-NLS-1$

	public static final String FORMATTER_INSERT_SPACE_BEFORE_ATTRIBUTE_GROUP_END = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_before_attribute_group_end"; //$NON-NLS-1$

	/**
	 * <pre>
	 * FORMATTER / Option to insert a space after the comma in the arguments of a method invocation
	 *     - option id:         &quot;org.eclipse.php.formatter.core.insert_space_after_comma_in_method_invocation_arguments&quot;
	 *     - possible values:   { INSERT, DO_NOT_INSERT }
	 *     - default:           INSERT
	 * </pre>
	 * 
	 * @see JavaCore#INSERT
	 * @see JavaCore#DO_NOT_INSERT
	 * @since 3.0
	 */
	public static final String FORMATTER_INSERT_SPACE_AFTER_COMMA_IN_METHOD_INVOCATION_ARGUMENTS = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_after_comma_in_method_invocation_arguments"; //$NON-NLS-1$
	public static final String FORMATTER_INSERT_SPACE_BEFORE_ARROW_IN_METHOD_INVOCATION = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_before_arrow_in_method_invocation"; //$NON-NLS-1$
	public static final String FORMATTER_INSERT_SPACE_AFTER_ARROW_IN_METHOD_INVOCATION = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_after_arrow_in_method_invocation"; //$NON-NLS-1$
	public static final String FORMATTER_INSERT_SPACE_BEFORE_COLONCOLON_IN_METHOD_INVOCATION = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_before_coloncolon_in_method_invocation"; //$NON-NLS-1$
	public static final String FORMATTER_INSERT_SPACE_AFTER_COLONCOLON_IN_METHOD_INVOCATION = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_after_coloncolon_in_method_invocation"; //$NON-NLS-1$
	public static final String FORMATTER_INSERT_SPACE_BEFORE_ARROW_IN_FIELD_ACCESS = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_before_arrow_in_field_access"; //$NON-NLS-1$
	public static final String FORMATTER_INSERT_SPACE_AFTER_ARROW_IN_FIELD_ACCESS = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_after_arrow_in_field_access"; //$NON-NLS-1$
	public static final String FORMATTER_INSERT_SPACE_BEFORE_COLONCOLON_IN_FIELD_ACCESS = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_before_coloncolon_in_field_access"; //$NON-NLS-1$
	public static final String FORMATTER_INSERT_SPACE_AFTER_COLONCOLON_IN_FIELD_ACCESS = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_after_coloncolon_in_field_access"; //$NON-NLS-1$

	public static final String FORMATTER_INSERT_SPACE_BEFORE_COMMA_IN_STATIC = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_before_comma_in_static"; //$NON-NLS-1$
	public static final String FORMATTER_INSERT_SPACE_AFTER_COMMA_IN_STATIC = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_after_comma_in_static"; //$NON-NLS-1$
	public static final String FORMATTER_INSERT_SPACE_BEFORE_COMMA_IN_GLOBAL = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_before_comma_in_global"; //$NON-NLS-1$
	public static final String FORMATTER_INSERT_SPACE_AFTER_COMMA_IN_GLOBAL = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_after_comma_in_global"; //$NON-NLS-1$
	public static final String FORMATTER_INSERT_SPACE_BEFORE_COMMA_IN_ECHO = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_before_comma_in_echo"; //$NON-NLS-1$
	public static final String FORMATTER_INSERT_SPACE_AFTER_COMMA_IN_ECHO = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_after_comma_in_echo"; //$NON-NLS-1$

	/**
	 * <pre>
	 * FORMATTER / Option to insert a space after the comma in multiple field declaration
	 *     - option id:         &quot;org.eclipse.php.formatter.core.insert_space_after_comma_in_multiple_field_declarations&quot;
	 *     - possible values:   { INSERT, DO_NOT_INSERT }
	 *     - default:           INSERT
	 * </pre>
	 * 
	 * @see JavaCore#INSERT
	 * @see JavaCore#DO_NOT_INSERT
	 * @since 3.0
	 */
	public static final String FORMATTER_INSERT_SPACE_AFTER_COMMA_IN_MULTIPLE_FIELD_DECLARATIONS = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_after_comma_in_multiple_field_declarations"; //$NON-NLS-1$

	public static final String FORMATTER_INSERT_SPACE_BEFORE_COMMA_IN_MULTIPLE_CONSTANT_DECLARATIONS = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_before_comma_in_multiple_constant_declarations"; //$NON-NLS-1$
	public static final String FORMATTER_INSERT_SPACE_AFTER_COMMA_IN_MULTIPLE_CONSTANT_DECLARATIONS = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_after_comma_in_multiple_constant_declarations"; //$NON-NLS-1$

	/**
	 * <pre>
	 * FORMATTER / Option to insert a space after the comma in superinterfaces names of a type header
	 *     - option id:         &quot;org.eclipse.php.formatter.core.insert_space_after_comma_in_superinterfaces&quot;
	 *     - possible values:   { INSERT, DO_NOT_INSERT }
	 *     - default:           INSERT
	 * </pre>
	 * 
	 * @see JavaCore#INSERT
	 * @see JavaCore#DO_NOT_INSERT
	 * @since 3.0
	 */
	public static final String FORMATTER_INSERT_SPACE_AFTER_COMMA_IN_SUPERINTERFACES = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_after_comma_in_superinterfaces"; //$NON-NLS-1$

	/**
	 * <pre>
	 * FORMATTER / Option to insert a space after the opening bracket inside an array reference
	 *     - option id:         &quot;org.eclipse.php.formatter.core.insert_space_after_opening_bracket_in_array_reference&quot;
	 *     - possible values:   { INSERT, DO_NOT_INSERT }
	 *     - default:           DO_NOT_INSERT
	 * </pre>
	 * 
	 * @see JavaCore#INSERT
	 * @see JavaCore#DO_NOT_INSERT
	 * @since 3.0
	 */
	public static final String FORMATTER_INSERT_SPACE_AFTER_OPENING_BRACKET_IN_ARRAY_REFERENCE = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_after_opening_bracket_in_array_reference";//$NON-NLS-1$

	/**
	 * <pre>
	 * FORMATTER / Option to insert a space after the opening parenthesis in a cast expression
	 *     - option id:         &quot;org.eclipse.php.formatter.core.insert_space_after_opening_paren_in_cast&quot;
	 *     - possible values:   { INSERT, DO_NOT_INSERT }
	 *     - default:           DO_NOT_INSERT
	 * </pre>
	 * 
	 * @see JavaCore#INSERT
	 * @see JavaCore#DO_NOT_INSERT
	 * @since 3.0
	 */
	public static final String FORMATTER_INSERT_SPACE_AFTER_OPENING_PAREN_IN_CAST = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_after_opening_paren_in_cast"; //$NON-NLS-1$
	/**
	 * <pre>
	 * FORMATTER / Option to insert a space after the opening parenthesis in a catch
	 *     - option id:         &quot;org.eclipse.php.formatter.core.insert_space_after_opening_paren_in_catch&quot;
	 *     - possible values:   { INSERT, DO_NOT_INSERT }
	 *     - default:           DO_NOT_INSERT
	 * </pre>
	 * 
	 * @see JavaCore#INSERT
	 * @see JavaCore#DO_NOT_INSERT
	 * @since 3.0
	 */
	public static final String FORMATTER_INSERT_SPACE_AFTER_OPENING_PAREN_IN_CATCH = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_after_opening_paren_in_catch"; //$NON-NLS-1$

	/**
	 * <pre>
	 * FORMATTER / Option to insert a space after the opening parenthesis in a for statement
	 *     - option id:         &quot;org.eclipse.php.formatter.core.insert_space_after_opening_paren_in_for&quot;
	 *     - possible values:   { INSERT, DO_NOT_INSERT }
	 *     - default:           DO_NOT_INSERT
	 * </pre>
	 * 
	 * @see JavaCore#INSERT
	 * @see JavaCore#DO_NOT_INSERT
	 * @since 3.0
	 */
	public static final String FORMATTER_INSERT_SPACE_AFTER_OPENING_PAREN_IN_FOR = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_after_opening_paren_in_for"; //$NON-NLS-1$
	public static final String FORMATTER_INSERT_SPACE_AFTER_OPENING_PAREN_IN_FOREACH = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_after_opening_paren_in_foreach"; //$NON-NLS-1$
	/**
	 * <pre>
	 * FORMATTER / Option to insert a space after the opening parenthesis in an if statement
	 *     - option id:         &quot;org.eclipse.php.formatter.core.insert_space_after_opening_paren_in_if&quot;
	 *     - possible values:   { INSERT, DO_NOT_INSERT }
	 *     - default:           DO_NOT_INSERT
	 * </pre>
	 * 
	 * @see JavaCore#INSERT
	 * @see JavaCore#DO_NOT_INSERT
	 * @since 3.0
	 */
	public static final String FORMATTER_INSERT_SPACE_AFTER_OPENING_PAREN_IN_IF = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_after_opening_paren_in_if"; //$NON-NLS-1$
	/**
	 * <pre>
	 * FORMATTER / Option to insert a space after the opening parenthesis in a method declaration
	 *     - option id:         &quot;org.eclipse.php.formatter.core.insert_space_after_opening_paren_in_method_declaration&quot;
	 *     - possible values:   { INSERT, DO_NOT_INSERT }
	 *     - default:           DO_NOT_INSERT
	 * </pre>
	 * 
	 * @see JavaCore#INSERT
	 * @see JavaCore#DO_NOT_INSERT
	 * @since 3.0
	 */
	public static final String FORMATTER_INSERT_SPACE_AFTER_OPENING_PAREN_IN_METHOD_DECLARATION = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_after_opening_paren_in_method_declaration"; //$NON-NLS-1$
	/**
	 * <pre>
	 * FORMATTER / Option to insert a space after the opening parenthesis in a method invocation
	 *     - option id:         &quot;org.eclipse.php.formatter.core.insert_space_after_opening_paren_in_method_invocation&quot;
	 *     - possible values:   { INSERT, DO_NOT_INSERT }
	 *     - default:           DO_NOT_INSERT
	 * </pre>
	 * 
	 * @see JavaCore#INSERT
	 * @see JavaCore#DO_NOT_INSERT
	 * @since 3.0
	 */
	public static final String FORMATTER_INSERT_SPACE_AFTER_OPENING_PAREN_IN_METHOD_INVOCATION = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_after_opening_paren_in_method_invocation"; //$NON-NLS-1$
	/**
	 * <pre>
	 * FORMATTER / Option to insert a space after the opening parenthesis in a parenthesized expression
	 *     - option id:         &quot;org.eclipse.php.formatter.core.insert_space_after_opening_paren_in_parenthesized_expression&quot;
	 *     - possible values:   { INSERT, DO_NOT_INSERT }
	 *     - default:           DO_NOT_INSERT
	 * </pre>
	 * 
	 * @see JavaCore#INSERT
	 * @see JavaCore#DO_NOT_INSERT
	 * @since 3.0
	 */
	public static final String FORMATTER_INSERT_SPACE_AFTER_OPENING_PAREN_IN_PARENTHESIZED_EXPRESSION = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_after_opening_paren_in_parenthesized_expression"; //$NON-NLS-1$
	/**
	 * <pre>
	 * FORMATTER / Option to insert a space after the reference symbol in a referenced expression
	 *     - option id:         &quot;org.eclipse.php.formatter.core.insert_space_after_reference_symbol_in_referenced_expression&quot;
	 *     - possible values:   { INSERT, DO_NOT_INSERT }
	 *     - default:           DO_NOT_INSERT
	 * </pre>
	 * 
	 * @see JavaCore#INSERT
	 * @see JavaCore#DO_NOT_INSERT
	 * @since 3.0
	 */
	public static final String FORMATTER_INSERT_SPACE_AFTER_REFERENCE_SYMBOL_IN_REFERENCED_EXPRESSION = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_after_reference_symbol_in_referenced_expression"; //$NON-NLS-1$
	/**
	 * <pre>
	 * FORMATTER / Option to insert a space after the opening parenthesis in a switch statement
	 *     - option id:         &quot;org.eclipse.php.formatter.core.insert_space_after_opening_paren_in_switch&quot;
	 *     - possible values:   { INSERT, DO_NOT_INSERT }
	 *     - default:           DO_NOT_INSERT
	 * </pre>
	 * 
	 * @see JavaCore#INSERT
	 * @see JavaCore#DO_NOT_INSERT
	 * @since 3.0
	 */
	public static final String FORMATTER_INSERT_SPACE_AFTER_OPENING_PAREN_IN_SWITCH = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_after_opening_paren_in_switch"; //$NON-NLS-1$

	public static final String FORMATTER_INSERT_SPACE_AFTER_OPENING_PAREN_IN_MATCH = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_after_opening_paren_in_match"; //$NON-NLS-1$

	/**
	 * <pre>
	 * FORMATTER / Option to insert a space after the opening parenthesis in a while statement
	 *     - option id:         &quot;org.eclipse.php.formatter.core.insert_space_after_opening_paren_in_while&quot;
	 *     - possible values:   { INSERT, DO_NOT_INSERT }
	 *     - default:           DO_NOT_INSERT
	 * </pre>
	 * 
	 * @see JavaCore#INSERT
	 * @see JavaCore#DO_NOT_INSERT
	 * @since 3.0
	 */
	public static final String FORMATTER_INSERT_SPACE_AFTER_OPENING_PAREN_IN_WHILE = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_after_opening_paren_in_while"; //$NON-NLS-1$
	/**
	 * <pre>
	 * FORMATTER / Option to insert a space after a postfix operator
	 *     - option id:         &quot;org.eclipse.php.formatter.core.insert_space_after_postfix_operator&quot;
	 *     - possible values:   { INSERT, DO_NOT_INSERT }
	 *     - default:           DO_NOT_INSERT
	 * </pre>
	 * 
	 * @see JavaCore#INSERT
	 * @see JavaCore#DO_NOT_INSERT
	 * @since 3.0
	 */
	public static final String FORMATTER_INSERT_SPACE_AFTER_POSTFIX_OPERATOR = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_after_postfix_operator"; //$NON-NLS-1$
	/**
	 * <pre>
	 * FORMATTER / Option to insert a space after a concatenation operator
	 *     - option id:         &quot;org.eclipse.php.formatter.core.insert_space_after_concatenation_operator&quot;
	 *     - possible values:   { INSERT, DO_NOT_INSERT }
	 *     - default:           DO_NOT_INSERT
	 * </pre>
	 * 
	 * @see JavaCore#INSERT
	 * @see JavaCore#DO_NOT_INSERT
	 * @since 7.1
	 */
	public static final String FORMATTER_INSERT_SPACE_AFTER_CONCATENATION_OPERATOR = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_after_concatenation_operator"; //$NON-NLS-1$
	/**
	 * <pre>
	 * FORMATTER / Option to insert a space after a prefix operator
	 *     - option id:         &quot;org.eclipse.php.formatter.core.insert_space_after_prefix_operator&quot;
	 *     - possible values:   { INSERT, DO_NOT_INSERT }
	 *     - default:           DO_NOT_INSERT
	 * </pre>
	 * 
	 * @see JavaCore#INSERT
	 * @see JavaCore#DO_NOT_INSERT
	 * @since 3.0
	 */
	public static final String FORMATTER_INSERT_SPACE_AFTER_PREFIX_OPERATOR = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_after_prefix_operator"; //$NON-NLS-1$
	/**
	 * <pre>
	 * FORMATTER / Option to insert a space after question mark in a conditional expression
	 *     - option id:         &quot;org.eclipse.php.formatter.core.insert_space_after_question_in_conditional&quot;
	 *     - possible values:   { INSERT, DO_NOT_INSERT }
	 *     - default:           DO_NOT_INSERT
	 * </pre>
	 * 
	 * @see JavaCore#INSERT
	 * @see JavaCore#DO_NOT_INSERT
	 * @since 3.0
	 */
	public static final String FORMATTER_INSERT_SPACE_AFTER_QUESTION_IN_CONDITIONAL = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_after_question_in_conditional"; //$NON-NLS-1$

	/**
	 * <pre>
	 * FORMATTER / Option to insert a space after semicolon in a for statement
	 *     - option id:         &quot;org.eclipse.php.formatter.core.insert_space_after_semicolon_in_for&quot;
	 *     - possible values:   { INSERT, DO_NOT_INSERT }
	 *     - default:           INSERT
	 * </pre>
	 * 
	 * @see JavaCore#INSERT
	 * @see JavaCore#DO_NOT_INSERT
	 * @since 3.0
	 */
	public static final String FORMATTER_INSERT_SPACE_AFTER_SEMICOLON_IN_FOR = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_after_semicolon_in_for"; //$NON-NLS-1$
	/**
	 * <pre>
	 * FORMATTER / Option to insert a space after an unary operator
	 *     - option id:         &quot;org.eclipse.php.formatter.core.insert_space_after_unary_operator&quot;
	 *     - possible values:   { INSERT, DO_NOT_INSERT }
	 *     - default:           DO_NOT_INSERT
	 * </pre>
	 * 
	 * @see JavaCore#INSERT
	 * @see JavaCore#DO_NOT_INSERT
	 * @since 3.0
	 */
	public static final String FORMATTER_INSERT_SPACE_AFTER_UNARY_OPERATOR = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_after_unary_operator"; //$NON-NLS-1$

	/**
	 * <pre>
	 * FORMATTER / Option to insert a space before an assignment operator
	 *     - option id:         &quot;org.eclipse.php.formatter.core.insert_space_before_assignment_operator&quot;
	 *     - possible values:   { INSERT, DO_NOT_INSERT }
	 *     - default:           INSERT
	 * </pre>
	 * 
	 * @see JavaCore#INSERT
	 * @see JavaCore#DO_NOT_INSERT
	 * @since 3.0
	 */
	public static final String FORMATTER_INSERT_SPACE_BEFORE_ASSIGNMENT_OPERATOR = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_before_assignment_operator"; //$NON-NLS-1$

	/**
	 * <pre>
	 * FORMATTER / Option to insert a space before an binary operator
	 *     - option id:         &quot;org.eclipse.php.formatter.core.insert_space_before_binary_operator&quot;
	 *     - possible values:   { INSERT, DO_NOT_INSERT }
	 *     - default:           DO_NOT_INSERT
	 * </pre>
	 * 
	 * @see JavaCore#INSERT
	 * @see JavaCore#DO_NOT_INSERT
	 * @since 3.0
	 */
	public static final String FORMATTER_INSERT_SPACE_BEFORE_BINARY_OPERATOR = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_before_binary_operator"; //$NON-NLS-1$

	/**
	 * <pre>
	 * FORMATTER / Option to insert a space before the closing bracket in an array reference
	 *     - option id:         &quot;org.eclipse.php.formatter.core.insert_space_before_closing_bracket_in_array_reference&quot;
	 *     - possible values:   { INSERT, DO_NOT_INSERT }
	 *     - default:           DO_NOT_INSERT
	 * </pre>
	 * 
	 * @see JavaCore#INSERT
	 * @see JavaCore#DO_NOT_INSERT
	 * @since 3.0
	 */
	public static final String FORMATTER_INSERT_SPACE_BEFORE_CLOSING_BRACKET_IN_ARRAY_REFERENCE = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_before_closing_bracket_in_array_reference";//$NON-NLS-1$

	/**
	 * <pre>
	 * FORMATTER / Option to insert a space before the closing parenthesis in a cast expression
	 *     - option id:         &quot;org.eclipse.php.formatter.core.insert_space_before_closing_paren_in_cast&quot;
	 *     - possible values:   { INSERT, DO_NOT_INSERT }
	 *     - default:           DO_NOT_INSERT
	 * </pre>
	 * 
	 * @see JavaCore#INSERT
	 * @see JavaCore#DO_NOT_INSERT
	 * @since 3.0
	 */
	public static final String FORMATTER_INSERT_SPACE_BEFORE_CLOSING_PAREN_IN_CAST = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_before_closing_paren_in_cast"; //$NON-NLS-1$
	/**
	 * <pre>
	 * FORMATTER / Option to insert a space before the closing parenthesis in a catch
	 *     - option id:         &quot;org.eclipse.php.formatter.core.insert_space_before_closing_paren_in_catch&quot;
	 *     - possible values:   { INSERT, DO_NOT_INSERT }
	 *     - default:           DO_NOT_INSERT
	 * </pre>
	 * 
	 * @see JavaCore#INSERT
	 * @see JavaCore#DO_NOT_INSERT
	 * @since 3.0
	 */
	public static final String FORMATTER_INSERT_SPACE_BEFORE_CLOSING_PAREN_IN_CATCH = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_before_closing_paren_in_catch"; //$NON-NLS-1$

	/**
	 * <pre>
	 * FORMATTER / Option to insert a space before the closing parenthesis in a for statement
	 *     - option id:         &quot;org.eclipse.php.formatter.core.insert_space_before_closing_paren_in_for&quot;
	 *     - possible values:   { INSERT, DO_NOT_INSERT }
	 *     - default:           DO_NOT_INSERT
	 * </pre>
	 * 
	 * @see JavaCore#INSERT
	 * @see JavaCore#DO_NOT_INSERT
	 * @since 3.0
	 */
	public static final String FORMATTER_INSERT_SPACE_BEFORE_CLOSING_PAREN_IN_FOR = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_before_closing_paren_in_for"; //$NON-NLS-1$
	public static final String FORMATTER_INSERT_SPACE_BEFORE_CLOSING_PAREN_IN_FOREACH = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_before_closing_paren_in_foreach"; //$NON-NLS-1$
	/**
	 * <pre>
	 * FORMATTER / Option to insert a space before the closing parenthesis in an if statement
	 *     - option id:         &quot;org.eclipse.php.formatter.core.insert_space_before_closing_paren_in_if&quot;
	 *     - possible values:   { INSERT, DO_NOT_INSERT }
	 *     - default:           DO_NOT_INSERT
	 * </pre>
	 * 
	 * @see JavaCore#INSERT
	 * @see JavaCore#DO_NOT_INSERT
	 * @since 3.0
	 */
	public static final String FORMATTER_INSERT_SPACE_BEFORE_CLOSING_PAREN_IN_IF = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_before_closing_paren_in_if"; //$NON-NLS-1$
	/**
	 * <pre>
	 * FORMATTER / Option to insert a space before the closing parenthesis in a method declaration
	 *     - option id:         &quot;org.eclipse.php.formatter.core.insert_space_before_closing_paren_in_method_declaration&quot;
	 *     - possible values:   { INSERT, DO_NOT_INSERT }
	 *     - default:           DO_NOT_INSERT
	 * </pre>
	 * 
	 * @see JavaCore#INSERT
	 * @see JavaCore#DO_NOT_INSERT
	 * @since 3.0
	 */
	public static final String FORMATTER_INSERT_SPACE_BEFORE_CLOSING_PAREN_IN_METHOD_DECLARATION = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_before_closing_paren_in_method_declaration"; //$NON-NLS-1$
	/**
	 * <pre>
	 * FORMATTER / Option to insert a space before the closing parenthesis in a method invocation
	 *     - option id:         &quot;org.eclipse.php.formatter.core.insert_space_before_closing_paren_in_method_invocation&quot;
	 *     - possible values:   { INSERT, DO_NOT_INSERT }
	 *     - default:           DO_NOT_INSERT
	 * </pre>
	 * 
	 * @see JavaCore#INSERT
	 * @see JavaCore#DO_NOT_INSERT
	 * @since 3.0
	 */
	public static final String FORMATTER_INSERT_SPACE_BEFORE_CLOSING_PAREN_IN_METHOD_INVOCATION = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_before_closing_paren_in_method_invocation"; //$NON-NLS-1$
	/**
	 * <pre>
	 * FORMATTER / Option to insert a space before the closing parenthesis in a parenthesized expression
	 *     - option id:         &quot;org.eclipse.php.formatter.core.insert_space_before_closing_paren_in_parenthesized_expression&quot;
	 *     - possible values:   { INSERT, DO_NOT_INSERT }
	 *     - default:           DO_NOT_INSERT
	 * </pre>
	 * 
	 * @see JavaCore#INSERT
	 * @see JavaCore#DO_NOT_INSERT
	 * @since 3.0
	 */
	public static final String FORMATTER_INSERT_SPACE_BEFORE_CLOSING_PAREN_IN_PARENTHESIZED_EXPRESSION = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_before_closing_paren_in_parenthesized_expression"; //$NON-NLS-1$
	/**
	 * <pre>
	 * FORMATTER / Option to insert a space before the closing parenthesis in a switch statement
	 *     - option id:         &quot;org.eclipse.php.formatter.core.insert_space_before_closing_paren_in_switch&quot;
	 *     - possible values:   { INSERT, DO_NOT_INSERT }
	 *     - default:           DO_NOT_INSERT
	 * </pre>
	 * 
	 * @see JavaCore#INSERT
	 * @see JavaCore#DO_NOT_INSERT
	 * @since 3.0
	 */
	public static final String FORMATTER_INSERT_SPACE_BEFORE_CLOSING_PAREN_IN_SWITCH = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_before_closing_paren_in_switch"; //$NON-NLS-1$

	public static final String FORMATTER_INSERT_SPACE_BEFORE_CLOSING_PAREN_IN_MATCH = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_before_closing_paren_in_match"; //$NON-NLS-1$

	/**
	 * <pre>
	 * FORMATTER / Option to insert a space before the closing parenthesis in a while statement
	 *     - option id:         &quot;org.eclipse.php.formatter.core.insert_space_before_closing_paren_in_while&quot;
	 *     - possible values:   { INSERT, DO_NOT_INSERT }
	 *     - default:           DO_NOT_INSERT
	 * </pre>
	 * 
	 * @see JavaCore#INSERT
	 * @see JavaCore#DO_NOT_INSERT
	 * @since 3.0
	 */
	public static final String FORMATTER_INSERT_SPACE_BEFORE_CLOSING_PAREN_IN_WHILE = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_before_closing_paren_in_while"; //$NON-NLS-1$

	/**
	 * <pre>
	 * FORMATTER / Option to insert a space before colon in a case statement
	 *     - option id:         &quot;org.eclipse.php.formatter.core.insert_space_before_colon_in_case&quot;
	 *     - possible values:   { INSERT, DO_NOT_INSERT }
	 *     - default:           INSERT
	 * </pre>
	 * 
	 * @see JavaCore#INSERT
	 * @see JavaCore#DO_NOT_INSERT
	 * @since 3.0
	 */
	public static final String FORMATTER_INSERT_SPACE_BEFORE_COLON_IN_CASE = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_before_colon_in_case"; //$NON-NLS-1$
	/**
	 * <pre>
	 * FORMATTER / Option to insert a space before colon in a conditional expression
	 *     - option id:         &quot;org.eclipse.php.formatter.core.insert_space_before_colon_in_conditional&quot;
	 *     - possible values:   { INSERT, DO_NOT_INSERT }
	 *     - default:           INSERT
	 * </pre>
	 * 
	 * @see JavaCore#INSERT
	 * @see JavaCore#DO_NOT_INSERT
	 * @since 3.0
	 */
	public static final String FORMATTER_INSERT_SPACE_BEFORE_COLON_IN_CONDITIONAL = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_before_colon_in_conditional"; //$NON-NLS-1$
	/**
	 * <pre>
	 * FORMATTER / Option to insert a space before colon in a default statement
	 *     - option id:         &quot;org.eclipse.php.formatter.core.insert_space_before_colon_in_default&quot;
	 *     - possible values:   { INSERT, DO_NOT_INSERT }
	 *     - default:           INSERT
	 * </pre>
	 * 
	 * @see JavaCore#INSERT
	 * @see JavaCore#DO_NOT_INSERT
	 * @since 3.0
	 */
	public static final String FORMATTER_INSERT_SPACE_BEFORE_COLON_IN_DEFAULT = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_before_colon_in_default"; //$NON-NLS-1$

	/**
	 * <pre>
	 * FORMATTER / Option to insert a space before comma in the initializations of a for statement
	 *     - option id:         &quot;org.eclipse.php.formatter.core.insert_space_before_comma_in_for_inits&quot;
	 *     - possible values:   { INSERT, DO_NOT_INSERT }
	 *     - default:           DO_NOT_INSERT
	 * </pre>
	 * 
	 * @see JavaCore#INSERT
	 * @see JavaCore#DO_NOT_INSERT
	 * @since 3.0
	 */
	public static final String FORMATTER_INSERT_SPACE_BEFORE_COMMA_IN_FOR = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_before_comma_in_for_inits"; //$NON-NLS-1$
	public static final String FORMATTER_INSERT_SPACE_BEFORE_ARROW_IN_FOREACH = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_before_arrow_in_foreach"; //$NON-NLS-1$
	public static final String FORMATTER_INSERT_SPACE_AFTER_ARROW_IN_FOREACH = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_after_arrow_in_foreach"; //$NON-NLS-1$
	public static final String FORMATTER_INSERT_SPACE_BEFORE_ARROW_IN_YIELD = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_before_arrow_in_yield"; //$NON-NLS-1$
	public static final String FORMATTER_INSERT_SPACE_AFTER_ARROW_IN_YIELD = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_after_arrow_in_yield"; //$NON-NLS-1$

	/**
	 * spaces around MATCH ARM expression
	 * 
	 * @since 8.0
	 */
	public static final String FORMATTER_INSERT_SPACE_BEFORE_ARROW_IN_MATCH_ARM = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_before_arrow_in_match_arm"; //$NON-NLS-1$
	public static final String FORMATTER_INSERT_SPACE_AFTER_ARROW_IN_MATCH_ARM = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_after_arrow_in_match_arm"; //$NON-NLS-1$

	public static final String FORMATTER_INSERT_SPACE_BEFORE_COMMA_IN_MATCH = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_before_comma_in_match_expressions"; //$NON-NLS-1$
	public static final String FORMATTER_INSERT_SPACE_AFTER_COMMA_IN_MATCH = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_after_comma_in_match_expressions"; //$NON-NLS-1$

	public static final String FORMATTER_INSERT_SPACE_BEFORE_COMMA_IN_MATCH_ARM_CONDITIONS = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_before_comma_in_match_arm_conditions"; //$NON-NLS-1$
	public static final String FORMATTER_INSERT_SPACE_AFTER_COMMA_IN_MATCH_ARM_CONDITIONS = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_after_comma_in_match_arm_conditions"; //$NON-NLS-1$

	public static final String FORMATTER_INSERT_SPACE_BEFORE_COLON_IN_ENUM_TYPE = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_before_colon_in_enum_type"; //$NON-NLS-1$
	public static final String FORMATTER_INSERT_SPACE_AFTER_COLON_IN_ENUM_TYPE = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_after_colon_in_enum_type"; //$NON-NLS-1$

	public static final String FORMATTER_BLANK_LINES_BEFORE_ENUM_CASE = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.blank_lines_before_enum_case"; //$NON-NLS-1$
	public static final String FORMATTER_INSERT_SPACE_BEFORE_EQUAL_IN_ENUM_CASE = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_before_equal_in_enum_case"; //$NON-NLS-1$
	public static final String FORMATTER_INSERT_SPACE_AFTER_EQUAL_IN_ENUM_CASE = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_after_equal_in_enum_case"; //$NON-NLS-1$

	public static final String FORMATTER_INSERT_SPACE_BEFORE_COLON_IN_NAMED_ARGUMENT = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_before_colon_in_named_argument"; //$NON-NLS-1$
	public static final String FORMATTER_INSERT_SPACE_AFTER_COLON_IN_NAMED_ARGUMENT = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_after_colon_in_named_argument"; //$NON-NLS-1$

	/**
	 * <pre>
	 * FORMATTER / Option to insert a space before comma in the parameters of a method declaration
	 *     - option id:         &quot;org.eclipse.php.formatter.core.insert_space_before_comma_in_method_declaration_parameters&quot;
	 *     - possible values:   { INSERT, DO_NOT_INSERT }
	 *     - default:           DO_NOT_INSERT
	 * </pre>
	 * 
	 * @see JavaCore#INSERT
	 * @see JavaCore#DO_NOT_INSERT
	 * @since 3.0
	 */
	public static final String FORMATTER_INSERT_SPACE_BEFORE_COMMA_IN_METHOD_DECLARATION_PARAMETERS = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_before_comma_in_method_declaration_parameters"; //$NON-NLS-1$

	/**
	 * <pre>
	 * FORMATTER / Option to insert a space before comma in the arguments of a method invocation
	 *     - option id:         &quot;org.eclipse.php.formatter.core.insert_space_before_comma_in_method_invocation_arguments&quot;
	 *     - possible values:   { INSERT, DO_NOT_INSERT }
	 *     - default:           DO_NOT_INSERT
	 * </pre>
	 * 
	 * @see JavaCore#INSERT
	 * @see JavaCore#DO_NOT_INSERT
	 * @since 3.0
	 */
	public static final String FORMATTER_INSERT_SPACE_BEFORE_COMMA_IN_METHOD_INVOCATION_ARGUMENTS = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_before_comma_in_method_invocation_arguments"; //$NON-NLS-1$
	/**
	 * <pre>
	 * FORMATTER / Option to insert a space before comma in a multiple field declaration
	 *     - option id:         &quot;org.eclipse.php.formatter.core.insert_space_before_comma_in_multiple_field_declarations&quot;
	 *     - possible values:   { INSERT, DO_NOT_INSERT }
	 *     - default:           DO_NOT_INSERT
	 * </pre>
	 * 
	 * @see JavaCore#INSERT
	 * @see JavaCore#DO_NOT_INSERT
	 * @since 3.0
	 */
	public static final String FORMATTER_INSERT_SPACE_BEFORE_COMMA_IN_MULTIPLE_FIELD_DECLARATIONS = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_before_comma_in_multiple_field_declarations"; //$NON-NLS-1$

	/**
	 * <pre>
	 * FORMATTER / Option to insert a space before comma in the superinterfaces names in a type header
	 *     - option id:         &quot;org.eclipse.php.formatter.core.insert_space_before_comma_in_superinterfaces&quot;
	 *     - possible values:   { INSERT, DO_NOT_INSERT }
	 *     - default:           DO_NOT_INSERT
	 * </pre>
	 * 
	 * @see JavaCore#INSERT
	 * @see JavaCore#DO_NOT_INSERT
	 * @since 3.0
	 */
	public static final String FORMATTER_INSERT_SPACE_BEFORE_COMMA_IN_SUPERINTERFACES = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_before_comma_in_superinterfaces"; //$NON-NLS-1$

	/**
	 * <pre>
	 * FORMATTER / Option to insert a space before the opening brace in a block
	 *     - option id:         &quot;org.eclipse.php.formatter.core.insert_space_before_opening_brace_in_block&quot;
	 *     - possible values:   { INSERT, DO_NOT_INSERT }
	 *     - default:           INSERT
	 * </pre>
	 * 
	 * @see JavaCore#INSERT
	 * @see JavaCore#DO_NOT_INSERT
	 * @since 3.0
	 */
	public static final String FORMATTER_INSERT_SPACE_BEFORE_OPENING_BRACE_IN_BLOCK = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_before_opening_brace_in_block"; //$NON-NLS-1$

	/**
	 * <pre>
	 * FORMATTER / Option to insert a space before the opening brace in a method declaration
	 *     - option id:         &quot;org.eclipse.php.formatter.core.insert_space_before_opening_brace_in_method_declaration&quot;
	 *     - possible values:   { INSERT, DO_NOT_INSERT }
	 *     - default:           INSERT
	 * </pre>
	 * 
	 * @see JavaCore#INSERT
	 * @see JavaCore#DO_NOT_INSERT
	 * @since 3.0
	 */
	public static final String FORMATTER_INSERT_SPACE_BEFORE_OPENING_BRACE_IN_METHOD_DECLARATION = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_before_opening_brace_in_method_declaration"; //$NON-NLS-1$
	/**
	 * <pre>
	 * FORMATTER / Option to insert a space before the opening brace in a switch statement
	 *     - option id:         &quot;org.eclipse.php.formatter.core.insert_space_before_opening_brace_in_switch&quot;
	 *     - possible values:   { INSERT, DO_NOT_INSERT }
	 *     - default:           INSERT
	 * </pre>
	 * 
	 * @see JavaCore#INSERT
	 * @see JavaCore#DO_NOT_INSERT
	 * @since 3.0
	 */
	public static final String FORMATTER_INSERT_SPACE_BEFORE_OPENING_BRACE_IN_SWITCH = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_before_opening_brace_in_switch"; //$NON-NLS-1$

	/**
	 * @since 8.0
	 */
	public static final String FORMATTER_INSERT_SPACE_BEFORE_OPENING_BRACE_IN_MATCH = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_before_opening_brace_in_match"; //$NON-NLS-1$
	/**
	 * <pre>
	 * FORMATTER / Option to insert a space before the opening brace in a type declaration
	 *     - option id:         &quot;org.eclipse.php.formatter.core.insert_space_before_opening_brace_in_type_declaration&quot;
	 *     - possible values:   { INSERT, DO_NOT_INSERT }
	 *     - default:           INSERT
	 * </pre>
	 * 
	 * @see JavaCore#INSERT
	 * @see JavaCore#DO_NOT_INSERT
	 * @since 3.0
	 */
	public static final String FORMATTER_INSERT_SPACE_BEFORE_OPENING_BRACE_IN_TYPE_DECLARATION = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_before_opening_brace_in_type_declaration"; //$NON-NLS-1$

	/**
	 * <pre>
	 * FORMATTER / Option to insert a space before the opening bracket in an array reference
	 *     - option id:         &quot;org.eclipse.php.formatter.core.insert_space_before_opening_bracket_in_array_reference&quot;
	 *     - possible values:   { INSERT, DO_NOT_INSERT }
	 *     - default:           DO_NOT_INSERT
	 * </pre>
	 * 
	 * @see JavaCore#INSERT
	 * @see JavaCore#DO_NOT_INSERT
	 * @since 3.0
	 */
	public static final String FORMATTER_INSERT_SPACE_BEFORE_OPENING_BRACKET_IN_ARRAY_REFERENCE = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_before_opening_bracket_in_array_reference";//$NON-NLS-1$

	public static final String FORMATTER_INSERT_SPACE_BEFORE_OPENING_PAREN_IN_ARRAY_CREATION = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_before_opening_paren_in_array_creation";//$NON-NLS-1$
	public static final String FORMATTER_INSERT_SPACE_AFTER_OPENING_PAREN_IN_ARRAY_CREATION = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_after_opening_paren_in_array_creation";//$NON-NLS-1$
	public static final String FORMATTER_INSERT_SPACE_BEFORE_CLOSING_PAREN_IN_ARRAY_CREATION = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_before_closing_paren_in_array_creation";//$NON-NLS-1$
	public static final String FORMATTER_INSERT_SPACE_BEFORE_COMMA_IN_ARRAY_CREATION = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_before_comma_in_array_creation";//$NON-NLS-1$
	public static final String FORMATTER_INSERT_SPACE_AFTER_COMMA_IN_ARRAY_CREATION = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_after_comma_in_array_creation";//$NON-NLS-1$
	public static final String FORMATTER_INSERT_SPACE_BEFORE_ARROW_IN_ARRAY_CREATION = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_before_arrow_in_array_creation";//$NON-NLS-1$
	public static final String FORMATTER_INSERT_SPACE_AFTER_ARROW_IN_ARRAY_CREATION = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_after_arrow_in_array_creation";//$NON-NLS-1$
	public static final String FORMATTER_INSERT_SPACE_BEFORE_OPENING_PAREN_IN_LIST = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_before_opening_paren_in_list";//$NON-NLS-1$
	public static final String FORMATTER_INSERT_SPACE_AFTER_OPENING_PAREN_IN_LIST = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_after_opening_paren_in_list";//$NON-NLS-1$
	public static final String FORMATTER_INSERT_SPACE_BEFORE_CLOSING_PAREN_IN_LIST = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_before_closing_paren_in_list";//$NON-NLS-1$
	public static final String FORMATTER_INSERT_SPACE_BEFORE_COMMA_IN_LIST = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_before_comma_in_list";//$NON-NLS-1$
	public static final String FORMATTER_INSERT_SPACE_AFTER_COMMA_IN_LIST = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_after_comma_in_list";//$NON-NLS-1$

	/**
	 * <pre>
	 * FORMATTER / Option to insert a space before the opening parenthesis in a catch
	 *     - option id:         &quot;org.eclipse.php.formatter.core.insert_space_before_opening_paren_in_catch&quot;
	 *     - possible values:   { INSERT, DO_NOT_INSERT }
	 *     - default:           INSERT
	 * </pre>
	 * 
	 * @see JavaCore#INSERT
	 * @see JavaCore#DO_NOT_INSERT
	 * @since 3.0
	 */
	public static final String FORMATTER_INSERT_SPACE_BEFORE_OPENING_PAREN_IN_CATCH = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_before_opening_paren_in_catch"; //$NON-NLS-1$

	/**
	 * <pre>
	 * FORMATTER / Option to insert a space before the opening parenthesis in a for statement
	 *     - option id:         &quot;org.eclipse.php.formatter.core.insert_space_before_opening_paren_in_for&quot;
	 *     - possible values:   { INSERT, DO_NOT_INSERT }
	 *     - default:           INSERT
	 * </pre>
	 * 
	 * @see JavaCore#INSERT
	 * @see JavaCore#DO_NOT_INSERT
	 * @since 3.0
	 */
	public static final String FORMATTER_INSERT_SPACE_BEFORE_OPENING_PAREN_IN_FOR = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_before_opening_paren_in_for"; //$NON-NLS-1$
	public static final String FORMATTER_INSERT_SPACE_BEFORE_OPENING_PAREN_IN_FOREACH = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_before_opening_paren_in_foreach"; //$NON-NLS-1$
	/**
	 * <pre>
	 * FORMATTER / Option to insert a space before the opening parenthesis in an if statement
	 *     - option id:         &quot;org.eclipse.php.formatter.core.insert_space_before_opening_paren_in_if&quot;
	 *     - possible values:   { INSERT, DO_NOT_INSERT }
	 *     - default:           INSERT
	 * </pre>
	 * 
	 * @see JavaCore#INSERT
	 * @see JavaCore#DO_NOT_INSERT
	 * @since 3.0
	 */
	public static final String FORMATTER_INSERT_SPACE_BEFORE_OPENING_PAREN_IN_IF = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_before_opening_paren_in_if"; //$NON-NLS-1$
	/**
	 * <pre>
	 * FORMATTER / Option to insert a space before the opening parenthesis in a method declaration
	 *     - option id:         &quot;org.eclipse.php.formatter.core.insert_space_before_opening_paren_in_method_declaration&quot;
	 *     - possible values:   { INSERT, DO_NOT_INSERT }
	 *     - default:           DO_NOT_INSERT
	 * </pre>
	 * 
	 * @see JavaCore#INSERT
	 * @see JavaCore#DO_NOT_INSERT
	 * @since 3.0
	 */
	public static final String FORMATTER_INSERT_SPACE_BEFORE_OPENING_PAREN_IN_METHOD_DECLARATION = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_before_opening_paren_in_method_declaration"; //$NON-NLS-1$
	/**
	 * <pre>
	 * FORMATTER / Option to insert a space before the opening parenthesis in a method invocation
	 *     - option id:         &quot;org.eclipse.php.formatter.core.insert_space_before_opening_paren_in_method_invocation&quot;
	 *     - possible values:   { INSERT, DO_NOT_INSERT }
	 *     - default:           DO_NOT_INSERT
	 * </pre>
	 * 
	 * @see JavaCore#INSERT
	 * @see JavaCore#DO_NOT_INSERT
	 * @since 3.0
	 */
	public static final String FORMATTER_INSERT_SPACE_BEFORE_OPENING_PAREN_IN_METHOD_INVOCATION = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_before_opening_paren_in_method_invocation"; //$NON-NLS-1$
	/**
	 * <pre>
	 * FORMATTER / Option to insert a space before the opening parenthesis in a switch statement
	 *     - option id:         &quot;org.eclipse.php.formatter.core.insert_space_before_opening_paren_in_switch&quot;
	 *     - possible values:   { INSERT, DO_NOT_INSERT }
	 *     - default:           INSERT
	 * </pre>
	 * 
	 * @see JavaCore#INSERT
	 * @see JavaCore#DO_NOT_INSERT
	 * @since 3.0
	 */
	public static final String FORMATTER_INSERT_SPACE_BEFORE_OPENING_PAREN_IN_SWITCH = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_before_opening_paren_in_switch"; //$NON-NLS-1$

	public static final String FORMATTER_INSERT_SPACE_BEFORE_OPENING_PAREN_IN_MATCH = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_before_opening_paren_in_match"; //$NON-NLS-1$

	/**
	 * <pre>
	 * FORMATTER / Option to insert a space before the opening parenthesis in a while statement
	 *     - option id:         &quot;org.eclipse.php.formatter.core.insert_space_before_opening_paren_in_while&quot;
	 *     - possible values:   { INSERT, DO_NOT_INSERT }
	 *     - default:           INSERT
	 * </pre>
	 * 
	 * @see JavaCore#INSERT
	 * @see JavaCore#DO_NOT_INSERT
	 * @since 3.0
	 */
	public static final String FORMATTER_INSERT_SPACE_BEFORE_OPENING_PAREN_IN_WHILE = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_before_opening_paren_in_while"; //$NON-NLS-1$
	/**
	 * <pre>
	 * FORMATTER / Option to insert a space before a postfix operator
	 *     - option id:         &quot;org.eclipse.php.formatter.core.insert_space_before_postfix_operator&quot;
	 *     - possible values:   { INSERT, DO_NOT_INSERT }
	 *     - default:           DO_NOT_INSERT
	 * </pre>
	 * 
	 * @see JavaCore#INSERT
	 * @see JavaCore#DO_NOT_INSERT
	 * @since 3.0
	 */
	public static final String FORMATTER_INSERT_SPACE_BEFORE_POSTFIX_OPERATOR = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_before_postfix_operator"; //$NON-NLS-1$
	/**
	 * <pre>
	 * FORMATTER / Option to insert a space before a concatenation operator
	 *     - option id:         &quot;org.eclipse.php.formatter.core.insert_space_before_concatenation_operator&quot;
	 *     - possible values:   { INSERT, DO_NOT_INSERT }
	 *     - default:           DO_NOT_INSERT
	 * </pre>
	 * 
	 * @see JavaCore#INSERT
	 * @see JavaCore#DO_NOT_INSERT
	 * @since 7.1
	 */
	public static final String FORMATTER_INSERT_SPACE_BEFORE_CONCATENATION_OPERATOR = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_before_concatenation_operator"; //$NON-NLS-1$
	/**
	 * <pre>
	 * FORMATTER / Option to insert a space before a prefix operator
	 *     - option id:         &quot;org.eclipse.php.formatter.core.insert_space_before_prefix_operator&quot;
	 *     - possible values:   { INSERT, DO_NOT_INSERT }
	 *     - default:           DO_NOT_INSERT
	 * </pre>
	 * 
	 * @see JavaCore#INSERT
	 * @see JavaCore#DO_NOT_INSERT
	 * @since 3.0
	 */
	public static final String FORMATTER_INSERT_SPACE_BEFORE_PREFIX_OPERATOR = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_before_prefix_operator"; //$NON-NLS-1$
	/**
	 * <pre>
	 * FORMATTER / Option to insert a space before question mark in a conditional expression
	 *     - option id:         &quot;org.eclipse.php.formatter.core.insert_space_before_question_in_conditional&quot;
	 *     - possible values:   { INSERT, DO_NOT_INSERT }
	 *     - default:           INSERT
	 * </pre>
	 * 
	 * @see JavaCore#INSERT
	 * @see JavaCore#DO_NOT_INSERT
	 * @since 3.0
	 */
	public static final String FORMATTER_INSERT_SPACE_BEFORE_QUESTION_IN_CONDITIONAL = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_before_question_in_conditional"; //$NON-NLS-1$
	/**
	 * <pre>
	 * FORMATTER / Option to insert a space before semicolon
	 *     - option id:         &quot;org.eclipse.php.formatter.core.insert_space_before_semicolon&quot;
	 *     - possible values:   { INSERT, DO_NOT_INSERT }
	 *     - default:           DO_NOT_INSERT
	 * </pre>
	 * 
	 * @see JavaCore#INSERT
	 * @see JavaCore#DO_NOT_INSERT
	 * @since 3.0
	 */
	public static final String FORMATTER_INSERT_SPACE_BEFORE_SEMICOLON = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_before_semicolon"; //$NON-NLS-1$
	/**
	 * <pre>
	 * FORMATTER / Option to insert a space before semicolon in for statement
	 *     - option id:         &quot;org.eclipse.php.formatter.core.insert_space_before_semicolon_in_for&quot;
	 *     - possible values:   { INSERT, DO_NOT_INSERT }
	 *     - default:           DO_NOT_INSERT
	 * </pre>
	 * 
	 * @see JavaCore#INSERT
	 * @see JavaCore#DO_NOT_INSERT
	 * @since 3.0
	 */
	public static final String FORMATTER_INSERT_SPACE_BEFORE_SEMICOLON_IN_FOR = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_before_semicolon_in_for"; //$NON-NLS-1$
	/**
	 * <pre>
	 * FORMATTER / Option to insert a space before unary operator
	 *     - option id:         &quot;org.eclipse.php.formatter.core.insert_space_before_unary_operator&quot;
	 *     - possible values:   { INSERT, DO_NOT_INSERT }
	 *     - default:           DO_NOT_INSERT
	 * </pre>
	 * 
	 * @see JavaCore#INSERT
	 * @see JavaCore#DO_NOT_INSERT
	 * @since 3.0
	 */
	public static final String FORMATTER_INSERT_SPACE_BEFORE_UNARY_OPERATOR = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_before_unary_operator"; //$NON-NLS-1$

	/**
	 * <pre>
	 * FORMATTER / Option to insert a space between brackets in an array type reference
	 *     - option id:         &quot;org.eclipse.php.formatter.core.insert_space_between_brackets_in_array_type_reference&quot;
	 *     - possible values:   { INSERT, DO_NOT_INSERT }
	 *     - default:           DO_NOT_INSERT
	 * </pre>
	 * 
	 * @see JavaCore#INSERT
	 * @see JavaCore#DO_NOT_INSERT
	 * @since 3.0
	 */
	public static final String FORMATTER_INSERT_SPACE_BETWEEN_EMPTY_BRACKETS_IN_ARRAY_TYPE_REFERENCE = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_between_brackets_in_array_type_reference"; //$NON-NLS-1$

	/**
	 * <pre>
	 * FORMATTER / Option to insert a space between empty parenthesis in a method declaration
	 *     - option id:         &quot;org.eclipse.php.formatter.core.insert_space_between_empty_parens_in_method_declaration&quot;
	 *     - possible values:   { INSERT, DO_NOT_INSERT }
	 *     - default:           DO_NOT_INSERT
	 * </pre>
	 * 
	 * @see JavaCore#INSERT
	 * @see JavaCore#DO_NOT_INSERT
	 * @since 3.0
	 */
	public static final String FORMATTER_INSERT_SPACE_BETWEEN_EMPTY_PARENS_IN_METHOD_DECLARATION = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_between_empty_parens_in_method_declaration"; //$NON-NLS-1$

	/**
	 * FORMATTER / Option to keep elseif statement on the same line
	 */
	public static final String FORMATTER_KEEP_ELSEIF_STATEMENT_ON_SAME_LINE = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.keep_elseif_statement_on_same_line"; //$NON-NLS-1$

	/**
	 * <pre>
	 * FORMATTER / Option to insert a space between empty parenthesis in a method invocation
	 *     - option id:         &quot;org.eclipse.php.formatter.core.insert_space_between_empty_parens_in_method_invocation&quot;
	 *     - possible values:   { INSERT, DO_NOT_INSERT }
	 *     - default:           DO_NOT_INSERT
	 * </pre>
	 * 
	 * @see JavaCore#INSERT
	 * @see JavaCore#DO_NOT_INSERT
	 * @since 3.0
	 */
	public static final String FORMATTER_INSERT_SPACE_BETWEEN_EMPTY_PARENS_IN_METHOD_INVOCATION = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.insert_space_between_empty_parens_in_method_invocation"; //$NON-NLS-1$
	/**
	 * <pre>
	 * FORMATTER / Option to keep else statement on the same line
	 *     - option id:         &quot;org.eclipse.php.formatter.core.keep_else_statement_on_same_line&quot;
	 *     - possible values:   { TRUE, FALSE }
	 *     - default:           FALSE
	 * </pre>
	 * 
	 * @see #TRUE
	 * @see #FALSE
	 * @since 3.0
	 */
	public static final String FORMATTER_KEEP_ELSE_STATEMENT_ON_SAME_LINE = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.keep_else_statement_on_same_line"; //$NON-NLS-1$

	/**
	 * <pre>
	 * FORMATTER / Option to keep guardian clause on one line
	 *     - option id:         &quot;org.eclipse.php.formatter.core.format_guardian_clause_on_one_line&quot;
	 *     - possible values:   { TRUE, FALSE }
	 *     - default:           FALSE
	 * </pre>
	 * 
	 * @see #TRUE
	 * @see #FALSE
	 * @since 3.0
	 */
	public static final String FORMATTER_KEEP_GUARDIAN_CLAUSE_ON_ONE_LINE = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.format_guardian_clause_on_one_line"; //$NON-NLS-1$
	/**
	 * <pre>
	 * FORMATTER / Option to keep simple if statement on the one line
	 *     - option id:         &quot;org.eclipse.php.formatter.core.keep_imple_if_on_one_line&quot;
	 *     - possible values:   { TRUE, FALSE }
	 *     - default:           FALSE
	 * </pre>
	 * 
	 * @see #TRUE
	 * @see #FALSE
	 * @since 3.0
	 */
	public static final String FORMATTER_KEEP_SIMPLE_IF_ON_ONE_LINE = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.keep_imple_if_on_one_line"; //$NON-NLS-1$
	/**
	 * <pre>
	 * FORMATTER / Option to keep then statement on the same line
	 *     - option id:         &quot;org.eclipse.php.formatter.core.keep_then_statement_on_same_line&quot;
	 *     - possible values:   { TRUE, FALSE }
	 *     - default:           FALSE
	 * </pre>
	 * 
	 * @see #TRUE
	 * @see #FALSE
	 * @since 3.0
	 */
	public static final String FORMATTER_KEEP_THEN_STATEMENT_ON_SAME_LINE = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.keep_then_statement_on_same_line";//$NON-NLS-1$

	/**
	 * <pre>
	 * FORMATTER / Option to control whether trailing list commas should be kept or not
	 *     - option id:         &quot;org.eclipse.php.formatter.core.keep_trailing_comma_in_list&quot;
	 *     - possible values:   { TRUE, FALSE }
	 *     - default:           FALSE
	 * </pre>
	 * 
	 * @since 6.0
	 */
	public static final String FORMATTER_KEEP_TRAILING_COMMA_IN_LIST = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.keep_trailing_comma_in_list"; //$NON-NLS-1$

	/**
	 * <pre>
	 * FORMATTER / Option to specify the length of the page. Beyond this length, the formatter will try to split the code
	 *     - option id:         &quot;org.eclipse.php.formatter.core.lineSplit&quot;
	 *     - possible values:   &quot;&lt;n&gt;&quot;, where n is zero or a positive integer
	 *     - default:           &quot;80&quot;
	 * </pre>
	 * 
	 * @since 3.0
	 */
	public static final String FORMATTER_LINE_SPLIT = FormatterCorePlugin.PLUGIN_ID + ".formatter.lineSplit"; //$NON-NLS-1$

	/**
	 * <pre>
	 * FORMATTER / Option to specify the number of empty lines to preserve
	 *     - option id:         &quot;org.eclipse.php.formatter.core.number_of_empty_lines_to_preserve&quot;
	 *     - possible values:   &quot;&lt;n&gt;&quot;, where n is zero or a positive integer
	 *     - default:           &quot;0&quot;
	 * </pre>
	 * 
	 * @since 3.0
	 */
	public static final String FORMATTER_NUMBER_OF_EMPTY_LINES_TO_PRESERVE = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.number_of_empty_lines_to_preserve"; //$NON-NLS-1$
	/**
	 * <pre>
	 * FORMATTER / Option to specify whether or not empty statement should be on a new line
	 *     - option id:         &quot;org.eclipse.php.formatter.core.put_empty_statement_on_new_line&quot;
	 *     - possible values:   { TRUE, FALSE }
	 *     - default:           FALSE
	 * </pre>
	 * 
	 * @see #TRUE
	 * @see #FALSE
	 * @since 3.0
	 */
	public static final String FORMATTER_PUT_EMPTY_STATEMENT_ON_NEW_LINE = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.put_empty_statement_on_new_line"; //$NON-NLS-1$

	/**
	 * <pre>
	 * FORMATTER / Option to specify the equivalent number of spaces that represents one tabulation
	 *     - option id:         &quot;org.eclipse.php.formatter.core.tabulation.size&quot;
	 *     - possible values:   &quot;&lt;n&gt;&quot;, where n is zero or a positive integer
	 *     - default:           &quot;4&quot;
	 * </pre>
	 * 
	 * @since 3.0
	 */
	public static final String FORMATTER_TAB_SIZE = FormatterCorePlugin.PLUGIN_ID + ".formatter.tabulation.size"; //$NON-NLS-1$

	/**
	 * <pre>
	 * FORMATTER / Value to set an option to true.
	 * </pre>
	 * 
	 * @since 3.0
	 */
	public static final String TRUE = "true"; //$NON-NLS-1$

	/**
	 * <pre>
	 * FORMATTER / Option to control whether blank lines are cleared inside javadoc comments
	 *     - option id:         "org.eclipse.php.formatter.core.comment.clear_blank_lines_in_javadoc_comment"
	 *     - possible values:   { TRUE, FALSE }
	 *     - default:           FALSE
	 * </pre>
	 * 
	 * @see #TRUE
	 * @see #FALSE
	 * @since 3.3
	 */
	public final static String FORMATTER_COMMENT_CLEAR_BLANK_LINES_IN_JAVADOC_COMMENT = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.comment.clear_blank_lines_in_javadoc_comment"; //$NON-NLS-1$

	/**
	 * <pre>
	 * FORMATTER / Option to control whether blank lines are cleared inside block comments
	 *     - option id:         "org.eclipse.php.formatter.core.comment.clear_blank_lines_in_block_comment"
	 *     - possible values:   { TRUE, FALSE }
	 *     - default:           FALSE
	 * </pre>
	 * 
	 * @see #TRUE
	 * @see #FALSE
	 * @since 3.3
	 */
	public final static String FORMATTER_COMMENT_CLEAR_BLANK_LINES_IN_BLOCK_COMMENT = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.comment.clear_blank_lines_in_block_comment"; //$NON-NLS-1$

	/**
	 * <pre>
	 * FORMATTER / Option to control whether single line comments are formatted
	 *     - option id:         "org.eclipse.php.formatter.core.comment.format_line_comments"
	 *     - possible values:   { TRUE, FALSE }
	 *     - default:           TRUE
	 * </pre>
	 * 
	 * @see #TRUE
	 * @see #FALSE
	 * @since 3.3
	 */
	public final static String FORMATTER_COMMENT_FORMAT_LINE_COMMENT = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.comment.format_line_comments"; //$NON-NLS-1$
	/**
	 * <pre>
	 * FORMATTER / Option to format line comments that start on the first column
	 *     - option id:         "org.eclipse.php.formatter.core.format_line_comment_starting_on_first_column"
	 *     - possible values:   { TRUE, FALSE }
	 *     - default:           TRUE
	 * </pre>
	 * 
	 * Note that this option is ignored if either the
	 * {@link #FORMATTER_COMMENT_FORMAT_LINE_COMMENT} option has been set to
	 * {@link #FALSE} or the formatter is created with the mode
	 * {@link ToolFactory#M_FORMAT_NEW}.
	 * 
	 * @see #TRUE
	 * @see #FALSE
	 * @see ToolFactory#createCodeFormatter(Map, int)
	 * @since 3.6
	 */
	public static final String FORMATTER_COMMENT_FORMAT_LINE_COMMENT_STARTING_ON_FIRST_COLUMN = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.format_line_comment_starting_on_first_column"; //$NON-NLS-1$

	/**
	 * <pre>
	 * FORMATTER / Option to control whether the white space between code and line comments should be preserved or replaced with a single space
	 *     - option id:         "org.eclipse.php.formatter.core.comment.preserve_white_space_between_code_and_line_comments"
	 *     - possible values:   { TRUE, FALSE }
	 *     - default:           FALSE
	 * </pre>
	 * 
	 * @see #TRUE
	 * @see #FALSE
	 * @since 3.7
	 */
	public final static String FORMATTER_COMMENT_PRESERVE_WHITE_SPACE_BETWEEN_CODE_AND_LINE_COMMENT = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.comment.preserve_white_space_between_code_and_line_comments"; //$NON-NLS-1$

	/**
	 * <pre>
	 * FORMATTER / Option to control whether multiple lines comments are formatted
	 *     - option id:         "org.eclipse.php.formatter.core.comment.format_block_comments"
	 *     - possible values:   { TRUE, FALSE }
	 *     - default:           TRUE
	 * </pre>
	 * 
	 * @see #TRUE
	 * @see #FALSE
	 * @since 3.3
	 */
	public final static String FORMATTER_COMMENT_FORMAT_BLOCK_COMMENT = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.comment.format_block_comments"; //$NON-NLS-1$

	/**
	 * <pre>
	 * FORMATTER / Option to control whether javadoc comments are formatted
	 *     - option id:         "org.eclipse.php.formatter.core.comment.format_javadoc_comments"
	 *     - possible values:   { TRUE, FALSE }
	 *     - default:           TRUE
	 * </pre>
	 * 
	 * @see #TRUE
	 * @see #FALSE
	 * @since 3.3
	 */
	public final static String FORMATTER_COMMENT_FORMAT_JAVADOC_COMMENT = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.comment.format_javadoc_comments"; //$NON-NLS-1$

	/**
	 * <pre>
	 * FORMATTER / Option to control whether the header comment of a Java source file is formatted
	 *     - option id:         "org.eclipse.php.formatter.core.comment.format_header"
	 *     - possible values:   { TRUE, FALSE }
	 *     - default:           FALSE
	 * </pre>
	 * 
	 * @see #TRUE
	 * @see #FALSE
	 * @since 3.1
	 */
	public final static String FORMATTER_COMMENT_FORMAT_HEADER = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.comment.format_header"; //$NON-NLS-1$

	/**
	 * <pre>
	 * FORMATTER / Option to control whether HTML tags are formatted.
	 *     - option id:         "org.eclipse.php.formatter.core.comment.format_html"
	 *     - possible values:   { TRUE, FALSE }
	 *     - default:           TRUE
	 * </pre>
	 * 
	 * @see #TRUE
	 * @see #FALSE
	 * @since 3.1
	 */
	public final static String FORMATTER_COMMENT_FORMAT_HTML = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.comment.format_html"; //$NON-NLS-1$

	/**
	 * <pre>
	 * FORMATTER / Option to control whether code snippets are formatted in comments
	 *     - option id:         "org.eclipse.php.formatter.core.comment.format_source_code"
	 *     - possible values:   { TRUE, FALSE }
	 *     - default:           TRUE
	 * </pre>
	 * 
	 * @see #TRUE
	 * @see #FALSE
	 * @since 3.1
	 */
	public final static String FORMATTER_COMMENT_FORMAT_SOURCE = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.comment.format_source_code"; //$NON-NLS-1$

	/**
	 * <pre>
	 * FORMATTER / Option to control whether description of Javadoc parameters are indented
	 *     - option id:         "org.eclipse.php.formatter.core.comment.indent_parameter_description"
	 *     - possible values:   { TRUE, FALSE }
	 *     - default:           TRUE
	 * </pre>
	 * 
	 * @see #TRUE
	 * @see #FALSE
	 * @since 3.1
	 */
	public final static String FORMATTER_COMMENT_INDENT_PARAMETER_DESCRIPTION = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.comment.indent_parameter_description"; //$NON-NLS-1$

	/**
	 * <pre>
	 * FORMATTER / Option to control whether unknown tags should keep their content unchanged (i.e. no code formatting)
	 *     - option id:         "org.eclipse.php.formatter.core.never_format_unknown_tags"
	 *     - possible values:   { TRUE, FALSE }
	 *     - default:           TRUE
	 * </pre>
	 * 
	 * @see #TRUE
	 * @see #FALSE
	 * @since 5.3
	 */
	public final static String FORMATTER_COMMENT_NEVER_FORMAT_UNKNOWN_TAGS = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.comment.never_format_unknown_tags"; //$NON-NLS-1$

	/**
	 * <pre>
	 * FORMATTER / Option to control whether Javadoc root tags are indented.
	 *     - option id:         "org.eclipse.php.formatter.core.comment.indent_root_tags"
	 *     - possible values:   { TRUE, FALSE }
	 *     - default:           TRUE
	 * </pre>
	 * 
	 * @see #TRUE
	 * @see #FALSE
	 * @since 3.1
	 */
	public final static String FORMATTER_COMMENT_INDENT_ROOT_TAGS = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.comment.indent_root_tags"; //$NON-NLS-1$

	/**
	 * <pre>
	 * FORMATTER / Option to insert an empty line before the Javadoc root tag block
	 *     - option id:         "org.eclipse.php.formatter.core.comment.insert_new_line_before_root_tags"
	 *     - possible values:   { TRUE, FALSE }
	 *     - default:           TRUE
	 * </pre>
	 * 
	 * @see #TRUE
	 * @see #FALSE
	 * @since 3.1
	 */
	public final static String FORMATTER_COMMENT_INSERT_EMPTY_LINE_BEFORE_ROOT_TAGS = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.comment.insert_new_line_before_root_tags"; //$NON-NLS-1$

	/**
	 * <pre>
	 * FORMATTER / Option to keep the empty line before the PHPDoc root tag block, when the PHPDoc description is empty.
	 * Only active when value "org.eclipse.php.formatter.core.comment.insert_new_line_before_root_tags" is TRUE.
	 *     - option id:         "org.eclipse.php.formatter.core.comment.keep_empty_line_for_empty_description"
	 *     - possible values:   { TRUE, FALSE }
	 *     - default:           TRUE
	 * </pre>
	 * 
	 * @see #TRUE
	 * @see #FALSE
	 * @since 6.0
	 */
	public final static String FORMATTER_COMMENT_KEEP_EMPTY_LINE_FOR_EMPTY_DESCRIPTION = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.comment.keep_empty_line_for_empty_description"; //$NON-NLS-1$

	/**
	 * <pre>
	 * FORMATTER / Option to insert a new line after Javadoc root tag parameters
	 *     - option id:         "org.eclipse.php.formatter.core.comment.insert_new_line_for_parameter"
	 *     - possible values:   { INSERT, DO_NOT_INSERT }
	 *     - default:           INSERT
	 * </pre>
	 * 
	 * @see JavaCore#INSERT
	 * @see JavaCore#DO_NOT_INSERT
	 * @since 3.1
	 */
	public final static String FORMATTER_COMMENT_INSERT_NEW_LINE_FOR_PARAMETER = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.comment.insert_new_line_for_parameter"; //$NON-NLS-1$

	/**
	 * <pre>
	 * FORMATTER / Option to specify the line length for comments.
	 *     - option id:         "org.eclipse.php.formatter.core.comment.line_length"
	 *     - possible values:   "&lt;n&gt;", where n is zero or a positive integer
	 *     - default:           "80"
	 * </pre>
	 * 
	 * @since 3.1
	 */
	public final static String FORMATTER_COMMENT_LINE_LENGTH = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.comment.line_length"; //$NON-NLS-1$

	/**
	 * <pre>
	 * FORMATTER / Option to control whether block comments will have new lines at boundaries
	 *     - option id:         "org.eclipse.php.formatter.core.comment.new_lines_at_block_boundaries"
	 *     - possible values:   { TRUE, FALSE }
	 *     - default:           TRUE
	 * </pre>
	 * 
	 * @see #TRUE
	 * @see #FALSE
	 * @since 3.6
	 */
	public final static String FORMATTER_COMMENT_NEW_LINES_AT_BLOCK_BOUNDARIES = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.comment.new_lines_at_block_boundaries"; //$NON-NLS-1$

	/**
	 * <pre>
	 * FORMATTER / Option to control whether javadoc comments will have new lines at boundaries
	 *     - option id:         "org.eclipse.php.formatter.core.comment.new_lines_at_javadoc_boundaries"
	 *     - possible values:   { TRUE, FALSE }
	 *     - default:           TRUE
	 * </pre>
	 * 
	 * @see #TRUE
	 * @see #FALSE
	 * @since 3.6
	 */
	public final static String FORMATTER_COMMENT_NEW_LINES_AT_JAVADOC_BOUNDARIES = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.comment.new_lines_at_javadoc_boundaries"; //$NON-NLS-1$

	/**
	 * <pre>
	 * FORMATTER / Option to set the continuation indentation
	 *     - option id:         "org.eclipse.php.formatter.core.continuation_indentation"
	 *     - possible values:   "&lt;n&gt;", where n is zero or a positive integer
	 *     - default:           "2"
	 * </pre>
	 * 
	 * @since 3.0
	 */
	public static final String FORMATTER_CONTINUATION_INDENTATION = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.continuation_indentation"; //$NON-NLS-1$
	/**
	 * <pre>
	 * FORMATTER / Option to set the continuation indentation inside array initializer
	 *     - option id:         "org.eclipse.php.formatter.core.continuation_indentation_for_array_initializer"
	 *     - possible values:   "&lt;n&gt;", where n is zero or a positive integer
	 *     - default:           "2"
	 * </pre>
	 * 
	 * @since 3.0
	 */
	public static final String FORMATTER_CONTINUATION_INDENTATION_FOR_ARRAY_INITIALIZER = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.continuation_indentation_for_array_initializer"; //$NON-NLS-1$
	/**
	 * <pre>
	 * FORMATTER / Option to indent line comments that start on the first column
	 *     - option id:         "org.eclipse.php.formatter.core.formatter.never_indent_line_comments_on_first_column"
	 *     - possible values:   { TRUE, FALSE }
	 *     - default:           FALSE
	 * </pre>
	 * 
	 * Note that:
	 * <ul>
	 * <li>this option is ignored if the formatter is created with the mode
	 * {@link ToolFactory#M_FORMAT_NEW}</li>
	 * <li>even with this option activated, the formatter still can ignore line
	 * comments starting at first column if the option
	 * {@link #FORMATTER_COMMENT_FORMAT_LINE_COMMENT_STARTING_ON_FIRST_COLUMN}
	 * is set to {@value #FALSE}</li>
	 * </ul>
	 * 
	 * @see #TRUE
	 * @see #FALSE
	 * @see ToolFactory#createCodeFormatter(Map, int)
	 * @since 3.3
	 */
	public static final String FORMATTER_NEVER_INDENT_LINE_COMMENTS_ON_FIRST_COLUMN = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.never_indent_line_comments_on_first_column"; //$NON-NLS-1$
	/**
	 * <pre>
	 * FORMATTER / Option to indent block comments that start on the first column
	 *     - option id:         "org.eclipse.php.formatter.core.formatter.never_indent_block_comments_on_first_column"
	 *     - possible values:   { TRUE, FALSE }
	 *     - default:           FALSE
	 * </pre>
	 * 
	 * Note that this option is ignored if the formatter is created with the
	 * mode {@link ToolFactory#M_FORMAT_NEW}.
	 * 
	 * @see #TRUE
	 * @see #FALSE
	 * @see ToolFactory#createCodeFormatter(Map, int)
	 * @since 3.3
	 */
	public static final String FORMATTER_NEVER_INDENT_BLOCK_COMMENTS_ON_FIRST_COLUMN = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.never_indent_block_comments_on_first_column"; //$NON-NLS-1$
	/**
	 * <pre>
	 * FORMATTER / Option to specify whether the formatter can join text lines in comments or not
	 * 
	 * 		For example, the following comment:
	 * 			/**
	 * 			 * The foo method.
	 * 			 * foo is a substitute for bar.
	 * 			 *&#0047;
	 * 			public class X {
	 * 			}
	 * 
	 * 		will be unchanged by the formatter when this new preference is used,
	 * 		even if the maximum line width would give it enough space to join the lines.
	 * 
	 *     - option id:         "org.eclipse.php.formatter.core.join_lines_in_comments"
	 *     - possible values:   { TRUE, FALSE }
	 *     - default:           TRUE
	 * </pre>
	 * 
	 * @since 3.5
	 */
	public static final String FORMATTER_JOIN_LINES_IN_COMMENTS = FormatterCorePlugin.PLUGIN_ID
			+ ".formatter.join_lines_in_comments"; //$NON-NLS-1$

	/**
	 * <pre>
	 * FORMATTER / Option to use the disabling and enabling tags defined respectively by the {@link #FORMATTER_DISABLING_TAG} and the {@link #FORMATTER_ENABLING_TAG} options.
	 *     - option id:         "org.eclipse.php.formatter.core.use_on_off_tags"
	 *     - possible values:   TRUE / FALSE
	 *     - default:           FALSE
	 * </pre>
	 * 
	 * @since 3.6
	 */
	public static final String FORMATTER_USE_ON_OFF_TAGS = FormatterCorePlugin.PLUGIN_ID + ".formatter.use_on_off_tags"; //$NON-NLS-1$
	/**
	 * <pre>
	 * FORMATTER / Option to define the tag to put in a comment to disable the formatting.
	 *     - option id:         "org.eclipse.php.formatter.core.disabling_tag"
	 *     - possible values:   String, with constraints mentioned below
	 *     - default:           "@formatter:off"
	 * 
	 * See the {@link #FORMATTER_ENABLING_TAG} option to re-enable it.
	 * </pre>
	 * 
	 * <p>
	 * Note that:
	 * <ol>
	 * <li>This tag is used by the formatter only if the
	 * {@link #FORMATTER_USE_ON_OFF_TAGS} option is set to {@link #TRUE}.</li>
	 * <li>The tag name will be trimmed. Hence if it does contain white spaces
	 * at the beginning or at the end, they will not be taken into account while
	 * searching for the tag in the comments</li>
	 * <li>If a tag is starting with a letter or digit, then it cannot be leaded
	 * by another letter or digit to be recognized (
	 * <code>"ToDisableFormatter"</code> will not be recognized as a disabling
	 * tag <code>"DisableFormatter"</code>, but
	 * <code>"To:DisableFormatter"</code> will be detected for either tag
	 * <code>"DisableFormatter"</code> or <code>":DisableFormatter"</code>).<br>
	 * Respectively, a tag ending with a letter or digit cannot be followed by a
	 * letter or digit to be recognized (<code>"DisableFormatter1"</code> will
	 * not be recognized as a disabling tag <code>"DisableFormatter"</code>, but
	 * <code>"DisableFormatter:1"</code> will be detected either for tag
	 * <code>"DisableFormatter"</code> or <code>"DisableFormatter:"</code>)</li>
	 * <li>As soon as the formatter encounters the defined disabling tag, it
	 * stops to format the code from the beginning of the comment including this
	 * tag. If it was already disabled, the tag has no special effect.
	 * <p>
	 * For example, the second default enabling tag &quot;<b>@formatter:off</b>
	 * &quot; in the following snippet is not necessary as the formatter was
	 * already disabled since the first one:
	 * 
	 * <pre>
	 * class X {
	 * 	// @formatter:off
	 * 	void foo1() {
	 * 	}
	 * 
	 * 	// @formatter:off
	 * 	void foo2() {
	 * 	}
	 * 
	 * 	void bar1() {
	 * 	}
	 * 
	 * 	void bar2() {
	 * 	}
	 * }
	 * </pre>
	 * 
	 * </p>
	 * </li>
	 * <li>If no enabling tag is found by the formatter after the disabling tag,
	 * then the end of the snippet won't be formatted.<br>
	 * For example, when a disabling tag is put at the beginning of the code,
	 * then the entire content of a compilation unit is not formatted:
	 * 
	 * <pre>
	 * // @formatter:off
	 * class X {
	 * 	void foo1() {
	 * 	}
	 * 
	 * 	void foo2() {
	 * 	}
	 * 
	 * 	void bar1() {
	 * 	}
	 * 
	 * 	void bar2() {
	 * 	}
	 * }
	 * </pre>
	 * 
	 * </li>
	 * <li>If a mix of disabling and enabling tags is done in the same comment,
	 * then the formatter will only take into account the last encountered tag
	 * in the comment.
	 * <p>
	 * For example, in the following snippet, the formatter will be disabled
	 * after the comment:
	 * </p>
	 * 
	 * <pre>
	 * class X {
	 * 	/*
	 * 	 * This is a comment with a mix of disabling and enabling tags: -
	 * 	 * &lt;b&gt;@formatter:off&lt;/b&gt; - &lt;b&gt;@formatter:on&lt;/b&gt; -
	 * 	 * &lt;b&gt;@formatter:off&lt;/b&gt; The formatter will stop to format from the
	 * 	 * beginning of this comment...
	 * 	&#42;/
	 * 	void foo() {
	 * 	}
	 * 
	 * 	void bar() {
	 * 	}
	 * }
	 * </pre>
	 * 
	 * </li>
	 * <li>The tag cannot include newline character (i.e. '\n') but it can have
	 * white spaces.<br>
	 * E.g. "<b>format: off</b>" is a valid disabling tag.<br>
	 * In the future, newlines may be used to support multiple disabling
	 * tags.</li>
	 * <li>The tag can include line or block comments start/end tokens.
	 * <p>
	 * If such tags are used, e.g. "<b>//J-</b>", then the single comment can
	 * also stop the formatting as shown in the following snippet:
	 * </p>
	 * 
	 * <pre>
	 * //J-
	 * // Formatting was stopped from comment above...
	 * public class X {
	 * //J+
	 * // Formatting is restarted from here...
	 * void foo() {}
	 * </pre>
	 * 
	 * <p>
	 * As any disabling tags, as soon as a comment includes it, the formatting
	 * stops from this comment:
	 * </p>
	 * 
	 * <pre>
	 * public class X {
	 * 	// Line comment including the disabling tag: //J-
	 * 	// Formatting was stopped from comment above...
	 * 	void foo1() {
	 * 	}
	 * 
	 * 	// J+
	 * 	// Formatting restarts from here...
	 * 	void bar1() {
	 * 	}
	 * 
	 * 	/*
	 * 	 * Block comment including the disabling tag: //J+ The formatter
	 * 	 * stops from this comment...
	 * 	&#42;/
	 * 	void foo2() {
	 * 	}
	 * 
	 * 	// J+
	 * 	// Formatting restarts from here...
	 * 	void bar2() {
	 * 	}
	 * 
	 * 	/**
	 * 	 * Javadoc comment including the enabling tag: //J+ The formatter
	 * 	 * stops from this comment...
	 * 	&#42;/
	 * 	void foo3() {
	 * 	}
	 * }
	 * </pre>
	 * 
	 * </li>
	 * </ol>
	 * </p>
	 * 
	 * @since 3.6
	 */
	public static final String FORMATTER_DISABLING_TAG = FormatterCorePlugin.PLUGIN_ID + ".formatter.disabling_tag"; //$NON-NLS-1$
	/**
	 * <pre>
	 * FORMATTER / Option to define the tag to put in a comment to re-enable the formatting after it has been disabled (see {@link #FORMATTER_DISABLING_TAG})
	 *     - option id:         "org.eclipse.php.formatter.core.enabling_tag"
	 *     - possible values:   String, with constraints mentioned below
	 *     - default:           "@formatter:on"
	 * </pre>
	 * 
	 * <p>
	 * Note that:
	 * <ol>
	 * <li>This tag is used by the formatter only if the
	 * {@link #FORMATTER_USE_ON_OFF_TAGS} option is set to {@link #TRUE}.</li>
	 * <li>The tag name will be trimmed. Hence if it does contain white spaces
	 * at the beginning or at the end, they will not be taken into account while
	 * searching for the tag in the comments</li>
	 * <li>If a tag is starting with a letter or digit, then it cannot be leaded
	 * by another letter or digit to be recognized (
	 * <code>"ReEnableFormatter"</code> will not be recognized as an enabling
	 * tag <code>"EnableFormatter"</code>, but <code>"Re:EnableFormatter"</code>
	 * will be detected for either tag <code>"EnableFormatter"</code> or
	 * <code>":EnableFormatter"</code>).<br>
	 * Respectively, a tag ending with a letter or digit cannot be followed by a
	 * letter or digit to be recognized (<code>"EnableFormatter1"</code> will
	 * not be recognized as an enabling tag <code>"EnableFormatter"</code>, but
	 * <code>"EnableFormatter:1"</code> will be detected either for tag
	 * <code>"EnableFormatter"</code> or <code>"EnableFormatter:"</code>)</li>
	 * <li>As soon as the formatter encounters the defined enabling tag, it
	 * re-starts to format the code just after the comment including this tag.
	 * If it was already active, i.e. already re-enabled or never disabled, the
	 * tag has no special effect.
	 * <p>
	 * For example, the default enabling tag &quot;<b>@formatter:on</b>&quot; in
	 * the following snippet is not necessary as the formatter has never been
	 * disabled:
	 * 
	 * <pre>
	 * class X {
	 * 	void foo1() {
	 * 	}
	 * 
	 * 	void foo2() {
	 * 	}
	 * 
	 * 	// @formatter:on
	 * 	void bar1() {
	 * 	}
	 * 
	 * 	void bar2() {
	 * 	}
	 * }
	 * </pre>
	 * 
	 * Or, in the following other snippet, the second enabling tag is not
	 * necessary as the formatting will have been re-enabled by the first one:
	 * 
	 * <pre>
	 * class X {
	 * 	// @formatter:off
	 * 	void foo1() {
	 * 	}
	 * 
	 * 	void foo2() {
	 * 	}
	 * 
	 * 	// @formatter:on
	 * 	void bar1() {
	 * 	}
	 * 
	 * 	// @formatter:on
	 * 	void bar2() {
	 * 	}
	 * }
	 * </pre>
	 * 
	 * </p>
	 * </li>
	 * <li>If a mix of disabling and enabling tags is done in the same comment,
	 * then the formatter will only take into account the last encountered tag
	 * in the comment.
	 * <p>
	 * For example, in the following snippet, the formatter will be re-enabled
	 * after the comment:
	 * </p>
	 * 
	 * <pre>
	 * // @formatter:off
	 * class X {
	 * 	/*
	 * 	 * This is a comment with a mix of disabling and enabling tags: -
	 * 	 * &lt;b&gt;@formatter:on&lt;/b&gt; - &lt;b&gt;@formatter:off&lt;/b&gt; -
	 * 	 * &lt;b&gt;@formatter:on&lt;/b&gt; The formatter will restart to format after
	 * 	 * this comment...
	 * 	&#42;/
	 * 	void foo() {
	 * 	}
	 * 
	 * 	void bar() {
	 * 	}
	 * }
	 * </pre>
	 * 
	 * </li>
	 * <li>The tag cannot include newline character (i.e. '\n') but it can have
	 * white spaces.<br>
	 * E.g. "<b>format: on</b>" is a valid enabling tag<br>
	 * In the future, newlines may be used to support multiple enabling
	 * tags.</li>
	 * <li>The tag can include line or block comments start/end tokens. Javadoc
	 * tokens are not considered as valid tags.
	 * <p>
	 * If such tags are used, e.g. "<b>//J+</b>", then the single comment can
	 * also start the formatting as shown in the following snippet:
	 * </p>
	 * 
	 * <pre>
	 * // J-
	 * // Formatting was stopped from comment above...
	 * public class X {
	 * 	// J+
	 * 	// Formatting restarts from here...
	 * 	void foo() {
	 * 	}
	 * }
	 * </pre>
	 * 
	 * <p>
	 * As any enabling tags, as soon as a comment includes it, the formatting
	 * restarts just after the comment:
	 * </p>
	 * 
	 * <pre>
	 * public class X {
	 * 	// J-
	 * 	// Formatting was stopped from comment above...
	 * 	void foo1() {
	 * 	}
	 * 
	 * 	// Line comment including the enabling tag: //J+
	 * 	// Formatting restarts from here...
	 * 	void bar1() {
	 * 	}
	 * 
	 * 	// J-
	 * 	// Formatting was stopped from comment above...
	 * 	void foo2() {
	 * 	}
	 * 
	 * 	/*
	 * 	 * Block comment including the enabling tag: //J+ The formatter
	 * 	 * restarts after this comment...
	 * 	&#42;/
	 * 	// Formatting restarts from here...
	 * 	void bar2() {
	 * 	}
	 * 
	 * 	// J-
	 * 	// Formatting was stopped from comment above...
	 * 	void foo3() {
	 * 	}
	 * 
	 * 	/**
	 * 	 * Javadoc comment including the enabling tag: //J+ The formatter
	 * 	 * restarts after this comment...
	 * 	&#42;/
	 * 	void bar3() {
	 * 	}
	 * }
	 * </pre>
	 * 
	 * </li>
	 * </ol>
	 * </p>
	 * 
	 * @since 3.6
	 */
	public static final String FORMATTER_ENABLING_TAG = FormatterCorePlugin.PLUGIN_ID + ".formatter.enabling_tag"; //$NON-NLS-1$

	public static final String EMPTY_STRING = ""; //$NON-NLS-1$

}
