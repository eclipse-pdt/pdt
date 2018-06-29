/*******************************************************************************
 * Copyright (c) 2009, 2017 IBM Corporation and others.
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

	@Override
	public boolean isValid(@NonNull ISourceModule sourceModule, int offset, CompletionRequestor requestor) {
		if (!super.isValid(sourceModule, offset, requestor)) {
			return false;
		}
		if (getType() == TYPES.TRAIT) {
			TextSequence statementText = getStatementText();
			if (statementText.toString().indexOf('{') < 0 && statementText.toString().indexOf('}') < 0) {
				return true;
			}
		} else if (getType() == TYPES.USE || getType() == TYPES.USE_GROUP) {
			return !(isUseFunctionStatement() || isUseConstStatement());
		}
		return false;
	}
}
