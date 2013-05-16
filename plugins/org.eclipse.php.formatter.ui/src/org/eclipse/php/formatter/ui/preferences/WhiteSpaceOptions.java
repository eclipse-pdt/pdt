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

import java.util.*;

import org.eclipse.php.formatter.core.CodeFormatterConstants;
import org.eclipse.php.formatter.core.CodeFormatterPreferences;
import org.eclipse.php.formatter.ui.FormatterMessages;

/**
 * Manage code formatter white space options on a higher level.
 */
public final class WhiteSpaceOptions {

	/**
	 * Represents a node in the options tree.
	 */
	public abstract static class Node {

		private final InnerNode fParent;
		private final String fName;

		public int index;

		protected final Map fWorkingValues;
		protected final ArrayList fChildren;

		public Node(InnerNode parent, Map workingValues, String message) {
			if (workingValues == null || message == null)
				throw new IllegalArgumentException();
			fParent = parent;
			fWorkingValues = workingValues;
			fName = message;
			fChildren = new ArrayList();
			if (fParent != null)
				fParent.add(this);
		}

		public abstract void setChecked(boolean checked);

		public boolean hasChildren() {
			return !fChildren.isEmpty();
		}

		public List getChildren() {
			return Collections.unmodifiableList(fChildren);
		}

		public InnerNode getParent() {
			return fParent;
		}

		public final String toString() {
			return fName;
		}

		public abstract List getSnippets();

		public abstract void getCheckedLeafs(List list);
	}

	/**
	 * A node representing a group of options in the tree.
	 */
	public static class InnerNode extends Node {

		public InnerNode(InnerNode parent, Map workingValues, String messageKey) {
			super(parent, workingValues, messageKey);
		}

		public void setChecked(boolean checked) {
			for (final Iterator iter = fChildren.iterator(); iter.hasNext();)
				((Node) iter.next()).setChecked(checked);
		}

		public void add(Node child) {
			fChildren.add(child);
		}

		public List getSnippets() {
			final ArrayList snippets = new ArrayList(fChildren.size());
			for (Iterator iter = fChildren.iterator(); iter.hasNext();) {
				final List childSnippets = ((Node) iter.next()).getSnippets();
				for (final Iterator chIter = childSnippets.iterator(); chIter
						.hasNext();) {
					final Object snippet = chIter.next();
					if (!snippets.contains(snippet))
						snippets.add(snippet);
				}
			}
			return snippets;
		}

		public void getCheckedLeafs(List list) {
			for (Iterator iter = fChildren.iterator(); iter.hasNext();) {
				((Node) iter.next()).getCheckedLeafs(list);
			}
		}
	}

	/**
	 * A node representing a concrete white space option in the tree.
	 */
	public static class OptionNode extends Node {
		private final String fKey;
		private final ArrayList fSnippets;

		public OptionNode(InnerNode parent, Map workingValues,
				String messageKey, String key, String snippet) {
			super(parent, workingValues, messageKey);
			fKey = key;
			fSnippets = new ArrayList(1);
			fSnippets.add(snippet);
		}

		public void setChecked(boolean checked) {
			fWorkingValues.put(fKey, checked ? CodeFormatterPreferences.TRUE
					: CodeFormatterPreferences.FALSE);
		}

		public boolean getChecked() {
			return CodeFormatterPreferences.TRUE.equals(fWorkingValues
					.get(fKey));
		}

		public List getSnippets() {
			return fSnippets;
		}

		public void getCheckedLeafs(List list) {
			if (getChecked())
				list.add(this);
		}
	}

	/**
	 * Preview snippets.
	 */

	private final static String FOR_PREVIEW = "for ($i= 0, $j= 0; $i < 8; $i++, $j--) {}"; //$NON-NLS-1$
	private final static String FOREACH_PREVIEW = "foreach ($s as $key => $value) {}"; //$NON-NLS-1$

	private final static String WHILE_PREVIEW = "while ($condition) {} do {} while ($condition);"; //$NON-NLS-1$

	private final static String CATCH_PREVIEW = "try { echo $b; } catch (Exception $e) {}"; //$NON-NLS-1$

	private final static String STATIC_PREVIEW = "static $a,$b;"; //$NON-NLS-1$

	private final static String GLOBAL_PREVIEW = "global $a,$b;"; //$NON-NLS-1$

	private final static String ECHO_PREVIEW = "echo 'hello ',$a;"; //$NON-NLS-1$

	private final static String IF_PREVIEW = "if ($condition) { return $foo; } else {return $bar;}"; //$NON-NLS-1$

	private final static String SWITCH_PREVIEW = "switch ($number) { case RED: return GREEN; case GREEN: return BLUE; case BLUE: return RED; default: return BLACK;}"; //$NON-NLS-1$

	private final static String METHOD_DECL_PREVIEW = "function foo() {}" + //$NON-NLS-1$
			"function bar(MyClass $x, $y, $z=1) {}"; //$NON-NLS-1$

	private final static String ARRAY_CREATION_PREVIEW = "list($a,$b)= array(1,2,3);\n" + //$NON-NLS-1$
			"$array=array(1=>2,2=>3);\n"; //$NON-NLS-1$

	private final static String ARRAY_ACCESS_PREVIEW = "$array[$i]->foo();$array[]='first cell';"; //$NON-NLS-1$

	private final static String METHOD_CALL_PREVIEW = "foo();\n" + //$NON-NLS-1$
			"bar($x, $y);MyClass::foo();$myClass->foo();"; //$NON-NLS-1$

	private final static String FIELD_ACCESS_PREVIEW = "$myClass->$attr;MyClass::$attr;MyClass::MY_CONST;";//$NON-NLS-1$

	private final static String SEMICOLON_PREVIEW = "$a= 4; foo(); bar($x, $y);"; //$NON-NLS-1$

	private final static String CONDITIONAL_PREVIEW = "$a = $condition ? TRUE : FALSE;"; //$NON-NLS-1$

	private final static String CLASS_DECL_PREVIEW = "class MyClass implements I0, I1, I2 {}"; //$NON-NLS-1$

	private final static String OPERATOR_PREVIEW = "$a= -4 + -9; $b= $a++ / --$number; $c += 4; $value= true && false;"; //$NON-NLS-1$

	private final static String CAST_PREVIEW = "$s= (string)$object;"; //$NON-NLS-1$

	private final static String PARENTHESIS_EXPRESSION_PREVIEW = "(($a));"; //$NON-NLS-1$

	private final static String MULT_FIELD_PREVIEW = "class MyClass {public $a=0,$b=1,$c=2,$d=3; const MY_TRUE=1,MY_FALSE=2;}"; //$NON-NLS-1$

	private final static String BLOCK_PREVIEW = "if (true) { return 1; } else if(true) {return 3; }else { return 2; }"; //$NON-NLS-1$

