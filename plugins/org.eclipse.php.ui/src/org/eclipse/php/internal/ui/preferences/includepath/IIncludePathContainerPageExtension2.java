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

import org.eclipse.php.internal.core.project.IIncludePathEntry;

/**
 * IncludePath container pages that implement {@link IIncludePathContainerPage} can 
 * optionally implement {@link IIncludePathContainerPageExtension2} to return more
 * than one element when creating new containers. If implemented, the method {@link #getNewContainers()}
 * is used instead of the method {@link IIncludePathContainerPage#getSelection() } to get the
 * newly selected containers. {@link IIncludePathContainerPage#getSelection() } is still used
 * for edited elements.
 *
 * @since 3.0
 */
public interface IIncludePathContainerPageExtension2 {

	/**
	 * Method {@link #getNewContainers()} is called instead of {@link IIncludePathContainerPage#getSelection() }
	 * to get the the newly added containers. {@link IIncludePathContainerPage#getSelection() } is still used
	 * to get the edited elements.
	 * @return the include path entries created on the page. All returned entries must be on kind
	 * {@link IIncludePathEntry#CPE_CONTAINER}
	 */
	public IIncludePathEntry[] getNewContainers();

}
