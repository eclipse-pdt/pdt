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

public class BracesTabPage extends ModifyDialogTabPage {

	private final String PREVIEW = "<?php\n" + createPreviewHeader(FormatterMessages.BracesTabPage_preview_header) + //$NON-NLS-1$
			"interface EmptyInterface {}\n" + //$NON-NLS-1$
			"\n" + //$NON-NLS-1$
			"class Example {" + //$NON-NLS-1$
			"  function bar($p) {" + //$NON-NLS-1$
			"    for ($i= 0; $i<10; $i++) {" + //$NON-NLS-1$
			"    }" + //$NON-NLS-1$
			"    switch($p) {" + //$NON-NLS-1$
			"      case 0:" + //$NON-NLS-1$
			"        $fField->set(0);" + //$NON-NLS-1$
			"        break;" + //$NON-NLS-1$
			"      case 1: {" + //$NON-NLS-1$
			"        break;" + //$NON-NLS-1$
			"        }" + //$NON-NLS-1$
			"      default:" + //$NON-NLS-1$
			"        $fField->reset();" + //$NON-NLS-1$
			"    }" + //$NON-NLS-1$
			"  }" + //$NON-NLS-1$
			"}" + //$NON-NLS-1$
			"\n?>"; //$NON-NLS-1$

	private CodeFormatterPreview fPreview;

	private boolean isInitialized = false;

	private ComboPreference classDeclaration;
	private ComboPreference methodDeclaration;
	private ComboPreference blocks;
	private ComboPreference switchCase;

	private final String[] fBracePositionNames = {
			FormatterMessages.BracesTabPage_position_same_line,
			FormatterMessages.BracesTabPage_position_next_line,
			FormatterMessages.BracesTabPage_position_next_line_indented };

	/**
	 * Create a new BracesTabPage.
	 * 
	 * @param modifyDialog
	 * @param workingValues
	 */
	public BracesTabPage(ModifyDialog modifyDialog,
			CodeFormatterPreferences preferences) {
		super(modifyDialog, preferences);
	}

	protected void doCreatePreferences(Composite composite, int numColumns) {

		final Group group = createGroup(numColumns, composite,
				FormatterMessages.BracesTabPage_group_brace_positions_title);
		classDeclaration = createBracesCombo(group, numColumns,
				FormatterMessages.BracesTabPage_option_class_declaration);
		setComboValue(classDeclaration,
				codeFormatterPreferences.brace_position_for_class);
		methodDeclaration = createBracesCombo(group, numColumns,
				FormatterMessages.BracesTabPage_option_method_declaration);
		setComboValue(methodDeclaration,
				codeFormatterPreferences.brace_position_for_function);
		blocks = createBracesCombo(group, numColumns,
				FormatterMessages.BracesTabPage_option_blocks);
		setComboValue(blocks, codeFormatterPreferences.brace_position_for_block);
		switchCase = createBracesCombo(group, numColumns,
				FormatterMessages.BracesTabPage_option_switch_case);
		setComboValue(switchCase,
				codeFormatterPreferences.brace_position_for_switch);

		isInitialized = true;
	}

	protected void initializePage() {
		fPreview.setPreviewText(PREVIEW);
	}

	protected PhpPreview doCreatePhpPreview(Composite parent) {
		fPreview = new CodeFormatterPreview(codeFormatterPreferences, parent);
		return fPreview;
	}

	private ComboPreference createBracesCombo(Composite composite,
			int numColumns, String message) {
		return createComboPref(composite, numColumns, message,
				fBracePositionNames);
	}

	protected void doUpdatePreview() {
		if (fPreview != null) {
			fPreview.update();
		}
	}

	protected void updatePreferences() {
		if (isInitialized) {
			codeFormatterPreferences.brace_position_for_class = getComboValue(classDeclaration);
			codeFormatterPreferences.brace_position_for_function = getComboValue(methodDeclaration);
			codeFormatterPreferences.brace_position_for_block = getComboValue(blocks);
			codeFormatterPreferences.brace_position_for_switch = getComboValue(switchCase);
		}

	}

	private byte getComboValue(ComboPreference combo) {
		int index = combo.getSelectionIndex();
		switch (index) {
		case 0:
			return CodeFormatterPreferences.SAME_LINE;
		case 1:
			return CodeFormatterPreferences.NEXT_LINE;
		case 2:
			return CodeFormatterPreferences.NEXT_LINE_INDENT;
		}
		return -1;
	}

	private void setComboValue(ComboPreference combo, byte value) {
		switch (value) {
		case CodeFormatterPreferences.SAME_LINE:
			combo.setSelectedItem(FormatterMessages.BracesTabPage_position_same_line);
			break;
		case CodeFormatterPreferences.NEXT_LINE:
			combo.setSelectedItem(FormatterMessages.BracesTabPage_position_next_line);
			break;
		case CodeFormatterPreferences.NEXT_LINE_INDENT:
			combo.setSelectedItem(FormatterMessages.BracesTabPage_position_next_line_indented);
			break;
		}
	}

}
