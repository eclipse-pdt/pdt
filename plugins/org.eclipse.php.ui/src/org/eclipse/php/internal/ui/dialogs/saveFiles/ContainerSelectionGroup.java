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
package org.eclipse.php.internal.ui.dialogs.saveFiles;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.internal.ide.IDEWorkbenchMessages;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.eclipse.ui.part.DrillDownComposite;

/**
 * Workbench-level composite for choosing a container.
 * 
 * @see org.eclipse.ui.dialogs.ContainerSelectionGroup
 */
public class ContainerSelectionGroup extends Composite {
	// The listener to notify of events
	private Listener listener;

	// Enable user to type in new container name
	private boolean allowNewContainerName = true;

	// show all projects by default
	private boolean showClosedProjects = true;

	// Last selection made by user
	private IContainer selectedContainer;

	// handle on parts
	private Text containerNameField;

	private ContainerContentProvider cp;

	TreeViewer treeViewer;

	// the message to display at the top of this dialog
	private static final String DEFAULT_MSG_NEW_ALLOWED = IDEWorkbenchMessages.ContainerGroup_message;

	private static final String DEFAULT_MSG_SELECT_ONLY = IDEWorkbenchMessages.ContainerGroup_selectFolder;

	// sizing constants
	private static final int SIZING_SELECTION_PANE_WIDTH = 320;

	private static final int SIZING_SELECTION_PANE_HEIGHT = 300;

	/**
	 * Creates a new instance of the widget.
	 * 
	 * @param parent
	 *            The parent widget of the group.
	 * @param listener
	 *            A listener to forward events to. Can be null if no listener is
	 *            required.
	 * @param allowNewContainerName
	 *            Enable the user to type in a new container name instead of
	 *            just selecting from the existing ones.
	 */
	public ContainerSelectionGroup(Composite parent, Listener listener, boolean allowNewContainerName) {
		this(parent, listener, allowNewContainerName, null);
	}

	/**
	 * Creates a new instance of the widget.
	 * 
	 * @param parent
	 *            The parent widget of the group.
	 * @param listener
	 *            A listener to forward events to. Can be null if no listener is
	 *            required.
	 * @param allowNewContainerName
	 *            Enable the user to type in a new container name instead of
	 *            just selecting from the existing ones.
	 * @param message
	 *            The text to present to the user.
	 */
	public ContainerSelectionGroup(Composite parent, Listener listener, boolean allowNewContainerName, String message) {
		this(parent, listener, allowNewContainerName, message, true);
	}

	/**
	 * Creates a new instance of the widget.
	 * 
	 * @param parent
	 *            The parent widget of the group.
	 * @param listener
	 *            A listener to forward events to. Can be null if no listener is
	 *            required.
	 * @param allowNewContainerName
	 *            Enable the user to type in a new container name instead of
	 *            just selecting from the existing ones.
	 * @param message
	 *            The text to present to the user.
	 * @param showClosedProjects
	 *            Whether or not to show closed projects.
	 */
	public ContainerSelectionGroup(Composite parent, Listener listener, boolean allowNewContainerName, String message,
			boolean showClosedProjects) {
		this(parent, listener, allowNewContainerName, message, showClosedProjects, SIZING_SELECTION_PANE_HEIGHT,
				SIZING_SELECTION_PANE_WIDTH);
	}

	/**
	 * Creates a new instance of the widget.
	 * 
	 * @param parent
	 *            The parent widget of the group.
	 * @param listener
	 *            A listener to forward events to. Can be null if no listener is
	 *            required.
	 * @param allowNewContainerName
	 *            Enable the user to type in a new container name instead of
	 *            just selecting from the existing ones.
	 * @param message
	 *            The text to present to the user.
	 * @param showClosedProjects
	 *            Whether or not to show closed projects.
	 * @param heightHint
	 *            height hint for the drill down composite
	 * @param widthHint
	 *            width hint for the drill down composite
	 */
	public ContainerSelectionGroup(Composite parent, Listener listener, boolean allowNewContainerName, String message,
			boolean showClosedProjects, int heightHint, int widthHint) {
		super(parent, SWT.NONE);
		this.listener = listener;
		this.allowNewContainerName = allowNewContainerName;
		this.showClosedProjects = showClosedProjects;
		if (message != null) {
			createContents(message, heightHint, widthHint);
		} else if (allowNewContainerName) {
			createContents(DEFAULT_MSG_NEW_ALLOWED, heightHint, widthHint);
		} else {
			createContents(DEFAULT_MSG_SELECT_ONLY, heightHint, widthHint);
		}
	}

