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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.StatusDialog;
import org.eclipse.jface.viewers.*;
import org.eclipse.jface.window.Window;
import org.eclipse.php.profile.ui.PHPProfileUIMessages;
import org.eclipse.php.profile.ui.ProfilerUIImages;
import org.eclipse.php.profile.ui.filters.ExecutionStatisticsFilter;
import org.eclipse.php.profile.ui.filters.ExecutionStatisticsFiltersRegistry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

/**
 * Filters dialog.
 */
public class FiltersDialog extends StatusDialog {

	private Label fMainLabel;
	private TableViewer fViewer;
	private Button fNewButton;
	private Button fEditButton;
	private Button fRemoveButton;
	private List<ExecutionStatisticsFilter> fFilters;

	public FiltersDialog(Shell parent) {
		super(parent);
		setShellStyle(getShellStyle() | SWT.RESIZE);
		setTitle(PHPProfileUIMessages.getString("FiltersDialog_0")); //$NON-NLS-1$

		fFilters = new ArrayList<>();
	}

	private void addButtonsGroup(Composite parent) {
		Composite buttonGroup = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		buttonGroup.setLayout(layout);

		fNewButton = new Button(buttonGroup, SWT.NONE);
		fNewButton.setText(PHPProfileUIMessages.getString("FiltersDialog_1")); //$NON-NLS-1$
		fNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				doEditFilter(null);
			}
		});

		fEditButton = new Button(buttonGroup, SWT.NONE);
		fEditButton.setText(PHPProfileUIMessages.getString("FiltersDialog_2")); //$NON-NLS-1$
		fEditButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				IStructuredSelection selection = (IStructuredSelection) fViewer.getSelection();
				doEditFilter((ExecutionStatisticsFilter) selection.getFirstElement());
			}
		});

		fRemoveButton = new Button(buttonGroup, SWT.NONE);
		fRemoveButton.setText(PHPProfileUIMessages.getString("FiltersDialog_3")); //$NON-NLS-1$
		fRemoveButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				IStructuredSelection selection = (IStructuredSelection) fViewer.getSelection();
				fFilters.remove(selection.getFirstElement());
				fViewer.refresh();
			}
		});
	}

	private void updateNewEditRemoveButtonsStates() {
		ExecutionStatisticsFilter filter = (ExecutionStatisticsFilter) ((IStructuredSelection) fViewer.getSelection())
				.getFirstElement();
		fEditButton.setEnabled(filter != null);
		fRemoveButton.setEnabled(filter != null && filter.isRemovable());
	}

	private void doEditFilter(ExecutionStatisticsFilter filter) {
		ExecutionStatisticsFilterDialog dialog = new ExecutionStatisticsFilterDialog(getShell(), filter, fFilters);
		if (dialog.open() == Window.OK) {
			if (filter == null) {
				fFilters.add(dialog.getFilter());
			}
		}
		fViewer.refresh();
	}

	public ExecutionStatisticsFilter[] getFilters() {
		return fFilters.toArray(new ExecutionStatisticsFilter[fFilters.size()]);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
	 */
	@Override
	protected void okPressed() {
		ExecutionStatisticsFiltersRegistry
				.saveFilters(fFilters.toArray(new ExecutionStatisticsFilter[fFilters.size()]));
		super.okPressed();
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

		fMainLabel = new Label(parent, SWT.NONE);
		fMainLabel.setText(PHPProfileUIMessages.getString("FiltersDialog_4")); //$NON-NLS-1$
		GridData layoutData = new GridData();
		layoutData.grabExcessHorizontalSpace = true;
		layoutData.horizontalAlignment = SWT.FILL;
		fMainLabel.setLayoutData(layoutData);

		fViewer = new TableViewer(parent);
		layoutData = new GridData();
		layoutData.grabExcessHorizontalSpace = true;
		layoutData.horizontalAlignment = SWT.FILL;
		layoutData.verticalAlignment = SWT.FILL;
		layoutData.heightHint = 300;
		layoutData.widthHint = 250;
		fViewer.getControl().setLayoutData(layoutData);

		fViewer.setContentProvider(new FiltersListContentProvider());
		fViewer.setLabelProvider(new FiltersListLabelProvider());

		ExecutionStatisticsFiltersRegistry.getFilters(fFilters);
		fViewer.setInput(fFilters);

		fViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see org.eclipse.jface.viewers.ISelectionChangedListener# selectionChanged
			 * (org.eclipse.jface.viewers.SelectionChangedEvent)
			 */
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				updateNewEditRemoveButtonsStates();
			}
		});

		addButtonsGroup(parent);

		updateNewEditRemoveButtonsStates();

		return parent;
	}

	class FiltersListContentProvider implements IStructuredContentProvider {
		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(
		 * java.lang.Object)
		 */
		@Override
		public Object[] getElements(Object inputElement) {
			if (inputElement instanceof List) {
				@SuppressWarnings("unchecked")
				List<Object> list = (List<Object>) inputElement;
				return list.toArray(new Object[list.size()]);
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

	class FiltersListLabelProvider extends LabelProvider {

		private Image fFilterImage;

		public FiltersListLabelProvider() {
			fFilterImage = ProfilerUIImages.get(ProfilerUIImages.IMG_OBJ_STAT_FILTER);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.viewers.LabelProvider#getImage(java.lang.Object)
		 */
		@Override
		public Image getImage(Object element) {
			return fFilterImage;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.viewers.LabelProvider#getText(java.lang.Object)
		 */
		@Override
		public String getText(Object element) {
			if (element instanceof ExecutionStatisticsFilter) {
				return ((ExecutionStatisticsFilter) element).getName();
			}
			return null;
		}
	}
}
