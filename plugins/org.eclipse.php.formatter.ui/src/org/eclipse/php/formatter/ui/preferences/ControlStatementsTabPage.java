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
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

public class ControlStatementsTabPage extends ModifyDialogTabPage {
	private boolean isInitialized = false;

	private final String PREVIEW = "<?php\n"
			+ createPreviewHeader(FormatterMessages.ControlStatementsTabPage_preview_header)
			+ "class Example { function bar() {	do {} while ( true );"
			+ "try {} catch ( Exception $e ) {	}} function foo2() {if (true) {return;}"
			+ "if (true) {return;} elseif (false) {return;} else { return;}}" + "function foo($s) {	if (true) return;	if (true)	return; else if (false) return; else return;}}?>"; //$NON-NLS-1$

	private CodeFormatterPreview fPreview;

	protected CheckboxPreference fThenStatementPref, fSimpleIfPref,
			newLineBeforeElseCB, newLineBeforeCatchCB, newLineBeforeWhileCB,
			ifElseSameLineCB, elseIfSameLineCB, guardianSameLineCB;

	public ControlStatementsTabPage(ModifyDialog modifyDialog,
			CodeFormatterPreferences codeFormatterPreferences) {
		super(modifyDialog, codeFormatterPreferences);
	}

	protected void doCreatePreferences(Composite composite, int numColumns) {

		final Group generalGroup = createGroup(numColumns, composite,
				FormatterMessages.ControlStatementsTabPage_general_group_title);
		newLineBeforeElseCB = createOption(
				generalGroup,
				numColumns,
				FormatterMessages.ControlStatementsTabPage_general_group_insert_new_line_before_else_and_elseif_statements);
		newLineBeforeElseCB
				.setIsChecked(codeFormatterPreferences.control_statement_insert_newline_before_else_and_elseif_in_if);

		newLineBeforeCatchCB = createOption(
				generalGroup,
				numColumns,
				FormatterMessages.ControlStatementsTabPage_general_group_insert_new_line_before_catch_statements);
		newLineBeforeCatchCB
				.setIsChecked(codeFormatterPreferences.control_statement_insert_newline_before_catch_in_try);

		newLineBeforeWhileCB = createOption(
				generalGroup,
				numColumns,
				FormatterMessages.ControlStatementsTabPage_general_group_insert_new_line_before_while_in_do_statements);
		newLineBeforeWhileCB
				.setIsChecked(codeFormatterPreferences.control_statement_insert_newline_before_while_in_do);

		final Group ifElseGroup = createGroup(numColumns, composite,
				FormatterMessages.ControlStatementsTabPage_if_else_group_title);
		fThenStatementPref = createOption(
				ifElseGroup,
				numColumns,
				FormatterMessages.ControlStatementsTabPage_if_else_group_keep_then_on_same_line);
		fThenStatementPref
				.setIsChecked(codeFormatterPreferences.control_statement_keep_then_on_same_line);

		Label l = new Label(ifElseGroup, SWT.NONE);
		GridData gd = new GridData();
		gd.widthHint = fPixelConverter.convertWidthInCharsToPixels(4);
		l.setLayoutData(gd);

		fSimpleIfPref = createOption(
				ifElseGroup,
				numColumns - 1,
				FormatterMessages.ControlStatementsTabPage_if_else_group_keep_simple_if_on_one_line);
		fSimpleIfPref
				.setIsChecked(codeFormatterPreferences.control_statement_keep_simple_if_on_one_line);

		fThenStatementPref.addObserver(new Observer() {
			public void update(Observable o, Object arg) {
				fSimpleIfPref.setEnabled(!fThenStatementPref.isChecked());
			}

		});

		fSimpleIfPref.setEnabled(!fThenStatementPref.isChecked());

		ifElseSameLineCB = createOption(
				ifElseGroup,
				numColumns,
				FormatterMessages.ControlStatementsTabPage_if_else_group_keep_else_on_same_line);
		ifElseSameLineCB
				.setIsChecked(codeFormatterPreferences.control_statement_keep_else_on_same_line);

		elseIfSameLineCB = createCheckboxPref(
				ifElseGroup,
				numColumns,
				FormatterMessages.ControlStatementsTabPage_if_else_group_keep_else_if_on_one_line);
		elseIfSameLineCB
				.setIsChecked(codeFormatterPreferences.control_statement_keep_else_if_on_same_line);

		guardianSameLineCB = createCheckboxPref(
				ifElseGroup,
				numColumns,
				FormatterMessages.ControlStatementsTabPage_if_else_group_keep_guardian_clause_on_one_line);
		guardianSameLineCB
				.setIsChecked(codeFormatterPreferences.control_statement_keep_guardian_on_one_line);

		isInitialized = true;
	}

	protected void initializePage() {
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
		fPreview.update();
	}

	private CheckboxPreference createOption(Composite composite, int span,
			String name) {
		return createCheckboxPref(composite, span, name);
	}

	protected void updatePreferences() {
		if (isInitialized) {
			codeFormatterPreferences.control_statement_insert_newline_before_else_and_elseif_in_if = newLineBeforeElseCB
					.isChecked();
			codeFormatterPreferences.control_statement_insert_newline_before_catch_in_try = newLineBeforeCatchCB
					.isChecked();
			codeFormatterPreferences.control_statement_insert_newline_before_while_in_do = newLineBeforeWhileCB
					.isChecked();
			codeFormatterPreferences.control_statement_keep_then_on_same_line = fThenStatementPref
					.isChecked();
			codeFormatterPreferences.control_statement_keep_simple_if_on_one_line = fSimpleIfPref
					.isChecked();
			codeFormatterPreferences.control_statement_keep_else_on_same_line = ifElseSameLineCB
					.isChecked();
			codeFormatterPreferences.control_statement_keep_else_if_on_same_line = elseIfSameLineCB
					.isChecked();
			codeFormatterPreferences.control_statement_keep_guardian_on_one_line = guardianSameLineCB
					.isChecked();
		}

	}
}
