package org.eclipse.php.internal.ui.actions;

import org.eclipse.dltk.ui.IDLTKUILanguageToolkit;
import org.eclipse.php.internal.ui.PHPUILanguageToolkit;

/**
 * Open type in hierarchy action. 
 * 
 * @author nirc
 */
public class OpenTypeAction extends org.eclipse.dltk.ui.actions.OpenTypeAction {

	/* (non-Javadoc)
	 * @see org.eclipse.dltk.ui.actions.OpenTypeInHierarchyAction#getLanguageToolkit()
	 */

	@Override
	protected IDLTKUILanguageToolkit getUILanguageToolkit() {
		return new PHPUILanguageToolkit();
	}
}
