/*******************************************************************************
 * Copyright (c) 2017 Alex Xu and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Alex Xu - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.editor.contentassist;

import org.eclipse.dltk.core.CompletionProposal;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationExtension;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.swt.graphics.Image;

/**
 * Implementation of the <code>IContextInformation</code> interface.
 */
public final class ProposalContextInformation implements IContextInformation, IContextInformationExtension {

	private final String fContextDisplayString;
	private final String fInformationDisplayString;
	private final Image fImage;
	private int fPosition;

	/**
	 * Creates a new context information.
	 *
	 * @param proposal
	 *            the JDT Core completion proposal
	 */
	public ProposalContextInformation(CompletionProposal proposal) {
		// don't cache the core proposal because the ContentAssistant might
		// hang on to the context info.
		PHPCompletionProposalLabelProvider labelProvider = new PHPCompletionProposalLabelProvider();
		fInformationDisplayString = labelProvider.createParameterList(proposal);
		ImageDescriptor descriptor = labelProvider.createImageDescriptor(proposal);
		if (descriptor != null)
			fImage = PHPUiPlugin.getImageDescriptorRegistry().get(descriptor);
		else
			fImage = null;
		if (proposal.getCompletion().length() == 0)
			fPosition = proposal.getCompletionLocation() + 1;
		else
			fPosition = -1;
		fContextDisplayString = labelProvider.createLabel(proposal);
	}

	/*
	 * @see IContextInformation#equals
	 */
	@Override
	public boolean equals(Object object) {
		if (object instanceof IContextInformation) {
			IContextInformation contextInformation = (IContextInformation) object;
			boolean equals = getInformationDisplayString()
					.equalsIgnoreCase(contextInformation.getInformationDisplayString());
			if (getContextDisplayString() != null)
				equals = equals
						&& getContextDisplayString().equalsIgnoreCase(contextInformation.getContextDisplayString());
			return equals;
		}
		return false;
	}

	/*
	 * @see java.lang.Object#hashCode()
	 * 
	 * @since 3.5
	 */
	@Override
	public int hashCode() {
		int low = fContextDisplayString != null ? fContextDisplayString.hashCode() : 0;
		return (fInformationDisplayString.hashCode() << 16) | low;
	}

	/*
	 * @see IContextInformation#getInformationDisplayString()
	 */
	@Override
	public String getInformationDisplayString() {
		return fInformationDisplayString;
	}

	/*
	 * @see IContextInformation#getImage()
	 */
	@Override
	public Image getImage() {
		return fImage;
	}

	/*
	 * @see IContextInformation#getContextDisplayString()
	 */
	@Override
	public String getContextDisplayString() {
		return fContextDisplayString;
	}

	/*
	 * @see IContextInformationExtension#getContextInformationPosition()
	 */
	@Override
	public int getContextInformationPosition() {
		return fPosition;
	}

	/**
	 * Sets the context information position.
	 *
	 * @param position
	 *            the new position, or -1 for unknown.
	 * @since 3.1
	 */
	public void setContextInformationPosition(int position) {
		fPosition = position;
	}
}
