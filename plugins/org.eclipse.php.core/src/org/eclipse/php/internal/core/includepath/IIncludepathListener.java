/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.core.includepath;

import org.eclipse.core.resources.IProject;

/**
 * A Listener class that is called when the include path property is updated
 * 
 * @author Roy, 2008
 */
public interface IIncludepathListener {

	/**
	 * This call-back is fetched when the include path property is changed
	 * 
	 * @param project
	 */
	public void refresh(IProject project);

}
