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
package org.eclipse.php.internal.ui.wizards;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

/**
 * A composite for use as fragments in Server editing dialogs. This composite
 * has an attached IControlHandler that allows interaction with other
 * components. This class is intended to be subclassed.
 */
public abstract class CompositeFragment extends Composite {

	protected IControlHandler controlHandler;
	private boolean isComplete;
	private String name;
	private boolean isForEditing;
	private String title;
	private String description;
	private Object fragmentData;
	private ImageDescriptor imageDescriptor;

	/**
	 * Constructs a new CompositeFragment.
	 * 
	 * @param parent
	 * @param style
	 * @param handler
	 * @param isForEditing
	 *            Indicate if this composite will be used for server editing (as
	 *            opposed to server Wizard)
	 */
	public CompositeFragment(Composite parent, int style, IControlHandler handler, boolean isForEditing) {
		super(parent, style);
		this.controlHandler = handler;
		this.isForEditing = isForEditing;
		createControl();
	}

	/**
	 * Constructs a new CompositeFragment.
	 * 
	 * @param parent
	 * @param handler
	 * @param isForEditing
	 *            Indicate if this composite will be used for server editing (as
	 *            opposed to server Wizard)
	 */
	public CompositeFragment(Composite parent, IControlHandler handler, boolean isForEditing) {
		this(parent, SWT.NONE, handler, isForEditing);
	}

	/**
	 * Performs special processing when the OK button has been pressed.
	 * 
	 * @return <code>false</code> to abort the container's OK processing and
	 *         <code>true</code> to allow the OK to happen
	 */
	public abstract boolean performOk();

	/**
	 * Validate data values in current composite.
	 */
	public abstract void validate();

	/**
	 * Attach data instance to this fragment.
	 * 
	 * @param fragmentData
	 */
	@Override
	public void setData(Object fragmentData) {
		this.fragmentData = fragmentData;
	}

	/**
	 * Returns the attached data instance.
	 * 
	 * @return The data instance attached to this fragment.
	 */
	@Override
	public Object getData() {
		return fragmentData;
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
	 * @return The display name of the composite. The returned value should
	 *         never be null.
	 */
	public String getDisplayName() {
		if (name == null) {
			name = ""; //$NON-NLS-1$
		}
		return name;
	}

	/**
	 * Sets image descriptor
	 * 
	 * @param descriptor
	 */
	public void setImageDescriptor(ImageDescriptor descriptor) {
		imageDescriptor = descriptor;
	}

	/**
	 * @return the imageDescriptor
	 */
	public ImageDescriptor getImageDescriptor() {
		return imageDescriptor;
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
	 * Returns if this runtime composite was designated for editing mode. When
	 * in editing, the behavior of the composite's creation and validation
	 * processes might be different.
	 * 
	 * @return True, iff the composite was created in editing mode; false
	 *         otherwise.
	 */
	public boolean isForEditing() {
		return isForEditing;
	}

	/**
	 * Returns a unique id of the CompositeFragment. The user must re-implement
	 * this method to return a unique ID.
	 * 
	 * @return returns the unique ID.
	 */
	public String getId() {
		return null;
	}

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
	 * Performs special processing when this runtime composite when Cancel
	 * button has been pressed.
	 * <p>
	 * The default implementation of this method does nothing and returns
	 * <code>true</code>.
	 */
	public boolean performCancel() {
		return true;
	}

	/**
	 * Returns if this composite processing has no errors and is defined as
	 * 'complete'.
	 * 
	 * @return The completeness state of this composite.
	 */
	public boolean isComplete() {
		return isComplete;
	}

	public void setMessage(String message, int type) {
		controlHandler.setMessage(message, type);
		setComplete(type != IMessageProvider.ERROR);
		controlHandler.update();
	}

	/**
	 * Sets the completeness state of this composite.
	 * 
	 * @param isComplete
	 */
	public void setComplete(boolean isComplete) {
		this.isComplete = isComplete;
		controlHandler.update();
	}

	/**
	 * Creates contents of this fragment with the use of provided parent
	 * composite.
	 * 
	 * @param parent
	 */
	protected abstract void createContents(Composite parent);

	/**
	 * Creates control for this fragment.
	 */
	protected void createControl() {
		GridLayout layout = new GridLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		setLayout(layout);
		setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		Composite composite = new Composite(this, SWT.NONE);
		composite.setLayout(new GridLayout());
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		createContents(composite);
	}

}
