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
package org.eclipse.php.internal.core.typeinference.evaluators;

import java.util.*;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.declarations.TypeDeclaration;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.dltk.ast.statements.Block;
import org.eclipse.dltk.ast.statements.Statement;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.core.search.SearchEngine;
import org.eclipse.dltk.internal.core.SourceField;
import org.eclipse.dltk.ti.GoalState;
import org.eclipse.dltk.ti.IContext;
import org.eclipse.dltk.ti.ISourceModuleContext;
import org.eclipse.dltk.ti.goals.GoalEvaluator;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.internal.core.compiler.ast.nodes.*;
import org.eclipse.php.internal.core.mixin.PHPMixinModel;
import org.eclipse.php.internal.core.typeinference.PHPTypeInferenceUtils;
import org.eclipse.php.internal.core.typeinference.context.ContextFinder;
import org.eclipse.php.internal.core.typeinference.goals.GlobalVariableReferencesGoal;
import org.eclipse.php.internal.core.typeinference.goals.VariableDeclarationGoal;

/**
 * This evaluator finds all global declarations of the variable and produces {@link VariableDeclarationGoal} as a subgoal.
 */
public class GlobalVariableReferencesEvaluator extends GoalEvaluator {

	private List<IEvaluatedType> evaluated = new LinkedList<IEvaluatedType>();

	public GlobalVariableReferencesEvaluator(IGoal goal) {
		super(goal);
	}

	public IGoal[] init() {
		GlobalVariableReferencesGoal typedGoal = (GlobalVariableReferencesGoal) goal;

		IContext context = goal.getContext();
		ISourceModuleContext sourceModuleContext = null;
		if (context instanceof ISourceModuleContext) {
			sourceModuleContext = (ISourceModuleContext) context;
		}

		String variableName = typedGoal.getVariableName();

		boolean exploreOtherFiles = true;

		// Find all global variables from mixin
		IScriptProject scriptProject = sourceModuleContext.getSourceModule().getScriptProject();
		IDLTKSearchScope scope = SearchEngine.createSearchScope(scriptProject);
		IModelElement[] elements = PHPMixinModel.getInstance(scriptProject).getVariable(variableName, scope);
		Map<ISourceModule, SortedSet<ISourceRange>> offsets = new HashMap<ISourceModule, SortedSet<ISourceRange>>();

		Comparator<ISourceRange> sourceRangeComparator = new Comparator<ISourceRange>() {
			public int compare(ISourceRange o1, ISourceRange o2) {
				return o1.getOffset() - o2.getOffset();
			}
		};

		for (IModelElement element : elements) {
			if (element instanceof SourceField) {
				SourceField sourceField = (SourceField) element;
				ISourceModule sourceModule = sourceField.getSourceModule();
				if (!offsets.containsKey(sourceModule)) {
					offsets.put(sourceModule, new TreeSet<ISourceRange>(sourceRangeComparator));
				}
				try {
					offsets.get(sourceModule).add(sourceField.getSourceRange());
				} catch (ModelException e) {
					if (DLTKCore.DEBUG) {
						e.printStackTrace();
					}
				}
			}
		}

		List<IGoal> subGoals = new LinkedList<IGoal>();
		Iterator<ISourceModule> sourceModuleIt = offsets.keySet().iterator();
		while (sourceModuleIt.hasNext()) {
			ISourceModule sourceModule = sourceModuleIt.next();
			if (exploreOtherFiles || (sourceModuleContext != null && sourceModuleContext.getSourceModule().equals(sourceModule))) {

				ModuleDeclaration moduleDeclaration = SourceParserUtil.getModuleDeclaration(sourceModule);
				SortedSet<ISourceRange> fileOffsets = offsets.get(sourceModule);

				if (!fileOffsets.isEmpty()) {
					VariableDeclarationSearcher varSearcher = new VariableDeclarationSearcher(sourceModule, fileOffsets, variableName);
					try {
						moduleDeclaration.traverse(varSearcher);

						Map<IContext, LinkedList<ASTNode>> contextToDeclarationMap = varSearcher.getContextToDeclarationMap();
						Iterator<IContext> contextIt = contextToDeclarationMap.keySet().iterator();
						while (contextIt.hasNext()) {
							IContext c = contextIt.next();
							for (ASTNode declaration : contextToDeclarationMap.get(c)) {
								subGoals.add(new VariableDeclarationGoal(c, declaration));
							}
						}
					} catch (Exception e) {
						if (DLTKCore.DEBUG) {
							e.printStackTrace();
						}
					}
				}
			}
		}

		return subGoals.toArray(new IGoal[subGoals.size()]);
	}

	public Object produceResult() {
		return PHPTypeInferenceUtils.combineTypes(evaluated);
	}

	public IGoal[] subGoalDone(IGoal subgoal, Object result, GoalState state) {
		if (state != GoalState.RECURSIVE && result != null) {
			evaluated.add((IEvaluatedType) result);
		}
		return IGoal.NO_GOALS;
	}

	class VariableDeclarationSearcher extends ContextFinder {

