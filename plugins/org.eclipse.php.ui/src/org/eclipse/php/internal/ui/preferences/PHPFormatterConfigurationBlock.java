/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.ui.preferences;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.php.internal.core.PHPCoreConstants;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.preferences.util.Key;
import org.eclipse.php.internal.ui.util.StatusInfo;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.preferences.IWorkbenchPreferenceContainer;

/**
 * @author guy.g
 * 
 */
public class PHPFormatterConfigurationBlock extends
		PHPCoreOptionsConfigurationBlock implements ModifyListener,
		SelectionListener {

	public static final Key PREF_FORMATTER_USE_TABS = getPHPCoreKey(PHPCoreConstants.FORMATTER_USE_TABS);
	public static final Key PREF_FORMATTER_INDENTATION_SIZE = getPHPCoreKey(PHPCoreConstants.FORMATTER_INDENTATION_SIZE);
	public static final Key PREF_FORMATTER_INDENTATION_WRAPPED_LINE_SIZE = getPHPCoreKey(PHPCoreConstants.FORMATTER_INDENTATION_WRAPPED_LINE_SIZE);
	public static final Key PREF_FORMATTER_INDENTATION_ARRAY_INIT_SIZE = getPHPCoreKey(PHPCoreConstants.FORMATTER_INDENTATION_ARRAY_INIT_SIZE);

	private static final int MIN_INDENT_SIZE = 0;
	private static final int MAX_INDENT_SIZE = 32;

	private IStatus fFormatterStatus;

	private Combo tabPolicyCombo;
	private Text indentSizeTxt;
	private Text fDefaultIndentWrapLineSizeTxt;
	private Text fDefaultIndentArrayInitSizeTxt;

	public PHPFormatterConfigurationBlock(IStatusChangeListener context,
			IProject project, IWorkbenchPreferenceContainer container) {
		super(context, project, getKeys(), container);

		fFormatterStatus = new StatusInfo();
	}

	public Control createContents(Composite parent) {
		setShell(parent.getShell());

		Composite markersComposite = createFormaterContent(parent);

		validateSettings(null, null, null);

		return markersComposite;
	}

	private Composite createFormaterContent(Composite parent) {
		Group formattingComposite = createComposite(parent, 2);
		final String[] tabPolicyLabels = new String[] {
				PHPUIMessages.PHPFormatterConfigurationBlock_tabsLabel,
				PHPUIMessages.PHPFormatterConfigurationBlock_spacesLabel }; //$NON-NLS-1$ //$NON-NLS-2$
		Label indentTabsLabel = new Label(formattingComposite, SWT.NULL);
		indentTabsLabel
				.setText(PHPUIMessages.PHPFormatterConfigurationBlock_tabPolicyLabel); //$NON-NLS-1$
		tabPolicyCombo = new Combo(formattingComposite, SWT.NULL
				| SWT.READ_ONLY);
		tabPolicyCombo.setItems(tabPolicyLabels);
		tabPolicyCombo.select(0);

		GridData gd = new GridData();
		gd.widthHint = 20;

		Label indentSizeLabel = new Label(formattingComposite, SWT.NULL);
		indentSizeLabel
				.setText(PHPUIMessages.PHPFormatterConfigurationBlock_indentSizeLabel); //$NON-NLS-1$
		indentSizeTxt = new Text(formattingComposite, SWT.BORDER);
		indentSizeTxt.setTextLimit(2);
		indentSizeTxt.setLayoutData(gd);

		gd = new GridData();
		gd.widthHint = 20;

		Label indentWrapLineSize = new Label(formattingComposite, SWT.NULL);
		indentWrapLineSize
				.setText(PHPUIMessages.PHPFormatterConfigurationBlock_indentWrapLineSizeLabel); //$NON-NLS-1$
		fDefaultIndentWrapLineSizeTxt = new Text(formattingComposite,
				SWT.BORDER);
		fDefaultIndentWrapLineSizeTxt.setTextLimit(2);
		fDefaultIndentWrapLineSizeTxt.setLayoutData(gd);

		tabPolicyCombo.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub

			}

			public void widgetSelected(SelectionEvent e) {
				Combo source = (Combo) e.getSource();
				int selectIndex = source.getSelectionIndex();
				if (selectIndex == 1) { // select 'Spaces'
					indentSizeTxt.setEnabled(true);
				} else {// select 'Tabs'
					indentSizeTxt.setEnabled(false);
					indentSizeTxt.setText("1"); //$NON-NLS-1$
				}
			}
		});

		indentSizeTxt.addModifyListener(this);
		fDefaultIndentWrapLineSizeTxt.addModifyListener(this);
		tabPolicyCombo.addSelectionListener(this);

		initValues();
		updateValues();

		return formattingComposite;
	}

	protected void validateSettings(Key changedKey, String oldValue,
			String newValue) {
		if (changedKey != null) {
			if (PREF_FORMATTER_INDENTATION_SIZE.equals(changedKey)) {
				try {
					fIndentationSize = Integer.valueOf(newValue);
					if (fIndentationSize < MIN_INDENT_SIZE
							|| fIndentationSize > MAX_INDENT_SIZE) {
						fFormatterStatus = new StatusInfo(
								IStatus.ERROR,
								PHPUIMessages.PHPFormatterConfigurationBlock_indentSizeErrorMessage); //$NON-NLS-1$
					} else {
						setValue(PREF_FORMATTER_INDENTATION_SIZE,
								String.valueOf(fIndentationSize));
						fFormatterStatus = new StatusInfo();
					}
				} catch (NumberFormatException nfe) {
					fFormatterStatus = new StatusInfo(
							IStatus.ERROR,
							PHPUIMessages.PHPFormatterConfigurationBlock_indentSizeErrorMessage); //$NON-NLS-1$
				}
			}
			if (PREF_FORMATTER_INDENTATION_WRAPPED_LINE_SIZE.equals(changedKey)) {
				try {
					fIndentationWrappedLineSize = Integer.valueOf(newValue);
					if (fIndentationWrappedLineSize < MIN_INDENT_SIZE
							|| fIndentationWrappedLineSize > MAX_INDENT_SIZE) {
						fFormatterStatus = new StatusInfo(
								IStatus.ERROR,
								PHPUIMessages.PHPFormatterConfigurationBlock_indentSizeErrorMessage); //$NON-NLS-1$
					} else {
						setValue(PREF_FORMATTER_INDENTATION_WRAPPED_LINE_SIZE,
								String.valueOf(fIndentationWrappedLineSize));
						fFormatterStatus = new StatusInfo();
					}
				} catch (NumberFormatException nfe) {
					fFormatterStatus = new StatusInfo(
							IStatus.ERROR,
							PHPUIMessages.PHPFormatterConfigurationBlock_indentSizeErrorMessage); //$NON-NLS-1$
				}
			}
			if (PREF_FORMATTER_INDENTATION_ARRAY_INIT_SIZE.equals(changedKey)) {
				// try {
				// fIndentationArrayInitSize = Integer.valueOf(newValue);
				// if (fIndentationArrayInitSize < MIN_INDENT_SIZE
				// || fIndentationArrayInitSize > MAX_INDENT_SIZE) {
				// fFormatterStatus = new StatusInfo(
				// IStatus.ERROR,
				//								PHPUIMessages.PHPFormatterConfigurationBlock_indentSizeErrorMessage); //$NON-NLS-1$
				// } else {
				// setValue(PREF_FORMATTER_INDENTATION_ARRAY_INIT_SIZE,
				// String.valueOf(fIndentationArrayInitSize));
				// fFormatterStatus = new StatusInfo();
				// }
				// } catch (NumberFormatException nfe) {
				// fFormatterStatus = new StatusInfo(
				// IStatus.ERROR,
				//							PHPUIMessages.PHPFormatterConfigurationBlock_indentSizeErrorMessage); //$NON-NLS-1$
				// }
			} else {
				return;
			}
		} else {
			fFormatterStatus = new StatusInfo();
		}

		fContext.statusChanged(fFormatterStatus);

	}

	protected String[] getFullBuildDialogStrings(boolean workspaceSettings) {
		return null;
	}

	private static Key[] getKeys() {
		return new Key[] { PREF_FORMATTER_USE_TABS,
				PREF_FORMATTER_INDENTATION_SIZE,
				PREF_FORMATTER_INDENTATION_WRAPPED_LINE_SIZE,
				PREF_FORMATTER_INDENTATION_ARRAY_INIT_SIZE };
	}

	private Group createComposite(Composite parent, int numColumns) {
		Group group = new Group(parent, SWT.NONE);
		group.setText(PHPUIMessages.PHPFormatterConfigurationBlock_0); //$NON-NLS-1$

		// GridLayout
		GridLayout layout = new GridLayout();
		layout.numColumns = numColumns;
		layout.marginTop = 5;
		layout.marginBottom = 5;

		group.setLayout(layout);

		// GridData
		GridData data = new GridData(SWT.FILL, SWT.TOP, true, false);

		group.setLayoutData(data);
		group.setLayoutData(data);

		return group;
	}

	public void modifyText(ModifyEvent e) {
		// If we are called too early, i.e. before the controls are created
		// then return
		// to avoid null pointer exceptions
		if (e.widget != null && e.widget.isDisposed())
			return;

		validateValues(e.widget);
	}

	protected void validateValues(Widget w) {
		Text c = (Text) w;
		String textFieldStr = c.getText();
		validateSettings(PREF_FORMATTER_INDENTATION_SIZE, new Integer(
				fIndentationSize).toString(), textFieldStr);
		validateSettings(PREF_FORMATTER_INDENTATION_WRAPPED_LINE_SIZE,
				new Integer(fIndentationWrappedLineSize).toString(),
				textFieldStr);
		validateSettings(PREF_FORMATTER_INDENTATION_ARRAY_INIT_SIZE,
				new Integer(fIndentationArrayInitSize).toString(), textFieldStr);

	}

	protected void updateControls() {
		initValues();
		updateValues();
	}

	private boolean fUseTabs;
	private int fIndentationSize;
	private int fIndentationWrappedLineSize;
	private int fIndentationArrayInitSize;

	private void initValues() {
		String useTabs = getValue(PREF_FORMATTER_USE_TABS);
		String indentationSize = getValue(PREF_FORMATTER_INDENTATION_SIZE);
		String indentationWrappedLineSize = getValue(PREF_FORMATTER_INDENTATION_WRAPPED_LINE_SIZE);
		String indentationArrayInitSize = getValue(PREF_FORMATTER_INDENTATION_ARRAY_INIT_SIZE);

		fUseTabs = Boolean.valueOf(useTabs).booleanValue();
		fIndentationSize = Integer.valueOf(indentationSize).intValue();
		indentSizeTxt.setText(indentationSize);

		fIndentationWrappedLineSize = Integer.valueOf(
				indentationWrappedLineSize).intValue();
		fDefaultIndentWrapLineSizeTxt.setText(indentationWrappedLineSize);

		fIndentationArrayInitSize = Integer.valueOf(indentationArrayInitSize)
				.intValue();
		// fDefaultIndentArrayInitSizeTxt.setText(indentationArrayInitSize);
	}

	private void updateValues() {
		if (fUseTabs) {
			tabPolicyCombo.select(0);
			indentSizeTxt.setEnabled(false);
		} else {
			tabPolicyCombo.select(1);
			indentSizeTxt.setEnabled(true);
		}
	}

	public void widgetSelected(SelectionEvent e) {
		updateButtonStatus((Combo) e.widget);
	}

	public void widgetDefaultSelected(SelectionEvent e) {
		updateButtonStatus((Combo) e.widget);
	}

	private void updateButtonStatus(Combo b) {
		fUseTabs = (b.getSelectionIndex() == 0);
		setValue(PREF_FORMATTER_USE_TABS, fUseTabs ? "true" : "false"); //$NON-NLS-1$ //$NON-NLS-2$
	}
}
