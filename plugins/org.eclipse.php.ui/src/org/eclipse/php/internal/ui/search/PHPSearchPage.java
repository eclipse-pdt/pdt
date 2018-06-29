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
package org.eclipse.php.internal.ui.search;

import org.eclipse.dltk.core.IDLTKLanguageToolkit;
import org.eclipse.dltk.ui.search.ScriptSearchPage;
import org.eclipse.php.internal.core.PHPLanguageToolkit;

public class PHPSearchPage extends ScriptSearchPage {
	public static final String ID = "org.eclipse.php.ui.PHPSearchPage"; //$NON-NLS-1$

	@Override
	protected IDLTKLanguageToolkit getLanguageToolkit() {
		return PHPLanguageToolkit.getDefault();
	}

}