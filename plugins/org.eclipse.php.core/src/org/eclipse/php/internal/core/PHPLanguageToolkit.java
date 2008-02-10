package org.eclipse.php.internal.core;

import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.dltk.core.AbstractLanguageToolkit;
import org.eclipse.dltk.core.IDLTKLanguageToolkit;
import org.eclipse.php.internal.core.documentModel.provisional.contenttype.ContentTypeIdForPHP;
import org.eclipse.php.internal.core.project.PHPNature;

public class PHPLanguageToolkit extends AbstractLanguageToolkit {

	private static PHPLanguageToolkit toolkit = new PHPLanguageToolkit();

	protected String getCorePluginID() {
		return PHPCorePlugin.ID;
	}

	public String[] getLanguageFileExtensions() {
		IContentType type = Platform.getContentTypeManager().getContentType(ContentTypeIdForPHP.ContentTypeID_PHP);
		return type.getFileSpecs(IContentType.FILE_EXTENSION_SPEC);
	}

	public String getLanguageName() {
		return "PHP";
	}

	public String getNatureId() {
		return PHPNature.ID;
	}

	public String getLanguageContentType() {
		return ContentTypeIdForPHP.ContentTypeID_PHP;
	}

	public static IDLTKLanguageToolkit getDefault() {
		return toolkit;
	}
}
