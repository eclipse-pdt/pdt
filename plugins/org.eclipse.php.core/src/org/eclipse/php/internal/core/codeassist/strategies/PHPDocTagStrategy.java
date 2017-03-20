/*******************************************************************************
 * Copyright (c) 2009, 2016 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.core.codeassist.strategies;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.dltk.core.CompletionRequestor;
import org.eclipse.dltk.core.ISourceRange;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.core.codeassist.ICompletionReporter;
import org.eclipse.php.core.codeassist.IElementFilter;
import org.eclipse.php.core.compiler.ast.nodes.PHPDocTag.TagKind;
import org.eclipse.php.internal.core.codeassist.contexts.PHPDocTagContext;

/**
 * This strategy completes PHPDoc tag names.
 * 
 * @author michael
 */
public class PHPDocTagStrategy extends AbstractCompletionStrategy {

	public PHPDocTagStrategy(ICompletionContext context, IElementFilter elementFilter) {
		super(context, elementFilter);
	}

	public PHPDocTagStrategy(ICompletionContext context) {
		super(context);
	}

	public void apply(ICompletionReporter reporter) throws BadLocationException {
		ICompletionContext context = getContext();
		if (!(context instanceof PHPDocTagContext)) {
			return;
		}
		PHPDocTagContext tagContext = (PHPDocTagContext) context;
		String tagName = tagContext.getTagName();
		CompletionRequestor requestor = tagContext.getCompletionRequestor();

		ISourceRange replaceRange = getReplacementRange(tagContext);
		String suffix = ""; //$NON-NLS-1$

		for (TagKind nextTag : TagKind.values()) {
			String nextTagName = nextTag.getName();
			if (StringUtils.startsWithIgnoreCase(nextTagName, tagName)) {
				if (!requestor.isContextInformationMode() || nextTagName.length() == tagName.length()) {

					// Tags are reported like keywords:
					reporter.reportKeyword(nextTagName, suffix, replaceRange);
				}
			}
		}
	}

}
