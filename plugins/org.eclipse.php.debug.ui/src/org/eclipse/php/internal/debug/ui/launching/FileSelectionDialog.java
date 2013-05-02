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
package org.eclipse.php.internal.debug.ui.launching;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;

/**
 * Dialog for selecting a file in the workspace. Derived from
 * org.eclipse.ui.dialogs.ResourceSelectionDialog
 */
public class FileSelectionDialog extends MessageDialog {
	// the root element to populate the viewer with
	private IAdaptable root;

	// the visual selection widget group
	private TreeAndListGroup selectionGroup;
	// constants
	private final static int SIZING_SELECTION_WIDGET_WIDTH = 400;
	private final static int SIZING_SELECTION_WIDGET_HEIGHT = 300;
	/**
	 * The file(s) selected by the user.
	 */
	private IStructuredSelection result = null;

	private boolean allowMultiselection = false;

	private Pattern fPattern;

	/**
	 * Creates a resource selection dialog rooted at the given element.
	 * 
	 * @param parentShell
	 *            the parent shell
	 * @param rootElement
	 *            the root element to populate this dialog with
	 * @param message
	 *            the message to be displayed at the top of this dialog, or
	 *            <code>null</code> to display a default message
	 */
	public FileSelectionDialog(Shell parentShell, IAdaptable rootElement,
			String message) {
		super(
				parentShell,
				Messages.FileSelectionDialog_0, null, message, MessageDialog.NONE, new String[] { Messages.FileSelectionDialog_1, Messages.FileSelectionDialog_2 }, 0); 
		root = rootElement;
		setShellStyle(getShellStyle() | SWT.RESIZE);
	}

	/**
	 * Limits the files displayed in this dialog to files matching the given
	 * pattern. The string can be a filename or a regular expression containing
	 * '*' for any series of characters or '?' for any single character.
	 * 
	 * @param pattern
	 *            a pattern used to filter the displayed files or
	 *            <code>null</code> to display all files. If a pattern is
	 *            supplied, only files whose names match the given pattern will
	 *            be available for selection.
	 * @param ignoreCase
	 *            if true, case is ignored. If the pattern argument is
	 *            <code>null</code>, this argument is ignored.
	 */
	public void setFileFilter(String pattern, boolean ignoreCase) {
		if (pattern != null) {
			if (ignoreCase) {
				fPattern = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
			} else {
				fPattern = Pattern.compile(pattern);
			}
		} else {
			fPattern = null;
		}
	}

	/*
	 * (non-Javadoc) Method declared in Window.
	 */
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		// PlatformUI.getWorkbench().getHelpSystem().setHelp(shell,
		// IExternalToolsHelpContextIds.FILE_SELECTION_DIALOG);
	}

	protected void createButtonsForButtonBar(Composite parent) {
		super.createButtonsForButtonBar(parent);
		initializeDialog();
	}

	/*
	 * (non-Javadoc) Method declared on Dialog.
	 */
	protected Control createDialogArea(Composite parent) {
		// page group
		Composite composite = (Composite) super.createDialogArea(parent);

		// create the input element, which has the root resource
		// as its only child
		selectionGroup = new TreeAndListGroup(composite, root,
				getResourceProvider(IResource.FOLDER | IResource.PROJECT
						| IResource.ROOT),
				new WorkbenchLabelProvider(),
				getResourceProvider(IResource.FILE),
				new WorkbenchLabelProvider(),
				SWT.NONE,
				// since this page has no other significantly-sized
				// widgets we need to hardcode the combined widget's
				// size, otherwise it will open too small
				SIZING_SELECTION_WIDGET_WIDTH, SIZING_SELECTION_WIDGET_HEIGHT,
				allowMultiselection);

		composite.addControlListener(new ControlListener() {
			public void controlMoved(ControlEvent e) {
			}

			public void controlResized(ControlEvent e) {
				// Also try and reset the size of the columns as appropriate
				TableColumn[] columns = selectionGroup.getListTable()
						.getColumns();
				for (int i = 0; i < columns.length; i++) {
					columns[i].pack();
				}
			}
		});

		return composite;
	}

	/**
	 * Returns a content provider for <code>IResource</code> s that returns only
	 * children of the given resource type.
	 */
	private ITreeContentProvider getResourceProvider(final int resourceType) {
		return new WorkbenchContentProvider() {
			public Object[] getChildren(Object o) {
				if (o instanceof IContainer) {
					IResource[] members = null;
					try {
						members = ((IContainer) o).members();
						List accessibleMembers = new ArrayList(members.length);
						for (int i = 0; i < members.length; i++) {
							IResource resource = members[i];
							if (resource.isAccessible()) {
								accessibleMembers.add(resource);
							}
						}
						members = (IResource[]) accessibleMembers
								.toArray(new IResource[accessibleMembers.size()]);
					} catch (CoreException e) {
						// just return an empty set of children
						return new Object[0];
					}

					// filter out the desired resource types
					ArrayList results = new ArrayList();
					for (int i = 0; i < members.length; i++) {
						// And the test bits with the resource types to see if
						// they are what we want
						if ((members[i].getType() & resourceType) > 0) {
							if (members[i].getType() == IResource.FILE
									&& fPattern != null
									&& !fPattern.matcher(members[i].getName())
											.find()) {
								continue;
							}
							results.add(members[i]);
						}
					}
					return results.toArray();
				}

				return new Object[0];
			}
		};
	}

	/**
	 * Initializes this dialog's controls.
	 */
	private void initializeDialog() {
		selectionGroup
				.addSelectionChangedListener(new ISelectionChangedListener() {
					public void selectionChanged(SelectionChangedEvent event) {
						getButton(IDialogConstants.OK_ID).setEnabled(
								!selectionGroup.getListTableSelection()
										.isEmpty());
					}
				});
		selectionGroup.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				buttonPressed(IDialogConstants.OK_ID);
			}
		});

		getButton(IDialogConstants.OK_ID).setEnabled(false);
	}

	/**
	 * Returns the file the user chose or <code>null</code> if none.
	 */
	public IStructuredSelection getResult() {
		return result;
	}

	protected void buttonPressed(int buttonId) {
		if (buttonId == IDialogConstants.OK_ID) {
			result = selectionGroup.getListTableSelection();
		}
		super.buttonPressed(buttonId);
	}

	/**
	 * Sets whether this dialog will allow multi-selection. Must be called
	 * before <code>open</code>
	 * 
	 * @param allowMultiselection
	 *            whether to allow multi-selection in the dialog
	 */
	public void setAllowMultiselection(boolean allowMultiselection) {
		this.allowMultiselection = allowMultiselection;
	}
}
