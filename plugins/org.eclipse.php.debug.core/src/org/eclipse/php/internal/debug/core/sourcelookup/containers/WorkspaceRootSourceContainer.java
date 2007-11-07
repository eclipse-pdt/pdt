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

	public ISourceContainerType getType() {
		return getSourceContainerType(TYPE_ID);
	}
}
