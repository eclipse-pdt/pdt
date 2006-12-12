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
package org.eclipse.php.ui.editor.contentassist;

import java.io.IOException;
import java.util.ArrayList;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;
import org.eclipse.php.core.Logger;
import org.eclipse.php.core.documentModel.DOMModelForPHP;
import org.eclipse.php.internal.ui.text.PHPCodeReader;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.provisional.IModelManager;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;

public class PHPContentAssistProcessor implements IContentAssistProcessor {

	// This is the resource that it's being edited in the Editor
	protected IContentAssistSupport support = new ContentAssistSupport();
	protected PHPContextInformationValidator contextInformationValidator = new PHPContextInformationValidator();
	protected static final char[] contextInformationActivationChars = { '(', ',' };

	public ICompletionProposal[] computeCompletionProposals(ITextViewer viewer, int offset) {
		// System.out.println("computeCompletionProposals: " + offset);

		IModelManager modelManager = StructuredModelManager.getModelManager();
		if (modelManager == null) {
			return new ICompletionProposal[0];
		}

		IStructuredModel structuredModel = null;
		structuredModel = modelManager.getExistingModelForRead(viewer.getDocument());
		if (structuredModel == null) {
			return new ICompletionProposal[0];
		}
		ICompletionProposal[] completionProposals;
		try {
			DOMModelForPHP phpDOMModel = (DOMModelForPHP) structuredModel;
			try {
				completionProposals = support.getCompletionOption(viewer, phpDOMModel, offset);
			} catch (Exception e) {
				Logger.logException(e);
				return new ICompletionProposal[0];
			}
			if (completionProposals == null) {
				completionProposals = new ICompletionProposal[0];
			}
		} finally {
			structuredModel.releaseFromRead();
		}
		return completionProposals;
	}

	public IContextInformation[] computeContextInformation(ITextViewer viewer, int offset) {
		int contextInformationPosition= guessContextInformationPosition(viewer, offset);
		if (contextInformationPosition == -1) {
			return null;
		}
		ICompletionProposal[] proposals = computeCompletionProposals(viewer, contextInformationPosition);
		ArrayList contextInfo = new ArrayList();
		for (int i=0; i<proposals.length; ++i) {
			IContextInformation info = proposals[i].getContextInformation();
			if (info != null) {
				contextInfo.add(info);
			}
		}
		return (IContextInformation[]) contextInfo.toArray(new IContextInformation[contextInfo.size()]);
	}

	public char[] getCompletionProposalAutoActivationCharacters() {
		return support.getAutoactivationTriggers();
	}

	public char[] getContextInformationAutoActivationCharacters() {
		return contextInformationActivationChars;
	}

	public String getErrorMessage() {
		// System.out.println("getErrorMessage");
		// TODO Auto-generated method stub
		return null;
	}

	public IContextInformationValidator getContextInformationValidator() {
		return contextInformationValidator;
	}
	
	private int guessContextInformationPosition(ITextViewer viewer, int offset) {
		int contextPosition= -1;
		IDocument document= viewer.getDocument();
		try {
			PHPCodeReader reader= new PHPCodeReader();
			reader.configureBackwardReader(document, offset, true, true);

			int nestingLevel= 0;

			int curr= reader.read();
			while (curr != PHPCodeReader.EOF) {

				if (')' == (char) curr)
					++ nestingLevel;

				else if ('(' == (char) curr) {
					-- nestingLevel;

					if (nestingLevel < 0) {
						// int start= reader.getOffset();
						if (looksLikeMethod(reader)) {
							// return start + 1;
							return reader.getOffset()+1;
						} else {
							return -1;
						}
					}
				}

				curr= reader.read();
			}
		} catch (IOException e) {
		}
		return contextPosition;
	}
	
	private boolean looksLikeMethod(PHPCodeReader reader) throws IOException {
		int curr= reader.read();
		while (curr != PHPCodeReader.EOF && Character.isWhitespace((char) curr))
			curr= reader.read();

		if (curr == PHPCodeReader.EOF)
			return false;

		return Character.isJavaIdentifierPart((char) curr) || Character.isJavaIdentifierStart((char) curr);
	}
}
