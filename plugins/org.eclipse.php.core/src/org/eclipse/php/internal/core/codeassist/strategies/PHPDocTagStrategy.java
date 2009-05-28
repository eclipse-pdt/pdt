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
package org.eclipse.php.internal.core.codeassist.strategies;

import org.eclipse.dltk.core.CompletionRequestor;
import org.eclipse.dltk.internal.core.SourceRange;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.internal.core.codeassist.CodeAssistUtils;
import org.eclipse.php.internal.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.codeassist.contexts.ICompletionContext;
import org.eclipse.php.internal.core.codeassist.contexts.PHPDocTagContext;

/**
 * This strategy completes PHPDoc tag names. 
 * @author michael
 */
public class PHPDocTagStrategy extends AbstractCompletionStrategy {

	public static final String[] PHPDOC_TAGS = { "abstract", "access", "author", "category", "copyright", "deprecated", "example", "final", "filesource", "global", "ignore", "internal", "license", "link", "method", "name", "package", "param", "property", "return", "see", "since", "static",
		"staticvar", "subpackage", "todo", "tutorial", "uses", "var", "version" };
	
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

		SourceRange replaceRange = getReplacementRange(tagContext);
		String suffix = ""; //$NON-NLS-1$

		for (String nextTag : PHPDOC_TAGS) {
			if (CodeAssistUtils.startsWithIgnoreCase(nextTag, tagName)) {
				if (!requestor.isContextInformationMode() || nextTag.length() == tagName.length()) {

					// Tags are reported like keywords:
					reporter.reportKeyword(nextTag, suffix, replaceRange);
				}
			}
		}
	}

}
