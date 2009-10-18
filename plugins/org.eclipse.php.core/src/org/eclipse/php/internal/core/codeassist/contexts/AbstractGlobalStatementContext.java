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
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.internal.core.codeassist.IPHPCompletionRequestor;
import org.eclipse.php.internal.core.util.text.TextSequence;

/**
 * This context represents state when staying in a global statement. That means
 * we are going to complete: keywords and all global elements in this context.
 * 
 * @author michael
 */
public abstract class AbstractGlobalStatementContext extends StatementContext {

	public boolean isValid(ISourceModule sourceModule, int offset,
			CompletionRequestor requestor) {
		if (!super.isValid(sourceModule, offset, requestor)) {
			return false;
		}

		if (requestor instanceof IPHPCompletionRequestor) {
			IPHPCompletionRequestor phpCompletionRequestor = (IPHPCompletionRequestor) requestor;
			boolean isExplicit = phpCompletionRequestor.isExplicit();
			if (!isExplicit) {
				try {
					String prefix = getPrefix();
					if ((prefix == null || prefix.length() == 0)) {
						return false;
					}
					TextSequence statementText = getStatementText();
					if (statementText.length() > 0
							&& statementText.charAt(statementText.length() - 1) == ':') {
						return false;
					}
				} catch (BadLocationException e) {
				}
			}
		}
		return true;
	}

	public boolean isExclusive() {
		return true;
	}
}
