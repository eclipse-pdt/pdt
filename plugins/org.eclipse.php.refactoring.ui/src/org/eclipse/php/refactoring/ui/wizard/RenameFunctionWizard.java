/*******************************************************************************
 * Copyright (c) 2007, 2015 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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
public class RenameFunctionWizard extends RenameRefactoringWizard {

	public RenameFunctionWizard(Refactoring refactoring) {
		super(
				refactoring,
				PHPRefactoringUIMessages
						.getString("RenameFunctionWizard_title"), PHPRefactoringUIMessages.getString("RenameGlobalVariableWizard_inputPageDescription"), null, null); //$NON-NLS-1$ //$NON-NLS-2$		
	}
}
