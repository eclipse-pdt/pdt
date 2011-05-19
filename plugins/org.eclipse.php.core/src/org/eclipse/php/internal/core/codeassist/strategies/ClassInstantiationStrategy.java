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

import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.internal.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.codeassist.contexts.AbstractCompletionContext;
import org.eclipse.php.internal.core.compiler.ast.nodes.NamespaceReference;

/**
 * This strategy completes global classes after 'new' statement. It adds "self"
 * class to the final result
 * 
 * @author michael
 */
public class ClassInstantiationStrategy extends
		AbstractClassInstantiationStrategy {

	public ClassInstantiationStrategy(ICompletionContext context) {
		super(context, 0, Modifiers.AccInterface | Modifiers.AccNameSpace
				| Modifiers.AccAbstract);
	}

	@Override
	public void apply(ICompletionReporter reporter) throws BadLocationException {
		// let NamespaceClassInstantiationStrategy to deal with namespace prefix
		AbstractCompletionContext completionContext = (AbstractCompletionContext) getContext();
		String suffix = getSuffix(completionContext);
		addAlias(reporter, suffix);
		if (completionContext.getPrefix() != null
				&& completionContext.getPrefix().indexOf(
						NamespaceReference.NAMESPACE_SEPARATOR) >= 0) {
			return;
		}
		super.apply(reporter);
		ICompletionContext context = getContext();
		AbstractCompletionContext concreteContext = (AbstractCompletionContext) context;
		addSelf(concreteContext, reporter);
	}

}
