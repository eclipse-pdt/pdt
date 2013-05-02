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
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.internal.core.PHPVersion;

public class GotoStatementContext extends StatementContext {

	private static final String GOTO_KEYWORD = "goto"; //$NON-NLS-1$

	private IModelElement currentElement;

	public boolean isValid(ISourceModule sourceModule, int offset,
			CompletionRequestor requestor) {
		if (!super.isValid(sourceModule, offset, requestor)) {
			return false;
		}
		if (getPhpVersion().isLessThan(PHPVersion.PHP5_3)) {
			return false;
		}
		try {
			if (GOTO_KEYWORD.equalsIgnoreCase(getPreviousWord())) {
				currentElement = sourceModule.getElementAt(offset);
				return true;
			}
		} catch (BadLocationException e) {
		} catch (ModelException e) {
		}
		return false;
	}

	public IModelElement getCurrentElement() {
		return currentElement;
	}

}
