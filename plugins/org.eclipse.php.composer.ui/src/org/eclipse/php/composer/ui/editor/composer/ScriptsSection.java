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

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.osgi.util.NLS;
import org.eclipse.php.composer.api.objects.Script;
import org.eclipse.php.composer.api.objects.Script.HandlerValue;
import org.eclipse.php.composer.ui.controller.ScriptsController;
import org.eclipse.php.composer.ui.dialogs.ScriptDialog;
import org.eclipse.php.composer.ui.editor.ComposerFormPage;
import org.eclipse.php.composer.ui.editor.FormLayoutFactory;
import org.eclipse.php.composer.ui.editor.TreeSection;
import org.eclipse.php.composer.ui.parts.TreePart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

public class ScriptsSection extends TreeSection implements PropertyChangeListener {

	private TreeViewer scriptsViewer;

	private IAction addAction;
	private IAction editAction;
	private IAction removeAction;

	private static final int ADD_INDEX = 0;
	private static final int EDIT_INDEX = 1;
	private static final int REMOVE_INDEX = 2;

	public ScriptsSection(ComposerFormPage page, Composite parent) {
		super(page, parent, Section.DESCRIPTION, new String[] { Messages.ScriptsSection_AddButton,
				Messages.ScriptsSection_EditButton, Messages.ScriptsSection_RemoveButton });
		createClient(getSection(), page.getManagedForm().getToolkit());
	}

	@Override
	protected void createClient(Section section, FormToolkit toolkit) {
		section.setText(Messages.ScriptsSection_Title);
		section.setDescription(Messages.ScriptsSection_Description);
		section.setLayout(FormLayoutFactory.createClearGridLayout(false, 1));
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.grabExcessVerticalSpace = true;
		section.setLayoutData(gd);

		Composite container = createClientContainer(section, 2, toolkit);
		createViewerPartControl(container, SWT.SINGLE, 2, toolkit);
		TreePart treePart = getTreePart();
		ScriptsController scriptsController = new ScriptsController(treePart.getTreeViewer());
		scriptsViewer = treePart.getTreeViewer();
		// optimize object manipulations by avoiding unnecessary object cloning:
		scriptsViewer.setUseHashlookup(true);
		scriptsViewer.setContentProvider(scriptsController);
		scriptsViewer.setLabelProvider(scriptsController);

		toolkit.paintBordersFor(container);
		section.setClient(container);
		section.setLayout(FormLayoutFactory.createClearGridLayout(false, 1));

		scriptsViewer.setInput(composerPackage.getScripts());
		composerPackage.addPropertyChangeListener(this);
		updateButtons();

		makeActions();
		updateMenu();
	}

	protected boolean createCount() {
		return true;
	}

	private void updateButtons() {
		ISelection selection = scriptsViewer.getSelection();

		TreePart treePart = getTreePart();
		treePart.setButtonEnabled(ADD_INDEX, enabled);
		treePart.setButtonEnabled(EDIT_INDEX, !selection.isEmpty()
				&& !(((StructuredSelection) selection).getFirstElement() instanceof Script) && enabled);
		treePart.setButtonEnabled(REMOVE_INDEX, !selection.isEmpty() && enabled);
	}

	private void updateMenu() {
		IStructuredSelection selection = (IStructuredSelection) scriptsViewer.getSelection();

		editAction.setEnabled(selection.size() > 0);
		removeAction.setEnabled(selection.size() > 0);
	}

	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		updateButtons();

