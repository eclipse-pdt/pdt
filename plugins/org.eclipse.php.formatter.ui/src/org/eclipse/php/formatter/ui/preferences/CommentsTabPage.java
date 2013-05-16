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

import org.eclipse.core.runtime.Platform;
import org.eclipse.php.formatter.core.CodeFormatterConstants;
import org.eclipse.php.formatter.core.CodeFormatterPreferences;
import org.eclipse.php.formatter.ui.FormatterMessages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;

/**
 * Tab page for the comment formatter settings.
 */
public class CommentsTabPage extends ModifyDialogTabPage {

	private static abstract class Controller implements Observer {

		private final Collection<CheckboxPreference> fMasters;
		private final Collection<Object> fSlaves;

		public Controller(Collection<CheckboxPreference> masters,
				Collection<Object> slaves) {
			fMasters = masters;
			fSlaves = slaves;
			for (final Iterator<CheckboxPreference> iter = fMasters.iterator(); iter
					.hasNext();) {
				iter.next().addObserver(this);
			}
		}

		public void update(Observable o, Object arg) {
			boolean enabled = areSlavesEnabled();

			for (final Iterator<Object> iter = fSlaves.iterator(); iter
					.hasNext();) {
				final Object obj = iter.next();
				if (obj instanceof CheckboxPreference) {
					((CheckboxPreference) obj).setEnabled(enabled);
				} else if (obj instanceof Control) {
					((Group) obj).setEnabled(enabled);
				}
			}
		}

		public Collection<CheckboxPreference> getMasters() {
			return fMasters;
		}

		protected abstract boolean areSlavesEnabled();
	}

	private final static class OrController extends Controller {

		public OrController(Collection<CheckboxPreference> masters,
				Collection<Object> slaves) {
			super(masters, slaves);
			update(null, null);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		protected boolean areSlavesEnabled() {
			for (final Iterator<CheckboxPreference> iter = getMasters()
					.iterator(); iter.hasNext();) {
				if (iter.next().isChecked())
					return true;
			}
			return false;
		}
	}

	private boolean isInitialized = false;
	private final String LINE_SEPARATOR = System
			.getProperty(Platform.PREF_LINE_SEPARATOR);
	private final String PREVIEW = "<?php"
			+ LINE_SEPARATOR
			+ "\t/**       "
			+ LINE_SEPARATOR
			+ " * This is the header comment."
			+ LINE_SEPARATOR
			+ "\t * \ttest"
			+ LINE_SEPARATOR
			+ " */"
			+ LINE_SEPARATOR
			+ "\t/**"
			+ LINE_SEPARATOR
			+ " * This is the comment for the example interface."
			+ LINE_SEPARATOR
			+ " */"
			+ LINE_SEPARATOR
			+ " interface Example {"
			+ LINE_SEPARATOR
			+ "// This is a long comment    with\twhitespace     that should be split in multiple line comments in case the line comment formatting is enabled"
			+ LINE_SEPARATOR
			+ "function foo3();"
			+ LINE_SEPARATOR
			+ " "
			+ LINE_SEPARATOR
			+ "//\tfunction commented() {"
			+ LINE_SEPARATOR
			+ "//\t\t\techo(\"indented\");"
			+ LINE_SEPARATOR
			+ "//\t}"
			+ LINE_SEPARATOR
			+ ""
			+ LINE_SEPARATOR
			+ "\t//\tfunction indentedCommented() {"
			+ LINE_SEPARATOR
			+ "\t//\t\t\techo(\"indented\");"
			+ LINE_SEPARATOR
			+ "\t//\t}"
			+ LINE_SEPARATOR
			+ ""
			+ LINE_SEPARATOR
			+ "/* block comment          on first column*/"
			+ LINE_SEPARATOR
			+ " function bar();"
			+ LINE_SEPARATOR
			+ "\t/*"
			+ LINE_SEPARATOR
			+ "\t*"
			+ LINE_SEPARATOR
			+ "\t* These possibilities include:"
			+ LINE_SEPARATOR
			+ "\t* <ul><li>Formatting of header comments.</li><li>Formatting of Javadoc tags</li></ul>"
			+ LINE_SEPARATOR
			+ "\t*/"
			+ LINE_SEPARATOR
			+ " function bar2(); // This is a long comment that should be split in multiple line comments in case the line comment formatting is enabled"
			+ LINE_SEPARATOR
			+ " /**"
			+ LINE_SEPARATOR
			+ " * The following is some sample code which illustrates source formatting within javadoc comments:"
			+ LINE_SEPARATOR
			+ " * Descriptions of parameters and return values are best appended at end of the javadoc comment."
			+ LINE_SEPARATOR
			+ " * @param $a int The first parameter. For an optimum result, this should be an odd number"
			+ LINE_SEPARATOR
			+ " * between 0 and 100."
			+ LINE_SEPARATOR
			+ " * @param $b int The second parameter."
			+ LINE_SEPARATOR
			+ " * @return int The result of the foo operation, usually within 0 and 1000."
			+ LINE_SEPARATOR
			+ " */"
			+ " function foo(int $a, int $b);"
			+ LINE_SEPARATOR
			+ "}"
			+ LINE_SEPARATOR
			+ "// This is a long comment    with\twhitespace     that should be split in multiple line comments in case the line comment formatting is enabled"
			+ LINE_SEPARATOR + "class Test {" + LINE_SEPARATOR
			+ "\t\tfunction trailingCommented() {" + LINE_SEPARATOR
			+ "\t\t\t\techo(\"indented\");\t\t// comment" + LINE_SEPARATOR
			+ "\t\t\t\techo(\"indent\");\t\t// comment" + LINE_SEPARATOR
			+ "\t\t}" + LINE_SEPARATOR + "}";

