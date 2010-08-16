/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.ui.editor.contentassist;

import org.eclipse.dltk.ui.text.completion.ContentAssistInvocationContext;
import org.eclipse.dltk.ui.text.completion.ScriptCompletionProcessor;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;
import org.eclipse.php.internal.core.project.PHPNature;
import org.eclipse.ui.IEditorPart;

public class PHPCompletionProcessor extends ScriptCompletionProcessor {

	protected PHPContextInformationValidator contextInformationValidator = new PHPContextInformationValidator();
	protected static final char[] contextInformationActivationChars = { '(',
			',' };
	protected static final char[] completionAutoActivationChars = { '>', ':' };
	private boolean explicit;
	private ContentAssistant assistant;

	public PHPCompletionProcessor(IEditorPart editor,
			ContentAssistant assistant, String partition) {
		super(editor, assistant, partition);
		this.assistant = assistant;
		setCompletionProposalAutoActivationCharacters(getAutoactivationTriggers());
	}

	protected String getNatureId() {
		return PHPNature.ID;
	}

	public IContextInformationValidator getContextInformationValidator() {
		return contextInformationValidator;
	}

	public char[] getContextInformationAutoActivationCharacters() {
		return contextInformationActivationChars;
	}

	protected static char[] getAutoactivationTriggers() {
		return completionAutoActivationChars;
	}

	protected ContentAssistInvocationContext createContext(ITextViewer viewer,
			int offset) {

		boolean oldExplicitValue = explicit;
		explicit = false;

		return new PHPContentAssistInvocationContext(viewer, offset, fEditor,
				getNatureId(), oldExplicitValue);
	}

	public void setExplicit(boolean explicit) {
		this.explicit = explicit;
	}

	public boolean isExplicit() {
		return explicit;
	}

	public ContentAssistant getAssistant() {
		return assistant;
	}
}
