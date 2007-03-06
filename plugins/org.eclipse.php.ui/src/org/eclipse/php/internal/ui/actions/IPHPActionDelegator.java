package org.eclipse.php.internal.ui.actions;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;

public interface IPHPActionDelegator {
	
	void init();
	void setSelection(StructuredSelection selection);
	void run(IStructuredSelection selection); 	
}
