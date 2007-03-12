package org.eclipse.php.internal.ui.actions;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.viewers.IStructuredSelection;

public interface IPHPMoveActionDelegator extends IPHPActionDelegator {
		
	void runDrop(IStructuredSelection selection);
	void setSources(IResource[] resources);
	void setTarget(IContainer target);
}
