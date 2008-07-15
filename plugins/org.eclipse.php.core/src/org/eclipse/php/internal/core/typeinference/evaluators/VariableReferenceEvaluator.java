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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.ast.declarations.TypeDeclaration;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.dltk.ast.statements.Block;
import org.eclipse.dltk.ast.statements.Statement;
import org.eclipse.dltk.evaluation.types.SimpleType;
import org.eclipse.dltk.ti.BasicContext;
import org.eclipse.dltk.ti.GoalState;
import org.eclipse.dltk.ti.IContext;
import org.eclipse.dltk.ti.ISourceModuleContext;
import org.eclipse.dltk.ti.goals.ExpressionTypeGoal;
import org.eclipse.dltk.ti.goals.GoalEvaluator;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.core.compiler.ast.nodes.*;
import org.eclipse.php.internal.core.typeinference.MethodContext;
import org.eclipse.php.internal.core.typeinference.PHPClassType;
import org.eclipse.php.internal.core.typeinference.PHPTypeInferenceUtils;
import org.eclipse.php.internal.core.typeinference.goals.GlobalVariableReferencesGoal;
import org.eclipse.php.internal.core.typeinference.goals.VariableDeclarationGoal;

/**
 * This evaluator finds all local variable declarations and produces the following sub-goals:
 *  {@link GlobalVariableReferencesGoal} or {@link VariableDeclarationGoal}
 */
public class VariableReferenceEvaluator extends GoalEvaluator {

	private List<IEvaluatedType> results = new ArrayList<IEvaluatedType>();

	public VariableReferenceEvaluator(IGoal goal) {
		super(goal);
	}

	public IGoal[] init() {
		VariableReference variableReference = (VariableReference) ((ExpressionTypeGoal) goal).getExpression();
		IContext context = goal.getContext();

		// Handle $this variable reference
		if (variableReference.getName().equals("$this")) {
			if (context instanceof MethodContext) {
				MethodContext methodContext = (MethodContext) context;
				IEvaluatedType instanceType = methodContext.getInstanceType();
				if (instanceType != null) {
					this.results.add(new PHPClassType(instanceType.getTypeName()));
				} else {
					this.results.add(new SimpleType(SimpleType.TYPE_NULL));
				}
				return IGoal.NO_GOALS;
			}
		}

		try {
			if (context instanceof ISourceModuleContext) {
				ISourceModuleContext typedContext = (ISourceModuleContext) context;
				ASTNode node = (context instanceof MethodContext) ? ((MethodContext)context).getMethodNode() : typedContext.getRootNode();
				VariableDeclarationSearcher varDecSearcher = new VariableDeclarationSearcher(variableReference);
				node.traverse(varDecSearcher);

				List<IGoal> subGoals = new LinkedList<IGoal>();

				LinkedList<ASTNode> declarations = varDecSearcher.getDeclarations();
				if (varDecSearcher.needsMergingWithGlobalScope() || (declarations.isEmpty() && context.getClass() == BasicContext.class)) {
					// collect all global variables, and merge results with existing declarations
					subGoals.add(new GlobalVariableReferencesGoal(context, variableReference.getName()));
				}
				for (ASTNode declaration : declarations) {
					subGoals.add(new VariableDeclarationGoal(context, declaration));
				}
				return subGoals.toArray(new IGoal[subGoals.size()]);
			}
		} catch (Exception e) {
			Logger.logException(e);
		}

		return IGoal.NO_GOALS;
	}

	public Object produceResult() {
		return PHPTypeInferenceUtils.combineTypes(results);
	}

	public IGoal[] subGoalDone(IGoal subgoal, Object result, GoalState state) {
		if (state != GoalState.RECURSIVE && result != null) {
			results.add((IEvaluatedType) result);
		}
		return IGoal.NO_GOALS;
	}

	class VariableDeclarationSearcher extends ASTVisitor {

		private final String variableName;
		private final int variableOffset;
		private LinkedList<ASTNode> declarations = new LinkedList<ASTNode>();
		private int level = 0;
		private Stack<ASTNode> nodesStack = new Stack<ASTNode>();
		private int seenGlobal = 0;
		private boolean mergeWithGlobalScope;

		public VariableDeclarationSearcher(VariableReference variableReference) {
			variableName = variableReference.getName();
			variableOffset = variableReference.sourceStart();
		}

		public LinkedList<ASTNode> getDeclarations() {
			int nullIdx;
			while ((nullIdx = declarations.indexOf(null)) != -1) {
				declarations.remove(nullIdx);
			}
			return declarations;
		}

