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
package org.eclipse.php.internal.core.codeassist;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.ITypeHierarchy;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.internal.core.codeassist.contexts.AbstractCompletionContext;
import org.eclipse.php.internal.core.util.text.PHPTextSequenceUtilities;
import org.eclipse.php.internal.core.util.text.TextSequence;

/**
 * This companion is shared between different completion contexts, and it can be
 * used for caching information gathered by resource-intensive operations.
 * 
 * @author michael
 */
public class CompletionCompanion {

	/**
	 * Cache for calculated return types by document position
	 */
	private Map<Integer, IType[]> rhTypesCache = new HashMap<Integer, IType[]>();

	/**
	 * Cache for calculated super type hierarchy
	 */
	private Map<IType, ITypeHierarchy> superHierarchyCache = new HashMap<IType, ITypeHierarchy>();

	/**
	 * Caclulates type for the left hand part in expression enclosed by given
	 * statement text.
	 * <p>
	 * For example:
	 * 
	 * <pre>
	 * 1. If statement text contains &quot;$a-&gt;foo()-&gt;&quot; the result will be the return type of method 'foo'
	 * 2. If statement text contains &quot;A::&quot; the result will be class 'A'
	 * 3. etc...
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param context
	 *            Completion context
	 * @return right hand type(s) for the expression that encloses current
	 *         offset
	 */
	public IType[] getLeftHandType(ICompletionContext context) {
		AbstractCompletionContext aContext = (AbstractCompletionContext) context;
		int offset = aContext.getOffset();
		if (!rhTypesCache.containsKey(offset)) {

			TextSequence statementText = aContext.getStatementText();
			int triggerEnd = PHPTextSequenceUtilities.readBackwardSpaces(
					statementText, statementText.length());
			triggerEnd = PHPTextSequenceUtilities.readIdentifierStartIndex(
					statementText, triggerEnd, true);
			triggerEnd = PHPTextSequenceUtilities.readBackwardSpaces(
					statementText, triggerEnd);

			rhTypesCache.put(offset, CodeAssistUtils.getTypesFor(
					aContext.getSourceModule(), statementText, triggerEnd,
					offset));
		}
		return rhTypesCache.get(offset);
	}

	public IType[] getLeftHandType(ICompletionContext context, boolean isType) {
		AbstractCompletionContext aContext = (AbstractCompletionContext) context;
		int offset = aContext.getOffset();
		if (!rhTypesCache.containsKey(offset)) {

			TextSequence statementText = aContext.getStatementText();
			int triggerEnd = PHPTextSequenceUtilities.readBackwardSpaces(
					statementText, statementText.length());
			triggerEnd = PHPTextSequenceUtilities.readIdentifierStartIndex(
					statementText, triggerEnd, true);
			triggerEnd = PHPTextSequenceUtilities.readBackwardSpaces(
					statementText, triggerEnd);

			if (isType) {
				rhTypesCache.put(offset, CodeAssistUtils.getTypesFor(
						aContext.getSourceModule(), statementText, triggerEnd,
						offset));
			} else {
				rhTypesCache.put(offset, CodeAssistUtils.getTraitsFor(
						aContext.getSourceModule(), statementText, triggerEnd,
						offset));
			}
		}
		return rhTypesCache.get(offset);
	}

	/**
	 * Calculates super type hierarchy
	 * 
	 * @throws ModelException
	 */
	public ITypeHierarchy getSuperTypeHierarchy(IType type,
			IProgressMonitor monitor) throws ModelException {
		if (!superHierarchyCache.containsKey(type)) {
			superHierarchyCache.put(type, type.newSupertypeHierarchy(monitor));
		}
		return superHierarchyCache.get(type);
	}
}