		refresh();
		scriptsViewer.getTree().setEnabled(enabled);
	}

	public void refresh() {
		scriptsViewer.refresh();
		super.refresh();
	}

	@Override
	public void propertyChange(PropertyChangeEvent e) {
		if (e.getPropertyName().startsWith("scripts")) { //$NON-NLS-1$
			refresh();
		}
	}

	protected void selectionChanged(IStructuredSelection sel) {
		updateButtons();
		updateMenu();
	}

	private void makeActions() {
		addAction = new Action(Messages.ScriptsSection_AddActionTitle) {
			@Override
			public void run() {
				handleAdd();
			}
		};

		editAction = new Action(Messages.ScriptsSection_EditActionTitle) {
			@Override
			public void run() {
				handleEdit();
			}
		};

		removeAction = new Action(Messages.ScriptsSection_RemoveActionTitle) {
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
		Script script = null;
		Object element = ((StructuredSelection) scriptsViewer.getSelection()).getFirstElement();

		// get parent if element is HandlerValue
		if (element instanceof HandlerValue) {
			element = ((ScriptsController) scriptsViewer.getContentProvider()).getParent(element);
		}

		if (element instanceof Script) {
			script = (Script) element;
		}

		Script dialogScript = new Script(script != null ? script.getScript() : null, new HandlerValue("")); //$NON-NLS-1$

		ScriptDialog dialog = new ScriptDialog(scriptsViewer.getTree().getShell(), dialogScript,
				dialogScript.getFirst());

		if (dialog.open() == Dialog.OK && dialog.getScript().getScript() != null) {
			assert dialogScript == dialog.getScript();
			composerPackage.getScripts().add(dialog.getScript());
		}
	}

	private void handleEdit() {

		Script script = null;
		int index = -1;
		Object element = ((StructuredSelection) scriptsViewer.getSelection()).getFirstElement();

		// get parent if element is HandlerValue
		if (element instanceof HandlerValue) {
			element = ((ScriptsController) scriptsViewer.getContentProvider()).getParent(element);
			// find selection index
			TreeItem selectedItem = scriptsViewer.getTree().getSelection()[0];
			TreeItem[] items = selectedItem.getParentItem().getItems();
			for (int i = 0; i < items.length; i++) {
				if (items[i] == selectedItem) {
					index = i;
					break;
				}
			}
		}

		if (element instanceof Script) {
			script = (Script) element;
		}

		if (script != null && index != -1) {
			assert index < script.size();

			Script dialogScript = script.clone();

			ScriptDialog dialog = new ScriptDialog(scriptsViewer.getTree().getShell(), dialogScript,
					dialogScript.get(index));
			dialog.setEventEnabled(false);

			if (dialog.open() == Dialog.OK) {
				assert dialogScript == dialog.getScript();

				Script cpscript = composerPackage.getScripts().get(script.getScript());
				if (cpscript.equals(dialog.getScript())) {
					// nothing changed
					return;
				}

				assert cpscript.getScript() != null && cpscript.getScript().equals(dialog.getScript().getScript());

				cpscript.clear();
				cpscript.addHandlers(dialog.getScript());
			}
		}
	}

	private void handleRemove() {
		Object element = ((StructuredSelection) scriptsViewer.getSelection()).getFirstElement();
		ScriptsController controller = (ScriptsController) scriptsViewer.getLabelProvider();

		if (element instanceof Script) {
			String text = controller.getText(element);
			MessageDialog diag = new MessageDialog(scriptsViewer.getTree().getShell(),
					Messages.ScriptsSection_RemoveEventDialogTitle, null,
					NLS.bind(Messages.ScriptsSection_RemoveEventDialogMessage, text), MessageDialog.WARNING,
					new String[] { Messages.ScriptsSection_YesButton, Messages.ScriptsSection_NoButton }, 0);

			if (diag.open() == Dialog.OK) {
				composerPackage.getScripts().remove((Script) element);
			}
		} else if (element instanceof HandlerValue) {
			String text = controller.getText(element);
			Script script = (Script) ((ScriptsController) scriptsViewer.getContentProvider()).getParent(element);
			if (script != null) {
				int index = -1;
				// find selection index
				TreeItem selectedItem = scriptsViewer.getTree().getSelection()[0];
				TreeItem[] items = selectedItem.getParentItem().getItems();
				for (int i = 0; i < items.length; i++) {
					if (items[i] == selectedItem) {
						index = i;
						break;
					}
				}

				assert index != -1 && index < script.size();

				String event = controller.getText(script);

				MessageDialog diag = new MessageDialog(scriptsViewer.getTree().getShell(),
						Messages.ScriptsSection_RemoveHandlerDialogTitle, null,
						NLS.bind(Messages.ScriptsSection_RemoveHandlerDialogMessage, text, event),
						MessageDialog.WARNING,
						new String[] { Messages.ScriptsSection_YesButton, Messages.ScriptsSection_NoButton }, 0);

				if (diag.open() == Dialog.OK) {
					script.remove(index);
				}
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
