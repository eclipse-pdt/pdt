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

	@Override
	public boolean isValid(@NonNull ISourceModule sourceModule, int offset, CompletionRequestor requestor) {
		if (!super.isValid(sourceModule, offset, requestor)) {
			return false;
		}
		int typeEnd = getTypeEnd();
		if (typeEnd >= 6) {
			TextSequence statementText = getStatementText();
			String typeString = statementText.subSequence(typeEnd - 6, typeEnd - 1).toString();
			if ("class".equalsIgnoreCase(typeString)) { //$NON-NLS-1$
				return true;
			}
		}
		return false;
	}
}
