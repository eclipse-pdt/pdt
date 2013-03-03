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
import org.eclipse.php.internal.core.codeassist.ProposalExtraInfo;
import org.eclipse.php.internal.core.codeassist.contexts.AbstractCompletionContext;
import org.eclipse.php.internal.core.compiler.ast.nodes.NamespaceReference;

/**
 * This strategy results like {@link GlobalClassesStrategy}, but filters final
 * classes.
 * 
 * @author michael
 * 
 */
public class NonFinalClassesStrategy extends GlobalClassesStrategy {

	public NonFinalClassesStrategy(ICompletionContext context) {
		super(context, 0, Modifiers.AccInterface | Modifiers.AccNameSpace
				| Modifiers.AccFinal);
	}

	@Override
	public void apply(ICompletionReporter reporter) throws BadLocationException {
		// let NamespaceNonFinalClassesStrategy to deal with namespace prefix
		AbstractCompletionContext completionContext = (AbstractCompletionContext) getContext();
		if (completionContext.getPrefix() != null
				&& completionContext.getPrefix().indexOf(
						NamespaceReference.NAMESPACE_SEPARATOR) >= 0) {
			return;
		}
		super.apply(reporter);
	}

	@Override
	protected int getExtraInfo() {
		return ProposalExtraInfo.TYPE_ONLY;
	}
}
