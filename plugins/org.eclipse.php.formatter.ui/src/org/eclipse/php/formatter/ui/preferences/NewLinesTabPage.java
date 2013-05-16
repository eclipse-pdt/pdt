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

/**
 * @author moshe, yaronm
 * 
 */
public class NewLinesTabPage extends ModifyDialogTabPage {

	private final String PREVIEW = "<?php\n" + createPreviewHeader(FormatterMessages.NewLinesTabPage_preview_header) + "class EmptyBody{}" + //$NON-NLS-1$ //$NON-NLS-2$
			"class Example {function emptyFoo(){} function foo() {do {} while (false); for (;;){}}}" //$NON-NLS-1$
			+ "$array=array(1,2,3,4,5); while($a){}; $b = new EmptyBody(); $b->foo()->foo();" //$NON-NLS-1$
			+ // move
				// to
				// blank
				// lines
				// :
				// "switch ($number){case RED: return RED; case GREEN: return GREEN; case BLUE: return BLUE; default: return BLACK ;}"+
			"\n?>"; //$NON-NLS-1$

	private CheckboxPreference inEmptyClassBodyCB;
	private CheckboxPreference inEmptyMethodBodyCB;
	private CheckboxPreference inEmptyBlockCB;
	private CheckboxPreference afterOpenBraceArrayCB;
	private CheckboxPreference beforeCloseBraceArrayCB;
	private CheckboxPreference putEmptyStatementsNewlineCB;

	protected CheckboxPreference fThenStatementPref, fSimpleIfPref;

	private CodeFormatterPreview fPreview;

	private boolean isInitialized = false;

	private NumberPreference inSecondMetodInvoke;

	public NewLinesTabPage(ModifyDialog modifyDialog,
			CodeFormatterPreferences codeFormatterPreferences) {
		super(modifyDialog, codeFormatterPreferences);
	}

	protected void doCreatePreferences(Composite composite, int numColumns) {

		final Group newlinesGroup = createGroup(numColumns, composite,
				FormatterMessages.NewLinesTabPage_newlines_group_title);
		inEmptyClassBodyCB = createCheckboxPref(
				newlinesGroup,
				numColumns,
				FormatterMessages.NewLinesTabPage_newlines_group_option_empty_class_body);
		inEmptyClassBodyCB
				.setIsChecked(codeFormatterPreferences.new_line_in_empty_class_body);

		inEmptyMethodBodyCB = createCheckboxPref(
				newlinesGroup,
				numColumns,
				FormatterMessages.NewLinesTabPage_newlines_group_option_empty_method_body);
		inEmptyMethodBodyCB
				.setIsChecked(codeFormatterPreferences.new_line_in_empty_method_body);

		inEmptyBlockCB = createCheckboxPref(
				newlinesGroup,
				numColumns,
				FormatterMessages.NewLinesTabPage_newlines_group_option_empty_block);
		inEmptyBlockCB
				.setIsChecked(codeFormatterPreferences.new_line_in_empty_block);

		inSecondMetodInvoke = createNumberPref(newlinesGroup, numColumns,
				FormatterMessages.NewLinesTabPage_3, 0, 99);
		inSecondMetodInvoke
				.setValue(codeFormatterPreferences.new_line_in_second_invoke);

		final Group arrayInitializerGroup = createGroup(numColumns, composite,
				FormatterMessages.NewLinesTabPage_arrayInitializer_group_title);
		afterOpenBraceArrayCB = createCheckboxPref(arrayInitializerGroup,
				numColumns, FormatterMessages.NewLinesTabPage_4);
		afterOpenBraceArrayCB
				.setIsChecked(codeFormatterPreferences.new_line_after_open_array_parenthesis);

		beforeCloseBraceArrayCB = createCheckboxPref(arrayInitializerGroup,
				numColumns, FormatterMessages.NewLinesTabPage_5);
		beforeCloseBraceArrayCB
				.setIsChecked(codeFormatterPreferences.new_line_before_close_array_parenthesis_array);

		final Group emptyStatementsGroup = createGroup(numColumns, composite,
				FormatterMessages.NewLinesTabPage_empty_statement_group_title);
		putEmptyStatementsNewlineCB = createCheckboxPref(
				emptyStatementsGroup,
				numColumns,
				FormatterMessages.NewLinesTabPage_emtpy_statement_group_option_empty_statement_on_new_line);
		putEmptyStatementsNewlineCB
				.setIsChecked(codeFormatterPreferences.new_line_for_empty_statement);

		isInitialized = true;
	}

	protected void initializePage() {
		fPreview.setPreviewText(PREVIEW);
	}

	protected PhpPreview doCreatePhpPreview(Composite parent) {
		fPreview = new CodeFormatterPreview(codeFormatterPreferences, parent);
		return fPreview;
	}

	protected void doUpdatePreview() {
		if (fPreview != null) {
			fPreview.update();
		}
	}

	protected void updatePreferences() {
		if (isInitialized) {
			codeFormatterPreferences.new_line_in_empty_class_body = inEmptyClassBodyCB
					.isChecked();
			codeFormatterPreferences.new_line_in_empty_method_body = inEmptyMethodBodyCB
					.isChecked();
			codeFormatterPreferences.new_line_in_empty_block = inEmptyBlockCB
					.isChecked();
			codeFormatterPreferences.new_line_after_open_array_parenthesis = afterOpenBraceArrayCB
					.isChecked();
			codeFormatterPreferences.new_line_before_close_array_parenthesis_array = beforeCloseBraceArrayCB
					.isChecked();
			codeFormatterPreferences.new_line_for_empty_statement = putEmptyStatementsNewlineCB
					.isChecked();
			codeFormatterPreferences.new_line_in_second_invoke = inSecondMetodInvoke
					.getValue();
		}
	}
}
