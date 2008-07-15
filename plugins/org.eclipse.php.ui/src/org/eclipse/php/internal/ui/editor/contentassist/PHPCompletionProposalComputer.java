/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.editor.contentassist;

import org.eclipse.dltk.ui.text.completion.ContentAssistInvocationContext;
import org.eclipse.dltk.ui.text.completion.ScriptCompletionProposalCollector;
import org.eclipse.dltk.ui.text.completion.ScriptCompletionProposalComputer;
import org.eclipse.dltk.ui.text.completion.ScriptContentAssistInvocationContext;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.templates.TemplateCompletionProcessor;
import org.eclipse.php.internal.ui.editor.templates.PhpTemplateCompletionProcessor;

public class PHPCompletionProposalComputer extends ScriptCompletionProposalComputer {

	public PHPCompletionProposalComputer() {
	}

	protected TemplateCompletionProcessor createTemplateProposalComputer(ScriptContentAssistInvocationContext context) {
		return new PhpTemplateCompletionProcessor(context);
	}

	protected ScriptCompletionProposalCollector createCollector(ScriptContentAssistInvocationContext context) {
		
		boolean explicit = false;
		if (context instanceof PHPContentAssistInvocationContext) {
			explicit = ((PHPContentAssistInvocationContext)context).isExplicit();
		}
		
		return new PHPCompletionProposalCollector(context.getDocument(), context.getSourceModule(), explicit);
	}

	protected int guessContextInformationPosition(ContentAssistInvocationContext context) {
		
		IDocument document = context.getDocument();
		int offset = context.getInvocationOffset();
		try {
			for (; offset > 0; --offset) {
				if (document.getChar(offset) == '(') {
					return offset;
				}
			}
		} catch (BadLocationException e) {
		}
		
		return super.guessContextInformationPosition(context);
	}
}
