/*******************************************************************************
 * Copyright (c) 2009, 2015, 2016 IBM Corporation and others.
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

import java.util.LinkedList;
import java.util.List;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.ti.GoalState;
import org.eclipse.dltk.ti.IContext;
import org.eclipse.dltk.ti.goals.ExpressionTypeGoal;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.core.compiler.ast.nodes.LambdaFunctionDeclaration;
import org.eclipse.php.core.compiler.ast.nodes.PHPMethodDeclaration;
import org.eclipse.php.core.compiler.ast.nodes.ReturnStatement;
import org.eclipse.php.core.compiler.ast.nodes.YieldExpression;
import org.eclipse.php.internal.core.compiler.ast.parser.ASTUtils;
import org.eclipse.php.internal.core.typeinference.GeneratorClassType;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;
import org.eclipse.php.internal.core.typeinference.PHPSimpleTypes;
import org.eclipse.php.internal.core.typeinference.PHPTypeInferenceUtils;
import org.eclipse.php.internal.core.typeinference.context.IModelCacheContext;
import org.eclipse.php.internal.core.typeinference.context.MethodContext;
import org.eclipse.php.internal.core.typeinference.goals.MethodElementReturnTypeGoal;

public class MethodReturnTypeEvaluator extends AbstractMethodReturnTypeEvaluator {

	private final List<IEvaluatedType> evaluated = new LinkedList<IEvaluatedType>();
	private final List<IEvaluatedType> yieldEvaluated = new LinkedList<IEvaluatedType>();
	private final List<IGoal> yieldGoals = new LinkedList<IGoal>();

	public MethodReturnTypeEvaluator(IGoal goal) {
		super(goal);
	}

	public IGoal[] init() {
		MethodElementReturnTypeGoal goal = (MethodElementReturnTypeGoal) getGoal();

		final List<IGoal> subGoals = new LinkedList<IGoal>();
		MethodsAndTypes mat = getMethodsAndTypes();
		for (int i = 0; i < mat.methods.length; i++) {
			IMethod method = mat.methods[i];
			if (method == null) {
				continue;
			}

			ISourceModule sourceModule = method.getSourceModule();
			ModuleDeclaration module = SourceParserUtil.getModuleDeclaration(sourceModule);

			MethodDeclaration decl = null;
			try {
				decl = PHPModelUtils.getNodeByMethod(module, method);
			} catch (ModelException e) {
				if (DLTKCore.DEBUG) {
					e.printStackTrace();
				}
			}
			// final boolean found[] = new boolean[1];
			if (decl != null) {
				final IContext innerContext = ASTUtils.findContext(sourceModule, module, decl);
				if (innerContext instanceof MethodContext) {
					MethodContext mc = (MethodContext) innerContext;
					mc.setCurrentType(mat.types[i]);
				}
				if (goal.getContext() instanceof IModelCacheContext && innerContext instanceof IModelCacheContext) {
					((IModelCacheContext) innerContext).setCache(((IModelCacheContext) goal.getContext()).getCache());
				}

				final MethodDeclaration topDeclaration = decl;
				if (topDeclaration instanceof PHPMethodDeclaration) {
					PHPMethodDeclaration methodDeclaration = (PHPMethodDeclaration) topDeclaration;
					if (methodDeclaration.getReturnType() != null) {
						subGoals.add(new ExpressionTypeGoal(innerContext, methodDeclaration.getReturnType()));
						continue;
					}
				}
				ASTVisitor visitor = new ASTVisitor() {
					public boolean visitGeneral(ASTNode node) throws Exception {
						// https://bugs.eclipse.org/bugs/show_bug.cgi?id=464921
						// do not evaluate content of inner lambda functions
						if (node instanceof LambdaFunctionDeclaration
								// but never exclude top node (even if this case
								// cannot
								// happen here)
								&& node != topDeclaration) {
							return false;
						}
						if (node instanceof ReturnStatement) {
							ReturnStatement statement = (ReturnStatement) node;
							Expression expr = statement.getExpr();
							if (expr == null) {
								evaluated.add(PHPSimpleTypes.VOID);
							} else {
								subGoals.add(new ExpressionTypeGoal(innerContext, expr));
							}
						} else if (node instanceof YieldExpression) {
							YieldExpression statement = (YieldExpression) node;
							Expression expr = statement.getExpr();
							if (expr == null) {
								yieldEvaluated.add(PHPSimpleTypes.NULL);
							} else {
								final ExpressionTypeGoal yg = new ExpressionTypeGoal(innerContext, expr);
								subGoals.add(yg);
								yieldGoals.add(yg);
							}
						}
						return super.visitGeneral(node);
					}
				};

				try {
					decl.traverse(visitor);
				} catch (Exception e) {
					if (DLTKCore.DEBUG) {
						e.printStackTrace();
					}
				}
			}
		}

		return subGoals.toArray(new IGoal[subGoals.size()]);
	}

	public IGoal[] subGoalDone(IGoal subgoal, Object result, GoalState state) {
		if (state != GoalState.RECURSIVE && result != null) {
			if (!yieldGoals.contains(subgoal)) {
				evaluated.add((IEvaluatedType) result);
			} else {
				yieldEvaluated.add((IEvaluatedType) result);
			}
		}
		return IGoal.NO_GOALS;
	}

	public Object produceResult() {
		if (yieldEvaluated.size() > 0 || yieldGoals.size() > 0) {
			GeneratorClassType generatorClassType = new GeneratorClassType();
			generatorClassType.getTypes().addAll(yieldEvaluated);
			evaluated.add(generatorClassType);
		}
		return PHPTypeInferenceUtils.combineTypes(evaluated);
	}

}
