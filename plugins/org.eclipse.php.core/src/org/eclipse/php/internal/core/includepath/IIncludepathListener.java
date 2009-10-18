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
