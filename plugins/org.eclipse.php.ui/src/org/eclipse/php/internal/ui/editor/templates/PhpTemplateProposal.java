/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.editor.templates;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.ICompletionProposalExtension4;
import org.eclipse.jface.text.templates.Template;
import org.eclipse.jface.text.templates.TemplateContext;
import org.eclipse.jface.text.templates.TemplateProposal;
import org.eclipse.php.internal.core.format.FormatterUtils;
import org.eclipse.php.internal.ui.Logger;
import org.eclipse.swt.graphics.Image;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.sse.core.utils.StringUtils;

public class PhpTemplateProposal extends TemplateProposal implements ICompletionProposalExtension4 {

	public PhpTemplateProposal(Template template, TemplateContext context, IRegion region, Image image, int relevance) {
		super(template, context, region, image, relevance);
	}

	protected Template getTemplateNew() {
		return super.getTemplate();
	}

	public String getAdditionalProposalInfo() {
		String additionalInfo = super.getAdditionalProposalInfo();
		return StringUtils.convertToHTMLContent(additionalInfo);
	}

	public boolean isAutoInsertable() {
		return getTemplate().isAutoInsertable();
	}

	/*
	 * Use this function in order to fix the indentation of multiple line templates.
	 * 
	 * (non-Javadoc)
	 * @see org.eclipse.jface.text.templates.TemplateProposal#apply(org.eclipse.jface.text.ITextViewer, char, int, int)
	 */
	public void apply(ITextViewer viewer, char trigger, int stateMask, int offset) {

		IStructuredDocument document = (IStructuredDocument) viewer.getDocument();
		String originalTemplate = getTemplate().getPattern();

		String blanks = ""; //$NON-NLS-1$

		int lineNumber = document.getLineOfOffset(offset);
		IRegion lineRegion;
		try {
			lineRegion = document.getLineInformation(lineNumber);
			blanks = FormatterUtils.getLineBlanks(document, lineRegion);
		} catch (BadLocationException e) {
			Logger.logException(e);
			super.apply(viewer, trigger, stateMask, offset);
			return;
		}

		String docLineDelimiter = document.getLineDelimiter();

		String lineDelimiter = "\n"; //$NON-NLS-1$
		StringBuffer sb = new StringBuffer();

		// Look for document line delimiter or "\n" in the template
		// and add to each occurance found the appropriate indentation
		Pattern p = Pattern.compile(docLineDelimiter + "|" + lineDelimiter); //$NON-NLS-1$
		Matcher m = p.matcher(originalTemplate);
		String matchedDelimiter = ""; //$NON-NLS-1$
		boolean result = m.find();
		while (result) {
			matchedDelimiter = m.group(0);
			m.appendReplacement(sb, docLineDelimiter + blanks);
			result = m.find();
		}
		// Add the last segment of input to the new String
		m.appendTail(sb);
		String newTemplate = sb.toString();

		// temporary replace the template with the "fixed" one (with proper indentation)
		// and then restore the original
		getTemplate().setPattern(newTemplate);
		super.apply(viewer, trigger, stateMask, offset);
		getTemplate().setPattern(originalTemplate);

	}

}