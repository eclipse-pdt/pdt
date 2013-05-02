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
import org.eclipse.php.internal.core.util.text.TextSequence;

/**
 * This context represents the state when staying before extends/implements
 * block for completion of 'extends' or 'implements' keywords. <br/>
 * Example:
 * 
 * <pre>
 *  class A |
 * </pre>
 * 
 * @author michael
 */
public class ClassDeclarationKeywordContext extends ClassDeclarationContext {

	public boolean isValid(ISourceModule sourceModule, int offset,
			CompletionRequestor requestor) {
		if (!super.isValid(sourceModule, offset, requestor)) {
			return false;
		}

		TextSequence statementText = getStatementText();
		statementText = statementText.subTextSequence(getTypeIdentifierEnd(),
				statementText.length());

		if (!hasExtends() && !hasImplements()) { // the cursor position is right
													// after the class name
			return true;
		}

		if (!hasImplements()) { // check that the previous word is not a keyword
			try {
				String previousWord = getPreviousWord();
				if (!"extends".equalsIgnoreCase(previousWord) //$NON-NLS-1$
						&& !"implements".equalsIgnoreCase(previousWord)) { //$NON-NLS-1$
					return true;
				}
			} catch (BadLocationException e) {
				PHPCorePlugin.log(e);
			}
		}
		return false;
	}
}
