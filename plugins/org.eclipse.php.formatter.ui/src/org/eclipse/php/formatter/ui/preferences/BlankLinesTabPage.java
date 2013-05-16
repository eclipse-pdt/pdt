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
import org.eclipse.php.formatter.ui.FormatterMessages;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

public class BlankLinesTabPage extends ModifyDialogTabPage {

	private final String PREVIEW = "<?php\n"
			+ "namespace test;\n"
			+ "use test1;\n"
			+ "use test2;\n"
			+ "use test3;\n"
			+ createPreviewHeader(FormatterMessages.BlankLinesTabPage_preview_header)
			+ "class Example {" + //$NON-NLS-1$
			"  const CONST2 = 3;" + "  var $theInt= 1;" + //$NON-NLS-1$
			"  public function foo($a, $b) {" + //$NON-NLS-1$
			"    switch($a) {" + //$NON-NLS-1$
			"    case 0: " + //$NON-NLS-1$
			"      $Other->doFoo();" + //$NON-NLS-1$
			"      break;" + //$NON-NLS-1$
			"    default:" + //$NON-NLS-1$
			"      $Other->doBaz();" + //$NON-NLS-1$
			"    }" + //$NON-NLS-1$
			"  }" + //$NON-NLS-1$
			"  function bar($v) {" + //$NON-NLS-1$
			"    for ($i= 0; $i < 10; $i++) {" + //$NON-NLS-1$
			"      $v->add($i);" + //$NON-NLS-1$
			"    }" + //$NON-NLS-1$
			"  }" + //$NON-NLS-1$
			"} namespace test1;\n" + "interface MyInterface {}\n?>"; //$NON-NLS-1$

	private final static int MIN_NUMBER_LINES = 0;
	private final static int MAX_NUMBER_LINES = 99;

	private boolean isInitialized = false;

	private NumberPreference namespaceDecl;
	private NumberPreference namespaceDeclAfter;
	// private NumberPreference useStatementsBefore;
	private NumberPreference useStatementsAfter;
	// private NumberPreference useStatementsBetween;
	private NumberPreference namespaceDeclBetween;

	private NumberPreference classDecl;
	private NumberPreference constantDecl;
	private NumberPreference fieldDecl;
	private NumberPreference methodDecl;
	private NumberPreference methodBegin;
	private NumberPreference methodEnd;
	private NumberPreference classEnd;
	private NumberPreference emptyLinesToPreserve;

	private CodeFormatterPreview fPreview;

	/**
	 * Create a new BlankLinesTabPage.
	 * 
	 * @param modifyDialog
	 *            The main configuration dialog
	 * 
	 * @param workingValues
	 *            The values wherein the options are stored.
	 */
	public BlankLinesTabPage(ModifyDialog modifyDialog,
			CodeFormatterPreferences codeFormatterPreferences) {
		super(modifyDialog, codeFormatterPreferences);
	}

