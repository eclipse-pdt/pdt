/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.phpCodeData;

import org.eclipse.php.internal.core.phpModel.phpElementData.PHPCodeData;
import org.eclipse.php.ui.actions.filters.IActionFilterContributor;

public class CodeDataActionFilterContributor implements IActionFilterContributor {

	public boolean testAttribute(Object target, String name, String value) {
		PHPCodeData phpCodeData = (PHPCodeData)target;
		
		// null phpCodeData.getUserData() indicates on language model elements
		return phpCodeData.getUserData() != null && phpCodeData.getDocBlock() == null;
	}
}
