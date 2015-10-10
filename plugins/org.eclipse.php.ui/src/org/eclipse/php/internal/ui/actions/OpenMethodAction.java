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
package org.eclipse.php.internal.ui.actions;

import org.eclipse.dltk.ui.IDLTKUILanguageToolkit;
import org.eclipse.php.internal.ui.PHPUILanguageToolkit;

/**
 * Open type in hierarchy action.
 * 
 * @author Roy, 2008
 */
public class OpenMethodAction extends org.eclipse.dltk.ui.actions.OpenMethodAction {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.dltk.ui.actions.OpenTypeInHierarchyAction#getLanguageToolkit
	 * ()
	 */

	@Override
	protected IDLTKUILanguageToolkit getUILanguageToolkit() {
		return PHPUILanguageToolkit.getInstance();
	}
}
