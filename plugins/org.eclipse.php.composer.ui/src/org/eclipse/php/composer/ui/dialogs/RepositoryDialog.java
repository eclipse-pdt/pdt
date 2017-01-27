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

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.php.composer.api.repositories.Repository;
import org.eclipse.php.composer.api.repositories.RepositoryFactory;
import org.eclipse.php.composer.ui.ComposerUIPluginConstants;
import org.eclipse.php.composer.ui.ComposerUIPluginImages;
import org.eclipse.php.internal.ui.util.ValuedCombo;
import org.eclipse.php.internal.ui.util.ValuedCombo.Entry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

public class RepositoryDialog extends Dialog {

	private Repository repository;
	private String url;
	private String name;
	private String type;

	private static Map<String, String> repos = new HashMap<String, String>();
	static {
		repos.put("composer", Messages.RepositoryDialog_ComposerRepoType); //$NON-NLS-1$
		repos.put("package", Messages.RepositoryDialog_PackageRepoType); //$NON-NLS-1$
		repos.put("git", Messages.RepositoryDialog_GitRepoType); //$NON-NLS-1$
		repos.put("svn", Messages.RepositoryDialog_SvnRepoType); //$NON-NLS-1$
		repos.put("hg", Messages.RepositoryDialog_MercurialRepoType); //$NON-NLS-1$
		repos.put("pear", Messages.RepositoryDialog_PearRepoType); //$NON-NLS-1$
	}

	public RepositoryDialog(Shell parentShell, Repository repository) {
		super(parentShell);
		this.repository = repository;
	}

	public RepositoryDialog(IShellProvider parentShell, Repository repository) {
		super(parentShell);
		this.repository = repository;
	}

	/**
	 * @wbp.parser.constructor
	 */
	public RepositoryDialog(Shell parentShell) {
		super(parentShell);
	}

	public RepositoryDialog(IShellProvider parentShell) {
		super(parentShell);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		getShell().setText(Messages.RepositoryDialog_Title);
		getShell().setImage(ComposerUIPluginImages.REPO_GENERIC.createImage());

		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(2, false));

		Label lblType = new Label(container, SWT.NONE);
		GridData gd_lblType = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_lblType.widthHint = ComposerUIPluginConstants.DIALOG_LABEL_WIDTH;
		lblType.setLayoutData(gd_lblType);
		lblType.setText(Messages.RepositoryDialog_TypeLabel);

		final ValuedCombo typeControl = new ValuedCombo(container, SWT.READ_ONLY, buildEntryList());
		GridData gd_type = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_type.widthHint = ComposerUIPluginConstants.DIALOG_CONTROL_WIDTH;
		typeControl.setLayoutData(gd_type);
		typeControl.setItems((String[]) repos.values().toArray(new String[] {}));
		if (repository != null) {
			String type = repository.getType();
			if (repos.containsKey(type)) {
				typeControl.selectValue(type);
			}
			typeControl.setEnabled(false);
		}
		typeControl.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				type = typeControl.getSelectionValue();
			}
		});

		Label lblUrl = new Label(container, SWT.NONE);
		lblUrl.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		lblUrl.setText(Messages.RepositoryDialog_UrlLabel);

		final Text urlControl = new Text(container, SWT.BORDER);
		urlControl.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		urlControl.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				if (repository != null) {
					repository.setUrl(urlControl.getText());
				}
				url = urlControl.getText();
			}
		});
		if (repository != null && repository.has("url") && repository.getUrl() != null) { //$NON-NLS-1$
			urlControl.setText(repository.getUrl());
		} else {
			// must never be null, so at least be sure to always return an empty
			// string
			urlControl.setText(""); //$NON-NLS-1$
		}

		Label lblName = new Label(container, SWT.NONE);
		lblName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		lblName.setText(Messages.RepositoryDialog_NameLabel);

		final Text nameControl = new Text(container, SWT.BORDER);
		nameControl.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		nameControl.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				if (repository != null) {
					repository.setName(nameControl.getText());
				}
				name = nameControl.getText();
			}
		});
		if (repository != null && repository.has("name") && repository.getName() != null) { //$NON-NLS-1$
			nameControl.setText(repository.getName());
		} else {
			// must never be null, so at least be sure to always return an empty
			// string
			nameControl.setText(""); //$NON-NLS-1$
		}

		return container;
	}

	private List<Entry> buildEntryList() {
		List<Entry> res = new LinkedList<ValuedCombo.Entry>();
		for (java.util.Map.Entry<String, String> entry : repos.entrySet()) {
			res.add(new Entry(entry.getKey(), entry.getValue()));
		}
		return res;
	}

	public Repository getRepository() {
		if (repository != null) {
			return repository;
		}

		if (type == null) {
			return null;
		}

		Repository repo = RepositoryFactory.create(type);
		repo.setUrl(url != null ? url : ""); //$NON-NLS-1$
		repo.setName(name != null ? name : ""); //$NON-NLS-1$

		return repo;
	}
}
