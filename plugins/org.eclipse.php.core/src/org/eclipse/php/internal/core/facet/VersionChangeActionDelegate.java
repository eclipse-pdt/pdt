/*******************************************************************************
 * Copyright (c) 2012 IBM Corporation and others.
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
 *     Martin Eisengardt <martin.eisengardt@fiducia.de>
 *******************************************************************************/
package org.eclipse.php.internal.core.facet;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.wst.common.project.facet.core.IDelegate;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;

public class VersionChangeActionDelegate implements IDelegate {

	@Override
	public void execute(IProject arg0, IProjectFacetVersion arg1, Object arg2, IProgressMonitor arg3)
			throws CoreException {
		// sync php version of the project
		// check if there is any change to prevent endless loops
		// (php options upgrade will invoke a version change and lead to this)
		// TODO
	}

}
