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

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.core.codeassist.ICompletionReporter;
import org.eclipse.php.core.compiler.ast.nodes.NamespaceReference;
import org.eclipse.php.internal.core.codeassist.ProposalExtraInfo;
import org.eclipse.php.internal.core.codeassist.contexts.AbstractCompletionContext;

/**
 * This strategy completes global classes after 'new' statement
 * 
 * @author michael
 */
public class InstanceOfStrategy extends GlobalTypesStrategy {

	public InstanceOfStrategy(ICompletionContext context) {
		super(context);
	}

	public void apply(ICompletionReporter reporter) throws BadLocationException {
		// let NamespaceInstanceOfStrategy to deal with namespace prefix
		AbstractCompletionContext completionContext = (AbstractCompletionContext) getContext();
		// String suffix = getSuffix(completionContext);
		// addAlias(reporter, suffix);
		if (completionContext.getPrefix().indexOf(NamespaceReference.NAMESPACE_SEPARATOR) >= 0) {
			return;
		}

		super.apply(reporter);
		ICompletionContext context = getContext();
		AbstractCompletionContext concreteContext = (AbstractCompletionContext) context;
		addSelf(concreteContext, reporter);
	}

	public String getSuffix(AbstractCompletionContext abstractContext) {
		return ""; //$NON-NLS-1$
	}

	protected int getExtraInfo() {
		return ProposalExtraInfo.TYPE_ONLY;
	}
}