	/**
	 * The container selection has changed in the tree view. Update the
	 * container name field value and notify all listeners.
	 * 
	 * @param container
	 *            The container that changed
	 */
	public void containerSelectionChanged(IContainer container) {
		selectedContainer = container;

		if (allowNewContainerName) {
			if (container == null) {
				containerNameField.setText(""); //$NON-NLS-1$
			} else {
				String text = container.getFullPath().makeRelative().toString();
				containerNameField.setText(text);
				containerNameField.setToolTipText(text);
			}
		}

		// fire an event so the parent can update its controls
		if (listener != null) {
			Event changeEvent = new Event();
			changeEvent.type = SWT.Selection;
			changeEvent.widget = this;
			listener.handleEvent(changeEvent);
		}
	}

	/**
	 * Creates the contents of the composite.
	 * 
	 * @param message
	 */
	public void createContents(String message) {
		createContents(message, SIZING_SELECTION_PANE_HEIGHT, SIZING_SELECTION_PANE_WIDTH);
	}

	/**
	 * Creates the contents of the composite.
	 * 
	 * @param message
	 * @param heightHint
	 * @param widthHint
	 */
	public void createContents(String message, int heightHint, int widthHint) {
		GridLayout layout = new GridLayout();
		layout.marginWidth = 0;
		setLayout(layout);
		setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		Label label = new Label(this, SWT.WRAP);
		label.setText(message);
		label.setFont(this.getFont());

		if (allowNewContainerName) {
			containerNameField = new Text(this, SWT.SINGLE | SWT.BORDER);
			GridData gd = new GridData(GridData.FILL_HORIZONTAL);
			gd.widthHint = widthHint;
			containerNameField.setLayoutData(gd);
			containerNameField.addListener(SWT.Modify, listener);
			containerNameField.setFont(this.getFont());
		} else {
			// filler...
			new Label(this, SWT.NONE);
		}

		createTreeViewer(heightHint);
		Dialog.applyDialogFont(this);
	}

	/**
	 * Returns a new drill down viewer for this dialog.
	 * 
	 * @param heightHint
	 *            height hint for the drill down composite
	 */
	protected void createTreeViewer(int heightHint) {
		// Create drill down.
		DrillDownComposite drillDown = new DrillDownComposite(this, SWT.BORDER);
		GridData spec = new GridData(SWT.FILL, SWT.FILL, true, true);
		spec.widthHint = SIZING_SELECTION_PANE_WIDTH;
		spec.heightHint = heightHint;
		drillDown.setLayoutData(spec);

		// Create tree viewer inside drill down.
		treeViewer = new TreeViewer(drillDown, SWT.NONE);
		drillDown.setChildTree(treeViewer);
		cp = new ContainerContentProvider();
		cp.showClosedProjects(showClosedProjects);
		treeViewer.setContentProvider(cp);
		treeViewer.setLabelProvider(WorkbenchLabelProvider.getDecoratingWorkbenchLabelProvider());
		treeViewer.setComparator(new ViewerComparator());
		treeViewer.setUseHashlookup(true);
		treeViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection selection = (IStructuredSelection) event.getSelection();
				containerSelectionChanged((IContainer) selection.getFirstElement()); // allow
																						// null
			}
		});
		treeViewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				ISelection selection = event.getSelection();
				if (selection instanceof IStructuredSelection) {
					Object item = ((IStructuredSelection) selection).getFirstElement();
					if (item == null) {
						return;
					}
					if (treeViewer.getExpandedState(item)) {
						treeViewer.collapseToLevel(item, 1);
					} else {
						treeViewer.expandToLevel(item, 1);
					}
				}
			}
		});

		// This has to be done after the viewer has been laid out
		treeViewer.setInput(ResourcesPlugin.getWorkspace());
	}

	void refreshResources() {
		treeViewer.refresh();
	}

	/**
	 * Returns the currently entered container name. Null if the field is empty.
	 * Note that the container may not exist yet if the user entered a new
	 * container name in the field.
	 * 
	 * @return IPath
	 */
	public IPath getContainerFullPath() {
		if (allowNewContainerName) {
			String pathName = containerNameField.getText();
			if (pathName == null || pathName.length() < 1) {
				return null;
			}
			// The user may not have made this absolute so do it for them
			return (new Path(pathName)).makeAbsolute();

		}
		if (selectedContainer == null)
			return null;
		return selectedContainer.getFullPath();

	}

	/**
	 * Gives focus to one of the widgets in the group, as determined by the
	 * group.
	 */
	public void setInitialFocus() {
		if (allowNewContainerName) {
			containerNameField.setFocus();
		} else {
			treeViewer.getTree().setFocus();
		}
	}

	/**
	 * Sets the selected existing container.
	 * 
	 * @param container
	 */
	public void setSelectedContainer(IContainer container) {
		selectedContainer = container;

		// expand to and select the specified container
		List itemsToExpand = new ArrayList();
		IContainer parent = container.getParent();
		while (parent != null) {
			itemsToExpand.add(0, parent);
			parent = parent.getParent();
		}
		treeViewer.setExpandedElements(itemsToExpand.toArray());
		treeViewer.setSelection(new StructuredSelection(container), true);
	}
}
