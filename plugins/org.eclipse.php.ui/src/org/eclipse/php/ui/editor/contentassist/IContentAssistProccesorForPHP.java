package org.eclipse.php.ui.editor.contentassist;

import org.eclipse.jface.text.contentassist.IContentAssistProcessor;

public interface IContentAssistProccesorForPHP extends IContentAssistProcessor {

	public String[] getSupportedPartitionsTypes();
}
