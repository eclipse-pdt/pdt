package org.eclipse.php.ui.editor;

import org.eclipse.osgi.util.NLS;
import org.eclipse.php.ui.editor.hover.IHoverMessageDecorators;

public class DefaultHoverMessageDecorator implements IHoverMessageDecorators {

	public String getDecoratedMessage(String msg) {
		
		String hoverMessage = PHPEditorMessages.HoverFocus_decoration;
		if (hoverMessage.indexOf("{0}") != -1) {
			hoverMessage = NLS.bind(hoverMessage, msg);
		}

		if (hoverMessage != null) {
			return hoverMessage;
		} else {
			return msg;
		}
		
	}

}
