/**
 * 
 */
package org.eclipse.php.internal.ui.actions;

import org.eclipse.dltk.ui.IDLTKUILanguageToolkit;
import org.eclipse.php.internal.ui.PHPUILanguageToolkit;

/**
 * Open type in hierarchy action. 
 * 
 * @author shalom
 */
public class OpenTypeInHierarchyAction extends org.eclipse.dltk.ui.actions.OpenTypeInHierarchyAction {

	/* (non-Javadoc)
	 * @see org.eclipse.dltk.ui.actions.OpenTypeInHierarchyAction#getLanguageToolkit()
	 */
	protected IDLTKUILanguageToolkit getLanguageToolkit() {
		return new PHPUILanguageToolkit();
	}
}
