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
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.internal.core.PHPCorePlugin;

/**
 * This context represents the state when staying in a use statement in a alias
 * part. <br/>
 * Examples:
 * 
 * <pre>
 *  1. use A as |
 *  2. use A as B|
 *  etc...
 * </pre>
 * 
 * @author michael
 */
public class UseAliasContext extends UseStatementContext {

	@Override
	public boolean isValid(@NonNull ISourceModule sourceModule, int offset, CompletionRequestor requestor) {
		if (!super.isValid(sourceModule, offset, requestor)) {
			return false;
		}

		try {
			String previousWord = getPreviousWord();
			if ("as".equalsIgnoreCase(previousWord)) { //$NON-NLS-1$
				return true;
			}
		} catch (BadLocationException e) {
			PHPCorePlugin.log(e);
		}
		return false;
	}
}
