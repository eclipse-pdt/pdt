/*******************************************************************************
 * Copyright (c) 2006, 2015 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.refactoring.ui.actions;

import org.eclipse.php.ui.actions.IRenamePHPElementActionFactory;
import org.eclipse.ui.IActionDelegate;

/**
 * A factory of the element refactoring for Studio (replaces the default PDT
 * one)
 * 
 * @author Roy, 2007
 */
public class RenamePhpElementActionFactory implements
		IRenamePHPElementActionFactory {

	public IActionDelegate createRenameAction() {
		return new RenamePHPElementActionDelegate();
	}

}
