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

import org.eclipse.ltk.core.refactoring.participants.ProcessorBasedRefactoring;
import org.eclipse.ltk.ui.refactoring.RefactoringWizard;

/**
 * @author seva
 */
public class OrganizeIncludesWizard extends RefactoringWizard {

	public OrganizeIncludesWizard(ProcessorBasedRefactoring refactoring) {
		super(refactoring, DIALOG_BASED_USER_INTERFACE
				| PREVIEW_EXPAND_FIRST_NODE);
		setDefaultPageTitle("Organize Includes"); //$NON-NLS-1$
		// setForcePreviewReview(true);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ltk.ui.refactoring.RefactoringWizard#addUserInputPages()
	 */
	protected void addUserInputPages() {
		// addPage(new OrganizeIncludesWizardInitializationPage());
	}

}
