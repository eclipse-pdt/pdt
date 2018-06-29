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
package org.eclipse.php.refactoring.ui.prefereces;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.php.refactoring.ui.RefactoringUIPlugin;

public class PreferenceConstants {
	/**
	 * A named preference that controls whether certain refactorings use a
	 * lightweight UI when started from a PHP editor.
	 * <p>
	 * Value is of type <code>Boolean</code>.
	 */
	public static final String REFACTOR_LIGHTWEIGHT = "Refactor.lightweight"; //$NON-NLS-1$

	public static void initializeDefaultValues(IPreferenceStore store) {
		store.setDefault(PreferenceConstants.REFACTOR_LIGHTWEIGHT, true);
	}

	/**
	 * Returns the Refactor-UI preference store.
	 * 
	 * @return the Refactor-UI preference store
	 */
	public static IPreferenceStore getPreferenceStore() {
		return RefactoringUIPlugin.getDefault().getPreferenceStore();
	}

}