	/**
	 * Create the tree, in this order: syntax element - position - abstract
	 * element
	 * 
	 * @param workingValues
	 * @return returns roots (type <code>Node</code>)
	 */
	public static List createTreeBySyntaxElem(Map workingValues) {
		final ArrayList roots = new ArrayList();

		InnerNode element;

		element = new InnerNode(null, workingValues,
				FormatterMessages.WhiteSpaceOptions_opening_paren);
		createBeforeOpenParenTree(
				workingValues,
				createChild(element, workingValues,
						FormatterMessages.WhiteSpaceOptions_before));
		createAfterOpenParenTree(
				workingValues,
				createChild(element, workingValues,
						FormatterMessages.WhiteSpaceOptions_after));
		roots.add(element);

		element = new InnerNode(null, workingValues,
				FormatterMessages.WhiteSpaceOptions_closing_paren);
		createBeforeClosingParenTree(
				workingValues,
				createChild(element, workingValues,
						FormatterMessages.WhiteSpaceOptions_before));
		createAfterCloseParenTree(
				workingValues,
				createChild(element, workingValues,
						FormatterMessages.WhiteSpaceOptions_after));
		roots.add(element);

		element = new InnerNode(null, workingValues,
				FormatterMessages.WhiteSpaceOptions_opening_brace);
		createBeforeOpenBraceTree(
				workingValues,
				createChild(element, workingValues,
						FormatterMessages.WhiteSpaceOptions_before));
		roots.add(element);

		element = new InnerNode(null, workingValues,
				FormatterMessages.WhiteSpaceOptions_closing_brace);
		createAfterCloseBraceTree(
				workingValues,
				createChild(element, workingValues,
						FormatterMessages.WhiteSpaceOptions_after));
		roots.add(element);

		element = new InnerNode(null, workingValues,
				FormatterMessages.WhiteSpaceOptions_opening_bracket);
		createBeforeOpenBracketTree(
				workingValues,
				createChild(element, workingValues,
						FormatterMessages.WhiteSpaceOptions_before));
		createAfterOpenBracketTree(
				workingValues,
				createChild(element, workingValues,
						FormatterMessages.WhiteSpaceOptions_after));
		roots.add(element);

		element = new InnerNode(null, workingValues,
				FormatterMessages.WhiteSpaceOptions_closing_bracket);
		createBeforeClosingBracketTree(
				workingValues,
				createChild(element, workingValues,
						FormatterMessages.WhiteSpaceOptions_before));
		roots.add(element);

		element = new InnerNode(null, workingValues,
				FormatterMessages.WhiteSpaceOptions_operator);
		createBeforeOperatorTree(
				workingValues,
				createChild(element, workingValues,
						FormatterMessages.WhiteSpaceOptions_before));
		createAfterOperatorTree(
				workingValues,
				createChild(element, workingValues,
						FormatterMessages.WhiteSpaceOptions_after));
		roots.add(element);

		element = new InnerNode(null, workingValues,
				FormatterMessages.WhiteSpaceOptions_comma);
		createBeforeCommaTree(
				workingValues,
				createChild(element, workingValues,
						FormatterMessages.WhiteSpaceOptions_before));
		createAfterCommaTree(
				workingValues,
				createChild(element, workingValues,
						FormatterMessages.WhiteSpaceOptions_after));
		roots.add(element);

		element = new InnerNode(null, workingValues,
				FormatterMessages.WhiteSpaceOptions_colon);
		createBeforeColonTree(
				workingValues,
				createChild(element, workingValues,
						FormatterMessages.WhiteSpaceOptions_before));
		createAfterColonTree(
				workingValues,
				createChild(element, workingValues,
						FormatterMessages.WhiteSpaceOptions_after));
		roots.add(element);

		element = new InnerNode(null, workingValues,
				FormatterMessages.WhiteSpaceOptions_semicolon);
		createBeforeSemicolonTree(
				workingValues,
				createChild(element, workingValues,
						FormatterMessages.WhiteSpaceOptions_before));
		createAfterSemicolonTree(
				workingValues,
				createChild(element, workingValues,
						FormatterMessages.WhiteSpaceOptions_after));
		roots.add(element);

		element = new InnerNode(null, workingValues,
				FormatterMessages.WhiteSpaceOptions_question_mark);
		createBeforeQuestionTree(
				workingValues,
				createChild(element, workingValues,
						FormatterMessages.WhiteSpaceOptions_before));
		createAfterQuestionTree(
				workingValues,
				createChild(element, workingValues,
						FormatterMessages.WhiteSpaceOptions_after));
		roots.add(element);

		element = new InnerNode(null, workingValues,
				FormatterMessages.WhiteSpaceOptions_between_empty_parens);
		createBetweenEmptyParenTree(workingValues, element);
		roots.add(element);

		element = new InnerNode(null, workingValues,
				FormatterMessages.WhiteSpaceOptions_between_empty_brackets);
		createBetweenEmptyBracketsTree(workingValues, element);
		roots.add(element);

		element = new InnerNode(null, workingValues,
				FormatterMessages.WhiteSpaceOptions_before_arrow);
		createBeforeArrowTree(workingValues, element);
		roots.add(element);

		element = new InnerNode(null, workingValues,
				FormatterMessages.WhiteSpaceOptions_after_arrow);
		createAfterArrowTree(workingValues, element);
		roots.add(element);

		element = new InnerNode(null, workingValues,
				FormatterMessages.WhiteSpaceOptions_before_coloncolon);
		createBeforeColoncolonTree(workingValues, element);
		roots.add(element);

		element = new InnerNode(null, workingValues,
				FormatterMessages.WhiteSpaceOptions_after_coloncolon);
		createAfterColoncolonTree(workingValues, element);
		roots.add(element);

		return roots;
	}

	/**
	 * Create the tree, in this order: position - syntax element - abstract
	 * element
	 * 
	 * @param workingValues
	 * @return returns roots (type <code>Node</code>)
	 */
	public static List createAltTree(Map workingValues) {

		final ArrayList roots = new ArrayList();

		InnerNode parent;

		parent = createParentNode(roots, workingValues,
				FormatterMessages.WhiteSpaceOptions_before_opening_paren);
		createBeforeOpenParenTree(workingValues, parent);

		parent = createParentNode(roots, workingValues,
				FormatterMessages.WhiteSpaceOptions_after_opening_paren);
		createAfterOpenParenTree(workingValues, parent);

		parent = createParentNode(roots, workingValues,
				FormatterMessages.WhiteSpaceOptions_before_closing_paren);
		createBeforeClosingParenTree(workingValues, parent);

		parent = createParentNode(roots, workingValues,
				FormatterMessages.WhiteSpaceOptions_after_closing_paren);
		createAfterCloseParenTree(workingValues, parent);

		parent = createParentNode(roots, workingValues,
				FormatterMessages.WhiteSpaceOptions_between_empty_parens);
		createBetweenEmptyParenTree(workingValues, parent);

		parent = createParentNode(roots, workingValues,
				FormatterMessages.WhiteSpaceOptions_before_opening_brace);
		createBeforeOpenBraceTree(workingValues, parent);

		parent = createParentNode(roots, workingValues,
				FormatterMessages.WhiteSpaceOptions_after_closing_brace);
		createAfterCloseBraceTree(workingValues, parent);

		parent = createParentNode(roots, workingValues,
				FormatterMessages.WhiteSpaceOptions_before_opening_bracket);
		createBeforeOpenBracketTree(workingValues, parent);

		parent = createParentNode(roots, workingValues,
				FormatterMessages.WhiteSpaceOptions_after_opening_bracket);
		createAfterOpenBracketTree(workingValues, parent);

		parent = createParentNode(roots, workingValues,
				FormatterMessages.WhiteSpaceOptions_before_closing_bracket);
		createBeforeClosingBracketTree(workingValues, parent);

		parent = createParentNode(roots, workingValues,
				FormatterMessages.WhiteSpaceOptions_between_empty_brackets);
		createBetweenEmptyBracketsTree(workingValues, parent);

		parent = createParentNode(roots, workingValues,
				FormatterMessages.WhiteSpaceOptions_before_operator);
		createBeforeOperatorTree(workingValues, parent);

		parent = createParentNode(roots, workingValues,
				FormatterMessages.WhiteSpaceOptions_after_operator);
		createAfterOperatorTree(workingValues, parent);

		parent = createParentNode(roots, workingValues,
				FormatterMessages.WhiteSpaceOptions_before_comma);
		createBeforeCommaTree(workingValues, parent);

		parent = createParentNode(roots, workingValues,
				FormatterMessages.WhiteSpaceOptions_after_comma);
		createAfterCommaTree(workingValues, parent);

		parent = createParentNode(roots, workingValues,
				FormatterMessages.WhiteSpaceOptions_after_colon);
		createAfterColonTree(workingValues, parent);

		parent = createParentNode(roots, workingValues,
				FormatterMessages.WhiteSpaceOptions_before_colon);
		createBeforeColonTree(workingValues, parent);

		parent = createParentNode(roots, workingValues,
				FormatterMessages.WhiteSpaceOptions_before_semicolon);
		createBeforeSemicolonTree(workingValues, parent);

		parent = createParentNode(roots, workingValues,
				FormatterMessages.WhiteSpaceOptions_after_semicolon);
		createAfterSemicolonTree(workingValues, parent);

		parent = createParentNode(roots, workingValues,
				FormatterMessages.WhiteSpaceOptions_before_question_mark);
		createBeforeQuestionTree(workingValues, parent);

		parent = createParentNode(roots, workingValues,
				FormatterMessages.WhiteSpaceOptions_after_question_mark);
		createAfterQuestionTree(workingValues, parent);

		parent = createParentNode(roots, workingValues,
				FormatterMessages.WhiteSpaceOptions_before_arrow);
		createBeforeArrowTree(workingValues, parent);

		parent = createParentNode(roots, workingValues,
				FormatterMessages.WhiteSpaceOptions_after_arrow);
		createAfterArrowTree(workingValues, parent);

		parent = createParentNode(roots, workingValues,
				FormatterMessages.WhiteSpaceOptions_before_coloncolon);
		createBeforeColoncolonTree(workingValues, parent);

		parent = createParentNode(roots, workingValues,
				FormatterMessages.WhiteSpaceOptions_after_coloncolon);
		createAfterColoncolonTree(workingValues, parent);
		return roots;
	}

