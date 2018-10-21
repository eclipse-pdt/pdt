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
package org.eclipse.php.composer.core.resources;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.php.composer.api.ComposerPackage;
import org.eclipse.php.composer.api.collection.ComposerPackages;

public interface IComposerProject {

	public String getVendorDir();

	/**
	 * Returns the absolute path to the vendor directory
	 * 
	 * @return
	 */
	public IPath getVendorPath();

	public IFile getComposerJson();

	public ComposerPackage getComposerPackage();

	public IProject getProject();

	public IScriptProject getScriptProject();

	public ComposerPackages getInstalledPackages();

	public boolean isValidComposerJson();

	public String getNamespace(IPath path);

	public IPath getNamespaceDir(IPath source, String namespace);

	/**
	 * Returns the full, absolute path of this resource relative to the
	 * workspace.
	 * 
	 * @return the path
	 */
	public IPath getFullPath();
}
