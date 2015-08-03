/*******************************************************************************
 * Copyright (c) 2015 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.core.pathmapper;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

/**
 * Local file search result.
 * 
 * @author Bartlomiej Laczkowski
 */
public class LocalFileSearchResult {

	private final PathEntry entry;
	private IStatus status = Status.OK_STATUS;

	/**
	 * Creates new local file search result with the use of provided entry.
	 * 
	 * @param entry
	 */
	public LocalFileSearchResult(PathEntry entry) {
		this.entry = entry;
	}

	/**
	 * Creates new local file search result with the use of provided entry and
	 * status of performed search process.
	 * 
	 * @param entry
	 * @param status
	 */
	public LocalFileSearchResult(PathEntry entry, IStatus status) {
		this.entry = entry;
		this.status = status;
	}

	/**
	 * Returns result path entry.
	 * 
	 * @return result path entry
	 */
	public final PathEntry getPathEntry() {
		return entry;
	}

	/**
	 * Returns status of performed search.
	 * 
	 * @return status of performed search
	 */
	public final IStatus getStatus() {
		return status;
	}

}