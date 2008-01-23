package org.eclipse.php.internal.core;

import org.eclipse.dltk.core.AbstractLanguageToolkit;
import org.eclipse.php.internal.core.project.PHPNature;

public class PHPLanguageToolkit extends AbstractLanguageToolkit {

	protected String getCorePluginID() {
		return PHPCorePlugin.ID;
	}

	public String[] getLanguageFileExtensions() {
		return new String[] {"php"};
	}

	public String getLanguageName() {
		return "PHP";
	}

	public String getNatureId() {
		return PHPNature.ID;
	}

}
