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
package org.eclipse.php.internal.ui.editor.templates;

import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.contentassist.ICompletionProposalExtension4;
import org.eclipse.jface.text.templates.Template;
import org.eclipse.jface.text.templates.TemplateContext;
import org.eclipse.jface.text.templates.TemplateProposal;
import org.eclipse.swt.graphics.Image;
import org.eclipse.wst.sse.core.utils.StringUtils;

public class PHPTemplateProposal extends TemplateProposal implements ICompletionProposalExtension4 {

	public PHPTemplateProposal(Template template, TemplateContext context, IRegion region, Image image, int relevance) {
		super(template, context, region, image, relevance);
	}

	protected Template getTemplateNew() {
		return super.getTemplate();
	}

	@Override
	public String getAdditionalProposalInfo() {
		String additionalInfo = super.getAdditionalProposalInfo();
		return StringUtils.convertToHTMLContent(additionalInfo);
	}

	@Override
	public boolean isAutoInsertable() {
		return getTemplate().isAutoInsertable();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof PHPTemplateProposal)) {
			return false;
		}
		PHPTemplateProposal newTemplateProposal = (PHPTemplateProposal) obj;
		return getTemplate().equals(newTemplateProposal.getTemplate());
	}

	@Override
	public int hashCode() {
		return getTemplate().hashCode();
	}
}