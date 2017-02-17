/*******************************************************************************
 * Copyright (c) 2012, 2016 PDT Extension Group and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.ui.parts.composer;

import org.eclipse.php.composer.api.ComposerPackage;
import org.eclipse.php.composer.ui.controller.PackageController;
import org.eclipse.php.composer.ui.utils.WidgetFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.widgets.FormToolkit;

public class PackageSearchPart {

	protected String name;

	protected FormToolkit toolkit;

	protected Composite body;
	protected Button checkbox;

	protected ComposerPackage composerPackage;

	public PackageSearchPart(Composite parent, ComposerPackage composerPackage, FormToolkit toolkit, String name) {
		this.toolkit = toolkit;
		this.name = name;
		this.composerPackage = composerPackage;
		create(parent, new WidgetFactory(toolkit));
	}

	protected void create(Composite parent, WidgetFactory factory) {
		createBody(parent, factory);
		createPackageCheckbox(body, factory, name);
	}

	protected Composite createBody(Composite parent, WidgetFactory factory) {
		body = factory.createComposite(parent, SWT.BORDER);
		body.setLayout(new GridLayout());
		body.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		body.setBackground(getBackgroundColor());
		return body;
	}

	protected Button createPackageCheckbox(Composite parent, WidgetFactory factory, String name) {
		checkbox = new Button(parent, SWT.CHECK);
		checkbox.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		checkbox.setSelection(true);
		checkbox.setImage(PackageController.getPackageImage(name));
		checkbox.setText(PackageController.getPackageName(name));
		return checkbox;
	}

	protected Color getBackgroundColor() {
		if (toolkit != null) {
			return toolkit.getColors().getColor(IFormColors.TB_BG);
		} else {
			return Display.getCurrent().getSystemColor(SWT.COLOR_LIST_BACKGROUND);
		}
	}

	public void addSelectionListener(SelectionListener listener) {
		checkbox.addSelectionListener(listener);
	}

	public void removeSelectionListener(SelectionListener listener) {
		checkbox.removeSelectionListener(listener);
	}

	public String getName() {
		return name;
	}

	public boolean isChecked() {
		return checkbox.getSelection();
	}

	public void dispose() {
		body.dispose();
	}
}
