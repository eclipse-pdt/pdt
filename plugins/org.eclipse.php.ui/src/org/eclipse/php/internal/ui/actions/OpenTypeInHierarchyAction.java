/**
 * 
 */
package org.eclipse.php.internal.ui.actions;

import org.eclipse.dltk.core.IDLTKLanguageToolkit;
import org.eclipse.dltk.ui.AbstractDLTKUILanguageToolkit;
import org.eclipse.dltk.ui.IDLTKUILanguageToolkit;
import org.eclipse.php.internal.core.PHPLanguageToolkit;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.ui.plugin.AbstractUIPlugin;

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

	/**
	 * An implementation of IDLTKUILanguageToolkit for PHP
	 */
	static class PHPUILanguageToolkit extends AbstractDLTKUILanguageToolkit {
		protected AbstractUIPlugin getUIPLugin() {
			return PHPUiPlugin.getDefault();
		}

		public IDLTKLanguageToolkit getCoreToolkit() {
			return PHPLanguageToolkit.getDefault();
		}
	}
}