	private static InnerNode createParentNode(List roots, Map workingValues,
			String text) {
		final InnerNode parent = new InnerNode(null, workingValues, text);
		roots.add(parent);
		return parent;
	}

	public static ArrayList createTreeByPhpElement(Map workingValues) {

		final InnerNode declarations = new InnerNode(null, workingValues,
				FormatterMessages.WhiteSpaceTabPage_declarations);
		createClassTree(workingValues, declarations);
		createFieldTree(workingValues, declarations);
		createMethodDeclTree(workingValues, declarations);

		final InnerNode statements = new InnerNode(null, workingValues,
				FormatterMessages.WhiteSpaceTabPage_statements);
		createOption(statements, workingValues,
				FormatterMessages.WhiteSpaceOptions_before_semicolon,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_SEMICOLON,
				SEMICOLON_PREVIEW);
		createBlockTree(workingValues, statements);
		createIfStatementTree(workingValues, statements);
		createForStatementTree(workingValues, statements);
		createForeachStatementTree(workingValues, statements);
		createSwitchStatementTree(workingValues, statements);
		createDoWhileTree(workingValues, statements);
		createTryStatementTree(workingValues, statements);
		createStaticTree(workingValues, statements);
		createGlobalTree(workingValues, statements);
		createEchoTree(workingValues, statements);

		final InnerNode expressions = new InnerNode(null, workingValues,
				FormatterMessages.WhiteSpaceTabPage_expressions);
		createFieldAccessTree(workingValues, expressions);
		createFunctionCallTree(workingValues, expressions);
		createAssignmentTree(workingValues, expressions);
		createOperatorTree(workingValues, expressions);
		createTypecastTree(workingValues, expressions);
		createConditionalTree(workingValues, expressions);
		createParenthesisExpressionTree(workingValues, expressions);

		final InnerNode arrays = new InnerNode(null, workingValues,
				FormatterMessages.WhiteSpaceTabPage_arrays);
		createArrayCreationTree(workingValues, arrays);
		createArrayElementAccessTree(workingValues, arrays);

		final ArrayList roots = new ArrayList();
		roots.add(declarations);
		roots.add(statements);
		roots.add(expressions);
		roots.add(arrays);
		return roots;
	}

	private static void createBeforeQuestionTree(Map workingValues,
			final InnerNode parent) {
		createOption(
				parent,
				workingValues,
				FormatterMessages.WhiteSpaceOptions_conditional,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_QUESTION_IN_CONDITIONAL,
				CONDITIONAL_PREVIEW);
	}

	private static void createBeforeSemicolonTree(Map workingValues,
			final InnerNode parent) {
		createOption(
				parent,
				workingValues,
				FormatterMessages.WhiteSpaceOptions_for,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_SEMICOLON_IN_FOR,
				FOR_PREVIEW);
		createOption(parent, workingValues,
				FormatterMessages.WhiteSpaceOptions_statements,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_SEMICOLON,
				SEMICOLON_PREVIEW);
	}

	private static void createBeforeColonTree(Map workingValues,
			final InnerNode parent) {
		createOption(
				parent,
				workingValues,
				FormatterMessages.WhiteSpaceOptions_conditional,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_COLON_IN_CONDITIONAL,
				CONDITIONAL_PREVIEW);

		final InnerNode switchStatement = createChild(parent, workingValues,
				FormatterMessages.WhiteSpaceOptions_switch);
		createOption(
				switchStatement,
				workingValues,
				FormatterMessages.WhiteSpaceOptions_case,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_COLON_IN_CASE,
				SWITCH_PREVIEW);
		createOption(
				switchStatement,
				workingValues,
				FormatterMessages.WhiteSpaceOptions_default,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_COLON_IN_DEFAULT,
				SWITCH_PREVIEW);
	}

	private static void createBeforeCommaTree(Map workingValues,
			final InnerNode parent) {
		createOption(
				parent,
				workingValues,
				FormatterMessages.WhiteSpaceOptions_for,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_COMMA_IN_FOR,
				FOR_PREVIEW);
		createOption(
				parent,
				workingValues,
				FormatterMessages.WhiteSpaceOptions_method_call,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_COMMA_IN_METHOD_INVOCATION_ARGUMENTS,
				METHOD_CALL_PREVIEW);
		createOption(
				parent,
				workingValues,
				FormatterMessages.WhiteSpaceOptions_method_decl,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_COMMA_IN_METHOD_DECLARATION_PARAMETERS,
				METHOD_DECL_PREVIEW);
		createOption(
				parent,
				workingValues,
				FormatterMessages.WhiteSpaceOptions_fields,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_COMMA_IN_MULTIPLE_FIELD_DECLARATIONS,
				MULT_FIELD_PREVIEW);
		createOption(
				parent,
				workingValues,
				FormatterMessages.WhiteSpaceOptions_constants,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_COMMA_IN_MULTIPLE_CONSTANT_DECLARATIONS,
				MULT_FIELD_PREVIEW);
		createOption(
				parent,
				workingValues,
				FormatterMessages.WhiteSpaceOptions_implements_clause,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_COMMA_IN_SUPERINTERFACES,
				CLASS_DECL_PREVIEW);
		createOption(
				parent,
				workingValues,
				FormatterMessages.WhiteSpaceOptions_array_decl,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_COMMA_IN_ARRAY_CREATION,
				ARRAY_CREATION_PREVIEW);
		createOption(
				parent,
				workingValues,
				FormatterMessages.WhiteSpaceOptions_list,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_COMMA_IN_LIST,
				ARRAY_CREATION_PREVIEW);
		createOption(
				parent,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_echo,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_COMMA_IN_ECHO,
				ECHO_PREVIEW);
		createOption(
				parent,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_global,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_COMMA_IN_GLOBAL,
				GLOBAL_PREVIEW);
		createOption(
				parent,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_static,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_COMMA_IN_STATIC,
				STATIC_PREVIEW);
	}

