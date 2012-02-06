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
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.compiler.ast.nodes.NamespaceReference;
import org.eclipse.php.internal.core.util.text.TextSequence;

/**
 * This context represents the state when staying in a use statement in a
 * namespace name part. <br/>
 * Examples:
 * 
 * <pre>
 *  1. use |
 *  2. use A\B| 
 *  etc...
 * </pre>
 * 
 * @author michael
 */
public class UseNameContext extends UseStatementContext {

	public boolean isValid(ISourceModule sourceModule, int offset,
			CompletionRequestor requestor) {
		if (!super.isValid(sourceModule, offset, requestor)) {
			return false;
		}

		try {
			if (useTrait) {
				TextSequence statementText = getStatementText();
				if (statementText.toString().indexOf('{') < 0
						&& statementText.toString().indexOf('}') < 0) {
					return true;
				}
			} else {
				String previousWord = getPreviousWord();
				if ("use".equalsIgnoreCase(previousWord)) { //$NON-NLS-1$
					return true;
				}
			}
		} catch (BadLocationException e) {
			PHPCorePlugin.log(e);
		}
		return false;
	}

	public String getPrefix() throws BadLocationException {
		String prefix = super.getPrefix();
		if (prefix.length() > 0
				&& prefix.charAt(0) == NamespaceReference.NAMESPACE_SEPARATOR) {
			return prefix.substring(1);
		}
		return prefix;
	}
}
