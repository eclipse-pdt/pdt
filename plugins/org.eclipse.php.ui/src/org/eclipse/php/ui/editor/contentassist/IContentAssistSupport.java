package org.eclipse.php.ui.editor.contentassist;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.php.core.documentModel.PHPEditorModel;

public interface IContentAssistSupport {

	public ICompletionProposal[] getCompletionOption(ITextViewer viewer, PHPEditorModel phpEditorModel, int offset) throws BadLocationException;

	public char[] getAutoactivationTriggers();

}