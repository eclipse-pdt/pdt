/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.debug.ui.preferences.stepFilter;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.php.internal.debug.ui.PHPDebugUIMessages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ISelectionValidator;
import org.eclipse.ui.dialogs.SelectionDialog;
import org.eclipse.ui.internal.ide.IIDEHelpContextIds;

/**
 * A PHP resources selection dialog. The <code>getResult</code> method returns
 * the selected container resource.
 * 
 * @see org.eclipse.ui.dialogs.ContainerSelectionDialog
 */
public class PHPResourceSelectionDialog extends SelectionDialog {

	private static final String EMPTY_STRING = ""; //$NON-NLS-1$

	// the widget group;
	PHPResourceSelectionGroup group;

	// the root resource to populate the viewer with
	private IContainer initialSelection;

	// allow the user to type in a new container name
	private boolean allowNewContainerName = true;

	// the validation message
	Label statusMessage;

	// for validating the selection
	ISelectionValidator validator;

	// show closed projects by default
	private boolean showClosedProjects = true;

	public PHPResourceSelectionDialog(Shell parentShell,
			IContainer initialRoot, boolean allowNewContainerName,
			String message) {
		super(parentShell);
		setTitle(PHPDebugUIMessages.PHPResourceSelectionDialog_selectResource);
		this.initialSelection = initialRoot;
		this.allowNewContainerName = allowNewContainerName;
		if (message != null) {
			setMessage(message);
		}
		setShellStyle(getShellStyle() | SWT.RESIZE);
	}

	/*
	 * (non-Javadoc) Method declared in Window.
	 */
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(shell,
				IIDEHelpContextIds.CONTAINER_SELECTION_DIALOG);
	}

	/*
	 * (non-Javadoc) Method declared on Dialog.
	 */
	protected Control createDialogArea(Composite parent) {
		// create composite
		Composite area = (Composite) super.createDialogArea(parent);

		Listener listener = new Listener() {
			public void handleEvent(Event event) {
				if (statusMessage != null && validator != null) {
					String errorMsg = validator.isValid(group
							.getPathForSelectedResource());
					if (errorMsg == null || errorMsg.equals(EMPTY_STRING)) {
						statusMessage.setText(EMPTY_STRING);
						getOkButton().setEnabled(true);
					} else {
						statusMessage.setText(errorMsg);
						getOkButton().setEnabled(false);
					}
				}
			}

		};

		// container selection group
		group = new PHPResourceSelectionGroup(area, listener,
				allowNewContainerName, getMessage(), showClosedProjects);
		if (initialSelection != null) {
			group.setSelectedResource(initialSelection);
		}

		statusMessage = new Label(area, SWT.WRAP);
		statusMessage.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		statusMessage.setText(" \n "); //$NON-NLS-1$
		statusMessage.setFont(parent.getFont());

		return dialogArea;
	}

	/**
	 * The <code>ContainerSelectionDialog</code> implementation of this
	 * <code>Dialog</code> method builds a list of the selected resource
	 * containers for later retrieval by the client and closes this dialog.
	 */
	protected void okPressed() {

		List<Object> chosenResourcesList = new ArrayList<Object>();
		Object selection = ((TreeSelection) group.treeViewer.getSelection())
				.getFirstElement();
		if (selection != null) {
			chosenResourcesList.add(selection);
		}
		setResult(chosenResourcesList);
		super.okPressed();
	}

	/**
	 * Sets the validator to use.
	 * 
	 * @param validator
	 *            A selection validator
	 */
	public void setValidator(ISelectionValidator validator) {
		this.validator = validator;
	}

	/**
	 * Set whether or not closed projects should be shown in the selection
	 * dialog.
	 * 
	 * @param show
	 *            Whether or not to show closed projects.
	 */
	public void showClosedProjects(boolean show) {
		this.showClosedProjects = show;
	}
}