	protected void doCreatePreferences(Composite composite, int numColumns) {
		Group group = createGroup(numColumns, composite,
				FormatterMessages.BlankLinesTabPage_namespace_group_title);
		namespaceDecl = createNumberPref(
				group,
				numColumns,
				FormatterMessages.BlankLinesTabPage_namespace_option_before_namespace,
				0, 32);
		namespaceDecl
				.setValue(codeFormatterPreferences.blank_lines_before_namespace);

		namespaceDeclAfter = createNumberPref(
				group,
				numColumns,
				FormatterMessages.BlankLinesTabPage_namespace_option_after_namespace,
				0, 32);
		namespaceDeclAfter
				.setValue(codeFormatterPreferences.blank_lines_after_namespace);

		// useStatementsBefore = createNumberPref(
		// group,
		// numColumns,
		// FormatterMessages.BlankLinesTabPage_namespace_option_before_use_statements,
		// 0, 32);
		// useStatementsBefore
		// .setValue(codeFormatterPreferences.blank_lines_before_use_statements);

		useStatementsAfter = createNumberPref(
				group,
				numColumns,
				FormatterMessages.BlankLinesTabPage_namespace_option_after_use_statements,
				0, 32);
		useStatementsAfter
				.setValue(codeFormatterPreferences.blank_lines_after_use_statements);

		// useStatementsBetween = createNumberPref(
		// group,
		// numColumns,
		// FormatterMessages.BlankLinesTabPage_namespace_option_between_use_statements,
		// 0, 32);
		// useStatementsBetween
		// .setValue(codeFormatterPreferences.blank_lines_between_use_statements);

		namespaceDeclBetween = createNumberPref(
				group,
				numColumns,
				FormatterMessages.BlankLinesTabPage_namespace_option_between_namespaces,
				0, 32);
		namespaceDeclBetween
				.setValue(codeFormatterPreferences.blank_lines_between_namespaces);

		group = createGroup(numColumns, composite,
				FormatterMessages.BlankLinesTabPage_class_group_title);
		classDecl = createNumberPref(
				group,
				numColumns,
				FormatterMessages.BlankLinesTabPage_class_option_before_class_declarations,
				0, 32);
		classDecl
				.setValue(codeFormatterPreferences.blank_line_before_class_declaration);

		constantDecl = createNumberPref(
				group,
				numColumns,
				FormatterMessages.BlankLinesTabPage_class_option_before_constant_decls,
				0, 32);
		constantDecl
				.setValue(codeFormatterPreferences.blank_line_before_constant_declaration);

		fieldDecl = createNumberPref(
				group,
				numColumns,
				FormatterMessages.BlankLinesTabPage_class_option_before_field_decls,
				0, 32);
		fieldDecl
				.setValue(codeFormatterPreferences.blank_line_before_field_declaration);

		methodDecl = createNumberPref(
				group,
				numColumns,
				FormatterMessages.BlankLinesTabPage_class_option_before_method_decls,
				0, 32);
		methodDecl
				.setValue(codeFormatterPreferences.blank_line_before_method_declaration);

		methodBegin = createNumberPref(
				group,
				numColumns,
				FormatterMessages.BlankLinesTabPage_class_option_at_beginning_of_method_body,
				0, 32);
		methodBegin
				.setValue(codeFormatterPreferences.blank_line_at_begin_of_method);

		methodEnd = createNumberPref(
				group,
				numColumns,
				FormatterMessages.BlankLinesTabPage_class_option_at_end_of_method_body,
				0, 32);
		methodEnd
				.setValue(codeFormatterPreferences.blank_line_at_end_of_method);

		classEnd = createNumberPref(
				group,
				numColumns,
				FormatterMessages.BlankLinesTabPage_class_option_at_end_of_class_body,
				0, 32);
		classEnd.setValue(codeFormatterPreferences.blank_line_at_end_of_class);
		group = createGroup(numColumns, composite,
				FormatterMessages.BlankLinesTabPage_blank_lines_group_title);
		emptyLinesToPreserve = createNumberPref(
				group,
				numColumns,
				FormatterMessages.BlankLinesTabPage_blank_lines_option_empty_lines_to_preserve,
				0, 32);
		emptyLinesToPreserve
				.setValue(codeFormatterPreferences.blank_line_preserve_empty_lines);

		isInitialized = true;
	}

	protected void initializePage() {
		fPreview.setPreviewText(PREVIEW);
	}

	/*
	 * A helper method to create a number preference for blank lines.
	 */
	private void createBlankLineTextField(Composite composite, int numColumns,
			String message, String key) {
		createNumberPref(composite, numColumns, message, MIN_NUMBER_LINES,
				MAX_NUMBER_LINES);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jdt.internal.ui.preferences.formatter.ModifyDialogTabPage
	 * #doCreateJavaPreview(org.eclipse.swt.widgets.Composite)
	 */
	protected PhpPreview doCreatePhpPreview(Composite parent) {
		fPreview = new CodeFormatterPreview(codeFormatterPreferences, parent);
		return fPreview;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jdt.internal.ui.preferences.formatter.ModifyDialogTabPage
	 * #doUpdatePreview()
	 */
	protected void doUpdatePreview() {
		if (fPreview != null) {
			fPreview.update();
		}
	}

	protected void updatePreferences() {
		if (isInitialized) {
			codeFormatterPreferences.blank_line_before_class_declaration = classDecl
					.getValue();
			codeFormatterPreferences.blank_line_before_constant_declaration = constantDecl
					.getValue();
			codeFormatterPreferences.blank_line_before_field_declaration = fieldDecl
					.getValue();
			codeFormatterPreferences.blank_line_before_method_declaration = methodDecl
					.getValue();
			codeFormatterPreferences.blank_line_at_begin_of_method = methodBegin
					.getValue();
			codeFormatterPreferences.blank_line_preserve_empty_lines = emptyLinesToPreserve
					.getValue();

			codeFormatterPreferences.blank_line_at_end_of_class = classEnd
					.getValue();
			codeFormatterPreferences.blank_line_at_end_of_method = methodEnd
					.getValue();
			// namespace
			codeFormatterPreferences.blank_lines_before_namespace = namespaceDecl
					.getValue();
			codeFormatterPreferences.blank_lines_after_namespace = namespaceDeclAfter
					.getValue();
			// codeFormatterPreferences.blank_lines_before_use_statements =
			// useStatementsBefore
			// .getValue();
			codeFormatterPreferences.blank_lines_after_use_statements = useStatementsAfter
					.getValue();
			// codeFormatterPreferences.blank_lines_between_use_statements =
			// useStatementsBetween
			// .getValue();
			codeFormatterPreferences.blank_lines_between_namespaces = namespaceDeclBetween
					.getValue();

		}
	}
}
