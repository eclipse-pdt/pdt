/*******************************************************************************
 * Copyright (c) 2012, 2016, 2017 PDT Extension Group and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *     Kaloyan Raev - [501269] externalize strings
 *******************************************************************************/
package org.eclipse.php.composer.ui.editor.composer;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.*;
import org.eclipse.php.composer.api.collection.JsonArray;
import org.eclipse.php.composer.api.objects.Autoload;
import org.eclipse.php.composer.api.objects.Namespace;
import org.eclipse.php.composer.ui.controller.FileController;
import org.eclipse.php.composer.ui.dialogs.Messages;
import org.eclipse.php.composer.ui.dialogs.ResourceDialog;
import org.eclipse.php.composer.ui.editor.FormLayoutFactory;
import org.eclipse.php.composer.ui.editor.TableSection;
import org.eclipse.php.composer.ui.parts.TablePart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.dnd.TransferData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.dialogs.CheckedTreeSelectionDialog;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.part.ResourceTransfer;

public abstract class AutoloadArraySection extends TableSection implements PropertyChangeListener {

	protected TableViewer listViewer;

	private IAction addAction;
	private IAction editAction;
	private IAction removeAction;

	private static final int ADD_INDEX = 0;
	private static final int EDIT_INDEX = 1;
	private static final int REMOVE_INDEX = 2;

	protected JsonArray list = null;

	public AutoloadArraySection(AbstractAutoloadPage page, Composite parent) {
		super(page, parent, Section.DESCRIPTION, new String[] { "Add...", "Edit...", "Remove" });

		list = getList();
		createClient(getSection(), page.getManagedForm().getToolkit());
	}

	abstract protected JsonArray getList();

	abstract String getName();

	abstract String getDescription();

	@Override
	protected void createClient(Section section, FormToolkit toolkit) {
		section.setText(getName());
		section.setDescription(getDescription());
		section.setLayout(FormLayoutFactory.createClearGridLayout(false, 1));
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.grabExcessVerticalSpace = true;
		section.setLayoutData(gd);

		Composite container = createClientContainer(section, 2, toolkit);
		createViewerPartControl(container, SWT.SINGLE, 2, toolkit);
		TablePart tablePart = getTablePart();
		FileController controller = new FileController();
		listViewer = tablePart.getTableViewer();
		listViewer.setContentProvider(controller);
		listViewer.setLabelProvider(controller);

		Transfer[] transferTypes = new Transfer[] { ResourceTransfer.getInstance() };
		int types = DND.DROP_MOVE | DND.DROP_COPY | DND.DROP_LINK | DND.DROP_DEFAULT;
		listViewer.addDropSupport(types, transferTypes, new PathDropAdapter(listViewer));

		toolkit.paintBordersFor(container);
		section.setClient(container);
		section.setLayout(FormLayoutFactory.createClearGridLayout(false, 1));

		listViewer.setInput(list);
		composerPackage.getAutoload().addPropertyChangeListener(this);

		updateButtons();
		makeActions();
		updateMenu();
	}

	private void updateButtons() {
		ISelection selection = listViewer.getSelection();

		TablePart tablePart = getTablePart();
		tablePart.setButtonEnabled(ADD_INDEX, enabled);
		tablePart.setButtonEnabled(EDIT_INDEX, !selection.isEmpty() && enabled);
		tablePart.setButtonEnabled(REMOVE_INDEX, !selection.isEmpty() && enabled);
	}

	private void updateMenu() {
		IStructuredSelection selection = (IStructuredSelection) listViewer.getSelection();
		editAction.setEnabled(selection.size() > 0);
		removeAction.setEnabled(selection.size() > 0);
	}

	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		updateButtons();

