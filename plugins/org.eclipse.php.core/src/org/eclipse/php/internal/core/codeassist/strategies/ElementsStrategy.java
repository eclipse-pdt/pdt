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
package org.eclipse.php.internal.core.codeassist.strategies;

import org.eclipse.dltk.core.*;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.core.codeassist.IElementFilter;
import org.eclipse.php.core.compiler.PHPFlags;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.core.codeassist.contexts.AbstractCompletionContext;

/**
 * This strategy completes elements: classes, functions, variables, etc...
 * 
 * @author michael
 */
public abstract class ElementsStrategy extends AbstractCompletionStrategy {

	public ElementsStrategy(ICompletionContext context, IElementFilter elementFilter) {
		super(context, elementFilter);
	}

	public ElementsStrategy(ICompletionContext context) {
		super(context);
	}

	protected boolean useCurrentNamespace = true;

	public ISourceRange getReplacementRangeForMember(AbstractCompletionContext context) throws BadLocationException {
		ISourceRange basicRange = getReplacementRange(context);
		int move = (context.isAbsoluteName() ? 1 : 0);
		String namespacePrefix = context.getNamespaceName();
		if (namespacePrefix != null) {
			move += 1 + namespacePrefix.length();
		}
		return new SourceRange(basicRange.getOffset() + move, basicRange.getLength() - move);
	}

	public int getRelevance(String currentNamespace, IMember model) {
		IModelElement parent = model.getParent();
		try {
			if (model.getElementType() == IMember.TYPE && PHPFlags.isNamespace(model.getFlags())) {
				return -10;
			}
			if (currentNamespace == null) {
				if (parent.getElementType() == IMember.SOURCE_MODULE) {
					return 10;
				}
			} else {
				if (parent.getElementName().equalsIgnoreCase(currentNamespace)) {
					return 10;
				}
			}
		} catch (ModelException e) {
			Logger.logException(e);
		}

		return 0;
	}
}
