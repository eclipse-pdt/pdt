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
package org.eclipse.php.debug.ui.preferences.phps;

import org.eclipse.php.debug.core.preferences.PHPexeItem;

public interface IAddPHPexeDialogRequestor {

	public boolean isDuplicateName(String name);

	public void phpExeAdded(PHPexeItem vm);

}
