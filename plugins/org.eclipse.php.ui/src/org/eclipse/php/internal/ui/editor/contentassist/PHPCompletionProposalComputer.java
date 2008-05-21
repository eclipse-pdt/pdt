package org.eclipse.php.internal.ui.editor.contentassist;

import org.eclipse.dltk.ui.text.completion.ScriptCompletionProposalCollector;
import org.eclipse.dltk.ui.text.completion.ScriptCompletionProposalComputer;
import org.eclipse.dltk.ui.text.completion.ScriptContentAssistInvocationContext;
import org.eclipse.jface.text.templates.TemplateCompletionProcessor;
import org.eclipse.php.internal.ui.editor.templates.PHPTemplateCompletionProcessor;

public class PHPCompletionProposalComputer extends ScriptCompletionProposalComputer {

	public PHPCompletionProposalComputer() {
	}

	protected TemplateCompletionProcessor createTemplateProposalComputer(ScriptContentAssistInvocationContext context) {
		return new PHPTemplateCompletionProcessor();
	}

	protected ScriptCompletionProposalCollector createCollector(ScriptContentAssistInvocationContext context) {
		return new PHPCompletionProposalCollector(context.getSourceModule());
	}
}
