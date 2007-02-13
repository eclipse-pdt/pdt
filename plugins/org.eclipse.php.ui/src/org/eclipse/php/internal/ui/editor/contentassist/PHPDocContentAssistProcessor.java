/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.editor.contentassist;

import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.core.documentModel.DOMModelForPHP;
import org.eclipse.php.ui.editor.contentassist.IContentAssistProcessorForPHP;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.provisional.IModelManager;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;

public class PHPDocContentAssistProcessor implements IContentAssistProcessorForPHP {

	private PHPDocContentAssistSupport support = new PHPDocContentAssistSupport();

	public ICompletionProposal[] computeCompletionProposals(ITextViewer viewer, int offset) {
		IModelManager modelManager = StructuredModelManager.getModelManager();
		if (modelManager == null) {
			return null;
		}

		IStructuredModel structuredModel = null;
		structuredModel = modelManager.getExistingModelForRead(viewer.getDocument());
		if (structuredModel == null) {
			return null;
		}
		ICompletionProposal[] completionProposals;
		try {
			DOMModelForPHP phpDOMModel = (DOMModelForPHP) structuredModel;
			try {
				completionProposals = support.getCompletionOption(viewer, phpDOMModel, offset, isExplicitRequest);
			} catch (Exception e) {
				Logger.logException(e);
				return null;
			}
			if (completionProposals == null) {
				completionProposals = new ICompletionProposal[0];
			}
		} finally {
			structuredModel.releaseFromRead();
			isExplicitRequest = true;
		}
		return completionProposals;
	}

	public IContextInformation[] computeContextInformation(ITextViewer viewer, int offset) {
		return null;
	}

	public char[] getCompletionProposalAutoActivationCharacters() {
		return support.getAutoactivationTriggers();
	}

	public char[] getContextInformationAutoActivationCharacters() {
		return null;
	}

	public String getErrorMessage() {
		return null;
	}

	public IContextInformationValidator getContextInformationValidator() {
		return null;
	}

	private boolean isExplicitRequest = true;

	public void setAutoActivationRequest(boolean b) {
		//this is a bit confusing here but if this is an auto activation then it is not an explicit request.
		isExplicitRequest = !b;
	}

}
