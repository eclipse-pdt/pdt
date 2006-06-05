/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.core.project.deployables;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.wst.server.core.IModuleArtifact;
import org.eclipse.wst.server.core.model.ModuleArtifactAdapterDelegate;

public class PHPDeployableObjectAdapter extends ModuleArtifactAdapterDelegate implements IAdapterFactory

{

	public PHPDeployableObjectAdapter() {
		super();
	}

	public Object getAdapter(Object adaptableObject, Class adapterType) {
		return null;
	}

	public Class[] getAdapterList() {
		return new Class[] { IPHPModuleArtifact.class };
	}

	public IModuleArtifact getModuleArtifact(Object obj) {
		return PHPDeployableObjectAdapterUtil.getModuleObject(obj);
	}
}