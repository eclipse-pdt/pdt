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
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.core.compiler.ast.nodes.NamespaceReference;

public class ExceptionClassInstantiationContext extends StatementContext {
	public boolean isValid(ISourceModule sourceModule, int offset, CompletionRequestor requestor) {
		if (!super.isValid(sourceModule, offset, requestor)) {
			return false;
		}

		try {
			String previousWord = getPreviousWord();
			String previous2Word = getPreviousWord(2);
			if ("new".equalsIgnoreCase(previousWord) //$NON-NLS-1$
					&& "throw".equalsIgnoreCase(previous2Word)) { //$NON-NLS-1$
				return true;
			}
		} catch (BadLocationException e) {
			if (DLTKCore.DEBUG_COMPLETION) {
				e.printStackTrace();
			}
		}

		return false;
	}

	public String getPrefix() throws BadLocationException {
		String prefix = super.getPrefix();
		if (prefix.length() > 0 && prefix.charAt(0) == NamespaceReference.NAMESPACE_SEPARATOR) {
			return prefix.substring(1);
		}
		return prefix;
	}
}
