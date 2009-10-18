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

import java.util.HashMap;
import java.util.Map;

/**
 * A wizard model represents a model that can be shared between multiple tasks
 * in a common workflow.
 * <p>
 * The wizard model contains information about the overall task flow and allows
 * tasks to store and retreive data. Its usage allows mutliple tasks to be
 * chained together and share data from the output of one task to the input of
 * another.
 * </p>
 * 
 * [Copied from WST TaskModel)
 */
public class WizardModel {

	/**
	 * Wizard model id for an Server.
	 * 
	 * @see #getObject(String)
	 * @see #putObject(String, Object)
	 */
	public static final String SERVER = "server"; //$NON-NLS-1$

	private Map map = new HashMap();

	/**
	 * Returns the object in the wizard model with the given id.
	 * <p>
	 * The id can be any of the predefined ids within WizardModel, or any other
	 * key to retreive task-specific data.
	 * </p>
	 * 
	 * @param id
	 *            an id for the object
	 * @return the object with the given id, or <code>null</code> if no object
	 *         could be found with that id
	 */
	public Object getObject(String id) {
		try {
			return map.get(id);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Put an object into the wizard model with the given id.
	 * <p>
	 * The id can be any of the predefined ids within WizardModel, or any other
	 * key to store task-specific data.
	 * </p>
	 * 
	 * @param id
	 *            the id to associate the object with
	 * @param obj
	 *            an object, or <code>null</code> to reset (clear) the id
	 */
	public void putObject(String id, Object obj) {
		map.put(id, obj);
	}
}