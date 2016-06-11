/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.eclipse.php.internal.ui.outline;

import java.util.*;
import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.eclipse.dltk.internal.ui.filters.FilterDescriptor;
import org.eclipse.dltk.internal.ui.filters.FilterMessages;
import org.eclipse.dltk.internal.ui.util.SWTUtil;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.activities.WorkbenchActivityHelper;
import org.eclipse.ui.dialogs.SelectionDialog;

@SuppressWarnings("restriction")
public class CustomFiltersDialog extends SelectionDialog {

	private static final String SEPARATOR = ","; //$NON-NLS-1$

	private String fViewId;
	private boolean fEnablePatterns;
	private String[] fPatterns;
	private String[] fEnabledFilterIds;

	private FilterDescriptor[] fBuiltInFilters;

	private CheckboxTableViewer fCheckBoxList;
	private Button fEnableUserDefinedPatterns;
	private Text fUserDefinedPatterns;

	private Stack<FilterDescriptor> fFilterDescriptorChangeHistory;

	/**
	 * Creates a dialog to customize script element filters.
	 * 
	 * @param shell
	 *            the parent shell
	 * @param viewId
	 *            the id of the view
	 * @param enablePatterns
	 *            <code>true</code> if pattern filters are enabled
	 * @param patterns
	 *            the filter patterns
	 * @param enabledFilterIds
	 *            the Ids of the enabled filters
	 */
	public CustomFiltersDialog(Shell shell, String viewId, boolean enablePatterns, String[] patterns,
			String[] enabledFilterIds) {

		super(shell);
		Assert.isNotNull(viewId);
		Assert.isNotNull(patterns);
		Assert.isNotNull(enabledFilterIds);

		fViewId = viewId;
		fPatterns = patterns;
		fEnablePatterns = enablePatterns;
		fEnabledFilterIds = enabledFilterIds;

		fBuiltInFilters = getFilterDescriptors(fViewId);
		fFilterDescriptorChangeHistory = new Stack<FilterDescriptor>();
		setShellStyle(getShellStyle() | SWT.RESIZE);
	}

	/**
	 * Returns all element filters which are contributed to the given view.
	 */
	public static FilterDescriptor[] getFilterDescriptors(String targetId) {
		FilterDescriptor[] filterDescs = FilterDescriptor.getFilterDescriptors();
		List<FilterDescriptor> result = new ArrayList<FilterDescriptor>(filterDescs.length);
		for (int i = 0; i < filterDescs.length; i++) {
			String tid = filterDescs[i].getTargetId();
			if (WorkbenchActivityHelper.filterItem(filterDescs[i]))
				continue;
			if (targetId.equals(tid))// exactly equal
				result.add(filterDescs[i]);
		}
		return result.toArray(new FilterDescriptor[result.size()]);
	}

	protected void configureShell(Shell shell) {
		setTitle(FilterMessages.CustomFiltersDialog_title);
		setMessage(FilterMessages.CustomFiltersDialog_filterList_label);
		super.configureShell(shell);
		// TODO: help system
		// PlatformUI.getWorkbench().getHelpSystem().setHelp(shell,
		// IDLTKHelpContextIds.CUSTOM_FILTERS_DIALOG);
	}

