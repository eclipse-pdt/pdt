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
 * This context represents the class declaration state. <br/>
 * Examples:
 * 
 * <pre>
 *  1. class A implements |
 *  2. class A implements B|
 * </pre>
 * 
 * @author michael
 */
public abstract class ClassDeclarationContext extends TypeDeclarationContext {

	public boolean isValid(ISourceModule sourceModule, int offset,
			CompletionRequestor requestor) {
		if (!super.isValid(sourceModule, offset, requestor)) {
			return false;
		}
		int typeEnd = getTypeEnd();
		if (typeEnd >= 6) {
			TextSequence statementText = getStatementText();
			String typeString = statementText.subSequence(typeEnd - 6,
					typeEnd - 1).toString();
			if ("class".equals(typeString)) { //$NON-NLS-1$
				return true;
			}
		}
		return false;
	}
}
