/*******************************************************************************
 * Copyright (c) 2012, 2016 PDT Extension Group and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.core.model;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;

/**
 *
 */
public interface NamespaceResolverInterface {
	/**
	 * Resolve the namespace of a given resource.
	 * 
	 * Example input: IResource is a folder "/TestProject/lib/Acme/Demo"
	 * 
	 * The psr-0 autoload path in TestProject is "lib"
	 * 
	 * The return value will be an IPath with the segments "Acme/Demo".
	 * 
	 * @param the
	 *            resource to resolve
	 * @return the resolved namespace as an IPath
	 */
	IPath resolve(IResource resource);

	/**
	 * Resolve the source folder for given Namespace in a project
	 * 
	 * @param namespace
	 *            The Namespace to be resolved
	 * @return {@link IPath} | null
	 */
	IPath reverseResolve(IProject project, String namespace);
}
