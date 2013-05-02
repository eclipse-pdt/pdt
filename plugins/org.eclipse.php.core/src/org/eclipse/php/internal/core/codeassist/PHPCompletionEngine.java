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

import java.util.*;

import org.eclipse.core.runtime.IPath;
import org.eclipse.dltk.codeassist.ScriptCompletionEngine;
import org.eclipse.dltk.compiler.env.IModuleSource;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.internal.core.ModelManager;
import org.eclipse.dltk.internal.core.SourceRange;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.core.codeassist.ICompletionContextResolver;
import org.eclipse.php.core.codeassist.ICompletionStrategy;
import org.eclipse.php.core.codeassist.ICompletionStrategyFactory;
import org.eclipse.php.core.compiler.PHPFlags;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.codeassist.contexts.CompletionContextResolver;
import org.eclipse.php.internal.core.codeassist.strategies.CompletionStrategyFactory;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;

/**
 * Completion engine for PHP. This engine uses structured document for defining
 * the completion context; AST is not used since it lacks error recovery for all
 * cases.
 * 
 * @author michael
 */
public class PHPCompletionEngine extends ScriptCompletionEngine implements
		ICompletionReporter {

	private int relevanceKeyword;
	private int relevanceMethod;
	private int relevanceClass;
	private int relevanceVar;
	private int relevanceConst;
	private Map<? super Object, Object> processedElements = new HashMap<Object, Object>();
	private Set<? super Object> processedPaths = new HashSet<Object>();
	private Set<IField> processedFields = new TreeSet<IField>(
			new Comparator<IField>() {
				public int compare(IField f1, IField f2) {
					// filter duplications of variables
					if (PHPModelUtils.isSameField((IField) f1, (IField) f2)) {
						return 0;
					}
					return f1.getElementName().compareTo(f2.getElementName());
				}
			});

	IModuleSource module;

	public void complete(IModuleSource module, int position, int i) {
		complete(module, position, i, true);
	}

	public void complete(IModuleSource module, int position, int i,
			boolean waitForBuilder) {
		if (!PHPCorePlugin.toolkitInitialized) {
			return;
		}
		if (requestor instanceof IPHPCompletionRequestor) {
			((IPHPCompletionRequestor) requestor).setOffset(offset);
		}
		if (waitForBuilder) {
			ModelManager.getModelManager().getIndexManager().waitUntilReady();
		}

		this.module = module;
		relevanceKeyword = RELEVANCE_KEYWORD;
		relevanceMethod = RELEVANCE_METHOD;
		relevanceClass = RELEVANCE_CLASS;
		relevanceVar = RELEVANCE_VAR;
		relevanceConst = RELEVANCE_CONST;

		try {
			ICompletionContextResolver[] contextResolvers = CompletionContextResolver
					.getActive();
			ICompletionStrategyFactory[] strategyFactories = CompletionStrategyFactory
					.getActive();

			CompletionCompanion companion = new CompletionCompanion();
			org.eclipse.dltk.core.ISourceModule sourceModule = (org.eclipse.dltk.core.ISourceModule) module
					.getModelElement();

			for (ICompletionContextResolver resolver : contextResolvers) {
				ICompletionContext[] contexts = resolver.resolve(sourceModule,
						position, requestor, companion);

				if (contexts != null && contexts.length > 0) {
					for (ICompletionStrategyFactory factory : strategyFactories) {
						ICompletionStrategy[] strategies = factory
								.create(contexts);

						if (strategies != null && strategies.length > 0) {
							for (ICompletionStrategy strategy : strategies) {
								strategy.init(companion);
								try {
									strategy.apply(this);
								} catch (Exception e) {
									PHPCorePlugin.log(e);
								}
							}
						}
					}
				}
			}
		} finally {
			processedElements.clear();
			processedPaths.clear();
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

	public void reportField(IField field, String suffix,
			SourceRange replaceRange, boolean removeDollar) {
		reportField(field, suffix, replaceRange, removeDollar, 0);
	}

	public void reportField(IField field, String suffix,
			SourceRange replaceRange, boolean removeDollar, int subRelevance) {
		reportField(field, suffix, replaceRange, removeDollar, subRelevance,
				null);
	}

	public void reportField(IField field, String suffix,
			SourceRange replaceRange, boolean removeDollar, int subRelevance,
			Object extraInfo) {
		if (processedFields.contains(field)) {
			return;
		}
		processedFields.add(field);

		int flags = 0;
		try {
			flags = field.getFlags();
		} catch (ModelException e) {
			PHPCorePlugin.log(e);
		}
		int relevance = PHPFlags.isConstant(flags) ? relevanceConst
				: relevanceVar;
		relevance += subRelevance;

		noProposal = false;

		if (!requestor.isIgnored(CompletionProposal.FIELD_REF)) {

			CompletionProposal proposal = createProposal(
					CompletionProposal.FIELD_REF, actualCompletionPosition);
			proposal.setName(field.getElementName());

			String completion = field.getElementName() + suffix;
			if (removeDollar && completion.startsWith("$")) { //$NON-NLS-1$
				completion = completion.substring(1);
			}
			proposal.setCompletion(completion);
			proposal.setExtraInfo(extraInfo);
			proposal.setModelElement(field);
			proposal.setFlags(flags);
			proposal.setRelevance(relevance);
			proposal.setReplaceRange(replaceRange.getOffset(),
					replaceRange.getOffset() + replaceRange.getLength());

			this.requestor.accept(proposal);

			if (DEBUG) {
				this.printDebug(proposal);
			}
		}
	}

	public void reportField(IField field, String completion,
			SourceRange replaceRange, int subRelevance) {
		if (processedFields.contains(field)) {
			return;
		}
		processedFields.add(field);

		int flags = 0;
		try {
			flags = field.getFlags();
		} catch (ModelException e) {
			PHPCorePlugin.log(e);
		}
		int relevance = PHPFlags.isConstant(flags) ? relevanceConst
				: relevanceVar;
		relevance += subRelevance;

		noProposal = false;

		if (!requestor.isIgnored(CompletionProposal.FIELD_REF)) {

			CompletionProposal proposal = createProposal(
					CompletionProposal.FIELD_REF, actualCompletionPosition);
			proposal.setName(field.getElementName());

			proposal.setCompletion(completion);

			proposal.setModelElement(field);
			proposal.setFlags(flags);
			proposal.setRelevance(relevance);
			proposal.setReplaceRange(replaceRange.getOffset(),
					replaceRange.getOffset() + replaceRange.getLength());

			this.requestor.accept(proposal);

			if (DEBUG) {
				this.printDebug(proposal);
			}
		}
	}

	public void reportKeyword(String keyword, String suffix,
			SourceRange replaceRange) {
		reportKeyword(keyword, suffix, replaceRange, 0);
	}

	public void reportKeyword(String keyword, String suffix,
			SourceRange replaceRange, int subRelevance) {
		if (processedElements.containsKey(keyword)) {
			return;
		}
		processedElements.put(keyword, keyword);

		noProposal = false;

		if (!requestor.isIgnored(CompletionProposal.FIELD_REF)) {

			CompletionProposal proposal = createProposal(
					CompletionProposal.KEYWORD, actualCompletionPosition);
			proposal.setName(keyword);
			proposal.setCompletion(keyword + suffix);
			proposal.setRelevance(relevanceKeyword + subRelevance);
			proposal.setReplaceRange(replaceRange.getOffset(),
					replaceRange.getOffset() + replaceRange.getLength());

			this.requestor.accept(proposal);

			if (DEBUG) {
				this.printDebug(proposal);
			}
		}
	}

	public void reportMethod(IMethod method, String suffix,
			SourceRange replaceRange, Object extraInfo) {
		reportMethod(method, suffix, replaceRange, extraInfo, 0);
	}

	public void reportMethod(IMethod method, String suffix,
			SourceRange replaceRange, Object extraInfo, int subRelevance) {
		if (processedElements.containsKey(method)
				&& ((IMethod) processedElements.get(method)).getParent()
						.getClass() == method.getParent().getClass()) {

			return;
		}
		processedElements.put(method, method);

		noProposal = false;

		if (!requestor.isIgnored(CompletionProposal.METHOD_DECLARATION)) {

			CompletionProposal proposal = createProposal(
					CompletionProposal.METHOD_DECLARATION,
					actualCompletionPosition);
			proposal.setExtraInfo(extraInfo);
			// show method parameter names:
			String[] params = null;
			try {
				params = method.getParameterNames();
			} catch (ModelException e) {
				PHPCorePlugin.log(e);
			}
			if (params != null && params.length > 0) {
				proposal.setParameterNames(params);
			}

			String elementName = method.getElementName();
			String completionName = elementName;

			proposal.setModelElement(method);
			proposal.setName(elementName);

			int relevance = relevanceMethod + subRelevance;
			proposal.setCompletion((completionName + suffix));

			try {
				proposal.setIsConstructor(elementName.equals("__construct") //$NON-NLS-1$
						|| method.isConstructor());
				proposal.setFlags(method.getFlags());
			} catch (ModelException e) {
				if (DEBUG) {
					e.printStackTrace();
				}
			}

			proposal.setReplaceRange(replaceRange.getOffset(),
					replaceRange.getOffset() + replaceRange.getLength());
			proposal.setRelevance(relevance);

			this.requestor.accept(proposal);

			if (DEBUG) {
				this.printDebug(proposal);
			}
		}

	}

	public void reportMethod(IMethod method, String suffix,
			SourceRange replaceRange) {
		reportMethod(method, suffix, replaceRange, null);
	}

	public void reportType(IType type, String suffix, SourceRange replaceRange) {
		reportType(type, suffix, replaceRange, null);
	}

	public void reportType(IType type, String suffix, SourceRange replaceRange,
			Object extraInfo) {
		reportType(type, suffix, replaceRange, extraInfo, 0);
	}

	public void reportType(IType type, String suffix, SourceRange replaceRange,
			Object extraInfo, int subRelevance) {
		if (processedElements.containsKey(type)
				&& processedElements.get(type).getClass() == type.getClass()) {
			return;
		}
		processedElements.put(type, type);

		noProposal = false;

		if (!requestor.isIgnored(CompletionProposal.TYPE_REF)) {

			CompletionProposal proposal = createProposal(
					CompletionProposal.TYPE_REF, actualCompletionPosition);
			proposal.setExtraInfo(extraInfo);
			// Support parameter names for constructor:
			if (requestor.isContextInformationMode()) {
				try {
					for (IMethod method : type.getMethods()) {
						if (method.isConstructor()) {
							String[] params = method.getParameterNames();
							if (params != null && params.length > 0) {
								proposal.setParameterNames(params);
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
			proposal.setName(elementName);

			int relevance = relevanceClass + subRelevance;
			proposal.setCompletion(completionName + suffix);

			try {
				proposal.setFlags(type.getFlags());
			} catch (ModelException e) {
				PHPCorePlugin.log(e);
			}

			proposal.setReplaceRange(replaceRange.getOffset(),
					replaceRange.getOffset() + replaceRange.getLength());
			proposal.setRelevance(relevance);

			this.requestor.accept(proposal);

			if (DEBUG) {
				this.printDebug(proposal);
			}
		}
	}

	public void reportResource(IModelElement model, IPath relative,
			String suffix, SourceRange replaceRange) {
		if (processedElements.containsKey(model)
				|| processedPaths.contains(relative)) {
			return;
		}
		processedElements.put(model, model);
		processedPaths.add(relative);
		noProposal = false;

		CompletionProposal proposal = null;
		if (model.getElementType() == IModelElement.SCRIPT_FOLDER
				&& !requestor.isIgnored(CompletionProposal.PACKAGE_REF)) {
			proposal = createProposal(CompletionProposal.PACKAGE_REF,
					actualCompletionPosition);
		} else if (model.getElementType() == IModelElement.PROJECT_FRAGMENT) {
			proposal = createProposal(CompletionProposal.PACKAGE_REF,
					actualCompletionPosition);
		} else if (!requestor.isIgnored(CompletionProposal.KEYWORD)) {
			proposal = createProposal(CompletionProposal.KEYWORD,
					actualCompletionPosition);
		}

		proposal.setName(relative.toString());
		proposal.setCompletion((relative.toString() + suffix));
		proposal.setRelevance(relevanceKeyword);
		proposal.setReplaceRange(replaceRange.getOffset(),
				replaceRange.getOffset() + replaceRange.getLength());
		proposal.setModelElement(model);

		this.requestor.accept(proposal);
		if (DEBUG) {
			this.printDebug(proposal);
		}
	}

	protected int getEndOfEmptyToken() {
		return 0;
	}

	protected String processMethodName(IMethod method, String token) {
		return method.getElementName();
	}

	protected String processTypeName(IType type, String token) {
		return type.getElementName();
	}

	public IModuleSource getModule() {
		return module;
	}

}
