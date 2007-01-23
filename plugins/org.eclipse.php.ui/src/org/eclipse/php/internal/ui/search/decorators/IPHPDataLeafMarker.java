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
package org.eclipse.php.internal.ui.search.decorators;

import org.eclipse.core.resources.IProject;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPCodeData;

public interface IPHPDataLeafMarker extends PHPCodeData{

	/**
	 * Returns true if the search algorithm defined this code data to be displayed as a leaf.
	 * 
	 * @return True, if this code data has been defined as a leaf.
	 */
	public boolean isLeaf();
	
	/**
	 * Returns the IProject that contains this code data.
	 * 
	 * @return The IProject container.
	 */
	public IProject getProject();
}
