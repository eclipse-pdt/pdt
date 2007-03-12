package org.eclipse.php.internal.ui.actions;

import org.eclipse.jface.viewers.IStructuredSelection;

public interface IPHPActionDelegator {
	
	void init();
	void setSelection(IStructuredSelection selection);
	void run(IStructuredSelection selection); 	
}
