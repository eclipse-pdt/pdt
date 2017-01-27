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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.osgi.util.NLS;
import org.eclipse.php.composer.api.VersionedPackage;
import org.eclipse.php.composer.api.collection.Dependencies;
import org.eclipse.php.composer.ui.controller.DependencyController;
import org.eclipse.php.composer.ui.dialogs.DependencyDialog;
import org.eclipse.php.composer.ui.editor.ComposerFormPage;
import org.eclipse.php.composer.ui.editor.FormLayoutFactory;
import org.eclipse.php.composer.ui.editor.TableSection;
import org.eclipse.php.composer.ui.job.UpdateDevJob;
import org.eclipse.php.composer.ui.parts.TablePart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

public class DependencySection extends TableSection implements PropertyChangeListener {

	private Dependencies dependencies;
	private TableViewer dependencyViewer;

	private IAction editAction;
	private IAction removeAction;
	private IAction updateAction;

	private UpdateDevJob updateJob;

	private static final int EDIT_INDEX = 0;
	private static final int REMOVE_INDEX = 1;
	private static final int UPDATE_INDEX = 2;

	public DependencySection(ComposerFormPage page, Composite parent, Dependencies dependencies, String title,
			String description, boolean expanded) {
		super(page, parent, Section.EXPANDED | Section.DESCRIPTION | Section.TWISTIE | Section.TITLE_BAR,
				new String[] { Messages.DependencySection_EditButton, Messages.DependencySection_RemoveButton,
						Messages.DependencySection_UpdateButton });

		this.dependencies = dependencies;
		createClient(getSection(), page.getManagedForm().getToolkit(), title, description, expanded);
		updateJob = new UpdateDevJob(page.getComposerEditor().getProject());
		updateJob.setUser(true);
	}

	protected void createClient(final Section section, FormToolkit toolkit, String title, String description,
			boolean expanded) {
		section.setText(title);
		section.setDescription(description);
		section.setExpanded(expanded);
		section.setLayout(FormLayoutFactory.createClearGridLayout(false, 1));
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.grabExcessVerticalSpace = expanded;
		section.setLayoutData(gd);

		Composite container = createClientContainer(section, 2, toolkit);
		createViewerPartControl(container, SWT.MULTI, 2, toolkit);
		TablePart tablePart = getTablePart();
		DependencyController dependencyController = new DependencyController();
		dependencyViewer = tablePart.getTableViewer();
		dependencyViewer.setContentProvider(dependencyController);
		dependencyViewer.setLabelProvider(dependencyController);

		toolkit.paintBordersFor(container);
		section.setClient(container);

		dependencyViewer.setInput(dependencies);
		dependencies.addPropertyChangeListener(this);
		updateButtons();

		makeActions();
		updateMenu();
	}

	public void setExpanded(boolean expanded) {
		getSection().setExpanded(expanded);

		if (expanded) {
			((GridData) getSection().getLayoutData()).widthHint = 0;
		} else {
			((GridData) getSection().getLayoutData()).widthHint = SWT.DEFAULT;
		}
	}

	protected boolean createCount() {
		return true;
	}

	private void updateButtons() {
		ISelection selection = dependencyViewer.getSelection();

		TablePart tablePart = getTablePart();
		tablePart.setButtonEnabled(EDIT_INDEX, !selection.isEmpty() && enabled);
		tablePart.setButtonEnabled(REMOVE_INDEX, !selection.isEmpty() && enabled);
		tablePart.setButtonEnabled(UPDATE_INDEX, !selection.isEmpty() && enabled);
	}

	private void updateMenu() {
		IStructuredSelection selection = (IStructuredSelection) dependencyViewer.getSelection();

		editAction.setEnabled(selection.size() > 0);
		removeAction.setEnabled(selection.size() > 0);
		updateAction.setEnabled(selection.size() > 0);
	}

	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		updateButtons();

		refresh();
		dependencyViewer.getTable().setEnabled(enabled);
	}

	public void refresh() {
		dependencyViewer.refresh();
		super.refresh();
	}

	@Override
	public void propertyChange(PropertyChangeEvent e) {
		refresh();
	}

	protected void selectionChanged(IStructuredSelection sel) {
		updateButtons();
		updateMenu();
	}

	private void makeActions() {
		editAction = new Action(Messages.DependencySection_EditActionTitle) {
			@Override
			public void run() {
				handleEdit();
			}
		};

		removeAction = new Action(Messages.DependencySection_RemoveActionTitle) {
			@Override
			public void run() {
				handleRemove();
			}
		};

		updateAction = new Action(Messages.DependencySection_UpdateActionTitle) {
			@Override
			public void run() {
				handleUpdate();
			}
		};
	}

	@Override
	protected void fillContextMenu(IMenuManager manager) {
		manager.add(editAction);
		manager.add(removeAction);
		manager.add(updateAction);
	}

	private void handleEdit() {
		VersionedPackage dep = (VersionedPackage) ((StructuredSelection) dependencyViewer.getSelection())
				.getFirstElement();
		DependencyDialog diag = new DependencyDialog(dependencyViewer.getTable().getShell(), dep.clone());
		if (diag.open() == Dialog.OK) {
			dep.setVersion(diag.getDependency().getVersion());
			// refresh();
		}
	}

	@SuppressWarnings("unchecked")
	private void handleRemove() {
		StructuredSelection selection = ((StructuredSelection) dependencyViewer.getSelection());
		Iterator<Object> it = selection.iterator();
		String[] names = new String[selection.size()];
		List<VersionedPackage> deps = new ArrayList<VersionedPackage>();

		for (int i = 0; it.hasNext(); i++) {
			VersionedPackage dep = (VersionedPackage) it.next();
			deps.add(dep);
			names[i] = dep.getName();
		}

		String title = selection.size() > 1 ? Messages.DependencySection_RemoveDialogTitlePlural
				: Messages.DependencySection_RemoveDialogTitle;
		MessageDialog diag = new MessageDialog(dependencyViewer.getTable().getShell(), title, null,
				NLS.bind(Messages.DependencySection_RemoveDialogMessage, StringUtils.join(names, ", ")), //$NON-NLS-1$
				MessageDialog.WARNING,
				new String[] { Messages.DependencySection_YesButton, Messages.DependencySection_NoButton }, 0);

		if (diag.open() == Dialog.OK) {
			for (VersionedPackage dep : deps) {
				dependencies.remove(dep);
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void handleUpdate() {
		StructuredSelection selection = ((StructuredSelection) dependencyViewer.getSelection());
		Iterator<Object> it = selection.iterator();
		String[] names = new String[selection.size()];
		List<VersionedPackage> deps = new ArrayList<VersionedPackage>();

		for (int i = 0; it.hasNext(); i++) {
			VersionedPackage dep = (VersionedPackage) it.next();
			deps.add(dep);
			names[i] = dep.getName();
		}

		updateJob.setPackages(names);
		updateJob.schedule();
	}

	@Override
	protected void buttonSelected(int index) {
		switch (index) {

		case EDIT_INDEX:
			handleEdit();
			break;

		case REMOVE_INDEX:
			handleRemove();
			break;

		case UPDATE_INDEX:
			handleUpdate();
			break;
		}
	}

	protected void createClient(Section section, FormToolkit toolkit) {
	}
}