	private static void createBeforeOperatorTree(Map workingValues,
			final InnerNode parent) {
		createOption(
				parent,
				workingValues,
				FormatterMessages.WhiteSpaceOptions_assignment_operator,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_ASSIGNMENT_OPERATOR,
				OPERATOR_PREVIEW);
		createOption(
				parent,
				workingValues,
				FormatterMessages.WhiteSpaceOptions_unary_operator,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_UNARY_OPERATOR,
				OPERATOR_PREVIEW);
		createOption(
				parent,
				workingValues,
				FormatterMessages.WhiteSpaceOptions_binary_operator,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_BINARY_OPERATOR,
				OPERATOR_PREVIEW);
		createOption(
				parent,
				workingValues,
				FormatterMessages.WhiteSpaceOptions_prefix_operator,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_PREFIX_OPERATOR,
				OPERATOR_PREVIEW);
		createOption(
				parent,
				workingValues,
				FormatterMessages.WhiteSpaceOptions_postfix_operator,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_POSTFIX_OPERATOR,
				OPERATOR_PREVIEW);
	}

	private static void createBeforeClosingBracketTree(Map workingValues,
			final InnerNode parent) {
		createOption(
				parent,
				workingValues,
				FormatterMessages.WhiteSpaceOptions_array_element_access,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_CLOSING_BRACKET_IN_ARRAY_REFERENCE,
				ARRAY_ACCESS_PREVIEW);
	}

	private static void createBeforeOpenBracketTree(Map workingValues,
			final InnerNode parent) {
		createOption(
				parent,
				workingValues,
				FormatterMessages.WhiteSpaceOptions_array_element_access,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_OPENING_BRACKET_IN_ARRAY_REFERENCE,
				ARRAY_ACCESS_PREVIEW);
	}

	private static void createBeforeOpenBraceTree(Map workingValues,
			final InnerNode parent) {

		createOption(
				parent,
				workingValues,
				FormatterMessages.WhiteSpaceOptions_class_decl,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_OPENING_BRACE_IN_TYPE_DECLARATION,
				CLASS_DECL_PREVIEW);
		createOption(
				parent,
				workingValues,
				FormatterMessages.WhiteSpaceOptions_method_decl,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_OPENING_BRACE_IN_METHOD_DECLARATION,
				METHOD_DECL_PREVIEW);
		createOption(
				parent,
				workingValues,
				FormatterMessages.WhiteSpaceOptions_block,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_OPENING_BRACE_IN_BLOCK,
				BLOCK_PREVIEW);
		createOption(
				parent,
				workingValues,
				FormatterMessages.WhiteSpaceOptions_switch,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_OPENING_BRACE_IN_SWITCH,
				SWITCH_PREVIEW);
	}

	private static void createBeforeClosingParenTree(Map workingValues,
			final InnerNode parent) {

		createOption(
				parent,
				workingValues,
				FormatterMessages.WhiteSpaceOptions_catch,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_CLOSING_PAREN_IN_CATCH,
				CATCH_PREVIEW);
		createOption(
				parent,
				workingValues,
				FormatterMessages.WhiteSpaceOptions_for,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_CLOSING_PAREN_IN_FOR,
				FOR_PREVIEW);
		createOption(
				parent,
				workingValues,
				FormatterMessages.WhiteSpaceOptions_foreach,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_CLOSING_PAREN_IN_FOREACH,
				FOREACH_PREVIEW);
		createOption(
				parent,
				workingValues,
				FormatterMessages.WhiteSpaceOptions_list,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_CLOSING_PAREN_IN_LIST,
				ARRAY_CREATION_PREVIEW);
		createOption(
				parent,
				workingValues,
				FormatterMessages.WhiteSpaceOptions_if,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_CLOSING_PAREN_IN_IF,
				IF_PREVIEW);
		createOption(
				parent,
				workingValues,
				FormatterMessages.WhiteSpaceOptions_switch,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_CLOSING_PAREN_IN_SWITCH,
				SWITCH_PREVIEW);
		createOption(
				parent,
				workingValues,
				FormatterMessages.WhiteSpaceOptions_while,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_CLOSING_PAREN_IN_WHILE,
				WHILE_PREVIEW);
		createOption(
				parent,
				workingValues,
				FormatterMessages.WhiteSpaceOptions_type_cast,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_CLOSING_PAREN_IN_CAST,
				CAST_PREVIEW);
		createOption(
				parent,
				workingValues,
				FormatterMessages.WhiteSpaceOptions_method_decl,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_CLOSING_PAREN_IN_METHOD_DECLARATION,
				METHOD_DECL_PREVIEW);
		createOption(
				parent,
				workingValues,
				FormatterMessages.WhiteSpaceOptions_method_call,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_CLOSING_PAREN_IN_METHOD_INVOCATION,
				METHOD_CALL_PREVIEW);
		createOption(
				parent,
				workingValues,
				FormatterMessages.WhiteSpaceOptions_paren_expr,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_CLOSING_PAREN_IN_PARENTHESIZED_EXPRESSION,
				PARENTHESIS_EXPRESSION_PREVIEW);
	}

	private static void createBeforeOpenParenTree(Map workingValues,
			final InnerNode parent) {

		createOption(
				parent,
				workingValues,
				FormatterMessages.WhiteSpaceOptions_catch,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_OPENING_PAREN_IN_CATCH,
				CATCH_PREVIEW);
		createOption(
				parent,
				workingValues,
				FormatterMessages.WhiteSpaceOptions_for,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_OPENING_PAREN_IN_FOR,
				FOR_PREVIEW);
		createOption(
				parent,
				workingValues,
				FormatterMessages.WhiteSpaceOptions_foreach,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_OPENING_PAREN_IN_FOREACH,
				FOREACH_PREVIEW);
		createOption(
				parent,
				workingValues,
				FormatterMessages.WhiteSpaceOptions_list,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_OPENING_PAREN_IN_LIST,
				ARRAY_CREATION_PREVIEW);
		createOption(
				parent,
				workingValues,
				FormatterMessages.WhiteSpaceOptions_if,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_OPENING_PAREN_IN_IF,
				IF_PREVIEW);
		createOption(
				parent,
				workingValues,
				FormatterMessages.WhiteSpaceOptions_switch,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_OPENING_PAREN_IN_SWITCH,
				SWITCH_PREVIEW);
		createOption(
				parent,
				workingValues,
				FormatterMessages.WhiteSpaceOptions_while,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_OPENING_PAREN_IN_WHILE,
				WHILE_PREVIEW);
		createOption(
				parent,
				workingValues,
				FormatterMessages.WhiteSpaceOptions_method_decl,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_OPENING_PAREN_IN_METHOD_DECLARATION,
				METHOD_DECL_PREVIEW);
		createOption(
				parent,
				workingValues,
				FormatterMessages.WhiteSpaceOptions_method_call,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_OPENING_PAREN_IN_METHOD_INVOCATION,
				METHOD_CALL_PREVIEW);
	}

	private static void createAfterQuestionTree(Map workingValues,
			final InnerNode parent) {
		createOption(
				parent,
				workingValues,
				FormatterMessages.WhiteSpaceOptions_conditional,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_QUESTION_IN_CONDITIONAL,
				CONDITIONAL_PREVIEW);
	}

	private static void createAfterColoncolonTree(Map workingValues,
			InnerNode parent) {
		createOption(
				parent,
				workingValues,
				FormatterMessages.WhiteSpaceOptions_field_access,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_COLONCOLON_IN_FIELD_ACCESS,
				FIELD_ACCESS_PREVIEW);
		createOption(
				parent,
				workingValues,
				FormatterMessages.WhiteSpaceOptions_method_call,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_COLONCOLON_IN_METHOD_INVOCATION,
				METHOD_CALL_PREVIEW);
	}

