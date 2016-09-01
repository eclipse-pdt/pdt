/*******************************************************************************
 * Copyright (c) 2012, 2016 PDT Extension Group and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.ui.editor.composer;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Arrays;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.php.composer.ui.controller.ScriptsController;
import org.eclipse.php.composer.ui.dialogs.ScriptDialog;
import org.eclipse.php.composer.ui.editor.ComposerFormPage;
import org.eclipse.php.composer.ui.editor.FormLayoutFactory;
import org.eclipse.php.composer.ui.editor.TreeSection;
import org.eclipse.php.composer.ui.parts.TreePart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

import org.eclipse.php.composer.api.collection.JsonArray;
import org.eclipse.php.composer.api.objects.Scripts;

public class ScriptsSection extends TreeSection implements PropertyChangeListener {

	private TreeViewer scriptsViewer;
	
	private IAction addAction;
	private IAction editAction;
	private IAction removeAction;
	
	private static final int ADD_INDEX = 0;
	private static final int EDIT_INDEX = 1;
	private static final int REMOVE_INDEX = 2;
	
	public ScriptsSection(ComposerFormPage page, Composite parent) {
		super(page, parent, Section.DESCRIPTION, new String[]{"Add...", "Edit...", "Remove"});
		createClient(getSection(), page.getManagedForm().getToolkit());
	}

	@Override
	protected void createClient(Section section, FormToolkit toolkit) {
		section.setText("Scripts");
		section.setDescription("Manage the scripts for your package.");
		section.setLayout(FormLayoutFactory.createClearGridLayout(false, 1));
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.grabExcessVerticalSpace = true;
		section.setLayoutData(gd);

		Composite container = createClientContainer(section, 2, toolkit);
		createViewerPartControl(container, SWT.SINGLE, 2, toolkit);
		TreePart treePart = getTreePart();
		ScriptsController scriptsController = new ScriptsController();
		scriptsViewer = treePart.getTreeViewer();
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
		treePart.setButtonEnabled(EDIT_INDEX, !selection.isEmpty() && enabled);
		treePart.setButtonEnabled(REMOVE_INDEX, !selection.isEmpty() && enabled);
	}
	
	private void updateMenu() {
		IStructuredSelection selection = (IStructuredSelection)scriptsViewer.getSelection();
		
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
		if (e.getPropertyName().startsWith("scripts")) { 
			refresh();
		}
	}
	
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
	
	@Override
	protected void fillContextMenu(IMenuManager manager) {
		manager.add(addAction);
		manager.add(editAction);
		manager.add(removeAction);
	}
	
	private void handleAdd() {
		ScriptDialog diag = new ScriptDialog(scriptsViewer.getTree().getShell());
		
		if (!scriptsViewer.getSelection().isEmpty()) {
			Object element = ((StructuredSelection)scriptsViewer.getSelection()).getFirstElement();
			ScriptsController controller = (ScriptsController)scriptsViewer.getLabelProvider();
			String text = controller.getText(element);
			if (Arrays.asList(Scripts.getEvents()).contains(text)) {
				diag.setEvent(text);
			}
		}
		
		if (diag.open() == Dialog.OK) {
			composerPackage.getScripts().getAsArray(diag.getEvent()).add(diag.getHandler());
			refresh();
		}
	}
	
	private void handleEdit() {
		Object element = ((StructuredSelection)scriptsViewer.getSelection()).getFirstElement();
		ScriptsController controller = (ScriptsController)scriptsViewer.getLabelProvider();
		String text = controller.getText(element);
		ScriptDialog diag = new ScriptDialog(scriptsViewer.getTree().getShell());
		
		// edit event
		if (Arrays.asList(Scripts.getEvents()).contains(text)) {
			diag.setEvent(text);
			diag.setHandlerEnabled(false);
			if (diag.open() == Dialog.OK) {
				String event = diag.getEvent();
				if (!event.equalsIgnoreCase(text)) {
					composerPackage.getScripts().set(event, 
							composerPackage.getScripts().getAsArray(text));
					composerPackage.getScripts().remove(text);
				}
			}
		} 
		
		// edit handler
		else {
			String event = controller.getText(controller.getParent(element));
			diag.setEvent(event);
			diag.setHandler(text);
			diag.setEventEnabled(false);
			if (diag.open() == Dialog.OK) {
				String handler = diag.getHandler();
				if (!handler.equalsIgnoreCase(text)) {
					JsonArray events = composerPackage.getScripts().getAsArray(event);
					events.replace(text, handler);
				}
			}
		}
	}
	
	private void handleRemove() {
		Object element = ((StructuredSelection)scriptsViewer.getSelection()).getFirstElement();
		ScriptsController controller = (ScriptsController)scriptsViewer.getLabelProvider();
		String text = controller.getText(element);
		
		// remove event
		if (Arrays.asList(Scripts.getEvents()).contains(text)) {
			MessageDialog diag = new MessageDialog(
				scriptsViewer.getTree().getShell(), 
				"Remove Event", 
				null, 
				"Do you really wan't to remove " + text + "?", 
				MessageDialog.WARNING,
				new String[] {"Yes", "No"},
				0);

			if (diag.open() == Dialog.OK) {
				composerPackage.getScripts().remove(text);
			}
		} 
		
		// remove handler
		else {
			String event = controller.getText(controller.getParent(element));
			
			MessageDialog diag = new MessageDialog(
					scriptsViewer.getTree().getShell(), 
					"Remove Event", 
					null, 
					"Do you really wan't to remove " + text + " in " + event + "?", 
					MessageDialog.WARNING,
					new String[] {"Yes", "No"},
					0);
			
			if (diag.open() == Dialog.OK) {
				JsonArray events = composerPackage.getScripts().getAsArray(event);
				events.remove(text);
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
