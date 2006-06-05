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
package org.eclipse.php.ui.workingset;

import org.eclipse.jface.action.IMenuManager;

public interface IWorkingSetActionGroup {

	public static final String ACTION_GROUP = "working_set_action_group"; //$NON-NLS-1$

	public void fillViewMenu(IMenuManager mm);

	public void cleanViewMenu(IMenuManager menuManager);

}
