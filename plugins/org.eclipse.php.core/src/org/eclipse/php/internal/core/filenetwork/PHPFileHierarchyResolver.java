/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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

	public IFileHierarchyInfo resolveDown(ISourceModule file,
			IProgressMonitor monitor) {
		return FileNetworkUtility.buildReferencingFilesTree(file, monitor);
	}

	public IFileHierarchyInfo resolveUp(ISourceModule file,
			IProgressMonitor monitor) {
		return FileNetworkUtility.buildReferencedFilesTree(file, monitor);
	}

}
