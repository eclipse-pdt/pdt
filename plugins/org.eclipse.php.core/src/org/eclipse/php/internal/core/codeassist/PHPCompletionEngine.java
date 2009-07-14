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

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.IPath;
import org.eclipse.dltk.codeassist.IAssistParser;
import org.eclipse.dltk.codeassist.ScriptCompletionEngine;
import org.eclipse.dltk.compiler.env.ISourceModule;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.internal.core.SourceRange;
import org.eclipse.php.internal.core.PHPCorePlugin;
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
	private Set<? super Object> processedElements = new HashSet<Object>();

	public void complete(ISourceModule module, int position, int i) {

		relevanceKeyword = RELEVANCE_KEYWORD;
		relevanceMethod = RELEVANCE_METHOD;
		relevanceClass = RELEVANCE_CLASS;
		relevanceVar = RELEVANCE_VAR;
		relevanceConst = RELEVANCE_CONST;
		
		try {
			ICompletionContextResolver[] contextResolvers = CompletionContextResolver.getActive();
			ICompletionStrategyFactory[] strategyFactories = CompletionStrategyFactory.getActive();
			
			CompletionCompanion companion = new CompletionCompanion();
			org.eclipse.dltk.core.ISourceModule sourceModule = (org.eclipse.dltk.core.ISourceModule) module.getModelElement();

			for (ICompletionContextResolver resolver : contextResolvers) {
				ICompletionContext[] contexts = resolver.resolve(sourceModule, position, requestor, companion);

				if (contexts != null) {
					for (ICompletionStrategyFactory factory : strategyFactories) {
						ICompletionStrategy[] strategies = factory.create(contexts);
						
						if (strategies != null) {
							for (ICompletionStrategy strategy : strategies) {
								strategy.init(companion);
								
								try {
									strategy.apply(this);
								} catch (Exception e) {
									PHPCorePlugin.log(e);
									return; // stop processing other completions
								}
							}
						}
					}
				}
			}
		} finally {
			processedElements.clear();
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

	public void reportField(IField field, String suffix, SourceRange replaceRange, boolean removeDollar) {
		if (processedElements.contains(field)) {
			return;
		}
		processedElements.add(field);

		int flags = 0;
		try {
			flags = field.getFlags();
		} catch (ModelException e) {
			PHPCorePlugin.log(e);
		}
		int relevance = PHPFlags.isConstant(flags) ? nextConstantRelevance() : nextVariableRelevance();

		noProposal = false;

		if (!requestor.isIgnored(CompletionProposal.FIELD_REF)) {

			CompletionProposal proposal = createProposal(CompletionProposal.FIELD_REF, actualCompletionPosition);
			proposal.setName(field.getElementName().toCharArray());

			String completion = field.getElementName() + suffix;
			if (removeDollar && completion.startsWith("$")) {
				completion = completion.substring(1);
			}
			proposal.setCompletion(completion.toCharArray());

			proposal.setModelElement(field);
			proposal.setFlags(flags);
			proposal.setRelevance(relevance);
			proposal.setReplaceRange(replaceRange.getOffset(), replaceRange.getOffset() + replaceRange.getLength());

			this.requestor.accept(proposal);

			if (DEBUG) {
				this.printDebug(proposal);
			}
		}
	}

	public void reportKeyword(String keyword, String suffix, SourceRange replaceRange) {
		if (processedElements.contains(keyword)) {
			return;
		}
		processedElements.add(keyword);

		noProposal = false;

		if (!requestor.isIgnored(CompletionProposal.FIELD_REF)) {

			CompletionProposal proposal = createProposal(CompletionProposal.KEYWORD, actualCompletionPosition);
			proposal.setName(keyword.toCharArray());
			proposal.setCompletion((keyword + suffix).toCharArray());
			proposal.setRelevance(nextKeywordRelevance());
			proposal.setReplaceRange(replaceRange.getOffset(), replaceRange.getOffset() + replaceRange.getLength());

			this.requestor.accept(proposal);

			if (DEBUG) {
				this.printDebug(proposal);
			}
		}
	}

	public void reportMethod(IMethod method, String suffix, SourceRange replaceRange) {
		if (processedElements.contains(method)) {
			return;
		}
		processedElements.add(method);

		noProposal = false;

		if (!requestor.isIgnored(CompletionProposal.METHOD_DECLARATION)) {

			CompletionProposal proposal = createProposal(CompletionProposal.METHOD_DECLARATION, actualCompletionPosition);

			// show method parameter names:
			String[] params = null;
			try {
				params = method.getParameters();
			} catch (ModelException e) {
				PHPCorePlugin.log(e);
			}
			if (params != null && params.length > 0) {
				char[][] args = new char[params.length][];
				for (int i = 0; i < params.length; ++i) {
					args[i] = params[i].toCharArray();
				}
				proposal.setParameterNames(args);
			}

			String elementName = method.getElementName();
			String completionName = elementName;

			proposal.setModelElement(method);
			proposal.setName(elementName.toCharArray());

			int relevance = nextMethodRelevance();

			if (method instanceof FakeGroupMethod) {
				// remove the trailing '*' from the group name
				completionName = elementName.substring(0, elementName.length() - 1);

				// put the group to the top of list
				relevance = RELEVANCE_KEYWORD + 1;
			}
			proposal.setCompletion((completionName + suffix).toCharArray());

			try {
				proposal.setIsConstructor(elementName.equals("__construct") || method.isConstructor());
				proposal.setFlags(method.getFlags());
			} catch (ModelException e) {
				if (DEBUG) {
					e.printStackTrace();
				}
			}

			proposal.setReplaceRange(replaceRange.getOffset(), replaceRange.getOffset() + replaceRange.getLength());
			proposal.setRelevance(relevance);

			this.requestor.accept(proposal);

			if (DEBUG) {
				this.printDebug(proposal);
			}
		}

	}

	public void reportType(IType type, String suffix, SourceRange replaceRange) {
		if (processedElements.contains(type)) {
			return;
		}
		processedElements.add(type);

		noProposal = false;

		if (!requestor.isIgnored(CompletionProposal.TYPE_REF)) {

			CompletionProposal proposal = createProposal(CompletionProposal.TYPE_REF, actualCompletionPosition);

			// Support parameter names for constructor:
			if (requestor.isContextInformationMode()) {
				try {
					for (IMethod method : type.getMethods()) {
						if (method.isConstructor()) {
							String[] params = method.getParameters();
							if (params != null && params.length > 0) {
								char[][] args = new char[params.length][];
								for (int i = 0; i < params.length; ++i) {
									args[i] = params[i].toCharArray();
								}
								proposal.setParameterNames(args);
							}
							break;
						}
					}
				} catch (ModelException e) {
					PHPCorePlugin.log(e);
				}
			}

			String elementName = type.getElementName();
			String completionName = elementName;

			proposal.setModelElement(type);
			proposal.setName(elementName.toCharArray());

			int relevance = nextClassRelevance();

			if (type instanceof FakeGroupType) {
				// remove '*' from the fake group type name
				completionName = elementName.substring(0, elementName.length() - 1);

				// put groups on the top of list
				relevance = RELEVANCE_KEYWORD + 1;
			}
			proposal.setCompletion((completionName + suffix).toCharArray());

			try {
				proposal.setFlags(type.getFlags());
			} catch (ModelException e) {
				PHPCorePlugin.log(e);
			}

			proposal.setReplaceRange(replaceRange.getOffset(), replaceRange.getOffset() + replaceRange.getLength());
			proposal.setRelevance(relevance);

			this.requestor.accept(proposal);

			if (DEBUG) {
				this.printDebug(proposal);
			}
		}
	}

	public void reportResource(IModelElement model, IPath relative, String suffix, SourceRange replaceRange) {
		if (processedElements.contains(model)) {
			return;
		}
		processedElements.add(model);
		noProposal = false;

		CompletionProposal proposal = null;
		if (model.getElementType() == IModelElement.SCRIPT_FOLDER && !requestor.isIgnored(CompletionProposal.PACKAGE_REF)) {
			proposal = createProposal(CompletionProposal.PACKAGE_REF, actualCompletionPosition);
		} else if (!requestor.isIgnored(CompletionProposal.KEYWORD)) {
			proposal = createProposal(CompletionProposal.KEYWORD, actualCompletionPosition);
		}

		proposal.setName(relative.toString().toCharArray());
		proposal.setCompletion((relative.toString() + suffix).toCharArray());
		proposal.setRelevance(nextKeywordRelevance());
		proposal.setReplaceRange(replaceRange.getOffset(), replaceRange.getOffset() + replaceRange.getLength());
		proposal.setModelElement(model);

		this.requestor.accept(proposal);
		if (DEBUG) {
			this.printDebug(proposal);
		}
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
