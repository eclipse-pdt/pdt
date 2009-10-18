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
package org.eclipse.php.internal.debug.core.sourcelookup.containers;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.debug.core.sourcelookup.ISourceContainerType;
import org.eclipse.debug.core.sourcelookup.containers.ContainerSourceContainer;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;

public class WorkspaceRootSourceContainer extends ContainerSourceContainer {

	public static final String TYPE_ID = PHPDebugPlugin.getID()
			+ ".containerType.workspaceRoot"; //$NON-NLS-1$

	public WorkspaceRootSourceContainer() {
		super(ResourcesPlugin.getWorkspace().getRoot(), false);
	}

	public ISourceContainerType getType() {
		return getSourceContainerType(TYPE_ID);
	}
}
