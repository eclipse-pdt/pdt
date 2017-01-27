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
package org.eclipse.php.composer.ui.dialogs;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.php.composer.api.objects.Person;
import org.eclipse.php.composer.ui.ComposerUIPluginConstants;
import org.eclipse.php.composer.ui.ComposerUIPluginImages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

public class PersonDialog extends Dialog {

	private Person person;
	private Text name;
	private Text email;
	private Text homepage;
	private Text role;

	/**
	 * @wbp.parser.constructor
	 */
	public PersonDialog(Shell parentShell, Person author) {
		super(parentShell);
		this.person = author;
	}

	public PersonDialog(IShellProvider parentShell, Person author) {
		super(parentShell);
		this.person = author;
	}

	public Person getPerson() {
		return person;
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		getShell().setText(Messages.PersonDialog_Title);
		getShell().setImage(ComposerUIPluginImages.PERSON.createImage());

		Composite contents = new Composite(parent, SWT.NONE);
		contents.setLayout(new GridLayout(2, false));

		Label lblName = new Label(contents, SWT.NONE);
		GridData gd_lblName = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_lblName.widthHint = ComposerUIPluginConstants.DIALOG_LABEL_WIDTH;
		lblName.setLayoutData(gd_lblName);
		lblName.setText(Messages.PersonDialog_NameLabel);

		name = new Text(contents, SWT.BORDER);
		GridData gd_name = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_name.widthHint = ComposerUIPluginConstants.DIALOG_CONTROL_WIDTH;
		name.setLayoutData(gd_name);
		if (person.getName() != null) {
			name.setText(person.getName());
		}
		name.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				person.setName(name.getText());
			}
		});

		Label lblEmail = new Label(contents, SWT.NONE);
		lblEmail.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		lblEmail.setText(Messages.PersonDialog_EmailLabel);

		email = new Text(contents, SWT.BORDER);
		email.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		if (person.getEmail() != null) {
			email.setText(person.getEmail());
		}
		email.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				person.setEmail(email.getText());
			}
		});

		Label lblHomepage = new Label(contents, SWT.NONE);
		lblHomepage.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		lblHomepage.setText(Messages.PersonDialog_HomepageLabel);

		homepage = new Text(contents, SWT.BORDER);
		homepage.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		if (person.getHomepage() != null) {
			homepage.setText(person.getHomepage());
		}
		homepage.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				person.setHomepage(homepage.getText());
			}
		});

		Label lblRole = new Label(contents, SWT.NONE);
		lblRole.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		lblRole.setText(Messages.PersonDialog_RoleLabel);

		role = new Text(contents, SWT.BORDER);
		role.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		if (person.getRole() != null) {
			role.setText(person.getRole());
		}
		role.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				person.setRole(role.getText());
			}
		});

		return contents;
	}
}
