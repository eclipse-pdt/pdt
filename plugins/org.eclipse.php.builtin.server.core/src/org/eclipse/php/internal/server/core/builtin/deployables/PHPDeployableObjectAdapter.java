/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software, Inc. and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Kaloyan Raev - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.server.core.builtin.deployables;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.php.internal.server.core.builtin.Trace;
import org.eclipse.wst.server.core.IModuleArtifact;
import org.eclipse.wst.server.core.model.ModuleArtifactAdapterDelegate;

public class PHPDeployableObjectAdapter extends ModuleArtifactAdapterDelegate {

	public PHPDeployableObjectAdapter() {
		super();
	}

	@Override
	public IModuleArtifact getModuleArtifact(Object obj) {
		try {
			return PHPDeployableObjectAdapterUtil.getModuleObject(obj);
		} catch (CoreException e) {
			Trace.trace(Trace.SEVERE, "Could not get module object", e); //$NON-NLS-1$
			return null;
		}
	}
}
