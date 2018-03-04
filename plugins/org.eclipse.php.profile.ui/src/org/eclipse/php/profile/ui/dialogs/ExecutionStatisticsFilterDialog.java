/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.profile.ui.dialogs;

import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.dialogs.StatusDialog;
import org.eclipse.jface.viewers.*;
import org.eclipse.php.internal.ui.util.PixelConverter;
import org.eclipse.php.internal.ui.util.SWTUtil;
import org.eclipse.php.internal.ui.util.StatusInfo;
import org.eclipse.php.profile.ui.PHPProfileUIMessages;
import org.eclipse.php.profile.ui.ProfilerUIImages;
import org.eclipse.php.profile.ui.filters.ExecutionStatisticsFieldFilter;
import org.eclipse.php.profile.ui.filters.ExecutionStatisticsFilter;
import org.eclipse.php.profile.ui.filters.ExecutionStatisticsFilterCondition;
import org.eclipse.php.profile.ui.filters.ExecutionStatisticsFilterString;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

/**
 * Execution statistics filter dialog.
 */
public class ExecutionStatisticsFilterDialog extends StatusDialog {

	private ExecutionStatisticsFilter fFilter;
	private Image fFilterImage;

	private List<ExecutionStatisticsFilter> fExistingFilters;

	private CTabFolder fTabFolder;
	private CTabItem fStandardTab;
	private CTabItem fAdvancedTab;

	private Text fFilterName;

	private Text fFilterString;
	private Button fCaseSensitive;
	private Button fFilterByFile;
	private Button fFilterByClass;
	private Button fFilterByFunction;
	private Button fShowInformationMatching;
	private Combo fDescriptor;
	private Text fNumber;
	private Combo fField;
	private Label fShowLabel;
	private Label fByLabel;

	private Combo fCondAttribute;
	private Combo fCondOperator;
	private Text fCondValue;
	private Table fCondTable;
	private TableViewer fCondTableViewer;
	private Button fAddButton;
	private Button fDeleteButton;

	private static final String[] fDescriptors = { PHPProfileUIMessages.getString("ExecutionStatisticsFilterDialog.4"), //$NON-NLS-1$
			PHPProfileUIMessages.getString("ExecutionStatisticsFilterDialog.5"), }; //$NON-NLS-1$
	private static final String[] fDescriptorsData = { ExecutionStatisticsFilterString.FILTER_BY_FILE_NAME,
			ExecutionStatisticsFilterString.FILTER_BY_CLASS_NAME,
			ExecutionStatisticsFilterString.FILTER_BY_FUNCTION_NAME, };

	private static final String[] fFields = { PHPProfileUIMessages.getString("ExecutionStatisticsFilterDialog.6"), //$NON-NLS-1$
			PHPProfileUIMessages.getString("ExecutionStatisticsFilterDialog.7"), //$NON-NLS-1$
			PHPProfileUIMessages.getString("ExecutionStatisticsFilterDialog.8"), //$NON-NLS-1$
			PHPProfileUIMessages.getString("ExecutionStatisticsFilterDialog.9"), //$NON-NLS-1$
			PHPProfileUIMessages.getString("ExecutionStatisticsFilterDialog.10"), }; //$NON-NLS-1$
	private static final String[] fFieldsData = { ExecutionStatisticsFieldFilter.FIELD_CALLS_COUNT,
			ExecutionStatisticsFieldFilter.FIELD_AVERAGE_OWN_TIME, ExecutionStatisticsFieldFilter.FIELD_OWN_TIME,
			ExecutionStatisticsFieldFilter.FIELD_OTHERS_TIME, ExecutionStatisticsFieldFilter.FIELD_TOTAL_TIME };

