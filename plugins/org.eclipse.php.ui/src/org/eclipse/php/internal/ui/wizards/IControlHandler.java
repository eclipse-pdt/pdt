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

import java.lang.reflect.InvocationTargetException;

import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.resource.ImageDescriptor;

public interface IControlHandler {

	/**
	 * Control handler kind.
	 */
	public enum Kind {

		WIZARD, EDITOR;

	}

	/**
	 * Updates the control error messages and buttons.
	 */
	public void update();

	/**
	 * Sets the title of this control (if exists).
	 * 
	 * @param title
	 *            the title of the control
	 */
	public void setTitle(String title);

	/**
	 * Sets the control's description (if exists).
	 * 
	 * @param desc
	 *            the control's description
	 */
	public void setDescription(String desc);

	/**
	 * The control's image descriptor.
	 * 
	 * @param image
	 *            the control's image descriptor
	 */
	public void setImageDescriptor(ImageDescriptor image);

	/**
	 * Set an error or warning message.
	 * 
	 * @param newMessage
	 *            the new message
	 * @param newType
	 *            the new type, from IStatus
	 */
	public void setMessage(String newMessage, int newType);

	/**
	 * Returns kind of control handler.
	 * 
	 * @return kind of control handler
	 */
	public Kind getKind();

	/**
	 * Runs the given IRunnableWithProgress in this context. It may not be
	 * supported by all implementors.
	 * 
	 * If fork is false, the current thread is used to run the runnable. Note
	 * that if fork is true, it is unspecified whether or not this method blocks
	 * until the runnable has been run. Implementers should document whether the
	 * runnable is run synchronously (blocking) or asynchronously
	 * (non-blocking), or if no assumption can be made about the blocking
	 * behaviour.
	 * 
	 * Parameters: fork - true if the runnable should be run in a separate
	 * thread, and false to run in the same thread cancelable - true to enable
	 * the cancelation, and false to make the operation uncancellable runnable -
	 * the runnable to run Throws: InvocationTargetException - wraps any
	 * exception or error which occurs while running the runnable
	 * InterruptedException - propagated by the context if the runnable
	 * acknowledges cancelation by throwing this exception. This should not be
	 * thrown if cancelable is false.
	 * 
	 * @param fork
	 *            - <code>true</code> if the runnable should be run in a
	 *            separate thread, and false to run in the same thread
	 * @param cancelable
	 *            - <code>true</code> to enable the cancelation, and false to
	 *            make the operation uncancellable
	 * @param runnable
	 *            - the runnable to run
	 * @throws InvocationTargetException
	 *             - wraps any exception or error which occurs while running the
	 *             runnable
	 * @throws InterruptedException
	 *             - propagated by the context if the runnable acknowledges
	 *             cancelation by throwing this exception. This should not be
	 *             thrown if cancelable is <code>false</code>.
	 */
	void run(boolean fork, boolean cancelable, IRunnableWithProgress runnable)
			throws InvocationTargetException, InterruptedException;

}
