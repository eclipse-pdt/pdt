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
package org.eclipse.php.internal.ui.preferences;

import org.eclipse.dltk.core.IDLTKLanguageToolkit;
import org.eclipse.dltk.ui.preferences.UserLibraryPreferencePage;
import org.eclipse.php.internal.core.PHPLanguageToolkit;

/**
 * Preference page for user libraries
 */
public class PhpLibraryPreferencePage extends UserLibraryPreferencePage {

	protected IDLTKLanguageToolkit getLanguageToolkit() {
		return PHPLanguageToolkit.getDefault();
	}

}
