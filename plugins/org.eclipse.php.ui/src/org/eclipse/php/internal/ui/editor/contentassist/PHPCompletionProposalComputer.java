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

import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.dltk.ui.PreferenceConstants;
import org.eclipse.dltk.ui.text.completion.*;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.templates.TemplateCompletionProcessor;
import org.eclipse.php.internal.ui.editor.templates.PhpTemplateCompletionProcessor;
import org.eclipse.swt.widgets.Shell;

public class PHPCompletionProposalComputer extends
		ScriptCompletionProposalComputer {

	private String fErrorMessage;
	private PHPCompletionProposalCollector phpCompletionProposalCollector;
	private PhpTemplateCompletionProcessor phpTemplateCompletionProcessor;

	protected TemplateCompletionProcessor createTemplateProposalComputer(
			ScriptContentAssistInvocationContext context) {
		boolean explicit = false;
		if (context instanceof PHPContentAssistInvocationContext) {
			explicit = ((PHPContentAssistInvocationContext) context)
					.isExplicit();
		}

		phpTemplateCompletionProcessor = new PhpTemplateCompletionProcessor(
				context, explicit);
		return phpTemplateCompletionProcessor;
	}

	protected ScriptCompletionProposalCollector createCollector(
			ScriptContentAssistInvocationContext context) {

		boolean explicit = false;
		if (context instanceof PHPContentAssistInvocationContext) {
			explicit = ((PHPContentAssistInvocationContext) context)
					.isExplicit();
		}

		phpCompletionProposalCollector = new PHPCompletionProposalCollector(
				context.getDocument(), context.getSourceModule(), explicit);
		return phpCompletionProposalCollector;
	}

	protected int guessContextInformationPosition(
			ContentAssistInvocationContext context) {

		IDocument document = context.getDocument();
		int offset = context.getInvocationOffset();
		int leftNumber = 0;
		// if the cursor is at the end of the document
		if (document.getLength() == offset) {
			offset--;
		}
		try {
			for (; offset > 0; --offset) {
				if (document.getChar(offset) == '(') {
					leftNumber++;
				} else if (document.getChar(offset - 1) == ')') {
					leftNumber--;
				}
				if (leftNumber == 1) {
					return offset;
				}
			}
		} catch (BadLocationException e) {
		}

		return super.guessContextInformationPosition(context);
	}

	private void handleCodeCompletionException(ModelException e,
			ScriptContentAssistInvocationContext context) {
		ISourceModule module = context.getSourceModule();
		Shell shell = context.getViewer().getTextWidget().getShell();
		if (e.isDoesNotExist()
				&& !module.getScriptProject().isOnBuildpath(module)) {
			IPreferenceStore store = DLTKUIPlugin.getDefault()
					.getPreferenceStore();
			boolean value = store
					.getBoolean(PreferenceConstants.NOTIFICATION_NOT_ON_BUILDPATH_MESSAGE);
			if (!value) {
				MessageDialog
						.openInformation(
								shell,
								ScriptTextMessages.CompletionProcessor_error_notOnBuildPath_title,
								ScriptTextMessages.CompletionProcessor_error_notOnBuildPath_message);
			}
			store.setValue(
					PreferenceConstants.NOTIFICATION_NOT_ON_BUILDPATH_MESSAGE,
					true);
		} else
			ErrorDialog
					.openError(
							shell,
							ScriptTextMessages.CompletionProcessor_error_accessing_title,
							ScriptTextMessages.CompletionProcessor_error_accessing_message,
							e.getStatus());
	}

	public String getErrorMessage() {
		return fErrorMessage;
	}

	public void sessionEnded() {
		super.sessionEnded();
		fErrorMessage = null;
		if (phpTemplateCompletionProcessor != null) {
			phpTemplateCompletionProcessor.reset();
		}
	}
}
