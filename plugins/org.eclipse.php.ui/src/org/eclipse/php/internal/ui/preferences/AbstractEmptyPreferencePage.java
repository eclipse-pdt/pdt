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
package org.eclipse.php.internal.ui.preferences;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.PlatformUI;

abstract public class AbstractEmptyPreferencePage extends PropertyAndPreferencePage
		implements IWorkbenchPreferencePage {

	public AbstractEmptyPreferencePage() {
		super();
		noDefaultAndApplyButton();
	}

	public AbstractEmptyPreferencePage(String title) {
		super();
	}

	public AbstractEmptyPreferencePage(String title, ImageDescriptor image) {
		super();
	}

	@Override
	public void init(IWorkbench workbench) {
	}

	protected abstract String getPreferenceHelpId();

	protected abstract String getPropertiesHelpId();

	abstract public String getBodyText();

	@Override
	protected Control createPreferenceContent(Composite composite) {
		Composite comp = new Composite(composite, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		comp.setLayout(layout);

		Label descLabel = new Label(comp, SWT.NONE);
		descLabel.setText(getBodyText());

		PlatformUI.getWorkbench().getHelpSystem().setHelp(getControl(),
				getProject() != null ? getPropertiesHelpId() : getPreferenceHelpId());

		return comp;
	}

	@Override
	protected String getPreferencePageID() {
		return null;
	}

	@Override
	protected String getPropertyPageID() {
		return null;
	}

	@Override
	protected boolean hasProjectSpecificOptions(IProject project) {
		return false;
	}

	@Override
	protected boolean isProjectPreferencePage() {
		return false;
	}

}