		refresh();
		listViewer.getTable().setEnabled(enabled);
	}

	@Override
	public void refresh() {
		listViewer.refresh();
	}

	@Override
	public void propertyChange(PropertyChangeEvent e) {
		refresh();
	}

	@Override
	protected void selectionChanged(IStructuredSelection sel) {
		updateButtons();
		updateMenu();
	}

	private void makeActions() {
		addAction = new Action("Add...") {
			@Override
			public void run() {
				handleAdd();
			}
		};

		editAction = new Action("Edit...") {
			@Override
			public void run() {
				handleEdit();
			}
		};

		removeAction = new Action("Remove") {
			@Override
			public void run() {
				handleRemove();
			}
		};
	}

	protected Class[] getFilter() {
		return new Class[] { IFolder.class, IFile.class };
	}

	@Override
	protected void fillContextMenu(IMenuManager manager) {
		manager.add(addAction);
		manager.add(editAction);
		manager.add(removeAction);
	}

	private void handleAdd() {
		List<IResource> folders = new ArrayList<>();
		IProject project = getPage().getComposerEditor().getProject();
		for (Object path : list) {
			IResource resource = project.findMember((String) path);
			if (resource != null && (resource instanceof IFolder || resource instanceof IFile)) {
				folders.add(resource);
			}
		}
		CheckedTreeSelectionDialog dialog = ResourceDialog.createMulti(
				getTablePart().getTableViewer().getTable().getShell(), "Select resource",
				Messages.PsrDialog_SelectionDialogMessage, new Class[] { IFolder.class, IFile.class }, project,
				folders);
		if (dialog.open() == Dialog.OK) {

		}
		/*
		 * PsrDialog dialog = new PsrDialog(listViewer.getTree().getShell(), new
		 * Namespace(), getPage().getComposerEditor().getProject());
		 * 
		 * if (dialog.open() == Dialog.OK) { psr.add(dialog.getNamespace()); }
		 */
	}

	private void handleEdit() {

		/*
		 * Namespace namespace = null; Object element = ((StructuredSelection)
		 * listViewer.getSelection()).getFirstElement();
		 * 
		 * // get parent if element is string if (element instanceof String) {
		 * element = ((PsrController)
		 * listViewer.getContentProvider()).getParent(element); }
		 * 
		 * if (element instanceof Namespace) { namespace = (Namespace) element;
		 * }
		 * 
		 * if (namespace != null) { PsrDialog diag = new
		 * PsrDialog(listViewer.getTree().getShell(), namespace.clone(),
		 * getPage().getComposerEditor().getProject());
		 * 
		 * if (diag.open() == Dialog.OK) { Namespace nmspc =
		 * psr.get(namespace.getNamespace()); if
		 * (nmspc.equals(diag.getNamespace())) { // nothing changed return; } if
		 * (!nmspc.getNamespace().equals(diag.getNamespace().getNamespace())) {
		 * // https://bugs.eclipse.org/bugs/show_bug.cgi?id=507543 // We cannot
		 * simply do //
		 * "nmspc.setNamespace(diag.getNamespace().getNamespace());" // since
		 * namespace name nmspc.getNamespace() is used as key // in map
		 * psr.properties // We do remove&add to properly update psr.properties
		 * keys psr.remove(nmspc); psr.add(diag.getNamespace()); } else {
		 * nmspc.clear(); nmspc.addPaths(diag.getNamespace().getPaths()); } } }
		 */
	}

	private void handleRemove() {
		/*
		 * Object element = ((StructuredSelection)
		 * listViewer.getSelection()).getFirstElement();
		 * 
		 * if (element instanceof Namespace) { psr.remove((Namespace) element);
		 * } else if (element instanceof String) { Namespace namespace =
		 * (Namespace) ((PsrController)
		 * listViewer.getContentProvider()).getParent(element); if (namespace !=
		 * null) { namespace.remove((String) element); } }
		 */
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

	private class PathDropAdapter extends ViewerDropAdapter {

		private Namespace target;

		public PathDropAdapter(Viewer viewer) {
			super(viewer);
		}

		@Override
		public boolean performDrop(Object data) {
			if (data instanceof IResource[]) {
				IResource[] resources = (IResource[]) data;

				List<IFolder> folders = new ArrayList<>();

				for (IResource resource : resources) {
					if (resource instanceof IFolder) {
						folders.add((IFolder) resource);
					}
				}

				for (IFolder folder : folders) {
					target.add(folder.getProjectRelativePath().toString());
				}
				return false;
			}

			return false;
		}

		@Override
		public boolean validateDrop(Object target, int operation, TransferData transferType) {
			return false;
		}
	}

	protected Autoload getAutoload() {
		return ((AbstractAutoloadPage) getPage()).getAutoload();
	}
}
