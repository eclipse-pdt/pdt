/*******************************************************************************
 * Copyright (c) 2014 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Dawid Pakuła - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.search;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.expressions.CallExpression;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.core.search.*;
import org.eclipse.dltk.ti.IContext;
import org.eclipse.dltk.ti.ISourceModuleContext;
import org.eclipse.dltk.ti.goals.ExpressionTypeGoal;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.core.compiler.PHPFlags;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.core.model.PerFileModelAccessCache;
import org.eclipse.php.internal.core.typeinference.IModelAccessCache;
import org.eclipse.php.internal.core.typeinference.IPHPTypeInferencer;
import org.eclipse.php.internal.core.typeinference.PHPTypeInferenceUtils;
import org.eclipse.php.internal.core.typeinference.PHPTypeInferencer;
import org.eclipse.php.internal.core.typeinference.context.IModelCacheContext;

public class PHPCallProcessor implements ICallProcessor {
	private SearchEngine engine = new SearchEngine();

	private class ContextFinder extends
			org.eclipse.php.internal.core.typeinference.context.ContextFinder {

		private IContext context;
		private ASTNode node;
		private int start;
		private int end;

		public ContextFinder(ISourceModule sourceModule, int start, int end) {
			super(sourceModule);
			this.start = start;
			this.end = end;
		}

		/**
		 * Returns found AST node
		 * 
		 * @return AST node
		 */
		public ASTNode getNode() {
			return node;
		}

		public boolean visitGeneral(ASTNode node) throws Exception {
			if (node.sourceStart() > end) {
				return false;
			}
			if (node.sourceEnd() < start) {
				return false;
			}
			if (node.sourceStart() <= start && node.sourceEnd() >= end) {
				if (!contextStack.isEmpty()) {
					this.context = contextStack.peek();
					this.node = node;
				}
			}
			// search inside - we are looking for minimal node
			return true;
		}

		@Override
		public IContext getContext() {
			return context;
		}
	}

	private class Requestor extends SearchRequestor {
		private boolean isGlobal = true;
		private IModelElement search;
		private IModelAccessCache cache;
		private IProgressMonitor monitor;

		public Map<Object, Object> result = new HashMap<Object, Object>();
		private ITypeHierarchy hierarchy;

		public Requestor(IModelElement search, IProgressMonitor monitor) {
			try {
				if ((search.getParent() instanceof IType && !PHPFlags
						.isNamespace(((IType) search.getParent()).getFlags()))) {
					isGlobal = false;
					IType type = (IType) search.getParent();
					hierarchy = type.newTypeHierarchy(monitor);
				} else {
					isGlobal = true;
				}
				this.search = search;
				this.monitor = monitor;
				cache = new PerFileModelAccessCache(
						(ISourceModule) search
								.getAncestor(IModelElement.SOURCE_MODULE));
			} catch (ModelException e) {
				Logger.logException(e);
			}
		}

		@Override
		public void acceptSearchMatch(SearchMatch match) throws CoreException {
			if ((match.getAccuracy() != SearchMatch.A_ACCURATE)) {
				return;
			}

			if (match.isInsideDocComment()) {
				return;
			}

			if (match.getElement() != null
					&& match.getElement() instanceof IModelElement) {
				IModelElement member = (IModelElement) match.getElement();
				ISourceModule module = (ISourceModule) member
						.getAncestor(IModelElement.SOURCE_MODULE);
				ModuleDeclaration moduleDeclaration = SourceParserUtil
						.getModuleDeclaration(module, null);
				ContextFinder finder = new ContextFinder(module,
						match.getOffset(), match.getOffset()
								+ match.getLength());
				try {
					moduleDeclaration.traverse(finder);
				} catch (Exception e) {
					Logger.logException(e);
				}
				IPHPTypeInferencer inferencer = new PHPTypeInferencer();
				if (!(finder.getNode() instanceof CallExpression)) {
					return;
				}
				CallExpression expression = (CallExpression) finder.getNode();
				if (isGlobal && expression.getReceiver() == null) {
					result.put(expression.getCallName(), member);
				} else if (!isGlobal && expression.getReceiver() != null) {
					ISourceModuleContext context = (ISourceModuleContext) finder
							.getContext();
					if (context instanceof IModelCacheContext) {
						((IModelCacheContext) context).setCache(cache);
					}
					IEvaluatedType evaluateType = inferencer
							.evaluateType(new ExpressionTypeGoal(finder
									.getContext(), expression.getReceiver()));
					IType[] modelElements = PHPTypeInferenceUtils
							.getModelElements(evaluateType, context,
									match.getOffset(), cache);
					if (modelElements != null) {
						for (IType type : modelElements) {
							if (!hierarchy.contains(type)) {
								continue;
							}

							result.put(expression.getCallName(), member);
							return;
						}
					}
				}
			}
		}

	}

	@Override
	public Map process(IModelElement parent, IModelElement member,
			IDLTKSearchScope scope, IProgressMonitor monitor) {
		// TODO: Detect and ignore lambdas
		SearchPattern pattern = SearchPattern.createPattern(
				member.getElementName(), IDLTKSearchConstants.METHOD,
				IDLTKSearchConstants.REFERENCES, SearchPattern.R_EXACT_MATCH
						| SearchPattern.R_ERASURE_MATCH,
				scope.getLanguageToolkit());
		Requestor req = new Requestor(member, monitor);
		try {
			engine.search(pattern, new SearchParticipant[] { SearchEngine
					.getDefaultSearchParticipant() }, scope, req, monitor);
		} catch (CoreException e) {
			Logger.logException(e);
		}

		return req.result;
	}

}
