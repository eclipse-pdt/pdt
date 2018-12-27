/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.profile.ui.views;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Enumeration;
import java.util.Vector;

import org.eclipse.jface.viewers.*;
import org.eclipse.php.internal.core.util.collections.IntHashtable;
import org.eclipse.php.internal.ui.IPHPHelpContextIds;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.util.PHPPluginImages;
import org.eclipse.php.profile.core.data.ProfilerCallTrace;
import org.eclipse.php.profile.core.data.ProfilerCallTraceLayer;
import org.eclipse.php.profile.core.data.ProfilerFunctionData;
import org.eclipse.php.profile.core.engine.ProfilerDB;
import org.eclipse.php.profile.ui.PHPProfileUIMessages;
import org.eclipse.php.profile.ui.ProfilerUIConstants;
import org.eclipse.php.profile.ui.ProfilerUIImages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.part.ViewPart;

/**
 * Execution invocation statistics view.
 */
public class FunctionInvocationStatisticsView extends ViewPart {

	private static final String[] fSelectedMethodHeaders = {
			PHPProfileUIMessages.getString("FunctionInvocationStatisticsView_0"), //$NON-NLS-1$
			PHPProfileUIMessages.getString("FunctionInvocationStatisticsView_1"), //$NON-NLS-1$
			PHPProfileUIMessages.getString("FunctionInvocationStatisticsView_2"), //$NON-NLS-1$
			PHPProfileUIMessages.getString("FunctionInvocationStatisticsView_3"), //$NON-NLS-1$
			PHPProfileUIMessages.getString("FunctionInvocationStatisticsView_4"), //$NON-NLS-1$
			PHPProfileUIMessages.getString("FunctionInvocationStatisticsView_5") }; //$NON-NLS-1$
	private static final ColumnWeightData[] fSelectedMethodWeights = { new ColumnWeightData(100),
			new ColumnWeightData(150), new ColumnWeightData(200), new ColumnWeightData(170), new ColumnWeightData(130),
			new ColumnWeightData(130) };
	private static final String[] fInvokeFunctionHeaders = {
			PHPProfileUIMessages.getString("FunctionInvocationStatisticsView_6"), //$NON-NLS-1$
			PHPProfileUIMessages.getString("FunctionInvocationStatisticsView_7"), //$NON-NLS-1$
			PHPProfileUIMessages.getString("FunctionInvocationStatisticsView_8"), //$NON-NLS-1$
			PHPProfileUIMessages.getString("FunctionInvocationStatisticsView_9"), //$NON-NLS-1$
			PHPProfileUIMessages.getString("FunctionInvocationStatisticsView_10"), //$NON-NLS-1$
			PHPProfileUIMessages.getString("FunctionInvocationStatisticsView_11"), //$NON-NLS-1$
			PHPProfileUIMessages.getString("FunctionInvocationStatisticsView_12") }; //$NON-NLS-1$
	private static final ColumnWeightData[] fInvokeFunctionWeights = { new ColumnWeightData(130),
			new ColumnWeightData(150), new ColumnWeightData(200), new ColumnWeightData(170), new ColumnWeightData(130),
			new ColumnWeightData(130), new ColumnWeightData(130) };

	private static NumberFormat fNumberFormatter = new DecimalFormat("0.000000"); //$NON-NLS-1$
	private static final Object[] EMPTY_SET = new Object[0];

