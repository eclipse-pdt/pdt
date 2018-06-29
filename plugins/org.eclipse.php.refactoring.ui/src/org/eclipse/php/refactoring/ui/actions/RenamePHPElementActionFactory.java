/*******************************************************************************
 * Copyright (c) 2006, 2015 Zend Technologies and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
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
public class RenamePHPElementActionFactory implements IRenamePHPElementActionFactory {

	@Override
	public IActionDelegate createRenameAction() {
		return new RenamePHPElementActionDelegate();
	}

}
