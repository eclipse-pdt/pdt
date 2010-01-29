/*******************************************************************************
 * Copyright (c) 2005, 2009 Zend Technologies.
 * All rights reserved. This program and the accompanying materials
 * are the copyright of Zend Technologies and is protected under
 * copyright laws of the United States.
 * You must not copy, adapt or redistribute this document for 
 * any use. 
 *
 *******************************************************************************/
package org.eclipse.php.internal.ui.editor.contentassist;

import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.dltk.ui.text.completion.ScriptContentAssistInvocationContext;
import org.eclipse.jface.text.templates.TemplateCompletionProcessor;
import org.eclipse.php.internal.ui.editor.templates.PhpCommentTemplateCompletionProcessor;

public class PHPCommentTemplateCompletionProposalComputer extends
		PHPCompletionProposalComputer {

	public PHPCommentTemplateCompletionProposalComputer() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected List computeScriptCompletionProposals(int offset,
			ScriptContentAssistInvocationContext context,
			IProgressMonitor monitor) {

		return Collections.EMPTY_LIST;

	}

	protected TemplateCompletionProcessor createTemplateProposalComputer(
			ScriptContentAssistInvocationContext context) {
		context.getCoreContext();
		return new PhpCommentTemplateCompletionProcessor(context);
	}
}