	private CodeFormatterPreview fPreview;

	private CheckboxPreference javadoc;

	private CheckboxPreference blockComment;

	private CheckboxPreference singleLineComments;

	private CheckboxPreference singleLineCommentsOnFirstColumn;

	private CheckboxPreference header;

	// private CheckboxPreference chkbox1;

	private CheckboxPreference chkbox2;

	private CheckboxPreference chkbox3;

	private CheckboxPreference chkbox4;

	// private CheckboxPreference html;
	//
	// private CheckboxPreference code;

	private CheckboxPreference blankJavadoc;

	private CheckboxPreference indentJavadoc;

	private CheckboxPreference indentDesc;

	private CheckboxPreference nlParam;

	private CheckboxPreference nlBoundariesJavadoc;

	private CheckboxPreference blankLinesJavadoc;

	private CheckboxPreference nlBoundariesBlock;

	private CheckboxPreference blankLinesBlock;

	private NumberPreference lineWidth;

	public CommentsTabPage(ModifyDialog modifyDialog,
			CodeFormatterPreferences codeFormatterPreferences) {
		super(modifyDialog, codeFormatterPreferences);
	}

	protected void doCreatePreferences(Composite composite, int numColumns) {
		final int indent = fPixelConverter.convertWidthInCharsToPixels(4);

		// global group
		final Group globalGroup = createGroup(numColumns, composite,
				FormatterMessages.CommentsTabPage_group1_title);
		javadoc = createCheckboxPref(
				globalGroup,
				numColumns,
				FormatterMessages.commentsTabPage_enable_javadoc_comment_formatting);
		javadoc.setIsChecked(codeFormatterPreferences.comment_format_javadoc_comment);

		blockComment = createCheckboxPref(
				globalGroup,
				numColumns,
				FormatterMessages.CommentsTabPage_enable_block_comment_formatting);
		blockComment
				.setIsChecked(codeFormatterPreferences.comment_format_block_comment);

		singleLineComments = createCheckboxPref(
				globalGroup,
				numColumns,
				FormatterMessages.CommentsTabPage_enable_line_comment_formatting);
		singleLineComments
				.setIsChecked(codeFormatterPreferences.comment_format_line_comment);

		singleLineCommentsOnFirstColumn = createCheckboxPref(
				globalGroup,
				numColumns,
				FormatterMessages.CommentsTabPage_format_line_comments_on_first_column);
		singleLineCommentsOnFirstColumn
				.setIsChecked(codeFormatterPreferences.comment_format_line_comment_starting_on_first_column);

		((GridData) singleLineCommentsOnFirstColumn.getControl()
				.getLayoutData()).horizontalIndent = indent;
		header = createCheckboxPref(globalGroup, numColumns,
				FormatterMessages.CommentsTabPage_format_header);
		header.setIsChecked(codeFormatterPreferences.comment_format_header);

		GridData spacerData = new GridData(0, 0);
		spacerData.horizontalSpan = numColumns;
		new Composite(globalGroup, SWT.NONE).setLayoutData(spacerData);
		// chkbox1 = createCheckboxPref(
		// globalGroup,
		// numColumns,
		// FormatterMessages.CommentsTabPage_preserve_white_space_before_line_comment);
		// chkbox1.setIsChecked(codeFormatterPreferences.comment_format_javadoc_comment);

		chkbox2 = createCheckboxPref(
				globalGroup,
				numColumns,
				FormatterMessages.CommentsTabPage_never_indent_line_comments_on_first_column);
		chkbox2.setIsChecked(codeFormatterPreferences.never_indent_line_comments_on_first_column);

		chkbox3 = createCheckboxPref(
				globalGroup,
				numColumns,
				FormatterMessages.CommentsTabPage_never_indent_block_comments_on_first_column);
		chkbox3.setIsChecked(codeFormatterPreferences.never_indent_block_comments_on_first_column);

		chkbox4 = createCheckboxPref(globalGroup, numColumns,
				FormatterMessages.CommentsTabPage_do_not_join_lines);
		chkbox4.setIsChecked(codeFormatterPreferences.join_lines_in_comments);

		// javadoc comment formatting settings
		final Group settingsGroup = createGroup(numColumns, composite,
				FormatterMessages.CommentsTabPage_group2_title);
		// html = createCheckboxPref(settingsGroup, numColumns,
		// FormatterMessages.CommentsTabPage_format_html);
		// html.setIsChecked(codeFormatterPreferences.comment_format_javadoc_comment);
		//
		// code = createCheckboxPref(settingsGroup, numColumns,
		// FormatterMessages.CommentsTabPage_format_code_snippets);
		// code.setIsChecked(codeFormatterPreferences.comment_format_javadoc_comment);

		blankJavadoc = createPrefInsert(
				settingsGroup,
				numColumns,
				FormatterMessages.CommentsTabPage_blank_line_before_javadoc_tags,
				CodeFormatterConstants.FORMATTER_COMMENT_INSERT_EMPTY_LINE_BEFORE_ROOT_TAGS);
		blankJavadoc
				.setIsChecked(codeFormatterPreferences.comment_insert_empty_line_before_root_tags);

		indentJavadoc = createCheckboxPref(settingsGroup, numColumns,
				FormatterMessages.CommentsTabPage_indent_javadoc_tags);
		indentJavadoc
				.setIsChecked(codeFormatterPreferences.comment_indent_root_tags);

		indentDesc = createCheckboxPref(
				settingsGroup,
				numColumns,
				FormatterMessages.CommentsTabPage_indent_description_after_param);
		indentDesc
				.setIsChecked(codeFormatterPreferences.comment_indent_parameter_description);

		((GridData) indentDesc.getControl().getLayoutData()).horizontalIndent = indent;
		nlParam = createPrefInsert(
				settingsGroup,
				numColumns,
				FormatterMessages.CommentsTabPage_new_line_after_param_tags,
				CodeFormatterConstants.FORMATTER_COMMENT_INSERT_NEW_LINE_FOR_PARAMETER);
		nlParam.setIsChecked(codeFormatterPreferences.comment_insert_new_line_for_parameter);

		nlBoundariesJavadoc = createCheckboxPref(
				settingsGroup,
				numColumns,
				FormatterMessages.CommentsTabPage_new_lines_at_javadoc_boundaries);
		nlBoundariesJavadoc
				.setIsChecked(codeFormatterPreferences.comment_new_lines_at_javadoc_boundaries);

		blankLinesJavadoc = createCheckboxPref(settingsGroup, numColumns,
				FormatterMessages.CommentsTabPage_clear_blank_lines);
		blankLinesJavadoc
				.setIsChecked(codeFormatterPreferences.comment_clear_blank_lines_in_javadoc_comment);

		// block comment settings
		final Group blockSettingsGroup = createGroup(numColumns, composite,
				FormatterMessages.CommentsTabPage_group4_title);
		nlBoundariesBlock = createCheckboxPref(
				blockSettingsGroup,
				numColumns,
				FormatterMessages.CommentsTabPage_new_lines_at_comment_boundaries);
		nlBoundariesBlock
				.setIsChecked(codeFormatterPreferences.comment_new_lines_at_block_boundaries);

		blankLinesBlock = createCheckboxPref(
				blockSettingsGroup,
				numColumns,
				FormatterMessages.CommentsTabPage_remove_blank_block_comment_lines);
		blankLinesBlock
				.setIsChecked(codeFormatterPreferences.comment_clear_blank_lines_in_block_comment);

		// line width settings
		final Group widthGroup = createGroup(numColumns, composite,
				FormatterMessages.CommentsTabPage_group3_title);
		lineWidth = createNumberPref(widthGroup, numColumns,
				FormatterMessages.CommentsTabPage_line_width, 0, 9999);
		lineWidth.setValue(codeFormatterPreferences.comment_line_length);

		ArrayList<CheckboxPreference> lineFirstColumnMasters = new ArrayList<CheckboxPreference>();
		lineFirstColumnMasters.add(singleLineComments);

		ArrayList<Object> lineFirstColumnSlaves = new ArrayList<Object>();
		lineFirstColumnSlaves.add(singleLineCommentsOnFirstColumn);

		new Controller(lineFirstColumnMasters, lineFirstColumnSlaves) {
			@Override
			protected boolean areSlavesEnabled() {
				return singleLineComments.isChecked();
			}
		}.update(null, null);

		ArrayList<CheckboxPreference> javaDocMaster = new ArrayList<CheckboxPreference>();
		javaDocMaster.add(javadoc);
		javaDocMaster.add(header);

		ArrayList<Object> javaDocSlaves = new ArrayList<Object>();
		javaDocSlaves.add(settingsGroup);
		// javaDocSlaves.add(html);
		// javaDocSlaves.add(code);
		javaDocSlaves.add(blankJavadoc);
		javaDocSlaves.add(indentJavadoc);
		javaDocSlaves.add(nlParam);
		javaDocSlaves.add(nlBoundariesJavadoc);
		javaDocSlaves.add(blankLinesJavadoc);

		new OrController(javaDocMaster, javaDocSlaves);

		ArrayList<CheckboxPreference> indentMasters = new ArrayList<CheckboxPreference>();
		indentMasters.add(javadoc);
		indentMasters.add(header);
		indentMasters.add(indentJavadoc);

		ArrayList<Object> indentSlaves = new ArrayList<Object>();
		indentSlaves.add(indentDesc);

		new Controller(indentMasters, indentSlaves) {
			@Override
			protected boolean areSlavesEnabled() {
				return (javadoc.isChecked() || header.isChecked())
						&& indentJavadoc.isChecked();
			}
		}.update(null, null);

		ArrayList<CheckboxPreference> blockMasters = new ArrayList<CheckboxPreference>();
		blockMasters.add(blockComment);
		blockMasters.add(header);

		ArrayList<Object> blockSlaves = new ArrayList<Object>();
		blockSlaves.add(blockSettingsGroup);
		blockSlaves.add(nlBoundariesBlock);
		blockSlaves.add(blankLinesBlock);

		new OrController(blockMasters, blockSlaves);

		ArrayList<CheckboxPreference> lineWidthMasters = new ArrayList<CheckboxPreference>();
		lineWidthMasters.add(javadoc);
		lineWidthMasters.add(blockComment);
		lineWidthMasters.add(singleLineComments);
		lineWidthMasters.add(header);

		ArrayList<Object> lineWidthSlaves = new ArrayList<Object>();
		lineWidthSlaves.add(widthGroup);
		lineWidthSlaves.add(lineWidth);

		new OrController(lineWidthMasters, lineWidthSlaves);

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

	private CheckboxPreference createPrefInsert(Composite composite,
			int numColumns, String text, String key) {
		return createCheckboxPref(composite, numColumns, text);
	}

	@Override
	protected void updatePreferences() {
		if (isInitialized) {
			codeFormatterPreferences.comment_format_javadoc_comment = javadoc
					.isChecked();

			codeFormatterPreferences.comment_format_block_comment = blockComment
					.isChecked();

			codeFormatterPreferences.comment_format_line_comment = singleLineComments
					.isChecked();

			codeFormatterPreferences.comment_format_line_comment_starting_on_first_column = singleLineCommentsOnFirstColumn
					.isChecked();

			codeFormatterPreferences.comment_format_header = header.isChecked();

			codeFormatterPreferences.never_indent_line_comments_on_first_column = chkbox2
					.isChecked();

			codeFormatterPreferences.never_indent_block_comments_on_first_column = chkbox3
					.isChecked();

			codeFormatterPreferences.join_lines_in_comments = chkbox4
					.isChecked();

			codeFormatterPreferences.comment_insert_empty_line_before_root_tags = blankJavadoc
					.isChecked();

			codeFormatterPreferences.comment_indent_root_tags = indentJavadoc
					.isChecked();

			codeFormatterPreferences.comment_indent_parameter_description = indentDesc
					.isChecked();

			codeFormatterPreferences.comment_insert_new_line_for_parameter = nlParam
					.isChecked();

			codeFormatterPreferences.comment_new_lines_at_javadoc_boundaries = nlBoundariesJavadoc
					.isChecked();

			codeFormatterPreferences.comment_clear_blank_lines_in_javadoc_comment = blankLinesJavadoc
					.isChecked();

			codeFormatterPreferences.comment_new_lines_at_block_boundaries = nlBoundariesBlock
					.isChecked();

			codeFormatterPreferences.comment_clear_blank_lines_in_block_comment = blankLinesBlock
					.isChecked();

			codeFormatterPreferences.comment_line_length = lineWidth.getValue();
		}
	}
}
