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
import org.eclipse.php.core.codeassist.IElementFilter;
import org.eclipse.php.internal.core.codeassist.ProposalExtraInfo;
import org.eclipse.php.internal.core.codeassist.contexts.NamespacePHPDocVarStartContext;

public class NamespaceDocTypesCompositeStrategy extends AbstractCompletionStrategy {

	public NamespaceDocTypesCompositeStrategy(ICompletionContext context, IElementFilter elementFilter) {
		super(context, elementFilter);
	}

	public NamespaceDocTypesCompositeStrategy(ICompletionContext context) {
		super(context);
	}

	public void apply(ICompletionReporter reporter) throws BadLocationException {
		ICompletionContext context = getContext();
		if (!(context instanceof NamespacePHPDocVarStartContext)) {
			return;
		}
		NamespacePHPDocVarStartContext concreteContext = (NamespacePHPDocVarStartContext) context;
		if (concreteContext.isGlobal()) {
			GlobalTypesStrategy strategy = new GlobalTypesStrategy(context) {

				protected int getExtraInfo() {
					return ProposalExtraInfo.TYPE_ONLY;
				}
			};
			strategy.apply(reporter);
		} else {
			NamespaceDocTypesStrategy strategy = new NamespaceDocTypesStrategy(context);
			strategy.apply(reporter);
		}
	}
}
