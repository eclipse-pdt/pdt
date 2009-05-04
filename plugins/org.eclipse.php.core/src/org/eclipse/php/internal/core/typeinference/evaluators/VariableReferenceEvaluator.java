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

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.dltk.ast.statements.Statement;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.evaluation.types.SimpleType;
import org.eclipse.dltk.ti.GoalState;
import org.eclipse.dltk.ti.IContext;
import org.eclipse.dltk.ti.ISourceModuleContext;
import org.eclipse.dltk.ti.goals.ExpressionTypeGoal;
import org.eclipse.dltk.ti.goals.GoalEvaluator;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.internal.core.compiler.ast.nodes.Assignment;
import org.eclipse.php.internal.core.compiler.ast.nodes.ForEachStatement;
import org.eclipse.php.internal.core.compiler.ast.nodes.GlobalStatement;
import org.eclipse.php.internal.core.typeinference.PHPTypeInferenceUtils;
import org.eclipse.php.internal.core.typeinference.context.FileContext;
import org.eclipse.php.internal.core.typeinference.context.MethodContext;
import org.eclipse.php.internal.core.typeinference.goals.ForeachStatementGoal;
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
					this.results.add(instanceType);
				} else {
					this.results.add(new SimpleType(SimpleType.TYPE_NULL));
				}
				return IGoal.NO_GOALS;
			}
		}

		try {
			if (context instanceof ISourceModuleContext) {
				ISourceModuleContext typedContext = (ISourceModuleContext) context;
				ASTNode node = /*(context instanceof MethodContext) ? ((MethodContext) context).getMethodNode() :*/ typedContext.getRootNode();
				VariableDeclarationSearcher varDecSearcher = new VariableDeclarationSearcher(typedContext.getSourceModule(), variableReference);
				node.traverse(varDecSearcher);

				List<IGoal> subGoals = new LinkedList<IGoal>();

				LinkedList<ASTNode> declarations = varDecSearcher.getDeclarations();
				if (varDecSearcher.needsMergingWithGlobalScope() || (declarations.isEmpty() && context.getClass() == FileContext.class)) {
					// collect all global variables, and merge results with existing declarations
					subGoals.add(new GlobalVariableReferencesGoal(context, variableReference.getName()));
				}
				for (ASTNode declaration : declarations) {
					if (declaration instanceof ForEachStatement) {
						subGoals.add(new ForeachStatementGoal(context, ((ForEachStatement) declaration).getExpression()));
					} else {
						subGoals.add(new VariableDeclarationGoal(context, declaration));
					}
				}
				return subGoals.toArray(new IGoal[subGoals.size()]);
			}
		} catch (Exception e) {
			if (DLTKCore.DEBUG) {
				e.printStackTrace();
			}
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

	class VariableDeclarationSearcher extends org.eclipse.php.internal.core.typeinference.VariableDeclarationSearcher {

		private final String variableName;
		private final int variableOffset;
		private int seenGlobal = 0;
		private boolean mergeWithGlobalScope;
		private int variableLevel;
		private IContext variableContext;

		public VariableDeclarationSearcher(ISourceModule sourceModule, VariableReference variableReference) {
			super(sourceModule);
			variableName = variableReference.getName();
			variableOffset = variableReference.sourceStart();
		}

		public LinkedList<ASTNode> getDeclarations() {
			LinkedList<ASTNode> declList = getDeclList(variableContext, variableName);
			
			int nullIdx;
			// Remove all outer level declarations
			for (int i = 0; i < variableLevel; ++i) {
				declList.set(i, null);
			}
			while ((nullIdx = declList.indexOf(null)) != -1) {
				declList.remove(nullIdx);
			}
			return declList;
		}

		public boolean needsMergingWithGlobalScope() {
			return mergeWithGlobalScope;
		}

		protected void postProcessVarAssignment(Assignment node) {
			super.postProcessVarAssignment(node);

			Expression variable = node.getVariable();
			if (variable instanceof VariableReference) {
				VariableReference variableReference = (VariableReference) variable;
				if (variableName.equals(variableReference.getName())) {
					if (blockLevel <= seenGlobal) { // if current level is lower than one where global statement has been seen - all global vars where overriden
						mergeWithGlobalScope = false;
					}
				}
			}
		}

		public boolean visit(Statement s) throws Exception {
			boolean visit = super.visit(s);
			if (!visit) {
				return false;
			}
			if (s instanceof GlobalStatement) {
				GlobalStatement globalStatement = (GlobalStatement) s;
				for (Expression variable : globalStatement.getVariables()) {
					if (variable instanceof VariableReference) {
						VariableReference variableReference = (VariableReference) variable;
						if (variableReference.getName().equals(variableName)) {
							seenGlobal = blockLevel;
							mergeWithGlobalScope = true;
						}
					}
				}
			}
			return visit;
		}
		
		public boolean visitGeneral(ASTNode node) throws Exception {
			boolean visit = super.visitGeneral(node);
			if (node.sourceStart() == variableOffset) {
				variableLevel = blockLevel;
				variableContext = contextStack.peek();
			}
			return visit;
		}

		protected boolean interesting(ASTNode node) {
			return node.sourceStart() < variableOffset;
		}
	}
}
