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
package org.eclipse.php.composer.ui.wizard.project;

import java.util.Observable;

import org.eclipse.dltk.internal.ui.wizards.dialogfields.DialogField;
import org.eclipse.dltk.internal.ui.wizards.dialogfields.IDialogFieldListener;
import org.eclipse.dltk.internal.ui.wizards.dialogfields.LayoutUtil;
import org.eclipse.dltk.internal.ui.wizards.dialogfields.StringDialogField;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

@SuppressWarnings("restriction")
public class AutoloadGroup extends Observable implements IDialogFieldListener {

	private Shell shell;
	private StringDialogField namespaceField;

	public AutoloadGroup(Composite composite, Shell shell) {

		this.shell = shell;

		final Composite nameComposite = new Composite(composite, SWT.NONE);
		nameComposite.setFont(composite.getFont());
		nameComposite.setLayout(new GridLayout(2, false));
		nameComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		// text field for project vendor name
		namespaceField = new StringDialogField();
		namespaceField.setLabelText(Messages.AutoloadGroup_NamespaceLabel);
		namespaceField.setDialogFieldListener(this);
		namespaceField.doFillIntoGrid(nameComposite, 2);
		LayoutUtil.setHorizontalGrabbing(namespaceField.getTextControl(null));

	}

	protected void fireEvent() {
		setChanged();
		notifyObservers();
	}

	@Override
	public void dialogFieldChanged(DialogField field) {
		fireEvent();
	}

	public String getNamespace() {
		return namespaceField.getText();
	}

	public void setNamespace(String namespace) {
		namespaceField.setText(namespace);
	}
}
