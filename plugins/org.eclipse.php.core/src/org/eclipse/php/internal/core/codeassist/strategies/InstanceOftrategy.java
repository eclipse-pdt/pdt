/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.codeassist.strategies;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.internal.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.codeassist.contexts.AbstractCompletionContext;
import org.eclipse.php.internal.core.codeassist.contexts.ICompletionContext;

/**
 * This strategy completes global classes after 'new' statement 
 * @author michael
 */
public class InstanceOftrategy extends GlobalClassesStrategy {

	public void apply(ICompletionContext context, ICompletionReporter reporter) throws BadLocationException {
		super.apply(context, reporter);
		
		AbstractCompletionContext concreteContext = (AbstractCompletionContext) context;
		addSelf(concreteContext, reporter);
	}
}
