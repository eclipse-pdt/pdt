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
import java.util.List;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.*;
import org.eclipse.osgi.util.NLS;
import org.eclipse.php.composer.api.collection.Psr;
import org.eclipse.php.composer.api.objects.Namespace;
import org.eclipse.php.composer.ui.controller.PsrController;
import org.eclipse.php.composer.ui.dialogs.PsrDialog;
import org.eclipse.php.composer.ui.editor.ComposerFormPage;
import org.eclipse.php.composer.ui.editor.FormLayoutFactory;
import org.eclipse.php.composer.ui.editor.TreeSection;
import org.eclipse.php.composer.ui.parts.TreePart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.dnd.TransferData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.part.ResourceTransfer;

public abstract class PsrSection extends TreeSection implements PropertyChangeListener {

	protected TreeViewer psrViewer;

	private IAction addAction;
	private IAction editAction;
	private IAction removeAction;

	private static final int ADD_INDEX = 0;
	private static final int EDIT_INDEX = 1;
	private static final int REMOVE_INDEX = 2;

	protected Psr psr = null;

	public PsrSection(ComposerFormPage page, Composite parent) {
		super(page, parent, Section.DESCRIPTION, new String[] { Messages.PsrSection_AddButton,
				Messages.PsrSection_EditButton, Messages.PsrSection_RemoveButton });

		psr = getPsr();
		createClient(getSection(), page.getManagedForm().getToolkit());
	}

	abstract protected Psr getPsr();

	abstract protected String getPsrName();

	@Override
	protected void createClient(Section section, FormToolkit toolkit) {
		String name = getPsrName();
		section.setText(name);
		section.setDescription(NLS.bind(Messages.PsrSection_Description, name));
		section.setLayout(FormLayoutFactory.createClearGridLayout(false, 1));
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.grabExcessVerticalSpace = true;
		section.setLayoutData(gd);

		Composite container = createClientContainer(section, 2, toolkit);
		createViewerPartControl(container, SWT.SINGLE, 2, toolkit);
		TreePart treePart = getTreePart();
		PsrController controller = new PsrController(treePart.getTreeViewer());
		psrViewer = treePart.getTreeViewer();
		psrViewer.setContentProvider(controller);
		psrViewer.setLabelProvider(controller);

		Transfer[] transferTypes = new Transfer[] { ResourceTransfer.getInstance() };
		int types = DND.DROP_MOVE | DND.DROP_COPY | DND.DROP_LINK | DND.DROP_DEFAULT;
		psrViewer.addDropSupport(types, transferTypes, new PathDropAdapter(psrViewer));

		toolkit.paintBordersFor(container);
		section.setClient(container);
		section.setLayout(FormLayoutFactory.createClearGridLayout(false, 1));

		psrViewer.setInput(psr);
		composerPackage.getAutoload().addPropertyChangeListener(this);

		updateButtons();
		makeActions();
		updateMenu();
	}

	private void updateButtons() {
		ISelection selection = psrViewer.getSelection();

		TreePart treePart = getTreePart();
		treePart.setButtonEnabled(ADD_INDEX, enabled);
		treePart.setButtonEnabled(EDIT_INDEX, !selection.isEmpty() && enabled);
		treePart.setButtonEnabled(REMOVE_INDEX, !selection.isEmpty() && enabled);
	}

	private void updateMenu() {
		IStructuredSelection selection = (IStructuredSelection) psrViewer.getSelection();
		editAction.setEnabled(selection.size() > 0);
		removeAction.setEnabled(selection.size() > 0);
	}

	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		updateButtons();

		refresh();
		psrViewer.getTree().setEnabled(enabled);
	}

	public void refresh() {
		psrViewer.refresh();
	}

	@Override
	public void propertyChange(PropertyChangeEvent e) {
		if (e.getPropertyName().startsWith(getPsrName())) {
			refresh();
		}
	}

	protected void selectionChanged(IStructuredSelection sel) {
		updateButtons();
		updateMenu();
	}

	private void makeActions() {
		addAction = new Action(Messages.PsrSection_AddActionTitle) {
			@Override
			public void run() {
				handleAdd();
			}
		};

		editAction = new Action(Messages.PsrSection_EditActionTitle) {
			@Override
			public void run() {
				handleEdit();
			}
		};

		removeAction = new Action(Messages.PsrSection_RemoveActionTitle) {
			@Override
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
		PsrDialog dialog = new PsrDialog(psrViewer.getTree().getShell(), new Namespace(),
				getPage().getComposerEditor().getProject());

		if (dialog.open() == Dialog.OK) {
			psr.add(dialog.getNamespace());
		}
	}

	private void handleEdit() {

		Namespace namespace = null;
		Object element = ((StructuredSelection) psrViewer.getSelection()).getFirstElement();

		// get parent if element is string
		if (element instanceof String) {
			element = ((PsrController) psrViewer.getContentProvider()).getParent(element);
		}

		if (element instanceof Namespace) {
			namespace = (Namespace) element;
		}

		if (namespace != null) {
			PsrDialog diag = new PsrDialog(psrViewer.getTree().getShell(), namespace.clone(),
					getPage().getComposerEditor().getProject());

			if (diag.open() == Dialog.OK) {
				Namespace nmspc = psr.get(namespace.getNamespace());
				if (nmspc.equals(diag.getNamespace())) {
					// nothing changed
					return;
				}
				if (!nmspc.getNamespace().equals(diag.getNamespace().getNamespace())) {
					// https://bugs.eclipse.org/bugs/show_bug.cgi?id=507543
					// We cannot simply do
					// "nmspc.setNamespace(diag.getNamespace().getNamespace());"
					// since namespace name nmspc.getNamespace() is used as key
					// in map psr.properties
					// We do remove&add to properly update psr.properties keys
					psr.remove(nmspc);
					psr.add(diag.getNamespace());
				} else {
					nmspc.clear();
					nmspc.addPaths(diag.getNamespace().getPaths());
				}
			}
		}
	}

	private void handleRemove() {
		Object element = ((StructuredSelection) psrViewer.getSelection()).getFirstElement();

		if (element instanceof Namespace) {
			psr.remove((Namespace) element);
		} else if (element instanceof String) {
			Namespace namespace = (Namespace) ((PsrController) psrViewer.getContentProvider()).getParent(element);
			if (namespace != null) {
				namespace.remove((String) element);
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

	private class PathDropAdapter extends ViewerDropAdapter {

		private Namespace target;

		public PathDropAdapter(Viewer viewer) {
			super(viewer);
		}

		@Override
		public boolean performDrop(Object data) {
			if (data instanceof IResource[]) {
				IResource[] resources = (IResource[]) data;

				List<IFolder> folders = new ArrayList<IFolder>();

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

			if (target instanceof Namespace) {
				this.target = (Namespace) target;
				return true;
			}

			return false;
		}
	}
}