	private static final String[] fAttributes = { PHPProfileUIMessages.getString("ExecutionStatisticsFilterDialog.11"), //$NON-NLS-1$
			PHPProfileUIMessages.getString("ExecutionStatisticsFilterDialog.12"), //$NON-NLS-1$
			PHPProfileUIMessages.getString("ExecutionStatisticsFilterDialog.13"), //$NON-NLS-1$
			PHPProfileUIMessages.getString("ExecutionStatisticsFilterDialog.14"), //$NON-NLS-1$
			PHPProfileUIMessages.getString("ExecutionStatisticsFilterDialog.15"), //$NON-NLS-1$
			PHPProfileUIMessages.getString("ExecutionStatisticsFilterDialog.16"), //$NON-NLS-1$
			PHPProfileUIMessages.getString("ExecutionStatisticsFilterDialog.17"), //$NON-NLS-1$
			PHPProfileUIMessages.getString("ExecutionStatisticsFilterDialog.18"), //$NON-NLS-1$
			PHPProfileUIMessages.getString("ExecutionStatisticsFilterDialog.19"), //$NON-NLS-1$
			PHPProfileUIMessages.getString("ExecutionStatisticsFilterDialog.20"), }; //$NON-NLS-1$
	private static final String[] fAttributesData = { ExecutionStatisticsFilterCondition.ATTR_FILE_NAME,
			ExecutionStatisticsFilterCondition.ATTR_CLASS_NAME, ExecutionStatisticsFilterCondition.ATTR_FUNCTION_NAME,
			ExecutionStatisticsFilterCondition.ATTR_FILE_TOTAL_TIME,
			ExecutionStatisticsFilterCondition.ATTR_CLASS_TOTAL_TIME,
			ExecutionStatisticsFilterCondition.ATTR_FUNCTION_TOTAL_TIME,
			ExecutionStatisticsFilterCondition.ATTR_FUNCTION_AVERAGE_OWN_TIME,
			ExecutionStatisticsFilterCondition.ATTR_FUNCTION_OWN_TIME,
			ExecutionStatisticsFilterCondition.ATTR_FUNCTION_OTHERS_TIME,
			ExecutionStatisticsFilterCondition.ATTR_FUNCTION_CALLS_COUNT, };

	private static final String[] fOperators = { PHPProfileUIMessages.getString("ExecutionStatisticsFilterDialog.21"), //$NON-NLS-1$
			PHPProfileUIMessages.getString("ExecutionStatisticsFilterDialog.22"), //$NON-NLS-1$
			PHPProfileUIMessages.getString("ExecutionStatisticsFilterDialog.23"), //$NON-NLS-1$
			PHPProfileUIMessages.getString("ExecutionStatisticsFilterDialog.24"), //$NON-NLS-1$
			PHPProfileUIMessages.getString("ExecutionStatisticsFilterDialog.25"), //$NON-NLS-1$
			PHPProfileUIMessages.getString("ExecutionStatisticsFilterDialog.26"), }; //$NON-NLS-1$
	private static final String[] fOperatorsData = { ExecutionStatisticsFilterCondition.OP_EQUALS,
			ExecutionStatisticsFilterCondition.OP_NOT_EQUALS, ExecutionStatisticsFilterCondition.OP_MATCHES,
			ExecutionStatisticsFilterCondition.OP_DOESNT_MATCH, ExecutionStatisticsFilterCondition.OP_LESS_THAN,
			ExecutionStatisticsFilterCondition.OP_MORE_THAN, };

	private static final String[] fCondTableColumns = {
			PHPProfileUIMessages.getString("ExecutionStatisticsFilterDialog.36"), //$NON-NLS-1$
			PHPProfileUIMessages.getString("ExecutionStatisticsFilterDialog.37"), //$NON-NLS-1$
			PHPProfileUIMessages.getString("ExecutionStatisticsFilterDialog.38"), }; //$NON-NLS-1$
	private static final int[] fCondTableColumnsWidths = { 30, 15, 30, };

	public ExecutionStatisticsFilterDialog(Shell parent, ExecutionStatisticsFilter filter,
			List<ExecutionStatisticsFilter> existingFilters) {
		super(parent);

		setShellStyle(getShellStyle() | SWT.RESIZE);
		setTitle(filter == null ? PHPProfileUIMessages.getString("ExecutionStatisticsFilterDialog.49") //$NON-NLS-1$
				: PHPProfileUIMessages.getString("ExecutionStatisticsFilterDialog.0")); //$NON-NLS-1$

		fFilter = filter;
		fExistingFilters = existingFilters;
		fFilterImage = ProfilerUIImages.get(ProfilerUIImages.IMG_OBJ_FILTER);
	}

