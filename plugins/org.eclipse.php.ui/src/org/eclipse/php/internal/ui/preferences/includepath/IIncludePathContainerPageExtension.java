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
package org.eclipse.php.internal.ui.preferences.includepath;

import org.eclipse.core.resources.IProject;
import org.eclipse.php.internal.core.project.IIncludePathEntry;

/**
 * IncludePath container pages that implement <code>IIncludePathContainerPage</code> can 
 * optionally implement <code>IIncludePathContainerPageExtension</code> to get additional
 * information about the context when the page is opened. Method <code>initialize()</code>
 * is called before  <code>IIncludePathContainerPage.setSelection</code>.
 *
 * @since 2.1
 */
public interface IIncludePathContainerPageExtension {

	/**
	 * Method <code>initialize()</code> is called before  <code>IIncludePathContainerPage.setSelection</code>
	 * to give additional information about the context the include path container entry is configured in. This information
	 * only reflects the underlying dialogs current selection state. The user still can make changes after the
	 * the include path container pages has been closed or decide to cancel the operation.
	 * @param project The project the new or modified entry is added to. The project does not have to exist. 
	 * Project can be <code>null</code>.
	 * @param currentEntries The include path entries currently selected to be set as the projects include path. This can also
	 * include the entry to be edited.
	 */
	public void initialize(IProject project, IIncludePathEntry[] currentEntries);

}