	private static void createBeforeColoncolonTree(Map workingValues,
			InnerNode parent) {
		createOption(
				parent,
				workingValues,
				FormatterMessages.WhiteSpaceOptions_field_access,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_COLONCOLON_IN_FIELD_ACCESS,
				FIELD_ACCESS_PREVIEW);
		createOption(
				parent,
				workingValues,
				FormatterMessages.WhiteSpaceOptions_method_call,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_COLONCOLON_IN_METHOD_INVOCATION,
				METHOD_CALL_PREVIEW);
	}

	private static void createAfterArrowTree(Map workingValues, InnerNode parent) {
		createOption(
				parent,
				workingValues,
				FormatterMessages.WhiteSpaceOptions_foreach,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_ARROW_IN_FOREACH,
				FOREACH_PREVIEW);
		createOption(
				parent,
				workingValues,
				FormatterMessages.WhiteSpaceOptions_method_call,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_ARROW_IN_METHOD_INVOCATION,
				METHOD_CALL_PREVIEW);
		createOption(
				parent,
				workingValues,
				FormatterMessages.WhiteSpaceOptions_field_access,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_ARROW_IN_FIELD_ACCESS,
				FIELD_ACCESS_PREVIEW);
		createOption(
				parent,
				workingValues,
				FormatterMessages.WhiteSpaceOptions_array_decl,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_ARROW_IN_ARRAY_CREATION,
				ARRAY_CREATION_PREVIEW);
	}

	private static void createBeforeArrowTree(Map workingValues,
			InnerNode parent) {
		createOption(
				parent,
				workingValues,
				FormatterMessages.WhiteSpaceOptions_foreach,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_ARROW_IN_FOREACH,
				FOREACH_PREVIEW);
		createOption(
				parent,
				workingValues,
				FormatterMessages.WhiteSpaceOptions_method_call,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_ARROW_IN_METHOD_INVOCATION,
				METHOD_CALL_PREVIEW);
		createOption(
				parent,
				workingValues,
				FormatterMessages.WhiteSpaceOptions_field_access,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_ARROW_IN_FIELD_ACCESS,
				FIELD_ACCESS_PREVIEW);
		createOption(
				parent,
				workingValues,
				FormatterMessages.WhiteSpaceOptions_array_decl,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_ARROW_IN_ARRAY_CREATION,
				ARRAY_CREATION_PREVIEW);
	}

	private static void createAfterSemicolonTree(Map workingValues,
			final InnerNode parent) {
		createOption(
				parent,
				workingValues,
				FormatterMessages.WhiteSpaceOptions_for,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_SEMICOLON_IN_FOR,
				FOR_PREVIEW);
	}

	private static void createAfterColonTree(Map workingValues,
			final InnerNode parent) {
		createOption(
				parent,
				workingValues,
				FormatterMessages.WhiteSpaceOptions_conditional,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_COLON_IN_CONDITIONAL,
				CONDITIONAL_PREVIEW);
	}

	private static void createAfterCommaTree(Map workingValues,
			final InnerNode parent) {
		createOption(
				parent,
				workingValues,
				FormatterMessages.WhiteSpaceOptions_for,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_COMMA_IN_FOR,
				FOR_PREVIEW);
		createOption(
				parent,
				workingValues,
				FormatterMessages.WhiteSpaceOptions_method_call,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_COMMA_IN_METHOD_INVOCATION_ARGUMENTS,
				METHOD_CALL_PREVIEW);
		createOption(
				parent,
				workingValues,
				FormatterMessages.WhiteSpaceOptions_method_decl,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_COMMA_IN_METHOD_DECLARATION_PARAMETERS,
				METHOD_DECL_PREVIEW);
		createOption(
				parent,
				workingValues,
				FormatterMessages.WhiteSpaceOptions_fields,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_COMMA_IN_MULTIPLE_FIELD_DECLARATIONS,
				MULT_FIELD_PREVIEW);
		createOption(
				parent,
				workingValues,
				FormatterMessages.WhiteSpaceOptions_constants,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_COMMA_IN_MULTIPLE_CONSTANT_DECLARATIONS,
				MULT_FIELD_PREVIEW);
		createOption(
				parent,
				workingValues,
				FormatterMessages.WhiteSpaceOptions_implements_clause,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_COMMA_IN_SUPERINTERFACES,
				CLASS_DECL_PREVIEW);
		createOption(
				parent,
				workingValues,
				FormatterMessages.WhiteSpaceOptions_array_decl,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_COMMA_IN_ARRAY_CREATION,
				ARRAY_CREATION_PREVIEW);
		createOption(
				parent,
				workingValues,
				FormatterMessages.WhiteSpaceOptions_list,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_COMMA_IN_LIST,
				ARRAY_CREATION_PREVIEW);
		createOption(
				parent,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_echo,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_COMMA_IN_ECHO,
				ECHO_PREVIEW);
		createOption(
				parent,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_global,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_COMMA_IN_GLOBAL,
				GLOBAL_PREVIEW);
		createOption(
				parent,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_static,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_COMMA_IN_STATIC,
				STATIC_PREVIEW);
	}

	private static void createAfterOperatorTree(Map workingValues,
			final InnerNode parent) {

		createOption(
				parent,
				workingValues,
				FormatterMessages.WhiteSpaceOptions_assignment_operator,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_ASSIGNMENT_OPERATOR,
				OPERATOR_PREVIEW);
		createOption(
				parent,
				workingValues,
				FormatterMessages.WhiteSpaceOptions_unary_operator,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_UNARY_OPERATOR,
				OPERATOR_PREVIEW);
		createOption(
				parent,
				workingValues,
				FormatterMessages.WhiteSpaceOptions_binary_operator,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_BINARY_OPERATOR,
				OPERATOR_PREVIEW);
		createOption(
				parent,
				workingValues,
				FormatterMessages.WhiteSpaceOptions_prefix_operator,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_PREFIX_OPERATOR,
				OPERATOR_PREVIEW);
		createOption(
				parent,
				workingValues,
				FormatterMessages.WhiteSpaceOptions_postfix_operator,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_POSTFIX_OPERATOR,
				OPERATOR_PREVIEW);
	}

	private static void createAfterOpenBracketTree(Map workingValues,
			final InnerNode parent) {
		createOption(
				parent,
				workingValues,
				FormatterMessages.WhiteSpaceOptions_array_element_access,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_OPENING_BRACKET_IN_ARRAY_REFERENCE,
				ARRAY_ACCESS_PREVIEW);
	}

	private static void createAfterCloseBraceTree(Map workingValues,
			final InnerNode parent) {
		createOption(
				parent,
				workingValues,
				FormatterMessages.WhiteSpaceOptions_block,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_CLOSING_BRACE_IN_BLOCK,
				BLOCK_PREVIEW);
	}

	private static void createAfterCloseParenTree(Map workingValues,
			final InnerNode parent) {

		createOption(
				parent,
				workingValues,
				FormatterMessages.WhiteSpaceOptions_type_cast,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_CLOSING_PAREN_IN_CAST,
				CAST_PREVIEW);
	}

