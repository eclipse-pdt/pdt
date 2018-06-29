/*******************************************************************************
 * Copyright (c) 2007, 2015 Zend Technologies and others.
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
package org.eclipse.php.refactoring.ui.wizard;

import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.php.refactoring.ui.PHPRefactoringUIMessages;

/**
 * This class is responsible for the display of the rename refactoring wizard
 * and it functionality.
 * 
 * @author Roy G., 2007
 * 
 */
public class RenameLocalVariableWizard extends RenameRefactoringWizard {

	public RenameLocalVariableWizard(Refactoring refactoring) {
		super(refactoring, PHPRefactoringUIMessages.getString("RenameLocalVariableWizard_title"), //$NON-NLS-1$
				PHPRefactoringUIMessages.getString("RenameGlobalVariableWizard_inputPageDescription"), null, null); //$NON-NLS-1$
	}
}
