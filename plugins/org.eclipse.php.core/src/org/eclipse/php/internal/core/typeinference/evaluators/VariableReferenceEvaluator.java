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
package org.eclipse.php.internal.core.typeinference.evaluators;

import java.util.*;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ast.references.TypeReference;
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
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.compiler.ast.nodes.*;
import org.eclipse.php.internal.core.project.ProjectOptions;
import org.eclipse.php.internal.core.typeinference.ArrayDeclaration;
import org.eclipse.php.internal.core.typeinference.Declaration;
import org.eclipse.php.internal.core.typeinference.PHPTypeInferenceUtils;
import org.eclipse.php.internal.core.typeinference.context.ContextFinder;
import org.eclipse.php.internal.core.typeinference.context.FileContext;
import org.eclipse.php.internal.core.typeinference.context.MethodContext;
import org.eclipse.php.internal.core.typeinference.goals.ArrayDeclarationGoal;
import org.eclipse.php.internal.core.typeinference.goals.ForeachStatementGoal;
import org.eclipse.php.internal.core.typeinference.goals.GlobalVariableReferencesGoal;

/**
 * This evaluator finds all local variable declarations and produces the
 * following sub-goals: {@link GlobalVariableReferencesGoal} or
 * {@link VariableDeclarationGoal}
 */
public class VariableReferenceEvaluator extends GoalEvaluator {

	private List<IEvaluatedType> results = new ArrayList<IEvaluatedType>();

	public VariableReferenceEvaluator(IGoal goal) {
		super(goal);
	}

