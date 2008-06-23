/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.search;

import org.eclipse.dltk.core.IDLTKLanguageToolkit;
import org.eclipse.dltk.ui.search.ScriptSearchPage;
import org.eclipse.php.internal.core.PHPLanguageToolkit;

public class PHPSearchPage extends ScriptSearchPage {

	@Override
	protected IDLTKLanguageToolkit getLanguageToolkit() {
		return PHPLanguageToolkit.getDefault();
	}

}