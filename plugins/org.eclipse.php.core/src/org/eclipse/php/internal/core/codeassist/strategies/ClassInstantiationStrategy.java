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
package org.eclipse.php.internal.core.codeassist.strategies;

import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.core.ISourceRange;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.core.PHPVersion;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.core.codeassist.ICompletionReporter;
import org.eclipse.php.core.compiler.ast.nodes.NamespaceReference;
import org.eclipse.php.internal.core.codeassist.contexts.AbstractCompletionContext;

/**
 * This strategy completes global classes after 'new' statement. It adds "self"
 * class to the final result
 * 
 * @author michael
 */
public class ClassInstantiationStrategy extends AbstractClassInstantiationStrategy {

	private static final String CLASS_KEYWORD = "class"; //$NON-NLS-1$

	public ClassInstantiationStrategy(ICompletionContext context) {
		super(context, 0, Modifiers.AccInterface | Modifiers.AccNameSpace | Modifiers.AccAbstract);
	}

	@Override
	public void apply(ICompletionReporter reporter) throws BadLocationException {
		AbstractCompletionContext completionContext = (AbstractCompletionContext) getContext();
		String suffix = getSuffix(completionContext);
		addAlias(reporter, suffix);
		super.apply(reporter);
		addSelf(completionContext, reporter);

		// for anonymous class (PHP 7)
		if (getCompanion().getPHPVersion().isGreaterThan(PHPVersion.PHP5_6)) {
			String prefix = completionContext.getPrefix();
			ISourceRange replaceRange = getReplacementRangeForMember(completionContext);
			if (CLASS_KEYWORD.startsWith(prefix) && prefix.indexOf(NamespaceReference.NAMESPACE_SEPARATOR) == -1) {
				reporter.reportKeyword(CLASS_KEYWORD, "", replaceRange); //$NON-NLS-1$
			}
		}
	}

}
