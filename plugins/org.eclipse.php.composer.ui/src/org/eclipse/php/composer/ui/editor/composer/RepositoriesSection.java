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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.*;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.*;
import org.eclipse.osgi.util.NLS;
import org.eclipse.php.composer.api.collection.Repositories;
import org.eclipse.php.composer.api.repositories.*;
import org.eclipse.php.composer.ui.ComposerUIPluginImages;
import org.eclipse.php.composer.ui.controller.IController;
import org.eclipse.php.composer.ui.dialogs.RepositoryDialog;
import org.eclipse.php.composer.ui.editor.ComposerFormPage;
import org.eclipse.php.composer.ui.editor.FormLayoutFactory;
import org.eclipse.php.composer.ui.editor.TableSection;
import org.eclipse.php.composer.ui.parts.TablePart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

public class RepositoriesSection extends TableSection implements PropertyChangeListener {

	private TableViewer repositoryViewer;

	private IAction addAction;
	private IAction editAction;
	private IAction removeAction;

	private static final int ADD_INDEX = 0;
	private static final int EDIT_INDEX = 1;
	private static final int REMOVE_INDEX = 2;

	class RepositoriesController extends LabelProvider implements IController {

		private Repositories repositories;
		private Map<String, Image> images = new HashMap<String, Image>();
		private Map<String, ImageDescriptor> descriptors = new HashMap<String, ImageDescriptor>() {
			private static final long serialVersionUID = -2019489473873127982L;

			{
				put("generic", ComposerUIPluginImages.REPO_GENERIC); //$NON-NLS-1$
				put("git", ComposerUIPluginImages.REPO_GIT); //$NON-NLS-1$
				put("svn", ComposerUIPluginImages.REPO_SVN); //$NON-NLS-1$
				put("mercurial", ComposerUIPluginImages.REPO_MERCURIAL); //$NON-NLS-1$
				put("pear", ComposerUIPluginImages.REPO_PEAR); //$NON-NLS-1$
				put("composer", ComposerUIPluginImages.REPO_COMPOSER); //$NON-NLS-1$
				put("package", ComposerUIPluginImages.REPO_PACKAGE); //$NON-NLS-1$
			}
		};

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			repositories = (Repositories) newInput;
		}

		public Object[] getElements(Object inputElement) {
			return repositories.toArray();
		}

		@Override
		public String getText(Object element) {
			if (element instanceof Repository) {
				Repository repo = (Repository) element;
				String name;

				// name
				if (repo.has("name")) { //$NON-NLS-1$
					name = repo.getAsString("name"); //$NON-NLS-1$
				} else {
					name = repo.getUrl();
				}

				return name;
			}

			return super.getText(element);
		}

		private Image createImage(String type) {
			if (descriptors.containsKey(type)) {
				return descriptors.get(type).createImage();
			}

			return null;
		}

		private Image getRepoImage(String type) {
			if (!images.containsKey(type)) {
				images.put(type, createImage(type));
			}
			return images.get(type);
		}

