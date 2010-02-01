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

import org.eclipse.core.runtime.Platform;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.ui.ScriptElementImageDescriptor;
import org.eclipse.dltk.ui.ScriptElementImageProvider;
import org.eclipse.dltk.ui.text.completion.*;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.text.IDocument;
import org.eclipse.php.internal.core.codeassist.IPHPCompletionRequestor;
import org.eclipse.php.internal.ui.util.PHPPluginImages;
import org.eclipse.swt.graphics.Image;

public class PHPCompletionProposalCollector extends
		ScriptCompletionProposalCollector implements IPHPCompletionRequestor {

	private IDocument document;
	private boolean explicit;

	public PHPCompletionProposalCollector(IDocument document, ISourceModule cu,
			boolean explicit) {
		super(cu);
		this.document = document;
		this.explicit = explicit;
	}

	protected ScriptCompletionProposal createOverrideCompletionProposal(
			IScriptProject scriptProject, ISourceModule compilationUnit,
			String name, String[] paramTypes, int start, int length,
			String label, String string) {
		return new PHPOverrideCompletionProposal(scriptProject,
				compilationUnit, name, paramTypes, start, length, label, string);
	}

	protected ScriptCompletionProposal createScriptCompletionProposal(
			String completion, int replaceStart, int length, Image image,
			String displayString, int i) {
		return new PHPCompletionProposal(completion, replaceStart, length,
				image, displayString, i);
	}

	protected ScriptCompletionProposal createScriptCompletionProposal(
			String completion, int replaceStart, int length, Image image,
			String displayString, int i, boolean isInDoc) {
		return new PHPCompletionProposal(completion, replaceStart, length,
				image, displayString, i, isInDoc);
	}

	protected CompletionProposalLabelProvider createLabelProvider() {
		CompletionProposalLabelProvider labelProvider = new PHPCompletionProposalLabelProvider();

		// check if there are any adapters extending basic label provider
		CompletionProposalLabelProvider extended = (CompletionProposalLabelProvider) Platform
				.getAdapterManager().getAdapter(labelProvider,
						CompletionProposalLabelProvider.class);

		if (extended != null)
			return extended;

		return labelProvider;

	}

	protected IScriptCompletionProposal createPackageProposal(
			CompletionProposal proposal) {
		final AbstractScriptCompletionProposal scriptProposal = (AbstractScriptCompletionProposal) super
				.createPackageProposal(proposal);
		final IModelElement modelElement = proposal.getModelElement();
		if (modelElement != null) {
			scriptProposal.setProposalInfo(new ProposalInfo(modelElement
					.getScriptProject(), new String(proposal.getName())));
		}
		return scriptProposal;
	}

	protected IScriptCompletionProposal createKeywordProposal(
			CompletionProposal proposal) {
		AbstractScriptCompletionProposal scriptProposal = (AbstractScriptCompletionProposal) super
				.createKeywordProposal(proposal);
		final IModelElement modelElement = proposal.getModelElement();
		if (modelElement != null
				&& modelElement.getElementType() == IModelElement.SOURCE_MODULE) {
			scriptProposal.setImage(PHPPluginImages
					.get(PHPPluginImages.IMG_OBJS_PHP_FILE));
		}
		return scriptProposal;
	}

	protected IScriptCompletionProposal createScriptCompletionProposal(
			CompletionProposal proposal) {
		ScriptCompletionProposal completionProposal = (ScriptCompletionProposal) super
				.createScriptCompletionProposal(proposal);
		if (proposal.getKind() == CompletionProposal.METHOD_DECLARATION) {
			IMethod method = (IMethod) proposal.getModelElement();
			try {
				if (method.isConstructor()) {
					// replace method icon with class icon:
					int flags = proposal.getFlags();
					ImageDescriptor typeImageDescriptor = ScriptElementImageProvider
							.getTypeImageDescriptor(flags, false);
					int adornmentFlags = ScriptElementImageProvider
							.computeAdornmentFlags(
									method.getDeclaringType(),
									ScriptElementImageProvider.SMALL_ICONS
											| ScriptElementImageProvider.OVERLAY_ICONS);
					ScriptElementImageDescriptor descriptor = new ScriptElementImageDescriptor(
							typeImageDescriptor, adornmentFlags,
							ScriptElementImageProvider.SMALL_SIZE);
					completionProposal.setImage(getImage(descriptor));
				}
			} catch (ModelException e) {
				if (DLTKCore.DEBUG_COMPLETION) {
					e.printStackTrace();
				}
			}
		}
		return completionProposal;
	}

	protected char[] getVarTrigger() {
		// variable proposal will be inserted automatically if one of these
		// characters
		// is being typed in showing proposal time:
		return null;
	}

	public IDocument getDocument() {
		return document;
	}

	public boolean isExplicit() {
		return explicit;
	}
}
