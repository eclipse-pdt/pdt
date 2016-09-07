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
package org.eclipse.php.composer.ui.utils;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;

public class WidgetFactory {

	protected FormToolkit toolkit;

	public WidgetFactory(FormToolkit toolkit) {
		this.toolkit = toolkit;
	}

	private static WidgetFactory builder(FormToolkit toolkit) {
		return new WidgetFactory(toolkit);
	}

	// ---- Button

	public Button createButton(Composite parent) {
		return createButton(parent, SWT.DEFAULT);
	}

	public static Button createButton(FormToolkit toolkit, Composite parent) {
		return WidgetFactory.builder(toolkit).createButton(parent);
	}

	public Button createButton(Composite parent, int style) {
		if (toolkit == null) {
			return new Button(parent, style);
		} else {
			return toolkit.createButton(parent, "", style);
		}
	}

	public static Button createButton(FormToolkit toolkit, Composite parent, int style) {
		return WidgetFactory.builder(toolkit).createButton(parent, style);
	}

	// ---- Label

	public Label createLabel(Composite parent) {
		return createLabel(parent, SWT.NONE);
	}

	public static Label createLabel(FormToolkit toolkit, Composite parent) {
		return WidgetFactory.builder(toolkit).createLabel(parent);
	}

	public Label createLabel(Composite parent, int style) {
		if (toolkit == null) {
			return new Label(parent, style);
		} else {
			return toolkit.createLabel(parent, "", style);
		}
	}

	public static Label createLabel(FormToolkit toolkit, Composite parent, int style) {
		return WidgetFactory.builder(toolkit).createLabel(parent, style);
	}

	// ---- Composite

	public Composite createComposite(Composite parent) {
		return createComposite(parent, SWT.NONE);
	}

	public static Composite createComposite(FormToolkit toolkit, Composite parent) {
		return WidgetFactory.builder(toolkit).createComposite(parent);
	}

	public Composite createComposite(Composite parent, int style) {
		if (toolkit == null) {
			return new Composite(parent, style);
		} else {
			return toolkit.createComposite(parent, style);
		}
	}

	public static Composite createComposite(FormToolkit toolkit, Composite parent, int style) {
		return WidgetFactory.builder(toolkit).createComposite(parent, style);
	}

	// ---- ExpandableComposite

	public ExpandableComposite createExpandableComposite(Composite parent) {
		return createExpandableComposite(parent, SWT.NONE);
	}

	public static ExpandableComposite createExpandableComposite(FormToolkit toolkit, Composite parent) {
		return WidgetFactory.builder(toolkit).createExpandableComposite(parent);
	}

	public ExpandableComposite createExpandableComposite(Composite parent, int style) {
		if (toolkit == null) {
			return new ExpandableComposite(parent, style);
		} else {
			return toolkit.createExpandableComposite(parent, style);
		}
	}

	public static ExpandableComposite createExpandableComposite(FormToolkit toolkit, Composite parent, int style) {
		return WidgetFactory.builder(toolkit).createExpandableComposite(parent, style);
	}

	public ExpandableComposite createExpandableComposite(Composite parent, int style, int expansionStyle) {
		if (toolkit == null) {
			return new ExpandableComposite(parent, style, expansionStyle);
		} else {
			ExpandableComposite ec = new ExpandableComposite(parent, style | toolkit.getOrientation(), expansionStyle);
			ec.setMenu(parent.getMenu());
			toolkit.adapt(ec, true, true);
			return ec;
		}
	}

	// ---- Text

	public Text createText(Composite parent) {
		return createText(parent, SWT.DEFAULT);
	}

	public static Text createText(FormToolkit toolkit, Composite parent) {
		return WidgetFactory.builder(toolkit).createText(parent);
	}

	public Text createText(Composite parent, int style) {
		if (toolkit == null) {
			return new Text(parent, style);
		} else {
			return toolkit.createText(parent, "", style);
		}
	}

	public static Text createText(FormToolkit toolkit, Composite parent, int style) {
		return WidgetFactory.builder(toolkit).createText(parent, style);
	}

	// ---- ComboBox

	public Combo createCombo(Composite parent) {
		return createCombo(parent, SWT.BORDER);
	}

	public static Combo createCombo(FormToolkit toolkit, Composite parent) {
		return WidgetFactory.builder(toolkit).createCombo(parent);
	}

	public Combo createCombo(Composite parent, int style) {
		if (toolkit == null) {
			return new Combo(parent, style);
		} else {
			Combo combo = new Combo(parent, style);
			toolkit.adapt(combo, false, false);
			return combo;
		}
	}

	public static Combo createCombo(FormToolkit toolkit, Composite parent, int style) {
		return WidgetFactory.builder(toolkit).createCombo(parent, style);
	}
}
