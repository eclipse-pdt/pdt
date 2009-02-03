/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.codeassist;

import org.eclipse.dltk.codeassist.IAssistParser;
import org.eclipse.dltk.codeassist.ScriptCompletionEngine;
import org.eclipse.dltk.compiler.env.ISourceModule;
import org.eclipse.dltk.core.*;
import org.eclipse.php.internal.core.codeassist.contexts.CompletionContextResolver;
import org.eclipse.php.internal.core.codeassist.contexts.ICompletionContext;
import org.eclipse.php.internal.core.codeassist.contexts.ICompletionContextResolver;
import org.eclipse.php.internal.core.codeassist.strategies.CompletionStrategyFactory;
import org.eclipse.php.internal.core.codeassist.strategies.ICompletionStrategy;
import org.eclipse.php.internal.core.codeassist.strategies.ICompletionStrategyFactory;
import org.eclipse.php.internal.core.compiler.PHPFlags;

/**
 * Completion engine for PHP. This engine uses structured document for defining the completion
 * context; AST is not used since it lacks error recovery for all cases.
 * 
 * @author michael
 */
public class PHPCompletionEngine extends ScriptCompletionEngine implements ICompletionReporter {
	
	private int relevanceKeyword;
	private int relevanceMethod;
	private int relevanceClass;
	private int relevanceVar;
	private int relevanceConst;

	public void complete(ISourceModule module, int position, int i) {
		
		relevanceKeyword = RELEVANCE_KEYWORD;
		relevanceMethod = RELEVANCE_METHOD;
		relevanceClass = RELEVANCE_CLASS;
		relevanceVar = RELEVANCE_VAR;
		relevanceConst = RELEVANCE_CONST;
		
		try {
			ICompletionContextResolver resolver = CompletionContextResolver.getActive();
			ICompletionContext context = resolver.resolve(
				(org.eclipse.dltk.core.ISourceModule) module.getModelElement(), position, requestor);
			
			if (context != null) {
				ICompletionStrategyFactory completionStrategyFactory = CompletionStrategyFactory.getActive();
				ICompletionStrategy completionStrategy = completionStrategyFactory.create(context);
				if (completionStrategy != null) {
					completionStrategy.apply(context, this);
				}
			}
		} catch (Exception e) {
			if (DLTKCore.DEBUG) {
				e.printStackTrace();
			}
		}
	}
	
	private int nextKeywordRelevance() {
		int relevance = relevanceKeyword--;
		if (relevance < 1) {
			relevance = 1;
		}
		return relevance;
	}
	
	private int nextMethodRelevance() {
		int relevance = relevanceMethod--;
		if (relevance < 1) {
			relevance = 1;
		}
		return relevance;
	}
	
	private int nextClassRelevance() {
		int relevance = relevanceClass--;
		if (relevance < 1) {
			relevance = 1;
		}
		return relevance;
	}
	
	private int nextVariableRelevance() {
		int relevance = relevanceVar--;
		if (relevance < 1) {
			relevance = 1;
		}
		return relevance;
	}
	
	private int nextConstantRelevance() {
		int relevance = relevanceConst--;
		if (relevance < 1) {
			relevance = 1;
		}
		return relevance;
	}

	public void reportField(IField field, String suffix) {
		int flags = 0;
		try {
			flags = field.getFlags();
		} catch (ModelException e) {
			if (DLTKCore.DEBUG_COMPLETION) {
				e.printStackTrace();
			}
		}
		int relevance = PHPFlags.isConstant(flags) ? nextConstantRelevance() : nextVariableRelevance(); 
	}
	
	public void reportKeyword(String keyword, String suffix) {
		int relevance = nextKeywordRelevance();
	}

	public void reportMethod(IMethod method, String suffix) {
		int relevance = nextVariableRelevance();		
	}

	public void reportType(IType type, String suffix) {
	}
	
	protected int getEndOfEmptyToken() {
		return 0;
	}

	protected String processFieldName(IField field, String token) {
		return field.getElementName();
	}

	protected String processMethodName(IMethod method, String token) {
		return method.getElementName();
	}

	protected String processTypeName(IType type, String token) {
		return type.getElementName();
	}

	public IAssistParser getParser() {
		return null;
	}
}
