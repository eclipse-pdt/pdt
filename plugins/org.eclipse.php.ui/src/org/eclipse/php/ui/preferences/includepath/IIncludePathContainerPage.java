/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.ui.preferences.includepath;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.php.core.project.IIncludePathEntry;

public interface IIncludePathContainerPage extends IWizardPage {

	/**
	 * Called when the include path container wizard is closed by selecting 
	 * the finish button. Implementers typically override this method to 
	 * store the page result (new/changed include path entry returned in 
	 * getSelection) into its model.
	 * 
	 * @return if the operation was successful. Only when returned
	 * <code>true</code>, the wizard will close.
	 */
	public boolean finish();

	/**
	 * Returns the edited or created include path container entry. This method
	 * may return <code>null</code> if no include path container entry exists.
	 * The returned include path entry is of kind <code>IIncludePathEntry.CPE_CONTAINER
	 * </code>.
	 * 
	 * @return the include path entry edited or created on the page.
	 */
	public IIncludePathEntry getSelection();

	/**
	 * Sets the include path container entry to be edited or <code>null</code> 
	 * if a new entry should be created.
	 * 
	 * @param containerEntry the include path entry to edit or <code>null</code>.
	 * If not <code>null</code> then the include path entry must be of
	 * kind <code>IIncludePathEntry.CPE_CONTAINER</code>
	 */
	public void setSelection(IIncludePathEntry containerEntry);

}
