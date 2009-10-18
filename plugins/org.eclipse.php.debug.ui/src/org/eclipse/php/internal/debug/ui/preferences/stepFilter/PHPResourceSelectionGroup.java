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
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.dltk.core.IBuildpathEntry;
import org.eclipse.dltk.core.environment.EnvironmentPathUtils;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.internal.ide.IDEWorkbenchMessages;
import org.eclipse.ui.part.DrillDownComposite;

/**
 * Workbench-level composite for choosing a container.
 * 
 * @see org.eclipse.ui.dialogs.ContainerSelectionGroup
 */
public class PHPResourceSelectionGroup extends Composite {
	// The listener to notify of events
	private Listener listener;

	// Enable user to type in new container name
	private boolean allowNewContainerName = true;

	// show all projects by default
	private boolean showClosedProjects = true;

	// Last selection made by user
	private Object selectedResource;

	private PHPResourceContentProvider cp;

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
	 * @param message
	 *            The text to present to the user.
	 * @param showClosedProjects
	 *            Whether or not to show closed projects.
	 */
	public PHPResourceSelectionGroup(Composite parent, Listener listener,
			boolean allowNewContainerName, String message,
			boolean showClosedProjects) {
		this(parent, listener, allowNewContainerName, message,
				showClosedProjects, SIZING_SELECTION_PANE_HEIGHT,
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
	public PHPResourceSelectionGroup(Composite parent, Listener listener,
			boolean allowNewContainerName, String message,
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
	public void resourceSelectionChanged(Object resource) {
		selectedResource = resource;

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
		createContents(message, SIZING_SELECTION_PANE_HEIGHT,
				SIZING_SELECTION_PANE_WIDTH);
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
		cp = new PHPResourceContentProvider();
		treeViewer.setContentProvider(cp);
		treeViewer.setLabelProvider(new PHPResLabelProvider());
		treeViewer.setUseHashlookup(true);
		treeViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection selection = (IStructuredSelection) event
						.getSelection();
				resourceSelectionChanged(selection.getFirstElement()); // allow
																		// null
			}
		});
		treeViewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				ISelection selection = event.getSelection();
				if (selection instanceof IStructuredSelection) {
					Object item = ((IStructuredSelection) selection)
							.getFirstElement();
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
		treeViewer.setInput(ResourcesPlugin.getWorkspace().getRoot());
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
	public IPath getPathForSelectedResource() {
		if (selectedResource == null) {
			return null;
		}
		IPath result = null;
		if (selectedResource instanceof IResource) {
			result = ((IResource) selectedResource).getFullPath();
		} else if (selectedResource instanceof IBuildpathEntry) {
			result = EnvironmentPathUtils
					.getLocalPath(((IBuildpathEntry) selectedResource)
							.getPath());
		} else if (selectedResource instanceof IncPathFile) {
			result = new Path(((IncPathFile) selectedResource).file
					.getAbsolutePath());
		}
		return result;

	}

	/**
	 * Gives focus to one of the widgets in the group, as determined by the
	 * group.
	 */
	public void setInitialFocus() {
		treeViewer.getTree().setFocus();
	}

	/**
	 * Sets the selected existing container.
	 * 
	 * @param container
	 */
	public void setSelectedResource(IResource resource) {
		selectedResource = resource;

		// expand to and select the specified container
		List itemsToExpand = new ArrayList();
		IContainer parent = resource.getParent();
		while (parent != null) {
			itemsToExpand.add(0, parent);
			parent = parent.getParent();
		}
		treeViewer.setExpandedElements(itemsToExpand.toArray());
		treeViewer.setSelection(new StructuredSelection(resource), true);
	}
}
