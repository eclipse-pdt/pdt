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

public class CodeStylePreferencePage extends PropertyAndPreferencePage
		implements IWorkbenchPreferencePage {

	public CodeStylePreferencePage() {
		super();
		noDefaultAndApplyButton();
	}

	public CodeStylePreferencePage(String title) {
		super();
	}

	public CodeStylePreferencePage(String title, ImageDescriptor image) {
		super();
	}

	public void init(IWorkbench workbench) {
	}

	@Override
	protected Control createPreferenceContent(Composite composite) {
		Composite comp = new Composite(composite, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		comp.setLayout(layout);

		Label descLabel = new Label(comp, SWT.NONE);
		descLabel.setText(PreferencesMessages.CodeStylePreferencePage_0);

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
