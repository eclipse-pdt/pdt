/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.ui.views.coverage;

import org.eclipse.php.internal.debug.core.pathmapper.VirtualPath;

/**
 * Filter for the Code Coverage view.
 */
public interface ICodeCoverageFilter {

	/**
	 * Returns whether to filter the given path from the representation in a Code
	 * Coverage view
	 * 
	 * @param path
	 *            File path
	 * @return
	 */
	public boolean isFiltered(VirtualPath path);
}