	private static void createAfterOpenParenTree(Map workingValues,
			final InnerNode parent) {
		createOption(
				parent,
				workingValues,
				FormatterMessages.WhiteSpaceOptions_catch,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_OPENING_PAREN_IN_CATCH,
				CATCH_PREVIEW);
		createOption(
				parent,
				workingValues,
				FormatterMessages.WhiteSpaceOptions_for,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_OPENING_PAREN_IN_FOR,
				FOR_PREVIEW);
		createOption(
				parent,
				workingValues,
				FormatterMessages.WhiteSpaceOptions_foreach,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_OPENING_PAREN_IN_FOREACH,
				FOREACH_PREVIEW);
		createOption(
				parent,
				workingValues,
				FormatterMessages.WhiteSpaceOptions_list,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_OPENING_PAREN_IN_LIST,
				ARRAY_CREATION_PREVIEW);
		createOption(
				parent,
				workingValues,
				FormatterMessages.WhiteSpaceOptions_if,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_OPENING_PAREN_IN_IF,
				IF_PREVIEW);
		createOption(
				parent,
				workingValues,
				FormatterMessages.WhiteSpaceOptions_switch,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_OPENING_PAREN_IN_SWITCH,
				SWITCH_PREVIEW);
		createOption(
				parent,
				workingValues,
				FormatterMessages.WhiteSpaceOptions_while,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_OPENING_PAREN_IN_WHILE,
				WHILE_PREVIEW);
		createOption(
				parent,
				workingValues,
				FormatterMessages.WhiteSpaceOptions_method_decl,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_OPENING_PAREN_IN_METHOD_DECLARATION,
				METHOD_DECL_PREVIEW);
		createOption(
				parent,
				workingValues,
				FormatterMessages.WhiteSpaceOptions_type_cast,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_OPENING_PAREN_IN_CAST,
				CAST_PREVIEW);
		createOption(
				parent,
				workingValues,
				FormatterMessages.WhiteSpaceOptions_method_call,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_OPENING_PAREN_IN_METHOD_INVOCATION,
				METHOD_CALL_PREVIEW);
		createOption(
				parent,
				workingValues,
				FormatterMessages.WhiteSpaceOptions_paren_expr,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_OPENING_PAREN_IN_PARENTHESIZED_EXPRESSION,
				PARENTHESIS_EXPRESSION_PREVIEW);
	}

	private static void createBetweenEmptyParenTree(Map workingValues,
			final InnerNode parent) {
		createOption(
				parent,
				workingValues,
				FormatterMessages.WhiteSpaceOptions_method_decl,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BETWEEN_EMPTY_PARENS_IN_METHOD_DECLARATION,
				METHOD_DECL_PREVIEW);
		createOption(
				parent,
				workingValues,
				FormatterMessages.WhiteSpaceOptions_method_call,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BETWEEN_EMPTY_PARENS_IN_METHOD_INVOCATION,
				METHOD_CALL_PREVIEW);
	}

	private static void createBetweenEmptyBracketsTree(Map workingValues,
			final InnerNode parent) {
		createOption(
				parent,
				workingValues,
				FormatterMessages.WhiteSpaceOptions_array_element_access,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BETWEEN_EMPTY_BRACKETS_IN_ARRAY_TYPE_REFERENCE,
				ARRAY_ACCESS_PREVIEW);
	}

	// syntax element tree

	private static InnerNode createClassTree(Map workingValues, InnerNode parent) {
		final InnerNode root = new InnerNode(parent, workingValues,
				FormatterMessages.WhiteSpaceTabPage_classes);
		createOption(
				root,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_classes_before_opening_brace_of_a_class,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_OPENING_BRACE_IN_TYPE_DECLARATION,
				CLASS_DECL_PREVIEW);
		createOption(
				root,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_classes_before_comma_implements,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_COMMA_IN_SUPERINTERFACES,
				CLASS_DECL_PREVIEW);
		createOption(
				root,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_classes_after_comma_implements,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_COMMA_IN_SUPERINTERFACES,
				CLASS_DECL_PREVIEW);

		return root;
	}

	private static InnerNode createAssignmentTree(Map workingValues,
			InnerNode parent) {
		final InnerNode root = new InnerNode(parent, workingValues,
				FormatterMessages.WhiteSpaceTabPage_assignments);
		createOption(
				root,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_assignments_before_assignment_operator,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_ASSIGNMENT_OPERATOR,
				OPERATOR_PREVIEW);
		createOption(
				root,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_assignments_after_assignment_operator,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_ASSIGNMENT_OPERATOR,
				OPERATOR_PREVIEW);
		return root;
	}

	private static InnerNode createOperatorTree(Map workingValues,
			InnerNode parent) {
		final InnerNode root = new InnerNode(parent, workingValues,
				FormatterMessages.WhiteSpaceTabPage_operators);

		createOption(
				root,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_operators_before_binary_operators,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_BINARY_OPERATOR,
				OPERATOR_PREVIEW);
		createOption(
				root,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_operators_after_binary_operators,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_BINARY_OPERATOR,
				OPERATOR_PREVIEW);
		createOption(
				root,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_operators_before_unary_operators,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_UNARY_OPERATOR,
				OPERATOR_PREVIEW);
		createOption(
				root,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_operators_after_unary_operators,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_UNARY_OPERATOR,
				OPERATOR_PREVIEW);
		createOption(
				root,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_operators_before_prefix_operators,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_PREFIX_OPERATOR,
				OPERATOR_PREVIEW);
		createOption(
				root,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_operators_after_prefix_operators,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_PREFIX_OPERATOR,
				OPERATOR_PREVIEW);
		createOption(
				root,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_operators_before_postfix_operators,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_POSTFIX_OPERATOR,
				OPERATOR_PREVIEW);
		createOption(
				root,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_operators_after_postfix_operators,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_POSTFIX_OPERATOR,
				OPERATOR_PREVIEW);
		return root;
	}

	private static InnerNode createMethodDeclTree(Map workingValues,
			InnerNode parent) {
		final InnerNode root = new InnerNode(parent, workingValues,
				FormatterMessages.WhiteSpaceTabPage_methods);

		createOption(
				root,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_before_opening_paren,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_OPENING_PAREN_IN_METHOD_DECLARATION,
				METHOD_DECL_PREVIEW);
		createOption(
				root,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_after_opening_paren,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_OPENING_PAREN_IN_METHOD_DECLARATION,
				METHOD_DECL_PREVIEW);
		createOption(
				root,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_before_closing_paren,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_CLOSING_PAREN_IN_METHOD_DECLARATION,
				METHOD_DECL_PREVIEW);
		createOption(
				root,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_between_empty_parens,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BETWEEN_EMPTY_PARENS_IN_METHOD_DECLARATION,
				METHOD_DECL_PREVIEW);
		createOption(
				root,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_before_opening_brace,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_OPENING_BRACE_IN_METHOD_DECLARATION,
				METHOD_DECL_PREVIEW);

		createOption(
				root,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_before_comma_in_params,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_COMMA_IN_METHOD_DECLARATION_PARAMETERS,
				METHOD_DECL_PREVIEW);
		createOption(
				root,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_after_comma_in_params,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_COMMA_IN_METHOD_DECLARATION_PARAMETERS,
				METHOD_DECL_PREVIEW);

		return root;
	}

	private static InnerNode createFieldTree(Map workingValues, InnerNode parent) {
		final InnerNode root = new InnerNode(parent, workingValues,
				FormatterMessages.WhiteSpaceTabPage_fields);

		createOption(
				root,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_fields_before_comma,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_COMMA_IN_MULTIPLE_FIELD_DECLARATIONS,
				MULT_FIELD_PREVIEW);
		createOption(
				root,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_fields_after_comma,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_COMMA_IN_MULTIPLE_FIELD_DECLARATIONS,
				MULT_FIELD_PREVIEW);
		createOption(
				root,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_constant_before_comma,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_COMMA_IN_MULTIPLE_CONSTANT_DECLARATIONS,
				MULT_FIELD_PREVIEW);
		createOption(
				root,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_constant_after_comma,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_COMMA_IN_MULTIPLE_CONSTANT_DECLARATIONS,
				MULT_FIELD_PREVIEW);
		return root;
	}

