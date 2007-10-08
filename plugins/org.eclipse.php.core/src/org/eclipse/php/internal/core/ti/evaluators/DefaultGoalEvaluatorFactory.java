package org.eclipse.php.internal.core.ti.evaluators;

import org.eclipse.php.internal.core.ast.nodes.ASTNode;
import org.eclipse.php.internal.core.ast.nodes.Assignment;
import org.eclipse.php.internal.core.ti.goals.ExpressionTypeGoal;
import org.eclipse.php.internal.core.ti.goals.IGoal;

public class DefaultGoalEvaluatorFactory implements IGoalEvaluatorFactory {

	public IGoalEvaluator createEvaluator(IGoal goal) {
		if (goal instanceof ExpressionTypeGoal) {
			ExpressionTypeGoal expGoal = (ExpressionTypeGoal) goal;
			ASTNode node = expGoal.getExpression();
			
			if (node instanceof Assignment) {
				return new AssignmentEvaluator(goal);
			}
		}
		return null;
	}

}
