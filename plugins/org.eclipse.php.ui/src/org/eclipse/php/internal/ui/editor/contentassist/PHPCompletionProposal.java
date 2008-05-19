package org.eclipse.php.internal.ui.editor.contentassist;

import org.eclipse.dltk.ui.text.completion.ScriptCompletionProposal;
import org.eclipse.swt.graphics.Image;

public class PHPCompletionProposal extends ScriptCompletionProposal {

	public PHPCompletionProposal(String replacementString, int replacementOffset, int replacementLength, Image image, String displayString, int relevance) {
		super(replacementString, replacementOffset, replacementLength, image, displayString, relevance);
	}

	public PHPCompletionProposal(String replacementString, int replacementOffset, int replacementLength, Image image, String displayString, int relevance, boolean indoc) {
		super(replacementString, replacementOffset, replacementLength, image, displayString, relevance, indoc);
	}

	protected boolean isSmartTrigger(char trigger) {
		return trigger == '$';
	}
}