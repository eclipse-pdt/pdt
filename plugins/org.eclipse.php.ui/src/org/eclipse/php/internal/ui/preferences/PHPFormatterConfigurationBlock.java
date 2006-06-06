/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.preferences;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.php.core.PHPCoreConstants;
import org.eclipse.php.ui.preferences.IStatusChangeListener;
import org.eclipse.php.ui.util.StatusInfo;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.preferences.IWorkbenchPreferenceContainer;
import org.eclipse.wst.xml.ui.internal.XMLUIMessages;

/**
 * @author guy.g
 *
 */
public class PHPFormatterConfigurationBlock extends PHPCoreOptionsConfigurationBlock implements ModifyListener, SelectionListener {

	private static final Key PREF_FORMATTER_USE_TABS = getPHPCoreKey(PHPCoreConstants.FORMATTER_USE_TABS);
	private static final Key PREF_FORMATTER_INDENTATION_SIZE = getPHPCoreKey(PHPCoreConstants.FORMATTER_INDENTATION_SIZE);

	private IStatus fFormatterStatus;

	private final int MIN_INDENTATION_SIZE = 0;
	private final int MAX_INDENTATION_SIZE = 16;

	private Button fIndentUsingTabs;
	private Button fIndentUsingSpaces;
	private Spinner fIndentationSizeSpinner;

	public PHPFormatterConfigurationBlock(IStatusChangeListener context, IProject project, IWorkbenchPreferenceContainer container) {
		super(context, project, getKeys(), container);

		fFormatterStatus = new StatusInfo();
		initValues();
	}

	protected Control createContents(Composite parent) {
		setShell(parent.getShell());

		Composite markersComposite = createFormaterContent(parent);

		validateSettings(null, null, null);

		return markersComposite;
	}

	private Composite createFormaterContent(Composite parent) {

		Composite formattingComposite = createComposite(parent, 2);

		fIndentUsingTabs = createRadioButton(formattingComposite, XMLUIMessages.Indent_using_tabs);
		((GridData) fIndentUsingTabs.getLayoutData()).horizontalSpan = 2;
		fIndentUsingTabs.addSelectionListener(this);

		fIndentUsingSpaces = createRadioButton(formattingComposite, XMLUIMessages.Indent_using_spaces);
		((GridData) fIndentUsingSpaces.getLayoutData()).horizontalSpan = 2;

		createLabel(formattingComposite, XMLUIMessages.Indentation_size);
		fIndentationSizeSpinner = new Spinner(formattingComposite, SWT.READ_ONLY | SWT.BORDER);
		GridData gd = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
		fIndentationSizeSpinner.setLayoutData(gd);
		fIndentationSizeSpinner.setToolTipText(XMLUIMessages.Indentation_size_tip);
		fIndentationSizeSpinner.setMinimum(MIN_INDENTATION_SIZE);
		fIndentationSizeSpinner.setMaximum(MAX_INDENTATION_SIZE);
		fIndentationSizeSpinner.setIncrement(1);
		fIndentationSizeSpinner.setPageIncrement(4);
		fIndentationSizeSpinner.addModifyListener(this);

		updateValues();
		return formattingComposite;
	}

	protected void validateSettings(Key changedKey, String oldValue, String newValue) {
		if (changedKey != null) {
			if (PREF_FORMATTER_INDENTATION_SIZE.equals(changedKey)) {
				fFormatterStatus = validate();
			} else {
				return;
			}
		} else {
			fFormatterStatus = validate();
		}
		IStatus status = fFormatterStatus;
		fContext.statusChanged(status);

	}

	protected String[] getFullBuildDialogStrings(boolean workspaceSettings) {
		return null;
	}

	private IStatus validate() {
		return new StatusInfo();
	}

	private static Key[] getKeys() {
		return new Key[] { PREF_FORMATTER_USE_TABS, PREF_FORMATTER_INDENTATION_SIZE };
	}

	private Composite createComposite(Composite parent, int numColumns) {
		Composite composite = new Composite(parent, SWT.NULL);

		//GridLayout
		GridLayout layout = new GridLayout();
		layout.numColumns = numColumns;
		composite.setLayout(layout);

		//GridData
		GridData data = new GridData(GridData.FILL);
		data.horizontalIndent = 0;
		data.verticalAlignment = GridData.FILL;
		data.horizontalAlignment = GridData.FILL;
		data.grabExcessHorizontalSpace = true;
		composite.setLayoutData(data);

		return composite;
	}

	private Button createRadioButton(Composite group, String label) {
		Button button = new Button(group, SWT.RADIO);
		button.setText(label);

		//GridData
		GridData data = new GridData(GridData.FILL);
		data.verticalAlignment = GridData.CENTER;
		data.horizontalAlignment = GridData.FILL;
		button.setLayoutData(data);

		return button;
	}

	private Label createLabel(Composite parent, String text) {
		Label label = new Label(parent, SWT.LEFT);
		label.setText(text);

		//GridData
		GridData data = new GridData(GridData.FILL);
		data.verticalAlignment = GridData.CENTER;
		data.horizontalAlignment = GridData.FILL;
		label.setLayoutData(data);

		return label;
	}

	public void modifyText(ModifyEvent e) {
		// If we are called too early, i.e. before the controls are created
		// then return
		// to avoid null pointer exceptions
		if (e.widget != null && e.widget.isDisposed())
			return;

		validateValues();
		enableValues(e.widget);
	}

	protected void validateValues() {
	}

	protected void enableValues(Widget w) {
		fIndentationSize = ((Spinner) w).getSelection();
		setValue(PREF_FORMATTER_INDENTATION_SIZE, String.valueOf(fIndentationSize));
	}

	protected void updateControls() {
		initValues();
		updateValues();
	}

	private boolean fUseTabs;
	private int fIndentationSize;

	private void initValues() {
		String useTabs = getValue(PREF_FORMATTER_USE_TABS);
		String indentationSize = getValue(PREF_FORMATTER_INDENTATION_SIZE);

		fUseTabs = Boolean.valueOf(useTabs).booleanValue();
		fIndentationSize = Integer.valueOf(indentationSize).intValue();

	}

	private void updateValues() {
		fIndentUsingTabs.setSelection(fUseTabs);
		fIndentUsingSpaces.setSelection(!fUseTabs);
		fIndentationSizeSpinner.setSelection(fIndentationSize);
	}

	public void widgetSelected(SelectionEvent e) {
		updateButtonStatus((Button) e.widget);
	}

	public void widgetDefaultSelected(SelectionEvent e) {
		updateButtonStatus((Button) e.widget);
	}

	private void updateButtonStatus(Button b) {
		fUseTabs = b.getSelection();
		setValue(PREF_FORMATTER_USE_TABS, fUseTabs ? "true" : "false");
	}
}