	public IGoal[] init() {
		final VariableReference variableReference = (VariableReference) ((ExpressionTypeGoal) goal)
				.getExpression();
		IContext context = goal.getContext();

		// Handle $this variable reference
		if (variableReference.getName().equals("$this")) { //$NON-NLS-1$
			if (context instanceof MethodContext) {
				MethodContext methodContext = (MethodContext) context;
				final LambdaFunctionDeclaration[] lambdas = new LambdaFunctionDeclaration[1];
				ContextFinder contextFinder = new ContextFinder(
						methodContext.getSourceModule()) {
					@Override
					public boolean visit(Expression s) throws Exception {
						if (s instanceof LambdaFunctionDeclaration) {
							LambdaFunctionDeclaration lambda = (LambdaFunctionDeclaration) s;
							if (variableReference.sourceStart() > lambda
									.sourceStart()
									&& variableReference.sourceEnd() < lambda
											.sourceEnd()) {
								lambdas[0] = lambda;
							}
						}
						return super.visit(s);
					}
				};
				try {
					methodContext.getRootNode().traverse(contextFinder);
				} catch (Exception e) {
				}
				PHPVersion phpVersion = ProjectOptions
						.getPhpVersion(methodContext.getSourceModule()
								.getScriptProject().getProject());
				if (lambdas[0] != null
						&& (lambdas[0].isStatic() || phpVersion
								.isLessThan(PHPVersion.PHP5_4))) {
					this.results.add(new SimpleType(SimpleType.TYPE_NULL));
				} else {
					IEvaluatedType instanceType = methodContext
							.getInstanceType();
					if (instanceType != null) {
						this.results.add(instanceType);
					} else {
						this.results.add(new SimpleType(SimpleType.TYPE_NULL));
					}
				}
				return IGoal.NO_GOALS;
			}
		}

		try {
			if (context instanceof ISourceModuleContext) {
				ISourceModuleContext typedContext = (ISourceModuleContext) context;
				ASTNode rootNode = typedContext.getRootNode();
				ASTNode localScopeNode = rootNode;
				if (context instanceof MethodContext) {
					localScopeNode = ((MethodContext) context).getMethodNode();
				}
				LocalReferenceDeclSearcher varDecSearcher = new LocalReferenceDeclSearcher(
						typedContext.getSourceModule(), variableReference,
						localScopeNode);
				rootNode.traverse(varDecSearcher);

				List<IGoal> subGoals = new LinkedList<IGoal>();

				List<VarComment> varComments = ((PHPModuleDeclaration) rootNode)
						.getVarComments();
				List<VarComment> newList = new ArrayList<VarComment>();
				newList.addAll(varComments);
				Collections.sort(newList, new Comparator<VarComment>() {

					public int compare(VarComment o1, VarComment o2) {
						return o2.sourceStart() - o1.sourceStart();
					}
				});
				for (VarComment varComment : newList) {
					if (varComment.sourceStart() > variableReference
							.sourceStart()) {
						continue;
					}
					if (varComment.getVariableReference().getName()
							.equals(variableReference.getName())) {
						List<IGoal> goals = new LinkedList<IGoal>();
						for (TypeReference ref : varComment.getTypeReferences()) {
							goals.add(new ExpressionTypeGoal(context, ref));
						}
						return (IGoal[]) goals.toArray(new IGoal[goals.size()]);
					}
				}

				Declaration[] decls = varDecSearcher.getDeclarations();
				boolean mergeWithGlobalScope = false;
				for (int i = 0; i < decls.length; ++i) {
					Declaration decl = decls[i];
					// TODO check ArrayCreation and its element type
					if (decl instanceof ArrayDeclaration) {
						ArrayDeclaration arrayDeclaration = (ArrayDeclaration) decl;
						subGoals.add(new ArrayDeclarationGoal(context,
								arrayDeclaration));
					} else if (decl.getNode() instanceof GlobalStatement) {
						mergeWithGlobalScope = true;
					} else {
						ASTNode declNode = decl.getNode();
						if (declNode instanceof ForEachStatement) {
							subGoals.add(new ForeachStatementGoal(context,
									((ForEachStatement) declNode)
											.getExpression()));
						} else {
							subGoals.add(new ExpressionTypeGoal(context,
									declNode));
						}
					}
				}
				if (mergeWithGlobalScope
						|| (decls.length == 0 && context.getClass() == FileContext.class)) {
					// collect all global variables, and merge results with
					// existing declarations
					subGoals.add(new GlobalVariableReferencesGoal(context,
							variableReference.getName()));
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

	public static class LocalReferenceDeclSearcher
			extends
			org.eclipse.php.internal.core.typeinference.VariableDeclarationSearcher {

		private final String variableName;
		private final int variableOffset;
		private final ASTNode localScopeNode;
		private IContext variableContext;
		private int variableLevel;

		public LocalReferenceDeclSearcher(ISourceModule sourceModule,
				VariableReference variableReference, ASTNode localScopeNode) {
			super(sourceModule);
			variableName = variableReference.getName();
			variableOffset = variableReference.sourceStart();
			this.localScopeNode = localScopeNode;
		}

		public Declaration[] getDeclarations() {
			Declaration[] declarations = getScope(variableContext)
					.getDeclarations(variableName);
			if (variableLevel > 0 && variableLevel < declarations.length) {
				Declaration[] newDecls = new Declaration[declarations.length
						- variableLevel];
				System.arraycopy(declarations, variableLevel, newDecls, 0,
						newDecls.length);
				declarations = newDecls;
			}

			List<Declaration> filteredDecls = new LinkedList<Declaration>();
			for (Declaration decl : declarations) {
				if (decl.getNode().sourceStart() > localScopeNode.sourceStart()) {
					filteredDecls.add(decl);
				}
			}
			return (Declaration[]) filteredDecls
					.toArray(new Declaration[filteredDecls.size()]);
		}

		protected void postProcess(Expression node) {
			if (node instanceof InstanceOfExpression) {
				InstanceOfExpression expr = (InstanceOfExpression) node;
				if (expr.getExpr() instanceof VariableReference) {
					VariableReference varReference = (VariableReference) expr
							.getExpr();
					if (variableName.equals(varReference.getName())) {
						getScope().addDeclaration(variableName,
								expr.getClassName());
					}
				}
			}
		}

		protected void postProcessGeneral(ASTNode node) {
			if (node.sourceStart() <= variableOffset
					&& node.sourceEnd() >= variableOffset) {
				variableContext = contextStack.peek();
				variableLevel = getScope(variableContext).getInnerBlockLevel();
			}
		}

		protected void postProcess(Statement node) {
		}

		protected boolean isInteresting(ASTNode node) {
			return node.sourceStart() <= variableOffset;
		}
	}
}
