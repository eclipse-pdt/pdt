package org.eclipse.php.ui.editor.contentassist;

import org.eclipse.jface.text.contentassist.IContentAssistProcessor;

public interface IContentAssistProcessorForPHP extends IContentAssistProcessor {
	
	public void setAutoActivationRequest(boolean b);
}
