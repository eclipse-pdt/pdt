/*******************************************************************************
 * Copyright (c) 2014 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.core.libfolders;

import org.eclipse.core.resources.IProject;

/**
 * Provides common names for library folders according to some convention of a
 * framework or a tool, e.g. "vendor" is a common name for a library folder when
 * using Composer. These names are used when automatically detecting library
 * folders for PHP projects.
 * 
 * <p>
 * This interface is associated with a library folder name provider extension.
 * </p>
 * 
 * <p>
 * The following is an example of a library folder name provider extension:
 * 
 * <pre>
 * &lt;extension point=&quot;org.eclipse.php.core.libraryFolderNameProviders&quot;&gt;
 *    &lt;provider
 *       class=&quot;com.example.ExampleLibraryFolderNameProvider&quot;&gt;
 *    &lt;/provider&gt;
 * &lt;/extension&gt;
 * </pre>
 * 
 * </p>
 * 
 * <p>
 * In the example above, the specified library folder name provider will be
 * consulted for common names for library folders.
 * </p>
 * 
 * <p>
 * Clients contributing library folder name providers must implement this
 * interface.
 * </p>
 * 
 * @author Kaloyan Raev
 * @since 3.3
 */
public interface ILibraryFolderNameProvider {

	/**
	 * Returns the common names for library folders which applicable for the
	 * given project.
	 * 
	 * @param project
	 *            a project
	 * 
	 * @return an array of folder names
	 */
	public String[] getLibraryFolderNames(IProject project);

}
