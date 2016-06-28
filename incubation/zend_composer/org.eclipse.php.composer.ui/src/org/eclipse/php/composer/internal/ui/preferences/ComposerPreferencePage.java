/*******************************************************************************
 * Copyright (c) 2013, 2016 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.internal.ui.preferences;

import java.io.File;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.viewers.*;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.php.composer.core.ComposerCorePlugin;
import org.eclipse.php.composer.core.ComposerPreferences;
import org.eclipse.php.composer.core.ComposerService;
import org.eclipse.php.composer.core.model.IRepositories;
import org.eclipse.php.composer.core.model.IRepository;
import org.eclipse.php.composer.core.model.IRepositoryPackage;
import org.eclipse.php.composer.core.model.ModelFactory;
import org.eclipse.php.composer.internal.ui.ComposerUIPlugin;
import org.eclipse.php.composer.internal.ui.wizards.CreateRepositoryWizard;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.dialogs.PreferencesUtil;
import org.osgi.service.prefs.BackingStoreException;

/**
 * Composer preferences page.
 * 
 * @author Wojciech Galanciak, 2013
 * 
 */
public class ComposerPreferencePage extends PreferencePage implements IWorkbenchPreferencePage {

	private static final String PHP_EXECS_PREFS_PAGE_ID = "org.eclipse.php.debug.ui.preferencesphps.PHPsPreferencePage"; //$NON-NLS-1$

	public static final String ID = "org.eclipse.php.composer.ui.preferences.ComposerPreferencePage"; //$NON-NLS-1$

	private TableViewer viewer;
	private Button removeButton;
	private Button modifyButton;
	private Button addButton;

	private IEclipsePreferences prefs;

	private IRepositories input;

	private Text composerPharText;

	private Combo phpCombo;

	private Button updateButton;

	private Button useDefaultButton;

	private Button useCustomButton;

	private Button browseButton;

