/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.ui.editor.templates;

import java.util.function.Supplier;

import org.eclipse.dltk.ui.templates.ScriptTemplateProposal;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.templates.Template;
import org.eclipse.jface.text.templates.TemplateContext;
import org.eclipse.swt.graphics.Image;
import org.eclipse.wst.sse.core.utils.StringUtils;

public class PHPTemplateProposal extends ScriptTemplateProposal {

	public PHPTemplateProposal(Template template, TemplateContext context, IRegion region, Supplier<Image> image,
			int relevance) {
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