	private void addStandardFilterTab(CTabFolder folder) {
		Composite page = new Composite(folder, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.marginHeight = 10;
		layout.marginWidth = 10;
		page.setLayout(layout);
		page.setLayoutData(new GridData(GridData.FILL_BOTH));

		Composite filterStringComposite = new Composite(page, SWT.NONE);
		filterStringComposite.setLayout(new GridLayout());
		filterStringComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		Label label = new Label(filterStringComposite, SWT.NONE);
		label.setText(PHPProfileUIMessages.getString("ExecutionStatisticsFilterDialog.1")); //$NON-NLS-1$

		fFilterString = new Text(filterStringComposite, SWT.BORDER);
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalIndent = 10;
		fFilterString.setLayoutData(data);

		fCaseSensitive = new Button(filterStringComposite, SWT.CHECK);
		fCaseSensitive.setText(PHPProfileUIMessages.getString("ExecutionStatisticsFilterDialog.42")); //$NON-NLS-1$

		Composite filterByComposite = new Composite(page, SWT.NONE);
		filterByComposite.setLayout(new GridLayout());
		filterByComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		label = new Label(filterByComposite, SWT.NONE);
		label.setText(PHPProfileUIMessages.getString("ExecutionStatisticsFilterDialog.27")); //$NON-NLS-1$

		Composite filterByGroup = new Composite(filterByComposite, SWT.NONE);
		layout = new GridLayout();
		filterByGroup.setLayout(layout);
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalIndent = 10;
		filterByGroup.setLayoutData(data);

		fFilterByFile = new Button(filterByGroup, SWT.RADIO);
		fFilterByFile.setText(PHPProfileUIMessages.getString("ExecutionStatisticsFilterDialog.28")); //$NON-NLS-1$

		fFilterByClass = new Button(filterByGroup, SWT.RADIO);
		fFilterByClass.setText(PHPProfileUIMessages.getString("ExecutionStatisticsFilterDialog.29")); //$NON-NLS-1$

		fFilterByFunction = new Button(filterByGroup, SWT.RADIO);
		fFilterByFunction.setText(PHPProfileUIMessages.getString("ExecutionStatisticsFilterDialog.30")); //$NON-NLS-1$
		fFilterByFunction.setSelection(true); // default

		Composite showInformationMatchingGroup = new Composite(page, SWT.NONE);
		layout = new GridLayout();
		layout.numColumns = 5;
		showInformationMatchingGroup.setLayout(layout);
		showInformationMatchingGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		fShowInformationMatching = new Button(showInformationMatchingGroup, SWT.CHECK);
		fShowInformationMatching.setText(PHPProfileUIMessages.getString("ExecutionStatisticsFilterDialog.31")); //$NON-NLS-1$
		data = new GridData();
		data.horizontalSpan = 5;
		fShowInformationMatching.setLayoutData(data);
		fShowInformationMatching.addSelectionListener(new SelectionAdapter() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org. eclipse
			 * .swt.events.SelectionEvent)
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				showInformationMatchingSelected();
			}
		});

		fShowLabel = new Label(showInformationMatchingGroup, SWT.NONE);
		fShowLabel.setText(PHPProfileUIMessages.getString("ExecutionStatisticsFilterDialog.32")); //$NON-NLS-1$

		fDescriptor = new Combo(showInformationMatchingGroup, SWT.READ_ONLY);
		for (int i = 0; i < fDescriptors.length; ++i) {
			fDescriptor.add(fDescriptors[i]);
			fDescriptor.setData(fDescriptors[i], fDescriptorsData[i]);
		}
		fDescriptor.select(1);
		fDescriptor.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		fNumber = new Text(showInformationMatchingGroup, SWT.BORDER);
		fNumber.setText("10"); //$NON-NLS-1$
		fNumber.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		fNumber.addModifyListener(new ModifyListener() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see org.eclipse.swt.events.ModifyListener#modifyText(org.eclipse.
			 * swt.events.ModifyEvent)
			 */
			@Override
			public void modifyText(ModifyEvent e) {
				ExecutionStatisticsFilterDialog.this.validateFieldFilter();
			}
		});

		fByLabel = new Label(showInformationMatchingGroup, SWT.NONE);
		fByLabel.setText(PHPProfileUIMessages.getString("ExecutionStatisticsFilterDialog.34")); //$NON-NLS-1$

		fField = new Combo(showInformationMatchingGroup, SWT.READ_ONLY);
		for (int i = 0; i < fFields.length; ++i) {
			fField.add(fFields[i]);
			fField.setData(fFields[i], fFieldsData[i]);
		}
		fField.select(2);
		fField.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		fStandardTab = new CTabItem(folder, SWT.NONE);
		fStandardTab.setText(PHPProfileUIMessages.getString("ExecutionStatisticsFilterDialog.2")); //$NON-NLS-1$
		fStandardTab.setImage(fFilterImage);
		fStandardTab.setControl(page);
		folder.setSelection(fStandardTab);
	}

	private void updateAddDeleteButtonsStatus() {
		fAddButton.setEnabled(fCondAttribute.getText().length() > 0 && fCondOperator.getText().length() > 0);
		fDeleteButton.setEnabled(((IStructuredSelection) fCondTableViewer.getSelection()).getFirstElement() != null);
	}

	private void addAdvancedFilterTab(CTabFolder folder) {
		Composite page = new Composite(folder, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.marginHeight = 10;
		layout.marginWidth = 10;
		page.setLayout(layout);
		page.setLayoutData(new GridData(GridData.FILL_BOTH));

		PixelConverter pixelConverter = new PixelConverter(page);

		// Condition composite:
		Composite condComposite = new Composite(page, SWT.NONE);
		layout = new GridLayout();
		layout.numColumns = 3;
		condComposite.setLayout(layout);
		condComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		// Condition:
		Label label = new Label(condComposite, SWT.NONE);
		label.setText(PHPProfileUIMessages.getString("ExecutionStatisticsFilterDialog.35")); //$NON-NLS-1$
		GridData data = new GridData();
		data.horizontalSpan = 3;
		label.setLayoutData(data);

		SelectionAdapter fAddDeleteButtonsStatusUpdater = new SelectionAdapter() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org. eclipse
			 * .swt.events.SelectionEvent)
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				updateAddDeleteButtonsStatus();
				ExecutionStatisticsFilterDialog.this.validateCondition();
			}
		};

		fCondAttribute = new Combo(condComposite, SWT.READ_ONLY);
		for (int i = 0; i < fAttributes.length; ++i) {
			fCondAttribute.add(fAttributes[i]);
			fCondAttribute.setData(fAttributes[i], fAttributesData[i]);
		}
		fCondAttribute.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		fCondAttribute.addSelectionListener(fAddDeleteButtonsStatusUpdater);

		fCondOperator = new Combo(condComposite, SWT.READ_ONLY);
		for (int i = 0; i < fOperators.length; ++i) {
			fCondOperator.add(fOperators[i]);
			fCondOperator.setData(fOperators[i], fOperatorsData[i]);
		}
		fCondOperator.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		fCondOperator.addSelectionListener(fAddDeleteButtonsStatusUpdater);

		fCondValue = new Text(condComposite, SWT.BORDER);
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.widthHint = pixelConverter.convertWidthInCharsToPixels(30);
		fCondValue.setLayoutData(data);
		fCondValue.addModifyListener(new ModifyListener() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see org.eclipse.swt.events.ModifyListener#modifyText(org.eclipse.
			 * swt.events.ModifyEvent)
			 */
			@Override
			public void modifyText(ModifyEvent e) {
				ExecutionStatisticsFilterDialog.this.validateCondition();
			}
		});

		// Table composite
		Composite condTableComposite = new Composite(page, SWT.NONE);
		layout = new GridLayout();
		layout.numColumns = 2;
		condTableComposite.setLayout(layout);
		condTableComposite.setLayoutData(new GridData(GridData.FILL_BOTH));

		// Table:
		fCondTable = new Table(condTableComposite,
				SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER | SWT.MULTI | SWT.FULL_SELECTION);
		data = new GridData(GridData.FILL_BOTH);
		fCondTable.setLayoutData(data);
		fCondTable.setHeaderVisible(true);
		fCondTable.setLinesVisible(true);

		TableLayout tableLayout = new TableLayout();

		for (int i = 0; i < fCondTableColumns.length; ++i) {
			int width = pixelConverter.convertWidthInCharsToPixels(fCondTableColumnsWidths[i]);
			tableLayout.addColumnData(new ColumnWeightData(width, true));
			TableColumn column = new TableColumn(fCondTable, SWT.NONE);
			column.setText(fCondTableColumns[i]);
		}

		fCondTable.setLayout(tableLayout);

		fCondTableViewer = new TableViewer(fCondTable);
		fCondTableViewer.setUseHashlookup(true);
		fCondTableViewer.setContentProvider(new CondTableContentProvider());
		fCondTableViewer.setLabelProvider(new CondTableLabelProvider());

		fCondTableViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			/*
			 * (non-Javadoc)
			 * 
			 * @seeorg.eclipse.jface.viewers.ISelectionChangedListener# selectionChanged
			 * (org.eclipse.jface.viewers.SelectionChangedEvent)
			 */
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection sSelection = (IStructuredSelection) fCondTableViewer.getSelection();
				ExecutionStatisticsFilterCondition cond = (ExecutionStatisticsFilterCondition) sSelection
						.getFirstElement();
				if (cond != null) {
					fCondAttribute.setText(cond.getAttribute());
					fCondOperator.setText(cond.getOperator());
					fCondValue.setText(cond.getValue());
				}
				updateAddDeleteButtonsStatus();
			}
		});

		// Buttons bar:
		Composite buttonsBar = new Composite(condTableComposite, SWT.NONE);
		layout = new GridLayout();
		buttonsBar.setLayout(layout);
		buttonsBar.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));

		fAddButton = new Button(buttonsBar, SWT.NONE);
		fAddButton.setText(PHPProfileUIMessages.getString("ExecutionStatisticsFilterDialog.39")); //$NON-NLS-1$
		fAddButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ExecutionStatisticsFilterCondition cond = new ExecutionStatisticsFilterCondition();
				cond.setAttribute(fCondAttribute.getText());
				cond.setOperator(fCondOperator.getText());
				cond.setValue(fCondValue.getText());
				fCondTableViewer.add(cond);
			}
		});

		fDeleteButton = new Button(buttonsBar, SWT.NONE);
		fDeleteButton.setText(PHPProfileUIMessages.getString("ExecutionStatisticsFilterDialog.40")); //$NON-NLS-1$
		fDeleteButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				IStructuredSelection sSelection = (IStructuredSelection) fCondTableViewer.getSelection();
				ExecutionStatisticsFilterCondition cond = (ExecutionStatisticsFilterCondition) sSelection
						.getFirstElement();
				fCondTableViewer.remove(cond);
			}
		});

		fAdvancedTab = new CTabItem(folder, SWT.NONE);
		fAdvancedTab.setText(PHPProfileUIMessages.getString("ExecutionStatisticsFilterDialog.3")); //$NON-NLS-1$
		fAdvancedTab.setImage(fFilterImage);
		fAdvancedTab.setControl(page);
	}

	private void validateFilterName() {
		IStatus status = StatusInfo.OK_STATUS;

		String filterName = fFilterName.getText();
		if (filterName.length() == 0) {
			status = new StatusInfo(IStatus.ERROR,
					PHPProfileUIMessages.getString("ExecutionStatisticsFilterDialog.43")); //$NON-NLS-1$
		}
		if (fFilter == null) {
			for (int i = 0; i < fExistingFilters.size(); ++i) {
				if (fExistingFilters.get(i).getName().equals(filterName)) {
					status = new StatusInfo(IStatus.ERROR,
							PHPProfileUIMessages.getString("ExecutionStatisticsFilterDialog.44")); //$NON-NLS-1$
					break;
				}
			}
		}
		updateStatus(status);
	}

	private void validateFieldFilter() {
		IStatus status = StatusInfo.OK_STATUS;

		if (fShowInformationMatching.getSelection()) {
			String numberStr = fNumber.getText();
			if (numberStr.length() == 0) {
				status = new StatusInfo(IStatus.ERROR,
						PHPProfileUIMessages.getString("ExecutionStatisticsFilterDialog.45")); //$NON-NLS-1$
			} else {
				try {
					Integer.parseInt(numberStr);
				} catch (NumberFormatException e) {
					status = new StatusInfo(IStatus.ERROR,
							PHPProfileUIMessages.getString("ExecutionStatisticsFilterDialog.46")); //$NON-NLS-1$
				}
			}
		}
		updateStatus(status);
	}

	private void validateCondition() {
		IStatus status = StatusInfo.OK_STATUS;

		String operator = fCondOperator.getText();
		String condition = fCondValue.getText();
		if (ExecutionStatisticsFilterCondition.OP_LESS_THAN.equals(operator)
				|| ExecutionStatisticsFilterCondition.OP_MORE_THAN.equals(operator)) {
			try {
				Double.parseDouble(condition);
			} catch (NumberFormatException e) {
				status = new StatusInfo(IStatus.ERROR,
						PHPProfileUIMessages.getString("ExecutionStatisticsFilterDialog.47")); //$NON-NLS-1$
			}
		} else {
			if (condition.trim().length() == 0) {
				status = new StatusInfo(IStatus.ERROR,
						PHPProfileUIMessages.getString("ExecutionStatisticsFilterDialog.48")); //$NON-NLS-1$
			}
		}

		if (status != StatusInfo.OK_STATUS) {
			fAddButton.setEnabled(false);
		} else {
			updateAddDeleteButtonsStatus();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets
	 * .Composite)
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		parent = (Composite) super.createDialogArea(parent);

		PixelConverter pixelConverter = new PixelConverter(parent);

		// Filter Name:
		Composite nameGroup = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		nameGroup.setLayout(layout);

		Label label = new Label(nameGroup, SWT.NONE);
		label.setText(PHPProfileUIMessages.getString("ExecutionStatisticsFilterDialog.41")); //$NON-NLS-1$

		fFilterName = new Text(nameGroup, SWT.BORDER);
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.widthHint = pixelConverter.convertWidthInCharsToPixels(50);
		fFilterName.setLayoutData(data);
		fFilterName.addModifyListener(new ModifyListener() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see org.eclipse.swt.events.ModifyListener#modifyText(org.eclipse.
			 * swt.events.ModifyEvent)
			 */
			@Override
			public void modifyText(ModifyEvent e) {
				ExecutionStatisticsFilterDialog.this.validateFilterName();
			}
		});

		// Filter configuration:
		fTabFolder = SWTUtil.createTabFolder(parent);
		data = new GridData(GridData.FILL_BOTH);
		data.heightHint = pixelConverter.convertHeightInCharsToPixels(20);
		data.widthHint = pixelConverter.convertWidthInCharsToPixels(100);
		fTabFolder.setLayoutData(data);

		addStandardFilterTab(fTabFolder);
		addAdvancedFilterTab(fTabFolder);

		initializeValues();
		updateAddDeleteButtonsStatus();

		if (fFilter == null) {
			updateStatus(new StatusInfo(IStatus.ERROR, "")); //$NON-NLS-1$
		}

		return parent;
	}

	private void showInformationMatchingSelected() {
		boolean status = fShowInformationMatching.getSelection();
		fShowLabel.setEnabled(status);
		fDescriptor.setEnabled(status);
		fNumber.setEnabled(status);
		fByLabel.setEnabled(status);
		fField.setEnabled(status);
	}

	private void initializeValues() {
		if (fFilter != null) {
			fFilterName.setText(fFilter.getName());

			ExecutionStatisticsFilterString filterString = fFilter.getFilterString();
			if (filterString != null) {
				fFilterString.setText(filterString.getString() == null ? "" : filterString.getString()); //$NON-NLS-1$
				fCaseSensitive.setSelection(filterString.isCaseSensitive());
				fFilterByFile.setSelection(
						ExecutionStatisticsFilterString.FILTER_BY_FILE_NAME.equals(filterString.getFilterBy()));
				fFilterByClass.setSelection(
						ExecutionStatisticsFilterString.FILTER_BY_CLASS_NAME.equals(filterString.getFilterBy()));
				fFilterByFunction.setSelection(
						ExecutionStatisticsFilterString.FILTER_BY_FUNCTION_NAME.equals(filterString.getFilterBy()));
			}

			ExecutionStatisticsFieldFilter fieldFilter = fFilter.getFieldFilter();
			if (fieldFilter != null) {
				fShowInformationMatching.setSelection(true);
				for (int i = 0; i < fDescriptor.getItemCount(); ++i) {
					if (fDescriptor.getData(fDescriptor.getItem(i)).equals(fieldFilter.getDescriptor())) {
						fDescriptor.select(i);
						break;
					}
				}
				for (int i = 0; i < fField.getItemCount(); ++i) {
					if (fField.getData(fField.getItem(i)).equals(fieldFilter.getField())) {
						fField.select(i);
						break;
					}
				}
				fNumber.setText(Integer.toString(fieldFilter.getNumber()));
			}
		}

		fCondTableViewer.setInput(fFilter);

		showInformationMatchingSelected();
	}

	public ExecutionStatisticsFilter getFilter() {
		return fFilter;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
	 */
	@Override
	protected void okPressed() {

		if (fFilter == null) {
			fFilter = new ExecutionStatisticsFilter();
		}

		fFilter.setName(fFilterName.getText());

		String filterPattern = fFilterString.getText().trim();
		if (filterPattern.length() > 0) {
			ExecutionStatisticsFilterString filterString = new ExecutionStatisticsFilterString();
			filterString.setString(filterPattern);
			filterString.setCaseSensitive(fCaseSensitive.getSelection());
			if (fFilterByFile.getSelection()) {
				filterString.setFilterBy(ExecutionStatisticsFilterString.FILTER_BY_FILE_NAME);
			} else if (fFilterByClass.getSelection()) {
				filterString.setFilterBy(ExecutionStatisticsFilterString.FILTER_BY_CLASS_NAME);
			} else if (fFilterByFunction.getSelection()) {
				filterString.setFilterBy(ExecutionStatisticsFilterString.FILTER_BY_FUNCTION_NAME);
			}
			fFilter.setFilterString(filterString);
		}

		if (fShowInformationMatching.getSelection()) {
			ExecutionStatisticsFieldFilter fieldFilter = new ExecutionStatisticsFieldFilter();
			fieldFilter.setDescriptor(fDescriptor.getText());
			fieldFilter.setField(fField.getText());
			fieldFilter.setNumber(Integer.parseInt(fNumber.getText()));
			fFilter.setFieldFilter(fieldFilter);
		} else {
			fFilter.setFieldFilter(null);
		}

		TableItem[] items = fCondTable.getItems();
		ExecutionStatisticsFilterCondition[] conditions = new ExecutionStatisticsFilterCondition[items.length];
		for (int i = 0; i < items.length; ++i) {
			conditions[i] = (ExecutionStatisticsFilterCondition) items[i].getData();
		}
		fFilter.setFilterConditions(conditions);

		super.okPressed();
	}

	class CondTableContentProvider implements IStructuredContentProvider {
		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(
		 * java.lang.Object)
		 */
		@Override
		public Object[] getElements(Object inputElement) {
			if (inputElement instanceof ExecutionStatisticsFilter
					&& ((ExecutionStatisticsFilter) inputElement).getFilterConditions() != null) {
				return ((ExecutionStatisticsFilter) inputElement).getFilterConditions();
			}
			return new Object[0];
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
		 */
		@Override
		public void dispose() {
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse
		 * .jface.viewers.Viewer, java.lang.Object, java.lang.Object)
		 */
		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}
	}

	class CondTableLabelProvider extends LabelProvider implements ITableLabelProvider {
		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(java
		 * .lang.Object, int)
		 */
		@Override
		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnText(java.
		 * lang.Object, int)
		 */
		@Override
		public String getColumnText(Object element, int columnIndex) {
			if (element instanceof ExecutionStatisticsFilterCondition) {
				ExecutionStatisticsFilterCondition cond = (ExecutionStatisticsFilterCondition) element;
				switch (columnIndex) {
				case 0:
					return cond.getAttribute();
				case 1:
					return cond.getOperator();
				case 2:
					return cond.getValue();
				}
			}
			return null;
		}
	}
}
