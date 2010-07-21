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
package org.eclipse.php.internal.debug.ui.preferences.phps;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.dltk.ui.DLTKPluginImages;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.*;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.debug.core.preferences.PHPexeItem;
import org.eclipse.php.internal.debug.core.preferences.PHPexes;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.IWorkbench;

/**
 * PHP version configuration block preferences page.
 */
public class PHPInterpreterExecutionConfigurationBlock {

	private TableViewer fProfilesViewer;
	private CheckboxTableViewer fJREsViewer;

	/**
	 * PHPVersion -> Default interpreter
	 */
	private Map<PHPVersion, PHPexeItem> versionToDefaultItem = new HashMap<PHPVersion, PHPexeItem>();

	/**
	 * PHPVersion -> Default interpreter
	 */
	private Map<PHPVersion, PHPexeItem[]> versionToCompatibleItems = new HashMap<PHPVersion, PHPexeItem[]>();
	PHPexes phpExes;
	PHPexeItem[] allItems;
	/**
	 * This block's control
	 */
	private Composite fControl;

	public class ExecutionEnvironmentsLabelProvider extends LabelProvider {

		/**
		 * @see org.eclipse.jface.viewers.ILabelProvider#getImage(java.lang.Object)
		 */
		public Image getImage(Object element) {
			return DLTKPluginImages.getDescriptor(
					DLTKPluginImages.IMG_OBJS_LIBRARY).createImage();
		}

		/**
		 * @see org.eclipse.jface.viewers.ILabelProvider#getText(java.lang.Object)
		 */
		public String getText(Object element) {
			return ((PHPVersion) element).getAlias();
		}
	}

	/**
	 * Label provider for installed PHPs table.
	 */
	class PHPExeLabelProvider extends LabelProvider {

		/**
		 * @see org.eclipse.jface.viewers.ILabelProvider#getImage(java.lang.Object)
		 */
		public Image getImage(Object element) {
			return DLTKPluginImages.getDescriptor(
					DLTKPluginImages.IMG_OBJS_LIBRARY).createImage();
		}

		/**
		 * @see org.eclipse.jface.viewers.ILabelProvider#getText(java.lang.Object)
		 */
		public String getText(Object element) {
			if (element instanceof PHPexeItem) {
				final PHPexeItem phpExe = (PHPexeItem) element;
				return phpExe.getName();
			}
			return element.toString();
		}
	}

	public PHPInterpreterExecutionConfigurationBlock() {
		super();
		this.phpExes = PHPexes.getInstance();
		allItems = phpExes.getAllItems();
		versionToDefaultItem.putAll(phpExes.getDefaultItemsForPHPVersion());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.preference.PreferencePage#createContents(org.eclipse
	 * .swt.widgets.Composite)
	 */
	protected Control createControl(Composite ancestor) {
		fControl = ancestor;
		Composite container = new Composite(ancestor, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		layout.makeColumnsEqualWidth = true;
		container.setLayout(layout);
		GridData gd = new GridData(GridData.FILL_BOTH);
		container.setLayoutData(gd);
		container.setFont(ancestor.getFont());

		Composite eeContainer = new Composite(container, SWT.NONE);
		layout = new GridLayout();
		layout.marginWidth = 0;
		eeContainer.setLayout(layout);
		eeContainer.setLayoutData(new GridData(GridData.FILL_BOTH));

		Label label = new Label(eeContainer, SWT.NONE);
		label.setFont(ancestor.getFont());
		label.setText(Messages.PHPInterpreterExecutionConfigurationBlock_0);
		label.setLayoutData(new GridData(SWT.FILL, 0, true, false));

		Table table = new Table(eeContainer, SWT.BORDER | SWT.SINGLE);
		table.setLayout(layout);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		fProfilesViewer = new TableViewer(table);
		fProfilesViewer.setContentProvider(new ArrayContentProvider());
		fProfilesViewer
				.setLabelProvider(new ExecutionEnvironmentsLabelProvider());
		fProfilesViewer.setComparator(new ViewerComparator());
		fProfilesViewer.setInput(PHPVersion.values());

		Composite jreContainer = new Composite(container, SWT.NONE);
		layout = new GridLayout();
		layout.marginWidth = 0;
		jreContainer.setLayout(layout);
		jreContainer.setLayoutData(new GridData(GridData.FILL_BOTH));

		label = new Label(jreContainer, SWT.NONE);
		label.setFont(ancestor.getFont());
		label.setText(Messages.PHPInterpreterExecutionConfigurationBlock_1);
		label.setLayoutData(new GridData(SWT.FILL, 0, true, false));

		table = new Table(jreContainer, SWT.CHECK | SWT.BORDER | SWT.SINGLE);
		table.setLayout(layout);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		fJREsViewer = new CheckboxTableViewer(table);
		fJREsViewer.setContentProvider(new ArrayContentProvider());
		fJREsViewer.setLabelProvider(new PHPExeLabelProvider());
		fJREsViewer.setInput(new PHPexeItem[0]);

		fProfilesViewer
				.addSelectionChangedListener(new ISelectionChangedListener() {
					public void selectionChanged(SelectionChangedEvent event) {
						PHPVersion version = (PHPVersion) ((IStructuredSelection) event
								.getSelection()).getFirstElement();
						PHPexeItem jre = versionToDefaultItem.get(version);
						fJREsViewer.setInput(getCompatibleItems(allItems,
								version));
						if (jre != null) {
							fJREsViewer
									.setCheckedElements(new Object[] { jre });
						} else {
							fJREsViewer.setCheckedElements(new Object[0]);
						}
					}

				});

		fJREsViewer.addCheckStateListener(new ICheckStateListener() {
			public void checkStateChanged(CheckStateChangedEvent event) {
				if (event.getChecked()) {
					Object element = event.getElement();
					versionToDefaultItem
							.put(
									(PHPVersion) ((IStructuredSelection) fProfilesViewer
											.getSelection()).getFirstElement(),
									(PHPexeItem) element);
					fJREsViewer.setCheckedElements(new Object[] { element });
				} else {
					versionToDefaultItem.remove(fJREsViewer.getInput());
				}

			}
		});

		Dialog.applyDialogFont(ancestor);
		return ancestor;
	}

	private PHPexeItem[] getCompatibleItems(PHPexeItem[] allItems,
			PHPVersion version) {
		String versionNumber = version.getAlias().substring(3);
		PHPexeItem[] result = versionToCompatibleItems.get(version);
		if (result == null) {
			result = phpExes.getCompatibleItems(allItems, version);
			versionToCompatibleItems.put(version, result);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.preference.IPreferencePage#performOk()
	 */
	public boolean performOk() {
		for (Iterator<PHPVersion> iterator = versionToDefaultItem.keySet()
				.iterator(); iterator.hasNext();) {
			PHPVersion version = iterator.next();
			phpExes.setItemDefaultForPHPVersion(versionToDefaultItem
					.get(version), version);
		}
		phpExes.save();
		return true;
	}

	public Control getControl() {
		return fControl;
	}

}
