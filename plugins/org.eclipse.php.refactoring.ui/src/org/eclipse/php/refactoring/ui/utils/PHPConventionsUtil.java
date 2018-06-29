/*******************************************************************************
 * Copyright (c) 2005, 2015 Zend Technologies and others.
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
package org.eclipse.php.refactoring.ui.utils;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.php.refactoring.ui.RefactoringUIPlugin;

public class PHPConventionsUtil {

	public static boolean validateIdentifier(String newName) {
		if (newName == null || newName.length() == 0
				|| !Character.isLetter(newName.charAt(0)) && newName.charAt(0) != '_') {
			return false;
		}

		final int length = newName.length();
		for (int i = 1; i < length; i++) {
			if (!Character.isJavaIdentifierPart(newName.charAt(i))) {
				return false;
			}
			if (newName.charAt(i) == '$') {
				// Seva: in addition to java rules, PHP doesn't allow dollar
				// signs inside element names
				return false;
			}
		}
		return true;
	}

	public static IStatus validateFieldName(String text) {
		if (validateIdentifier(text)) {
			return Status.OK_STATUS;
		} else {
			return new Status(IStatus.ERROR, RefactoringUIPlugin.PLUGIN_ID, "Error Parameter name"); //$NON-NLS-1$
		}
	}

	public static boolean startsWithLowerCase(String text) {
		if (text == null || text.length() == 0 || !Character.isLowerCase(text.charAt(0))) {
			return false;
		}
		return true;
	}

	public static RefactoringStatus checkParameterTypeSyntax(String type, IScriptProject scriptProject) {
		// TODO Auto-generated method stub
		return null;
	}

}
