package org.eclipse.php.internal.core.typeinference;

import org.eclipse.dltk.ti.DefaultTypeInferencer;
import org.eclipse.dltk.ti.EvaluatorStatistics;
import org.eclipse.dltk.ti.TimelimitPruner;
import org.eclipse.dltk.ti.goals.AbstractTypeGoal;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.dltk.ti.goals.MethodReturnTypeGoal;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.internal.core.typeinference.evaluators.PHPGoalEvaluatorFactory;
import org.eclipse.php.internal.core.typeinference.goals.phpdoc.PHPDocMethodReturnTypeGoal;

public class PHPTypeInferencer extends DefaultTypeInferencer {

	public PHPTypeInferencer() {
		super(new PHPGoalEvaluatorFactory());
	}

	public IEvaluatedType evaluateTypePHPDoc(AbstractTypeGoal goal, int timeout) {
		return super.evaluateType(goal, new HeavyGoalsPruner(timeout));
	}

	public IEvaluatedType evaluateTypeHeavy(AbstractTypeGoal goal, int timeout) {
		return super.evaluateType(goal, new PHPDocGoalsPruner(timeout));
	}

	/**
	 * This class prunes all PHP goals except for PHPDoc based goals
	 */
	class HeavyGoalsPruner extends TimelimitPruner {

		public HeavyGoalsPruner(long timeLimit) {
			super(timeLimit);
		}

		public boolean prune(IGoal goal, EvaluatorStatistics stat) {
			if (goal instanceof MethodReturnTypeGoal) {
				return true;
			}
			return super.prune(goal, stat);
		}
	}

	/**
	 * This class prunes all PHPDoc based goals
	 */
	class PHPDocGoalsPruner extends TimelimitPruner {

		public PHPDocGoalsPruner(long timeLimit) {
			super(timeLimit);
		}

		public boolean prune(IGoal goal, EvaluatorStatistics stat) {
			if (goal instanceof PHPDocMethodReturnTypeGoal) {
				return true;
			}
			return super.prune(goal, stat);
		}
	}
}
