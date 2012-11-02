/*******************************************************************************
 * Copyright (c) 2012 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Martin Eisengardt <martin.eisengardt@fiducia.de>
 *******************************************************************************/
package org.eclipse.php.ui.wizards;

import org.eclipse.core.resources.IProject;
import org.eclipse.php.internal.ui.wizards.WizardModel;

/**
 * A single operation for the new project wizard
 * 
 */
public interface INewProjectWizardOperation {

	/**
	 * Invoked before switching from facets page to the php include page. Can be
	 * used to preset/manipulate the include path. The given project is already
	 * prepared/ the default.
	 * 
	 * @param projectHandle
	 *            The project handle
	 * @param model
	 *            the wizard data model
	 * @param keep
	 *            true to keep the sources (overwrite existing project) or false
	 *            to create a new project
	 */
	public void onPreparePhpProject(IProject projectHandle, WizardModel model,
			boolean keep);

	/**
	 * Invoked after the user clicks on finish
	 * 
	 * @param projectHandle
	 *            the final php project
	 * @param model
	 *            the wizard data model
	 */
	public void onFinish(IProject projectHandle, WizardModel model);

	/**
	 * Invoked after the user clicks on cancel. should be used for cleanup.
	 * 
	 * @param projectHandle
	 *            the temporary php project
	 * @param model
	 *            the wizard data model
	 */
	public void onCancel(IProject projectHandle, WizardModel model);

}