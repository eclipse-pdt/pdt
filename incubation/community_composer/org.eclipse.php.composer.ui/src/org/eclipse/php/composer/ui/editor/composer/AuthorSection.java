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
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.php.composer.ui.ComposerUIPluginImages;
import org.eclipse.php.composer.ui.dialogs.PersonDialog;
import org.eclipse.php.composer.ui.editor.ComposerFormPage;
import org.eclipse.php.composer.ui.editor.FormLayoutFactory;
import org.eclipse.php.composer.ui.editor.TableSection;
import org.eclipse.php.composer.ui.parts.TablePart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;

import org.eclipse.php.composer.api.collection.Persons;
import org.eclipse.php.composer.api.objects.Person;

public class AuthorSection extends TableSection implements PropertyChangeListener {

	private TableViewer authorViewer;
	
	private IAction addAction;
	private IAction editAction;
	private IAction removeAction;
	
	private static final int ADD_INDEX = 0;
	private static final int EDIT_INDEX = 1;
	private static final int REMOVE_INDEX = 2;

	class AuthorController extends StyledCellLabelProvider implements IStructuredContentProvider {

		private Persons authors;
		private Image authorImage = ComposerUIPluginImages.PERSON.createImage();

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			authors = (Persons)newInput;
		}

		public Object[] getElements(Object inputElement) {
			return authors.toArray();
		}
		
		public void update(ViewerCell cell) {
			Object obj = cell.getElement();
			
			if (obj instanceof Person) {
				Person author = (Person)obj;
				
				StyledString styledString = new StyledString(author.getName());
				
				if (author.getEmail() != null && author.getEmail().trim() != "" && !author.getEmail().trim().equals("")) {
					styledString.append(" <" + author.getEmail().trim() + ">", StyledString.COUNTER_STYLER);
				}
				
				if (author.getHomepage() != null && author.getHomepage().trim() != "" && !author.getHomepage().trim().equals("")) {
					styledString.append(" - " + author.getHomepage().trim(), StyledString.DECORATIONS_STYLER);
				}
				
				cell.setText(styledString.toString());
				cell.setStyleRanges(styledString.getStyleRanges());
				
				cell.setImage(authorImage);
				
				super.update(cell);
			}
		}
	}
	
	public AuthorSection(ComposerFormPage page, Composite parent) {
		super(page, parent, Section.DESCRIPTION, new String[]{"Add...", "Edit...", "Remove"});
//		createClient(getSection(), page.getManagedForm().getToolkit());
	}
	

	@Override
	protected void createClient(Section section, FormToolkit toolkit) {
		section.setText("Authors");
		section.setDescription("Honour the glorious authors of this package.");
		section.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB));

		Composite container = createClientContainer(section, 2, toolkit);
		createViewerPartControl(container, SWT.MULTI, 2, toolkit);
		TablePart tablePart = getTablePart();
		AuthorController authorController = new AuthorController();
		authorViewer = tablePart.getTableViewer();
		authorViewer.setContentProvider(authorController);
		authorViewer.setLabelProvider(authorController);
		
		toolkit.paintBordersFor(container);
		section.setClient(container);
		section.setLayout(FormLayoutFactory.createClearGridLayout(false, 1));

		authorViewer.setInput(composerPackage.getAuthors());
		composerPackage.addPropertyChangeListener(this);
		updateButtons();
		
		makeActions();
		updateMenu();
	}
	
	protected boolean createCount() {
		return true;
	}
	
	private void updateButtons() {
		ISelection selection = authorViewer.getSelection();
		
		TablePart tablePart = getTablePart();
		tablePart.setButtonEnabled(ADD_INDEX, enabled);
		tablePart.setButtonEnabled(EDIT_INDEX, !selection.isEmpty() && enabled);
		tablePart.setButtonEnabled(REMOVE_INDEX, !selection.isEmpty() && enabled);
	}
	
	private void updateMenu() {
		IStructuredSelection selection = (IStructuredSelection)authorViewer.getSelection();
		
		editAction.setEnabled(selection.size() > 0);
		removeAction.setEnabled(selection.size() > 0);
	}
	
	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		updateButtons();
		
		refresh();
		authorViewer.getTable().setEnabled(enabled);
	}

	public void refresh() {
		authorViewer.refresh();
		super.refresh();
	}

	@Override
	public void propertyChange(PropertyChangeEvent e) {
		if (e.getPropertyName().startsWith("authors")) {
//			refresh();
		}
	}
	
	protected void selectionChanged(IStructuredSelection sel) {
		updateButtons();
		updateMenu();
	}
	
	private void makeActions() {
		addAction = new Action("Add...") {
			public void run() {
				handleAdd();
			}
		};
		
		editAction = new Action("Edit...") {
			public void run() {
				handleEdit();
			}
		};
		
		removeAction = new Action("Remove") {
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
		PersonDialog diag = new PersonDialog(authorViewer.getTable().getShell(), new Person());
		if (diag.open() == Dialog.OK) {
			composerPackage.getAuthors().add(diag.getPerson());
			refresh();
		}
	}
	
	private void handleEdit() {
		Person author = (Person)((StructuredSelection)authorViewer.getSelection()).getFirstElement();
		PersonDialog diag = new PersonDialog(authorViewer.getTable().getShell(), author.clone());
		if (diag.open() == Dialog.OK) {
			author.setName(diag.getPerson().getName());
			author.setEmail(diag.getPerson().getEmail());
			author.setHomepage(diag.getPerson().getHomepage());
			author.setRole(diag.getPerson().getRole());
		}
	}
	
	@SuppressWarnings("unchecked")
	private void handleRemove() {
		StructuredSelection selection = ((StructuredSelection)authorViewer.getSelection());
		Iterator<Object> it = selection.iterator();
		String[] names = new String[selection.size()];
		List<Person> persons = new ArrayList<Person>();

		for (int i = 0; it.hasNext(); i++) {
			Person person = (Person)it.next();
			persons.add(person);
			names[i] = person.getName();
		}

		MessageDialog diag = new MessageDialog(
				authorViewer.getTable().getShell(), 
				"Remove Author" + (selection.size() > 1 ? "s" : ""), 
				null, 
				"Do you really wan't to remove " + StringUtils.join(names, ", ") + "?", 
				MessageDialog.WARNING,
				new String[] {"Yes", "No"},
				0);
		
		if (diag.open() == Dialog.OK) {
			for (Person person : persons) {
				composerPackage.getAuthors().remove(person);
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
