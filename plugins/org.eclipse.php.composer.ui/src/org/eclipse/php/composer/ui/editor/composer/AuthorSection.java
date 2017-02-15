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
import org.eclipse.jface.viewers.*;
import org.eclipse.osgi.util.NLS;
import org.eclipse.php.composer.api.collection.Persons;
import org.eclipse.php.composer.api.objects.Person;
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
			authors = (Persons) newInput;
		}

		public Object[] getElements(Object inputElement) {
			return authors.toArray();
		}

		public void update(ViewerCell cell) {
			Object obj = cell.getElement();

			if (obj instanceof Person) {
				Person author = (Person) obj;

				StyledString styledString = author.getName() != null ? new StyledString(author.getName())
						: new StyledString();

				if (author.getEmail() != null && !author.getEmail().trim().isEmpty()) {
					styledString.append(" <" + author.getEmail().trim() + ">", StyledString.COUNTER_STYLER); //$NON-NLS-1$ //$NON-NLS-2$
				}

				if (author.getHomepage() != null && !author.getHomepage().trim().isEmpty()) {
					styledString.append(" - " + author.getHomepage().trim(), StyledString.DECORATIONS_STYLER); //$NON-NLS-1$
				}

				cell.setText(styledString.toString());
				cell.setStyleRanges(styledString.getStyleRanges());

				cell.setImage(authorImage);

				super.update(cell);
			}
		}
	}

	public AuthorSection(ComposerFormPage page, Composite parent) {
		super(page, parent, Section.DESCRIPTION, new String[] { Messages.AuthorSection_AddButton,
				Messages.AuthorSection_EditButton, Messages.AuthorSection_RemoveButton });
		// createClient(getSection(), page.getManagedForm().getToolkit());
	}

	@Override
	protected void createClient(Section section, FormToolkit toolkit) {
		section.setText(Messages.AuthorSection_Title);
		section.setDescription(Messages.AuthorSection_Description);
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
		IStructuredSelection selection = (IStructuredSelection) authorViewer.getSelection();

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
		if (e.getPropertyName().startsWith("authors")) { //$NON-NLS-1$
			// refresh();
		}
	}

	protected void selectionChanged(IStructuredSelection sel) {
		updateButtons();
		updateMenu();
	}

	private void makeActions() {
		addAction = new Action(Messages.AuthorSection_AddActionTitle) {
			public void run() {
				handleAdd();
			}
		};

		editAction = new Action(Messages.AuthorSection_EditActionTitle) {
			public void run() {
				handleEdit();
			}
		};

		removeAction = new Action(Messages.AuthorSection_RemoveActionTitle) {
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
		Person author = (Person) ((StructuredSelection) authorViewer.getSelection()).getFirstElement();
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
		StructuredSelection selection = ((StructuredSelection) authorViewer.getSelection());
		Iterator<Object> it = selection.iterator();
		String[] names = new String[selection.size()];
		List<Person> persons = new ArrayList<Person>();

		for (int i = 0; it.hasNext(); i++) {
			Person person = (Person) it.next();
			persons.add(person);
			names[i] = person.getName();
		}

		String title = selection.size() > 1 ? Messages.AuthorSection_RemoveDialogTitlePlural
				: Messages.AuthorSection_RemoveDialogTitle;
		MessageDialog diag = new MessageDialog(authorViewer.getTable().getShell(), title, null,
				NLS.bind(Messages.AuthorSection_RemoveDialogMessage, StringUtils.join(names, ", ")), //$NON-NLS-1$
				MessageDialog.WARNING,
				new String[] { Messages.AuthorSection_YesButton, Messages.AuthorSection_NoButton }, 0);

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
