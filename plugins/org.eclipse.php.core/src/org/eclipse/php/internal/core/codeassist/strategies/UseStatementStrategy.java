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

import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.core.search.SearchEngine;
import org.eclipse.dltk.core.search.SearchPattern;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.internal.core.codeassist.CodeAssistUtils;
import org.eclipse.php.internal.core.codeassist.contexts.AbstractCompletionContext;
import org.eclipse.php.internal.core.codeassist.contexts.ICompletionContext;
import org.eclipse.php.internal.core.compiler.PHPFlags;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;

/**
 * This strategy completes namespaces 
 * @author michael
 */
public class UseStatementStrategy extends GlobalTypesStrategy {

	public UseStatementStrategy(ICompletionContext context) {
		super(context, null);
	}
	
	public String getNSSuffix(AbstractCompletionContext abstractContext) {
		return ""; //$NON-NLS-1$
	}	

	public String getSuffix(AbstractCompletionContext abstractContext) {
		return ""; //$NON-NLS-1$
	}	
	
	@Override
	protected IType[] getTypes(AbstractCompletionContext context) throws BadLocationException {
		int mask = CodeAssistUtils.EXCLUDE_CONSTANTS | CodeAssistUtils.EXCLUDE_VARIABLES;
		if (context.getCompletionRequestor().isContextInformationMode()) {
			mask |= CodeAssistUtils.EXACT_NAME;
		}
		String prefix = context.getPrefix();
		if("\\".equals(prefix)) {
			return CodeAssistUtils.getGlobalTypes(context.getSourceModule(), "", mask);
		}
		if(prefix.length() > 1 && prefix.charAt(0) == '\\') {
			return CodeAssistUtils.getGlobalTypes(context.getSourceModule(), prefix.substring(1), mask);
		}
		
		return CodeAssistUtils.getGlobalTypes(context.getSourceModule(), prefix, mask);
	}
}
