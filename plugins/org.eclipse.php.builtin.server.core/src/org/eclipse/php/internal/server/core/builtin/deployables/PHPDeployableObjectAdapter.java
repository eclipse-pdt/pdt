/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software, Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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