		private final String variableName;
		private Map<IContext, LinkedList<ASTNode>> contextToDeclarations = new HashMap<IContext, LinkedList<ASTNode>>();
		private Stack<ASTNode> nodesStack = new Stack<ASTNode>();
		private int level = 0;
		private Iterator<ISourceRange> offsetsIt;
		private int currentStart;
		private int currentEnd;
		private boolean stopProcessing;

		public VariableDeclarationSearcher(ISourceModule sourceModule, SortedSet<ISourceRange> offsets, String variableName) {
			super(sourceModule);
			this.variableName = variableName;
			offsetsIt = offsets.iterator();
			setNextRange();
		}
		
		public IContext getContext() {
			return null;
		}

		public Map<IContext, LinkedList<ASTNode>> getContextToDeclarationMap() {
			return contextToDeclarations;
		}

		private void setNextRange() {
			if (offsetsIt.hasNext()) {
				ISourceRange range = offsetsIt.next();
				currentStart = range.getOffset();
				currentEnd = currentStart + range.getLength();
			} else {
				stopProcessing = true;
			}
		}

		private boolean interesting(ASTNode node) {
			return !stopProcessing && node.sourceStart() <= currentStart && node.sourceEnd() >= currentEnd;
		}

		private void increaseConditionalLevel() {
			++level;
		}

		private void decreaseConditionalLevel() {
			--level;
		}

		public boolean visit(Assignment node) throws Exception {
			if (!interesting(node)) {
				return false;
			}
			Expression variable = node.getVariable();
			if (variable instanceof VariableReference) {
				VariableReference variableReference = (VariableReference) variable;
				if (variableName.equals(variableReference.getName())) {

					LinkedList<ASTNode> decl = contextToDeclarations.get(contextStack.peek());

					// remove all declarations of this variable from the inner blocks
					while (decl.size() > level) {
						decl.removeLast();
					}
					decl.addLast(node.getValue());

					setNextRange();
				}
			}
			return visitGeneral(node);
		}

		public boolean visit(Block s) throws Exception {
			ASTNode parent = nodesStack.peek();
			if (parent instanceof CatchClause || parent instanceof IfStatement || parent instanceof ForStatement || parent instanceof ForEachStatement || parent instanceof SwitchCase || parent instanceof WhileStatement) {
				increaseConditionalLevel();
			}
			return visitGeneral(s);
		}

		public boolean visit(Statement node) throws Exception {
			if (!interesting(node)) {
				return false;
			}
			ASTNode parent = nodesStack.peek();
			if (parent instanceof IfStatement || parent instanceof ForStatement || parent instanceof ForEachStatement || parent instanceof SwitchCase || parent instanceof WhileStatement) {
				increaseConditionalLevel();
			}
			return visitGeneral(node);
		}

		public boolean visit(Expression node) throws Exception {
			if (!interesting(node)) {
				return false;
			}
			if (node instanceof Assignment) {
				return visit((Assignment) node);
			}
			if (node instanceof Block) {
				return visit((Block) node);
			}
			ASTNode parent = nodesStack.peek();
			if (parent instanceof ConditionalExpression) {
				increaseConditionalLevel();
			}
			return visitGeneral(node);
		}

		public boolean endvisit(Block s) throws Exception {
			ASTNode parent = nodesStack.peek();
			if (parent instanceof CatchClause || parent instanceof IfStatement || parent instanceof ForStatement || parent instanceof ForEachStatement || parent instanceof SwitchCase || parent instanceof WhileStatement) {
				decreaseConditionalLevel();
			}
			endvisitGeneral(s);
			return true;
		}

		public boolean endvisit(Statement s) throws Exception {
			if (interesting(s)) {
				ASTNode parent = nodesStack.peek();
				if (parent instanceof IfStatement || parent instanceof ForStatement || parent instanceof ForEachStatement || parent instanceof SwitchCase || parent instanceof WhileStatement) {
					decreaseConditionalLevel();
				}
			}
			return visitGeneral(s);
		}

		public boolean endvisit(Expression e) throws Exception {
			if (e instanceof Block) {
				return endvisit((Block) e);
			}
			if (interesting(e)) {
				ASTNode parent = nodesStack.peek();
				if (parent instanceof ConditionalExpression) {
					decreaseConditionalLevel();
				}
			}
			endvisitGeneral(e);
			return true;
		}

		public boolean visit(TypeDeclaration node) throws Exception {
			super.visit(node);
			if (!(node instanceof NamespaceDeclaration)) {
				contextToDeclarations.put(contextStack.peek(), new LinkedList<ASTNode>());
			}
			return visitGeneral(node);
		}

		public boolean visit(MethodDeclaration node) throws Exception {
			super.visit(node);
			contextToDeclarations.put(contextStack.peek(), new LinkedList<ASTNode>());
			return visitGeneral(node);
		}

		public boolean visit(ModuleDeclaration node) throws Exception {
			super.visit(node);
			contextToDeclarations.put(contextStack.peek(), new LinkedList<ASTNode>());
			return visitGeneral(node);
		}
		
		public boolean visitGeneral(ASTNode node) throws Exception {
			nodesStack.push(node);
			return interesting(node);
		}

		public void endvisitGeneral(ASTNode node) throws Exception {
			nodesStack.pop();
		}
	}
}
