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

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.resource.ImageDescriptor;

/**
 * A wizard handle. [Copied from WST)
 */
public interface IWizardHandle extends IMessageProvider {
	/**
	 * Updates the wizard error messages and buttons.
	 */
	public void update();

	/**
	 * Sets the title of this wizard page.
	 * 
	 * @param title
	 *            the title of the wizard page
	 */
	public void setTitle(String title);

	/**
	 * The page's description.
	 * 
	 * @param desc
	 *            the page's description
	 */
	public void setDescription(String desc);

	/**
	 * The page's image descriptor.
	 * 
	 * @param image
	 *            the page's image descriptor
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
	 * Execute a runnable within the context of the wizard. This will typically
	 * disable the wizard while the runnable is running, and provide a progress
	 * monitor for the user.
	 * 
	 * @param fork
	 *            true if a separate thread should be used
	 * @param cancelable
	 *            true if it should be cancelable
	 * @param runnable
	 *            the runnable
	 * @throws InterruptedException
	 *             thrown if it is interrupted
	 * @throws InvocationTargetException
	 *             thrown if there is an error
	 */
	public void run(boolean fork, boolean cancelable,
			IRunnableWithProgress runnable) throws InterruptedException,
			InvocationTargetException;
}