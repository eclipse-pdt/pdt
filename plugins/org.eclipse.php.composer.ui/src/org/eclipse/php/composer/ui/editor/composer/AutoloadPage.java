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
package org.eclipse.php.composer.ui.editor.composer;

import org.eclipse.php.composer.ui.editor.ComposerFormPage;
import org.eclipse.php.composer.ui.editor.FormLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;

public class AutoloadPage extends ComposerFormPage {

	public final static String ID = "org.eclipse.php.composer.ui.editor.composer.AutoloadPage"; //$NON-NLS-1$

	protected ComposerFormEditor editor;
	private Composite left;
	private Composite right;

	private Psr0Section psr0Section;
	private Psr4Section psr4Section;

	public AutoloadPage(ComposerFormEditor editor, String id, String title) {
		super(editor, id, title);
		this.editor = editor;
	}

	@Override
	public void setActive(boolean active) {
		super.setActive(active);

		if (active) {
			editor.getHeaderForm().getForm().setText(Messages.AutoloadPage_Title);
		}
	}

	@Override
	protected void createFormContent(IManagedForm managedForm) {

		ScrolledForm form = managedForm.getForm();
		FormToolkit toolkit = managedForm.getToolkit();

		form.getBody().setLayout(FormLayoutFactory.createFormGridLayout(true, 2));

		left = toolkit.createComposite(form.getBody(), SWT.NONE);
		left.setLayout(FormLayoutFactory.createFormPaneGridLayout(false, 1));
		left.setLayoutData(new GridData(GridData.FILL_BOTH));

		psr4Section = new Psr4Section(this, left);
		psr4Section.setEnabled(enabled);

		psr0Section = new Psr0Section(this, left);
		psr0Section.setEnabled(enabled);

		right = toolkit.createComposite(form.getBody(), SWT.NONE);
		right.setLayout(FormLayoutFactory.createFormPaneGridLayout(false, 1));
		right.setLayoutData(new GridData(GridData.FILL_BOTH));

	}

	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);

		if (psr0Section != null) {
			psr0Section.setEnabled(enabled);
		}

		if (psr4Section != null) {
			psr4Section.setEnabled(enabled);
		}
	}
}
