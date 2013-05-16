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

import java.util.Observable;
import java.util.Observer;

import org.eclipse.php.formatter.core.CodeFormatterPreferences;
import org.eclipse.php.formatter.ui.FormatterMessages;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

public class IndentationTabPage extends ModifyDialogTabPage {

	private final String PREVIEW = "<?php\n" + createPreviewHeader(FormatterMessages.IndentationTabPage_preview_header) + "class Example {" + //$NON-NLS-1$
			"  var $theInt= 1;" + //$NON-NLS-1$
			"  function foo($a, $b) {" + //$NON-NLS-1$
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
			"} \n?>"; //$NON-NLS-1$

	private CodeFormatterPreview fPreview;

	private ComboPreference tabPolicy;
	private NumberPreference indentSize;
	private NumberPreference tabSize;
	private NumberPreference fDefaultIndentArrayInit;
	private CheckboxPreference classIndent;
	private CheckboxPreference methodIndent;
	private CheckboxPreference blockIndent;
	private CheckboxPreference switchIndent;
	private CheckboxPreference caseIndent;
	private CheckboxPreference breakIndent;
	private CheckboxPreference emptylineIndent;

	private boolean isInitialized = false;

	public IndentationTabPage(ModifyDialog modifyDialog,
			CodeFormatterPreferences preferences) {
		super(modifyDialog, preferences);
	}

