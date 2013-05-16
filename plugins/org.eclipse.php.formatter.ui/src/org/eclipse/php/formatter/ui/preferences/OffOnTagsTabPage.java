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

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.php.formatter.core.CodeFormatterConstants;
import org.eclipse.php.formatter.core.CodeFormatterPreferences;
import org.eclipse.php.formatter.ui.FormatterMessages;
import org.eclipse.php.internal.ui.util.PixelConverter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;

/**
 * Tab page for the on/off formatter tags.
 * 
 * @since 3.6
 */
public class OffOnTagsTabPage extends ModifyDialogTabPage {

	private boolean isInitialized = false;
	private StringPreference enableTagPref;
	private StringPreference disableTagPref;
	private CheckboxPreference enablePref;

	public OffOnTagsTabPage(ModifyDialog modifyDialog,
			CodeFormatterPreferences codeFormatterPreferences) {
		super(modifyDialog, codeFormatterPreferences);
	}

	@Override
	protected void doCreatePreferences(Composite composite, int numColumns) {
		createLabel(numColumns, composite,
				FormatterMessages.OffOnTagsTabPage_description);

		// Add some vertical space
		Label separator = new Label(composite, SWT.NONE);
		separator.setVisible(false);
		GridData data = new GridData(GridData.FILL, GridData.FILL, false,
				false, numColumns, 1);
		data.heightHint = fPixelConverter.convertHeightInCharsToPixels(1) / 3;
		separator.setLayoutData(data);

		enablePref = createCheckboxPref(composite, numColumns,
				FormatterMessages.OffOnTagsTabPage_enableOffOnTags);
		enablePref.setIsChecked(codeFormatterPreferences.use_tags);

		IInputValidator inputValidator = new IInputValidator() {
			/*
			 * @see
			 * org.eclipse.jdt.internal.ui.preferences.formatter.ModifyDialogTabPage
			 * .StringPreference.Validator#isValid(java.lang.String)
			 * 
			 * @since 3.6
			 */
			public String isValid(String input) {
				if (input.length() == 0)
					return null;

				if (Character.isWhitespace(input.charAt(0)))
					return FormatterMessages.OffOnTagsTabPage_error_startsWithWhitespace;

				if (Character.isWhitespace(input.charAt(input.length() - 1)))
					return FormatterMessages.OffOnTagsTabPage_error_endsWithWhitespace;

				return null;
			}
		};

		Composite tagComposite = new Composite(composite, SWT.NONE);
		final int indent = fPixelConverter.convertWidthInCharsToPixels(4);
		GridLayout layout = new GridLayout(numColumns, false);
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		layout.marginLeft = indent;
		tagComposite.setLayout(layout);

		disableTagPref = createStringPref(tagComposite, numColumns,
				FormatterMessages.OffOnTagsTabPage_disableTag,
				CodeFormatterConstants.FORMATTER_DISABLING_TAG, inputValidator);
		if (codeFormatterPreferences.disabling_tag != null) {
			disableTagPref.setValue(new String(
					codeFormatterPreferences.disabling_tag));
		}

		enableTagPref = createStringPref(tagComposite, numColumns,
				FormatterMessages.OffOnTagsTabPage_enableTag,
				CodeFormatterConstants.FORMATTER_ENABLING_TAG, inputValidator);
		if (codeFormatterPreferences.enabling_tag != null) {
			enableTagPref.setValue(new String(
					codeFormatterPreferences.enabling_tag));
		}

		enablePref.getControl().addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				boolean enabled = enablePref.isChecked();
				enableTagPref.setEnabled(enabled);
				disableTagPref.setEnabled(enabled);
			}
		});

		boolean enabled = enablePref.isChecked();
		enableTagPref.setEnabled(enabled);
		disableTagPref.setEnabled(enabled);

		isInitialized = true;
	}

	public final Composite createContents(Composite parent) {
		if (fPixelConverter == null)
			fPixelConverter = new PixelConverter(parent);

		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayoutData(new GridData());

		final int numColumns = 2;
		GridLayout layout = new GridLayout(numColumns, false);
		layout.verticalSpacing = (int) (1.5 * fPixelConverter
				.convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_SPACING));
		layout.horizontalSpacing = fPixelConverter
				.convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_SPACING);
		layout.marginHeight = fPixelConverter
				.convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_MARGIN);
		layout.marginWidth = fPixelConverter
				.convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_MARGIN);
		composite.setLayout(layout);
		doCreatePreferences(composite, numColumns);

		return composite;
	}

	/*
	 * @see
	 * org.eclipse.jdt.internal.ui.preferences.formatter.ModifyDialogTabPage
	 * #initializePage()
	 */
	@Override
	protected void initializePage() {
		// Nothing to do.
	}

	@Override
	protected void doUpdatePreview() {
		// Nothing to do since this page has no preview.
	}

	@Override
	protected PhpPreview doCreatePhpPreview(Composite parent) {
		return null;
	}

	@Override
	protected void updatePreferences() {
		// TODO Auto-generated method stub
		if (isInitialized) {
			codeFormatterPreferences.use_tags = enablePref.isChecked();
			String disableTagOption = disableTagPref.getValue();
			if (disableTagOption != null) {
				int idx = disableTagOption.indexOf('\n');
				if (idx == 0) {
					codeFormatterPreferences.disabling_tag = null;
				} else {
					String tag = idx < 0 ? disableTagOption.trim()
							: disableTagOption.substring(0, idx).trim();
					if (tag.length() == 0) {
						codeFormatterPreferences.disabling_tag = null;
					} else {
						codeFormatterPreferences.disabling_tag = tag
								.toCharArray();
					}
				}
			} else {
				codeFormatterPreferences.disabling_tag = null;
			}

			String enableTagOption = enableTagPref.getValue();
			if (enableTagOption != null) {
				int idx = enableTagOption.indexOf('\n');
				if (idx == 0) {
					codeFormatterPreferences.enabling_tag = null;
				} else {
					String tag = idx < 0 ? enableTagOption.trim()
							: enableTagOption.substring(0, idx).trim();
					if (tag.length() == 0) {
						codeFormatterPreferences.enabling_tag = null;
					} else {
						codeFormatterPreferences.enabling_tag = tag
								.toCharArray();
					}
				}
			} else {
				codeFormatterPreferences.enabling_tag = null;
			}
		}
	}

}
