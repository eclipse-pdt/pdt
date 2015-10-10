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

/**
 * Implementors of this interface are responsible for finding a best match for a
 * remote file to local file mapping with the use of the provided set of
 * multiple possible results that were found by local file search engine.
 * 
 * @author Bartlomiej Laczkowski
 */
public interface ILocalFileSearchFilter {

	/**
	 * Filters the given list of multiple path entry results provided by local
	 * file search engine.
	 * 
	 * @param entries
	 *            Set of path entry matches that are possible equivalents of
	 *            local files for provided remote path
	 * @param remotePath
	 *            Remote file path
	 * @param serverUniqueId
	 *            Related server configuration unique ID
	 * @return best match search result
	 */
	public LocalFileSearchResult filter(final PathEntry[] entries, final VirtualPath remotePath,
			final String serverUniqueId);

}
