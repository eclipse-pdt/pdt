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
 * This context represents state when staying in a class static member completion (after paamayim-nekudotaim)
 * <br/>Examples:
 * <pre>
 *  1. A::|
 *  2. $lsb::|
 *  3. A::$|
 *  etc...
 * </pre>
 * @author michael
 */
public class ClassStaticMemberContext extends ClassMemberContext {
	
	private boolean isParentCall;
	
	public boolean isValid(ISourceModule sourceModule, int offset, CompletionRequestor requestor) {
		if (!super.isValid(sourceModule, offset, requestor)) {
			return false;
		}
		if (getTriggerType() != Trigger.CLASS) {
			return false;
		}
		
		isParentCall = false;
		
		int elementStart = getElementStart();
		int lhsIndex = elementStart - "parent".length() - getTriggerType().getName().length();
		if (lhsIndex == 0) {
			TextSequence statementText = getStatementText();
			String parentText = statementText.subSequence(0, elementStart - getTriggerType().getName().length()).toString();
			if (parentText.equals("parent")) { //$NON-NLS-1$
				isParentCall = true;
			}
		}
		return true;
	}
	
	/**
	 * Returns whether the left hand side is a word 'parent'
	 */
	public boolean isParentCall() {
		return isParentCall;
	}
}