	/**
	 * Overrides method in Dialog
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(Composite)
	 */
	protected Control createDialogArea(Composite parent) {
		initializeDialogUnits(parent);
		// create a composite with standard margins and spacing
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.marginHeight = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_MARGIN);
		layout.marginWidth = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_MARGIN);
		layout.verticalSpacing = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_SPACING);
		layout.horizontalSpacing = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_SPACING);
		composite.setLayout(layout);
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		composite.setFont(parent.getFont());
		Composite group = composite;

		// Checkbox
		fEnableUserDefinedPatterns = new Button(group, SWT.CHECK);
		fEnableUserDefinedPatterns.setFocus();
		fEnableUserDefinedPatterns.setText(FilterMessages.CustomFiltersDialog_enableUserDefinedPattern);

		// Pattern field
		fUserDefinedPatterns = new Text(group, SWT.SINGLE | SWT.BORDER);
		GridData data = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL);
		data.widthHint = convertWidthInCharsToPixels(59);
		fUserDefinedPatterns.setLayoutData(data);
		String patterns = convertToString(fPatterns, SEPARATOR);
		fUserDefinedPatterns.setText(patterns);

		// Info text
		final Label info = new Label(group, SWT.LEFT);
		info.setText(FilterMessages.CustomFiltersDialog_patternInfo);

		// Enabling / disabling of pattern group
		fEnableUserDefinedPatterns.setSelection(fEnablePatterns);
		fUserDefinedPatterns.setEnabled(fEnablePatterns);
		info.setEnabled(fEnablePatterns);
		fEnableUserDefinedPatterns.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				boolean state = fEnableUserDefinedPatterns.getSelection();
				fUserDefinedPatterns.setEnabled(state);
				info.setEnabled(fEnableUserDefinedPatterns.getSelection());
				if (state)
					fUserDefinedPatterns.setFocus();
			}
		});

		// Filters provided by extension point
		if (fBuiltInFilters.length > 0)
			createCheckBoxList(group);

		applyDialogFont(parent);
		return parent;
	}

	private void createCheckBoxList(Composite parent) {
		// Filler
		new Label(parent, SWT.NONE);

		Label info = new Label(parent, SWT.LEFT);
		info.setText(FilterMessages.CustomFiltersDialog_filterList_label);

		fCheckBoxList = CheckboxTableViewer.newCheckList(parent, SWT.BORDER);
		GridData data = new GridData(GridData.FILL_BOTH);
		data.heightHint = fCheckBoxList.getTable().getItemHeight() * 10;
		fCheckBoxList.getTable().setLayoutData(data);

		fCheckBoxList.setLabelProvider(createLabelPrivder());
		fCheckBoxList.setContentProvider(new ArrayContentProvider());
		Arrays.sort(fBuiltInFilters);
		fCheckBoxList.setInput(fBuiltInFilters);
		setInitialSelections(getEnabledFilterDescriptors());

		List<?> initialSelection = getInitialElementSelections();
		if (initialSelection != null && !initialSelection.isEmpty())
			checkInitialSelections();

		// Description
		info = new Label(parent, SWT.LEFT);
		info.setText(FilterMessages.CustomFiltersDialog_description_label);
		final Text description = new Text(parent,
				SWT.LEFT | SWT.WRAP | SWT.MULTI | SWT.READ_ONLY | SWT.BORDER | SWT.V_SCROLL);
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.heightHint = convertHeightInCharsToPixels(3);
		description.setLayoutData(data);
		fCheckBoxList.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				ISelection selection = event.getSelection();
				if (selection instanceof IStructuredSelection) {
					Object selectedElement = ((IStructuredSelection) selection).getFirstElement();
					if (selectedElement instanceof FilterDescriptor)
						description.setText(((FilterDescriptor) selectedElement).getDescription());
				}
			}
		});
		fCheckBoxList.addCheckStateListener(new ICheckStateListener() {
			/*
			 * @see
			 * org.eclipse.jface.viewers.ICheckStateListener#checkStateChanged
			 * (org.eclipse.jface.viewers.CheckStateChangedEvent)
			 */
			public void checkStateChanged(CheckStateChangedEvent event) {
				Object element = event.getElement();
				if (element instanceof FilterDescriptor) {
					// renew if already touched
					FilterDescriptor fd = (FilterDescriptor) element;
					if (fFilterDescriptorChangeHistory.contains(fd)) {
						fFilterDescriptorChangeHistory.remove(fd);
					}
					fFilterDescriptorChangeHistory.push(fd);
				}
			}
		});

		addSelectionButtons(parent);
	}

	private void addSelectionButtons(Composite composite) {
		Composite buttonComposite = new Composite(composite, SWT.RIGHT);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		buttonComposite.setLayout(layout);
		GridData data = new GridData(GridData.HORIZONTAL_ALIGN_END | GridData.GRAB_HORIZONTAL);
		data.grabExcessHorizontalSpace = true;
		composite.setData(data);

		// Select All button
		String label = FilterMessages.CustomFiltersDialog_SelectAllButton_label;
		Button selectButton = createButton(buttonComposite, IDialogConstants.SELECT_ALL_ID, label, false);
		SWTUtil.setButtonDimensionHint(selectButton);
		SelectionListener listener = new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				fCheckBoxList.setAllChecked(true);
				fFilterDescriptorChangeHistory.clear();
				for (int i = 0; i < fBuiltInFilters.length; i++)
					fFilterDescriptorChangeHistory.push(fBuiltInFilters[i]);
			}
		};
		selectButton.addSelectionListener(listener);

		// De-select All button
		label = FilterMessages.CustomFiltersDialog_DeselectAllButton_label;
		Button deselectButton = createButton(buttonComposite, IDialogConstants.DESELECT_ALL_ID, label, false);
		SWTUtil.setButtonDimensionHint(deselectButton);
		listener = new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				fCheckBoxList.setAllChecked(false);
				fFilterDescriptorChangeHistory.clear();
				for (int i = 0; i < fBuiltInFilters.length; i++)
					fFilterDescriptorChangeHistory.push(fBuiltInFilters[i]);
			}
		};
		deselectButton.addSelectionListener(listener);
	}

	private void checkInitialSelections() {
		Iterator<?> itemsToCheck = getInitialElementSelections().iterator();
		while (itemsToCheck.hasNext())
			fCheckBoxList.setChecked(itemsToCheck.next(), true);
	}

	protected void okPressed() {
		if (fBuiltInFilters != null) {
			ArrayList<FilterDescriptor> result = new ArrayList<FilterDescriptor>();
			for (int i = 0; i < fBuiltInFilters.length; ++i) {
				if (fCheckBoxList.getChecked(fBuiltInFilters[i]))
					result.add(fBuiltInFilters[i]);
			}
			setResult(result);
		}
		super.okPressed();
	}

	private ILabelProvider createLabelPrivder() {
		return new LabelProvider() {
			public Image getImage(Object element) {
				return null;
			}

			public String getText(Object element) {
				if (element instanceof FilterDescriptor)
					return ((FilterDescriptor) element).getName();
				else
					return null;
			}
		};
	}

	// ---------- result handling ----------
	@SuppressWarnings("rawtypes")
	@Override
	protected void setResult(List newResult) {
		super.setResult(newResult);
		if (fUserDefinedPatterns.getText().length() > 0) {
			fEnablePatterns = fEnableUserDefinedPatterns.getSelection();
			fPatterns = convertFromString(fUserDefinedPatterns.getText(), SEPARATOR);
		} else {
			fEnablePatterns = false;
			fPatterns = new String[0];
		}
	}

	/**
	 * @return the patterns which have been entered by the user
	 */
	public String[] getUserDefinedPatterns() {
		return fPatterns;
	}

	/**
	 * @return the Ids of the enabled built-in filters
	 */
	public String[] getEnabledFilterIds() {
		Object[] result = getResult();
		Set<String> enabledIds = new HashSet<String>(result.length);
		for (int i = 0; i < result.length; i++)
			enabledIds.add(((FilterDescriptor) result[i]).getId());
		return (String[]) enabledIds.toArray(new String[enabledIds.size()]);
	}

	/**
	 * @return <code>true</code> if the user-defined patterns are disabled
	 */
	public boolean areUserDefinedPatternsEnabled() {
		return fEnablePatterns;
	}

	/**
	 * @return a stack with the filter descriptor check history
	 * 
	 */
	public Stack<FilterDescriptor> getFilterDescriptorChangeHistory() {
		return fFilterDescriptorChangeHistory;
	}

	private FilterDescriptor[] getEnabledFilterDescriptors() {
		FilterDescriptor[] filterDescs = fBuiltInFilters;
		List<FilterDescriptor> result = new ArrayList<FilterDescriptor>(filterDescs.length);
		List<String> enabledFilterIds = Arrays.asList(fEnabledFilterIds);
		for (int i = 0; i < filterDescs.length; i++) {
			String id = filterDescs[i].getId();
			if (enabledFilterIds.contains(id))
				result.add(filterDescs[i]);
		}
		return result.toArray(new FilterDescriptor[result.size()]);
	}

	public static String[] convertFromString(String patterns, String separator) {
		StringTokenizer tokenizer = new StringTokenizer(patterns, separator, true);
		int tokenCount = tokenizer.countTokens();
		List<String> result = new ArrayList<String>(tokenCount);
		boolean escape = false;
		boolean append = false;
		while (tokenizer.hasMoreTokens()) {
			String token = tokenizer.nextToken().trim();
			if (separator.equals(token)) {
				if (!escape)
					escape = true;
				else {
					addPattern(result, separator);
					append = true;
				}
			} else {
				if (!append)
					result.add(token);
				else
					addPattern(result, token);
				append = false;
				escape = false;
			}
		}
		return result.toArray(new String[result.size()]);
	}

	private static void addPattern(List<String> list, String pattern) {
		if (list.isEmpty())
			list.add(pattern);
		else {
			int index = list.size() - 1;
			list.set(index, ((String) list.get(index)) + pattern);
		}
	}

	public static String convertToString(String[] patterns, String separator) {
		int length = patterns.length;
		StringBuilder strBuf = new StringBuilder();
		if (length > 0)
			strBuf.append(escapeSeparator(patterns[0], separator));
		else
			return ""; //$NON-NLS-1$
		int i = 1;
		while (i < length) {
			strBuf.append(separator);
			strBuf.append(" "); //$NON-NLS-1$
			strBuf.append(escapeSeparator(patterns[i++], separator));
		}
		return strBuf.toString();
	}

	private static String escapeSeparator(String pattern, String separator) {
		int length = pattern.length();
		StringBuilder buf = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			char ch = pattern.charAt(i);
			if (separator.equals(String.valueOf(ch)))
				buf.append(ch);
			buf.append(ch);
		}
		return buf.toString();

	}
}
