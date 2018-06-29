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
package org.eclipse.php.refactoring.ui.wizard;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.dltk.internal.ui.refactoring.RefactoringMessages;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.ui.refactoring.RefactoringWizard;
import org.eclipse.php.refactoring.core.extract.function.ExtractFunctionRefactoring;
import org.eclipse.php.refactoring.ui.RefactoringUIPlugin;

public class ExtractFunctionWizard extends RefactoringWizard {

	/* package */static final String DIALOG_SETTING_SECTION = "ExtractMethodWizard"; //$NON-NLS-1$

	public ExtractFunctionWizard(ExtractFunctionRefactoring ref) {
		super(ref, DIALOG_BASED_USER_INTERFACE | PREVIEW_EXPAND_FIRST_NODE);
		setDefaultPageTitle(RefactoringMessages.ExtractMethodWizard_extract_method);
		setDialogSettings(RefactoringUIPlugin.getDefault().getDialogSettings());
	}

	public Change createChange() {
		// creating the change is cheap. So we don't need to show progress.
		try {
			return getRefactoring().createChange(new NullProgressMonitor());
		} catch (CoreException e) {
			RefactoringUIPlugin.log(e);
			return null;
		}
	}

	@Override
	protected void addUserInputPages() {
		addPage(new ExtractFunctionInputPage());
	}
}
