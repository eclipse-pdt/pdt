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
/**
 * 
 */
package org.eclipse.php.internal.ui.actions;

import org.eclipse.dltk.ui.IDLTKUILanguageToolkit;
import org.eclipse.php.internal.ui.PHPUILanguageToolkit;

/**
 * Open type in hierarchy action.
 * 
 * @author shalom
 */
public class OpenTypeInHierarchyAction extends org.eclipse.dltk.ui.actions.OpenTypeInHierarchyAction {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.dltk.ui.actions.OpenTypeInHierarchyAction#getLanguageToolkit
	 * ()
	 */
	@Override
	protected IDLTKUILanguageToolkit getLanguageToolkit() {
		return PHPUILanguageToolkit.getInstance();
	}
}
