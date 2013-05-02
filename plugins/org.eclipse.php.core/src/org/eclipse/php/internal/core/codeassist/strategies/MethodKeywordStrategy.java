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

import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.ITypeHierarchy;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.core.codeassist.IElementFilter;
import org.eclipse.php.core.compiler.PHPFlags;
import org.eclipse.php.internal.core.codeassist.contexts.GlobalMethodStatementContext;
import org.eclipse.php.internal.core.language.keywords.PHPKeywords;
import org.eclipse.php.internal.core.language.keywords.PHPKeywords.KeywordData;

/**
 * This strategy completes keywords that can be shown in a method body
 * completion context
 * 
 * @author vadim.p
 */
public class MethodKeywordStrategy extends KeywordsStrategy {

	/**
	 * @param context
	 * @param elementFilter
	 */
	public MethodKeywordStrategy(ICompletionContext context,
			IElementFilter elementFilter) {
		super(context, elementFilter);
	}

	/**
	 * @param context
	 */
	public MethodKeywordStrategy(ICompletionContext context) {
		super(context);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.internal.core.codeassist.strategies.KeywordsStrategy#
	 * filterKeyword
	 * (org.eclipse.php.internal.core.language.keywords.PHPKeywords.KeywordData)
	 */
	@Override
	protected boolean filterKeyword(KeywordData keyword) {
		// if the class does not have parent
		if ((keyword.context & PHPKeywords.METHOD_BODY) != 0
				&& keyword.name.equals("parent")) { //$NON-NLS-1$
			ICompletionContext context = getContext();
			if (context instanceof GlobalMethodStatementContext) {
				GlobalMethodStatementContext globalContext = (GlobalMethodStatementContext) context;
				IType type = globalContext.getEnclosingType();
				try {
					if (PHPFlags.isClass(type.getFlags())) {
						ITypeHierarchy hierarchy = getCompanion()
								.getSuperTypeHierarchy(type, null);
						IType[] superTypes = hierarchy.getAllSupertypes(type);
						for (IType superType : superTypes) {
							if (PHPFlags.isClass(superType.getFlags())) {
								return false;
							}
						}
					}
				} catch (ModelException e) {
				}
				return true;
			}
		}
		return (keyword.context & PHPKeywords.METHOD_BODY) == 0;
	}

}