	private static InnerNode createArrayCreationTree(Map workingValues,
			InnerNode parent) {
		final InnerNode root = new InnerNode(parent, workingValues,
				FormatterMessages.WhiteSpaceTabPage_arraycreation);

		createOption(
				root,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_before_opening_paren_in_array,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_OPENING_PAREN_IN_ARRAY_CREATION,
				ARRAY_CREATION_PREVIEW);
		createOption(
				root,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_after_opening_paren_in_array,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_OPENING_PAREN_IN_ARRAY_CREATION,
				ARRAY_CREATION_PREVIEW);
		createOption(
				root,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_before_closing_paren_in_array,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_CLOSING_PAREN_IN_ARRAY_CREATION,
				ARRAY_CREATION_PREVIEW);
		createOption(
				root,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_before_comma_in_array,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_COMMA_IN_ARRAY_CREATION,
				ARRAY_CREATION_PREVIEW);
		createOption(
				root,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_after_comma_in_array,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_COMMA_IN_ARRAY_CREATION,
				ARRAY_CREATION_PREVIEW);
		createOption(
				root,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_before_arrow_in_array,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_ARROW_IN_ARRAY_CREATION,
				ARRAY_CREATION_PREVIEW);
		createOption(
				root,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_after_arrow_in_array,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_ARROW_IN_ARRAY_CREATION,
				ARRAY_CREATION_PREVIEW);

		createOption(
				root,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_before_opening_paren_in_list,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_OPENING_PAREN_IN_LIST,
				ARRAY_CREATION_PREVIEW);
		createOption(
				root,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_after_opening_paren_in_list,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_OPENING_PAREN_IN_LIST,
				ARRAY_CREATION_PREVIEW);
		createOption(
				root,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_before_closing_paren_in_list,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_CLOSING_PAREN_IN_LIST,
				ARRAY_CREATION_PREVIEW);
		createOption(
				root,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_before_comma_in_list,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_COMMA_IN_LIST,
				ARRAY_CREATION_PREVIEW);
		createOption(
				root,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_after_comma_in_list,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_COMMA_IN_LIST,
				ARRAY_CREATION_PREVIEW);

		return root;
	}

	private static InnerNode createArrayElementAccessTree(Map workingValues,
			InnerNode parent) {
		final InnerNode root = new InnerNode(parent, workingValues,
				FormatterMessages.WhiteSpaceTabPage_arrayelem);

		createOption(
				root,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_before_opening_bracket,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_OPENING_BRACKET_IN_ARRAY_REFERENCE,
				ARRAY_ACCESS_PREVIEW);
		createOption(
				root,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_after_opening_bracket,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_OPENING_BRACKET_IN_ARRAY_REFERENCE,
				ARRAY_ACCESS_PREVIEW);
		createOption(
				root,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_before_closing_bracket,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_CLOSING_BRACKET_IN_ARRAY_REFERENCE,
				ARRAY_ACCESS_PREVIEW);
		createOption(
				root,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_between_empty_brackets,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BETWEEN_EMPTY_BRACKETS_IN_ARRAY_TYPE_REFERENCE,
				ARRAY_ACCESS_PREVIEW);

		return root;
	}

	private static InnerNode createFunctionCallTree(Map workingValues,
			InnerNode parent) {
		final InnerNode root = new InnerNode(parent, workingValues,
				FormatterMessages.WhiteSpaceTabPage_calls);

		createOption(
				root,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_before_opening_paren,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_OPENING_PAREN_IN_METHOD_INVOCATION,
				METHOD_CALL_PREVIEW);
		createOption(
				root,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_after_opening_paren,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_OPENING_PAREN_IN_METHOD_INVOCATION,
				METHOD_CALL_PREVIEW);
		createOption(
				root,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_before_closing_paren,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_CLOSING_PAREN_IN_METHOD_INVOCATION,
				METHOD_CALL_PREVIEW);
		createOption(
				root,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_between_empty_parens,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BETWEEN_EMPTY_PARENS_IN_METHOD_INVOCATION,
				METHOD_CALL_PREVIEW);
		createOption(
				root,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_calls_before_comma_in_method_args,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_COMMA_IN_METHOD_INVOCATION_ARGUMENTS,
				METHOD_CALL_PREVIEW);
		createOption(
				root,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_calls_after_comma_in_method_args,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_COMMA_IN_METHOD_INVOCATION_ARGUMENTS,
				METHOD_CALL_PREVIEW);
		createOption(
				root,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_calls_before_arrow_in_method,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_ARROW_IN_METHOD_INVOCATION,
				METHOD_CALL_PREVIEW);
		createOption(
				root,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_calls_after_arrow_in_method,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_ARROW_IN_METHOD_INVOCATION,
				METHOD_CALL_PREVIEW);
		createOption(
				root,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_calls_before_coloncolon_in_method,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_COLONCOLON_IN_METHOD_INVOCATION,
				METHOD_CALL_PREVIEW);
		createOption(
				root,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_calls_after_coloncolon_in_method,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_COLONCOLON_IN_METHOD_INVOCATION,
				METHOD_CALL_PREVIEW);
		return root;
	}

	private static InnerNode createFieldAccessTree(Map workingValues,
			InnerNode parent) {
		final InnerNode root = new InnerNode(parent, workingValues,
				FormatterMessages.WhiteSpaceTabPage_fieldaccess);

		createOption(
				root,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_fieldaccess_before_arrow_in_field_acecss,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_ARROW_IN_FIELD_ACCESS,
				FIELD_ACCESS_PREVIEW);
		createOption(
				root,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_fieldaccess_after_arrow_in_field_acecss,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_ARROW_IN_FIELD_ACCESS,
				FIELD_ACCESS_PREVIEW);
		createOption(
				root,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_fieldaccess_before_coloncolon_in_field_acecss,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_COLONCOLON_IN_FIELD_ACCESS,
				FIELD_ACCESS_PREVIEW);
		createOption(
				root,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_fieldaccess_after_coloncolon_in_field_acecss,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_COLONCOLON_IN_FIELD_ACCESS,
				FIELD_ACCESS_PREVIEW);
		return root;
	}

	private static InnerNode createBlockTree(Map workingValues, InnerNode parent) {
		final InnerNode root = new InnerNode(parent, workingValues,
				FormatterMessages.WhiteSpaceTabPage_blocks);

		createOption(
				root,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_before_opening_brace,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_OPENING_BRACE_IN_BLOCK,
				BLOCK_PREVIEW);
		createOption(
				root,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_after_closing_brace,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_CLOSING_BRACE_IN_BLOCK,
				CATCH_PREVIEW);
		return root;
	}

	private static InnerNode createSwitchStatementTree(Map workingValues,
			InnerNode parent) {
		final InnerNode root = new InnerNode(parent, workingValues,
				FormatterMessages.WhiteSpaceTabPage_switch);

		createOption(
				root,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_switch_before_case_colon,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_COLON_IN_CASE,
				SWITCH_PREVIEW);
		createOption(
				root,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_switch_before_default_colon,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_COLON_IN_DEFAULT,
				SWITCH_PREVIEW);
		createOption(
				root,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_before_opening_brace,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_OPENING_BRACE_IN_SWITCH,
				SWITCH_PREVIEW);
		createOption(
				root,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_before_opening_paren,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_OPENING_PAREN_IN_SWITCH,
				SWITCH_PREVIEW);
		createOption(
				root,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_after_opening_paren,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_OPENING_PAREN_IN_SWITCH,
				SWITCH_PREVIEW);
		createOption(
				root,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_before_closing_paren,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_CLOSING_PAREN_IN_SWITCH,
				SWITCH_PREVIEW);
		return root;
	}

	private static InnerNode createDoWhileTree(Map workingValues,
			InnerNode parent) {
		final InnerNode root = new InnerNode(parent, workingValues,
				FormatterMessages.WhiteSpaceTabPage_do);

		createOption(
				root,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_before_opening_paren,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_OPENING_PAREN_IN_WHILE,
				WHILE_PREVIEW);
		createOption(
				root,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_after_opening_paren,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_OPENING_PAREN_IN_WHILE,
				WHILE_PREVIEW);
		createOption(
				root,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_before_closing_paren,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_CLOSING_PAREN_IN_WHILE,
				WHILE_PREVIEW);

		return root;
	}

