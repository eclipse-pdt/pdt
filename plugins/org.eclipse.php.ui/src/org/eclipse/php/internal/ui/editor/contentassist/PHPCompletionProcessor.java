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
	private boolean explicit;

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
	
	public void setExplicitRequest(boolean explicit) {
		this.explicit = explicit;
	}
}