	protected void doCreatePreferences(Composite composite, int numColumns) {
		// general setting
		final Group generalGroup = createGroup(numColumns, composite,
				FormatterMessages.IndentationTabPage_general_group_title);
		final String[] tabPolicyLabels = new String[] {
				FormatterMessages.IndentationTabPage_general_group_option_tab_policy_SPACE,
				FormatterMessages.IndentationTabPage_general_group_option_tab_policy_TAB };
		tabPolicy = createComboPref(
				generalGroup,
				numColumns,
				FormatterMessages.IndentationTabPage_general_group_option_tab_policy,
				tabPolicyLabels);

		// final CheckboxPreference onlyForLeading =
		// createCheckboxPref(generalGroup, numColumns,
		// CodeFormatterConstants.FORMATTER_USE_TABS_ONLY_FOR_LEADING_INDENTATIONS);
		indentSize = createNumberPref(
				generalGroup,
				numColumns,
				FormatterMessages.IndentationTabPage_general_group_option_indent_size,
				0, 32);
		indentSize.setValue(codeFormatterPreferences.indentationSize);
		// final NumberPreference tabSize = createNumberPref(generalGroup,
		// numColumns, CodeFormatterConstants.FORMATTER_TAB_SIZE, 0, 32);
		tabSize = createNumberPref(
				generalGroup,
				numColumns,
				FormatterMessages.IndentationTabPage_general_group_option_tab_size,
				0, 32);
		tabSize.setValue(codeFormatterPreferences.tabSize);

		fDefaultIndentArrayInit = createNumberPref(
				generalGroup,
				numColumns,
				FormatterMessages.LineWrappingTabPage_width_indent_option_default_indent_array,
				0, 9999);
		fDefaultIndentArrayInit
				.setValue(codeFormatterPreferences.line_wrap_array_init_indentation);

		char indentChar = codeFormatterPreferences.indentationChar;
		if (indentChar == CodeFormatterPreferences.SPACE_CHAR) {
			indentSize.getControl().setEnabled(true);
			tabPolicy.setSelectedItem(tabPolicyLabels[0]);
		} else {
			indentSize.getControl().setEnabled(false);
			tabPolicy.setSelectedItem(tabPolicyLabels[1]);
		}

		// enabled the indentation size checkbox according to the indentation
		// type
		tabPolicy.addObserver(new Observer() {
			public void update(Observable o, Object arg) {
				int index = tabPolicy.getSelectionIndex();
				char indentChar = index == 0 ? CodeFormatterPreferences.SPACE_CHAR
						: CodeFormatterPreferences.TAB_CHAR;
				if (indentChar == CodeFormatterPreferences.SPACE_CHAR
						&& !indentSize.getControl().isEnabled()) {
					indentSize.getControl().setEnabled(true);
				} else if (indentChar == CodeFormatterPreferences.TAB_CHAR
						&& indentSize.getControl().isEnabled()) {
					indentSize.getControl().setEnabled(false);
					indentSize.setValue(1);
				}

				// update the preview and preferences value
				updatePreferences();
				doUpdatePreview();
				notifyValuesModified();
			}
		});

		// alignment group
		// final Group typeMemberGroup = createGroup(numColumns, composite,
		// FormatterMessages.IndentationTabPage_field_alignment_group_title);
		// createCheckboxPref(typeMemberGroup, numColumns,
		// FormatterMessages.IndentationTabPage_field_alignment_group_align_fields_in_columns);

		// indent group
		final Group classGroup = createGroup(numColumns, composite,
				FormatterMessages.IndentationTabPage_indent_group_title);
		classIndent = createCheckboxPref(
				classGroup,
				numColumns,
				FormatterMessages.IndentationTabPage_class_group_option_indent_declarations_within_class_body);
		classIndent
				.setIsChecked(codeFormatterPreferences.indent_statements_within_type_declaration);
		methodIndent = createCheckboxPref(
				classGroup,
				numColumns,
				FormatterMessages.IndentationTabPage_block_group_option_indent_statements_compare_to_body);
		methodIndent
				.setIsChecked(codeFormatterPreferences.indent_statements_within_function);
		blockIndent = createCheckboxPref(
				classGroup,
				numColumns,
				FormatterMessages.IndentationTabPage_block_group_option_indent_statements_compare_to_block);
		blockIndent
				.setIsChecked(codeFormatterPreferences.indent_statements_within_block);
		switchIndent = createCheckboxPref(
				classGroup,
				numColumns,
				FormatterMessages.IndentationTabPage_switch_group_option_indent_statements_within_switch_body);
		switchIndent
				.setIsChecked(codeFormatterPreferences.indent_statements_within_switch);
		caseIndent = createCheckboxPref(
				classGroup,
				numColumns,
				FormatterMessages.IndentationTabPage_switch_group_option_indent_statements_within_case_body);
		caseIndent
				.setIsChecked(codeFormatterPreferences.indent_statements_within_case);
		breakIndent = createCheckboxPref(
				classGroup,
				numColumns,
				FormatterMessages.IndentationTabPage_switch_group_option_indent_break_statements);
		breakIndent
				.setIsChecked(codeFormatterPreferences.indent_break_statements_within_case);
		emptylineIndent = createCheckboxPref(classGroup, numColumns,
				FormatterMessages.IndentationTabPage_indent_empty_lines);
		emptylineIndent
				.setIsChecked(codeFormatterPreferences.indent_empty_lines);

		isInitialized = true;
	}

	public void initializePage() {
		fPreview.setPreviewText(PREVIEW);
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
			int index = tabPolicy.getSelectionIndex();
			codeFormatterPreferences.indentationChar = index == 0 ? CodeFormatterPreferences.SPACE_CHAR
					: CodeFormatterPreferences.TAB_CHAR;
			codeFormatterPreferences.indentationSize = indentSize.getValue();
			codeFormatterPreferences.tabSize = tabSize.getValue();
			codeFormatterPreferences.line_wrap_array_init_indentation = fDefaultIndentArrayInit
					.getValue();
			codeFormatterPreferences.indent_statements_within_type_declaration = classIndent
					.isChecked();
			codeFormatterPreferences.indent_statements_within_function = methodIndent
					.isChecked();
			codeFormatterPreferences.indent_statements_within_block = blockIndent
					.isChecked();
			codeFormatterPreferences.indent_statements_within_switch = switchIndent
					.isChecked();
			codeFormatterPreferences.indent_statements_within_case = caseIndent
					.isChecked();
			codeFormatterPreferences.indent_break_statements_within_case = breakIndent
					.isChecked();
			codeFormatterPreferences.indent_empty_lines = emptylineIndent
					.isChecked();
		}
	}

}