	private ScrolledForm fForm;
	private TableViewer fSelectedMethodViewer;
	private TableViewer fInvokedByViewer;
	private TableViewer fMethodInvokesViewer;
	private ProfilerDB fProfilerDB;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.
	 * widgets.Composite)
	 */
	@Override
	public void createPartControl(Composite parent) {
		createForm(parent);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(parent,
				IPHPHelpContextIds.FUNCTION_INVOCATION_STATISTICS_VIEW);
	}

	private void createForm(Composite parent) {

		FormToolkit toolkit = new FormToolkit(parent.getDisplay());
		fForm = toolkit.createScrolledForm(parent);
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		// layout.type = SWT.VERTICAL;
		layout.marginWidth = 5;
		layout.marginHeight = 5;
		fForm.getBody().setLayout(layout);

		createSelectedMethodSection(toolkit);
		createInvokedBySection(toolkit);
		createInvokesSection(toolkit);
	}

	private void createInvokesSection(FormToolkit toolkit) {
		Section section = toolkit.createSection(fForm.getBody(), SWT.DEFAULT);
		GridData data = new GridData();
		data.grabExcessHorizontalSpace = true;
		data.horizontalAlignment = SWT.FILL;
		section.setLayoutData(data);
		section.setText(PHPProfileUIMessages.getString("FunctionInvocationStatisticsView_13")); //$NON-NLS-1$

		Composite sectionClient = new Composite(section, SWT.NONE);
		toolkit.paintBordersFor(sectionClient);
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		sectionClient.setLayout(layout);

		fMethodInvokesViewer = new TableViewer(sectionClient,
				SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI | SWT.FULL_SELECTION);
		Table table = fMethodInvokesViewer.getTable();
		table.setLinesVisible(true);

		TableLayout tableLayout = new TableLayout();
		table.setLayout(tableLayout);
		table.setHeaderVisible(true);
		data = new GridData();
		data.grabExcessHorizontalSpace = true;
		data.horizontalAlignment = SWT.FILL;
		table.setLayoutData(data);

		for (int i = 0; i < fInvokeFunctionHeaders.length; ++i) {
			tableLayout.addColumnData(fInvokeFunctionWeights[i]);

			TableColumn tableColumn = new TableColumn(table, SWT.NONE, i);
			tableColumn.setText(fInvokeFunctionHeaders[i]);
			tableColumn.setResizable(fInvokeFunctionWeights[i].resizable);

			final int field = i;
			tableColumn.addSelectionListener(new SelectionAdapter() {
				/*
				 * (non-Javadoc)
				 * 
				 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org
				 * .eclipse.swt.events.SelectionEvent)
				 */
				@Override
				public void widgetSelected(SelectionEvent e) {
					InvokeFunctionSorter sorter = (InvokeFunctionSorter) fMethodInvokesViewer.getSorter();
					sorter.setColumn(field);

					TableColumn[] tableColumns = fMethodInvokesViewer.getTable().getColumns();
					for (int i = 0; i < fInvokeFunctionHeaders.length; ++i) {
						tableColumns[i].setImage(null);
					}
					if (sorter.getOrder() == ProfilerUIConstants.SORT_ASCENDING) {
						tableColumns[field].setImage(ProfilerUIImages.get(ProfilerUIImages.IMG_OBJ_SORT_ASCENDING));
					} else if (sorter.getOrder() == ProfilerUIConstants.SORT_DESCENDING) {
						tableColumns[field].setImage(ProfilerUIImages.get(ProfilerUIImages.IMG_OBJ_SORT_DESCENDING));
					}
					fMethodInvokesViewer.refresh();
				}
			});
		}

		section.setClient(sectionClient);
		toolkit.adapt(sectionClient);

		fMethodInvokesViewer.setContentProvider(new MethodInvokesContentProvider());
		fMethodInvokesViewer.setLabelProvider(new InvokeFunctionLabelProvider());
		fMethodInvokesViewer.setSorter(new InvokeFunctionSorter());
	}

	private void createInvokedBySection(FormToolkit toolkit) {
		Section section = toolkit.createSection(fForm.getBody(), SWT.DEFAULT);
		GridData data = new GridData();
		data.grabExcessHorizontalSpace = true;
		data.horizontalAlignment = SWT.FILL;
		section.setLayoutData(data);
		section.setText(PHPProfileUIMessages.getString("FunctionInvocationStatisticsView_14")); //$NON-NLS-1$

		Composite sectionClient = new Composite(section, SWT.NONE);
		toolkit.paintBordersFor(sectionClient);
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		sectionClient.setLayout(layout);

		fInvokedByViewer = new TableViewer(sectionClient, SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI | SWT.FULL_SELECTION);
		Table table = fInvokedByViewer.getTable();
		table.setLinesVisible(true);

		TableLayout tableLayout = new TableLayout();
		table.setLayout(tableLayout);
		table.setHeaderVisible(true);
		data = new GridData();
		data.grabExcessHorizontalSpace = true;
		data.horizontalAlignment = SWT.FILL;
		table.setLayoutData(data);

		for (int i = 0; i < fInvokeFunctionHeaders.length; ++i) {
			tableLayout.addColumnData(fInvokeFunctionWeights[i]);

			TableColumn tableColumn = new TableColumn(table, SWT.NONE, i);
			tableColumn.setText(fInvokeFunctionHeaders[i]);
			tableColumn.setResizable(fInvokeFunctionWeights[i].resizable);

			final int field = i;
			tableColumn.addSelectionListener(new SelectionAdapter() {
				/*
				 * (non-Javadoc)
				 * 
				 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org
				 * .eclipse.swt.events.SelectionEvent)
				 */
				@Override
				public void widgetSelected(SelectionEvent e) {
					InvokeFunctionSorter sorter = (InvokeFunctionSorter) fInvokedByViewer.getSorter();
					sorter.setColumn(field);

					TableColumn[] tableColumns = fInvokedByViewer.getTable().getColumns();
					for (int i = 0; i < fInvokeFunctionHeaders.length; ++i) {
						tableColumns[i].setImage(null);
					}
					if (sorter.getOrder() == ProfilerUIConstants.SORT_ASCENDING) {
						tableColumns[field].setImage(ProfilerUIImages.get(ProfilerUIImages.IMG_OBJ_SORT_ASCENDING));
					} else if (sorter.getOrder() == ProfilerUIConstants.SORT_DESCENDING) {
						tableColumns[field].setImage(ProfilerUIImages.get(ProfilerUIImages.IMG_OBJ_SORT_DESCENDING));
					}
					fInvokedByViewer.refresh();
				}
			});
		}

		section.setClient(sectionClient);
		toolkit.adapt(sectionClient);

		fInvokedByViewer.setContentProvider(new InvokedByContentProvider());
		fInvokedByViewer.setLabelProvider(new InvokeFunctionLabelProvider());
		fInvokedByViewer.setSorter(new InvokeFunctionSorter());
	}

	private void createSelectedMethodSection(FormToolkit toolkit) {
		Section section = toolkit.createSection(fForm.getBody(), SWT.DEFAULT);
		GridData data = new GridData();
		data.grabExcessHorizontalSpace = true;
		data.horizontalAlignment = SWT.FILL;
		section.setLayoutData(data);
		section.setText(PHPProfileUIMessages.getString("FunctionInvocationStatisticsView_15")); //$NON-NLS-1$

		Composite sectionClient = new Composite(section, SWT.NONE);
		toolkit.paintBordersFor(sectionClient);
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		sectionClient.setLayout(layout);

		fSelectedMethodViewer = new TableViewer(sectionClient,
				SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI | SWT.FULL_SELECTION);
		Table table = fSelectedMethodViewer.getTable();
		table.setLinesVisible(true);

		TableLayout tableLayout = new TableLayout();
		table.setLayout(tableLayout);
		table.setHeaderVisible(true);
		data = new GridData();
		data.grabExcessHorizontalSpace = true;
		data.horizontalAlignment = SWT.FILL;
		table.setLayoutData(data);

		for (int i = 0; i < fSelectedMethodHeaders.length; ++i) {
			tableLayout.addColumnData(fSelectedMethodWeights[i]);

			TableColumn tableColumn = new TableColumn(table, SWT.NONE, i);
			tableColumn.setText(fSelectedMethodHeaders[i]);
			tableColumn.setResizable(fSelectedMethodWeights[i].resizable);

			final int field = i;
			tableColumn.addSelectionListener(new SelectionAdapter() {
				/*
				 * (non-Javadoc)
				 * 
				 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org
				 * .eclipse.swt.events.SelectionEvent)
				 */
				@Override
				public void widgetSelected(SelectionEvent e) {
					SelectedMethodSorter sorter = (SelectedMethodSorter) fSelectedMethodViewer.getSorter();
					sorter.setColumn(field);

					TableColumn[] tableColumns = fSelectedMethodViewer.getTable().getColumns();
					for (int i = 0; i < fSelectedMethodHeaders.length; ++i) {
						tableColumns[i].setImage(null);
					}
					if (sorter.getOrder() == ProfilerUIConstants.SORT_ASCENDING) {
						tableColumns[field].setImage(ProfilerUIImages.get(ProfilerUIImages.IMG_OBJ_SORT_ASCENDING));
					} else if (sorter.getOrder() == ProfilerUIConstants.SORT_DESCENDING) {
						tableColumns[field].setImage(ProfilerUIImages.get(ProfilerUIImages.IMG_OBJ_SORT_DESCENDING));
					}
					fSelectedMethodViewer.refresh();
				}
			});
		}

		section.setClient(sectionClient);
		toolkit.adapt(sectionClient);

		fSelectedMethodViewer.setContentProvider(new SelectedMethodContentProvider());
		fSelectedMethodViewer.setLabelProvider(new SelectedMethodLabelProvider());
		fSelectedMethodViewer.setSorter(new SelectedMethodSorter());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IWorkbenchPart#setFocus()
	 */
	@Override
	public void setFocus() {
		// TODO Auto-generated method stub
	}

	/**
	 * Sets the data for the current view.
	 * 
	 * @param profiler
	 *                     db
	 * @param function
	 *                     data
	 */
	public void setInput(ProfilerDB db, ProfilerFunctionData function) {
		fProfilerDB = db;

		fSelectedMethodViewer.setInput(function);
		fInvokedByViewer.setInput(function);
		fMethodInvokesViewer.setInput(function);

		fForm.reflow(true);

		setTitleToolTip(function.toString());
	}

	class SelectedMethodContentProvider implements IStructuredContentProvider {

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(
		 * java.lang.Object)
		 */
		@Override
		public Object[] getElements(Object inputElement) {
			if (inputElement instanceof ProfilerFunctionData) {
				return new Object[] { inputElement };
			}
			return EMPTY_SET;
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

	class SelectedMethodLabelProvider implements ITableLabelProvider {

		private Image fFunctionImage;
		private Image fFileImage;

		public SelectedMethodLabelProvider() {
			fFunctionImage = PHPUiPlugin.getImageDescriptorRegistry().get(PHPPluginImages.DESC_MISC_PUBLIC);
			fFileImage = PHPUiPlugin.getImageDescriptorRegistry().get(PHPPluginImages.DESC_OBJS_CUNIT);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(java
		 * .lang.Object, int)
		 */
		@Override
		public Image getColumnImage(Object element, int columnIndex) {
			if (element instanceof ProfilerFunctionData) {
				switch (columnIndex) {
				case 1:
					return fFunctionImage;
				case 2:
					return fFileImage;
				}
			}
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
			if (element instanceof ProfilerFunctionData) {
				ProfilerFunctionData funcData = (ProfilerFunctionData) element;
				switch (columnIndex) {
				case 0:
					return Integer.toString(funcData.getCallsCount());
				case 1:
					return funcData.toString();
				case 2:
					return funcData.getFileName();
				case 3:
					if (funcData.getCallsCount() > 0) {
						return fNumberFormatter.format(funcData.getOwnTime() / funcData.getCallsCount());
					}
				case 4:
					return fNumberFormatter.format(funcData.getOwnTime());
				case 5:
					return fNumberFormatter.format(funcData.getTotalTime());
				}
			}
			return null;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.viewers.IBaseLabelProvider#addListener(org.eclipse
		 * .jface.viewers.ILabelProviderListener)
		 */
		@Override
		public void addListener(ILabelProviderListener listener) {
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.viewers.IBaseLabelProvider#dispose()
		 */
		@Override
		public void dispose() {
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.viewers.IBaseLabelProvider#isLabelProperty(java
		 * .lang.Object, java.lang.String)
		 */
		@Override
		public boolean isLabelProperty(Object element, String property) {
			return false;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.viewers.IBaseLabelProvider#removeListener(org. eclipse
		 * .jface.viewers.ILabelProviderListener)
		 */
		@Override
		public void removeListener(ILabelProviderListener listener) {
		}
	}

	class SelectedMethodSorter extends AbstractTableSorter {
		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.php.profile.ui.views.AbstractTableSorter#compare(org.
		 * eclipse .jface.viewers.Viewer, java.lang.Object, java.lang.Object)
		 */
		@Override
		public int compare(Viewer viewer, Object e1, Object e2) {
			if (e1 instanceof ProfilerFunctionData && e2 instanceof ProfilerFunctionData) {
				ProfilerFunctionData f1 = (ProfilerFunctionData) e1;
				ProfilerFunctionData f2 = (ProfilerFunctionData) e2;
				switch (getColumn()) {
				case 0:
					return compare(f1.getCallsCount(), f2.getCallsCount());
				case 2:
					return super.compare(viewer, f1.getFileName(), f2.getFileName());
				case 3:
					if (f1.getCallsCount() > 0 && f2.getCallsCount() > 0) {
						return compare(f1.getOwnTime() / f1.getCallsCount(), f2.getOwnTime() / f2.getCallsCount());
					}
				case 4:
					return compare(f1.getOwnTime(), f2.getOwnTime());
				case 5:
					return compare(f1.getTotalTime(), f2.getTotalTime());
				}
			}
			return super.compare(viewer, e1, e2);
		}
	}

	class InvokeFunctionTableElement {
		private ProfilerFunctionData fFunction;
		private int fInvokesNumber;

		public InvokeFunctionTableElement() {
		}

		public void setFunctionData(ProfilerFunctionData function) {
			fFunction = function;
		}

		public ProfilerFunctionData getFunctionData() {
			return fFunction;
		}

		public void setInvokesNumber(int invokesNumber) {
			fInvokesNumber = invokesNumber;
		}

		public int getInvokesNumber() {
			return fInvokesNumber;
		}
	}

	class InvokeFunctionSorter extends AbstractTableSorter {
		@Override
		public int compare(Viewer viewer, Object e1, Object e2) {
			if (e1 instanceof InvokeFunctionTableElement && e2 instanceof InvokeFunctionTableElement) {
				InvokeFunctionTableElement i1 = (InvokeFunctionTableElement) e1;
				InvokeFunctionTableElement i2 = (InvokeFunctionTableElement) e2;
				ProfilerFunctionData f1 = i1.getFunctionData();
				ProfilerFunctionData f2 = i2.getFunctionData();
				switch (getColumn()) {
				case 0:
					return compare(i1.getInvokesNumber(), i2.getInvokesNumber());
				case 1:
					return super.compare(viewer, f1, f2);
				case 2:
					return super.compare(viewer, f1.getFileName(), f2.getFileName());
				case 3:
					if (f1.getCallsCount() > 0 && f2.getCallsCount() > 0) {
						return compare(f1.getOwnTime() / f1.getCallsCount(), f2.getOwnTime() / f2.getCallsCount());
					}
				case 4:
					return compare(f1.getOwnTime(), f2.getOwnTime());
				case 5:
					return compare(f1.getTotalTime(), f2.getTotalTime());
				case 6:
					return compare(f1.getCallsCount(), f2.getCallsCount());
				}
			}
			return super.compare(viewer, e1, e2);
		}
	}

	class InvokeFunctionLabelProvider implements ITableLabelProvider {

		private Image fFunctionImage;
		private Image fFileImage;

		public InvokeFunctionLabelProvider() {
			fFunctionImage = PHPUiPlugin.getImageDescriptorRegistry().get(PHPPluginImages.DESC_MISC_PUBLIC);
			fFileImage = PHPUiPlugin.getImageDescriptorRegistry().get(PHPPluginImages.DESC_OBJS_CUNIT);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(java
		 * .lang.Object, int)
		 */
		@Override
		public Image getColumnImage(Object element, int columnIndex) {
			if (element instanceof InvokeFunctionTableElement) {
				switch (columnIndex) {
				case 1:
					return fFunctionImage;
				case 2:
					return fFileImage;
				}
			}
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
			if (element instanceof InvokeFunctionTableElement) {
				InvokeFunctionTableElement invokeFunctionElement = (InvokeFunctionTableElement) element;
				ProfilerFunctionData funcData = invokeFunctionElement.getFunctionData();
				switch (columnIndex) {
				case 0:
					return Integer.toString(invokeFunctionElement.getInvokesNumber());
				case 1:
					return funcData.toString();
				case 2:
					return funcData.getFileName();
				case 3:
					if (funcData.getCallsCount() > 0) {
						return fNumberFormatter.format(funcData.getOwnTime() / funcData.getCallsCount());
					}
				case 4:
					return fNumberFormatter.format(funcData.getOwnTime());
				case 5:
					return fNumberFormatter.format(funcData.getTotalTime());
				case 6:
					return Integer.toString(funcData.getCallsCount());
				}
			}
			return null;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.viewers.IBaseLabelProvider#addListener(org.eclipse
		 * .jface.viewers.ILabelProviderListener)
		 */
		@Override
		public void addListener(ILabelProviderListener listener) {
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.viewers.IBaseLabelProvider#dispose()
		 */
		@Override
		public void dispose() {
			// TODO Auto-generated method stub

		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.viewers.IBaseLabelProvider#isLabelProperty(java
		 * .lang.Object, java.lang.String)
		 */
		@Override
		public boolean isLabelProperty(Object element, String property) {
			// TODO Auto-generated method stub
			return false;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.viewers.IBaseLabelProvider#removeListener(org. eclipse
		 * .jface.viewers.ILabelProviderListener)
		 */
		@Override
		public void removeListener(ILabelProviderListener listener) {
			// TODO Auto-generated method stub

		}
	}

	class InvokedByContentProvider implements IStructuredContentProvider {

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

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(
		 * java.lang.Object)
		 */
		@Override
		public Object[] getElements(Object inputElement) {
			if (inputElement instanceof ProfilerFunctionData) {
				ProfilerFunctionData function = (ProfilerFunctionData) inputElement;
				IntHashtable invokers = new IntHashtable();
				Vector<Integer> callerIDs = new Vector<>(); // queue of
															// callers

				ProfilerCallTrace callTrace = fProfilerDB.getCallTrace();
				if (callTrace != null) {
					ProfilerCallTraceLayer[] layers = callTrace.getLayers();
					if (layers.length > 0) {
						callerIDs.add(layers[0].getCalledID()); // store
																// first
																// caller
						for (int i = 1; i < layers.length; ++i) {
							if (layers[i].getType() == ProfilerCallTraceLayer.EXIT && callerIDs.size() > 0) {
								callerIDs.removeElementAt(callerIDs.size() - 1);
								continue;
							}
							if (layers[i].getCalledID() == function.getID() && callerIDs.size() > 0) { // this
																										// function
																										// is
																										// called
								int id = callerIDs.lastElement().intValue(); // get
																				// last
																				// caller
								if (invokers.containsKey(id)) {
									InvokeFunctionTableElement invoker = (InvokeFunctionTableElement) invokers.get(id);
									invoker.setInvokesNumber(invoker.getInvokesNumber() + 1);
								} else {
									InvokeFunctionTableElement invoker = new InvokeFunctionTableElement();
									invoker.setFunctionData(fProfilerDB.getFunctionData(id));
									invoker.setInvokesNumber(1);
									invokers.put(id, invoker);
								}
							}
							callerIDs.add(layers[i].getCalledID());
						}
					}
				}
				Object[] elements = new Object[invokers.size()];
				Enumeration<?> e = invokers.elements();
				int i = 0;
				while (e.hasMoreElements()) {
					elements[i++] = e.nextElement();
				}
				return elements;
			}
			return EMPTY_SET;
		}
	}

	class MethodInvokesContentProvider implements IStructuredContentProvider {

		@Override
		public void dispose() {
		}

		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}

		@Override
		public Object[] getElements(Object inputElement) {
			if (inputElement instanceof ProfilerFunctionData) {
				ProfilerFunctionData function = (ProfilerFunctionData) inputElement;
				IntHashtable invokees = new IntHashtable();
				Vector<Integer> callerIDs = new Vector<>(); // queue of
															// callers

				ProfilerCallTrace callTrace = fProfilerDB.getCallTrace();
				if (callTrace != null) {
					ProfilerCallTraceLayer[] layers = callTrace.getLayers();
					if (layers.length > 0) {
						callerIDs.add(layers[0].getCalledID()); // store
																// first
																// caller
						for (int i = 1; i < layers.length; ++i) {
							if (layers[i].getType() == ProfilerCallTraceLayer.EXIT && callerIDs.size() > 0) {
								callerIDs.removeElementAt(callerIDs.size() - 1);
								continue;
							}
							if (callerIDs.size() > 0) {
								if (callerIDs.lastElement().intValue() == function.getID()) { // this
																								// function
																								// calls
																								// somebody
									int id = layers[i].getCalledID();
									if (invokees.containsKey(id)) {
										InvokeFunctionTableElement invokee = (InvokeFunctionTableElement) invokees
												.get(id);
										invokee.setInvokesNumber(invokee.getInvokesNumber() + 1);
									} else {
										InvokeFunctionTableElement invokee = new InvokeFunctionTableElement();
										invokee.setFunctionData(fProfilerDB.getFunctionData(id));
										invokee.setInvokesNumber(1);
										invokees.put(id, invokee);
									}
								}
							}
							callerIDs.add(layers[i].getCalledID());
						}
					}
				}
				Object[] elements = new Object[invokees.size()];
				Enumeration<?> e = invokees.elements();
				int i = 0;
				while (e.hasMoreElements()) {
					elements[i++] = e.nextElement();
				}
				return elements;
			}
			return EMPTY_SET;
		}
	}
}
