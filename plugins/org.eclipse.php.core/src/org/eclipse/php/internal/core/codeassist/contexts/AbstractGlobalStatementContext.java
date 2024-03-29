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
package org.eclipse.php.internal.core.codeassist.contexts;

import org.eclipse.dltk.annotations.NonNull;
import org.eclipse.dltk.core.CompletionRequestor;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ISourceReference;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.internal.core.codeassist.IPHPCompletionRequestor;
import org.eclipse.php.internal.core.util.text.TextSequence;

/**
 * This context represents state when staying in a global statement. That means
 * we are going to complete: keywords and all global elements in this context.
 * 
 * @author michael
 */
public abstract class AbstractGlobalStatementContext extends StatementContext {

	@Override
	public boolean isValid(@NonNull ISourceModule sourceModule, int offset, CompletionRequestor requestor) {
		if (!super.isValid(sourceModule, offset, requestor)) {
			return false;
		}

		if (requestor instanceof IPHPCompletionRequestor) {
			IPHPCompletionRequestor phpCompletionRequestor = (IPHPCompletionRequestor) requestor;
			boolean isExplicit = phpCompletionRequestor.isExplicit();
			if (!isExplicit) {
				try {
					String prefix = getPrefix().isEmpty() ? getPreviousWord() : getPrefix();
					if ((prefix == null || prefix.length() == 0)) {
						return false;
					}
					TextSequence statementText = getStatementText();
					if (statementText.length() > 0 && statementText.charAt(statementText.length() - 1) == ':') {
						return false;
					}
				} catch (BadLocationException e) {
				}
			}
		}
		return true;
	}

	protected boolean isBeforeName(int offset, ISourceReference reference) throws ModelException {
		return reference.getNameRange() != null && offset < reference.getNameRange().getOffset();
	}

	@Override
	public boolean isExclusive() {
		return false;
	}

	@Override
	public boolean isExclusive(ICompletionContext ctx) {
		return !(ctx instanceof FunctionCallParameterContext);
	}
}
