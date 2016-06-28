/*******************************************************************************
 * Copyright (c) 2014, 2016 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.internal.ui.dialogs;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.php.composer.core.ComposerPreferences;
import org.eclipse.php.composer.core.model.IRepositories;
import org.eclipse.php.composer.core.model.IRepository;
import org.eclipse.php.composer.core.model.IRepositoryPackage;
import org.eclipse.php.composer.core.model.ModelFactory;
import org.eclipse.php.composer.internal.ui.preferences.ComposerPreferencePage;
import org.eclipse.php.composer.internal.ui.preferences.RepositoriesContentProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.dialogs.PreferencesUtil;

/**
 * @author Wojciech Galanciak, 2013
 * 
 */
public class AddRepositoryDialog extends TitleAreaDialog {

	private IRepositories repositories;
	private TableViewer repoViewer;
	private List<IRepository> result;

	/**
	 * Create the dialog.
	 * 
	 * @param parentShell
	 */
	public AddRepositoryDialog(Shell parentShell, IRepositories repositories) {
		super(parentShell);
		this.repositories = repositories;
		this.result = new ArrayList<IRepository>();
	}

	public List<IRepository> getRepositories() {
		return result;
	}

	@Override
	protected void okPressed() {
		IStructuredSelection selection = (IStructuredSelection) repoViewer
				.getSelection();
		Object[] selectedRepos = selection.toArray();
		for (Object repo : selectedRepos) {
			result.add((IRepository) repo);
		}
		super.okPressed();
	}

	/**
	 * Create contents of the dialog.
	 * 
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		setMessage(Messages.AddRepositoryDialog_Desc);
		setTitle(Messages.AddRepositoryDialog_Title);
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayoutData(new GridData(GridData.FILL_BOTH));

		container.setLayout(new GridLayout(2, false));

		repoViewer = new TableViewer(container, SWT.BORDER | SWT.FULL_SELECTION
				| SWT.MULTI);
		repoViewer.setContentProvider(new RepositoriesContentProvider());
		repoViewer.setLabelProvider(new LabelProvider() {
			public String getText(Object element) {
				if (element instanceof IRepository) {
					IRepository repo = (IRepository) element;
					switch (repo.getType()) {
					case PACKAGE:
						IRepositoryPackage rPackage = repo
								.getRepositoryPackage();
						return rPackage.getName();
					default:
						return repo.getUrl();
					}
				}
				return super.getText(element);
			}
		});
		repoViewer.setInput(getInput());

		Table repoTable = repoViewer.getTable();
		repoTable.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1,
				1));

		Button manageButton = new Button(container, SWT.NONE);
		manageButton.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false,
				false, 1, 1));
		manageButton.setText(Messages.AddRepositoryDialog_ManageButton);
		manageButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				PreferenceDialog dialog = PreferencesUtil
						.createPreferenceDialogOn(getShell(),
								ComposerPreferencePage.ID,
								new String[] { ComposerPreferencePage.ID },
								null);
				if (dialog.open() == Window.OK) {
					repoViewer.setInput(getInput());
				}
			}
		});
		return area;
	}

	/**
	 * Create contents of the button bar.
	 * 
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(450, 300);
	}

	private IRepositories getInput() {
		String reposString = ComposerPreferences.get(ComposerPreferences.REPOSITORIES_NODE);
		IRepositories repos = ModelFactory.createRepositories();
		if (reposString != null) {
			repos = repos.deserialize(reposString);
			if (repositories != null
					&& repositories.getRepositories().size() > 0) {
				for (IRepository r : repositories.getRepositories()) {
					repos.removeRepository(r, false);
				}
			}
		}
		return repos;
	}
}
