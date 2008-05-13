package org.eclipse.php.internal.ui;

import org.eclipse.dltk.core.IDLTKLanguageToolkit;
import org.eclipse.dltk.ui.AbstractDLTKUILanguageToolkit;
import org.eclipse.php.internal.core.PHPLanguageToolkit;
import org.eclipse.ui.plugin.AbstractUIPlugin;

/**
 * An implementation of IDLTKUILanguageToolkit for PHP
 */
public class PHPUILanguageToolkit extends AbstractDLTKUILanguageToolkit {
	protected AbstractUIPlugin getUIPLugin() {
		return PHPUiPlugin.getDefault();
	}

	public IDLTKLanguageToolkit getCoreToolkit() {
		return PHPLanguageToolkit.getDefault();
	}
}