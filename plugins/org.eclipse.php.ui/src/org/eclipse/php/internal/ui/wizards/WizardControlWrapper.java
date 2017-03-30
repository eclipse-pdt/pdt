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

/**
 * The WizardControlWrapper wrapps a given IWizardHandler and IServerContainer
 * as an IControlHandler. This wrapper enables more flexibility in defining
 * Composits that will be usable in wizards fragments as well as stand-alone UI
 * parts.
 */
public class WizardControlWrapper implements IControlHandler {

	private IWizardHandle wizard;

	/**
	 * Constructs a new WizardControlWrapper with a given IWizardHandle
	 * 
	 * @param wizard
	 *            An IWizardHandle to wrap as IControlHandler
	 */
	public WizardControlWrapper(IWizardHandle wizard) {
		this.wizard = wizard;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.server.apache.ui.IControlHandler#update()
	 */
	@Override
	public void update() {
		wizard.update();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.server.apache.ui.IControlHandler#setTitle(java.lang.
	 * String )
	 */
	@Override
	public void setTitle(String title) {
		wizard.setTitle(title);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.server.apache.ui.IControlHandler#setDescription(java.
	 * lang.String)
	 */
	@Override
	public void setDescription(String desc) {
		wizard.setDescription(desc);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.server.apache.ui.IControlHandler#setImageDescriptor(org
	 * .eclipse.jface.resource.ImageDescriptor)
	 */
	@Override
	public void setImageDescriptor(ImageDescriptor image) {
		wizard.setImageDescriptor(image);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.server.apache.ui.IControlHandler#setMessage(java.lang
	 * .String, int)
	 */
	@Override
	public void setMessage(String newMessage, int newType) {
		wizard.setMessage(newMessage, newType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.ui.wizards.IControlHandler#getKind()
	 */
	@Override
	public Kind getKind() {
		return Kind.WIZARD;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.ui.wizards.IControlHandler#run(boolean,
	 * boolean, org.eclipse.jface.operation.IRunnableWithProgress)
	 */
	@Override
	public void run(boolean fork, boolean cancelable, IRunnableWithProgress runnable)
			throws InvocationTargetException, InterruptedException {
		wizard.run(fork, cancelable, runnable);
	}

}
