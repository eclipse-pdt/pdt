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

import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.internal.core.codeassist.ProposalExtraInfo;
import org.eclipse.php.internal.core.codeassist.contexts.AbstractCompletionContext;

/**
 * This strategy completes function parameter types
 * 
 * @author michael
 */
public class FunctionParameterTypeStrategy extends GlobalTypesStrategy {

	public FunctionParameterTypeStrategy(ICompletionContext context) {
		super(context);
	}

	public String getSuffix(AbstractCompletionContext abstractContext) {
		return " "; //$NON-NLS-1$
	}
	protected int getExtraInfo() {
		return ProposalExtraInfo.TYPE_ONLY;
	}
}
