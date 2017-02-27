/*******************************************************************************
 * Copyright (c) 2012, 2016, 2017 PDT Extension Group and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *     Kaloyan Raev - [501269] externalize strings
 *******************************************************************************/
package org.eclipse.php.composer.ui.editor.toolbar;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.ControlContribution;
import org.eclipse.php.composer.ui.ComposerUIPluginImages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.FormToolkit;

public class SearchControl extends ControlContribution {

	private final IManagedForm managedForm;

	Text searchText;

	private List<ModifyListener> modifyListeners = new ArrayList<ModifyListener>();

	public SearchControl(String id, IManagedForm managedForm) {
		super(id);
		this.managedForm = managedForm;
	}

	public String getText() {
		return searchText.getText().trim();
	}

	@Override
	protected Control createControl(Composite parent) {
		if (parent instanceof ToolBar) {
			// the FormHeading class sets the toolbar cursor to hand for some
			// reason,
			// we change it back so the input control can use a proper I-beam
			// cursor
			parent.setCursor(null);
		}

		FormToolkit toolkit = managedForm.getToolkit();
		Composite composite = toolkit.createComposite(parent);

		GridLayout layout = new GridLayout(3, false);
		layout.verticalSpacing = 0;
		layout.marginWidth = 0;

		switch (Platform.getOS()) {
		case Platform.OS_LINUX:
			layout.marginHeight = -2;
			break;
		case Platform.OS_MACOSX:
			layout.marginHeight = 1;
			break;

		default:
			layout.marginHeight = 0;
		}

		composite.setLayout(layout);
		composite.setBackground(null);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		Control label = toolkit.createLabel(composite, Messages.SearchControl_SearchLabel);
		label.setBackground(null);

		searchText = toolkit.createText(composite, "", SWT.FLAT | SWT.SEARCH); //$NON-NLS-1$
		searchText.setData(FormToolkit.TEXT_BORDER, Boolean.TRUE);
		searchText.setLayoutData(new GridData(200, -1));
		ToolBar cancelBar = new ToolBar(composite, SWT.FLAT);

		final ToolItem clearToolItem = new ToolItem(cancelBar, SWT.NONE);
		clearToolItem.setEnabled(false);
		clearToolItem.setImage(ComposerUIPluginImages.CLEAR.createImage());
		clearToolItem.setDisabledImage(ComposerUIPluginImages.CLEAR_DISABLED.createImage());
		clearToolItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				searchText.setText(""); //$NON-NLS-1$
			}
		});

		searchText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				clearToolItem.setEnabled(searchText.getText().length() > 0);
				for (ModifyListener listener : modifyListeners) {
					listener.modifyText(e);
				}
			}
		});

		toolkit.paintBordersFor(composite);

		return composite;
	}

	public void addModifyListener(ModifyListener listener) {
		if (!modifyListeners.contains(listener)) {
			modifyListeners.add(listener);
		}
	}
}