		@Override
		public Image getImage(Object element) {
			if (element instanceof GitRepository) {
				return getRepoImage("git"); //$NON-NLS-1$
			} else if (element instanceof SubversionRepository) {
				return getRepoImage("svn"); //$NON-NLS-1$
			} else if (element instanceof MercurialRepository) {
				return getRepoImage("mercurial"); //$NON-NLS-1$
			} else if (element instanceof PearRepository) {
				return getRepoImage("pear"); //$NON-NLS-1$
			} else if (element instanceof PackageRepository) {
				return getRepoImage("package"); //$NON-NLS-1$
			} else if (element instanceof ComposerRepository) {
				return getRepoImage("composer"); //$NON-NLS-1$
			} else if (element instanceof Repository) {
				return getRepoImage("generic"); //$NON-NLS-1$
			}

			return null;
		}
	}

	public RepositoriesSection(ComposerFormPage page, Composite parent) {
		super(page, parent, Section.DESCRIPTION, new String[] { Messages.RepositoriesSection_AddButton,
				Messages.RepositoriesSection_EditButton, Messages.RepositoriesSection_RemoveButton });
		createClient(getSection(), page.getManagedForm().getToolkit());
	}

	@Override
	protected void createClient(Section section, FormToolkit toolkit) {
		section.setText(Messages.RepositoriesSection_Title);
		section.setDescription(Messages.RepositoriesSection_Description);
		section.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		Composite container = createClientContainer(section, 2, toolkit);
		createViewerPartControl(container, SWT.MULTI, 2, toolkit);
		TablePart tablePart = getTablePart();
		RepositoriesController repositoriesController = new RepositoriesController();
		repositoryViewer = tablePart.getTableViewer();
		repositoryViewer.setContentProvider(repositoriesController);
		repositoryViewer.setLabelProvider(repositoriesController);

		toolkit.paintBordersFor(container);
		section.setClient(container);
		section.setLayout(FormLayoutFactory.createClearGridLayout(false, 1));

		repositoryViewer.setInput(composerPackage.getRepositories());
		composerPackage.addPropertyChangeListener(this);
		updateButtons();

		makeActions();
		updateMenu();
	}

	protected boolean createCount() {
		return true;
	}

	private void updateButtons() {
		ISelection selection = repositoryViewer.getSelection();

		TablePart tablePart = getTablePart();
		tablePart.setButtonEnabled(ADD_INDEX, enabled);
		tablePart.setButtonEnabled(EDIT_INDEX, !selection.isEmpty() && enabled);
		tablePart.setButtonEnabled(REMOVE_INDEX, !selection.isEmpty() && enabled);
	}

	private void updateMenu() {
		IStructuredSelection selection = (IStructuredSelection) repositoryViewer.getSelection();

		editAction.setEnabled(selection.size() > 0);
		removeAction.setEnabled(selection.size() > 0);
	}

	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		updateButtons();

		refresh();
		repositoryViewer.getTable().setEnabled(enabled);
	}

	public void refresh() {
		repositoryViewer.refresh();
		super.refresh();
	}

	@Override
	public void propertyChange(PropertyChangeEvent e) {
		if (e.getPropertyName().startsWith("repositories")) { //$NON-NLS-1$
			refresh();
		}
	}

	protected void selectionChanged(IStructuredSelection sel) {
		updateButtons();
		updateMenu();
	}

	private void makeActions() {
		addAction = new Action(Messages.RepositoriesSection_AddActionTitle) {
			public void run() {
				handleAdd();
			}
		};

		editAction = new Action(Messages.RepositoriesSection_EditActionTitle) {
			public void run() {
				handleEdit();
			}
		};

		removeAction = new Action(Messages.RepositoriesSection_RemoveActionTitle) {
			public void run() {
				handleRemove();
			}
		};
	}

	@Override
	protected void fillContextMenu(IMenuManager manager) {
		manager.add(addAction);
		manager.add(editAction);
		manager.add(removeAction);
	}

	private void handleAdd() {
		RepositoryDialog diag = new RepositoryDialog(repositoryViewer.getTable().getShell());
		if (diag.open() == Dialog.OK && diag.getRepository() != null) {
			composerPackage.getRepositories().add(diag.getRepository());
		}
	}

	private void handleEdit() {
		Repository repo = (Repository) ((StructuredSelection) repositoryViewer.getSelection()).getFirstElement();
		RepositoryDialog diag = new RepositoryDialog(repositoryViewer.getTable().getShell(), repo.clone());
		if (diag.open() == Dialog.OK) {
			Repository newRepo = diag.getRepository();
			repo.setName(newRepo.getName());
			repo.setUrl(newRepo.getUrl());
		}
	}

	@SuppressWarnings("unchecked")
	private void handleRemove() {
		StructuredSelection selection = ((StructuredSelection) repositoryViewer.getSelection());
		Iterator<Object> it = selection.iterator();
		String[] names = new String[selection.size()];
		List<Repository> repos = new ArrayList<Repository>();

		for (int i = 0; it.hasNext(); i++) {
			Repository repo = (Repository) it.next();
			repos.add(repo);
			names[i] = repo.getName();
		}

		String title = selection.size() > 1 ? Messages.RepositoriesSection_RemoveDialogTitlePlural
				: Messages.RepositoriesSection_RemoveDialogTitle;
		MessageDialog diag = new MessageDialog(repositoryViewer.getTable().getShell(), title, null,
				NLS.bind(Messages.RepositoriesSection_RemoveDialogMessage, StringUtils.join(names, ", ")), //$NON-NLS-1$
				MessageDialog.WARNING,
				new String[] { Messages.RepositoriesSection_YesButton, Messages.RepositoriesSection_NoButton }, 0);

		if (diag.open() == Dialog.OK) {
			for (Repository repo : repos) {
				composerPackage.getRepositories().remove(repo);
			}
		}
	}

	@Override
	protected void buttonSelected(int index) {
		switch (index) {
		case ADD_INDEX:
			handleAdd();
			break;

		case EDIT_INDEX:
			handleEdit();
			break;

		case REMOVE_INDEX:
			handleRemove();
			break;
		}
	}
}
