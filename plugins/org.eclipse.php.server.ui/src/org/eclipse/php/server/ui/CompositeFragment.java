/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.server.ui;

import org.eclipse.php.server.core.Server;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

/**
 * A composite for use as fragments in Server editing dialogs.
 * This composite has an attached IControlHandler that allows interaction with other 
 * components.
 * This class is intended to be subclassed.
 */
public abstract class CompositeFragment extends Composite {

	protected IControlHandler controlHandler;
	private boolean isComplete;
	private String name;
	private boolean isForEditing;
	protected Server server;
	private String title;
	private String description;

	/**
	 * Constructs a new CompositeFragment.
	 * 
	 * @param parent
	 * @param style
	 * @param handler
	 * @param isForEditing Indicate if this composite will be used for server editing (as opposed to server Wizard)
	 */
	public CompositeFragment(Composite parent, int style, IControlHandler handler, boolean isForEditing) {
		super(parent, style);
		this.controlHandler = handler;
		this.isForEditing = isForEditing;
	}

	/**
	 * Constructs a new CompositeFragment.
	 * 
	 * @param parent
	 * @param handler
	 * @param isForEditing Indicate if this composite will be used for server editing (as opposed to server Wizard)
	 */
	public CompositeFragment(Composite parent, IControlHandler handler, boolean isForEditing) {
		this(parent, SWT.NONE, handler, isForEditing);
	}

	/**
	 * Sets a Server.
	 * 
	 * @param server
	 */
	public void setServer(Server server) {
		this.server = server;
	}

	/**
	 * Returns the attached Server.
	 * 
	 * @return Server
	 */
	public Server getServer() {
		return server;
	}

	/**
	 * Sets a display name for this runtime composite.
	 * 
	 * @param name
	 */
	public void setDisplayName(String name) {
		this.name = name;
	}

	/**
	 * Returns the name of this runtime composite.
	 * 
	 * @return The display name of the composite. The returned value should never be null.
	 */
	public String getDisplayName() {
		if (name == null) {
			name = ""; //$NON-NLS-1$
		}
		return name;
	}

	/**
	 * Returns the fragment's title.
	 */
	public String getTitle() {
		if (title == null) {
			 title = ""; //$NON-NLS-1$
		}
		return title;
	}
	
	/**
	 * Sets the description for this fragment.
	 * 
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * Returns the fragment's description.
	 */
	public String getDescription() {
		if (description == null) {
			description = ""; //$NON-NLS-1$
		}
		return description;
	}
	
	/**
	 * Sets the title for this fragment.
	 * 
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * Returns if this runtime composite was designated for editing mode.
	 * When in editing, the behavior of the composite's creation and validation processes
	 * might be different.
	 * 
	 * @return True, iff the composite was created in editing mode; false otherwise. 
	 */
	public boolean isForEditing() {
		return isForEditing;
	}

	/**
	 * Performs special processing when the OK button has been pressed.
	 * 
	 * @return <code>false</code> to abort the container's OK
	 *  processing and <code>true</code> to allow the OK to happen
	 */
	public abstract boolean performOk();

	/**
	 * Performs special processing when the Apply button has been pressed.
	 * <p>
	 * The default implementation of this framework method simply calls
	 * <code>performOk</code> to simulate the pressing of the page's OK button.
	 * </p>
	 * 
	 * @see #performOk
	 */
	public void performApply() {
		performOk();
	}

	/**	
	 * Performs special processing when this runtime composite when Cancel button has
	 * been pressed.
	 * <p>
	 * The default implementation of this method does nothing and returns <code>true</code>.
	 */
	public boolean performCancel() {
		return true;
	}

	/**
	 * Returns if this composite processing has no errors and is defined as 'complete'.
	 * 
	 * @return The completeness state of this composite.
	 */
	public boolean isComplete() {
		return isComplete;
	}

	/**
	 * Sets the completeness state of this composite.
	 * @param isComplete
	 */
	protected void setComplete(boolean isComplete) {
		this.isComplete = isComplete;
	}
}
