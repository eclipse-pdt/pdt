/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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
	 * Returns whether to filter the given path from the representation in a
	 * Code Coverage view
	 * 
	 * @param path
	 *            File path
	 * @return
	 */
	public boolean isFiltered(VirtualPath path);
}
