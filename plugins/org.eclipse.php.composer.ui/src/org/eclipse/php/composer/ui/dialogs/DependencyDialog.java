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
import org.eclipse.php.composer.api.VersionedPackage;
import org.eclipse.php.composer.ui.ComposerUIPluginConstants;
import org.eclipse.php.composer.ui.parts.composer.VersionSuggestion;
import org.eclipse.php.composer.ui.utils.WidgetFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

public class DependencyDialog extends Dialog {

	private VersionedPackage dependency;
	private Text name;
	private Text version;

	/**
	 * @wbp.parser.constructor
	 * @param parentShell
	 * @param dependency
	 */
	public DependencyDialog(Shell parentShell, VersionedPackage dependency) {
		super(parentShell);
		setShellStyle(SWT.DIALOG_TRIM);
		this.dependency = dependency;
	}

	public DependencyDialog(IShellProvider parentShell, VersionedPackage dependency) {
		super(parentShell);
		this.dependency = dependency;
	}

	public VersionedPackage getDependency() {
		return dependency;
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		getShell().setText(Messages.DependencyDialog_Title);

		Composite contents = new Composite(parent, SWT.NONE);
		contents.setLayout(new GridLayout(2, false));
		GridData gd_contents = new GridData();
		gd_contents.widthHint = 350;
		contents.setLayoutData(gd_contents);

		Label lblName = new Label(contents, SWT.NONE);
		GridData gd_lblName = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_lblName.widthHint = ComposerUIPluginConstants.DIALOG_LABEL_WIDTH;
		lblName.setLayoutData(gd_lblName);
		lblName.setText(Messages.DependencyDialog_NameLabel);

		name = new Text(contents, SWT.BORDER);
		GridData gd_name = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_name.widthHint = ComposerUIPluginConstants.DIALOG_CONTROL_WIDTH;
		name.setLayoutData(gd_name);
		name.setEnabled(false);
		if (dependency.getName() != null) {
			name.setText(dependency.getName());
		}

		Label lblVersion = new Label(contents, SWT.NONE);
		lblVersion.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		lblVersion.setText(Messages.DependencyDialog_VersionLabel);

		version = new Text(contents, SWT.BORDER);
		version.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		if (dependency.getVersion() != null) {
			version.setText(dependency.getVersion());
		}
		version.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dependency.setVersion(version.getText());
			}
		});

		new VersionSuggestion(dependency.getName(), parent, version, null, new WidgetFactory(null));

		return contents;
	}
}
