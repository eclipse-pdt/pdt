package org.eclipse.php.core.tests.typeinference.evaluators;

import org.eclipse.dltk.ti.GoalState;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.core.compiler.ast.nodes.NamespaceReference;
import org.eclipse.php.internal.core.typeinference.PHPClassType;
import org.eclipse.php.internal.core.typeinference.evaluators.AbstractMethodReturnTypeEvaluator;
import org.eclipse.php.internal.core.typeinference.goals.FactoryMethodReturnTypeGoal;

public class TestFactoryMethodReturnTypeEvaluator extends AbstractMethodReturnTypeEvaluator {

	private static final String FACTORY_CLASS = "\\FactoryNamespace\\FactoryClass";
	private static final String FACTORY_METHOD = "factoryMethod";
	private static final int FACTORY_ARGUMENTINDEX = 0;

	public TestFactoryMethodReturnTypeEvaluator(IGoal goal) {
		super(goal);
	}

	public IGoal[] init() {
		return IGoal.NO_GOALS;
	}

	public IGoal[] subGoalDone(IGoal subgoal, Object result, GoalState state) {
		return IGoal.NO_GOALS;
	}

	public Object produceResult() {
		IEvaluatedType et = ((FactoryMethodReturnTypeGoal) this.goal).getEvaluatedType();
		if (et == null) {
			return null;
		}
		String className = et.getTypeName();
		if (className == null || className.isEmpty()) {
			return null;
		}
		if (!FACTORY_CLASS.equalsIgnoreCase(className)) {
			return null;
		}
		if (!FACTORY_METHOD.equalsIgnoreCase(((FactoryMethodReturnTypeGoal) this.goal).getMethodName())) {
			return null;
		}
		String[] stringArguments = ((FactoryMethodReturnTypeGoal) this.goal).getStringArguments();
		if (stringArguments.length < FACTORY_ARGUMENTINDEX || stringArguments[FACTORY_ARGUMENTINDEX] == null) {
			return null;
		}
		return new PHPClassType(NamespaceReference.NAMESPACE_DELIMITER.concat(stringArguments[FACTORY_ARGUMENTINDEX]));
	}

}
