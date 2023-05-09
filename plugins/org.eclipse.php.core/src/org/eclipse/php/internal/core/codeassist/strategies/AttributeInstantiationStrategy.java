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
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.codeassist.contexts.AbstractCompletionContext;

/**
 * This strategy completes global classes after 'new' statement. It adds "self"
 * class to the final result
 * 
 * @author michael
 */
public class AttributeInstantiationStrategy extends AbstractClassInstantiationStrategy {

	private static final String CLASS_KEYWORD = "class"; //$NON-NLS-1$

	public AttributeInstantiationStrategy(ICompletionContext context) {
		super(context, 0, Modifiers.AccNameSpace);
	}

	@Override
	public void apply(ICompletionReporter reporter) throws BadLocationException {
		AbstractCompletionContext completionContext = (AbstractCompletionContext) getContext();
		String suffix = getSuffix(completionContext);
		addAlias(reporter, suffix);
		super.apply(reporter);
		addSelf(completionContext, reporter);

	}

}
