/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.core.filenetwork;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.dltk.core.IFileHierarchyInfo;
import org.eclipse.dltk.core.IFileHierarchyResolver;
import org.eclipse.dltk.core.ISourceModule;

public class PHPFileHierarchyResolver implements IFileHierarchyResolver {

	@Override
	public IFileHierarchyInfo resolveDown(ISourceModule file, IProgressMonitor monitor) {
		return FileNetworkUtility.buildReferencingFilesTree(file, monitor);
	}

	@Override
	public IFileHierarchyInfo resolveUp(ISourceModule file, IProgressMonitor monitor) {
		return FileNetworkUtility.buildReferencedFilesTree(file, monitor);
	}

}
