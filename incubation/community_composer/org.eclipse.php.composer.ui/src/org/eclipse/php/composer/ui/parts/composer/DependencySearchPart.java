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

import org.eclipse.php.composer.ui.utils.WidgetFactory;
import org.eclipse.php.composer.ui.utils.WidgetHelper;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.events.IHyperlinkListener;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Twistie;

import org.eclipse.php.composer.api.ComposerPackage;
import org.eclipse.php.composer.api.VersionedPackage;

public class DependencySearchPart extends PackageSearchPart {

	protected Twistie toggle;
	protected Text version;
	protected VersionSuggestion suggestion;
	
	public DependencySearchPart(Composite parent, ComposerPackage composerPackage, FormToolkit toolkit, String name) {
		super(parent, composerPackage, toolkit, name);
	}

	protected void create(Composite parent, WidgetFactory factory) {
		createBody(parent, factory);
		WidgetHelper.trimComposite(body, 0, 0, 0, 0, 0, 0);

		// title
		Composite title = factory.createComposite(body, SWT.NO_BACKGROUND);
		title.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		title.setLayout(new GridLayout(3, false));
		WidgetHelper.trimComposite(title, -5,-5,-5,-5, 0, 0);

		// toggle box
		Composite toggleBox = factory.createComposite(title, SWT.NO_BACKGROUND);
		toggleBox.setLayout(new GridLayout());
		toggle = new Twistie(toggleBox, SWT.NO_BACKGROUND | SWT.NO_FOCUS);
		toggle.setData(this);
		WidgetHelper.trimComposite(toggleBox, 3, -7, 0, 0, 0, 0);
		
		// package
		createPackageCheckbox(title, factory, name);
		
		// version
		version = factory.createText(title, SWT.SINGLE | SWT.BORDER);
		GridData gd = new GridData(SWT.RIGHT, SWT.CENTER, false, false);
		gd.widthHint = 120;
		version.setLayoutData(gd);
		version.setData(this);
		
		// suggestion
		suggestion = new VersionSuggestion(name, body, version, composerPackage, toolkit);
		setExpanded(false);
		WidgetHelper.trimComposite(suggestion.getBody(), -5,-5,-5,-5, 0, 0);
	}
	
	public void addToggleListener(IHyperlinkListener listener) {
		toggle.addHyperlinkListener(listener);
	}
	
	public void removeToggleListener(IHyperlinkListener listener) {
		toggle.removeHyperlinkListener(listener);
	}
	
	public boolean isExpanded() {
		return toggle.isExpanded();
	}
	
	public Text getVersionControl() {
		return version;
	}
	
	public void setExpanded(boolean expanded) {
		toggle.setExpanded(expanded);
		suggestion.getBody().setVisible(expanded);
		((GridData)suggestion.getBody().getLayoutData()).exclude = !expanded;
	}
	
	public VersionedPackage getPackage() {
		VersionedPackage pkg = new VersionedPackage();
		pkg.setName(name);
		pkg.setVersion(version.getText());
		
		return pkg;
	}
}
