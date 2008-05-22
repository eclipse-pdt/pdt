package org.eclipse.php.internal.ui.editor.contentassist;

import org.eclipse.dltk.ui.text.completion.CompletionProposalLabelProvider;
import org.eclipse.dltk.ui.text.completion.ScriptCompletionProcessor;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;
import org.eclipse.php.internal.core.project.PHPNature;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.ui.IEditorPart;

public class PHPCompletionProcessor extends ScriptCompletionProcessor {
	
	protected PHPContextInformationValidator contextInformationValidator = new PHPContextInformationValidator();
	protected static final char[] contextInformationActivationChars = { '(', ',' };

	public PHPCompletionProcessor(IEditorPart editor, ContentAssistant assistant, String partition) {
		super(editor, assistant, partition);
	}

	protected String getNatureId() {
		return PHPNature.ID;
	}

	protected CompletionProposalLabelProvider getProposalLabelProvider() {
		return new PHPCompletionProposalLabelProvider();
	}

	protected IPreferenceStore getPreferenceStore() {
		return PHPUiPlugin.getDefault().getPreferenceStore();
	}
	
	public IContextInformationValidator getContextInformationValidator() {
		return contextInformationValidator;
	}
	
	public char[] getContextInformationAutoActivationCharacters() {
		return contextInformationActivationChars;
	}
}
