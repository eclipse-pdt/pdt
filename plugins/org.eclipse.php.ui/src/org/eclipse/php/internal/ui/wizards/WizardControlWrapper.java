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
	public void update() {
		wizard.update();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.server.apache.ui.IControlHandler#setTitle(java.lang.String
	 * )
	 */
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
	public void setMessage(String newMessage, int newType) {
		wizard.setMessage(newMessage, newType);
	}
}
