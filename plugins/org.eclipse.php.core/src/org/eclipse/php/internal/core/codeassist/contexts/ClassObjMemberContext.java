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
 * This context represents state when staying in an object member completion
 * <br/>Examples:
 * <pre>
 *  1. $this->|
 *  2. $a->|
 *  3. $a->fo|
 *  etc...
 * </pre>
 * @author michael
 */
public class ClassObjMemberContext extends ClassMemberContext {
	
	private boolean isThisCall;
	
	public boolean isValid(ISourceModule sourceModule, int offset, CompletionRequestor requestor) {
		if (!super.isValid(sourceModule, offset, requestor)) {
			return false;
		}
		if (getTriggerType() != Trigger.OBJECT) {
			return false;
		}
		
		isThisCall = false;
		
		int elementStart = getElementStart();
		int lhsIndex = elementStart - "$this".length() - getTriggerType().getName().length();
		if (lhsIndex >= 0) {
			TextSequence statementText = getStatementText();
			String parentText = statementText.subSequence(lhsIndex, elementStart - getTriggerType().getName().length()).toString();
			if (parentText.equals("$this")) { //$NON-NLS-1$
				isThisCall = true;
			}
		}
		return true;
	}
	
	/**
	 * Returns whether the left hand side is a variable '$this'
	 */
	public boolean isThisCall() {
		return isThisCall;
	}
}
