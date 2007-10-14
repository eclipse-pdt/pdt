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
package org.eclipse.php.internal.ui.editor.templates;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.templates.*;
import org.eclipse.jface.text.templates.persistence.TemplateStore;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.util.PHPPluginImages;
import org.eclipse.swt.graphics.Image;

public class PHPTemplateCompletionProcessor extends TemplateCompletionProcessor {

	private String contextTypeId = PHPTemplateContextTypeIds.PHP;

	public ICompletionProposal[] computeCompletionProposals(ITextViewer viewer, int offset) {
		ICompletionProposal[] completionProposals = super.computeCompletionProposals(viewer, offset);
		if (completionProposals == null) {
			return null;
		}
		return filterUsingPrefix(completionProposals, extractPrefix(viewer, offset));
	}
	
	private ICompletionProposal[] filterUsingPrefix(ICompletionProposal[] completionProposals, String prefix) {
		if (prefix.length() == 0) { // no templats should be offered if there is no prefix.
			return new ICompletionProposal[0];
		}
		List matches= new ArrayList();
		for (int i= 0; i < completionProposals.length; i++) {
			PhpTemplateProposal phpTemplateProposal = (PhpTemplateProposal)completionProposals[i];
			Template template = phpTemplateProposal.getTemplateNew();
			if (template.getName().startsWith(prefix)) {
				matches.add(phpTemplateProposal);
			}
		}
		
		return (ICompletionProposal[]) matches.toArray(new ICompletionProposal[matches.size()]);
	}

	/**
	 * 
	 */
	protected String extractPrefix(ITextViewer viewer, int offset) {
		int i= offset;
		IDocument document= viewer.getDocument();
		if (i > document.getLength())
			return ""; //$NON-NLS-1$

		try {
			while (i > 0) {
				char ch= document.getChar(i - 1);
				if (!(Character.isLetterOrDigit(ch))) {
					if (!('@' == ch || '_' == ch)) {
						break;
					}
				}
				i--;
			}

			return document.get(i, offset - i);
		} catch (BadLocationException e) {
			return ""; //$NON-NLS-1$
		}
	}


	protected Template[] getTemplates(String contextTypeId) {
		Template templates[] = null;
		TemplateStore store = getTemplateStore();
		if (store != null)
			templates = store.getTemplates(contextTypeId);

		return templates;
	}

	protected TemplateContextType getContextType(ITextViewer viewer, IRegion region) {

		//For now always return the context type for ALL PHP regions
		TemplateContextType type = null;

		ContextTypeRegistry registry = getTemplateContextRegistry();
		if (registry != null)
			type = registry.getContextType(contextTypeId);

		return type;
	}

	protected Image getImage(Template template) {
		return PHPUiPlugin.getImageDescriptorRegistry().get(PHPPluginImages.DESC_TEMPLATE);
	}

	protected ContextTypeRegistry getTemplateContextRegistry() {
		return PHPUiPlugin.getDefault().getTemplateContextRegistry();
	}

	protected TemplateStore getTemplateStore() {
		return PHPUiPlugin.getDefault().getTemplateStore();
	}

	public void setContextTypeId(String contextTypeId) {
		this.contextTypeId = contextTypeId;
	}

	protected ICompletionProposal createProposal(Template template, TemplateContext context, IRegion region, int relevance) {
		return new PhpTemplateProposal(template, context, region, getImage(template), relevance);
	}

	protected TemplateContext createContext(ITextViewer viewer, IRegion region) {
		PHPTemplateContextType contextType= (PHPTemplateContextType)getContextType(viewer, region);
		if (contextType != null) {
			IDocument document= viewer.getDocument();
			return new PhpTemplateContext(contextType, document, region.getOffset(), region.getLength());
		}
		return null;
	}
}
