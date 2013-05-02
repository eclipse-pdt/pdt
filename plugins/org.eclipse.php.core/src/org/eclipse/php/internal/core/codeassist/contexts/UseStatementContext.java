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
package org.eclipse.php.internal.core.codeassist.contexts;

import org.eclipse.dltk.core.CompletionRequestor;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.php.internal.core.util.text.TextSequence;

/**
 * This context represents the state when staying in a use statement. <br/>
 * Examples:
 * 
 * <pre>
 *  1. use |
 *  2. use A\B| 
 *  3. use A as |
 *  4. use A as B|
 *  etc...
 * </pre>
 * 
 * @author michael
 */
public abstract class UseStatementContext extends StatementContext {

	protected boolean useTrait;

	public boolean isValid(ISourceModule sourceModule, int offset,
			CompletionRequestor requestor) {
		if (!super.isValid(sourceModule, offset, requestor)) {
			return false;
		}

		useTrait = new ClassStatementContext().isValid(sourceModule, offset,
				requestor);
		TextSequence statementText = getStatementText();
		if (statementText.length() >= 4) {
			if ("use".equals(statementText.subSequence(0, 3).toString()) //$NON-NLS-1$
					&& Character.isWhitespace(statementText.subSequence(3, 4)
							.charAt(0))) {
				return true;
			}
		}
		return false;
	}

	public boolean isUseTrait() {
		return useTrait;
	}
}