	private static InnerNode createTryStatementTree(Map workingValues,
			InnerNode parent) {
		final InnerNode root = new InnerNode(parent, workingValues,
				FormatterMessages.WhiteSpaceTabPage_try);

		createOption(
				root,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_before_opening_paren,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_OPENING_PAREN_IN_CATCH,
				CATCH_PREVIEW);
		createOption(
				root,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_after_opening_paren,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_OPENING_PAREN_IN_CATCH,
				CATCH_PREVIEW);
		createOption(
				root,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_before_closing_paren,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_CLOSING_PAREN_IN_CATCH,
				CATCH_PREVIEW);
		return root;
	}

	private static InnerNode createStaticTree(Map workingValues,
			InnerNode parent) {
		final InnerNode root = new InnerNode(parent, workingValues,
				FormatterMessages.WhiteSpaceTabPage_static);

		createOption(
				root,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_before_comma,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_COMMA_IN_STATIC,
				STATIC_PREVIEW);
		createOption(
				root,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_after_comma,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_COMMA_IN_STATIC,
				STATIC_PREVIEW);
		return root;
	}

	private static InnerNode createGlobalTree(Map workingValues,
			InnerNode parent) {
		final InnerNode root = new InnerNode(parent, workingValues,
				FormatterMessages.WhiteSpaceTabPage_global);

		createOption(
				root,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_before_comma,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_COMMA_IN_GLOBAL,
				GLOBAL_PREVIEW);
		createOption(
				root,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_after_comma,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_COMMA_IN_GLOBAL,
				GLOBAL_PREVIEW);
		return root;
	}

	private static InnerNode createEchoTree(Map workingValues, InnerNode parent) {
		final InnerNode root = new InnerNode(parent, workingValues,
				FormatterMessages.WhiteSpaceTabPage_echo);

		createOption(
				root,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_before_comma,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_COMMA_IN_ECHO,
				ECHO_PREVIEW);
		createOption(
				root,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_after_comma,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_COMMA_IN_ECHO,
				ECHO_PREVIEW);
		return root;
	}

	private static InnerNode createIfStatementTree(Map workingValues,
			InnerNode parent) {
		final InnerNode root = new InnerNode(parent, workingValues,
				FormatterMessages.WhiteSpaceTabPage_if);

		createOption(
				root,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_before_opening_paren,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_OPENING_PAREN_IN_IF,
				IF_PREVIEW);
		createOption(
				root,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_after_opening_paren,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_OPENING_PAREN_IN_IF,
				IF_PREVIEW);
		createOption(
				root,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_before_closing_paren,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_CLOSING_PAREN_IN_IF,
				IF_PREVIEW);
		return root;
	}

	private static InnerNode createForStatementTree(Map workingValues,
			InnerNode parent) {
		final InnerNode root = new InnerNode(parent, workingValues,
				FormatterMessages.WhiteSpaceTabPage_for);

		createOption(
				root,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_before_opening_paren,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_OPENING_PAREN_IN_FOR,
				FOR_PREVIEW);
		createOption(
				root,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_after_opening_paren,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_OPENING_PAREN_IN_FOR,
				FOR_PREVIEW);
		createOption(
				root,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_before_closing_paren,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_CLOSING_PAREN_IN_FOR,
				FOR_PREVIEW);
		createOption(
				root,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_for_before_comma,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_COMMA_IN_FOR,
				FOR_PREVIEW);
		createOption(
				root,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_for_after_comma,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_COMMA_IN_FOR,
				FOR_PREVIEW);
		createOption(
				root,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_before_semicolon,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_SEMICOLON_IN_FOR,
				FOR_PREVIEW);
		createOption(
				root,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_after_semicolon,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_SEMICOLON_IN_FOR,
				FOR_PREVIEW);

		return root;
	}

	private static InnerNode createForeachStatementTree(Map workingValues,
			InnerNode parent) {
		final InnerNode root = new InnerNode(parent, workingValues,
				FormatterMessages.WhiteSpaceTabPage_foreach);

		createOption(
				root,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_before_opening_paren,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_OPENING_PAREN_IN_FOREACH,
				FOREACH_PREVIEW);
		createOption(
				root,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_after_opening_paren,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_OPENING_PAREN_IN_FOREACH,
				FOREACH_PREVIEW);
		createOption(
				root,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_before_closing_paren,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_CLOSING_PAREN_IN_FOREACH,
				FOREACH_PREVIEW);
		createOption(
				root,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_foreach_before_arrow,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_ARROW_IN_FOREACH,
				FOREACH_PREVIEW);
		createOption(
				root,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_foreach_after_arrow,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_ARROW_IN_FOREACH,
				FOREACH_PREVIEW);

		return root;
	}

	private static InnerNode createConditionalTree(Map workingValues,
			InnerNode parent) {
		final InnerNode root = new InnerNode(parent, workingValues,
				FormatterMessages.WhiteSpaceTabPage_conditionals);

		createOption(
				root,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_before_question,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_QUESTION_IN_CONDITIONAL,
				CONDITIONAL_PREVIEW);
		createOption(
				root,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_after_question,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_QUESTION_IN_CONDITIONAL,
				CONDITIONAL_PREVIEW);
		createOption(
				root,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_before_colon,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_COLON_IN_CONDITIONAL,
				CONDITIONAL_PREVIEW);
		createOption(
				root,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_after_colon,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_COLON_IN_CONDITIONAL,
				CONDITIONAL_PREVIEW);
		return root;
	}

	private static InnerNode createParenthesisExpressionTree(Map workingValues,
			InnerNode parent) {
		final InnerNode root = new InnerNode(parent, workingValues,
				FormatterMessages.WhiteSpaceTabPage_parenexpr);

		createOption(
				root,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_after_opening_paren,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_OPENING_PAREN_IN_PARENTHESIZED_EXPRESSION,
				PARENTHESIS_EXPRESSION_PREVIEW);
		createOption(
				root,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_before_closing_paren,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_CLOSING_PAREN_IN_PARENTHESIZED_EXPRESSION,
				PARENTHESIS_EXPRESSION_PREVIEW);
		return root;
	}

	private static InnerNode createTypecastTree(Map workingValues,
			InnerNode parent) {
		final InnerNode root = new InnerNode(parent, workingValues,
				FormatterMessages.WhiteSpaceTabPage_typecasts);

		createOption(
				root,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_after_opening_paren,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_OPENING_PAREN_IN_CAST,
				CAST_PREVIEW);
		createOption(
				root,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_before_closing_paren,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_CLOSING_PAREN_IN_CAST,
				CAST_PREVIEW);
		createOption(
				root,
				workingValues,
				FormatterMessages.WhiteSpaceTabPage_after_closing_paren,
				CodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_CLOSING_PAREN_IN_CAST,
				CAST_PREVIEW);
		return root;
	}

	private static InnerNode createChild(InnerNode root, Map workingValues,
			String message) {
		return new InnerNode(root, workingValues, message);
	}

	private static OptionNode createOption(InnerNode root, Map workingValues,
			String message, String key, String snippet) {
		return new OptionNode(root, workingValues, message, key, snippet);
	}

	public static void makeIndexForNodes(List tree, List flatList) {
		for (final Iterator iter = tree.iterator(); iter.hasNext();) {
			final Node node = (Node) iter.next();
			node.index = flatList.size();
			flatList.add(node);
			makeIndexForNodes(node.getChildren(), flatList);
		}
	}
}
