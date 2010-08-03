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
package org.eclipse.php.internal.ui.dialogs.openType.generic;

import org.eclipse.jface.viewers.*;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.dialogs.openType.generic.filter.ElementSpecificFilter;
import org.eclipse.php.internal.ui.dialogs.openType.generic.filter.IFilter;
import org.eclipse.php.internal.ui.util.SearchPattern;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class BasicSelector extends Composite {

	private Label instructionLabel;
	private Text filterText;
	private Label matchingLabel;
	private HighLoadTableViewer tableViewer;
	private Composite tableViewerComposite;
	private CompositeFactory contentAreaCompositeFactory;
	private ViewerElementFilter phpTypeViewerFilter;
	private IBasicSelectorLabelProvider basicSelectorLabelProvider;

	public BasicSelector(Composite parent,
			CompositeFactory contentAreaCompositeFactory) {
		super(parent, SWT.NONE);

		this.contentAreaCompositeFactory = new CompositeFactoryAsserter(
				contentAreaCompositeFactory);
		initialize();
	}

	public void setInitFilterText(String initFilterText) {
		filterText.setText(initFilterText);
	}

	private void initialize() {
		FormLayout formLayout = new FormLayout();
		formLayout.marginWidth = 10;
		formLayout.marginHeight = 5;

		this.setLayout(formLayout);

		initControls();

		this.setSize(new org.eclipse.swt.graphics.Point(341, 400));
	}

	private void initControls() {
		this.createInstructionLabel();
		FormData formData = new FormData();
		instructionLabel.setLayoutData(formData);
		formData.top = new FormAttachment(0);

		this.createFilterText();
		formData = new FormData();
		filterText.setLayoutData(formData);
		formData.top = new FormAttachment(instructionLabel, 5);
		formData.left = new FormAttachment(0);
		formData.right = new FormAttachment(100);

		Control lastControl = filterText;
		if (contentAreaCompositeFactory != null) {
			Composite contentFilterComposite = contentAreaCompositeFactory
					.createComposite(this);
			lastControl = contentFilterComposite;

			formData = new FormData();
			contentFilterComposite.setLayoutData(formData);
			formData.top = new FormAttachment(filterText, 5);
			formData.left = new FormAttachment(0);
			formData.right = new FormAttachment(100);
		}

		this.createMatchingLabel();
		formData = new FormData();
		this.matchingLabel.setLayoutData(formData);
		formData.top = new FormAttachment(lastControl, 5);
		formData.left = new FormAttachment(0);
		formData.right = new FormAttachment(100);

		this.createTableViewer();
		formData = new FormData();
		this.tableViewerComposite.setLayoutData(formData);
		formData.top = new FormAttachment(matchingLabel, 5);
		formData.left = new FormAttachment(0);
		formData.right = new FormAttachment(100);
		formData.bottom = new FormAttachment(100);
	}

	private void createTableViewer() {
		tableViewerComposite = new Composite(this, SWT.NONE);
		FillLayout fillLayout = new FillLayout();
		tableViewerComposite.setLayout(fillLayout);
		tableViewer = new HighLoadTableViewer(tableViewerComposite, SWT.SINGLE
				| SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		phpTypeViewerFilter = new ViewerElementFilter();
		tableViewer.addFilter(phpTypeViewerFilter);
		// Functionality to go to the text when pressing the up arrow and found
		// in the first element
		tableViewer.getControl().addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				StructuredSelection structuredSelection = (StructuredSelection) tableViewer
						.getSelection();
				if (structuredSelection.getFirstElement() == tableViewer
						.getElementAt(0)
						&& SWT.ARROW_UP == e.keyCode) {
					filterText.setFocus();
				}
			}

			public void keyReleased(KeyEvent e) {
			}

		});

		tableViewer.getControl().addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				if ((e.keyCode & SWT.KEYCODE_BIT) == 0) {
					if ((e.keyCode > 'a' && e.keyCode < 'z')
							|| (e.keyCode > 'A' && e.keyCode < 'Z')) {
						filterText.setFocus();
						filterText.setText(filterText.getText() + e.character);
						filterText.setSelection(filterText.getText().length());
					} else if (e.keyCode == SWT.BS) {
						filterText.setFocus();
						String text = filterText.getText();
						if (text.length() == 0) {
							return;
						}
						filterText
								.setText(text.substring(0, text.length() - 1));
						filterText.setSelection(text.length() - 1);
					}
				}
			}

			public void keyReleased(KeyEvent e) {
			}

		});
	}

	private void createMatchingLabel() {
		matchingLabel = new Label(this, SWT.NONE);
		matchingLabel.setText(PHPUIMessages.OpenType_matchingResources);
	}

	private void createFilterText() {
		this.filterText = new Text(this, SWT.BORDER);
		filterText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				phpTypeViewerFilter.setFilterText(filterText.getText());
			}
		});

		// When pressing the down arrow and found in the text, the focus is
		// changed for the table
		filterText.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				if (SWT.ARROW_DOWN == e.keyCode
						&& tableViewer.getTableElements().length != 0) {
					tableViewer.getControl().setFocus();
				}
			}

			public void keyReleased(KeyEvent e) {
			}
		});
	}

	private void createInstructionLabel() {
		instructionLabel = new Label(this, SWT.NONE);
		instructionLabel.setText(PHPUIMessages.OpenType_instructionText);
	}

	public void setElements(Object[] elements) {
		tableViewer.setElements(elements);
	}

	public void setLabelProvider(
			IBasicSelectorLabelProvider basicSelectorLabelProvider) {
		this.basicSelectorLabelProvider = basicSelectorLabelProvider;
		this.tableViewer.setLabelProvider(new ITableLabelProvider() {

			public Image getColumnImage(Object element, int columnIndex) {
				return BasicSelector.this.basicSelectorLabelProvider
						.getElementImage(element);
			}

			public String getColumnText(Object element, int columnIndex) {
				return BasicSelector.this.basicSelectorLabelProvider
						.getElementDescription(element);
			}

			public void addListener(ILabelProviderListener listener) {
			}

			public void dispose() {
			}

			public boolean isLabelProperty(Object element, String property) {
				return false;
			}

			public void removeListener(ILabelProviderListener listener) {
			}

		});
	}

	public void addFilter(final IFilter filter) {
		tableViewer.addFilter(filter);
	}

	private class ViewerElementFilter extends ElementSpecificFilter {

		private String textFilter = ""; //$NON-NLS-1$

		public boolean select(Object element) {
			if (textFilter.equals("")) { //$NON-NLS-1$
				return false;
			}

			String elementText = BasicSelector.this.basicSelectorLabelProvider
					.getElementName(element).toLowerCase();
			return SearchPattern.match(textFilter, elementText);
		}

		public void setFilterText(String textFilter) {
			this.textFilter = textFilter.toLowerCase();
			notifyFilterChanged();
		}
	}

	public Object getSelectedElement() {
		return ((StructuredSelection) tableViewer.getSelection())
				.getFirstElement();
	}

	public void addDoubleClickListener(IDoubleClickListener listener) {
		tableViewer.addDoubleClickListener(listener);
	}

	public void addSelectionChangedListener(ISelectionChangedListener listener) {
		tableViewer.addSelectionChangedListener(listener);
	}

	public void setDefaultElementSelection(boolean defaultElementSelection) {
		tableViewer.setDefaultElementSelection(defaultElementSelection);
	}

	public void close() {
		tableViewer.close();
	}
}