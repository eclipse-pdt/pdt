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
package org.eclipse.php.ui.actions;

import org.eclipse.php.internal.ui.actions.RenamePHPElementAction;
import org.eclipse.ui.IActionDelegate;

/**
 * Factory for {@link RenamePHPElementAction} This Extension will enable rename
 * php elements actions
 * 
 * @author Roy, 2007
 */
public interface IRenamePHPElementActionFactory {

	public abstract IActionDelegate createRenameAction();

}
