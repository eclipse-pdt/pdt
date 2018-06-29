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
package org.eclipse.php.internal.debug.core.sourcelookup.containers;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.debug.core.sourcelookup.ISourceContainerType;
import org.eclipse.debug.core.sourcelookup.containers.ContainerSourceContainer;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;

public class WorkspaceRootSourceContainer extends ContainerSourceContainer {

	public static final String TYPE_ID = PHPDebugPlugin.getID() + ".containerType.workspaceRoot"; //$NON-NLS-1$

	public WorkspaceRootSourceContainer() {
		super(ResourcesPlugin.getWorkspace().getRoot(), false);
	}

	@Override
	public ISourceContainerType getType() {
		return getSourceContainerType(TYPE_ID);
	}
}
