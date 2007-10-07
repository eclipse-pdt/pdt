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

import java.io.IOException;
import java.util.ArrayList;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationExtension;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.core.documentModel.DOMModelForPHP;
import org.eclipse.php.internal.ui.text.PHPCodeReader;
import org.eclipse.php.ui.editor.contentassist.IContentAssistProcessorForPHP;
import org.eclipse.php.ui.editor.contentassist.IContentAssistSupport;
import org.eclipse.swt.graphics.Image;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.provisional.IModelManager;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;

public class PHPContentAssistProcessor implements IContentAssistProcessorForPHP {

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
				completionProposals = support.getCompletionOption(viewer, phpDOMModel, offset, isExplicitRequest);
			} catch (Exception e) {
				Logger.logException(e);
				return new ICompletionProposal[0];
			}
			if (completionProposals == null) {
				completionProposals = new ICompletionProposal[0];
			}
		} finally {
			structuredModel.releaseFromRead();
			isExplicitRequest = false;
		}
		return completionProposals;
	}

	public IContextInformation[] computeContextInformation(ITextViewer viewer, int offset) {
		int contextInformationPosition = guessContextInformationPosition(viewer, offset);
		if (contextInformationPosition == -1) {
			return null;
		}
		ICompletionProposal[] proposals = computeCompletionProposals(viewer, contextInformationPosition);
		ArrayList contextInfo = new ArrayList();

		//the following for loop is fix for bug #200119
		//if we're getting more than one proposals - then we show show only the ones
		//with the shortest name. (the only case it can happen is inside C'tor 
		//when there are two classes and one name includes (actually starts with)the other  )
		int shortestName = Integer.MAX_VALUE;
		for (int i = 0; i < proposals.length; ++i) {
			if (proposals[i].getContextInformation() != null) {
				int nameLength = proposals[i].getDisplayString().length();
				if (nameLength < shortestName) {
					shortestName = nameLength;
				}
			}
		}
		for (int i = 0; i < proposals.length; ++i) {
			IContextInformation info = proposals[i].getContextInformation();
			if (info != null && proposals[i].getDisplayString().length() == shortestName) {
				ContextInformationWrapper contextInformation = new ContextInformationWrapper(info);
				contextInformation.setContextInformationPosition(contextInformationPosition + 1); 
				//the +1 is because guessContextInformationPosition() 
				//returns the position of the '(' and the ContextInformationPosition needs the position after it.
				contextInfo.add(contextInformation);
			}
		}
		return (IContextInformation[]) contextInfo.toArray(new IContextInformation[contextInfo.size()]);
	}

	/**
	 * This class purpose is to support {@link}IContextInformationExtension 
	 * It suppose to resolve bug https://bugs.eclipse.org/bugs/show_bug.cgi?id=204653 after wst will do their part in it.
	 * @author guy.g
	 *
	 */
	private static final class ContextInformationWrapper implements IContextInformation, IContextInformationExtension {

		private final IContextInformation fContextInformation;
		private int fPosition;

		public ContextInformationWrapper(IContextInformation contextInformation) {
			fContextInformation = contextInformation;
		}

		/*
		 * @see IContextInformation#getContextDisplayString()
		 */
		public String getContextDisplayString() {
			return fContextInformation.getContextDisplayString();
		}

		/*
		* @see IContextInformation#getImage()
		*/
		public Image getImage() {
			return fContextInformation.getImage();
		}

		/*
		 * @see IContextInformation#getInformationDisplayString()
		 */
		public String getInformationDisplayString() {
			return fContextInformation.getInformationDisplayString();
		}

		/*
		 * @see IContextInformationExtension#getContextInformationPosition()
		 */
		public int getContextInformationPosition() {
			return fPosition;
		}

		public void setContextInformationPosition(int position) {
			fPosition = position;
		}

		/*
		 * @see org.eclipse.jface.text.contentassist.IContextInformation#equals(java.lang.Object)
		 */
		public boolean equals(Object object) {
			if (object instanceof ContextInformationWrapper)
				return fContextInformation.equals(((ContextInformationWrapper) object).fContextInformation);
			else
				return fContextInformation.equals(object);
		}
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

	public final IContextInformationValidator getContextInformationValidator() {
		if (this.getClass().equals(PHPContentAssistProcessor.class)) {
			return contextInformationValidator;
		}
		return null;
	}

	private int guessContextInformationPosition(ITextViewer viewer, int offset) {
		IDocument document = viewer.getDocument();
		try {
			PHPCodeReader reader = new PHPCodeReader();
			reader.configureBackwardReader(document, offset, true, true);

			int nestingLevel = 0;

			int curr = reader.read();
			while (curr != PHPCodeReader.EOF) {

				if (')' == (char) curr)
					++nestingLevel;

				else if ('(' == (char) curr) {
					--nestingLevel;

					if (nestingLevel < 0) {
						// int start= reader.getOffset();
						if (looksLikeMethod(reader)) {
							// return start + 1;
							return reader.getOffset() + 1;
						} else {
							return -1;
						}
					}
				}

				curr = reader.read();
			}
		} catch (IOException e) {
		}
		return -1;
	}

	private boolean looksLikeMethod(PHPCodeReader reader) throws IOException {
		int curr = reader.read();
		while (curr != PHPCodeReader.EOF && Character.isWhitespace((char) curr))
			curr = reader.read();

		if (curr == PHPCodeReader.EOF)
			return false;

		return Character.isJavaIdentifierPart((char) curr) || Character.isJavaIdentifierStart((char) curr);
	}

	/**
	 * The protocol here is that we know when it is an implicit request - since we ask for it in {@link PHPContentAssistant}
	 * The explicit request comes fromn the editor and we don't control it.
	 * 
	 *  so we set it as implicit when we are asked for by PHPContentAssistant and unset it after the first request. 
	 */
	private boolean isExplicitRequest = false;

	public void explicitActivationRequest() {
		isExplicitRequest = true;
	}
}
