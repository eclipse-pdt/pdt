package org.eclipse.php.internal.ui.editor.hover;

import org.eclipse.dltk.internal.ui.text.hover.DocumentationHover;
import org.eclipse.php.ui.editor.hover.IHoverMessageDecorator;
import org.eclipse.php.ui.editor.hover.IPHPTextHover;

public class PHPDocumentationHover extends DocumentationHover implements IPHPTextHover {

	public IHoverMessageDecorator getMessageDecorator() {
		return null;
	}
}
