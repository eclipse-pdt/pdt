package org.eclipse.php.internal.ui.outline;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.wst.html.ui.internal.contentoutline.HTMLNodeActionManager;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;

public class PHPNodeActionManager extends HTMLNodeActionManager {

	public PHPNodeActionManager(IStructuredModel model, Viewer viewer) {
		super(model, viewer);
	}

	public void fillContextMenu(IMenuManager menuManager, ISelection selection) {
		// XXX: Contribute relevant actions here:
	}
}