	public ComposerPreferencePage() {
		this.prefs = InstanceScope.INSTANCE.getNode(ComposerCorePlugin.PLUGIN_ID);
		this.input = ModelFactory.createRepositories();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.preference.PreferencePage#performOk()
	 */
	public boolean performOk() {
		boolean isDirty = false;
		String phpExec = phpCombo.getText();
		if (!phpExec.equals(prefs.get(ComposerPreferences.PHP_EXEC_NODE, null))) {
			prefs.put(ComposerPreferences.PHP_EXEC_NODE, phpExec);
			isDirty = true;
		}
		if (useCustomButton.getSelection()) {
			String composerPhar = composerPharText.getText().trim();
			if (!composerPhar.isEmpty()
					&& !composerPhar.equals(ComposerPreferences.get(ComposerPreferences.COMPOSER_PHAR_NODE))) {
				prefs.put(ComposerPreferences.COMPOSER_PHAR_NODE, composerPhar);
				isDirty = true;
			}
		}
		if (updateButton.getSelection() != prefs.getBoolean(ComposerPreferences.UPDATE_COMPOSER_PHAR_NODE, true)) {
			prefs.putBoolean(ComposerPreferences.UPDATE_COMPOSER_PHAR_NODE, updateButton.getSelection());
			isDirty = true;
		}
		if (input.isDirty()) {
			prefs.put(ComposerPreferences.REPOSITORIES_NODE, input.serialize());
			isDirty = true;
		}
		if (isDirty) {
			try {
				prefs.flush();
			} catch (BackingStoreException e) {
				ComposerUIPlugin.logError(e);
			}
		}
		return super.performOk();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.preference.PreferencePage#createContents(org.eclipse
	 * .swt.widgets.Composite)
	 */
	protected Control createContents(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		composite.setLayout(new GridLayout(1, false));

		createPHPExecSection(composite);

		createComposerPharSection(composite);

		createRepositoriesSection(composite);

		initializeValues();

		return composite;
	}

	@Override
	public void setVisible(boolean visible) {
		if (visible && phpCombo != null && !phpCombo.isDisposed()) {
			String currentSelection = phpCombo.getText();
			initPHPExecs();
			if (currentSelection != null && !currentSelection.isEmpty()) {
				String[] items = phpCombo.getItems();
				for (int i = 0; i < items.length; i++) {
					if (currentSelection.equals(items[i])) {
						phpCombo.select(i);
						break;
					}
				}
			}
			setValid(validatePage());
		}
		super.setVisible(visible);
	}

	private void createRepositoriesSection(Composite parent) {
		Group filtersSection = new Group(parent, SWT.NONE);
		filtersSection.setText(Messages.ComposerPreferencePage_RepoSection);
		filtersSection.setLayout(new GridLayout(2, false));
		filtersSection.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		filtersSection.setLayout(new GridLayout(2, false));
		Label filtersLabel = new Label(filtersSection, SWT.NONE);
		filtersLabel.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, 2, 1));
		filtersLabel.setText(Messages.ComposerPreferencePage_RepoDesc);
		viewer = new TableViewer(filtersSection, SWT.SINGLE | SWT.BORDER);
		viewer.getTable().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		viewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				ISelection selection = viewer.getSelection();
				if (selection != null && !selection.isEmpty()) {
					removeButton.setEnabled(true);
					modifyButton.setEnabled(true);
				} else {
					removeButton.setEnabled(false);
					modifyButton.setEnabled(false);
				}
			}
		});
		viewer.setContentProvider(new RepositoriesContentProvider());
		viewer.setLabelProvider(new LabelProvider() {
			public String getText(Object element) {
				if (element instanceof IRepository) {
					IRepository repo = (IRepository) element;
					switch (repo.getType()) {
					case PACKAGE:
						IRepositoryPackage rPackage = repo.getRepositoryPackage();
						return rPackage.getName();
					default:
						return repo.getUrl();
					}
				}
				return super.getText(element);
			}
		});

		Composite buttonsSection = new Composite(filtersSection, SWT.NONE);
		GridData gd = new GridData(SWT.FILL, SWT.FILL, false, true);
		gd.widthHint = 90;
		buttonsSection.setLayoutData(gd);
		GridLayout layout = new GridLayout(1, true);
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		buttonsSection.setLayout(layout);
		addButton = new Button(buttonsSection, SWT.PUSH);
		addButton.setText(Messages.ComposerPreferencePage_RepoAdd);
		addButton.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		addButton.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				CreateRepositoryWizard wizard = new CreateRepositoryWizard(null, input);
				WizardDialog dialog = new WizardDialog(getShell(), wizard);
				dialog.setPageSize(550, 350);
				dialog.create();
				if (dialog.open() == Window.OK) {
					IRepository repo = wizard.getRepository();
					input.addRepository(repo, true);
					viewer.refresh();
				}
			}
		});
		modifyButton = new Button(buttonsSection, SWT.PUSH);
		modifyButton.setText(Messages.ComposerPreferencePage_RepoModify);
		modifyButton.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		modifyButton.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				IStructuredSelection sel = (IStructuredSelection) viewer.getSelection();
				Object[] selected = sel.toArray();
				if (selected.length > 0) {
					IRepository oldRepo = (IRepository) selected[0];
					CreateRepositoryWizard wizard = new CreateRepositoryWizard(oldRepo, input);
					WizardDialog dialog = new WizardDialog(getShell(), wizard);
					dialog.setPageSize(550, 350);
					dialog.create();
					if (dialog.open() == Window.OK) {
						IRepository repo = wizard.getRepository();
						oldRepo.setType(repo.getType());
						oldRepo.setUrl(repo.getUrl());
						oldRepo.setRepositoryPackage(repo.getRepositoryPackage());
						oldRepo.setParameters(repo.getParameters());
						viewer.refresh();
					}
				}
			}
		});
		removeButton = new Button(buttonsSection, SWT.PUSH);
		removeButton.setText(Messages.ComposerPreferencePage_RepoRemove);
		removeButton.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		removeButton.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				removeElement(viewer.getSelection());
			}
		});
		removeButton.setEnabled(false);
		modifyButton.setEnabled(false);
	}

	private void createPHPExecSection(Composite parent) {
		Group group = new Group(parent, SWT.NONE);
		group.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		group.setText(Messages.ComposerPreferencePage_PhpSection);
		group.setLayout(new GridLayout(3, false));

		Label blackberyDescLabel = new Label(group, SWT.WRAP);
		blackberyDescLabel.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 3, 1));
		blackberyDescLabel.setText(Messages.ComposerPreferencePage_PhpDesc);

		Label label = new Label(group, SWT.NONE);
		label.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label.setText(Messages.ComposerPreferencePage_PhpLabel);

		phpCombo = new Combo(group, SWT.BORDER | SWT.READ_ONLY);
		GridData gd = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd.widthHint = 300;
		phpCombo.setLayoutData(gd);
		phpCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				setValid(validatePage());
			}
		});
		Button button = new Button(group, SWT.NONE);
		button.setText(Messages.ComposerPreferencePage_PhpManage);
		gd = new GridData(SWT.FILL, SWT.FILL, false, true);
		gd.widthHint = 90;
		button.setLayoutData(gd);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				PreferenceDialog dialog = PreferencesUtil.createPreferenceDialogOn(getShell(), PHP_EXECS_PREFS_PAGE_ID,
						new String[] { PHP_EXECS_PREFS_PAGE_ID, ID }, null);
				if (dialog.open() == Window.OK) {
					String[] phpExecsNames = ComposerService.getPHPExecNames();
					for (String name : phpExecsNames) {
						if (!phpCombo.isDisposed()) {
							phpCombo.add(name);
						}
					}
				}
			}
		});
	}

	private void createComposerPharSection(Composite parent) {
		Group group = new Group(parent, SWT.NONE);
		group.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		group.setText(Messages.ComposerPreferencePage_PharSection);
		group.setLayout(new GridLayout(3, false));

		Label descLabel = new Label(group, SWT.WRAP);
		descLabel.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 3, 1));
		descLabel.setText(Messages.ComposerPreferencePage_PharDesc);

		useDefaultButton = new Button(group, SWT.RADIO);
		useDefaultButton.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 3, 1));
		useDefaultButton.setText(Messages.ComposerPreferencePage_BuiltIn);

		useCustomButton = new Button(group, SWT.RADIO);
		useCustomButton.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 3, 1));
		useCustomButton.setText(Messages.ComposerPreferencePage_External);

		Label pharLabel = new Label(group, SWT.NONE);
		pharLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		pharLabel.setText(Messages.ComposerPreferencePage_PharLabel);

		composerPharText = new Text(group, SWT.BORDER);
		GridData gd = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd.widthHint = 300;
		composerPharText.setLayoutData(gd);
		composerPharText.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent event) {
				setValid(validatePage());
			}
		});
		browseButton = new Button(group, SWT.NONE);
		browseButton.setText(Messages.ComposerPreferencePage_PharBrowse);
		gd = new GridData(SWT.FILL, SWT.FILL, false, true);
		gd.widthHint = 90;
		browseButton.setLayoutData(gd);
		browseButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String selectedDir = handlePharSelection(Messages.ComposerPreferencePage_PharDialogTitle);
				composerPharText.setText(selectedDir);
				setValid(validatePage());
			}
		});
		useDefaultButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				composerPharText.setEnabled(false);
				browseButton.setEnabled(false);
				setValid(validatePage());
			}
		});
		useCustomButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				composerPharText.setEnabled(true);
				browseButton.setEnabled(true);
				setValid(validatePage());
			}
		});
		updateButton = new Button(group, SWT.CHECK);
		updateButton.setText(Messages.ComposerPreferencePage_Update);
		updateButton.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 3, 1));
	}

	private boolean validatePage() {
		if (phpCombo.getText().isEmpty()) {
			setMessage(Messages.ComposerPreferencePage_PhpWarning, IMessageProvider.WARNING);
			return true;
		}
		if (useCustomButton.getSelection()) {
			if (!composerPharText.getText().isEmpty()) {
				File composerPhar = new File(composerPharText.getText());
				if (!composerPhar.exists() || !ComposerPreferences.COMPOSER_PHAR.equals(composerPhar.getName())) {
					setErrorMessage(Messages.ComposerPreferencePage_PharError);
					return false;
				}
			} else {
				setErrorMessage(Messages.ComposerPreferencePage_PathEmptyError);
				return false;
			}
		}
		setMessage(null);
		setErrorMessage(null);
		return true;
	}

	private void initializeValues() {
		String repos = ComposerPreferences.get(ComposerPreferences.REPOSITORIES_NODE);
		if (repos != null) {
			IRepositories repositories = ModelFactory.createRepositories();
			input = repositories.deserialize(repos);
		}
		viewer.setInput(input);
		viewer.refresh();

		boolean updatePhar = ComposerPreferences.getBoolean(ComposerPreferences.UPDATE_COMPOSER_PHAR_NODE, true);
		updateButton.setSelection(updatePhar);

		String phar = ComposerPreferences.get(ComposerPreferences.COMPOSER_PHAR_NODE);
		if (phar.equals(ComposerPreferences.getDefaultComposerPhar())) {
			useDefaultButton.setSelection(true);
			composerPharText.setEnabled(false);
			browseButton.setEnabled(false);
		} else {
			useCustomButton.setSelection(true);
			composerPharText.setText(phar);
		}

		initPHPExecs();

		String phpExec = ComposerPreferences.get(ComposerPreferences.PHP_EXEC_NODE);
		if (phpExec != null) {
			String[] items = phpCombo.getItems();
			for (int i = 0; i < items.length; i++) {
				if (items[i].equals(phpExec)) {
					phpCombo.select(i);
					break;
				}
			}
		}
		setValid(validatePage());
	}

	private void initPHPExecs() {
		phpCombo.removeAll();
		String[] phpExecsNames = ComposerService.getPHPExecNames();
		for (String name : phpExecsNames) {
			phpCombo.add(name);
		}
		if (phpExecsNames.length > 0) {
			phpCombo.select(phpExecsNames.length - 1);
		}
	}

	private void removeElement(ISelection selection) {
		IStructuredSelection sel = (IStructuredSelection) selection;
		Object[] toRemove = sel.toArray();
		for (Object elem : toRemove) {
			if (elem == null) {
				return;
			}
			input.removeRepository((IRepository) elem, true);
		}
		viewer.refresh();
	}

	private String handlePharSelection(String message) {
		FileDialog dialog = new FileDialog(getShell());
		dialog.setFilterExtensions(new String[] { "*.phar", "*" }); //$NON-NLS-1$ //$NON-NLS-2$
		dialog.setText(message);
		String result = dialog.open();
		return result == null ? "" : result; //$NON-NLS-1$
	}

}
