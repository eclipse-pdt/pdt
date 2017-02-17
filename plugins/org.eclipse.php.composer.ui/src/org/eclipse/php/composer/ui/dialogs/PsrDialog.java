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
package org.eclipse.php.composer.ui.dialogs;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.php.composer.api.objects.Namespace;
import org.eclipse.php.composer.core.log.Logger;
import org.eclipse.php.composer.ui.ComposerUIPluginConstants;
import org.eclipse.php.composer.ui.ComposerUIPluginImages;
import org.eclipse.php.composer.ui.controller.PathController;
import org.eclipse.php.composer.ui.utils.WidgetHelper;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.dialogs.CheckedTreeSelectionDialog;

public class PsrDialog extends Dialog {

	private Text namespaceControl;

	private Namespace namespace;
	private IProject project;

	private TableViewer pathViewer;

	public PsrDialog(Shell parentShell, Namespace namespace, IProject project) {
		super(parentShell);
		this.namespace = namespace;
		this.project = project;
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		getShell().setText(Messages.PsrDialog_Title);
		getShell().setImage(ComposerUIPluginImages.EVENT.createImage());

		Composite contents = new Composite(parent, SWT.NONE);
		contents.setLayout(new GridLayout(3, false));
		GridData gd_contents = new GridData();
		gd_contents.widthHint = 350;
		contents.setLayoutData(gd_contents);

		Label lblEvent = new Label(contents, SWT.NONE);
		GridData gd_lblEvent = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_lblEvent.widthHint = ComposerUIPluginConstants.DIALOG_LABEL_WIDTH;
		lblEvent.setLayoutData(gd_lblEvent);
		lblEvent.setText(Messages.PsrDialog_NamespaceLabel);

		namespaceControl = new Text(contents, SWT.BORDER);
		GridData gd_eventControl = new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1);
		gd_eventControl.widthHint = ComposerUIPluginConstants.DIALOG_CONTROL_WIDTH;
		namespaceControl.setLayoutData(gd_eventControl);

		namespaceControl.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				namespace.setNamespace(namespaceControl.getText());
			}
		});

		if (namespace.getNamespace() != null) {
			namespaceControl.setText(namespace.getNamespace());
		} else {
			// must never be null, so at least be sure to always return an empty
			// string
			namespaceControl.setText(""); //$NON-NLS-1$
		}

		Label lblHandler = new Label(contents, SWT.NONE);
		lblHandler.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1));
		lblHandler.setText(Messages.PsrDialog_PathsLabel);

		PathController controller = new PathController();
		pathViewer = new TableViewer(contents, SWT.BORDER | SWT.FULL_SELECTION);
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gridData.minimumHeight = 100;
		pathViewer.getTable().setLayoutData(gridData);
		pathViewer.setContentProvider(controller);
		pathViewer.setLabelProvider(controller);
		pathViewer.setInput(namespace.getPaths());

		Composite buttons = new Composite(contents, SWT.NONE);
		buttons.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1));
		buttons.setLayout(new GridLayout(1, false));

		WidgetHelper.trimComposite(buttons, 0);
		WidgetHelper.setMargin(buttons, 1, 1);
		WidgetHelper.setSpacing(buttons, 0, 0);

		Button btnEdit = new Button(buttons, SWT.PUSH);
		btnEdit.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnEdit.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1));
		btnEdit.setText(Messages.PsrDialog_EditButton);
		btnEdit.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				List<IFolder> folders = new ArrayList<IFolder>();
				for (Object path : namespace.getPaths()) {
					IResource resource = project.findMember((String) path);
					if (resource != null && resource instanceof IFolder) {
						folders.add((IFolder) resource);
					}
				}
				CheckedTreeSelectionDialog dialog = ResourceDialog.createMulti(pathViewer.getTable().getShell(),
						Messages.PsrDialog_SelectionDialogTitle, Messages.PsrDialog_SelectionDialogMessage,
						new Class[] { IFolder.class }, project, folders);

				if (dialog.open() == Dialog.OK) {
					namespace.clear();
					for (Object result : dialog.getResult()) {
						if (result instanceof IFolder) {
							namespace.add(((IFolder) result).getProjectRelativePath().toString());
						}
					}
				}
			}
		});

		Button btnRemove = new Button(buttons, SWT.NONE);
		btnRemove.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1));
		btnRemove.setText(Messages.PsrDialog_RemoveButton);

		// XXX: add/remove listener on dialog open/close
		btnRemove.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				ISelection selection = pathViewer.getSelection();
				if (!(selection instanceof StructuredSelection)) {
					return;
				}
				StructuredSelection s = (StructuredSelection) selection;
				for (Object o : s.toArray()) {
					try {
						String item = (String) o;
						pathViewer.remove(item);
						namespace.remove(item);
					} catch (Exception e2) {
						Logger.logException(e2);
					}
				}
			}
		});

		namespace.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent e) {
				// "namespace" can be modified afterwards by
				// PsrSection#handleEdit()
				if (e.getPropertyName().contains("#")) { //$NON-NLS-1$
					if (pathViewer.getControl().isDisposed()) {
						return;
					}
					pathViewer.refresh();
				}
			}
		});

		return contents;
	}

	public Namespace getNamespace() {
		return namespace;
	}

	public void setProject(IProject project) {
		this.project = project;
	}

}
