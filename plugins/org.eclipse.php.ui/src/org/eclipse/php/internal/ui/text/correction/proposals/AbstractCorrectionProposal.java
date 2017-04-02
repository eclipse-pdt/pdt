/*******************************************************************************
 * Copyright (c) 2009, 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *     Dawid Pakuła - base php correction proposal class
 *******************************************************************************/
package org.eclipse.php.internal.ui.text.correction.proposals;

import org.eclipse.dltk.ui.text.completion.IScriptCompletionProposal;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.ICompletionProposalExtension6;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.php.internal.ui.text.correction.CorrectionCommandHandler;
import org.eclipse.php.internal.ui.text.correction.CorrectionMessages;
import org.eclipse.php.internal.ui.text.correction.ICommandAccess;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;

/**
 * Implementation of a PHP completion proposal
 * 
 * @since 3.6
 */
abstract public class AbstractCorrectionProposal
		implements IScriptCompletionProposal, ICommandAccess, ICompletionProposalExtension6 {

	private String fName;
	private int fRelevance;
	private Image fImage;
	private String fCommandId;

	/**
	 * Constructs a change correction proposal.
	 * 
	 * @param name
	 *            The name that is displayed in the proposal selection dialog.
	 * @param relevance
	 *            The relevance of this proposal.
	 * @param image
	 *            The image that is displayed for this proposal or
	 *            <code>null</code> if no image is desired.
	 */
	public AbstractCorrectionProposal(String name, int relevance, Image image) {
		this(name, relevance, image, null);
	}

	/**
	 * Constructs a change correction proposal.
	 * 
	 * @param name
	 *            The name that is displayed in the proposal selection dialog.
	 * @param relevance
	 *            The relevance of this proposal.
	 * @param image
	 *            The image that is displayed for this proposal or
	 *            <code>null</code> if no image is desired.
	 * @param commandId
	 *            Command id for {@link CorrectionCommandHandler}
	 */
	public AbstractCorrectionProposal(String name, int relevance, Image image, String commandId) {
		if (name == null) {
			throw new IllegalArgumentException(CorrectionMessages.ChangeCorrectionProposal_0);
		}
		fName = name;
		fRelevance = relevance;
		fImage = image;
		fCommandId = commandId;
	}

	/**
	 * @see ICompletionProposal#getContextInformation()
	 */
	@Override
	public IContextInformation getContextInformation() {
		return null;
	}

	/**
	 * @see ICompletionProposal#getDisplayString()
	 */
	@Override
	public String getDisplayString() {
		return CorrectionCommandHandler.appendShortcut(getName(), this.getCommandId());
	}

	@Override
	public StyledString getStyledDisplayString() {
		return CorrectionCommandHandler.styleWithShortcut(getName(), this.getCommandId());
	}

	/**
	 * Returns the name of the proposal.
	 * 
	 * @return return the name of the proposal
	 */
	public String getName() {
		return fName;
	}

	/*
	 * @see ICompletionProposal#getImage()
	 */
	@Override
	public Image getImage() {
		return fImage;
	}

	/*
	 * @see ICompletionProposal#getSelection(IDocument)
	 */
	@Override
	public Point getSelection(IDocument document) {
		return null;
	}

	/**
	 * Sets the proposal's image or <code>null</code> if no image is desired.
	 * 
	 * @param image
	 *            the desired image.
	 */
	public void setImage(Image image) {
		fImage = image;
	}

	/**
	 * Sets the display name.
	 * 
	 * @param name
	 *            the name to set
	 */
	public void setDisplayName(String name) {
		if (name == null) {
			throw new IllegalArgumentException(CorrectionMessages.ChangeCorrectionProposal_5);
		}
		fName = name;
	}

	@Override
	public int getRelevance() {
		return fRelevance;
	}

	/**
	 * Sets the relevance.
	 * 
	 * @param relevance
	 *            the relevance to set
	 */
	public void setRelevance(int relevance) {
		fRelevance = relevance;
	}

	@Override
	public String getCommandId() {
		return fCommandId;
	}

	/**
	 * Set the proposal id to allow assigning a shortcut to the correction
	 * proposal.
	 * 
	 * @param commandId
	 *            The proposal id for this proposal or <code>null</code> if no
	 *            command should be assigned to this proposal.
	 */
	public void setCommandId(String commandId) {
		fCommandId = commandId;
	}
}