		public boolean needsMergingWithGlobalScope() {
			return mergeWithGlobalScope;
		}

		public boolean visit(Assignment s) throws Exception {
			Expression variable = s.getVariable();
			if (variable instanceof VariableReference) {
				VariableReference variableReference = (VariableReference) variable;
				if (variableName.equals(variableReference.getName())) {
					if (level <= seenGlobal) { // if current level is lower than one where global statement has been seen - all global vars where overriden
						mergeWithGlobalScope = false;
					}

					// remove all declarations of this variable from the inner blocks
					while (declarations.size() > level) {
						declarations.removeLast();
					}
					declarations.addLast(s);
				}
			}
			return visitGeneral(s);
		}

		private void increaseConditionalLevel() {
			++level;
		}

		private void decreaseConditionalLevel() {
			--level;
		}

		public boolean visit(Block s) throws Exception {
			ASTNode parent = nodesStack.peek();
			if (parent instanceof CatchClause || parent instanceof IfStatement || parent instanceof ForStatement || parent instanceof ForEachStatement || parent instanceof SwitchCase || parent instanceof WhileStatement) {
				increaseConditionalLevel();
			}
			return visitGeneral(s);
		}

		public boolean visit(Statement s) throws Exception {
			if (!shouldContinue(s)) {
				return false;
			}
			if (s instanceof GlobalStatement) {
				GlobalStatement globalStatement = (GlobalStatement) s;
				for (Expression variable : globalStatement.getVariables()) {
					if (variable instanceof VariableReference) {
						VariableReference variableReference = (VariableReference) variable;
						if (variableReference.getName().equals(variableName)) {
							seenGlobal = level;
							mergeWithGlobalScope = true;

							// remove all declarations, since global statement overrides them
							for (int i = 0; i < declarations.size(); ++i) {
								declarations.set(i, null);
								return visitGeneral(s);
							}
						}
					}
				}
			}
			else if (s instanceof FormalParameter) {
				FormalParameter parameter = (FormalParameter) s;
				if (parameter.getName().equals(variableName)) {
					declarations.clear(); // declarations list should be empty, but we still remove everything (maybe user typed the same argument twice)
					declarations.addLast(s);
					return visitGeneral(s);
				}
			}
			else if (s instanceof CatchClause) {
				CatchClause catchClause = (CatchClause) s;
				if (catchClause.getVariable().getName().equals(variableName)) {
					declarations.clear();
					declarations.addLast(catchClause);
					return visitGeneral(s);
				}
			}
			ASTNode parent = nodesStack.peek();
			if (parent instanceof IfStatement || parent instanceof ForStatement || parent instanceof ForEachStatement || parent instanceof SwitchCase || parent instanceof WhileStatement) {
				increaseConditionalLevel();
			}
			return visitGeneral(s);
		}

		public boolean visit(Expression e) throws Exception {
			if (!shouldContinue(e)) {
				return false;
			}
			if (e instanceof Assignment) {
				return visit((Assignment) e);
			}
			if (e instanceof Block) {
				return visit((Block) e);
			}
			ASTNode parent = nodesStack.peek();
			if (parent instanceof ConditionalExpression) {
				increaseConditionalLevel();
			}
			return visitGeneral(e);
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
			ASTNode parent = nodesStack.peek();
			if (parent instanceof IfStatement || parent instanceof ForStatement || parent instanceof ForEachStatement || parent instanceof SwitchCase || parent instanceof WhileStatement) {
				decreaseConditionalLevel();
			}
			return visitGeneral(s);
		}

		public boolean endvisit(Expression e) throws Exception {
			if (e instanceof Block) {
				return endvisit((Block) e);
			}
			ASTNode parent = nodesStack.peek();
			if (parent instanceof ConditionalExpression) {
				decreaseConditionalLevel();
			}
			endvisitGeneral(e);
			return true;
		}

		public boolean visit(TypeDeclaration node) throws Exception {
			return false;
		}

		public boolean visit(MethodDeclaration node) throws Exception {
			if (nodesStack.isEmpty()) {
				return visitGeneral(node);
			}
			return false;
		}

		public boolean visitGeneral(ASTNode node) throws Exception {
			nodesStack.push(node);
			return shouldContinue(node);
		}

		public void endvisitGeneral(ASTNode node) throws Exception {
			nodesStack.pop();
		}

		private boolean shouldContinue(ASTNode node) {
			return node.sourceStart() < variableOffset;
		}
	}
}
