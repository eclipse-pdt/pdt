package org.eclipse.php.internal.core.typeinference.evaluators;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ti.GoalState;
import org.eclipse.dltk.ti.IContext;
import org.eclipse.dltk.ti.goals.ExpressionTypeGoal;
import org.eclipse.dltk.ti.goals.GoalEvaluator;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.internal.core.compiler.ast.nodes.*;
import org.eclipse.php.internal.core.typeinference.ArrayDeclaration;
import org.eclipse.php.internal.core.typeinference.Declaration;
import org.eclipse.php.internal.core.typeinference.PHPTypeInferenceUtils;
import org.eclipse.php.internal.core.typeinference.goals.ArrayDeclarationGoal;
import org.eclipse.php.internal.core.typeinference.goals.ForeachStatementGoal;

public class ArrayDeclarationGoalEvaluator extends GoalEvaluator {

	private List<IEvaluatedType> evaluated = new LinkedList<IEvaluatedType>();

	public ArrayDeclarationGoalEvaluator(IGoal goal) {
		super(goal);
	}

	public IGoal[] init() {
		ArrayDeclarationGoal typedGoal = (ArrayDeclarationGoal) goal;

		List<IGoal> subGoals = new LinkedList<IGoal>();

		if ((typedGoal.getExpression().getNode() instanceof Assignment)) {

			if ((((Assignment) typedGoal.getExpression().getNode()).getValue() instanceof ArrayCreation)) {
				ArrayCreation arrayCreation = (ArrayCreation) ((Assignment) typedGoal
						.getExpression().getNode()).getValue();

				for (ArrayElement arrayElement : arrayCreation.getElements()) {
					subGoals.add(new ExpressionTypeGoal(typedGoal.getContext(),
							arrayElement.getValue()));
				}
				subGoals.toArray(new IGoal[subGoals.size()]);

				List<Declaration> decls = typedGoal.getExpression()
						.getDeclarations();

				IContext context = goal.getContext();
				for (int i = 0; i < decls.size(); ++i) {
					Declaration decl = decls.get(i);
					// TODO check ArrayCreation and its element type
					if (decl instanceof ArrayDeclaration) {
						ArrayDeclaration arrayDeclaration = (ArrayDeclaration) decl;
						subGoals.add(new ArrayDeclarationGoal(context,
								arrayDeclaration));
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

			} else if ((((Assignment) typedGoal.getExpression().getNode())
					.getValue() instanceof PHPCallExpression)) {
				// https://bugs.eclipse.org/bugs/show_bug.cgi?id=336995
				// TODO $form['path_redirect']['table'] =
				// path_redirect_list_redirects(array(), array('redirect' =>
				// 'node/' . $form['#node']->nid));
			} else {
				// TODO other case
			}

		}

		return subGoals.toArray(new IGoal[subGoals.size()]);
	}

	public Object produceResult() {
		return PHPTypeInferenceUtils.combineMultiType(evaluated);
	}

	public IGoal[] subGoalDone(IGoal subgoal, Object result, GoalState state) {
		if (state != GoalState.RECURSIVE) {
			evaluated.add((IEvaluatedType) result);
		}
		return IGoal.NO_GOALS;
	}
}
